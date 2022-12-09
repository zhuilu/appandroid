package com.xinniu.android.qiqueqiao.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alipay.sdk.app.PayTask;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.xinniu.android.qiqueqiao.R;
import com.xinniu.android.qiqueqiao.base.BaseActivity;
import com.xinniu.android.qiqueqiao.bean.TalkPackageBuyBean;
import com.xinniu.android.qiqueqiao.bean.VipV3Bean;
import com.xinniu.android.qiqueqiao.bean.WeChatPayInfo;
import com.xinniu.android.qiqueqiao.pay.PayResult;
import com.xinniu.android.qiqueqiao.request.RequestManager;
import com.xinniu.android.qiqueqiao.request.callback.GetVipV3ListCallback;
import com.xinniu.android.qiqueqiao.request.callback.PayCallback;
import com.xinniu.android.qiqueqiao.request.callback.TalkPackageCallback;
import com.xinniu.android.qiqueqiao.request.callback.WechatPayCallback;
import com.xinniu.android.qiqueqiao.utils.Logger;
import com.xinniu.android.qiqueqiao.utils.ToastUtils;

import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by yuchance on 2019/2/20.
 */

public class DataPlusBuyActivity extends BaseActivity {
    @BindView(R.id.mdata_plustv)
    TextView mdataPlustv;

    private static final int SDK_PAY_FLAG = 1;
    @BindView(R.id.msurplus_linktv)
    TextView msurplusLinktv;


    public static void start(Context context){
        Intent intent = new Intent(context,DataPlusBuyActivity.class);
        context.startActivity(intent);
    }



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
                        ToastUtils.showCentetImgToast(DataPlusBuyActivity.this, "支付成功");
//                        finish();
                        if (needPhotopop.isShowing()) {
                            dispopwindow();
                            buildData();
                        }
                    } else if (TextUtils.equals(resultStatus, "6001")) {
                        ToastUtils.showCentetImgToast(DataPlusBuyActivity.this, "支付取消");
                    } else if (TextUtils.equals(resultStatus, "8000")) {
                        ToastUtils.showCentetImgToast(DataPlusBuyActivity.this, "支付结果确认中");
                    } else {
                        ToastUtils.showCentetImgToast(DataPlusBuyActivity.this, "支付失败");
                    }

                    break;
                }
                default:
                    break;
            }
        }
    };
    private int xMealId;
    private String plusPrice;
    private String plusInfo;
    private int talkNum;

    @Override
    public int getLayoutId() {
        return R.layout.activity_dataplus_buy;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        topStatusBar(true);
        showBookingToast(1);
        buildData();
    }

    private void buildData() {
        RequestManager.getInstance().getVipV3x(new GetVipV3ListCallback() {
            @Override
            public void onSuccess(VipV3Bean bean) {
                dismissBookingToast();

                xMealId = bean.getTalk_package().getId();
                plusPrice = bean.getTalk_package().getAndroid_price();
                plusInfo = bean.getTalk_package().getDesc();
                talkNum = bean.getUsers().getTalk_num();
                msurplusLinktv.setText(talkNum+"");
                String price_dec=bean.getTalk_package().getPrice_desc();
                mdataPlustv.setText(price_dec);

            }

            @Override
            public void onFailed(int code, String msg) {
                dismissBookingToast();
                ToastUtils.showCentetToast(DataPlusBuyActivity.this, msg);

            }
        });
    }

    @OnClick({R.id.bt_finish, R.id.bdataplus_buy})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bt_finish:
                finish();
                break;
            case R.id.bdataplus_buy:
                buildList(xMealId);
                break;
        }
    }

    private void buildList(final int xMealId) {
        setPopWindow(R.layout.view_popup_selectway, R.style.anim_bottom);
        TextView titleTv = (TextView) popview.findViewById(R.id.mbuytitletv);
        TextView priceTv = (TextView) popview.findViewById(R.id.mbuy_moneytv);
        TextView pricemTv = (TextView) popview.findViewById(R.id.mpay_price);
        titleTv.setText(plusInfo);
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
                RequestManager.getInstance().talkPackageBuy(new TalkPackageCallback() {
                    @Override
                    public void onSuccess(TalkPackageBuyBean bean) {
                        if (!TextUtils.isEmpty(bean.getOrder_sn())) {
                            String orderSn = bean.getOrder_sn();
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
                        ToastUtils.showCentetImgToast(DataPlusBuyActivity.this, msg);
                    }
                });

            }
        });


    }

    private void aliPay(String orderSn) {
        RequestManager.getInstance().payAli(orderSn, "alipay", new PayCallback() {
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
                        PayTask alipay = new PayTask(DataPlusBuyActivity.this);
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
                ToastUtils.showCentetImgToast(DataPlusBuyActivity.this, msg);
            }
        });
    }

    private void weChatPay(String orderSn) {
        RequestManager.getInstance().weChatPay(orderSn, "wechat", new WechatPayCallback() {
            @Override
            public void onSuccess(WeChatPayInfo order) {
                dismissBookingToast();
                Logger.i("lzq", "微信支付成功");
                String weChatAppID = order.getAppid();
                final IWXAPI msgApi = WXAPIFactory.createWXAPI(DataPlusBuyActivity.this, null);
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
                ToastUtils.showCentetImgToast(DataPlusBuyActivity.this, msg);
            }
        });
    }
}
