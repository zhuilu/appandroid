package com.xinniu.android.qiqueqiao.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;

import com.xinniu.android.qiqueqiao.R;
import com.xinniu.android.qiqueqiao.adapter.HotResourceAdapter;
import com.xinniu.android.qiqueqiao.adapter.IndexNewAdapter;
import com.xinniu.android.qiqueqiao.base.BaseActivity;
import com.xinniu.android.qiqueqiao.bean.HotResourceBean;
import com.xinniu.android.qiqueqiao.bean.IndexNewBean;
import com.xinniu.android.qiqueqiao.request.RequestManager;
import com.xinniu.android.qiqueqiao.request.callback.GetHotResourceCallback;
import com.xinniu.android.qiqueqiao.request.callback.GetResourceListCallback;
import com.xinniu.android.qiqueqiao.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by yuchance on 2018/12/27.
 */

public class HotResourceActivity extends BaseActivity {
    @BindView(R.id.mrecycler)
    RecyclerView mrecycler;
    List<HotResourceBean> datas = new ArrayList<>();
    private HotResourceAdapter adapter;

    public static void start(Context context){
        Intent intent = new Intent(context,HotResourceActivity.class);

        context.startActivity(intent);

    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_hotrecource;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        topStatusBar(true);
        LinearLayoutManager manager = new LinearLayoutManager(HotResourceActivity.this,LinearLayoutManager.VERTICAL,false);
        mrecycler.setLayoutManager(manager);
        adapter = new HotResourceAdapter(HotResourceActivity.this, R.layout.item_index_new,datas);
        mrecycler.setAdapter(adapter);
        buildDatas();
    }

    private void buildDatas() {
        showBookingToast(1);
        RequestManager.getInstance().getHotResource(new GetHotResourceCallback() {
            @Override
            public void onSuccess(List<HotResourceBean> list) {
                dismissBookingToast();
                datas.addAll(list);
                adapter.notifyDataSetChanged();
                if (datas.size()>0) {
                    adapter.setFooterView(footView1);
                }
            }

            @Override
            public void onFailed(int code, String msg) {
                dismissBookingToast();
                ToastUtils.showCentetToast(HotResourceActivity.this,msg);
            }
        });
    }


    @OnClick({R.id.bt_finish})
    public void onViewClicked(View v) {
        switch (v.getId()){
            case R.id.bt_finish:
                finish();
                break;
                default:
                    break;

        }
    }
}
