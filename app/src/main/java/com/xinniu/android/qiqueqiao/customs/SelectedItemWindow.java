package com.xinniu.android.qiqueqiao.customs;

import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.xinniu.android.qiqueqiao.R;
import com.xinniu.android.qiqueqiao.adapter.StepModeAdapter;
import com.xinniu.android.qiqueqiao.bean.SelectCategory;
import com.xinniu.android.qiqueqiao.utils.AnimationUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lzq on 2018/3/2.
 */

public class SelectedItemWindow extends PopupWindow implements  PopupWindow.OnDismissListener,StepModeAdapter.OnItemClickListener{
//    private View pView;
    private View animTargetView;
    View contentView;
    private List<SelectCategory> categoryList = new ArrayList<>();
    private AppCompatActivity activity;
    private RecyclerView mRecyclerView;
    private StepModeAdapter mModeAdapter;

    public SelectedItemWindow(AppCompatActivity activity, List<SelectCategory> categoryList){
        this.activity = activity;
        this.categoryList = categoryList;
        initView();
    }

    private void initView(){
        this.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        this.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
        contentView = LayoutInflater.from(activity).inflate(R.layout.window_selected_item,null);
        mRecyclerView = (RecyclerView) contentView.findViewById(R.id.rv);
        mRecyclerView.setLayoutManager(new GridLayoutManager(activity,4));
        animTargetView = mRecyclerView;
//        pView = contentView.findViewById(R.id.view1);
//        pView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                startAnim(false);
//            }
//        });
        mModeAdapter = new StepModeAdapter(activity,categoryList);
        mModeAdapter.setOnItemtClikListener(this);
        mRecyclerView.setAdapter(mModeAdapter);
        this.setContentView(contentView);//????????????
        this.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        this.setAnimationStyle(R.style.SelectPopupWindow);
        this.setOutsideTouchable(true);
        this.setFocusable(true);
    }


    //??????
    public void showPopupWindow(View parent) {
        if (!this.isShowing()) {
            this.showAsDropDown(parent);
            AnimationUtil.createAnimation(true,contentView,animTargetView,null);
        } else {
            dismissPopup();
        }
    }

    //??????
    public void dismissPopup(){
        super.dismiss();
    }

    @Override
    public void dismiss() {
        startAnim(false);
    }

    @Override
    public void onDismiss() {
        startAnim(false);
    }

    private void startAnim(boolean isIn){
        AnimationUtil.createAnimation(false, contentView, animTargetView, new AnimationUtil.AnimInterface() {
            @Override
            public void animEnd() {
                dismissPopup();
            }
        });
    }

    @Override
    public void showAsDropDown(View anchor) {
        if(Build.VERSION.SDK_INT >= 24){
            Rect visibleFrame = new Rect();
            anchor.getGlobalVisibleRect(visibleFrame);
            int height = anchor.getResources().getDisplayMetrics().heightPixels - visibleFrame.bottom;
            setHeight(height);
        }
        super.showAsDropDown(anchor);

    }

    @Override
    public void onItemClik(SelectCategory content) {
        if (listner != null){
            listner.onItemClik(content);
        }
//        startAnim(false);
    }


    public interface ItemClikListner{
        void onItemClik(SelectCategory selectCategory);
    }
    ItemClikListner listner;
    public void setItemClikListner(ItemClikListner listner){
        this.listner = listner;
    }
}
