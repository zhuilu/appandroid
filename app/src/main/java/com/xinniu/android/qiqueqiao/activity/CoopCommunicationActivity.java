package com.xinniu.android.qiqueqiao.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
//import android.support.annotation.NonNull;
//import android.support.v4.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.xinniu.android.qiqueqiao.R;
import com.xinniu.android.qiqueqiao.adapter.CoopCommunicationAdapter;
import com.xinniu.android.qiqueqiao.base.BaseActivity;
import com.xinniu.android.qiqueqiao.bean.CoopDetailBean;
import com.xinniu.android.qiqueqiao.bean.ResourceErrorBean;
import com.xinniu.android.qiqueqiao.request.RequestManager;
import com.xinniu.android.qiqueqiao.request.callback.GetCoopDetailCallback;
import com.xinniu.android.qiqueqiao.utils.ToastUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by yuchance on 2018/10/25.
 */

public class CoopCommunicationActivity extends BaseActivity {
    @BindView(R.id.bt_finish)
    RelativeLayout btFinish;
    @BindView(R.id.coop_communrecycler)
    RecyclerView coopCommunrecycler;
    @BindView(R.id.mrefreshLayout)
    SmartRefreshLayout mrefreshLayout;

    private List<CoopDetailBean.ZListBean> datas = new ArrayList<>();
    private CoopCommunicationAdapter adapter;
    private int resourceId;
    private int page = 1;

    public static void start(Context context,int resourceId){
        Intent intent = new Intent(context,CoopCommunicationActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt("resourceId",resourceId);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }


    @Override
    public int getLayoutId() {
        return R.layout.activity_coop_communication;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        topStatusBar(true);
        Bundle bundle = getIntent().getExtras();
        if (bundle!=null){
            resourceId = bundle.getInt("resourceId");
        }
        mrefreshLayout.setEnableRefresh(false);
        LinearLayoutManager manager = new LinearLayoutManager(CoopCommunicationActivity.this,LinearLayoutManager.VERTICAL,false);
        coopCommunrecycler.setLayoutManager(manager);
        adapter = new CoopCommunicationAdapter(R.layout.item_coop_communication,datas);
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                PersonCentetActivity.start(CoopCommunicationActivity.this,datas.get(position).getUser_id()+"",true);
            }
        });
        coopCommunrecycler.setAdapter(adapter);
        mrefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                page++;
                initDatas();
            }
        });
        initDatas();
    }

    private void initDatas() {
        showBookingToast(1);
        RequestManager.getInstance().getCoopDetail(resourceId, page, new GetCoopDetailCallback() {
            @Override
            public void onSuccess(CoopDetailBean bean) {
                dismissBookingToast();
                datas.addAll(bean.getZ_list());
                adapter.notifyDataSetChanged();
                if (bean.getHasMore() == 0) {
                    adapter.setFooterView(footView1);
                    mrefreshLayout.setEnableLoadMore(false);
                } else {
                    adapter.removeAllFooterView();
                    mrefreshLayout.setEnableLoadMore(true);
                }
                stopRefresh();
            }

            @Override
            public void onUndercarriage(String bean) {
                dismissBookingToast();
                Gson gson = new Gson();
                ResourceErrorBean errorResponse = gson.fromJson(bean, ResourceErrorBean.class);
                ToastUtils.showCentetImgToast(CoopCommunicationActivity.this, errorResponse.getMsg());

            }

            @Override
            public void onFailed(int code, String msg) {
                dismissBookingToast();
                ToastUtils.showCentetToast(CoopCommunicationActivity.this,msg);
            }
        });

    }

    private void stopRefresh() {
        if (mrefreshLayout != null) {
            if (mrefreshLayout.isEnableLoadMore()) {
                mrefreshLayout.finishLoadMore(true);
            }
        }
    }

    @OnClick(R.id.bt_finish)
    public void onViewClicked() {
        finish();
    }
}
