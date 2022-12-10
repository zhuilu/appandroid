package com.xinniu.android.qiqueqiao.adapter;

import android.content.Context;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xinniu.android.qiqueqiao.R;
import com.xinniu.android.qiqueqiao.activity.WalletDetailActivity;
import com.xinniu.android.qiqueqiao.bean.CashWithdrawalBean;
import com.xinniu.android.qiqueqiao.utils.TimeUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yuchance on 2018/6/22.
 */

public class MyDiscountRecordAdapter extends BaseQuickAdapter<CashWithdrawalBean.ListBean, BaseViewHolder> {

    private List<CashWithdrawalBean.ListBean> datas = new ArrayList<>();
    private Context mContext;

    public MyDiscountRecordAdapter(Context mContext, int layoutResId, @Nullable List<CashWithdrawalBean.ListBean> data) {
        super(layoutResId, data);
        this.datas = data;
        this.mContext = mContext;
    }

    @Override
    protected void convert(final BaseViewHolder helper, final CashWithdrawalBean.ListBean item) {
        TextView tv_status = helper.getView(R.id.tv_status);
        helper.setText(R.id.tv_price, item.getWithdrawals_amount());
        helper.setText(R.id.tv_time, TimeUtils.time2ActTime(item.getCreate_time() * 1000));
        //到账账户类型，1：支付宝 ，2 ：银行卡
        if (item.getAccount_type() == 1) {
            helper.setText(R.id.tv_type, "支付宝");
        } else {
            helper.setText(R.id.tv_type, "银行卡");
        }

        //提现状态 0 提现申请，1成功，2失败
        if (item.getStatus() == 0) {

            helper.setText(R.id.tv_status, "等待到账");
            tv_status.setTextColor(ContextCompat.getColor(mContext, R.color.orange_ffa34a));
        } else if (item.getStatus() == 1) {

            helper.setText(R.id.tv_status, "提现已到账");
            tv_status.setTextColor(ContextCompat.getColor(mContext, R.color.color_aaaaaa));
        } else if (item.getStatus() == 2) {

            helper.setText(R.id.tv_status, "打款失败");
            tv_status.setTextColor(ContextCompat.getColor(mContext, R.color.bg_color_red_DE6654));
        }
        final RelativeLayout itemRl = helper.getView(R.id.llayout_root);
        itemRl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //详情
                WalletDetailActivity.start(mContext, item.getWater_bills_id());

            }
        });

    }


}
