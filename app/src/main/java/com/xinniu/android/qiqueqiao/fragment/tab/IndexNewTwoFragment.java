package com.xinniu.android.qiqueqiao.fragment.tab;

import android.content.Intent;
import android.os.Bundle;
//import android.support.v4.app.Fragment;
//import androidx.viewpager.widget.ViewPager;
import android.text.TextPaint;
import android.util.TypedValue;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.umeng.analytics.MobclickAgent;
import com.xinniu.android.qiqueqiao.R;
import com.xinniu.android.qiqueqiao.activity.LoginNewActivity;
import com.xinniu.android.qiqueqiao.activity.SreachActivity;
import com.xinniu.android.qiqueqiao.adapter.MainFragmentPagerAdapter;
import com.xinniu.android.qiqueqiao.base.LazyBaseFragment;
import com.xinniu.android.qiqueqiao.fragment.maintab.BusinessActivitiesFragment;
import com.xinniu.android.qiqueqiao.fragment.maintab.ClassRoomFragment;
import com.xinniu.android.qiqueqiao.fragment.maintab.ResourceFragment;
import com.xinniu.android.qiqueqiao.fragment.maintab.RewardFragment;
import com.xinniu.android.qiqueqiao.fragment.maintab.ServiceFragment;
import com.xinniu.android.qiqueqiao.user.UserInfoHelper;
import com.xinniu.android.qiqueqiao.utils.TokePhotoUtils;
import com.xinniu.android.qiqueqiao.zxing.activity.CaptureActivity;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

public class IndexNewTwoFragment extends LazyBaseFragment {
    @BindView(R.id.yindex_search)
    RelativeLayout yindexSearch;
    @BindView(R.id.r_search)
    RelativeLayout rSearch;
    @BindView(R.id.tv_tab_01)
    TextView tvTab01;
    @BindView(R.id.view_tab_01)
    View viewTab01;
    @BindView(R.id.rlayout_tab_01)
    RelativeLayout rlayoutTab01;
    @BindView(R.id.tv_tab_02)
    TextView tvTab02;
    @BindView(R.id.view_tab_02)
    View viewTab02;
    @BindView(R.id.rlayout_tab_02)
    RelativeLayout rlayoutTab02;
    @BindView(R.id.tv_tab_03)
    TextView tvTab03;
    @BindView(R.id.view_tab_03)
    View viewTab03;
    @BindView(R.id.rlayout_tab_03)
    RelativeLayout rlayoutTab03;
    @BindView(R.id.tv_tab_04)
    TextView tvTab04;
    @BindView(R.id.view_tab_04)
    View viewTab04;
    @BindView(R.id.rlayout_tab_04)
    RelativeLayout rlayoutTab04;
    @BindView(R.id.tv_tab_05)
    TextView tvTab05;
    @BindView(R.id.view_tab_05)
    View viewTab05;
    @BindView(R.id.rlayout_tab_05)
    RelativeLayout rlayoutTab05;
    @BindView(R.id.hs)
    HorizontalScrollView hs;

    @BindView(R.id.viewPager)
    ViewPager viewPager;

    //打开扫描界面请求码
    public final static int REQUEST_CODE = 0x01;
    //扫描成功返回码
    public final static int RESULT_OK = 0xA1;
    @BindView(R.id.ll_tab)
    LinearLayout llTab;

    private int mCurrent = 1;
    private ResourceFragment resourceFragment;//合作资源
    private ServiceFragment serviceFragment;
    private RewardFragment rewardFragment;
    private BusinessActivitiesFragment businessActivitiesFragment;
    private ClassRoomFragment classRoomFragment;
    private ArrayList<Fragment> fragments = new ArrayList<>();

