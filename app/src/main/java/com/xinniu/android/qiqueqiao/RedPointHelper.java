package com.xinniu.android.qiqueqiao;

import android.content.Context;
import android.content.Intent;
import android.view.View;

//import com.xinniu.android.qiqueqiao.utils.IMUtils;
import com.xinniu.android.qiqueqiao.utils.Logger;
import com.xinniu.android.qiqueqiao.utils.YzmHelper;

import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.Conversation;

/**
 * Created by lzq on 2017/12/27.
 */

public class RedPointHelper {
    private static RedPointHelper yzmHelper;
    private static Context mContext;
    public static String ACTION_TYPE_MAIN_MSG = "com.xinniu.adnroid.qiqueqiao.yzm.broadcast.redpoint.main.msg";
    public static String ACTION_TYPE_CIRLE_MSG = "com.xinniu.adnroid.qiqueqiao.yzm.broadcast.redpoint.cirle.msg";
    public static String SHOW_TYPE = "SHOW_TYPE";
    public static String MSG_TYPE = "MSG_TYPE";
    public static String MSG_COUNT = "MSG_COUNT";
    public final static int SHOW_RED = 0;
    public final static int UN_SHOW_RED = 1;
    public final static int MSG_TYPE_MAIN = 1;
    public final static int MSG_TYPE_CIRLE = 2;


    public static void init(Context context){
        mContext = context;
    }
    public static RedPointHelper getInstance(){
        if (yzmHelper == null){
            yzmHelper = new RedPointHelper();
        }
        return yzmHelper;
    }
    public void sendMsg(int type,int showStatu,int count){
        Intent intent = new Intent();
        intent.setAction(ACTION_TYPE_MAIN_MSG);
        intent.putExtra(MSG_TYPE,type);
        intent.putExtra(SHOW_TYPE,showStatu);
        intent.putExtra(MSG_COUNT,count);
        mContext.sendBroadcast(intent);
    }

    public void sendMsgCircle(){
        //IMUtils.getUnreadCount(new RongIMClient.ResultCallback<Integer>() {
//            @Override
//            public void onSuccess(Integer integer) {
//                Logger.i("lzq","RedPointHelper : conut  "+integer);
//                if (integer<=0){
//                    RedPointHelper.getInstance().sendMsg(RedPointHelper.MSG_TYPE_CIRLE,RedPointHelper.UN_SHOW_RED,0);
//                }else{
//                    RedPointHelper.getInstance().sendMsg(RedPointHelper.MSG_TYPE_CIRLE,RedPointHelper.SHOW_RED,0);
//                }
//            }
//
//            @Override
//            public void onError(RongIMClient.ErrorCode errorCode) {
//
//            }
//        }, Conversation.ConversationType.GROUP);
    }
}
