package com.xinniu.android.qiqueqiao.request.callback;

import com.xinniu.android.qiqueqiao.bean.SecretPhoneBean;
import com.xinniu.android.qiqueqiao.bean.SimilarGroupBean;

import java.util.List;

/**
 * Created by yuchance on 2018/10/10.
 */

public interface SimilarGroupCallback {
    void onSuccess(List<SimilarGroupBean> bean);
    void onFailue(int code,String msg);
}
