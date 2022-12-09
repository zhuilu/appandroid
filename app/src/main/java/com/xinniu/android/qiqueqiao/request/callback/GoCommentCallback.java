package com.xinniu.android.qiqueqiao.request.callback;

import com.xinniu.android.qiqueqiao.bean.GoCommentBean;
import com.xinniu.android.qiqueqiao.bean.GoToCollectBean;
import com.xinniu.android.qiqueqiao.bean.InquireBean;

/**
 * Created by yuchance on 2018/10/31.
 */

public interface GoCommentCallback {
    void onSuccess(InquireBean.ListBean data);
    void onFailed(int code,String msg);
}
