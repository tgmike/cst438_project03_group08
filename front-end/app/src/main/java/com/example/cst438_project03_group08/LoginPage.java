package com.example.cst438_project03_group08;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.io.UnsupportedEncodingException;
//import java.util.Base64;
import android.util.Base64;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginPage extends AppCompatActivity {

    private EditText userName;
    private EditText password;
    private String uName, uPassword;
    Button cancel;
    Button bLogin;

    private TextView userData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);

        userData = findViewById(R.id.userData);
        cancel = findViewById(R.id.cancel);
        bLogin = findViewById(R.id.bLogin);
        userName = findViewById(R.id.etUsername);
        password = findViewById(R.id.etPassword);


        bLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                String inputName = userName.getText().toString();
                String inputPassword = password.getText().toString();

                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl("https://cst438-project03-group08.herokuapp.com/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                RetrofitInterface retrofitInterface = retrofit.create(RetrofitInterface.class);

                Call<List<User>> call = retrofitInterface.getUsers();


                call.enqueue(new Callback<List<User>>() {
                    @Override
                    public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                        if (!response.isSuccessful()) {
                            userData.setText("Code: " + response.code());
                            return;
                        }

                        List<User> users = response.body();
                        if(!inputName.isEmpty() && !inputPassword.isEmpty()){
                            for(User user: users){
                                if(inputName.equals(user.getUsername())){
                                    uName = user.getUsername();
                                    if(inputPassword.equals(user.getPassword())){
                                        uPassword = user.getPassword();
                                        Toast.makeText(LoginPage.this, "Welcome", Toast.LENGTH_SHORT).show();
                                            Intent i = new Intent(LoginPage.this, HomePage.class);
                                            i.putExtra("user_id", user.getUserId());
                                            startActivity(i);
                                            finish();
                                            break;
                                    }else{
                                        Toast.makeText(LoginPage.this, "Wrong Credentials. Try Again.", Toast.LENGTH_SHORT).show();
                                        break;
                                    }
                                }else{
                                    Toast.makeText(LoginPage.this, "Wrong Credentials. Try Again.", Toast.LENGTH_SHORT).show();
                                    break;
                                }
                            }
                        }else{
                            Toast.makeText(LoginPage.this, "Fields Cannot be Empty.", Toast.LENGTH_SHORT).show();

                        }
                    }

                    @Override
                    public void onFailure(Call<List<User>> call, Throwable t) {
                        userData.setText(t.getMessage());
                    }
                });

            }

        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(LoginPage.this, LandingPage.class);
                startActivity(i);
                finish();
            }
        });


    }

}
