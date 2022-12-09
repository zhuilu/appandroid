package com.xinniu.android.qiqueqiao.request.callback;

import com.xinniu.android.qiqueqiao.bean.UnReadBean;
import com.xinniu.android.qiqueqiao.bean.UpgradeGroupBean;

/**
 * Created by yuchance on 2018/10/15.
 */

public interface UpgradeGroupCallback {
    void onSuccess(UpgradeGroupBean bean,String msg);
    void onFailue(int code,String msg);
}
