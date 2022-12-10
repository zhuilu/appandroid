package com.xinniu.android.qiqueqiao.adapter;

import android.content.Context;
import androidx.annotation.Nullable;
//import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xinniu.android.qiqueqiao.R;
import com.xinniu.android.qiqueqiao.activity.TransactionDetailsActivity;
import com.xinniu.android.qiqueqiao.bean.CashWithdrawalBean;
import com.xinniu.android.qiqueqiao.bean.GuaranteeOrderBean;
import com.xinniu.android.qiqueqiao.user.UserInfoHelper;
import com.xinniu.android.qiqueqiao.utils.TimeUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yuchance on 2018/6/22.
 */

public class MyTransactionOrdersAdapter extends BaseQuickAdapter<GuaranteeOrderBean.ListBean, BaseViewHolder> {

    private List<GuaranteeOrderBean.ListBean> datas = new ArrayList<>();
    private Context mContext;

    public MyTransactionOrdersAdapter(Context mContext, int layoutResId, @Nullable List<GuaranteeOrderBean.ListBean> data) {
        super(layoutResId, data);
        this.datas = data;
        this.mContext = mContext;
    }

    @Override
    protected void convert(final BaseViewHolder helper, final GuaranteeOrderBean.ListBean item) {
        helper.setText(R.id.tv_time, TimeUtils.time2ActTime(item.getCreate_time() * 1000));
        helper.setText(R.id.tv_name, item.getObj_user_name());
        helper.setText(R.id.tv_name_1, item.getObj_user_name());
        if (item.getEstimated_amount().contains(".")) {
            String[] pricr01 = item.getEstimated_amount().split("\\.");
            helper.setText(R.id.tv_price_d,  pricr01[0]);
        } else {
            helper.setText(R.id.tv_price_d, item.getEstimated_amount());
        }
        if (item.getSettlement_amount().contains(".")) {
            String[] pricr01 = item.getSettlement_amount().split("\\.");
            helper.setText(R.id.tv_price_j,  pricr01[0]);
        } else {
            helper.setText(R.id.tv_price_j, item.getSettlement_amount());
        }

        //0：甲方合同创建，乙方还未同意，
        // 1：合同已结束，
        // 2乙方同意未打款，
        // 3是已打款后台未确认，
        // 4：后台已确认合同进行中，
        // 5是乙方未同意合同废弃，
        // 6：后台未确认之前取消合同，
        // 7卖方未确认交易超时
        //8买方未支付超时
        //9买方申请退款超时成功
        int status = item.getStatus();
        int is_initiate = item.getIs_initiate();   //是否发起人
        int party_a_user_id = item.getParty_a_user_id();//甲方UID 买家
        int mUserId = UserInfoHelper.getIntance().getUserId();//当前登录的账号id
        boolean isBuyer;//当前账号是否是买家
        if (party_a_user_id == mUserId) {
            isBuyer = true;
        } else {
            isBuyer = false;
        }
        RelativeLayout rlayout_price_d = helper.getView(R.id.rlayout_price_d);
        RelativeLayout rlayout_price_j = helper.getView(R.id.rlayout_price_j);
        LinearLayout llayout_button = helper.getView(R.id.llayout_button);
        final TextView btn_01 = helper.getView(R.id.btn_01);
        final TextView btn_02 = helper.getView(R.id.btn_02);
        TextView tv_person = helper.getView(R.id.tv_person);
        TextView tv_name = helper.getView(R.id.tv_name);

        if (status == 0) {
            rlayout_price_d.setVisibility(View.VISIBLE);
            rlayout_price_j.setVisibility(View.GONE);
            llayout_button.setVisibility(View.VISIBLE);
            tv_person.setVisibility(View.VISIBLE);
            tv_name.setVisibility(View.VISIBLE);
            if (is_initiate == 1) {
                helper.setText(R.id.tv_status, "等待对方确认中");
                btn_02.setVisibility(View.GONE);
                btn_01.setVisibility(View.VISIBLE);
                btn_01.setText("取消交易");
                btn_01.setBackgroundResource(R.drawable.bg_trans_gray);
                btn_01.setTextColor(mContext.getResources().getColor(R.color._333));

            } else {
                helper.setText(R.id.tv_status, "确认中");
                btn_02.setVisibility(View.VISIBLE);
                btn_01.setVisibility(View.VISIBLE);
                btn_01.setText("取消交易");
                btn_01.setBackgroundResource(R.drawable.bg_trans_gray);
                btn_01.setTextColor(mContext.getResources().getColor(R.color._333));
                btn_02.setText("确认交易");
                btn_02.setBackgroundResource(R.drawable.bg_trans_blue);
                btn_02.setTextColor(mContext.getResources().getColor(R.color.blue_bg_4B96F3));

            }

        } else if (status == 1) {
            rlayout_price_d.setVisibility(View.VISIBLE);
            llayout_button.setVisibility(View.GONE);
            if (item.getRefund_status() == 0) {
                helper.setText(R.id.tv_status, "交易完成");
                rlayout_price_j.setVisibility(View.GONE);
                tv_person.setVisibility(View.VISIBLE);
                tv_name.setVisibility(View.VISIBLE);
            } else if (item.getRefund_status() == 2) {
                helper.setText(R.id.tv_status, "交易关闭");
                rlayout_price_j.setVisibility(View.VISIBLE);
                tv_person.setVisibility(View.INVISIBLE);
                tv_name.setVisibility(View.INVISIBLE);
            }


        } else if (status == 2) {
            // 2乙方同意未打款，
            rlayout_price_d.setVisibility(View.VISIBLE);
            rlayout_price_j.setVisibility(View.GONE);
            llayout_button.setVisibility(View.VISIBLE);
            tv_person.setVisibility(View.VISIBLE);
            tv_name.setVisibility(View.VISIBLE);
            btn_02.setVisibility(View.GONE);
            btn_01.setVisibility(View.VISIBLE);
            if (isBuyer) {
                //我是买家,付钱
                helper.setText(R.id.tv_status, "待支付");
                btn_01.setText("去支付");
                btn_01.setBackgroundResource(R.drawable.bg_trans_blue);
                btn_01.setTextColor(mContext.getResources().getColor(R.color.blue_bg_4B96F3));

            } else {
                //我是卖家，等待付钱
                helper.setText(R.id.tv_status, "等待对方支付中");
                btn_01.setText("取消交易");
                btn_01.setBackgroundResource(R.drawable.bg_trans_gray);
                btn_01.setTextColor(mContext.getResources().getColor(R.color._333));

            }

        } else if (status == 3) {
            //是已打款后台未确认
            rlayout_price_d.setVisibility(View.VISIBLE);
            rlayout_price_j.setVisibility(View.GONE);
            llayout_button.setVisibility(View.VISIBLE);
            tv_person.setVisibility(View.VISIBLE);
            tv_name.setVisibility(View.VISIBLE);
            btn_02.setVisibility(View.GONE);
            btn_01.setVisibility(View.VISIBLE);
            if (isBuyer) {
                //我是买家,付钱
                helper.setText(R.id.tv_status, "待支付");
                btn_01.setText("支付详情");
                btn_01.setBackgroundResource(R.drawable.bg_trans_organe);
                btn_01.setTextColor(mContext.getResources().getColor(R.color.orange));

            } else {
                //我是卖家，等待付钱
                helper.setText(R.id.tv_status, "等待对方支付中");
                btn_01.setText("取消交易");
                btn_01.setBackgroundResource(R.drawable.bg_trans_gray);
                btn_01.setTextColor(mContext.getResources().getColor(R.color._333));

            }

        } else if (status == 4) {
            //4：后台已确认合同进行中
            rlayout_price_d.setVisibility(View.VISIBLE);
            rlayout_price_j.setVisibility(View.VISIBLE);
            llayout_button.setVisibility(View.VISIBLE);
            tv_person.setVisibility(View.INVISIBLE);
            tv_name.setVisibility(View.INVISIBLE);

            if (isBuyer) {
                helper.setText(R.id.tv_status, "已支付");
                btn_02.setVisibility(View.VISIBLE);
                btn_01.setVisibility(View.VISIBLE);
                btn_01.setText("全额结算");
                btn_01.setBackgroundResource(R.drawable.bg_trans_blue);
                btn_01.setTextColor(mContext.getResources().getColor(R.color.blue_bg_4B96F3));
                btn_02.setText("部分结算");
                btn_02.setBackgroundResource(R.drawable.bg_trans_blue);
                btn_02.setTextColor(mContext.getResources().getColor(R.color.blue_bg_4B96F3));
                if (item.getRefund_status() == 1) {
                    //买方申请退款中
                    btn_02.setVisibility(View.GONE);
                    btn_01.setVisibility(View.GONE);
                    helper.setText(R.id.tv_status, "退款中");
                } else {

                }
            } else {
                helper.setText(R.id.tv_status, "对方已支付");
                btn_02.setVisibility(View.GONE);
                btn_01.setVisibility(View.VISIBLE);
                btn_01.setText("申请结算");
                btn_01.setBackgroundResource(R.drawable.bg_trans_blue);
                btn_01.setTextColor(mContext.getResources().getColor(R.color.blue_bg_4B96F3));
                if (item.getRefund_status() == 1) {
                    //买方申请退款中
                    btn_02.setVisibility(View.GONE);
                    btn_01.setVisibility(View.GONE);
                    helper.setText(R.id.tv_status, "退款中");
                } else {

                }

            }

        } else if (status == 5) {
            //是接收方不同意发起担保交易
            rlayout_price_d.setVisibility(View.VISIBLE);
            rlayout_price_j.setVisibility(View.GONE);
            llayout_button.setVisibility(View.GONE);
            tv_person.setVisibility(View.VISIBLE);
            tv_name.setVisibility(View.VISIBLE);
            helper.setText(R.id.tv_status, "交易已取消");

        } else if (status == 6) {
            //取消交易，进行判断取消操作是由谁发起的   取消人cancel_user_id进行判断
            rlayout_price_d.setVisibility(View.VISIBLE);
            rlayout_price_j.setVisibility(View.GONE);
            llayout_button.setVisibility(View.GONE);
            tv_person.setVisibility(View.VISIBLE);
            tv_name.setVisibility(View.VISIBLE);
            helper.setText(R.id.tv_status, "交易已取消");
        } else if (status == 7) {
            //接收方未确认交易超时
            rlayout_price_d.setVisibility(View.VISIBLE);
            rlayout_price_j.setVisibility(View.GONE);
            llayout_button.setVisibility(View.GONE);
            tv_person.setVisibility(View.VISIBLE);
            tv_name.setVisibility(View.VISIBLE);
            helper.setText(R.id.tv_status, "交易已取消");
        } else if (status == 8) {
            //买家未支付超时
            rlayout_price_d.setVisibility(View.VISIBLE);
            rlayout_price_j.setVisibility(View.GONE);
            llayout_button.setVisibility(View.GONE);
            tv_person.setVisibility(View.VISIBLE);
            tv_name.setVisibility(View.VISIBLE);
            helper.setText(R.id.tv_status, "交易已取消");
        } else if (status == 9) {
            //买家申请退款超时，退款成功

            rlayout_price_d.setVisibility(View.VISIBLE);
            rlayout_price_j.setVisibility(View.VISIBLE);
            llayout_button.setVisibility(View.GONE);
            tv_person.setVisibility(View.INVISIBLE);
            tv_name.setVisibility(View.INVISIBLE);
            helper.setText(R.id.tv_status, "交易关闭");

        }

        btn_01.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (setOnClick != null) {
                    setOnClick.setOnClickListeren(item, btn_01.getText().toString());
                }
            }
        });

        btn_02.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (setOnClick != null) {
                    setOnClick.setOnClickListeren(item, btn_02.getText().toString());
                }
            }
        });
        helper.getView(R.id.llayout_root).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (setOnClick != null) {
                    setOnClick.setOnClickListeren(item, "1");
                }


            }
        });
    }


    public interface setOnClick {
        void setOnClickListeren(GuaranteeOrderBean.ListBean listeren, String type);
    }

    private setOnClick setOnClick;

    public void setSetOnClick(MyTransactionOrdersAdapter.setOnClick setOnClick) {
        this.setOnClick = setOnClick;
    }
}
