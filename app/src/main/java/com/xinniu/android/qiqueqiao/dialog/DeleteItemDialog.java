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
import android.widget.TextView;

import androidx.appcompat.app.AppCompatDialog;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.xinniu.android.qiqueqiao.R;
import com.xinniu.android.qiqueqiao.SelectCompanyDialog;
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
 * Created by lzq on 2018/1/5.
 */

public class DeleteItemDialog extends AppCompatDialog implements DialogCompanyAdapter.ItemSelect,View.OnClickListener{
    RecyclerView rv;
    Context context;
    DialogCompanyAdapter adapter;
    TextView tv_sure;
    TextView tv_cancel;
    protected float mFillWidthPercent = ViewGroup.LayoutParams.MATCH_PARENT;
    private int resourceId;

    private SelectCategory selectCategories;
    private List<SelectCategory> mList = new ArrayList<>();

    public DeleteItemDialog(Context context, int theme,int resourceId) {
        super(context, theme);
        this.context = context;
        this.resourceId = resourceId;
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
        setContentView(R.layout.dialog_item_delete);
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
        RequestManager.getInstance().getScreen(Constants.TYPE_DELETE_ITEM, new GetCategoryCallback() {
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
        resourceShield(resourceId,selectCategories.getId());
        this.selectCategories = selectCategories;
        dismiss();
    }

    private DeleteItem defaultSelect;

    public void setDeleteItemListener(DeleteItem defaultSelect) {
        this.defaultSelect = defaultSelect;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        if (id == R.id.tv_cancel){
            dismiss();
        }
    }

    public interface DeleteItem {
        void deleteItem(int id);
    }

    private void resourceShield(final int resourceId, int enumId){
        RequestManager.getInstance().resourceShield(resourceId, enumId, new CommonCallback() {
            @Override
            public void onSuccess(ResultDO resultDO) {
                if (defaultSelect != null && selectCategories!=null){
                    defaultSelect.deleteItem(resourceId);
                }
            }

            @Override
            public void onFailed(int code, String msg) {
                ToastUtils.showCentetImgToast(context,msg);
            }
        });
    }
}
