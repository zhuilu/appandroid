package com.xinniu.android.qiqueqiao.im.provider;

import android.content.Context;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.xinniu.android.qiqueqiao.R;
import com.xinniu.android.qiqueqiao.activity.PersonCentetActivity;
import com.xinniu.android.qiqueqiao.im.message.HeadMessage;
import com.xinniu.android.qiqueqiao.im.message.ShareCardMessage;
import com.xinniu.android.qiqueqiao.utils.ImageLoader;
import com.xinniu.android.qiqueqiao.widget.CircleImageView;

import io.rong.imkit.model.ProviderTag;
import io.rong.imkit.model.UIMessage;
import io.rong.imkit.widget.provider.IContainerItemProvider;
import io.rong.imlib.model.Conversation;
import io.rong.imlib.model.Message;

/**
 * Created by yuchance on 2018/11/30.
 */
@ProviderTag(messageContent = ShareCardMessage.class, showPortrait = true)
public class ShareCardProvider extends IContainerItemProvider.MessageProvider<ShareCardMessage>{

    private Context context;
    class SendHolder{
        TextView nameTv,positionTv;
        CircleImageView headImg;
        RelativeLayout positionRl;
    }





    @Override
    public void bindView(View view, int i, ShareCardMessage shareCardMessage, UIMessage uiMessage) {

        if (uiMessage.getConversationType() == Conversation.ConversationType.PRIVATE) {
            SendHolder holder = (SendHolder) view.getTag();
            if (uiMessage.getMessageDirection()== Message.MessageDirection.SEND){
                RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) holder.positionRl.getLayoutParams();
                params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
                holder.positionRl.setLayoutParams(params);
                
            }else if(uiMessage.getMessageDirection()== Message.MessageDirection.RECEIVE){
                RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) holder.positionRl.getLayoutParams();
                params.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
                holder.positionRl.setLayoutParams(params);

            }
            if (shareCardMessage!=null){
                if (!TextUtils.isEmpty(shareCardMessage.head_pic)) {
                    ImageLoader.loadAvter(shareCardMessage.head_pic, holder.headImg);
                }
                if (!TextUtils.isEmpty(shareCardMessage.realname)){
                    holder.nameTv.setText(shareCardMessage.realname);
                }
                holder.positionTv.setText(shareCardMessage.company+shareCardMessage.position);
            }


        }
    }

    @Override
    public Spannable getContentSummary(ShareCardMessage shareCardMessage) {
        if (!TextUtils.isEmpty(shareCardMessage.content)) {
            return new SpannableString(shareCardMessage.content);
        }
        return null;
    }

    @Override
    public void onItemClick(View view, int i, ShareCardMessage shareCardMessage, UIMessage uiMessage) {
        if (uiMessage.getConversationType() == Conversation.ConversationType.PRIVATE) {
            if (shareCardMessage.user_id!=0){
                PersonCentetActivity.start(context,shareCardMessage.user_id+"");
            }
        }
    }

    @Override
    public View newView(Context context, ViewGroup viewGroup) {
        this.context = context;
        View  view= LayoutInflater.from(context).inflate(R.layout.item_lx_sendcard,viewGroup,false);
        SendHolder holder = new SendHolder();
        holder.headImg = (CircleImageView) view.findViewById(R.id.mcard_circleimg);
        holder.nameTv = (TextView) view.findViewById(R.id.mcard_nametv);
        holder.positionTv = (TextView) view.findViewById(R.id.mcard_positiontv);
        holder.positionRl = (RelativeLayout) view.findViewById(R.id.mpositionRl);
        view.setTag(holder);
        return view;

    }
}
