package com.xinniu.android.qiqueqiao.request.callback;

import com.xinniu.android.qiqueqiao.bean.ExchangeInfoBean;
import com.xinniu.android.qiqueqiao.request.converter.ResultDO;

/**
 * Created by lzq on 2017/12/14.
 */

public interface ExchageChatInfoCallback {
    void onSuccess(ResultDO exchangeInfoBean);
    void onFailed(int code,String msg);
}
