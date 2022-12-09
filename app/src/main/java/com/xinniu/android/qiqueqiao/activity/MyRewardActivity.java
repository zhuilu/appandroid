package com.xinniu.android.qiqueqiao.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
//import android.support.v4.app.Fragment;
//import android.support.v4.app.FragmentManager;
//import android.support.v4.app.FragmentPagerAdapter;
import android.view.KeyEvent;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;

import com.xinniu.android.qiqueqiao.R;
import com.xinniu.android.qiqueqiao.base.BaseActivity;
import com.xinniu.android.qiqueqiao.base.LazyBaseFragment;
import com.xinniu.android.qiqueqiao.customs.ViewPager;
import com.xinniu.android.qiqueqiao.fragment.push.ResourcePushFragment;
import com.xinniu.android.qiqueqiao.fragment.push.ServicePushFragment;
import com.xinniu.android.qiqueqiao.fragment.reward.PublicRewardFragment;
import com.xinniu.android.qiqueqiao.fragment.reward.TakeRewardFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

import static com.xinniu.android.qiqueqiao.fragment.push.ResourcePushFragment.MYPUSHCODE;
import static com.xinniu.android.qiqueqiao.fragment.push.ResourcePushFragment.MYPUSHCODETWO;


/**
 * Created by qinlei
 * Created on 2017/12/19
 * Created description :
 */

public class MyRewardActivity extends BaseActivity {
    @BindView(R.id.b_recommendtv)
    RadioButton bRecommendtv;
    @BindView(R.id.b_friendtv)
    RadioButton bFriendtv;
    @BindView(R.id.connection_rg)
    RadioGroup connectionRg;
    @BindView(R.id.mconnect_vp)
    ViewPager mconnectVp;
    List<LazyBaseFragment> fragmentList = new ArrayList<>();

    public static void start(Context context) {
        Intent starter = new Intent(context, MyRewardActivity.class);
        context.startActivity(starter);
    }


    @Override
    public int getLayoutId() {
        return R.layout.activity_my_reward;
    }


    @Override
    public void initViews(Bundle savedInstanceState) {
        topStatusBar(true);
        fragmentList.add(TakeRewardFragment.newInstance());
        fragmentList.add(PublicRewardFragment.newInstance());
        mconnectVp.setAdapter(new ConnectionAdapter(getSupportFragmentManager()));
        mconnectVp.setOffscreenPageLimit(2);
    }

    @OnClick({R.id.b_recommendtv, R.id.b_friendtv, R.id.bt_finish})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.b_recommendtv:
                mconnectVp.setCurrentItem(0);
                break;
            case R.id.b_friendtv:
                mconnectVp.setCurrentItem(1);
                break;
            case R.id.bt_finish:
                finish();
                break;
            default:
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
