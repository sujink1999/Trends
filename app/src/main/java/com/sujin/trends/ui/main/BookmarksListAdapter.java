package com.sujin.trends.ui.main;

import android.content.ContentValues;
import android.content.Context;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.sujin.trends.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class BookmarksListAdapter extends RecyclerView.Adapter<RepositoriesListAdapter.MyViewHolder>{

    private List<Repository> repositoryList = new ArrayList<>();
    DatabaseHelper databaseHelper;
    Context mContext;
    String userid;

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public View view;

        public MyViewHolder(View v) {
            super(v);
            view = v;
        }
    }

    public BookmarksListAdapter(List<Repository> names, Context context,String userid)
    {
        this.repositoryList = names;
        databaseHelper = new DatabaseHelper(context);
        this.mContext=context;
        this.userid=userid;
    }

    @NonNull
    @Override
    public RepositoriesListAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


        View v =  LayoutInflater.from(parent.getContext())
                .inflate(R.layout.repository_details, parent, false);
        RepositoriesListAdapter.MyViewHolder vh = new RepositoriesListAdapter.MyViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull RepositoriesListAdapter.MyViewHolder holder, final int position) {

        if(repositoryList!=null) {
            final TextView username_repository = holder.view.findViewById(R.id.username_repository);
            final TextView star = holder.view.findViewById(R.id.star);
            final TextView fork = holder.view.findViewById(R.id.fork);
            final TextView language = holder.view.findViewById(R.id.language);
            final TextView description = holder.view.findViewById(R.id.description);
            final ImageView dp = holder.view.findViewById(R.id.dp);
            username_repository.setText(repositoryList.get(position).getAuthor() + " / " + repositoryList.get(position).getName());
            star.setText(repositoryList.get(position).getStars().toString());
            fork.setText(repositoryList.get(position).getForks().toString());
            language.setText(repositoryList.get(position).getLanguage());
            description.setText(repositoryList.get(position).getDescription());
            Glide.with(dp.getContext())
                    .load(repositoryList.get(position).getAvatar())
                    .into(dp);
            final List<ImageView> contributors = new ArrayList<>();
            ImageView contributorpic1 = holder.view.findViewById(R.id.contributorpic1);
            ImageView contributorpic2 = holder.view.findViewById(R.id.contributorpic2);
            ImageView contributorpic3 = holder.view.findViewById(R.id.contributorpic3);
            ImageView contributorpic4 = holder.view.findViewById(R.id.contributorpic4);
            ImageView contributorpic5 = holder.view.findViewById(R.id.contributorpic5);
            contributors.add(contributorpic1);
            contributors.add(contributorpic2);
            contributors.add(contributorpic3);
            contributors.add(contributorpic4);
            contributors.add(contributorpic5);

            for(int i=0;i<repositoryList.get(position).getBuiltBy().size();i++)
            {
                Glide.with(dp.getContext())
                        .load(repositoryList.get(position).getBuiltBy().get(i).getAvatar())
                        .into(contributors.get(i));
            }
            final ImageView bookmarkpic = holder.view.findViewById(R.id.bookmarkpic);
            bookmarkpic.setImageResource(R.drawable.bookmark2);


            bookmarkpic.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                        databaseHelper.deleteData(repositoryList.get(position).getAuthor(),repositoryList.get(position).getName());


                    Retrofit retrofit = new Retrofit.Builder()
                            .baseUrl(Api.POST_URL)
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();
                    Api service = retrofit.create(Api.class);
                    Call<PostResult> call = service.sendBookmarkDeletion(new AddBookmark(userid,repositoryList.get(position)));
                    repositoryList.remove(position);
                    notifyItemRemoved(position);
                    notifyItemRangeChanged(position,repositoryList.size());
                    call.enqueue(new Callback<PostResult>() {
                        @Override
                        public void onResponse(Call<PostResult> call, Response<PostResult> response) {

                        }

                        @Override
                        public void onFailure(Call<PostResult> call, Throwable t) {

                            Toast.makeText(mContext, t.getMessage(), Toast.LENGTH_LONG).show();
                            Log.d("error", t.getMessage());
                        }
                    });




                }
            });
        }

    }

    @Override
    public int getItemCount() {
        if(repositoryList==null)
        {
            return 0;
        }
        return repositoryList.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }
}

