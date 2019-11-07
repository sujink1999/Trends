package com.sujin.trends.ui.main;

import com.google.gson.annotations.SerializedName;

public class Language {

    @SerializedName("urlParam")
    private String urlParam;

    @SerializedName("name")
    private String name;

    public Language(String urlParam,String name)
    {
        this.name=name;
        this.urlParam=urlParam;
    }


    public String getUrlParam() {
        return urlParam;
    }

    public String getName() {
        return name;
    }
}
