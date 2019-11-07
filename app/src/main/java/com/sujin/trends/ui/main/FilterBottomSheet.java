package com.sujin.trends.ui.main;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.sujin.trends.R;

import java.util.ArrayList;
import java.util.List;

public class FilterBottomSheet extends BottomSheetDialogFragment {

    List<String> lang;
    applyButtonClickListener mCallback;

    public interface applyButtonClickListener
    {
        void onLanguageSelected(int position);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            mCallback = (applyButtonClickListener) context;
        }catch (Exception e)
        {
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    public FilterBottomSheet(List<String> languages)
    {
        this.lang = languages;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.filter_layout, container,
                false);

        if(lang!=null) {
            final Spinner dropDownList = view.findViewById(R.id.drop_down_list);
            ArrayAdapter adapter = new ArrayAdapter(getContext(),android.R.layout.simple_spinner_dropdown_item,lang);
            dropDownList.setAdapter(adapter);
            Button applyButton = view.findViewById(R.id.apply_button);
            applyButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mCallback.onLanguageSelected(dropDownList.getSelectedItemPosition());
                }
            });

        }

        // get the views and attach the listener

        return view;

    }
}