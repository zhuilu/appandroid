package com.xinniu.android.qiqueqiao.request.callback;

import com.xinniu.android.qiqueqiao.bean.CircleCallBean;
import com.xinniu.android.qiqueqiao.request.converter.ResultDO;

/**
 * Created by yuchance on 2018/8/8.
 */

public interface CircleCallCallback {
    void onSuccess(CircleCallBean bean);
    void onFailed(int code,String msg,String data);
}
