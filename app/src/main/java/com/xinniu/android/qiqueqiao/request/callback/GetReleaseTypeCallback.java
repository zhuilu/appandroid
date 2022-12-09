package com.xinniu.android.qiqueqiao.request.callback;

import com.xinniu.android.qiqueqiao.bean.GetReleaseTypeBean;
import com.xinniu.android.qiqueqiao.bean.IndexNewBean;

import java.util.List;

/**
 * Created by yuchance on 2018/12/13.
 */

public interface GetReleaseTypeCallback {
    void onSuccess(List<GetReleaseTypeBean> bean);
    void onFailed(int code,String msg);
}
