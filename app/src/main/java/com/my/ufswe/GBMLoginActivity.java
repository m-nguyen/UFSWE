package com.my.ufswe;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.GetCallback;
import com.parse.Parse;
import com.parse.ParseAnonymousUtils;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.parse.SignUpCallback;

public class GBMLoginActivity extends MainActivity {

    Button checkIn;

    String firstNameTxt;
    String lastNameTxt;
    String majorTxt;

    EditText firstName;
    EditText lastName;
    EditText major;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (ParseAnonymousUtils.isLinked(ParseUser.getCurrentUser())) {
            // If user is anonymous, send the user to Login view
            setContentView(R.layout.gbm_checkin);

            // Locate EditTexts in gbm_checkin.xml
            firstName = (EditText) findViewById(R.id.firstName);
            lastName = (EditText) findViewById(R.id.lastName);
            major = (EditText) findViewById(R.id.major);

            // Locate Buttons in gmb_checkin.xml
            checkIn = (Button) findViewById(R.id.checkIn);

            // Sign up Button Click Listener
            checkIn.setOnClickListener(new View.OnClickListener() {
                public void onClick(View arg0) {
                    // Retrieve the text entered from the EditText
                    firstNameTxt = firstName.getText().toString();
                    lastNameTxt = lastName.getText().toString();
                    majorTxt = major.getText().toString();

                    // Force user to fill out all of the form
                    if (firstNameTxt.equals("") && lastNameTxt.equals("") && majorTxt.equals("")) {
                        Toast.makeText(getApplicationContext(),
                                "Please complete the check in form!", Toast.LENGTH_LONG).show();
                    } else {
                        // Save new user data into Parse.com Data Storage
                        ParseObject checkList = new ParseObject("GBM");
                        checkList.put("firstName", firstNameTxt);
                        checkList.put("lastName", lastNameTxt);
                        checkList.put("major", majorTxt);

                        checkList.saveInBackground(new SaveCallback() {
                            public void done(ParseException e) {
                                if (e == null) {
                                    // Saved successfully.
                                    Toast.makeText(getApplicationContext(), "Saved!", Toast.LENGTH_SHORT).show();
                                } else {
                                    // The save failed.
                                    Toast.makeText(getApplicationContext(), "Failed to Save", Toast.LENGTH_SHORT).show();
                                    Log.d(getClass().getSimpleName(), "User update error: " + e);
                                }
                            }
                        });
                    }
                }
            });
        } else {
            // If current user is NOT an anonymous user then go to homepage
            ParseUser currentUser = ParseUser.getCurrentUser();
            if (currentUser != null) {
                // Send logged in user to homepage
                this.startActivity(new Intent(this, Homepage.class));
            }
        }
    }
}
