package com.my.ufswe;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;

/**
 * Created by Ling on 10/09/2015.
 */

public class AboutSWE extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about_swe);     // Link the about_swe.xml layout file to correct class
        WebView mWebView = (WebView) findViewById(R.id.webview);
        mWebView.loadUrl("http://sweufonline.weebly.com/about-swe.html");
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }
}
