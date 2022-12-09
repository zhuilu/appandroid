package com.xinniu.android.qiqueqiao.fragment.maintab;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
//import android.support.annotation.NonNull;
//import android.support.v7.widget.GridLayoutManager;
//import android.support.v7.widget.LinearLayoutManager;
//import androidx.recyclerview.widget.RecyclerView;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.scwang.smartrefresh.layout.util.DensityUtil;
import com.umeng.analytics.MobclickAgent;
import com.xinniu.android.qiqueqiao.R;
import com.xinniu.android.qiqueqiao.activity.IndexCellActivity;
import com.xinniu.android.qiqueqiao.activity.IndexClassifyActivity;
import com.xinniu.android.qiqueqiao.adapter.IndexCellTwoAdapter;
import com.xinniu.android.qiqueqiao.adapter.IndexNewAdapter;
import com.xinniu.android.qiqueqiao.adapter.IndexTabAdapter;
import com.xinniu.android.qiqueqiao.base.LazyBaseFragment;
import com.xinniu.android.qiqueqiao.bean.GetConfigBean;
import com.xinniu.android.qiqueqiao.bean.IndexNewBean;
import com.xinniu.android.qiqueqiao.bean.SourceScreenBean;
import com.xinniu.android.qiqueqiao.common.Constants;
import com.xinniu.android.qiqueqiao.customs.CellScreenWindow;
import com.xinniu.android.qiqueqiao.customs.MyScrollView;
import com.xinniu.android.qiqueqiao.customs.ScrollLinearLayoutManager;
import com.xinniu.android.qiqueqiao.request.RequestManager;
import com.xinniu.android.qiqueqiao.request.callback.GetConfigCallback;
import com.xinniu.android.qiqueqiao.request.callback.GetResourceListCallback;
import com.xinniu.android.qiqueqiao.request.callback.SourceScreenCallback;
import com.xinniu.android.qiqueqiao.user.UserInfoHelper;
import com.xinniu.android.qiqueqiao.utils.ComUtils;
import com.xinniu.android.qiqueqiao.utils.FullyLinearLayoutManager;
import com.xinniu.android.qiqueqiao.utils.ResouceHelper;
import com.xinniu.android.qiqueqiao.utils.ServiceBannerImgLoader;
import com.xinniu.android.qiqueqiao.utils.ShowUtils;
import com.xinniu.android.qiqueqiao.utils.ToastUtils;
import com.xinniu.android.qiqueqiao.widget.CircleImageView;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.listener.OnBannerListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import fj.mtsortbutton.lib.DynamicSoreView;
import fj.mtsortbutton.lib.Interface.IDynamicSore;

/**
 * 首页-谈合作
 * Created by BDXK on 2017/11/7.
 */

public class ResourceFragment extends LazyBaseFragment {

