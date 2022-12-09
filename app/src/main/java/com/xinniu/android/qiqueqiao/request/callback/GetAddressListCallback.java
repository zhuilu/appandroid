package com.xinniu.android.qiqueqiao.request.callback;

import com.xinniu.android.qiqueqiao.bean.AddressListBean;
import com.xinniu.android.qiqueqiao.bean.CityV2Bean;

import java.util.List;

/**
 * Created by yuchance on 2019/2/14.
 */

public interface GetAddressListCallback {
    void onSuccess(AddressListBean list);
    void onFailed(int code,String msg);
}
