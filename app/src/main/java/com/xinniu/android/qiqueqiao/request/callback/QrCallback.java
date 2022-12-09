package com.xinniu.android.qiqueqiao.request.callback;

import com.xinniu.android.qiqueqiao.bean.QrcodeBean;
import com.xinniu.android.qiqueqiao.bean.ResourceReleaseBean;

/**
 * Created by lzq on 2018/1/23.
 */

public interface QrCallback {
    void onSuccess(QrcodeBean bean);
    void onFailed(int code,String msg,String data);
}
