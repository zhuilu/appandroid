package com.xinniu.android.qiqueqiao.request;

/**
 * Created by BDXK on 2017/11/24.
 * project : qiqueqiao--- android xs
 */

public class ResponseCode {
    public static final int OK = 200;// 	成功
    public static final int NULL_PARAMS  = 201;// 	参数为空
    public static final int ERROR  = 202;//报错
    public static final int NO_REGISTER  = 202;//账号未注册
    public static final int TOKEN_OUT_TIME  = 300;// 	用户token验证失败或者过期

    public static final int NO_NETWORK_CODE = -1;
    public static final int NO_LIMIT_CODE = 301; //301套餐不足或者权限不足

    public static final int NO_USER_CODE = 309; //用户呗禁用
}
