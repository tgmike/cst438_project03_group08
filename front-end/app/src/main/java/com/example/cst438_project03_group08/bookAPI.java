package com.example.cst438_project03_group08;

import java.util.List;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface bookAPI {
    @GET("availability")
    Call<List<Book>> getBooks();

    @POST("reservation")
    Call<String> reserveBook(@Body BookReserve bookReserve);
}
