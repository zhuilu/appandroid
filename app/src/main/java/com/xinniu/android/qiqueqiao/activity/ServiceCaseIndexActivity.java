package com.xinniu.android.qiqueqiao.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.xinniu.android.qiqueqiao.R;
import com.xinniu.android.qiqueqiao.adapter.IndexCaseAdapter;
import com.xinniu.android.qiqueqiao.base.BaseActivity;
import com.xinniu.android.qiqueqiao.bean.ServiceCaseBean;
import com.xinniu.android.qiqueqiao.request.RequestManager;
import com.xinniu.android.qiqueqiao.request.callback.GetCaseListCallback;
import com.xinniu.android.qiqueqiao.request.callback.RequestCallback;
import com.xinniu.android.qiqueqiao.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import retrofit2.Call;

public class ServiceCaseIndexActivity extends BaseActivity {
    @BindView(R.id.recyclerIndexCell)
    RecyclerView recyclerIndexCell;
    @BindView(R.id.refreshIndexCell)
    SmartRefreshLayout refreshIndexCell;
    @BindView(R.id.yperch_Rl)
    RelativeLayout yperchRl;
    private IndexCaseAdapter indexCaseAdapter;
    private List<ServiceCaseBean> datas = new ArrayList<>();
    private int serviceId;
    private Call mCall;

    @Override
    public int getLayoutId() {
        return R.layout.activity_service_case_index;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        topStatusBar(true);
        Bundle bundle = getIntent().getExtras();
        serviceId = bundle.getInt("id");
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
        recyclerIndexCell.setLayoutManager(linearLayoutManager);
        indexCaseAdapter = new IndexCaseAdapter(ServiceCaseIndexActivity.this, R.layout.item_case_index, datas);
        recyclerIndexCell.setAdapter(indexCaseAdapter);
        indexCaseAdapter.setSetOnClick(new IndexCaseAdapter.setOnClick() {
            @Override
            public void setAddOnClick(int position) {
                //删除
                delete(position);
            }
        });
        refreshIndexCell.setEnableLoadMore(false);

        refreshIndexCell.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                buildData();
            }
        });

        showBookingToast(1);
        buildData();

    }

    @Override
    protected void onResume() {
        super.onResume();
        buildData();

    }

    private void delete(final int position) {
        RequestManager.getInstance().delCase(datas.get(position).getId(), new RequestCallback<String>() {
            @Override
            public void requestStart(Call call) {
                showBookingToast(2);
                mCall = call;
            }

            @Override
            public void onSuccess(String s) {
                ToastUtils.showCentetImgToast(ServiceCaseIndexActivity.this, "删除成功");
                buildData();
            }

            @Override
            public void onFailed(int code, String msg) {

                ToastUtils.showCentetImgToast(ServiceCaseIndexActivity.this, msg);

            }

            @Override
            public void requestEnd() {
                dismissBookingToast();
            }
        });
    }

    private void buildData() {
        RequestManager.getInstance().getCaseList(serviceId, new GetCaseListCallback() {
            @Override
            public void onSuccess(List<ServiceCaseBean> item) {
                dismissBookingToast();
                datas.clear();
                if (item.size() == 0) {
                    yperchRl.setVisibility(View.VISIBLE);
                    refreshIndexCell.setVisibility(View.GONE);
                } else {
                    yperchRl.setVisibility(View.GONE);
                    refreshIndexCell.setVisibility(View.VISIBLE);
                    datas.addAll(item);
                    indexCaseAdapter.notifyDataSetChanged();
                }

                finishSwipe();
            }

            @Override
            public void onFailed(int code, String msg) {
                dismissBookingToast();
                ToastUtils.showCentetToast(ServiceCaseIndexActivity.this, msg);
            }
        });


    }

    private void finishSwipe() {
        if (refreshIndexCell != null) {
            if (refreshIndexCell.isEnableRefresh()) {
                refreshIndexCell.finishRefresh(200);
            }
            if (refreshIndexCell.isEnableLoadMore()) {
                refreshIndexCell.finishLoadMore(200);
            }
        }
    }

    @OnClick({R.id.bt_finish, R.id.tv_release_template, R.id.tv_add})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_finish:
                finish();
                break;
            case R.id.tv_release_template:
                if (datas.size() < 6) {
                    AddCaseActivity.start(ServiceCaseIndexActivity.this, serviceId);
                } else {
                    ToastUtils.showCentetToast(ServiceCaseIndexActivity.this, "最多可添加6个案例");
                }
                break;
            case R.id.tv_add:
                if (datas.size() < 6) {
                    AddCaseActivity.start(ServiceCaseIndexActivity.this, serviceId);
                } else {
                    ToastUtils.showCentetToast(ServiceCaseIndexActivity.this, "最多可添加6个案例");
                }
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mCall != null) {
            mCall.cancel();
        }
    }
}
