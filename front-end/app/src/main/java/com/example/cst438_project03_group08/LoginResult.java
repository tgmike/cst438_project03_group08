package com.example.cst438_project03_group08;

import com.google.gson.annotations.SerializedName;

public class LoginResult {

    private String id;

    private String username;

    private String password;

    private String getId() {
        return id;
    }

    public String getName() {
        return username;
    }

    public String getEmail() {
        return password;
    }
}
