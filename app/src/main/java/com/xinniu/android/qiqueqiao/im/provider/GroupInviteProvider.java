//package com.xinniu.android.qiqueqiao.im.provider;
//
//import android.content.Context;
//import android.text.Spannable;
//import android.text.SpannableString;
//import android.text.TextUtils;
//import android.util.Log;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.RelativeLayout;
//import android.widget.TextView;
//
//import com.xinniu.android.qiqueqiao.R;
//import com.xinniu.android.qiqueqiao.activity.GroupMessageActivity;
//import com.xinniu.android.qiqueqiao.activity.PersonCentetActivity;
//import com.xinniu.android.qiqueqiao.im.message.GroupInviteMessage;
//import com.xinniu.android.qiqueqiao.utils.ImageLoader;
//import com.xinniu.android.qiqueqiao.utils.RoundImageView;
//import com.xinniu.android.qiqueqiao.widget.CircleImageView;
//
//
//import io.rong.imlib.model.Conversation;
//import io.rong.imlib.model.Message;
//
///**
// * Created by yuchance on 2019/1/11.
// */
//@ProviderTag(messageContent = GroupInviteMessage.class, showPortrait = true)
//public class GroupInviteProvider extends  MessageProvider<GroupInviteMessage> {
//
//    private Context context;
//    class inviteHolder{
//        TextView groupName;
//        RoundImageView imageView;
//        RelativeLayout positionRl;
//    }
//
//
//    @Override
//    public void bindView(View view, int i, GroupInviteMessage groupInviteMessage, UIMessage uiMessage) {
//        if (uiMessage.getConversationType() == Conversation.ConversationType.PRIVATE) {
//            inviteHolder holder = (inviteHolder) view.getTag();
//            if (uiMessage.getMessageDirection()== Message.MessageDirection.SEND){
//                RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) holder.positionRl.getLayoutParams();
//                params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
//
//                holder.positionRl.setLayoutParams(params);
//
//            }else if(uiMessage.getMessageDirection()== Message.MessageDirection.RECEIVE){
//                RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) holder.positionRl.getLayoutParams();
//                params.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
//                holder.positionRl.setLayoutParams(params);
//            }
//            if (groupInviteMessage!=null){
//                Log.d("==GroupInviteProvider", groupInviteMessage.toString());
//                if (!TextUtils.isEmpty(groupInviteMessage.head_pic)) {
//                    ImageLoader.loadAvter(groupInviteMessage.head_pic, holder.imageView);
//                }
//                if (!TextUtils.isEmpty(groupInviteMessage.name)){
//                    holder.groupName.setText(groupInviteMessage.name);
//                }
//            }
//
//
//        }
//
//
//
//    }
//
//    @Override
//    public Spannable getContentSummary(GroupInviteMessage groupInviteMessage) {
//        if (!TextUtils.isEmpty(groupInviteMessage.content)) {
//            return new SpannableString(groupInviteMessage.content);
//        }
//        return null;
//    }
//
//    @Override
//    public void onItemClick(View view, int i, GroupInviteMessage groupInviteMessage, UIMessage uiMessage) {
//        if (uiMessage.getConversationType() == Conversation.ConversationType.PRIVATE) {
//            if (groupInviteMessage.id!=0){
//                GroupMessageActivity.start(context,groupInviteMessage.id,"add");
//            }
//        }
//    }
//
//    @Override
//    public View newView(Context context, ViewGroup viewGroup) {
//        this.context = context;
//        View  view= LayoutInflater.from(context).inflate(R.layout.item_lx_invitegroup,viewGroup,false);
//        inviteHolder holder = new inviteHolder();
//        holder.groupName = (TextView) view.findViewById(R.id.item_invite_groupname);
//        holder.imageView = (RoundImageView) view.findViewById(R.id.item_invite_head);
//        holder.positionRl = (RelativeLayout) view.findViewById(R.id.mpositionRl);
//        view.setTag(holder);
//        return view;
//
//    }
//}
