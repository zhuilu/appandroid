package com.xinniu.android.qiqueqiao.customs;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
//import android.support.v7.widget.GridLayoutManager;
//import android.support.v7.widget.LinearLayoutManager;
//import androidx.recyclerview.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.xinniu.android.qiqueqiao.R;
import com.xinniu.android.qiqueqiao.activity.PublishNewActivity;
import com.xinniu.android.qiqueqiao.adapter.PublicNeedTypeAdapter;
import com.xinniu.android.qiqueqiao.adapter.ScreenOnlyAdapter;
import com.xinniu.android.qiqueqiao.adapter.ScreenType1Adapter;
import com.xinniu.android.qiqueqiao.adapter.ScreenTypeAdapter;
import com.xinniu.android.qiqueqiao.bean.CellTagsBean;
import com.xinniu.android.qiqueqiao.bean.SelectCategory;
import com.xinniu.android.qiqueqiao.bean.SourceScreenBean;
import com.xinniu.android.qiqueqiao.utils.AnimationUtil;
import com.xinniu.android.qiqueqiao.utils.NoScrollRecyclerView;
import com.xinniu.android.qiqueqiao.utils.ResouceHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yuchance on 2018/6/29.
 */

public class ScreenTypeWindow extends PopupWindow implements PopupWindow.OnDismissListener {
    private Activity activity;
    private CellTagsBean datas = new CellTagsBean();
    private int mIndustryId = 0;
    private View industryView;
    private NoScrollRecyclerView offerrecyclerView;
    private RelativeLayout listRoot;
    public static boolean isClick;
    private RelativeLayout indusView;
    private int screenHeight;
    private NoScrollRecyclerView needrecyclerView;

    public ScreenTypeWindow(Activity context, CellTagsBean datas) {
        this.activity = context;
        this.datas = datas;
        initWindow();
    }


    private void initWindow() {
        //获取屏幕宽高
        WindowManager wm = (WindowManager) activity.getSystemService(Context.WINDOW_SERVICE);
        screenHeight = wm.getDefaultDisplay().getHeight();
        this.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        this.setHeight(screenHeight / 2);
        industryView = LayoutInflater.from(activity).inflate(R.layout.view_resource_type, null);
        indusView = (RelativeLayout) industryView.findViewById(R.id.view_resource_Rl);
        indusView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dissPop();
            }
        });

        listRoot = (RelativeLayout) industryView.findViewById(R.id.view_resource_root);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 1 * screenHeight / 2);
        listRoot.setLayoutParams(params);
        offerrecyclerView = (NoScrollRecyclerView) industryView.findViewById(R.id.item_offer_recycler_screenthree);

        needrecyclerView = (NoScrollRecyclerView) industryView.findViewById(R.id.item_need_recycler_screenthree);

        //确定按钮
        TextView sureTv = (TextView) industryView.findViewById(R.id.view_source_sureTv);
        //重置按钮
        TextView reTv = (TextView) industryView.findViewById(R.id.view_source_retagTv);
//        TextView offerTv = (TextView) industryView.findViewById(R.id.tvOfferWindow);
//        TextView needTv = (TextView) industryView.findViewById(R.id.tvNeedWindow);
        RecyclerView moffer_recycler = industryView.findViewById(R.id.moffer_recycler);
        RecyclerView need_recycler = industryView.findViewById(R.id.need_recycler);

        LinearLayoutManager manager1 = new LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false);
        moffer_recycler.setLayoutManager(manager1);
        final ScreenTypeAdapter offerAdapter = new ScreenTypeAdapter(activity, R.layout.item_publich_new, datas.getProvide_category());
        moffer_recycler.setAdapter(offerAdapter);
        moffer_recycler.setNestedScrollingEnabled(false);


        LinearLayoutManager manager2 = new LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false);
        need_recycler.setLayoutManager(manager2);
        final ScreenType1Adapter needAdapter = new ScreenType1Adapter(activity, R.layout.item_publich_new, datas.getNeed_category());
        need_recycler.setAdapter(needAdapter);
        need_recycler.setNestedScrollingEnabled(false);


        sureTv.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                isClick = true;
                StringBuffer stringBuffer = new StringBuffer();
                for (int i = 0; i < datas.getProvide_category().size(); i++) {
                    for (int j = 0; j < datas.getProvide_category().get(i).getList().size(); j++) {
                        if (datas.getProvide_category().get(i).getList().get(j).isCheck()) {
                            stringBuffer.append(datas.getProvide_category().get(i).getList().get(j).getId() + "_");
                        }
                    }

                }
                for (int i = 0; i < datas.getNeed_category().size(); i++) {
                    for (int j = 0; j < datas.getNeed_category().get(i).getList().size(); j++) {
                        if (datas.getNeed_category().get(i).getList().get(j).isCheck()) {
                            stringBuffer.append(datas.getNeed_category().get(i).getList().get(j).getId() + "_");
                        }
                    }

                }

                if (stringBuffer.length() > 1) {
                    mSetfinish.setToFinish(stringBuffer.substring(0, stringBuffer.length() - 1));
                } else {
                    mSetfinish.setToFinish(stringBuffer.toString());
                }

                dissPop();
            }
        });
        reTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i = 0; i < datas.getProvide_category().size(); i++) {
                    for (int j = 0; j < datas.getProvide_category().get(i).getList().size(); j++) {
                        datas.getProvide_category().get(i).getList().get(j).setCheck(false);
                    }
                }
                offerAdapter.notifyDataSetChanged();
                for (int i = 0; i < datas.getNeed_category().size(); i++) {
                    for (int j = 0; j < datas.getNeed_category().get(i).getList().size(); j++) {
                        datas.getNeed_category().get(i).getList().get(j).setCheck(false);
                    }
                }
                needAdapter.notifyDataSetChanged();
                mIndustryId = 0;
                isClick = false;
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
    public void dissPop() {
        if (this.isShowing()) {
            dismiss();
        }
    }

    @Override
    public void onDismiss() {
        dismissPopup();
    }

    private void dismissPopup() {
        if (!isClick) {
//            for (CellTagsBean company : ResouceHelper.getInstance().getCellTags()){
//                company.setCheck(false);
//            }
        }
        super.dismiss();
    }

    public interface setfinish {
        void setToFinish(String mId);
    }

    private setfinish mSetfinish;

    public void setmSetfinish(setfinish mSetfinish) {
        this.mSetfinish = mSetfinish;
    }

    //显示
    public void showPopupWindow(View parent) {
        if (!this.isShowing()) {
            this.showAsDropDown(parent, Gravity.CENTER, 0, 0);
            AnimationUtil.createAnimation(true, industryView, listRoot, null);
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
        } else {
            dissPop();
        }
    }
}
