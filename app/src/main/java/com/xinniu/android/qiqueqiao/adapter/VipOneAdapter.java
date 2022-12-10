package com.xinniu.android.qiqueqiao.adapter;

import android.app.Activity;
import android.content.Context;
import androidx.annotation.Nullable;
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

public class VipOneAdapter extends BaseQuickAdapter<VipV3Bean.VipListBean,BaseViewHolder> {

    private List<VipV3Bean.VipListBean> data = new ArrayList<>();
    private List<VipV3Bean.SvipListBean> datax = new ArrayList<>();
    private Context context;



    public VipOneAdapter(Context context,int layoutResId, @Nullable List<VipV3Bean.VipListBean> data,List<VipV3Bean.SvipListBean> datax) {
        super(layoutResId, data);
        this.data = data;
        this.datax = datax;
        this.context = context;
    }


    @Override
    protected void convert(BaseViewHolder helper, final VipV3Bean.VipListBean item) {
        RelativeLayout onevipRl = helper.getView(R.id.item_onevip_Rl);
        helper.setText(R.id.item_onevip_month, item.getName());
        helper.setText(R.id.item_onevip_time, item.getDesc());
        helper.setText(R.id.item_vip_moneytv, item.getPrice_unit()+"/");
        helper.setText(R.id.tvItemMonth,item.getPrice_suffix());
//        helper.setText(R.id.item_one_vip_perm, item.getPrice_desc());
//        if (item.isCheck()){
//            helper.setTextColor(R.id.item_onevip_month, ContextCompat.getColor(context,R.color.white));
//            helper.setTextColor(R.id.item_onevip_time, ContextCompat.getColor(context,R.color.white));
//            helper.setTextColor(R.id.item_vip_moneytv, ContextCompat.getColor(context,R.color.white));
////            helper.setTextColor(R.id.item_one_vip_perm, ContextCompat.getColor(context,R.color.white));
//            helper.setTextColor(R.id.item_one_y,ContextCompat.getColor(context,R.color.white));
//            helper.setTextColor(R.id.tvItemMonth,ContextCompat.getColor(context,R.color.white));
//            onevipRl.setBackground(ContextCompat.getDrawable(context,R.mipmap.blue_vip));
//        }else {
//            helper.setTextColor(R.id.item_onevip_month, ContextCompat.getColor(context,R.color.text_color_1));
//            helper.setTextColor(R.id.item_onevip_time, ContextCompat.getColor(context,R.color.text_color_87));
//            helper.setTextColor(R.id.item_vip_moneytv, ContextCompat.getColor(context,R.color.text_color_1));
////            helper.setTextColor(R.id.item_one_vip_perm, ContextCompat.getColor(context,R.color.text_color_87));
//            helper.setTextColor(R.id.item_one_y,ContextCompat.getColor(context,R.color.text_color_1));
//            helper.setTextColor(R.id.tvItemMonth,ContextCompat.getColor(context,R.color.text_color_1));
//            onevipRl.setBackground(ContextCompat.getDrawable(context,R.drawable.bg_item_vip));
//        }

//        onevipRl.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                for (int i = 0; i < data.size() ; i++) {
//                    data.get(i).setCheck(false);
//                }
//                for (int j = 0; j < datax.size() ; j++) {
//                    datax.get(j).setCheck(false);
//                }
//                item.setCheck(true);
//                selectVip.selectVipState(item.getAndroid_price(),item.getId());
//                notifyDataSetChanged();
//            }
//        });
    }

    private SelectVip selectVip;

    public interface SelectVip{
        void selectVipState(String price,int mealId);
    }

    public void setSelectVip(SelectVip selectVip) {
        this.selectVip = selectVip;
    }
}
