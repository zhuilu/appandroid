package com.xinniu.android.qiqueqiao.fragment.collect;

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
import com.xinniu.android.qiqueqiao.base.LazyBaseFragment;
import com.xinniu.android.qiqueqiao.bean.IndexNewBean;
import com.xinniu.android.qiqueqiao.request.RequestManager;
import com.xinniu.android.qiqueqiao.request.callback.GetResourceListCallback;
import com.xinniu.android.qiqueqiao.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class ResourceCollectFragment extends LazyBaseFragment {
    @BindView(R.id.mcollect_my_rv)
    RecyclerView mcollectMyRv;
    @BindView(R.id.mcollect_my_refresh)
    SmartRefreshLayout mcollectMyRefresh;
    @BindView(R.id.yperch_Rl)
    RelativeLayout yperchRl;
    private IndexNewAdapter adapter;
    private View footView;
    List<IndexNewBean.ListBean> datas = new ArrayList<>();
    int page = 1;

    public static ResourceCollectFragment newInstance() {
        Bundle args = new Bundle();
        ResourceCollectFragment fragment = new ResourceCollectFragment();
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public int getLayoutId() {
        return R.layout.fragment_my_collect;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        footView = getActivity().getLayoutInflater().inflate(R.layout.view_unload_more,null);
        LinearLayoutManager manager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        mcollectMyRv.setLayoutManager(manager);
        adapter = new IndexNewAdapter(getActivity(), R.layout.item_index_new, datas, 0,1);
        mcollectMyRv.setAdapter(adapter);
        mcollectMyRefresh.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                page++;
                initDatas(page,false);
            }
        });
        mcollectMyRefresh.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                page = 1;
                initDatas(page,false);
            }
        });


        initDatas(page,true);
    }

    @Override
    protected void lazyLoad() {

    }
    private void initDatas(final int page,boolean isShow) {
        if (isShow){
            showBookingToast(3);
        }
        RequestManager.getInstance().myCollectList(page, new GetResourceListCallback() {
            @Override
            public void onSuccess(IndexNewBean resultDO) {
                dismissBookingToast();
                if (page == 1) {
                    datas.clear();
                }
                datas.addAll(resultDO.getList());
                adapter.notifyDataSetChanged();
                finishSwipe();
                if (resultDO.getList()!=null) {
                    if (page == 1) {
                        if (resultDO.getList().size() == 0) {
                            yperchRl.setVisibility(View.VISIBLE);
                            adapter.removeAllFooterView();
                            mcollectMyRefresh.setEnableLoadMore(false);
                        }else {
                            yperchRl.setVisibility(View.GONE);
                            if (resultDO.getHasMore()==0){
                                adapter.setFooterView(footView);
                                mcollectMyRefresh.setEnableLoadMore(false);
                            }else {
                                adapter.removeAllFooterView();
                                mcollectMyRefresh.setEnableLoadMore(true);
                            }
                        }
                    } else {
                        if (resultDO.getHasMore()==0){
                            adapter.setFooterView(footView);
                            mcollectMyRefresh.setEnableLoadMore(false);
                        }else {
                            adapter.removeAllFooterView();
                            mcollectMyRefresh.setEnableLoadMore(true);
                        }
                    }
                }else {
                    yperchRl.setVisibility(View.VISIBLE);
                }

            }

            @Override
            public void onFailed(int code, String msg) {
                dismissBookingToast();
                finishSwipe();
                ToastUtils.showCentetToast(getActivity(), msg);
            }
        });


    }
    private void finishSwipe() {
        if (mcollectMyRefresh != null) {
            if (mcollectMyRefresh.isEnableRefresh()){
                mcollectMyRefresh.finishRefresh();
            }
            if (mcollectMyRefresh.isEnableLoadMore()) {
                mcollectMyRefresh.finishLoadMore();
            }
        }
    }


}
