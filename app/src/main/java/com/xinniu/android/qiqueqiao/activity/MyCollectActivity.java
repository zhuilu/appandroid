package com.xinniu.android.qiqueqiao.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
//import android.support.v4.app.FragmentManager;
//import android.support.v4.app.FragmentPagerAdapter;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.xinniu.android.qiqueqiao.R;
import com.xinniu.android.qiqueqiao.base.BaseActivity;
import com.xinniu.android.qiqueqiao.base.LazyBaseFragment;
import com.xinniu.android.qiqueqiao.customs.ViewPager;
import com.xinniu.android.qiqueqiao.fragment.collect.ResourceCollectFragment;
import com.xinniu.android.qiqueqiao.fragment.collect.ServiceCollectFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by yuchance on 2018/5/30.
 */

public class MyCollectActivity extends BaseActivity {

    @BindView(R.id.bt_finish)
    RelativeLayout btFinish;
    @BindView(R.id.b_recommendtv)
    RadioButton bRecommendtv;
    @BindView(R.id.b_friendtv)
    RadioButton bFriendtv;
    @BindView(R.id.connection_rg)
    RadioGroup connectionRg;
    @BindView(R.id.mconnect_vp)
    ViewPager mconnectVp;
    List<LazyBaseFragment> fragmentList = new ArrayList<>();
    @Override
    public int getLayoutId() {
        return R.layout.activity_collect_my;
    }

    public static void start(Context context) {
        Intent starter = new Intent(context, MyCollectActivity.class);
        context.startActivity(starter);
    }


    @Override
    public void initViews(Bundle savedInstanceState) {
        topStatusBar(true);
        fragmentList.add(ResourceCollectFragment.newInstance());
        fragmentList.add(ServiceCollectFragment.newInstance());
        mconnectVp.setAdapter(new ConnectionAdapter(getSupportFragmentManager()));
        mconnectVp.setOffscreenPageLimit(2);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


    }

    @OnClick({R.id.bt_finish, R.id.b_recommendtv, R.id.b_friendtv})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_finish:
                finish();
                break;
            case R.id.b_recommendtv:
                mconnectVp.setCurrentItem(0);
                break;
            case R.id.b_friendtv:
                mconnectVp.setCurrentItem(1);
                break;
        }
    }

    class ConnectionAdapter extends FragmentPagerAdapter {

        public ConnectionAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return fragmentList.get(position);
        }

        @Override
        public int getCount() {
            return fragmentList.size();
        }
    }

}
