package com.herokuapp.nextbeerback.nextbeer;

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
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Iterator;

import static android.R.attr.key;
import static android.R.attr.name;
import static android.R.attr.value;

/**
 * Created by innouvation on 10/26/16.
 */

public class History extends Fragment {

    private RadarChart mChart;
    private final ArrayList<RadarEntry> yData = new ArrayList<>();
    private String[] mActivities = new String[]{};

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View v = inflater.inflate(R.layout.history_frag, container, false);

        mChart = (RadarChart) v.findViewById(R.id.graph);

        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(getActivity());

        String recommend_url = "https://nextbeerback.herokuapp.com/api/recommend?access_token=" + SignIn.ACCESS_TOKEN;

        // Request a string response from the provided URL.
        StringRequest accessRequest = new StringRequest(Request.Method.GET, recommend_url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject getResponse = new JSONObject(response);
                            //Toast.makeText(getActivity(), getResponse.toString(), Toast.LENGTH_LONG).show();
                            String beername = getResponse.get("name").toString();

                            try {
                                Object sweet = getResponse.get("sweet");
                                yData.add(new RadarEntry(Float.valueOf(String.valueOf(sweet))));
                                mActivities = push(mActivities, "sweet");

                                Object fruit = getResponse.get("fruit");
                                yData.add(new RadarEntry(Float.valueOf(String.valueOf(fruit))));
                                mActivities = push(mActivities, "fruit");

                                Object malty = getResponse.get("malty");
                                yData.add(new RadarEntry(Float.valueOf(String.valueOf(malty))));
                                mActivities = push(mActivities, "malty");

                                Object spice = getResponse.get("spice");
                                yData.add(new RadarEntry(Float.valueOf(String.valueOf(spice))));
                                mActivities = push(mActivities, "spice");

                                Object smoke = getResponse.get("smoke");
                                yData.add(new RadarEntry(Float.valueOf(String.valueOf(smoke))));
                                mActivities = push(mActivities, "smoke");

                                Object sour = getResponse.get("sour");
                                yData.add(new RadarEntry(Float.valueOf(String.valueOf(sour))));
                                mActivities = push(mActivities, "sour");

                                Object hoppy = getResponse.get("hoppy");
                                yData.add(new RadarEntry(Float.valueOf(String.valueOf(hoppy))));
                                mActivities = push(mActivities, "hoppy");

                                Object wood = getResponse.get("wood");
                                yData.add(new RadarEntry(Float.valueOf(String.valueOf(wood))));
                                mActivities = push(mActivities, "wood");

                                Object bitter = getResponse.get("bitter");
                                yData.add(new RadarEntry(Float.valueOf(String.valueOf(bitter))));
                                mActivities = push(mActivities, "bitter");

                                Object roasty = getResponse.get("roasty");
                                yData.add(new RadarEntry(Float.valueOf(String.valueOf(roasty))));
                                mActivities = push(mActivities, "roasty");

                                setDataInput(beername);
                            } catch (JSONException e) {
                                // Something went wrong!
                            }

                            for (RadarEntry key : yData){
                                Log.i("WOWOOWOWOWOWOOWSTRING: ", key.toString());
                            }
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

    public void setDataInput(String beername) {
        RadarDataSet dataset1 = new RadarDataSet(yData, "Recommended Beer: " + beername);
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
