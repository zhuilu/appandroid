package com.xinniu.android.qiqueqiao.wxapi;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Xml;
import android.widget.Toast;

import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.xinniu.android.qiqueqiao.base.BaseApp;
import com.xinniu.android.qiqueqiao.request.RequestManager;
import com.xinniu.android.qiqueqiao.request.RetrofitHelper;
import com.xinniu.android.qiqueqiao.utils.EncryptUtils;
import com.xinniu.android.qiqueqiao.utils.ToastUtils;
import com.xinniu.android.qiqueqiao.utils.wxpay.WxUtils;
import com.xinniu.android.qiqueqiao.utils.wxpay.entry.WxPay;
import com.xinniu.android.qiqueqiao.utils.wxpay.entry.WxReq;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Collections;
import java.util.List;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by ql
 * 2017/12/14.
 * Description:
 */

public class WXPayEntryActivity extends Activity implements IWXAPIEventHandler {

    private static final int WX_PAY_SUCCESS = 0;
    private static final int WX_PAY_FAIL = -1;
    private static final int WX_PAY_CANCEL = -2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WxUtils.getWxapi().handleIntent(getIntent(), this);
    }

    @Override
    public void onReq(BaseReq baseReq) {

    }

    @Override
    public void onResp(BaseResp baseResp) {
        if (baseResp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {
            switch (baseResp.errCode) {
                case WX_PAY_SUCCESS:
                    //支付成功
                    ToastUtils.showCentetImgToast(this, "支付成功");

                    break;
                case WX_PAY_FAIL:
                    //支付失败
                    ToastUtils.showCentetImgToast(this, "支付失败");
                    break;
                case WX_PAY_CANCEL:
                    //支付取消
                    ToastUtils.showCentetImgToast(this, "支付取消");
                    break;
                default:
                    break;
            }
            closeSelf();
            Intent intent = new Intent();
            intent.setAction(this.getPackageName() + ".WeChatPay");
            intent.putExtra("result", baseResp.errCode);
            sendBroadcast(intent);
        }
    }

    public void closeSelf() {
        finish();
        overridePendingTransition(0, 0);
    }
}
