package com.my.ufswe;

/**
 * Created by Ling on 10/08/2015.
 * http://stackoverflow.com/questions/4027701/loading-existing-html-file-with-android-webview
 */

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.webkit.WebView;
import android.widget.TextView;

import java.io.FileReader;
import java.util.Iterator;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class Events extends AppCompatActivity {

    WebView mWebview;
    TextView mTextview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.events);

        mWebview = (WebView) findViewById(R.id.mybrowser);
        mWebview.getSettings().setJavaScriptEnabled(true);

        Log.d("WebView", "file:///android_asset/calendar.html");
        mWebview.loadUrl("file:///android_asset/calendar.html");

    }
}

