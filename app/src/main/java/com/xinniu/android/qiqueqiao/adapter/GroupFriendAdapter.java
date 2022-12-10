package com.xinniu.android.qiqueqiao.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.xinniu.android.qiqueqiao.R;
import com.xinniu.android.qiqueqiao.activity.PersonCentetActivity;
import com.xinniu.android.qiqueqiao.bean.GroupFriendBean;
import com.xinniu.android.qiqueqiao.utils.ImageLoader;
import com.xinniu.android.qiqueqiao.utils.StringUtils;
import com.xinniu.android.qiqueqiao.widget.CircleImageView;
import com.xinniu.android.qiqueqiao.widget.SwipeMenuLayout;

import java.util.List;

public class GroupFriendAdapter extends BaseExpandableListAdapter {
    private List<GroupFriendBean> commentListx;
    private Context context;


    public GroupFriendAdapter(List<GroupFriendBean> commentListx, Context context) {
        this.commentListx = commentListx;
        this.context = context;

    }

    @Override
    public int getGroupCount() {
        return commentListx.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        if (commentListx.get(groupPosition).getUser_list() == null) {
            return 0;
        } else {
            return commentListx.get(groupPosition).getUser_list().size() > 0 ? commentListx.get(groupPosition).getUser_list().size() : 0;

        }
    }

    @Override
    public Object getGroup(int groupPosition) {
        return commentListx.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return commentListx.get(groupPosition).getUser_list().get(childPosition);
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
            convertView = LayoutInflater.from(context).inflate(R.layout.item_group, parent, false);
            groupHolder = new GroupHolder(convertView);
            convertView.setTag(groupHolder);
        } else {
            groupHolder = (GroupHolder) convertView.getTag();
        }
        groupHolder.tv_name.setText(commentListx.get(groupPosition).getName());
        groupHolder.tv_num.setText(commentListx.get(groupPosition).getCount() + "");
        if (isExpanded) {
            groupHolder.img.setBackgroundResource(R.mipmap.group_down);
        } else {
            groupHolder.img.setBackgroundResource(R.mipmap.grop);
        }

        groupHolder.swipe_content.addListener(new SwipeMenuLayout.SwipeListener() {
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

                setOnClick.setOnClick(5, groupPosition, -1, "");
            }
        });
        if (commentListx.get(groupPosition).getGroup_id() == -1) {
            //全部成员

            groupHolder.tv_update_group_name.setVisibility(View.GONE);
            groupHolder.tv_delete.setVisibility(View.GONE);
        } else {
            groupHolder.tv_update_group_name.setVisibility(View.VISIBLE);
            groupHolder.tv_delete.setVisibility(View.VISIBLE);
        }

        groupHolder.tv_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //删除
                setOnClick.setOnClick(4, groupPosition, -1, "");

            }
        });
        groupHolder.tv_update_group_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //修改组名
                setOnClick.setOnClick(2, groupPosition, -1, "");
            }
        });
        return convertView;
    }

    @Override
    public View getChildView(final int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        ChildHolder childHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_group_data, parent, false);
            childHolder = new ChildHolder(convertView);
            convertView.setTag(childHolder);
        } else {
            childHolder = (ChildHolder) convertView.getTag();
        }

        ImageLoader.loadAvter(commentListx.get(groupPosition).getUser_list().get(childPosition).getHead_pic(), childHolder.headImg);
        childHolder.tv_name.setText(commentListx.get(groupPosition).getUser_list().get(childPosition).getRealname());
        int isVip = commentListx.get(groupPosition).getUser_list().get(childPosition).getIs_vip();
        if (isVip == 0) {
            childHolder.item_index_vipImg.setVisibility(View.GONE);
            childHolder.tv_name.setTextColor(ContextCompat.getColor(context, R.color._333));

        } else if (isVip == 1) {
            childHolder.item_index_vipImg.setVisibility(View.VISIBLE);
            childHolder.item_index_vipImg.setBackgroundResource(R.mipmap.vip_iconx);
            childHolder.tv_name.setTextColor(ContextCompat.getColor(context, R.color.king_color));
        } else if (isVip == 2) {
            childHolder.item_index_vipImg.setVisibility(View.VISIBLE);
            childHolder.item_index_vipImg.setBackgroundResource(R.mipmap.svip_iconx);
            childHolder.tv_name.setTextColor(ContextCompat.getColor(context, R.color.king_color));
        }

        if (StringUtils.isEmpty(commentListx.get(groupPosition).getUser_list().get(childPosition).getCompany()) && StringUtils.isEmpty(commentListx.get(groupPosition).getUser_list().get(childPosition).getPosition())) {
            childHolder.tv_position.setText("");
        } else {
            if (StringUtils.isEmpty(commentListx.get(groupPosition).getUser_list().get(childPosition).getCompany())) {
                childHolder.tv_position.setText(commentListx.get(groupPosition).getUser_list().get(childPosition).getPosition());
            } else if (StringUtils.isEmpty(commentListx.get(groupPosition).getUser_list().get(childPosition).getPosition())) {
                childHolder.tv_position.setText(commentListx.get(groupPosition).getUser_list().get(childPosition).getCompany());
            } else {
                childHolder.tv_position.setText(commentListx.get(groupPosition).getUser_list().get(childPosition).getCompany() + "|" + commentListx.get(groupPosition).getUser_list().get(childPosition).getPosition());
            }
        }


        childHolder.swipe_content.addListener(new SwipeMenuLayout.SwipeListener() {
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
                PersonCentetActivity.start(context, commentListx.get(groupPosition).getUser_list().get(childPosition).getUser_id() + "");
            }
        });
        if (commentListx.get(groupPosition).getGroup_id() == -1) {
            //全部成员
            childHolder.tv_update_group.setVisibility(View.VISIBLE);
            childHolder.tv_update_group_name.setVisibility(View.GONE);
            childHolder.tv_delete.setVisibility(View.GONE);
        } else {
            childHolder.tv_update_group.setVisibility(View.VISIBLE);
            childHolder.tv_update_group_name.setVisibility(View.GONE);
            childHolder.tv_delete.setVisibility(View.VISIBLE);
        }
        childHolder.tv_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //删除
                setOnClick.setOnClick(1, groupPosition, childPosition, "");

            }
        });
        childHolder.tv_update_group_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //修改组名
                setOnClick.setOnClick(2, groupPosition, childPosition, "");
            }
        });
        final String text = childHolder.tv_position.getText().toString();
        childHolder.tv_update_group.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //修改组
                setOnClick.setOnClick(3, groupPosition, childPosition, text);
            }
        });

