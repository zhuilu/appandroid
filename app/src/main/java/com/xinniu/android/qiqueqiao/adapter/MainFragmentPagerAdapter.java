package com.xinniu.android.qiqueqiao.adapter;

import android.content.Context;
import androidx.fragment.app.Fragment;
//import android.support.v4.app.FragmentManager;
//import android.support.v4.app.FragmentPagerAdapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainFragmentPagerAdapter extends FragmentPagerAdapter {

    private ArrayList<Fragment> fragmentsList;

    public MainFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    public MainFragmentPagerAdapter(FragmentManager fm, ArrayList<Fragment> fragments) {
        super(fm);
        this.fragmentsList = fragments;
    }

    @Override
    public int getCount() {
        return fragmentsList.size();
    }

    @Override
    public Fragment getItem(int arg0) {
        return fragmentsList.get(arg0);
    }


    private List<String> titles = new ArrayList<>();

    public MainFragmentPagerAdapter(FragmentManager fm, Context mContext, ArrayList<Fragment> fragments, int resId) {
        super(fm);
        this.fragmentsList = fragments;
        titles = new ArrayList<>(Arrays.asList(mContext.getResources().getStringArray(resId)));
    }

    public MainFragmentPagerAdapter(FragmentManager fm, ArrayList<Fragment> fragments, List<String> titles) {
        super(fm);
        this.fragmentsList = fragments;
        this.titles = titles;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles.get(position);
    }
}

