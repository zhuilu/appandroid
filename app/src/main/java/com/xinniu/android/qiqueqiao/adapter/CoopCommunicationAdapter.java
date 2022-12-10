package com.xinniu.android.qiqueqiao.adapter;

import androidx.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xinniu.android.qiqueqiao.R;
import com.xinniu.android.qiqueqiao.bean.CoopDetailBean;
import com.xinniu.android.qiqueqiao.utils.ImageLoader;
import com.xinniu.android.qiqueqiao.widget.CircleImageView;

import java.util.List;

/**
 * Created by yuchance on 2018/10/25.
 */

public class CoopCommunicationAdapter extends BaseQuickAdapter<CoopDetailBean.ZListBean,BaseViewHolder> {



    public CoopCommunicationAdapter(int layoutResId, @Nullable List<CoopDetailBean.ZListBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, CoopDetailBean.ZListBean item) {
        helper.setText(R.id.item_coop_name,item.getRealname());
        if (item.getCompany() == null){
            helper.setText(R.id.item_coop_postion,item.getPosition());
        }else {
            helper.setText(R.id.item_coop_postion, item.getCompany() + "|" + item.getPosition());
        }
        if(item.getIs_corporate_vip()==1){
            helper.getView(R.id.company_vip_img).setVisibility(View.VISIBLE);
            helper.getView(R.id.item_coop_isvip).setVisibility(View.GONE);
        }else {
            helper.getView(R.id.company_vip_img).setVisibility(View.GONE);
            helper.getView(R.id.item_coop_isvip).setVisibility(View.VISIBLE);
            if (item.getIs_vip() == 1) {
                helper.setBackgroundRes(R.id.item_coop_isvip, R.mipmap.vip_iconx);
            } else if (item.getIs_vip() == 2) {
                helper.setBackgroundRes(R.id.item_coop_isvip, R.mipmap.svip_iconx);
            } else {
                helper.getView(R.id.item_coop_isvip).setVisibility(View.GONE);
            }
        }
        ImageLoader.loadAvter(item.getHead_pic(), (CircleImageView) helper.getView(R.id.item_coop_img));


    }
}
