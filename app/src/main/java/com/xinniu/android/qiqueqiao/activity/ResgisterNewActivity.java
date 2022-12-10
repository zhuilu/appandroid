package com.xinniu.android.qiqueqiao.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.ArraySet;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xinniu.android.qiqueqiao.MainActivity;
import com.xinniu.android.qiqueqiao.MessageInfoHelper;
import com.xinniu.android.qiqueqiao.R;
import com.xinniu.android.qiqueqiao.base.BaseActivity;
import com.xinniu.android.qiqueqiao.base.NetInfoActivity;
import com.xinniu.android.qiqueqiao.bean.DetailedUserInfoBean;
import com.xinniu.android.qiqueqiao.bean.NewsBean;
import com.xinniu.android.qiqueqiao.bean.UserInfoBean;
import com.xinniu.android.qiqueqiao.request.RequestManager;
import com.xinniu.android.qiqueqiao.request.callback.LoginCallback;
import com.xinniu.android.qiqueqiao.request.callback.NewsCallback;
import com.xinniu.android.qiqueqiao.request.callback.RequestCallback;
import com.xinniu.android.qiqueqiao.request.callback.ResultCallback;
import com.xinniu.android.qiqueqiao.user.UserInfoHelper;
import com.xinniu.android.qiqueqiao.utils.ComUtils;
//import com.xinniu.android.qiqueqiao.utils.IMUtils;
import com.xinniu.android.qiqueqiao.utils.ToastUtils;
import com.xinniu.android.qiqueqiao.utils.YzmHelper;

import java.util.HashSet;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.jpush.android.api.JPushInterface;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.Conversation;
import io.rong.imlib.model.Message;
import io.rong.message.TextMessage;
import retrofit2.Call;

/**
 * Created by yuchance on 2018/5/3.
 */

public class ResgisterNewActivity extends BaseActivity {
    @BindView(R.id.bresgist_finishImg)
    LinearLayout bresgistFinishImg;
    @BindView(R.id.mresgist_account_et)
    EditText mresgistAccountEt;
    @BindView(R.id.mresgist_yz_et)
    EditText mresgistYzEt;
    @BindView(R.id.bresgit_yzm)
    TextView bresgitYzm;
    @BindView(R.id.brsgister_login)
    TextView brsgisterLogin;
    private Call mCall;

    private BroadcastReceiver mBroadcastReceiver;

