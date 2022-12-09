package com.xinniu.android.qiqueqiao.request.callback;

import com.xinniu.android.qiqueqiao.bean.MyCompanyBean;
import com.xinniu.android.qiqueqiao.bean.NewsV2Bean;

/**
 * Created by yuchance on 2018/11/23.
 */

public interface GetNewsV2Callback {
    void onSuccess(NewsV2Bean bean);
    void onFailed(int code,String msg);
}
