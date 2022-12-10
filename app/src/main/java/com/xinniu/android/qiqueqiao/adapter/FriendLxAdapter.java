package com.xinniu.android.qiqueqiao.adapter;

import android.content.Context;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xinniu.android.qiqueqiao.R;
import com.xinniu.android.qiqueqiao.activity.FriendLxActivity;
import com.xinniu.android.qiqueqiao.bean.GetFriendListBean;
import com.xinniu.android.qiqueqiao.utils.NoScrollRecyclerView;

import java.util.List;

/**
 * Created by yuchance on 2018/11/30.
 */

public class FriendLxAdapter extends BaseQuickAdapter<GetFriendListBean.GroupBean,BaseViewHolder>{

    private Context context;
    private boolean isManager = true;

    public void setManager(boolean manager) {
        isManager = manager;
    }

    public FriendLxAdapter(Context context, int layoutResId, @Nullable List<GetFriendListBean.GroupBean> data) {
        super(layoutResId, data);
        this.context = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, final GetFriendListBean.GroupBean item) {
        if (isManager) {
            if (helper.getAdapterPosition() == 0) {
                helper.getView(R.id.tag).setVisibility(View.VISIBLE);
            } else {
                helper.getView(R.id.tag).setVisibility(View.GONE);
            }
        }else {
            helper.getView(R.id.tag).setVisibility(View.GONE);
        }
        NoScrollRecyclerView itemRecycler = helper.getView(R.id.item_rv);
        LinearLayoutManager manager = new LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false);
        itemRecycler.setLayoutManager(manager);
        FriendNxAdapter adapter = new FriendNxAdapter(context,R.layout.item_friendlx_list,item.getList());
        itemRecycler.setAdapter(adapter);
        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                setOnClick.setOnClick(item.getList().get(position).getUser_id(),item.getList().get(position).getRealname());
            }
        });
    }

    public interface setOnClick{
        void setOnClick(int userId,String userName);
    }

    private setOnClick setOnClick;

    public void setSetOnClick(FriendLxAdapter.setOnClick setOnClick) {
        this.setOnClick = setOnClick;
    }
}
