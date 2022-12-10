package com.xinniu.android.qiqueqiao.adapter;

import androidx.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;


import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xinniu.android.qiqueqiao.R;
import com.xinniu.android.qiqueqiao.bean.GroupMemberManageBean;
import com.xinniu.android.qiqueqiao.utils.ComUtils;
import com.xinniu.android.qiqueqiao.utils.ImageLoader;
import com.xinniu.android.qiqueqiao.utils.TimeUtils;
import com.xinniu.android.qiqueqiao.widget.CircleImageView;

import java.util.List;

/**
 * Created by yuchance on 2018/10/11.
 */

public class GroupMemberManageAdapter extends BaseQuickAdapter<GroupMemberManageBean,BaseViewHolder> {

    public GroupMemberManageAdapter(int layoutResId, @Nullable List<GroupMemberManageBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, final GroupMemberManageBean item) {
        helper.setText(R.id.mgroupmembertv,item.getRealname());
        if (item.getIdentity() == 0) {
            helper.setText(R.id.mgroup_managestatus,"");
            helper.setGone(R.id.mgroup_managestatus,false);
        }else if (item.getIdentity() == 1){
            helper.setText(R.id.mgroup_managestatus,"管理");
            helper.setGone(R.id.mgroup_managestatus,true);
        }else if (item.getIdentity() == 2){
            helper.setText(R.id.mgroup_managestatus,"群主");
            helper.setGone(R.id.mgroup_managestatus,true);
        }
        helper.setText(R.id.mgroup_manageidentity,item.getCompany()+"|" + item.getPosition());
        helper.setText(R.id.mmanagetimetv, TimeUtils.getStatus(item.getLast_login()));
        if (item.isManage()){
            helper.setGone(R.id.mmanagetv,true);
            helper.setGone(R.id.mmanagetimetv,false);
            if (item.getIs_black()==1) {
                helper.setGone(R.id.mmanagestatus, true);
            }else {
                helper.setGone(R.id.mmanagestatus,false);
            }
        }else {
            helper.setGone(R.id.mmanagestatus,false);
            helper.setGone(R.id.mmanagetv,false);
            helper.setGone(R.id.mmanagetimetv,true);
        }
        helper.getView(R.id.mmanagetv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goManage.setGoMange(item.getUser_id(),item.getIdentity(),item.getIs_black());
            }
        });
        ImageLoader.loadAvter(item.getHead_pic(), (CircleImageView) helper.getView(R.id.mmanagemehead));
        helper.getView(R.id.bgroupmemberRl).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goManage.goToManage(item.getUser_id());
            }
        });


    }
    public interface goManage{
        void setGoMange(int userId,int identix,int isBlack);
        void goToManage(int userId);
    }
    private goManage goManage;

    public void setGoManage(GroupMemberManageAdapter.goManage goManage) {
        this.goManage = goManage;
    }
}
