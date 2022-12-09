package com.xinniu.android.qiqueqiao.customs;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
//import android.support.v4.content.ContextCompat;
//import android.support.v7.widget.GridLayoutManager;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.xinniu.android.qiqueqiao.R;
import com.xinniu.android.qiqueqiao.adapter.SourceScreenThreeAdapter;
import com.xinniu.android.qiqueqiao.adapter.SourceScreentwoAdapter;
import com.xinniu.android.qiqueqiao.bean.SourceScreenBean;
import com.xinniu.android.qiqueqiao.utils.AnimationUtil;
import com.xinniu.android.qiqueqiao.utils.NoScrollRecyclerView;
import com.xinniu.android.qiqueqiao.utils.ResouceHelper;

/**
 * Created by yuchance on 2018/4/13.
 */

public class SourceScreenWindow extends PopupWindow implements PopupWindow.OnDismissListener {

    private Activity activity;
    private View screenView;
    private RelativeLayout listRoot;
    private SourceScreenBean datas;
    private NoScrollRecyclerView recyclerOne,recyclerTwo,recyclerThree;
    public static boolean isClickBtn;
    private RelativeLayout viewRl;
    private String offerneedStr = "3";
    private TextView offerTv;
    private TextView needTv;
    private TextView offerneed;


    public SourceScreenWindow(Activity activity, SourceScreenBean list) {
        this.activity = activity;
        this.datas = list;
        initWindow();
    }

    private void initWindow() {
        //获取屏幕宽高
        WindowManager wm = (WindowManager) activity.getSystemService(Context.WINDOW_SERVICE);
        int screenHeight = wm.getDefaultDisplay().getHeight();
        this.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        this.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
        screenView = LayoutInflater.from(activity).inflate(R.layout.view_source_screen,null);
        viewRl = (RelativeLayout) screenView.findViewById(R.id.view_source_Rl);
        viewRl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismissPopup();
            }
        });
        listRoot = (RelativeLayout) screenView.findViewById(R.id.view_source_screen_Rl);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,2*screenHeight/3);
        listRoot.setLayoutParams(params);

        recyclerTwo = (NoScrollRecyclerView) screenView.findViewById(R.id.item_recycler_screentwo);
        recyclerThree = (NoScrollRecyclerView) screenView.findViewById(R.id.item_recycler_screenthree);
        GridLayoutManager manager1 = new GridLayoutManager(activity,4);
        recyclerTwo.setLayoutManager(manager1);
        GridLayoutManager manager2 = new GridLayoutManager(activity,4);
        recyclerThree.setLayoutManager(manager2);
        final SourceScreentwoAdapter adapter1 = new SourceScreentwoAdapter(activity,R.layout.item_source_screen_cell,datas.getCategory_list());
        recyclerTwo.setAdapter(adapter1);
        final SourceScreenThreeAdapter adapter2 = new SourceScreenThreeAdapter(activity,R.layout.item_source_screen_cell,datas.getCompany_list());
        recyclerThree.setAdapter(adapter2);
        offerTv = (TextView) screenView.findViewById(R.id.view_source_offersource);
        needTv = (TextView) screenView.findViewById(R.id.view_source_needsource);
        offerneed = (TextView) screenView.findViewById(R.id.view_source_offerneed);
        if (!TextUtils.isEmpty(datas.getOfferneed())){
            if (datas.getOfferneed().equals("1")){
                offerText();
            }else if (datas.getOfferneed().equals("2")){
                needText();
            }else if (datas.getOfferneed().equals("3")){
                offerneedText();
            }
        }
        offerTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               offerText();
                datas.setOfferneed("1");
                offerneedStr = "1";
            }
        });
        needTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               needText();
                datas.setOfferneed("2");
                offerneedStr = "2";

            }
        });
        offerneed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               offerneedText();
                datas.setOfferneed("3");
                offerneedStr = "3";
            }
        });



