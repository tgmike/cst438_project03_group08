package com.example.cst438_project03_group08;

import java.util.List;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface bookAPI {
    @GET("availability")
    Call<List<Book>> getBooks();

    @POST("reservation")
    Call<Void> reserveBook(@Query("userId") Integer userId,
                             @Query("bookId") Integer bookId);
}
