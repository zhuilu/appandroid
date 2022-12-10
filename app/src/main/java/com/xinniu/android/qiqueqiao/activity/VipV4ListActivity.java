package com.xinniu.android.qiqueqiao.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.StyleSpan;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.alipay.sdk.app.PayTask;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.xinniu.android.qiqueqiao.R;
import com.xinniu.android.qiqueqiao.base.BaseActivity;
import com.xinniu.android.qiqueqiao.bean.CenterBean;
import com.xinniu.android.qiqueqiao.bean.OrderInfoBean;
import com.xinniu.android.qiqueqiao.bean.TalkPackageBuyBean;
import com.xinniu.android.qiqueqiao.bean.VipV3Bean;
import com.xinniu.android.qiqueqiao.bean.WeChatPayInfo;
import com.xinniu.android.qiqueqiao.pay.PayResult;
import com.xinniu.android.qiqueqiao.request.RequestManager;
import com.xinniu.android.qiqueqiao.request.RetrofitHelper;
import com.xinniu.android.qiqueqiao.request.callback.GetVipV3ListCallback;
import com.xinniu.android.qiqueqiao.request.callback.PayCallback;
import com.xinniu.android.qiqueqiao.request.callback.RequestCallback;
import com.xinniu.android.qiqueqiao.request.callback.TalkPackageCallback;
import com.xinniu.android.qiqueqiao.request.callback.VipBugCallback;
import com.xinniu.android.qiqueqiao.request.callback.WechatPayCallback;
import com.xinniu.android.qiqueqiao.utils.Logger;
import com.xinniu.android.qiqueqiao.utils.ShowUtils;
import com.xinniu.android.qiqueqiao.utils.ToastUtils;
import com.xinniu.android.qiqueqiao.widget.CircleImageView;

import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import retrofit2.Call;

/**
 * Created by yuchance on 2018/4/18.
 * 开通会员页面
 */

