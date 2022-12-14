package com.xinniu.android.qiqueqiao.activity;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
//import android.support.annotation.NonNull;
import androidx.annotation.Nullable;
//import android.support.v4.app.ActivityCompat;
//import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;

import com.xinniu.android.qiqueqiao.OtherUserInfoDao;
import com.xinniu.android.qiqueqiao.R;
import com.xinniu.android.qiqueqiao.base.BaseActivity;
import com.xinniu.android.qiqueqiao.base.BaseApp;
import com.xinniu.android.qiqueqiao.bean.FriendStatusBean;
import com.xinniu.android.qiqueqiao.bean.GoFriendApplyBean;
import com.xinniu.android.qiqueqiao.bean.OtherUserInfo;
import com.xinniu.android.qiqueqiao.bean.SecretPhoneBean;
import com.xinniu.android.qiqueqiao.customs.qldialog.CallPhoneDialog;
import com.xinniu.android.qiqueqiao.customs.qldialog.QLTipDialog;
import com.xinniu.android.qiqueqiao.customs.qldialog.QLTipTwoDialog;
//import com.xinniu.android.qiqueqiao.im.TestConversationFragment;
import com.xinniu.android.qiqueqiao.request.RequestManager;
import com.xinniu.android.qiqueqiao.request.callback.AllResultDoCallback;
import com.xinniu.android.qiqueqiao.request.callback.CommonCallback;
import com.xinniu.android.qiqueqiao.request.callback.ExchageChatInfoCallback;
import com.xinniu.android.qiqueqiao.request.callback.GetFriendStatusCallback;
import com.xinniu.android.qiqueqiao.request.callback.GetOtherUserInfoCallback;
import com.xinniu.android.qiqueqiao.request.callback.GoFriendApplyCallback;
import com.xinniu.android.qiqueqiao.request.callback.SecretPhoneCallback;
import com.xinniu.android.qiqueqiao.request.converter.ResultDO;
import com.xinniu.android.qiqueqiao.user.UserInfoHelper;
////import com.xinniu.android.qiqueqiao.utils.IMUtils;
import com.xinniu.android.qiqueqiao.utils.Logger;
import com.xinniu.android.qiqueqiao.utils.ShowUtils;
import com.xinniu.android.qiqueqiao.utils.ToastUtils;

import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.OnClick;
import io.rong.imkit.RongIM;
//import io.rong.imkit.fragment.ConversationFragment;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.Conversation;
import io.rong.imlib.model.UserInfo;
import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;

/**
 * Created by lzq on 2017/12/9.
 * ????????????
 */

public class ChatActivity extends BaseActivity implements View.OnClickListener, EasyPermissions.PermissionCallbacks {
    @BindView(R.id.tool_bar_title)
    TextView titleTv;
    @BindView(R.id.exchange_wechat)
    TextView exchageWeChatTv;
    @BindView(R.id.exchange_phone)
    TextView exchangePhone;
    @BindView(R.id.no_exchange)
    TextView noExchange;
    @BindView(R.id.no_xingqu_tip)
    RelativeLayout noXingquTip;
    @BindView(R.id.exchang_item)
    LinearLayout exchangItem;
    @BindView(R.id.edit)
    TextView editNoXingqu;

//    TestConversationFragment fragment;

    public final static String M_TARGET_ID = "M_TARGET_ID";
    public final static String M_TITLE = "M_TITLE";
    public final static String IS_QRCODE = "is_qrcode";
    public final static String FROM_TYPE = "type";
    public final static String M_TITLE_POSITION = "title_position";
    public final static String M_TITLE_HEAD_PIC = "title_headpic";
    public final static String M_IS_VIP = "is_vip";
    public final static int FROM_TYPE_RESOURCE = 1;
    public final static int TYPE_QRCODE_TURE = 1;
    public final static int TYPE_QRCODE_FALSE = 0;
    @BindView(R.id.mrequest_titletv)
    TextView mrequestTitletv;
    @BindView(R.id.yrefuseRl)
    RelativeLayout yrefuseRl;
    @BindView(R.id.maddfriend_titletv)
    TextView maddfriendTitletv;
    @BindView(R.id.yaddfriendRl)
    RelativeLayout yaddfriendRl;
    @BindView(R.id.tool_bar_positiontv)
    TextView toolBarPositiontv;
    @BindView(R.id.right_bt)
    RelativeLayout rightBt;

