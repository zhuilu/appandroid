package com.xinniu.android.qiqueqiao.request.callback;

import com.xinniu.android.qiqueqiao.bean.CircleInfobean;
import com.xinniu.android.qiqueqiao.bean.NoticeBean;

import java.util.List;

/**
 * Created by lzq on 2018/2/1.
 */

public interface GetNoticeListCallback {
    void onSuccess(List<NoticeBean> list);
    void onFailed(int code,String msg);
}
