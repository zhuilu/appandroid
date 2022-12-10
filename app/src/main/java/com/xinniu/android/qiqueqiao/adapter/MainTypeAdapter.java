package com.xinniu.android.qiqueqiao.adapter;

import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.umeng.analytics.MobclickAgent;
import com.xinniu.android.qiqueqiao.R;
import com.xinniu.android.qiqueqiao.activity.IndexCellActivity;
import com.xinniu.android.qiqueqiao.bean.MainBean;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
//import android.support.v4.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by yuchance on 2018/12/27.
 */

public class MainTypeAdapter extends BaseQuickAdapter<MainBean.RecommendNavBean, BaseViewHolder> {

    private AppCompatActivity context;
    private List<MainBean.RecommendNavBean> mData = new ArrayList<>();

    public MainTypeAdapter(AppCompatActivity context, int layoutResId, @Nullable List<MainBean.RecommendNavBean> data) {
        super(layoutResId, data);
        this.context = context;
        this.mData = data;
    }

    @Override
    protected void convert(BaseViewHolder helper, final MainBean.RecommendNavBean item) {
        helper.setText(R.id.tv_name, item.getName());
        helper.getView(R.id.rlayout_more).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MobclickAgent.onEvent(context, "home_category", item.getP_name());//统计点击次数
                IndexCellActivity.start(context, item.getId(), item.getP_name());
            }
        });
        MainResoureAdapter mainResoureAdapter = new MainResoureAdapter(context, R.layout.item_index_new, item.getResources());
        RecyclerView recyclerView = helper.getView(R.id.item_mrecyclerType);
        LinearLayoutManager manager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(mainResoureAdapter);

    }
}
