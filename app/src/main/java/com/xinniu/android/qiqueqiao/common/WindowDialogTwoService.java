package com.xinniu.android.qiqueqiao.common;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.text.TextUtils;

import com.xinniu.android.qiqueqiao.base.NetInfoActivity;
import com.xinniu.android.qiqueqiao.base.ProhibitInfoActivity;
import com.xinniu.android.qiqueqiao.user.UserInfoHelper;

public class WindowDialogTwoService extends Service {
    private static final String TAG = "WindowDialogTwoService";
    public static  String DIALOG_MSG = "";

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

            Intent pending = new Intent();
            pending.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            pending.setClass(getApplicationContext(),ProhibitInfoActivity.class);
            startActivity(pending);

        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
