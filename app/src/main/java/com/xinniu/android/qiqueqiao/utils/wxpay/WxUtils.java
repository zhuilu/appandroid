package com.xinniu.android.qiqueqiao.utils.wxpay;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.text.TextUtils;
import android.util.Xml;

import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.xinniu.android.qiqueqiao.base.BaseApp;
import com.xinniu.android.qiqueqiao.request.RetrofitHelper;
import com.xinniu.android.qiqueqiao.utils.EncryptUtils;
import com.xinniu.android.qiqueqiao.utils.Logger;
import com.xinniu.android.qiqueqiao.utils.wxpay.entry.WxPay;
import com.xinniu.android.qiqueqiao.utils.wxpay.entry.WxReq;
import com.xinniu.android.qiqueqiao.wxapi.WXPayEntryActivity;

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
 * Created by qinlei
 * Created on 2017/12/15
 * Created description :
 */

public class WxUtils {

    // TODO: 2017/12/14
    public static final String APP_ID = "wx430262a62d31dff9";//微信appid
    public static String mch_id = "";//商户ID
    public static String PACKAGE = "Sign=WXPay";
    public static String NOTIFY_URL = "";//通知回调地址
    public static final String KEY = "";//设置的KEY用于统一下单后的签名比对
    // TODO: 使用时需要设置上面这些为正式的值

    private static IWXAPI wxapi;

    /**
     * 实例化 IWXAPI
     */
    public static IWXAPI getWxapi() {
        synchronized (WxUtils.class) {
            if (wxapi == null) {
                wxapi = WXAPIFactory.createWXAPI(BaseApp.getInstance(), APP_ID, false);
                wxapi.registerApp(APP_ID);
            }
        }
        return wxapi;
    }

    /**
     * 入口（如果手机端来处理统一下单）
     *
     * @param money
     */
    public static void pay(int money, String attach) {
        String nonce_str = createNoncestr(100);
        String out_trade_no = createNoncestr(4) + System.currentTimeMillis();
        String spbill_create_ip = getIPAddress(true);
        String trade_type = "APP";//表示App 支付

        WxPay wxPay = new WxPay();
        wxPay.setAppid(APP_ID);
        wxPay.setMch_id(mch_id);
        wxPay.setNonce_str(nonce_str);
        wxPay.setBody("微信支付");
        wxPay.setOut_trade_no(out_trade_no);
        wxPay.setTotal_fee(money * 1);
        wxPay.setSpbill_create_ip(spbill_create_ip);
        wxPay.setNotify_url(NOTIFY_URL);
        wxPay.setTrade_type(trade_type);
        wxPay.setAttach(attach);
        wxPay.setKey(KEY);

        RequestBody body = RequestBody.create(
                okhttp3.MediaType.parse("application/json;charset=UTF-8"), createXml(wxPay));

        RetrofitHelper.getInstence().getApiService().unifiedorder(body).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    String responseJson = null;
                    try {
                        responseJson = response.body().string();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    if (!TextUtils.isEmpty(getPrepayId(responseJson))) {
                        doWxPay(getPrepayId(responseJson));
                    } else {
                        Logger.e("WxUtils","获取 PrepayId 错误");
                    }
                } else {

                }

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }

    /**
     * 入口 (如果服务来处理统一下单)
     *
     * @param prepayid
     */
    public static void doWxPay(String prepayid) {
        //判断是否安装了微信
        if (!isInstallWx(BaseApp.getInstance())) {

            return;
        }

        String nonce_str = createNoncestr(100);

        WxReq wxReq = new WxReq();
        wxReq.setAppid(APP_ID);
        wxReq.setPartnerid(mch_id);
        wxReq.setPrepayid(prepayid);
        wxReq.setNoncestr(nonce_str);
        wxReq.setTimestamp(System.currentTimeMillis() / 1000 + "");
        wxReq.set_package(PACKAGE);
        wxReq.setKey(KEY);

        PayReq req = new PayReq();
        req.appId = wxReq.getAppid();
        req.partnerId = wxReq.getPartnerid();
        req.prepayId = wxReq.getPrepayid();
        req.nonceStr = wxReq.getNoncestr();
        req.timeStamp = wxReq.getTimestamp();
        req.packageValue = wxReq.get_package();
        req.sign = EncryptUtils.encryptMD5ToString(wxReq.toString()).toUpperCase();
        getWxapi().sendReq(req);
    }

