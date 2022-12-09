package com.xinniu.android.qiqueqiao.adapter;

//import android.support.annotation.Nullable;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xinniu.android.qiqueqiao.R;
import com.xinniu.android.qiqueqiao.bean.SelectCategory;

import java.util.List;



/**
 * Created by yuchance on 2018/8/31.
 */

public class SortOrderAdapter extends BaseQuickAdapter<SelectCategory,BaseViewHolder> {

    public SortOrderAdapter(int layoutResId, @Nullable List<SelectCategory> data) {
        super(layoutResId, data);

    }

    @Override
    protected void convert(BaseViewHolder helper, final SelectCategory item) {
        helper.setText(R.id.tvsortOrder,item.getName());
        helper.getView(R.id.tvsortOrderRl).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setItemData.onSetItemData(item.getId(),item.getName());
            }
        });
    }
    public interface setItemData{
        void onSetItemData(int id,String name);
    }
    private setItemData setItemData;

    public void setSetItemData(SortOrderAdapter.setItemData setItemData) {
        this.setItemData = setItemData;
    }
}
