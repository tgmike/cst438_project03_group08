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

public class BookAvailabilityPage extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

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

        call.enqueue(new Callback<List<Book>>() {
            @Override
            public void onResponse(Call<List<Book>> call, Response<List<Book>> response) {
                if (response.isSuccessful()) {
                    for(Book book: response.body()){
                        Log.d("BOOK", book.toString());
                        books.add(book);
                    }
                }

                // Adding the books to recycler view
                mRecyclerView = findViewById(R.id.rvBooks);
                mRecyclerView.setHasFixedSize(true);
                mAdapter = new BookAdapter(books);
                mRecyclerView.setLayoutManager(mLayoutManager);
                mRecyclerView.setAdapter(mAdapter);
            }

            @Override
            public void onFailure(Call<List<Book>> call, Throwable t) {
                Log.d("BOOK", "Running error");
                Toast.makeText(BookAvailabilityPage.this, "Fail to get the data..", Toast.LENGTH_SHORT).show();
            }
        });
//
//
//        List<Book> allBooks = new ArrayList<>();
//        mLayoutManager = new LinearLayoutManager(this);
//
//        // adding mock data of Books
//        allBooks.add(new Book("Green Eggs and Ham", "Dr. Seuss"));
//        allBooks.add(new Book("Hunger Games", "Suzanne Collins"));
//        allBooks.add(new Book("Unbroken", "Lauren Hillenbrand"));
//        allBooks.add(new Book("Harry Potter and the Deathly Hallows", "J.K. Rowling"));
//        allBooks.add(new Book("Twilight", "Stephanie Meyer"));
//        allBooks.add(new Book("The Lovely Bones", "Alice Sebold"));
//        allBooks.add(new Book("Life of Pi", "Yann Martel"));
//        allBooks.add(new Book("The Boy in the Striped Pyjamas", "John Boyne"));
//
//
//        // Adding the exercises to recycler view
//        mRecyclerView = findViewById(R.id.rvBooks);
//        mRecyclerView.setHasFixedSize(true);
//        mAdapter = new BookAdapter(books);
//        mRecyclerView.setLayoutManager(mLayoutManager);
//        mRecyclerView.setAdapter(mAdapter);
    }

}