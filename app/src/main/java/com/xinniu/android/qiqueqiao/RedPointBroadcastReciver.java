package com.xinniu.android.qiqueqiao;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by lzq on 2017/12/27.
 */

public class RedPointBroadcastReciver extends BroadcastReceiver{
    RedPointListner listner;

    public RedPointBroadcastReciver(RedPointListner listner) {
        this.listner = listner;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        int data = intent.getIntExtra(RedPointHelper.SHOW_TYPE,-1);
        int type = intent.getIntExtra(RedPointHelper.MSG_TYPE,-1);
        int count = intent.getIntExtra(RedPointHelper.MSG_COUNT,-1);
        if (data == RedPointHelper.SHOW_RED){
            if (listner != null){
                listner.onReceive(type,true,count);
            }
        }
        if (data == RedPointHelper.UN_SHOW_RED){
            if (listner != null){
                listner.onReceive(type,false,count);
            }
        }
    }
    interface RedPointListner{
        void onReceive(int type,boolean isShow,int count);
    }
}
