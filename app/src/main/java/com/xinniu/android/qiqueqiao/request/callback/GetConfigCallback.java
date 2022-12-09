package com.xinniu.android.qiqueqiao.request.callback;

import com.xinniu.android.qiqueqiao.bean.CityListBean;
import com.xinniu.android.qiqueqiao.bean.GetConfigBean;

/**
 * Created by yuchance on 2018/6/22.
 */

public interface GetConfigCallback {
    void onSuccess(GetConfigBean getConfigBean);
    void onFailed(int code,String msg);


}
