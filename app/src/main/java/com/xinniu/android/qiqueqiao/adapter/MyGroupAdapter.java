package com.xinniu.android.qiqueqiao.adapter;

import android.content.Context;
//import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xinniu.android.qiqueqiao.R;
import com.xinniu.android.qiqueqiao.bean.MyGroupListBean;
import com.xinniu.android.qiqueqiao.utils.ImageLoader;

import java.util.List;

/**
 * Created by yuchance on 2018/10/9.
 */

public class MyGroupAdapter extends BaseQuickAdapter<MyGroupListBean,BaseViewHolder> {



    public MyGroupAdapter(int layoutResId, @Nullable List<MyGroupListBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, final MyGroupListBean item) {
        helper.setText(R.id.mmygroup_name,item.getName());
        if (item.getStatus()==0){
            helper.setGone(R.id.mstatetv,true);
            helper.setText(R.id.mstatetv,"审核中");
        }else if (item.getStatus()==1){
            helper.setGone(R.id.mstatetv,false);
            helper.setText(R.id.mstatetv,"");
        }else if (item.getStatus()==2){
            helper.setGone(R.id.mstatetv,true);
            helper.setText(R.id.mstatetv,"审核不通过");
        }
        helper.setText(R.id.mmygroup_info,item.getIntroduction());
        helper.getView(R.id.bitem_mygroupRl).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToGroupMessage.goToGroupMessage(item.getStatus(),item.getId());
            }
        });
        ImageView headImg = helper.getView(R.id.item_mygroup_head);
        ImageLoader.loadAvter(item.getHead_pic(),headImg);
        if (item.getUser_identity() == 1){
            helper.setVisible(R.id.mstandtv,true);
            helper.setText(R.id.mstandtv,"管理");
        }else if(item.getUser_identity() == 2){
            helper.setVisible(R.id.mstandtv,true);
            helper.setText(R.id.mstandtv,"群主");
        }else {
            helper.setVisible(R.id.mstandtv,false);
            helper.setText(R.id.mstandtv,"");
        }

    }

    public interface goToGroupMessage{
        void goToGroupMessage(int status,int groupId);
    }

    private goToGroupMessage goToGroupMessage;

    public void setGoToGroupMessage(MyGroupAdapter.goToGroupMessage goToGroupMessage) {
        this.goToGroupMessage = goToGroupMessage;
    }
}
