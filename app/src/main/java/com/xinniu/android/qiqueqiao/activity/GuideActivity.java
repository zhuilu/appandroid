package com.xinniu.android.qiqueqiao.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
//import android.support.v4.view.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.xinniu.android.qiqueqiao.MainActivity;
import com.xinniu.android.qiqueqiao.R;
import com.xinniu.android.qiqueqiao.base.BaseActivity;
import com.xinniu.android.qiqueqiao.user.UserInfoHelper;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 引导页
 * Created by BDXK on 2019/2/27.
 */

public class GuideActivity extends BaseActivity {
    @BindView(R.id.viewpage)
    ViewPager viewpager;
    @BindView(R.id.llayout_dot)
    LinearLayout llayoutDot;
    @BindView(R.id.btn_experience)
    TextView btnExperience;
    private int[] mImages = {R.mipmap.welcome1, R.mipmap.welcome2, R.mipmap.welcome3};
    private ArrayList<ImageView> mImageViews;//用来存放几个imageview的实例
    private ImageView[] mDotViews;//小圆点

    @Override
    public int getLayoutId() {
        return R.layout.activity_guide;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        topStatusBar(true);
        initImages();
        initDotView();

    }

    /**
     * 小圆点处理
     */
    private void initDotView() {
        LinearLayout.LayoutParams mParams = new LinearLayout.LayoutParams(20, 20);
        mParams.setMargins(10, 0, 10, 0);//设置小圆点左右之间的间隔
        mDotViews = new ImageView[mImages.length];
        //判断小圆点的数量，从0开始，0表示第一个
        for (int i = 0; i < mImageViews.size(); i++) {
            ImageView imageView = new ImageView(this);
            imageView.setLayoutParams(mParams);
            if (i == 0) {
                imageView.setImageResource(R.mipmap.cricle_blue);
            } else {
                imageView.setImageResource(R.mipmap.cricle_white);
            }
            mDotViews[i] = imageView;//得到每个小圆点的引用，用于滑动页面时，（onPageSelected方法中）更改它们的状态。
            llayoutDot.addView(imageView);//添加到布局里面显示
        }


    }

    /**
     * 初始化图片
     */
    private void initImages() {
        LinearLayout.LayoutParams mParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        mImageViews = new ArrayList<>();
        for (int i = 0; i < mImages.length; i++) {
            ImageView imageView = new ImageView(this);
            imageView.setLayoutParams(mParams);
            imageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
            imageView.setImageResource(mImages[i]);
            mImageViews.add(imageView);
        }
        viewpager.setAdapter(new MyViewPageAdapter(mImageViews));
        viewpager.setCurrentItem(0);
        viewpager.setOffscreenPageLimit(1);
        viewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                for (int i = 0; i < mDotViews.length; i++) {
                    if (i == position) {
                        mDotViews[i].setImageResource(R.mipmap.cricle_blue);
                    } else {
                        mDotViews[i].setImageResource(R.mipmap.cricle_white);
                    }
                }
                if (position + 1 == mImageViews.size()) {
                    btnExperience.setVisibility(View.VISIBLE);
                    llayoutDot.setVisibility(View.GONE);
                } else {
                    btnExperience.setVisibility(View.GONE);
                    llayoutDot.setVisibility(View.VISIBLE);
                }

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @OnClick(R.id.btn_experience)
    public void onViewClicked() {
        //立即体验
//        showBookingToast(2);
        UserInfoHelper.getIntance().setIsFrist(false);
        startActivity(new Intent(GuideActivity.this, MainActivity.class));

//        new Handler().postDelayed(new Runnable(){
//            public void run() {
//                //execute the task
//                dismissBookingToast();
//            }
//        }, 1000);
        finish();
    }

    private class MyViewPageAdapter extends PagerAdapter {
        private ArrayList<ImageView> imageViews;

        public MyViewPageAdapter(ArrayList<ImageView> imageViews) {
            this.imageViews = imageViews;
        }

        /**
         * 获取当前要显示对象的数量
         */
        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return imageViews.size();
        }

        /**
         * 判断是否用对象生成界面
         */
        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            // TODO Auto-generated method stub
            return arg0 == arg1;
        }

        /**
         * 从ViewGroup中移除当前对象（图片）
         */
        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(imageViews.get(position));
        }

        /**
         * 当前要显示的对象（图片）
         */
        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(imageViews.get(position));
            return imageViews.get(position);
        }

    }
}
