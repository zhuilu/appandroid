package com.xinniu.android.qiqueqiao.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
//import android.support.annotation.NonNull;
//import android.support.v7.widget.LinearLayoutManager;
//import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.xinniu.android.qiqueqiao.R;
import com.xinniu.android.qiqueqiao.adapter.CellTagsAdapter;
import com.xinniu.android.qiqueqiao.adapter.CellTagxAdapter;
import com.xinniu.android.qiqueqiao.adapter.IndexNewAdapter;
import com.xinniu.android.qiqueqiao.adapter.IndexServiceAdapter;
import com.xinniu.android.qiqueqiao.adapter.ServiceTagsAdapter;
import com.xinniu.android.qiqueqiao.base.BaseActivity;
import com.xinniu.android.qiqueqiao.bean.CellTagsBean;
import com.xinniu.android.qiqueqiao.bean.IndexServiceBean;
import com.xinniu.android.qiqueqiao.bean.ServiceCategoryAndTag;
import com.xinniu.android.qiqueqiao.request.RequestManager;
import com.xinniu.android.qiqueqiao.request.callback.ServiceListCallback;
import com.xinniu.android.qiqueqiao.utils.ShowUtils;
import com.xinniu.android.qiqueqiao.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ServiceCategoryListActivity extends BaseActivity {
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_hot)
    TextView tvHot;
    @BindView(R.id.view_01)
    View view01;
    @BindView(R.id.tv_new)
    TextView tvNew;
    @BindView(R.id.view_02)
    View view02;
    @BindView(R.id.mrecyclerSth)
    RecyclerView mrecyclerSth;
    @BindView(R.id.recyclerIndexCell)
    RecyclerView recyclerIndexCell;
    @BindView(R.id.refreshIndexCell)
    SmartRefreshLayout refreshIndexCell;
    @BindView(R.id.yperch_Rl)
    RelativeLayout yperchRl;
    private int p_id;
    private String sort_order = "2";
    private int query_id = 0;
    private String theTitle;
    private ServiceTagsAdapter cellAdapter;
    private List<ServiceCategoryAndTag.ZlistBean> tags = new ArrayList<>();
    private int mSearchPage = 1;
    private IndexServiceAdapter indexServiceAdapter;
    private List<IndexServiceBean.ListBean> dataList = new ArrayList<>();
    private View footView;

    public static void start(Context mContext, int theId, String theTitle, String data) {
        Intent intent = new Intent(mContext, ServiceCategoryListActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt("id", theId);
        bundle.putString("title", theTitle);
        bundle.putString("tags", data);
        intent.putExtras(bundle);
        mContext.startActivity(intent, bundle);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_service_category_list;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        topStatusBar(true);
        Bundle bundle = getIntent().getExtras();
        p_id = bundle.getInt("id");
        theTitle = bundle.getString("title");
        String data = bundle.getString("tags");
        if (data != null && data.length() > 0) {
            Gson gson = new Gson();
            tags = gson.fromJson(data, new TypeToken<List<ServiceCategoryAndTag.ZlistBean>>() {
            }.getType());
        }
        ShowUtils.showTextPerfect(tvTitle, theTitle);
        LinearLayoutManager manager = new LinearLayoutManager(ServiceCategoryListActivity.this, LinearLayoutManager.HORIZONTAL, false);
        mrecyclerSth.setLayoutManager(manager);
        cellAdapter = new ServiceTagsAdapter(R.layout.item_service_tags, tags);
        mrecyclerSth.setAdapter(cellAdapter);
        cellAdapter.setSetTags(new ServiceTagsAdapter.setTags() {
            @Override
            public void setTags(int id) {
                query_id = id;
                mSearchPage = 1;
                //更新数据
                buildData(mSearchPage, sort_order, query_id, p_id);
            }
        });

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
        recyclerIndexCell.setLayoutManager(linearLayoutManager);
        indexServiceAdapter = new IndexServiceAdapter(ServiceCategoryListActivity.this, R.layout.item_service, dataList, 2);
        recyclerIndexCell.setAdapter(indexServiceAdapter);

        footView = getLayoutInflater().inflate(R.layout.view_unload_more, null);

        refreshIndexCell.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                mSearchPage = 1;
                buildData(mSearchPage, sort_order, query_id, p_id);
            }
        });
        refreshIndexCell.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                mSearchPage++;
                buildData(mSearchPage, sort_order, query_id, p_id);
            }
        });
        showBookingToast(1);
        buildData(mSearchPage, sort_order, query_id, p_id);

    }

    private void buildData(final int mSearchPage, String sort_order, int query_id, int p_id) {
        RequestManager.getInstance().getServiceInfoList(mSearchPage, sort_order, query_id, p_id, new ServiceListCallback() {
            @Override
            public void onSuccess(IndexServiceBean bean) {
                dismissBookingToast();
                refreshIndexCell.setVisibility(View.VISIBLE);
                yperchRl.setVisibility(View.GONE);
                if (mSearchPage == 1) {
                    dataList.clear();
                    if (bean.getList().size() == 0) {
                        yperchRl.setVisibility(View.VISIBLE);
                        refreshIndexCell.setVisibility(View.GONE);
                        indexServiceAdapter.removeAllFooterView();
                        refreshIndexCell.setEnableLoadMore(false);
                    } else {

                        if (bean.getHasMore() == 0) {
                            indexServiceAdapter.setFooterView(footView);
                            refreshIndexCell.setEnableLoadMore(false);
                        } else {
                            indexServiceAdapter.removeAllFooterView();
                            refreshIndexCell.setEnableLoadMore(true);
                        }
                    }
                } else {
                    if (bean.getHasMore() == 0) {
                        indexServiceAdapter.setFooterView(footView);
                        refreshIndexCell.setEnableLoadMore(false);
                    } else {
                        indexServiceAdapter.removeAllFooterView();
                        refreshIndexCell.setEnableLoadMore(true);
                    }
                }
                dataList.addAll(bean.getList());
                indexServiceAdapter.notifyDataSetChanged();

                finishSwipe();

            }

            @Override
            public void onFailue(int code, String msg) {
                dismissBookingToast();
                ToastUtils.showCentetToast(ServiceCategoryListActivity.this, msg);
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

    @OnClick({R.id.bt_finish, R.id.rlayout_hot, R.id.rlayout_new})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_finish:
                finish();
                break;
            case R.id.rlayout_hot:
                //最热
                sort_order = "1";
                tvHot.setTextColor(getResources().getColor(R.color.blue_bg_4B96F3));
                view01.setVisibility(View.VISIBLE);
                tvNew.setTextColor(getResources().getColor(R.color._999));
                view02.setVisibility(View.INVISIBLE);
                mSearchPage = 1;
                buildData(mSearchPage, sort_order, query_id, p_id);
                break;
            case R.id.rlayout_new:
                //最新
                sort_order = "2";
                tvNew.setTextColor(getResources().getColor(R.color.blue_bg_4B96F3));
                view02.setVisibility(View.VISIBLE);
                tvHot.setTextColor(getResources().getColor(R.color._999));
                view01.setVisibility(View.INVISIBLE);
                mSearchPage = 1;
                buildData(mSearchPage, sort_order, query_id, p_id);
                break;
        }


    }


}
