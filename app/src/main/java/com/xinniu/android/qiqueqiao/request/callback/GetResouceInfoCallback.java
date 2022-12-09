package com.xinniu.android.qiqueqiao.request.callback;

import com.xinniu.android.qiqueqiao.bean.ResouceInfoBean;

/**
 * Created by BDXK on 2017/12/12.
 * project : xiqueqiao--- android xs
 */

public interface GetResouceInfoCallback {
    void onSuccess(ResouceInfoBean bean);
    void onUndercarriage(String bean);
    void onFailed(int code,String msg);
}
