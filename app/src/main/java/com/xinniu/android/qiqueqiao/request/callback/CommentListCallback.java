package com.xinniu.android.qiqueqiao.request.callback;

import com.xinniu.android.qiqueqiao.bean.CommentBean;

/**
 * Created by yuchance on 2018/9/27.
 */

public interface CommentListCallback {
    void onSuccess(CommentBean item);
    void onFailed(int code, String msg);
}
