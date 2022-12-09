package com.xinniu.android.qiqueqiao.request.callback;

import com.xinniu.android.qiqueqiao.bean.AreaBean;

import java.util.List;

/**
 * Created by yuchance on 2018/9/27.
 */

public interface GetAreaGroupCallback {
    void onSuccess(List<AreaBean> item);

    void onFailed(int code, String msg);
}
