package com.xinniu.android.qiqueqiao.request.callback;

import com.xinniu.android.qiqueqiao.bean.SelectCategory;

import java.util.List;

/**
 * Created by lzq on 2018/1/11.
 */

public interface GetUserCategoryCallback {
    void onSuccess(List<SelectCategory> list);
    void onFailed(int code,String msg);
}
