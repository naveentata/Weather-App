package com.example.weatherapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class Main2Activity extends AppCompatActivity {

    RecyclerView rv;
    List<forecast_item> items;
    itemAdapter adapter;
    ProgressBar pg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        Intent intent = getIntent();
        String name = intent.getStringExtra("name");
        pg = findViewById(R.id.progressBar);


        Toast.makeText(getApplicationContext(), "Forecasting for " + name, Toast.LENGTH_SHORT).show();

        rv = findViewById(R.id.recyclerView);
        rv.setHasFixedSize(true);
        rv.setLayoutManager(new LinearLayoutManager(this));
        items = new ArrayList<>();

        pg.setVisibility(View.VISIBLE);
        if (find_weather(name)) {
            pg.setVisibility(View.INVISIBLE);
        }


    }


    private boolean find_weather(String city_name) {
        String url = "https://api.openweathermap.org/data/2.5/forecast?q=" + city_name + "&appid=b49e099fd548cd6847ded5c9fb8caccf&units=metric";

        JsonObjectRequest jor = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {

                    JSONArray mainarr = response.getJSONArray("list");

                    for (int i = 0; i < mainarr.length(); i++) {
                        JSONObject object = mainarr.getJSONObject(i);
                        JSONObject main = object.getJSONObject("main");
                        JSONArray weather1 = object.getJSONArray("weather");
                        JSONObject wind = object.getJSONObject("wind");
                        JSONObject weather = weather1.getJSONObject(0);
                        String windi = String.valueOf(Math.round(wind.getDouble("speed")));
                        String temp = String.valueOf(Math.round(main.getDouble("temp")));
                        String press = String.valueOf(Math.round(main.getDouble("pressure")));
                        String humidity = String.valueOf(Math.round(main.getDouble("humidity")));
                        String description = (weather.getString("description"));
                        String img = (weather.getString("icon"));
                        String datetime = object.getString("dt_txt");
                        forecast_item item = new forecast_item(datetime, windi, press, humidity, temp, img, description);
                        items.add(item);
                        adapter = new itemAdapter(Main2Activity.this, items);
                        rv.setAdapter(adapter);


                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }
        );

        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(jor);
        return true;
    }
}
