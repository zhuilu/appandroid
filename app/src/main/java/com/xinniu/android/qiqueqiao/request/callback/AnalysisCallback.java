package com.xinniu.android.qiqueqiao.request.callback;

import com.xinniu.android.qiqueqiao.bean.AnalysisBean;
import com.xinniu.android.qiqueqiao.bean.SelectCategory;

/**
 * Created by lzq on 2018/1/12.
 */

public interface AnalysisCallback {
    void onSuccess(AnalysisBean item);
    void onFailed(int code,String msg);
}
