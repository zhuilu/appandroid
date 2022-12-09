//package com.xinniu.android.qiqueqiao.utils;
//
//import android.app.Activity;
//import android.content.Context;
//import android.content.Intent;
//import android.net.Uri;
//import android.os.Bundle;
//import android.text.TextUtils;
//import android.util.Log;
//
//import com.google.gson.Gson;
//import com.xinniu.android.qiqueqiao.OtherUserInfoDao;
//import com.xinniu.android.qiqueqiao.RedPointHelper;
//import com.xinniu.android.qiqueqiao.activity.ChatActivity;
//import com.xinniu.android.qiqueqiao.base.BaseApp;
//import com.xinniu.android.qiqueqiao.base.NetInfoActivity;
//import com.xinniu.android.qiqueqiao.bean.GroupBean;
//import com.xinniu.android.qiqueqiao.bean.HandlingFeeBean;
//import com.xinniu.android.qiqueqiao.bean.OtherUserInfo;
//import com.xinniu.android.qiqueqiao.bean.RefreshTokenBean;
//import com.xinniu.android.qiqueqiao.im.QQQConversationBehaviorListener;
//import com.xinniu.android.qiqueqiao.im.message.ExchangeMessage;
//import com.xinniu.android.qiqueqiao.im.message.GroupInviteMessage;
//import com.xinniu.android.qiqueqiao.im.message.HeadMessage;
//import com.xinniu.android.qiqueqiao.im.message.SecretPhoneMessage;
//import com.xinniu.android.qiqueqiao.im.message.SecuredTransactionMessage;
//import com.xinniu.android.qiqueqiao.im.message.ServiceMessage;
//import com.xinniu.android.qiqueqiao.im.message.ShareCardMessage;
//import com.xinniu.android.qiqueqiao.im.message.ShareResourceMessage;
//import com.xinniu.android.qiqueqiao.im.provider.ExchageMessageProvider;
//import com.xinniu.android.qiqueqiao.im.provider.MyPrivateConversationProvider;
//import com.xinniu.android.qiqueqiao.im.view.SecPhoneExtensionModule;
//import com.xinniu.android.qiqueqiao.request.RequestManager;
//import com.xinniu.android.qiqueqiao.request.callback.AllResultDoCallback;
//import com.xinniu.android.qiqueqiao.request.callback.GetOtherUserInfoCallback;
//import com.xinniu.android.qiqueqiao.request.callback.GroupInfoCallback;
//import com.xinniu.android.qiqueqiao.request.callback.ResultCallback;
//import com.xinniu.android.qiqueqiao.user.UserInfoHelper;
//
//import java.util.List;
//
//
//import io.rong.imkit.RongIM;
//import io.rong.imkit.conversation.extension.IExtensionModule;
//import io.rong.imkit.conversation.extension.RongExtensionManager;
//
//import io.rong.imlib.IRongCallback;
//import io.rong.imlib.RongIMClient;
//import io.rong.imlib.model.Conversation;
//import io.rong.imlib.model.Group;
//import io.rong.imlib.model.Message;
//import io.rong.imlib.model.MessageContent;
//import io.rong.imlib.model.UserInfo;
//import io.rong.message.ImageMessage;
//import io.rong.message.InformationNotificationMessage;
//import io.rong.message.TextMessage;
//
///**
// * Created by lzq on 2017/12/10.
// */
//
//public class IMUtils {
//    private static Context mContext;
//
//
//    public static void registerMessageType(IContainerItemProvider.MessageProvider provider) {
//        RongIM.registerMessageTemplate(provider);
//    }
//
//    public static void init(Context context) {
//        mContext = context;
//        RongIM.init(mContext);
//
//        // 初始化用户和群组信息内容提供者
//        initInfoProvider(context);
//
//        // 初始化自定义消息和消息模版
//        initMessageAndTemplate();
//
//        // 初始化扩展模块
//        setMyExtensionModule();
//
//        //去掉红包里面的转账
//        //  setRedPacketModule();
//
//        // 初始化已读回执类型
//        initReadReceiptConversation();
//
//        // 初始化会话界面相关内容
//        initConversation();
//
//        // 初始化会话列表界面相关内容
//        initConversationList();
//
//
//        // 初始化连接状态变化监听
//        initConnectStateChangeListener();
//
//        // 初始化消息监听
//        initOnReceiveMessage(context);
//
//        // 缓存连接
//        cacheConnectIM();
//
////temp        RongIM.getInstance().setSendMessageListener(new ChatActivity.MySendMessageListener());
//    }
//
//    /**
//     * 缓存登录
//     */
//    private static void cacheConnectIM() {
//        if (RongIM.getInstance().getCurrentConnectionStatus() == RongIMClient.ConnectionStatusListener.ConnectionStatus.CONNECTED) {
//            return;
//        }
//        String loginToken = UserInfoHelper.getIntance().getRongyunToken();
//        if (TextUtils.isEmpty(loginToken)) {
//            return;
//        }
//        connectIM(loginToken, true, new ResultCallback<String>() {
//            @Override
//            public void onSuccess(String s) {
//                Log.i("yuki", "--------------------聊天登录成功1");
//            }
//
//            @Override
//            public void onFail(int errorCode) {
//                Log.i("yuki", "--------------------聊天登录失败1");
//                Intent intent = new Intent(mContext, NetInfoActivity.class);
//                Bundle bundle = new Bundle();
//                bundle.putString("from", "1");
//                intent.putExtras(bundle);
//                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                mContext.startActivity(intent);
//            }
//        });
//    }
//
//    // 初始化消息监听
//    private static void initOnReceiveMessage(Context context) {
//        IMUtils.setOnReceiveMessageListener(new RongIMClient.OnReceiveMessageListener() {
//            @Override
//            public boolean onReceived(final Message message, int i) {
//                //  Log.i("tttttttt22", message.getContent().toString());
//                RongIMClient.getInstance().getConversationList(new RongIMClient.ResultCallback<List<Conversation>>() {
//                    @Override
//                    public void onSuccess(List<Conversation> conversations) {
//                        if (conversations == null) {
//                            return;
//                        }
//                        UserInfoHelper.getIntance().setPrivateConversationList(conversations);
//
//                    }
//
//                    @Override
//                    public void onError(RongIMClient.ErrorCode errorCode) {
//                    }
//                }, new Conversation.ConversationType[]{Conversation.ConversationType.PRIVATE, Conversation.ConversationType.GROUP});
//                if (message.getConversationType() == Conversation.ConversationType.PRIVATE) {
//                    //IMUtils.getUnreadCount(new RongIMClient.ResultCallback<Integer>() {
//                        @Override
//                        public void onSuccess(Integer integer) {
//                            RedPointHelper.getInstance().sendMsg(RedPointHelper.MSG_TYPE_MAIN, RedPointHelper.SHOW_RED, integer);
//                        }
//
//                        @Override
//                        public void onError(RongIMClient.ErrorCode errorCode) {
//
//                        }
//                    }, Conversation.ConversationType.PRIVATE);
//                    RequestManager.getInstance().showUserInfo(Integer.valueOf(message.getSenderUserId()), new GetOtherUserInfoCallback() {
//                        @Override
//                        public void onSuccess(OtherUserInfo bean) {
//                            UserInfoHelper.getIntance().putUserInfoToMap(message.getSenderUserId(), bean);
//                        }
//
//                        @Override
//                        public void onFailed(int code, String msg) {
//
//                        }
//                    });
//                }
//                if (message.getConversationType() == Conversation.ConversationType.GROUP) {
//                    RedPointHelper.getInstance().sendMsg(RedPointHelper.MSG_TYPE_CIRLE, RedPointHelper.SHOW_RED, 0);
//                    RequestManager.getInstance().getCircleBasicInfo(Integer.valueOf(message.getSenderUserId()), new GroupInfoCallback() {
//                        @Override
//                        public void onSuccess(GroupBean bean) {
//                            UserInfoHelper.getIntance().putCircleToMap(message.getSenderUserId(), bean);
//
//                        }
//
//                        @Override
//                        public void onFailed(int code, String msg) {
//
//                        }
//                    });
//                }
//
//                MessageContent messageContent = message.getContent();
//                if (messageContent instanceof InformationNotificationMessage) {
//                    InformationNotificationMessage informationNotificationMessage = (InformationNotificationMessage) messageContent;
//                    if (informationNotificationMessage.getExtra() != null) {
//                        Log.d("==BaseApp", informationNotificationMessage.getExtra());
//                    }
//                    if (informationNotificationMessage.getExtra().contains("friend_status")) {
//                        Intent intent1 = new Intent(ChatActivity.FRIEND_MESSAGE);
//                        mContext.sendBroadcast(intent1);
//                    }
//                }
//                return false;
//            }
//        });
//    }
//
//    // 初始化连接状态变化监听
//    private static void initConnectStateChangeListener() {
//        RongIM.setConnectionStatusListener(new RongIMClient.ConnectionStatusListener() {
//            @Override
//            public void onChanged(ConnectionStatus connectionStatus) {
//                if (connectionStatus.equals(ConnectionStatus.KICKED_OFFLINE_BY_OTHER_CLIENT) || connectionStatus == ConnectionStatus.TOKEN_INCORRECT) {
//                    //被其他提出时，需要返回登录界面 ,token 错误时，重新登录
//                    Log.i("yuki", "--------------------掉线");
//                    Intent intent = new Intent(mContext, NetInfoActivity.class);
//                    Bundle bundle = new Bundle();
//                    bundle.putString("from", "1");
//                    intent.putExtras(bundle);
//                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                    mContext.startActivity(intent);
//                }
//            }
//        });
//    }
//
//    /**
//     * 初始化会话相关
//     */
//    private static void initConversation() {
//        // 启用会话界面新消息提示
//        RongIM.getInstance().enableNewComingMessageIcon(true);
//        // 启用会话界面未读信息提示
//        RongIM.getInstance().enableUnreadMessageIcon(true);
//
//    }
//
//    /**
//     * 初始化会话列表相关事件
//     */
//    private static void initConversationList() {
//        /**
//         * 设置会话界面操作的监听器。
//         */
////        RongIM.setConversationBehaviorListener(new QQQConversationBehaviorListener());
//
//    }
//
//    /**
//     * 初始化信息提供者，包括用户信息，群组信息，群主成员信息
//     */
//    private static void initInfoProvider(Context context) {
//        /**
//         * 设置用户信息的提供者，供 RongIM 调用获取用户名称和头像信息。
//         *
//         * @param userInfoProvider 用户信息提供者。
//         * @param isCacheUserInfo  设置是否由 IMKit 来缓存用户信息。<br>
//         *                         如果 App 提供的 UserInfoProvider
//         *                         每次都需要通过网络请求用户数据，而不是将用户数据缓存到本地内存，会影响用户信息的加载速度；<br>
//         *                         此时最好将本参数设置为 true，由 IMKit 将用户信息缓存到本地内存中。
//         * @see UserInfoProvider
//         */
//        RongIM.setUserInfoProvider(new RongIM.UserInfoProvider() {
//
//            private int userxId;
//
//            @Override
//            public UserInfo getUserInfo(final String userId) {
//
//                final OtherUserInfoDao dao = BaseApp.getApplication().getDaoSession().getOtherUserInfoDao();
//
//                try {
//                    userxId = Integer.valueOf(userId);
//                } catch (NumberFormatException e) {
//////
//                } finally {
//                    if (userxId != 0) {
//                        RequestManager.getInstance().showUserInfo(userxId, new GetOtherUserInfoCallback() {
//                            @Override
//                            public void onSuccess(OtherUserInfo bean) {
//                                List<OtherUserInfo> list = dao.queryBuilder().offset(0).limit(1).orderAsc(OtherUserInfoDao.Properties.User_id)
//                                        .where(OtherUserInfoDao.Properties.User_id.eq(userId)).build().list();
//
//                                if (list.size() > 0) {
//                                    List<OtherUserInfo> Id = dao.queryBuilder().offset(0).limit(1).orderAsc(OtherUserInfoDao.Properties.Id)
//                                            .where(OtherUserInfoDao.Properties.User_id.eq(userId)).build().list();
//                                    if (Id.size() > 0) {
//                                        long x = Id.get(0).getId();
//                                        if (x != 0) {
//                                            bean.setId(x);
//                                            dao.update(bean);
//                                        }
//                                    }
//                                } else {
//
//                                    bean.setId(null);
//                                    dao.insert(bean);
//
//                                }
//                                String mUserId = String.valueOf(bean.getUser_id());
//                                String name = bean.getRealname();
//                                String headUrl = bean.getHead_pic();
//                                StringBuilder builder = new StringBuilder();
//                                builder.append(name);
////                        RongIM.getInstance().setCurrentUserInfo(new UserInfo(userId, name, Uri.parse(headUrl)));
//                                if (headUrl != null && builder != null && mUserId != null) {
//                                    RongIM.getInstance().refreshUserInfoCache(new UserInfo(mUserId, builder.toString(), Uri.parse(headUrl)));
//                                }
//                            }
//
//                            @Override
//                            public void onFailed(int code, String msg) {
//
//                            }
//                        });
//                    }
//                }
//
//                return null;//根据 userId 去你的用户系统里查询对应的用户信息返回给融云 SDK。
//            }
//
//        }, true);
//
//
//        RongIM.setGroupInfoProvider(new RongIM.GroupInfoProvider() {
//            @Override
//            public Group getGroupInfo(String s) {
//
//                RequestManager.getInstance().getCircleBasicInfo(Integer.parseInt(s), new GroupInfoCallback() {
//                    @Override
//                    public void onSuccess(GroupBean bean) {
//                        String GroupId = String.valueOf(bean.getId());
//                        String name = bean.getName();
//                        String headUrl = bean.getHead_pic();
//                        if (headUrl != null && GroupId != null) {
//                            RongIM.getInstance().refreshGroupInfoCache(new Group(GroupId, name, Uri.parse(headUrl)));
//                        }
//
//                    }
//
//                    @Override
//                    public void onFailed(int code, String msg) {
//
//                    }
//                });
//                return null;
//            }
//        }, true);
//    }
//
//
////    private static void setRedPacketModule() {
////        List<IExtensionModule> extensionModules = RongExtensionManager.getInstance().getExtensionModules();
////        IExtensionModule redPacketModule = null;
////        int redPacketModuleIndex = 0;
////        for (int i = 0; i < extensionModules.size(); i++) {
////            IExtensionModule extensionModule = extensionModules.get(i);
////            if (extensionModule instanceof JrmfExtensionModule) {
////                redPacketModule = extensionModule;
////                redPacketModuleIndex = i;
////            }
////        }
////        if (redPacketModule != null) {
////            RongExtensionManager.getInstance().unregisterExtensionModule(redPacketModule);
////            RongExtensionManager.getInstance().registerExtensionModule(new JrmfExtensionModule() {
////                @Override
////                public List<IPluginModule> getPluginModules(Conversation.ConversationType conversationType) {
////                    List<IPluginModule> pluginModules = super.getPluginModules(conversationType);
////                    Iterator<IPluginModule> iterator = pluginModules.iterator();
////                    while (iterator.hasNext()) {
////                        // 如果转账 plugin ，去除之
////                        if (iterator.next() instanceof TransferAccountPlugin) {
////                            iterator.remove();
////                        }
////                    }
////                    return pluginModules;
////                }
////            });
////
////        }
////    }
//
//    /**
//     * 注册消息及消息模版
//     */
//    private static void initMessageAndTemplate() {
//        RongIM.registerMessageTemplate(ServiceMessage.class);
//        RongIM.registerMessageType(HeadMessage.class);
//        RongIM.registerMessageType(SecretPhoneMessage.class);
//        RongIM.registerMessageType(ExchangeMessage.class);
//        RongIM.registerMessageType(ShareCardMessage.class);
//        RongIM.registerMessageType(ShareResourceMessage.class);
//        RongIM.registerMessageType(SecuredTransactionMessage.class);
//        RongIM.registerMessageType(GroupInviteMessage.class);
//        RongIM.registerMessageTemplate(new ExchageMessageProvider());
//        RongIM.getInstance().registerConversationTemplate(new MyPrivateConversationProvider());
//    }
//
//    /**
//     * 初始化已读回执类型
//     */
//    private static void initReadReceiptConversation() {
//        // 将私聊，群组加入消息已读回执
//
//        Conversation.ConversationType[] types = new Conversation.ConversationType[]{
//                Conversation.ConversationType.PRIVATE,
//                Conversation.ConversationType.GROUP
//        };
//        RongIM.getInstance().setReadReceiptConversationTypeList(types);
//    }
//
//    private static void setMyExtensionModule() {
//        List<IExtensionModule> moduleList = RongExtensionManager.getInstance().getExtensionModules();
//        IExtensionModule defaultModule = null;
//        if (moduleList != null) {
//            for (IExtensionModule module : moduleList) {
//                if (module instanceof DefaultExtensionModule) {
//                    defaultModule = module;
//                    break;
//                }
//            }
//            if (defaultModule != null) {
//                RongExtensionManager.getInstance().unregisterExtensionModule(defaultModule);
//                RongExtensionManager.getInstance().registerExtensionModule(new SecPhoneExtensionModule());
//            }
//        }
//
//
//    }
//
//    /**
//     * 连接 IM 服务
//     *
//     * @param token
//     * @param getTokenOnIncorrect
//     * @param callback
//     */
//    public static void connectIM(String token, final boolean getTokenOnIncorrect, final ResultCallback<String> callback) {
//        /*
//         *  考虑到会有后台调用此方法，所以不采用 LiveData 做返回值
//         */
//        RongIM.connect(token, new RongIMClient.ConnectCallback() {
//            @Override
//            public void onTokenIncorrect() {
//                if (getTokenOnIncorrect) {
//                    //刷新token
//                    RequestManager.getInstance().refreshToken(new AllResultDoCallback() {
//                        @Override
//                        public void onSuccess(String msg) {
//                            Log.i("yuki", "--------------------刷新token成功");
//                            Gson gson = new Gson();
//                            RefreshTokenBean refreshTokenBean = gson.fromJson(msg, RefreshTokenBean.class);
//                            UserInfoHelper.getIntance().setRongyunToken(refreshTokenBean.getToken());
//                            connectIM(refreshTokenBean.getToken(), false, callback);
//                        }
//
//                        @Override
//                        public void onFailed(int code, String msg) {
//                            callback.onFail(code);
//                        }
//                    });
//
//
//                } else {
//                    if (callback != null) {
//                        callback.onFail(-4);
//                    } else {
//                        // do nothing
//                    }
//                }
//            }
//
//            @Override
//            public void onSuccess(String s) {
//                // 连接 IM 成功
//                callback.onSuccess(s);
//            }
//
//            @Override
//            public void onError(RongIMClient.ErrorCode errorCode) {
//
//                if (callback != null) {
//                    callback.onFail(errorCode.getValue());
//                } else {
//                    // do nothing
//                }
//            }
//        });
//    }
//
//    public static void setOnReceiveMessageListener(RongIMClient.OnReceiveMessageListener listener) {
//        RongIM.setOnReceiveMessageListener(listener);
//    }
//
//    public static void singleChat(final Context activity, final String toUserId, final String title, final String sourceType, final String talkContent) {
//        final OtherUserInfoDao dao = BaseApp.getApplication().getDaoSession().getOtherUserInfoDao();
//        RequestManager.getInstance().showUserInfo(Integer.valueOf(toUserId), new GetOtherUserInfoCallback() {
//            @Override
//            public void onSuccess(OtherUserInfo bean) {
//                List<OtherUserInfo> list = dao.queryBuilder().offset(0).limit(1).orderAsc(OtherUserInfoDao.Properties.User_id)
//                        .where(OtherUserInfoDao.Properties.User_id.eq(toUserId)).build().list();
//
//                if (list.size() > 0) {
//                    List<OtherUserInfo> Id = dao.queryBuilder().offset(0).limit(1).orderAsc(OtherUserInfoDao.Properties.Id)
//                            .where(OtherUserInfoDao.Properties.User_id.eq(toUserId)).build().list();
//                    if (Id.size() > 0) {
//                        if (!toUserId.equals("0")) {
//                            long x = Id.get(0).getId();
//                            bean.setId(x);
//                            dao.update(bean);
//                        }
//                    }
//                } else {
//                    if (!toUserId.equals("0")) {
//                        bean.setId(null);
//                        dao.insert(bean);
//                    }
//                }
//                if (title.contentEquals("客服")) {
//                    RongIM.getInstance().startPrivateChat(activity, toUserId, bean.getRealname());
//                    if (sourceType.equals("1")) {
//                        TextMessage myTextMessage = TextMessage.obtain(talkContent);
//                        Message myMsg = Message.obtain(toUserId, Conversation.ConversationType.PRIVATE, myTextMessage);
//                        RongIM.getInstance().sendMessage(myMsg, null, null, new IRongCallback.ISendMediaMessageCallback() {
//                            @Override
//                            public void onProgress(Message message, int i) {
//
//                            }
//
//                            @Override
//                            public void onCanceled(Message message) {
//
//                            }
//
//                            @Override
//                            public void onAttached(Message message) {
//
//                            }
//
//                            @Override
//                            public void onSuccess(Message message) {
//
//                            }
//
//                            @Override
//                            public void onError(Message message, RongIMClient.ErrorCode errorCode) {
//
//                            }
//                        });
//                    } else if (sourceType.equals("2")) {
//                        TextMessage myTextMessage = TextMessage.obtain(talkContent);
//                        Message myMsg = Message.obtain(toUserId, Conversation.ConversationType.PRIVATE, myTextMessage);
//                        RongIM.getInstance().sendMessage(myMsg, null, null, new IRongCallback.ISendMediaMessageCallback() {
//                            @Override
//                            public void onProgress(Message message, int i) {
//
//                            }
//
//                            @Override
//                            public void onCanceled(Message message) {
//
//                            }
//
//                            @Override
//                            public void onAttached(Message message) {
//
//                            }
//
//                            @Override
//                            public void onSuccess(Message message) {
//
//                            }
//
//                            @Override
//                            public void onError(Message message, RongIMClient.ErrorCode errorCode) {
//
//                            }
//                        });
//                    } else if (sourceType.equals("4")) {
//                        TextMessage myTextMessage = TextMessage.obtain(talkContent);
//                        Message myMsg = Message.obtain(toUserId, Conversation.ConversationType.PRIVATE, myTextMessage);
//                        RongIM.getInstance().sendMessage(myMsg, null, null, new IRongCallback.ISendMediaMessageCallback() {
//                            @Override
//                            public void onProgress(Message message, int i) {
//
//                            }
//
//                            @Override
//                            public void onCanceled(Message message) {
//
//                            }
//
//                            @Override
//                            public void onAttached(Message message) {
//
//                            }
//
//                            @Override
//                            public void onSuccess(Message message) {
//
//                            }
//
//                            @Override
//                            public void onError(Message message, RongIMClient.ErrorCode errorCode) {
//
//                            }
//                        });
//                    } else if (sourceType.equals("5")) {
//                        TextMessage myTextMessage = TextMessage.obtain(talkContent);
//                        Message myMsg = Message.obtain(toUserId, Conversation.ConversationType.PRIVATE, myTextMessage);
//                        RongIM.getInstance().sendMessage(myMsg, null, null, new IRongCallback.ISendMediaMessageCallback() {
//                            @Override
//                            public void onProgress(Message message, int i) {
//
//                            }
//
//                            @Override
//                            public void onCanceled(Message message) {
//
//                            }
//
//                            @Override
//                            public void onAttached(Message message) {
//
//                            }
//
//                            @Override
//                            public void onSuccess(Message message) {
//
//                            }
//
//                            @Override
//                            public void onError(Message message, RongIMClient.ErrorCode errorCode) {
//
//                            }
//                        });
//                    }
//                }
//                UserInfoHelper.getIntance().putUserInfoToMap(toUserId, bean);
//            }
//
//            @Override
//            public void onFailed(int code, String msg) {
//
//            }
//        });
//        if (!title.contentEquals("客服")) {
//            RongIM.getInstance().startPrivateChat(activity, toUserId, title);
//            TextMessage myTextMessage = TextMessage.obtain(talkContent);
//            Message myMsg = Message.obtain(toUserId, Conversation.ConversationType.PRIVATE, myTextMessage);
//            RongIM.getInstance().sendMessage(myMsg, null, null, new IRongCallback.ISendMediaMessageCallback() {
//                @Override
//                public void onProgress(Message message, int i) {
//
//                }
//
//                @Override
//                public void onCanceled(Message message) {
//
//                }
//
//                @Override
//                public void onAttached(Message message) {
//
//                }
//
//                @Override
//                public void onSuccess(Message message) {
//
//                }
//
//                @Override
//                public void onError(Message message, RongIMClient.ErrorCode errorCode) {
//
//                }
//            });
//        }
//    }
//
//    public static void singleChatForResource(final Activity activity, final String toUserId, final String title, int isQrcode, String position, String headPic, String isvip) {
//        final OtherUserInfoDao dao = BaseApp.getApplication().getDaoSession().getOtherUserInfoDao();
//        RequestManager.getInstance().showUserInfo(Integer.valueOf(toUserId), new GetOtherUserInfoCallback() {
//            @Override
//            public void onSuccess(OtherUserInfo bean) {
//                List<OtherUserInfo> list = dao.queryBuilder().offset(0).limit(1).orderAsc(OtherUserInfoDao.Properties.User_id)
//                        .where(OtherUserInfoDao.Properties.User_id.eq(toUserId)).build().list();
//
//                if (list.size() > 0) {
////                    bean.setId(null);
//                    List<OtherUserInfo> Id = dao.queryBuilder().offset(0).limit(1).orderAsc(OtherUserInfoDao.Properties.Id)
//                            .where(OtherUserInfoDao.Properties.User_id.eq(toUserId)).build().list();
//                    if (Id.size() > 0) {
//                        if (!toUserId.equals("0")) {
//                            long x = Id.get(0).getId();
//                            bean.setId(x);
//                            dao.update(bean);
//                        }
//                    }
//
//
//                } else {
//                    if (!toUserId.equals("0")) {
//                        bean.setId(null);
//                        dao.insert(bean);
//                    }
//                }
////                            map.put(targetId,bean);
//                UserInfoHelper.getIntance().putUserInfoToMap(toUserId, bean);
//            }
//
//            @Override
//            public void onFailed(int code, String msg) {
//
//            }
//        });
//        Intent intent = new Intent(activity, ChatActivity.class);
//        intent.putExtra(ChatActivity.FROM_TYPE, ChatActivity.FROM_TYPE_RESOURCE);
//        intent.putExtra(ChatActivity.M_TARGET_ID, toUserId);
//        intent.putExtra(ChatActivity.M_TITLE, title);
//        intent.putExtra(ChatActivity.M_TITLE_POSITION, position);
//        intent.putExtra(ChatActivity.M_IS_VIP, isvip);
//        intent.putExtra(ChatActivity.M_TITLE_HEAD_PIC, headPic);
//        intent.putExtra(ChatActivity.IS_QRCODE, isQrcode);
//        activity.startActivity(intent);
//    }
//
//    public static void insertMessage(Conversation.ConversationType type, String targetId, String senderUserId, MessageContent content, RongIMClient.ResultCallback<Message> resultCallback) {
//        RongIM.getInstance().insertMessage(type, targetId, senderUserId, content, System.currentTimeMillis(), resultCallback);
//    }
////    public static void sendMessage(){
////        RongIM.getInstance().sendMessage();
////    }
//
//    //    public static void groupChat(Activity activity, String toUserId, String title) {
////        RongIM.getInstance().startGroupChat(activity, toUserId, title);
////    }
////
////    public static void roomChat(Context context, String chatRoomId, boolean createIfNotExist) {
////        RongIM.getInstance().startChatRoomChat(context, chatRoomId, createIfNotExist);
////    }
//    public static void setTop(final String id) {
//        RongIMClient.getInstance().setConversationToTop(Conversation.ConversationType.PRIVATE, id, true, new RongIMClient.ResultCallback<Boolean>() {
//            @Override
//            public void onSuccess(Boolean aBoolean) {
//            }
//
//            @Override
//            public void onError(RongIMClient.ErrorCode errorCode) {
//
//            }
//        });
//    }
//
//    public static void setTop(final String id, boolean isTop) {
//        RongIMClient.getInstance().setConversationToTop(Conversation.ConversationType.PRIVATE, id, isTop, new RongIMClient.ResultCallback<Boolean>() {
//            @Override
//            public void onSuccess(Boolean aBoolean) {
//            }
//
//            @Override
//            public void onError(RongIMClient.ErrorCode errorCode) {
//
//            }
//        });
//    }
//
//    /**
//     * 设置某一会话为置顶或者取消置顶，回调方式获取设置是否成功。
//     *
//     * @param conversationType 会话类型。
//     * @param id               目标 Id。根据不同的 conversationType，可能是用户 Id、讨论组 Id、群组 Id 或聊天室 Id。
//     * @param isTop            是否置顶。
//     * @param callback         设置置顶或取消置顶是否成功的回调。
//     */
//    public static void setConversationToTop(final Conversation.ConversationType conversationType, final String id, final boolean isTop, final RongIMClient.ResultCallback<Boolean> callback) {
//        RongIMClient.getInstance().setConversationToTop(conversationType, id, isTop, new RongIMClient.ResultCallback<Boolean>() {
//            @Override
//            public void onSuccess(Boolean aBoolean) {
//                callback.onSuccess(aBoolean);
//            }
//
//            @Override
//            public void onError(RongIMClient.ErrorCode errorCode) {
//                callback.onError(errorCode);
//            }
//        });
//
//    }
//
//    /**
//     * 发送图片
//     */
//    public static void sendImageView(Conversation.ConversationType conversationType, String targetId, Uri thumUri, Uri imageUri, final SendResult sendResult) {
//
//        ImageMessage imgMsg = ImageMessage.obtain(thumUri, imageUri, false);
//
//        RongIMClient.getInstance().sendImageMessage(conversationType, targetId, imgMsg, "图片信息", null, new RongIMClient.SendImageMessageCallback() {
//            @Override
//            public void onAttached(Message message) {
//
//            }
//
//            @Override
//            public void onError(Message message, RongIMClient.ErrorCode errorCode) {
//                //发送失败
//                sendResult.sendResult("图片发送失败");
//            }
//
//            @Override
//            public void onSuccess(Message message) {
//                //发送成功
//                sendResult.sendResult("图片发送成功");
//            }
//
//            @Override
//            public void onProgress(Message message, int i) {
//                //发送进度
//            }
//        });
//
//
//    }
//
//    public interface SendResult {
//        void sendResult(String msg);
//    }
//
//    private SendResult sendResult;
//
//    public void setSendResult(IMUtils.SendResult sendResult) {
//        this.sendResult = sendResult;
//    }
//
//    public static void getUnreadCount(RongIMClient.ResultCallback<Integer> callback, Conversation.ConversationType conversationTypes) {
//        /**
//         * 回调方式获取某会话类型的未读消息数。
//         *
//         * @param callback          未读消息数的回调。
//         * @param conversationTypes 会话类型。
//         */
//        RongIMClient.getInstance().getUnreadCount(callback, conversationTypes);
//    }
//
//    public static void getUnreadCount(final Conversation.ConversationType conversationType, final String targetId, final RongIMClient.ResultCallback<Integer> callback) {
//        /**
//         * 根据会话类型的目标 Id，回调方式获取来自某用户（某会话）的未读消息数。
//         *
//         * @param conversationType 会话类型。
//         * @param targetId         目标 Id。根据不同的 conversationType，可能是用户 Id、讨论组 Id、群组 Id。
//         * @param callback         未读消息数的回调。
//         */
//        RongIMClient.getInstance().getUnreadCount(conversationType, targetId, callback);
//    }
//
//    public static void rongLogout() {
//        RongIM.getInstance().disconnect();
//        RongIM.getInstance().logout();
//    }
//
//
//}
