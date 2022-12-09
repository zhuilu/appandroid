package com.xinniu.android.qiqueqiao.adapter;

import android.content.Context;
//import android.support.annotation.Nullable;
import android.util.SparseBooleanArray;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xinniu.android.qiqueqiao.R;
import com.xinniu.android.qiqueqiao.bean.GroupFriendBean;

import java.util.List;

/**
 * 移动分组
 * Created by yuchance on 2018/12/12.
 */

public class RewardTypeAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

    private Context context;
    private SparseBooleanArray sparseBooleanArray;

    public RewardTypeAdapter(Context context, int layoutResId, @Nullable List<String> data) {
        super(layoutResId, data);
        this.context = context;
        sparseBooleanArray = new SparseBooleanArray();
    }

    public SparseBooleanArray getSparseBooleanArray() {
        return sparseBooleanArray;
    }

    @Override
    protected void convert(final BaseViewHolder helper, final String item) {
        if (sparseBooleanArray.get(helper.getAdapterPosition())) {
            helper.getView(R.id.item_label_type).setSelected(true);
        } else {
            helper.getView(R.id.item_label_type).setSelected(false);
        }


        helper.setText(R.id.item_label_type, item);
        helper.getView(R.id.item_label_type).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sparseBooleanArray.clear();
                sparseBooleanArray.put(helper.getAdapterPosition(), true);
                notifyDataSetChanged();
                setOnClick.setOnClick(item);
            }
        });

    }

    public interface setOnClick {
        void setOnClick(String group_name);
    }

    private setOnClick setOnClick;

    public void setSetOnClick(RewardTypeAdapter.setOnClick setOnClick) {
        this.setOnClick = setOnClick;
    }
}
