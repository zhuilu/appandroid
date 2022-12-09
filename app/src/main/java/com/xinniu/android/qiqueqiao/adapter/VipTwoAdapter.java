package com.xinniu.android.qiqueqiao.adapter;

import android.app.Activity;
import android.content.Context;
//import android.support.annotation.Nullable;
//import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.RelativeLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xinniu.android.qiqueqiao.R;
import com.xinniu.android.qiqueqiao.bean.VipV3Bean;
import com.xinniu.android.qiqueqiao.utils.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yuchance on 2018/4/19.
 */

public class VipTwoAdapter extends BaseQuickAdapter<VipV3Bean.SvipListBean,BaseViewHolder>  {

    private Activity mContext;
    private List<VipV3Bean.SvipListBean> data = new ArrayList<>();
    private List<VipV3Bean.VipListBean> datax = new ArrayList<>();

    public VipTwoAdapter(Activity context, int layoutResId, @Nullable List<VipV3Bean.SvipListBean> data, List<VipV3Bean.VipListBean> datax) {
        super(layoutResId, data);
        this.mContext = context;
        this.data = data;
        this.datax = datax;
    }

    @Override
    protected void convert(BaseViewHolder helper, final VipV3Bean.SvipListBean item) {
        RelativeLayout onevipRl = helper.getView(R.id.item_twovip_Rl);
        helper.setText(R.id.item_twovip_month, item.getName());
        helper.setText(R.id.item_twovip_time, item.getDesc());
//        helper.setText(R.id.item_vip_moneytv, item.getPrice_unit()+"/");
//        if (item.isCheck()){
//            helper.setTextColor(R.id.item_twovip_month, ContextCompat.getColor(mContext,R.color.white));
//            helper.setTextColor(R.id.item_twovip_time, ContextCompat.getColor(mContext,R.color.white));
//            helper.setTextColor(R.id.item_vip_moneytv, ContextCompat.getColor(mContext,R.color.white));
//            helper.setTextColor(R.id.item_two_y,ContextCompat.getColor(mContext,R.color.white));
//            helper.setTextColor(R.id.tvItemMonth,ContextCompat.getColor(mContext,R.color.white));
//            onevipRl.setBackground(ContextCompat.getDrawable(mContext,R.mipmap.red_vip));
//        }else {
//            helper.setTextColor(R.id.item_twovip_month, ContextCompat.getColor(mContext,R.color.text_color_1));
//            helper.setTextColor(R.id.item_twovip_time, ContextCompat.getColor(mContext,R.color.text_color_87));
//            helper.setTextColor(R.id.item_vip_moneytv, ContextCompat.getColor(mContext,R.color.text_color_1));
//            helper.setTextColor(R.id.item_two_y,ContextCompat.getColor(mContext,R.color.text_color_1));
//            helper.setTextColor(R.id.tvItemMonth,ContextCompat.getColor(mContext,R.color.text_color_1));
//            onevipRl.setBackground(ContextCompat.getDrawable(mContext,R.drawable.bg_item_vip));
//        }

        onevipRl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                for (int i = 0; i < data.size() ; i++) {
//                    data.get(i).setCheck(false);
//                }
//                for (int j = 0; j < datax.size() ; j++) {
//                    datax.get(j).setCheck(false);
//                }
//                item.setCheck(true);
//                selectVipX.setSelectVipX(item.getAndroid_price(),item.getId());
//                notifyDataSetChanged();
            }
        });

    }

    private SelectVipX selectVipX;

    public interface SelectVipX{
        void setSelectVipX(String price,int mealId);
    }

    public void setSelectVipX(SelectVipX selectVipX) {
        this.selectVipX = selectVipX;
    }
}
