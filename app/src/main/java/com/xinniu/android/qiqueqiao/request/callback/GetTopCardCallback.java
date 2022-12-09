package com.xinniu.android.qiqueqiao.request.callback;

import com.xinniu.android.qiqueqiao.bean.SelectCategory;
import com.xinniu.android.qiqueqiao.bean.TopCardBean;

import java.util.List;

/**
 * Created by yuchance on 2019/2/18.
 */

public interface GetTopCardCallback {
    void onSuccess(List<TopCardBean> list);
    void onFailed(int code,String msg);
}
