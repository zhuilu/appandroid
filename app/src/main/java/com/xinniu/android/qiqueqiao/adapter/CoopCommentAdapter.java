package com.xinniu.android.qiqueqiao.adapter;

import android.content.Context;
import android.graphics.Paint;
//import android.support.annotation.Nullable;
//import android.support.v4.content.ContextCompat;
//import android.support.v7.widget.LinearLayoutManager;
//import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xinniu.android.qiqueqiao.R;
import com.xinniu.android.qiqueqiao.bean.InquireBean;
import com.xinniu.android.qiqueqiao.user.UserInfoHelper;
import com.xinniu.android.qiqueqiao.utils.FullyLinearLayoutManager;
import com.xinniu.android.qiqueqiao.utils.ImageLoader;
import com.xinniu.android.qiqueqiao.utils.NoScrollRecyclerView;
import com.xinniu.android.qiqueqiao.utils.TimeUtils;
import com.xinniu.android.qiqueqiao.widget.CircleImageView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yuchance on 2018/10/26.
 */

public class CoopCommentAdapter extends BaseExpandableListAdapter {


    private List<InquireBean.ListBean> commentListx;
    private Context context;
    private int userIdx;


    public CoopCommentAdapter(Context context, List<InquireBean.ListBean> commentList) {
        this.context = context;
        this.commentListx = commentList;

    }


    public void setUserId(int userId) {
        this.userIdx = userId;
    }


    @Override
    public int getGroupCount() {
        return commentListx.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        if (commentListx.get(groupPosition).getReply() == null) {
            return 0;
        } else {
            if (commentListx.get(groupPosition).isOpen()) {
                return commentListx.get(groupPosition).getReply().size() > 0 ? commentListx.get(groupPosition).getReply().size() : 0;
            } else {
                return 3;
            }
        }
    }

    @Override
    public Object getGroup(int groupPosition) {
        return commentListx.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return commentListx.get(groupPosition).getReply().get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(final int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        GroupHolder groupHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_coop_comment, parent, false);
            groupHolder = new GroupHolder(convertView);
            convertView.setTag(groupHolder);
        } else {
            groupHolder = (GroupHolder) convertView.getTag();
        }
        ImageLoader.loadAvter(commentListx.get(groupPosition).getThumb_img(), groupHolder.headImg);
        groupHolder.headImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setOnClick.setOnDetail(commentListx.get(groupPosition).getFrom_uid());
            }
        });
        groupHolder.tv_name.setText(commentListx.get(groupPosition).getNickname());
        if (commentListx.get(groupPosition).isU()) {

            groupHolder.view_line.setVisibility(View.VISIBLE);
        } else {

            groupHolder.view_line.setVisibility(View.GONE);
        }
        if (commentListx.get(groupPosition).getIs_vip() == 0) {
            groupHolder.tv_name.setTextColor(ContextCompat.getColor(context, R.color.colorPrimary));
            groupHolder.view_line.setBackgroundColor(ContextCompat.getColor(context, R.color.colorPrimary));
        } else {
            groupHolder.tv_name.setTextColor(ContextCompat.getColor(context, R.color.king_color));
            groupHolder.view_line.setBackgroundColor(ContextCompat.getColor(context, R.color.king_color));
        }
        groupHolder.tv_time.setText(TimeUtils.getTimeStateNew(commentListx.get(groupPosition).getCreate_time() + ""));
