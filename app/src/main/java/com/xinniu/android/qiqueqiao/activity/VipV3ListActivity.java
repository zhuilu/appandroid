package com.xinniu.android.qiqueqiao.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Point;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import android.text.TextUtils;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.percentlayout.widget.PercentFrameLayout;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.alipay.sdk.app.PayTask;
import com.scwang.smartrefresh.layout.util.DensityUtil;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.xinniu.android.qiqueqiao.R;
import com.xinniu.android.qiqueqiao.base.BaseActivity;
import com.xinniu.android.qiqueqiao.bean.CenterBean;
import com.xinniu.android.qiqueqiao.bean.OrderInfoBean;
import com.xinniu.android.qiqueqiao.bean.TalkPackageBuyBean;
import com.xinniu.android.qiqueqiao.bean.VipCardListBean;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import retrofit2.Call;

/**
 * Created by yuchance on 2018/4/18.
 * 开通会员页面
 */

public class VipV3ListActivity extends BaseActivity {


    private static final int SDK_PAY_FLAG = 1;
    private static final int SDK_AUTH_FLAG = 2;
    @BindView(R.id.bt_close)
    RelativeLayout btClose;

    @BindView(R.id.mvip_head_cImg)
    CircleImageView mvipHeadCImg;
    @BindView(R.id.mvip_head_isv)
    ImageView mvipHeadIsv;

    @BindView(R.id.mvip_name)
    TextView mvipName;
    @BindView(R.id.mis_vip_img)
    ImageView misVipImg;
    @BindView(R.id.mtvVipTime)
    TextView mtvVipTime;
    @BindView(R.id.mvip_totime_tv)
    TextView mvipTotimeTv;
    @BindView(R.id.muntelltimes)
    TextView muntelltimes;
    @BindView(R.id.btext_bug_vip)
    TextView btextBugVip;
    @BindView(R.id.btext_bug_svip)
    TextView btextBugSvip;
//    @BindView(R.id.mhScrollView)
//    MyHScrollView mhScrollView;

    @BindView(R.id.vipRF)
    PercentFrameLayout vipRF;
    @BindView(R.id.svipRF)
    PercentFrameLayout svipRF;
    @BindView(R.id.minivipRF)
    PercentFrameLayout minivipRF;
    @BindView(R.id.mvip_to_svip)
    TextView mvipToSvip;
    @BindView(R.id.mMini_times)
    TextView mMiniTimes;
    @BindView(R.id.btext_bug_minivip)
    TextView btextBugMinivip;
    @BindView(R.id.imggroup)
    ImageView imggroup;
    @BindView(R.id.tvgroup)
    TextView tvgroup;
    @BindView(R.id.tvgroupin)
    TextView tvgroupin;
    @BindView(R.id.yblacksRl)
    RelativeLayout yblacksRl;
    @BindView(R.id.tvMsg)
    TextView tvMsg;
    @BindView(R.id.tvMsginfo)
    TextView tvMsginfo;
    @BindView(R.id.mmini_pricetv)
    TextView mminiPricetv;
    @BindView(R.id.mmini_originalpricetv)
    TextView mminiOriginalpricetv;
    @BindView(R.id.mvip_pricetv)
    TextView mvipPricetv;
    @BindView(R.id.msvip_pricetv)
    TextView msvipPricetv;
    @BindView(R.id.vip_plus_tv)
    TextView vipPlusTv;
    @BindView(R.id.mvip_plus_pricetv)
    TextView mvipPlusPricetv;
    @BindView(R.id.mvip_rb)
    RadioButton mvipRb;
    @BindView(R.id.msvip_rb)
    RadioButton msvipRb;
    @BindView(R.id.mvip_screenrg)
    RadioGroup mvipScreenrg;
    @BindView(R.id.mhScrollView)
    ViewPager mhScrollView;
    private Map<Integer, String> miniPriceMap = new HashMap<>();
    private Map<Integer, String> vipPriceMap = new HashMap<>();
    private Map<Integer, String> svipPriceMap = new HashMap<>();
    private Map<Integer, String> miniInfoMap = new HashMap<>();
    private Map<Integer, String> vipInfoMap = new HashMap<>();
    private Map<Integer, String> svipInfoMap = new HashMap<>();
    private Integer VIP = 1;
    private Integer SVIP = 2;
    private Integer MINIVIP = 3;
    private int mVipNum;
    private int mSvipNum;
    private int mNewVipNum;
    private List<VipCardListBean> vipCardList = new ArrayList<>();


