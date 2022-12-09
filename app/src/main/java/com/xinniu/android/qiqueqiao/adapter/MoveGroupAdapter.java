package com.xinniu.android.qiqueqiao.adapter;

import android.content.Context;
//import android.support.annotation.Nullable;
import android.util.SparseBooleanArray;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xinniu.android.qiqueqiao.R;
import com.xinniu.android.qiqueqiao.bean.GroupFriendBean;
import com.xinniu.android.qiqueqiao.bean.ServiceCategoryAndTag;

import java.util.List;

/**
 * 移动分组
 * Created by yuchance on 2018/12/12.
 */

public class MoveGroupAdapter extends BaseQuickAdapter<GroupFriendBean, BaseViewHolder> {

    private Context context;
    private SparseBooleanArray sparseBooleanArray;

    public MoveGroupAdapter(Context context, int layoutResId, @Nullable List<GroupFriendBean> data) {
        super(layoutResId, data);
        this.context = context;
        sparseBooleanArray = new SparseBooleanArray();
    }

    public SparseBooleanArray getSparseBooleanArray() {
        return sparseBooleanArray;
    }

    @Override
    protected void convert(final BaseViewHolder helper, final GroupFriendBean item) {
        if (sparseBooleanArray.get(helper.getAdapterPosition())) {
            helper.getView(R.id.img_check).setVisibility(View.VISIBLE);
        } else {
            helper.getView(R.id.img_check).setVisibility(View.GONE);
        }


        helper.setText(R.id.tv_hroup_name, item.getName());
        helper.getView(R.id.rlayout_root).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sparseBooleanArray.clear();
                sparseBooleanArray.put(helper.getAdapterPosition(), true);
                notifyDataSetChanged();
                setOnClick.setOnClick(item.getName(), item.getGroup_id());
            }
        });

    }

    public interface setOnClick {
        void setOnClick(String group_name, int group_id);
    }

    private setOnClick setOnClick;

    public void setSetOnClick(MoveGroupAdapter.setOnClick setOnClick) {
        this.setOnClick = setOnClick;
    }
}
