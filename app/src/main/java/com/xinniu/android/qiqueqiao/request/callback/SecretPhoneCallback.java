package com.xinniu.android.qiqueqiao.request.callback;

import com.xinniu.android.qiqueqiao.bean.SecretPhoneBean;
import com.xinniu.android.qiqueqiao.bean.SourceScreenBean;

/**
 * Created by yuchance on 2018/4/20.
 */

public interface SecretPhoneCallback {

    void onSuccess(SecretPhoneBean bean);
    void onFailue(int code,String msg);


}
