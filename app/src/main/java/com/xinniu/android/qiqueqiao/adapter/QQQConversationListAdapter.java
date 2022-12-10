package com.xinniu.android.qiqueqiao.adapter;

import android.content.Context;
import android.os.Bundle;
//import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.xinniu.android.qiqueqiao.OtherUserInfoDao;
import com.xinniu.android.qiqueqiao.R;
import com.xinniu.android.qiqueqiao.activity.ChatActivity;
import com.xinniu.android.qiqueqiao.base.BaseApp;
import com.xinniu.android.qiqueqiao.bean.OtherUserInfo;
import com.xinniu.android.qiqueqiao.request.RequestManager;
import com.xinniu.android.qiqueqiao.request.callback.GetOtherUserInfoCallback;
import com.xinniu.android.qiqueqiao.user.UserInfoHelper;
//import com.xinniu.android.qiqueqiao.utils.IMUtils;
import com.xinniu.android.qiqueqiao.utils.ImageLoader;
import com.xinniu.android.qiqueqiao.utils.TimeUtils;
import com.xinniu.android.qiqueqiao.widget.CircleImageView;
import com.xinniu.android.qiqueqiao.widget.SwipeMenuLayout;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


import io.rong.imkit.RongIM;

import io.rong.imkit.conversationlist.ConversationListAdapter;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.Conversation;
import io.rong.imlib.model.Message;


/**
 * Created by lzq on 2017/12/18.
 */

public class QQQConversationListAdapter extends ConversationListAdapter {
    private Context mContext;
    private List<Conversation> mConversations;
    final OtherUserInfoDao dao = BaseApp.getApplication().getDaoSession().getOtherUserInfoDao();
    LayoutInflater mInflater;

