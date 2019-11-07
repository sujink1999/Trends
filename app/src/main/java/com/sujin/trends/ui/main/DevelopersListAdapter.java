package com.sujin.trends.ui.main;

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

public class DevelopersListAdapter extends RecyclerView.Adapter<DevelopersListAdapter.DevelopersViewHolder>{

    private List<Developer> developers;

    public static class DevelopersViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public View view;

        public DevelopersViewHolder(View v) {
            super(v);
            view = v;
        }
    }

    public DevelopersListAdapter(List<Developer> developers)
    {
        this.developers = developers;
    }

    @NonNull
    @Override
    public DevelopersListAdapter.DevelopersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


        View v =  LayoutInflater.from(parent.getContext())
                .inflate(R.layout.developers_details, parent, false);
        DevelopersViewHolder vh = new DevelopersViewHolder(v);


        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull DevelopersListAdapter.DevelopersViewHolder holder, int position) {

        TextView name= holder.view.findViewById(R.id.name);
        TextView repositoryName= holder.view.findViewById(R.id.repositoryName);
        TextView repoDescription= holder.view.findViewById(R.id.repoDescription);
        ImageView profile = holder.view.findViewById(R.id.profile);

        name.setText(developers.get(position).getUsername()+" - "+developers.get(position).getName());
        repositoryName.setText(developers.get(position).getRepo().getName());
        repoDescription.setText(developers.get(position).getRepo().getDescription());
        Glide.with(profile.getContext())
                .load(developers.get(position).getAvatar())
                .into(profile);


    }

    @Override
    public int getItemCount() {

        if(developers==null)
            return 0;
        return developers.size();
    }
}