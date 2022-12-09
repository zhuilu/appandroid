package com.xinniu.android.qiqueqiao.request.callback;

import com.xinniu.android.qiqueqiao.bean.HotResourceBean;
import com.xinniu.android.qiqueqiao.bean.MemberInfoBean;

import java.util.List;

/**
 * Created by yuchance on 2018/12/27.
 */

public interface GetHotResourceCallback {
    void onSuccess(List<HotResourceBean> list);
    void onFailed(int code,String msg);
}
