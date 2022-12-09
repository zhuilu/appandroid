package com.xinniu.android.qiqueqiao.request.callback;

import com.xinniu.android.qiqueqiao.bean.WeChatPayInfo;

/**
 * Created by lzq on 2017/12/22.
 */

public interface WechatPayCallback {
    void onSuccess(WeChatPayInfo info);
    void onFailed(int code,String msg);
}
