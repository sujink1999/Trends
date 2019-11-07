package com.sujin.trends.ui.main;

import com.google.gson.annotations.SerializedName;

public class AddBookmark {


    @SerializedName("id")
    private String id;

    @SerializedName("val")
    private Repository val;

    public AddBookmark(String id,Repository val)
    {
        this.id=id;
        this.val=val;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setVal(Repository val) {
        this.val = val;
    }

    public Repository getVal() {
        return val;
    }
}
