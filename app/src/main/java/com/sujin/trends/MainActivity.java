package com.sujin.trends;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.tabs.TabLayout;
import com.sujin.trends.ui.main.Api;
import com.sujin.trends.ui.main.BookmarksFragment;
import com.sujin.trends.ui.main.DatabaseHelper;
import com.sujin.trends.ui.main.DevelopersFragment;
import com.sujin.trends.ui.main.FilterBottomSheet;
import com.sujin.trends.ui.main.Language;
import com.sujin.trends.ui.main.RepositoriesFragment;
import com.sujin.trends.ui.main.Developer;
import com.sujin.trends.ui.main.Repository;
import com.sujin.trends.ui.main.SectionsPageAdapter;
import com.sujin.trends.ui.main.User;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements FilterBottomSheet.applyButtonClickListener{


    private SectionsPageAdapter mSectionsPageAdapter;
    private ViewPager mViewPager;
    String user_id;
    TabLayout tabLayout;
    List<Repository> dailyRepositories,weeklyRepositories,monthlyRepositories,dailyLanguage,monthlyLanguage,weeklyLanguage,bookmarkrepositories;
    List<Repository> emptyRepositories;
    List<Developer> dailyDevelopers,weeklyDevelopers,monthlyDevelopers;
    List<String> lang = new ArrayList<>();
    FilterBottomSheet filterBottomSheet;
    List<Language> languages;
    ShimmerFrameLayout mShimmerViewContainer;
    int noc=0,nolc=0;
    DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        databaseHelper = new DatabaseHelper(this);
        user_id = databaseHelper.getUserId();
        Cursor bookmarks = databaseHelper.getBookmarks();
        bookmarkrepositories = new ArrayList<>();
        if(bookmarks.getCount()!=0)
        {
            while (bookmarks.moveToNext())
            {
                Repository newRepository = new Repository(bookmarks.getString(1),
                        bookmarks.getString(2),
                        bookmarks.getString(3),
                        bookmarks.getString(4),
                        bookmarks.getString(5),
                        bookmarks.getString(6),
                        bookmarks.getString(7),
                        bookmarks.getInt(8),
                        bookmarks.getInt(9),
                        bookmarks.getInt(10));
                Cursor contributors = databaseHelper.getBuiltBy(bookmarks.getString(1)+bookmarks.getString(2));
                if(contributors.getCount()!=0)
                {
                    while(contributors.moveToNext())
                    {
                        newRepository.setBuiltBy(new ArrayList<User>());
                        newRepository.getBuiltBy().add(new User(contributors.getString(1),contributors.getString(2),
                                contributors.getString(3)));
                    }
                }
                bookmarkrepositories.add(newRepository);
            }
        }


        mSectionsPageAdapter = new SectionsPageAdapter(getSupportFragmentManager());
        mViewPager = (ViewPager) findViewById(R.id.view_pager);
        tabLayout = (TabLayout) findViewById(R.id.tabs);
        filterBottomSheet = new FilterBottomSheet(lang);
        mShimmerViewContainer = findViewById(R.id.shimmer_view_container);
        mShimmerViewContainer.startShimmerAnimation();


        getDailyData();
        getMonthlyData();
        getWeeklyData();
        getDailyDevelopers();
        getMonthlyDevelopers();
        getWeeklyDevelopers();
        getLanguages();

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                /*if(item.getItemId()==R.id.developers)
                {
                    Toast.makeText(MainActivity.this, "Developers", Toast.LENGTH_SHORT).show();
                    setupViewPager(mViewPager,"developers");
                    tabLayout.setupWithViewPager(mViewPager);

                }else */
                if(item.getItemId()==R.id.bookmarks)
                {
                    Toast.makeText(MainActivity.this, "Bookmarks", Toast.LENGTH_SHORT).show();
                    setupViewPager(mViewPager,"bookmarks");
                }else if(item.getItemId()==R.id.repositories)
                {
                    Toast.makeText(MainActivity.this, "Repositories", Toast.LENGTH_SHORT).show();
                    setupViewPager(mViewPager,"repositories");
                    tabLayout.setupWithViewPager(mViewPager);
                }

                        return true;

                }

            });

    }

    private void setupViewPager(ViewPager viewPager,String nav)
    {
        if(nav.equals("repositories")) {

            mSectionsPageAdapter.removeFragments();
            mSectionsPageAdapter.addFragment(new RepositoriesFragment(dailyRepositories,bookmarkrepositories), "Daily");
            mSectionsPageAdapter.addFragment(new RepositoriesFragment(weeklyRepositories,bookmarkrepositories), "Weekly");
            mSectionsPageAdapter.addFragment(new RepositoriesFragment(monthlyRepositories,bookmarkrepositories), "Monthly");
            viewPager.setAdapter(mSectionsPageAdapter);

            //mSectionsPageAdapter.notifyDataSetChanged();
        }else if(nav.equals("developers"))
        {
            mSectionsPageAdapter.removeFragments();
            mSectionsPageAdapter.addFragment(new DevelopersFragment(dailyDevelopers), "Daily");
            mSectionsPageAdapter.addFragment(new DevelopersFragment(weeklyDevelopers), "Weekly");
            mSectionsPageAdapter.addFragment(new DevelopersFragment(monthlyDevelopers), "Monthly");

            viewPager.setAdapter(mSectionsPageAdapter);

        }else if(nav.equals("bookmarks"))
        {
            mSectionsPageAdapter.removeFragments();
            mSectionsPageAdapter.addFragment(new BookmarksFragment(bookmarkrepositories),"Bookmarks");
            viewPager.setAdapter(mSectionsPageAdapter);
        }else if(nav.equals("language"))
        {
            mSectionsPageAdapter.removeFragments();
            mSectionsPageAdapter.addFragment(new RepositoriesFragment(dailyLanguage,bookmarkrepositories), "Daily");
            mSectionsPageAdapter.addFragment(new RepositoriesFragment(weeklyLanguage,bookmarkrepositories), "Weekly");
            mSectionsPageAdapter.addFragment(new RepositoriesFragment(monthlyLanguage,bookmarkrepositories), "Monthly");
            viewPager.setAdapter(mSectionsPageAdapter);
        }else if(nav.equals("clear"))
        {
            mSectionsPageAdapter.removeFragments();
            viewPager.setAdapter(mSectionsPageAdapter);
        }
    }

    public void getDailyDevelopers()
    {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        Api service = retrofit.create(Api.class);
        Call<List<Developer>> repos = service.loadDevelopers("daily");

        repos.enqueue(new Callback<List<Developer>>() {
            @Override
            public void onResponse(Call<List<Developer>> call, Response<List<Developer>> response) {
                List<Developer> d=response.body();
                if(d!=null)
                {
                    dailyDevelopers = d;

                }



            }

            @Override
            public void onFailure(Call<List<Developer>> call, Throwable t) {
                Log.d("Repository",t.getMessage());
            }
        });
    }

    public void getLanguages()
    {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        Api service = retrofit.create(Api.class);
        Call<List<Language>> repos = service.loadLanguages();

        repos.enqueue(new Callback<List<Language>>() {
            @Override
            public void onResponse(Call<List<Language>> call, Response<List<Language>> response) {
                List<Language> d=response.body();
                if(d!=null)
                {
                    for (int i = 0; i < d.size(); i++) {
                        languages = d;
                        lang.add(d.get(i).getName());
                    }

                }
            }

            @Override
            public void onFailure(Call<List<Language>> call, Throwable t) {
                Log.d("Repository",t.getMessage());
            }
        });
    }

    public void getWeeklyDevelopers()
    {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        Api service = retrofit.create(Api.class);
        Call<List<Developer>> dev = service.loadWeekly();

        dev.enqueue(new Callback<List<Developer>>() {
            @Override
            public void onResponse(Call<List<Developer>> call, Response<List<Developer>> response) {
                List<Developer> week=response.body();
                if(week!=null)
                {
                    weeklyDevelopers = week;

                }
            }

            @Override
            public void onFailure(Call<List<Developer>> call, Throwable t) {
                Log.d("Repository",t.getMessage());
            }
        });
    }

    public void getMonthlyDevelopers()
    {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        Api service = retrofit.create(Api.class);
        Call<List<Developer>> repos = service.loadMonthly();

        repos.enqueue(new Callback<List<Developer>>() {
            @Override
            public void onResponse(Call<List<Developer>> call, Response<List<Developer>> response) {
                List<Developer> d=response.body();
                if(d!=null)
                {
                    Log.d("Developer",d.get(0).getUsername());
                    monthlyDevelopers = d;
                }
            }

            @Override
            public void onFailure(Call<List<Developer>> call, Throwable t) {
                Log.d("Repository",t.getMessage());
            }
        });
    }

    public void getDailyData()
    {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        Api service = retrofit.create(Api.class);
        Call<List<Repository>> repos = service.loadChanges("daily");

        repos.enqueue(new Callback<List<Repository>>() {
            @Override
            public void onResponse(Call<List<Repository>> call, Response<List<Repository>> response) {
                List<Repository> d=response.body();
                if(d!=null)
                {
                    Log.d("Repository",d.get(1).getAuthor());
                    dailyRepositories = d;
                    noc++;
                    if(noc==3) {
                        setupViewPager(mViewPager, "repositories");

                        tabLayout.setupWithViewPager(mViewPager);
                        noc=0;

                        mShimmerViewContainer.stopShimmerAnimation();
                        mShimmerViewContainer.setVisibility(View.INVISIBLE);

                    }
                }



            }

            @Override
            public void onFailure(Call<List<Repository>> call, Throwable t) {
                Log.d("Repository",t.getMessage());
            }
        });
    }

    public void getWeeklyData()
    {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        Api service = retrofit.create(Api.class);
        Call<List<Repository>> repos = service.loadChanges("weekly");

        repos.enqueue(new Callback<List<Repository>>() {
            @Override
            public void onResponse(Call<List<Repository>> call, Response<List<Repository>> response) {
                List<Repository> d=response.body();
                if(d!=null)
                {
                    Log.d("Repository",d.get(1).getAuthor());
                    weeklyRepositories = d;
                    noc++;
                    if(noc==3) {
                        setupViewPager(mViewPager, "repositories");
                        tabLayout.setupWithViewPager(mViewPager);
                        noc=0;

                        mShimmerViewContainer.stopShimmerAnimation();
                        mShimmerViewContainer.setVisibility(View.INVISIBLE);
                    }
                }



            }

            @Override
            public void onFailure(Call<List<Repository>> call, Throwable t) {
                Log.d("Repository",t.getMessage());
            }
        });
    }

    public void getMonthlyData()
    {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        Api service = retrofit.create(Api.class);
        Call<List<Repository>> repos = service.loadChanges("monthly");

        repos.enqueue(new Callback<List<Repository>>() {
            @Override
            public void onResponse(Call<List<Repository>> call, Response<List<Repository>> response) {
                List<Repository> d=response.body();
                if(d!=null)
                {
                    Log.d("Repository",d.get(1).getAuthor());
                    monthlyRepositories = d;

                    noc++;
                    if(noc==3) {
                        setupViewPager(mViewPager, "repositories");

                        tabLayout.setupWithViewPager(mViewPager);
                        noc=0;

                        mShimmerViewContainer.stopShimmerAnimation();
                        mShimmerViewContainer.setVisibility(View.INVISIBLE);
                    }
                }



            }

            @Override
            public void onFailure(Call<List<Repository>> call, Throwable t) {
                Log.d("Repository",t.getMessage());
            }
        });
    }

    public void getLanguageSpecificData(String language)
    {
        {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(Api.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            Api service = retrofit.create(Api.class);
            Call<List<Repository>> repos = service.loadLanguageDaily(language);

            repos.enqueue(new Callback<List<Repository>>() {
                @Override
                public void onResponse(Call<List<Repository>> call, Response<List<Repository>> response) {
                    List<Repository> d=response.body();
                    if(d!=null)
                    {
                        dailyLanguage = d;

                        nolc++;
                        if(nolc==3) {
                            setupViewPager(mViewPager, "language");

                            tabLayout.setupWithViewPager(mViewPager);
                            nolc=0;

                            mShimmerViewContainer.stopShimmerAnimation();
                            mShimmerViewContainer.setVisibility(View.INVISIBLE);
                        }
                    }
                }

                @Override
                public void onFailure(Call<List<Repository>> call, Throwable t) {
                    Log.d("Repository",t.getMessage());
                }
            });

            Call<List<Repository>> repos2 = service.loadLanguageMonthly(language);

            repos2.enqueue(new Callback<List<Repository>>() {
                @Override
                public void onResponse(Call<List<Repository>> call, Response<List<Repository>> response) {
                    List<Repository> d=response.body();
                    if(d!=null)
                    {
                        monthlyLanguage = d;

                        nolc++;
                        if(nolc==3) {
                            setupViewPager(mViewPager, "language");

                            tabLayout.setupWithViewPager(mViewPager);
                            nolc=0;

                            mShimmerViewContainer.stopShimmerAnimation();
                            mShimmerViewContainer.setVisibility(View.INVISIBLE);
                        }
                    }
                }

                @Override
                public void onFailure(Call<List<Repository>> call, Throwable t) {
                    Log.d("Repository",t.getMessage());
                }
            });

            Call<List<Repository>> repos3 = service.loadLanguageWeekly(language);

            repos3.enqueue(new Callback<List<Repository>>() {
                @Override
                public void onResponse(Call<List<Repository>> call, Response<List<Repository>> response) {
                    List<Repository> d=response.body();
                    if(d!=null)
                    {
                        weeklyLanguage = d;

                        nolc++;
                        if(nolc==3) {
                            setupViewPager(mViewPager, "language");
                            tabLayout.setupWithViewPager(mViewPager);
                            nolc=0;

                            mShimmerViewContainer.stopShimmerAnimation();
                            mShimmerViewContainer.setVisibility(View.INVISIBLE);
                        }
                    }
                }

                @Override
                public void onFailure(Call<List<Repository>> call, Throwable t) {
                    Log.d("Repository",t.getMessage());
                }
            });


        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

       switch (item.getItemId()) {
           case R.id.view_developers:
               setupViewPager(mViewPager, "developers");
               tabLayout.setupWithViewPager(mViewPager);
               break;

           case R.id.add_filter:

               filterBottomSheet.show(getSupportFragmentManager(),
                       "filter");
               break;
       }
        return false;
    }

    @Override
    public void onLanguageSelected(int position) {
        filterBottomSheet.dismiss();
        getLanguageSpecificData(languages.get(position).getUrlParam());
        setupViewPager(mViewPager, "clear");

        tabLayout.setupWithViewPager(mViewPager);

        mShimmerViewContainer.setVisibility(View.VISIBLE);
        mShimmerViewContainer.startShimmerAnimation();


    }

}