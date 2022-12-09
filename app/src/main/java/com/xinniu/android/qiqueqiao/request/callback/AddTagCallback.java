package com.xinniu.android.qiqueqiao.request.callback;

import com.xinniu.android.qiqueqiao.bean.SeclectCateBean;
import com.xinniu.android.qiqueqiao.bean.SelectCategory;

/**
 * Created by lzq on 2018/1/11.
 */

public interface AddTagCallback {
    void onSuccess(SeclectCateBean.UserCategoryBean item);
    void onFailed(int code,String msg);
}
