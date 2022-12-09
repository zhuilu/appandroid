package com.xinniu.android.qiqueqiao.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.xinniu.android.qiqueqiao.R;
import com.xinniu.android.qiqueqiao.base.BaseActivity;
import com.xinniu.android.qiqueqiao.bean.WalletDetailBean;
import com.xinniu.android.qiqueqiao.request.RequestManager;
import com.xinniu.android.qiqueqiao.request.callback.GetWalletDetailCallback;
import com.xinniu.android.qiqueqiao.utils.TimeUtils;
import com.xinniu.android.qiqueqiao.utils.ToastUtils;

import butterknife.BindView;
import butterknife.OnClick;

public class WalletDetailActivity extends BaseActivity {
    @BindView(R.id.tv_status)
    TextView tvStatus;
    @BindView(R.id.tv_status_memo)
    TextView tvStatusMemo;
    @BindView(R.id.tv_price)
    TextView tvPrice;
    @BindView(R.id.tv)
    TextView tv;
    @BindView(R.id.view)
    View view;
    @BindView(R.id.tv_detail_type)
    TextView tvDetailType;
    @BindView(R.id.tv_time)
    TextView tvTime;
    @BindView(R.id.tv_order)
    TextView tvOrder;
    @BindView(R.id.tv_order_sn)
    TextView tvOrderSn;
    @BindView(R.id.rlayout_tran_detail)
    RelativeLayout rlayoutTranDetail;
    @BindView(R.id.llayout_01)
    LinearLayout llayout01;
    @BindView(R.id.tv_detail_status)
    TextView tvDetailStatus;
    @BindView(R.id.tv_account_name)
    TextView tvAccountName;
    @BindView(R.id.tv_service_charge)
    TextView tvServiceCharge;
    @BindView(R.id.tv_actual_achievement)
    TextView tvActualAchievement;
    @BindView(R.id.tv_create_time)
    TextView tvCreateTime;
    @BindView(R.id.tv_order_sn_1)
    TextView tvOrderSn1;
    @BindView(R.id.llayout_02)
    LinearLayout llayout02;

    private int mId;
    private WalletDetailBean mBean;

    public static void start(Context context, int id) {
        Intent starter = new Intent(context, WalletDetailActivity.class);
        starter.putExtra("id", id);
        context.startActivity(starter);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_wallet_detail;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        topStatusBar(true);
        Intent intent = getIntent();
        mId = intent.getIntExtra("id", -1);
        getData();
    }

