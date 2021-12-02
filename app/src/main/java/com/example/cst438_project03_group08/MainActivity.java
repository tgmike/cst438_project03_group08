package com.example.cst438_project03_group08;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // To test different activities I have the lines below.
        // Commenting out for now so that, it doesn't default to this activity
        // when someone runs the application
//        Intent i = new Intent(MainActivity.this, EditAccountInfoPage.class);
//        startActivity(i);
    }
}