//package com.xinniu.android.qiqueqiao.im.provider;
//
//import android.content.Context;
//import android.text.Spannable;
//import android.text.SpannableString;
//import android.text.TextUtils;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.RelativeLayout;
//import android.widget.TextView;
//
//import com.xinniu.android.qiqueqiao.R;
//import com.xinniu.android.qiqueqiao.activity.TransactionDetailsActivity;
//import com.xinniu.android.qiqueqiao.im.message.SecuredTransactionMessage;
//
//import io.rong.imlib.model.Conversation;
//import io.rong.imlib.model.Message;
//
///**
// * Created by yuchance on 2018/11/30.
// */
//@ProviderTag(messageContent = SecuredTransactionMessage.class, showPortrait = true)
//public class SecuredTransactionProvider extends IContainerItemProvider.MessageProvider<SecuredTransactionMessage> {
//
//    private Context context;
//
//    class ViewHolder {
//        TextView send_contenttv, tv_content, tv_look_detail;
//        RelativeLayout positionRl;
//    }
//
//
//    @Override
//    public void bindView(View view, int i, SecuredTransactionMessage shareResourceMessage, UIMessage uiMessage) {
//        ViewHolder holder = (ViewHolder) view.getTag();
//        if (uiMessage.getConversationType() == Conversation.ConversationType.PRIVATE) {
//            if (shareResourceMessage != null) {
////                if (uiMessage.getMessageDirection() == Message.MessageDirection.SEND) {
////                    RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) holder.positionRl.getLayoutParams();
////                    params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
////                    holder.positionRl.setLayoutParams(params);
////                } else {
////                    RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) holder.positionRl.getLayoutParams();
////                    params.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
////                    holder.positionRl.setLayoutParams(params);
////                }
//
//                if (!TextUtils.isEmpty(shareResourceMessage.text)) {
//                    holder.tv_content.setText(shareResourceMessage.text);
//                }
//                if (!TextUtils.isEmpty(shareResourceMessage.estimated_amount)) {
//
//                    if (shareResourceMessage.estimated_amount.contains(".")) {
//                        String[] pricr01 = shareResourceMessage.estimated_amount.split("\\.");
//                        holder.send_contenttv.setText("￥ " + pricr01[0]);//担保金额
//                    } else {
//                        holder.send_contenttv.setText("￥" + shareResourceMessage.estimated_amount);
//                    }
//
//                }
//            }
//
//        }
//    }
//
//    @Override
//    public Spannable getContentSummary(SecuredTransactionMessage shareResourceMessage) {
//        if (!TextUtils.isEmpty(shareResourceMessage.content)) {
//            return new SpannableString(shareResourceMessage.content);
//        }
//        return null;
//    }
//
//    @Override
//    public void onItemClick(View view, int i, SecuredTransactionMessage shareResourceMessage, UIMessage uiMessage) {
//
//        if (uiMessage.getConversationType() == Conversation.ConversationType.PRIVATE) {
//            if (shareResourceMessage.id != 0) {
//                //交易详情
//                TransactionDetailsActivity.start(context, shareResourceMessage.id + "");
//            }
//        }
//    }
//
//    @Override
//    public View newView(Context context, ViewGroup viewGroup) {
//        this.context = context;
//        View view = LayoutInflater.from(context).inflate(R.layout.item_lx_send_secured_transactions, viewGroup, false);
//        ViewHolder holder = new ViewHolder();
//        holder.send_contenttv = (TextView) view.findViewById(R.id.send_contenttv);
//        holder.tv_content = (TextView) view.findViewById(R.id.tv_content);
//        holder.tv_look_detail = (TextView) view.findViewById(R.id.tv_look_detail);
//        holder.positionRl = (RelativeLayout) view.findViewById(R.id.positionRlayout);
//        view.setTag(holder);
//        return view;
//    }
//}
