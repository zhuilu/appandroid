package com.xinniu.android.qiqueqiao.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.xinniu.android.qiqueqiao.R;
import com.xinniu.android.qiqueqiao.base.BaseActivity;
import com.xinniu.android.qiqueqiao.bean.RewardOrderBean;
import com.xinniu.android.qiqueqiao.customs.qldialog.QLTipDialog;
import com.xinniu.android.qiqueqiao.request.RequestManager;
import com.xinniu.android.qiqueqiao.request.callback.AllResultDoCallback;
import com.xinniu.android.qiqueqiao.request.callback.GetRewardOrderCallback;
//import com.xinniu.android.qiqueqiao.utils.IMUtils;
import com.xinniu.android.qiqueqiao.utils.ShowUtils;
import com.xinniu.android.qiqueqiao.utils.ToastUtils;
import com.xinniu.android.qiqueqiao.widget.CircleImageView;

import butterknife.BindView;
import butterknife.OnClick;

public class RewardOrderDetailActivity extends BaseActivity {
    @BindView(R.id.tv_status)
    TextView tvStatus;
    @BindView(R.id.tv_status_memo)
    TextView tvStatusMemo;
    @BindView(R.id.tv_time)
    TextView tvTime;
    @BindView(R.id.tv_01)
    TextView tv01;
    @BindView(R.id.tv_02)
    TextView tv02;
    @BindView(R.id.tv_03)
    TextView tv03;
    @BindView(R.id.tv_04)
    TextView tv04;
    @BindView(R.id.tv_05)
    TextView tv05;
    @BindView(R.id.item_lx_headimg)
    CircleImageView itemLxHeadimg;
    @BindView(R.id.lx_nametv)
    TextView lxNametv;
    @BindView(R.id.lx_positiontv)
    TextView lxPositiontv;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_price)
    TextView tvPrice;
    private String order_sn;
    private RewardOrderBean mBean;
    private int mId;

    public static void start(Context mContext, String order_sn, int id) {
        Intent intent = new Intent(mContext, RewardOrderDetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("order_sn", order_sn);
        bundle.putInt("id", id);
        intent.putExtras(bundle);
        mContext.startActivity(intent, bundle);

    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_reward_order_detail;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        topStatusBar(true);
        order_sn = getIntent().getExtras().getString("order_sn");
        mId = getIntent().getExtras().getInt("id");
        getData();


    }

    private void getData() {
        showBookingToast(1);
        RequestManager.getInstance().getRewardOrders(order_sn, mId, new GetRewardOrderCallback() {
            @Override
            public void onSuccess(RewardOrderBean item) {
                mBean = item;
                ShowUtils.showImgPerfect(itemLxHeadimg, item.getReward_info().getHead_pic(), 1);
                ShowUtils.showTextPerfect(lxPositiontv, item.getReward_info().getCompany() + item.getReward_info().getPosition());
                ShowUtils.showTextPerfect(lxNametv, item.getReward_info().getRealname());
                ShowUtils.showTextPerfect(tvTitle, item.getReward_info().getTitle());
                //ShowUtils.showTextPerfect(tvPrice, item.getReward_info().getAmount());
                if (item.getReward_info().getAmount().contains(".")) {
                    String[] pricr01 = item.getReward_info().getAmount().split("\\.");
                    tvPrice.setText(pricr01[0]);
                } else {
                    tvPrice.setText(item.getReward_info().getAmount());
                }


                int is_settlement = item.getIs_settlement();//是否结算，1：结算，0：未结算
                int received_status = item.getReceived_status();//0:接单中，1：完成，2：取消
                int is_cancel = item.getIs_cancel();//发布人取消接单，0：未取消，1：取消，2：取消中
                int is_service = item.getIs_service();//	是否客服介入 0：未，1：介入中，2：介入完成
                if (is_settlement == 1) {
                    //已结算
                    tvStatus.setText("已结算");
                    tvStatusMemo.setText("对方已向您结算赏金");
                    tvTime.setVisibility(View.VISIBLE);
                    tvTime.setText("赏金已结算至您的钱包中，您可申请提现");
                    tv01.setVisibility(View.GONE);
                    tv02.setVisibility(View.GONE);
                    tv03.setVisibility(View.VISIBLE);
                    tv04.setVisibility(View.GONE);
                    tv05.setVisibility(View.GONE);
                } else {
                    if (received_status == 2) {
                        //自己取消订单
                        tvStatus.setText("已取消");
                        tvStatusMemo.setText("锁定赏金已退回");
                        tvTime.setVisibility(View.GONE);
                        if (is_service == 0 || is_service == 1) {
                            tv01.setVisibility(View.GONE);
                            tv02.setVisibility(View.GONE);
                            tv03.setVisibility(View.VISIBLE);
                            tv04.setVisibility(View.GONE);
                            tv05.setVisibility(View.GONE);
                        } else {
                            tv01.setVisibility(View.GONE);
                            tv02.setVisibility(View.GONE);
                            tv03.setVisibility(View.VISIBLE);
                            tv04.setVisibility(View.GONE);
                            tv05.setVisibility(View.VISIBLE);
                        }
                    } else if (received_status == 0) {
                        //接单中
                        //判断对方是否申请取消
                        if (is_cancel == 1) {
                            //已取消
                            tvStatus.setText("已取消");
                            tvStatusMemo.setText("锁定赏金已退回");
                            tvTime.setVisibility(View.GONE);
                            tv01.setVisibility(View.GONE);
                            tv02.setVisibility(View.GONE);
                            tv03.setVisibility(View.VISIBLE);
                            tv04.setVisibility(View.GONE);
                            tv05.setVisibility(View.GONE);
                        } else if (is_cancel == 2) {
                            //取消中
                            tvStatus.setText("已接单");
                            tvStatusMemo.setText("对方申请取消您的订单");
                            tvTime.setVisibility(View.VISIBLE);
                            long time = (item.getCancel_time() + 24 * 60 * 60) - item.getNow_time();
                            long longHours = time / (60 * 60); //根据时间差来计算小时数
                            long longMinutes = (time - longHours * (60 * 60)) / 60;   //根据时间差来计算分钟数
                            String longMinutes01;
                            String longHours01;
                            if (longMinutes < 10) {
                                longMinutes01 = "0" + longMinutes;
                            } else {
                                longMinutes01 = longMinutes + "";
                            }
                            if (longHours < 10) {
                                longHours01 = "0" + longHours;
                            } else {
                                longHours01 = longHours + "";
                            }
                            tvTime.setText("剩余时间" + longHours01 + ":" + longMinutes01 + "，超时将自动取消订单");
                            //判断是否申请客服介入
                            if (is_service == 0) {
                                //未介入
                                tv01.setVisibility(View.GONE);
                                tv02.setVisibility(View.VISIBLE);
                                tv03.setVisibility(View.VISIBLE);
                                tv04.setVisibility(View.VISIBLE);
                                tv05.setVisibility(View.GONE);

                            } else {
                                tvStatus.setText("已接单");
                                tvStatusMemo.setText("已申请客服介入");
                                tvTime.setVisibility(View.VISIBLE);
                                tvTime.setText("请等待客服处理结果");
                                tv01.setVisibility(View.GONE);
                                tv02.setVisibility(View.GONE);
                                tv03.setVisibility(View.VISIBLE);
                                tv04.setVisibility(View.GONE);
                                tv05.setVisibility(View.VISIBLE);
                            }

                        } else if (is_cancel == 0) {
                            //判断是否申请客服介入
                            tvStatus.setText("已接单");
                            tvStatusMemo.setText("请向对方提供所需资源");
                            tvTime.setVisibility(View.VISIBLE);
                            tvTime.setText("赏金已锁定，完成后，可向对方要求结算赏金");
                            tv01.setVisibility(View.VISIBLE);
                            tv02.setVisibility(View.VISIBLE);
                            tv03.setVisibility(View.VISIBLE);
                            tv04.setVisibility(View.GONE);
                            tv05.setVisibility(View.GONE);

                            if (is_service == 0) {
                                //未介入
                            } else {
                                tvStatus.setText("已接单");
                                tvStatusMemo.setText("已申请客服介入");
                                tvTime.setVisibility(View.VISIBLE);
                                tvTime.setText("请等待客服处理结果");
                                tv01.setVisibility(View.GONE);
                                tv02.setVisibility(View.GONE);
                                tv03.setVisibility(View.VISIBLE);
                                tv04.setVisibility(View.GONE);
                                tv05.setVisibility(View.VISIBLE);
                            }
                        }


                    }


                }
                dismissBookingToast();

            }

            @Override
            public void onFailed(int code, String msg) {
                dismissBookingToast();
                ToastUtils.showCentetToast(RewardOrderDetailActivity.this, msg);


            }
        });
    }

    @OnClick({R.id.bt_return, R.id.tv_01, R.id.tv_02, R.id.tv_03, R.id.tv_04, R.id.tv_05, R.id.rlayout_info, R.id.tv_detail})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_return:
                finish();
                break;
            case R.id.tv_01:
                //取消接单
                cancleReward();
                break;
            case R.id.tv_02:
                //申请客服介入
                ApplicatuinRewardCustomeServiceActivity.startSimpleEidtForResult(RewardOrderDetailActivity.this, mBean.getId(), 32);
                break;
            case R.id.tv_03:
                //私聊联系
                //聊天
                //IMUtils.singleChatForResource(RewardOrderDetailActivity.this, mBean.getReward_info().getUser_id() + "", "", 0, "", "", "0");
                break;
            case R.id.tv_04:
                //同意取消
                agreeCancleReward();
                break;
            case R.id.tv_05:
                //申请详情
                RewardCustomeServiceDetailActivity.start(RewardOrderDetailActivity.this, mBean.getOrder_sn(), mBean.getId());
                break;
            case R.id.rlayout_info:
                //个人中心
                PersonCentetActivity.start(RewardOrderDetailActivity.this, mBean.getReward_info().getUser_id() + "");
                break;
            case R.id.tv_detail:
                //详情
                RewardDetailActivity.start(RewardOrderDetailActivity.this, mBean.getOrder_sn());
                break;
        }
    }

    private void agreeCancleReward() {
        RequestManager.getInstance().agreeCancelReward(mBean.getOrder_sn(), mBean.getId(), new AllResultDoCallback() {
            @Override
            public void onSuccess(String msg) {
                dismissBookingToast();
                ToastUtils.showCentetToast(RewardOrderDetailActivity.this, msg);
                getData();

            }

            @Override
            public void onFailed(int code, String msg) {
                dismissBookingToast();
                ToastUtils.showCentetToast(RewardOrderDetailActivity.this, msg);
            }
        });
    }

    private void cancleReward() {

        RequestManager.getInstance().cancelReward(mBean.getOrder_sn(), mBean.getId(), new AllResultDoCallback() {
            @Override
            public void onSuccess(String msg) {
                dismissBookingToast();
                ToastUtils.showCentetToast(RewardOrderDetailActivity.this, msg);
                getData();

            }

            @Override
            public void onFailed(int code, String msg) {
                dismissBookingToast();
                ToastUtils.showCentetToast(RewardOrderDetailActivity.this, msg);
            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {

            if (requestCode == 32) {
                getData();
            }
        }

    }


}
