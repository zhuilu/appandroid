package com.xinniu.android.qiqueqiao.request.callback;

import com.xinniu.android.qiqueqiao.bean.SeenBean;

import java.util.List;

/**
 * Created by lzq on 2017/12/12.
 */

public interface GetSeenCallback {
    void onSuccess(SeenBean list);
    void onFailed(int code,String msg);
}