    private int xMealId = 0;
    private String orderSn;

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
                        ToastUtils.showCentetImgToast(VipV3ListActivity.this, "支付成功");
//                        finish();
                        if (needPhotopop.isShowing()) {
                            dispopwindow();
                            buildData();
                        }
                    } else if (TextUtils.equals(resultStatus, "6001")) {
                        ToastUtils.showCentetImgToast(VipV3ListActivity.this, "支付取消");
                    } else if (TextUtils.equals(resultStatus, "8000")) {
                        ToastUtils.showCentetImgToast(VipV3ListActivity.this, "支付结果确认中");
                    } else {
                        ToastUtils.showCentetImgToast(VipV3ListActivity.this, "支付失败");
                    }

                    break;
                }
                default:
                    break;
            }
        }
    };
    private WeChatPayBroadcastReceiver broadcastReceiver;
    private String svip = "vip";
    private int vipMealId;
    private int svipMealId;
    private int miniMealId;
    private int plusMealId;
    private int width;
    private int vipScrollX = 600;
    private int vipbuy = 1;
    private String price;
    private String vipinfo;
    private String plusPrice;
    private String plusInfo;
    private int isVip;
    private ScrollAdapter mScrollAdapter;
    private int curPosition;


    @Override
    public int getLayoutId() {
        return R.layout.activity_vip_v3list;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        topStatusBar(true);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            svip = bundle.getString("svip");
        }
        Display display = getWindowManager().getDefaultDisplay();
        Point point = new Point();
        display.getSize(point);
        width = point.x;

        if (vipRF != null && svipRF != null && minivipRF != null) {
            LinearLayout.LayoutParams vipParams = (LinearLayout.LayoutParams) vipRF.getLayoutParams();
            LinearLayout.LayoutParams svipParams = (LinearLayout.LayoutParams) svipRF.getLayoutParams();
            LinearLayout.LayoutParams minivipParams = (LinearLayout.LayoutParams) minivipRF.getLayoutParams();
            vipParams.width = width - DensityUtil.dp2px(55);
            svipParams.width = width - DensityUtil.dp2px(55);
            minivipParams.width = width - DensityUtil.dp2px(55);
            vipRF.setLayoutParams(vipParams);
            svipRF.setLayoutParams(svipParams);
            minivipRF.setLayoutParams(minivipParams);

        }
        IntentFilter filter = new IntentFilter();
        filter.addAction(this.getPackageName() + ".WeChatPay");
        broadcastReceiver = new WeChatPayBroadcastReceiver();
        registerReceiver(broadcastReceiver, filter);

        buildData();

        yblacksRl.setVisibility(View.VISIBLE);
        mvipToSvip.setText(R.string.svip_text_x);
        imggroup.setImageResource(R.mipmap.big_group);
        tvgroup.setText("500人群组");
        tvgroupin.setText("可创建5个500人群组");
        tvMsginfo.setText("可以与500位BOSS直聊合作");
     //   vipbuy = SVIP;
        msvipRb.setChecked(true);
        mvipRb.setChecked(false);
        mScrollAdapter = new ScrollAdapter();
        mhScrollView.setAdapter(mScrollAdapter);
        mhScrollView.setOffscreenPageLimit(2);
        mhScrollView.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position == 0) {
                    yblacksRl.setVisibility(View.VISIBLE);
                    mvipToSvip.setText(R.string.svip_text_x);
                    imggroup.setImageResource(R.mipmap.big_group);
                    tvgroup.setText("500人群组");
                    tvgroupin.setText("可创建5个500人群组");
                    tvMsg.setText(mSvipNum + "次沟通次数");
                    tvMsginfo.setText("可以与500位BOSS直聊合作");
                    vipbuy = SVIP;
                    msvipRb.setChecked(true);
                    mvipRb.setChecked(false);
                    curPosition = 0;
                }
                if (position == 1) {
                    yblacksRl.setVisibility(View.GONE);
                    mvipToSvip.setText(R.string.equity_text);
                    imggroup.setImageResource(R.mipmap.middle_group);
                    tvgroup.setText("200人群组");
                    tvgroupin.setText("可创建5个200人群组");
                    tvMsg.setText(mVipNum + "次沟通次数");
                    tvMsginfo.setText("可以与100位BOSS直聊合作");
                    vipbuy = VIP;
                    msvipRb.setChecked(false);
                    mvipRb.setChecked(true);
                    curPosition = 1;
                }
                if (position == 2) {
                    tvMsg.setText(mNewVipNum + "次沟通次数");
                    curPosition = 2;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        mvipScreenrg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.msvip_rb) {
                    mhScrollView.setCurrentItem(0, true);
                }
                if (checkedId == R.id.mvip_rb) {
                    mhScrollView.setCurrentItem(1, true);
                }

            }
        });


    }

    private void buildData() {
        showBookingToast(1);
        RequestManager.getInstance().getVipV3x(new GetVipV3ListCallback() {
            @Override
            public void onSuccess(VipV3Bean bean) {
                if (bean.getUsers().getIs_v() == 1) {
                    ShowUtils.showViewVisible(mvipHeadIsv, View.VISIBLE);
                } else {
                    ShowUtils.showViewVisible(mvipHeadIsv, View.GONE);
                }
                if (bean.getInterim() != null && bean.getInterim().size() > 0) {
                    minivipRF.setVisibility(View.VISIBLE);
                    vipRF.setVisibility(View.GONE);
                    if (!TextUtils.isEmpty(bean.getUsers().getTime_down())) {
                        mMiniTimes.setText("倒计时: " + bean.getUsers().getTime_down());
                    }
                    if (!TextUtils.isEmpty(bean.getInterim().get(0).getPrice_desc())) {
                        mminiPricetv.setText(bean.getInterim().get(0).getPrice_desc());
                    }
                    if (!TextUtils.isEmpty(bean.getInterim().get(0).getOriginal_price_desc())) {
                        mminiOriginalpricetv.setText("原价: " + bean.getInterim().get(0).getOriginal_price_desc());
                    }
                } else {
                    minivipRF.setVisibility(View.GONE);
                    vipRF.setVisibility(View.VISIBLE);
                }
                if (bean.getVipList() != null && bean.getVipList().size() > 0) {
                    if (!TextUtils.isEmpty(bean.getVipList().get(0).getPrice_desc())) {
                        ShowUtils.showTextPerfect(mvipPricetv, bean.getVipList().get(0).getPrice_desc());
                    }
                    mVipNum = bean.getVipList().get(0).getNum();

                }
                if (bean.getSvipList() != null && bean.getSvipList().size() > 0) {
                    if (!TextUtils.isEmpty(bean.getSvipList().get(0).getPrice_desc())) {
                        ShowUtils.showTextPerfect(msvipPricetv, bean.getSvipList().get(0).getPrice_desc());
                    }
                    mSvipNum = bean.getSvipList().get(0).getNum();
                    tvMsg.setText(mSvipNum + "次沟通次数");
                }

                isVip = bean.getUsers().getIs_vip();
                if (bean.getUsers().getIs_vip() == 0) {
                    ShowUtils.showTextPerfect(mvipName, bean.getUsers().getRealname());
                    ShowUtils.showViewVisible(misVipImg, View.GONE);
                    ShowUtils.showTextColor(mvipName, ContextCompat.getColor(VipV3ListActivity.this, R.color.text_color_1));
                    ShowUtils.showViewVisible(mtvVipTime, View.VISIBLE);
                    ShowUtils.showTextPerfect(mtvVipTime, "您还不是会员");
                    ShowUtils.showViewVisible(mvipTotimeTv, View.GONE);
//                   ShowUtils.showTextPerfect(btvGoToVip,"开通会员");
//                   ShowUtils.showTextPerfect(btvGoToVipx,"开通会员");
//                    ShowUtils.showTextPerfect(btextBugVip, "立即开通");
//                    ShowUtils.showTextPerfect(btextBugSvip, "立即开通");
                    ShowUtils.showTextPerfect(muntelltimes, bean.getUsers().getTalk_num() + "");
//                    ShowUtils.showTextPerfect(mvipTvTime, bean.getUsers().getCall_num() + "");
                    ShowUtils.showImgPerfect(mvipHeadCImg, bean.getUsers().getHead_pic(), 1);
                } else if (bean.getUsers().getIs_vip() == 1) {
                    ShowUtils.showTextPerfect(mvipName, bean.getUsers().getRealname());
                    ShowUtils.showImageResource(misVipImg, R.mipmap.vip_iconx);
                    ShowUtils.showTextColor(mvipName, ContextCompat.getColor(VipV3ListActivity.this, R.color.king_color));
                    ShowUtils.showTextPerfect(mvipTotimeTv, bean.getUsers().getText());
//                    ShowUtils.showTextPerfect(btextBugVip, "立即续费");
//                    ShowUtils.showTextPerfect(btextBugSvip, "立即开通");
//                   ShowUtils.showTextPerfect(btvGoToVip,"立即续费");
//                   ShowUtils.showTextPerfect(btvGoToVipx,"立即续费");
                    ShowUtils.showTextPerfect(muntelltimes, bean.getUsers().getTalk_num() + "");
//                    ShowUtils.showTextPerfect(mvipTvTime, bean.getUsers().getCall_num() + "");
                    ShowUtils.showImgPerfect(mvipHeadCImg, bean.getUsers().getHead_pic(), 1);
                } else if (bean.getUsers().getIs_vip() == 2) {
                    ShowUtils.showTextPerfect(mvipName, bean.getUsers().getRealname());
                    ShowUtils.showImageResource(misVipImg, R.mipmap.svip_iconx);
                    ShowUtils.showTextColor(mvipName, ContextCompat.getColor(VipV3ListActivity.this, R.color.king_color));
                    ShowUtils.showTextPerfect(mvipTotimeTv, bean.getUsers().getText());
//                   ShowUtils.showTextPerfect(btvGoToVip,"立即续费");
//                   ShowUtils.showTextPerfect(btvGoToVipx,"立即续费");
//                    ShowUtils.showTextPerfect(btextBugVip, "立即开通");
//                    ShowUtils.showTextPerfect(btextBugSvip, "立即续费");
                    ShowUtils.showTextPerfect(muntelltimes, bean.getUsers().getTalk_num() + "");
//                    ShowUtils.showTextPerfect(mvipTvTime, bean.getUsers().getCall_num() + "");
                    ShowUtils.showImgPerfect(mvipHeadCImg, bean.getUsers().getHead_pic(), 1);
                } else {
                    ShowUtils.showViewVisible(misVipImg, View.GONE);
                }
                if (bean != null && bean.getSvipList() != null && bean.getSvipList().size() > 0) {
                    svipMealId = bean.getSvipList().get(0).getId();
                    svipPriceMap.put(SVIP, bean.getSvipList().get(0).getAndroid_price());
                    svipInfoMap.put(SVIP, bean.getSvipList().get(0).getName_info());
                    vipCardList.add(new VipCardListBean(bean.getSvipList().get(0).getId(), bean.getUsers().getTime_down(), bean.getSvipList().get(0).getPrice_desc(), "", bean.getUsers().getIs_vip(), R.mipmap.svip_card, bean.getSvipList().get(0).getAndroid_price(), bean.getSvipList().get(0).getName_info(), R.drawable.shape_svip_btn, R.color.text_color_0605));
                }
                if (bean != null && bean.getInterim() != null && bean.getInterim().size() > 0) {
                    mNewVipNum = bean.getInterim().get(0).getNum();
                    miniMealId = bean.getInterim().get(0).getId();
                    miniPriceMap.put(MINIVIP, bean.getInterim().get(0).getAndroid_price());
                    miniInfoMap.put(MINIVIP, bean.getInterim().get(0).getName_info());
                    vipCardList.add(new VipCardListBean(bean.getInterim().get(0).getId(), bean.getUsers().getTime_down(), bean.getInterim().get(0).getPrice_desc(), bean.getInterim().get(0).getOriginal_price_desc(), bean.getUsers().getIs_vip(), R.mipmap.vip_mini, bean.getInterim().get(0).getAndroid_price(), bean.getInterim().get(0).getName_info(), R.drawable.shape_vipmini_btn, R.color.mini_vip_yellow));

                }
                if (bean != null && bean.getVipList() != null && bean.getVipList().size() > 0) {
                    vipMealId = bean.getVipList().get(0).getId();
                    vipPriceMap.put(VIP, bean.getVipList().get(0).getAndroid_price());
                    vipInfoMap.put(VIP, bean.getVipList().get(0).getName_info());
                    vipCardList.add(new VipCardListBean(bean.getVipList().get(0).getId(), bean.getUsers().getTime_down(), bean.getVipList().get(0).getPrice_desc(), "", bean.getUsers().getIs_vip(), R.mipmap.vip_card, bean.getVipList().get(0).getAndroid_price(), bean.getVipList().get(0).getName_info(), R.drawable.shape_vip_btn, R.color.vip_blue));
                }


                ShowUtils.showTextPerfect(mvipPlusPricetv, bean.getTalk_package().getPrice_desc());
                mScrollAdapter.notifyDataSetChanged();


                dismissBookingToast();
            }

            @Override
            public void onFailed(int code, String msg) {
                ToastUtils.showCentetImgToast(VipV3ListActivity.this, msg);
                dismissBookingToast();
            }
        });


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
                            ToastUtils.showCentetImgToast(VipV3ListActivity.this, msg);
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
                            ToastUtils.showCentetImgToast(VipV3ListActivity.this, msg);
                        }
                    });

                }
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
                        PayTask alipay = new PayTask(VipV3ListActivity.this);
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
                buildData();
            }

            @Override
            public void onFailed(int code, String msg) {
                dismissBookingToast();
                ToastUtils.showCentetImgToast(VipV3ListActivity.this, msg);
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
                final IWXAPI msgApi = WXAPIFactory.createWXAPI(VipV3ListActivity.this, null);
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
                ToastUtils.showCentetImgToast(VipV3ListActivity.this, msg);
            }
        });
    }


    @OnClick({R.id.bt_close, R.id.btext_bug_vip, R.id.btext_bug_svip, R.id.btext_bug_minivip,
            R.id.yblacksRl, R.id.bmake_convertcode, R.id.blx_servicemanager,
            R.id.b_bugvip, R.id.bcompanyvip})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bt_close:
                finish();
                break;
