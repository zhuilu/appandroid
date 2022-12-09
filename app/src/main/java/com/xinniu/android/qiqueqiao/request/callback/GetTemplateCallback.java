package com.xinniu.android.qiqueqiao.request.callback;

import com.xinniu.android.qiqueqiao.bean.AddGroupClassifyBean;
import com.xinniu.android.qiqueqiao.bean.ReleaseTemplateBean;

import java.util.List;

/**
 * Created by yuchance on 2018/9/27.
 */

public interface GetTemplateCallback {
    void onSuccess(ReleaseTemplateBean item);
    void onFailed(int code, String msg);
}
