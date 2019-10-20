package com.example.weatherapp;

import android.annotation.SuppressLint;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.squareup.picasso.Picasso;
//import com.tapadoo.alerter.Alerter;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.app.ShareCompat;

import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    private static final String CHANNEL_ID = "personal_notification";
    private static final int NOTIFICATION_ID = 001;


    TextView temp, city, des, date, pres, hum, windi;
    String city_name = "Vijayawada";
    ImageView icon_img;
    String desu;
    String humidity;
    String pressure;
    String w;
    String tempu;
    String delegate = "aaa";
    String dele = "hh";
    String am = (String) DateFormat.format(delegate, Calendar.getInstance().getTime());
    String hour = (String) DateFormat.format(dele, Calendar.getInstance().getTime());


    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (am.equals("PM") && Integer.parseInt(hour) > 6) {
            ConstraintLayout constraintLayout = (ConstraintLayout) findViewById(R.id.conslayout);
            constraintLayout.setBackgroundResource(R.drawable.pm);


        }
        temp = findViewById(R.id.temp);
        city = findViewById(R.id.city);
        des = findViewById(R.id.description);
        date = findViewById(R.id.date);
        icon_img = findViewById(R.id.img);
        windi = findViewById(R.id.wini);
        pres = findViewById(R.id.press2);
        hum = findViewById(R.id.hum);


        find_weather(city_name);


        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Updating Your City Weather!!", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                find_weather(city_name);
            }
        });
    }

    private void find_weather(String city_name) {
        String url = "https://api.openweathermap.org/data/2.5/weather?q=" + city_name + "&appid=b49e099fd548cd6847ded5c9fb8caccf&units=metric";

        JsonObjectRequest jor = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONObject mainob = response.getJSONObject("main");
                    JSONArray array = response.getJSONArray("weather");
                    JSONObject object = array.getJSONObject(0);
                    tempu = String.valueOf(Math.round(mainob.getDouble("temp")));
                    desu = object.getString("description");
                    String nameu = response.getString("name");
                    String icon = object.getString("icon");
                    JSONObject windob = response.getJSONObject("wind");
                    w = String.valueOf(Math.round(windob.getDouble("speed")));
                    ;
                    pressure = String.valueOf(Math.round(mainob.getDouble("pressure")));
                    humidity = String.valueOf(Math.round(mainob.getDouble("humidity")));

                    windi.setText(w);
                    pres.setText(pressure);
                    hum.setText(humidity);
                    temp.setText(tempu);
                    des.setText(desu);
                    city.setText(nameu);


                    Calendar calendar = Calendar.getInstance();
                    SimpleDateFormat sdf = new SimpleDateFormat("dd MMMM");
                    String formated_date = sdf.format(calendar.getTime());

                    date.setText(formated_date);
                    String imglink = "http://openweathermap.org/img/w/" + icon + ".png";
                    Picasso.get().load(imglink).into(icon_img);


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
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.edit_city) {


            AlertDialog.Builder myAlertBuilder = new
                    AlertDialog.Builder(MainActivity.this);
            myAlertBuilder.setTitle("Edit City");
            myAlertBuilder.setMessage("Enter your desired city name:");
            LayoutInflater inflater = (LayoutInflater) LayoutInflater.from(getApplicationContext());
            final View dview = (View) inflater.inflate(R.layout.edit_city_layout, null);
            myAlertBuilder.setView(dview);


            final EditText edit_city;
            edit_city = (EditText) dview.findViewById(R.id.editText_city);

            myAlertBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {


                    city_name = edit_city.getText().toString();
                    find_weather(city_name);


                }
            });

            myAlertBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    Toast.makeText(getApplicationContext(), "No Problem!", Toast.LENGTH_SHORT).show();
                }
            });
            myAlertBuilder.show();


        }


        if (id == R.id.forcaste) {
            Intent intent = new Intent(this, Main2Activity.class);
            intent.putExtra("name", city_name);

            Intent intent1 = new Intent(this, MainActivity.class);
            intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent1, PendingIntent.FLAG_ONE_SHOT);
//            intent.putExtra("name",city_name);
            NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                    .setSmallIcon(R.drawable.ic_terrain_black_24dp)
                    .setContentTitle(city_name)
                    .setContentText(tempu + " degrees C | " + desu + " | See Forecast")
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                    .setContentIntent(pendingIntent)
                    .setAutoCancel(true);
            NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(this);
            notificationManagerCompat.notify(NOTIFICATION_ID, builder.build());

            startActivity(intent);
        }

        if (id == R.id.aboutme) {
            Intent intent = new Intent(this, Resume.class);
            startActivity(intent);

        }

        if (id == R.id.locate) {
            Uri webpage = Uri.parse("geo:0,0?q=" + city_name);
            Intent intent = new Intent(Intent.ACTION_VIEW, webpage);

            if (intent.resolveActivity(getPackageManager()) != null) {
                startActivity(intent);
            } else {
                Log.d("ImplicitIntents", "Can't handle this intent!");
            }
        }

        if (id == R.id.Share) {

            String sharetxt = city_name + "\n" + desu + "\n" + "Temperature: " + tempu + " C\n";
            Intent shareIntent = ShareCompat.IntentBuilder.from(this)
                    .setType("text/plain")
                    .setText(sharetxt)
                    .getIntent();
            if (shareIntent.resolveActivity(getPackageManager()) != null) {
                startActivity(shareIntent);
            }
        }


        return super.onOptionsItemSelected(item);
    }
}
