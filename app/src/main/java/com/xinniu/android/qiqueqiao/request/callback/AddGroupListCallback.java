package com.xinniu.android.qiqueqiao.request.callback;

import com.xinniu.android.qiqueqiao.bean.AddGroupClassifyBean;
import com.xinniu.android.qiqueqiao.bean.AddGroupListBean;

import java.util.List;

/**
 * Created by yuchance on 2018/9/27.
 */

public interface AddGroupListCallback {
    void onSuccess(AddGroupListBean item);
    void onFailed(int code,String msg);
}
