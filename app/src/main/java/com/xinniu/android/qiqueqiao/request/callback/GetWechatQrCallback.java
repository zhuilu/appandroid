package com.xinniu.android.qiqueqiao.request.callback;

import com.xinniu.android.qiqueqiao.bean.WechatQr;

/**
 * Created by lzq on 2018/1/11.
 */

public interface GetWechatQrCallback {
    void onSuccess(WechatQr item);
    void onFailed(int code, String msg);
}
