package com.xinniu.android.qiqueqiao.request.callback;

import com.xinniu.android.qiqueqiao.bean.IndexServiceBean;


/**
 * Created by yuchance on 2018/5/10.
 */

public interface ServiceListCallback {

    void onSuccess(IndexServiceBean bean);
    void onFailue(int code, String msg);

}
