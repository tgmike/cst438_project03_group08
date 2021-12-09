package com.example.cst438_project03_group08;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Bundle;
import java.util.ArrayList;
import java.util.List;

public class BookAvailabilityPage extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_availability_page);

        List<Book> allBooks = new ArrayList<>();
        mLayoutManager = new LinearLayoutManager(this);

        // adding mock data of Books
        allBooks.add(new Book("Green Eggs and Ham", "Dr. Seuss"));
        allBooks.add(new Book("Hunger Games", "Suzanne Collins"));
        allBooks.add(new Book("Unbroken", "Lauren Hillenbrand"));
        allBooks.add(new Book("Harry Potter and the Deathly Hallows", "J.K. Rowling"));
        allBooks.add(new Book("Twilight", "Stephanie Meyer"));
        allBooks.add(new Book("The Lovely Bones", "Alice Sebold"));
        allBooks.add(new Book("Life of Pi", "Yann Martel"));
        allBooks.add(new Book("The Boy in the Striped Pyjamas", "John Boyne"));


        // Adding the exercises to recycler view
        mRecyclerView = findViewById(R.id.rvBooks);
        mRecyclerView.setHasFixedSize(true);
        mAdapter = new BookAdapter(allBooks);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
    }
}