    @Override
    public int getLayoutId() {
        return R.layout.activity_resgisternew;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        topStatusBar(true);
        ComUtils.addActivity(ResgisterNewActivity.this);
        mBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (bresgitYzm ==null){
                    return;
                }
                int i = intent.getIntExtra("countdown",0);
                if (i >= YzmHelper.MAX_MINUTE || i <= 1){
                    bresgitYzm.setText("获取验证码");
                    bresgitYzm.setClickable(true);
                    return;
                }
                dismissBookingToast();
                bresgitYzm.setClickable(false);
                bresgitYzm.setText(i+"s");

            }
        };
        IntentFilter intentFilter = new IntentFilter();

        //设置接收广播的类型
        intentFilter.addAction(YzmHelper.ACTION_TYPE_LOGIN);

        //调用Context的registerReceiver（）方法进行动态注册
        registerReceiver(mBroadcastReceiver, intentFilter);

    }

    @OnClick({R.id.bresgist_finishImg, R.id.bresgit_yzm, R.id.brsgister_login})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bresgist_finishImg:
                finish();
                break;
            case R.id.bresgit_yzm:

                if (ComUtils.isPhoneheckPass(ResgisterNewActivity.this,mresgistAccountEt)) {
                    showBookingToast(2);
                    YzmHelper.getInstance().startCountDown(3, mresgistAccountEt.getText().toString().trim());
                }
                break;
            case R.id.brsgister_login:
                showBookingToast(2);
                if (ComUtils.isLoginCheckPhoneCode(ResgisterNewActivity.this,mresgistAccountEt,mresgistYzEt)){
                    RequestManager.getInstance().loginByYzm(mresgistAccountEt.getText().toString(), mresgistYzEt.getText().toString(), new LoginCallback() {
                        @Override
                        public void onSuccess(final UserInfoBean userInfoBean) {

                            RequestManager.getInstance().getUserInfo(userInfoBean.user_id,userInfoBean.token,new RequestCallback<DetailedUserInfoBean>() {
                                @Override
                                public void requestStart(Call call) {
                                    mCall = call;
                                }

                                @Override
                                public void onSuccess(DetailedUserInfoBean detailedUserInfoBean) {


                                }

                                @Override
                                public void onFailed(int code, String msg) {

                                }

                                @Override
                                public void requestEnd() {

                                }
                            });
                            int status = userInfoBean.status;
                            if (status == 0) {
//                                IMUtils.connectIM(userInfoBean.rong_token, true, new ResultCallback<String>() {
//                                    @Override
//                                    public void onSuccess(String s) {
//                                        UserInfoHelper.getIntance().setUsername(mresgistAccountEt.getText().toString());
//                                        UserInfoHelper.getIntance().setUserId(userInfoBean.user_id);
//                                        UserInfoHelper.getIntance().setToken(userInfoBean.token);
//                                        UserInfoHelper.getIntance().setRongyunToken(userInfoBean.rong_token);
//                                        UserInfoHelper.getIntance().setUsername(mresgistAccountEt.getText().toString());
//                                        if (JPushInterface.isPushStopped(getApplicationContext())){
//                                            Set<String> PushArray  = new HashSet<>();
//                                            int userId = UserInfoHelper.getIntance().getUserId();
//                                            PushArray.add(userId+"");
//                                            JPushInterface.resumePush(getApplicationContext());
//                                            JPushInterface.setAlias(getApplicationContext(),0,userId+"");
//                                            JPushInterface.setTags(getApplicationContext(),0,PushArray);
//                                        }
//                                        dismissBookingToast();
//                                        startActivity(new Intent(ResgisterNewActivity.this, MainActivity.class));
//                                        ComUtils.finishshortAll();
//
//                                    }
//
//                                    @Override
//                                    public void onFail(int errorCode) {
//                                        ToastUtils.showCentetToast(ResgisterNewActivity.this, "聊天服务器连接失败");
//                                        dismissBookingToast();
//                                    }
//                                });

                            }else if (status == 1){
                                UserInfoHelper.getIntance().setUsername(mresgistAccountEt.getText().toString());
                                CompleteInfoActivity.start(ResgisterNewActivity.this,userInfoBean.getToken(),userInfoBean.getUser_id(),userInfoBean.getRong_token());
                                finish();
                            }
                        }

                        @Override
                        public void onFailed(int code, String msg) {
                            ToastUtils.showCentetToast(ResgisterNewActivity.this,msg);
                            dismissBookingToast();
                        }
                    });


                }
                break;
            default:
                break;
        }
    }

//    private void insertMessage() {
//        RequestManager.getInstance().getNews(new NewsCallback() {
//            @Override
//            public void onSuccess(NewsBean bean) {
//                MessageInfoHelper.getIntance().setNewsBean(bean);
//                TextMessage myTextMessage = new TextMessage(bean.getContent());
//
//
//                Message myMessage = Message.obtain(SplashActivity.GM_ID, Conversation.ConversationType.PRIVATE, myTextMessage);
//
//
//                IMUtils.insertMessage(Conversation.ConversationType.PRIVATE,SplashActivity.GM_ID,SplashActivity.GM_ID,myTextMessage, new RongIMClient.ResultCallback<Message>() {
//                    @Override
//                    public void onSuccess(Message message) {
//                    }
//
//
//                    @Override
//                    public void onError(RongIMClient.ErrorCode errorCode) {
//                    }
//                });
//            }
//
//            @Override
//            public void onFailed(int code, String msg) {
//                TextMessage myTextMessage = TextMessage.obtain("企鹊桥官方");
//
//                Message myMessage = Message.obtain(SplashActivity.GM_ID, Conversation.ConversationType.PRIVATE, myTextMessage);
//
//
//                IMUtils.insertMessage(Conversation.ConversationType.PRIVATE,SplashActivity.GM_ID,SplashActivity.GM_ID,myTextMessage, new RongIMClient.ResultCallback<Message>() {
//                    @Override
//                    public void onSuccess(Message message) {
//                    }
//
//
//                    @Override
//                    public void onError(RongIMClient.ErrorCode errorCode) {
//                    }
//                });
//            }
//        });
//
//    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mBroadcastReceiver);
    }
}
