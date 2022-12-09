package com.xinniu.android.qiqueqiao.im.provider;

import android.content.Context;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.xinniu.android.qiqueqiao.R;
import com.xinniu.android.qiqueqiao.activity.CoopDetailActivity;
import com.xinniu.android.qiqueqiao.activity.PersonCentetActivity;
import com.xinniu.android.qiqueqiao.im.message.HeadMessage;
import com.xinniu.android.qiqueqiao.im.message.ShareResourceMessage;
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
@ProviderTag(messageContent = ShareResourceMessage.class, showPortrait = true)
public class ShareResourceProvider extends IContainerItemProvider.MessageProvider<ShareResourceMessage>{

    private Context context;
    class ViewHolder{
        TextView titletv,nametv,positiontv;
        CircleImageView headimg;
        RelativeLayout positionRl;
    }



    @Override
    public void bindView(View view, int i, ShareResourceMessage shareResourceMessage, UIMessage uiMessage) {
        ViewHolder holder = (ViewHolder) view.getTag();
        if (uiMessage.getConversationType() == Conversation.ConversationType.PRIVATE) {
            if (shareResourceMessage!=null){
                if (uiMessage.getMessageDirection()== Message.MessageDirection.SEND){
                    RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) holder.positionRl.getLayoutParams();
                    params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
                    holder.positionRl.setLayoutParams(params);
                }else {
                    RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) holder.positionRl.getLayoutParams();
                    params.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
                    holder.positionRl.setLayoutParams(params);
                }

                if (!TextUtils.isEmpty(shareResourceMessage.head_pic)){
                    ImageLoader.loadAvter(shareResourceMessage.head_pic,holder.headimg);
                }
                if (!TextUtils.isEmpty(shareResourceMessage.realname)){
                    holder.nametv.setText(shareResourceMessage.realname);
                }
                if (!TextUtils.isEmpty(shareResourceMessage.title)){
                    holder.titletv.setText(shareResourceMessage.title);
                }
                holder.positiontv.setText(shareResourceMessage.company+shareResourceMessage.position);
            }

        }
    }

    @Override
    public Spannable getContentSummary(ShareResourceMessage shareResourceMessage) {
        if (!TextUtils.isEmpty(shareResourceMessage.content)) {
            return new SpannableString(shareResourceMessage.content);
        }
        return null;
    }

    @Override
    public void onItemClick(View view, int i, ShareResourceMessage shareResourceMessage, UIMessage uiMessage) {

        if (uiMessage.getConversationType() == Conversation.ConversationType.PRIVATE) {
            if (shareResourceMessage.id!=0){
                CoopDetailActivity.start(context,shareResourceMessage.id);
            }
        }
    }

    @Override
    public View newView(Context context, ViewGroup viewGroup) {
        this.context = context;
        View  view= LayoutInflater.from(context).inflate(R.layout.item_lx_sendresource,viewGroup,false);
        ViewHolder holder = new ViewHolder();
        holder.headimg = (CircleImageView) view.findViewById(R.id.mhead_img);
        holder.titletv = (TextView) view.findViewById(R.id.send_contenttv);
        holder.nametv = (TextView) view.findViewById(R.id.msend_nametv);
        holder.positiontv = (TextView) view.findViewById(R.id.msend_positiontv);
        holder.positionRl = (RelativeLayout) view.findViewById(R.id.positionRl);
        view.setTag(holder);
        return view;
    }
}
