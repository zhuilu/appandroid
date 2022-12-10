package com.xinniu.android.qiqueqiao.fragment.maintab;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.xinniu.android.qiqueqiao.R;
import com.xinniu.android.qiqueqiao.adapter.MainActicityAdapter;
import com.xinniu.android.qiqueqiao.adapter.MainEnentsTwoAdapter;
import com.xinniu.android.qiqueqiao.base.LazyBaseFragment;
import com.xinniu.android.qiqueqiao.bean.ActivityColumnListBean;
import com.xinniu.android.qiqueqiao.bean.ActivityListBean;
import com.xinniu.android.qiqueqiao.customs.qldialog.QLTipDialog;
import com.xinniu.android.qiqueqiao.request.RequestManager;
import com.xinniu.android.qiqueqiao.request.callback.GetActivityListCallback;
import com.xinniu.android.qiqueqiao.request.callback.GetAvtivityColumnCallback;
import com.xinniu.android.qiqueqiao.utils.ComUtils;
import com.xinniu.android.qiqueqiao.utils.FullyLinearLayoutManager;
import com.xinniu.android.qiqueqiao.utils.ServiceBannerImgLoader;
import com.xinniu.android.qiqueqiao.utils.ToastUtils;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.listener.OnBannerListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class BusinessActivitiesFragment extends LazyBaseFragment {
    @BindView(R.id.mindex_ban)
    Banner mindexBan;
    @BindView(R.id.activity_recycler)
    RecyclerView activityRecycler;
    @BindView(R.id.activity_recycler_more)
    RecyclerView activityRecyclerMore;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    @BindView(R.id.rlayout_bt)
    RelativeLayout rlayoutBt;
    @BindView(R.id.ll)
    LinearLayout ll;
    private List<String> bannerImgs = new ArrayList<>();
    private List<ActivityColumnListBean.BannerBean> bannerDatas = new ArrayList<>();

    private MainEnentsTwoAdapter mainEnentsTwoAdapter;
    private MainActicityAdapter mainActicityAdapter;
    private List<ActivityListBean.ListBean> mDatas = new ArrayList<>();
    private List<ActivityColumnListBean.ListBean> mDataTop = new ArrayList<>();
    private int mPage = 1;

    public static BusinessActivitiesFragment newInstance() {

        Bundle args = new Bundle();
        BusinessActivitiesFragment fragment = new BusinessActivitiesFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_bussiness_activities;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        mindexBan.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);
        mindexBan.setImageLoader(new ServiceBannerImgLoader());
        mindexBan.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {
                //跳转待定
                if (!TextUtils.isEmpty(bannerDatas.get(position).getUrl())) {
                    ComUtils.goToBannerNext(mContext, bannerDatas.get(position).getUrl(), true);
                }
            }
        });
        //推荐资源大类
        mainActicityAdapter = new MainActicityAdapter((AppCompatActivity) getActivity(), R.layout.item_main_activity_top, mDataTop);
        LinearLayoutManager manager = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
        activityRecycler.setLayoutManager(manager);
        activityRecycler.setAdapter(mainActicityAdapter);


        FullyLinearLayoutManager linearLayoutManager = new FullyLinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };

        activityRecyclerMore.setLayoutManager(linearLayoutManager);
        mainEnentsTwoAdapter = new MainEnentsTwoAdapter((AppCompatActivity) getActivity(), R.layout.item_main_activity_two, mDatas);
        mainEnentsTwoAdapter.setEnableLoadMore(true);
        activityRecyclerMore.setAdapter(mainEnentsTwoAdapter);


        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                //获取banner
                getServiceBanner();
                mPage = 1;
                buildData();
            }
        });
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                mPage++;
                buildData();
            }
        });
        refreshLayout.setEnableFooterTranslationContent(true);
        refreshLayout.setEnableRefresh(true);
        refreshLayout.setEnableLoadMore(true);
        refreshLayout.setEnableNestedScroll(true);
        refreshLayout.setEnableOverScrollDrag(true);
    }

    @Override
    protected void lazyLoad() {
        //   showBookingToast(3);
        //获取banner
        getServiceBanner();
        buildData();
    }

    private void getServiceBanner() {
        RequestManager.getInstance().getActivityColumnList(new GetAvtivityColumnCallback() {
            @Override
            public void onSuccess(ActivityColumnListBean list) {

                if (list.getBanner().size() == 0) {
                    mindexBan.setVisibility(View.GONE);
                } else {
                    mindexBan.setVisibility(View.VISIBLE);
                    bannerImgs.clear();
                    bannerDatas.clear();
                    bannerDatas.addAll(list.getBanner());
                    for (int i = 0; i < list.getBanner().size(); i++) {
                        bannerImgs.add(list.getBanner().get(i).getImg());
                    }
                    mindexBan.setImages(bannerImgs);
                    mindexBan.start();
                }

                if (list.getList().size() == 0) {
                    ll.setVisibility(View.GONE);
                    rlayoutBt.setVisibility(View.GONE);
                } else {
                    rlayoutBt.setVisibility(View.VISIBLE);
                    ll.setVisibility(View.VISIBLE);
                    mDataTop.clear();
                    mDataTop.addAll(list.getList());
                    mainActicityAdapter.notifyDataSetChanged();

                }
            }

            @Override
            public void onFailed(int code, String msg) {
                // dismissBookingToast();
                ToastUtils.showCentetToast(getActivity(), msg);
            }
        });
    }

    private void buildData() {
        RequestManager.getInstance().getActivityList(0, 0, mPage, new GetActivityListCallback() {
            @Override
            public void onSuccess(ActivityListBean bean) {
                //   dismissBookingToast();
                finishSwipe();
                if (mPage == 1) {
                    mDatas.clear();

                    if (bean.getList().size() == 0) {
                        rlayoutBt.setVisibility(View.GONE);
                        mainEnentsTwoAdapter.removeAllFooterView();
                        if (refreshLayout != null) {
                            refreshLayout.setEnableLoadMore(false);
                        }

                    } else {
                        rlayoutBt.setVisibility(View.VISIBLE);
                        if (bean.getHasMore() == 0) {
                            mainEnentsTwoAdapter.setFooterView(footView);
                            if (refreshLayout != null) {
                                refreshLayout.setEnableLoadMore(false);
                            }

                        } else {
                            mainEnentsTwoAdapter.removeAllFooterView();
                            if (refreshLayout != null) {
                                refreshLayout.setEnableLoadMore(true);
                            }

                        }
                    }
                } else {
                    if (bean.getHasMore() == 0) {
                        mainEnentsTwoAdapter.setFooterView(footView);
                        if (refreshLayout != null) {
                            refreshLayout.setEnableLoadMore(false);
                        }
                    } else {
                        mainEnentsTwoAdapter.removeAllFooterView();
                        if (refreshLayout != null) {
                            refreshLayout.setEnableLoadMore(true);
                        }
                    }
                }

                mDatas.addAll(bean.getList());
                mainEnentsTwoAdapter.notifyDataSetChanged();


            }

            @Override
            public void onFailed(int code, String msg) {
                //  dismissBookingToast();
                finishSwipe();
                ToastUtils.showCentetToast(getActivity(), msg);
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

    @Override
    public void onStart() {
        super.onStart();
        mindexBan.startAutoPlay();

    }

    @Override
    public void onStop() {
        super.onStop();
        mindexBan.stopAutoPlay();

    }

    @OnClick(R.id.rlayout_release_template)
    public void onClick() {
        new QLTipDialog.Builder(getActivity())
                .setCancelable(true)
                .setCancelableOnTouchOutside(true)
                .setMessage("如需发布活动，请在电脑上登录企鹊桥官网 www.qiqueqiao.com")
                .setNegativeButton("知道了", new QLTipDialog.INegativeCallback() {
                    @Override
                    public void onClick() {
                        dissMissDialog();

                    }
                })
                .show((AppCompatActivity) getActivity());

    }
}