    @BindView(R.id.mindex_ban)
    Banner mindexBan;
    // @BindView(R.id.mCompanyDySoreView_index)
    DynamicSoreView mCompanyDySoreViewIndex;
    @BindView(R.id.rcy_tab)
    RecyclerView rcyTab;
    @BindView(R.id.tv_recommed)
    TextView tvRecommed;
    @BindView(R.id.tv_hy)
    TextView tvHy;
    @BindView(R.id.tv_new)
    TextView tvNew;
    @BindView(R.id.xtvOrder_2)
    TextView xtvOrder2;
    @BindView(R.id.rlayout_1)
    RelativeLayout rlayout1;
    @BindView(R.id.indexnoImg)
    ImageView indexnoImg;
    @BindView(R.id.rlIndexblank)
    RelativeLayout rlIndexblank;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.indexScroll)
    MyScrollView indexScroll;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    @BindView(R.id.tv_recommed_01)
    TextView tvRecommed01;
    @BindView(R.id.tv_hy_01)
    TextView tvHy01;
    @BindView(R.id.tv_new_01)
    TextView tvNew01;
    @BindView(R.id.xtvOrder)
    TextView xtvOrder;
    @BindView(R.id.xtopTabLinear)
    RelativeLayout xtopTabLinear;
    //首页列表
    private List<IndexNewBean.ListBean> resourceItems = new ArrayList<>();
    //首页列表请求参数--start
    private int mSearchCityId = 0;
    private String mSearchKeyWord;
    public int mSearchSortOrder = 34;
    private int mSearchCategory;
    private String mSearchQueryId;
    private String mSearchIndustry;

    private int mSearchPage = 1;
    private int mQueryType = 3;
    private IndexTabAdapter indexTabAdapter;
    List<GetConfigBean.EntranceListBean> data = new ArrayList<>();
    //首页列表请求参数--end

    private IndexNewAdapter adapter;
    private List<String> bannerImgs = new ArrayList<>();
    private List<GetConfigBean.BannerBean> configList = new ArrayList<>();
    private CellScreenWindow screenWindow;

    public static ResourceFragment newInstance() {
        Bundle args = new Bundle();
        ResourceFragment fragment = new ResourceFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public int getLayoutId() {
        return R.layout.fragment_main_resource;
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


    @Override
    public void initViews(Bundle savedInstanceState) {
        TextPaint tp01 = tvRecommed.getPaint();
        tp01.setFakeBoldText(true);
        tvRecommed.setTextColor(getResources().getColor(R.color._333));

        TextPaint tp02 = tvRecommed01.getPaint();
        tp02.setFakeBoldText(true);
        tvRecommed01.setTextColor(getResources().getColor(R.color._333));
        adapter = new IndexNewAdapter(getActivity(), R.layout.item_index_new, resourceItems, 2, 1);
        FullyLinearLayoutManager linearLayoutManager = new FullyLinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        adapter.setEnableLoadMore(true);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);
        mindexBan.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);
        mindexBan.setImageLoader(new ServiceBannerImgLoader());
        mindexBan.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {
                if (!TextUtils.isEmpty(configList.get(position).getUrl())) {
                    MobclickAgent.onEvent(mContext, "home_banner", configList.get(position).getUrl());//统计banner点击次数
                    ComUtils.goToBannerNext(mContext, configList.get(position).getUrl(), true);
                }
            }
        });

        GridLayoutManager manager = new GridLayoutManager(mContext, 3);
