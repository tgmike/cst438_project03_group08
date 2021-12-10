package com.example.cst438_project03_group08;

/**
 *
 * <h2><b>Book</b></h2>
 * This is the Book data model that is used throughout the application to display the Books.
 *
 * @authors Eric Chavez
 */

public class Book {
    String title;
    String author;
    int bookId;

    public Book (int mBookId, String mTitle, String mAuthor){
        bookId = mBookId;
        title = mTitle;
        author = mAuthor;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    @Override
    public String toString() {
        return "Book{" +
                "title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", bookId=" + bookId +
                '}';
    }
}
