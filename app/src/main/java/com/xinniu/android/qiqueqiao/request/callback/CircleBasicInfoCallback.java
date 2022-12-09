package com.xinniu.android.qiqueqiao.request.callback;

import com.xinniu.android.qiqueqiao.bean.CircleBasicInfoBean;
import com.xinniu.android.qiqueqiao.bean.CircleBean;
import com.xinniu.android.qiqueqiao.bean.GroupBean;
import com.xinniu.android.qiqueqiao.bean.OtherUserInfo;

/**
 * Created by yuchance on 2018/5/14.
 */

public interface CircleBasicInfoCallback {
    void onSuccess(CircleBean bean);
    void onFailed(int code,String msg);

}
