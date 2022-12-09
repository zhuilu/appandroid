package com.xinniu.android.qiqueqiao.request.callback;

import com.xinniu.android.qiqueqiao.bean.TalkPackageBuyBean;
import com.xinniu.android.qiqueqiao.bean.TalkToUserBean;

/**
 * Created by yuchance on 2019/1/18.
 */

public interface TalkPackageCallback {
    void onSuccess(TalkPackageBuyBean bean);
    void onFailue(int code,String msg);
}
