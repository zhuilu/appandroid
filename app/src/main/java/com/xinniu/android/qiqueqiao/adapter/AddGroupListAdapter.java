package com.xinniu.android.qiqueqiao.adapter;

//import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xinniu.android.qiqueqiao.R;
import com.xinniu.android.qiqueqiao.bean.AddGroupListBean;
import com.xinniu.android.qiqueqiao.utils.ImageLoader;

import java.util.List;

/**
 * Created by yuchance on 2018/9/27.
 */

public class AddGroupListAdapter extends BaseQuickAdapter<AddGroupListBean.ListBean,BaseViewHolder> {



    public AddGroupListAdapter(int layoutResId, @Nullable List<AddGroupListBean.ListBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, final AddGroupListBean.ListBean item) {
        ImageLoader.loadAvter(item.getHead_pic(), (ImageView) helper.getView(R.id.maddgroupImg));
        helper.setText(R.id.maddgroupNametv,item.getName());
        helper.setText(R.id.maddgroupCitytv,item.getCity_name());
        helper.setText(R.id.maddgroupNumtv,item.getNum()+"人");
        helper.setText(R.id.maddgroupInfotv,item.getNotice());
        helper.getView(R.id.baddgroupRl).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToGroup.goToGroup(item.getId());
            }
        });


    }

    public interface goToGroup{
        void goToGroup(int groupId);
    }
    private goToGroup goToGroup;

    public void setGoToGroup(AddGroupListAdapter.goToGroup goToGroup) {
        this.goToGroup = goToGroup;
    }
}