//        RecyclerView recycler = (RecyclerView) screenView.findViewById(R.id.view_source_rv);
//        SourceScreenAdapter adapter = new SourceScreenAdapter(R.layout.);
        TextView retag = (TextView) screenView.findViewById(R.id.view_source_retagTv);
        TextView suretag = (TextView) screenView.findViewById(R.id.view_source_sureTv);


        retag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (SourceScreenBean.CooperationListBean category : ResouceHelper.getSourceScreenBean().getCooperation_list()){
                        category.setCheck(false);
                }
                for (SourceScreenBean.CategoryListBean categoryListBean : ResouceHelper.getSourceScreenBean().getCategory_list()){
                        categoryListBean.setCheck(false);
                }
                for (SourceScreenBean.CompanyListBean company : ResouceHelper.getSourceScreenBean().getCompany_list()){
                        company.setCheck(false);
                }
                adapter1.notifyDataSetChanged();
                adapter2.notifyDataSetChanged();
                needTv.setBackground(ContextCompat.getDrawable(activity,R.drawable.bg_resource_item));
                needTv.setTextColor(ContextCompat.getColor(activity,R.color._888));
                offerTv.setBackground(ContextCompat.getDrawable(activity,R.drawable.bg_resource_item));
                offerTv.setTextColor(ContextCompat.getColor(activity,R.color._888));
                offerneed.setBackground(ContextCompat.getDrawable(activity,R.drawable.bg_resource_item));
                offerneed.setTextColor(ContextCompat.getColor(activity,R.color._888));
                isClickBtn = false;
            }
        });
        suretag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isClickBtn = true;
                String coopId = "";
                StringBuffer sb = new StringBuffer();
                String companyId = "";
                for (SourceScreenBean.CooperationListBean category : ResouceHelper.getSourceScreenBean().getCooperation_list()){
                    if(category.isCheck()){
                       coopId = category.getId()+"";
                    }
                }
                for (SourceScreenBean.CategoryListBean categoryListBean : ResouceHelper.getSourceScreenBean().getCategory_list()){
                    if (categoryListBean.isCheck()){
                        sb.append(categoryListBean.getId());
                        sb.append("_");
                    }
                }
                if (sb.length()>0){
                    sb.delete(sb.length()-1,sb.length());
                }
                for (SourceScreenBean.CompanyListBean company : ResouceHelper.getSourceScreenBean().getCompany_list()){
                    if (company.isCheck()){
                        companyId = company.getId()+"";
                    }
                }
                offerneedStr = datas.getOfferneed();
                finish.setFinish(offerneedStr,sb.toString(),companyId);

            }
        });

        this.setContentView(screenView);
        this.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        this.setAnimationStyle(R.style.SelectPopupWindow);
        this.setOutsideTouchable(true);
        this.setFocusable(true);
        this.setOnDismissListener(this);

    }

    //显示
    public void showPopupWindow(View parent) {
        if (!this.isShowing()) {
            this.showAsDropDown(parent, Gravity.CENTER,0,0);
            AnimationUtil.createAnimation(true,screenView,listRoot,null);
        } else {
            dismissPopup();
        }
    }

    //消失
//    private void dismissPopup() {
//        super.dismiss();
//    }


    @Override
    public void onDismiss() {
        dismissPopup();
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
            AnimationUtil.createAnimation(true, screenView, listRoot, null);
            super.showAsDropDown(anchor);
        }else {
            dismissPopup();
        }
    }


    //消失
    public void dismissPopup(){
        //如果没有点击确定按钮，并且tempLeftSelectPd != leftSelectPd
        if (!isClickBtn){
            for (SourceScreenBean.CooperationListBean category : ResouceHelper.getSourceScreenBean().getCooperation_list()){
                category.setCheck(false);
            }
            for (SourceScreenBean.CategoryListBean categoryListBean : ResouceHelper.getSourceScreenBean().getCategory_list()){
                categoryListBean.setCheck(false);
            }
            for (SourceScreenBean.CompanyListBean company : ResouceHelper.getSourceScreenBean().getCompany_list()){
                company.setCheck(false);
            }
            datas.setOfferneed("");
        }
        super.dismiss();
    }
    private void offerText(){
        offerTv.setBackground(ContextCompat.getDrawable(activity,R.drawable.bg_offertv));
        offerTv.setTextColor(ContextCompat.getColor(activity,R.color.colorPrimary));
        needTv.setBackground(ContextCompat.getDrawable(activity,R.drawable.bg_resource_item));
        needTv.setTextColor(ContextCompat.getColor(activity,R.color._888));
        offerneed.setBackground(ContextCompat.getDrawable(activity,R.drawable.bg_resource_item));
        offerneed.setTextColor(ContextCompat.getColor(activity,R.color._888));
    }
    private void needText(){
        needTv.setBackground(ContextCompat.getDrawable(activity,R.drawable.bg_offertv));
        needTv.setTextColor(ContextCompat.getColor(activity,R.color.colorPrimary));
        offerTv.setBackground(ContextCompat.getDrawable(activity,R.drawable.bg_resource_item));
        offerTv.setTextColor(ContextCompat.getColor(activity,R.color._888));
        offerneed.setBackground(ContextCompat.getDrawable(activity,R.drawable.bg_resource_item));
        offerneed.setTextColor(ContextCompat.getColor(activity,R.color._888));
    }
    private void offerneedText(){
        offerneed.setBackground(ContextCompat.getDrawable(activity,R.drawable.bg_offertv));
        offerneed.setTextColor(ContextCompat.getColor(activity,R.color.colorPrimary));
        needTv.setBackground(ContextCompat.getDrawable(activity,R.drawable.bg_resource_item));
        needTv.setTextColor(ContextCompat.getColor(activity,R.color._888));
        offerTv.setBackground(ContextCompat.getDrawable(activity,R.drawable.bg_resource_item));
        offerTv.setTextColor(ContextCompat.getColor(activity,R.color._888));
    }








    public interface finish{
        void setFinish(String QueryType,String SearchQueryId,String SearchIndustry);
    }

    private finish finish;

    public void setFinish(SourceScreenWindow.finish finish) {
        this.finish = finish;
    }
}

