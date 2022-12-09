package com.xinniu.android.qiqueqiao.request.callback;

import com.xinniu.android.qiqueqiao.bean.VidStsBean;

/**
 * Created by yuchance on 2018/9/27.
 */

public interface GetAccessTokenCallback {
    void onSuccess(VidStsBean item);
    void onFailue(int code,String msg);
}
