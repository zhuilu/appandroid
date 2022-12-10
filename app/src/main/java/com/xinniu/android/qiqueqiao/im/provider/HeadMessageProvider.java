//package com.xinniu.android.qiqueqiao.im.provider;
//
//import android.content.Context;
//import android.content.Intent;
//import android.graphics.Color;
////import android.support.v4.content.ContextCompat;
////import android.support.v7.widget.CardView;
//import android.text.Spannable;
//import android.text.SpannableString;
//import android.text.TextUtils;
//import android.util.Log;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ImageView;
//import android.widget.LinearLayout;
//import android.widget.RelativeLayout;
//import android.widget.TextView;
//
//import androidx.cardview.widget.CardView;
//import androidx.core.content.ContextCompat;
//
//import com.xinniu.android.qiqueqiao.R;
//import com.xinniu.android.qiqueqiao.activity.CoopDetailActivity;
//import com.xinniu.android.qiqueqiao.activity.PersonCentetActivity;
//import com.xinniu.android.qiqueqiao.bean.OtherUserInfo;
//import com.xinniu.android.qiqueqiao.im.message.HeadMessage;
//import com.xinniu.android.qiqueqiao.user.UserInfoHelper;
//import com.xinniu.android.qiqueqiao.utils.ImageLoader;
//import com.xinniu.android.qiqueqiao.utils.Logger;
//import com.xinniu.android.qiqueqiao.utils.StringUtils;
//
//
//import io.rong.imlib.model.Conversation;
//import io.rong.imlib.model.Message;
//
///**
// * Created by lzq on 2017/12/14.
// */
//
//
//
//public class HeadMessageProvider    {
//
//    private Context context;
//
//    class ViewHolder {
//        TextView tvOut;
//        TextView tvNeed;
//        TextView tvType;
//        ImageView imgLogo;
//        TextView tvName;
//        TextView tvInfo;
//        TextView tvLookNum;
//        TextView tvTalkNum;
//        LinearLayout itemRoot;
//        RelativeLayout card_rl;
//        LinearLayout view1;
//        TextView headTv;
//        ImageView leftheadIv;
//        ImageView rightheadIv;
//        LinearLayout contentView;
//        TextView titleTv;
//        ImageView isVImg;
//        ImageView isVipImg;
//        CardView chatHeadRl;
//    }
//
//    @Override
//    public View newView(Context context, ViewGroup group) {
//        this.context = context;
//        View view = LayoutInflater.from(context).inflate(R.layout.item_chat_list_head, group, false);
//        ViewHolder holder = new ViewHolder();
//        holder.titleTv = (TextView) view.findViewById(R.id.listhead_title);
//        holder.tvType = (TextView) view.findViewById(R.id.tvType);
//        holder.imgLogo = (ImageView) view.findViewById(R.id.imgLogo);
//        holder.tvName = (TextView) view.findViewById(R.id.tvName);
//        holder.tvInfo = (TextView) view.findViewById(R.id.tvInfo);
//        holder.itemRoot = (LinearLayout) view.findViewById(R.id.root_item_ll);
//        holder.card_rl = (RelativeLayout) view.findViewById(R.id.card_rl);
//        holder.view1 = (LinearLayout) view.findViewById(R.id.view1);
//        holder.headTv = (TextView) view.findViewById(R.id.head_content);
//        holder.leftheadIv = (ImageView) view.findViewById(R.id.left_head_img_iv);
//        holder.rightheadIv = (ImageView) view.findViewById(R.id.right_head_img_iv);
//        holder.contentView = (LinearLayout) view.findViewById(R.id.view1);
//        holder.isVImg = (ImageView) view.findViewById(R.id.imgLogo_v);
//        holder.isVipImg = (ImageView) view.findViewById(R.id.vip_img);
//        holder.chatHeadRl = (CardView) view.findViewById(R.id.itemRoot);
//
//        view.setTag(holder);
//        return view;
//    }
//
//
////    @Override
////    public void bindView(View view, int i, final HeadMessage headMessage, final UIMessage uiMessage) {
////        ViewHolder holder = (ViewHolder) view.getTag();
////        holder.chatHeadRl.setOnClickListener(new View.OnClickListener() {
////            @Override
////            public void onClick(View v) {
////                CoopDetailActivity.start(context,headMessage.id);
//////                Intent intent = new Intent(context,CoopDetailActivity.class);
////
////
////            }
////        });
////        if (uiMessage.getConversationType() == Conversation.ConversationType.PRIVATE) {
////
//////            if (!TextUtils.isEmpty(headMessage.provide_remark)&&!headMessage.provide_remark.equals("null")){
//////
//////            }else{
//////            }
//////            if (!TextUtils.isEmpty(headMessage.title)&&!headMessage.title.equals("null")){
////                holder.titleTv.setText( "【"+headMessage.company +"】" +headMessage.title);
//////            }else {
//////                holder.titleTv.setText("");
//////            }
////            if (headMessage.isV == 1){
////                holder.isVImg.setVisibility(View.VISIBLE);
////            }else {
////                holder.isVImg.setVisibility(View.GONE);
////            }
////            if (headMessage.isVip == 0){
////                holder.isVipImg.setVisibility(View.GONE);
////            }else if (headMessage.isVip == 1){
////                holder.isVipImg.setBackground(ContextCompat.getDrawable(context,R.mipmap.vip_iconx));
////            }else if (headMessage.isVip == 2){
////                holder.isVipImg.setBackground(ContextCompat.getDrawable(context,R.mipmap.svip_iconx));
////            }else {
////                holder.isVipImg.setVisibility(View.GONE);
////            }
////
////
////            ImageLoader.loadImage( headMessage.head_pic, holder.imgLogo);
////            holder.tvName.setText(headMessage.realname);
////            holder.tvInfo.setText(headMessage.company);
////            holder.leftheadIv.setVisibility(View.GONE);
////            holder.rightheadIv.setVisibility(View.GONE);
////        }
////        if (uiMessage.getConversationType() == Conversation.ConversationType.GROUP) {
////            if (uiMessage.getMessageDirection() == Message.MessageDirection.SEND) {//消息方向，自己发送的
////                holder.leftheadIv.setVisibility(View.INVISIBLE);
////                holder.rightheadIv.setVisibility(View.GONE);
////                ImageLoader.loadImage( headMessage.head_pic, holder.rightheadIv);
////                holder.itemRoot.setBackgroundResource(R.mipmap.chat_rc_ic_bubble_right);
////                holder.rightheadIv.setOnClickListener(new View.OnClickListener() {
////                    @Override
////                    public void onClick(View v) {
////
////                        PersonCentetActivity.start(context,uiMessage.getSenderUserId());
////                    }
////                });
////                holder.contentView.setBackgroundColor(Color.parseColor("#a3d3fb"));
////            } else {
////
////                holder.itemRoot.setBackgroundResource(R.mipmap.chat_rc_ic_bubble_left);
////                holder.leftheadIv.setVisibility(View.GONE);
////                holder.rightheadIv.setVisibility(View.INVISIBLE);
////                ImageLoader.loadImage( headMessage.head_pic, holder.leftheadIv);
////                holder.leftheadIv.setOnClickListener(new View.OnClickListener() {
////                    @Override
////                    public void onClick(View v) {
////
////                        PersonCentetActivity.start(context,uiMessage.getUserInfo().getUserId());
////
////                    }
////                });
////                holder.contentView.setBackgroundColor(context.getResources().getColor(R.color.white));
////            }
////
////            ImageLoader.loadImage( headMessage.head_pic, holder.imgLogo);
////            holder.tvName.setText(StringUtils.hintString(headMessage.realname,5));
////            holder.tvInfo.setText(headMessage.company);
////            holder.titleTv.setText(headMessage.title);
////            holder.card_rl.setBackgroundColor(context.getResources().getColor(R.color._1c91ff));
////            holder.card_rl.setPadding(0, 0, 0, 10);
////
////            holder.tvName.setTextColor(context.getResources().getColor(R.color.white));
////            holder.tvInfo.setVisibility(View.GONE);
////            holder.headTv.setVisibility(View.GONE);
////        }
////    }
////
////    @Override
////    public Spannable getContentSummary(HeadMessage data) {
////        return new SpannableString(data.content);
////    }
////
////    @Override
////    public void onItemClick(View view, int i, HeadMessage headMessage, UIMessage uiMessage) {
////        CoopDetailActivity.start(context,headMessage.id);
////    }
//
//
//}