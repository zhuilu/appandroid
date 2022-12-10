package com.xinniu.android.qiqueqiao.activity;

import android.content.Context;
import android.content.Intent;

import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.xinniu.android.qiqueqiao.R;
import com.xinniu.android.qiqueqiao.base.BaseActivity;
import com.xinniu.android.qiqueqiao.base.LazyBaseFragment;
import com.xinniu.android.qiqueqiao.customs.ViewPager;
import com.xinniu.android.qiqueqiao.fragment.connect.KnowDoFragment;
import com.xinniu.android.qiqueqiao.fragment.connect.RecommendFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 消息-人脉
 * Created by yuchance on 2019/2/12.
 */

public class ConnectionActivity extends BaseActivity {
    @BindView(R.id.b_recommendtv)
    RadioButton bRecommendtv;
    @BindView(R.id.b_friendtv)
    RadioButton bFriendtv;
    @BindView(R.id.mconnect_vp)
    ViewPager mconnectVp;

    List<LazyBaseFragment> fragmentList = new ArrayList<>();

    public static void start(Context context) {
        Intent intent = new Intent(context, ConnectionActivity.class);
        context.startActivity(intent);
    }


    @Override
    public int getLayoutId() {
        return R.layout.activity_connection;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        topStatusBar(true);
        fragmentList.add(RecommendFragment.newInstance());
        fragmentList.add(KnowDoFragment.newInstance());
        mconnectVp.setAdapter(new ConnectionAdapter(getSupportFragmentManager()));
        mconnectVp.setOffscreenPageLimit(2);


    }


    @OnClick({R.id.bt_finish, R.id.b_recommendtv, R.id.b_friendtv})
    public void onViewClicked(View view) {
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
