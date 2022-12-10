package com.xinniu.android.qiqueqiao.customs;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;

import com.xinniu.android.qiqueqiao.R;
import com.xinniu.android.qiqueqiao.adapter.SourceScreenThreeAdapter;
import com.xinniu.android.qiqueqiao.bean.SourceScreenBean;
import com.xinniu.android.qiqueqiao.utils.AnimationUtil;
import com.xinniu.android.qiqueqiao.utils.NoScrollRecyclerView;
import com.xinniu.android.qiqueqiao.utils.ResouceHelper;

import java.util.ArrayList;
import java.util.List;

//import android.support.v7.widget.GridLayoutManager;

/**
 * Created by yuchance on 2018/5/9.
 * 公司页面行业弹窗
 */

public class IndustryWindow extends PopupWindow implements PopupWindow.OnDismissListener{

    private AppCompatActivity activity;
    private List<SourceScreenBean.CompanyListBean> datas = new ArrayList<>();
    private int mIndustryId = 0;
    private String mIndustryName = "";
    private View industryView;
    private NoScrollRecyclerView recyclerView;
    private RelativeLayout listRoot;
    public static boolean isClick;
    private RelativeLayout indusView;
    private int screenHeight;
    private String title;

    public IndustryWindow(AppCompatActivity context, List<SourceScreenBean.CompanyListBean> datas, String title) {
        this.activity = context;
        this.datas = datas;
        this.title = title;
        initWindow();
    }

    private void initWindow(){
        //获取屏幕宽高
        WindowManager wm = (WindowManager) activity.getSystemService(Context.WINDOW_SERVICE);
        screenHeight = wm.getDefaultDisplay().getHeight();
        this.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        this.setHeight(screenHeight /2);
        industryView = LayoutInflater.from(activity).inflate(R.layout.view_industry_window,null);
        indusView = (RelativeLayout) industryView.findViewById(R.id.view_resource_Rl);
        indusView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dissPop();
            }
        });

        listRoot = (RelativeLayout) industryView.findViewById(R.id.view_resource_root);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,1*screenHeight/2);
        listRoot.setLayoutParams(params);
        recyclerView = (NoScrollRecyclerView) industryView.findViewById(R.id.item_recycler_screenthree);
        //确定按钮
        TextView sureTv = (TextView) industryView.findViewById(R.id.view_source_sureTv);
        //重置按钮
        TextView reTv = (TextView) industryView.findViewById(R.id.view_source_retagTv);
        TextView textView = (TextView) industryView.findViewById(R.id.tvWindow);
        textView.setText(title);
        GridLayoutManager manager = new GridLayoutManager(activity,4);
        recyclerView.setLayoutManager(manager);
        final SourceScreenThreeAdapter adapter = new SourceScreenThreeAdapter(activity,R.layout.item_source_screen_cell,datas);
        recyclerView.setAdapter(adapter);
        sureTv.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                isClick = true;
                for (int i = 0; i <datas.size() ; i++) {
                    if (datas.get(i).isCheck()){
                        mIndustryId = datas.get(i).getId();
                        mIndustryName = datas.get(i).getName();
                    }
                }

                mSetfinish.setToFinish(mIndustryId,mIndustryName);

                dissPop();
            }
        });
        reTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i = 0; i <datas.size() ; i++) {
                    datas.get(i).setCheck(false);
                    adapter.notifyDataSetChanged();
                }
                mIndustryName = "行业";
                mIndustryId = 0;
                isClick = false;
            }
        });
        this.setContentView(industryView);
        this.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        this.setAnimationStyle(R.style.SelectPopupWindow);
        this.setOutsideTouchable(true);
        this.setFocusable(true);
        this.setOnDismissListener(this);

    }
    //弹窗消失事件
    private void dissPop(){
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
            for (SourceScreenBean.CompanyListBean company : ResouceHelper.getSourceScreenBean().getCompany_list()){
                company.setCheck(false);
            }
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
            showAsDropDown(parent);
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