public class VipV4ListActivity extends BaseActivity {
    @BindView(R.id.bt_close)
    RelativeLayout btClose;
    @BindView(R.id.bmake_convertcode)
    TextView bmakeConvertcode;//使用兑换码
    @BindView(R.id.tool_bar)
    RelativeLayout toolBar;
    @BindView(R.id.blx_servicemanager)
    TextView blxServicemanager;//咨询服务经理
    @BindView(R.id.b_bugvip)
    TextView bBugvip;//立即购买
    @BindView(R.id.vip_bottomRl)
    LinearLayout vipBottomRl;
    @BindView(R.id.mvip_head_cImg)
    CircleImageView mvipHeadCImg;
    @BindView(R.id.mvip_head_isv)
    ImageView mvipHeadIsv;
    @BindView(R.id.vip_view_headfl)
    FrameLayout vipViewHeadfl;
    @BindView(R.id.untell_text)
    TextView untellText;
    @BindView(R.id.thetimes)
    TextView thetimes;
    @BindView(R.id.muntelltimes)
    TextView muntelltimes;
    @BindView(R.id.un_timesRl)
    RelativeLayout unTimesRl;
    @BindView(R.id.mvip_name)
    TextView mvipName;
    @BindView(R.id.mis_vip_img)
    ImageView misVipImg;
    @BindView(R.id.mtvVipTime)
    TextView mtvVipTime;
    @BindView(R.id.mvip_totime_tv)
    TextView mvipTotimeTv;
    @BindView(R.id.relativeLayout)
    RelativeLayout relativeLayout;
    @BindView(R.id.vip_info_Rl)
    RelativeLayout vipInfoRl;
    @BindView(R.id.tv_open)
    TextView tvOpen;
    @BindView(R.id.img_logo_1)
    ImageView imgLogo1;
    @BindView(R.id.tv_number_1)
    TextView tvNumber1;
    @BindView(R.id.tv_origin_price)
    TextView tvOriginPrice;
    @BindView(R.id.tv_price_1)
    TextView tvPrice1;
    @BindView(R.id.rlayout_vip_new)
    RelativeLayout rlayoutVipNew;
    @BindView(R.id.tv_time)
    TextView tvTime;
    @BindView(R.id.img_logo_2)
    ImageView imgLogo2;
    @BindView(R.id.tv_number_2)
    TextView tvNumber2;
    @BindView(R.id.tv_price_2)
    TextView tvPrice2;
    @BindView(R.id.rlayout_vip)
    RelativeLayout rlayoutVip;
    @BindView(R.id.img_logo_3)
    ImageView imgLogo3;
    @BindView(R.id.tv_number_3)
    TextView tvNumber3;
    @BindView(R.id.tv)
    TextView tv;
    @BindView(R.id.tv_price_3)
    TextView tvPrice3;
    @BindView(R.id.rlayout_svip)
    RelativeLayout rlayoutSvip;
    @BindView(R.id.llayout_root)
    LinearLayout llayoutRoot;
    @BindView(R.id.bcompanyvip)
    RelativeLayout bcompanyvip;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.imgblacks)
    ImageView imgblacks;
    @BindView(R.id.tvblacks)
    TextView tvblacks;
    @BindView(R.id.yblacksRl)
    RelativeLayout yblacksRl;
    @BindView(R.id.tvMsg)
    TextView tvMsg;
    @BindView(R.id.llayout_root_2)
    LinearLayout llayoutRoot2;
    @BindView(R.id.tv_group)
    TextView tvGroup;
    @BindView(R.id.tv_top)
    TextView tvTop;
    @BindView(R.id.vip_content_card)
    RelativeLayout vipContentCard;
    @BindView(R.id.vip_scroll)
    ScrollView vipScroll;
    @BindView(R.id.rlayout_vip_new_root)
    RelativeLayout rlayoutVipNewRoot;
    @BindView(R.id.tv_type)
    TextView tvType;
    @BindView(R.id.company_vip_img)
    ImageView companyVipImg;
    @BindView(R.id.img_logo_4)
    ImageView imgLogo4;
    @BindView(R.id.tv_number_4)
    TextView tvNumber4;
    @BindView(R.id.tv_price_4)
    TextView tvPrice4;
    @BindView(R.id.rlayout_experience_vip)
    RelativeLayout rlayoutExperienceVip;
    @BindView(R.id.rlayout_empty)
    RelativeLayout rlayoutEmpty;
    @BindView(R.id.tv_price_5)
    TextView tvPrice5;
    @BindView(R.id.tv_number_5)
    TextView tvNumber5;
    @BindView(R.id.rlayout_company_vip)
    RelativeLayout rlayoutCompanyVip;
    @BindView(R.id.tv_price_55)
    TextView tvPrice55;
    @BindView(R.id.tv_number_55)
    TextView tvNumber55;
    @BindView(R.id.ll)
    LinearLayout ll;
    @BindView(R.id.rlayout_memo)
    RelativeLayout rlayoutMemo;
    private WeChatPayBroadcastReceiver broadcastReceiver;
    private int mVipNum;
    private int mSvipNum;
    private int mNewVipNum;
    private int mExVipNum;
    private int curPosition;
    private VipV3Bean vipV3Bean;
    private String orderSn;
    private static final int SDK_PAY_FLAG = 1;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SDK_PAY_FLAG: {
                    Log.i("lzq", "支付成功");
//                    HashMap<String, Object> mapData = (HashMap<String, Object>) msg.obj;
                    PayResult payResult = new PayResult((Map<String, String>) msg.obj);
                    String resultStatus = payResult.getResultStatus();
                    Log.i("Alipay", "payResult : " + payResult.toString());
                    Log.i("Alipay", "resultStatus : " + resultStatus);
                    // 判断resultStatus 为“9000”则代表支付成功，具体状态码代表含义可参考接口文档
                    if (TextUtils.equals(resultStatus, "9000")) {
                        ToastUtils.showCentetImgToast(VipV4ListActivity.this, "支付成功");
//                        finish();
                        if (needPhotopop.isShowing()) {
                            dispopwindow();
                            buildData();
                        }
                    } else if (TextUtils.equals(resultStatus, "6001")) {
                        ToastUtils.showCentetImgToast(VipV4ListActivity.this, "支付取消");
                    } else if (TextUtils.equals(resultStatus, "8000")) {
                        ToastUtils.showCentetImgToast(VipV4ListActivity.this, "支付结果确认中");
                    } else {
                        ToastUtils.showCentetImgToast(VipV4ListActivity.this, "支付失败");
                    }

                    break;
                }
                default:
                    break;
            }
        }
    };
    private String svip = "vip";

    @Override
    public int getLayoutId() {
        return R.layout.activity_vip_v4list;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        topStatusBar(true);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            svip = bundle.getString("svip");
        }
        IntentFilter filter = new IntentFilter();
        filter.addAction(this.getPackageName() + ".WeChatPay");
        broadcastReceiver = new WeChatPayBroadcastReceiver();
        registerReceiver(broadcastReceiver, filter);
        //"可创建<font color='#4B96F3'>5</font>个<font color='#4B96F3'>200</font>人群组";
        String str = "提现服务费由1%降低至<font color='#4B96F3'>0.5%</font>";
        tvGroup.setText(Html.fromHtml(str));
        String str1 = "每日享受<font color='#4B96F3'>3</font>次一键刷新特权";
        tvTop.setText(Html.fromHtml(str1));
        yblacksRl.setVisibility(View.GONE);//svip特权
        tvTitle.setText("VIP特权");
        tvType.setText("专属VIP标识");
        buildData();

    }

    /**
     * @param view
     */
    @OnClick({R.id.bt_close, R.id.blx_servicemanager, R.id.b_bugvip, R.id.rlayout_vip_new, R.id.rlayout_vip, R.id.rlayout_svip, R.id.rlayout_experience_vip, R.id.rlayout_company_vip, R.id.bmake_convertcode, R.id.yblacksRl, R.id.bcompanyvip})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bt_close:
                finish();
                break;
            case R.id.blx_servicemanager:
                RequestManager.getInstance().center(new RequestCallback<CenterBean>() {
                    @Override
                    public void requestStart(Call call) {

                    }

                    @Override
                    public void onSuccess(CenterBean centerBean) {
                        CenterBean mCenterBean = centerBean;
                        //IMUtils.singleChat(VipV4ListActivity.this, String.valueOf(mCenterBean.getUsers().getF_id()), "客服", "5", "服务经理你好，我想要咨询办理企鹊桥会员");
                    }

                    @Override
                    public void onFailed(int code, String msg) {
                        ToastUtils.showCentetImgToast(mContext, msg);
                    }

                    @Override
                    public void requestEnd() {

                    }
                });
                break;
            case R.id.b_bugvip:
                if (curPosition == 0) {
                    buildList(vipV3Bean.getInterim().get(0).getAndroid_price(), vipV3Bean.getInterim().get(0).getName_info(), vipV3Bean.getInterim().get(0).getId(), false);
                } else if (curPosition == 1) {
                    buildList(vipV3Bean.getVipList().get(0).getAndroid_price(), vipV3Bean.getVipList().get(0).getName_info(), vipV3Bean.getVipList().get(0).getId(), false);
                } else if (curPosition == 2) {
                    buildList(vipV3Bean.getSvipList().get(0).getAndroid_price(), vipV3Bean.getSvipList().get(0).getName_info(), vipV3Bean.getSvipList().get(0).getId(), false);
                } else if (curPosition == 3) {
                    buildList(vipV3Bean.getExperience().get(0).getAndroid_price(), vipV3Bean.getExperience().get(0).getName_info(), vipV3Bean.getExperience().get(0).getId(), false);
                }
                break;
            case R.id.rlayout_vip_new:
                //新vip
                rlayoutMemo.setVisibility(View.GONE);
                curPosition = 0;
                rlayoutVipNew.setBackgroundResource(R.drawable.bg_vip_select);
                rlayoutVip.setBackgroundResource(R.drawable.bg_vip);
                rlayoutSvip.setBackgroundResource(R.drawable.bg_vip);
                rlayoutExperienceVip.setBackgroundResource(R.drawable.bg_vip);
                yblacksRl.setVisibility(View.GONE);
                tvTitle.setText(R.string.equity_text);
                String str1 = "增加额外<font color='#4B96F3'>" + mNewVipNum + "</font>次沟通权限";
                tvMsg.setText(Html.fromHtml(str1));

                // String str = "可创建<font color='#4B96F3'>5</font>个<font color='#4B96F3'>200</font>人群组";
                String str = "提现服务费由1%降低至<font color='#4B96F3'>0.5%</font>";
                tvGroup.setText(Html.fromHtml(str));
                tvType.setText("专属VIP标识");
                blxServicemanager.setVisibility(View.GONE);
                break;
            case R.id.rlayout_vip:
                //vip
                rlayoutMemo.setVisibility(View.GONE);
                curPosition = 1;
                rlayoutVipNew.setBackgroundResource(R.drawable.bg_vip);
                rlayoutVip.setBackgroundResource(R.drawable.bg_vip_select);
                rlayoutSvip.setBackgroundResource(R.drawable.bg_vip);
                rlayoutExperienceVip.setBackgroundResource(R.drawable.bg_vip);
                tvTitle.setText(R.string.equity_text);
                yblacksRl.setVisibility(View.GONE);
                String str2 = "增加额外<font color='#4B96F3'>" + mVipNum + "</font>次沟通权限";
                tvMsg.setText(Html.fromHtml(str2));
