package com.xinniu.android.qiqueqiao.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xinniu.android.qiqueqiao.R;
import com.xinniu.android.qiqueqiao.activity.PersonCentetActivity;
import com.xinniu.android.qiqueqiao.activity.RewardDetailActivity;
import com.xinniu.android.qiqueqiao.bean.RewardListBean;
import com.xinniu.android.qiqueqiao.utils.ImageLoader;
import com.xinniu.android.qiqueqiao.utils.StringUtils;

import java.util.List;
//import android.support.v4.content.ContextCompat;

/**
 * Created by yuchance on 2018/3/30.
 */

public class RewardListAdapter extends BaseQuickAdapter<RewardListBean.ListBean, BaseViewHolder> {
    private AppCompatActivity context;

    public RewardListAdapter(AppCompatActivity context, int layoutResId, @Nullable List<RewardListBean.ListBean> data) {
        super(layoutResId, data);
        this.context = context;
    }


    @Override
    protected void convert(BaseViewHolder helper, final RewardListBean.ListBean item) {

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
            nameTv.setTextColor(ContextCompat.getColor(context, R.color.king_color));

        } else if (item.getIs_vip() == 0) {
            helper.getView(R.id.item_index_new_kingimg).setVisibility(View.GONE);
            nameTv.setTextColor(ContextCompat.getColor(context, R.color._333));

        } else if (item.getIs_vip() == 2) {
            helper.getView(R.id.item_index_new_kingimg).setVisibility(View.VISIBLE);
            helper.setBackgroundRes(R.id.item_index_new_kingimg, R.mipmap.svip_iconx);
            nameTv.setTextColor(ContextCompat.getColor(context, R.color.king_color));

        }
        if (item.getIs_v() == 1) {
            helper.setVisible(R.id.index_new_isv, true);
        } else {
            helper.setVisible(R.id.index_new_isv, false);
        }

        helper.setText(R.id.tv_title, item.getTitle());
        helper.setText(R.id.tv_content, item.getDetail());
        if (item.getAmount().contains(".")) {
            String[] pricr01 = item.getAmount().split("\\.");
            helper.setText(R.id.tv_price,  pricr01[0]);
        } else {
            helper.setText(R.id.tv_price, item.getAmount());
        }

        if (item.getRemaining_number() == 0||item.getStatus()==2) {
            helper.getView(R.id.img_status).setVisibility(View.VISIBLE);
        }else{
            helper.getView(R.id.img_status).setVisibility(View.GONE);
        }
        helper.getView(R.id.rlayout_data).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PersonCentetActivity.start(context, item.getUser_id()+"");
            }
        });

        helper.getView(R.id.llayout_root).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RewardDetailActivity.start(context, item.getOrder_sn());
            }
        });
    }



}
