package com.xinniu.android.qiqueqiao.widget;

//import android.support.v7.widget.LinearLayoutManager;
//import androidx.recyclerview.widget.RecyclerView;

import com.xinniu.android.qiqueqiao.utils.Logger;

/**
 * Created by lzq on 2017/12/25.
 */

public abstract class EndLessOnScrollListener extends RecyclerView.OnScrollListener {

    //声明一个LinearLayoutManager
    private LinearLayoutManager mLinearLayoutManager;

    protected int lastVisibleItem;
    protected boolean isCanloadMore;
    protected int page = 1;
    private RecyclerView.Adapter mAdapter;

//    private int currentPage = 0;
//    //已经加载出来的Item的数量
//    private int totalItemCount;
//
//    //主要用来存储上一个totalItemCount
//    private int previousTotal = 0;
//
//    //在屏幕上可见的item数量
//    private int visibleItemCount;
//
//    //在屏幕可见的Item中的第一个
//    private int firstVisibleItem;
//
//    //是否正在上拉数据
//    private boolean loading = true;

    public EndLessOnScrollListener(LinearLayoutManager linearLayoutManager) {
        this.mLinearLayoutManager = linearLayoutManager;
    }

    public  EndLessOnScrollListener setAdapter(RecyclerView.Adapter adapter){
        this.mAdapter = adapter;
        return this;
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);

        lastVisibleItem = mLinearLayoutManager.findLastVisibleItemPosition();
        isCanloadMore = dy > 0;
    }

    @Override
    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
        super.onScrollStateChanged(recyclerView, newState);
        if (newState == RecyclerView.SCROLL_STATE_IDLE
                && lastVisibleItem + 1 == mAdapter.getItemCount()
                && isCanloadMore) {
            page++;
            onLoadMore(page);
        }
    }

    /**
     * 提供一个抽闲方法，在Activity中监听到这个EndLessOnScrollListener
     * 并且实现这个方法
     */
    public abstract void onLoadMore(int currentPage);
    public void pageReduce(){
        if (page > 1){
            page --;
        }
    }
}

