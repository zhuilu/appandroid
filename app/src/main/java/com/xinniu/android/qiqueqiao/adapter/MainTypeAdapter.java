package com.xinniu.android.qiqueqiao.adapter;

import android.app.Activity;
import android.content.Context;
//import android.support.annotation.Nullable;
//import android.support.v4.content.ContextCompat;
//import android.support.v7.widget.LinearLayoutManager;
//import androidx.recyclerview.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.umeng.analytics.MobclickAgent;
import com.xinniu.android.qiqueqiao.R;
import com.xinniu.android.qiqueqiao.activity.CoopDetailActivity;
import com.xinniu.android.qiqueqiao.activity.IndexCellActivity;
import com.xinniu.android.qiqueqiao.activity.IndexClassifyActivity;
import com.xinniu.android.qiqueqiao.activity.LoginNewActivity;
import com.xinniu.android.qiqueqiao.activity.MyActListActivity;
import com.xinniu.android.qiqueqiao.bean.HotResourceBean;
import com.xinniu.android.qiqueqiao.bean.MainBean;
import com.xinniu.android.qiqueqiao.user.UserInfoHelper;
import com.xinniu.android.qiqueqiao.utils.ImageLoader;
import com.xinniu.android.qiqueqiao.utils.TimeUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yuchance on 2018/12/27.
 */

public class MainTypeAdapter extends BaseQuickAdapter<MainBean.RecommendNavBean, BaseViewHolder> {

    private Activity context;
    private List<MainBean.RecommendNavBean> mData = new ArrayList<>();

    public MainTypeAdapter(Activity context, int layoutResId, @Nullable List<MainBean.RecommendNavBean> data) {
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
