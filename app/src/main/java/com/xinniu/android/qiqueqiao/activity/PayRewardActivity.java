package com.xinniu.android.qiqueqiao.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alipay.sdk.app.PayTask;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.xinniu.android.qiqueqiao.R;
import com.xinniu.android.qiqueqiao.base.BaseActivity;
import com.xinniu.android.qiqueqiao.bean.WeChatPayInfo;
import com.xinniu.android.qiqueqiao.pay.PayResult;
import com.xinniu.android.qiqueqiao.request.RequestManager;
import com.xinniu.android.qiqueqiao.request.callback.PayCallback;
import com.xinniu.android.qiqueqiao.request.callback.WechatPayCallback;
import com.xinniu.android.qiqueqiao.utils.ComUtils;
import com.xinniu.android.qiqueqiao.utils.Logger;
import com.xinniu.android.qiqueqiao.utils.ToastUtils;

import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

public class PayRewardActivity extends BaseActivity {
    @BindView(R.id.tv_price)
    TextView tvPrice;
    @BindView(R.id.check_01)
    CheckBox check01;
    @BindView(R.id.rlayout_01)
    RelativeLayout rlayout01;
    @BindView(R.id.check_02)
    CheckBox check02;
    @BindView(R.id.rlayout_02)
    RelativeLayout rlayout02;
    @BindView(R.id.btn_submit)
    TextView btnSubmit;
    private int mType = 1;//1：微信，2：支付宝
    private int mId;
    private String order_sn;
    private WeChatPayBroadcastReceiver broadcastReceiver;

    public static void start(Context context, String price, int id, String order_sn) {
        Intent starter = new Intent(context, PayRewardActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("price", price);
        bundle.putString("order_sn", order_sn);
        bundle.getInt("id", id);
        starter.putExtras(bundle);
        context.startActivity(starter);
    }

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
                        ToastUtils.showCentetImgToast(PayRewardActivity.this, "支付成功");

                        RewardDetailActivity.start(mContext, order_sn);
                        ComUtils.finishshortAll();

                    } else if (TextUtils.equals(resultStatus, "6001")) {
                        ToastUtils.showCentetImgToast(PayRewardActivity.this, "支付取消");
                    } else if (TextUtils.equals(resultStatus, "8000")) {
                        ToastUtils.showCentetImgToast(PayRewardActivity.this, "支付结果确认中");
                    } else {
                        ToastUtils.showCentetImgToast(PayRewardActivity.this, "支付失败");
                    }

                    break;
                }
                default:
                    break;
            }
        }
    };

    @Override
    public int getLayoutId() {
        return R.layout.activity_pay_reward;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        topStatusBar(true);
        ComUtils.addActivity(this);
        mId = getIntent().getExtras().getInt("id");
        order_sn = getIntent().getExtras().getString("order_sn");
        String price = getIntent().getExtras().getString("price");
        tvPrice.setText(price);
        IntentFilter filter = new IntentFilter();
        filter.addAction(this.getPackageName() + ".WeChatPay");
        broadcastReceiver = new WeChatPayBroadcastReceiver();
        registerReceiver(broadcastReceiver, filter);
    }

    @OnClick({R.id.bt_return, R.id.rlayout_01, R.id.rlayout_02, R.id.btn_submit})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_return:
                finish();
                break;
            case R.id.rlayout_01:
                mType = 1;
                check01.setChecked(true);
                check02.setChecked(false);
                break;
            case R.id.rlayout_02:
                mType = 2;
                check01.setChecked(false);
                check02.setChecked(true);
                break;
            case R.id.btn_submit:
                //  ComUtils.finishshortAll();
                if (mType == 1) {
                    weChatPay(order_sn);
                } else if (mType == 2) {
                    aliPay(order_sn);
                }
                break;
        }
    }

    private void aliPay(String orderStr) {
//        String authInfo = "app_id=2017112800216549&method=alipay.trade.app.payAli&format=JSON&charset=utf-8&sign_type=RSA2&version=1.0&return_url=&notify_url=http%3A%2F%2Fq.qiqueqiao.com%2Fnotify&timestamp=2017-12-19+17%3A43%3A39&sign=Z9IFhJGWHWOb42LbIOjZmp%2BFH1rHCDJirDGt3jclvEW4fxZ3wLAgfyi9hcu6ixofX7hwdU7Ud3MyCKLAM8dIw%2FdjRnSmuNFMA53u57s0%2F3DGWU2qGr1qJ4cRBTACaGPWG3AOhCRM8x89pGRke44f09dMVBVKkPK2nPjMP4nYec5VaOQrgbf%2BFD8fye85NGGVRQnkCrGdtG6uX5%2FcUePAEFaNIkZ8O30Nh0cU%2BiUTyLlZnhYmdyHkuvoHlcouU5HQ10i4MCMlHuY1IsIxvEu31lAxYQvmJn%2BnZ5k6PU9Qreq4i8g%2BP2cjOvG%2FfGXZ0%2B1KOFsp0B8E67TFSKGXGe0gBw%3D%3D&biz_content=%7B%22out_trade_no%22%3A%22G2017121919766192722696%22%2C%22total_amount%22%3A%220.01%22%2C%22subject%22%3A%22test+subject%22%2C%22product_code%22%3A%22QUICK_MSECURITY_PAY%22%7D";

        RequestManager.getInstance().rewardPayAli(mId, orderStr, "alipay", new PayCallback() {
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
                        PayTask alipay = new PayTask(PayRewardActivity.this);
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
                ToastUtils.showCentetImgToast(PayRewardActivity.this, msg);
            }
        });
    }

    private void weChatPay(final String orderStr) {
        RequestManager.getInstance().rewardWeChatPay(mId, orderStr, "wechat", new WechatPayCallback() {
            @Override
            public void onSuccess(WeChatPayInfo order) {
                dismissBookingToast();
                Logger.i("lzq", "微信支付成功");
                String weChatAppID = order.getAppid();
                final IWXAPI msgApi = WXAPIFactory.createWXAPI(PayRewardActivity.this, null);
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
                ToastUtils.showCentetImgToast(PayRewardActivity.this, msg);
            }
        });
    }

    class WeChatPayBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            int result = intent.getIntExtra("result", -1);
            if (result == 0) {
                RewardDetailActivity.start(mContext, order_sn);
                ComUtils.finishshortAll();

            }

        }
    }
}
