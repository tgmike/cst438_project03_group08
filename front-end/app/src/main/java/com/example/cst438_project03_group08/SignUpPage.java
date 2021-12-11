package com.example.cst438_project03_group08;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SignUpPage extends AppCompatActivity {

    ImageButton btnBack;
    Button btnSignUp;
    private EditText userName;
    private EditText password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_page);

        btnBack = findViewById(R.id.btnBack);
        btnSignUp = findViewById(R.id.btnSignUp);
        userName = findViewById(R.id.etUsername);
        password = findViewById(R.id.etPassword);

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String inputName = userName.getText().toString();
                String inputPassword = password.getText().toString();
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl("https://cst438-project03-group08.herokuapp.com/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                RetrofitInterface retrofitInterface = retrofit.create(RetrofitInterface.class);
                Call<Void> call = retrofitInterface.createAccount(inputName, inputPassword);
                call.enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        Toast.makeText(SignUpPage.this, "Account Successfully Created", Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(SignUpPage.this, LandingPage.class);
                        startActivity(i);
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        finish();
                    }
                });
                finish();
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}