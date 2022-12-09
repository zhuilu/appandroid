package com.xinniu.android.qiqueqiao.request.callback;

import com.xinniu.android.qiqueqiao.bean.VideoCategoryBean;

import java.util.List;

public interface GetVedioCategoryCallback {
    void onSuccess(List<VideoCategoryBean> bean);
    void onFailed(int code,String msg);
}
