package com.xinniu.android.qiqueqiao.request.callback;

import com.xinniu.android.qiqueqiao.bean.GetPopupBean;

import java.util.List;

/**
 * Created by yuchance on 2018/9/25.
 */

public interface GetPopupCallback {
    void onSuccess(GetPopupBean bean);
    void onFailed(int code,String msg);

}
