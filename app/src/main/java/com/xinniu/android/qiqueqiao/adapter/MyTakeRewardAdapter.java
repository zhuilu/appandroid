package com.xinniu.android.qiqueqiao.adapter;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
//import android.support.annotation.Nullable;
//import android.support.annotation.RequiresApi;
//import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xinniu.android.qiqueqiao.R;
import com.xinniu.android.qiqueqiao.activity.RewardOrderDetailActivity;
import com.xinniu.android.qiqueqiao.activity.ServiceCaseIndexActivity;
import com.xinniu.android.qiqueqiao.activity.ServiceDetailActivity;
import com.xinniu.android.qiqueqiao.bean.MyServicePushBean;
import com.xinniu.android.qiqueqiao.bean.TakeRewardBean;
import com.xinniu.android.qiqueqiao.utils.ImageLoader;
import com.xinniu.android.qiqueqiao.utils.RoundImageView;
import com.xinniu.android.qiqueqiao.utils.StringUtils;
import com.xinniu.android.qiqueqiao.widget.CircleImageView;

import java.util.List;

/**
 * Created by qinlei
 * Created on 2017/12/12
 * Created description :
 */

public class MyTakeRewardAdapter extends BaseQuickAdapter<TakeRewardBean.ListBean, BaseViewHolder> {
    private Callback callback;
    private Activity mContext;

    public MyTakeRewardAdapter(Activity context, int layoutResId, @Nullable List<TakeRewardBean.ListBean> data) {
        super(layoutResId, data);
        this.mContext = context;
    }

    public void setCallback(Callback callback) {
        this.callback = callback;
    }

    @Override
    protected void convert(final BaseViewHolder helper, final TakeRewardBean.ListBean item) {
        if (StringUtils.isEmpty(item.getCompany()) && StringUtils.isEmpty(item.getPosition())) {
            helper.setText(R.id.lx_positiontv, "");

        } else {
            if (StringUtils.isEmpty(item.getCompany())) {
                helper.setText(R.id.lx_positiontv, item.getPosition());

            } else if (StringUtils.isEmpty(item.getPosition())) {
                helper.setText(R.id.lx_positiontv, item.getCompany());

            } else {
                helper.setText(R.id.lx_positiontv, item.getCompany() + "|" + item.getPosition());

            }
        }
        helper.setText(R.id.lx_nametv, item.getRealname());
        ImageLoader.loadAvter(item.getHead_pic(), (ImageView) helper.getView(R.id.item_lx_headimg));
        TextView nameTv = helper.getView(R.id.lx_nametv);

        if (item.getIs_vip() == 1) {
            helper.getView(R.id.item_index_new_kingimg).setVisibility(View.VISIBLE);
            helper.setBackgroundRes(R.id.item_index_new_kingimg, R.mipmap.vip_iconx);
            nameTv.setTextColor(ContextCompat.getColor(mContext, R.color.king_color));

        } else if (item.getIs_vip() == 0) {
            helper.getView(R.id.item_index_new_kingimg).setVisibility(View.GONE);
            nameTv.setTextColor(ContextCompat.getColor(mContext, R.color._333));

        } else if (item.getIs_vip() == 2) {
            helper.getView(R.id.item_index_new_kingimg).setVisibility(View.VISIBLE);
            helper.setBackgroundRes(R.id.item_index_new_kingimg, R.mipmap.svip_iconx);
            nameTv.setTextColor(ContextCompat.getColor(mContext, R.color.king_color));

        }
        if (item.getIs_v() == 1) {
            helper.setVisible(R.id.index_new_isv, true);
        } else {
            helper.setVisible(R.id.index_new_isv, false);
        }
        helper.setText(R.id.tv_title, item.getTitle());
        if (item.getAmount().contains(".")) {
            String[] pricr01 = item.getAmount().split("\\.");
            helper.setText(R.id.tv_price,  pricr01[0]);
        } else {
            helper.setText(R.id.tv_price, item.getAmount());
        }
        if(item.getReceived_status()==0){
            //接单中
            helper.getView(R.id.tv_cancle).setVisibility(View.VISIBLE);
        }else{
            helper.getView(R.id.tv_cancle).setVisibility(View.GONE);
        }
        helper.getView(R.id.tv_cancle).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(callback!=null){
                    callback.onEdit(helper.getAdapterPosition(),"1",item);
                }

            }
        });
        helper.getView(R.id.tv_chat).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(callback!=null){
                    callback.onEdit(helper.getAdapterPosition(),"2",item);
                }
            }
        });
        helper.getView(R.id.llayout_root).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RewardOrderDetailActivity.start(mContext,item.getOrder_sn(),item.getId());
            }
        });
    }

    public interface Callback {
        void onEdit(int position,String isUp,TakeRewardBean.ListBean item);
    }
}
