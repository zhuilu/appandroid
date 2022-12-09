package com.xinniu.android.qiqueqiao.utils;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.xinniu.android.qiqueqiao.bean.YzmBean;
import com.xinniu.android.qiqueqiao.request.RequestManager;
import com.xinniu.android.qiqueqiao.request.ResponseCode;
import com.xinniu.android.qiqueqiao.request.RetrofitHelper;
import com.xinniu.android.qiqueqiao.request.callback.YzmCallback;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import io.rong.imlib.MD5;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by lzq on 2017/12/12.
 */

public class YzmHelper {
    private static YzmHelper yzmHelper;
    private HashMap<Integer,Integer> yzmMap;
    private static Context mContext;
    public static String ACTION_TYPE_REGISTER = "com.xinniu.adnroid.qiqueqiao.yzm.broadcast.register";
    public static String ACTION_TYPE_FORGET_PWD = "com.xinniu.adnroid.qiqueqiao.yzm.broadcast.forget.pwd";
    public static String ACTION_TYPE_CHANGE_PHONE = "com.xinniu.adnroid.qiqueqiao.yzm.broadcast.change.phone";
    public static String ACTION_TYPE_LOGIN = "com.xinniu.adnroid.qiqueqiao.yzm.broadcast.login";

    public final static int MAX_MINUTE = 60;
//    private static String ACTION_TYPE_REGISTER = "com.xinniu.adnroid.qiqueqiao.yzm.broadcast.register";

    private YzmHelper(){
        yzmMap = new HashMap<>();
    }
    public static void init(Context context){
        mContext = context;
    }
    public static YzmHelper getInstance(){
        if (yzmHelper == null){
            yzmHelper = new YzmHelper();
        }
        return yzmHelper;
    }

    public boolean isNeedCoundown(int type){
        if (!yzmMap.containsKey(type)){
            return false;
        }
        int integer = yzmMap.get(type).intValue();
        if(integer>0 && integer<MAX_MINUTE){
            return true;
        }
        return false;
    }

    public void startCountDown(final int type, String phone){
        int realType = 1;
        if (type == 2){
            realType = 2;
        }
        if (type == 3){
            realType = 3;
        }
        if (type == 4){
            realType = 4;
        }
        if (yzmMap.containsKey(type)){
            return;
        }
        String[] signArray = phone.split("");
        Arrays.sort(signArray);
        StringBuffer sign = new StringBuffer();
        for (int i = 0; i < signArray.length; i++) {
            sign.append(signArray[i]);
        }
        sign.append("QiQueqiao2018aySo08pks1k");
        Log.d("===YzmHelper", sign.toString());
        String signx = MD5.encrypt(sign.toString(),true);
        Log.d("===YzmHelper", signx);
        RequestManager.getInstance().getYzm(phone, realType,signx, new YzmCallback() {
            @Override
            public void onSuccess(YzmBean msg) {
                String message = msg.msg;
                if (!yzmMap.containsKey(type)){
                    yzmMap.put(type,MAX_MINUTE);
                }
                new Thread(new CountDown(type,MAX_MINUTE)).start();
                ToastUtils.showCentetToast(mContext,message);
            }

            @Override
            public void onFailed(String error,int code) {
                ToastUtils.showCentetToast(mContext,error);

            }
        });
    }



    public void reCountDown(int type){
        if (yzmMap.containsKey(type)){
            yzmMap.put(type,MAX_MINUTE);
            sendCountDown(type,MAX_MINUTE);
        }
    }

    class CountDown implements Runnable{
        private Integer integer;
        private int type;
        private CountDown(int type,Integer integer){
            this.integer = yzmMap.get(type);
            this.type = type;
        }
        @Override
        public void run() {
            while (true){
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                integer--;
                sendCountDown(type,integer);
                if (integer.equals(0)){
                    yzmMap.remove(type);
                    break;
                }
            }

        }
    }

    private void sendCountDown(int type,Integer integer){
        Intent intent = new Intent();
        if (type == 1){
            intent.setAction(ACTION_TYPE_REGISTER);
        }
        if (type == 2) {
            intent.setAction(ACTION_TYPE_FORGET_PWD);
        }
        if (type == 3){
            intent.setAction(ACTION_TYPE_LOGIN);
        }
        if (type == 4){
            intent.setAction(ACTION_TYPE_CHANGE_PHONE);
        }
        intent.putExtra("countdown",integer);
        mContext.sendBroadcast(intent);
    }

//    public void makeTVstartCountdown(TextView getYzmTv,int i){
//        if (getYzmTv ==null){
//            return;
//        }
////        int i = intent.getIntExtra("countdown",0);
//        if (i >= 60 || i <= 0){
//            getYzmTv.setText("获取验证码");
//            getYzmTv.setClickable(true);
//        }
//        getYzmTv.setClickable(false);
//        getYzmTv.setText(""+i);
//    }

}
