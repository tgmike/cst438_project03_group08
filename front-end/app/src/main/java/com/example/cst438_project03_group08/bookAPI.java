package com.example.cst438_project03_group08;

import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;

public interface bookAPI {
    @GET("availability")
    Call<List<Book>> getBooks();
}
