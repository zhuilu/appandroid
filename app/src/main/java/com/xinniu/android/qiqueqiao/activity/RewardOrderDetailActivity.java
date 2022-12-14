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


                int is_settlement = item.getIs_settlement();//???????????????1????????????0????????????
                int received_status = item.getReceived_status();//0:????????????1????????????2?????????
                int is_cancel = item.getIs_cancel();//????????????????????????0???????????????1????????????2????????????
                int is_service = item.getIs_service();//	?????????????????? 0?????????1???????????????2???????????????
                if (is_settlement == 1) {
                    //?????????
                    tvStatus.setText("?????????");
                    tvStatusMemo.setText("???????????????????????????");
                    tvTime.setVisibility(View.VISIBLE);
                    tvTime.setText("??????????????????????????????????????????????????????");
                    tv01.setVisibility(View.GONE);
                    tv02.setVisibility(View.GONE);
                    tv03.setVisibility(View.VISIBLE);
                    tv04.setVisibility(View.GONE);
                    tv05.setVisibility(View.GONE);
                } else {
                    if (received_status == 2) {
                        //??????????????????
                        tvStatus.setText("?????????");
                        tvStatusMemo.setText("?????????????????????");
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
                        //?????????
                        //??????????????????????????????
                        if (is_cancel == 1) {
                            //?????????
                            tvStatus.setText("?????????");
                            tvStatusMemo.setText("?????????????????????");
                            tvTime.setVisibility(View.GONE);
                            tv01.setVisibility(View.GONE);
                            tv02.setVisibility(View.GONE);
                            tv03.setVisibility(View.VISIBLE);
                            tv04.setVisibility(View.GONE);
                            tv05.setVisibility(View.GONE);
                        } else if (is_cancel == 2) {
                            //?????????
                            tvStatus.setText("?????????");
                            tvStatusMemo.setText("??????????????????????????????");
                            tvTime.setVisibility(View.VISIBLE);
                            long time = (item.getCancel_time() + 24 * 60 * 60) - item.getNow_time();
                            long longHours = time / (60 * 60); //?????????????????????????????????
                            long longMinutes = (time - longHours * (60 * 60)) / 60;   //?????????????????????????????????
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
                            tvTime.setText("????????????" + longHours01 + ":" + longMinutes01 + "??????????????????????????????");
                            //??????????????????????????????
                            if (is_service == 0) {
                                //?????????
                                tv01.setVisibility(View.GONE);
                                tv02.setVisibility(View.VISIBLE);
                                tv03.setVisibility(View.VISIBLE);
                                tv04.setVisibility(View.VISIBLE);
                                tv05.setVisibility(View.GONE);

                            } else {
                                tvStatus.setText("?????????");
                                tvStatusMemo.setText("?????????????????????");
                                tvTime.setVisibility(View.VISIBLE);
                                tvTime.setText("???????????????????????????");
                                tv01.setVisibility(View.GONE);
                                tv02.setVisibility(View.GONE);
                                tv03.setVisibility(View.VISIBLE);
                                tv04.setVisibility(View.GONE);
                                tv05.setVisibility(View.VISIBLE);
                            }

                        } else if (is_cancel == 0) {
                            //??????????????????????????????
                            tvStatus.setText("?????????");
                            tvStatusMemo.setText("??????????????????????????????");
                            tvTime.setVisibility(View.VISIBLE);
                            tvTime.setText("????????????????????????????????????????????????????????????");
                            tv01.setVisibility(View.VISIBLE);
                            tv02.setVisibility(View.VISIBLE);
                            tv03.setVisibility(View.VISIBLE);
                            tv04.setVisibility(View.GONE);
                            tv05.setVisibility(View.GONE);

                            if (is_service == 0) {
                                //?????????
                            } else {
                                tvStatus.setText("?????????");
                                tvStatusMemo.setText("?????????????????????");
                                tvTime.setVisibility(View.VISIBLE);
                                tvTime.setText("???????????????????????????");
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
                //????????????
                cancleReward();
                break;
            case R.id.tv_02:
                //??????????????????
                ApplicatuinRewardCustomeServiceActivity.startSimpleEidtForResult(RewardOrderDetailActivity.this, mBean.getId(), 32);
                break;
            case R.id.tv_03:
                //????????????
                //??????
                //IMUtils.singleChatForResource(RewardOrderDetailActivity.this, mBean.getReward_info().getUser_id() + "", "", 0, "", "", "0");
                break;
            case R.id.tv_04:
                //????????????
                agreeCancleReward();
                break;
            case R.id.tv_05:
                //????????????
                RewardCustomeServiceDetailActivity.start(RewardOrderDetailActivity.this, mBean.getOrder_sn(), mBean.getId());
                break;
            case R.id.rlayout_info:
                //????????????
                PersonCentetActivity.start(RewardOrderDetailActivity.this, mBean.getReward_info().getUser_id() + "");
                break;
            case R.id.tv_detail:
                //??????
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
