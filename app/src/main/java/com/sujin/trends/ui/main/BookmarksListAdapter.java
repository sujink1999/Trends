package com.sujin.trends.ui.main;

import android.content.ContentValues;
import android.content.Context;
import android.provider.ContactsContract;
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

public class BookmarksListAdapter extends RecyclerView.Adapter<RepositoriesListAdapter.MyViewHolder>{

    private List<Repository> repositoryList = new ArrayList<>();
    DatabaseHelper databaseHelper;
    Context mContext;

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public View view;

        public MyViewHolder(View v) {
            super(v);
            view = v;
        }
    }

    public BookmarksListAdapter(List<Repository> names, Context context)
    {
        this.repositoryList = names;
        databaseHelper = new DatabaseHelper(context);
        this.mContext=context;
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


                    /*bookmarkpic.setImageResource(R.drawable.bookmark1);*/
                        databaseHelper.deleteData(repositoryList.get(position).getAuthor(),repositoryList.get(position).getName());
                        repositoryList.remove(position);
                        notifyItemRemoved(position);
                        notifyItemRangeChanged(position,repositoryList.size());


                    Toast.makeText(mContext, "Bookmark removed", Toast.LENGTH_SHORT).show();

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

