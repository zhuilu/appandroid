package com.xinniu.android.qiqueqiao.request.callback;

import com.xinniu.android.qiqueqiao.bean.MyGroupListBean;
import com.xinniu.android.qiqueqiao.bean.SelectCategory;

import java.util.List;

/**
 * Created by yuchance on 2018/10/9.
 */

public interface MyGroupListCallback {
    void onSuccess(List<MyGroupListBean> list);
    void onFailed(int code,String msg);
}
