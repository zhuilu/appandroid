package com.xinniu.android.qiqueqiao.request.callback;

import com.xinniu.android.qiqueqiao.bean.AnalysisBean;
import com.xinniu.android.qiqueqiao.bean.CheckStatusBean;

/**
 * Created by lzq on 2018/2/5.
 */

public interface CheckCircleCallback {
    void onSuccess();
    void onOther(CheckStatusBean item);
    void onFailed(int code,String msg);
}
