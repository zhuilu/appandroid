package com.xinniu.android.qiqueqiao.request.callback;

import com.xinniu.android.qiqueqiao.bean.CityTokenBean;
import com.xinniu.android.qiqueqiao.bean.CompanyNameBean;
import com.xinniu.android.qiqueqiao.request.converter.ResultDO;

import java.util.List;

/**
 * Created by yuchance on 2018/5/15.
 */

public interface CompanyNameCallback {

    void onSuccess(ResultDO<List<CompanyNameBean.ResultBean>> bean);
    void onFailue(int code,String msg);
}
