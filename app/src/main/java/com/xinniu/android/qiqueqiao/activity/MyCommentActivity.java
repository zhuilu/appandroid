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
import com.xinniu.android.qiqueqiao.adapter.MyCommentAdapter;
import com.xinniu.android.qiqueqiao.base.BaseActivity;
import com.xinniu.android.qiqueqiao.bean.MyCommentBean;
import com.xinniu.android.qiqueqiao.request.RequestManager;
import com.xinniu.android.qiqueqiao.request.callback.GetMyCommentCallback;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by yuchance on 2018/12/27.
 */

public class MyCommentActivity extends BaseActivity {
    @BindView(R.id.mrecycler)
    RecyclerView mrecycler;
    @BindView(R.id.mrefreshLayout)
    SmartRefreshLayout mrefreshLayout;
    @BindView(R.id.yperch_Rl)
    RelativeLayout yperchRl;
    private int page = 1;

    private List<MyCommentBean.ListBean> datas = new ArrayList<>();
    private MyCommentAdapter adapter;


    public static void start(Context context) {
        Intent intent = new Intent(context, MyCommentActivity.class);

        context.startActivity(intent);
    }


    @Override
    public int getLayoutId() {
        return R.layout.activity_mycomment;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {

        buildDatas(page, true);
        LinearLayoutManager manager = new LinearLayoutManager(MyCommentActivity.this, LinearLayoutManager.VERTICAL, false);
        mrecycler.setLayoutManager(manager);
        adapter = new MyCommentAdapter(MyCommentActivity.this, R.layout.item_my_comment, datas);
        mrecycler.setAdapter(adapter);
        mrefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                page = 1;
                buildDatas(page, false);
            }
        }).setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                page++;
                buildDatas(page, false);
            }
        });
        adapter.setSetOnClick(new MyCommentAdapter.setOnClick() {
            @Override
            public void setOnClick(int resourceId, int commentId, String commentStatus, int type) {
                ////1:资源，2：服务商
                if (type == 1) {
                    CoopDetailActivity.startx(MyCommentActivity.this, resourceId, commentId, commentStatus);
                } else {
                    ServiceDetailActivity.startx(MyCommentActivity.this, resourceId, commentId, commentStatus);
                }
            }
        });
    }

    private void buildDatas(final int page, boolean isShow) {
        topStatusBar(true);
        if (isShow) {
            showBookingToast(1);
        }
        RequestManager.getInstance().getMyComment(page, new GetMyCommentCallback() {
            @Override
            public void onSuccess(MyCommentBean bean) {
                dismissBookingToast();
                if (page == 1) {
                    datas.clear();
                }
                datas.addAll(bean.getList());
                adapter.notifyDataSetChanged();
                finishSwipe();
                if (bean.getList() != null) {
                    if (page == 1) {
                        if (bean.getList().size() == 0) {
                            yperchRl.setVisibility(View.VISIBLE);
                            adapter.removeAllFooterView();
                            mrefreshLayout.setEnableLoadMore(false);
                        } else {
                            yperchRl.setVisibility(View.GONE);
                            if (bean.getHasMore() == 0) {
                                adapter.setFooterView(footView);
                                mrefreshLayout.setEnableLoadMore(false);
                            } else {
                                adapter.removeAllFooterView();
                                mrefreshLayout.setEnableLoadMore(true);
                            }
                        }
                    } else {
                        if (bean.getHasMore() == 0) {
                            adapter.setFooterView(footView);
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
    public void onViewClicked(View v) {
        switch (v.getId()) {
            case R.id.bt_finish:
                finish();
                break;
            default:
                break;

        }
    }

}
