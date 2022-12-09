package com.xinniu.android.qiqueqiao.request.callback;

import com.xinniu.android.qiqueqiao.bean.GoToCollectBean;
import com.xinniu.android.qiqueqiao.bean.IndexNewBean;

import java.util.List;

/**
 * Created by yuchance on 2018/5/30.
 */

public interface GotoCollectCallback {
    void onSuccess(GoToCollectBean data,String msg);
    void onFailed(int code,String msg);

}
