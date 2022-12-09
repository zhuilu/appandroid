package com.xinniu.android.qiqueqiao.customs;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
//import android.support.annotation.Nullable;
//import android.support.v7.widget.GridLayoutManager;
//import android.support.v7.widget.LinearLayoutManager;
//import androidx.recyclerview.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xinniu.android.qiqueqiao.R;
import com.xinniu.android.qiqueqiao.adapter.LeftCityAdapter;
import com.xinniu.android.qiqueqiao.adapter.RightCityAdapter;
import com.xinniu.android.qiqueqiao.bean.CityV2Bean;
import com.xinniu.android.qiqueqiao.utils.AnimationUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yuchance on 2018/7/31.
 */

public class CityNewWindow extends PopupWindow {

    private Activity activity;
    private int screenHeight;
    private List<CityV2Bean> leftCityList = new ArrayList<>();
    private List<CityV2Bean.ZlistBean> rightCityList = new ArrayList<>();
    private View cityView;
    private LinearLayout theLl;
    private RecyclerView leftRecycler;
    private RecyclerView rightRecycler;
    private int xpostion = 0;


    public CityNewWindow(Activity context,List<CityV2Bean> leftCityList,int postion) {
       this.activity = context;
       this.leftCityList = leftCityList;
       this.xpostion = postion;
        initView();
    }

    private void initView() {
        //获取屏幕宽高
        WindowManager wm = (WindowManager) activity.getSystemService(Context.WINDOW_SERVICE);
        screenHeight = wm.getDefaultDisplay().getHeight();
        this.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        this.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
        cityView = LayoutInflater.from(activity).inflate(R.layout.view_citynew_window,null);
        RelativeLayout theView = (RelativeLayout) cityView.findViewById(R.id.view_city_window);
        theView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dissPop();
            }
        });
        theLl = (LinearLayout) cityView.findViewById(R.id.citynewLl);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,1*screenHeight/2);
        theLl.setLayoutParams(params);
        leftRecycler = (RecyclerView) cityView.findViewById(R.id.city_left_recycler);
        rightRecycler = (RecyclerView) cityView.findViewById(R.id.city_right_recycler);
        LinearLayoutManager leftManager = new LinearLayoutManager(activity,LinearLayoutManager.VERTICAL,false);
        GridLayoutManager rightManager = new GridLayoutManager(activity,2);
        leftRecycler.setLayoutManager(leftManager);
        rightRecycler.setLayoutManager(rightManager);
        leftCityList.get(xpostion).setCheck(true);
        final LeftCityAdapter leftAdapter = new LeftCityAdapter(R.layout.item_city_left,leftCityList);
        rightCityList = leftCityList.get(xpostion).getZlist();
        final RightCityAdapter rightAdapter = new RightCityAdapter(R.layout.item_city_right,rightCityList,leftCityList);
        leftRecycler.setAdapter(leftAdapter);
        rightRecycler.setAdapter(rightAdapter);
        leftAdapter.setRightList(new LeftCityAdapter.setRightList() {
            @Override
            public void setRightPostion(int position) {
                xpostion = position;
               rightCityList = leftCityList.get(xpostion).getZlist();
                final RightCityAdapter rightAdapter = new RightCityAdapter(R.layout.item_city_right,rightCityList,leftCityList);
                rightRecycler.setAdapter(rightAdapter);
                rightAdapter.getSetRightPostion(new RightCityAdapter.getRightPostion() {
                    @Override
                    public void setRightPostion(int postion,String cityName) {
                        setCityIdAndPostion.getCityIdandPostion(xpostion,postion,cityName);
                        dissPop();
                    }
                });
            }
        });






        this.setContentView(cityView);//设置布局
        this.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        this.setAnimationStyle(R.style.SelectPopupWindow);
        this.setOutsideTouchable(true);
        this.setFocusable(true);

    }
    //显示
    public void showPopupWindow(View parent) {
        if (!this.isShowing()) {
            this.showAsDropDown(parent, Gravity.CENTER,0,0);
//            this.showAsDropDown(parent);
            AnimationUtil.createAnimation(true,theLl,cityView,null);
        } else {
            dissPop();
        }
    }
    public void showPopupWindowx(View parent) {
        if (!this.isShowing()) {
            if(Build.VERSION.SDK_INT == 24) {
                Rect rect = new Rect();
                parent.getGlobalVisibleRect(rect);
                int h = parent.getResources().getDisplayMetrics().heightPixels - rect.bottom;
                setHeight(h);
            }
//            int windowPos[] = ComUtils.calculatePopWindowPos(parent, contentView);
            this.showAsDropDown(parent);
            AnimationUtil.createAnimation(true,cityView,leftRecycler,null);
        } else {
            dissPop();
        }
    }



    @Override
    public void showAsDropDown(View anchor) {
        if (!this.isShowing()) {
            if (Build.VERSION.SDK_INT >= 24) {
                Rect visibleFrame = new Rect();
                anchor.getGlobalVisibleRect(visibleFrame);
                int height = anchor.getResources().getDisplayMetrics().heightPixels - visibleFrame.bottom;
                setHeight(height);
            }
            AnimationUtil.createAnimation(true, cityView, theLl, null);
            super.showAsDropDown(anchor);
        }else {
            dissPop();
        }
    }

    private void dissPop() {
        if (this.isShowing()) {
            dismiss();
        }
    }
    public interface getCityIdAndPostion{
        void getCityIdandPostion(int leftPostion,int cityId,String cityName);
    }
    private getCityIdAndPostion setCityIdAndPostion;

    public void setSetCityIdAndPostion(getCityIdAndPostion setCityIdAndPostion) {
        this.setCityIdAndPostion = setCityIdAndPostion;
    }
}
