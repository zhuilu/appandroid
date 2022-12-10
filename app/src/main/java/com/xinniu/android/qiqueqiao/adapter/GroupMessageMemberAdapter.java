package com.xinniu.android.qiqueqiao.adapter;

import android.content.Context;
import androidx.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xinniu.android.qiqueqiao.R;
import com.xinniu.android.qiqueqiao.bean.GroupInfoBean;
import com.xinniu.android.qiqueqiao.bean.SystemMsgBean;
import com.xinniu.android.qiqueqiao.utils.ImageLoader;
import com.xinniu.android.qiqueqiao.widget.CircleImageView;

import java.util.List;

/**
 * Created by yuchance on 2018/10/10.
 */

public class GroupMessageMemberAdapter extends BaseMultiItemQuickAdapter<GroupInfoBean.UserListBean,BaseViewHolder> {


    private Context context;


    public GroupMessageMemberAdapter(Context context,List<GroupInfoBean.UserListBean> data) {
        super(data);
        this.context = context;
        addItemType(GroupInfoBean.UserListBean.COMMON, R.layout.item_groupmessage_member);
        addItemType(GroupInfoBean.UserListBean.INVITE, R.layout.item_groupmessage_invite);
    }

    @Override
    protected void convert(BaseViewHolder helper, GroupInfoBean.UserListBean item) {
        switch (helper.getItemViewType()){
            case GroupInfoBean.UserListBean.COMMON:
                helper.setText(R.id.member_nametv,item.getRealname());
                ImageLoader.loadAvter(item.getHead_pic(), (CircleImageView) helper.getView(R.id.member_circleImg));
                break;
            case GroupInfoBean.UserListBean.INVITE:
                helper.getView(R.id.member_circleImg).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        setOnClick.setOnClick();
                    }
                });
                break;
            default:
                break;

        }
    }

    public interface setOnClick{
        void setOnClick();
    }
    private setOnClick setOnClick;

    public void setSetOnClick(GroupMessageMemberAdapter.setOnClick setOnClick) {
        this.setOnClick = setOnClick;
    }
}
