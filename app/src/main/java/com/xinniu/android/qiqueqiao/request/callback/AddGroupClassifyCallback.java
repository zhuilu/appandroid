package com.xinniu.android.qiqueqiao.request.callback;

import com.xinniu.android.qiqueqiao.bean.AddGroupClassifyBean;

import java.util.List;

/**
 * Created by yuchance on 2018/9/27.
 */

public interface AddGroupClassifyCallback {
    void onSuccess(List<AddGroupClassifyBean> item);
    void onFailed(int code,String msg);
}
