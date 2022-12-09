package com.xinniu.android.qiqueqiao;

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
import android.widget.TextView;

import androidx.appcompat.app.AppCompatDialog;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.xinniu.android.qiqueqiao.adapter.DialogCompanyAdapter;
import com.xinniu.android.qiqueqiao.bean.SelectCategory;
import com.xinniu.android.qiqueqiao.customs.qldialog.QLDialog;
import com.xinniu.android.qiqueqiao.divider.DividerItemDecoration;
import com.xinniu.android.qiqueqiao.request.RequestManager;
import com.xinniu.android.qiqueqiao.request.callback.GetCategoryCallback;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lzq on 2017/12/21.
 */

public class SelectCompanyDialog extends AppCompatDialog implements DialogCompanyAdapter.ItemSelect,View.OnClickListener{

    RecyclerView rv;
    Context context;
    DialogCompanyAdapter adapter;
    TextView tv_sure;
    TextView tv_cancel;
    protected float mFillWidthPercent = ViewGroup.LayoutParams.MATCH_PARENT;

    private SelectCategory selectCategories;
    private List<SelectCategory> mList = new ArrayList<>();

    public SelectCompanyDialog(Context context, int theme) {
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
        setContentView(R.layout.dialog_select_company);
        tv_sure = (TextView) findViewById(R.id.tv_sure);
        tv_sure.setOnClickListener(this);
        tv_cancel = (TextView) findViewById(R.id.tv_cancel);
        tv_cancel.setOnClickListener(this);
        Window dialog_window = this.getWindow();
        dialog_window.setGravity(Gravity.BOTTOM);
        setFileWidth(dialog_window);
        adapter = new DialogCompanyAdapter(context,mList);
        adapter.setItemSelect(this);
        rv = (RecyclerView) findViewById(R.id.rv);
        rv.setLayoutManager(new LinearLayoutManager(context));
        rv.addItemDecoration(new DividerItemDecoration(context,LinearLayoutManager.VERTICAL));
        rv.setAdapter(adapter);
        RequestManager.getInstance().getScreen(2, new GetCategoryCallback() {
            @Override
            public void onSuccess(List<SelectCategory> list) {
                mList.clear();
                mList.addAll(list);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailed(int code, String msg) {

            }
        });
    }

    @Override
    public void itemSelect(SelectCategory selectCategories) {
        this.selectCategories = selectCategories;
        if (defaultSelect != null && selectCategories!=null){
            defaultSelect.itemSelect(selectCategories);
        }
        dismiss();
    }

    private ItemSelect defaultSelect;

    public void setItemSelect(ItemSelect defaultSelect) {
        this.defaultSelect = defaultSelect;
    }

    @Override
    public void onClick(View v) {
//        int id = v.getId();
//        if (id == R.id.tv_sure){
//            if (defaultSelect != null && selectCategories!=null){
//                defaultSelect.itemSelect(selectCategories);
//            }
//            dismiss();
//        }
//        if (id == R.id.tv_cancel){
//            dismiss();
//        }
    }

    public interface ItemSelect {
        void itemSelect(SelectCategory selectCategories);
    }
}
