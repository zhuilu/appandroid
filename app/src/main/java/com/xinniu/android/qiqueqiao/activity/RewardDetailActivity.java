package com.xinniu.android.qiqueqiao.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
//import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.xinniu.android.qiqueqiao.R;
import com.xinniu.android.qiqueqiao.base.BaseActivity;
import com.xinniu.android.qiqueqiao.bean.HandlingFeeBean;
import com.xinniu.android.qiqueqiao.bean.RewardDetailBean;
import com.xinniu.android.qiqueqiao.common.Constants;
import com.xinniu.android.qiqueqiao.customs.qldialog.QLTipDialog;
import com.xinniu.android.qiqueqiao.customs.qldialog.QLTipTwoDialog;
import com.xinniu.android.qiqueqiao.request.RequestManager;
import com.xinniu.android.qiqueqiao.request.callback.GetRewardDetailCallback;
import com.xinniu.android.qiqueqiao.request.callback.RequestCallback;
import com.xinniu.android.qiqueqiao.user.UserInfoHelper;
import com.xinniu.android.qiqueqiao.utils.ShowUtils;
import com.xinniu.android.qiqueqiao.utils.ToastUtils;
import com.xinniu.android.qiqueqiao.widget.CircleImageView;

import butterknife.BindView;
import butterknife.OnClick;
import retrofit2.Call;

