package com.universe_explorer.universeexplorer;

import android.app.Service;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Binder;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;

public class ConsoleService extends Service implements SensorEventListener {
    //attitude euler angle
    private float[] eulerAngles = new float[3];
    //handler message and bundle
    public Handler handler;
    public Message message;
    public Bundle bundle;
    //UDP socket thread
    private WindSpeaker windSpeaker;
    //function variety
    private int fcnSelector;
    private boolean socketSwitch;
    //sensors
    private SensorManager sensorManager;
    private Sensor accel;
    private Sensor compass;
    private Sensor orient;
    private Sensor gyro;
    //the 3 value group
    private static final float nanoToSecond = 1.0f / 1000000000.0f;
    private float timeStamp;
    private boolean gyroSensingOn;
    private float[] gyroSensingValues = new float[3];
    //calculation resources
    private float[] accValues = new float[3];
    private float[] compassValues = new float[3];
    private float[] inR = new float[9];
    private float[] inclineMatrix = new float[9];
    private float[] prefValues = new float[3];
    private boolean gmReady;
    private float mAzimuth;
    private float mPitch;
    private float mRoll;
    private double mInclination;
    private int counter;
    private int mRotation;
    //Quaternion method
    private float wx, wy, wz;
    private float[] q = new float[4];
    private float[] quaternion = new float[4];
    private float[] dQuaternion = new float[4];
    private float[][] matrixA = new float[3][3]; //Rotation matrix
    private float[] gyroLinear = new float[3];
    private float[] angleJoint = new float[3];
    private int[] circles = new int[3];
    //There are 3 sensors need for Kalman filter. So 3 * 3 = 9 single Kalman filter
    private float[] estimateOld = new float[9];//the previous estimated value
    private float[] estimateNew = new float[9];//the current estimated value
    private double[] meaError = new double[9];//error in measurement
    private double[] estError = new double[9];//error in estimate
    /**
     * =========(Day day programming)=======szO====雍正爷====Orz========(Bug less less)=========
     */

    public ConsoleService() {
    }

    public class MyBinder extends Binder {
        ConsoleService getService() {
            return ConsoleService.this;
        }
    }

    private final IBinder mBinder = new MyBinder();

    @Override
    public void onCreate() {
        InitVariety();
        InitKmFltVar();
        super.onCreate();
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        return mBinder;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        //for unbind function
        if (socketSwitch) {
            DisconnectSocket();
        }
        return super.onUnbind(intent);
    }

    @Override
    public void onDestroy() {
        //close the windSpeaker thread
        if (!windSpeaker.isInterrupted()) {
            windSpeaker.interrupt();
        }
        sensorManager.unregisterListener(this);
        super.onDestroy();
    }

