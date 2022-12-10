package com.xinniu.android.qiqueqiao.dialog;

import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatDialog;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatDialog;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.xinniu.android.qiqueqiao.R;
import com.xinniu.android.qiqueqiao.adapter.DialogCompanyAdapter;
import com.xinniu.android.qiqueqiao.bean.SelectCategory;
import com.xinniu.android.qiqueqiao.common.Constants;
import com.xinniu.android.qiqueqiao.divider.DividerItemDecoration;
import com.xinniu.android.qiqueqiao.request.RequestManager;
import com.xinniu.android.qiqueqiao.request.callback.CommonCallback;
import com.xinniu.android.qiqueqiao.request.callback.GetCategoryCallback;
import com.xinniu.android.qiqueqiao.request.converter.ResultDO;
import com.xinniu.android.qiqueqiao.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lzq on 2018/2/28.
 */

public class SelectDialog extends AppCompatDialog implements DialogCompanyAdapter.ItemSelect,View.OnClickListener{
    RecyclerView rv;
    Context context;
    DialogCompanyAdapter adapter;
    protected float mFillWidthPercent = ViewGroup.LayoutParams.MATCH_PARENT;

    private int dialogType;
    private SelectCategory selectCategories;
    private List<SelectCategory> mList = new ArrayList<>();

    public SelectDialog(Context context, int theme,int dialogType) {
        super(context, theme);
        this.context = context;
        this.dialogType = dialogType;
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
        setContentView(R.layout.dialog_item_selected);
        Window dialog_window = this.getWindow();
        dialog_window.setGravity(Gravity.BOTTOM);
        setFileWidth(dialog_window);
        adapter = new DialogCompanyAdapter(context,mList);
        adapter.setItemSelect(this);
        rv = (RecyclerView) findViewById(R.id.rv);
        rv.setLayoutManager(new LinearLayoutManager(context));
        rv.addItemDecoration(new DividerItemDecoration(context,LinearLayoutManager.VERTICAL));
        rv.setAdapter(adapter);
        RequestManager.getInstance().getScreen(dialogType, new GetCategoryCallback() {
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
        if (defaultSelect != null){
            defaultSelect.onSelected(dialogType,selectCategories);
        }
        dismiss();
    }

    private OnSelectItemListener defaultSelect;

    public void setOnSelectItemListener(OnSelectItemListener defaultSelect) {
        this.defaultSelect = defaultSelect;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        if (id == R.id.tv_cancel){
            dismiss();
        }
    }

    public interface OnSelectItemListener {
        void onSelected(int type,SelectCategory item);
    }

}
