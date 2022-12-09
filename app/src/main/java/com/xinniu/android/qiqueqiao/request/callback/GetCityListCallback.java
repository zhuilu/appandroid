package com.xinniu.android.qiqueqiao.request.callback;

import com.xinniu.android.qiqueqiao.bean.CityListBean;

/**
 * Created by BDXK on 2017/12/12.
 * project : xiqueqiao--- android xs
 */

public interface GetCityListCallback {
    void onSuccess(CityListBean cityListBean);
    void onFailed(int code,String msg);
}
