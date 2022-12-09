package com.xinniu.android.qiqueqiao.adapter;

import android.app.Activity;
//import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xinniu.android.qiqueqiao.R;
import com.xinniu.android.qiqueqiao.bean.CoopDetailBean;
import com.xinniu.android.qiqueqiao.customs.label.FlowLayout;
import com.xinniu.android.qiqueqiao.customs.label.TagAdapter;
import com.xinniu.android.qiqueqiao.customs.label.TagFlowLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yuchance on 2018/12/27.
 */

public class CoopDetailOfferAdapter extends BaseQuickAdapter<CoopDetailBean.ProvideCategoryBean, BaseViewHolder> {

    private Activity context;
    private List<CoopDetailBean.ProvideCategoryBean> mData = new ArrayList<>();

    public CoopDetailOfferAdapter(Activity context, int layoutResId, @Nullable List<CoopDetailBean.ProvideCategoryBean> data) {
        super(layoutResId, data);
        this.context = context;
        this.mData = data;
    }

    @Override
    protected void convert(BaseViewHolder helper, final CoopDetailBean.ProvideCategoryBean item) {
        helper.setText(R.id.moffer_titletv, item.getTitleX()+"：");
        final TagFlowLayout tagFlowLayout=helper.getView(R.id.coop_moffer_label);
        tagFlowLayout.setAdapter(new TagAdapter<CoopDetailBean.ProvideCategoryBean.ListBean>(item.getList()) {
            @Override
            public View getView(FlowLayout parent, int position, CoopDetailBean.ProvideCategoryBean.ListBean item) {
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