    public static IndexNewTwoFragment newInstance() {
        Bundle args = new Bundle();
        IndexNewTwoFragment fragment = new IndexNewTwoFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_index_new_two;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        resourceFragment = new ResourceFragment();
        serviceFragment = new ServiceFragment();
        rewardFragment = new RewardFragment();
        businessActivitiesFragment = new BusinessActivitiesFragment();
        classRoomFragment = new ClassRoomFragment();
        fragments.add(resourceFragment);
        fragments.add(rewardFragment);
        fragments.add(businessActivitiesFragment);
        fragments.add(serviceFragment);
        fragments.add(classRoomFragment);
        viewPager.setAdapter(new MainFragmentPagerAdapter(getChildFragmentManager(), fragments));
        viewPager.setOffscreenPageLimit(5);
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position == 0) {
                    createView01();
                } else if (position == 1) {
                    createView03();
                } else if (position == 2) {
                    createView04();
                } else if (position == 3) {
                    createView02();
                } else if (position == 4) {
                    createView05();
                }
                viewPager.setCurrentItem(position, false);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        createView01();
        viewPager.setCurrentItem(0, false);


    }

    @Override
    protected void lazyLoad() {

    }


    @OnClick({R.id.qrBt, R.id.sreach_content_et, R.id.sreach_content_ll, R.id.rlayout_tab_01, R.id.rlayout_tab_02, R.id.rlayout_tab_03, R.id.rlayout_tab_04, R.id.rlayout_tab_05})
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.qrBt:
                if (!UserInfoHelper.getIntance().isLogin()) {
                    LoginNewActivity.start(mContext);
                } else {
                    //二维码
                    requestPermission();
                }
                break;
            case R.id.sreach_content_et:
                MobclickAgent.onEvent(mContext, "home_searchbar");//统计搜索点击次数
                Intent intent1 = new Intent(getActivity(), SreachActivity.class);
                startActivity(intent1);
                break;
            case R.id.sreach_content_ll:
                MobclickAgent.onEvent(mContext, "home_searchbar");//统计搜索点击次数
                Intent intent = new Intent(getActivity(), SreachActivity.class);
                startActivity(intent);
                break;
            case R.id.rlayout_tab_01:
                hs.smoothScrollBy (0, 0);
                mCurrent = 1;
                createView01();
                viewPager.setCurrentItem(0, false);
                break;
            case R.id.rlayout_tab_02:
                mCurrent = 4;
                createView02();
                viewPager.setCurrentItem(3, false);
                break;
            case R.id.rlayout_tab_03:
                mCurrent = 2;
                createView03();
                viewPager.setCurrentItem(1, false);
                break;
            case R.id.rlayout_tab_04:
                mCurrent = 3;
                createView04();
                viewPager.setCurrentItem(2, false);
                break;
            case R.id.rlayout_tab_05:
                int x=llTab.getMeasuredWidth();
                hs.smoothScrollBy (x, 0);
                mCurrent = 5;
                createView05();
                viewPager.setCurrentItem(4, false);
                break;

        }
    }

    /**
     * 合作信息布局
     */
    private void createView01() {
        clearAllStyle();
        TextPaint tp01 = tvTab01.getPaint();
        tp01.setFakeBoldText(true);
        tvTab01.setTextColor(getResources().getColor(R.color._2e82ea));
        viewTab01.setVisibility(View.VISIBLE);

    }

    /**
     * 企业服务布局
     */
    private void createView02() {
        clearAllStyle();
        TextPaint tp02 = tvTab02.getPaint();
        tp02.setFakeBoldText(true);
        tvTab02.setTextColor(getResources().getColor(R.color._2e82ea));
        viewTab02.setVisibility(View.VISIBLE);


    }

    /**
     * 悬赏求助布局
     */
    private void createView03() {
        clearAllStyle();
        TextPaint tp03 = tvTab03.getPaint();
        tp03.setFakeBoldText(true);
        tvTab03.setTextColor(getResources().getColor(R.color._2e82ea));
        viewTab03.setVisibility(View.VISIBLE);


    }

    /**
     * 商务活动布局
     */
    private void createView04() {
        clearAllStyle();
        TextPaint tp04 = tvTab04.getPaint();
        tp04.setFakeBoldText(true);
        tvTab04.setTextColor(getResources().getColor(R.color._2e82ea));
        viewTab04.setVisibility(View.VISIBLE);


    }


    /**
     * 异业课堂布局
     */
    private void createView05() {
        clearAllStyle();
        TextPaint tp05 = tvTab05.getPaint();
        tp05.setFakeBoldText(true);
        tvTab05.setTextColor(getResources().getColor(R.color._2e82ea));
        viewTab05.setVisibility(View.VISIBLE);

    }

    /**
     * 清除选中样式
     */
    private void clearAllStyle() {
        TextPaint tp01 = tvTab01.getPaint();
        tp01.setFakeBoldText(false);
        tvTab01.setTextColor(getResources().getColor(R.color._666));
        viewTab01.setVisibility(View.INVISIBLE);

        TextPaint tp02 = tvTab02.getPaint();
        tp02.setFakeBoldText(false);
        tvTab02.setTextColor(getResources().getColor(R.color._666));
        viewTab02.setVisibility(View.INVISIBLE);

        TextPaint tp03 = tvTab03.getPaint();
        tp03.setFakeBoldText(false);
        tvTab03.setTextColor(getResources().getColor(R.color._666));
        viewTab03.setVisibility(View.INVISIBLE);

        TextPaint tp04 = tvTab04.getPaint();
        tp04.setFakeBoldText(false);
        tvTab04.setTextColor(getResources().getColor(R.color._666));
        viewTab04.setVisibility(View.INVISIBLE);

        TextPaint tp05 = tvTab05.getPaint();
        tp05.setFakeBoldText(false);
        tvTab05.setTextColor(getResources().getColor(R.color._666));
        viewTab05.setVisibility(View.INVISIBLE);
    }

    @AfterPermissionGranted(TokePhotoUtils.PERMISSION_TOKE_PHOTO)
    public void requestPermission() {
        if (EasyPermissions.hasPermissions(getContext(), TokePhotoUtils.TOKE_PHOTO)) {
            Intent intent2 = new Intent(getActivity(), CaptureActivity.class);
            getActivity().startActivityForResult(intent2, REQUEST_CODE);
        } else {
            EasyPermissions.requestPermissions(
                    this,
                    getString(R.string.permission_need_toke_photo),
                    TokePhotoUtils.PERMISSION_TOKE_PHOTO, TokePhotoUtils.TOKE_PHOTO);
        }
    }


}
