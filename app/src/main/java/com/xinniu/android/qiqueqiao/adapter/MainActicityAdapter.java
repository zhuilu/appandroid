package com.xinniu.android.qiqueqiao.adapter;

import androidx.appcompat.app.AppCompatActivity;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xinniu.android.qiqueqiao.R;
import com.xinniu.android.qiqueqiao.bean.ActivityColumnListBean;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by yuchance on 2018/12/27.
 */

public class MainActicityAdapter extends BaseQuickAdapter<ActivityColumnListBean.ListBean, BaseViewHolder> {

    private AppCompatActivity context;
    private List<ActivityColumnListBean.ListBean> mData = new ArrayList<>();

    public MainActicityAdapter(AppCompatActivity context, int layoutResId, @Nullable List<ActivityColumnListBean.ListBean> data) {
        super(layoutResId, data);
        this.context = context;
        this.mData = data;
    }

    @Override
    protected void convert(BaseViewHolder helper, final ActivityColumnListBean.ListBean item) {
        helper.setText(R.id.tv_name, item.getTitle());
        List<ActivityColumnListBean.ListBean.ActivityListBean> datas = new ArrayList<>();
        if(item.getActivity_list().size()>2){
            datas.add(item.getActivity_list().get(0));
            datas.add(item.getActivity_list().get(1));
        }else{
            datas.addAll(item.getActivity_list());
        }

        MainEnentsNewAdapter mainResoureAdapter = new MainEnentsNewAdapter(context, R.layout.item_main_activity, datas);
        RecyclerView recyclerView = helper.getView(R.id.item_mrecyclerType);
        LinearLayoutManager manager = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(mainResoureAdapter);

    }
}
