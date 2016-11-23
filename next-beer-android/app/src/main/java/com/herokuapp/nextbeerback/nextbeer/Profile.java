package com.herokuapp.nextbeerback.nextbeer;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.github.mikephil.charting.charts.RadarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.RadarData;
import com.github.mikephil.charting.data.RadarDataSet;
import com.github.mikephil.charting.data.RadarEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IRadarDataSet;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by innouvation on 10/26/16.
 */

public class Profile extends Fragment {

    private RadarChart mChart;
    private final ArrayList<RadarEntry> yData = new ArrayList<>();
    private String[] mActivities = new String[]{};

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.profile_frag, container, false);

        TextView username_display = (TextView) v.findViewById(R.id.username_display);
        username_display.setText(SignIn.username);

        mChart = (RadarChart) v.findViewById(R.id.graph);

        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(getActivity());

        String access_taste_url = SignIn.taste_url_no_access + "?access_token=" + SignIn.ACCESS_TOKEN;

        //Toast.makeText(getActivity(), access_taste_url, Toast.LENGTH_LONG).show();

        // Request a string response from the provided URL.
        StringRequest accessRequest = new StringRequest(Request.Method.GET, access_taste_url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject getResponse = new JSONObject(response);
                            //Toast.makeText(getActivity(), getResponse.toString(), Toast.LENGTH_LONG).show();

                            Iterator<String> iter = getResponse.keys();
                            while (iter.hasNext()) {
                                String key = iter.next();
                                try {
                                    Object value = getResponse.get(key);
                                    yData.add(new RadarEntry(Float.valueOf(String.valueOf(value))));
                                    mActivities = push(mActivities, key.toString());
                                    setDataInput();
                                } catch (JSONException e) {
                                    // Something went wrong!
                                }
                            }

                            for (RadarEntry key : yData){
                                Log.i("WOWOOWOWOWOWOOWSTRING: ", key.toString());
                            }
                        } catch (JSONException e) {
                            Toast.makeText(getActivity(), "FAILED", Toast.LENGTH_LONG).show();
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

    public void setDataInput() {
        RadarDataSet dataset1 = new RadarDataSet(yData, "User Preference");
        dataset1.setColor(Color.rgb(103, 110, 129));
        dataset1.setFillColor(Color.rgb(103, 110, 129));
        dataset1.setDrawFilled(true);
        dataset1.setFillAlpha(180);
        dataset1.setLineWidth(2f);
        dataset1.setDrawHighlightCircleEnabled(true);
        dataset1.setDrawHighlightIndicators(false);

        ArrayList<IRadarDataSet> sets = new ArrayList<>();
        sets.add(dataset1);

        RadarData data = new RadarData(sets);
        data.setValueTextSize(8f);
        data.setDrawValues(false);
        data.setValueTextColor(Color.WHITE);

        mChart.setData(data);
        mChart.invalidate();

        mChart.getDescription().setEnabled(false);

        mChart.setWebLineWidth(0.5f);
        mChart.animate();

        XAxis xAxis = mChart.getXAxis();
        xAxis.setTextSize(9f);
        xAxis.setYOffset(0f);
        xAxis.setXOffset(0f);

        YAxis yAxis = mChart.getYAxis();
        yAxis.setLabelCount(10, false);
        yAxis.setTextSize(9f);
        yAxis.setAxisMinimum(0f);
        yAxis.setAxisMaximum(10f);
        yAxis.setDrawLabels(false);

        xAxis.setValueFormatter(new IAxisValueFormatter() {

            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return mActivities[(int) value % mActivities.length];
            }

            /** this is only needed if numbers are returned, else return 0 */
            @Override
            public int getDecimalDigits() { return 1; }
        });
        xAxis.setTextColor(Color.WHITE);

        Legend l = mChart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        l.setDrawInside(false);
        l.setXEntrySpace(7f);
        l.setYEntrySpace(5f);
        l.setTextColor(Color.WHITE);
    }

    private static String[] push(String[] array, String push) {
        String[] longer = new String[array.length + 1];
        System.arraycopy(array, 0, longer, 0, array.length);
        longer[array.length] = push;
        return longer;
    }
}
