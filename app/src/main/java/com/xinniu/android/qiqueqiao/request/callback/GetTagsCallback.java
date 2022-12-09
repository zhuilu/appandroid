package com.xinniu.android.qiqueqiao.request.callback;

import com.xinniu.android.qiqueqiao.bean.CellTagsBean;
import com.xinniu.android.qiqueqiao.bean.SeenBean;

import java.util.List;

/**
 * Created by yuchance on 2018/6/26.
 */

public interface GetTagsCallback {
    void onSuccess(CellTagsBean bean);
    void onFailed(int code,String msg);

}
