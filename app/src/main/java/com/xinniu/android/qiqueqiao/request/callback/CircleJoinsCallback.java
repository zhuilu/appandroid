package com.xinniu.android.qiqueqiao.request.callback;

import com.xinniu.android.qiqueqiao.bean.CircleApplyBean;
import com.xinniu.android.qiqueqiao.bean.SelectCategory;

import java.util.List;

/**
 * Created by lzq on 2018/2/5.
 */

public interface CircleJoinsCallback {
    void onSuccess(CircleApplyBean list);
    void onFailed(int code,String msg);
}
