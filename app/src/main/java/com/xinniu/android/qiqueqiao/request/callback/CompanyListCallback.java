package com.xinniu.android.qiqueqiao.request.callback;

import com.xinniu.android.qiqueqiao.bean.CompanyListsBean;

import java.util.List;


/**
 * Created by yuchance on 2018/5/10.
 */

public interface CompanyListCallback {

    void onSuccess(CompanyListsBean bean);
    void onFailue(int code,String msg);

}
