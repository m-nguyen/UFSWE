package com.my.ufswe;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.Typeface;
import android.preference.PreferenceManager;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import java.io.IOException;
import java.util.ArrayList;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import android.os.AsyncTask;
import android.widget.TextView;

import com.parse.ParseAnonymousUtils;
import com.parse.ParseUser;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private NavigationView mDrawer;
    private DrawerLayout mDrawerLayout;
    public ActionBarDrawerToggle mDrawerToggle;
    private static final String FIRST_TIME = "first_time";
    private boolean userSeeDrawer = false;

    boolean isGBM = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        Toolbar mToolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(mToolbar);

        mDrawer = (NavigationView) findViewById(R.id.main_drawer);
        if (!isGBM) {
            MenuItem item = mDrawer.getMenu().findItem(R.id.gbm);
            item.setVisible(false);
        }
        mDrawer.setNavigationItemSelectedListener(this);        // Tell this activity is the one to handle the events
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle mDrawerToggle = new ActionBarDrawerToggle(
                this,                 /* host Activity */
                mDrawerLayout,        /* DrawerLayout object */
                mToolbar,
                R.string.drawer_open,               /* "open drawer" description */
                R.string.drawer_close);             /* "close drawer" description */
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        mDrawerToggle.syncState();

        if (didUserSeeDrawer()) {
            hideDrawer();
        } else {
            showDrawer();
            markUserSeeDrawer();
        }
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem menuItem) {
        Intent intent;
        // Checking which tab was selected
        if (menuItem.getItemId() == R.id.navigation_home) {
            mDrawerLayout.closeDrawer(GravityCompat.START);     // Close the drawing after clicking tab
            if (this != com.my.ufswe.MainActivity.this) {
                intent = new Intent(this, MainActivity.class);
                startActivity(intent);
            }
            return true;
        }
        if (menuItem.getItemId() == R.id.navigation_aboutSWE) {
            mDrawerLayout.closeDrawer(GravityCompat.START);     // Close the drawing after clicking tab
            intent = new Intent(this, AboutSWE.class);
            startActivity(intent);
            return true;
        }
        if (menuItem.getItemId() == R.id.navigation_events) {
            mDrawerLayout.closeDrawer(GravityCompat.START);     // Close the drawing after clicking tab
            intent = new Intent(this, Events.class);
            startActivity(intent);
            return true;
        }
        if (menuItem.getItemId() == R.id.navigation_officers) {
            mDrawerLayout.closeDrawer(GravityCompat.START);     // Close the drawing after clicking tab
            intent = new Intent(this, Officers.class);
            startActivity(intent);
            return true;
        }
        if (menuItem.getItemId() == R.id.gbm) {
            mDrawerLayout.closeDrawer(GravityCompat.START);     // Close the drawing after clicking tab
            // Determine whether the current user is an anonymous user
            if (!ParseAnonymousUtils.isLinked(ParseUser.getCurrentUser())) {    // if not send to homepage
                intent = new Intent(this, Homepage.class);
                intent.putExtra("KEY_GBM", isGBM);
                startActivity(intent);
                //this.startActivity(new Intent(this, Homepage.class));
                return true;
            } else {
                intent = new Intent(this, GBMLoginActivity.class);
                startActivity(intent);
                return true;
            }
        }

        return false;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id ==  R.id.action_signIn) {
            // Determine whether the current user is an anonymous user
            if (ParseAnonymousUtils.isLinked(ParseUser.getCurrentUser())) {
                // If user is anonymous, send the user to Login view
                //this.startActivity(new Intent(this, Login.class));
                Intent intent = new Intent(this, Login.class);
                intent.putExtra("KEY_GBM", isGBM);
                startActivity(intent);
                return true;
            } else {
                // If current user is NOT an anonymous user then get current user data from Parse.com
                ParseUser currentUser = ParseUser.getCurrentUser();
                if (currentUser != null) {
                    // Send logged in user to homepage
                    Intent intent = new Intent(this, Homepage.class);
                    intent.putExtra("KEY_GBM", isGBM);
                    startActivity(intent);
                    //this.startActivity(new Intent(this, Homepage.class));
                    return true;
                } else {
                    // Send user to Login view
                    //this.startActivity(new Intent(this, Login.class));
                    Intent intent = new Intent(this, Login.class);
                    intent.putExtra("KEY_GBM", isGBM);
                    startActivity(intent);
                    return true;
                }
            }
        }
        if (id == R.id.action_signUp) {
            //this.startActivity(new Intent(this, Signup.class));
            Intent intent = new Intent(this, Signup.class);
            intent.putExtra("KEY_GBM", isGBM);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    // For when user open the drawer navigation
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    // When clicking the back button on phone, close the drawer instead of closing the whole app
    @Override
    public void onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    // Check if the user see the drawer; when user start app they do not see drawer
    private boolean didUserSeeDrawer() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        userSeeDrawer = sharedPreferences.getBoolean(FIRST_TIME, false);
        return userSeeDrawer;
    }

    // Hide the drawer once user have seen the drawer
    private void markUserSeeDrawer() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        userSeeDrawer = true;
        sharedPreferences.edit().putBoolean(FIRST_TIME, userSeeDrawer).apply();
    }

    private void showDrawer() {
        mDrawerLayout.openDrawer(GravityCompat.START);
    }

    private void hideDrawer() {
        mDrawerLayout.closeDrawer(GravityCompat.START);
    }

    // Title AsyncTask
    private class Title extends AsyncTask<Void, Void, Void> {
        String title;

        @Override
        protected Void doInBackground(Void... params) {
            try {
                // Connect to the web site
                Document document = Jsoup.connect("http://sweufonline.weebly.com/").get();
                // Get the html document title
                title = document.select("div.paragraph").eq(0).text();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            // Set and add styling to title into TextView
            TextView titleView =(TextView)findViewById(R.id.titleText);
            titleView.setTypeface(null, Typeface.BOLD);
            titleView.setTextSize(15);
            titleView.setText(title);
        }
    }

    // SubTitle AsyncTask
    private class SubTitle extends AsyncTask<Void, Void, Void> {
        String subTitle;
        String subText;

        @Override
        protected Void doInBackground(Void... params) {
            try {
                // Connect to the web site
                Document document = Jsoup.connect("http://sweufonline.weebly.com/").get();
                // Get the html document subtitle & text
                subTitle = document.select("div.paragraph").eq(1).text().substring(0, 22).trim();
                subText = document.select("div.paragraph").eq(1).text().substring(22, 142).trim();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            // Set and add styling to subtitle into TextView
            TextView subTitleView =(TextView)findViewById(R.id.subTitleText);
            subTitleView.setTextColor(Color.BLUE);
            //subTitleView.setTypeface(null, Typeface.BOLD);
            subTitleView.setTextSize(14);
            subTitleView.setText(subTitle + "\n");
            subTitleView.setTextColor(Color.BLACK);
            //subTitleView.setTypeface(null, Typeface.NORMAL);
            subTitleView.append(subText + ":");
        }
    }

    private class Texts extends AsyncTask<Void, Void, ArrayList<String>> {
        ArrayList<String> currentEvent = new ArrayList<>();

        @Override
        protected ArrayList<String> doInBackground(Void... params) {
            Document doc;
            String text;
            String cutOff;

            try {
                doc = Jsoup.connect("http://sweufonline.weebly.com/").get();
                Elements title = doc.select("div.paragraph").eq(1);
                //Elements currentEvents = doc.select("div.paragraph font[size=\"4\"]");

                int y = 1;
                for (int i = 1; i < 9; i++) {
                    text = doc.select("div.paragraph font[color=\"#515151\"]").eq(i).text();
                    if (i == 5) {
                        cutOff = doc.select("div.paragraph font[size=\"4\"]").eq(2).text();
                        currentEvent.add(cutOff);
                    }
                    if (i == 7) {
                        cutOff = doc.select("div.paragraph font[size=\"4\"]").eq(3).text();
                        currentEvent.add(cutOff);
                    }
                    currentEvent.add(text);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return currentEvent;    // Return currents from here
        }

        @Override
        protected void onPostExecute(ArrayList<String> result) {
            // Get all the values from result to display in TextViews
            TextView textView =(TextView)findViewById(R.id.textView);

            // The main view body
            textView.setTextColor(Color.BLACK);
            //textView.setTextSize(12);
            int x = 0;
            for (String temp_result : result) {
                Log.d("Printing text", temp_result);
                    if (x == 4 || x == 7) {
                        textView.append(temp_result + "\n");
                    } else  {
                        textView.append(temp_result + "\n" + "\n");
                    }
                x++;
            }
        }
    }
}
