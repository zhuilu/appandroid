package com.xinniu.android.qiqueqiao.adapter.base;

import android.content.Context;
//import android.support.annotation.Nullable;
//import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xinniu.android.qiqueqiao.R;
import com.xinniu.android.qiqueqiao.activity.PersonCentetActivity;
import com.xinniu.android.qiqueqiao.bean.CoopDetailBean;
import com.xinniu.android.qiqueqiao.utils.ImageLoader;
import com.xinniu.android.qiqueqiao.utils.TimeUtils;

import java.util.List;

/**
 * Created by yuchance on 2018/3/31.
 */

public class CoopLxAdapter extends BaseQuickAdapter<CoopDetailBean.ZListBean,BaseViewHolder> {

    private Context context;

    public CoopLxAdapter(int layoutResId, @Nullable List<CoopDetailBean.ZListBean> data,Context context) {
        super(layoutResId, data);
        this.context = context;
    }

    public CoopLxAdapter(int layoutResId, @Nullable List<CoopDetailBean.ZListBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, final CoopDetailBean.ZListBean item) {
        helper.setText(R.id.item_index_nameTv,item.getRealname());
        helper.setText(R.id.item_index_position," · " + item.getCompany()+item.getPosition());
        if (item.getIs_vip()==0){
            helper.setVisible(R.id.coop_talk_list_img,false);
            helper.setTextColor(R.id.item_index_nameTv, ContextCompat.getColor(context,R.color.text_color_1));
        }else if (item.getIs_vip() == 1){
            helper.setBackgroundRes(R.id.coop_talk_list_img,R.mipmap.vip_iconx);
            helper.setTextColor(R.id.item_index_nameTv, ContextCompat.getColor(context,R.color.king_color));
        }else if (item.getIs_vip() == 2){
            helper.setBackgroundRes(R.id.coop_talk_list_img,R.mipmap.svip_iconx);
            helper.setTextColor(R.id.item_index_nameTv, ContextCompat.getColor(context,R.color.king_color));
        }
        long lxTime = item.getCreate_time();
        helper.setText(R.id.coop_detail_time, TimeUtils.getTimeStateNew(lxTime+""));
        ImageLoader.loadAvter(item.getHead_pic(), (ImageView) helper.getView(R.id.item_index_recycler_img));
        (helper.getView(R.id.item_coop_call)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PersonCentetActivity.start(context,item.getUser_id()+"");
            }
        });




    }
}
