package com.xinniu.android.qiqueqiao.base;

import android.app.Activity;
import android.app.Application;
import android.app.Notification;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.alibaba.security.rp.RPSDK;
import com.alivc.player.AliVcMediaPlayer;
import com.alivc.player.VcPlayerLog;
import com.umeng.analytics.MobclickAgent;
import com.umeng.commonsdk.UMConfigure;
import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.UMShareAPI;
import com.xinniu.android.qiqueqiao.DaoMaster;
import com.xinniu.android.qiqueqiao.DaoSession;
import com.xinniu.android.qiqueqiao.MainActivity;
import com.xinniu.android.qiqueqiao.R;
import com.xinniu.android.qiqueqiao.RedPointHelper;
import com.xinniu.android.qiqueqiao.activity.AddPictruActivity;
import com.xinniu.android.qiqueqiao.activity.ChatActivity;
import com.xinniu.android.qiqueqiao.activity.LoginNewActivity;
import com.xinniu.android.qiqueqiao.activity.TopCardActivity;
import com.xinniu.android.qiqueqiao.bean.GroupBean;
import com.xinniu.android.qiqueqiao.bean.OtherUserInfo;
import com.xinniu.android.qiqueqiao.customs.qldialog.QLTipDialog;
import com.xinniu.android.qiqueqiao.request.RequestManager;
import com.xinniu.android.qiqueqiao.request.callback.GetOtherUserInfoCallback;
import com.xinniu.android.qiqueqiao.request.callback.GroupInfoCallback;
import com.xinniu.android.qiqueqiao.user.UserInfoHelper;
//import com.xinniu.android.qiqueqiao.utils.IMUtils;
import com.xinniu.android.qiqueqiao.utils.ImageLoader;
import com.xinniu.android.qiqueqiao.utils.Utils;
import com.xinniu.android.qiqueqiao.utils.YzmHelper;
import com.xinniu.android.qiqueqiao.utils.db.MySQLiteOpenHelper;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import cn.com.chinatelecom.account.api.CtAuth;
import cn.jpush.android.api.BasicPushNotificationBuilder;
import cn.jpush.android.api.JPushInterface;
import io.rong.imkit.RongIM;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.Conversation;
import io.rong.imlib.model.Message;
import io.rong.imlib.model.MessageContent;
import io.rong.message.InformationNotificationMessage;



/**
 * Created by BDXK on 2017/11/7.
 */

public class BaseApp extends Application {

    public static Context context;
    private static BaseApp instance;
    public static Set<Activity> allActivities;
    //    public static boolean SKIP_WELCOME;
    private DaoSession daoSession;
    private static BaseApp application;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        application = this;
        instance = this;
        JPushInterface.setDebugMode(true);//推送环境
        JPushInterface.init(this);//推送init
        RPSDK.initialize(this);//人脸认证


        UMConfigure.init(this, UMConfigure.DEVICE_TYPE_PHONE, "");//友盟统计初始化
        /**
         * 阿里云播放器初始化
         */
        VcPlayerLog.enableLog();
        //初始化播放器
        AliVcMediaPlayer.init(getApplicationContext());
        CtAuth.getInstance().init(context, "8023528514", "THakC4qU5j1RZ5tXxJ0qv4raMk9GRbPQ", null);
        /**
         * 子进程是否支持自定义事件统计。
         * 参数：boolean 默认不使用
         */
        UMConfigure.setProcessEvent(true);
        MobclickAgent.setScenarioType(this, MobclickAgent.EScenarioType.E_UM_NORMAL);
        initGreenDao();

//        if ("com.xinniu.android.qiqueqiao".contentEquals(getCurProcessName(this))) {
//            InitConfig config=new InitConfig.Builder().setImgAdapter(new ImageAdapter()).build();
//            WXSDKEngine.initialize(this,config);
        RedPointHelper.init(this);
        ImageLoader.init(this);
        Utils.init(this);
        IMUtils.init(this);
        UMShareAPI.get(this);
        YzmHelper.init(this);
        PlatformConfig.setWeixin("wx430262a62d31dff9", "323df1385a2e217d6d15f7fd75902738");
        PlatformConfig.setSinaWeibo("221908820", "19f6a0fd59edb4f3ae4bc7b8671f5adc", "http://www.qiqueqiao.com/find_act.html");
        PlatformConfig.setQQZone("1106451375", "yAMafE9fAWLnIiuO");
        PlatformConfig.setDing("dingoaxhuwk2wfnqqqruyt");


        //      }
    }

    private void initGreenDao() {
//        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this,"userinfo.db");
        MySQLiteOpenHelper helper = new MySQLiteOpenHelper(this, "userinfo.db");
        SQLiteDatabase db = helper.getWritableDatabase();
        DaoMaster daoMaster = new DaoMaster(db);
        daoSession = daoMaster.newSession();
    }

    public static BaseApp getApplication() {
        return application;
    }

    public void addActivitySet(Activity activity) {
        if (allActivities == null) {
            allActivities = new HashSet<>();
        }
        allActivities.add(activity);
    }

    public void removeActivity(Activity activity) {
        allActivities.remove(activity);
    }


    public void Logout() {

        Intent intent = new Intent(context, LoginNewActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        UserInfoHelper.getIntance().logout();
        JPushInterface.stopPush(this);
        JPushInterface.cleanTags(this, 0);
        JPushInterface.deleteAlias(this, 0);
        IMUtils.rongLogout();
    }

    public static BaseApp getInstance() {
        return instance;
    }


    public DaoSession getDaoSession() {
        return daoSession;
    }

}
