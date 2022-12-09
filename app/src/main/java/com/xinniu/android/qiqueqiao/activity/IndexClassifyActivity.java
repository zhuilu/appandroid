package com.xinniu.android.qiqueqiao.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
//import android.support.v7.widget.LinearLayoutManager;
//import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;

import com.xinniu.android.qiqueqiao.R;
import com.xinniu.android.qiqueqiao.adapter.IndexClassifyAdapter;
import com.xinniu.android.qiqueqiao.base.BaseActivity;
import com.xinniu.android.qiqueqiao.bean.GetReleaseTemplateBean;
import com.xinniu.android.qiqueqiao.bean.GetReleaseTypeBean;
import com.xinniu.android.qiqueqiao.request.RequestManager;
import com.xinniu.android.qiqueqiao.request.callback.GetReleaseTypeCallback;
import com.xinniu.android.qiqueqiao.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by yuchance on 2019/1/10.
 */

public class IndexClassifyActivity extends BaseActivity {
    @BindView(R.id.bt_finish)
    RelativeLayout btFinish;
    @BindView(R.id.recycler)
    RecyclerView recycler;
    private List<GetReleaseTypeBean> datas = new ArrayList<>();
    private IndexClassifyAdapter adapter;

    public static void start(Context context){
        Intent intent = new Intent(context,IndexClassifyActivity.class);

        context.startActivity(intent);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_index_classify;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        topStatusBar(true);
        LinearLayoutManager manager = new LinearLayoutManager(IndexClassifyActivity.this,LinearLayoutManager.VERTICAL,false);
        recycler.setLayoutManager(manager);
        adapter = new IndexClassifyAdapter(R.layout.item_index_classify,datas);
        recycler.setAdapter(adapter);
        adapter.setSetOnClick(new IndexClassifyAdapter.setOnClick() {
            @Override
            public void setOnClick(String title,int f_id) {
                IndexCellActivity.start(IndexClassifyActivity.this,f_id,title);
            }
        });
        buildDatas();


    }

    private void buildDatas() {
        showBookingToast(1);
        RequestManager.getInstance().getReleaseType(0,new GetReleaseTypeCallback() {
            @Override
            public void onSuccess(List<GetReleaseTypeBean> bean) {
                dismissBookingToast();
                datas.addAll(bean);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailed(int code, String msg) {
                dismissBookingToast();
                ToastUtils.showCentetToast(IndexClassifyActivity.this,msg);
            }
        });


    }


    @OnClick(R.id.bt_finish)
    public void onViewClicked(View view) {
        switch (view.getId()){
            case R.id.bt_finish:
                finish();
                break;
                default:
                    break;
        }
    }
}
