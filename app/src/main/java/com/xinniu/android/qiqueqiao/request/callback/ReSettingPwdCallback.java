package com.xinniu.android.qiqueqiao.request.callback;

import com.xinniu.android.qiqueqiao.request.converter.ResultDO;

/**
 * Created by lzq on 2017/12/12.
 */

public interface ReSettingPwdCallback {
    void onSuccess(ResultDO resultDO);
    void onFailed(int code,String msg);
}
