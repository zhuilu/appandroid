package com.xinniu.android.qiqueqiao.request.callback;

import com.xinniu.android.qiqueqiao.adapter.IndexNewAdapter;
import com.xinniu.android.qiqueqiao.bean.IndexNewBean;
import com.xinniu.android.qiqueqiao.bean.OtherUserInfo;

import java.util.List;

/**
 * Created by yuchance on 2018/8/30.
 */

public interface GetRecommendCallback {
    void onSuccess(List<IndexNewBean.ListBean> bean);
    void onFailed(int code,String msg);

}
