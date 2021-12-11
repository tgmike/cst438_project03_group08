package com.example.cst438_project03_group08;

import java.time.LocalDateTime;

public class Reservations {

    int bookId;
    int userId;
    int reservationId;
    LocalDateTime date;

    public Reservations(int bookId, int userId, int reservationId, LocalDateTime date) {
        this.bookId = bookId;
        this.userId = userId;
        this.reservationId = reservationId;
        this.date = date;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getReservationId() {
        return reservationId;
    }

    public void setReservationId(int reservationId) {
        this.reservationId = reservationId;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Reservations{" +
                "bookId=" + bookId +
                ", userId=" + userId +
                ", reservationId=" + reservationId +
                ", date=" + date +
                '}';
    }
}
