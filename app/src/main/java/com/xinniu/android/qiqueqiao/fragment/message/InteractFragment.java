package com.xinniu.android.qiqueqiao.fragment.message;

import android.os.Bundle;
//import com.google.android.material.tabs.TabLayout;
//import android.support.v4.app.Fragment;
//import androidx.viewpager.widget.ViewPager;
import android.util.Log;

import com.xinniu.android.qiqueqiao.R;
import com.xinniu.android.qiqueqiao.adapter.MainFragmentPagerAdapter;
import com.xinniu.android.qiqueqiao.base.LazyBaseFragment;
import com.xinniu.android.qiqueqiao.fragment.message.interact.CareFragment;
import com.xinniu.android.qiqueqiao.fragment.message.interact.NewResourceFragment;
import com.xinniu.android.qiqueqiao.fragment.message.interact.SeeMeFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by lzq on 2017/12/9.
 */

public class InteractFragment extends LazyBaseFragment{
    @BindView(R.id.tabLayout)
    TabLayout tabLayout;
    @BindView(R.id.viewPager)
    ViewPager viewPager;

    private ArrayList<Fragment> fragmentList = new ArrayList<>();
    private List<String> titleList = new ArrayList<>();
    private TalkListFragment talkListFragment;
    private NewResourceFragment resourceFragment;
    private CareFragment careFragment;
    private SeeMeFragment seeMeFragment;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_message_interact;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        resourceFragment = new NewResourceFragment();
        careFragment = new CareFragment();
        seeMeFragment = new SeeMeFragment();
        fragmentList.add(resourceFragment);
        fragmentList.add(careFragment);
        fragmentList.add(seeMeFragment);
        titleList.add("推荐资源");
        titleList.add("关注");
        titleList.add("看过我");
        viewPager.setOffscreenPageLimit(2);
        viewPager.setAdapter(new MainFragmentPagerAdapter(getChildFragmentManager(),fragmentList,titleList));
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    protected void lazyLoad() {
        Log.i("---MessageFragment"," lazyLoad()");
    }

}
