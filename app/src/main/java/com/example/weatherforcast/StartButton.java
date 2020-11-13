package com.example.weatherforcast;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class StartButton extends AppCompatActivity {


    private static String startscreen;
    private static final String ACTIVITY_NAME = startscreen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.button_start);

        Button buttonWeather = (Button) findViewById(R.id.button);
        buttonWeather.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(ACTIVITY_NAME, "User clicked Check Weather");
                Intent intent = new Intent(StartButton.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

}