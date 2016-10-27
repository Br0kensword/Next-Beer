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
import static android.R.attr.visible;

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

        // Makes loader visible
        final TextView loader = (TextView) findViewById(R.id.sign_in_loader);
        loader.setVisibility(View.VISIBLE);

        // Getting user input for sign in (username, password)
        EditText username_input = (EditText)findViewById(R.id.username_input);
        String username = username_input.getText().toString();
        EditText password_input = (EditText)findViewById(R.id.password_input);
        String password = password_input.getText().toString();

        // Creating the url for signing in (username, password)
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
                        // If valid, then sign in and hide alert
                        TextView alert = (TextView) findViewById(R.id.alert_sign_in);
                        alert.setVisibility(View.GONE);

                        // Sets loader invisible
                        loader.setVisibility(View.GONE);

                        startActivity(intent);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // If invalid, then show alert
                TextView alert = (TextView) findViewById(R.id.alert_sign_in);
                alert.setVisibility(View.VISIBLE);
                
                // Sets loader invisible
                loader.setVisibility(View.GONE);
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
