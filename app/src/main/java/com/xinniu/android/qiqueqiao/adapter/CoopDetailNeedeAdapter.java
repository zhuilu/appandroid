package com.xinniu.android.qiqueqiao.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xinniu.android.qiqueqiao.R;
import com.xinniu.android.qiqueqiao.bean.CoopDetailBean;
import com.xinniu.android.qiqueqiao.customs.label.FlowLayout;
import com.xinniu.android.qiqueqiao.customs.label.TagAdapter;
import com.xinniu.android.qiqueqiao.customs.label.TagFlowLayout;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;

/**
 * Created by yuchance on 2018/12/27.
 */

public class CoopDetailNeedeAdapter extends BaseQuickAdapter<CoopDetailBean.NeedCategoryBean, BaseViewHolder> {

    private AppCompatActivity context;
    private List<CoopDetailBean.NeedCategoryBean> mData = new ArrayList<>();

    public CoopDetailNeedeAdapter(AppCompatActivity context, int layoutResId, @Nullable List<CoopDetailBean.NeedCategoryBean> data) {
        super(layoutResId, data);
        this.context = context;
        this.mData = data;
    }

    @Override
    protected void convert(BaseViewHolder helper, final CoopDetailBean.NeedCategoryBean item) {
        helper.setText(R.id.moffer_titletv, item.getTitleX()+"：");
        final TagFlowLayout tagFlowLayout=helper.getView(R.id.coop_moffer_label);
        tagFlowLayout.setAdapter(new TagAdapter<CoopDetailBean.NeedCategoryBean.ListBeanX>(item.getList()) {
            @Override
            public View getView(FlowLayout parent, int position, CoopDetailBean.NeedCategoryBean.ListBeanX item) {
                TextView tv = (TextView) LayoutInflater.from(context).inflate(R.layout.item_label_tv, tagFlowLayout, false);
                tv.setText(item.getName());
                return tv;
            }
        });

    }

    public interface setAddOnClick {
        void setAddOnClick(int isType);
    }

    private setAddOnClick setAddOnClick;

    public void setSetAddOnClick(setAddOnClick setAddOnClick) {
        this.setAddOnClick = setAddOnClick;
    }
}