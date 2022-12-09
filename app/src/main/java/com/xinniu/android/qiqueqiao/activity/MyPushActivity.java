package com.xinniu.android.qiqueqiao.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
//import android.support.v4.app.FragmentManager;
//import android.support.v4.app.FragmentPagerAdapter;
import android.view.KeyEvent;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;

import com.google.gson.Gson;
import com.xinniu.android.qiqueqiao.R;
import com.xinniu.android.qiqueqiao.base.BaseActivity;
import com.xinniu.android.qiqueqiao.base.LazyBaseFragment;
import com.xinniu.android.qiqueqiao.bean.AppointmentBean;
import com.xinniu.android.qiqueqiao.customs.ViewPager;
import com.xinniu.android.qiqueqiao.fragment.push.ResourcePushFragment;
import com.xinniu.android.qiqueqiao.fragment.push.ServicePushFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

import static com.xinniu.android.qiqueqiao.fragment.push.ResourcePushFragment.MYPUSHCODE;
import static com.xinniu.android.qiqueqiao.fragment.push.ResourcePushFragment.MYPUSHCODETHREE;
import static com.xinniu.android.qiqueqiao.fragment.push.ResourcePushFragment.MYPUSHCODETWO;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;


/**
 * Created by qinlei
 * Created on 2017/12/19
 * Created description :
 */

public class MyPushActivity extends BaseActivity {
    public static int RESULTCODE = 501;
    public static int REQUESTCODE = 2019;
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
    ResourcePushFragment resourcePushFragment;
    ServicePushFragment servicePushFragment;

    public static void start(Context context) {
        Intent starter = new Intent(context, MyPushActivity.class);
        context.startActivity(starter);
    }

    public static void start(Activity context, int requestCode) {
        Intent starter = new Intent(context, MyPushActivity.class);
        context.startActivityForResult(starter, requestCode);
    }


    @Override
    public int getLayoutId() {
        return R.layout.activity_my_push;
    }


    @Override
    public void initViews(Bundle savedInstanceState) {
        topStatusBar(true);
        btFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setResult(RESULTCODE);
                finish();
            }
        });
        resourcePushFragment = ResourcePushFragment.newInstance();
        fragmentList.add(resourcePushFragment);
        servicePushFragment=ServicePushFragment.newInstance();
        fragmentList.add(servicePushFragment);
        mconnectVp.setAdapter(new ConnectionAdapter(getSupportFragmentManager()));
        mconnectVp.setOffscreenPageLimit(2);
    }

    @OnClick({R.id.b_recommendtv, R.id.b_friendtv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
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

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            //跳转至登陆
            setResult(RESULTCODE);
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUESTCODE && resultCode == CoopDetailActivity.REQUESTREFRESH) {
            if (resourcePushFragment != null) {
                resourcePushFragment.refresh();
            }
        }
        if (requestCode == MYPUSHCODE && resultCode == Activity.RESULT_OK) {
            if (resourcePushFragment != null) {
                resourcePushFragment.refresh();
            }
            if (servicePushFragment != null) {
                servicePushFragment.refresh();
            }
        }

        if (requestCode == MYPUSHCODETWO && resultCode == Activity.RESULT_OK) {
            if (resourcePushFragment != null) {
                resourcePushFragment.refresh();
            }
            if (servicePushFragment != null) {
                servicePushFragment.refresh();
            }
        }


        if (requestCode == MYPUSHCODETHREE && resultCode == Activity.RESULT_OK) {
            if (resourcePushFragment != null) {
                String originData = data.getStringExtra("data");
                int p = data.getIntExtra("position", -1);
                Gson gson = new Gson();
                AppointmentBean appointmentBean = gson.fromJson(originData, AppointmentBean.class);
                resourcePushFragment.refreshTwo(appointmentBean.getFixed_top_card_num(), appointmentBean.getReservation_time(), p);
            }

        }
    }
}
