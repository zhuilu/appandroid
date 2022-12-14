package com.xinniu.android.qiqueqiao.fragment.maintab;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.xinniu.android.qiqueqiao.R;
import com.xinniu.android.qiqueqiao.activity.CertificationActivity;
import com.xinniu.android.qiqueqiao.activity.CompanyEditActivity;
import com.xinniu.android.qiqueqiao.activity.LoginNewActivity;
import com.xinniu.android.qiqueqiao.activity.PublicRewardAvtivity;
import com.xinniu.android.qiqueqiao.adapter.RewardListAdapter;
import com.xinniu.android.qiqueqiao.base.LazyBaseFragment;
import com.xinniu.android.qiqueqiao.bean.RewardListBean;
import com.xinniu.android.qiqueqiao.customs.qldialog.QLTipDialog;
import com.xinniu.android.qiqueqiao.request.RequestManager;
import com.xinniu.android.qiqueqiao.request.callback.CommonCallback;
import com.xinniu.android.qiqueqiao.request.callback.getRewardListCallback;
import com.xinniu.android.qiqueqiao.request.converter.ResultDO;
import com.xinniu.android.qiqueqiao.user.UserInfoHelper;
import com.xinniu.android.qiqueqiao.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class RewardFragment extends LazyBaseFragment {

    @BindView(R.id.tv_empty)
    TextView tvEmpty;
    @BindView(R.id.yperch_Rl)
    RelativeLayout yperchRl;
    @BindView(R.id.rv)
    RecyclerView rv;
    @BindView(R.id.system_smartrefresh)
    SmartRefreshLayout systemSmartrefresh;
    private List<RewardListBean.ListBean> datas = new ArrayList<>();
    private RewardListAdapter rewardListAdapter;
    private int page = 1;

    public static RewardFragment newInstance() {
        Bundle args = new Bundle();
        RewardFragment fragment = new RewardFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_reward_list;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
        rv.setLayoutManager(linearLayoutManager);
        rewardListAdapter = new RewardListAdapter((AppCompatActivity) getActivity(), R.layout.item_reward, datas);
        rv.setAdapter(rewardListAdapter);
        rv.setNestedScrollingEnabled(false);

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
    }

    @Override
    protected void lazyLoad() {
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
                                rewardListAdapter.setFooterView(footView);
                                systemSmartrefresh.setEnableLoadMore(false);
                            } else {
                                rewardListAdapter.removeAllFooterView();
                                systemSmartrefresh.setEnableLoadMore(true);
                            }
                        }

                    } else {
                        if (item.getHasMore() == 0) {
                            rewardListAdapter.setFooterView(footView);
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
                ToastUtils.showCentetToast(getActivity(), msg);

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

    @OnClick(R.id.tv_launch_reward)
    public void onClick() {
        if(!isLogin()){
            return;
        }
        toReleaseActivity();
    }

    /**
     * ?????????????????????
     */
    public void toReleaseActivity() {
        showBookingToast(2);
        RequestManager.getInstance().isPerfect(new CommonCallback() {
            @Override
            public void onSuccess(ResultDO resultDO) {
                dismissBookingToast();
                PublicRewardAvtivity.start(getActivity());
            }

            @Override
            public void onFailed(int code, final String msg) {
                dismissBookingToast();
                if (code == 202) {
                    new QLTipDialog.Builder(getActivity())
                            .setCancelable(true)
                            .setCancelableOnTouchOutside(true)
                            .setMessage(msg)
                            .setNegativeButton("????????????", new QLTipDialog.INegativeCallback() {
                                @Override
                                public void onClick() {

                                }
                            })
                            .show((AppCompatActivity) getActivity());
                } else if (code == 305) {

                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setMessage(msg);
                    builder.setPositiveButton("?????????", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
//                            ToastUtils.showCentetImgToast(MainActivity.this, msg);
                            Intent intent = new Intent(getActivity(), CompanyEditActivity.class);

                            startActivity(intent);
                            dialog.dismiss();
                        }
                    });
                    builder.setNegativeButton("??????", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    builder.show();
                } else if (code == 310) {
                    //???????????????
                    new QLTipDialog.Builder(getActivity())
                            .setCancelable(true)
                            .setCancelableOnTouchOutside(true)
                            .setMessage(msg)
                            .setNegativeButton("??????", new QLTipDialog.INegativeCallback() {
                                @Override
                                public void onClick() {

                                }
                            }).setPositiveButton("?????????", new QLTipDialog.IPositiveCallback() {
                        @Override
                        public void onClick() {
                            CertificationActivity.start(getActivity(), 1);
                        }
                    })
                            .show((AppCompatActivity) getActivity());
                }
            }
        });
    }
    private boolean isLogin() {
        if (!UserInfoHelper.getIntance().isLogin()) {
            // TODO: 2017/12/20
//            ToastUtils.showCentetImgToast(getContext(), "????????????");
            LoginNewActivity.start(mContext);
        }
        return UserInfoHelper.getIntance().isLogin();
    }
}
