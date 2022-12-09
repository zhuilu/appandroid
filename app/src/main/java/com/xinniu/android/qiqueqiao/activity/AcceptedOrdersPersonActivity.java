package com.xinniu.android.qiqueqiao.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
//import android.support.v4.content.ContextCompat;
//import android.support.v7.widget.LinearLayoutManager;
//import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.xinniu.android.qiqueqiao.R;
import com.xinniu.android.qiqueqiao.adapter.AccptedPersonAdapter;
import com.xinniu.android.qiqueqiao.base.BaseActivity;
import com.xinniu.android.qiqueqiao.bean.AcceptedOrdersPersonBean;
import com.xinniu.android.qiqueqiao.customs.qldialog.QLTipDialog;
import com.xinniu.android.qiqueqiao.customs.qldialog.QLTipTwoDialog;
import com.xinniu.android.qiqueqiao.request.RequestManager;
import com.xinniu.android.qiqueqiao.request.callback.AllResultDoCallback;
import com.xinniu.android.qiqueqiao.request.callback.GetPersonListCallback;
//import com.xinniu.android.qiqueqiao.utils.IMUtils;
import com.xinniu.android.qiqueqiao.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class AcceptedOrdersPersonActivity extends BaseActivity {
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_price)
    TextView tvPrice;
    @BindView(R.id.ll)
    LinearLayout ll;
    @BindView(R.id.tv)
    TextView tv;
    @BindView(R.id.tv_number)
    TextView tvNumber;
    @BindView(R.id.tv_take_person_num)
    TextView tvTakePersonNum;
    @BindView(R.id.tv_status)
    TextView tvStatus;
    @BindView(R.id.tv_01)
    TextView tv01;
    @BindView(R.id.tv_number_01)
    TextView tvNumber01;
    @BindView(R.id.llayout_01)
    RelativeLayout llayout01;
    @BindView(R.id.tv_02)
    TextView tv02;
    @BindView(R.id.tv_number_02)
    TextView tvNumber02;
    @BindView(R.id.llayout_02)
    RelativeLayout llayout02;
    @BindView(R.id.tv_03)
    TextView tv03;
    @BindView(R.id.tv_number_03)
    TextView tvNumber03;
    @BindView(R.id.llayout_03)
    RelativeLayout llayout03;
    @BindView(R.id.tv_refund)
    TextView tvRefund;
    @BindView(R.id.rcy_person)
    RecyclerView rcyPerson;
    @BindView(R.id.tv_total_price)
    TextView tvTotalPrice;
    @BindView(R.id.pushtv)
    TextView pushtv;
    private String order_sn;
    private AccptedPersonAdapter accptedPersonAdapter;
    private List<AcceptedOrdersPersonBean.TakingOrderListBean> mData = new ArrayList<>();
    private AcceptedOrdersPersonBean mBean;

    public static void start(Context mContext, String order_sn) {
        Intent intent = new Intent(mContext, AcceptedOrdersPersonActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("order_sn", order_sn);
        intent.putExtras(bundle);
        mContext.startActivity(intent, bundle);

    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_accepted_orders_person;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        topStatusBar(true);
        order_sn = getIntent().getExtras().getString("order_sn");
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
        rcyPerson.setLayoutManager(linearLayoutManager);
        rcyPerson.setNestedScrollingEnabled(false);//禁止recyclerView嵌套滑动
        accptedPersonAdapter = new AccptedPersonAdapter(AcceptedOrdersPersonActivity.this, R.layout.item_accepted_orders_person, mData);
        rcyPerson.setAdapter(accptedPersonAdapter);
        accptedPersonAdapter.setCallback(new AccptedPersonAdapter.Callback() {
            @Override
            public void onEdit(int position, String isUp, final AcceptedOrdersPersonBean.TakingOrderListBean item) {
                if (isUp.equals("1")) {
                    //结算
                    new QLTipTwoDialog.Builder(AcceptedOrdersPersonActivity.this)
                            .setCancelable(true)
                            .setCancelableOnTouchOutside(true)
                            .setTitle("确认结算")
                            .setMessage("结算后对方将收到赏金")
                            .setPositiveButton("确认结算", new QLTipTwoDialog.IPositiveCallback() {
                                @Override
                                public void onClick() {
                                    settlementReward(item);

                                }
                            })
                            .setNegativeButton("取消", new QLTipTwoDialog.INegativeCallback() {
                                @Override
                                public void onClick() {
                                    dissMissDialog();

                                }
                            })
                            .show(AcceptedOrdersPersonActivity.this);
                } else if (isUp.equals("2")) {
                    //私聊
                    //IMUtils.singleChatForResource(AcceptedOrdersPersonActivity.this, item.getUser_id() + "", "", 0, "", "", "0");
                } else if (isUp.equals("3")) {
                    //申请详情
                    RewardCustomeServiceDetailActivity.start(AcceptedOrdersPersonActivity.this, order_sn, item.getId());
                } else if (isUp.equals("4")) {
                    //申请取消
                    new QLTipTwoDialog.Builder(AcceptedOrdersPersonActivity.this)
                            .setCancelable(true)
                            .setCancelableOnTouchOutside(true)
                            .setTitle("确认申请")
                            .setMessage("取消接单需要经过对方同意，等待时间24小时，超时将自动取消接单")
                            .setPositiveButton("确认申请", new QLTipTwoDialog.IPositiveCallback() {
                                @Override
                                public void onClick() {
                                    cancleReward(item);

                                }
                            })
                            .setNegativeButton("取消", new QLTipTwoDialog.INegativeCallback() {
                                @Override
                                public void onClick() {
                                    dissMissDialog();

                                }
                            })
                            .show(AcceptedOrdersPersonActivity.this);

                }

            }
        });
        getData();

    }

    private void settlementReward(AcceptedOrdersPersonBean.TakingOrderListBean item) {
        showBookingToast(2);

        RequestManager.getInstance().settlementReward(order_sn, item.getId(), new AllResultDoCallback() {
            @Override
            public void onSuccess(String msg) {
                dismissBookingToast();
                ToastUtils.showCentetToast(AcceptedOrdersPersonActivity.this, msg);
                getData();
            }

            @Override
            public void onFailed(int code, String msg) {
                dismissBookingToast();
                ToastUtils.showCentetToast(AcceptedOrdersPersonActivity.this, msg);

            }
        });
    }

    private void getData() {
        showBookingToast(1);
        RequestManager.getInstance().takingOrderPeople(order_sn, new GetPersonListCallback() {
            @Override
            public void onSuccess(AcceptedOrdersPersonBean item) {
                mBean = item;
                dismissBookingToast();
                tvTitle.setText(item.getTitle());
                if (item.getAmount().contains(".")) {
                    String[] pricr01 = item.getAmount().split("\\.");
                    tvPrice.setText(pricr01[0]);
                } else {
                    tvPrice.setText(item.getAmount());
                }

                tvNumber.setText(item.getNumber() + "份");

                if (item.getTotal_amount().contains(".")) {
                    String[] pricr01 = item.getTotal_amount().split("\\.");
                    tvTotalPrice.setText("赏金总额 ￥" + pricr01[0]);
                } else {
                    tvTotalPrice.setText("赏金总额 ￥" + item.getTotal_amount());
                }

                tvTakePersonNum.setText(item.getNumberOrders() + "人接单");
                if (item.getStatus() == 1) {
                    //	1：进行中，2：结束
                    tvStatus.setText("已上线");
                    tvStatus.setTextColor(ContextCompat.getColor(mContext, R.color.blue_bg_4B96F3));
                } else if (item.getStatus() == 2) {
                    tvStatus.setText("赏金库存不足，已下线");
                    tvStatus.setTextColor(ContextCompat.getColor(mContext, R.color._777));
                }
                tvNumber01.setText(item.getSettlementNumber() + "");
                tvNumber02.setText(item.getLockingShare() + "");
                tvNumber03.setText(item.getRemaining_number() + "");
                mData.clear();
                mData.addAll(item.getTaking_order_list());
                accptedPersonAdapter.notifyDataSetChanged();
                if (item.getTaking_order_list().size() == 0) {
                    pushtv.setVisibility(View.VISIBLE);
                } else {
                    pushtv.setVisibility(View.GONE);
                }
                if (item.getRefund_number() > 0 && item.getRemaining_number() == 0) {
                    //
                    tvRefund.setText("已退款");
                    tvRefund.setTextColor(getResources().getColor(R.color.color_aaaaaa));
                    tvRefund.setClickable(false);
                } else {
                    tvRefund.setText("退款");
                    tvRefund.setTextColor(getResources().getColor(R.color._333));
                    tvRefund.setClickable(true);
                }
            }

            @Override
            public void onFailed(int code, String msg) {
                dismissBookingToast();
                ToastUtils.showCentetToast(AcceptedOrdersPersonActivity.this, msg);

            }
        });
    }

    @OnClick({R.id.bt_finish, R.id.tv_detail, R.id.tv_refund})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_finish:
                finish();
                break;
            case R.id.tv_detail:
                //悬赏详情
                RewardDetailActivity.start(AcceptedOrdersPersonActivity.this, order_sn);
                break;
            case R.id.tv_refund:
                //退款
                new QLTipTwoDialog.Builder(AcceptedOrdersPersonActivity.this)
                        .setCancelable(true)
                        .setCancelableOnTouchOutside(true)
                        .setTitle("确认退款")
                        .setMessage("退款仅可退还剩余赏金")
                        .setPositiveButton("确认退款", new QLTipTwoDialog.IPositiveCallback() {
                            @Override
                            public void onClick() {
                                refundReward();

                            }
                        })
                        .setNegativeButton("取消", new QLTipTwoDialog.INegativeCallback() {
                            @Override
                            public void onClick() {
                                dissMissDialog();

                            }
                        })
                        .show(AcceptedOrdersPersonActivity.this);


                break;
        }
    }

    private void refundReward() {
        showBookingToast(2);

        RequestManager.getInstance().refundReward(order_sn, new AllResultDoCallback() {
            @Override
            public void onSuccess(String msg) {
                dismissBookingToast();
                getData();
                new QLTipDialog.Builder(AcceptedOrdersPersonActivity.this)
                        .setCancelable(true)
                        .setCancelableOnTouchOutside(true)
                        .setMessage(msg)
                        .setPositiveButton("查看钱包", new QLTipDialog.IPositiveCallback() {
                            @Override
                            public void onClick() {
                                //查看钱包
                                MyWalletActivity.start(AcceptedOrdersPersonActivity.this, mBean.getIs_vip());

                            }
                        })
                        .setNegativeButton("知道了", new QLTipDialog.INegativeCallback() {
                            @Override
                            public void onClick() {
                                dissMissDialog();

                            }
                        })
                        .show(AcceptedOrdersPersonActivity.this);
            }

            @Override
            public void onFailed(int code, String msg) {
                dismissBookingToast();
                ToastUtils.showCentetToast(AcceptedOrdersPersonActivity.this, msg);

            }
        });

    }

    private void cancleReward(AcceptedOrdersPersonBean.TakingOrderListBean item) {

        RequestManager.getInstance().cancelReward(order_sn, item.getId(), new AllResultDoCallback() {
            @Override
            public void onSuccess(String msg) {
                dismissBookingToast();
                ToastUtils.showCentetToast(AcceptedOrdersPersonActivity.this, msg);
                getData();

            }

            @Override
            public void onFailed(int code, String msg) {
                dismissBookingToast();
                ToastUtils.showCentetToast(AcceptedOrdersPersonActivity.this, msg);
            }
        });


    }
}
