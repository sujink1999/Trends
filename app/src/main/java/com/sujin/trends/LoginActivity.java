package com.sujin.trends;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.sujin.trends.ui.main.Api;
import com.sujin.trends.ui.main.Data;
import com.sujin.trends.ui.main.DatabaseHelper;
import com.sujin.trends.ui.main.PostResult;
import com.sujin.trends.ui.main.Repository;
import com.sujin.trends.ui.main.UserDetails;

import java.io.File;
import java.io.Serializable;
import java.util.List;
import java.util.Timer;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends AppCompatActivity {


    TextView username, password;
    DatabaseHelper databaseHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        File dbFile = this.getDatabasePath("name.db");
        if(dbFile.exists())
        {
            Toast.makeText(this, "Welcome back!", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(getApplicationContext(),MainActivity.class);
            intent.putExtra("type","logged_in");
            startActivity(intent);
            finish();
        }


        Button register = findViewById(R.id.register);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if(username.getText().toString().equals("") || password.getText().toString().equals(""))
                {
                    Toast.makeText(LoginActivity.this, "Fields cannot be empty", Toast.LENGTH_SHORT).show();
                }else {

                    Retrofit retrofit = new Retrofit.Builder()
                            .baseUrl(Api.POST_URL)
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();
                    Api service = retrofit.create(Api.class);
                    Call<PostResult> call = service.sendStatus(new UserDetails(username.getText().toString(), password.getText().toString()));
                    Toast.makeText(LoginActivity.this, "Working on it...", Toast.LENGTH_SHORT).show();
                    call.enqueue(new Callback<PostResult>() {
                        @Override
                        public void onResponse(Call<PostResult> call, Response<PostResult> response) {

                            if (response.body().getObj().equals("success")) {
                                databaseHelper = new DatabaseHelper(getApplicationContext());
                                Log.d("id", response.body().getId());
                                PostResult postResult = response.body();
                                if (postResult.getList() != null) {
                                    for (int i = 0; i < postResult.getList().size(); i++) {
                                        databaseHelper.insertData(postResult.getList().get(i));

                                    }
                                    databaseHelper.insertUserId(postResult.getId());
                                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                    intent.putExtra("type","register");
                                    intent.putExtra("uid",postResult.getId());
                                    Log.d("id",postResult.getId());
                                    startActivity(intent);
                                    finish();

                                }
                            } else {
                                Toast.makeText(LoginActivity.this, "Invalid Credentials", Toast.LENGTH_SHORT).show();
                            }

                        }


                        @Override
                        public void onFailure(Call<PostResult> call, Throwable t) {

                            Toast.makeText(LoginActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
                            Log.d("error", t.getMessage());
                        }
                    });


                }


            }
        });

        Button login = findViewById(R.id.log_in);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if(username.getText().toString().equals("") || password.getText().toString().equals(""))
                {
                    Toast.makeText(LoginActivity.this, "Fields cannot be empty", Toast.LENGTH_SHORT).show();
                }else {
                    Retrofit retrofit = new Retrofit.Builder()
                            .baseUrl(Api.POST_URL)
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();
                    Api service = retrofit.create(Api.class);
                    Call<PostResult> call = service.login(new UserDetails(username.getText().toString(), password.getText().toString()));
                    Toast.makeText(LoginActivity.this, "Working on it...", Toast.LENGTH_SHORT).show();
                    call.enqueue(new Callback<PostResult>() {
                        @Override
                        public void onResponse(Call<PostResult> call, Response<PostResult> response) {

                            if (response.body().getObj().equals("true")) {
                                databaseHelper = new DatabaseHelper(getApplicationContext());
                                //Toast.makeText(LoginActivity.this, response.body().getList().toString(), Toast.LENGTH_SHORT).show();
                                PostResult postResult = response.body();

                                for (int i = 0; i < postResult.getList().size(); i++) {
                                    //databaseHelper.insertData(postResult.getList().get(i));
                                    Log.d("tag", postResult.getList().get(i).getAuthor());
                                }

                                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                Bundle args = new Bundle();
                                intent.putExtra("type","login");
                                Log.d("id",postResult.getId());
                                args.putString("uid", postResult.getId());
                                args.putSerializable("ARRAYLIST", (Serializable) postResult.getList());
                                intent.putExtra("BUNDLE", args);
                                startActivity(intent);
                                finish();

                            }
                        }
                        /*for(int i=0;i<postResult.getList().size();i++)
                        {
                            databaseHelper = new DatabaseHelper(getApplicationContext());
                            databaseHelper.insertData(postResult.getList().get(i));
                        }*/


                        @Override
                        public void onFailure(Call<PostResult> call, Throwable t) {

                            Toast.makeText(LoginActivity.this, "Invalid Credentials", Toast.LENGTH_LONG).show();
                            Log.d("error", t.getMessage());
                        }
                    });
                }
            }
        });


            }



    }

