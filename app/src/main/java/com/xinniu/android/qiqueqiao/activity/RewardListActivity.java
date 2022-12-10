package com.xinniu.android.qiqueqiao.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.xinniu.android.qiqueqiao.R;
import com.xinniu.android.qiqueqiao.adapter.RewardListAdapter;
import com.xinniu.android.qiqueqiao.base.BaseActivity;
import com.xinniu.android.qiqueqiao.bean.RewardListBean;
import com.xinniu.android.qiqueqiao.customs.qldialog.QLTipDialog;
import com.xinniu.android.qiqueqiao.request.RequestManager;
import com.xinniu.android.qiqueqiao.request.callback.CommonCallback;
import com.xinniu.android.qiqueqiao.request.callback.getRewardListCallback;
import com.xinniu.android.qiqueqiao.request.converter.ResultDO;
import com.xinniu.android.qiqueqiao.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class RewardListActivity extends BaseActivity {

    @BindView(R.id.yperch_Rl)
    RelativeLayout yperchRl;
    @BindView(R.id.rv)
    RecyclerView rv;
    @BindView(R.id.system_smartrefresh)
    SmartRefreshLayout systemSmartrefresh;
    @BindView(R.id.bt_finish)
    RelativeLayout btFinish;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tool_bar)
    RelativeLayout toolBar;
    @BindView(R.id.view)
    View view;
    @BindView(R.id.tv_empty)
    TextView tvEmpty;
    @BindView(R.id.img_release)
    ImageView imgRelease;
    private List<RewardListBean.ListBean> datas = new ArrayList<>();
    private RewardListAdapter rewardListAdapter;
    private int page = 1;

    public static void start(Context context) {
        Intent starter = new Intent(context, RewardListActivity.class);
        context.startActivity(starter);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_reward_list;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        topStatusBar(true);
        toolBar.setVisibility(View.VISIBLE);
        imgRelease.setVisibility(View.VISIBLE);
        view.setVisibility(View.VISIBLE);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
        rv.setLayoutManager(linearLayoutManager);
        rewardListAdapter = new RewardListAdapter(RewardListActivity.this, R.layout.item_reward, datas);
        rv.setAdapter(rewardListAdapter);

        systemSmartrefresh.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                page = 1;
                buildData(page);
            }
        });
        systemSmartrefresh.setOnLoadMoreListener(new OnLoadMoreListener() {
                                                     @Override
                                                     public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                                                         page++;
                                                         buildData(page);
                                                     }
                                                 }

        );
        buildData(page);
    }


    private void buildData(final int page) {
        RequestManager.getInstance().getRewardList(page, new getRewardListCallback() {

            @Override
            public void onSuccess(RewardListBean item) {
                if (page == 1) {
                    datas.clear();
                }
                datas.addAll(item.getList());
                rewardListAdapter.notifyDataSetChanged();
                finishSwipe();
                if (item.getList() != null) {
                    if (page == 1) {
                        if (item.getList().size() == 0) {
                            yperchRl.setVisibility(View.VISIBLE);
                            rewardListAdapter.removeAllFooterView();
                            systemSmartrefresh.setEnableLoadMore(false);
                        } else {
                            yperchRl.setVisibility(View.GONE);
                            if (item.getHasMore() == 0) {
                                rewardListAdapter.setFooterView(footView1);
                                systemSmartrefresh.setEnableLoadMore(false);
                            } else {
                                rewardListAdapter.removeAllFooterView();
                                systemSmartrefresh.setEnableLoadMore(true);
                            }
                        }

                    } else {
                        if (item.getHasMore() == 0) {
                            rewardListAdapter.setFooterView(footView1);
                            systemSmartrefresh.setEnableLoadMore(false);
                        } else {
                            rewardListAdapter.removeAllFooterView();
                            systemSmartrefresh.setEnableLoadMore(true);
                        }
                    }
                } else {
                    yperchRl.setVisibility(View.VISIBLE);
                }

            }

            @Override
            public void onFailed(int code, String msg) {
                ToastUtils.showCentetToast(RewardListActivity.this, msg);

            }
        });
    }

    private void finishSwipe() {
        if (systemSmartrefresh != null) {
            if (systemSmartrefresh.isEnableRefresh()) {
                systemSmartrefresh.finishRefresh();
            }
            if (systemSmartrefresh.isEnableLoadMore()) {
                systemSmartrefresh.finishLoadMore();
            }
        }
    }

    @OnClick({R.id.bt_finish, R.id.img_release})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_finish:
                finish();
                break;
            case R.id.img_release:
                toReleaseActivity();
                break;
        }
    }

    /**
     * 转调到发布页面
     */
    public void toReleaseActivity() {
        showBookingToast(2);
        RequestManager.getInstance().isPerfectR(new CommonCallback() {
            @Override
            public void onSuccess(ResultDO resultDO) {
                dismissBookingToast();
                PublicRewardAvtivity.start(RewardListActivity.this);
            }

            @Override
            public void onFailed(int code, final String msg) {
                dismissBookingToast();
                if (code == 202) {
                    new QLTipDialog.Builder(RewardListActivity.this)
                            .setCancelable(true)
                            .setCancelableOnTouchOutside(true)
                            .setMessage(msg)
                            .setNegativeButton("我知道了", new QLTipDialog.INegativeCallback() {
                                @Override
                                public void onClick() {

                                }
                            })
                            .show(RewardListActivity.this);
                } else if (code == 305) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(RewardListActivity.this);
                    builder.setMessage(msg);
                    builder.setPositiveButton("去完善", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
//                            ToastUtils.showCentetImgToast(MainActivity.this, msg);
                            Intent intent = new Intent(RewardListActivity.this, CompanyEditActivity.class);

                            startActivity(intent);
                            dialog.dismiss();
                        }
                    });
                    builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    builder.show();
                }
            }
        });
    }

}