    public QQQConversationListAdapter(Context context) {
//        super(context);
        this.mContext = context;
        this.mInflater = LayoutInflater.from(this.mContext);
//        Log.i("000000000====","调用次数");

        Map<String, OtherUserInfo> map = UserInfoHelper.getIntance().getUserInfoFromMap();
        RongIMClient.getInstance().getConversationList(new RongIMClient.ResultCallback<List<Conversation>>() {
            @Override
            public void onSuccess(List<Conversation> conversations) {
                if (conversations == null) {
                    return;
                }
                mConversations = conversations;
                for (int i = 0; i < conversations.size(); i++) {

                    final String targetId = conversations.get(i).getTargetId();
                    Conversation.ConversationType type = conversations.get(i).getConversationType();
                    if (type == Conversation.ConversationType.PRIVATE) {
                        List<OtherUserInfo> list = dao.queryBuilder().offset(0).limit(1).orderAsc(OtherUserInfoDao.Properties.User_id)
                                .where(OtherUserInfoDao.Properties.User_id.eq(targetId)).build().list();
                        if (list.size() > 0) {
                            OtherUserInfo otherUserInfo = list.get(0);
                            UserInfoHelper.getIntance().putUserInfoToMap(targetId, otherUserInfo);
                        } else {
                            RequestManager.getInstance().showUserInfo(Integer.valueOf(targetId), new GetOtherUserInfoCallback() {
                                @Override
                                public void onSuccess(OtherUserInfo bean) {
                                    UserInfoHelper.getIntance().putUserInfoToMap(targetId, bean);
                                    bean.setId(null);
                                    dao.insert(bean);
                                }

                                @Override
                                public void onFailed(int code, String msg) {

                                }
                            });
                        }
                    }
                }
            }

            @Override
            public void onError(RongIMClient.ErrorCode errorCode) {
            }
        }, new Conversation.ConversationType[]{Conversation.ConversationType.PRIVATE});
    }

//
//    @Override
//    protected View newView(Context context, int position, ViewGroup group) {
//        View result = this.mInflater.inflate(R.layout.item_comversation_list_qqq, group, false);
//        ViewHolder holder = new ViewHolder(result);
//        result.setTag(holder);
//        return result;
//    }
//
//    @Override
//    protected void bindView(View v, final int position, final UIConversation data) {
//
//        final ViewHolder holder = (ViewHolder) v.getTag();
//        if (holder != null) {
//            if (Conversation.ConversationType.PRIVATE == data.getConversationType()) {
//
//                if (data.getIconUrl() == null) {
//                    holder.headIv.setImageResource(R.mipmap.default_head_img);
//                } else {
//                    ImageLoader.loadImage(data.getIconUrl().toString(), holder.headIv);
//                }
//                holder.content.setText(data.getConversationContent());
//                if (data.getUnReadMessageCount() > 0) {
//                    holder.unReadMsg.setText(String.valueOf(data.getUnReadMessageCount()));
//                    holder.unReadMsg.setVisibility(View.VISIBLE);
//                } else {
//                    holder.unReadMsg.setVisibility(View.GONE);
//                }
//                holder.time.setText(TimeUtils.millis2String(data.getUIConversationTime()));
//
//                final String targetId = data.getConversationTargetId();
//                final OtherUserInfo userInfo = UserInfoHelper.getIntance().getUserInfoFromMap(targetId);
//
//                if (userInfo != null) {
//                    if (!TextUtils.isEmpty(userInfo.getCompany())) {
//                        holder.company.setText(userInfo.getCompany());
//                    } else {
//                        holder.company.setText("");
//                    }
//                    if (!TextUtils.isEmpty(userInfo.getPosition())) {
//                        holder.company.setText(userInfo.getPosition());
//                    } else {
//                        holder.company.setText("");
//                    }
//                    if (!TextUtils.isEmpty(userInfo.getCompany()) && !TextUtils.isEmpty(userInfo.getPosition())) {
//                        holder.company.setText(userInfo.getCompany() + "" + userInfo.getPosition());
//                    } else {
//                        holder.company.setText("");
//                    }
//                    if (TextUtils.isEmpty(userInfo.getRealname())) {
//                        holder.name.setText("");
//                    } else {
//                        holder.name.setText(userInfo.getRealname());
//                    }
//                    if (data.isTop()) {
//                        holder.careTv.setText("取消置顶");
//                        holder.theRl.setBackgroundColor(ContextCompat.getColor(mContext, R.color.blue_the_bg_chat));
//                    } else {
//                        holder.careTv.setText("置顶");
//                        holder.theRl.setBackgroundColor(ContextCompat.getColor(mContext, R.color.white));
//
//                    }
//                    holder.careTv.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            useFollow(userInfo.getUser_id(), holder.careTv, data);
//                        }
//                    });
//
//                    if (!TextUtils.isEmpty(userInfo.getIs_corporate_vip()) && userInfo.getIs_corporate_vip().equals("1")) {
//                        holder.ImgIsVip.setVisibility(View.GONE);
//                        holder.ImgCompanyVip.setVisibility(View.VISIBLE);
//                        holder.name.setTextColor(ContextCompat.getColor(mContext, R.color.king_color));
//                    } else {
//                        holder.ImgCompanyVip.setVisibility(View.GONE);
//                        if (!TextUtils.isEmpty(userInfo.getIs_vip()) && userInfo.getIs_vip().equals("0")) {
//                            holder.ImgIsVip.setVisibility(View.INVISIBLE);
//                            holder.name.setTextColor(ContextCompat.getColor(mContext, R.color.text_color_1));
//                        } else if (!TextUtils.isEmpty(userInfo.getIs_vip()) && userInfo.getIs_vip().equals("1")) {
//                            holder.ImgIsVip.setVisibility(View.INVISIBLE);
//                            holder.ImgIsVip.setVisibility(View.VISIBLE);
//                            holder.ImgIsVip.setBackground(ContextCompat.getDrawable(mContext, R.mipmap.vip_iconx));
//                            holder.name.setTextColor(ContextCompat.getColor(mContext, R.color.king_color));
//                        } else if (!TextUtils.isEmpty(userInfo.getIs_vip()) && userInfo.getIs_vip().equals("2")) {
//                            holder.ImgIsVip.setVisibility(View.INVISIBLE);
//                            holder.ImgIsVip.setVisibility(View.VISIBLE);
//                            holder.ImgIsVip.setBackground(ContextCompat.getDrawable(mContext, R.mipmap.svip_iconx));
//                            holder.name.setTextColor(ContextCompat.getColor(mContext, R.color.king_color));
//                        } else {
//                            holder.ImgIsVip.setVisibility(View.INVISIBLE);
//                            holder.name.setTextColor(ContextCompat.getColor(mContext, R.color.text_color_1));
//                        }
//
//                    }
//                }
//                if (data.getConversationSenderId().equals(UserInfoHelper.getIntance().getUserId() + "")) {
//                    if (data.getSentStatus() == Message.SentStatus.SENT) {
//                        holder.unReadTv.setText("送达");
//                        holder.unReadTv.setSelected(true);
//                    } else if (data.getSentStatus() == Message.SentStatus.READ) {
//                        holder.unReadTv.setText("已读");
//                        holder.unReadTv.setSelected(false);
//                    } else {
//                        holder.unReadTv.setVisibility(View.GONE);
//                    }
//                } else {
//                    holder.unReadTv.setVisibility(View.GONE);
//                }
//
//                if (UserInfoHelper.getIntance().getCenterBean() != null
//                        && UserInfoHelper.getIntance().getCenterBean().getUsers() != null
//                        && targetId.contentEquals(String.valueOf(UserInfoHelper.getIntance().getCenterBean().getUsers().getF_id()))) {
//                    //服务经理
//                    data.setTop(true);
////                    if (userInfo != null) {
////                        if (!TextUtils.isEmpty(userInfo.getCompany()) && !TextUtils.isEmpty(userInfo.getPosition())) {
////                            holder.company.setText(userInfo.getCompany() + "" + userInfo.getPosition());
////                        } else {
////                            holder.company.setText("");
////                        }
////                    }
//                    holder.ImgIsVip.setVisibility(View.INVISIBLE);
////                    if (userInfo != null) {
////                        if (TextUtils.isEmpty(userInfo.getRealname())) {
////                            holder.name.setText("");
////                        } else {
////                            holder.name.setText(userInfo.getRealname());
////                        }
////                    }
//                    holder.name.setTextColor(ContextCompat.getColor(mContext, R.color.blue_bg_4B96F3));
//                    holder.name.setText("专属服务经理");
//                    holder.company.setText("");
//                    holder.careTv.setVisibility(View.GONE);
//                    holder.deleteTv.setVisibility(View.GONE);
//                    holder.headIv.setImageResource(R.mipmap.customer_service);
//                    holder.menuLayout.addListener(new SwipeMenuLayout.SwipeListener() {
//                        @Override
//                        public void onUpdate(SwipeMenuLayout layout, float offset) {
//
//                        }
//
//                        @Override
//                        public void onOpened(SwipeMenuLayout layout) {
//
//                        }
//
//                        @Override
//                        public void onClosed(SwipeMenuLayout layout) {
//
//                        }
//
//                        @Override
//                        public void onClick(SwipeMenuLayout layout) {
//                            UIConversation uiConversation = (UIConversation) getItem(position);
//                            Conversation.ConversationType conversationType = uiConversation.getConversationType();
//                            if (RongContext.getInstance().getConversationListBehaviorListener() != null && RongContext.getInstance().getConversationListBehaviorListener().onConversationClick(mContext, layout, uiConversation)) {
//                                return;
//                            }
////
//                            uiConversation.setUnReadMessageCount(0);
//                            Bundle bundle = new Bundle();
//                            try {
//                                bundle.putString(ChatActivity.M_TITLE_POSITION, userInfo.getCompany() + "" + userInfo.getPosition());
//                                bundle.putString(ChatActivity.M_TITLE_HEAD_PIC, userInfo.getHead_pic());
//                            } catch (NullPointerException e) {
//                                bundle.putString(ChatActivity.M_TITLE_POSITION, "");
//                                bundle.putString(ChatActivity.M_TITLE_HEAD_PIC, "");
//                            }
//
//                            RongIM.getInstance().startConversation(mContext, conversationType, uiConversation.getConversationTargetId(), uiConversation.getUIConversationTitle(), bundle);
////                            IMUtils.
//                        }
//                    });
//                } else {
//                    holder.careTv.setVisibility(View.VISIBLE);
//                    holder.deleteTv.setVisibility(View.VISIBLE);
//                    holder.menuLayout.addListener(new SwipeMenuLayout.SwipeListener() {
//                        @Override
//                        public void onUpdate(SwipeMenuLayout layout, float offset) {
//
//                        }
//
//                        @Override
//                        public void onOpened(SwipeMenuLayout layout) {
//
//                        }
//
//                        @Override
//                        public void onClosed(SwipeMenuLayout layout) {
//
//                        }
//
//                        @Override
//                        public void onClick(SwipeMenuLayout layout) {
//                            UIConversation uiConversation = (UIConversation) getItem(position);
//                            Conversation.ConversationType conversationType = uiConversation.getConversationType();
//                            if (RongContext.getInstance().getConversationListBehaviorListener() != null && RongContext.getInstance().getConversationListBehaviorListener().onConversationClick(mContext, layout, uiConversation)) {
//                                return;
//                            }
//
//                            uiConversation.setUnReadMessageCount(0);
//                            Bundle bundle = new Bundle();
//                            //处理异常
//                            try {
//                                bundle.putString(ChatActivity.M_TITLE_POSITION, userInfo.getCompany() + "" + userInfo.getPosition());
//                                bundle.putString(ChatActivity.M_TITLE_HEAD_PIC, userInfo.getHead_pic());
//                                bundle.putString(ChatActivity.M_IS_VIP, userInfo.getIs_vip());
//                            } catch (NullPointerException e) {
//                                bundle.putString(ChatActivity.M_TITLE_POSITION, "");
//                                bundle.putString(ChatActivity.M_TITLE_HEAD_PIC, "");
//                                bundle.putString(ChatActivity.M_IS_VIP, "0");
//                            }
//                            RongIM.getInstance().startConversation(mContext, conversationType, uiConversation.getConversationTargetId(), uiConversation.getUIConversationTitle(), bundle);
//                        }
//                    });
//                    final OtherUserInfoDao dao = BaseApp.getApplication().getDaoSession().getOtherUserInfoDao();
//                    holder.deleteTv.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            List<OtherUserInfo> list = dao.queryBuilder().offset(0).limit(1).orderAsc(OtherUserInfoDao.Properties.User_id).build().list();
//                            if (list.size() > 0) {
//                                Long id = list.get(0).getId();
//                                dao.deleteByKey(id);
//                            }
//                            RongIM.getInstance().removeConversation(data.getConversationType(), data.getConversationTargetId(), (RongIMClient.ResultCallback) null);
//                        }
//                    });
//                }
//            }
//
//        }
//    }
//
//    private void useFollow(int userId, final TextView view, final UIConversation data) {
//        final boolean followAction;
//        if (view.getText().toString().contentEquals("置顶")) {
//            followAction = true;
//        } else {
//            followAction = false;
//        }
//        IMUtils.setConversationToTop(Conversation.ConversationType.PRIVATE, userId + "", followAction, new RongIMClient.ResultCallback<Boolean>() {
//            @Override
//            public void onSuccess(Boolean aBoolean) {
//                if (followAction) {
//                    data.setTop(true);
//                } else {
//                    data.setTop(false);
//                }
//                notifyDataSetChanged();
//            }
//
//            @Override
//            public void onError(RongIMClient.ErrorCode errorCode) {
//
//            }
//        });
//
//
//    }
//
//    @Override
//    public void add(UIConversation uiConversation, int position) {
//        super.add(uiConversation, position);
//    }

