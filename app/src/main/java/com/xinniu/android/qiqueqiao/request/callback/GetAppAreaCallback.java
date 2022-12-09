package com.xinniu.android.qiqueqiao.request.callback;

import com.xinniu.android.qiqueqiao.bean.CityV2Bean;
import com.xinniu.android.qiqueqiao.bean.SeclectCateBean;

import java.util.List;

/**
 * Created by yuchance on 2018/8/2.
 */

public interface GetAppAreaCallback {
    void onSuccess(List<CityV2Bean> list);
    void onFailed(int code,String msg);
}
