package com.xinniu.android.qiqueqiao.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.StyleSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;

import com.google.gson.Gson;
import com.xinniu.android.qiqueqiao.R;
import com.xinniu.android.qiqueqiao.base.BaseActivity;
import com.xinniu.android.qiqueqiao.bean.GuaranteeDetailBean;
import com.xinniu.android.qiqueqiao.customs.qldialog.QLTipDialog;
import com.xinniu.android.qiqueqiao.customs.qldialog.QLTipTwoDialog;
import com.xinniu.android.qiqueqiao.request.RequestManager;
import com.xinniu.android.qiqueqiao.request.callback.AllResultDoCallback;
import com.xinniu.android.qiqueqiao.request.callback.GetGuaranteeDetailtCallback;
import com.xinniu.android.qiqueqiao.user.UserInfoHelper;
import com.xinniu.android.qiqueqiao.utils.ImageLoader;
import com.xinniu.android.qiqueqiao.utils.ShowUtils;
import com.xinniu.android.qiqueqiao.utils.StringUtils;
import com.xinniu.android.qiqueqiao.utils.TimeUtils;
import com.xinniu.android.qiqueqiao.utils.ToastUtils;
import com.xinniu.android.qiqueqiao.widget.CircleImageView;

import butterknife.BindView;
import butterknife.OnClick;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

public class TransactionDetailsActivity extends BaseActivity {
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
    @BindView(R.id.tv_identity)
    TextView tvIdentity;
    @BindView(R.id.lx_positiontv)
    TextView lxPositiontv;
    @BindView(R.id.mcompany_introducetv)
    TextView mcompanyIntroducetv;
    @BindView(R.id.bcompany_intromoreImg)
    ImageView bcompanyIntromoreImg;
    @BindView(R.id.ycompany_intromoreRl)
    RelativeLayout ycompanyIntromoreRl;
    @BindView(R.id.tv_price_d)
    TextView tvPriceD;
    @BindView(R.id.rlayout_01)
    RelativeLayout rlayout01;
    @BindView(R.id.view_01)
    View view01;
    @BindView(R.id.tv_price_y)
    TextView tvPriceY;
    @BindView(R.id.rlayout_02)
    RelativeLayout rlayout02;
    @BindView(R.id.view_02)
    View view02;
    @BindView(R.id.rlayout)
    RelativeLayout rlayout;
    @BindView(R.id.tv_operation)
    TextView tvOperation;
    @BindView(R.id.rlayout_03)
    RelativeLayout rlayout03;
    @BindView(R.id.rlayout_04)
    RelativeLayout rlayout04;
    @BindView(R.id.tv_price_t)
    TextView tvPriceT;
    @BindView(R.id.rlayout_05)
    RelativeLayout rlayout05;
    @BindView(R.id.tv_call_customer_service)
    TextView tvCallCustomerService;
    @BindView(R.id.tv_order_sn)
    TextView tvOrderSn;
    @BindView(R.id.tv_create_time)
    TextView tvCreateTime;
    @BindView(R.id.view_5)
    View view5;
    @BindView(R.id.tv_done_time)
    TextView tvDoneTime;
    @BindView(R.id.rlayout_done_time)
    RelativeLayout rlayoutDoneTime;
    @BindView(R.id.tv_person_status)
    TextView tvPersonStatus;
    @BindView(R.id.tv_price_s)
    TextView tvPriceS;
    @BindView(R.id.tv_price_ss)
    TextView tvPriceSs;
    @BindView(R.id.tv_bj_status)
    TextView tvBjStatus;
    private String mId;
    private GuaranteeDetailBean mBean;
    private String toBeconfirmed = "0";//是否是接收方待确认状态，用来判断取消交易和同意交易

    public static void start(Context context, String id) {
        Intent starter = new Intent(context, TransactionDetailsActivity.class);
        starter.putExtra("id", id);
        context.startActivity(starter);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_transaction_details;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        topStatusBar(false);
        mId = getIntent().getStringExtra("id");
        showBookingToast(1);
        getDatas();
    }

