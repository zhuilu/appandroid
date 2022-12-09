package com.xinniu.android.qiqueqiao.request.callback;

import com.xinniu.android.qiqueqiao.bean.AddGroupBean;
import com.xinniu.android.qiqueqiao.bean.GreentingsBean;

/**
 * Created by yuchance on 2018/9/27.
 */

public interface GetGreentsCallback {
        void onSuccess(GreentingsBean item);
    void onFailed(int code, String msg);
}
