package com.xinniu.android.qiqueqiao.adapter;

//import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xinniu.android.qiqueqiao.R;
import com.xinniu.android.qiqueqiao.bean.SelectCategory;

import java.util.List;

/**
 * Created by yuchance on 2018/3/30.
 */

public class CoopWayAdapter extends BaseQuickAdapter<SelectCategory,BaseViewHolder>{


    public CoopWayAdapter(int layoutResId, @Nullable List<SelectCategory> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, SelectCategory item) {
        if (!TextUtils.isEmpty(item.getName())) {
            helper.setText(R.id.item_coopway_tv, item.getName());
        }

    }
}
