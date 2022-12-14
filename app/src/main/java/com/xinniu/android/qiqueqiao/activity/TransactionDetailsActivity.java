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
    private String toBeconfirmed = "0";//???????????????????????????????????????????????????????????????????????????

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
                    //	1?????????2?????????
                    tvIdentity.setText("??????");
                } else {
                    tvIdentity.setText("??????");
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
                    String text1="?? ";
                    String text=text1 + pricr01[0];
                    final SpannableStringBuilder sb = new SpannableStringBuilder(text);
                    sb.setSpan(new AbsoluteSizeSpan(14, true), text1.length(), text.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    sb.setSpan(new StyleSpan(Typeface.BOLD), text1.length(), text.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    tvPriceD.setText(sb);//????????????
                } else {
                    String text1="?? ";
                    String text=text1+ guaranteeDetailBean.getEstimated_amount();
                    final SpannableStringBuilder sb = new SpannableStringBuilder(text);
                    sb.setSpan(new AbsoluteSizeSpan(14, true), text1.length(), text.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    sb.setSpan(new StyleSpan(Typeface.BOLD), text1.length(), text.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    tvPriceD.setText(sb);//????????????
                }
                tvOrderSn.setText(guaranteeDetailBean.getOrder_sn());
                tvCreateTime.setText(TimeUtils.time2ActTime(guaranteeDetailBean.getCreate_time() * 1000));
                //??????????????????
                int is_initiate = guaranteeDetailBean.getIs_initiate();   //???????????????
                if (is_initiate == 1) {
                    tvPersonStatus.setText("????????????");
                } else {
                    tvPersonStatus.setText("?????????");
                }
                int party_a_user_id = guaranteeDetailBean.getParty_a_user_id();//??????UID ??????
                int mUserId = UserInfoHelper.getIntance().getUserId();//?????????????????????id
                boolean isBuyer = false;//???????????????????????????
                if (party_a_user_id == mUserId) {
                    isBuyer = true;
                }

                if (guaranteeDetailBean.getStatus() == 0) {
                    long time = (guaranteeDetailBean.getCreate_time() + 24 * 60 * 60) - guaranteeDetailBean.getNow_time();
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
                    tv01.setText("????????????");
                    //???????????????????????????????????????
                    if (is_initiate == 1) {
                        //?????????????????????????????????
                        tvStatus.setText("?????????????????????");
                        tvStatusMemo.setText("???????????????????????????");
                        tvTime.setText("????????????" + longHours01 + ":" + longMinutes01 + "??????????????????????????????");
                        tv02.setVisibility(View.INVISIBLE);

                    } else {
                        //???????????????
                        toBeconfirmed = "1";
                        tvStatus.setText("???????????????");
                        tvStatusMemo.setText("??????????????????????????????????????????????????????");
                        tvTime.setText("????????????" + longHours01 + ":" + longMinutes01 + "??????????????????????????????");
                        tv02.setVisibility(View.VISIBLE);
                        tv02.setBackgroundResource(R.drawable.bg_trans_blue);
                        tv02.setTextColor(getResources().getColor(R.color.blue_bg_4B96F3));
                        tv02.setText("????????????");
                    }

                } else if (guaranteeDetailBean.getStatus() == 1) {
                    tvBjStatus.setVisibility(View.GONE);
                    tvTime.setVisibility(View.VISIBLE);
                    view01.setVisibility(View.VISIBLE);
                    rlayout02.setVisibility(View.VISIBLE);
                    view02.setVisibility(View.VISIBLE);
                    rlayout03.setVisibility(View.GONE);
                    rlayout04.setVisibility(View.VISIBLE);
                    tvPriceSs.setText("???????????????????????????");
                    tvPriceSs.setTextColor(getResources().getColor(R.color._777));
                    rlayout05.setVisibility(View.GONE);
                    view5.setVisibility(View.VISIBLE);
                    rlayoutDoneTime.setVisibility(View.VISIBLE);
                    tv01.setVisibility(View.VISIBLE);
                    tv02.setVisibility(View.VISIBLE);
                    tv04.setVisibility(View.INVISIBLE);
                    tv03.setVisibility(View.INVISIBLE);
                    tv05.setVisibility(View.GONE);

                    //1?????????????????????
                    if (guaranteeDetailBean.getRefund_status() == 0||guaranteeDetailBean.getRefund_status()==3) {
                        //??????????????????,???????????????????????????
                        tvStatus.setText("????????????");
                        tvStatusMemo.setText("??????????????????????????????");

                        if (isBuyer) {
                            tvTime.setText("????????????????????????????????????");
                        } else {
                            tvTime.setText("?????????????????????????????????????????????????????????");
                        }

                        tv01.setBackgroundResource(R.drawable.bg_trans_gray);
                        tv01.setTextColor(getResources().getColor(R.color._333));
                        tv01.setText("????????????");
                        tv02.setBackgroundResource(R.drawable.bg_trans_blue);
                        tv02.setTextColor(getResources().getColor(R.color.blue_bg_4B96F3));
                        tv02.setText("????????????");
                        //??????????????????
                        tvDoneTime.setText(TimeUtils.time2ActTime(guaranteeDetailBean.getEnd_time() * 1000));
                        if (guaranteeDetailBean.getSettlement_amount().contains(".")) {
                            String[] pricr01 = guaranteeDetailBean.getSettlement_amount().split("\\.");
                            String text1="-?? ";
                            String text=text1+ pricr01[0];
                            final SpannableStringBuilder sb = new SpannableStringBuilder(text);
                            sb.setSpan(new AbsoluteSizeSpan(14, true), text1.length(), text.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                            sb.setSpan(new StyleSpan(Typeface.BOLD), text1.length(), text.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                            tvPriceY.setText(sb);//????????????
                        } else {

                            String text1="-?? ";
                            String text=text1+guaranteeDetailBean.getSettlement_amount();
                            final SpannableStringBuilder sb = new SpannableStringBuilder(text);
                            sb.setSpan(new AbsoluteSizeSpan(14, true), text1.length(), text.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                            sb.setSpan(new StyleSpan(Typeface.BOLD), text1.length(), text.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                            tvPriceY.setText(sb);//????????????

                        }


                    } else if (guaranteeDetailBean.getRefund_status() == 2) {
                        //??????????????????
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
                        tvStatus.setText("????????????");
                        if (isBuyer) {
                            tvStatusMemo.setText("???????????????");

                        } else {
                            tvStatusMemo.setText("???????????????");
                        }
                        tv01.setBackgroundResource(R.drawable.bg_trans_organe);
                        tv01.setTextColor(getResources().getColor(R.color.orange));
                        tv01.setText("????????????");
                        tv02.setBackgroundResource(R.drawable.bg_trans_blue);
                        tv02.setTextColor(getResources().getColor(R.color.blue_bg_4B96F3));
                        tv02.setText("????????????");
                        //????????????????????????????????????

                        //??????????????????
                        tvDoneTime.setText(TimeUtils.time2ActTime(guaranteeDetailBean.getEnd_time() * 1000));
                        if (guaranteeDetailBean.getSettlement_amount().contains(".")) {
                            String[] pricr01 = guaranteeDetailBean.getSettlement_amount().split("\\.");

                            String text1="-?? ";
                            String text=text1+pricr01[0];
                            final SpannableStringBuilder sb = new SpannableStringBuilder(text);
                            sb.setSpan(new AbsoluteSizeSpan(14, true), text1.length(), text.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                            sb.setSpan(new StyleSpan(Typeface.BOLD), text1.length(), text.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                            tvPriceY.setText(sb);//????????????
                        } else {

                            String text1="-?? ";
                            String text=text1+guaranteeDetailBean.getSettlement_amount();
                            final SpannableStringBuilder sb = new SpannableStringBuilder(text);
                            sb.setSpan(new AbsoluteSizeSpan(14, true), text1.length(), text.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                            sb.setSpan(new StyleSpan(Typeface.BOLD), text1.length(), text.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                            tvPriceY.setText(sb);//????????????
                        }

                        if (guaranteeDetailBean.getRefund_amount().contains(".")) {
                            String[] pricr01 = guaranteeDetailBean.getRefund_amount().split("\\.");
                            String text1="?? ";
                            String text=text1+pricr01[0];
                            final SpannableStringBuilder sb = new SpannableStringBuilder(text);
                            sb.setSpan(new AbsoluteSizeSpan(14, true), text1.length(), text.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                            sb.setSpan(new StyleSpan(Typeface.BOLD), text1.length(), text.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                            tvPriceT.setText(sb);//????????????
                        } else {
                            String text1="?? ";
                            String text=text1+guaranteeDetailBean.getRefund_amount();
                            final SpannableStringBuilder sb = new SpannableStringBuilder(text);
                            sb.setSpan(new AbsoluteSizeSpan(14, true), text1.length(), text.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                            sb.setSpan(new StyleSpan(Typeface.BOLD), text1.length(), text.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                            tvPriceT.setText(sb);//????????????
                        }
                    }


                } else if (guaranteeDetailBean.getStatus() == 2) {
                    //?????????????????????
                    long time = (guaranteeDetailBean.getAgree_time() + 24 * 60 * 60) - guaranteeDetailBean.getNow_time();
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
                    tv01.setText("????????????");
                    tv02.setBackgroundResource(R.drawable.bg_trans_gray);
                    tv02.setTextColor(getResources().getColor(R.color._333));
                    tv02.setText("????????????");

                    if (isBuyer) {
                        //????????????,??????
                        if (is_initiate == 1) {
                            //???????????????
                            tvStatus.setText("?????????????????????");
                        } else {
                            tvStatus.setText("???????????????");
                        }
                        tvStatusMemo.setText("????????????????????????");

                        tvTime.setText("????????????" + longHours01 + ":" + longMinutes01 + "??????????????????????????????");
                        tv03.setVisibility(View.VISIBLE);
                        tv03.setBackgroundResource(R.drawable.bg_trans_blue);
                        tv03.setTextColor(getResources().getColor(R.color.blue_bg_4B96F3));
                        tv03.setText("?????????");

                    } else {
                        //???????????????????????????
                        tvStatus.setText("?????????????????????");
                        tvStatusMemo.setText("????????????????????????????????????");
                        tvTime.setText("????????????" + longHours01 + ":" + longMinutes01 + "??????????????????????????????");
                        tv03.setVisibility(View.INVISIBLE);
                    }


                } else if (guaranteeDetailBean.getStatus() == 3) {
                    //???????????????????????????
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
                        //????????????,??????
                        if (is_initiate == 1) {
                            //???????????????
                            tvStatus.setText("?????????????????????");
                        } else {
                            tvStatus.setText("???????????????");
                        }
                        tvStatusMemo.setText("????????????????????????????????????????????????");
                        tv02.setBackgroundResource(R.drawable.bg_trans_organe);
                        tv02.setTextColor(getResources().getColor(R.color.orange));
                        tv02.setText("????????????");

                        tv01.setBackgroundResource(R.drawable.bg_trans_gray);
                        tv01.setTextColor(getResources().getColor(R.color._333));
                        tv01.setText("????????????");

                    } else {
                        //???????????????????????????
                        tvStatus.setText("?????????????????????");
                        tvStatusMemo.setText("????????????????????????????????????");
                        tv01.setBackgroundResource(R.drawable.bg_trans_gray);
                        tv01.setTextColor(getResources().getColor(R.color._333));
                        tv01.setText("????????????");
                        tv02.setBackgroundResource(R.drawable.bg_trans_gray);
                        tv02.setTextColor(getResources().getColor(R.color._333));
                        tv02.setText("????????????");
                    }


                } else if (guaranteeDetailBean.getStatus() == 4) {
                    //4?????????????????????????????????
                    tvBjStatus.setVisibility(View.GONE);
                    tvTime.setVisibility(View.VISIBLE);
                    view01.setVisibility(View.VISIBLE);
                    rlayout02.setVisibility(View.VISIBLE);
                    view02.setVisibility(View.VISIBLE);
                    rlayout05.setVisibility(View.GONE);
                    view5.setVisibility(View.GONE);
                    rlayoutDoneTime.setVisibility(View.GONE);
                    //???????????????????????????
                    int recharge_status = guaranteeDetailBean.getRecharge_status();//0??????????????????1?????????????????????????????????
                    //??????????????????
                    int billing_status = guaranteeDetailBean.getBilling_status();//	??????????????????0??????????????????1???????????????????????????????????????
                    //?????????????????? 0:?????????1???????????????3???????????????
                    int refund_status = guaranteeDetailBean.getRefund_status();
                    int serving_status = guaranteeDetailBean.getServing_status();//0?????????????????????1???????????????2???????????????
                    if (isBuyer) {
                        rlayout03.setVisibility(View.VISIBLE);
                        rlayout04.setVisibility(View.GONE);
                        tvStatus.setText("?????????????????????");
                        tvStatusMemo.setText("???????????????????????????????????????");
                        tvTime.setText("???????????????????????????????????????");
                        tv01.setVisibility(View.VISIBLE);
                        tv02.setVisibility(View.VISIBLE);
                        tv04.setVisibility(View.VISIBLE);
                        tv03.setVisibility(View.VISIBLE);
                        tv05.setVisibility(View.GONE);
                        tv01.setBackgroundResource(R.drawable.bg_trans_gray);
                        tv01.setTextColor(getResources().getColor(R.color._333));
                        tv01.setText("????????????");

                        tv02.setBackgroundResource(R.drawable.bg_trans_gray);
                        tv02.setTextColor(getResources().getColor(R.color._333));
                        tv02.setText("????????????");

                        tv03.setBackgroundResource(R.drawable.bg_trans_blue);
                        tv03.setTextColor(getResources().getColor(R.color.blue_bg_4B96F3));
                        tv03.setText("????????????");

                        tv04.setBackgroundResource(R.drawable.bg_trans_blue);
                        tv04.setTextColor(getResources().getColor(R.color.blue_bg_4B96F3));
                        tv04.setText("????????????");
                        //????????????
                        if (recharge_status == 1) {
                            tvBjStatus.setVisibility(View.VISIBLE);
                            tvBjStatus.setText("?????????????????????????????????????????????");
                            tvOperation.setText("????????????");
                            tvOperation.setBackgroundResource(R.drawable.bg_trans_organe);
                            tvOperation.setTextColor(getResources().getColor(R.color.orange));
                            //?????????????????????????????????????????????????????????????????????
                            tv01.setVisibility(View.GONE);
                        } else if (recharge_status == 0) {
                            tvOperation.setBackgroundResource(R.drawable.bg_trans_blue);
                            tvOperation.setTextColor(getResources().getColor(R.color.blue_bg_4B96F3));
                            tvOperation.setText("???????????????");
                            tv01.setVisibility(View.VISIBLE);
                        }
                        //??????????????????????????????
                        if (billing_status == 1) {
                            tvStatusMemo.setText("???????????????????????????");
                            tvTime.setText("???????????????????????????????????????");
                        }
                        if (refund_status == 1) {
                            //?????????????????????

                            tv01.setVisibility(View.VISIBLE);
                            tv02.setVisibility(View.VISIBLE);
                            tv04.setVisibility(View.GONE);
                            tv03.setVisibility(View.GONE);
                            tv05.setVisibility(View.GONE);
                            tv01.setBackgroundResource(R.drawable.bg_trans_organe);
                            tv01.setTextColor(getResources().getColor(R.color.orange));
                            tv01.setText("?????????");
                            tv02.setBackgroundResource(R.drawable.bg_trans_gray);
                            tv02.setTextColor(getResources().getColor(R.color._333));
                            tv02.setText("????????????");
                            //??????????????????????????????
                            rlayout03.setVisibility(View.GONE);
                            rlayout04.setVisibility(View.VISIBLE);
                            tvPriceSs.setTextColor(getResources().getColor(R.color._ee634e));
                            if (guaranteeDetailBean.getRemaining_amount().contains(".")) {
                                String[] pricr01 = guaranteeDetailBean.getRemaining_amount().split("\\.");

                                String text1="?? ";
                                String text=text1+pricr01[0];
                                final SpannableStringBuilder sb = new SpannableStringBuilder(text);
                                sb.setSpan(new AbsoluteSizeSpan(14, true), text1.length(), text.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                                sb.setSpan(new StyleSpan(Typeface.BOLD), text1.length(), text.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                                tvPriceSs.setText(sb);//????????????
                            } else {

                                String text1="?? ";
                                String text=text1+guaranteeDetailBean.getRemaining_amount();
                                final SpannableStringBuilder sb = new SpannableStringBuilder(text);
                                sb.setSpan(new AbsoluteSizeSpan(14, true), text1.length(), text.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                                sb.setSpan(new StyleSpan(Typeface.BOLD), text1.length(), text.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                                tvPriceSs.setText(sb);//????????????
                            }

                        } else if (refund_status == 3) {
                            //????????????
                            tv01.setVisibility(View.VISIBLE);
                            tv02.setVisibility(View.VISIBLE);
                            tv04.setVisibility(View.VISIBLE);
                            tv03.setVisibility(View.VISIBLE);
                            tv05.setVisibility(View.GONE);
                            tv01.setBackgroundResource(R.drawable.bg_trans_red);
                            tv01.setTextColor(getResources().getColor(R.color.bg_color_red_DE6654));
                            tv01.setText("????????????");
                            tv02.setBackgroundResource(R.drawable.bg_trans_gray);
                            tv02.setTextColor(getResources().getColor(R.color._333));
                            tv02.setText("????????????");
                            tv03.setBackgroundResource(R.drawable.bg_trans_blue);
                            tv03.setTextColor(getResources().getColor(R.color.blue_bg_4B96F3));
                            tv03.setText("????????????");

                            tv04.setBackgroundResource(R.drawable.bg_trans_blue);
                            tv04.setTextColor(getResources().getColor(R.color.blue_bg_4B96F3));
                            tv04.setText("????????????");

                        }
                        //??????????????????????????????????????????,
                        ////0?????????????????????1???????????????2???????????????
                        if (serving_status == 0) {

                        } else if (serving_status == 1) {
                            if (guaranteeDetailBean.getServing_type() == 2 || guaranteeDetailBean.getServing_type() == 3) {
                                tvStatus.setText("?????????????????????");
                                tvStatusMemo.setText("???????????????????????????");
                                if (guaranteeDetailBean.getServing_type() == 2) {
                                    tvTime.setText("????????????: ????????????");
                                } else if (guaranteeDetailBean.getServing_type() == 3) {
                                    tvTime.setText("????????????: ????????????");
                                }
                                tv01.setVisibility(View.VISIBLE);
                                tv02.setVisibility(View.VISIBLE);
                                tv04.setVisibility(View.VISIBLE);
                                tv03.setVisibility(View.VISIBLE);
                                tv05.setVisibility(View.GONE);
                                tv01.setBackgroundResource(R.drawable.bg_trans_organe);
                                tv01.setTextColor(getResources().getColor(R.color.orange));
                                tv01.setText("????????????");
                                tv02.setBackgroundResource(R.drawable.bg_trans_gray);
                                tv02.setTextColor(getResources().getColor(R.color._333));
                                tv02.setText("????????????");
                                tv03.setBackgroundResource(R.drawable.bg_trans_blue);
                                tv03.setTextColor(getResources().getColor(R.color.blue_bg_4B96F3));
                                tv03.setText("????????????");

                                tv04.setBackgroundResource(R.drawable.bg_trans_blue);
                                tv04.setTextColor(getResources().getColor(R.color.blue_bg_4B96F3));
                                tv04.setText("????????????");
                            }
                        } else if (serving_status == 2) {
                            if (guaranteeDetailBean.getServing_type() == 2 || guaranteeDetailBean.getServing_type() == 3) {
                                tvStatus.setText("?????????????????????");
                                tvStatusMemo.setText("???????????????????????????");
                                tvTime.setText("????????????: " + guaranteeDetailBean.getServing_desc());
                                tv01.setVisibility(View.VISIBLE);
                                tv02.setVisibility(View.VISIBLE);
                                tv04.setVisibility(View.VISIBLE);
                                tv03.setVisibility(View.VISIBLE);
                                tv05.setVisibility(View.GONE);
                                tv01.setBackgroundResource(R.drawable.bg_trans_organe);
                                tv01.setTextColor(getResources().getColor(R.color.orange));
                                tv01.setText("????????????");
                                tv02.setBackgroundResource(R.drawable.bg_trans_gray);
                                tv02.setTextColor(getResources().getColor(R.color._333));
                                tv02.setText("????????????");
                                tv03.setBackgroundResource(R.drawable.bg_trans_blue);
                                tv03.setTextColor(getResources().getColor(R.color.blue_bg_4B96F3));
                                tv03.setText("????????????");

                                tv04.setBackgroundResource(R.drawable.bg_trans_blue);
                                tv04.setTextColor(getResources().getColor(R.color.blue_bg_4B96F3));
                                tv04.setText("????????????");
                            }

                        }


                    } else {
                        //????????????
                        tvStatus.setText("???????????????");
                        tvStatusMemo.setText("????????????????????????");
                        tvTime.setText("?????????????????????????????????????????????????????????????????????");
                        rlayout03.setVisibility(View.GONE);
                        rlayout04.setVisibility(View.VISIBLE);
                        tvPriceSs.setTextColor(getResources().getColor(R.color._ee634e));
                        if (guaranteeDetailBean.getRemaining_amount().contains(".")) {
                            String[] pricr01 = guaranteeDetailBean.getRemaining_amount().split("\\.");

                            String text1="?? ";
                            String text=text1+pricr01[0];
                            final SpannableStringBuilder sb = new SpannableStringBuilder(text);
                            sb.setSpan(new AbsoluteSizeSpan(14, true), text1.length(), text.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                            sb.setSpan(new StyleSpan(Typeface.BOLD), text1.length(), text.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                            tvPriceSs.setText(sb);//????????????
                        } else {
                            String text1="?? ";
                            String text=text1+guaranteeDetailBean.getRemaining_amount();
                            final SpannableStringBuilder sb = new SpannableStringBuilder(text);
                            sb.setSpan(new AbsoluteSizeSpan(14, true), text1.length(), text.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                            sb.setSpan(new StyleSpan(Typeface.BOLD), text1.length(), text.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                            tvPriceSs.setText(sb);//????????????

                        }
                        tv01.setVisibility(View.VISIBLE);
                        tv02.setVisibility(View.GONE);
                        tv04.setVisibility(View.INVISIBLE);
                        tv03.setVisibility(View.VISIBLE);
                        tv05.setVisibility(View.VISIBLE);
                        tv01.setBackgroundResource(R.drawable.bg_trans_gray);
                        tv01.setTextColor(getResources().getColor(R.color._333));
                        tv01.setText("????????????");

                        tv03.setBackgroundResource(R.drawable.bg_trans_blue);
                        tv03.setTextColor(getResources().getColor(R.color.blue_bg_4B96F3));
                        tv03.setText("????????????");

                        if (refund_status == 1 || refund_status == 3) {
                            //?????????????????????
                            tvStatusMemo.setText("??????????????????");

                            long time = (guaranteeDetailBean.getRefund_time() + 72 * 60 * 60) - guaranteeDetailBean.getNow_time();
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
                            if (refund_status == 3) {
                                tvTime.setVisibility(View.GONE);
                            } else {
                                tvTime.setVisibility(View.VISIBLE);
                                tvTime.setText("????????????" + longHours01 + ":" + longMinutes01 + "??????????????????????????????????????????");
                            }
                            tv01.setVisibility(View.VISIBLE);
                            tv02.setVisibility(View.VISIBLE);
                            tv04.setVisibility(View.GONE);
                            tv03.setVisibility(View.GONE);
                            tv05.setVisibility(View.GONE);
                            tv01.setBackgroundResource(R.drawable.bg_trans_gray);
                            tv01.setTextColor(getResources().getColor(R.color._333));
                            tv01.setText("????????????");

                            tv02.setBackgroundResource(R.drawable.bg_trans_organe);
                            tv02.setTextColor(getResources().getColor(R.color.orange));
                            tv02.setText("????????????");


                        }

                        //????????????????????????
                        //??????????????????????????????????????????
                        if (serving_status == 0) {

                        } else if (serving_status == 1) {
                            if (guaranteeDetailBean.getServing_type() == 2 || guaranteeDetailBean.getServing_type() == 3) {
                                tvStatus.setText("???????????????");
                                tvStatusMemo.setText("????????????????????????????????????????????????");
                                if (guaranteeDetailBean.getServing_type() == 2) {
                                    tvTime.setText("??????: ????????????");
                                } else if (guaranteeDetailBean.getServing_type() == 3) {
                                    tvTime.setText("??????: ????????????");
                                }
                                tv01.setVisibility(View.VISIBLE);
                                tv02.setVisibility(View.VISIBLE);
                                tv04.setVisibility(View.GONE);
                                tv03.setVisibility(View.GONE);
                                tv05.setVisibility(View.GONE);
                                tv01.setBackgroundResource(R.drawable.bg_trans_organe);
                                tv01.setTextColor(getResources().getColor(R.color.orange));
                                tv01.setText("????????????");
                                tv02.setBackgroundResource(R.drawable.bg_trans_gray);
                                tv02.setTextColor(getResources().getColor(R.color._333));
                                tv02.setText("????????????");
                            }


                        } else if (serving_status == 2) {
                            if (guaranteeDetailBean.getServing_type() == 2 || guaranteeDetailBean.getServing_type() == 3) {
                                tvStatus.setText("???????????????");
                                tvStatusMemo.setText("??????????????????????????????");
                                tvTime.setText("????????????:" + guaranteeDetailBean.getServing_desc());
                                tv01.setVisibility(View.VISIBLE);
                                tv02.setVisibility(View.GONE);
                                tv04.setVisibility(View.GONE);
                                tv03.setVisibility(View.VISIBLE);
                                tv05.setVisibility(View.VISIBLE);
                                tv03.setBackgroundResource(R.drawable.bg_trans_organe);
                                tv03.setTextColor(getResources().getColor(R.color.orange));
                                tv03.setText("????????????");
                                tv01.setBackgroundResource(R.drawable.bg_trans_gray);
                                tv01.setTextColor(getResources().getColor(R.color._333));
                                tv01.setText("????????????");
                            }


                        }

                    }
                    if (guaranteeDetailBean.getSettlement_amount().contains(".")) {
                        String[] pricr01 = guaranteeDetailBean.getSettlement_amount().split("\\.");
                        String text1="-?? " ;
                        String text=text1+pricr01[0];
                        final SpannableStringBuilder sb = new SpannableStringBuilder(text);
                        sb.setSpan(new AbsoluteSizeSpan(14, true), text1.length(), text.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                        sb.setSpan(new StyleSpan(Typeface.BOLD), text1.length(), text.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                        tvPriceY.setText(sb);//????????????

                    } else {
                        String text1="-?? " ;
                        String text=text1+guaranteeDetailBean.getSettlement_amount();
                        final SpannableStringBuilder sb = new SpannableStringBuilder(text);
                        sb.setSpan(new AbsoluteSizeSpan(14, true), text1.length(), text.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                        sb.setSpan(new StyleSpan(Typeface.BOLD), text1.length(), text.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                        tvPriceY.setText(sb);//????????????

                    }

                    if (guaranteeDetailBean.getRemaining_amount().contains(".")) {
                        String[] pricr01 = guaranteeDetailBean.getRemaining_amount().split("\\.");
                        String text1="?? ";
                        String text=text1+pricr01[0];
                        final SpannableStringBuilder sb = new SpannableStringBuilder(text);
                        sb.setSpan(new AbsoluteSizeSpan(14, true), text1.length(), text.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                        sb.setSpan(new StyleSpan(Typeface.BOLD), text1.length(), text.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                        tvPriceS.setText(sb);//????????????
                    } else {
                        String text1="?? ";
                        String text=text1+ guaranteeDetailBean.getRemaining_amount();
                        final SpannableStringBuilder sb = new SpannableStringBuilder(text);
                        sb.setSpan(new AbsoluteSizeSpan(14, true), text1.length(), text.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                        sb.setSpan(new StyleSpan(Typeface.BOLD), text1.length(), text.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                        tvPriceS.setText(sb);//????????????
                    }


                } else if (guaranteeDetailBean.getStatus() == 5) {
                    //???????????????????????????????????????
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
                    tvStatus.setText("???????????????");
                    if (is_initiate == 1) {
                        //???????????????
                        tvStatusMemo.setText("?????????????????????");
                    } else {
                        tvStatusMemo.setText("??????????????????");
                    }

                    tv01.setBackgroundResource(R.drawable.bg_trans_blue);
                    tv01.setTextColor(getResources().getColor(R.color.blue_bg_4B96F3));
                    tv01.setText("????????????");


                } else if (guaranteeDetailBean.getStatus() == 6) {
                    //?????????????????????????????????????????????????????????   ?????????cancel_user_id????????????
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
                    tvStatus.setText("???????????????");
                    int cancel_user_id = guaranteeDetailBean.getCancel_user_id();
                    if (cancel_user_id == mUserId) {
                        //????????????
                        tvStatusMemo.setText("??????????????????");
                    } else {
                        tvStatusMemo.setText("?????????????????????");
                    }
                    tv01.setBackgroundResource(R.drawable.bg_trans_blue);
                    tv01.setTextColor(getResources().getColor(R.color.blue_bg_4B96F3));
                    tv01.setText("????????????");

                } else if (guaranteeDetailBean.getStatus() == 7) {
                    //??????????????????????????????
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
                    tvStatus.setText("???????????????");
                    tvStatusMemo.setText("???????????????????????????????????????");
                    tv01.setBackgroundResource(R.drawable.bg_trans_blue);
                    tv01.setTextColor(getResources().getColor(R.color.blue_bg_4B96F3));
                    tv01.setText("????????????");
                } else if (guaranteeDetailBean.getStatus() == 8) {
                    //?????????????????????
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
                    tvStatus.setText("???????????????");
                    tvStatusMemo.setText("???????????????????????????????????????");
                    tv01.setBackgroundResource(R.drawable.bg_trans_blue);
                    tv01.setTextColor(getResources().getColor(R.color.blue_bg_4B96F3));
                    tv01.setText("????????????");
                } else if (guaranteeDetailBean.getStatus() == 9) {
                    //???????????????????????????????????????
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
                    tvStatus.setText("????????????");
                    if (isBuyer) {
                        tvStatusMemo.setText("???????????????");

                    } else {
                        tvStatusMemo.setText("???????????????");
                    }
                    tv01.setBackgroundResource(R.drawable.bg_trans_organe);
                    tv01.setTextColor(getResources().getColor(R.color.orange));
                    tv01.setText("????????????");
                    tv02.setBackgroundResource(R.drawable.bg_trans_blue);
                    tv02.setTextColor(getResources().getColor(R.color.blue_bg_4B96F3));
                    tv02.setText("????????????");
                    //????????????????????????????????????
                    //??????????????????
                    tvDoneTime.setText(TimeUtils.time2ActTime(guaranteeDetailBean.getEnd_time() * 1000));
                    if (guaranteeDetailBean.getSettlement_amount().contains(".")) {
                        String[] pricr01 = guaranteeDetailBean.getSettlement_amount().split("\\.");
                        String text1="-?? ";
                        String text=text1+ pricr01[0];
                        final SpannableStringBuilder sb = new SpannableStringBuilder(text);
                        sb.setSpan(new AbsoluteSizeSpan(14, true), text1.length(), text.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                        sb.setSpan(new StyleSpan(Typeface.BOLD), text1.length(), text.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                        tvPriceY.setText(sb);//????????????
                    } else {
                        String text1="-?? ";
                        String text=text1+ guaranteeDetailBean.getSettlement_amount();
                        final SpannableStringBuilder sb = new SpannableStringBuilder(text);
                        sb.setSpan(new AbsoluteSizeSpan(14, true), text1.length(), text.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                        sb.setSpan(new StyleSpan(Typeface.BOLD), text1.length(), text.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                        tvPriceY.setText(sb);//????????????

                    }

                    if (guaranteeDetailBean.getRefund_amount().contains(".")) {
                        String[] pricr01 = guaranteeDetailBean.getRefund_amount().split("\\.");

                        String text1="-?? ";
                        String text=text1+pricr01[0];
                        final SpannableStringBuilder sb = new SpannableStringBuilder(text);
                        sb.setSpan(new AbsoluteSizeSpan(14, true), text1.length(), text.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                        sb.setSpan(new StyleSpan(Typeface.BOLD), text1.length(), text.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                        tvPriceT.setText(sb);//????????????


                    } else {
                        String text1="-?? ";
                        String text=text1+guaranteeDetailBean.getRefund_amount();
                        final SpannableStringBuilder sb = new SpannableStringBuilder(text);
                        sb.setSpan(new AbsoluteSizeSpan(14, true), text1.length(), text.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                        sb.setSpan(new StyleSpan(Typeface.BOLD), text1.length(), text.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                        tvPriceT.setText(sb);//????????????
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
                //????????????
                //IMUtils.singleChatForResource(TransactionDetailsActivity.this, String.valueOf(mBean.getObj_user_info().getUser_id()), mBean.getObj_user_info().getRealname(), 0, mBean.getObj_user_info().getCompany() + mBean.getObj_user_info().getPosition(), mBean.getObj_user_info().getHead_pic(), "0");
                break;
            case R.id.ycompany_intromoreRl:
                mcompanyIntroducetv.setMaxLines(Integer.MAX_VALUE);
                ShowUtils.showViewVisible(bcompanyIntromoreImg, View.GONE);
                ShowUtils.showViewVisible(ycompanyIntromoreRl, View.GONE);
                break;
            case R.id.tv_call_customer_service:
                //????????????,??????
                //IMUtils.singleChatForResource(TransactionDetailsActivity.this, "50276", "", 0, "", "", "0");
                break;
            case R.id.tv_01:
                //????????????//????????????//????????????//????????????//????????????//????????????????????????
                if (tv01.getText().toString().equals("????????????")) {
                    if (toBeconfirmed.equals("1")) {
                        agreeGuarantee(mId, 0);
                    } else {
                        //????????????
                        cancelGuarantee(mId);
                    }
                } else if (tv01.getText().toString().equals("????????????")) {
                    goTocall(mBean.getObj_user_info().getMobile());

                } else if (tv01.getText().toString().equals("????????????")) {
                    LaunchTransactionAvtivity.start(TransactionDetailsActivity.this, mBean.getObj_user_info().getUser_id() + "", 0);
                    finish();
                } else if (tv01.getText().toString().equals("????????????")) {
                    Gson gson = new Gson();
                    String json = gson.toJson(mBean);
                    ApplicationRefundAvtivity.startSimpleEidtForResult(TransactionDetailsActivity.this, Integer.parseInt(mId), json, 32);
                } else if (tv01.getText().toString().equals("?????????")) {
                    RefundDetailActivity.startSimpleEidtForResult(TransactionDetailsActivity.this, mBean.getOrder_sn(), Integer.parseInt(mId), 32);

                } else if (tv01.getText().toString().equals("????????????")) {
                    RefundDetailActivity.startSimpleEidtForResult(TransactionDetailsActivity.this, mBean.getOrder_sn(), Integer.parseInt(mId), 32);

                } else if (tv01.getText().toString().equals("????????????")) {
                    RefundDetailActivity.startSimpleEidtForResult(TransactionDetailsActivity.this, mBean.getOrder_sn(), Integer.parseInt(mId), 32);

                } else if (tv01.getText().toString().equals("????????????")) {
                    CustomeServiceDetailActivity.start(TransactionDetailsActivity.this, mBean.getOrder_sn(), Integer.parseInt(mId));

                }

                break;
            case R.id.tv_02:
                //????????????//????????????//????????????//????????????//????????????
                if (tv02.getText().toString().equals("????????????")) {
                    if (toBeconfirmed.equals("1")) {
                        showDialog();

                    }
                } else if (tv02.getText().toString().equals("????????????")) {
                    //???????????????????????????
                    goTocall(mBean.getObj_user_info().getMobile());
                } else if (tv02.getText().toString().equals("????????????")) {
                    LaunchTransactionAvtivity.start(TransactionDetailsActivity.this, mBean.getObj_user_info().getUser_id() + "",0);
                    finish();
                } else if (tv02.getText().toString().equals("????????????")) {
                    PayDetailActivity.start(TransactionDetailsActivity.this, mBean.getOrder_sn(), 1);

                } else if (tv02.getText().toString().equals("????????????")) {
                    RefundDetailActivity.startSimpleEidtForResult(TransactionDetailsActivity.this, mBean.getOrder_sn(), Integer.parseInt(mId), 32);

                }


                break;
            case R.id.tv_03:
                //?????????
                if (tv03.getText().toString().equals("?????????")) {
                    PayDespositActivity.startSimpleEidtForResult(TransactionDetailsActivity.this, Integer.parseInt(mId), tvPriceD.getText().toString().replace("??", ""), 32);
                } else if (tv03.getText().toString().equals("????????????")) {
                    //????????????????????????????????????
                    SettlementActivity.startSimpleEidtForResult(TransactionDetailsActivity.this, Integer.parseInt(mId), mBean.getRemaining_amount(), 33);
                } else if (tv03.getText().toString().equals("????????????")) {
                    //??????????????????
                    doSettlement();

                }
                break;
            case R.id.tv_04:
                if (tv04.getText().toString().equals("????????????")) {
                    //?????????????????????????????????????????????
                    new QLTipTwoDialog.Builder(TransactionDetailsActivity.this)
                            .setCancelable(true)
                            .setCancelableOnTouchOutside(true)
                            .setTitle("?????????????????????????????????")
                            .setMessage("????????????????????????????????????")
                            .setPositiveButton("????????????", new QLTipTwoDialog.IPositiveCallback() {
                                @Override
                                public void onClick() {
                                    doSettlementTwo();

                                }
                            })
                            .setNegativeButton("??????", new QLTipTwoDialog.INegativeCallback() {
                                @Override
                                public void onClick() {
                                    dissMissDialog();

                                }
                            })
                            .show(TransactionDetailsActivity.this);


                }
                break;
            case R.id.tv_05:
                //??????????????????
                ApplicatuinCustomeServiceActivity.startSimpleEidtForResult(TransactionDetailsActivity.this, "2", Integer.parseInt(mId), 32);
                break;
            case R.id.tv_operation:
                //???????????????//????????????
                if (tvOperation.getText().toString().equals("????????????")) {
                    PayDetailActivity.start(TransactionDetailsActivity.this, mBean.getOrder_sn(), 4);

                } else if (tvOperation.getText().toString().equals("???????????????")) {

                    PayDespositAfterActivity.startSimpleEidtForResult(TransactionDetailsActivity.this, Integer.parseInt(mId), 32);
                }
                break;
        }
    }


    /**
     * ??????????????????
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
     * ??????????????????
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
     * ????????????
     *
     * @param mId ??????id
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
     * ???????????????????????????
     *
     * @param mId      ??????id
     * @param is_agree 1?????????0?????????
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
                ToastUtils.showCentetToast(TransactionDetailsActivity.this, "?????????????????????");
                return;
            }
            startActivity(intent1);
        } else {
            EasyPermissions.requestPermissions(TransactionDetailsActivity.this,
                    "????????????????????????????????????",
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
                        .setTitle("????????????")
                        .setMessage("?????????????????????????????????")
                        .setNegativeButton("????????????", new QLTipDialog.INegativeCallback() {
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
        //??????WebSettings??????
        WebSettings webSettings = agree_ment.getSettings();
        //????????????????????????????????????
        webSettings.setUseWideViewPort(true); //????????????????????????webview?????????
        webSettings.setLoadWithOverviewMode(true); // ????????????????????????
        //????????????
        webSettings.setSupportZoom(true); //????????????????????????true??????????????????????????????
        webSettings.setBuiltInZoomControls(true); //????????????????????????????????????false?????????WebView????????????
        webSettings.setDisplayZoomControls(false); //???????????????????????????
        //??????????????????
        webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK); //??????webview?????????
        webSettings.setAllowFileAccess(true); //????????????????????????
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true); //????????????JS???????????????
        webSettings.setLoadsImagesAutomatically(true); //????????????????????????
        webSettings.setDefaultTextEncodingName("utf-8");//??????????????????

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