    private void getData() {
        showBookingToast(1);
        RequestManager.getInstance().billDetail(mId, new GetWalletDetailCallback() {
            @Override
            public void onSuccess(WalletDetailBean item) {
                mBean = item;
                int status = item.getEvent_status();//1:提现，2：退款，3：担保交易结算收入，4提现失败收入,5：悬赏退款收入，6：悬赏结算收入
                if (status == 3) {
                    tvStatus.setText("交易到账");

                    tvStatusMemo.setVisibility(View.VISIBLE);
                    //1支出-，2收入+
                    if (item.getEvent_type() == 1) {
                        tvStatusMemo.setText("-");
                        tvPrice.setText(item.getDisburse_amount());
                    } else if (item.getEvent_type() == 2) {
                        tvStatusMemo.setText("+");
                        tvPrice.setText(item.getIncome_amount());
                    }

                    tv.setVisibility(View.GONE);
                    view.setVisibility(View.GONE);
                    llayout01.setVisibility(View.VISIBLE);
                    llayout02.setVisibility(View.GONE);
                    tvDetailType.setText("担保交易");
                    rlayoutTranDetail.setVisibility(View.VISIBLE);
                    tvTime.setText(TimeUtils.time2ActTime(item.getCreate_time() * 1000));
                    tvOrder.setText(item.getOrder_sn());
                    tvOrderSn.setText(item.getGuarantee_order_sn());
                } else if (status == 2) {
                    tvStatus.setText("退款");
                    tvStatusMemo.setVisibility(View.VISIBLE);
                    tvStatusMemo.setVisibility(View.VISIBLE);
                    //1支出-，2收入+
                    if (item.getEvent_type() == 1) {
                        tvStatusMemo.setText("-");
                        tvPrice.setText(item.getDisburse_amount());
                    } else if (item.getEvent_type() == 2) {
                        tvStatusMemo.setText("+");
                        tvPrice.setText(item.getIncome_amount());
                    }
                    tv.setVisibility(View.GONE);
                    view.setVisibility(View.GONE);
                    llayout01.setVisibility(View.VISIBLE);
                    llayout02.setVisibility(View.GONE);
                    rlayoutTranDetail.setVisibility(View.VISIBLE);
                    tvDetailType.setText("担保交易");
                    tvTime.setText(TimeUtils.time2ActTime(item.getCreate_time() * 1000));
                    tvOrder.setText(item.getOrder_sn());
                    tvOrderSn.setText(item.getGuarantee_order_sn());
                } else if (status == 1) {
                    int withdraw_status = item.getWithdraw_status();//	提现状态 0 提现申请，1成功，2失败,-1不是提现
                    tvPrice.setText(item.getDisburse_amount());
                    tvStatusMemo.setVisibility(View.GONE);
                    view.setVisibility(View.VISIBLE);
                    llayout01.setVisibility(View.GONE);
                    llayout02.setVisibility(View.VISIBLE);

                    if (item.getPay_type() == 2) {//	1：支付宝 ，2 ：银行卡 3：担保交易
                        tvAccountName.setText("银行卡");
                    } else if (item.getPay_type() == 1) {//	1：支付宝 ，2 ：银行卡 3：担保交易
                        tvAccountName.setText("支付宝");
                    }
                    tvServiceCharge.setText("-￥" + item.getHandling_fee());
                    tvActualAchievement.setText("￥" + item.getActual_amount_achieved());
                    tvCreateTime.setText(TimeUtils.time2ActTime(item.getCreate_time() * 1000));
                    tvOrderSn1.setText(item.getOrder_sn());

                    if (withdraw_status == 0) {
                        tvStatus.setText("提现中");
                        tv.setVisibility(View.VISIBLE);
                        tvDetailStatus.setText("等待到账");
                    } else if (withdraw_status == 1) {
                        tvStatus.setText("提现成功");
                        tv.setVisibility(View.GONE);
                        tvDetailStatus.setText("提现已到账");
                    } else if (withdraw_status == 2) {
                        tvStatus.setText("提现失败");
                        tv.setVisibility(View.GONE);
                        tvDetailStatus.setText("打款失败");
                    }


                } else if (status == 4) {

                    tvStatus.setText("交易到账");
                    tvStatusMemo.setVisibility(View.VISIBLE);
                    tvStatusMemo.setVisibility(View.VISIBLE);
                    //1支出-，2收入+
                    if (item.getEvent_type() == 1) {
                        tvStatusMemo.setText("-");
                        tvPrice.setText(item.getDisburse_amount());
                    } else if (item.getEvent_type() == 2) {
                        tvStatusMemo.setText("+");
                        tvPrice.setText(item.getIncome_amount());
                    }
                    tv.setVisibility(View.GONE);
                    view.setVisibility(View.GONE);
                    llayout01.setVisibility(View.VISIBLE);
                    llayout02.setVisibility(View.GONE);
                    tvDetailType.setText("提现失败退回");
                    tvTime.setText(TimeUtils.time2ActTime(item.getCreate_time() * 1000));
                    tvOrder.setText(item.getOrder_sn());
                    rlayoutTranDetail.setVisibility(View.GONE);

                } else if (status == 6) {
                    tvStatus.setText("交易到账");

                    tvStatusMemo.setVisibility(View.VISIBLE);
                    //1支出-，2收入+
                    if (item.getEvent_type() == 1) {
                        tvStatusMemo.setText("-");
                        tvPrice.setText(item.getDisburse_amount());
                    } else if (item.getEvent_type() == 2) {
                        tvStatusMemo.setText("+");
                        tvPrice.setText(item.getIncome_amount());
                    }

                    tv.setVisibility(View.GONE);
                    view.setVisibility(View.GONE);
                    llayout01.setVisibility(View.VISIBLE);
                    llayout02.setVisibility(View.GONE);
                    tvDetailType.setText("悬赏订单");
                    rlayoutTranDetail.setVisibility(View.VISIBLE);
                    tvTime.setText(TimeUtils.time2ActTime(item.getCreate_time() * 1000));
                    tvOrder.setText(item.getOrder_sn());
                    tvOrderSn.setText(item.getReward_order_sn());
                } else if (status == 5) {
                    tvStatus.setText("退款");

                    tvStatusMemo.setVisibility(View.VISIBLE);
                    //1支出-，2收入+
                    if (item.getEvent_type() == 1) {
                        tvStatusMemo.setText("-");
                        tvPrice.setText(item.getDisburse_amount());
                    } else if (item.getEvent_type() == 2) {
                        tvStatusMemo.setText("+");
                        tvPrice.setText(item.getIncome_amount());
                    }

                    tv.setVisibility(View.GONE);
                    view.setVisibility(View.GONE);
                    llayout01.setVisibility(View.VISIBLE);
                    llayout02.setVisibility(View.GONE);
                    tvDetailType.setText("悬赏订单");
                    rlayoutTranDetail.setVisibility(View.VISIBLE);
                    tvTime.setText(TimeUtils.time2ActTime(item.getCreate_time() * 1000));
                    tvOrder.setText(item.getOrder_sn());
                    tvOrderSn.setText(item.getReward_order_sn());
                }


                dismissBookingToast();
            }

            @Override
            public void onFailed(int code, String msg) {
                dismissBookingToast();
                ToastUtils.showCentetToast(WalletDetailActivity.this, msg);

            }
        });
    }

    @OnClick({R.id.bt_finish, R.id.rlayout_tran_detail})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_finish:
                finish();
                break;
            case R.id.rlayout_tran_detail:
                //交易详情
                if (mBean.getEvent_status() == 5) {
                    //已接单人
                    AcceptedOrdersPersonActivity.start(WalletDetailActivity.this, mBean.getReward_order_sn());

                } else if (mBean.getEvent_status() == 6) {
                    //悬赏订单
                    RewardOrderDetailActivity.start(WalletDetailActivity.this, mBean.getReward_order_sn(), mBean.getReceived_id());
                } else {
                    TransactionDetailsActivity.start(WalletDetailActivity.this, mBean.getGuarantee_id() + "");
                }
                break;
        }
    }
}
