package com.xinniu.android.qiqueqiao.request.callback;

import com.xinniu.android.qiqueqiao.bean.TalkToUserBean;

/**
 * Created by lzq on 2018/2/6.
 */

public interface TalkToUserCallback {
    void onSuccess(TalkToUserBean bean);
    void onFailue(int code,String msg);
}
