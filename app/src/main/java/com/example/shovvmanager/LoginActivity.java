package com.example.shovvmanager;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class LoginActivity extends AppCompatActivity {

    WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        webView = (WebView)findViewById( R.id.webView );
        webView.setNetworkAvailable( true );

        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled( true );

        webView.setWebChromeClient( new WebChromeClient() );
        webView.setWebViewClient( new WebViewClient() );

        webView.loadUrl( "http://www.naver.com"); //webView로드

    }
}