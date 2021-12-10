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
    List<Book> books;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_availability_page);

        getBooksFunction();
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
//        mAdapter = new BookAdapter(allBooks);
//        mRecyclerView.setLayoutManager(mLayoutManager);
//        mRecyclerView.setAdapter(mAdapter);
    }

    void getBooksFunction(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://cst438-project03-group08.herokuapp.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        bookAPI bookService = retrofit.create(bookAPI.class);
        Call<BookHelper> call = bookService.getBooks();

        call.enqueue(new Callback<BookHelper>() {
            @Override
            public void onResponse(Call<BookHelper> call, Response<BookHelper> response) {
                if (response.isSuccessful()) {
                    for(Book book: response.body().getBooks()){
                        Log.d("BOOK", book.toString());
                        books.add(book);
                    }
                }
            }

            @Override
            public void onFailure(Call<BookHelper> call, Throwable t) {
                Log.d("BOOK", "Running error");
                Toast.makeText(BookAvailabilityPage.this, "Fail to get the data..", Toast.LENGTH_SHORT).show();
            }
        });
    }
}