//            case R.id.btvGoToVip:
//                GoVipBuyActivity.start(this);
//                break;
//            case R.id.btvGoToVipx:
//                GoVipBuyActivity.start(this);
//                break;
//            case R.id.btext_bug_vip:
//                buildList(vipMealId, false);
//                break;
//            case R.id.btext_bug_svip:
//                buildList(svipMealId, false);
//                break;
//            case R.id.btext_bug_minivip:
//                buildList(miniMealId, false);
//                break;
            case R.id.yblacksRl:
                HuntServiceActivity.start(VipV3ListActivity.this);
                break;
            case R.id.bmake_convertcode:
                ActMemberActivity.start(VipV3ListActivity.this);
                break;
            case R.id.blx_servicemanager:
                RequestManager.getInstance().center(new RequestCallback<CenterBean>() {
                    @Override
                    public void requestStart(Call call) {

                    }

                    @Override
                    public void onSuccess(CenterBean centerBean) {
                        CenterBean mCenterBean = centerBean;
                        //IMUtils.singleChat(VipV3ListActivity.this, String.valueOf(mCenterBean.getUsers().getF_id()), "客服", "5", "服务经理你好，我想要咨询办理企鹊桥会员");
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
//                if (vipbuy == 1) {
//                    if (minivipRF.getVisibility() == View.VISIBLE) {
//                        buildList(miniMealId, false);
//                    } else if (vipRF.getVisibility() == View.VISIBLE) {
//                        buildList(vipMealId, false);
//                    }
//                } else if (vipbuy == 2) {
//                    buildList(svipMealId, false);
//                }
                buildList(vipCardList.get(curPosition).getAndroidPrice(), vipCardList.get(curPosition).getNameInfo(), vipCardList.get(curPosition).getMealId(), false);


                break;
            case R.id.bcompanyvip:
                ApproveCardActivity.start(VipV3ListActivity.this, "vip", RetrofitHelper.API_URL + "/resource/pages/businessPlan/businessPlan.html", "企业服务");
                break;
            default:
                break;
        }
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

    private class ScrollAdapter extends PagerAdapter {


        @Override
        public int getCount() {
            return vipCardList.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {

            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, final int position) {
            View view = LayoutInflater.from(VipV3ListActivity.this).inflate(R.layout.item_card_pager, null);
            TextView mpricetv = (TextView) view.findViewById(R.id.mpricetv);
            TextView moriginalpricetv = (TextView) view.findViewById(R.id.moriginalpricetv);
            TextView mtimes = (TextView) view.findViewById(R.id.mtimes);
            ImageView imgView = (ImageView) view.findViewById(R.id.item_card_img);
            TextView buyTv = (TextView) view.findViewById(R.id.btext_bug_minivip);
            buyTv.setBackgroundResource(vipCardList.get(position).getBgBtnsrc());
            buyTv.setTextColor(vipCardList.get(position).getTextBtn());
            imgView.setImageResource(vipCardList.get(position).getVipCardId());
            mpricetv.setText(vipCardList.get(position).getPriceDesc());
            if (!TextUtils.isEmpty(vipCardList.get(position).getOriginalPriceDesc())) {
                moriginalpricetv.setVisibility(View.VISIBLE);
                mtimes.setVisibility(View.VISIBLE);
                moriginalpricetv.setText(vipCardList.get(position).getOriginalPriceDesc());
                mtimes.setText(vipCardList.get(position).getTimeDown());
            } else {
                moriginalpricetv.setVisibility(View.GONE);
                mtimes.setVisibility(View.GONE);

            }
            mtimes.setText(vipCardList.get(position).getTimeDown());

            buyTv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    buildList(vipCardList.get(position).getAndroidPrice(), vipCardList.get(position).getNameInfo(), vipCardList.get(position).getMealId(), false);
                }
            });
            container.addView(view);
            return view;
        }
        /**
         * 从ViewGroup中移除当前对象（图片）
         */
        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View)object);
        }

    }


}
