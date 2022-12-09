package com.xinniu.android.qiqueqiao.request.callback;

import com.xinniu.android.qiqueqiao.bean.IndexNewBean;
import com.xinniu.android.qiqueqiao.bean.ResourceItem;
import com.xinniu.android.qiqueqiao.request.converter.ResultDO;

import java.util.List;

/**
 * Created by lzq on 2017/12/13.
 */

public interface GetResourceListCallback {
    void onSuccess(IndexNewBean resultDO);
    void onFailed(int code,String msg);
}
