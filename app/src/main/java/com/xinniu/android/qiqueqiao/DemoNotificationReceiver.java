package com.xinniu.android.qiqueqiao;

import android.content.Context;
import android.content.Intent;

import com.xinniu.android.qiqueqiao.activity.ConversationActivity;
import com.xinniu.android.qiqueqiao.bean.GroupBean;
import com.xinniu.android.qiqueqiao.request.RequestManager;
import com.xinniu.android.qiqueqiao.request.callback.GroupInfoCallback;

import io.rong.push.PushType;
import io.rong.push.RongPushClient;
import io.rong.push.notification.PushMessageReceiver;
import io.rong.push.notification.PushNotificationMessage;

/**
 * Created by lzq on 2017/12/26.
 */

public class DemoNotificationReceiver extends PushMessageReceiver {

    @Override
    public boolean onNotificationMessageArrived(Context context, PushType pushType, PushNotificationMessage pushNotificationMessage) {
        return false;
    }

    @Override
    public boolean onNotificationMessageClicked(final Context context, PushType pushType, PushNotificationMessage message) {
        if (message.getConversationType() == RongPushClient.ConversationType.GROUP) {
            final String GroupId = message.getTargetId();
            RequestManager.getInstance().getCircleBasicInfo(Integer.parseInt(GroupId), new GroupInfoCallback() {
                @Override
                public void onSuccess(GroupBean bean) {
                    ConversationActivity.start(context, Long.parseLong(GroupId), bean.getName(), bean.getNum(), bean.getHead_pic(), bean.getShare_url());
                }

                @Override
                public void onFailed(int code, String msg) {

                }
            });


        } else {
            Intent intent = new Intent(context, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        }
        return true;
    }
}
