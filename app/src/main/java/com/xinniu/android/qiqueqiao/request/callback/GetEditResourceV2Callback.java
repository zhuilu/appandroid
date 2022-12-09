package com.xinniu.android.qiqueqiao.request.callback;

import com.xinniu.android.qiqueqiao.bean.GetConfigBean;
import com.xinniu.android.qiqueqiao.bean.GetEditResourceInfoV2Bean;

/**
 * Created by yuchance on 2018/12/17.
 */

public interface GetEditResourceV2Callback {
    void onSuccess(GetEditResourceInfoV2Bean bean);
    void onFailed(int code,String msg);
}