//        childHolder.rlayoutData.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                PersonCentetActivity.start(context, commentListx.get(groupPosition).getUser_list().get(childPosition).getUser_id() + "");
//            }
//        });

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    private class GroupHolder {

        private TextView tv_name, tv_num, tv_update_group_name, tv_delete;
        private ImageView img;
        private RelativeLayout rlayoutRoot;
        SwipeMenuLayout swipe_content;

        public GroupHolder(View view) {

            tv_name = (TextView) view.findViewById(R.id.tv_name);
            tv_num = (TextView) view.findViewById(R.id.tv_num);
            img = (ImageView) view.findViewById(R.id.img);
            rlayoutRoot = (RelativeLayout) view.findViewById(R.id.rlayout_root);
            tv_update_group_name = (TextView) view.findViewById(R.id.tv_update_group_name);
            tv_delete = (TextView) view.findViewById(R.id.tv_delete);
            swipe_content = (SwipeMenuLayout) view.findViewById(R.id.swipe_content);
        }
    }

    private class ChildHolder {
        private TextView tv_name, tv_position, tv_update_group_name, tv_update_group, tv_delete;
        RelativeLayout rlayoutData;
        private CircleImageView headImg;
        SwipeMenuLayout swipe_content;
        private ImageView item_index_vipImg;


        public ChildHolder(View view) {
            tv_name = (TextView) view.findViewById(R.id.lx_nametv);
            headImg = (CircleImageView) view.findViewById(R.id.item_lx_headimg);
            tv_position = (TextView) view.findViewById(R.id.lx_positiontv);
            rlayoutData = (RelativeLayout) view.findViewById(R.id.rlayout_data);
            tv_update_group_name = (TextView) view.findViewById(R.id.tv_update_group_name);
            tv_update_group = (TextView) view.findViewById(R.id.tv_update_group);
            tv_delete = (TextView) view.findViewById(R.id.tv_delete);
            swipe_content = (SwipeMenuLayout) view.findViewById(R.id.swipe_content);
            item_index_vipImg = (ImageView) view.findViewById(R.id.item_index_vipImg);
        }
    }


    public interface setOnClick {
        void setOnClick(int type, int groupPosition, int childPosition, String position);
    }

    private setOnClick setOnClick;

    public void setSetOnClick(setOnClick setOnClick) {
        this.setOnClick = setOnClick;
    }
}
