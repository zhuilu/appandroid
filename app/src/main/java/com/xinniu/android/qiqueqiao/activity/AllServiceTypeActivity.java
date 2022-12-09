package com.xinniu.android.qiqueqiao.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
//import android.support.v7.widget.LinearLayoutManager;
//import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;
import com.xinniu.android.qiqueqiao.R;
import com.xinniu.android.qiqueqiao.adapter.AllServiceTypeAdapter;
import com.xinniu.android.qiqueqiao.adapter.PublishServiceSelectTypeAdapter;
import com.xinniu.android.qiqueqiao.base.BaseActivity;
import com.xinniu.android.qiqueqiao.bean.ServiceCategoryAndTag;
import com.xinniu.android.qiqueqiao.request.RequestManager;
import com.xinniu.android.qiqueqiao.request.callback.GetServiceCategoryAndTagCallback;
import com.xinniu.android.qiqueqiao.utils.ComUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 服务全部类型
 * Created by yuchance on 2018/12/12.
 */

public class AllServiceTypeActivity extends BaseActivity {
    @BindView(R.id.mrecycler)
    RecyclerView mrecycler;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    private List<ServiceCategoryAndTag> datas = new ArrayList<>();
    private AllServiceTypeAdapter adapter;

    public static void start(Context context) {
        Intent starter = new Intent(context, AllServiceTypeActivity.class);
        context.startActivity(starter);
    }


    @Override
    public int getLayoutId() {
        return R.layout.activity_publish_service_selecttype;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        topStatusBar(true);
        tvTitle.setText("全部分类");
        LinearLayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mrecycler.setLayoutManager(manager);
        adapter = new AllServiceTypeAdapter(AllServiceTypeActivity.this, R.layout.item_all_service_type, datas);

        mrecycler.setAdapter(adapter);

        buildData();
        adapter.setSetOnClick(new AllServiceTypeAdapter.setOnClick() {
            @Override
            public void setOnClick(String title, int typeId, List<ServiceCategoryAndTag.ZlistBean> data) {
                Gson g = new Gson();
                String jsonString = g.toJson(data);
              ServiceCategoryListActivity.start(AllServiceTypeActivity.this,typeId,title,jsonString);
            }
        });

    }

    private void buildData() {
        showBookingToast(1);
        RequestManager.getInstance().getCategoryAndTag(new GetServiceCategoryAndTagCallback() {

            @Override
            public void onSuccess(List<ServiceCategoryAndTag> item) {
                dismissBookingToast();
                datas.addAll(item);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailed(int code, String msg) {
                dismissBookingToast();
            }
        });
    }


    @OnClick(R.id.bt_finish)
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bt_finish:
                finish();
                break;
            default:
                break;
        }


    }


}
