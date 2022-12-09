package com.xinniu.android.qiqueqiao.fragment.message.group;

import android.content.Context;
//import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.xinniu.android.qiqueqiao.GroupBeanDao;
import com.xinniu.android.qiqueqiao.OtherUserInfoDao;
import com.xinniu.android.qiqueqiao.R;
import com.xinniu.android.qiqueqiao.activity.CircleActivity;
import com.xinniu.android.qiqueqiao.base.BaseApp;
import com.xinniu.android.qiqueqiao.bean.GroupBean;
import com.xinniu.android.qiqueqiao.request.RequestManager;
import com.xinniu.android.qiqueqiao.request.callback.GroupInfoCallback;
import com.xinniu.android.qiqueqiao.user.UserInfoHelper;
import com.xinniu.android.qiqueqiao.utils.ImageLoader;
import com.xinniu.android.qiqueqiao.utils.TimeUtils;
import com.xinniu.android.qiqueqiao.widget.SwipeMenuLayout;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import io.rong.imkit.RongIM;
import io.rong.imkit.model.UIConversation;
import io.rong.imkit.widget.adapter.ConversationListAdapter;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.Conversation;
import io.rong.imlib.model.Message;

/**
 * Created by yuchance on 2018/9/26.
 */

public class QQQGroupListAdapter extends ConversationListAdapter {
    private Context mContext;
    private List<Conversation> mConversations;
    final OtherUserInfoDao dao = BaseApp.getApplication().getDaoSession().getOtherUserInfoDao();
    GroupBeanDao groupDao = BaseApp.getApplication().getDaoSession().getGroupBeanDao();
    LayoutInflater mInflater;
    public QQQGroupListAdapter(Context context) {
        super(context);
        this.mContext = context;
        this.mInflater = LayoutInflater.from(this.mContext);
        RongIMClient.getInstance().getConversationList(new RongIMClient.ResultCallback<List<Conversation>>() {
            @Override
            public void onSuccess(List<Conversation> conversations) {
                if (conversations == null) {
                    return;
                }
                mConversations = conversations;
                Collection<Integer> collection = new ArrayList<>();

                for (int i = 0; i < conversations.size(); i++) {

                    final String targetId = conversations.get(i).getTargetId();
                    Conversation.ConversationType type = conversations.get(i).getConversationType();
                    if (type == Conversation.ConversationType.GROUP) {
                        List<GroupBean> list = groupDao.queryBuilder().offset(0).limit(1).orderAsc(GroupBeanDao.Properties.Id)
                                .where(GroupBeanDao.Properties.Id.eq(targetId)).build().list();
                        if (list.size() > 0) {
                            GroupBean groupBean = list.get(0);
                            UserInfoHelper.getIntance().putCircleToMap(targetId, groupBean);
                        } else {
                            RequestManager.getInstance().getCircleBasicInfo(Integer.valueOf(targetId), new GroupInfoCallback() {
                                @Override
                                public void onSuccess(GroupBean bean) {
                                    UserInfoHelper.getIntance().putCircleToMap(targetId, bean);
                                    bean.setTheid(null);
                                    groupDao.insert(bean);

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
            public void onError(RongIMClient.ErrorCode errorCode){
            }
        }, new Conversation.ConversationType[] {Conversation.ConversationType.GROUP});
    }

    @Override
    protected View newView(Context context, int position, ViewGroup group) {
        View result = this.mInflater.inflate(R.layout.item_conversation_group_qqq, group, false);
        ViewHolder holder = new ViewHolder(result);
        result.setTag(holder);
        return result;
    }

    @Override
    protected void bindView(View v, int position, final UIConversation data) {
        final ViewHolder holder = (ViewHolder) v.getTag();
        if (holder != null){
            if (Conversation.ConversationType.GROUP == data.getConversationType()) {
//                holder.unReadMsg.setVisibility(View.GONE);
                if (data.getIconUrl()==null) {
                    if (holder.headIv!=null) {
                        holder.headIv.setImageResource(R.mipmap.group_headimg);
                    }
                } else {
                    if (!TextUtils.isEmpty(data.getIconUrl().toString())) {
                        ImageLoader.loadImage(data.getIconUrl().toString(), holder.headIv);
                    }
                }
                holder.content.setText(data.getConversationContent());
//                if (data.getUnReadMessageCount() > 0) {
//                    holder.unReadMsg.setVisibility(View.VISIBLE);
//                    holder.unReadMsg.setText(data.getUnReadMessageCount() + "");
//                } else {
//                    holder.unReadMsg.setVisibility(View.GONE);
//                }
                holder.time.setText(TimeUtils.millis2String(data.getUIConversationTime()));
                holder.deleteTv.setVisibility(View.VISIBLE);

                final String GroupId = data.getConversationTargetId();
                final GroupBean bean = UserInfoHelper.getIntance().getCircleFromMap(GroupId);

                RongIM.getInstance().getUnreadCount(Conversation.ConversationType.GROUP, GroupId, new RongIMClient.ResultCallback<Integer>() {
                    @Override
                    public void onSuccess(final Integer integer) {
                        if (integer==0){
                            holder.newremind.setVisibility(View.GONE);
                            holder.unReadMsg.setVisibility(View.GONE);
                            holder.undian.setVisibility(View.GONE);
                        }else if (integer<=99){
                            RongIM.getInstance().getConversationNotificationStatus(Conversation.ConversationType.GROUP, GroupId, new RongIMClient.ResultCallback<Conversation.ConversationNotificationStatus>() {
                                @Override
                                public void onSuccess(Conversation.ConversationNotificationStatus conversationNotificationStatus) {
                                    if (conversationNotificationStatus == Conversation.ConversationNotificationStatus.DO_NOT_DISTURB){
                                        holder.unReadMsg.setVisibility(View.GONE);
                                        holder.undian.setVisibility(View.VISIBLE);
                                        holder.newremind.setText("["+ integer +"条"+"]");
                                    }else if (conversationNotificationStatus == Conversation.ConversationNotificationStatus.NOTIFY){
                                        holder.unReadMsg.setVisibility(View.VISIBLE);
                                        holder.unReadMsg.setText(integer+"");
                                        holder.undian.setVisibility(View.GONE);
                                        holder.newremind.setVisibility(View.GONE);
                                    }
                                }

                                @Override
                                public void onError(RongIMClient.ErrorCode errorCode) {

                                }
                            });


                        }else {
                            RongIM.getInstance().getConversationNotificationStatus(Conversation.ConversationType.GROUP, GroupId, new RongIMClient.ResultCallback<Conversation.ConversationNotificationStatus>() {
                                @Override
                                public void onSuccess(Conversation.ConversationNotificationStatus conversationNotificationStatus) {
                                    if (conversationNotificationStatus == Conversation.ConversationNotificationStatus.DO_NOT_DISTURB){
                                        holder.unReadMsg.setVisibility(View.GONE);
                                        holder.undian.setVisibility(View.VISIBLE);
                                        holder.newremind.setText("["+ integer +"条"+"]");
                                    }else if (conversationNotificationStatus == Conversation.ConversationNotificationStatus.NOTIFY){
                                        holder.unReadMsg.setVisibility(View.VISIBLE);
                                        holder.unReadMsg.setText("···");
                                        holder.undian.setVisibility(View.GONE);
                                        holder.newremind.setVisibility(View.GONE);
                                    }
                                }

                                @Override
                                public void onError(RongIMClient.ErrorCode errorCode) {

                                }
                            });
                        }
                    }

                    @Override
                    public void onError(RongIMClient.ErrorCode errorCode) {

                    }
                });








                if (bean != null) {
                    if (!TextUtils.isEmpty(bean.getName())) {
                        holder.name.setText(bean.getName());
                        holder.name.setTextColor(ContextCompat.getColor(mContext,R.color.text_color_1));
                    }else {
                        holder.name.setText("未知圈子");
                    }






                    holder.menuLayout.addListener(new SwipeMenuLayout.SwipeListener() {
                        @Override
                        public void onUpdate(SwipeMenuLayout layout, float offset) {

                        }

                        @Override
                        public void onOpened(SwipeMenuLayout layout) {

                        }

                        @Override
                        public void onClosed(SwipeMenuLayout layout) {

                        }

                        @Override
                        public void onClick(SwipeMenuLayout layout) {
                            CircleActivity.startConversationActivity(mContext, bean);
                        }
                    });

                }
                final GroupBeanDao groupDao = BaseApp.getApplication().getDaoSession().getGroupBeanDao();
                holder.deleteTv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        List<GroupBean> list = groupDao.queryBuilder().offset(0).limit(1).orderAsc(GroupBeanDao.Properties.Id).build().list();
                        if (list.size()>0) {
                            Long id = list.get(0).getTheid();
                            dao.deleteByKey(id);
                        }
                        RongIM.getInstance().removeConversation(data.getConversationType(), data.getConversationTargetId(), (RongIMClient.ResultCallback) null);

                    }
                });
            }



        }
    }
    class ViewHolder {
        TextView name;
        TextView time;
        TextView content;
        TextView unReadMsg;
        ImageView headIv;
        ImageView undian;
        TextView newremind;

        public TextView deleteTv;


        public SwipeMenuLayout menuLayout;

        protected ViewHolder(View view) {
            name = (TextView) view.findViewById(R.id.mgroupNametv);
            time = (TextView) view.findViewById(R.id.mgroupTimetv);
            content = (TextView) view.findViewById(R.id.mremindcontenttv);
            unReadMsg = (TextView) view.findViewById(R.id.mtellnum);
            undian = (ImageView) view.findViewById(R.id.mtelldian);
            deleteTv = (TextView) view.findViewById(R.id.delete_tv);
            menuLayout = (SwipeMenuLayout) view.findViewById(R.id.swipe_content);
            headIv = (ImageView) view.findViewById(R.id.head_iv);
            newremind = (TextView) view.findViewById(R.id.newremind);

        }
    }
}
