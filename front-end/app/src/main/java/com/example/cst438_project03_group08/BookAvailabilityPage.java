package com.example.cst438_project03_group08;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class BookAvailabilityPage extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private String book_id, user_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_availability_page);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://cst438-project03-group08.herokuapp.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        List<Book> books;
        books = new ArrayList<>();
        mLayoutManager = new LinearLayoutManager(this);
        bookAPI bookService = retrofit.create(bookAPI.class);
        Call<List<Book>> call = bookService.getBooks();
        user_id = getIntent().getStringExtra("UserId");
        book_id = getIntent().getStringExtra("BookId");

        // if bookID not empty, then (reserve the book using route)
        if(book_id != null){
            Call<Void> reserveCall = bookService.reserveBook(Integer.valueOf(user_id), Integer.valueOf(book_id));
            reserveCall.enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    // Log.d("RESPONSE", response.toString());
                    Toast.makeText(BookAvailabilityPage.this, "RESERVED", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(BookAvailabilityPage.this, HomePage.class);
                    i.putExtra("UserId", user_id);
                    startActivity(i);
                }

                @Override
                public void onFailure(Call<Void> call, Throwable t) {
                    // Log.d("FAILURE", call.toString());
                    Toast.makeText(BookAvailabilityPage.this, "FAILED TO RESERVE", Toast.LENGTH_SHORT).show();
                }
            });
        }

        call.enqueue(new Callback<List<Book>>() {
            @Override
            public void onResponse(Call<List<Book>> call, Response<List<Book>> response) {
                if (response.isSuccessful()) {
                    for(Book book: response.body()){
                        // Log.d("BOOK", book.toString());
                        books.add(book);
                    }
                }

                // Adding the books to recycler view
                mRecyclerView = findViewById(R.id.rvBooks);
                mRecyclerView.setHasFixedSize(true);
                // Log.d("USERID", user_id);
                Integer u_id;
                u_id = Integer.valueOf(user_id);
                mAdapter = new BookAdapter(books, BookAvailabilityPage.this, u_id);
                mRecyclerView.setLayoutManager(mLayoutManager);
                mRecyclerView.setAdapter(mAdapter);
            }

            @Override
            public void onFailure(Call<List<Book>> call, Throwable t) {
                // Log.d("BOOK", "Running error");
                Toast.makeText(BookAvailabilityPage.this, "Fail to get the data..", Toast.LENGTH_SHORT).show();
            }
        });
    }
}