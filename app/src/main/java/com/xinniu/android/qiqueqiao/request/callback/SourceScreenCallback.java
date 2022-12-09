package com.xinniu.android.qiqueqiao.request.callback;

import com.xinniu.android.qiqueqiao.bean.SourceScreenBean;
import com.xinniu.android.qiqueqiao.bean.TalkToUserBean;

/**
 * Created by yuchance on 2018/4/16.
 */

public interface SourceScreenCallback {

    void onSuccess(SourceScreenBean bean);
    void onFailue(int code,String msg);


}
