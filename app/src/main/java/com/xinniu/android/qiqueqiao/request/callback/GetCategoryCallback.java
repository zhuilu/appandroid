package com.xinniu.android.qiqueqiao.request.callback;

import com.xinniu.android.qiqueqiao.bean.SelectCategory;

import java.util.List;

/**
 * Created by lzq on 2017/12/12.
 */

public interface GetCategoryCallback {
    void onSuccess(List<SelectCategory> list);
    void onFailed(int code , String msg);
}
