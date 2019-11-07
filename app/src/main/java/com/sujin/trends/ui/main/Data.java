package com.sujin.trends.ui.main;

import com.google.gson.annotations.SerializedName;

public class Data {


    @SerializedName("msg")
    private String msg;

    public Data(String msg)
    {
        this.msg = msg;
    }
}
