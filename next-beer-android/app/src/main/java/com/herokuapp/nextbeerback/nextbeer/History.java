package com.herokuapp.nextbeerback.nextbeer;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.github.mikephil.charting.charts.RadarChart;
import com.github.mikephil.charting.data.RadarEntry;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;

/**
 * Created by innouvation on 10/26/16.
 */

public class History extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.history_frag, container, false);

        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(getActivity());

        String access_beers_url = SignIn.beers_url_no_access + "?access_token=" + SignIn.ACCESS_TOKEN;

        // Request a string response from the provided URL.
        StringRequest accessRequest = new StringRequest(Request.Method.GET, access_beers_url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject getResponse = new JSONObject(response);
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

        return v;
    }
}
