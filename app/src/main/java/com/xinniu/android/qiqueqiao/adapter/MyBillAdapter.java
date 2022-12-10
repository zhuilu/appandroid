package com.xinniu.android.qiqueqiao.adapter;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xinniu.android.qiqueqiao.R;
import com.xinniu.android.qiqueqiao.activity.WalletDetailActivity;
import com.xinniu.android.qiqueqiao.bean.BillBean;
import com.xinniu.android.qiqueqiao.utils.TimeUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yuchance on 2018/6/22.
 */

public class MyBillAdapter extends BaseQuickAdapter<BillBean.ListBean, BaseViewHolder> {

    private List<BillBean.ListBean> datas = new ArrayList<>();
    private Context mContext;

    public MyBillAdapter(Context mContext, int layoutResId, @Nullable List<BillBean.ListBean> data) {
        super(layoutResId, data);
        this.datas = data;
        this.mContext = mContext;
    }

    @Override
    protected void convert(final BaseViewHolder helper, final BillBean.ListBean item) {
        TextView tv_price=helper.getView(R.id.tv_price);
        helper.setText(R.id.tv_time, TimeUtils.time2ActTime(item.getCreate_time() * 1000));
        //1支出-，2收入+

            //	1:提现，2：担保交易退款，3：担保交易结算收入，4提现失败收入,5：悬赏退款收入，6：悬赏结算收入
            if (item.getEvent_status() == 1) {
                helper.getView(R.id.img).setBackgroundResource(R.mipmap.bill_blue);
                helper.setText(R.id.tv_status, "提现");
                if(item.getEvent_type()==1) {
                    helper.setText(R.id.tv_price, "-" + item.getDisburse_amount() + "元");
                    tv_price.setTextColor(ContextCompat.getColor(mContext, R.color._333));
                }else  if(item.getEvent_type()==2) {
                    helper.setText(R.id.tv_price, "+" + item.getIncome_amount() + "元");
                    tv_price.setTextColor(ContextCompat.getColor(mContext, R.color.bg_color_red_DE6654));
                }

            }else   if (item.getEvent_status() == 2) {
                helper.getView(R.id.img).setBackgroundResource(R.mipmap.bill_red);
                helper.setText(R.id.tv_status, "担保交易退款");
                if(item.getEvent_type()==1) {
                    helper.setText(R.id.tv_price, "-" + item.getDisburse_amount() + "元");
                    tv_price.setTextColor(ContextCompat.getColor(mContext, R.color._333));
                }else  if(item.getEvent_type()==2) {
                    helper.setText(R.id.tv_price, "+" + item.getIncome_amount() + "元");
                    tv_price.setTextColor(ContextCompat.getColor(mContext, R.color.bg_color_red_DE6654));
                }
            }else   if (item.getEvent_status() == 3) {
                helper.getView(R.id.img).setBackgroundResource(R.mipmap.bill_green);
                helper.setText(R.id.tv_status, "担保交易收款");
                if(item.getEvent_type()==1) {
                    helper.setText(R.id.tv_price, "-" + item.getDisburse_amount() + "元");
                    tv_price.setTextColor(ContextCompat.getColor(mContext, R.color._333));
                }else  if(item.getEvent_type()==2) {
                    helper.setText(R.id.tv_price, "+" + item.getIncome_amount() + "元");
                    tv_price.setTextColor(ContextCompat.getColor(mContext, R.color.bg_color_red_DE6654));
                }
            }else   if (item.getEvent_status() == 4) {
                helper.getView(R.id.img).setBackgroundResource(R.mipmap.bill_orange);
                helper.setText(R.id.tv_status, "提现失败");
                if(item.getEvent_type()==1) {
                    helper.setText(R.id.tv_price, "-" + item.getDisburse_amount() + "元");
                    tv_price.setTextColor(ContextCompat.getColor(mContext, R.color._333));
                }else  if(item.getEvent_type()==2) {
                    helper.setText(R.id.tv_price, "+" + item.getIncome_amount() + "元");
                    tv_price.setTextColor(ContextCompat.getColor(mContext, R.color.bg_color_red_DE6654));
                }
            }else   if (item.getEvent_status() == 5) {
                helper.getView(R.id.img).setBackgroundResource(R.mipmap.bill_red);
                helper.setText(R.id.tv_status, "悬赏订单退款");
                if(item.getEvent_type()==1) {
                    helper.setText(R.id.tv_price, "-" + item.getDisburse_amount() + "元");
                    tv_price.setTextColor(ContextCompat.getColor(mContext, R.color._333));
                }else  if(item.getEvent_type()==2) {
                    helper.setText(R.id.tv_price, "+" + item.getIncome_amount() + "元");
                    tv_price.setTextColor(ContextCompat.getColor(mContext, R.color.bg_color_red_DE6654));
                }
            }else   if (item.getEvent_status() == 6) {
                helper.getView(R.id.img).setBackgroundResource(R.mipmap.bill_blue_two);
                helper.setText(R.id.tv_status, "悬赏订单收款");
                if(item.getEvent_type()==1) {
                    helper.setText(R.id.tv_price, "-" + item.getDisburse_amount() + "元");
                    tv_price.setTextColor(ContextCompat.getColor(mContext, R.color._333));
                }else  if(item.getEvent_type()==2) {
                    helper.setText(R.id.tv_price, "+" + item.getIncome_amount() + "元");
                    tv_price.setTextColor(ContextCompat.getColor(mContext, R.color.bg_color_red_DE6654));
                }
            }



        final LinearLayout itemRl = helper.getView(R.id.llayout_root);
        itemRl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //详情
                WalletDetailActivity.start(mContext,item.getId());

            }
        });

    }


}
