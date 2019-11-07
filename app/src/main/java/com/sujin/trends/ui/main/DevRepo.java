package com.sujin.trends.ui.main;

import com.google.gson.annotations.SerializedName;

public class DevRepo {

    @SerializedName("name")
    private String name;

    @SerializedName("description")
    private String description;

    @SerializedName("url")
    private String url;

    public DevRepo(String name,String description,String url){
        this.name = name;
        this.description = description;
        this.url = url;
    }


    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getUrl() {
        return url;
    }
}

/*

{"username":"tmcw","name":"Tom MacWright","type":"user","url":"https://github.com/tmcw",
        "avatar":"https://avatars1.githubusercontent.com/u/32314",
        "repo":{"name":"big","description":"presentations for busy messy hackers","url":"https://github.com/tmcw/big"}}*/
