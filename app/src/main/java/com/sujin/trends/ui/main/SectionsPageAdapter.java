package com.sujin.trends.ui.main;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentStatePagerAdapter;


import java.util.ArrayList;
import java.util.List;


public class SectionsPageAdapter extends FragmentStatePagerAdapter {

    private  List<Fragment> fragments = new ArrayList<>();
    private  List<String> titles = new ArrayList<>();
    public SectionsPageAdapter(FragmentManager fm) {
        super(fm);
    }


    public void addFragment(Fragment fragment,String title)
    {
        fragments.add(fragment);
        titles.add(title);
    }

    public void removeFragments()
    {
        fragments.clear();
        titles.clear();

    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return titles.get(position);
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return titles.size();
    }
}
