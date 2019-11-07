package com.sujin.trends.ui.main;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Repository {
    @SerializedName("author")
    private String author;

    @SerializedName("name")
    private String name;

    @SerializedName("avatar")
    private String avatar;

    @SerializedName("url")
    private String url;

    @SerializedName("description")
    private String description;

    @SerializedName("language")
    private String language;

    @SerializedName("languageColor")
    private String languageColor;

    @SerializedName("stars")
    private Integer stars;

    @SerializedName("forks")
    private Integer forks;

    @SerializedName("currentPeriodStars")
    private Integer currentPeriodStars;

    @SerializedName("builtBy")
    private List<User> builtBy;

    public Repository(String author, String name, String avatar, String url, String description, String language,
                      String languageColor, Integer stars, Integer forks, Integer currentPeriodStars )
    {
        this.author=author;
        this.avatar=avatar;
        this.name=name;
        this.url=url;
        this.description=description;
        this.language=language;
        this.languageColor=languageColor;
        this.stars=stars;
        this.forks=forks;
        this.currentPeriodStars=currentPeriodStars;
    }



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getLanguageColor() {
        return languageColor;
    }

    public void setLanguageColor(String languageColor) {
        this.languageColor = languageColor;
    }

    public Integer getStars() {
        return stars;
    }

    public void setStars(Integer stars) {
        this.stars = stars;
    }

    public Integer getForks() {
        return forks;
    }

    public void setForks(Integer forks) {
        this.forks = forks;
    }

    public Integer getCurrentPeriodStars() {
        return currentPeriodStars;
    }

    public void setCurrentPeriodStars(Integer currentPeriodStars) {
        this.currentPeriodStars = currentPeriodStars;
    }

    public void setBuiltBy(List<User> builtBy) {
        this.builtBy = builtBy;
    }

    public List<User> getBuiltBy() {
        return builtBy;
    }
}

//"author":"notepad-plus-plus","name":"notepad-plus-plus","avatar":"https://github.com/notepad-plus-plus.png",
// "url":"https://github.com/notepad-plus-plus/notepad-plus-plus","description":"Notepad++ official repository",
// "language":"C++","languageColor":"#f34b7d","stars":9552,"forks":2462,"currentPeriodStars":362,
// "builtBy":[{"username":"donho","href":"https://github.com/donho",
// "avatar":"https://avatars0.githubusercontent.com/u/90293"},{"username":"SinghRajenM",
// "href":"https://github.com/SinghRajenM","avatar":"https://avatars0.githubusercontent.com/u/14791461"},
// {"username":"dail8859","href":"https://github.com/dail8859","avatar":"https://avatars1.githubusercontent.com/u/3694843"},
// {"username":"chcg","href":"https://github.com/chcg","avatar":"https://avatars1.githubusercontent.com/u/12630740"},
// {"username":"jonandr","href":"https://github.com/jonandr","avatar":"https://avatars3.githubusercontent.com/u/22964455"}]}