public class RewardDetailActivity extends BaseActivity {
    @BindView(R.id.btn_submit)
    TextView btnSubmit;
    @BindView(R.id.item_index_recycler_img)
    CircleImageView itemIndexRecyclerImg;
    @BindView(R.id.item_index_isv_img)
    ImageView itemIndexIsvImg;
    @BindView(R.id.item_index_Fl)
    FrameLayout itemIndexFl;
    @BindView(R.id.item_index_nameTv)
    TextView itemIndexNameTv;
    @BindView(R.id.view)
    View view;
    @BindView(R.id.ll_name)
    LinearLayout llName;
    @BindView(R.id.company_vip_img)
    ImageView companyVipImg;
    @BindView(R.id.item_index_vipImg)
    ImageView itemIndexVipImg;
    @BindView(R.id.person_rz)
    ImageView personRz;
    @BindView(R.id.coop_detail_time)
    TextView coopDetailTime;
    @BindView(R.id.item_index_Rl)
    RelativeLayout itemIndexRl;
    @BindView(R.id.item_index_position)
    TextView itemIndexPosition;
    @BindView(R.id.coopdetail_Rl)
    RelativeLayout coopdetailRl;
    @BindView(R.id.rlayout)
    RelativeLayout rlayout;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_content)
    TextView tvContent;
    @BindView(R.id.tv_type)
    TextView tvType;
    @BindView(R.id.llayout_type)
    LinearLayout llayoutType;
    @BindView(R.id.tv_price)
    TextView tvPrice;
    @BindView(R.id.tv_number)
    TextView tvNumber;
    private String order_sn;
    private RewardDetailBean mBean;

    public static void start(Context mContext, String order_sn) {
        Intent intent = new Intent(mContext, RewardDetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("order_sn", order_sn);
        intent.putExtras(bundle);
        mContext.startActivity(intent, bundle);

    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_reward_detail;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        topStatusBar(true);
        order_sn = getIntent().getExtras().getString("order_sn");
        getData();

    }

    private void getData() {
        showBookingToast(1);
        RequestManager.getInstance().getRewardDetail(order_sn, new GetRewardDetailCallback() {
            @Override
            public void onSuccess(RewardDetailBean item) {
                mBean = item;
                ShowUtils.showImgPerfect(itemIndexRecyclerImg, item.getHead_pic(), 1);
                ShowUtils.showTextPerfect(itemIndexPosition, item.getCompany() + item.getPosition());
                ShowUtils.showTextPerfect(itemIndexNameTv, item.getRealname());
                view.setVisibility(View.GONE);
                if (Constants.userIdList.length() > 0) {
                    String[] all = Constants.userIdList.split(",");
                    int size1 = all.length;
                    int j;

                    for (j = 0; j < size1; j++) {
                        if (item.getUser_id() == Integer.parseInt(all[j])) {
                            view.setVisibility(View.VISIBLE);
                        }
                    }
                }

                if (item.getIs_v() == 1) {
                    ShowUtils.showViewVisible(itemIndexIsvImg, View.VISIBLE);
                } else {
                    ShowUtils.showViewVisible(itemIndexIsvImg, View.GONE);
                }
                if (item.getIs_cloud_auth() == 1) {
                    personRz.setVisibility(View.VISIBLE);
                } else {
                    personRz.setVisibility(View.GONE);
                }
                int isVip = item.getIs_vip();
                if (item.getIs_corporate_vip() == 1) {
                    //企业会员不显示vip
                    ShowUtils.showViewVisible(companyVipImg, View.VISIBLE);
                    ShowUtils.showViewVisible(itemIndexVipImg, View.GONE);
                    ShowUtils.showTextColor(itemIndexNameTv, ContextCompat.getColor(RewardDetailActivity.this, R.color.king_color));
                    view.setBackgroundColor(ContextCompat.getColor(RewardDetailActivity.this, R.color.king_color));
                } else {
                    ShowUtils.showViewVisible(companyVipImg, View.GONE);
                    if (isVip == 0) {
                        ShowUtils.showViewVisible(itemIndexVipImg, View.GONE);
                        ShowUtils.showTextColor(itemIndexNameTv, ContextCompat.getColor(RewardDetailActivity.this, R.color.text_color_1));
                        view.setBackgroundColor(ContextCompat.getColor(RewardDetailActivity.this, R.color.text_color_1));
                    } else if (isVip == 1) {
                        ShowUtils.showBackgroud(itemIndexVipImg, ContextCompat.getDrawable(RewardDetailActivity.this, R.mipmap.vip_iconx));
                        ShowUtils.showTextColor(itemIndexNameTv, ContextCompat.getColor(RewardDetailActivity.this, R.color.king_color));
                        view.setBackgroundColor(ContextCompat.getColor(RewardDetailActivity.this, R.color.king_color));
                    } else if (isVip == 2) {
                        ShowUtils.showBackgroud(itemIndexVipImg, ContextCompat.getDrawable(RewardDetailActivity.this, R.mipmap.svip_iconx));
                        ShowUtils.showTextColor(itemIndexNameTv, ContextCompat.getColor(RewardDetailActivity.this, R.color.king_color));
                        view.setBackgroundColor(ContextCompat.getColor(RewardDetailActivity.this, R.color.king_color));
                    }
                }


                tvTitle.setText(item.getTitle());
                tvContent.setText(item.getDetail());
                tvType.setText(item.getType_name());
                //    tvPrice.setText(item.getAmount());
                if (item.getAmount().contains(".")) {
                    String[] pricr01 = item.getAmount().split("\\.");
                    tvPrice.setText(pricr01[0]);
                } else {
                    tvPrice.setText(item.getAmount());
                }
                tvNumber.setText(item.getRemaining_number() + "");
                btnSubmit.setBackgroundResource(R.drawable.bg_tran_pay);
                btnSubmit.setText("立即接单");
                btnSubmit.setClickable(true);
                if (item.getRemaining_number() == 0||item.getStatus()==2) {
                    btnSubmit.setBackgroundResource(R.drawable.bg_orders_gray);
                    btnSubmit.setText("赏金份额不足");
                    btnSubmit.setClickable(false);
                }
                if (item.getIs_order_taking() == 1) {
                    btnSubmit.setBackgroundResource(R.drawable.bg_orders_gray);
                    btnSubmit.setText("已接单");
                    btnSubmit.setClickable(false);
                }

                dismissBookingToast();
            }

            @Override
            public void onFailed(int code, String msg) {
                dismissBookingToast();
                ToastUtils.showCentetToast(RewardDetailActivity.this, msg);

            }
        });
    }

    @OnClick({R.id.bt_return, R.id.btn_submit, R.id.coopdetail_Rl, R.id.rlayout_share})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_return:
                finish();
                break;
            case R.id.btn_submit:

                int userId = UserInfoHelper.getIntance().getUserId();
                if (mBean.getUser_id() == userId) {
                    ToastUtils.showCentetToast(RewardDetailActivity.this, "不可以接自己单");
                    return;
                }

                    new QLTipTwoDialog.Builder(RewardDetailActivity.this)
                            .setCancelable(true)
                            .setCancelableOnTouchOutside(true)
                            .setTitle("确认接单")
                            .setMessage("接单后赏金将会锁定，请尽快向对方提供所需资源，完成后赏金会自动进入您的钱包")
                            .setNegativeButton("取消", new QLTipTwoDialog.INegativeCallback() {
                                @Override
                                public void onClick() {

                                }
                            }).setPositiveButton("确认接单", new QLTipTwoDialog.IPositiveCallback() {
                        @Override
                        public void onClick() {
                            //接单
                            toReward();
                        }
                    })
                            .show(RewardDetailActivity.this);


                break;
            case R.id.coopdetail_Rl://查看个人主页
                PersonCentetActivity.start(RewardDetailActivity.this, mBean.getUser_id() + "");
                break;
            case R.id.rlayout_share://分享
                Gson gson = new Gson();
                String data = gson.toJson(mBean);
                RewardShareActivity.start(RewardDetailActivity.this, data);

                break;
        }
    }

    private void toReward() {

        RequestManager.getInstance().takeReward(order_sn, new RequestCallback<String>() {
            @Override
            public void requestStart(Call call) {
                showBookingToast(2);
            }

            @Override
            public void onSuccess(String s) {
                Gson gson = new Gson();
                HandlingFeeBean handlingFeeBean = gson.fromJson(s, HandlingFeeBean.class);
                RewardOrderDetailActivity.start(RewardDetailActivity.this, mBean.getOrder_sn(), handlingFeeBean.getId());
                finish();
            }

            @Override
            public void onFailed(int code, String msg) {
                dismissBookingToast();
                ToastUtils.showCentetToast(mContext, msg);
            }

            @Override
            public void requestEnd() {
                dismissBookingToast();
            }
        });
    }
}
