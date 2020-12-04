package com.example.weatherforcast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

public class TestToolbar extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_toolbar);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Lab 8 toolbar");
        setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout)findViewById(R.id.drawerlay);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.open, R.string.close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView)findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

    }

    public boolean onCreateOptionsMenu (Menu menu) {
        MenuInflater menuinflater = getMenuInflater();
        menuinflater.inflate(R.menu.menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {

        switch(item.getItemId()) {
            case R.id.back:
                Toast.makeText(this, "You clicked on item 1", Toast.LENGTH_SHORT).show();
            break;

            case R.id.weather:
                Toast.makeText(this, "you clicked on item 2", Toast.LENGTH_SHORT).show();
            break;

            case R.id.chat:
                Toast.makeText(this, "you clicked on item 3", Toast.LENGTH_SHORT).show();
            break;

            case R.id.menuExtra:
                Toast.makeText(this, "You have clicked on the overflow menu item", Toast.LENGTH_SHORT).show();


    }

    return true;
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch(item.getItemId())
        {
            case R.id.chatwindow:

                Intent i = new Intent(TestToolbar.this, Page_chat.class);
                startActivity(i);


                break;
            case R.id.weatherwindow:


                Intent a = new Intent(TestToolbar.this,MainActivity.class);
                startActivity(a);

                break;
            case R.id.loginwindow:

                break;

        }
        return false;
    }
}