    private void getDatas() {

        RequestManager.getInstance().getGuaranteeDetail(Integer.parseInt(mId), new GetGuaranteeDetailtCallback() {
            @Override
            public void onSuccess(GuaranteeDetailBean guaranteeDetailBean) {
                mBean = guaranteeDetailBean;
                ImageLoader.loadAvter(guaranteeDetailBean.getObj_user_info().getHead_pic(), itemLxHeadimg);
                lxNametv.setText(guaranteeDetailBean.getObj_user_info().getRealname());
                String job = "";
                if (StringUtils.isEmpty(guaranteeDetailBean.getObj_user_info().getCompany()) && StringUtils.isEmpty(guaranteeDetailBean.getObj_user_info().getPosition())) {
                    job = "";
                } else {
                    if (StringUtils.isEmpty(guaranteeDetailBean.getObj_user_info().getCompany())) {
                        job = guaranteeDetailBean.getObj_user_info().getPosition();
                    } else if (StringUtils.isEmpty(guaranteeDetailBean.getObj_user_info().getPosition())) {
                        job = guaranteeDetailBean.getObj_user_info().getCompany();
                    } else {
                        job = guaranteeDetailBean.getObj_user_info().getCompany() + "|" + guaranteeDetailBean.getObj_user_info().getPosition();
                    }
                }
                lxPositiontv.setText(job);
                if (guaranteeDetailBean.getObj_user_info().getParty_a_or_party_b() == 1) {
                    //	1甲方，2：乙方
                    tvIdentity.setText("买方");
                } else {
                    tvIdentity.setText("卖方");
                }
                ShowUtils.showTextPerfect(mcompanyIntroducetv, guaranteeDetailBean.getText());
                if (mcompanyIntroducetv != null) {
                    if (mcompanyIntroducetv.getLineCount() <= 5) {
                        ShowUtils.showViewVisible(bcompanyIntromoreImg, View.GONE);
                        ShowUtils.showViewVisible(ycompanyIntromoreRl, View.GONE);
                    } else {
                        ShowUtils.showViewVisible(bcompanyIntromoreImg, View.VISIBLE);
                        ShowUtils.showViewVisible(ycompanyIntromoreRl, View.VISIBLE);
                    }
                }
                if (guaranteeDetailBean.getEstimated_amount().contains(".")) {
                    String[] pricr01 = guaranteeDetailBean.getEstimated_amount().split("\\.");
                    String text1="¥ ";
                    String text=text1 + pricr01[0];
                    final SpannableStringBuilder sb = new SpannableStringBuilder(text);
                    sb.setSpan(new AbsoluteSizeSpan(14, true), text1.length(), text.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    sb.setSpan(new StyleSpan(Typeface.BOLD), text1.length(), text.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    tvPriceD.setText(sb);//担保金额
                } else {
                    String text1="¥ ";
                    String text=text1+ guaranteeDetailBean.getEstimated_amount();
                    final SpannableStringBuilder sb = new SpannableStringBuilder(text);
                    sb.setSpan(new AbsoluteSizeSpan(14, true), text1.length(), text.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    sb.setSpan(new StyleSpan(Typeface.BOLD), text1.length(), text.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    tvPriceD.setText(sb);//担保金额
                }
                tvOrderSn.setText(guaranteeDetailBean.getOrder_sn());
                tvCreateTime.setText(TimeUtils.time2ActTime(guaranteeDetailBean.getCreate_time() * 1000));
                //买方显示处理
                int is_initiate = guaranteeDetailBean.getIs_initiate();   //是否发起人
                if (is_initiate == 1) {
                    tvPersonStatus.setText("交易对方");
                } else {
                    tvPersonStatus.setText("发起人");
                }
                int party_a_user_id = guaranteeDetailBean.getParty_a_user_id();//甲方UID 买家
                int mUserId = UserInfoHelper.getIntance().getUserId();//当前登录的账号id
                boolean isBuyer = false;//当前账号是否是买家
                if (party_a_user_id == mUserId) {
                    isBuyer = true;
                }

                if (guaranteeDetailBean.getStatus() == 0) {
                    long time = (guaranteeDetailBean.getCreate_time() + 24 * 60 * 60) - guaranteeDetailBean.getNow_time();
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
                    tvBjStatus.setVisibility(View.GONE);
                    tvTime.setVisibility(View.VISIBLE);
                    view01.setVisibility(View.GONE);
                    rlayout02.setVisibility(View.GONE);
                    view02.setVisibility(View.GONE);
                    rlayout03.setVisibility(View.GONE);
                    rlayout04.setVisibility(View.GONE);
                    rlayout05.setVisibility(View.GONE);
                    view5.setVisibility(View.GONE);
                    rlayoutDoneTime.setVisibility(View.GONE);
                    tv01.setVisibility(View.VISIBLE);
                    tv03.setVisibility(View.INVISIBLE);
                    tv04.setVisibility(View.INVISIBLE);
                    tv05.setVisibility(View.GONE);
                    tv01.setBackgroundResource(R.drawable.bg_trans_gray);
                    tv01.setTextColor(getResources().getColor(R.color._333));
                    tv01.setText("取消交易");
                    //甲方合同创建，乙方还未同意
                    if (is_initiate == 1) {
                        //发起人，等待对方确认中
                        tvStatus.setText("等待对方确认中");
                        tvStatusMemo.setText("已通知对方确认交易");
                        tvTime.setText("剩余时间" + longHours01 + ":" + longMinutes01 + "，超时将自动取消交易");
                        tv02.setVisibility(View.INVISIBLE);

                    } else {
                        //确认交易中
                        toBeconfirmed = "1";
                        tvStatus.setText("确认交易中");
                        tvStatusMemo.setText("对方已申请担保交易，请您尽快进行确认");
                        tvTime.setText("剩余时间" + longHours01 + ":" + longMinutes01 + "，超时将自动取消交易");
                        tv02.setVisibility(View.VISIBLE);
                        tv02.setBackgroundResource(R.drawable.bg_trans_blue);
                        tv02.setTextColor(getResources().getColor(R.color.blue_bg_4B96F3));
                        tv02.setText("确认交易");
                    }

                } else if (guaranteeDetailBean.getStatus() == 1) {
                    tvBjStatus.setVisibility(View.GONE);
                    tvTime.setVisibility(View.VISIBLE);
                    view01.setVisibility(View.VISIBLE);
                    rlayout02.setVisibility(View.VISIBLE);
                    view02.setVisibility(View.VISIBLE);
                    rlayout03.setVisibility(View.GONE);
                    rlayout04.setVisibility(View.VISIBLE);
                    tvPriceSs.setText("交易完成已全部结算");
                    tvPriceSs.setTextColor(getResources().getColor(R.color._777));
                    rlayout05.setVisibility(View.GONE);
                    view5.setVisibility(View.VISIBLE);
                    rlayoutDoneTime.setVisibility(View.VISIBLE);
                    tv01.setVisibility(View.VISIBLE);
                    tv02.setVisibility(View.VISIBLE);
                    tv04.setVisibility(View.INVISIBLE);
                    tv03.setVisibility(View.INVISIBLE);
                    tv05.setVisibility(View.GONE);

                    //1：合同已结束，
                    if (guaranteeDetailBean.getRefund_status() == 0||guaranteeDetailBean.getRefund_status()==3) {
                        //正常结束交易,退款失败后正常结束
                        tvStatus.setText("交易完成");
                        tvStatusMemo.setText("感谢您对企鹊桥的支持");

                        if (isBuyer) {
                            tvTime.setText("已向对方结算全部担保金额");
                        } else {
                            tvTime.setText("交易款项已汇入您的钱包中，您可申请提现");
                        }

                        tv01.setBackgroundResource(R.drawable.bg_trans_gray);
                        tv01.setTextColor(getResources().getColor(R.color._333));
                        tv01.setText("电话联系");
                        tv02.setBackgroundResource(R.drawable.bg_trans_blue);
                        tv02.setTextColor(getResources().getColor(R.color.blue_bg_4B96F3));
                        tv02.setText("再次交易");
                        //剩下字段未给
                        tvDoneTime.setText(TimeUtils.time2ActTime(guaranteeDetailBean.getEnd_time() * 1000));
                        if (guaranteeDetailBean.getSettlement_amount().contains(".")) {
                            String[] pricr01 = guaranteeDetailBean.getSettlement_amount().split("\\.");
                            String text1="-¥ ";
                            String text=text1+ pricr01[0];
                            final SpannableStringBuilder sb = new SpannableStringBuilder(text);
                            sb.setSpan(new AbsoluteSizeSpan(14, true), text1.length(), text.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                            sb.setSpan(new StyleSpan(Typeface.BOLD), text1.length(), text.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                            tvPriceY.setText(sb);//担保金额
                        } else {

                            String text1="-¥ ";
                            String text=text1+guaranteeDetailBean.getSettlement_amount();
                            final SpannableStringBuilder sb = new SpannableStringBuilder(text);
                            sb.setSpan(new AbsoluteSizeSpan(14, true), text1.length(), text.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                            sb.setSpan(new StyleSpan(Typeface.BOLD), text1.length(), text.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                            tvPriceY.setText(sb);//担保金额

                        }


                    } else if (guaranteeDetailBean.getRefund_status() == 2) {
                        //退款结束交易
                        tvTime.setVisibility(View.GONE);
                        view01.setVisibility(View.VISIBLE);
                        rlayout02.setVisibility(View.VISIBLE);
                        view02.setVisibility(View.GONE);
                        rlayout03.setVisibility(View.GONE);
                        rlayout04.setVisibility(View.GONE);
                        rlayout05.setVisibility(View.VISIBLE);
                        view5.setVisibility(View.VISIBLE);
                        rlayoutDoneTime.setVisibility(View.VISIBLE);
                        tv01.setVisibility(View.VISIBLE);
                        tv02.setVisibility(View.VISIBLE);
                        tv04.setVisibility(View.INVISIBLE);
                        tv03.setVisibility(View.INVISIBLE);
                        tv05.setVisibility(View.GONE);
                        tvStatus.setText("交易关闭");
                        if (isBuyer) {
                            tvStatusMemo.setText("已成功退款");

                        } else {
                            tvStatusMemo.setText("对方已退款");
                        }
                        tv01.setBackgroundResource(R.drawable.bg_trans_organe);
                        tv01.setTextColor(getResources().getColor(R.color.orange));
                        tv01.setText("退款详情");
                        tv02.setBackgroundResource(R.drawable.bg_trans_blue);
                        tv02.setTextColor(getResources().getColor(R.color.blue_bg_4B96F3));
                        tv02.setText("再次交易");
                        //已结算，已退款，成交时间

                        //剩下字段未给
                        tvDoneTime.setText(TimeUtils.time2ActTime(guaranteeDetailBean.getEnd_time() * 1000));
                        if (guaranteeDetailBean.getSettlement_amount().contains(".")) {
                            String[] pricr01 = guaranteeDetailBean.getSettlement_amount().split("\\.");

                            String text1="-¥ ";
                            String text=text1+pricr01[0];
                            final SpannableStringBuilder sb = new SpannableStringBuilder(text);
                            sb.setSpan(new AbsoluteSizeSpan(14, true), text1.length(), text.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                            sb.setSpan(new StyleSpan(Typeface.BOLD), text1.length(), text.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                            tvPriceY.setText(sb);//担保金额
                        } else {

                            String text1="-¥ ";
                            String text=text1+guaranteeDetailBean.getSettlement_amount();
                            final SpannableStringBuilder sb = new SpannableStringBuilder(text);
                            sb.setSpan(new AbsoluteSizeSpan(14, true), text1.length(), text.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                            sb.setSpan(new StyleSpan(Typeface.BOLD), text1.length(), text.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                            tvPriceY.setText(sb);//担保金额
                        }

                        if (guaranteeDetailBean.getRefund_amount().contains(".")) {
                            String[] pricr01 = guaranteeDetailBean.getRefund_amount().split("\\.");
                            String text1="¥ ";
                            String text=text1+pricr01[0];
                            final SpannableStringBuilder sb = new SpannableStringBuilder(text);
                            sb.setSpan(new AbsoluteSizeSpan(14, true), text1.length(), text.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                            sb.setSpan(new StyleSpan(Typeface.BOLD), text1.length(), text.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                            tvPriceT.setText(sb);//担保金额
                        } else {
                            String text1="¥ ";
                            String text=text1+guaranteeDetailBean.getRefund_amount();
                            final SpannableStringBuilder sb = new SpannableStringBuilder(text);
                            sb.setSpan(new AbsoluteSizeSpan(14, true), text1.length(), text.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                            sb.setSpan(new StyleSpan(Typeface.BOLD), text1.length(), text.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                            tvPriceT.setText(sb);//担保金额
                        }
                    }


                } else if (guaranteeDetailBean.getStatus() == 2) {
                    //乙方同意未打款
                    long time = (guaranteeDetailBean.getAgree_time() + 24 * 60 * 60) - guaranteeDetailBean.getNow_time();
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
                    tvBjStatus.setVisibility(View.GONE);
                    tvTime.setVisibility(View.VISIBLE);
                    view01.setVisibility(View.GONE);
                    rlayout02.setVisibility(View.GONE);
                    view02.setVisibility(View.GONE);
                    rlayout03.setVisibility(View.GONE);
                    rlayout04.setVisibility(View.GONE);
                    rlayout05.setVisibility(View.GONE);
                    view5.setVisibility(View.GONE);
                    rlayoutDoneTime.setVisibility(View.GONE);
                    tv01.setVisibility(View.VISIBLE);
                    tv02.setVisibility(View.VISIBLE);
                    tv04.setVisibility(View.INVISIBLE);
                    tv05.setVisibility(View.GONE);
                    tv01.setBackgroundResource(R.drawable.bg_trans_gray);
                    tv01.setTextColor(getResources().getColor(R.color._333));
                    tv01.setText("取消交易");
                    tv02.setBackgroundResource(R.drawable.bg_trans_gray);
                    tv02.setTextColor(getResources().getColor(R.color._333));
                    tv02.setText("电话联系");

                    if (isBuyer) {
                        //我是买家,付钱
                        if (is_initiate == 1) {
                            //我是发起方
                            tvStatus.setText("对方已同意交易");
                        } else {
                            tvStatus.setText("已同意交易");
                        }
                        tvStatusMemo.setText("请尽快支付担保金");

                        tvTime.setText("剩余时间" + longHours01 + ":" + longMinutes01 + "，超时将自动取消交易");
                        tv03.setVisibility(View.VISIBLE);
                        tv03.setBackgroundResource(R.drawable.bg_trans_blue);
                        tv03.setTextColor(getResources().getColor(R.color.blue_bg_4B96F3));
                        tv03.setText("去支付");

                    } else {
                        //我是卖家，等待付钱
                        tvStatus.setText("等待对方支付中");
                        tvStatusMemo.setText("已通知对方尽快支付担保金");
                        tvTime.setText("剩余时间" + longHours01 + ":" + longMinutes01 + "，超时将自动取消交易");
                        tv03.setVisibility(View.INVISIBLE);
                    }


                } else if (guaranteeDetailBean.getStatus() == 3) {
                    //是已打款后台未确认
                    tvBjStatus.setVisibility(View.GONE);
                    tvTime.setVisibility(View.GONE);
                    view01.setVisibility(View.GONE);
                    rlayout02.setVisibility(View.GONE);
                    view02.setVisibility(View.GONE);
                    rlayout03.setVisibility(View.GONE);
                    rlayout04.setVisibility(View.GONE);
                    rlayout05.setVisibility(View.GONE);
                    view5.setVisibility(View.GONE);
                    rlayoutDoneTime.setVisibility(View.GONE);
                    tv01.setVisibility(View.VISIBLE);
                    tv02.setVisibility(View.VISIBLE);
                    tv04.setVisibility(View.INVISIBLE);
                    tv03.setVisibility(View.INVISIBLE);
                    tv05.setVisibility(View.GONE);

                    if (isBuyer) {
                        //我是买家,付钱
                        if (is_initiate == 1) {
                            //我是发起方
                            tvStatus.setText("对方已同意交易");
                        } else {
                            tvStatus.setText("已同意交易");
                        }
                        tvStatusMemo.setText("您已支付，请等待系统确认到账情况");
                        tv02.setBackgroundResource(R.drawable.bg_trans_organe);
                        tv02.setTextColor(getResources().getColor(R.color.orange));
                        tv02.setText("支付详情");

                        tv01.setBackgroundResource(R.drawable.bg_trans_gray);
                        tv01.setTextColor(getResources().getColor(R.color._333));
                        tv01.setText("电话联系");

                    } else {
                        //我是卖家，等待付钱
                        tvStatus.setText("等待对方支付中");
                        tvStatusMemo.setText("已通知对方尽快支付担保金");
                        tv01.setBackgroundResource(R.drawable.bg_trans_gray);
                        tv01.setTextColor(getResources().getColor(R.color._333));
                        tv01.setText("取消交易");
                        tv02.setBackgroundResource(R.drawable.bg_trans_gray);
                        tv02.setTextColor(getResources().getColor(R.color._333));
                        tv02.setText("电话联系");
                    }


                } else if (guaranteeDetailBean.getStatus() == 4) {
                    //4：后台已确认合同进行中
                    tvBjStatus.setVisibility(View.GONE);
                    tvTime.setVisibility(View.VISIBLE);
                    view01.setVisibility(View.VISIBLE);
                    rlayout02.setVisibility(View.VISIBLE);
                    view02.setVisibility(View.VISIBLE);
                    rlayout05.setVisibility(View.GONE);
                    view5.setVisibility(View.GONE);
                    rlayoutDoneTime.setVisibility(View.GONE);
                    //判断是否补交担保金
                    int recharge_status = guaranteeDetailBean.getRecharge_status();//0：状态正常，1：发起充值中后台未确认
                    //是否申请结算
                    int billing_status = guaranteeDetailBean.getBilling_status();//	当前结算状态0：状态正常，1：乙方申请结算，甲方未操做
                    //退款状态判断 0:正常，1：申请中，3，退款失败
                    int refund_status = guaranteeDetailBean.getRefund_status();
                    int serving_status = guaranteeDetailBean.getServing_status();//0：无客服介入，1：介入中，2：介入结束
                    if (isBuyer) {
                        rlayout03.setVisibility(View.VISIBLE);
                        rlayout04.setVisibility(View.GONE);
                        tvStatus.setText("您已支付交易款");
                        tvStatusMemo.setText("确认交易达成后，请进行结算");
                        tvTime.setText("结算后，对方将收到交易款项");
                        tv01.setVisibility(View.VISIBLE);
                        tv02.setVisibility(View.VISIBLE);
                        tv04.setVisibility(View.VISIBLE);
                        tv03.setVisibility(View.VISIBLE);
                        tv05.setVisibility(View.GONE);
                        tv01.setBackgroundResource(R.drawable.bg_trans_gray);
                        tv01.setTextColor(getResources().getColor(R.color._333));
                        tv01.setText("申请退款");

                        tv02.setBackgroundResource(R.drawable.bg_trans_gray);
                        tv02.setTextColor(getResources().getColor(R.color._333));
                        tv02.setText("电话联系");

                        tv03.setBackgroundResource(R.drawable.bg_trans_blue);
                        tv03.setTextColor(getResources().getColor(R.color.blue_bg_4B96F3));
                        tv03.setText("部分结算");

                        tv04.setBackgroundResource(R.drawable.bg_trans_blue);
                        tv04.setTextColor(getResources().getColor(R.color.blue_bg_4B96F3));
                        tv04.setText("全额结算");
                        //我是买家
                        if (recharge_status == 1) {
                            tvBjStatus.setVisibility(View.VISIBLE);
                            tvBjStatus.setText("已补交、请等待系统确认到账情况");
                            tvOperation.setText("支付详情");
                            tvOperation.setBackgroundResource(R.drawable.bg_trans_organe);
                            tvOperation.setTextColor(getResources().getColor(R.color.orange));
                            //担保金审核中，不能申请退款，申请退款按钮不出现
                            tv01.setVisibility(View.GONE);
                        } else if (recharge_status == 0) {
                            tvOperation.setBackgroundResource(R.drawable.bg_trans_blue);
                            tvOperation.setTextColor(getResources().getColor(R.color.blue_bg_4B96F3));
                            tvOperation.setText("补交担保金");
                            tv01.setVisibility(View.VISIBLE);
                        }
                        //判断卖方是否申请结算
                        if (billing_status == 1) {
                            tvStatusMemo.setText("对方已申请进行结算");
                            tvTime.setText("结算后，对方将收到交易款项");
                        }
                        if (refund_status == 1) {
                            //买方申请退款中

                            tv01.setVisibility(View.VISIBLE);
                            tv02.setVisibility(View.VISIBLE);
                            tv04.setVisibility(View.GONE);
                            tv03.setVisibility(View.GONE);
                            tv05.setVisibility(View.GONE);
                            tv01.setBackgroundResource(R.drawable.bg_trans_organe);
                            tv01.setTextColor(getResources().getColor(R.color.orange));
                            tv01.setText("退款中");
                            tv02.setBackgroundResource(R.drawable.bg_trans_gray);
                            tv02.setTextColor(getResources().getColor(R.color._333));
                            tv02.setText("电话联系");
                            //补交担保金按钮不出现
                            rlayout03.setVisibility(View.GONE);
                            rlayout04.setVisibility(View.VISIBLE);
                            tvPriceSs.setTextColor(getResources().getColor(R.color._ee634e));
                            if (guaranteeDetailBean.getRemaining_amount().contains(".")) {
                                String[] pricr01 = guaranteeDetailBean.getRemaining_amount().split("\\.");

                                String text1="¥ ";
                                String text=text1+pricr01[0];
                                final SpannableStringBuilder sb = new SpannableStringBuilder(text);
                                sb.setSpan(new AbsoluteSizeSpan(14, true), text1.length(), text.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                                sb.setSpan(new StyleSpan(Typeface.BOLD), text1.length(), text.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                                tvPriceSs.setText(sb);//担保金额
                            } else {

                                String text1="¥ ";
                                String text=text1+guaranteeDetailBean.getRemaining_amount();
                                final SpannableStringBuilder sb = new SpannableStringBuilder(text);
                                sb.setSpan(new AbsoluteSizeSpan(14, true), text1.length(), text.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                                sb.setSpan(new StyleSpan(Typeface.BOLD), text1.length(), text.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                                tvPriceSs.setText(sb);//担保金额
                            }

                        } else if (refund_status == 3) {
                            //退款失败
                            tv01.setVisibility(View.VISIBLE);
                            tv02.setVisibility(View.VISIBLE);
                            tv04.setVisibility(View.VISIBLE);
                            tv03.setVisibility(View.VISIBLE);
                            tv05.setVisibility(View.GONE);
                            tv01.setBackgroundResource(R.drawable.bg_trans_red);
                            tv01.setTextColor(getResources().getColor(R.color.bg_color_red_DE6654));
                            tv01.setText("退款失败");
                            tv02.setBackgroundResource(R.drawable.bg_trans_gray);
                            tv02.setTextColor(getResources().getColor(R.color._333));
                            tv02.setText("电话联系");
                            tv03.setBackgroundResource(R.drawable.bg_trans_blue);
                            tv03.setTextColor(getResources().getColor(R.color.blue_bg_4B96F3));
                            tv03.setText("部分结算");

                            tv04.setBackgroundResource(R.drawable.bg_trans_blue);
                            tv04.setTextColor(getResources().getColor(R.color.blue_bg_4B96F3));
                            tv04.setText("全额结算");

                        }
                        //卖方申请结算申请客服介入处理,
                        ////0：无客服介入，1：介入中，2：介入结束
                        if (serving_status == 0) {

                        } else if (serving_status == 1) {
                            if (guaranteeDetailBean.getServing_type() == 2 || guaranteeDetailBean.getServing_type() == 3) {
                                tvStatus.setText("您已支付交易款");
                                tvStatusMemo.setText("对方已申请客服介入");
                                if (guaranteeDetailBean.getServing_type() == 2) {
                                    tvTime.setText("对方申请: 部分结算");
                                } else if (guaranteeDetailBean.getServing_type() == 3) {
                                    tvTime.setText("对方申请: 全额结算");
                                }
                                tv01.setVisibility(View.VISIBLE);
                                tv02.setVisibility(View.VISIBLE);
                                tv04.setVisibility(View.VISIBLE);
                                tv03.setVisibility(View.VISIBLE);
                                tv05.setVisibility(View.GONE);
                                tv01.setBackgroundResource(R.drawable.bg_trans_organe);
                                tv01.setTextColor(getResources().getColor(R.color.orange));
                                tv01.setText("申请详情");
                                tv02.setBackgroundResource(R.drawable.bg_trans_gray);
                                tv02.setTextColor(getResources().getColor(R.color._333));
                                tv02.setText("电话联系");
                                tv03.setBackgroundResource(R.drawable.bg_trans_blue);
                                tv03.setTextColor(getResources().getColor(R.color.blue_bg_4B96F3));
                                tv03.setText("部分结算");

                                tv04.setBackgroundResource(R.drawable.bg_trans_blue);
                                tv04.setTextColor(getResources().getColor(R.color.blue_bg_4B96F3));
                                tv04.setText("全额结算");
                            }
                        } else if (serving_status == 2) {
                            if (guaranteeDetailBean.getServing_type() == 2 || guaranteeDetailBean.getServing_type() == 3) {
                                tvStatus.setText("您已支付交易款");
                                tvStatusMemo.setText("对方已申请客服介入");
                                tvTime.setText("处理结果: " + guaranteeDetailBean.getServing_desc());
                                tv01.setVisibility(View.VISIBLE);
                                tv02.setVisibility(View.VISIBLE);
                                tv04.setVisibility(View.VISIBLE);
                                tv03.setVisibility(View.VISIBLE);
                                tv05.setVisibility(View.GONE);
                                tv01.setBackgroundResource(R.drawable.bg_trans_organe);
                                tv01.setTextColor(getResources().getColor(R.color.orange));
                                tv01.setText("申请退款");
                                tv02.setBackgroundResource(R.drawable.bg_trans_gray);
                                tv02.setTextColor(getResources().getColor(R.color._333));
                                tv02.setText("电话联系");
                                tv03.setBackgroundResource(R.drawable.bg_trans_blue);
                                tv03.setTextColor(getResources().getColor(R.color.blue_bg_4B96F3));
                                tv03.setText("部分结算");

                                tv04.setBackgroundResource(R.drawable.bg_trans_blue);
                                tv04.setTextColor(getResources().getColor(R.color.blue_bg_4B96F3));
                                tv04.setText("全额结算");
                            }

                        }


                    } else {
                        //我是卖家
                        tvStatus.setText("交易进行中");
                        tvStatusMemo.setText("对方已支付担保金");
                        tvTime.setText("请您尽快完成交易，交易达成后您可向对方申请结算");
                        rlayout03.setVisibility(View.GONE);
                        rlayout04.setVisibility(View.VISIBLE);
                        tvPriceSs.setTextColor(getResources().getColor(R.color._ee634e));
                        if (guaranteeDetailBean.getRemaining_amount().contains(".")) {
                            String[] pricr01 = guaranteeDetailBean.getRemaining_amount().split("\\.");

                            String text1="¥ ";
                            String text=text1+pricr01[0];
                            final SpannableStringBuilder sb = new SpannableStringBuilder(text);
                            sb.setSpan(new AbsoluteSizeSpan(14, true), text1.length(), text.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                            sb.setSpan(new StyleSpan(Typeface.BOLD), text1.length(), text.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                            tvPriceSs.setText(sb);//担保金额
                        } else {
                            String text1="¥ ";
                            String text=text1+guaranteeDetailBean.getRemaining_amount();
                            final SpannableStringBuilder sb = new SpannableStringBuilder(text);
                            sb.setSpan(new AbsoluteSizeSpan(14, true), text1.length(), text.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                            sb.setSpan(new StyleSpan(Typeface.BOLD), text1.length(), text.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                            tvPriceSs.setText(sb);//担保金额

                        }
                        tv01.setVisibility(View.VISIBLE);
                        tv02.setVisibility(View.GONE);
                        tv04.setVisibility(View.INVISIBLE);
                        tv03.setVisibility(View.VISIBLE);
                        tv05.setVisibility(View.VISIBLE);
                        tv01.setBackgroundResource(R.drawable.bg_trans_gray);
                        tv01.setTextColor(getResources().getColor(R.color._333));
                        tv01.setText("电话联系");

                        tv03.setBackgroundResource(R.drawable.bg_trans_blue);
                        tv03.setTextColor(getResources().getColor(R.color.blue_bg_4B96F3));
                        tv03.setText("申请结算");

                        if (refund_status == 1 || refund_status == 3) {
                            //买方申请退款中
                            tvStatusMemo.setText("对方申请退款");

                            long time = (guaranteeDetailBean.getRefund_time() + 72 * 60 * 60) - guaranteeDetailBean.getNow_time();
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
                            if (refund_status == 3) {
                                tvTime.setVisibility(View.GONE);
                            } else {
                                tvTime.setVisibility(View.VISIBLE);
                                tvTime.setText("剩余时间" + longHours01 + ":" + longMinutes01 + "，请尽快处理，超时将自动退款");
                            }
                            tv01.setVisibility(View.VISIBLE);
                            tv02.setVisibility(View.VISIBLE);
                            tv04.setVisibility(View.GONE);
                            tv03.setVisibility(View.GONE);
                            tv05.setVisibility(View.GONE);
                            tv01.setBackgroundResource(R.drawable.bg_trans_gray);
                            tv01.setTextColor(getResources().getColor(R.color._333));
                            tv01.setText("电话联系");

                            tv02.setBackgroundResource(R.drawable.bg_trans_organe);
                            tv02.setTextColor(getResources().getColor(R.color.orange));
                            tv02.setText("退款详情");


                        }

                        //申请客服介入处理
                        //卖方申请结算申请客服介入处理
                        if (serving_status == 0) {

                        } else if (serving_status == 1) {
                            if (guaranteeDetailBean.getServing_type() == 2 || guaranteeDetailBean.getServing_type() == 3) {
                                tvStatus.setText("交易进行中");
                                tvStatusMemo.setText("您已申请客服介入，请等待客服处理");
                                if (guaranteeDetailBean.getServing_type() == 2) {
                                    tvTime.setText("申请: 部分结算");
                                } else if (guaranteeDetailBean.getServing_type() == 3) {
                                    tvTime.setText("申请: 全额结算");
                                }
                                tv01.setVisibility(View.VISIBLE);
                                tv02.setVisibility(View.VISIBLE);
                                tv04.setVisibility(View.GONE);
                                tv03.setVisibility(View.GONE);
                                tv05.setVisibility(View.GONE);
                                tv01.setBackgroundResource(R.drawable.bg_trans_organe);
                                tv01.setTextColor(getResources().getColor(R.color.orange));
                                tv01.setText("申请详情");
                                tv02.setBackgroundResource(R.drawable.bg_trans_gray);
                                tv02.setTextColor(getResources().getColor(R.color._333));
                                tv02.setText("电话联系");
                            }


                        } else if (serving_status == 2) {
                            if (guaranteeDetailBean.getServing_type() == 2 || guaranteeDetailBean.getServing_type() == 3) {
                                tvStatus.setText("交易进行中");
                                tvStatusMemo.setText("您的申请已被客服处理");
                                tvTime.setText("处理结果:" + guaranteeDetailBean.getServing_desc());
                                tv01.setVisibility(View.VISIBLE);
                                tv02.setVisibility(View.GONE);
                                tv04.setVisibility(View.GONE);
                                tv03.setVisibility(View.VISIBLE);
                                tv05.setVisibility(View.VISIBLE);
                                tv03.setBackgroundResource(R.drawable.bg_trans_organe);
                                tv03.setTextColor(getResources().getColor(R.color.orange));
                                tv03.setText("申请结算");
                                tv01.setBackgroundResource(R.drawable.bg_trans_gray);
                                tv01.setTextColor(getResources().getColor(R.color._333));
                                tv01.setText("电话联系");
                            }


                        }

                    }
                    if (guaranteeDetailBean.getSettlement_amount().contains(".")) {
                        String[] pricr01 = guaranteeDetailBean.getSettlement_amount().split("\\.");
                        String text1="-¥ " ;
                        String text=text1+pricr01[0];
                        final SpannableStringBuilder sb = new SpannableStringBuilder(text);
                        sb.setSpan(new AbsoluteSizeSpan(14, true), text1.length(), text.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                        sb.setSpan(new StyleSpan(Typeface.BOLD), text1.length(), text.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                        tvPriceY.setText(sb);//担保金额

                    } else {
                        String text1="-¥ " ;
                        String text=text1+guaranteeDetailBean.getSettlement_amount();
                        final SpannableStringBuilder sb = new SpannableStringBuilder(text);
                        sb.setSpan(new AbsoluteSizeSpan(14, true), text1.length(), text.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                        sb.setSpan(new StyleSpan(Typeface.BOLD), text1.length(), text.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                        tvPriceY.setText(sb);//担保金额

                    }

                    if (guaranteeDetailBean.getRemaining_amount().contains(".")) {
                        String[] pricr01 = guaranteeDetailBean.getRemaining_amount().split("\\.");
                        String text1="¥ ";
                        String text=text1+pricr01[0];
                        final SpannableStringBuilder sb = new SpannableStringBuilder(text);
                        sb.setSpan(new AbsoluteSizeSpan(14, true), text1.length(), text.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                        sb.setSpan(new StyleSpan(Typeface.BOLD), text1.length(), text.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                        tvPriceS.setText(sb);//担保金额
                    } else {
                        String text1="¥ ";
                        String text=text1+ guaranteeDetailBean.getRemaining_amount();
                        final SpannableStringBuilder sb = new SpannableStringBuilder(text);
                        sb.setSpan(new AbsoluteSizeSpan(14, true), text1.length(), text.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                        sb.setSpan(new StyleSpan(Typeface.BOLD), text1.length(), text.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                        tvPriceS.setText(sb);//担保金额
                    }


                } else if (guaranteeDetailBean.getStatus() == 5) {
                    //是接收方不同意发起担保交易
                    tvBjStatus.setVisibility(View.GONE);
                    tvTime.setVisibility(View.GONE);
                    view01.setVisibility(View.GONE);
                    rlayout02.setVisibility(View.GONE);
                    view02.setVisibility(View.GONE);
                    rlayout03.setVisibility(View.GONE);
                    rlayout04.setVisibility(View.GONE);
                    rlayout05.setVisibility(View.GONE);
                    view5.setVisibility(View.GONE);
                    rlayoutDoneTime.setVisibility(View.GONE);
                    tv01.setVisibility(View.VISIBLE);
                    tv02.setVisibility(View.INVISIBLE);
                    tv04.setVisibility(View.INVISIBLE);
                    tv03.setVisibility(View.INVISIBLE);
                    tv05.setVisibility(View.GONE);
                    tvStatus.setText("交易已取消");
                    if (is_initiate == 1) {
                        //我是发起方
                        tvStatusMemo.setText("对方已取消交易");
                    } else {
                        tvStatusMemo.setText("您取消了交易");
                    }

                    tv01.setBackgroundResource(R.drawable.bg_trans_blue);
                    tv01.setTextColor(getResources().getColor(R.color.blue_bg_4B96F3));
                    tv01.setText("再次交易");


                } else if (guaranteeDetailBean.getStatus() == 6) {
                    //取消交易，进行判断取消操作是由谁发起的   取消人cancel_user_id进行判断
                    tvBjStatus.setVisibility(View.GONE);
                    tvTime.setVisibility(View.GONE);
                    view01.setVisibility(View.GONE);
                    rlayout02.setVisibility(View.GONE);
                    view02.setVisibility(View.GONE);
                    rlayout03.setVisibility(View.GONE);
                    rlayout04.setVisibility(View.GONE);
                    rlayout05.setVisibility(View.GONE);
                    view5.setVisibility(View.GONE);
                    rlayoutDoneTime.setVisibility(View.GONE);
                    tv01.setVisibility(View.VISIBLE);
                    tv02.setVisibility(View.INVISIBLE);
                    tv04.setVisibility(View.INVISIBLE);
                    tv03.setVisibility(View.INVISIBLE);
                    tv05.setVisibility(View.GONE);
                    tvStatus.setText("交易已取消");
                    int cancel_user_id = guaranteeDetailBean.getCancel_user_id();
                    if (cancel_user_id == mUserId) {
                        //自己取消
                        tvStatusMemo.setText("您取消了交易");
                    } else {
                        tvStatusMemo.setText("对方已取消交易");
                    }
                    tv01.setBackgroundResource(R.drawable.bg_trans_blue);
                    tv01.setTextColor(getResources().getColor(R.color.blue_bg_4B96F3));
                    tv01.setText("再次交易");

                } else if (guaranteeDetailBean.getStatus() == 7) {
                    //接收方未确认交易超时
                    tvBjStatus.setVisibility(View.GONE);
                    tvTime.setVisibility(View.GONE);
                    view01.setVisibility(View.GONE);
                    rlayout02.setVisibility(View.GONE);
                    view02.setVisibility(View.GONE);
                    rlayout03.setVisibility(View.GONE);
                    rlayout04.setVisibility(View.GONE);
                    rlayout05.setVisibility(View.GONE);
                    view5.setVisibility(View.GONE);
                    rlayoutDoneTime.setVisibility(View.GONE);
                    tv01.setVisibility(View.VISIBLE);
                    tv02.setVisibility(View.INVISIBLE);
                    tv04.setVisibility(View.INVISIBLE);
                    tv03.setVisibility(View.INVISIBLE);
                    tv05.setVisibility(View.GONE);
                    tvStatus.setText("交易已取消");
                    tvStatusMemo.setText("超时未确认，已自动取消交易");
                    tv01.setBackgroundResource(R.drawable.bg_trans_blue);
                    tv01.setTextColor(getResources().getColor(R.color.blue_bg_4B96F3));
                    tv01.setText("再次交易");
                } else if (guaranteeDetailBean.getStatus() == 8) {
                    //买家未支付超时
                    tvBjStatus.setVisibility(View.GONE);
                    tvTime.setVisibility(View.GONE);
                    view01.setVisibility(View.GONE);
                    rlayout02.setVisibility(View.GONE);
                    view02.setVisibility(View.GONE);
                    rlayout03.setVisibility(View.GONE);
                    rlayout04.setVisibility(View.GONE);
                    rlayout05.setVisibility(View.GONE);
                    view5.setVisibility(View.GONE);
                    rlayoutDoneTime.setVisibility(View.GONE);
                    tv01.setVisibility(View.VISIBLE);
                    tv02.setVisibility(View.INVISIBLE);
                    tv04.setVisibility(View.INVISIBLE);
                    tv03.setVisibility(View.INVISIBLE);
                    tv05.setVisibility(View.GONE);
                    tvStatus.setText("交易已取消");
                    tvStatusMemo.setText("超时未支付，已自动取消交易");
                    tv01.setBackgroundResource(R.drawable.bg_trans_blue);
                    tv01.setTextColor(getResources().getColor(R.color.blue_bg_4B96F3));
                    tv01.setText("再次交易");
                } else if (guaranteeDetailBean.getStatus() == 9) {
                    //买家申请退款超时，退款成功
                    tvBjStatus.setVisibility(View.GONE);
                    tvTime.setVisibility(View.GONE);
                    view01.setVisibility(View.VISIBLE);
                    rlayout02.setVisibility(View.VISIBLE);
                    view02.setVisibility(View.GONE);
                    rlayout03.setVisibility(View.GONE);
                    rlayout04.setVisibility(View.GONE);
                    rlayout05.setVisibility(View.VISIBLE);
                    view5.setVisibility(View.VISIBLE);
                    rlayoutDoneTime.setVisibility(View.VISIBLE);
                    tv01.setVisibility(View.VISIBLE);
                    tv02.setVisibility(View.VISIBLE);
                    tv04.setVisibility(View.INVISIBLE);
                    tv03.setVisibility(View.INVISIBLE);
                    tv05.setVisibility(View.GONE);
                    tvStatus.setText("交易关闭");
                    if (isBuyer) {
                        tvStatusMemo.setText("已成功退款");

                    } else {
                        tvStatusMemo.setText("对方已退款");
                    }
                    tv01.setBackgroundResource(R.drawable.bg_trans_organe);
                    tv01.setTextColor(getResources().getColor(R.color.orange));
                    tv01.setText("退款详情");
                    tv02.setBackgroundResource(R.drawable.bg_trans_blue);
                    tv02.setTextColor(getResources().getColor(R.color.blue_bg_4B96F3));
                    tv02.setText("再次交易");
                    //已结算，已退款，成交时间
                    //剩下字段未给
                    tvDoneTime.setText(TimeUtils.time2ActTime(guaranteeDetailBean.getEnd_time() * 1000));
                    if (guaranteeDetailBean.getSettlement_amount().contains(".")) {
                        String[] pricr01 = guaranteeDetailBean.getSettlement_amount().split("\\.");
                        String text1="-¥ ";
                        String text=text1+ pricr01[0];
                        final SpannableStringBuilder sb = new SpannableStringBuilder(text);
                        sb.setSpan(new AbsoluteSizeSpan(14, true), text1.length(), text.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                        sb.setSpan(new StyleSpan(Typeface.BOLD), text1.length(), text.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                        tvPriceY.setText(sb);//担保金额
                    } else {
                        String text1="-¥ ";
                        String text=text1+ guaranteeDetailBean.getSettlement_amount();
                        final SpannableStringBuilder sb = new SpannableStringBuilder(text);
                        sb.setSpan(new AbsoluteSizeSpan(14, true), text1.length(), text.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                        sb.setSpan(new StyleSpan(Typeface.BOLD), text1.length(), text.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                        tvPriceY.setText(sb);//担保金额

                    }

                    if (guaranteeDetailBean.getRefund_amount().contains(".")) {
                        String[] pricr01 = guaranteeDetailBean.getRefund_amount().split("\\.");

                        String text1="-¥ ";
                        String text=text1+pricr01[0];
                        final SpannableStringBuilder sb = new SpannableStringBuilder(text);
                        sb.setSpan(new AbsoluteSizeSpan(14, true), text1.length(), text.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                        sb.setSpan(new StyleSpan(Typeface.BOLD), text1.length(), text.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                        tvPriceT.setText(sb);//担保金额


                    } else {
                        String text1="-¥ ";
                        String text=text1+guaranteeDetailBean.getRefund_amount();
                        final SpannableStringBuilder sb = new SpannableStringBuilder(text);
                        sb.setSpan(new AbsoluteSizeSpan(14, true), text1.length(), text.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                        sb.setSpan(new StyleSpan(Typeface.BOLD), text1.length(), text.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                        tvPriceT.setText(sb);//担保金额
                    }

                }


                dismissBookingToast();

            }

            @Override
            public void onFailed(int code, String msg) {
                dismissBookingToast();
                ToastUtils.showCentetImgToast(mContext, msg);

            }
        });
    }

    @OnClick({R.id.rlayout_info, R.id.bt_back, R.id.tv_chat, R.id.ycompany_intromoreRl, R.id.tv_call_customer_service, R.id.tv_01, R.id.tv_02, R.id.tv_03, R.id.tv_04, R.id.tv_05, R.id.tv_operation})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rlayout_info:
                PersonCentetActivity.start(TransactionDetailsActivity.this, mBean.getObj_user_info().getUser_id() + "");
                break;
            case R.id.bt_back:
                finish();
                break;

            case R.id.tv_chat:
                //在线联系
                //IMUtils.singleChatForResource(TransactionDetailsActivity.this, String.valueOf(mBean.getObj_user_info().getUser_id()), mBean.getObj_user_info().getRealname(), 0, mBean.getObj_user_info().getCompany() + mBean.getObj_user_info().getPosition(), mBean.getObj_user_info().getHead_pic(), "0");
                break;
            case R.id.ycompany_intromoreRl:
                mcompanyIntroducetv.setMaxLines(Integer.MAX_VALUE);
                ShowUtils.showViewVisible(bcompanyIntromoreImg, View.GONE);
                ShowUtils.showViewVisible(ycompanyIntromoreRl, View.GONE);
                break;
            case R.id.tv_call_customer_service:
                //联系客服,聊天
                //IMUtils.singleChatForResource(TransactionDetailsActivity.this, "50276", "", 0, "", "", "0");
                break;
            case R.id.tv_01:
                //取消交易//电话联系//再次交易//退款详情//电话联系//申请客服介入详情
                if (tv01.getText().toString().equals("取消交易")) {
                    if (toBeconfirmed.equals("1")) {
                        agreeGuarantee(mId, 0);
                    } else {
                        //取消交易
                        cancelGuarantee(mId);
                    }
                } else if (tv01.getText().toString().equals("电话联系")) {
                    goTocall(mBean.getObj_user_info().getMobile());

                } else if (tv01.getText().toString().equals("再次交易")) {
                    LaunchTransactionAvtivity.start(TransactionDetailsActivity.this, mBean.getObj_user_info().getUser_id() + "", 0);
                    finish();
                } else if (tv01.getText().toString().equals("申请退款")) {
                    Gson gson = new Gson();
                    String json = gson.toJson(mBean);
                    ApplicationRefundAvtivity.startSimpleEidtForResult(TransactionDetailsActivity.this, Integer.parseInt(mId), json, 32);
                } else if (tv01.getText().toString().equals("退款中")) {
                    RefundDetailActivity.startSimpleEidtForResult(TransactionDetailsActivity.this, mBean.getOrder_sn(), Integer.parseInt(mId), 32);

                } else if (tv01.getText().toString().equals("退款失败")) {
                    RefundDetailActivity.startSimpleEidtForResult(TransactionDetailsActivity.this, mBean.getOrder_sn(), Integer.parseInt(mId), 32);

                } else if (tv01.getText().toString().equals("退款详情")) {
                    RefundDetailActivity.startSimpleEidtForResult(TransactionDetailsActivity.this, mBean.getOrder_sn(), Integer.parseInt(mId), 32);

                } else if (tv01.getText().toString().equals("申请详情")) {
                    CustomeServiceDetailActivity.start(TransactionDetailsActivity.this, mBean.getOrder_sn(), Integer.parseInt(mId));

                }

                break;
            case R.id.tv_02:
                //确认交易//电话联系//支付详情//再次交易//再次交易
                if (tv02.getText().toString().equals("确认交易")) {
                    if (toBeconfirmed.equals("1")) {
                        showDialog();

                    }
                } else if (tv02.getText().toString().equals("电话联系")) {
                    //点击拨打对方手机号
                    goTocall(mBean.getObj_user_info().getMobile());
                } else if (tv02.getText().toString().equals("再次交易")) {
                    LaunchTransactionAvtivity.start(TransactionDetailsActivity.this, mBean.getObj_user_info().getUser_id() + "",0);
                    finish();
                } else if (tv02.getText().toString().equals("支付详情")) {
                    PayDetailActivity.start(TransactionDetailsActivity.this, mBean.getOrder_sn(), 1);

                } else if (tv02.getText().toString().equals("退款详情")) {
                    RefundDetailActivity.startSimpleEidtForResult(TransactionDetailsActivity.this, mBean.getOrder_sn(), Integer.parseInt(mId), 32);

                }


                break;
            case R.id.tv_03:
                //去支付
                if (tv03.getText().toString().equals("去支付")) {
                    PayDespositActivity.startSimpleEidtForResult(TransactionDetailsActivity.this, Integer.parseInt(mId), tvPriceD.getText().toString().replace("¥", ""), 32);
                } else if (tv03.getText().toString().equals("部分结算")) {
                    //买家跳转结算界面输入金额
                    SettlementActivity.startSimpleEidtForResult(TransactionDetailsActivity.this, Integer.parseInt(mId), mBean.getRemaining_amount(), 33);
                } else if (tv03.getText().toString().equals("申请结算")) {
                    //卖家申请结算
                    doSettlement();

                }
                break;
            case R.id.tv_04:
                if (tv04.getText().toString().equals("全额结算")) {
                    //买家直接全额结算，不用输入金额
                    new QLTipTwoDialog.Builder(TransactionDetailsActivity.this)
                            .setCancelable(true)
                            .setCancelableOnTouchOutside(true)
                            .setTitle("确认结算全部剩余担保金")
                            .setMessage("全额结算后，交易将会关闭")
                            .setPositiveButton("确认结算", new QLTipTwoDialog.IPositiveCallback() {
                                @Override
                                public void onClick() {
                                    doSettlementTwo();

                                }
                            })
                            .setNegativeButton("取消", new QLTipTwoDialog.INegativeCallback() {
                                @Override
                                public void onClick() {
                                    dissMissDialog();

                                }
                            })
                            .show(TransactionDetailsActivity.this);


                }
                break;
            case R.id.tv_05:
                //申请客服介入
                ApplicatuinCustomeServiceActivity.startSimpleEidtForResult(TransactionDetailsActivity.this, "2", Integer.parseInt(mId), 32);
                break;
            case R.id.tv_operation:
                //补交担保金//支付详情
                if (tvOperation.getText().toString().equals("支付详情")) {
                    PayDetailActivity.start(TransactionDetailsActivity.this, mBean.getOrder_sn(), 4);

                } else if (tvOperation.getText().toString().equals("补交担保金")) {

                    PayDespositAfterActivity.startSimpleEidtForResult(TransactionDetailsActivity.this, Integer.parseInt(mId), 32);
                }
                break;
        }
    }


    /**
     * 买家申请结算
     */
    private void doSettlementTwo() {
        showBookingToast(2);
        RequestManager.getInstance().guaranteeSettlement(Integer.parseInt(mId), mBean.getRemaining_amount(), new AllResultDoCallback() {
            @Override
            public void onSuccess(String msg) {
                dismissBookingToast();
                ToastUtils.showCentetToast(TransactionDetailsActivity.this, msg);
                getDatas();

            }

            @Override
            public void onFailed(int code, String msg) {
                dismissBookingToast();
                ToastUtils.showCentetToast(TransactionDetailsActivity.this, msg);
            }
        });
    }

    /**
     * 卖家申请结算
     */
    private void doSettlement() {
        showBookingToast(2);
        RequestManager.getInstance().applicationSettlement(Integer.parseInt(mId), new AllResultDoCallback() {
            @Override
            public void onSuccess(String msg) {
                dismissBookingToast();
                ToastUtils.showCentetToast(TransactionDetailsActivity.this, msg);
                getDatas();

            }

            @Override
            public void onFailed(int code, String msg) {
                dismissBookingToast();
                ToastUtils.showCentetToast(TransactionDetailsActivity.this, msg);
            }
        });
    }

    /**
     * 取消交易
     *
     * @param mId 交易id
     */
    private void cancelGuarantee(String mId) {
        showBookingToast(2);
        RequestManager.getInstance().cancelGuarantee(Integer.parseInt(mId), new AllResultDoCallback() {
            @Override
            public void onSuccess(String msg) {
                getDatas();
                dismissBookingToast();
                ToastUtils.showCentetImgToast(TransactionDetailsActivity.this, msg);
            }

            @Override
            public void onFailed(int code, String msg) {
                dismissBookingToast();
                ToastUtils.showCentetImgToast(TransactionDetailsActivity.this, msg);

            }
        });
    }


    /**
     * 同意或者不同意交易
     *
     * @param mId      交易id
     * @param is_agree 1同意，0不同意
     */
    private void agreeGuarantee(String mId, int is_agree) {
        showBookingToast(2);
        RequestManager.getInstance().agreeGuarantee(Integer.parseInt(mId), is_agree, new AllResultDoCallback() {
            @Override
            public void onSuccess(String msg) {
                getDatas();
                dismissBookingToast();
                ToastUtils.showCentetImgToast(TransactionDetailsActivity.this, msg);
            }

            @Override
            public void onFailed(int code, String msg) {
                dismissBookingToast();
                ToastUtils.showCentetImgToast(TransactionDetailsActivity.this, msg);

            }
        });
    }


    @AfterPermissionGranted(11111)
    private void goTocall(String phoneNum) {
        if (EasyPermissions.hasPermissions(this, new String[]{Manifest.permission.CALL_PHONE})) {
            Intent intent1 = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phoneNum));
            intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            if (ActivityCompat.checkSelfPermission(TransactionDetailsActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                ToastUtils.showCentetToast(TransactionDetailsActivity.this, "未获取电话权限");
                return;
            }
            startActivity(intent1);
        } else {
            EasyPermissions.requestPermissions(TransactionDetailsActivity.this,
                    "系统需要获取您的电话权限",
                    11111, new String[]{Manifest.permission.CALL_PHONE});
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {

            if (requestCode == 32) {
                getDatas();


            } else if (requestCode == 33) {
                getDatas();
                new QLTipDialog.Builder(TransactionDetailsActivity.this)
                        .setCancelable(true)
                        .setCancelableOnTouchOutside(true)
                        .setTitle("结算成功")
                        .setMessage("结算金额已进入对方钱包")
                        .setNegativeButton("我知道了", new QLTipDialog.INegativeCallback() {
                            @Override
                            public void onClick() {

                            }
                        })
                        .show(TransactionDetailsActivity.this);
            }
        }

    }


    private void showDialog() {
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_secured_transactiona_greement, null, false);
        final AlertDialog dialog = new AlertDialog.Builder(this).setView(view).create();
        WebView agree_ment = view.findViewById(R.id.agree_ment);
        //声明WebSettings子类
        WebSettings webSettings = agree_ment.getSettings();
        //设置自适应屏幕，两者合用
        webSettings.setUseWideViewPort(true); //将图片调整到适合webview的大小
        webSettings.setLoadWithOverviewMode(true); // 缩放至屏幕的大小
        //缩放操作
        webSettings.setSupportZoom(true); //支持缩放，默认为true。是下面那个的前提。
        webSettings.setBuiltInZoomControls(true); //设置内置的缩放控件。若为false，则该WebView不可缩放
        webSettings.setDisplayZoomControls(false); //隐藏原生的缩放控件
        //其他细节操作
        webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK); //关闭webview中缓存
        webSettings.setAllowFileAccess(true); //设置可以访问文件
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true); //支持通过JS打开新窗口
        webSettings.setLoadsImagesAutomatically(true); //支持自动加载图片
        webSettings.setDefaultTextEncodingName("utf-8");//设置编码格式

        agree_ment.loadUrl("file:////android_asset/secured_transaction_agreement.html");
        view.findViewById(R.id.tv_cancle).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dialog.dismiss();
            }
        });
        view.findViewById(R.id.tv_sure).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                agreeGuarantee(mId, 1);
                dialog.dismiss();
            }
        });
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();

    }

}
