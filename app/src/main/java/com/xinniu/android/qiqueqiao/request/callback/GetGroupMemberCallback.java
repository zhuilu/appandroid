package com.xinniu.android.qiqueqiao.request.callback;

import com.xinniu.android.qiqueqiao.bean.CircleInfobean;
import com.xinniu.android.qiqueqiao.bean.MemberInfoBean;

import java.util.List;

/**
 * Created by lzq on 2018/2/1.
 */

public interface GetGroupMemberCallback {
    void onSuccess(List<MemberInfoBean> list);
    void onFailed(int code,String msg);
}
