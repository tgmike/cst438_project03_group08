package com.example.cst438_project03_group08;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class HomePage extends AppCompatActivity {

    Button btnAvailable;
    Button btnMyReservations;
    Button btnAllReservations;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        btnAvailable = findViewById(R.id.btnAvailable);
        btnMyReservations = findViewById(R.id.btnReservations);
        btnAllReservations = findViewById(R.id.btnAllReservation);

        btnAvailable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(HomePage.this, BookAvailabilityPage.class);
                String user_id;
                user_id = getIntent().getStringExtra("UserId");
                i.putExtra("UserId", user_id);
                startActivity(i);
            }
        });

        btnMyReservations.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(HomePage.this, MyReservations.class);
                String user_id;
                user_id = getIntent().getStringExtra("UserId");
                i.putExtra("UserId", user_id);
                startActivity(i);
            }
        });

        btnAllReservations.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(HomePage.this, AllReservations.class);
                String user_id;
                user_id = getIntent().getStringExtra("UserId");
                i.putExtra("UserId", user_id);
                startActivity(i);
            }
        });

    }
}