//        groupHolder.groupRl.setLayoutAnimation(get);
        if (commentListx.get(groupPosition).getFrom_uid() == userIdx) {
            groupHolder.isPush.setVisibility(View.VISIBLE);
            groupHolder.isPush.setText("发布人");
            groupHolder.positionTv.setMaxEms(6);
        } else {
            groupHolder.isPush.setVisibility(View.GONE);
            groupHolder.isPush.setText("");
            groupHolder.positionTv.setMaxEms(11);

        }
        groupHolder.positionTv.setText(commentListx.get(groupPosition).getCompany() + commentListx.get(groupPosition).getPosition());
        groupHolder.tv_content.setText(commentListx.get(groupPosition).getContent());
        groupHolder.tv_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setOnClick.setOnDetail(commentListx.get(groupPosition).getFrom_uid());
            }
        });
        groupHolder.positionTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setOnClick.setOnDetail(commentListx.get(groupPosition).getFrom_uid());
            }
        });
        groupHolder.groupRl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setOnClick.setGroupComment(groupPosition);
            }
        });
        groupHolder.item_comment_infoRl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setOnClick.setGroupComment(groupPosition);
            }
        });
        if (commentListx.get(groupPosition).isNew()) {
            groupHolder.groupRl.setVisibility(View.VISIBLE);
            AlphaAnimation alphaAnimation = new AlphaAnimation(1, 0);
            alphaAnimation.setDuration(3000);
            alphaAnimation.setFillAfter(true);
            alphaAnimation.setRepeatCount(1);
            groupHolder.groupRl.setAnimation(alphaAnimation);
        } else {
            groupHolder.groupRl.setVisibility(View.GONE);
        }
        if (commentListx.get(groupPosition).getIs_v() == 1) {
            groupHolder.isvImg.setVisibility(View.VISIBLE);
        } else {
            groupHolder.isvImg.setVisibility(View.GONE);
        }


        return convertView;
    }

    @Override
    public View getChildView(final int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {

        ChildHolder childHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_coop_comment_reply, parent, false);
            childHolder = new ChildHolder(convertView);
            convertView.setTag(childHolder);
        } else {
            childHolder = (ChildHolder) convertView.getTag();
        }
        childHolder.tv_name.setText(commentListx.get(groupPosition).getReply().get(childPosition).getFrom_nickname());
        if (commentListx.get(groupPosition).getReply().get(childPosition).isU()) {

            childHolder.view_line_child.setVisibility(View.VISIBLE);


        } else {
            childHolder.view_line_child.setVisibility(View.GONE);
        }

        if (commentListx.get(groupPosition).getReply().get(childPosition).getIs_vip() == 0) {
            childHolder.tv_name.setTextColor(ContextCompat.getColor(context, R.color.colorPrimary));
            childHolder.view_line_child.setBackgroundColor(ContextCompat.getColor(context, R.color.colorPrimary));
        } else {
            childHolder.tv_name.setTextColor(ContextCompat.getColor(context, R.color.king_color));
            childHolder.view_line_child.setBackgroundColor(ContextCompat.getColor(context, R.color.king_color));
        }

        childHolder.tv_time.setText(TimeUtils.getTimeStateNew(commentListx.get(groupPosition).getReply().get(childPosition).getCreate_time() + ""));
        if (commentListx.get(groupPosition).getReply().get(childPosition).getFrom_uid() == userIdx) {
            childHolder.tv_ispush.setVisibility(View.VISIBLE);
            childHolder.tv_ispush.setText("发布人");
            childHolder.tv_position.setMaxEms(6);
        } else {
            childHolder.tv_ispush.setVisibility(View.GONE);
            childHolder.tv_ispush.setText("");
            childHolder.tv_position.setMaxEms(11);


        }
        childHolder.tv_position.setText(commentListx.get(groupPosition).getReply().get(childPosition).getFrom_company() + commentListx.get(groupPosition).getReply().get(childPosition).getFrom_position());
        childHolder.tv_content.setText(commentListx.get(groupPosition).getReply().get(childPosition).getContent());
        if (commentListx.get(groupPosition).getReply().get(childPosition).getReply_type() == 1) {
            childHolder.isreply.setVisibility(View.GONE);
            childHolder.replyName.setVisibility(View.GONE);
            childHolder.isreply.setText("");
            childHolder.replyName.setText("");
        } else {
            childHolder.isreply.setVisibility(View.VISIBLE);
            childHolder.replyName.setVisibility(View.VISIBLE);
            childHolder.isreply.setText("回复");
            childHolder.replyName.setText(commentListx.get(groupPosition).getReply().get(childPosition).getTo_nickname()+" :");
            if (commentListx.get(groupPosition).getReply().get(childPosition).getTo_is_vip() == 0) {
                childHolder.replyName.setTextColor(ContextCompat.getColor(context, R.color.colorPrimary));
            } else {
                childHolder.replyName.setTextColor(ContextCompat.getColor(context, R.color.king_color));

            }
        }
        if (!commentListx.get(groupPosition).isOpen()) {
            if (childPosition == 2) {
                childHolder.moreRl.setVisibility(View.VISIBLE);
            } else {
                childHolder.moreRl.setVisibility(View.GONE);
            }
        } else {
            childHolder.moreRl.setVisibility(View.GONE);
        }
        childHolder.commentmore.setText("查看全部" + commentListx.get(groupPosition).getReply().size() + "条回复");
        childHolder.moreRl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                commentListx.get(groupPosition).setOpen(true);
                notifyDataSetChanged();
            }
        });
        childHolder.replycommentRl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setOnClick.setChildReply(groupPosition, childPosition);
            }
        });
        childHolder.replyRl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setOnClick.setChildReply(groupPosition, childPosition);
            }
        });

        childHolder.tv_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setOnClick.setOnDetail(commentListx.get(groupPosition).getReply().get(childPosition).getFrom_uid());
            }
        });

        if (commentListx.get(groupPosition).getReply().get(childPosition).isNew()) {
            childHolder.childview.setVisibility(View.VISIBLE);
            AlphaAnimation alphaAnimation = new AlphaAnimation(1, 0);
            alphaAnimation.setDuration(3000);
            alphaAnimation.setFillAfter(true);
            alphaAnimation.setRepeatCount(1);
            childHolder.childview.setAnimation(alphaAnimation);
        } else {
            childHolder.childview.setVisibility(View.GONE);
        }


        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    private class GroupHolder {
        private CircleImageView headImg;
        private TextView tv_name, tv_content, tv_time, positionTv, isPush;
        private View groupRl;
        private ImageView isvImg;
        private RelativeLayout item_comment_infoRl;
        private View view_line;


        public GroupHolder(View view) {
            headImg = (CircleImageView) view.findViewById(R.id.item_comment_headimg);
            tv_name = (TextView) view.findViewById(R.id.item_comment_nametv);
            tv_time = (TextView) view.findViewById(R.id.item_comment_timetv);
            tv_content = (TextView) view.findViewById(R.id.item_commenttv);
            positionTv = (TextView) view.findViewById(R.id.item_comment_positiontv);
            isPush = (TextView) view.findViewById(R.id.item_comment_ispushtv);
            groupRl = view.findViewById(R.id.item_commentview);
            isvImg = (ImageView) view.findViewById(R.id.item_comment_head_isv);
            item_comment_infoRl = (RelativeLayout) view.findViewById(R.id.item_comment_infoRl);
            view_line = view.findViewById(R.id.view);
        }
    }

    private class ChildHolder {
        private TextView tv_name, tv_content, tv_time, tv_ispush, tv_position, isreply, replyName, commentmore;
        RelativeLayout moreRl;
        RelativeLayout replycommentRl;
        RelativeLayout replyRl;
        View childview;
         View view_line_child;

        public ChildHolder(View view) {
            tv_name = (TextView) view.findViewById(R.id.coop_reply_nametv);
            tv_content = (TextView) view.findViewById(R.id.coop_reply_commenttv);
            tv_time = (TextView) view.findViewById(R.id.coop_reply_timetv);
            tv_ispush = (TextView) view.findViewById(R.id.coop_reply_ispushtv);
            tv_position = (TextView) view.findViewById(R.id.coop_reply_positiontv);
            isreply = (TextView) view.findViewById(R.id.item_is_reply);
            replyName = (TextView) view.findViewById(R.id.item_reply_name);
            moreRl = (RelativeLayout) view.findViewById(R.id.comment_moreRl);
            commentmore = (TextView) view.findViewById(R.id.comment_more);
            replycommentRl = (RelativeLayout) view.findViewById(R.id.coop_reply_commentRl);
            replyRl = (RelativeLayout) view.findViewById(R.id.coop_reply_comrl);
            childview = view.findViewById(R.id.ychildview);
            view_line_child = view.findViewById(R.id.view);
        }
    }


