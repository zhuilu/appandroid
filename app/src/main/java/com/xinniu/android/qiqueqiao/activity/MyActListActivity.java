package com.xinniu.android.qiqueqiao.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
//import android.support.annotation.NonNull;
//import android.support.v7.widget.LinearLayoutManager;
//import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.xinniu.android.qiqueqiao.R;
import com.xinniu.android.qiqueqiao.adapter.MyActListAdapter;
import com.xinniu.android.qiqueqiao.base.BaseActivity;
import com.xinniu.android.qiqueqiao.bean.MyActListBean;
import com.xinniu.android.qiqueqiao.request.RequestManager;
import com.xinniu.android.qiqueqiao.request.callback.GetMyActListCallback;
import com.xinniu.android.qiqueqiao.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 我的活动
 * Created by yuchance on 2019/1/8.
 */

public class MyActListActivity extends BaseActivity {
    @BindView(R.id.mrecycler)
    RecyclerView mrecycler;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    @BindView(R.id.ymy_actlistRl)
    RelativeLayout ymyActlistRl;
    private int page = 1;
    private List<MyActListBean.ListBean> datas = new ArrayList<>();
    private MyActListAdapter actListAdapter;


    public static void start(Context context) {
        Intent intent = new Intent(context, MyActListActivity.class);

        context.startActivity(intent);
    }


    @Override
    public int getLayoutId() {
        return R.layout.activity_myact_list;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        topStatusBar(true);
        LinearLayoutManager manager = new LinearLayoutManager(MyActListActivity.this, LinearLayoutManager.VERTICAL, false);
        mrecycler.setLayoutManager(manager);
        actListAdapter = new MyActListAdapter(MyActListActivity.this, R.layout.item_myact_list, datas);
        mrecycler.setAdapter(actListAdapter);
        buildDatas(page);
        actListAdapter.setSetOnClick(new MyActListAdapter.setOnClick() {
            @Override
            public void setOnClick(int actId, int status) {
                if (status == 0) {
                    ToastUtils.showCentetToast(MyActListActivity.this, "您已取消此活动");
                } else {
                    GoseeBillActivity.start(MyActListActivity.this, actId);
                }
            }
        });
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                page = 1;
                buildDatas(page);
            }
        }).setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                page++;
                buildDatas(page);
            }
        });
    }

    private void buildDatas(final int page) {
        showBookingToast(1);
        RequestManager.getInstance().getMyActList(page, new GetMyActListCallback() {
            @Override
            public void onSuccess(MyActListBean bean) {
                dismissBookingToast();
                if (page == 1) {
                    datas.clear();
                }
                datas.addAll(bean.getList());
                actListAdapter.notifyDataSetChanged();
                finishSwipe();
                if (bean.getList() != null) {
                    if (page == 1) {
                        if (bean.getList().size() == 0) {
                            ymyActlistRl.setVisibility(View.VISIBLE);
                            actListAdapter.removeAllFooterView();
                            refreshLayout.setEnableLoadMore(false);
                        } else {
                            ymyActlistRl.setVisibility(View.GONE);
                            if (bean.getHasMore() == 0) {
                                actListAdapter.setFooterView(footView1);
                                refreshLayout.setEnableLoadMore(false);
                            } else {
                                actListAdapter.removeAllFooterView();
                                refreshLayout.setEnableLoadMore(true);
                            }
                        }
                    } else {
                        if (bean.getHasMore() == 0) {
                            actListAdapter.setFooterView(footView1);
                            refreshLayout.setEnableLoadMore(false);
                        } else {
                            actListAdapter.removeAllFooterView();
                            refreshLayout.setEnableLoadMore(true);
                        }
                    }
                } else {
                    ymyActlistRl.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailed(int code, String msg) {
                dismissBookingToast();
                ToastUtils.showCentetToast(MyActListActivity.this, msg);
            }
        });


    }

    private void finishSwipe() {
        if (refreshLayout != null) {
            if (refreshLayout.isEnableRefresh()) {
                refreshLayout.finishRefresh();
            }
            if (refreshLayout.isEnableLoadMore()) {
                refreshLayout.finishLoadMore();
            }
        }
    }


    @OnClick(R.id.bt_finish)
    public void onViewClicked(View v) {
        switch (v.getId()) {
            case R.id.bt_finish:
                finish();
                break;
            default:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GoseeBillActivity.GOSEEREQUEST && resultCode == GoseeBillActivity.GOSEERESULT) {
            page = 1;
            buildDatas(page);
        }
    }
}
