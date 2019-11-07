package com.sujin.trends;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.sujin.trends.ui.main.Api;
import com.sujin.trends.ui.main.Data;
import com.sujin.trends.ui.main.DatabaseHelper;
import com.sujin.trends.ui.main.PostResult;
import com.sujin.trends.ui.main.Repository;

import java.io.File;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends AppCompatActivity {


    DatabaseHelper databaseHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        /*File dbFile = this.getDatabasePath("name.db");
        if(dbFile.exists())
        {
            Toast.makeText(this, "Welcome back!", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(getApplicationContext(),MainActivity.class);
            startActivity(intent);
        }else {
            //DatabaseHelper databaseHelper = new DatabaseHelper(this);
        }*/


        Button button = findViewById(R.id.log_in);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                /*databaseHelper = new DatabaseHelper(getApplicationContext());
                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);*/

                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(Api.POST_URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                Api service = retrofit.create(Api.class);
                Call<PostResult> call = service.sendStatus("username","password");

                call.enqueue(new Callback<PostResult>() {
                    @Override
                    public void onResponse(Call<PostResult> call, Response<PostResult> response) {
                        Toast.makeText(LoginActivity.this, response.toString(), Toast.LENGTH_SHORT).show();
                        PostResult postResult = response.body();
                        for(int i=0;i<postResult.getList().size();i++)
                        {
                            databaseHelper = new DatabaseHelper(getApplicationContext());
                            databaseHelper.insertData(postResult.getList().get(i));
                        }
                    }

                    @Override
                    public void onFailure(Call<PostResult> call, Throwable t) {

                        Toast.makeText(LoginActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });


            }
        });

        /*final RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        queue.start();
        Button button = findViewById(R.id.log_in);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final HashMap<String, String> params = new HashMap<String, String>();
                params.put("username", "sujin"); // the entered data as the body.
                params.put("name", "name");
                params.put("password", "password");

                JsonObjectRequest jsObjRequest = new
                        JsonObjectRequest(Request.Method.POST,
                        "http://192.168.0.102:5500/",
                        new JSONObject(params),
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                try {


                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        }



                        , new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //DisplayText.setText("That didn't work!");
                        error.printStackTrace();
                    }
                });
                queue.add(jsObjRequest);
            }
        });*/
            }



    }

