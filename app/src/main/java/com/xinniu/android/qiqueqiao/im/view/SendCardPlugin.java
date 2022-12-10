//package com.xinniu.android.qiqueqiao.im.view;
//
//import android.content.Context;
//import android.content.Intent;
//import android.graphics.drawable.Drawable;
//
//import androidx.core.content.ContextCompat;
//import androidx.fragment.app.Fragment;
//
//import com.xinniu.android.qiqueqiao.R;
//import com.xinniu.android.qiqueqiao.activity.FriendLxActivity;
//
//import io.rong.imkit.RongExtension;
//import io.rong.imkit.plugin.IPluginModule;
//
///**
// * Created by yuchance on 2018/11/30.
// */
//
//public class SendCardPlugin implements IPluginModule {
//
//    private Context context;
//
//    @Override
//    public Drawable obtainDrawable(Context context) {
//        return ContextCompat.getDrawable(context,R.mipmap.send_card);
//    }
//
//    @Override
//    public String obtainTitle(Context context) {
//        return context.getString(R.string.send_card);
//    }
//
//    @Override
//    public void onClick(Fragment fragment, RongExtension rongExtension) {
//        context = fragment.getActivity().getApplicationContext();
//        String targetId = rongExtension.getTargetId();
//        FriendLxActivity.start(context,FriendLxActivity.CHATACT,targetId);
//
//    }
//
//    @Override
//    public void onActivityResult(int i, int i1, Intent intent) {
//
//    }
//}
