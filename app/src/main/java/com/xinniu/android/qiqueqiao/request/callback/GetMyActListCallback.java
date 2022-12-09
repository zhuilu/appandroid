package com.xinniu.android.qiqueqiao.request.callback;

import com.xinniu.android.qiqueqiao.bean.InteractiveNewsBean;
import com.xinniu.android.qiqueqiao.bean.MyActListBean;

/**
 * Created by yuchance on 2019/1/8.
 */

public interface GetMyActListCallback {
    void onSuccess(MyActListBean bean);
    void onFailed(int code,String msg);
}
