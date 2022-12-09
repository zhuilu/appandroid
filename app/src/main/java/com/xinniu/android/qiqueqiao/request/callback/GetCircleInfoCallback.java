package com.xinniu.android.qiqueqiao.request.callback;

import com.xinniu.android.qiqueqiao.bean.CircleInfobean;
import com.xinniu.android.qiqueqiao.bean.SelectCategory;

/**
 * Created by lzq on 2018/2/1.
 */

public interface GetCircleInfoCallback {
    void onSuccess(CircleInfobean item);
    void onFailed(int code,String msg);
}
