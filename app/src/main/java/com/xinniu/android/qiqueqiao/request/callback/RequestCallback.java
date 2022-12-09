package com.xinniu.android.qiqueqiao.request.callback;

import retrofit2.Call;

/**
 * Created by qinlei
 * Created on 2017/12/13
 * Created description :
 */

public interface RequestCallback<T> {
    //开始请求，回调 Call 可以用于取消请求
    void requestStart(Call call);

    //请求成功
    void onSuccess(T t);

    //请求失败
    void onFailed(int code, String msg);

    //请求介绍
    void requestEnd();
}