    private int vip = 0;
    private int qrcode = 0;
    private String title;
    public final static int EXCHAGE_WECHAT = 3;
    public final static int EXCHAGE_PHONE = 1;
    public final static int EXCHAGE_WECHAT_ASK = 3;
    public String mTargetId; //?????? Id
    private Conversation.ConversationType mConversationType; //????????????
    //    private SecretPhoneNumProvider mSecretPhoneNumProvider;
    public static String FRIEND_MESSAGE = "com.xinniu.android.qiqueqiao.addfriend";
    private AddFriendBroadCast addFriendBroadCast;
    private int applyId = 0;
    private String position;
    private String headPic;
    private String isVip;
    private String company;
    private String mp;
    private String head_pic;
    private String phoneNum;
    final OtherUserInfoDao dao = BaseApp.getApplication().getDaoSession().getOtherUserInfoDao();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onResume() {
        super.onResume();
        addFriendBroadCast = new AddFriendBroadCast();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(FRIEND_MESSAGE);
        registerReceiver(addFriendBroadCast, intentFilter);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_chat;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        topStatusBar(true);
//        IMUtils.registerMessageType(new ServiceMessageProvider());
//        IMUtils.registerMessageType(new PrivateMessageProvider());
//        IMUtils.registerMessageType(new SecretPhoneNumProvider());
//        IMUtils.registerMessageType(new ShareCardProvider());
//        IMUtils.registerMessageType(new ShareResourceProvider());
//        IMUtils.registerMessageType(new SecuredTransactionProvider());
//        IMUtils.registerMessageType(new GroupInviteProvider());
        Intent intent = getIntent();

        vip = UserInfoHelper.getIntance().getCenterBean().getUsers().getIs_vip();

        int type = intent.getIntExtra(FROM_TYPE, -1);
        if (type == -1) {
            title = intent.getData().getQueryParameter("title");
            mTargetId = intent.getData().getQueryParameter("targetId");
            if (intent.getExtras() != null) {
                if (intent.getExtras().getString(M_TITLE_POSITION) != null) {
                    position = intent.getExtras().getString(M_TITLE_POSITION);
                }
                if (intent.getExtras().getString(M_TITLE_HEAD_PIC) != null) {
                    headPic = intent.getExtras().getString(M_TITLE_HEAD_PIC);
                }
                if (intent.getExtras().getString(M_IS_VIP) != null) {
                    isVip = intent.getExtras().getString(M_IS_VIP);
                }
            }
            /* ??? intent ???????????????????????? targetId ???????????????*/
            mConversationType = Conversation.ConversationType.valueOf(intent.getData().getLastPathSegment().toUpperCase(Locale.US));
            Logger.i("lzq", "chatactivity " + intent.getData().getLastPathSegment().toUpperCase(Locale.US));
        } else {
            title = intent.getStringExtra(M_TITLE);
            position = intent.getStringExtra(M_TITLE_POSITION);
            mTargetId = intent.getStringExtra(M_TARGET_ID);
            qrcode = intent.getIntExtra(IS_QRCODE, 0);//1?????????????????? 0???
            headPic = intent.getStringExtra(M_TITLE_HEAD_PIC);
            isVip = intent.getStringExtra(M_IS_VIP);
        }
        //???????????????????????????????????????
        RequestManager.getInstance().showUserInfo(Integer.parseInt(mTargetId), new GetOtherUserInfoCallback() {
            @Override
            public void onSuccess(OtherUserInfo bean) {
                title = bean.getRealname();
                position = bean.getCompany() + bean.getPosition();
                isVip = bean.getIs_vip();
                company = bean.getCompany();
                head_pic = bean.getHead_pic();
                mp = bean.getPosition();
                String mUserId = String.valueOf(bean.getUser_id());
                String name = bean.getRealname();
                String headUrl = bean.getHead_pic();
                StringBuilder builder = new StringBuilder();
                builder.append(name);
                if (headUrl != null && builder != null && mUserId != null) {
                    RongIM.getInstance().refreshUserInfoCache(new UserInfo(mUserId, builder.toString(), Uri.parse(headUrl)));
                }
                titleTv.setText(title);
                toolBarPositiontv.setText(position);
                //???????????????????????????????????????????????????????????????????????????????????????????????????
                List<OtherUserInfo> list = dao.queryBuilder().offset(0).limit(1).orderAsc(OtherUserInfoDao.Properties.User_id)
                        .where(OtherUserInfoDao.Properties.User_id.eq(bean.getUser_id())).build().list();

                if (list.size() > 0) {
                    OtherUserInfo otherUserInfo = list.get(0);
                    otherUserInfo.setIs_corporate_vip(bean.getIs_corporate_vip());
                    //??????
                    dao.update(otherUserInfo);
                }
            }

            @Override
            public void onFailed(int code, String msg) {

            }
        });

        RongIMClient.getInstance().getBlacklistStatus(mTargetId, new RongIMClient.ResultCallback<RongIMClient.BlacklistStatus>() {
            @Override
            public void onSuccess(RongIMClient.BlacklistStatus blacklistStatus) {
                if (blacklistStatus == RongIMClient.BlacklistStatus.IN_BLACK_LIST) {
                    if (noXingquTip != null) {
                        noXingquTip.setVisibility(View.VISIBLE);
                    }
                    if (exchangItem != null) {
                        exchangItem.setVisibility(View.GONE);
                    }
                } else {
                    if (noXingquTip != null) {
                        noXingquTip.setVisibility(View.GONE);
                    }
                    if (exchangItem != null) {
                        exchangItem.setVisibility(View.VISIBLE);
                    }
                }
                if (mTargetId.contentEquals(String.valueOf(UserInfoHelper.getIntance().getCenterBean().getUsers().getF_id()))) {
                    noXingquTip.setVisibility(View.GONE);
                    //   exchangItem.setVisibility(View.GONE);
                    titleTv.setText(title);
                    toolBarPositiontv.setText(position);
                    rightBt.setVisibility(View.GONE);
                }
            }

            @Override
            public void onError(RongIMClient.ErrorCode errorCode) {

            }
        });
//        if (qrcode == TYPE_QRCODE_TURE) {
//            exchageWeChatTv.setText("?????????");
//        }
//        if (qrcode == TYPE_QRCODE_FALSE) {
//            if (vip == 1) {
//                exchageWeChatTv.setText("?????????");
//            }
//            if (vip == 0) {
//                exchageWeChatTv.setText("?????????");
//            }
//        }
        titleTv.setText(title);
        toolBarPositiontv.setText(position);

        /* ?????? ConversationFragment ??????????????? setUri() ??????????????????*/
//        fragment = new TestConversationFragment();
//        Uri uri = Uri.parse("rong://" + getApplicationInfo().packageName).buildUpon()
//                .appendPath("conversation").appendPath("PRIVATE")
//                .appendQueryParameter("targetId", mTargetId).build();
//
//        fragment.setUri(uri);
//
//
//        /* ?????? ConversationFragment */
//        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
//        transaction.add(R.id.rong_content, fragment);
//        transaction.commit();
//        buildFriendStatus();


    }

