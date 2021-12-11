package com.example.cst438_project03_group08;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
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

public class AllReservations extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private String user_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_reservations);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://cst438-project03-group08.herokuapp.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        List<Reservations> reservations;
        reservations = new ArrayList<>();
        mLayoutManager = new LinearLayoutManager(this);
        bookAPI bookService = retrofit.create(bookAPI.class);
        Call<List<Reservations>> allReservationCall = bookService.getAllReservation();
        user_id = getIntent().getStringExtra("UserId");

        allReservationCall.enqueue(new Callback<List<Reservations>>() {
            @Override
            public void onResponse(Call<List<Reservations>> call, Response<List<Reservations>> response) {
                if (response.isSuccessful()) {
                    for(Reservations reservation: response.body()){
                        // Log.d("RESERVATION", reservation.toString());
                        reservations.add(reservation);
                    }
                }

                // Adding the reservation to recycler view
                mRecyclerView = findViewById(R.id.rvAllReservations);
                mRecyclerView.setHasFixedSize(true);
                // Log.d("USERID", user_id);
                Integer u_id;
                u_id = Integer.valueOf(user_id);
                mAdapter = new AllReservationsAdapter(reservations, AllReservations.this, u_id);
                mRecyclerView.setLayoutManager(mLayoutManager);
                mRecyclerView.setAdapter(mAdapter);
            }

            @Override
            public void onFailure(Call<List<Reservations>> call, Throwable t) {
                Toast.makeText(AllReservations.this, "Fail to get the data..", Toast.LENGTH_SHORT).show();
            }
        });
    }
}