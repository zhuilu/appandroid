package com.xinniu.android.qiqueqiao.fragment.maintab;

import android.os.Bundle;
import android.text.TextPaint;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.xinniu.android.qiqueqiao.R;
import com.xinniu.android.qiqueqiao.activity.AllServiceTypeActivity;
import com.xinniu.android.qiqueqiao.activity.ServiceCategoryListActivity;
import com.xinniu.android.qiqueqiao.adapter.IndexServiceAdapter;
import com.xinniu.android.qiqueqiao.adapter.ServiceClassAdapter;
import com.xinniu.android.qiqueqiao.base.LazyBaseFragment;
import com.xinniu.android.qiqueqiao.bean.IndexServiceBean;
import com.xinniu.android.qiqueqiao.bean.ServiceBannerBean;
import com.xinniu.android.qiqueqiao.bean.ServiceCategoryAndTag;
import com.xinniu.android.qiqueqiao.customs.MyScrollView;
import com.xinniu.android.qiqueqiao.request.RequestManager;
import com.xinniu.android.qiqueqiao.request.callback.GetServiceBannerCallback;
import com.xinniu.android.qiqueqiao.request.callback.GetServiceCategoryAndTagCallback;
import com.xinniu.android.qiqueqiao.request.callback.ServiceListCallback;
import com.xinniu.android.qiqueqiao.utils.ComUtils;
import com.xinniu.android.qiqueqiao.utils.ServiceBannerImgLoader;
import com.xinniu.android.qiqueqiao.utils.ToastUtils;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.listener.OnBannerListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class ServiceFragment extends LazyBaseFragment {
    @BindView(R.id.bt_finish)
    RelativeLayout btFinish;
    @BindView(R.id.mpublish_titletv)
    TextView mpublishTitletv;
    @BindView(R.id.tv_release_template)
    TextView tvReleaseTemplate;
    @BindView(R.id.tool_bar)
    RelativeLayout toolBar;
    @BindView(R.id.item_need_recycler_screenthree)
    RecyclerView itemNeedRecyclerScreenthree;
    @BindView(R.id.tv_hot)
    TextView tvHot;
    @BindView(R.id.view_01)
    View view01;
    @BindView(R.id.rlayout_hot)
    RelativeLayout rlayoutHot;
    @BindView(R.id.tv_new)
    TextView tvNew;
    @BindView(R.id.view_02)
    View view02;
    @BindView(R.id.rlayout_new)
    RelativeLayout rlayoutNew;
    @BindView(R.id.view)
    View view;
    @BindView(R.id.top)
    LinearLayout top;
    @BindView(R.id.recyclerIndexCell)
    RecyclerView recyclerIndexCell;
    @BindView(R.id.indexScroll)
    MyScrollView indexScroll;
    @BindView(R.id.refreshIndexCell)
    SmartRefreshLayout refreshIndexCell;
    @BindView(R.id.yperch_Rl)
    RelativeLayout yperchRl;
    @BindView(R.id.tv_hot_1)
    TextView tvHot1;
    @BindView(R.id.view_011)
    View view011;
    @BindView(R.id.rlayout_hot_1)
    RelativeLayout rlayoutHot1;
    @BindView(R.id.tv_new_1)
    TextView tvNew1;
    @BindView(R.id.view_022)
    View view022;
    @BindView(R.id.rlayout_new_1)
    RelativeLayout rlayoutNew1;
    @BindView(R.id.view_1)
    View view1;
    @BindView(R.id.llayout_top)
    LinearLayout llayoutTop;
    @BindView(R.id.mindex_ban)
    Banner mindexBan;
    @BindView(R.id.img_release)
    ImageView imgRelease;
    private int mSearchPage = 1;
    private IndexServiceAdapter indexServiceAdapter;
    private List<IndexServiceBean.ListBean> data = new ArrayList<>();
    private View footView;
    private ServiceClassAdapter serviceClassAdapter;
    private List<ServiceCategoryAndTag> datas = new ArrayList<>();
    private int p_id = 0;
    private String sort_order = "2";
    private int query_id = 0;
    private List<String> bannerImgs = new ArrayList<>();
    private List<ServiceBannerBean> bannerDatas = new ArrayList<>();
    public static ServiceFragment newInstance() {
        Bundle args = new Bundle();
        ServiceFragment fragment = new ServiceFragment();
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public int getLayoutId() {
        return R.layout.activity_index_service;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        //最新
        TextPaint tp011 = tvHot.getPaint();
        tp011.setFakeBoldText(false);
        tvHot.setTextColor(getResources().getColor(R.color._999999));

        TextPaint tp022 = tvNew.getPaint();
        tp022.setFakeBoldText(true);
        tvNew.setTextColor(getResources().getColor(R.color._333));

        TextPaint tp033 = tvHot1.getPaint();
        tp033.setFakeBoldText(false);
        tvHot1.setTextColor(getResources().getColor(R.color._999999));

        TextPaint tp044 = tvNew1.getPaint();
        tp044.setFakeBoldText(true);
        tvNew1.setTextColor(getResources().getColor(R.color._333));

        GridLayoutManager manager = new GridLayoutManager(mContext, 3);
        itemNeedRecyclerScreenthree.setLayoutManager(manager);
        serviceClassAdapter = new ServiceClassAdapter(mContext, R.layout.item_sercice_class, datas);
        itemNeedRecyclerScreenthree.setAdapter(serviceClassAdapter);
        serviceClassAdapter.setSetOnClick(new ServiceClassAdapter.setOnClick() {
            @Override
            public void setAddOnClick(ServiceCategoryAndTag item) {
                if (item.getId() == -1) {
                    AllServiceTypeActivity.start(getActivity());

                } else {

                    Gson g = new Gson();
                    String jsonString = g.toJson(item.getZlist());
                    ServiceCategoryListActivity.start(getActivity(), item.getId(), item.getName(), jsonString);

                }
            }
        });

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
        recyclerIndexCell.setLayoutManager(linearLayoutManager);
        indexServiceAdapter = new IndexServiceAdapter(getActivity(), R.layout.item_service, data, 2);
        recyclerIndexCell.setAdapter(indexServiceAdapter);
        recyclerIndexCell.setNestedScrollingEnabled(false);
        footView = getLayoutInflater().inflate(R.layout.view_unload_more, null);

        refreshIndexCell.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                loadDatax();
                //获取banner
                getServiceBanner();
            }
        });
        refreshIndexCell.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                mSearchPage++;
                buildData(mSearchPage, sort_order, query_id, p_id);
            }
        });
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

        indexScroll.setListener(new MyScrollView.OnScrollListener() {
            @Override
            public void onScroll(int scrollY) {
                int ps = top.getBottom();
                if (scrollY >= ps) {
                    llayoutTop.setVisibility(View.VISIBLE);
                } else {
                    llayoutTop.setVisibility(View.GONE);

                }

            }
        });
    }

    @Override
    protected void lazyLoad() {
     //   showBookingToast(3);
        loadDatax();
        //获取banner
        getServiceBanner();
    }


    private void getServiceBanner() {
        RequestManager.getInstance().getServiceBanner(new GetServiceBannerCallback() {
            @Override
            public void onSuccess(List<ServiceBannerBean> list) {
                if (list.size() == 0) {
                    mindexBan.setVisibility(View.GONE);
                } else{
                    mindexBan.setVisibility(View.VISIBLE);
                    bannerImgs.clear();
                    bannerDatas.clear();
                    bannerDatas.addAll(list);
                    for (int i = 0; i < list.size(); i++) {
                        bannerImgs.add(list.get(i).getImg());
                    }
                    mindexBan.setImages(bannerImgs);
                    mindexBan.start();
                }
            }

            @Override
            public void onFailed(int code, String msg) {
                mindexBan.setVisibility(View.GONE);
            }
        });
    }

    private void buildData(final int mSearchPage, String sort_order, int query_id, int p_id) {
        RequestManager.getInstance().getServiceInfoList(mSearchPage, sort_order, query_id, p_id, new ServiceListCallback() {
            @Override
            public void onSuccess(IndexServiceBean bean) {
               // dismissBookingToast();
                yperchRl.setVisibility(View.GONE);
                if (mSearchPage == 1) {
                    data.clear();
                    if (bean.getList().size() == 0) {
                        yperchRl.setVisibility(View.VISIBLE);
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
                data.addAll(bean.getList());
                indexServiceAdapter.notifyDataSetChanged();

                finishSwipe();

            }

            @Override
            public void onFailue(int code, String msg) {
               // dismissBookingToast();
                ToastUtils.showCentetToast(getActivity(), msg);
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

    private void loadDatax() {
        RequestManager.getInstance().getCategoryAndTag(new GetServiceCategoryAndTagCallback() {

            @Override
            public void onSuccess(List<ServiceCategoryAndTag> item) {

                datas.clear();
                if (item.size() > 5) {
                    for (int i = 0; i < 5; i++) {
                        datas.add(item.get(i));
                    }
                } else {
                    datas.addAll(item);
                }
                ServiceCategoryAndTag serviceCategoryAndTag = new ServiceCategoryAndTag();
                serviceCategoryAndTag.setId(-1);
                serviceCategoryAndTag.setName("全部分类");
                //照片每次后台上传，地址不变
                serviceCategoryAndTag.setImg("https://qqq2017.oss-cn-hangzhou.aliyuncs.com/%E5%85%A8%E9%83%A8%403x.png");
                datas.add(serviceCategoryAndTag);
                serviceClassAdapter.notifyDataSetChanged();
                buildData(mSearchPage, sort_order, query_id, p_id);
            }

            @Override
            public void onFailed(int code, String msg) {

                buildData(mSearchPage, sort_order, query_id, p_id);
            }
        });
    }
    @OnClick({R.id.rlayout_hot, R.id.rlayout_new, R.id.rlayout_hot_1, R.id.rlayout_new_1})
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.rlayout_hot:
                sort_order = "1";
                //最热
                TextPaint tp01 = tvHot.getPaint();
                tp01.setFakeBoldText(true);
                tvHot.setTextColor(getResources().getColor(R.color._333));

                TextPaint tp02 = tvNew.getPaint();
                tp02.setFakeBoldText(false);
                tvNew.setTextColor(getResources().getColor(R.color._999999));

                TextPaint tp03 = tvHot1.getPaint();
                tp03.setFakeBoldText(true);
                tvHot1.setTextColor(getResources().getColor(R.color._333));

                TextPaint tp04 = tvNew1.getPaint();
                tp04.setFakeBoldText(false);
                tvNew1.setTextColor(getResources().getColor(R.color._999999));
                mSearchPage = 1;
                buildData(mSearchPage, sort_order, query_id, p_id);
                break;
            case R.id.rlayout_new:
                sort_order = "2";
                //最新
                TextPaint tp011 = tvHot.getPaint();
                tp011.setFakeBoldText(false);
                tvHot.setTextColor(getResources().getColor(R.color._999999));

                TextPaint tp022 = tvNew.getPaint();
                tp022.setFakeBoldText(true);
                tvNew.setTextColor(getResources().getColor(R.color._333));

                TextPaint tp033 = tvHot1.getPaint();
                tp033.setFakeBoldText(false);
                tvHot1.setTextColor(getResources().getColor(R.color._999999));

                TextPaint tp044 = tvNew1.getPaint();
                tp044.setFakeBoldText(true);
                tvNew1.setTextColor(getResources().getColor(R.color._333));

                mSearchPage = 1;
                buildData(mSearchPage, sort_order, query_id, p_id);

                break;
            case R.id.rlayout_hot_1:
                sort_order = "1";
                //最热
                TextPaint tp0111 = tvHot.getPaint();
                tp0111.setFakeBoldText(true);
                tvHot.setTextColor(getResources().getColor(R.color._333));

                TextPaint tp0222 = tvNew.getPaint();
                tp0222.setFakeBoldText(false);
                tvNew.setTextColor(getResources().getColor(R.color._999999));

                TextPaint tp0333 = tvHot1.getPaint();
                tp0333.setFakeBoldText(true);
                tvHot1.setTextColor(getResources().getColor(R.color._333));

                TextPaint tp0444 = tvNew1.getPaint();
                tp0444.setFakeBoldText(false);
                tvNew1.setTextColor(getResources().getColor(R.color._999999));
                mSearchPage = 1;
                buildData(mSearchPage, sort_order, query_id, p_id);
                int ps = top.getBottom();
                indexScroll.smoothScrollTo(0, ps);
                break;
            case R.id.rlayout_new_1:
                sort_order = "2";
                //最新
                TextPaint tp01111 = tvHot.getPaint();
                tp01111.setFakeBoldText(false);
                tvHot.setTextColor(getResources().getColor(R.color._999999));

                TextPaint tp02222 = tvNew.getPaint();
                tp02222.setFakeBoldText(true);
                tvNew.setTextColor(getResources().getColor(R.color._333));

                TextPaint tp03333 = tvHot1.getPaint();
                tp03333.setFakeBoldText(false);
                tvHot1.setTextColor(getResources().getColor(R.color._999999));

                TextPaint tp04444 = tvNew1.getPaint();
                tp04444.setFakeBoldText(true);
                tvNew1.setTextColor(getResources().getColor(R.color._333));

                mSearchPage = 1;
                buildData(mSearchPage, sort_order, query_id, p_id);
                int ay = top.getBottom();
                indexScroll.smoothScrollTo(0, ay);

                break;
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

}
