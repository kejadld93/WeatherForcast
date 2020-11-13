package com.example.weatherforcast;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {
    //1. create variable textviews
        private TextView currentTv;
        private TextView minTv;
        private TextView maxTv;
        private TextView uv;
        private ImageView weatherIm;
        protected static final String ACTIVITY_NAME = "WeatherForecast";


    ProgressBar visibleProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //find the textview
        currentTv = (TextView)findViewById(R.id.current);
        minTv = (TextView)findViewById(R.id.min);
        maxTv = (TextView)findViewById(R.id.max);
        uv = (TextView)findViewById(R.id.uv);
        weatherIm = (ImageView) findViewById(R.id.weatherIm);
        visibleProgressBar = (ProgressBar)findViewById(R.id.visibleProgressBar);
        ForcastQuery FQ = new ForcastQuery();
        FQ.execute("http://api.openweathermap.org/data/2.5/weather?q=ottawa,ca&APPID=7e943c97096a9784391a981c4d878b22&mode=xml&units=metric");

    }

    protected static Bitmap getImage(URL url) {

        HttpURLConnection Wethicon = null;
        Bitmap image = null;
        try {
            Wethicon = (HttpURLConnection) url.openConnection();
            Wethicon.connect();
            int wethiconResponseCode = Wethicon.getResponseCode();
            if (wethiconResponseCode == 200) {
                return BitmapFactory.decodeStream(Wethicon.getInputStream());
            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if (Wethicon != null) {
                Wethicon.disconnect();
            }
        }
    }

    public boolean fileExistance(String fileName) {
        Log.i(null, "In fileExistance");
        Log.i(null, getBaseContext().getFileStreamPath(fileName).toString());
        File file = getBaseContext().getFileStreamPath(fileName);
        return file.exists();
    }

    private class ForcastQuery extends AsyncTask< String, Integer, String> {

        String UV;
        String min;
        String max;
        String current;
        String uvRating;
        String iconName;
        Bitmap icon;

        @Override
        protected String doInBackground(String... strings) {

            try {

                //create a URL object of what server to contact:
                URL url = new URL("http://api.openweathermap.org/data/2.5/weather?q=ottawa,ca&APPID=7e943c97096a9784391a981c4d878b22&mode=xml&units=metric");
                //open the connection
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                //wait for data:
                InputStream response = urlConnection.getInputStream();

                //From part 3: slide 19
                XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
                factory.setNamespaceAware(false);
                XmlPullParser xpp = factory.newPullParser();
                xpp.setInput( response  , "UTF-8");

                //From part 3, slide 20
                String parameter = null;
                int eventType = xpp.getEventType(); //The parser is currently at START_DOCUMENT

                while(eventType != XmlPullParser.END_DOCUMENT)
                {
                    if(eventType == XmlPullParser.START_TAG)
                    {
                        //If you get here, then you are pointing at a start tag
                        if(xpp.getName().equals("temperature"))
                        {
                            //If you get here, then you are pointing to a <Weather> start tag
                            min = xpp.getAttributeValue(null,  "min");
                            publishProgress(25);
                            max = xpp.getAttributeValue(null, "max");
                            publishProgress(50);
                            current = xpp.getAttributeValue(null, "value");
                            publishProgress(75);
                        }


                            if (xpp.getName().equals("weather")) {
                                iconName = xpp.getAttributeValue(null, "icon");
                                String iconFile = iconName+".png";
                                if (fileExistance(iconFile)) {
                                    FileInputStream inputStream = null;
                                    try {
                                        inputStream = new FileInputStream(getBaseContext().getFileStreamPath(iconFile));
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                   icon = BitmapFactory.decodeStream(inputStream);
                                    Log.i(null, "Found image");
                                } else {
                                    URL iconUrl = new URL("http://openweathermap.org/img/w/" + iconName + ".png");
                                    icon = getImage(iconUrl);
                                    FileOutputStream outputStream = openFileOutput(iconName + ".png", Context.MODE_PRIVATE);
                                    icon.compress(Bitmap.CompressFormat.PNG, 80, outputStream);
                                    outputStream.flush();
                                    outputStream.close();
                                    Log.i(null, "Not found, downloading.. ");

                                    URL uvUrl = new URL("http://api.openweathermap.org/data/2.5/uvi?appid=7e943c97096a9784391a981c4d878b22&lat=45.348945&lon=-75.759389");

                                    //open the connection
                                    HttpURLConnection uvConnection = (HttpURLConnection) url.openConnection();

                                    //wait for data:
                                    InputStream uvResponse = urlConnection.getInputStream();

                                    //JSON reading:   Look at slide 26
                                    //Build the entire string response:
                                    BufferedReader reader = new BufferedReader(new InputStreamReader(uvResponse, "UTF-8"), 8);
                                    StringBuilder sb = new StringBuilder();

                                    String line = null;
                                    while ((line = reader.readLine()) != null)
                                    {
                                        sb.append(line + "\n");
                                    }
                                    String result = sb.toString(); //result is the whole string

                                    JSONObject uvReport = new JSONObject(result);

                                    //get the double associated with "value"
                                    double uvRating = uvReport.getDouble("value");

                                }
                                Log.i(null, "file name is "+iconFile);
                                publishProgress(100);
                            }




                    }
                    eventType = xpp.next(); //move to the next xml event and store it in a variable


                }




            }
            catch (Exception e)
            {

            }

            return "Done";

        }

        public void onProgressUpdate(Integer ... values)
        {
            Log.i(ACTIVITY_NAME, "onOrogressUpdate");
            visibleProgressBar.setVisibility(View.VISIBLE);
            visibleProgressBar.setProgress(values[0]);

        }

        public void onPostExecute(String fromDoInBackground)
        {
            //use the textview varaibles to setTet()
            currentTv.setText("Current temp is: " + current + " Celcius");
            minTv.setText("minimum temp is: " + min + " Celcius");
            maxTv.setText("maximum temp is: " + max + " Celcius");
            weatherIm.setImageBitmap(icon);
            visibleProgressBar.setVisibility(View.INVISIBLE);
            uv.setText("UV rating is: " + uvRating);



            Log.i("HTTP", fromDoInBackground);
        }
         }
    }