    public int srcollToUnReadMsg() {
        mConversations = UserInfoHelper.getIntance().getPrivateConversationList();
        if (mConversations == null) {
            return 0;
        }
        for (int i = 0; i < mConversations.size(); i++) {
            if (mConversations.get(i).getUnreadMessageCount() > 0) {
                return i;
            }
        }
        return 0;
    }

    class ViewHolder {
        TextView name;
        TextView time;
        TextView content;
        TextView unReadMsg;
        TextView company;
        CircleImageView headIv;

        public TextView deleteTv;
        public TextView careTv;
        TextView unReadTv;
        ImageView ImgIsVip;
        RelativeLayout theRl;
        ImageView ImgCompanyVip;


        public SwipeMenuLayout menuLayout;

        protected ViewHolder(View view) {
            name = (TextView) view.findViewById(R.id.name);
            company = (TextView) view.findViewById(R.id.compay);
            time = (TextView) view.findViewById(R.id.time);
            content = (TextView) view.findViewById(R.id.content);
            unReadMsg = (TextView) view.findViewById(R.id.un_read_msg);
            careTv = (TextView) view.findViewById(R.id.care_tv);
            menuLayout = (SwipeMenuLayout) view.findViewById(R.id.swipe_content);
            deleteTv = (TextView) view.findViewById(R.id.delete_tv);
            headIv = (CircleImageView) view.findViewById(R.id.head_iv);
            unReadTv = (TextView) view.findViewById(R.id.item_unread);
            ImgIsVip = (ImageView) view.findViewById(R.id.imgIsVip);
            theRl = (RelativeLayout) view.findViewById(R.id.rc_item_conversation);
            ImgCompanyVip = (ImageView) view.findViewById(R.id.item_index_new_company_v);
        }
    }
}
