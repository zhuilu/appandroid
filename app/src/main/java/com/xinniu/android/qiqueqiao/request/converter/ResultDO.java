package com.xinniu.android.qiqueqiao.request.converter;

import java.io.Serializable;

/**
 * Created by Administrator on 2015/6/23.
 */
public class ResultDO<T> implements Serializable {

    private static final long serialVersionUID = -1467576157657126613L;

    private int code;
    private String msg;
    private T data;

    public int getCode() {
        return code;
    }
    public void setCode(int code) {
        this.code = code;
    }
    public String getMsg() {
        return msg;
    }
    public void setMsg(String msg) {
        this.msg = msg;
    }
    public T getData() {
        return data;
    }
    public void setData(T data) {
        this.data = data;
    }



}
