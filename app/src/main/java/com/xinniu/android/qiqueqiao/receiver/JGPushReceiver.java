package com.xinniu.android.qiqueqiao.receiver;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
//import android.support.v4.app.NotificationCompat;
import android.text.TextUtils;
import android.util.Log;

import com.xinniu.android.qiqueqiao.MainActivity;
import com.xinniu.android.qiqueqiao.R;
import com.xinniu.android.qiqueqiao.base.BaseApp;
import com.xinniu.android.qiqueqiao.bean.NewsV2Bean;
import com.xinniu.android.qiqueqiao.fragment.message.QQQConversationListFragment;
import com.xinniu.android.qiqueqiao.request.RequestManager;
import com.xinniu.android.qiqueqiao.request.callback.GetNewsV2Callback;
import com.xinniu.android.qiqueqiao.utils.ComUtils;
import com.xinniu.android.qiqueqiao.utils.Logger;

import org.json.JSONException;
import org.json.JSONObject;


import java.util.Iterator;

import cn.jpush.android.api.BasicPushNotificationBuilder;
import cn.jpush.android.api.JPushInterface;

import static com.xinniu.android.qiqueqiao.MainActivity.BROADCAST_ACTION;


public class JGPushReceiver extends BroadcastReceiver {
    private static final String TAG = "JGPushReceiver";

    private NotificationManager nm;

    public static String SYSTEMNUM = "systemnum";
    public static String INTACTNUM = "intactnum";



