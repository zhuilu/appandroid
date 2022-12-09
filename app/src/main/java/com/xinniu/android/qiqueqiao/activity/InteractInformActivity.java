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
import com.xinniu.android.qiqueqiao.R;
import com.xinniu.android.qiqueqiao.adapter.InteractInformAdapter;
import com.xinniu.android.qiqueqiao.base.BaseActivity;
import com.xinniu.android.qiqueqiao.bean.InteractiveNewsBean;
import com.xinniu.android.qiqueqiao.bean.NewsV2Bean;
import com.xinniu.android.qiqueqiao.request.RequestManager;
import com.xinniu.android.qiqueqiao.request.callback.GetInteractNewsCallback;
import com.xinniu.android.qiqueqiao.request.callback.GetNewsV2Callback;
import com.xinniu.android.qiqueqiao.utils.ShowUtils;
import com.xinniu.android.qiqueqiao.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 消息-互动通知
 * Created by yuchance on 2018/10/31.
 */

public class InteractInformActivity extends BaseActivity {
    @BindView(R.id.minform_recycler)
    RecyclerView minformRecycler;
    @BindView(R.id.mrefreshLayout)
    SmartRefreshLayout mrefreshLayout;
    @BindView(R.id.yperch_Rl)
    RelativeLayout yperchRl;
    private int page = 1;
    private int newnum = 0;


    private List<InteractiveNewsBean.ListBean> datas = new ArrayList<>();
    private InteractInformAdapter adapter;

    public static void start(Context context) {
        Intent intent = new Intent(context, InteractInformActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }


    @Override
    public int getLayoutId() {
        return R.layout.activity_interact_inform;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        topStatusBar(true);
        mrefreshLayout.setEnableRefresh(false);
        LinearLayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        minformRecycler.setLayoutManager(manager);
        adapter = new InteractInformAdapter(R.layout.item_interact_inform, datas);
        minformRecycler.setAdapter(adapter);
        adapter.setSetOnclick(new InteractInformAdapter.setOnclick() {
            @Override
            public void setOnClick(int resourceId, int commentId,int type) {
                if(type==1) {
                    CoopDetailActivity.startx(InteractInformActivity.this, resourceId, commentId);
                }else{
                    ServiceDetailActivity.startx(InteractInformActivity.this, resourceId, commentId);
                }
            }
        });
        RequestManager.getInstance().getNewsV2(new GetNewsV2Callback() {
            @Override
            public void onSuccess(NewsV2Bean bean) {
                newnum = bean.getInteractive().getNum();
                buildDatas(page, true);
            }

            @Override
            public void onFailed(int code, String msg) {

            }
        });
        mrefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                page++;
                buildDatas(page, false);
            }
        });


    }

    private void buildDatas(final int page, boolean isShow) {
        if (isShow) {
            showBookingToast(1);
        }
        RequestManager.getInstance().getInteractiveNews(page, new GetInteractNewsCallback() {
            @Override
            public void onSuccess(InteractiveNewsBean bean) {
                if (page == 1) {
                    datas.clear();

                    if (bean.getList().size() == 0) {
                        ShowUtils.showViewVisible(yperchRl, View.VISIBLE);
                        adapter.removeAllFooterView();
                        mrefreshLayout.setEnableLoadMore(false);
                    } else {
                        ShowUtils.showViewVisible(yperchRl, View.GONE);
                        if (bean.getHasMore() == 0) {
                            adapter.setFooterView(footView1);
                            mrefreshLayout.setEnableLoadMore(false);
                        } else {
                            adapter.removeAllFooterView();
                            mrefreshLayout.setEnableLoadMore(true);
                        }
                    }
                } else {
                    if (bean.getHasMore() == 0) {
                        adapter.setFooterView(footView);
                        if (mrefreshLayout != null) {
                            mrefreshLayout.setEnableLoadMore(false);
                        }
                    } else {
                        adapter.removeAllFooterView();
                        if (mrefreshLayout != null) {
                            mrefreshLayout.setEnableLoadMore(true);
                        }
                    }
                }
                datas.addAll(bean.getList());
                for (int i = 0; i < datas.size(); i++) {
                    if (i < newnum) {
                        datas.get(i).setNew(true);
                    } else {
                        datas.get(i).setNew(false);
                    }
                }
                adapter.notifyDataSetChanged();
                dismissBookingToast();
                finishSwipe();
            }

            @Override
            public void onFailed(int code, String msg) {
                ToastUtils.showCentetToast(InteractInformActivity.this, msg);
                dismissBookingToast();
                finishSwipe();
            }
        });
    }

    @OnClick(R.id.bt_finish)
    public void onViewClicked() {
        finish();
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

}
