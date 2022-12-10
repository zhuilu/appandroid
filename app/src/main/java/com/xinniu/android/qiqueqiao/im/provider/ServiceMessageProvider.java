//package com.xinniu.android.qiqueqiao.im.provider;
//
//import android.content.Context;
//import android.text.Spannable;
//import android.text.SpannableString;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ImageView;
//import android.widget.LinearLayout;
//import android.widget.RelativeLayout;
//import android.widget.TextView;
//
//import androidx.cardview.widget.CardView;
//
//import com.xinniu.android.qiqueqiao.R;
//import com.xinniu.android.qiqueqiao.activity.ServiceDetailActivity;
//import com.xinniu.android.qiqueqiao.im.message.ServiceMessage;
//import com.xinniu.android.qiqueqiao.utils.ImageLoader;
//import com.xinniu.android.qiqueqiao.utils.RoundImageView;
//
//import io.rong.imlib.model.Conversation;
//
///**
// * Created by lzq on 2017/12/14.
// */
//
//
//@ProviderTag(messageContent = ServiceMessage.class, showPortrait = false, centerInHorizontal = true)
//public class ServiceMessageProvider extends IContainerItemProvider.MessageProvider<ServiceMessage> {
//
//    private Context context;
//
//    class ViewHolder {
//        RoundImageView imgLogo;
//        TextView tv_tag_1;
//        TextView tv_tag_2;
//        TextView tv_tag_3;
//        RelativeLayout card_rl;
//        TextView titleTv;
//        CardView chatHeadRl;
//        TextView headTv;
//        ImageView leftheadIv;
//        ImageView rightheadIv;
//        LinearLayout itemRoot;
//    }
//
//    @Override
//    public View newView(Context context, ViewGroup group) {
//        this.context = context;
//        View view = LayoutInflater.from(context).inflate(R.layout.item_chat_list_service, group, false);
//        ViewHolder holder = new ViewHolder();
//        holder.titleTv = (TextView) view.findViewById(R.id.tv_title);
//        holder.imgLogo = (RoundImageView) view.findViewById(R.id.mcoop_detail_companyImg);
//        holder.tv_tag_1 = (TextView) view.findViewById(R.id.tv_tag_1);
//        holder.tv_tag_2 = (TextView) view.findViewById(R.id.tv_tag_2);
//        holder.tv_tag_3 = (TextView) view.findViewById(R.id.tv_tag_3);
//        holder.card_rl = (RelativeLayout) view.findViewById(R.id.card_rl);
//        holder.headTv = (TextView) view.findViewById(R.id.head_content);
//        holder.leftheadIv = (ImageView) view.findViewById(R.id.left_head_img_iv);
//        holder.rightheadIv = (ImageView) view.findViewById(R.id.right_head_img_iv);
//        holder.chatHeadRl = (CardView) view.findViewById(R.id.itemRoot);
//        holder.itemRoot = (LinearLayout) view.findViewById(R.id.root_item_ll);
//
//        view.setTag(holder);
//        return view;
//    }
//
//
//    @Override
//    public void bindView(View view, int i, final ServiceMessage headMessage, final UIMessage uiMessage) {
//        ViewHolder holder = (ViewHolder) view.getTag();
//        holder.chatHeadRl.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                ServiceDetailActivity.start(context, headMessage.id);
//
//
//            }
//        });
//        if (uiMessage.getConversationType() == Conversation.ConversationType.PRIVATE) {
//
////            if (!TextUtils.isEmpty(headMessage.provide_remark)&&!headMessage.provide_remark.equals("null")){
////
////            }else{
////            }
////            if (!TextUtils.isEmpty(headMessage.title)&&!headMessage.title.equals("null")){
//            holder.titleTv.setText(headMessage.title);
////            }else {
////                holder.titleTv.setText("");
////            }
//            if (headMessage.images != null && !headMessage.images.equals("null") && headMessage.images.length() > 0) {
//                String[] sourceStrArray = headMessage.images.split(",");
//                ImageLoader.loadImage(sourceStrArray[0], holder.imgLogo);
//            }
//
//            holder.leftheadIv.setVisibility(View.GONE);
//            holder.rightheadIv.setVisibility(View.GONE);
//
////        if (uiMessage.getConversationType() == Conversation.ConversationType.GROUP) {
////            if (uiMessage.getMessageDirection() == Message.MessageDirection.SEND) {//消息方向，自己发送的
////                holder.leftheadIv.setVisibility(View.INVISIBLE);
////                holder.rightheadIv.setVisibility(View.GONE);
////                if(headMessage.images.length()>0) {
////                    String[] sourceStrArray = headMessage.images.split(",");
////                    ImageLoader.loadImage(sourceStrArray[0], holder.rightheadIv);
////                }
////                holder.itemRoot.setBackgroundResource(R.mipmap.chat_rc_ic_bubble_right);
//////                holder.rightheadIv.setOnClickListener(new View.OnClickListener() {
//////                    @Override
//////                    public void onClick(View v) {
//////
//////                        PersonCentetActivity.start(context, uiMessage.getSenderUserId());
//////                    }
//////                });
////
////            } else {
////
////                holder.itemRoot.setBackgroundResource(R.mipmap.chat_rc_ic_bubble_left);
////                holder.leftheadIv.setVisibility(View.GONE);
////                holder.rightheadIv.setVisibility(View.INVISIBLE);
////                String[] sourceStrArray = headMessage.images.split(",");
////                ImageLoader.loadImage(sourceStrArray[0], holder.leftheadIv);
//////                holder.leftheadIv.setOnClickListener(new View.OnClickListener() {
//////                    @Override
//////                    public void onClick(View v) {
//////
//////                        PersonCentetActivity.start(context, uiMessage.getUserInfo().getUserId());
//////
//////                    }
//////                });
////
////            }
////            if(headMessage.images.length()>0) {
////                String[] sourceStrArray = headMessage.images.split(",");
////                ImageLoader.loadImage(sourceStrArray[0], holder.imgLogo);
////            }
////            holder.titleTv.setText(headMessage.title);
////            holder.card_rl.setBackgroundColor(context.getResources().getColor(R.color._1c91ff));
////            holder.card_rl.setPadding(0, 0, 0, 10);
////            holder.headTv.setVisibility(View.GONE);
////        }
//            if (headMessage.remark != null && !headMessage.remark.equals("null") &&headMessage.remark.length() > 0) {
//                String[] tags = headMessage.remark.split(",");
//
//                if (tags.length == 3) {
//                    holder.tv_tag_1.setVisibility(View.VISIBLE);
//                    holder.tv_tag_2.setVisibility(View.VISIBLE);
//                    holder.tv_tag_3.setVisibility(View.VISIBLE);
//                    holder.tv_tag_1.setText(tags[0]);
//                    holder.tv_tag_2.setText(tags[1]);
//                    holder.tv_tag_3.setText(tags[2]);
//                } else if (tags.length == 2) {
//                    holder.tv_tag_1.setVisibility(View.VISIBLE);
//                    holder.tv_tag_2.setVisibility(View.VISIBLE);
//                    holder.tv_tag_3.setVisibility(View.GONE);
//                    holder.tv_tag_1.setText(tags[0]);
//                    holder.tv_tag_2.setText(tags[1]);
//
//                } else if (tags.length == 1) {
//                    holder.tv_tag_1.setVisibility(View.VISIBLE);
//                    holder.tv_tag_2.setVisibility(View.GONE);
//                    holder.tv_tag_3.setVisibility(View.GONE);
//                    holder.tv_tag_1.setText(tags[0]);
//                }
//            } else {
//                holder.tv_tag_1.setVisibility(View.GONE);
//                holder.tv_tag_2.setVisibility(View.GONE);
//                holder.tv_tag_3.setVisibility(View.GONE);
//            }
//        }
//
//    }
//
//    @Override
//    public Spannable getContentSummary(ServiceMessage data) {
//        return new SpannableString(data.content);
//    }
//
//    @Override
//    public void onItemClick(View view, int i, ServiceMessage headMessage, UIMessage uiMessage) {
//        ServiceDetailActivity.start(context, headMessage.id);
//    }
//
//
//}