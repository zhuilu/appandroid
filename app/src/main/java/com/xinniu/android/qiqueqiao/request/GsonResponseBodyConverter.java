package com.xinniu.android.qiqueqiao.request;

import android.content.Intent;

import com.google.gson.Gson;
import com.xinniu.android.qiqueqiao.base.BaseApp;
import com.xinniu.android.qiqueqiao.common.WindowDialogService;
import com.xinniu.android.qiqueqiao.common.WindowDialogTwoService;
import com.xinniu.android.qiqueqiao.request.converter.ResultDO;
import com.xinniu.android.qiqueqiao.utils.Logger;

import java.io.IOException;
import java.lang.reflect.Type;

import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Response;

/**
 * Created by lzq on 2017/12/18.
 */

public class GsonResponseBodyConverter<T> implements Converter<ResponseBody,T> {
    private final Gson gson;
    private final Type type;


    public GsonResponseBodyConverter(Gson gson,Type type){
        this.gson = gson;
        this.type = type;
    }
    @Override
    public T convert(ResponseBody value) throws IOException {

        String response = value.string();
        //先将返回的json数据解析到Response中，如果code==200，则解析到我们的实体基类中，否则抛异常
        ResultDO httpResult = gson.fromJson(response, ResultDO.class);
        Logger.i("lzq","httpResult.getCode : "+httpResult.getCode() + httpResult.getData());
        if (httpResult.getCode()==ResponseCode.OK){
            //200的时候就直接解析，不可能出现解析异常。因为我们实体基类中传入的泛型，就是数据成功时候的格式
            return gson.fromJson(response,type);
        }else {
            ResultDO errorResponse = gson.fromJson(response,ResultDO.class);

            if (httpResult.getCode() == ResponseCode.TOKEN_OUT_TIME){
                if (httpResult.getMsg() != null){
                    WindowDialogService.DIALOG_MSG = httpResult.getMsg();
                }
                BaseApp.context.startService(new Intent(BaseApp.context, WindowDialogService.class));
            }else if(httpResult.getCode() == ResponseCode.NO_USER_CODE){
                if (httpResult.getMsg() != null){
                    WindowDialogTwoService.DIALOG_MSG = httpResult.getMsg();
                }
                BaseApp.context.startService(new Intent(BaseApp.context, WindowDialogTwoService.class));
            }
            //抛一个自定义ResultException 传入失败时候的状态码，和信息
            throw new ResultException(errorResponse.getCode(),errorResponse.getMsg(),response);
        }
    }
}


