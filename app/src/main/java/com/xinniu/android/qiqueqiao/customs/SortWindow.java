package com.xinniu.android.qiqueqiao.customs;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.xinniu.android.qiqueqiao.R;
import com.xinniu.android.qiqueqiao.utils.AnimationUtil;

public class SortWindow extends PopupWindow implements PopupWindow.OnDismissListener {
    private AppCompatActivity activity;
    private View screenView;
    private LinearLayout llayoutRoot;
    private int mSearchSortOrder;

    public SortWindow(AppCompatActivity activity, int id) {
        this.activity = activity;
        this.mSearchSortOrder = id;
        initWindow();
    }

    private void initWindow() {
        //获取屏幕宽高
        WindowManager wm = (WindowManager) activity.getSystemService(Context.WINDOW_SERVICE);
        this.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        this.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        screenView = LayoutInflater.from(activity).inflate(R.layout.view_resource_sort, null);
        final TextView tvNomal = (TextView) screenView.findViewById(R.id.tv_normol);
        final TextView tvNew = (TextView) screenView.findViewById(R.id.tv_new);
        LinearLayout linearLayout = (LinearLayout) screenView.findViewById(R.id.view);
        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        llayoutRoot = (LinearLayout) screenView.findViewById(R.id.view_root);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        llayoutRoot.setLayoutParams(params);
        if (mSearchSortOrder == 36) {
            tvNomal.setTextColor(activity.getResources().getColor(R.color.blue_bg_4B96F3));
            tvNew.setTextColor(activity.getResources().getColor(R.color._333));
        } else if (mSearchSortOrder == 37) {
            tvNomal.setTextColor(activity.getResources().getColor(R.color._333));
            tvNew.setTextColor(activity.getResources().getColor(R.color.blue_bg_4B96F3));
        }
        tvNomal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish.setFinish(36, tvNomal.getText().toString());
            }
        });
        tvNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish.setFinish(37, tvNew.getText().toString());
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
            this.showAsDropDown(parent, Gravity.CENTER, 0, 0);
            AnimationUtil.createAnimation(true, screenView, llayoutRoot, null);
        } else {
            dismiss();
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
            AnimationUtil.createAnimation(true, screenView, llayoutRoot, null);
            super.showAsDropDown(anchor);
        } else {
            dismiss();
        }
    }

    @Override
    public void onDismiss() {
        dismiss();
    }

    public interface finish {
        void setFinish(int id, String name);
    }

    private finish finish;

    public void setFinish(finish finish) {
        this.finish = finish;
    }
}
