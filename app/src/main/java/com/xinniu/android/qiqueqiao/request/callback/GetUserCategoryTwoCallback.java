package com.xinniu.android.qiqueqiao.request.callback;

import com.xinniu.android.qiqueqiao.bean.SelectCategoryTwo;

/**
 * Created by lzq on 2018/1/11.
 */

public interface GetUserCategoryTwoCallback {
    void onSuccess(SelectCategoryTwo list);
    void onFailed(int code, String msg);
}
