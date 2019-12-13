package com.example.yourweather;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.content.Intent;
import android.widget.Toast;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    TextView celsius;
    EditText inPlace;
    ImageView view_weather;
    TextView view_temp;
    TextView view_humidity;
    TextView view_wind;
    TextView view_place;
    TextView view_error;
    View v_bg;
    ImageView image_humidity;
    ImageView image_wind;
    Button info;
    String placeee;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button show = (Button) (findViewById(R.id.show));

        celsius = findViewById(R.id.celsius);
        celsius.setText("");
        inPlace = findViewById(R.id.inPlace);
        inPlace.setText("");
        view_temp = findViewById(R.id.temp);
        view_temp.setText("");
        view_place = findViewById(R.id.city);
        view_place.setText("");
        view_humidity = findViewById(R.id.humidity);
        view_humidity.setText("");
        view_wind = findViewById(R.id.wind);
        view_wind.setText("");
        view_weather = findViewById(R.id.weather);
        v_bg = findViewById(R.id.bg);
        view_error = findViewById(R.id.error);
        view_error.setText("");

        image_humidity = findViewById(R.id.v_humidity);
        image_humidity.setImageDrawable(null);
        image_wind = findViewById(R.id.v_wind);
        image_wind.setImageDrawable(null);
        view_weather = findViewById(R.id.weather);
        view_weather.setImageDrawable(null);
        info = findViewById(R.id.info);
        info.setVisibility(View.GONE);

        show.setOnClickListener(
                new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {

                        // Hide KeyBoard
                        InputMethodManager im = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
                        im.hideSoftInputFromWindow(getCurrentFocus().getRootView().getWindowToken(), 0);
                        api_key(String.valueOf(inPlace.getText()));
                    }
                }
        );

    }

    private void api_key(String place) {

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://api.openweathermap.org/data/2.5/weather?q=" + place + "&appid=a6f41d947e0542a26580bcd5c3fb90ef&units=metric")
                .get()
                .build();
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        try {
            Response response = client.newCall(request).execute();
            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(@NotNull Call call, @NotNull IOException e) {
                }

                @Override
                public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                    String responseData = response.body().string();
                    try {
                        JSONObject json = new JSONObject(responseData);
                        JSONArray array = json.getJSONArray("weather");
                        JSONObject object = array.getJSONObject(0);

                        String icons = object.getString("icon");
                        setImage(view_weather, icons);

                        JSONObject count = json.getJSONObject("sys");
                        String country = count.getString("country");
                        String place = json.getString("name");
                        String yourPlace = place + ", " + country;
                        placeee = place;
                        setText(view_place, yourPlace);

                        JSONObject temp1 = json.getJSONObject("main");
                        Double Temperature = temp1.getDouble("temp");
                        String temps = Math.round(Temperature) + "";
                        setText(view_temp, temps);
                        setText(celsius, "°C");

                        int humidi = temp1.getInt("humidity");
                        String humidity = humidi + "%";
                        setText(view_humidity, humidity);

                        JSONObject winds = json.getJSONObject("wind");
                        Double windd = winds.getDouble("speed");
                        String wind = String.format("%.1f", windd) + "m/s";
                        setText(view_wind, wind);

                        setImage(image_wind, "wind");
                        setImage(image_humidity, "humidity");

                        setBackgroundResource(v_bg, icons);

                        setText(view_error, "");
                        setBackgroundB(info, "info");

                    } catch (JSONException e) {
                        e.printStackTrace();
                        setText(view_error, "Please Enter a Valid City");
                        setImage(view_weather, "");
                        setText(view_place, "");
                        setText(view_temp, "");
                        setText(celsius, "");
                        setText(view_humidity, "");
                        setText(view_wind, "");
                        setImage(image_wind, "");
                        setImage(image_humidity, "");
                        setBackgroundB(info, "noinfo");
                    }
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setText(final TextView text, final String value) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                text.setText(value);
            }
        });
    }

    private void setImage(final ImageView imageView, final String value) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                //paste switch
                switch (value) {
                    case "01d":
                        imageView.setImageDrawable(getResources().getDrawable(R.drawable.d01d));
                        break;
                    case "01n":
                        imageView.setImageDrawable(getResources().getDrawable(R.drawable.d01n));
                        break;
                    case "02d":
                        imageView.setImageDrawable(getResources().getDrawable(R.drawable.d02d));
                        break;
                    case "02n":
                        imageView.setImageDrawable(getResources().getDrawable(R.drawable.d02n));
                        break;
                    case "03d":
                        imageView.setImageDrawable(getResources().getDrawable(R.drawable.d03d));
                        break;
                    case "03n":
                        imageView.setImageDrawable(getResources().getDrawable(R.drawable.d03n));
                        break;
                    case "04d":
                        imageView.setImageDrawable(getResources().getDrawable(R.drawable.d04d));
                        break;
                    case "04n":
                        imageView.setImageDrawable(getResources().getDrawable(R.drawable.d04n));
                        break;
                    case "09d":
                        imageView.setImageDrawable(getResources().getDrawable(R.drawable.d09d));
                        break;
                    case "09n":
                        imageView.setImageDrawable(getResources().getDrawable(R.drawable.d09n));
                        break;
                    case "10d":
                        imageView.setImageDrawable(getResources().getDrawable(R.drawable.d10d));
                        break;
                    case "10n":
                        imageView.setImageDrawable(getResources().getDrawable(R.drawable.d10n));
                        break;
                    case "11d":
                        imageView.setImageDrawable(getResources().getDrawable(R.drawable.d11d));
                        break;
                    case "11n":
                        imageView.setImageDrawable(getResources().getDrawable(R.drawable.d11n));
                        break;
                    case "13d":
                        imageView.setImageDrawable(getResources().getDrawable(R.drawable.d13d));
                        break;
                    case "13n":
                        imageView.setImageDrawable(getResources().getDrawable(R.drawable.d13n));
                        break;
                    case "50d":
                        imageView.setImageDrawable(getResources().getDrawable(R.drawable.d50d));
                        break;
                    case "50n":
                        imageView.setImageDrawable(getResources().getDrawable(R.drawable.d50n));
                        break;
                    case "humidity":
                        image_humidity.setImageDrawable(getResources().getDrawable(R.drawable.humidity));
                        break;
                    case "wind":
                        image_wind.setImageDrawable(getResources().getDrawable(R.drawable.wind));
                        break;
                    default:
                        imageView.setImageDrawable(getResources().getDrawable(R.drawable.transp));
                }
            }
        });
    }

    private void setBackgroundResource(final View v, final String value) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                //paste switch
                char chr = value.charAt(2);
                if (chr == 'd') {
                    v.setBackgroundResource(R.drawable.bg);
                } else {
                    v.setBackgroundResource(R.drawable.bgn);
                }

            }
        });
    }

    private void setBackgroundB(final Button b, final String value) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                //paste switch
                if (value == "info") {
                    b.setVisibility(View.VISIBLE);
                } else {
                    b.setVisibility(View.GONE);
                }

            }
        });
    }

    public void picShow(View view) {
        Intent myIntent = new Intent(this, picWeather.class);
        myIntent.putExtra("place", view_place.getText());
        myIntent.putExtra("city", placeee);
        startActivity(myIntent);
    }


}
