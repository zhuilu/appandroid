package com.xinniu.android.qiqueqiao.request;

import java.io.IOException;

/**
 * Created by lzq on 2017/12/18.
 */

public class ResultException extends IOException{
    public int code;
    public String msg;
    public String data;
    public ResultException(int code,String msg){
        this.code = code;
        this.msg = msg;
    }
    public ResultException(int code,String msg,String data){
        this.code = code;
        this.msg = msg;
        this.data = data;
    }
}
