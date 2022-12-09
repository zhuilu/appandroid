package com.xinniu.android.qiqueqiao.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
//import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xinniu.android.qiqueqiao.R;
import com.xinniu.android.qiqueqiao.activity.CreateGroupNameActivity;
import com.xinniu.android.qiqueqiao.bean.AddGroupClassifyBean;
import com.xinniu.android.qiqueqiao.utils.ImageLoader;

import java.util.List;

/**
 * Created by yuchance on 2018/9/28.
 */

public class CreateGroupClassifyAdapter extends BaseQuickAdapter<AddGroupClassifyBean,BaseViewHolder> {

    private Context context;


    public CreateGroupClassifyAdapter(Context context,int layoutResId, @Nullable List<AddGroupClassifyBean> data) {
        super(layoutResId, data);
        this.context = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, final AddGroupClassifyBean item) {
        RelativeLayout itemRl = helper.getView(R.id.item_addgroup_Rl);
        ImageView addGroupImg = helper.getView(R.id.item_addgroup_img);
        helper.setText(R.id.item_addgroup_tv,item.getName());
        ImageLoader.loadAvter(item.getImg(), addGroupImg);
        itemRl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                goToNext.goToNext(item.getId());
            }
        });

    }

    public interface goToNext{
        void goToNext(int groupId);
    }

    private goToNext goToNext;

    public void setGoToNext(CreateGroupClassifyAdapter.goToNext goToNext) {
        this.goToNext = goToNext;
    }
}
