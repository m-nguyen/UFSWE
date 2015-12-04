package com.my.ufswe;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.parse.ParseAnonymousUtils;
import com.parse.ParseUser;

/**
 * Created by Ling on 10/09/2015.
 */

public class AboutSWE extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about_swe);     // Link the about_swe.xml layout file to correct class
        final WebView mWebView = (WebView) findViewById(R.id.webview);
        //mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.loadUrl("http://sweufonline.weebly.com/about-swe.html");
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }
}