//                String str3 = "可创建<font color='#4B96F3'>5</font>个<font color='#4B96F3'>200</font>人群组";
                String str3 = "提现服务费由1%降低至<font color='#4B96F3'>0.5%</font>";
                tvGroup.setText(Html.fromHtml(str3));
                tvType.setText("专属VIP标识");
                blxServicemanager.setVisibility(View.GONE);
                break;
            case R.id.rlayout_svip:
                //svip
                rlayoutMemo.setVisibility(View.GONE);
                curPosition = 2;
                rlayoutVipNew.setBackgroundResource(R.drawable.bg_vip);
                rlayoutVip.setBackgroundResource(R.drawable.bg_vip);
                rlayoutExperienceVip.setBackgroundResource(R.drawable.bg_vip);
                rlayoutSvip.setBackgroundResource(R.drawable.bg_vip_select);
                yblacksRl.setVisibility(View.VISIBLE);

                tvTitle.setText(R.string.svip_text_x);
                String str4 = "增加额外<font color='#4B96F3'>" + mSvipNum + "</font>次沟通权限";
                tvMsg.setText(Html.fromHtml(str4));

                // String str5 = "可创建<font color='#4B96F3'>5</font>个<font color='#4B96F3'>500</font>人群组";
                String str5 = "提现服务费由1%降低至<font color='#4B96F3'>0.5%</font>";
                tvGroup.setText(Html.fromHtml(str5));
                tvType.setText("专属SVIP标识");
                blxServicemanager.setVisibility(View.VISIBLE);
                break;
            case R.id.rlayout_experience_vip:
                //体验vip
                rlayoutMemo.setVisibility(View.VISIBLE);
                curPosition = 3;
                rlayoutVipNew.setBackgroundResource(R.drawable.bg_vip);
                rlayoutVip.setBackgroundResource(R.drawable.bg_vip);
                rlayoutExperienceVip.setBackgroundResource(R.drawable.bg_vip_select);
                rlayoutSvip.setBackgroundResource(R.drawable.bg_vip);
                yblacksRl.setVisibility(View.GONE);
                tvTitle.setText(R.string.equity_text);
                String str6 = "增加额外<font color='#4B96F3'>" + mExVipNum + "</font>次沟通权限";
                tvMsg.setText(Html.fromHtml(str6));

