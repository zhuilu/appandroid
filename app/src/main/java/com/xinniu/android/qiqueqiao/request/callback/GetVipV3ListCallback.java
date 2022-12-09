package com.xinniu.android.qiqueqiao.request.callback;

import com.xinniu.android.qiqueqiao.bean.VipListBean;
import com.xinniu.android.qiqueqiao.bean.VipV3Bean;

/**
 * Created by yuchance on 2018/4/18.
 */

public interface GetVipV3ListCallback {
    void onSuccess(VipV3Bean bean);
    void onFailed(int code,String msg);
}
