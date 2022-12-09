package com.xinniu.android.qiqueqiao.request.callback;

import com.xinniu.android.qiqueqiao.bean.CompanyUsersBean;
import com.xinniu.android.qiqueqiao.bean.MemberInfoBean;

import java.util.List;

/**
 * Created by yuchance on 2018/5/11.
 */

public interface CompanyUsersCallback {

    void onSuccess(List<MemberInfoBean> bean);
    void onFailue(int code,String msg);
}
