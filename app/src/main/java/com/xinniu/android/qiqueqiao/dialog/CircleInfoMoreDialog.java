package com.xinniu.android.qiqueqiao.dialog;

import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
//import android.support.v7.app.AppCompatDialog;
//import android.support.v7.widget.LinearLayoutManager;
//import androidx.recyclerview.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import androidx.appcompat.app.AppCompatDialog;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.xinniu.android.qiqueqiao.R;
import com.xinniu.android.qiqueqiao.adapter.DialogCompanyAdapter;
import com.xinniu.android.qiqueqiao.bean.SelectCategory;
import com.xinniu.android.qiqueqiao.divider.DividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lzq on 2018/2/1.
 */

public class CircleInfoMoreDialog extends AppCompatDialog implements DialogCompanyAdapter.ItemSelect,View.OnClickListener{
    Context context;
    protected float mFillWidthPercent = ViewGroup.LayoutParams.MATCH_PARENT;

    RecyclerView rv;
    DialogCompanyAdapter adapter;

    private List<SelectCategory> mList = new ArrayList<>();
    public final static int ITEM_SHARE = 1;
    public final static int ITME_DELETE = 2;
    public final static int ITME_RENOVATE = 3;

    public CircleInfoMoreDialog(Context context, int theme) {
        super(context, theme);
        this.context = context;
    }

    private void setFileWidth(Window dialog_window) {
        if (mFillWidthPercent == ViewGroup.LayoutParams.MATCH_PARENT) {
            dialog_window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        } else {
            int deviceWidth = Resources.getSystem().getDisplayMetrics().widthPixels;
            dialog_window.setLayout((int) (deviceWidth * mFillWidthPercent), ViewGroup.LayoutParams.WRAP_CONTENT);
        }
    }

    protected void setFillWidthPercent(float mFillPercent) {
        this.mFillWidthPercent = mFillPercent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_push);
        Window dialog_window = this.getWindow();
        dialog_window.setGravity(Gravity.BOTTOM);
        setFileWidth(dialog_window);
        adapter = new DialogCompanyAdapter(context,mList);
        adapter.setItemSelect(this);
        rv = (RecyclerView) findViewById(R.id.rv);
        rv.setLayoutManager(new LinearLayoutManager(context));
        rv.addItemDecoration(new DividerItemDecoration(context,LinearLayoutManager.VERTICAL));
        rv.setAdapter(adapter);
    }

    @Override
    public void itemSelect(SelectCategory selectCategories) {
        dismiss();
        if (onItemClikListener == null){
            return;
        }
        if (selectCategories.getId() == ITEM_SHARE){
            onItemClikListener.onClick1();
        }
        if (selectCategories.getId() == ITME_DELETE){
            onItemClikListener.onClick2();
        }
        if (selectCategories.getId() == ITME_RENOVATE){
            onItemClikListener.onClick3();
        }
    }


    public void setItemForRenovate(){
        mList.clear();
        SelectCategory item1 = new SelectCategory();
        item1.setId(ITEM_SHARE);
        item1.setName("分享圈子");
        SelectCategory item2 = new SelectCategory();
        item2.setId(ITME_DELETE);
        item2.setName("退出圈子");
        SelectCategory item3 = new SelectCategory();
        item3.setId(ITME_RENOVATE);
        item3.setName("取消");
        mList.add(item1);
        mList.add(item2);
        mList.add(item3);
        adapter.notifyDataSetChanged();
    }

    public void setItemWithoutQuit(){
        mList.clear();
        SelectCategory item1 = new SelectCategory();
        item1.setId(ITEM_SHARE);
        item1.setName("分享圈子");
        SelectCategory item3 = new SelectCategory();
        item3.setId(ITME_RENOVATE);
        item3.setName("取消");
        mList.add(item1);
        mList.add(item3);
        adapter.notifyDataSetChanged();
    }



    @Override
    public void onClick(View v) {
        int id = v.getId();

        if (id == R.id.tv_cancel){
            dismiss();
        }
    }

    private CircleInfoMoreDialog.OnItemClikListener onItemClikListener;

    public interface OnItemClikListener{
        void onClick1();
        void onClick2();
        void onClick3();
    }

    public void setOnItemClikListener(CircleInfoMoreDialog.OnItemClikListener onItemClikListener){
        this.onItemClikListener = onItemClikListener;
    }


}