    /**
     * 创建随机数
     *
     * @param len
     * @return
     */
    private static String createNoncestr(int len) {
        String md5String = EncryptUtils.encryptMD5ToString(System.currentTimeMillis() + "");
        if (len > md5String.length()) {
            len = md5String.length();
        }
        return md5String.substring(0, len);
    }

    /**
     * 获取本机的IP地址
     *
     * @param useIPv4
     * @return
     */
    private static String getIPAddress(boolean useIPv4) {
        try {
            List<NetworkInterface> interfaces = Collections.list(NetworkInterface.getNetworkInterfaces());
            for (NetworkInterface intf : interfaces) {
                List<InetAddress> addrs = Collections.list(intf.getInetAddresses());
                for (InetAddress addr : addrs) {
                    if (!addr.isLoopbackAddress()) {
                        String sAddr = addr.getHostAddress();
                        //boolean isIPv4 = InetAddressUtils.isIPv4Address(sAddr);
                        boolean isIPv4 = sAddr.indexOf(':') < 0;

                        if (useIPv4) {
                            if (isIPv4)
                                return sAddr;
                        } else {
                            if (!isIPv4) {
                                int delim = sAddr.indexOf('%'); // drop ip6 zone suffix
                                return delim < 0 ? sAddr.toUpperCase() : sAddr.substring(0, delim).toUpperCase();
                            }
                        }
                    }
                }
            }
        } catch (Exception ex) {
        } // for now eat exceptions
        return "";
    }

    /**
     * 解析微信统一下单服务器返回的 xml 并获得 与支付id
     *
     * @param xml
     * @return
     */
    public static String getPrepayId(String xml) {
        String prepay_id = "";
        ByteArrayInputStream inputStream = new ByteArrayInputStream(xml.getBytes());
        XmlPullParser xmlPullParser = Xml.newPullParser();
        try {
            xmlPullParser.setInput(inputStream, "UTF-8");
            int eventType = xmlPullParser.getEventType();
            while (eventType != XmlPullParser.END_DOCUMENT) {
                switch (eventType) {
                    case XmlPullParser.START_DOCUMENT:
                        break;
                    case XmlPullParser.START_TAG:
                        if (("prepay_id").equals(xmlPullParser.getName())) {
                            prepay_id = xmlPullParser.nextText();
                        } else {
                            eventType = xmlPullParser.next();
                        }
                        break;
                    case XmlPullParser.END_TAG:
                        break;
                }
                eventType = xmlPullParser.next();
            }
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return prepay_id;
    }

    /**
     * 判断是否安装微信
     *
     * @param context
     * @return
     */
    public static boolean isInstallWx(Context context) {
        final PackageManager packageManager = context.getPackageManager();// 获取packagemanager
        List<PackageInfo> pinfo = packageManager.getInstalledPackages(0);// 获取所有已安装程序的包信息
        if (pinfo != null) {
            for (int i = 0; i < pinfo.size(); i++) {
                String pn = pinfo.get(i).packageName;
                if (pn.equals("com.tencent.mm")) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 创建统一下单内容
     *
     * @param wxXmls
     * @return
     */
    private static String createXml(WxPay wxXmls) {
        StringBuilder xml = new StringBuilder();
        xml.append("<xml>");
        xml.append("<appid>" + wxXmls.getAppid() + "</appid>");
        xml.append("<attach>" + wxXmls.getAttach() + "</attach>");
        xml.append("<body>" + wxXmls.getBody() + "</body>");
        xml.append("<mch_id>" + wxXmls.getMch_id() + "</mch_id>");
        xml.append("<nonce_str>" + wxXmls.getNonce_str() + "</nonce_str>");
        xml.append("<notify_url>" + wxXmls.getNotify_url() + "</notify_url>");
        xml.append("<out_trade_no>" + wxXmls.getOut_trade_no() + "</out_trade_no>");
        xml.append("<spbill_create_ip>" + wxXmls.getSpbill_create_ip() + "</spbill_create_ip>");
        xml.append("<total_fee>" + wxXmls.getTotal_fee() + "</total_fee>");
        xml.append("<trade_type>" + wxXmls.getTrade_type() + "</trade_type>");
        xml.append("<sign>" + EncryptUtils.encryptMD5ToString(wxXmls.toString()).toUpperCase() + "</sign>");
        xml.append("</xml>");
        return xml.toString();
    }

}
