package com.xinniu.android.qiqueqiao.request.callback;

import com.xinniu.android.qiqueqiao.bean.LLSimpleTextBean;
import com.xinniu.android.qiqueqiao.bean.TopCardBean;

import java.util.List;

/**
 * Created by BDXK on 2019/3/12.
 * project : newbridge--- android xs
 */

public interface GetCommlySentenceCallback {
    void onSuccess(List<LLSimpleTextBean> list);
    void onFailed(int code,String msg);
}
