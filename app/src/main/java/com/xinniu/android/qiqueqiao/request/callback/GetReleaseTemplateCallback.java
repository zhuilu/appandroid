package com.xinniu.android.qiqueqiao.request.callback;

import com.xinniu.android.qiqueqiao.bean.GetReleaseTemplateBean;
import com.xinniu.android.qiqueqiao.bean.GetReleaseTemplateNewBean;
import com.xinniu.android.qiqueqiao.bean.GetReleaseTypeBean;

import java.util.List;

/**
 * Created by yuchance on 2018/12/13.
 */

public interface GetReleaseTemplateCallback {
    void onSuccess(GetReleaseTemplateNewBean bean);
    void onFailed(int code,String msg);
}
