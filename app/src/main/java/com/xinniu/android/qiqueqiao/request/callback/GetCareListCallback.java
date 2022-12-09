package com.xinniu.android.qiqueqiao.request.callback;

import com.xinniu.android.qiqueqiao.bean.CareBean;
import com.xinniu.android.qiqueqiao.bean.SeenBean;

import java.util.List;

/**
 * Created by lzq on 2017/12/13.
 */

public interface GetCareListCallback {
    void onSuccess(CareBean list);
    void onFailed(int code,String msg);
}
