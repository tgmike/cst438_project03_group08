package com.example.cst438_project03_group08;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.BookViewHolder> {
    private List<Book> mExampleList;

    public class BookViewHolder extends RecyclerView.ViewHolder {
        public TextView mTitle;
        public TextView mAuthor;

        public BookViewHolder(View itemView) {
            super(itemView);
            mTitle = itemView.findViewById(R.id.tvTitle);
            mAuthor = itemView.findViewById(R.id.tvAuthor);
        }

        public void bind(Book book) {
            mTitle.setText("Title: " + book.getTitle());
            mAuthor.setText("Author: " + book.getAuthor() + "\n");
        }
    }

    public BookAdapter(List<Book> exampleList) {
        mExampleList = exampleList;
    }

    @Override
    public BookViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_book, parent, false);
        BookViewHolder bvh = new BookViewHolder(v);
        return bvh;
    }

    @Override
    public void onBindViewHolder(@NonNull BookViewHolder holder, int position) {
        Book book = mExampleList.get(position);
        holder.bind(book);
    }

    @Override
    public int getItemCount() {
        return mExampleList.size();
    }

}
