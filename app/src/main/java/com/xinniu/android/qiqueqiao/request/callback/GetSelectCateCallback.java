package com.xinniu.android.qiqueqiao.request.callback;

import com.xinniu.android.qiqueqiao.bean.SeclectCateBean;
import com.xinniu.android.qiqueqiao.bean.SelectCategory;

import java.util.List;

/**
 * Created by yuchance on 2018/6/27.
 */

public interface GetSelectCateCallback {
    void onSuccess(SeclectCateBean list);
    void onFailed(int code,String msg);
}