    //initial varieties
    private void InitVariety() {
        handler = new Handler();
        eulerAngles[0] = eulerAngles[1] = eulerAngles[2] = 0.0f;
        gyroSensingValues[0] = gyroSensingValues[1] = gyroSensingValues[2] = 0.0f;
        //the boolean values for judging if mission is already carrying on
        socketSwitch = false;
        gmReady = false;
        gyroSensingOn = false;
        fcnSelector = 0;
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        accel = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        compass = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        orient = sensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION);
        gyro = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
    }

    //Initialize the Kalman filter varieties
    private void InitKmFltVar() {
        //accelerate measurement error and estimate error.
        meaError[0] = meaError[1] = meaError[2] = 0.0001;
        estError[0] = estError[1] = estError[2] = 0.0002;
        //magnetic measurement error and estimate error.
        meaError[3] = meaError[4] = meaError[5] = 0.0000001;
        estError[3] = estError[4] = estError[5] = 0.0000002;
        //gyroscope measurement error and estimate error.
        meaError[6] = meaError[7] = meaError[8] = 0.00001;
        estError[6] = estError[7] = estError[8] = 0.00002;
        //
        for(int i = 0; i < estimateOld.length; i++) {
            estimateOld[i] = 0.0f;
        }
    }

    //initial socket
    public void InitSocket() {
        windSpeaker = new WindSpeaker();
        if (!windSpeaker.isAlive()) {
            windSpeaker.start();
        }
        socketSwitch = true;
    }

    //disconnect socket
    public void DisconnectSocket() {
        windSpeaker.interrupt();
        socketSwitch = false;
    }

    //send eulerAngle to desktop
    public void SendEulerAngles() {
        FlatEulerAngles();
        bundle = new Bundle();
        bundle.putFloatArray("attitudeToSend", eulerAngles);
        message = handler.obtainMessage();
        message.setData(bundle);
        handler.sendMessage(message);
    }

    //Gyro sensing register and unregister
    private void RegGyroSensing() {
        gyroSensingValues[0] = gyroSensingValues[1] = gyroSensingValues[2] = 0.0f;
        sensorManager.registerListener(this, gyro, SensorManager.SENSOR_DELAY_GAME);
        gyroSensingOn = true;
        System.out.println("=========reg gyroSensing=========");
    }

    private void UnRegGyroSensing() {
        sensorManager.unregisterListener(this, gyro);
        gyroSensingOn = false;
        System.out.println("=========unReg gyroSensing========");
    }



    //Calculate the init quaternion value

    /**
     * If U want to calculate the initial quaternion value by using the acceleration sensor value,
     * U might use this
     */
    private float[] EulerAngleToQuaternion(float[] initEulerAngleValue) {
        float[] initQuaternionValue = new float[4];
        float cosPhi_2, cosGamma_2, cosPsi_2;
        float sinPhi_2, sinGamma_2, sinPsi_2;
        //Calculate the triangle function, reduce the calculation
        cosPhi_2 = (float) Math.cos(initEulerAngleValue[0] / 2.0f);
        cosGamma_2 = (float) Math.cos(initEulerAngleValue[1] / 2.0f);
        cosPsi_2 = (float) Math.cos(initEulerAngleValue[2] / 2.0f);
        sinPhi_2 = (float) Math.sin(initEulerAngleValue[0] / 2.0f);
        sinGamma_2 = (float) Math.sin(initEulerAngleValue[1] / 2.0f);
        sinPsi_2 = (float) Math.sin(initEulerAngleValue[2] / 2.0f);
        //Calculate the initial quaternion value
        initQuaternionValue[0] = cosPhi_2 * cosGamma_2 * cosPsi_2
                + sinPhi_2 * sinGamma_2 * sinPsi_2;
        initQuaternionValue[1] = sinPhi_2 * cosGamma_2 * cosPsi_2
                - cosPhi_2 * sinGamma_2 * sinPsi_2;
        initQuaternionValue[2] = cosPhi_2 * sinGamma_2 * cosPsi_2
                + sinPhi_2 * cosGamma_2 * sinPsi_2;
        initQuaternionValue[3] = cosPhi_2 * cosGamma_2 * sinPsi_2
                - sinPhi_2 * sinGamma_2 * cosPsi_2;
        return initQuaternionValue;
    }

    //Calculate the quaternion by using dQuaternion
    private float[] RefreshQuaternion(float[] theQuaternion, float[] theDQuaternion, float deltaTime) {
        float[] thisQuaternion = new float[4];
        for (int i = 0; i < 4; i++) {
            thisQuaternion[i] = theQuaternion[i] + theDQuaternion[i] * deltaTime;
        }
        return thisQuaternion;
    }

    //Calculate the rotation matrix by using quaternion
    private void CalRotationMatrix() {
        //Reducing the calculation
        float
                q0q0 = q[0]*q[0],
                q0q1 = q[0]*q[1],
                q0q2 = q[0]*q[2],
                q0q3 = q[0]*q[3],
                q1q1 = q[1]*q[1],
                q1q2 = q[1]*q[2],
                q1q3 = q[1]*q[3],
                q2q2 = q[2]*q[2],
                q2q3 = q[2]*q[3],
                q3q3 = q[3]*q[3];
        //Begin the calculation
        matrixA[0][0] = q0q0 + q1q1 - q2q2 - q3q3;
        matrixA[0][1] = 2.0f * (q1q2 + q0q3);
        matrixA[0][2] = 2.0f * (q1q3 - q0q2);

        matrixA[1][0] = 2.0f * (q1q2 - q0q3);
        matrixA[1][1] = q0q0 - q1q1 + q2q2 - q3q3;
        matrixA[1][2] = 2.0f * (q2q3 + q0q1);

        matrixA[2][0] = 2.0f * (q1q3 + q0q2);
        matrixA[2][1] = 2.0f * (q2q3 - q0q1);
        matrixA[2][2] = q0q0 - q1q1 - q2q2 + q3q3;
    }

    //Matrix times array
    private float[] MatrixTimesArray(float[][] theMatrix, float[] theArray) {
        int matrixRow = theMatrix[0].length;
        int matrixColumn = theMatrix.length;
        float[] resultMatrix;
        resultMatrix = new float[matrixColumn];
        // Row manipulating
        for(int column = 0; column < matrixColumn; column++){
            resultMatrix[column] = 0.0f;
            for(int row = 0; row < matrixRow; row++){
                resultMatrix[column] += theMatrix[column][row] * theArray[row];
            }
        }
        return resultMatrix;
    }

    //Normalize an array
    private float[] NormalizeArray(float[] theArray) {
        int length = theArray.length;
        float modulus = 0.0f;
        float[] thisArray = new float[length];
        for (int i = 0; i < length; i++) {
            modulus += (float) Math.pow(theArray[i], 2);
        }
        modulus = (float) Math.sqrt(modulus);
        for (int i = 0; i < length; i++) {
            thisArray[i] = theArray[i] / modulus;
        }
        return thisArray;
    }

    //execute when sensor accuracy change
    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        //ignored
        //modify if needed
    }

    //execute when sensor value change
    @Override
    public void onSensorChanged(SensorEvent event) {
        //deal with different sensor values
        switch (event.sensor.getType()) {
            case Sensor.TYPE_ACCELEROMETER: {
                for (int i = 0; i < 3; i++) {
                        accValues[i] = event.values[i];
                }
                //gmSensing needs both G and M values to go on
                if (compassValues[0] != 0) {
                    gmReady = true;
                }
                break;
            }
            case Sensor.TYPE_MAGNETIC_FIELD: {
                for (int i = 0; i < 3; i++) {
                        compassValues[i] = event.values[i];
                }
                //gmSensing needs both G and M values to go on
                if (accValues[2] != 0) {
                    gmReady = true;
                }
                break;
            }
            //==============================Gyroscope measurement===============================
            case Sensor.TYPE_GYROSCOPE: {
                //need for redesign
                if (timeStamp != 0) {
                    float dT = (event.timestamp - timeStamp) * nanoToSecond;
                    //Quaternion array calculation is coded here
                        wx = event.values[0];
                        wy = event.values[1];
                        wz = event.values[2];
                    //===========================================
                    float[][] velocityMatrix = new float[][]{
                            {0, -wx, -wy, -wz},
                            {wx, 0, wz, -wy},
                            {wy, -wz, 0, wx},
                            {wz, wy, -wx, 0}
                    };
                    dQuaternion = MatrixTimesArray(velocityMatrix, quaternion);
                    for (int i = 0; i < 4; i++) {
                        dQuaternion[i] = 0.5f * dQuaternion[i];
                    }
                    quaternion = RefreshQuaternion(quaternion, dQuaternion, dT);
                    //Normalize the quaternion array
                    quaternion = NormalizeArray(quaternion);
                    //This is unnecessary part, it is just for visual effect
                    for (int i = 0; i < 4; i++) {
                        q[i] = quaternion[i];
                    }
                    //Calculating the rotation matrix
                    CalRotationMatrix();
                    //
                    for(int i = 0; i < 3; i++) {
                        angleJoint[i] = gyroSensingValues[i];
                    }
                    //Resolve Euler angle from quaternion
//                    gyroSensingValues[0] = (float) Math.asin(matrixA[0][2]);
//                    gyroSensingValues[1] = (float) Math.atan(matrixA[1][2] / matrixA[2][2]);
//                    gyroSensingValues[2] = (float) Math.atan(matrixA[0][1] / matrixA[0][0]);
                    gyroSensingValues[0] = (float) Math.asin(matrixA[0][2]);
                    gyroSensingValues[1] = (float) Math.atan2(matrixA[1][2], matrixA[2][2]);
                    gyroSensingValues[2] = (float) Math.atan2(matrixA[0][1], matrixA[0][0]);
                    //Convert the radial to degree
                    for (int i = 0; i < 3; i++) {
                        gyroSensingValues[i] = (float) Math.toDegrees(gyroSensingValues[i]);
                    }

                    gyroLinear[0] = gyroSensingValues[0];
                    gyroLinear[1] = gyroSensingValues[1];
                    gyroLinear[2] = gyroSensingValues[2];
                    //Apply the measurement value to output value
                    if (fcnSelector == 2) {
                        //for some reason, the eulerAngle has no linear relationship with gyroSensing value
                        eulerAngles[0] = -gyroLinear[0];
                        eulerAngles[1] = gyroLinear[1];
                        eulerAngles[2] = -gyroLinear[2];
                        //0-2,1-1,2-0
                    }
                }
                //if socket is on, and fcnSelector means gyroSensing
                if (socketSwitch) {
                    if (fcnSelector == 2) {
                        SendEulerAngles();
                    }
                }
                timeStamp = event.timestamp;
                break;
            }
        }
        //=========================gravity-magnetic field sensing algorithm================
        if (!gmReady) {
            return;
        }
        if (SensorManager.getRotationMatrix(inR, inclineMatrix, accValues, compassValues)) {
            SensorManager.getOrientation(inR, prefValues);
            mInclination = SensorManager.getInclination(inclineMatrix);
            mAzimuth = (float) Math.toDegrees(prefValues[0]);
            mPitch = (float) Math.toDegrees(prefValues[1]);
            mRoll = (float) Math.toDegrees(prefValues[2]);
            //if socket is on, and fcnSelector means gmSensing
            if (socketSwitch) {
                if (fcnSelector == 1) {
                    SendEulerAngles();
                }
            }
        }
    }

    //the method return eulerAngle to activity
    public float[] EulerAngleReturn() {
        FlatEulerAngles();
        return eulerAngles;
    }

    //flat eulerAngle to specified precision
    private void FlatEulerAngles() {
        for (int i = 0; i < 3; i++) {
            eulerAngles[i] = (Math.round(eulerAngles[i] * 10)) / 10.0f;
        }
    }

    //offer solution to select function
    public void FunctionSelect(int fcn) {
        fcnSelector = fcn;
        switch (fcn) {
            //================sliderSensing=================
            case 0: {
                //check if the other sensing is on, and deal with them
                if (gyroSensingOn) {
                    UnRegGyroSensing();
                }
                break;
            }
            //===================gmSensing====================
            case 1: {
                //check if the other sensing is on, and deal with them
                if (gyroSensingOn) {
                    UnRegGyroSensing();
                }
                //there may be sensors overlap, so register sensor listener at last
                break;
            }
            //===================gyroSensing==================
            case 2: {
                //check if the other sensing is on, and deal with them
                //reset the initial value to be {0,0,0};
                eulerAngles[0] = eulerAngles[1] = eulerAngles[2] = 0.0f;
                gyroSensingValues[0] = gyroSensingValues[1] = gyroSensingValues[2] = 0.0f;
                circles[0] = circles[1] = circles[2] = 0;
                quaternion = EulerAngleToQuaternion(gyroSensingValues);
                //there may be sensors overlap, so register sensor listener at last
                if (!gyroSensingOn) {
                    RegGyroSensing();
                }
                break;
            }
            //=====================virtualSensing===================
            case 3: {
                //check if the other sensing is on, and deal with them
                if (gyroSensingOn) {
                    UnRegGyroSensing();
                }
                break;
            }
        }
    }


    //thread to build UDP socket
    class WindSpeaker extends Thread {
        @Override
        public void start() {
            System.out.println("thread.start() is called");
            super.start();
        }

        @Override
        public void run() {
            Looper.prepare();
            handler = new Handler() {
                @Override
                public void handleMessage(Message msg) {
                    //establish socket
                    try {
                        bundle = msg.getData();
                    } catch (Exception e) {
                        e.printStackTrace();
                        System.out.println("read bundle error");
                    }
                }
            };
            Looper.loop();
        }

    }

}
