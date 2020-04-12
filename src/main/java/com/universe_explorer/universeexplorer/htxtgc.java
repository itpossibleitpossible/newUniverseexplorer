package com.universe_explorer.universeexplorer;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.Toast;

public class htxtgc extends AppCompatActivity {
    WebView webView;
    Button fh, syy;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_htxtgc);
        Toast.makeText(htxtgc.this, "正在加载中，请耐心等待",Toast.LENGTH_LONG).show();

        webView = (WebView) findViewById(R.id.webView);
        fh = (Button) findViewById(R.id.fh);
        syy = (Button) findViewById(R.id.syy);

        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);//允许运行JavaScript
        webView.loadUrl("https://baike.so.com/doc/7722086-7996181.html");             //加载外网
        webView.setWebViewClient(new htxtgc.HelloWebViewClient());

        Toast.makeText(htxtgc.this, "加载成功",Toast.LENGTH_SHORT).show();

        fh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(htxtgc.this, MainActivity.class);
                startActivity(intent);
            }
        });

        syy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                webView.goBack(); //调用goBack()返回WebView的上一页面
            }
        });

        //webView.loadUrl("file:///android_asset/XX.html");   //本地网站
    }


    //Web视图
    private class HelloWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }

    }
}

