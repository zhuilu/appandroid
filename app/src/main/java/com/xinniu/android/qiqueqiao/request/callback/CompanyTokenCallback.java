package com.xinniu.android.qiqueqiao.request.callback;

import com.xinniu.android.qiqueqiao.bean.CityTokenBean;
import com.xinniu.android.qiqueqiao.bean.CompanyListsBean;
import com.xinniu.android.qiqueqiao.request.converter.ResultDO;

import java.util.List;

/**
 * Created by yuchance on 2018/5/15.
 */

public interface CompanyTokenCallback {
    void onSuccess(ResultDO<CityTokenBean> bean);
    void onFailue(int code,String msg);
}
