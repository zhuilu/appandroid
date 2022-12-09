package com.xinniu.android.qiqueqiao.adapter;

import android.content.Context;
//import android.support.annotation.Nullable;
//import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xinniu.android.qiqueqiao.R;
import com.xinniu.android.qiqueqiao.activity.CoopDetailActivity;
import com.xinniu.android.qiqueqiao.bean.GetFriendListBean;
import com.xinniu.android.qiqueqiao.utils.ImageLoader;
import com.xinniu.android.qiqueqiao.utils.ShowUtils;

import java.util.List;

/**
 * Created by yuchance on 2018/12/5.
 */

public class FriendNxAdapter extends BaseQuickAdapter<GetFriendListBean.GroupBean.ListBean, BaseViewHolder> {

    private Context context;

    public FriendNxAdapter(Context context, int layoutResId, @Nullable List<GetFriendListBean.GroupBean.ListBean> data) {
        super(layoutResId, data);
        this.context = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, GetFriendListBean.GroupBean.ListBean item) {
        helper.setText(R.id.lx_nametv, item.getRealname());
        int isVip = item.getIs_vip();
        TextView nameTv = helper.getView(R.id.lx_nametv);
        if (isVip == 0) {
            helper.getView(R.id.item_index_vipImg).setVisibility(View.GONE);
            nameTv.setTextColor(ContextCompat.getColor(context, R.color._333));

        } else if (isVip == 1) {
            helper.getView(R.id.item_index_vipImg).setVisibility(View.VISIBLE);
            helper.setBackgroundRes(R.id.item_index_vipImg, R.mipmap.vip_iconx);
            nameTv.setTextColor(ContextCompat.getColor(context, R.color.king_color));
        } else if (isVip == 2) {
            helper.getView(R.id.item_index_vipImg).setVisibility(View.VISIBLE);
            helper.setBackgroundRes(R.id.item_index_vipImg, R.mipmap.svip_iconx);
            nameTv.setTextColor(ContextCompat.getColor(context, R.color.king_color));
        }
        helper.setText(R.id.lx_positiontv, item.getCompany() + "|" + item.getPosition());
        ImageLoader.loadAvter(item.getHead_pic(), (ImageView) helper.getView(R.id.item_lx_headimg));
    }
}