//                String str7 = "可创建<font color='#4B96F3'>5</font>个<font color='#4B96F3'>200</font>人群组";
                String str7 = "提现服务费由1%降低至<font color='#4B96F3'>0.5%</font>";
                tvGroup.setText(Html.fromHtml(str7));
                tvType.setText("专属VIP标识");
                blxServicemanager.setVisibility(View.GONE);
                break;
            case R.id.rlayout_company_vip:
                ApproveCardActivity.start(VipV4ListActivity.this, "vip", RetrofitHelper.API_URL + "/resource/pages/businessPlan/businessPlan.html", "企业服务");
                break;

            case R.id.bmake_convertcode:
                ActMemberActivity.start(VipV4ListActivity.this);
                break;
            case R.id.yblacksRl:
                HuntServiceActivity.start(VipV4ListActivity.this);
                break;
            case R.id.bcompanyvip:
                ApproveCardActivity.start(VipV4ListActivity.this, "vip", RetrofitHelper.API_URL + "/resource/pages/businessPlan/businessPlan.html", "企业服务");
                break;
        }
    }

    private void buildList(String plusPrice, String plusInfo, final int xMealId, final boolean isPlus) {

        setPopWindow(R.layout.view_popup_selectway, R.style.anim_bottom);
        TextView titleTv = (TextView) popview.findViewById(R.id.mbuytitletv);

        titleTv.setText(plusInfo);
        TextView priceTv = (TextView) popview.findViewById(R.id.mbuy_moneytv);
        TextView pricemTv = (TextView) popview.findViewById(R.id.mpay_price);
        priceTv.setText("¥ " + plusPrice);
        pricemTv.setText("" + plusPrice);
        ImageView finishimg = (ImageView) popview.findViewById(R.id.popdissImg);
        finishimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                needPhotopop.dismiss();
            }
        });
        RelativeLayout wechatRl = (RelativeLayout) popview.findViewById(R.id.bwechat_pay);
        RelativeLayout zfbaoRl = (RelativeLayout) popview.findViewById(R.id.bzfbao_pay);
        final CheckBox wechatCb = (CheckBox) popview.findViewById(R.id.mwechat_paycb);
        final CheckBox zfbaoCb = (CheckBox) popview.findViewById(R.id.mzfbao_paycb);
        RelativeLayout commitRl = (RelativeLayout) popview.findViewById(R.id.bpay_commit);
        wechatRl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!wechatCb.isChecked()) {
                    wechatCb.setChecked(true);
                    zfbaoCb.setChecked(false);
                }
            }
        });

        zfbaoRl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!zfbaoCb.isChecked()) {
                    zfbaoCb.setChecked(true);
                    wechatCb.setChecked(false);
                }
            }
        });
        commitRl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showBookingToast(2);
                if (isPlus) {
                    RequestManager.getInstance().talkPackageBuy(new TalkPackageCallback() {
                        @Override
                        public void onSuccess(TalkPackageBuyBean bean) {
                            if (!TextUtils.isEmpty(bean.getOrder_sn())) {
                                orderSn = bean.getOrder_sn();
                                Log.i("lzq", "创建订单成功：" + bean.getOrder_sn());
                                if (wechatCb.isChecked()) {
                                    weChatPay(orderSn);
                                }
                                if (zfbaoCb.isChecked()) {
                                    aliPay(orderSn);
                                }
                            }
                        }

                        @Override
                        public void onFailue(int code, String msg) {
                            dismissBookingToast();
                            ToastUtils.showCentetImgToast(VipV4ListActivity.this, msg);
                        }
                    });


                } else {
                    RequestManager.getInstance().bugVipV3(xMealId, new VipBugCallback() {
                        @Override
                        public void onSuccess(OrderInfoBean bean) {
                            if (!TextUtils.isEmpty(bean.getOrder_sn())) {
                                orderSn = bean.getOrder_sn();
                                Log.i("lzq", "创建订单成功：" + bean.order_sn);
                                if (wechatCb.isChecked()) {
                                    weChatPay(orderSn);
                                }
                                if (zfbaoCb.isChecked()) {
                                    aliPay(orderSn);
                                }
                            }

                        }

                        @Override
                        public void onFailed(int code, String msg) {
                            dismissBookingToast();
                            Log.i("lzq", "创建订单失败：" + msg);
                            ToastUtils.showCentetImgToast(VipV4ListActivity.this, msg);
                        }
                    });

                }
            }
        });


    }


    class WeChatPayBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            int result = intent.getIntExtra("result", -1);
            if (result == 0) {
//                finish();
                if (needPhotopop.isShowing()) {
                    dispopwindow();
                    buildData();
                }

            }

        }
    }

    @Override
    protected void onDestroy() {
        unregisterReceiver(broadcastReceiver);
        super.onDestroy();
    }

    private void buildData() {
        showBookingToast(1);
        RequestManager.getInstance().getVipV3x(new GetVipV3ListCallback() {
            @Override
            public void onSuccess(VipV3Bean bean) {
                vipV3Bean = bean;

                if (bean.getUsers().getIs_corporate_vip() == 1) {
                    ShowUtils.showViewVisible(companyVipImg, View.VISIBLE);

                } else {
                    ShowUtils.showViewVisible(companyVipImg, View.GONE);
                }
                if (bean.getUsers().getIs_v() == 1) {
                    ShowUtils.showViewVisible(mvipHeadIsv, View.VISIBLE);
                } else {
                    ShowUtils.showViewVisible(mvipHeadIsv, View.GONE);
                }

                if (bean.getExperience() != null && bean.getExperience().size() > 0) {
                    rlayoutExperienceVip.setVisibility(View.VISIBLE);
                    bcompanyvip.setVisibility(View.VISIBLE);
                    rlayoutMemo.setVisibility(View.VISIBLE);
                    rlayoutCompanyVip.setVisibility(View.GONE);
                    rlayoutEmpty.setVisibility(View.GONE);
                    curPosition = 3;

                } else {
                    rlayoutExperienceVip.setVisibility(View.GONE);
                    rlayoutEmpty.setVisibility(View.INVISIBLE);
                    rlayoutMemo.setVisibility(View.GONE);
                    rlayoutCompanyVip.setVisibility(View.VISIBLE);
                    bcompanyvip.setVisibility(View.GONE);
                    curPosition = 1;

                }
                if (bean.getInterim() != null && bean.getInterim().size() > 0) {
                    //新手vip可以购买
                    if (curPosition != 3) {
                        curPosition = 0;
                    }
                    rlayoutVipNewRoot.setVisibility(View.VISIBLE);
                    rlayoutVip.setVisibility(View.GONE);
                    if (!TextUtils.isEmpty(bean.getUsers().getTime_down())) {
                        tvTime.setText("倒计时: " + bean.getUsers().getTime_down());
                    }

                    if (!TextUtils.isEmpty(bean.getInterim().get(0).getOriginal_price_desc())) {
                        tvOriginPrice.setText("原价: " + bean.getInterim().get(0).getOriginal_price_desc());
                    }


                } else {
                    if (curPosition != 3) {
                        curPosition = 1;
                        rlayoutEmpty.setVisibility(View.INVISIBLE);
                    } else {
                        rlayoutEmpty.setVisibility(View.GONE);
                    }
                    rlayoutVipNewRoot.setVisibility(View.GONE);
                    rlayoutVip.setVisibility(View.VISIBLE);

                }
                // 获得转换后的px值

                if (bean.getBusiness_package() != null && !bean.getBusiness_package().equals("null")) {
                    rlayoutEmpty.setVisibility(View.GONE);
                    //企业套餐

                     tvNumber5.setText(bean.getBusiness_package().getDesc());
                    String text = bean.getBusiness_package().getPrice_desc();
                    final SpannableStringBuilder sb = new SpannableStringBuilder(text);
                    sb.setSpan(new AbsoluteSizeSpan(20, true), 1, text.length() - 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    sb.setSpan(new StyleSpan(Typeface.BOLD), 1, text.length() - 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    tvPrice5.setText(sb);
                 //   tvNumber55.setText(bean.getBusiness_package().getDesc());

                    final SpannableStringBuilder sb1 = new SpannableStringBuilder(text);
                    sb1.setSpan(new AbsoluteSizeSpan(12, true), 0, 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    sb1.setSpan(new AbsoluteSizeSpan(18, true), 1, text.length() - 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    sb1.setSpan(new StyleSpan(Typeface.BOLD), 1, text.length() - 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    tvPrice55.setText(sb1);
                } else {
                    rlayoutEmpty.setVisibility(View.INVISIBLE);
                    rlayoutCompanyVip.setVisibility(View.GONE);
                }


                if (bean.getUsers().getIs_vip() == 0) {
                    ShowUtils.showTextPerfect(mvipName, bean.getUsers().getRealname());
                    ShowUtils.showViewVisible(misVipImg, View.GONE);
                    ShowUtils.showTextColor(mvipName, ContextCompat.getColor(VipV4ListActivity.this, R.color.text_color_1));
                    ShowUtils.showViewVisible(mtvVipTime, View.VISIBLE);
                    ShowUtils.showTextPerfect(mtvVipTime, "您还不是会员");
                    ShowUtils.showViewVisible(mvipTotimeTv, View.GONE);
                    ShowUtils.showTextPerfect(muntelltimes, bean.getUsers().getTalk_num() + "");
                    ShowUtils.showImgPerfect(mvipHeadCImg, bean.getUsers().getHead_pic(), 1);
                    bBugvip.setText("立即购买");
                } else if (bean.getUsers().getIs_vip() == 1) {
                    ShowUtils.showTextPerfect(mvipName, bean.getUsers().getRealname());
                    ShowUtils.showImageResource(misVipImg, R.mipmap.vip_iconx);
                    ShowUtils.showTextColor(mvipName, ContextCompat.getColor(VipV4ListActivity.this, R.color.king_color));
                    ShowUtils.showTextPerfect(mvipTotimeTv, bean.getUsers().getText());
                    ShowUtils.showTextPerfect(muntelltimes, bean.getUsers().getTalk_num() + "");
                    ShowUtils.showImgPerfect(mvipHeadCImg, bean.getUsers().getHead_pic(), 1);
                    bBugvip.setText("立即续费");
                } else if (bean.getUsers().getIs_vip() == 2) {
                    ShowUtils.showTextPerfect(mvipName, bean.getUsers().getRealname());
                    ShowUtils.showImageResource(misVipImg, R.mipmap.svip_iconx);
                    ShowUtils.showTextColor(mvipName, ContextCompat.getColor(VipV4ListActivity.this, R.color.king_color));
                    ShowUtils.showTextPerfect(mvipTotimeTv, bean.getUsers().getText());
                    ShowUtils.showTextPerfect(muntelltimes, bean.getUsers().getTalk_num() + "");
                    ShowUtils.showImgPerfect(mvipHeadCImg, bean.getUsers().getHead_pic(), 1);
                    bBugvip.setText("立即续费");
                } else {
                    ShowUtils.showViewVisible(misVipImg, View.GONE);
                    bBugvip.setText("立即购买");
                }
                if (bean != null && bean.getSvipList() != null && bean.getSvipList().size() > 0) {
                    mSvipNum = bean.getSvipList().get(0).getNum();
                    tvNumber3.setText(mSvipNum + "次沟通权限");
                    //  tvPrice3.setText(bean.getSvipList().get(0).getPrice_desc());

                    String text = bean.getSvipList().get(0).getPrice_desc();
                    final SpannableStringBuilder sb = new SpannableStringBuilder(text);
                    sb.setSpan(new AbsoluteSizeSpan(20, true), 1, text.length() - 2, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    sb.setSpan(new StyleSpan(Typeface.BOLD), 1, text.length() - 2, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    tvPrice3.setText(sb);


                }
                if (bean != null && bean.getInterim() != null && bean.getInterim().size() > 0) {
                    mNewVipNum = bean.getInterim().get(0).getNum();

                    tvNumber1.setText(mNewVipNum + "次沟通权限");
                    tvPrice1.setText(bean.getInterim().get(0).getPrice_desc());

                }

                if (bean != null && bean.getExperience() != null && bean.getExperience().size() > 0) {
                    mExVipNum = bean.getExperience().get(0).getNum();

                    String str1 = "增加额外<font color='#4B96F3'>" + mExVipNum + "</font>次沟通权限";
                    tvMsg.setText(Html.fromHtml(str1));
                    tvNumber4.setText(mExVipNum + "次沟通权限");
                    // tvPrice4.setText(bean.getExperience().get(0).getPrice_desc());
                    String text = bean.getExperience().get(0).getPrice_desc();
                    final SpannableStringBuilder sb = new SpannableStringBuilder(text);
                    sb.setSpan(new AbsoluteSizeSpan(20, true), 1, text.length() - 3, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    sb.setSpan(new StyleSpan(Typeface.BOLD), 1, text.length() - 3, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    tvPrice4.setText(sb);

                }
                if (bean != null && bean.getVipList() != null && bean.getVipList().size() > 0) {
                    mVipNum = bean.getVipList().get(0).getNum();

                    String str1 = "增加额外<font color='#4B96F3'>" + mVipNum + "</font>次沟通权限";
                    tvMsg.setText(Html.fromHtml(str1));
                    tvNumber2.setText(mVipNum + "次沟通权限");
                    //   tvPrice2.setText(bean.getVipList().get(0).getPrice_desc());

                    String text = bean.getVipList().get(0).getPrice_desc();
                    final SpannableStringBuilder sb = new SpannableStringBuilder(text);
                    sb.setSpan(new AbsoluteSizeSpan(20, true), 1, text.length() - 2, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    sb.setSpan(new StyleSpan(Typeface.BOLD), 1, text.length() - 2, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    tvPrice2.setText(sb);

                }
                rlayoutVipNew.setBackgroundResource(R.drawable.bg_vip);
                rlayoutVip.setBackgroundResource(R.drawable.bg_vip);
                rlayoutSvip.setBackgroundResource(R.drawable.bg_vip);
                rlayoutExperienceVip.setBackgroundResource(R.drawable.bg_vip);
                if (curPosition == 0) {
                    String str1 = "增加额外<font color='#4B96F3'>" + mNewVipNum + "</font>次沟通权限";
                    tvMsg.setText(Html.fromHtml(str1));
                    rlayoutVipNew.setBackgroundResource(R.drawable.bg_vip_select);

                } else if (curPosition == 1) {
                    String str1 = "增加额外<font color='#4B96F3'>" + mVipNum + "</font>次沟通权限";
                    tvMsg.setText(Html.fromHtml(str1));
                    rlayoutVip.setBackgroundResource(R.drawable.bg_vip_select);

                } else if (curPosition == 3) {
                    String str1 = "增加额外<font color='#4B96F3'>" + mExVipNum + "</font>次沟通权限";
                    tvMsg.setText(Html.fromHtml(str1));
                    rlayoutExperienceVip.setBackgroundResource(R.drawable.bg_vip_select);
                }

                dismissBookingToast();
            }

            @Override
            public void onFailed(int code, String msg) {
                ToastUtils.showCentetImgToast(VipV4ListActivity.this, msg);
                dismissBookingToast();
            }
        });


    }

    private void weChatPay(final String orderStr) {
        RequestManager.getInstance().weChatPay(orderStr, "wechat", new WechatPayCallback() {
            @Override
            public void onSuccess(WeChatPayInfo order) {
                dismissBookingToast();
                Logger.i("lzq", "微信支付成功");
                String weChatAppID = order.getAppid();
                final IWXAPI msgApi = WXAPIFactory.createWXAPI(VipV4ListActivity.this, null);
                msgApi.registerApp(weChatAppID);

                PayReq request = new PayReq();
                request.appId = weChatAppID;
                request.partnerId = order.getPartnerid();
                request.prepayId = order.getPrepayid();
                Logger.i("lzq", "微信支付成功" + order.getPackageX());
                request.packageValue = order.getPackageX();
                request.nonceStr = order.getNoncestr();
                request.timeStamp = String.valueOf(order.getTimestamp());
                request.sign = order.getSign();
                msgApi.sendReq(request);
            }

            @Override
            public void onFailed(int code, String msg) {
                dismissBookingToast();
                ToastUtils.showCentetImgToast(VipV4ListActivity.this, msg);
            }
        });
    }

    private void aliPay(String orderStr) {
//        String authInfo = "app_id=2017112800216549&method=alipay.trade.app.payAli&format=JSON&charset=utf-8&sign_type=RSA2&version=1.0&return_url=&notify_url=http%3A%2F%2Fq.qiqueqiao.com%2Fnotify&timestamp=2017-12-19+17%3A43%3A39&sign=Z9IFhJGWHWOb42LbIOjZmp%2BFH1rHCDJirDGt3jclvEW4fxZ3wLAgfyi9hcu6ixofX7hwdU7Ud3MyCKLAM8dIw%2FdjRnSmuNFMA53u57s0%2F3DGWU2qGr1qJ4cRBTACaGPWG3AOhCRM8x89pGRke44f09dMVBVKkPK2nPjMP4nYec5VaOQrgbf%2BFD8fye85NGGVRQnkCrGdtG6uX5%2FcUePAEFaNIkZ8O30Nh0cU%2BiUTyLlZnhYmdyHkuvoHlcouU5HQ10i4MCMlHuY1IsIxvEu31lAxYQvmJn%2BnZ5k6PU9Qreq4i8g%2BP2cjOvG%2FfGXZ0%2B1KOFsp0B8E67TFSKGXGe0gBw%3D%3D&biz_content=%7B%22out_trade_no%22%3A%22G2017121919766192722696%22%2C%22total_amount%22%3A%220.01%22%2C%22subject%22%3A%22test+subject%22%2C%22product_code%22%3A%22QUICK_MSECURITY_PAY%22%7D";

        RequestManager.getInstance().payAli(orderStr, "alipay", new PayCallback() {
            @Override
            public void onSuccess(final String order) {
                dismissBookingToast();
                /**
                 * 这里只是为了方便直接向商户展示支付宝的整个支付流程；所以Demo中加签过程直接放在客户端完成；
                 * 真实App里，privateKey等数据严禁放在客户端，加签过程务必要放在服务端完成；
                 * 防止商户私密数据泄露，造成不必要的资金损失，及面临各种安全风险；
                 *
                 * authInfo的获取必须来自服务端；
                 */

                Runnable payRunnable = new Runnable() {

                    @Override
                    public void run() {
                        PayTask alipay = new PayTask(VipV4ListActivity.this);
                        Map<String, String> result = alipay.payV2(order, true);
                        Log.i("msp", result.toString());

                        Message msg = new Message();
                        msg.what = SDK_PAY_FLAG;
                        msg.obj = result;
                        mHandler.sendMessage(msg);
                    }
                };

                Thread payThread = new Thread(payRunnable);
                payThread.start();

            }

            @Override
            public void onFailed(int code, String msg) {
                dismissBookingToast();
                ToastUtils.showCentetImgToast(VipV4ListActivity.this, msg);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 106 && resultCode == 16) {
            buildData();
        }
        if (resultCode == 0 && requestCode == 106) {
            buildData();
        }
    }
}

