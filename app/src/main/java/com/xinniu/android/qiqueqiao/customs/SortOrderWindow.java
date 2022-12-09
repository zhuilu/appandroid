package com.xinniu.android.qiqueqiao.customs;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
//import android.support.v7.widget.GridLayoutManager;
//import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.xinniu.android.qiqueqiao.R;
import com.xinniu.android.qiqueqiao.adapter.ScreenOnlyAdapter;
import com.xinniu.android.qiqueqiao.adapter.SortOrderAdapter;
import com.xinniu.android.qiqueqiao.bean.SelectCategory;
import com.xinniu.android.qiqueqiao.utils.AnimationUtil;
import com.xinniu.android.qiqueqiao.utils.NoScrollRecyclerView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yuchance on 2018/8/31.
 */

public class SortOrderWindow extends PopupWindow implements PopupWindow.OnDismissListener {
    private Activity activity;
    private List<SelectCategory> datas = new ArrayList<>();
    private int mIndustryId = 0;
    private String mIndustryName = "";
    private View industryView;
    private NoScrollRecyclerView recyclerView;
    private RelativeLayout listRoot;
    public static boolean isClick;
    private RelativeLayout indusView;
    private int screenHeight;
    private String title;

    public SortOrderWindow(Activity context, List<SelectCategory> datas) {
        this.activity = context;
        this.datas = datas;
        initWindow();
    }


    private void initWindow(){
        //获取屏幕宽高
        WindowManager wm = (WindowManager) activity.getSystemService(Context.WINDOW_SERVICE);
        screenHeight = wm.getDefaultDisplay().getHeight();
        this.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        this.setHeight(screenHeight /2);
        industryView = LayoutInflater.from(activity).inflate(R.layout.window_sort_order,null);
        indusView = (RelativeLayout) industryView.findViewById(R.id.view_resource_Rl);
        indusView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dissPop();
            }
        });

        listRoot = (RelativeLayout) industryView.findViewById(R.id.view_resource_root);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        listRoot.setLayoutParams(params);
        recyclerView = (NoScrollRecyclerView) industryView.findViewById(R.id.item_recycler);

        LinearLayoutManager manager = new LinearLayoutManager(activity,LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(manager);
        SortOrderAdapter adapter = new SortOrderAdapter(R.layout.item_sort_order,datas);
        recyclerView.setAdapter(adapter);
        adapter.setSetItemData(new SortOrderAdapter.setItemData() {
            @Override
            public void onSetItemData(int id, String name) {
                mSetfinish.setToFinish(id,name);
            }
        });
        this.setContentView(industryView);
        this.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        this.setAnimationStyle(R.style.SelectPopupWindow);
        this.setOutsideTouchable(true);
        this.setFocusable(false);
        this.setOnDismissListener(this);

    }
    //弹窗消失事件
    public void dissPop(){
        if (this.isShowing()) {
            dismiss();
        }
    }

    @Override
    public void onDismiss() {
        dismissPopup();
    }

    private void dismissPopup() {
        if (!isClick){
//            if ()
//            for (SourceScreenBean.CompanyListBean company : ResouceHelper.getSourceScreenBean().getCompany_list()){
//                company.setCheck(false);
//            }
        }
        super.dismiss();
    }

    public interface setfinish{
        void setToFinish(int mId,String mName);
    }

    private setfinish mSetfinish;

    public void setmSetfinish(setfinish mSetfinish) {
        this.mSetfinish = mSetfinish;
    }
    //显示
    public void showPopupWindow(View parent) {
        if (!this.isShowing()) {
            this.showAsDropDown(parent);
            AnimationUtil.createAnimation(true,industryView,listRoot,null);
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
            AnimationUtil.createAnimation(true, industryView, listRoot, null);
            super.showAsDropDown(anchor);
        }else {
            dissPop();
        }
    }
}
