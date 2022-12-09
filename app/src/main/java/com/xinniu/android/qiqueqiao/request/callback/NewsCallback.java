package com.xinniu.android.qiqueqiao.request.callback;

import com.xinniu.android.qiqueqiao.bean.NewsBean;

/**
 * Created by lzq on 2017/12/25.
 */

public interface NewsCallback {
    void onSuccess(NewsBean bean);
    void onFailed(int code,String msg);
}
