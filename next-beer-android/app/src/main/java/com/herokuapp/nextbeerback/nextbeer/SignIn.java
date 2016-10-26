package com.herokuapp.nextbeerback.nextbeer;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;

import static android.R.attr.password;

public class SignIn extends AppCompatActivity {

    // API URL
    final String url = "https://nextbeerback.herokuapp.com/api/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
    }

    public void signIn(View v) {
        // Used for creating a new intent if sign in is successful
        final Intent intent = new Intent(this, MainContent.class);

        // Getting user input for sign in
        EditText username_input = (EditText)findViewById(R.id.username_input);
        String username = username_input.getText().toString();

        EditText password_input = (EditText)findViewById(R.id.password_input);
        String password = password_input.getText().toString();

        // Creating the url for signing in
        String signin_url = "https://nextbeerback.herokuapp.com/api/auth?username=" +
                            username +
                            "&password=" +
                            password;

        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);

        // Request a string response from the provided URL.
        StringRequest accessRequest = new StringRequest(Request.Method.GET, signin_url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        startActivity(intent);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        // Add the request to the RequestQueue.
        queue.add(accessRequest);
    }

    public void signUp(View v) {
        Intent intent = new Intent(this, SignUp.class);
        startActivity(intent);
    }
}
