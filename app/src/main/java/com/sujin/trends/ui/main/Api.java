package com.sujin.trends.ui.main;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface Api {
    String BASE_URL = "https://github-trending-api.now.sh/";
    String POST_URL = "http://www.mocky.io/v2/";

    @GET("repositories?language=&since=")
    Call<List<Repository>> loadChanges(@Query("since") String since);

    @GET("developers?language=&since=")
    Call<List<Developer>> loadDevelopers(@Query("since") String since);

    @GET("developers?language=&since=weekly")
    Call<List<Developer>> loadWeekly();

    @GET("developers?language=&since=monthly")
    Call<List<Developer>> loadMonthly();

    @GET("languages")
    Call<List<Language>> loadLanguages();

    @GET("repositories?language=&since=daily")
    Call<List<Repository>> loadLanguageDaily(@Query("language") String language);

    @GET("repositories?language=&since=weekly")
    Call<List<Repository>> loadLanguageWeekly(@Query("language") String language);

    @GET("repositories?language=&since=monthly")
    Call<List<Repository>> loadLanguageMonthly(@Query("language") String language);

    @FormUrlEncoded
    @POST("5dc0ffb7330000f12d1a4d9d")
    Call<PostResult> sendStatus(@Field("username") String username, @Field("password") String password);

    @FormUrlEncoded
    @POST("5dc0ffb7330000f12d1a4d9d")
    Call<PostResult> sendBookmarkUpdation(@Field("id") String id, @Field("bookmark") Repository bookmark);




}
