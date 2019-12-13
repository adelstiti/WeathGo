package com.example.yourweather;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.os.Bundle;
import android.os.StrictMode;
import android.widget.ImageView;
import android.widget.TextView;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;
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
import com.squareup.picasso.Picasso;

public class picWeather extends AppCompatActivity {

    ImageView image1;
    TextView day1 ;
    TextView temp1 ;
    ImageView image2;
    TextView day2 ;
    TextView temp2 ;
    ImageView image3;
    TextView day3 ;
    TextView temp3 ;
    ImageView image4;
    TextView day4 ;
    TextView temp4 ;

    TextView sunrise ;
    TextView sunset ;
    ImageView imagecity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pic_weather);

        day1 = findViewById(R.id.day1);
        day1.setText("");
        temp1 = findViewById(R.id.temp1);
        temp1.setText("");

        day2 = findViewById(R.id.day2);
        day2.setText("");
        temp2 = findViewById(R.id.temp2);
        temp2.setText("");

        day3 = findViewById(R.id.day3);
        day3.setText("");
        temp3 = findViewById(R.id.temp3);
        temp3.setText("");

        day4 = findViewById(R.id.day4);
        day4.setText("");
        temp4 = findViewById(R.id.temp4);
        temp4.setText("");

        image1=findViewById(R.id.image1);
        image2=findViewById(R.id.image2);
        image3=findViewById(R.id.image3);
        image4=findViewById(R.id.image4);


        sunset = findViewById(R.id.sunset);
        sunset.setText("");
        sunrise = findViewById(R.id.sunrise);
        sunrise.setText("");
        imagecity=findViewById(R.id.imagecity);



        Bundle b =  getIntent().getExtras();
        String cite = b.getString("place");
        String cityyy = b.getString("city");
        getDays(String.valueOf(cite));
        api_image(cityyy);

    }

    private void getDays(String cite) {

        OkHttpClient client = new OkHttpClient();
        Request request=new Request.Builder()
                .url("http://api.openweathermap.org/data/2.5/forecast?q="+cite+"&appid=a6f41d947e0542a26580bcd5c3fb90ef&units=metric&cnt=31")
                .get()
                .build();
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        try {
            Response response= client.newCall(request).execute();
            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(@NotNull Call call, @NotNull IOException e) {
                }

                @Override
                public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                    String responseData= response.body().string();
                    try {
                        JSONObject json=new JSONObject(responseData);
                        JSONObject sun= json.getJSONObject("city");
                        int sunris = sun.getInt("sunrise");
                        int sunse = sun.getInt("sunset");
                        int timezn = sun.getInt("timezone");
                        sunris = sunris + timezn ;
                        sunse = sunse + timezn ;
                        setText(sunrise,getDate(sunris));
                        setText(sunset,getDate(sunse));

                        JSONArray array=json.getJSONArray("list");

                        JSONObject nhar1=array.getJSONObject(5);
                        JSONObject tem1= nhar1.getJSONObject("main");
                        Double Temperature1=tem1.getDouble("temp");
                        String temps1=Math.round(Temperature1)+"°";
                        setText(temp1,temps1);

                        JSONArray tab1=nhar1.getJSONArray("weather");
                        JSONObject tab11=tab1.getJSONObject(0);
                        String icon1 = tab11.getString("icon");
                        setImage(image1,icon1);

                        String datee1 = nhar1.getString("dt_txt");
                        String date1 = datee1.substring(0,10);
                        setText(day1,date1);


                        JSONObject nhar2=array.getJSONObject(14);
                        JSONObject tem2= nhar2.getJSONObject("main");
                        Double Temperature2=tem2.getDouble("temp");
                        String temps2=Math.round(Temperature2)+"°";
                        setText(temp2,temps2);

                        JSONArray tab2=nhar2.getJSONArray("weather");
                        JSONObject tab22=tab2.getJSONObject(0);
                        String icon2 = tab22.getString("icon");
                        setImage(image2,icon2);

                        String datee2 = nhar2.getString("dt_txt");
                        String date2 = datee2.substring(0,10);
                        setText(day2,date2);



                        JSONObject nhar3=array.getJSONObject(23);
                        JSONObject tem3= nhar3.getJSONObject("main");
                        Double Temperature3=tem3.getDouble("temp");
                        String temps3=Math.round(Temperature3)+"°";
                        setText(temp3,temps3);

                        JSONArray tab3=nhar3.getJSONArray("weather");
                        JSONObject tab33=tab3.getJSONObject(0);
                        String icon3 = tab33.getString("icon");
                        setImage(image3,icon3);

                        String datee3 = nhar3.getString("dt_txt");
                        String date3 = datee3.substring(0,10);
                        setText(day3,date3);


                        JSONObject nhar4=array.getJSONObject(30);
                        JSONObject tem4= nhar4.getJSONObject("main");
                        Double Temperature4=tem4.getDouble("temp");
                        String temps4=Math.round(Temperature4)+"°";
                        setText(temp4,temps4);

                        JSONArray tab4=nhar4.getJSONArray("weather");
                        JSONObject tab44=tab4.getJSONObject(0);
                        String icon4 = tab44.getString("icon");
                        setImage(image4,icon4);

                        String datee4 = nhar4.getString("dt_txt");
                        String date4 = datee4.substring(0,10);
                        setText(day4,date4);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setText(final TextView text, final String value){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                text.setText(value);
            }
        });
    }


    private void setImage(final ImageView imageView, final String value){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                //paste switch
                switch (value){
                    case "01d": imageView.setImageDrawable(getResources().getDrawable(R.drawable.d01d));
                        break;
                    case "01n": imageView.setImageDrawable(getResources().getDrawable(R.drawable.d01d));
                        break;
                    case "02d": imageView.setImageDrawable(getResources().getDrawable(R.drawable.d02d));
                        break;
                    case "02n": imageView.setImageDrawable(getResources().getDrawable(R.drawable.d02d));
                        break;
                    case "03d": imageView.setImageDrawable(getResources().getDrawable(R.drawable.d03d));
                        break;
                    case "03n": imageView.setImageDrawable(getResources().getDrawable(R.drawable.d03d));
                        break;
                    case "04d": imageView.setImageDrawable(getResources().getDrawable(R.drawable.d04d));
                        break;
                    case "04n": imageView.setImageDrawable(getResources().getDrawable(R.drawable.d04d));
                        break;
                    case "09d": imageView.setImageDrawable(getResources().getDrawable(R.drawable.d09d));
                        break;
                    case "09n": imageView.setImageDrawable(getResources().getDrawable(R.drawable.d09d));
                        break;
                    case "10d": imageView.setImageDrawable(getResources().getDrawable(R.drawable.d10d));
                        break;
                    case "10n": imageView.setImageDrawable(getResources().getDrawable(R.drawable.d10d));
                        break;
                    case "11d": imageView.setImageDrawable(getResources().getDrawable(R.drawable.d11d));
                        break;
                    case "11n": imageView.setImageDrawable(getResources().getDrawable(R.drawable.d11n));
                        break;
                    case "13d": imageView.setImageDrawable(getResources().getDrawable(R.drawable.d13d));
                        break;
                    case "13n": imageView.setImageDrawable(getResources().getDrawable(R.drawable.d13n));
                        break;
                    case "50d": imageView.setImageDrawable(getResources().getDrawable(R.drawable.d50d));
                        break;
                    case "50n": imageView.setImageDrawable(getResources().getDrawable(R.drawable.d50n));
                        break;
                    default:
                        imageView.setImageDrawable(getResources().getDrawable(R.drawable.transp));
                }
            }
        });
    }

    private String getDate(int time) {
        Date date = new Date(time*1000L);
        SimpleDateFormat sdf = new SimpleDateFormat("kk:mm");
        sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
        return sdf.format(date);
    }

    public void finAct(View view){
        finish();
    }


    private void api_image(String place) {

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://pixabay.com/api/?key=13505607-5594b8502d53f3c87ed57cc99&q="+place)
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
                        JSONArray array = json.getJSONArray("hits");
                        JSONObject object = array.getJSONObject(0);

                        String urll = object.getString("largeImageURL");
                        setBackImg(imagecity,urll);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setBackImg(final ImageView imagecity, final String value) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Picasso.get().load(value).into(imagecity);
            }
        });
    }

}
