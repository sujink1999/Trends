package com.sujin.trends.ui.main;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class User implements Serializable {

    @SerializedName("username")
    private String username;

    @SerializedName("href")
    private String href;

    @SerializedName("avatar")
    private String avatar;

    public User(String username,String href,String avatar){
        this.username = username;
        this.href = href;
        this.avatar = avatar;
    }

    public String getAvatar() {
        return avatar;
    }

    public String getHref() {
        return href;
    }

    public String getUsername() {
        return username;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
