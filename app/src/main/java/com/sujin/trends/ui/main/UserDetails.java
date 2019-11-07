package com.sujin.trends.ui.main;

import com.google.gson.annotations.SerializedName;

public class UserDetails {

    @SerializedName("username")
    private String username;

    @SerializedName("password")
    private String password;

    public UserDetails(String username,String password)
    {
        this.username=username;
        this.password=password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
