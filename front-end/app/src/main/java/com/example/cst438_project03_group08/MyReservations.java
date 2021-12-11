package com.example.cst438_project03_group08;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MyReservations extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private String book_id, user_id, reservation_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_reservations);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://cst438-project03-group08.herokuapp.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        List<Reservations> reservations;
        reservations = new ArrayList<>();
        mLayoutManager = new LinearLayoutManager(this);
        bookAPI bookService = retrofit.create(bookAPI.class);
        user_id = getIntent().getStringExtra("UserId");
        reservation_id = getIntent().getStringExtra("ReservationId");


        // found a reservation to delete
        if(reservation_id != null){
            Call<Void> deleteCall = bookService.deleteMyReservation(Integer.valueOf(reservation_id));
            deleteCall.enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    Toast.makeText(MyReservations.this, "DELETED", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(MyReservations.this, HomePage.class);
                    i.putExtra("UserId", user_id);
                    startActivity(i);
                }

                @Override
                public void onFailure(Call<Void> call, Throwable t) {
                    Log.d("FAILURE", call.toString());
                    Toast.makeText(MyReservations.this, "FAILED TO DELETE", Toast.LENGTH_SHORT).show();
                }
            });

        }


        Call<List<Reservations>> call = bookService.getMyReservations(Integer.valueOf(user_id));
        call.enqueue(new Callback<List<Reservations>>() {
            @Override
            public void onResponse(Call<List<Reservations>> call, Response<List<Reservations>> response) {
                if (response.isSuccessful()) {
                    for(Reservations reservation: response.body()){
                        Log.d("RESERVATION", reservation.toString());
                        reservations.add(reservation);
                    }
                }

                // Adding the books to recycler view
                mRecyclerView = findViewById(R.id.rvBooks);
                mRecyclerView.setHasFixedSize(true);
                Log.d("USERID", user_id);
                Integer u_id;
                u_id = Integer.valueOf(user_id);
                mAdapter = new ReservationsAdapter(reservations, MyReservations.this, u_id);
                mRecyclerView.setLayoutManager(mLayoutManager);
                mRecyclerView.setAdapter(mAdapter);
            }

            @Override
            public void onFailure(Call<List<Reservations>> call, Throwable t) {
                Log.d("BOOK", "Running error");
                Toast.makeText(MyReservations.this, "Fail to get the data..", Toast.LENGTH_SHORT).show();
            }
        });


    }
}