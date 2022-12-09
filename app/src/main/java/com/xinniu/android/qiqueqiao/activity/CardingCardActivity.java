package com.xinniu.android.qiqueqiao.activity;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
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
import com.xinniu.android.qiqueqiao.bean.CombingCardBean;
import com.xinniu.android.qiqueqiao.bean.OrderInfoBean;
import com.xinniu.android.qiqueqiao.bean.WeChatPayInfo;
import com.xinniu.android.qiqueqiao.dialog.AlertDialogUtils;
import com.xinniu.android.qiqueqiao.pay.PayResult;
import com.xinniu.android.qiqueqiao.request.RequestManager;
import com.xinniu.android.qiqueqiao.request.callback.GetCombingCardCallback;
import com.xinniu.android.qiqueqiao.request.callback.PayCallback;
import com.xinniu.android.qiqueqiao.request.callback.VipBugCallback;
import com.xinniu.android.qiqueqiao.request.callback.WechatPayCallback;
import com.xinniu.android.qiqueqiao.utils.ComUtils;
import com.xinniu.android.qiqueqiao.utils.Logger;
import com.xinniu.android.qiqueqiao.utils.ToastUtils;

import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

public class CardingCardActivity extends BaseActivity {
    @BindView(R.id.tv_price)
    TextView tvPrice;
    private static final int SDK_PAY_FLAG = 1;
    @BindView(R.id.tv_num)
    TextView tvNum;
    private WeChatPayBroadcastReceiver broadcastReceiver;
    private int oneMeal;
    private String oneTitle;
    private String onePrice;
    private String mFrom = "0";
    private String mTitle;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SDK_PAY_FLAG: {
                    Log.i("lzq", "支付成功");
//                    HashMap<String, Object> mapData = (HashMap<String, Object>) msg.obj;
                    PayResult payResult = new PayResult((Map<String, String>) msg.obj);
                    String resultStatus = payResult.getResultStatus();
                    Bundle bundle = msg.getData();
                    String title = bundle.getString("title");
                    Log.i("Alipay", "payResult : " + payResult.toString());
                    Log.i("Alipay", "resultStatus : " + resultStatus);
                    // 判断resultStatus 为“9000”则代表支付成功，具体状态码代表含义可参考接口文档
                    if (TextUtils.equals(resultStatus, "9000")) {
                        ToastUtils.showCentetImgToast(CardingCardActivity.this, "支付成功");
//                        finish();
                        goMyPush(title);

                        if (needPhotopop.isShowing()) {
                            dispopwindow();
                        }
                    } else if (TextUtils.equals(resultStatus, "6001")) {
                        ToastUtils.showCentetImgToast(CardingCardActivity.this, "支付取消");
                    } else if (TextUtils.equals(resultStatus, "8000")) {
                        ToastUtils.showCentetImgToast(CardingCardActivity.this, "支付结果确认中");
                    } else {
                        ToastUtils.showCentetImgToast(CardingCardActivity.this, "支付失败");
                    }

                    break;
                }
                default:
                    break;
            }
        }
    };

    public static void start(Context context, String type) {
        Intent intent = new Intent(context, CardingCardActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("from", type);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

    public static void start(Activity context, int requestCode) {
        Intent intent = new Intent(context, CardingCardActivity.class);
        context.startActivityForResult(intent, requestCode);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_carding_card;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        topStatusBar(true);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            mFrom = getIntent().getExtras().getString("from");
        }
        IntentFilter filter = new IntentFilter();
        filter.addAction(this.getPackageName() + ".WeChatPay");
        broadcastReceiver = new WeChatPayBroadcastReceiver();
        registerReceiver(broadcastReceiver, filter);
        showBookingToast(1);
        getData();
    }

    private void getData() {
        RequestManager.getInstance().getCombingCardList(new GetCombingCardCallback() {
            @Override
            public void onSuccess(CombingCardBean list) {
                tvNum.setText("当前已有 " + list.getCombing_card_num() + " 张");
                if (list.getCombingCardList().size() > 0) {
                    tvPrice.setText("¥" + list.getCombingCardList().get(0).getAndroid_price());
                    oneMeal = list.getCombingCardList().get(0).getId();
                    oneTitle = list.getCombingCardList().get(0).getDesc();
                    onePrice = list.getCombingCardList().get(0).getAndroid_price();

                }
                dismissBookingToast();
            }

            @Override
            public void onFailed(int code, String msg) {
                dismissBookingToast();
                ToastUtils.showCentetToast(CardingCardActivity.this, msg);
            }
        });
    }


    @OnClick({R.id.bt_finish, R.id.tv_bug})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_finish:
                finish();
                break;
            case R.id.tv_bug:
                buildList(oneMeal, oneTitle, onePrice);
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
                }
                goMyPush(mTitle);

            }

        }
    }

    @Override
    protected void onDestroy() {
        unregisterReceiver(broadcastReceiver);
        super.onDestroy();
    }

    private void buildList(final int mealId, final String type, String price) {
        setPopWindow(R.layout.view_popup_selectway, R.style.anim_bottom);
        TextView titleTv = (TextView) popview.findViewById(R.id.mbuytitletv);
        TextView priceTv = (TextView) popview.findViewById(R.id.mbuy_moneytv);
        TextView pricemTv = (TextView) popview.findViewById(R.id.mpay_price);
        titleTv.setText(type);
        priceTv.setText("¥ " + price);
        pricemTv.setText("" + price);
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
                RequestManager.getInstance().CombingCardBuy(mealId, new VipBugCallback() {
                    @Override
                    public void onSuccess(OrderInfoBean bean) {
                        if (!TextUtils.isEmpty(bean.getOrder_sn())) {
                            String orderSn = bean.getOrder_sn();
                            Log.i("lzq", "创建订单成功：" + bean.getOrder_sn());
                            if (wechatCb.isChecked()) {
                                weChatPay(orderSn, type);
                            }
                            if (zfbaoCb.isChecked()) {
                                aliPay(orderSn, type);
                            }
                        }
                    }

                    @Override
                    public void onFailed(int code, String msg) {
                        dismissBookingToast();
                        ToastUtils.showCentetToast(CardingCardActivity.this, msg);
                    }
                });


            }
        });

    }

    private void weChatPay(final String orderStr, final String title) {
        RequestManager.getInstance().weChatPay(orderStr, "wechat", new WechatPayCallback() {
            @Override
            public void onSuccess(WeChatPayInfo order) {
                dismissBookingToast();
                mTitle = title;
                Logger.i("lzq", "微信支付成功");
                String weChatAppID = order.getAppid();
                final IWXAPI msgApi = WXAPIFactory.createWXAPI(CardingCardActivity.this, null);
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
                ToastUtils.showCentetImgToast(CardingCardActivity.this, msg);
            }
        });
    }

    private void aliPay(String orderStr, final String title) {
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
                        PayTask alipay = new PayTask(CardingCardActivity.this);
                        Map<String, String> result = alipay.payV2(order, true);
                        Log.i("msp", result.toString());

                        Message msg = new Message();
                        msg.what = SDK_PAY_FLAG;
                        msg.obj = result;
                        Bundle bundle = new Bundle();
                        bundle.putString("title", title);
                        msg.setData(bundle);

                        mHandler.sendMessage(msg);
                    }
                };

                Thread payThread = new Thread(payRunnable);
                payThread.start();
            }

            @Override
            public void onFailed(int code, String msg) {
                dismissBookingToast();
                ToastUtils.showCentetImgToast(CardingCardActivity.this, msg);
            }
        });
    }

    private void goMyPush(String title) {
        AlertDialogUtils.AlertDialog(CardingCardActivity.this, "已成功购买" + title, "知道了", "", new AlertDialogUtils.setOnClick() {
            @Override
            public void setLeftOnClick(DialogInterface dialog) {
                dialog.dismiss();
                if (mFrom.equals("1")) {

                  getData();
                } else {
                    Intent intent = new Intent();
                    setResult(Activity.RESULT_OK, intent);
                    finish();
                }
            }

            @Override
            public void setRightOnClick(DialogInterface dialog) {
                dialog.dismiss();
            }
        });


    }


}
