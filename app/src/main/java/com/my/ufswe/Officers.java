package com.my.ufswe;

import android.app.ProgressDialog;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.io.InputStream;

import android.util.Log;


/**
 * Created by Ling on 10/19/2015.
 */
public class Officers extends AppCompatActivity {

    ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.officers);     // Link the right xml layout file to correct class

        // Execute Pictures AsyncTask
        new FacultyPic().execute();
        new FacultyName().execute();
        new FacultyContact().execute();
        new OfficerTitle().execute();
        new OfficersPic().execute();
        new OfficerName().execute();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    // Faculty Image AsyncTask
    private class FacultyPic extends AsyncTask<Void, Void, Bitmap> {

        @Override
        protected Bitmap doInBackground(Void... params) {
            Bitmap result = null;

            try {
                // Connect to the web site
                Document document = Jsoup.connect("http://sweufonline.weebly.com/meet-our-officers.html").get();
                // Using Elements to get the class data
                Element img = document.select("img").get(1); //document.select("img").eq(0);
                // Locate the src attribute
                String imgSrc = img.absUrl("src");
                // Download image from URL
                InputStream input = new java.net.URL(imgSrc).openStream();
                // Decode Bitmap
                result = BitmapFactory.decodeStream(input);

            } catch (IOException e) {
                e.printStackTrace();
            }
            return result;
        }

        @Override
        protected void onPostExecute(Bitmap result) {
            // Set downloaded image into ImageView
            ImageView facultyIMG = (ImageView) findViewById(R.id.facultyImage);
            facultyIMG.setImageBitmap(result);
        }
    }

    // Faculty's name
    private class FacultyName extends AsyncTask<Void, Void, Void> {
        String name;

        @Override
        protected Void doInBackground(Void... params) {
            try {
                // Connect to the web site
                Document document = Jsoup.connect("http://sweufonline.weebly.com/meet-our-officers.html").get();
                // Using Elements to get the class data
                name = document.select(".paragraph font").text();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            // Set downloaded image into ImageView
            TextView facultyName = (TextView) findViewById(R.id.facultyName);
            facultyName.setText(name);
        }
    }

    // Faculty's contract
    private class FacultyContact extends AsyncTask<Void, Void, Void> {
        String email;

        @Override
        protected Void doInBackground(Void... params) {
            try {
                // Connect to the web site
                Document document = Jsoup.connect("http://sweufonline.weebly.com/meet-our-officers.html").get();
                // Using Elements to get the class data
                email = document.select(".paragraph a").text();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            // Set downloaded image into ImageView
            TextView facultyContact = (TextView) findViewById(R.id.facultyContact);
            facultyContact.setText(email);
        }
    }

    // Officers AsyncTask
    private class OfficerTitle extends AsyncTask<Void, Void, Void> {
        String title;

        @Override
        protected Void doInBackground(Void... params) {
            try {
                // Connect to the web site
                Document document = Jsoup.connect("http://sweufonline.weebly.com/meet-our-officers.html").get();
                // Using Elements to get the class data
                title = document.select("h2").eq(2).text();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            // Set downloaded image into ImageView
            TextView officerTitle = (TextView) findViewById(R.id.officersTitle);
            officerTitle.setText(title);
        }
    }

    // Officers picture AsyncTask
    private class OfficersPic extends AsyncTask<Void, Void, Bitmap> {
        Bitmap bitmap;

        @Override
        protected Bitmap doInBackground(Void... params) {

            try {
                // Connect to the web site
                Document document = Jsoup.connect("http://sweufonline.weebly.com/meet-our-officers.html").get();
                // Using Elements to get the class data
                Element img = document.select("img").get(2);
                // Locate the src attribute
                String imgSrc = img.absUrl("src");
                //Log.d("imgSrc: ", imgSrc);
                // Download image from URL
                InputStream input = new java.net.URL(imgSrc).openStream();
                // Decode Bitmap
                bitmap = BitmapFactory.decodeStream(input);

            } catch (IOException e) {
                e.printStackTrace();
            }
            return bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap result) {
            // Set downloaded image into ImageView
            ImageView officerIMG = (ImageView) findViewById(R.id.officersPic);
            officerIMG.setImageBitmap(result);
        }
    }

    // Officers name AsyncTask
    private class OfficerName extends AsyncTask<Void, Void, Void> {
        String name;

        @Override
        protected Void doInBackground(Void... params) {
            try {
                // Connect to the web site
                Document document = Jsoup.connect("http://sweufonline.weebly.com/meet-our-officers.html").get();
                // Using Elements to get the class data
                name = document.select(".paragraph").eq(2).text();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            // Set downloaded image into ImageView
            TextView officersNames = (TextView) findViewById(R.id.officersName);
            officersNames.setText(name + "\n");
        }
    }
}

