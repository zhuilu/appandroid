package com.xinniu.android.qiqueqiao.request.callback;

import com.xinniu.android.qiqueqiao.bean.MyCommentBean;

/**
 * Created by yuchance on 2018/12/25.
 */

public interface GetMyCommentCallback {

    void onSuccess(MyCommentBean bean);
    void onFailed(int code,String msg);
}
