package com.xinniu.android.qiqueqiao.adapter;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xinniu.android.qiqueqiao.R;
import com.xinniu.android.qiqueqiao.activity.AcceptedOrdersPersonActivity;
import com.xinniu.android.qiqueqiao.bean.MyPublicRewardBean;

import java.util.List;
//import android.support.v4.content.ContextCompat;

/**
 * Created by qinlei
 * Created on 2017/12/12
 * Created description :
 */

public class MyPublicRewardAdapter extends BaseQuickAdapter<MyPublicRewardBean.ListBean, BaseViewHolder> {
    private AppCompatActivity mContext;

    public MyPublicRewardAdapter(AppCompatActivity context, int layoutResId, @Nullable List<MyPublicRewardBean.ListBean> data) {
        super(layoutResId, data);
        this.mContext = context;
    }


    @Override
    protected void convert(final BaseViewHolder helper, final MyPublicRewardBean.ListBean item) {
        final TextView stateTv = helper.getView(R.id.tv_status);
        helper.setText(R.id.tv_title, item.getTitle());
        if (item.getAmount().contains(".")) {
            String[] pricr01 = item.getAmount().split("\\.");
            helper.setText(R.id.tv_price,  pricr01[0]);
        } else {
            helper.setText(R.id.tv_price, item.getAmount());
        }

        helper.setText(R.id.tv_number, item.getRemaining_number() + "");
        helper.setText(R.id.tv_num, item.getNumberOrders() + "");
        if (item.getStatus() == 1) {
            //	1：进行中，2：结束
            stateTv.setText("已上线");
            stateTv.setTextColor(ContextCompat.getColor(mContext, R.color.blue_bg_4B96F3));
        } else if (item.getStatus() == 2) {
            stateTv.setText("赏金库存不足，已下线");
            stateTv.setTextColor(ContextCompat.getColor(mContext, R.color._777));
        }
        helper.getView(R.id.llayout_root).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //已接单人界面
                AcceptedOrdersPersonActivity.start(mContext, item.getOrder_sn());
            }
        });
    }


}
