package com.herokuapp.nextbeerback.nextbeer;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import static com.herokuapp.nextbeerback.nextbeer.R.string.email;
import static com.herokuapp.nextbeerback.nextbeer.R.string.username;
import static com.herokuapp.nextbeerback.nextbeer.SignIn.ACCESS_TOKEN;
import static com.herokuapp.nextbeerback.nextbeer.SignIn.signin_url;
import static com.herokuapp.nextbeerback.nextbeer.SignIn.taste_url_no_access;

public class Questionnaire extends AppCompatActivity {

    SeekBar question1;
    TextView question1Val;

    SeekBar question2;
    TextView question2Val;

    SeekBar question3;
    TextView question3Val;

    SeekBar question4;
    TextView question4Val;

    SeekBar question5;
    TextView question5Val;

    SeekBar question6;
    TextView question6Val;

    SeekBar question7;
    TextView question7Val;

    SeekBar question8;
    TextView question8Val;

    SeekBar question9;
    TextView question9Val;

    SeekBar question10;
    TextView question10Val;

    SeekBar question11;
    TextView question11Val;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questionnaire);

        //Toast.makeText(getApplicationContext(), signin_url, Toast.LENGTH_LONG).show();

        question1 = (SeekBar) findViewById(R.id.question1);
        question1Val = (TextView) findViewById(R.id.question1Val);

        question1.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                question1Val.setText(String.valueOf(progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        question2 = (SeekBar) findViewById(R.id.question2);
        question2Val = (TextView) findViewById(R.id.question2Val);

        question2.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                question2Val.setText(String.valueOf(progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        question3 = (SeekBar) findViewById(R.id.question3);
        question3Val = (TextView) findViewById(R.id.question3Val);

        question3.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                question3Val.setText(String.valueOf(progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        question4 = (SeekBar) findViewById(R.id.question4);
        question4Val = (TextView) findViewById(R.id.question4Val);

        question4.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                question4Val.setText(String.valueOf(progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        question5 = (SeekBar) findViewById(R.id.question5);
        question5Val = (TextView) findViewById(R.id.question5Val);

        question5.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                question5Val.setText(String.valueOf(progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        question6 = (SeekBar) findViewById(R.id.question6);
        question6Val = (TextView) findViewById(R.id.question6Val);

        question6.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                question6Val.setText(String.valueOf(progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        question7 = (SeekBar) findViewById(R.id.question7);
        question7Val = (TextView) findViewById(R.id.question7Val);

        question7.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                question7Val.setText(String.valueOf(progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        question8 = (SeekBar) findViewById(R.id.question8);
        question8Val = (TextView) findViewById(R.id.question8Val);

        question8.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                question8Val.setText(String.valueOf(progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        question9 = (SeekBar) findViewById(R.id.question9);
        question9Val = (TextView) findViewById(R.id.question9Val);

        question9.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                question9Val.setText(String.valueOf(progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        question10 = (SeekBar) findViewById(R.id.question10);
        question10Val = (TextView) findViewById(R.id.question10Val);

        question10.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                question10Val.setText(String.valueOf(progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        question11 = (SeekBar) findViewById(R.id.question11);
        question11Val = (TextView) findViewById(R.id.question11Val);

        question11.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                question11Val.setText(String.valueOf(progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        signIn();
    }

    public void signIn() {
        // Instantiate the RequestQueue.
        final RequestQueue queue = Volley.newRequestQueue(this);

        // Request a string response from the provided URL.
        final StringRequest accessRequest = new StringRequest(Request.Method.GET, signin_url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //Toast.makeText(getApplicationContext(), response.toString(), Toast.LENGTH_LONG).show();
                        try {
                            JSONObject getResponse = new JSONObject(response);
                            ACCESS_TOKEN = getResponse.getString("access_token");
                            //Toast.makeText(getApplicationContext(), ACCESS_TOKEN, Toast.LENGTH_LONG).show();
                            addTaste();
                        } catch (JSONException e) {

                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });
        // Add the request to the RequestQueue.
        queue.add(accessRequest);
    }

    public void addTaste() {
        RequestQueue queue = Volley.newRequestQueue(Questionnaire.this);

        final JSONObject userJsonObj = new JSONObject();

        try {
            userJsonObj.put("access_token", SignIn.ACCESS_TOKEN);
            userJsonObj.put("malty", question1.getProgress());
            userJsonObj.put("bitter", question2.getProgress());
            userJsonObj.put("hoppy", question3.getProgress());
            userJsonObj.put("roasty", question4.getProgress());
            userJsonObj.put("smoke", question5.getProgress());
            userJsonObj.put("sour", question6.getProgress());
            userJsonObj.put("wood", question7.getProgress());
            userJsonObj.put("fruit", question8.getProgress());
            userJsonObj.put("spice", question9.getProgress());
            userJsonObj.put("sweet", question10.getProgress());

            //Toast.makeText(Questionnaire.this, userJsonObj.toString(), Toast.LENGTH_LONG).show();
            Log.v("JSONOBJECT", userJsonObj.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        // Creating the url for signing in (username, password)

        JsonObjectRequest createAccount = new JsonObjectRequest(Request.Method.POST, SignIn.taste_url_no_access, userJsonObj,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        //Toast.makeText(getApplicationContext(), ACCESS_TOKEN, Toast.LENGTH_LONG).show();
                        //Toast.makeText(getApplicationContext(), userJsonObj.toString(), Toast.LENGTH_LONG).show();
                        Log.v("SUCCESS", ACCESS_TOKEN);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //Toast.makeText(getApplicationContext(), ACCESS_TOKEN, Toast.LENGTH_LONG).show();
                Log.v("FAILED", ACCESS_TOKEN);

            }
        });

        // Add the request to the RequestQueue.
        queue.add(createAccount);
    }

    public void completeSignUp(View v) {
        signIn();
        Intent intent = new Intent(Questionnaire.this, MainContent.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        finish();
        startActivity(intent);
    }
}
