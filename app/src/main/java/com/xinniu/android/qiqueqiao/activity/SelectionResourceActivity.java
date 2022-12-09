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
import com.xinniu.android.qiqueqiao.adapter.IndexNewAdapter;
import com.xinniu.android.qiqueqiao.base.BaseActivity;
import com.xinniu.android.qiqueqiao.bean.IndexNewBean;
import com.xinniu.android.qiqueqiao.request.RequestManager;
import com.xinniu.android.qiqueqiao.request.callback.GetResourceListCallback;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by yuchance on 2018/12/27.
 */

public class SelectionResourceActivity extends BaseActivity {
    @BindView(R.id.mrecycler)
    RecyclerView mrecycler;
    @BindView(R.id.mrefreshLayout)
    SmartRefreshLayout mrefreshLayout;
    @BindView(R.id.yperch_Rl)
    RelativeLayout yperchRl;
    private int page = 1;
    List<IndexNewBean.ListBean> datas = new ArrayList<>();
    private IndexNewAdapter adapter;

    public static void start(Context context){
        Intent intent = new Intent(context,SelectionResourceActivity.class);

        context.startActivity(intent);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_selection_resource;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        topStatusBar(true);
        LinearLayoutManager manager = new LinearLayoutManager(SelectionResourceActivity.this,LinearLayoutManager.VERTICAL,false);
        mrecycler.setLayoutManager(manager);
        adapter = new IndexNewAdapter(SelectionResourceActivity.this, R.layout.item_index_new,datas,0,1);
        mrecycler.setAdapter(adapter);
        mrefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                page = 1;
                buildDatas(page,false);
            }
        }).setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                page++;
                buildDatas(page,false);
            }
        });
        buildDatas(page,true);
    }

    private void buildDatas(final int page, boolean isShow) {
        if (isShow) {
            showBookingToast(1);
        }
        RequestManager.getInstance().getSelectionResource(page, new GetResourceListCallback() {
            @Override
            public void onSuccess(IndexNewBean resultDO) {
                dismissBookingToast();
                if (page == 1) {
                    datas.clear();
                }
                datas.addAll(resultDO.getList());
                adapter.notifyDataSetChanged();
                finishSwipe();
                if (resultDO.getList() != null) {
                    if (page == 1) {
                        if (resultDO.getList().size() == 0) {
                            yperchRl.setVisibility(View.VISIBLE);
                            adapter.removeAllFooterView();
                            mrefreshLayout.setEnableLoadMore(false);
                        } else {
                            yperchRl.setVisibility(View.GONE);
                            if (resultDO.getHasMore() == 0) {
                                adapter.setFooterView(footView1);
                                mrefreshLayout.setEnableLoadMore(false);
                            } else {
                                adapter.removeAllFooterView();
                                mrefreshLayout.setEnableLoadMore(true);
                            }
                        }
                    } else {
                        if (resultDO.getHasMore() == 0) {
                            adapter.setFooterView(footView1);
                            mrefreshLayout.setEnableLoadMore(false);
                        } else {
                            adapter.removeAllFooterView();
                            mrefreshLayout.setEnableLoadMore(true);
                        }
                    }
                } else {
                    yperchRl.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailed(int code, String msg) {

            }
        });
    }

    private void finishSwipe() {
        if (mrefreshLayout != null) {
            if (mrefreshLayout.isEnableRefresh()) {
                mrefreshLayout.finishRefresh();
            }
            if (mrefreshLayout.isEnableLoadMore()) {
                mrefreshLayout.finishLoadMore();
            }
        }
    }


    @OnClick({R.id.bt_finish})
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
