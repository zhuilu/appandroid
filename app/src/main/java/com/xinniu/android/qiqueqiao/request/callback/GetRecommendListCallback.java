package com.xinniu.android.qiqueqiao.request.callback;

import com.xinniu.android.qiqueqiao.bean.IndexNewBean;
import com.xinniu.android.qiqueqiao.bean.RecommendBean;

import java.util.List;

/**
 * Created by yuchance on 2019/2/13.
 */

public interface GetRecommendListCallback {
    void onSuccess(List<RecommendBean> bean);
    void onFailed(int code,String msg);
}