//    @Override
//    public int getChildType(int groupPosition, int childPosition) {
//        int type = 2;
//       if (commentListx.get(groupPosition).getReply().get(childPosition).getReply_type() == InquireBean.ListBean.ReplyBean.COMMMON){
//           type = InquireBean.ListBean.ReplyBean.COMMMON;
//       }else if (commentListx.get(groupPosition).getReply().get(childPosition).getReply_type() == InquireBean.ListBean.ReplyBean.MORE){
//           type = InquireBean.ListBean.ReplyBean.MORE;
//       }
//        return type;
//    }

//    private class ChildHolderMore{
//        private TextView tvmore;
//
//        public ChildHolderMore(View view) {
//            tvmore = (TextView) view.findViewById(R.id.comment_more);
//        }
//    }


    /**
     * by moos on 2018/04/20
     * func:评论成功后插入一条数据
     *
     * @param listBean 新的评论数据
     */
    public void addTheCommentData(InquireBean.ListBean listBean) {
        if (commentListx != null) {
            commentListx.add(0, listBean);
            notifyDataSetChanged();
        } else {
            throw new IllegalArgumentException("评论数据为空!");
        }

    }

    /**
     * by moos on 2018/04/20
     * func:回复成功后插入一条数据
     *
     * @param replyBean 新的回复数据
     */
    public void addTheReplyData(InquireBean.ListBean replyBean, int groupPosition) {
        if (replyBean != null) {
//            Log.e(TAG, "addTheReplyData: >>>>该刷新回复列表了:"+replyDetailBean.toString() );
            if (commentListx.get(groupPosition) != null) {
                commentListx.set(groupPosition, replyBean);
                commentListx.get(groupPosition).setOpen(true);
            }
            notifyDataSetChanged();
        } else {
            throw new IllegalArgumentException("回复数据为空!");
        }

    }

    /**
     * 回复内容插入
     */
    public void addTheCommentReData(InquireBean.ListBean replyBean, int groupPosition, int childPostion) {
        if (replyBean != null) {
//            Log.e(TAG, "addTheReplyData: >>>>该刷新回复列表了:"+replyDetailBean.toString() );
            if (commentListx.get(groupPosition) != null) {
                commentListx.set(groupPosition, replyBean);
                commentListx.get(groupPosition).setOpen(true);
                for (int i = 0; i < commentListx.size(); i++) {
                    commentListx.get(i).setNew(false);
                    for (int j = 0; j < commentListx.get(i).getReply().size(); j++) {
                        commentListx.get(i).getReply().get(j).setNew(false);
                    }
                }
                commentListx.get(groupPosition).getReply().get(commentListx.get(groupPosition).getReply().size() - 1).setNew(true);
            }
            notifyDataSetChanged();
        } else {
            throw new IllegalArgumentException("回复数据为空!");
        }

    }


    /**
     * 删除评论留言
     */

    public void deleteReplyData(int groupPostion) {

        if (commentListx.get(groupPostion) != null) {
            commentListx.remove(groupPostion);
        }
        notifyDataSetChanged();

    }

    /**
     * 删除评论回复
     */
    public void deleteReplyData(int groupPostion, int childPosition) {
        if (commentListx.get(groupPostion).getReply().get(childPosition) != null) {
            commentListx.get(groupPostion).getReply().remove(childPosition);
        }
        notifyDataSetChanged();
    }

    public interface setOnClick {
        void setOnDetail(int userId);

        void setGroupComment(int groupPosition);

        void setChildReply(int groupPosition, int childPosition);
    }

    private setOnClick setOnClick;

    public void setSetOnClick(CoopCommentAdapter.setOnClick setOnClick) {
        this.setOnClick = setOnClick;
    }
}