    @Override
    public void onReceive(final Context context, final Intent intent) {
        Logger.i(TAG, "收到通知");
        if (null == nm) {
            nm = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        }

        Bundle bundle = intent.getExtras();
        Logger.i(TAG, "onReceive - " + intent.getAction() + ", extras: " + printBundle(bundle));

        if (JPushInterface.ACTION_REGISTRATION_ID.equals(intent.getAction())) {
            String regId = bundle.getString(JPushInterface.EXTRA_REGISTRATION_ID);
             Logger.i(TAG, "[JGPushReceiver] 接收Registration Id : " + regId);
            //send the Registration Id to your server...

        } else if (JPushInterface.ACTION_MESSAGE_RECEIVED.equals(intent.getAction())) {
             Logger.i(TAG, "[JGPushReceiver] 接收到推送下来的自定义消息: " + bundle.getString(JPushInterface.EXTRA_MESSAGE));
            //可以发送广播

        } else if (JPushInterface.ACTION_NOTIFICATION_RECEIVED.equals(intent.getAction())) {
             Logger.i(TAG, "[JGPushReceiver] 接收到推送下来的通知");
            int notifactionId = bundle.getInt(JPushInterface.EXTRA_NOTIFICATION_ID);
             Logger.i(TAG, "[JGPushReceiver] 接收到推送下来的通知的ID: " + notifactionId);
            RequestManager.getInstance().getNewsV2(new GetNewsV2Callback() {
                @Override
                public void onSuccess(NewsV2Bean bean) {
                    Intent intent1 = new Intent("com.xinniu.android.qiqueqiao.abc");
                    intent1.putExtra(SYSTEMNUM,""+bean.getSystem().getNum());
                    intent1.putExtra(INTACTNUM,""+bean.getInteractive().getNum());
                    context.sendBroadcast(intent1);
                }

                @Override
                public void onFailed(int code, String msg) {

                }
            });
//            receivingNotification(context,bundle);
         //   showNotice(BaseApp.context,bundle.getString(JPushInterface.EXTRA_MESSAGE));

        } else if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent.getAction())) {
             Logger.i(TAG, "用户点击打开了通知");


            String extras = bundle.getString(JPushInterface.EXTRA_EXTRA);
            Log.d(TAG+"abc", extras);
            try {
                JSONObject object = new JSONObject(extras);
                String url = object.optString("url");
                Log.d(TAG+"abc", url);
                if (!TextUtils.isEmpty(url)){
                    ComUtils.goToBannerNext(context,url,false);
                }else {
                    Intent intent1 = new Intent(context,MainActivity.class);
                    intent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent1);
                    JPushInterface.clearNotificationById(context,bundle.getInt(JPushInterface.EXTRA_NOTIFICATION_ID));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }



        } else if (JPushInterface.ACTION_RICHPUSH_CALLBACK.equals(intent.getAction())) {
             Logger.i(TAG, "[JGPushReceiver] 用户收到到RICH PUSH CALLBACK: " + bundle.getString(JPushInterface.EXTRA_EXTRA));
            //在这里根据 JPushInterface.EXTRA_EXTRA 的内容处理代码，比如打开新的Activity， 打开一个网页等..

        } else if(JPushInterface.ACTION_CONNECTION_CHANGE.equals(intent.getAction())) {
            boolean connected = intent.getBooleanExtra(JPushInterface.EXTRA_CONNECTION_CHANGE, false);
            Logger.w(TAG, "[JGPushReceiver]" + intent.getAction() +" connected state change to "+connected);
        } else {
             Logger.i(TAG, "[JGPushReceiver] Unhandled intent - " + intent.getAction());
        }
    }

   private void receivingNotification(Context context, Bundle bundle){
        String title = bundle.getString(JPushInterface.EXTRA_NOTIFICATION_TITLE);
        Logger.i(TAG, " [JGPushReceiver] 接收到推送下来的通知的title : " + title);
        String message = bundle.getString(JPushInterface.EXTRA_ALERT);
        Logger.i(TAG, "[JGPushReceiver] 接收到推送下来的通知的message : " + message);
        String extras = bundle.getString(JPushInterface.EXTRA_EXTRA);
        Logger.i(TAG, "[JGPushReceiver] 接收到推送下来的通知的extras : " + extras);
    }


    // 打印所有的 intent extra 数据
    private static String printBundle(Bundle bundle) {
        StringBuilder sb = new StringBuilder();
        for (String key : bundle.keySet()) {
            if (key.equals(JPushInterface.EXTRA_NOTIFICATION_ID)) {
                sb.append("\nkey:" + key + ", value:" + bundle.getInt(key));
            }else if(key.equals(JPushInterface.EXTRA_CONNECTION_CHANGE)){
                sb.append("\nkey:" + key + ", value:" + bundle.getBoolean(key));
            } else if (key.equals(JPushInterface.EXTRA_EXTRA)) {
                if (TextUtils.isEmpty(bundle.getString(JPushInterface.EXTRA_EXTRA))) {
                    Logger.i(TAG, "This message has no Extra data");
                    continue;
                }

                try {
                    JSONObject json = new JSONObject(bundle.getString(JPushInterface.EXTRA_EXTRA));
                    Iterator<String> it =  json.keys();

                    while (it.hasNext()) {
                        String myKey = it.next();
                        sb.append("\nkey:" + key + ", value: [" +
                                myKey + " - " +json.optString(myKey) + "]");
                    }
                } catch (JSONException e) {
                    Logger.e(TAG, "Get message extra JSON error!");
                }

            } else {
                sb.append("\nkey:" + key + ", value:" + bundle.getString(key));
            }
        }
        return sb.toString();
    }

    private void showNotice(Context context,String content){
               NotificationManager mNotificationManager;
        mNotificationManager = (NotificationManager) context.getSystemService(context.NOTIFICATION_SERVICE);
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context);
                        String  packageName = context.getApplicationInfo().packageName;
                        //Intent intent = new Intent(context,DirectionActivity.class);
                        //PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
                        mBuilder.setContentTitle("企鹊桥")//设置通知栏标题
                                .setContentText(content)
                                .setTicker("企鹊桥") //通知首次出现在通知栏，带上升动画效果的
                                .setWhen(System.currentTimeMillis())//通知产生的时间，会在通知信息里显示，一般是系统获取到的时间
                                .setPriority(Notification.PRIORITY_DEFAULT) //设置该通知优先级
                                .setAutoCancel(true)//设置这个标志当用户单击面板就可以让通知将自动取消
                                .setOngoing(false)//ture，设置他为一个正在进行的通知。他们通常是用来表示一个后台任务,用户积极参与(如播放音乐)或以某种方式正在等待,因此占用设备(如一个文件下载,同步操作,主动网络连接)
                                .setDefaults(Notification.DEFAULT_ALL)//向通知添加声音、闪灯和振动效果的最简单、最一致的方式是使用当前的用户默认设置，使用defaults属性，可以组合
                                //Notification.DEFAULT_ALL  Notification.DEFAULT_SOUND  DEFAULT_VIBRATE 添加声音 // requires VIBRATE permission
                                .setSmallIcon(R.mipmap.ic_launcher);//设置通知小ICON
                        mNotificationManager.notify(1,mBuilder.build());



    }





}