//        rcyTab.setLayoutManager(manager);
//        indexTabAdapter = new IndexTabAdapter(R.layout.item_main_recycler, data, getActivity());
//        rcyTab.setAdapter(indexTabAdapter);
//        LinearLayoutManager manager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false){
//            //禁止水平滑动
//            @Override
//            public boolean canScrollHorizontally() {
//                return false;
//            }
//        };
        rcyTab.setLayoutManager(manager);
        indexTabAdapter = new IndexTabAdapter(R.layout.item_main_recycler, data, getActivity());
        rcyTab.setAdapter(indexTabAdapter);

        mCompanyDySoreViewIndex.setiDynamicSore(new IDynamicSore() {
            @Override
            public void setGridView(View view, int i, List list) {
                final List<GetConfigBean.NavBean> datas = list;
                GridView gridView = (GridView) view.findViewById(R.id.gridView);
                gridView.setSelector(new ColorDrawable(Color.TRANSPARENT));
                mCompanyDySoreViewIndex.setNumColumns(gridView);
                IndexCellTwoAdapter adapter = new IndexCellTwoAdapter(mContext, datas);
                gridView.setAdapter(adapter);
                gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        if (datas.get(position).getIs_all() == 0) {
                            MobclickAgent.onEvent(mContext, "home_category", datas.get(position).getName());//统计点击次数
                            IndexCellActivity.start(mContext, datas.get(position).getId(), datas.get(position).getName());
                        } else {
                            IndexClassifyActivity.start(mContext);
                        }
                    }
                });
            }
        });

        //下拉刷新
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                mSearchPage = 1;
                UserInfoHelper.getIntance().setIndexList("");
                resourceItems.clear();
                buildConfig(false, false);
                loadDate(false);
            }
        });
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                mSearchPage++;
                loadDate(false);
            }
        });

        refreshLayout.setEnableFooterTranslationContent(true);
        refreshLayout.setEnableRefresh(true);
        refreshLayout.setEnableLoadMore(true);
        refreshLayout.setEnableNestedScroll(true);
        refreshLayout.setEnableOverScrollDrag(true);
        indexScroll.setListener(new MyScrollView.OnScrollListener() {
            @Override
            public void onScroll(int scrollY) {
                int ay = DensityUtil.dp2px(375f);
                //  Log.i("main====", ay + "==============" + scrollY);
                if (scrollY >= ay) {
                    xtopTabLinear.setVisibility(View.VISIBLE);
                } else {
                    xtopTabLinear.setVisibility(View.GONE);
                }
            }
        });

    }

    @Override
    protected void lazyLoad() {
        if (UserInfoHelper.getIntance().getIndexBannerList().length() > 0 && UserInfoHelper.getIntance().getIndexList().length() > 0) {
            //判断有缓存
            String json = UserInfoHelper.getIntance().getIndexBannerList();
            Gson gson = new Gson();
            GetConfigBean getConfigBean = gson.fromJson(json, new TypeToken<GetConfigBean>() {
            }.getType());
            if (getConfigBean.getEntranceList() != null && getConfigBean.getEntranceList().size() > 0) {//判断原来本地是否存了数据
                initBannerData(true, getConfigBean, false);

                //判断存储是否有数据
                String json2 = UserInfoHelper.getIntance().getIndexList();
                Gson gson2 = new Gson();
                IndexNewBean bean = gson2.fromJson(json2, new TypeToken<IndexNewBean>() {
                }.getType());
                initData(bean);
                buildConfig(true, false);
            } else {
                //重新请求
                showBookingToast(3);
                buildConfig(true, true);
            }

        } else {
            showBookingToast(3);
            buildConfig(true, true);
        }


    }

    /**
     * 获取banner,分类，活动数据
     *
     * @param isRefresh
     */
    private void buildConfig(final boolean isRefresh, final boolean isShow) {

        RequestManager.getInstance().getConfigV1(new GetConfigCallback() {
            @Override
            public void onSuccess(final GetConfigBean getConfigBean) {
                //存入数据并显示
                Gson gson = new Gson();
                String json = gson.toJson(getConfigBean);
                UserInfoHelper.getIntance().setIndexBannerList(json);
                initBannerData(isRefresh, getConfigBean, isShow);


            }

            @Override
            public void onFailed(int code, String msg) {
                dismissBookingToast();
                ToastUtils.showCentetImgToast(getActivity(), msg);
            }
        });

    }

    private void initBannerData(boolean isRefresh, GetConfigBean getConfigBean, boolean isShow) {
        bannerImgs.clear();
        configList.clear();
        for (int i = 0; i < getConfigBean.getBanner().size(); i++) {
            bannerImgs.add(getConfigBean.getBanner().get(i).getImg());

        }
        configList.addAll(getConfigBean.getBanner());
        if (mindexBan != null) {
            mindexBan.setImages(bannerImgs);
        }
        if (mindexBan != null) {
            mindexBan.start();
        }

        mCompanyDySoreViewIndex.setGridView(R.layout.viewpager_page).init(getConfigBean.getNav());


        List<GetConfigBean.EntranceListBean> entranceList = getConfigBean.getEntranceList();
        data.clear();
        data.addAll(entranceList);
        indexTabAdapter.notifyDataSetChanged();


        if (isRefresh) {
            loadDate(false);
        }
    }


    @OnClick({R.id.tv_recommed, R.id.tv_hy, R.id.tv_new, R.id.xtvOrder_2, R.id.tv_recommed_01, R.id.tv_hy_01, R.id.tv_new_01, R.id.xtvOrder})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.xtvOrder_2:
                moveToTopx();
                if (ResouceHelper.getSourceScreenBean() != null) {
                    showScreenPop(ResouceHelper.getSourceScreenBean());
                } else {
                    buildScreen();
                }
                break;
            case R.id.xtvOrder:

                if (ResouceHelper.getSourceScreenBean() != null) {
                    showScreenPop(ResouceHelper.getSourceScreenBean());
                } else {
                    buildScreen();
                }
                break;
            case R.id.tv_recommed:
                clearAllStyle();
                TextPaint tp01 = tvRecommed.getPaint();
                tp01.setFakeBoldText(true);
                tvRecommed.setTextColor(getResources().getColor(R.color._333));

                TextPaint tp02 = tvRecommed01.getPaint();
                tp02.setFakeBoldText(true);
                tvRecommed01.setTextColor(getResources().getColor(R.color._333));

                mSearchSortOrder = 34;
                mSearchPage = 1;
                showBookingToast(3);
                loadDate(true);
                break;
            case R.id.tv_hy:
                clearAllStyle();
                TextPaint tp03 = tvHy.getPaint();
                tp03.setFakeBoldText(true);
                tvHy.setTextColor(getResources().getColor(R.color._333));
                TextPaint tp04 = tvHy01.getPaint();
                tp04.setFakeBoldText(true);
                tvHy01.setTextColor(getResources().getColor(R.color._333));
                mSearchSortOrder = 36;
                mSearchPage = 1;
                showBookingToast(3);
                loadDate(true);
                break;
            case R.id.tv_new:
                clearAllStyle();
                TextPaint tp05 = tvNew.getPaint();
                tp05.setFakeBoldText(true);
                tvNew.setTextColor(getResources().getColor(R.color._333));

                TextPaint tp06 = tvNew01.getPaint();
                tp06.setFakeBoldText(true);
                tvNew01.setTextColor(getResources().getColor(R.color._333));
                mSearchSortOrder = 37;
                mSearchPage = 1;
                showBookingToast(3);
                loadDate(true);
                break;
            case R.id.tv_recommed_01:
                clearAllStyle();
                TextPaint tp011 = tvRecommed.getPaint();
                tp011.setFakeBoldText(true);
                tvRecommed.setTextColor(getResources().getColor(R.color._333));

                TextPaint tp022 = tvRecommed01.getPaint();
                tp022.setFakeBoldText(true);
                tvRecommed01.setTextColor(getResources().getColor(R.color._333));
                mSearchSortOrder = 34;
                mSearchPage = 1;
                showBookingToast(3);
                loadDate(true);
                moveToTopx();
                break;
            case R.id.tv_hy_01:
                clearAllStyle();
                TextPaint tp033 = tvHy.getPaint();
                tp033.setFakeBoldText(true);
                tvHy.setTextColor(getResources().getColor(R.color._333));
                TextPaint tp044 = tvHy01.getPaint();
                tp044.setFakeBoldText(true);
                tvHy01.setTextColor(getResources().getColor(R.color._333));
                mSearchSortOrder = 36;
                mSearchPage = 1;
                showBookingToast(3);
                loadDate(true);
                moveToTopx();
                break;
            case R.id.tv_new_01:
                clearAllStyle();
                TextPaint tp055 = tvNew.getPaint();
                tp055.setFakeBoldText(true);
                tvNew.setTextColor(getResources().getColor(R.color._333));
                TextPaint tp066 = tvNew01.getPaint();
                tp066.setFakeBoldText(true);
                tvNew01.setTextColor(getResources().getColor(R.color._333));
                mSearchSortOrder = 37;
                mSearchPage = 1;
                showBookingToast(3);
                loadDate(true);
                moveToTopx();
                break;

            default:
                break;
        }
    }

    /**
     * 清除选中样式
     */
    private void clearAllStyle() {
        TextPaint tp01 = tvRecommed.getPaint();
        tp01.setFakeBoldText(false);
        tvRecommed.setTextColor(getResources().getColor(R.color._999999));


        TextPaint tp02 = tvRecommed01.getPaint();
        tp02.setFakeBoldText(false);
        tvRecommed01.setTextColor(getResources().getColor(R.color._999999));


        TextPaint tp03 = tvHy.getPaint();
        tp03.setFakeBoldText(false);
        tvHy.setTextColor(getResources().getColor(R.color._999999));


        TextPaint tp04 = tvHy01.getPaint();
        tp04.setFakeBoldText(false);
        tvHy01.setTextColor(getResources().getColor(R.color._999999));


        TextPaint tp05 = tvNew.getPaint();
        tp05.setFakeBoldText(false);
        tvNew.setTextColor(getResources().getColor(R.color._999999));

        TextPaint tp06 = tvNew01.getPaint();
        tp06.setFakeBoldText(false);
        tvNew01.setTextColor(getResources().getColor(R.color._999999));

    }


    private void initData(IndexNewBean list) {
        if (mSearchPage == 1) {
            this.resourceItems.clear();

            if (list.getList().size() == 0) {
                ShowUtils.showViewVisible(rlIndexblank, View.VISIBLE);
                adapter.removeAllFooterView();
                if (refreshLayout != null) {
                    refreshLayout.setEnableLoadMore(false);
                }

            } else {
                ShowUtils.showViewVisible(rlIndexblank, View.GONE);
                if (list.getHasMore() == 0) {
                    adapter.setFooterView(footView);
                    if (refreshLayout != null) {
                        refreshLayout.setEnableLoadMore(false);
                    }

                } else {
                    adapter.removeAllFooterView();
                    if (refreshLayout != null) {
                        refreshLayout.setEnableLoadMore(true);
                    }

                }
            }
        } else {
            if (list.getHasMore() == 0) {
                adapter.setFooterView(footView);
                if (refreshLayout != null) {
                    refreshLayout.setEnableLoadMore(false);
                }
            } else {
                adapter.removeAllFooterView();
                if (refreshLayout != null) {
                    refreshLayout.setEnableLoadMore(true);
                }
            }
        }
        if (Constants.userIdList.length() > 0) {
//            Log.i("time01====", System.currentTimeMillis() + "");
            String[] all = Constants.userIdList.split(",");
            int size = list.getList().size();
            int size1 = all.length;
            int i, j;
            for (i = 0; i < size; i++) {
                for (j = 0; j < size1; j++) {
                    if (list.getList().get(i).getUser_id() == Integer.parseInt(all[j])) {
                        //  Log.i("数据===", "user_id==" + list.getList().get(i).getUser_id() + ",i==" + i + ",j===" + j + "  " + Integer.parseInt(all[j]));
                        list.getList().get(i).setU(true);
                    }
                }
            }
            Log.i("time02====", System.currentTimeMillis() + "");
        }

        this.resourceItems.addAll(list.getList());
        if (resourceItems.size() > 15 && !resourceItems.get(15).isSearchPerch()) {
            resourceItems.add(15, new IndexNewBean.ListBean(0, "", 0, "", "", "", "", "", "", 0, 0, 0, 0, 0, "", "", true));
        }
        adapter.notifyDataSetChanged();
        dismissBookingToast();
    }


    public void moveToTop() {
        if (indexScroll != null) {
            indexScroll.smoothScrollTo(0, 0);
        }
    }


    //获取合作信息数据
    public void loadDate(boolean isShow) {
        if (isShow) {
            refreshLayout.setEnableRefresh(true);
        }
        int time;
        if (mSearchPage == 1 || resourceItems.size() == 0) {
            time = 0;
        } else {
            time = (int) resourceItems.get(resourceItems.size() - 1).getCreate_time();

        }
        RequestManager.getInstance().getResourceItem(mSearchPage, mSearchCityId, mSearchKeyWord, "", mSearchSortOrder, mSearchQueryId, mSearchIndustry, 0, 1, time, 0, new GetResourceListCallback() {
            @Override
            public void onSuccess(IndexNewBean resultDO) {
                if (refreshLayout != null) {
                    if (refreshLayout.isEnableRefresh()) {
                        refreshLayout.finishRefresh();
                    }
                    if (refreshLayout.isEnableLoadMore()) {
                        refreshLayout.finishLoadMore();
                    }
                }
                //存入数据并显示
                if (mSearchPage == 1) {
                    Gson gson = new Gson();
                    String json = gson.toJson(resultDO);
                    UserInfoHelper.getIntance().setIndexList(json);
                }
                initData(resultDO);

            }

            @Override
            public void onFailed(int code, String msg) {
                if (refreshLayout != null) {
                    if (refreshLayout.isEnableRefresh()) {
                        refreshLayout.finishRefresh();
                    }
                    if (refreshLayout.isEnableLoadMore()) {
                        refreshLayout.finishLoadMore();
                    }
                }
                dismissBookingToast();
                ToastUtils.showCentetImgToast(getActivity(), msg);
            }
        });


    }


    private void buildScreen() {
        showBookingToast(2);
        RequestManager.getInstance().getSourceScreen(new SourceScreenCallback() {
            @Override
            public void onSuccess(SourceScreenBean bean) {
                dismissBookingToast();
                ResouceHelper.setSourceScreenBean(bean);
                showScreenPop(bean);
            }

            @Override
            public void onFailue(int code, String msg) {

                screenWindow.onDismiss();

            }
        });

    }

    private void showScreenPop(SourceScreenBean screenBean) {
        if (mSearchIndustry != null && !mSearchIndustry.equals("")) {
            for (int i = 0; i < screenBean.getCompany_list().size(); i++) {
                if (mSearchIndustry.equals(screenBean.getCompany_list().get(i).getId() + "")) {
                    screenBean.getCompany_list().get(i).setCheck(true);
                }
            }

        } else {
            for (int i = 0; i < screenBean.getCompany_list().size(); i++) {
                screenBean.getCompany_list().get(i).setCheck(false);
            }
        }
        screenWindow = new CellScreenWindow(getActivity(), screenBean);
        screenWindow.showAsDropDown(xtopTabLinear);

        screenWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                screenWindow.onDismiss();
                if (TextUtils.isEmpty(mSearchIndustry)) {
                    // xtvOrder.setSelected(false);

                    //   xtvOrder2.setSelected(false);
                    xtvOrder.setTextColor(getResources().getColor(R.color._999));
                    xtvOrder2.setTextColor(getResources().getColor(R.color._999));

                } else {
//                    xtvOrder.setSelected(true);
//                    xtvOrder2.setSelected(true);
                    xtvOrder.setTextColor(getResources().getColor(R.color._333));
                    xtvOrder2.setTextColor(getResources().getColor(R.color._333));
                }
            }
        });
        screenWindow.setFinish(new CellScreenWindow.finish() {
            @Override
            public void setFinish(String SearchIndustry, String name) {
                MobclickAgent.onEvent(mContext, "home_sourceIndustry", name);
                mSearchIndustry = SearchIndustry;
                mSearchPage = 1;
                refreshResouceListByHy(name);
                moveToTopx();
                loadDate(true);
                screenWindow.dismissPopup();

            }
        });

    }

    private void refreshResouceListByHy(String name) {
        if (name.length() == 0) {
            name = "行业";
            ShowUtils.showTextPerfect(xtvOrder, name);
            ShowUtils.showTextPerfect(xtvOrder2, name);
//            xtvOrder.setSelected(false);
//            xtvOrder2.setSelected(false);
            xtvOrder.setTextColor(getResources().getColor(R.color._999999));
            xtvOrder2.setTextColor(getResources().getColor(R.color._999999));
        } else {
            ShowUtils.showTextPerfect(xtvOrder, name);
            // xtvOrder.setSelected(true);
            ShowUtils.showTextPerfect(xtvOrder2, name);
            //  xtvOrder2.setSelected(true);
            xtvOrder.setTextColor(getResources().getColor(R.color._333));
            xtvOrder2.setTextColor(getResources().getColor(R.color._333));


        }
    }

    public void moveToTopx() {
        if (indexScroll != null) {

            int ay = DensityUtil.dp2px(375f);
            indexScroll.smoothScrollTo(0, ay);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        mCompanyDySoreViewIndex = rootView.findViewById(R.id.mCompanyDySoreView_index);
        return rootView;
    }
}
