package com.sujin.trends.ui.main;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.sujin.trends.R;

import java.util.ArrayList;
import java.util.List;

public class RepositoriesFragment extends Fragment {



    private RecyclerView repositoriesList;
    private RecyclerView.Adapter repositoriesListAdapter;
    private RecyclerView.LayoutManager layoutManager;
    List<Repository> repositoryList,bookmarks;
    String userid;

    public RepositoriesFragment(List<Repository> repositoryList,List<Repository> bookmarks,String userid){
        this.repositoryList = repositoryList;
        this.bookmarks=bookmarks;
        this.userid =userid;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.repositories_list,container,false);
        repositoriesList = view.findViewById(R.id.repositories_list);
        repositoriesList.setHasFixedSize(true);


        // use a linear layout manager
        layoutManager = new LinearLayoutManager(getActivity());
        repositoriesList.setLayoutManager(layoutManager);

        // specify an adapter (see also next example)
        repositoriesListAdapter = new RepositoriesListAdapter(repositoryList,getContext(),bookmarks,userid);
        repositoriesList.setAdapter(repositoriesListAdapter);



        return view;



    }
}
