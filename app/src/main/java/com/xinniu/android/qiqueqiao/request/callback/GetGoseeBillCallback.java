package com.xinniu.android.qiqueqiao.request.callback;

import com.xinniu.android.qiqueqiao.bean.GoseeBillBean;
import com.xinniu.android.qiqueqiao.bean.GroupInfoBean;

/**
 * Created by yuchance on 2019/1/9.
 */

public interface GetGoseeBillCallback {
    void onSuccess(GoseeBillBean bean);
    void onFailed(int code,String msg);
}
