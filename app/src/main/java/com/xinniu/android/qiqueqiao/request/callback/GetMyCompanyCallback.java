package com.xinniu.android.qiqueqiao.request.callback;

import com.xinniu.android.qiqueqiao.bean.MyCompanyBean;
import com.xinniu.android.qiqueqiao.bean.ResouceInfoBean;

/**
 * Created by lzq on 2018/2/28.
 */

public interface GetMyCompanyCallback {
    void onSuccess(MyCompanyBean bean);
    void onFailed(int code,String msg);
}
