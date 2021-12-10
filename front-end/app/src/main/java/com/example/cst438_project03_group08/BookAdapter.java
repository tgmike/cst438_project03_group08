package com.example.cst438_project03_group08;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.BookViewHolder> {
    private Context context;
    private List<Book> books;
    private int userId;

    public BookAdapter(List<Book> allBooks, Context context, int userId) {
        books = allBooks;
        this.context = context;
        this.userId = userId;
    }

    @Override
    public BookViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_book, parent, false);
        BookViewHolder bvh = new BookViewHolder(v);
        return bvh;
    }

    @Override
    public void onBindViewHolder(@NonNull BookViewHolder holder, int position) {
        Book book = books.get(position);
        holder.tvTitle.setText("Title: " + book.getTitle());
        holder.tvAuthor.setText("Author: " + book.getAuthor());
        holder.book = book;
    }

    @Override
    public int getItemCount() {
        return books.size();
    }

    public class BookViewHolder extends RecyclerView.ViewHolder {
        public TextView tvTitle;
        public TextView tvAuthor;
        public Button btnReserve;
        Book book;

        public BookViewHolder(View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvAuthor = itemView.findViewById(R.id.tvAuthor);
            btnReserve = itemView.findViewById(R.id.btnReserve);
            btnReserve.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.d("RESERVE", "Successfully reserved book " + book.getBookId());
                    Intent intent = new Intent(context, BookAvailabilityPage.class);
                    intent.putExtra("BookId", String.valueOf(book.bookId));
                    intent.putExtra("UserId", String.valueOf(userId));
                    intent.putExtra("BookTitle", book.getTitle());
                    ((Activity)context).finish();
                    context.startActivity(intent);
                }
            });
        }
    }

//    public interface OnBookListenser;

}
