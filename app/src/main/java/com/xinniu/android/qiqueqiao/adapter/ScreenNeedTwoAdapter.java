package com.xinniu.android.qiqueqiao.adapter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xinniu.android.qiqueqiao.R;
import com.xinniu.android.qiqueqiao.bean.CellTagsBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yuchance on 2018/12/27.
 */

public class ScreenNeedTwoAdapter extends BaseQuickAdapter<CellTagsBean.NeedCategoryBean.ListBeanX, BaseViewHolder> {

    private Context context;
    private List<CellTagsBean.NeedCategoryBean.ListBeanX> mData = new ArrayList<>();

    public ScreenNeedTwoAdapter(Context context, int layoutResId, @Nullable List<CellTagsBean.NeedCategoryBean.ListBeanX> data) {
        super(layoutResId, data);
        this.context = context;
        this.mData = data;
    }

    @Override
    protected void convert(BaseViewHolder helper, final CellTagsBean.NeedCategoryBean.ListBeanX item) {
        TextView tv = helper.getView(R.id.item_source_celltv);
        tv.setText(item.getName());
        if (item.isCheck()) {
            tv.setBackground(ContextCompat.getDrawable(mContext, R.drawable.bg_source_cell_two));
            helper.setTextColor(R.id.item_source_celltv, ContextCompat.getColor(mContext, R.color.white));
        } else {
            tv.setBackground(ContextCompat.getDrawable(mContext, R.drawable.bg_source_cell));
            helper.setTextColor(R.id.item_source_celltv, ContextCompat.getColor(mContext, R.color._888));
        }
        (helper.getView(R.id.item_source_celltv)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //单选
                for (int u = 0; u < mData.size(); u++) {
                    mData.get(u).setCheck(false);
                }
//                if (item.isCheck()) {
//                    item.setCheck(false);
//                }else {
                item.setCheck(true);
                //         }

                notifyDataSetChanged();
            }
        });
    }
}
