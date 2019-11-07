package com.sujin.trends.ui.main;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PostResult {

    @SerializedName("obj")
    private String obj;

    @SerializedName("id")
    private String id;

    @SerializedName("list")
    private List<Repository> list;


    public String getObj() {
        return obj;
    }

    public String getId() {
        return id;
    }

    public List<Repository> getList() {
        return list;
    }
}
