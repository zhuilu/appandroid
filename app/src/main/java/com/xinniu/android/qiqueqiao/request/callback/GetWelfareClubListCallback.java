package com.xinniu.android.qiqueqiao.request.callback;

import com.xinniu.android.qiqueqiao.bean.ServiceCaseBean;
import com.xinniu.android.qiqueqiao.bean.WelfareAgencyBean;

import java.util.List;

/**
 * Created by yuchance on 2018/9/27.
 */

public interface GetWelfareClubListCallback {
    void onSuccess(List<WelfareAgencyBean> item);

    void onFailed(int code, String msg);
}
