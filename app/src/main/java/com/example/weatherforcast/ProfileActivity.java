package com.example.weatherforcast;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class ProfileActivity extends AppCompatActivity {
    protected static final String ACTIVITY_NAME = "ProfileActivity";
    private Button toolbarbutton;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        toolbarbutton = (Button) findViewById(R.id.button);
        toolbarbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent goToToolBar = new Intent(ProfileActivity.this, TestToolbar.class);

                startActivityForResult(goToToolBar, 500);}});}}





















