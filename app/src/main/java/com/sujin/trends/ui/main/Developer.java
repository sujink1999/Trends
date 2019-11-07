package com.sujin.trends.ui.main;

import com.google.gson.annotations.SerializedName;

public class Developer {

    @SerializedName("username")
    private String username;

    @SerializedName("name")
    private String name;

    @SerializedName("type")
    private String type;

    @SerializedName("avatar")
    private String avatar;

    @SerializedName("repo")
    private DevRepo repo;

    public Developer(String username,String name,String type,String avatar){
        this.username=username;
        this.name = name;
        this.type = type;
        this.avatar = avatar;
    }


    public String getUsername() {
        return username;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public String getAvatar() {
        return avatar;
    }

    public DevRepo getRepo() {
        return repo;
    }
}

/*

{"username":"tmcw","name":"Tom MacWright","type":"user","url":"https://github.com/tmcw",
        "avatar":"https://avatars1.githubusercontent.com/u/32314",
        "repo":{"name":"big","description":"presentations for busy messy hackers","url":"https://github.com/tmcw/big"}}*/

