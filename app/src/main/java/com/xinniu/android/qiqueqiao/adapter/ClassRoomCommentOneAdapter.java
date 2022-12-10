package com.xinniu.android.qiqueqiao.adapter;

import android.content.Context;
import android.text.Html;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xinniu.android.qiqueqiao.R;
import com.xinniu.android.qiqueqiao.bean.CommentBean;
import com.xinniu.android.qiqueqiao.utils.ImageLoader;
import com.xinniu.android.qiqueqiao.utils.TimeUtils;

import java.util.List;

/**
 * Created by yuchance on 2018/10/26.
 */

public class ClassRoomCommentOneAdapter extends BaseQuickAdapter<CommentBean.ListBean, BaseViewHolder> {


    private List<CommentBean.ListBean> data;
    private Context mContext;

    public ClassRoomCommentOneAdapter(Context context, int layoutResId, @Nullable List<CommentBean.ListBean> data) {
        super(layoutResId, data);
        this.data = data;
        this.mContext = context;
    }


    @Override
    protected void convert(final BaseViewHolder helper, final CommentBean.ListBean bean) {
        ImageLoader.loadAvter(bean.getHead_pic(), (ImageView) helper.getView(R.id.item_comment_headimg));
        helper.getView(R.id.item_comment_headimg).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setOnClick.setOnDetail(bean.getUser_id());
            }
        });
        helper.setText(R.id.item_comment_nametv, bean.getRealname());
        TextView nameTv = helper.getView(R.id.item_comment_nametv);
        if (bean.getIs_vip() == 0) {
            nameTv.setTextColor(ContextCompat.getColor(mContext, R.color.blue_bg_4B96F3));
        } else {
            nameTv.setTextColor(ContextCompat.getColor(mContext, R.color.king_color));
        }
        helper.setText(R.id.tv_time, TimeUtils.getTimeStateNew(bean.getCreate_time() + ""));

        helper.setText(R.id.item_comment_positiontv, "· " + bean.getPosition());
        helper.setText(R.id.item_commenttv, bean.getContent());
        nameTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setOnClick.setOnDetail(bean.getUser_id());
            }
        });
        helper.getView(R.id.item_comment_positiontv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setOnClick.setOnDetail(bean.getUser_id());
            }
        });

        helper.getView(R.id.item_comment_infoRl).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setOnClick.setGroupComment(helper.getLayoutPosition());
            }
        });

        if (bean.getIs_v() == 1) {

            helper.getView(R.id.item_comment_head_isv).setVisibility(View.VISIBLE);
        } else {
            helper.getView(R.id.item_comment_head_isv).setVisibility(View.GONE);

        }
        if(bean.getUpvote_num()>999){
            helper.setText(R.id.item_good_num, "999+");
        }else {
            helper.setText(R.id.item_good_num, bean.getUpvote_num() + "");
        }

        TextView tv_good_num = helper.getView(R.id.item_good_num);
        if (bean.getIs_upvote() == 1) {
            helper.setBackgroundRes(R.id.img_good, R.mipmap.good_blue);
            tv_good_num.setTextColor(mContext.getResources().getColor(R.color.blue_bg_4B96F3));
        } else {
            helper.setBackgroundRes(R.id.img_good, R.mipmap.good_gray);
            tv_good_num.setTextColor(mContext.getResources().getColor(R.color._666));
        }

        helper.getView(R.id.llayout_good).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setOnClick.setGood(helper.getLayoutPosition());
            }
        });

        if(bean.getReply()!=null) {

            helper.getView(R.id.coop_reply_comrl).setVisibility(View.VISIBLE);
            String str = "<font color='#4B96F3'>" + bean.getReply().getRealname() + ":</font>" + bean.getReply().getContent();
            helper.setText(R.id.coop_reply_commenttv, Html.fromHtml(str));
        }else{
            helper.getView(R.id.coop_reply_comrl).setVisibility(View.GONE);
        }
        if (bean.isNew()){
            helper.getView(R.id.item_commentview).setVisibility(View.VISIBLE);
            AlphaAnimation alphaAnimation = new AlphaAnimation(1,0);
            alphaAnimation.setDuration(3000);
            alphaAnimation.setFillAfter(true);
            alphaAnimation.setRepeatCount(1);
            helper.getView(R.id.item_commentview).setAnimation(alphaAnimation);
        }else {
            helper.getView(R.id.item_commentview).setVisibility(View.GONE);
        }

    }

    public interface setOnClick {
        void setOnDetail(int userId);

        void setGroupComment(int groupPosition);

        void setChildReply(int groupPosition, int childPosition);

        void setGood(int id);
    }

    private ClassRoomCommentOneAdapter.setOnClick setOnClick;

    public void setSetOnClick(ClassRoomCommentOneAdapter.setOnClick setOnClick) {
        this.setOnClick = setOnClick;
    }

    /**
     * 删除评论留言
     */

    public void deleteReplyData(int groupPostion){

        if (data.get(groupPostion)!=null){
            data.remove(groupPostion);
        }
        notifyDataSetChanged();

    }
}
