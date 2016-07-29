package com.example.a212568485.login;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.view.View;
import android.widget.EditText;
import android.content.Intent;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

//This is the main activity of my Android app.
//It will ask users to enter SSO crendentials into a login screen.
//The app will access the database on my Raspberry Pi.
//If user has authorization, app will automatically log into Formidable app.

public class MainActivity extends AppCompatActivity {

    private ProgressDialog pDialog; //create progress dialog for when code is executing and you need to wait

    JSONParser jsonParser = new JSONParser(); //to send the data

    //to extrude inserted data
    EditText SSO_ID;
    EditText SSO_Password;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //link the EditTexts from above with the real editTexts in the app layout
        SSO_ID = (EditText) findViewById(R.id.SSO_ID);
        SSO_Password = (EditText) findViewById(R.id.SSO_Password);

        Log.d("after getting edittext","edittext");
    }

    //create a new function called Send that calls Get_Formidable_ID.php
    public void Send(View view) {
        String String_SSO_ID = SSO_ID.getText().toString();
        String String_SSO_Password = SSO_Password.getText().toString();

        new Get_Formidable_ID().execute(String_SSO_ID,String_SSO_Password);
    }
    //create a new class to connect to the server and send the previous typed info
    class Get_Formidable_ID extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(MainActivity.this);
            pDialog.setMessage("Authenticating ID from the database..."); //set the message for the loading window
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show(); //place the loading message on the screen

            Log.d("preexecute","ending");
        }

        @Override
        protected String doInBackground(String... args) {
          
            HashMap<String, String> params = new HashMap<>();
            params.put("SSO_ID",args[0]);
            params.put("SSO_Password",args[1]);

            Log.d("request","starting");

            //do the HTTP POST Request with the JSON parameters
            //include Raspberry Pi's IP address
            JSONObject json = jsonParser.makeHttpRequest("http://192.168.0.27/db_get_formidable_id.php", "POST", params);

            Log.d("after json","before try");

            try {
                int success = json.getInt("success");
                Log.d("after int success","inside try");

                if (success == 1) {
                    Intent i = new Intent(getApplicationContext(), ConfirmActivity.class); //open new activity to confirm successful login
                    startActivity(i);
                    finish();
                }
                Log.d("after success","before catch");
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }

        protected void onPostExecute(String file_url) {
            pDialog.dismiss(); //close the loading window when ready
        }
    }
    }

