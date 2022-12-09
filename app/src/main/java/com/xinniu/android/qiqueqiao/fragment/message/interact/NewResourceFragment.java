package com.xinniu.android.qiqueqiao.fragment.message.interact;

import android.os.Bundle;
//import android.support.v7.widget.LinearLayoutManager;
//import androidx.recyclerview.widget.RecyclerView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
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

/**
 * Created by BDXK on 2017/11/7.
 * 新资源页面
 */

public class NewResourceFragment extends LazyBaseFragment{
    @BindView(R.id.care_new_resource_rv)
    RecyclerView mRecyclerView;
    //首页列表
    private List<IndexNewBean.ListBean> resourceItems = new ArrayList<>();

    private IndexNewAdapter mIndexFragmentAdapter;
    @BindView(R.id.new_resource_refresh_layout)
    SmartRefreshLayout mSwipeRefreshLayout;
    private LinearLayoutManager manager;

    private int page = 1;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_resource_new;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        mIndexFragmentAdapter = new IndexNewAdapter(getActivity(), R.layout.item_index_new, resourceItems,0,1);
        manager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.setAdapter(mIndexFragmentAdapter);
        mSwipeRefreshLayout.setEnableRefresh(false);
        mSwipeRefreshLayout.setEnableLoadMore(false);
//        mSwipeRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
//            @Override
//            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
//                page = 1;
//                loadData(page,false);
//            }
//        });
//        mSwipeRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
//            @Override
//            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
//                loadData(page++,false);
//            }
//        });


    }

    @Override
    protected void lazyLoad() {
        loadData(1,true);
    }



    private void loadData(final int page,boolean isShow){
        if (isShow) {
            showBookingToast(1);
        }
        RequestManager.getInstance().getNewResourceItem(page, new GetResourceListCallback() {
            @Override
            public void onSuccess(IndexNewBean resultDO) {
//                finishSwipe();
                dismissBookingToast();
                if (page == 1) {
                    resourceItems.clear();
                    mIndexFragmentAdapter.setFooterView(footView);
                    mSwipeRefreshLayout.setEnableLoadMore(false);

                }
                resourceItems.addAll(resultDO.getList());
                mIndexFragmentAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailed(int code, String msg) {
                ToastUtils.showCentetImgToast(getActivity(),msg);
                dismissBookingToast();
//                finishSwipe();
            }
        });

    }

    private void finishSwipe(){
        if (mSwipeRefreshLayout!=null){
            if (mSwipeRefreshLayout.isEnableRefresh()){
                mSwipeRefreshLayout.finishRefresh();
            }
            if (mSwipeRefreshLayout.isEnableLoadMore()){
                mSwipeRefreshLayout.finishLoadMore();
            }
        }
    }

}