    private void buildFriendStatus() {
        if (TextUtils.isEmpty(mTargetId)) {
            return;
        }
        int targetId = Integer.parseInt(mTargetId);

        RequestManager.getInstance().getFriendStatus(targetId, new GetFriendStatusCallback() {
            @Override
            public void onSuccess(FriendStatusBean bean) {
                applyId = bean.getId();
                int status = bean.getStatus();
                if (status == 0) {
                    yrefuseRl.setVisibility(View.GONE);
                    yaddfriendRl.setVisibility(View.VISIBLE);
                } else if (status == 2) {
                    yrefuseRl.setVisibility(View.VISIBLE);
                    yaddfriendRl.setVisibility(View.GONE);
                } else {
                    yrefuseRl.setVisibility(View.GONE);
                    yaddfriendRl.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailed(int code, String msg) {

            }
        });


    }

    @OnClick({R.id.exchange_wechat, R.id.exchange_phone, R.id.no_exchange, R.id.bt_return, R.id.edit, R.id.bchat_agreetv, R.id.bchat_refusetv, R.id.bchat_addfriendtv, R.id.right_bt})
    @Override
    public void onClick(View view) {
        final int id = view.getId();
        switch (id) {
            case R.id.exchange_wechat://?????????
                // exchangeWechat();
                sendExchageInfo(EXCHAGE_WECHAT);
                break;
            case R.id.exchange_phone://????????????
                sendExchageInfo(EXCHAGE_PHONE);
                break;
            case R.id.no_exchange://??????????????????
                // SendCoopInfoActivity.start(ChatActivity.this, mTargetId, title);
                //????????????

                LaunchTransactionAvtivity.start(ChatActivity.this, mTargetId, 0);


                break;
            case R.id.bt_return:
                finish();
                break;
            case R.id.edit:
                new QLTipDialog.Builder(this)
                        .setCancelable(true)
                        .setCancelableOnTouchOutside(true)
                        .setMessage("??????????????????????????????\n" +
                                "?????????????????????TA???????????????")
                        .setPositiveButton("??????", new QLTipDialog.IPositiveCallback() {
                            @Override
                            public void onClick() {
                                blacklistBehavior(2);
                            }
                        })
                        .show(ChatActivity.this);
                break;
            case R.id.bchat_agreetv:
                goIsAgree(applyId, 1);
                break;
            case R.id.bchat_refusetv:
                goIsAgree(applyId, 2);
                break;
            case R.id.bchat_addfriendtv:
                goApplyFor();
                break;
            case R.id.right_bt:
                LxSettingActivity.start(ChatActivity.this, mTargetId, title, position, headPic, isVip);
                break;
            default:
                break;
        }

    }

    private void goApplyFor() {
        int toUserId = Integer.parseInt(mTargetId);
        RequestManager.getInstance().goFriendApply(toUserId, 1, new GoFriendApplyCallback() {
            @Override
            public void onSuccess(GoFriendApplyBean data, String msg) {
                ToastUtils.showCentetToast(ChatActivity.this, msg);
            }

            @Override
            public void onFailed(int code, String msg) {
                ToastUtils.showCentetToast(ChatActivity.this, msg);
            }
        });
    }

    private void goIsAgree(int applyId, int operating) {
        RequestManager.getInstance().friendIsAgree(applyId, operating, new AllResultDoCallback() {
            @Override
            public void onSuccess(String msg) {
                ToastUtils.showCentetToast(ChatActivity.this, msg);
                ShowUtils.showViewVisible(yrefuseRl, View.GONE);
                buildFriendStatus();
            }

            @Override
            public void onFailed(int code, String msg) {
                ToastUtils.showCentetToast(ChatActivity.this, msg);
            }
        });
    }


    private void blacklistBehavior(final int action) {
        RequestManager.getInstance().blacklistBehavior(Integer.valueOf(mTargetId), action, new CommonCallback() {
            @Override
            public void onSuccess(ResultDO resultDO) {
                if (action == 1) {
                    noXingquTip.setVisibility(View.VISIBLE);
                    exchangItem.setVisibility(View.GONE);
                }
                if (action == 2) {
                    noXingquTip.setVisibility(View.GONE);
                    exchangItem.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailed(int code, String msg) {
                ToastUtils.showCentetImgToast(ChatActivity.this, msg);
            }
        });
    }

    private void sendExchageInfo(final int info) {
        if (info == EXCHAGE_WECHAT || info == EXCHAGE_WECHAT_ASK) {
            exchangeChatInfo(info);
            return;
        }
        if (info == EXCHAGE_PHONE) {
//            new QLTipDialog.Builder(this)
//                    .setCancelable(false)
//                    .setCancelableOnTouchOutside(false)
//                    .setMessage("?????????????????????????????????")
//                    .setPositiveButton("??????", new QLTipDialog.IPositiveCallback() {
//                        @Override
//                        public void onClick() {
//                            exchangeChatInfo(info);
//                        }
//                    })
//                    .setNegativeButton("??????", new QLTipDialog.INegativeCallback() {
//                        @Override
//                        public void onClick() {
////                        dissMissDialog();
//                        }
//                    })
//                    .show();

            int toUserId = Integer.parseInt(mTargetId);
            RequestManager.getInstance().getSecretPhone(toUserId, new SecretPhoneCallback() {
                @Override
                public void onSuccess(SecretPhoneBean bean) {
                    phoneNum = bean.getMobile();
                    CallPhoneDialog callPhoneDialog = new CallPhoneDialog(ChatActivity.this, R.style.QLDialog);
                    callPhoneDialog.setmShareCallback(new CallPhoneDialog.CallPhoneCallback() {
                        @Override
                        public void onClickCall() {
                            goTocall(phoneNum);
                        }
                    });
                    callPhoneDialog.show();

                }

                @Override
                public void onFailue(int code, String msg) {
                    if (code == 301) {

                        new QLTipTwoDialog.Builder(ChatActivity.this)
                                .setCancelable(true)
                                .setCancelableOnTouchOutside(true)
                                .setTitle("???????????????VIP????????????")
                                .setMessage("??????????????????????????????????????????")
                                .setPositiveButton("?????????", new QLTipTwoDialog.IPositiveCallback() {
                                    @Override
                                    public void onClick() {

                                        Intent intent = new Intent(ChatActivity.this, VipV4ListActivity.class);
                                        startActivity(intent);
                                    }
                                })
                                .setNegativeButton("??????", new QLTipTwoDialog.INegativeCallback() {
                                    @Override
                                    public void onClick() {

                                    }
                                })
                                .show(ChatActivity.this);
                    } else {
                        ToastUtils.showToast(ChatActivity.this, msg);
                    }


                }
            });


        }
    }

//    private void exchangeWechat() {
//        if (qrcode == TYPE_QRCODE_TURE) {
//            sendExchageInfo(EXCHAGE_WECHAT_ASK);
//        }
//        if (qrcode == TYPE_QRCODE_FALSE) {
//            sendExchageInfo(EXCHAGE_WECHAT_ASK);
//            if (vip == 1 || vip == 2) {
//                sendExchageInfo(EXCHAGE_WECHAT_ASK);
//            }
//            if (vip == 0) {
//                new QLTipDialog.Builder(this)
//                        .setCancelable(true)
//                        .setCancelableOnTouchOutside(true)
//                        .setMessage("????????????VIP????????????")
//                        .setPositiveButton("????????????", new QLTipDialog.IPositiveCallback() {
//                            @Override
//                            public void onClick() {
//                                startActivity(new Intent(ChatActivity.this, VipV4ListActivity.class));
//                            }
//                        })
//                        .setNegativeButton("??????", new QLTipDialog.INegativeCallback() {
//                            @Override
//                            public void onClick() {
//                                dissMissDialog();
//                                sendExchageInfo(EXCHAGE_WECHAT);
//                            }
//                        })
//                        .show();
//            }
//        }
//    }

    private void exchangeChatInfo(final int info) {
        RequestManager.getInstance().exchangeChatInfo(info, Integer.valueOf(mTargetId), new ExchageChatInfoCallback() {
            @Override
            public void onSuccess(ResultDO exchangeInfoBean) {
                if (info == EXCHAGE_PHONE) {

                    //??????????????????
//                    int userId = UserInfoHelper.getIntance().getUserId();
//                    InformationNotificationMessage myTextMessage = InformationNotificationMessage.obtain("???????????????????????????");
//                    IMUtils.insertMessage(Conversation.ConversationType.PRIVATE, mTargetId, String.valueOf(userId), myTextMessage, new RongIMClient.ResultCallback<Message>() {
//                        @Override
//                        public void onSuccess(Message message) {
////                                    exchangePhone.setText("?????????");
//                        }
//
//                        @Override
//                        public void onError(RongIMClient.ErrorCode errorCode) {
//                        }
//                    });
                }

                if (info == EXCHAGE_WECHAT) {
//                    int userId = UserInfoHelper.getIntance().getUserId();
//                    InformationNotificationMessage myTextMessage = InformationNotificationMessage.obtain("???????????????????????????");
//                    IMUtils.insertMessage(Conversation.ConversationType.PRIVATE, mTargetId, String.valueOf(userId), myTextMessage, new RongIMClient.ResultCallback<Message>() {
//                        @Override
//                        public void onSuccess(Message message) {
////                            exchageWeChatTv.setText("?????????");
//                        }
//
//                        @Override
//                        public void onError(RongIMClient.ErrorCode errorCode) {
//                        }
//                    });
                }
            }

            @Override
            public void onFailed(int code, String msg) {
                if (code == 307 && info == EXCHAGE_WECHAT) {
                    new QLTipDialog.Builder(ChatActivity.this)
                            .setCancelable(true)
                            .setCancelableOnTouchOutside(true)
                            .setMessage(msg)
                            .setPositiveButton("????????????", new QLTipDialog.IPositiveCallback() {
                                @Override
                                public void onClick() {
                                    startActivity(new Intent(ChatActivity.this, MineInfoActivity.class));
                                }
                            })
                            .setNegativeButton("??????", new QLTipDialog.INegativeCallback() {
                                @Override
                                public void onClick() {

                                }
                            })
                            .show(ChatActivity.this);

                } else if (code == 301 && info == EXCHAGE_WECHAT) {
                    new QLTipTwoDialog.Builder(ChatActivity.this)
                            .setCancelable(true)
                            .setCancelableOnTouchOutside(true)
                            .setTitle("????????????VIP????????????")
                            .setMessage("???????????????????????????????????????????????????")
                            .setPositiveButton("?????????", new QLTipTwoDialog.IPositiveCallback() {
                                @Override
                                public void onClick() {
                                    startActivity(new Intent(ChatActivity.this, VipV4ListActivity.class));
                                }
                            })
                            .setNegativeButton("??????", new QLTipTwoDialog.INegativeCallback() {
                                @Override
                                public void onClick() {
                                    dissMissDialog();

                                }
                            })
                            .show(ChatActivity.this);
                } else {
                    ToastUtils.showCentetImgToast(ChatActivity.this, msg);
                }

            }
        });
    }


//    public static class MySendMessageListener implements RongIM.OnSendMessageListener {
//
//
//        @Override
//        public Message onSend(Message message) {
//            return message;
//        }
//
//        @Override
//        public boolean onSent(Message message, RongIM.SentMessageErrorCode sentMessageErrorCode) {
//            //??????????????????????????????????????????
//
//            if (message.getSentStatus() == Message.SentStatus.FAILED) {
//                if (sentMessageErrorCode == RongIM.SentMessageErrorCode.NOT_IN_CHATROOM) {
//                    //???????????????
//                } else if (sentMessageErrorCode == RongIM.SentMessageErrorCode.NOT_IN_DISCUSSION) {
//                    //???????????????
//                } else if (sentMessageErrorCode == RongIM.SentMessageErrorCode.NOT_IN_GROUP) {
//                    //????????????
//                } else if (sentMessageErrorCode == RongIM.SentMessageErrorCode.REJECTED_BY_BLACKLIST) {
//                    //????????????????????????
//                }
//            }
//
//            MessageContent messageContent = message.getContent();
//
//            if (messageContent instanceof TextMessage) {//????????????
//                TextMessage textMessage = (TextMessage) messageContent;
////                Log.d(TAG, "onSent-TextMessage:" + textMessage.getContent());
//                int toUserId = Integer.parseInt(message.getTargetId());
//
//                RequestManager.getInstance().sendTem(toUserId, textMessage.getContent());
//            } else if (messageContent instanceof ImageMessage) {//????????????
//                ImageMessage imageMessage = (ImageMessage) messageContent;
//                int toUserId = Integer.parseInt(message.getTargetId());
//                RequestManager.getInstance().sendTem(toUserId, "??????");
////                Log.d(TAG, "onSent-ImageMessage:" + imageMessage.getRemoteUri());
//            } else if (messageContent instanceof VoiceMessage) {//????????????
//                VoiceMessage voiceMessage = (VoiceMessage) messageContent;
//                int toUserId = Integer.parseInt(message.getTargetId());
//                RequestManager.getInstance().sendTem(toUserId, "??????");
////                Log.d(TAG, "onSent-voiceMessage:" + voiceMessage.getUri().toString());
//            } else if (messageContent instanceof RichContentMessage) {//????????????
//                RichContentMessage richContentMessage = (RichContentMessage) messageContent;
//                int toUserId = Integer.parseInt(message.getTargetId());
//                RequestManager.getInstance().sendTem(toUserId, "??????");
////                Log.d(TAG, "onSent-RichContentMessage:" + richContentMessage.getContent());
//            } else {
////                Log.d(TAG, "onSent-????????????????????????????????????");
//                int toUserId = Integer.parseInt(message.getTargetId());
//                RequestManager.getInstance().sendTem(toUserId, "??????");
//            }
//
//
//            return false;
//        }
//    }

    public class AddFriendBroadCast extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            buildFriendStatus();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (addFriendBroadCast != null) {
            unregisterReceiver(addFriendBroadCast);
        }
    }

    private void goTocall(String phoneNum) {
        if (EasyPermissions.hasPermissions(this, new String[]{Manifest.permission.CALL_PHONE})) {
            callPhone();
        } else {
//            EasyPermissions.requestPermissions(fragment.getActivity(),
//                    "????????????????????????????????????",
//                    11111, new String[]{Manifest.permission.CALL_PHONE});
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        // ???????????????????????????EasyPermissions
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {
        //??????
        callPhone();
    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {
        //?????????????????????
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            new AppSettingsDialog
                    .Builder(this)
                    .setRationale("????????????????????????????????????????????????")
                    .setPositiveButton("???")
                    .setNegativeButton("???")
                    .build()
                    .show();
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == AppSettingsDialog.DEFAULT_SETTINGS_REQ_CODE) {
            //??????????????????????????????????????????????????????APP?????????????????????
            callPhone();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void callPhone() {
        Intent intent1 = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phoneNum));
        intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
//            ToastUtils.showCentetToast(fragment.getActivity(), "?????????????????????");
            return;
        }
        startActivity(intent1);
    }

}
