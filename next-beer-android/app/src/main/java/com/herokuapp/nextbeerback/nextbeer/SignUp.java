package com.herokuapp.nextbeerback.nextbeer;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import static com.herokuapp.nextbeerback.nextbeer.R.id.email_input;
import static com.herokuapp.nextbeerback.nextbeer.R.string.sign_up;

public class SignUp extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
    }

    public void questionnaire(View v) {
        final TextView username = (TextView) findViewById(R.id.username_input);
        final TextView password = (TextView) findViewById(R.id.password_input);
        TextView password_confirm = (TextView) findViewById(R.id.password_input_confirm);
        TextView email = (TextView) findViewById(email_input);

        RequestQueue queue = Volley.newRequestQueue(SignUp.this);

        JSONObject userJsonObj = new JSONObject();

        try {
            userJsonObj.put("username", username.getText().toString());
            userJsonObj.put("password", password.getText().toString());
            userJsonObj.put("password_confirm", password_confirm.getText().toString());
            userJsonObj.put("email", email.getText().toString());

            Toast.makeText(SignUp.this, userJsonObj.toString(), Toast.LENGTH_LONG).show();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        // Creating the url for signing in (username, password)
        String signup_url = "https://nextbeerback.herokuapp.com/api/user";

        SignIn.username = username.getText().toString();
        SignIn.password = password.getText().toString();
        SignIn.signin_url = "https://nextbeerback.herokuapp.com/api/auth?username=" +
                SignIn.username +
                "&password=" +
                SignIn.password;

        JsonObjectRequest createAccount = new JsonObjectRequest(Request.Method.POST, signup_url, userJsonObj,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        // Add the request to the RequestQueue.
        queue.add(createAccount);

        Intent intent = new Intent(this, Questionnaire.class);
        startActivity(intent);
    }
}
