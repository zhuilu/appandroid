package com.xinniu.android.qiqueqiao.request.callback;

import com.xinniu.android.qiqueqiao.bean.YzmBean;

/**
 * Created by lzq on 2017/12/12.
 */

public interface YzmCallback{
    void onSuccess(YzmBean msg);
    void onFailed(String error,int code);
}

