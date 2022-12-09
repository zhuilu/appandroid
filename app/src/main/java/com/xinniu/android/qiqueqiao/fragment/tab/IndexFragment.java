package com.xinniu.android.qiqueqiao.fragment.tab;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
//import android.support.annotation.NonNull;
//import android.support.v4.content.ContextCompat;
//import android.support.v7.widget.LinearLayoutManager;
//import androidx.recyclerview.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshFooter;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.RefreshState;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnMultiPurposeListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.scwang.smartrefresh.layout.util.DensityUtil;
import com.stx.xmarqueeview.XMarqueeView;
import com.umeng.analytics.MobclickAgent;
import com.xinniu.android.qiqueqiao.R;
import com.xinniu.android.qiqueqiao.activity.ApproveCardActivity;
import com.xinniu.android.qiqueqiao.activity.CompanyIndexActivity;
import com.xinniu.android.qiqueqiao.activity.CoopDetailActivity;
import com.xinniu.android.qiqueqiao.activity.HotResourceActivity;
import com.xinniu.android.qiqueqiao.activity.IndexCellActivity;
import com.xinniu.android.qiqueqiao.activity.IndexClassifyActivity;
import com.xinniu.android.qiqueqiao.activity.IndexServiceActivity;
import com.xinniu.android.qiqueqiao.activity.LoginNewActivity;
import com.xinniu.android.qiqueqiao.activity.SelectionResourceActivity;
import com.xinniu.android.qiqueqiao.activity.SreachActivity;
import com.xinniu.android.qiqueqiao.adapter.IndexActivityCellAdapter;
import com.xinniu.android.qiqueqiao.adapter.IndexCellTwoAdapter;
import com.xinniu.android.qiqueqiao.adapter.IndexMarqueeAdapter;
import com.xinniu.android.qiqueqiao.adapter.IndexNewAdapter;
import com.xinniu.android.qiqueqiao.adapter.IndexTabAdapter;
import com.xinniu.android.qiqueqiao.base.LazyBaseFragment;
import com.xinniu.android.qiqueqiao.bean.GetConfigBean;
import com.xinniu.android.qiqueqiao.bean.IndexNewBean;
import com.xinniu.android.qiqueqiao.bean.SourceScreenBean;
import com.xinniu.android.qiqueqiao.common.Constants;
import com.xinniu.android.qiqueqiao.customs.CellScreenWindow;
import com.xinniu.android.qiqueqiao.customs.ClearEditText;
import com.xinniu.android.qiqueqiao.customs.MyScrollView;
import com.xinniu.android.qiqueqiao.customs.ScrollLinearLayoutManager;
import com.xinniu.android.qiqueqiao.customs.SortWindow;
import com.xinniu.android.qiqueqiao.request.RequestManager;
import com.xinniu.android.qiqueqiao.request.RetrofitHelper;
import com.xinniu.android.qiqueqiao.request.callback.GetConfigCallback;
import com.xinniu.android.qiqueqiao.request.callback.GetResourceListCallback;
import com.xinniu.android.qiqueqiao.request.callback.SourceScreenCallback;
import com.xinniu.android.qiqueqiao.user.UserInfoHelper;
import com.xinniu.android.qiqueqiao.utils.ComUtils;
import com.xinniu.android.qiqueqiao.utils.FullyLinearLayoutManager;
import com.xinniu.android.qiqueqiao.utils.MyBannerImgLoader;
import com.xinniu.android.qiqueqiao.utils.ResouceHelper;
import com.xinniu.android.qiqueqiao.utils.ShowUtils;
import com.xinniu.android.qiqueqiao.utils.StatusBarUtil;
import com.xinniu.android.qiqueqiao.utils.ToastUtils;
import com.xinniu.android.qiqueqiao.utils.TokePhotoUtils;
import com.xinniu.android.qiqueqiao.zxing.activity.CaptureActivity;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.listener.OnBannerListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import fj.mtsortbutton.lib.DynamicSoreView;
import fj.mtsortbutton.lib.Interface.IDynamicSore;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

/**
 * 首页-谈合作
 * Created by BDXK on 2017/11/7.
 */

public class IndexFragment extends LazyBaseFragment {
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    @BindView(R.id.qrBt)
    ImageView qrBt;
    @BindView(R.id.r_search)
    RelativeLayout rSearch;
    @BindView(R.id.sreach_content_et)
    ClearEditText mClearEditText;
    @BindView(R.id.sreach_content_ll)
    RelativeLayout linearLayout;
    @BindView(R.id.mindex_ban)
    Banner mindexBan;
    @BindView(R.id.mrecyclerWindow)
    RecyclerView mrecyclerWindow;
    @BindView(R.id.indexScroll)
    MyScrollView indexScroll;
    @BindView(R.id.thecoopRl)
    LinearLayout thecoopRl;
    @BindView(R.id.rlIndexblank)
    RelativeLayout rlIndexblank;
    @BindView(R.id.rl_search)
    RelativeLayout rlSearch;
    @BindView(R.id.yindex_search)
    RelativeLayout yindexSearch;
    @BindView(R.id.mMarqueeView)
    XMarqueeView mMarqueeView;
    @BindView(R.id.mleft_titletv)
    TextView mleftTitletv;
    @BindView(R.id.mleft_contenttv)
    TextView mleftContenttv;
    @BindView(R.id.mright_titletv)
    TextView mrightTitletv;
    @BindView(R.id.mright_contenttv)
    TextView mrightContenttv;
    @BindView(R.id.yindex_actLl)
    LinearLayout yindexActLl;
    @BindView(R.id.bleft_rl)
    RelativeLayout bleftRl;
    @BindView(R.id.bright_rl)
    RelativeLayout brightRl;
    @BindView(R.id.bleft_img)
    ImageView bleftImg;
    @BindView(R.id.bright_img)
    ImageView brightImg;
    @BindView(R.id.mCompanyDySoreView_index)
    DynamicSoreView mCompanyDySoreView;
    @BindView(R.id.mActivityDySoreView)
    DynamicSoreView mActivityDySoreView;
    @BindView(R.id.xtvOrder_2)
    TextView xtvOrder2;
    @BindView(R.id.xtvOrder)
    TextView xtvOrder;
    @BindView(R.id.xtopTabLinear)
    RelativeLayout xtopTabLinear;
    @BindView(R.id.rlayout_1)
    RelativeLayout rlayout1;
    @BindView(R.id.bright_company_img)
    ImageView brightCompanyImg;
    @BindView(R.id.mright_contenttv_company)
    TextView mrightContenttvCompany;
    @BindView(R.id.mright_titletv_company)
    TextView mrightTitletvCompany;
    @BindView(R.id.bright_company)
    RelativeLayout brightCompany;
    @BindView(R.id.tab_recycler)
    RecyclerView tabRecycler;
    @BindView(R.id.btvSort_2)
    TextView btvSort2;
    @BindView(R.id.btvSort)
    TextView btvSort;

    private PopupWindow popupWindow;
    private CellScreenWindow screenWindow;
    //首页列表
    private List<IndexNewBean.ListBean> resourceItems = new ArrayList<>();
    //首页列表请求参数--start
    private int mSearchCityId = 0;
    private String mSearchKeyWord;
    public int mSearchSortOrder = 36;
    private int mSearchCategory;
    private String mSearchQueryId;
    private String mSearchIndustry;

    private int mSearchPage = 1;
    private int mQueryType = 3;
    private IndexTabAdapter indexTabAdapter;
    List<GetConfigBean.EntranceListBean> data = new ArrayList<>();
    //首页列表请求参数--end

    //    public final static int SCANNING_REQUEST_CODE = -1;
    //打开扫描界面请求码
    public final static int REQUEST_CODE = 0x01;
    //扫描成功返回码
    public final static int RESULT_OK = 0xA1;
    private IndexNewAdapter adapter;
    private List<String> bannerImgs = new ArrayList<>();
    private List<GetConfigBean.BannerBean> configList = new ArrayList<>();
    //    public static String mSearchType;
//    public static int mSearchId;
    private List<GetConfigBean.EventBean> event;
    private IndexMarqueeAdapter marqueeAdapter;
    private List<GetConfigBean.ScrollingInfoBean> scrollList = new ArrayList<>();

    public static IndexFragment newInstance() {
        Bundle args = new Bundle();
        IndexFragment fragment = new IndexFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public int getLayoutId() {
        return R.layout.fragment_index;
    }

    @Override
    public void onStart() {
        super.onStart();
        mindexBan.startAutoPlay();
        mMarqueeView.startFlipping();
    }

    @Override
    public void onStop() {
        super.onStop();
        mindexBan.stopAutoPlay();
        mMarqueeView.stopFlipping();
    }


    @Override
    public void initViews(Bundle savedInstanceState) {
        adapter = new IndexNewAdapter(getActivity(), R.layout.item_index_new, resourceItems, 2,1);
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
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) mindexBan.getLayoutParams();
        int width = ComUtils.getScreenWidth(mContext);
        params.height = 180 * width / 375;
        mindexBan.setLayoutParams(params);
        mindexBan.setImageLoader(new MyBannerImgLoader());
        mindexBan.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {
                if (!TextUtils.isEmpty(configList.get(position).getUrl())) {
                    MobclickAgent.onEvent(mContext, "home_banner", configList.get(position).getUrl());//统计banner点击次数
                    ComUtils.goToBannerNext(mContext, configList.get(position).getUrl(), true);
                }
            }
        });

        LinearLayoutManager manager = new ScrollLinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        tabRecycler.setLayoutManager(manager);
        indexTabAdapter = new IndexTabAdapter(R.layout.item_main_recycler, data, getActivity());
        tabRecycler.setAdapter(indexTabAdapter);
//        GridLayoutManager manager = new GridLayoutManager(mContext, 5);
//        mrecyclerWindow.setLayoutManager(manager);
        mCompanyDySoreView.setiDynamicSore(new IDynamicSore() {
            @Override
            public void setGridView(View view, int i, List list) {
                final List<GetConfigBean.NavBean> datas = list;
                GridView gridView = (GridView) view.findViewById(R.id.gridView);
                gridView.setSelector(new ColorDrawable(Color.TRANSPARENT));
                mCompanyDySoreView.setNumColumns(gridView);
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


        mActivityDySoreView.setiDynamicSore(new IDynamicSore() {
            @Override
            public void setGridView(View view, int i, List list) {
                final List<GetConfigBean.EventBean> mActivityDatas = list;
                GridView mGridView = (GridView) view.findViewById(R.id.gridView_two);
                mGridView.setSelector(new ColorDrawable(Color.TRANSPARENT));
                IndexActivityCellAdapter adapterTwo = new IndexActivityCellAdapter(mContext, mActivityDatas);
                mGridView.setAdapter(adapterTwo);
                mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        if (mActivityDatas.get(position).getId() == -1) {
                            //更多
                            if (!isLogin()) {
                                return;
                            }
                            MobclickAgent.onEvent(mContext, "home_eventMore");//统计点击次数
                            String actListUrl = RetrofitHelper.API_URL + "resource/pages/qqqAct/home.html";
                            ApproveCardActivity.start(getActivity(), "url", actListUrl, "");
                        } else {
                            if (!isLogin()) {
                                return;
                            }
                            ApproveCardActivity.start(getActivity(), "url", mActivityDatas.get(position).getUrl(), "");
                        }
                    }
                });
            }
        });
        handlerRecyclerView();
        int w = View.MeasureSpec.makeMeasureSpec(0,
                View.MeasureSpec.UNSPECIFIED);
        int h = View.MeasureSpec.makeMeasureSpec(0,
                View.MeasureSpec.UNSPECIFIED);
        rSearch.measure(w, h);
        final int height = rSearch.getMeasuredHeight();
        indexScroll.setListener(new MyScrollView.OnScrollListener() {
            @Override
            public void onScroll(int scrollY) {
                int ps = ComUtils.countWindowTop(recyclerView);
                int bs = ComUtils.countWindowTop(mCompanyDySoreView);
                ps = DensityUtil.dp2px(383.25f);
                if (scrollY >= ps) {
                    xtopTabLinear.setVisibility(View.VISIBLE);
                } else {
                    xtopTabLinear.setVisibility(View.GONE);
                }

                if (scrollY > bs) {
                    qrBt.setImageResource(R.mipmap.qrcode_gray);
                    linearLayout.setBackground(ContextCompat.getDrawable(mContext, R.drawable.shape_circle_search));
                    rSearch.setBackground(ContextCompat.getDrawable(mContext, R.drawable.shape_white_bg));
                    StatusBarUtil.StatusBarLightMode(getActivity(), true);
                } else {
                    rSearch.setBackground(null);
                    qrBt.setImageResource(R.mipmap.qrcode_white);
                    linearLayout.setBackground(ContextCompat.getDrawable(mContext, R.mipmap.search_bg));
                    StatusBarUtil.StatusBarLightMode(getActivity(), false);

                }
            }
        });

        refreshLayout.setEnableFooterTranslationContent(true);
        refreshLayout.setEnableRefresh(true);
        refreshLayout.setEnableLoadMore(true);
        refreshLayout.setEnableNestedScroll(true);
        refreshLayout.setEnableOverScrollDrag(true);
//        refreshLayout.setDragRate(0.5f);
        refreshLayout.setOnMultiPurposeListener(new OnMultiPurposeListener() {
            @Override
            public void onHeaderMoving(RefreshHeader header, boolean isDragging, float percent, int offset, int headerHeight, int maxDragHeight) {
//                Log.d("==IndexFragment", "percent:" + percent);
                if (offset > 0) {
                    if (percent < 1) {
                        yindexSearch.setAlpha(1 - percent);

                    }
                } else {
                    try {
                        yindexSearch.setAlpha(1);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onHeaderReleased(RefreshHeader header, int headerHeight, int maxDragHeight) {

            }

            @Override
            public void onHeaderStartAnimator(RefreshHeader header, int headerHeight, int maxDragHeight) {

            }

            @Override
            public void onHeaderFinish(RefreshHeader header, boolean success) {

            }

            @Override
            public void onFooterMoving(RefreshFooter footer, boolean isDragging, float percent, int offset, int footerHeight, int maxDragHeight) {

            }

            @Override
            public void onFooterReleased(RefreshFooter footer, int footerHeight, int maxDragHeight) {

            }

            @Override
            public void onFooterStartAnimator(RefreshFooter footer, int footerHeight, int maxDragHeight) {

            }

            @Override
            public void onFooterFinish(RefreshFooter footer, boolean success) {

            }

            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {

            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {

            }

            @Override
            public void onStateChanged(@NonNull RefreshLayout refreshLayout, @NonNull RefreshState oldState, @NonNull RefreshState newState) {

            }
        });
        popupWindow = new PopupWindow();
        popupWindow.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        popupWindow.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
        popupWindow.setAnimationStyle(R.style.anims);
        View view = LayoutInflater.from(mContext).inflate(R.layout.dialog_sign_success, null);
        TextView sureTv = (TextView) view.findViewById(R.id.bsureTv);
        sureTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                popupWindow.dismiss();
            }
        });
        popupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        popupWindow.setContentView(view);
        popupWindow.setOutsideTouchable(false);
        popupWindow.setFocusable(true);


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
        // if (isRefresh) {
        mCompanyDySoreView.setGridView(R.layout.viewpager_page).init(getConfigBean.getNav());
        //   IndexCellAdapter adapter = new IndexCellAdapter(mContext, R.layout.item_index_cell, getConfigBean.getNav());
//                    mrecyclerWindow.setAdapter(adapter);
        //   }
//        mSearchSortOrder = Integer.parseInt(getConfigBean.getDefault_config().getSort_order_default());
//        mSearchType = getConfigBean.getDefault_config().getSort_order_name();
//        mSearchId = Integer.parseInt(getConfigBean.getDefault_config().getSort_order_default());
        scrollList.clear();
        scrollList.addAll(getConfigBean.getScrollingInfo());
        if (isRefresh) {
            if (marqueeAdapter != null) {
                marqueeAdapter.setDatas(scrollList);
            } else {
                marqueeAdapter = new IndexMarqueeAdapter(mContext, scrollList);
                mMarqueeView.setAdapter(marqueeAdapter);
            }
        }
        marqueeAdapter.notifyDataChanged();
        marqueeAdapter.setSetOnClick(new IndexMarqueeAdapter.setOnClick() {
            @Override
            public void setOnClick(int resourceId) {
                if (!isLogin()) {
                    return;
                }
                MobclickAgent.onEvent(mContext, "home_scrollinfo");//统计点击次数
                CoopDetailActivity.start(mContext, resourceId);
            }
        });

        List<GetConfigBean.EntranceListBean> entranceList = getConfigBean.getEntranceList();
        data.clear();
        data.addAll(entranceList);
        indexTabAdapter.notifyDataSetChanged();



        // 优质活动判断
//        event = getConfigBean.getEvent();
//        if (event != null && event.size() > 0) {
//            final List<GetConfigBean.EventBean> mActivityDatas = getConfigBean.getEvent();
//            GetConfigBean.EventBean eventBean = new GetConfigBean.EventBean();
//            eventBean.setId(-1);//更多
//            mActivityDatas.add(eventBean);
//            mActivityDySoreView.setGridView(R.layout.viewpager_page_two).init(mActivityDatas);
//
//        } else {
//            ShowUtils.showViewVisible(yindexActLl, View.GONE);
//        }


        if (isRefresh) {
            loadDate(false);
        }
    }


    @OnClick({R.id.qrBt, R.id.r_search, R.id.sreach_content_et, R.id.sreach_content_ll, R.id.bgoto_moreRl, R.id.xtvOrder_2, R.id.xtvOrder, R.id.btvSort_2, R.id.btvSort})
    public void onViewClicked(View view) {
        switch (view.getId()) {

            case R.id.sreach_content_et:
                MobclickAgent.onEvent(mContext, "home_searchbar");//统计搜索点击次数
                Intent intent1 = new Intent(getActivity(), SreachActivity.class);
                startActivity(intent1);
                break;
            case R.id.sreach_content_ll:
                Intent intent3 = new Intent(getActivity(), SreachActivity.class);
                startActivity(intent3);
                break;

            case R.id.qrBt:
                if (!UserInfoHelper.getIntance().isLogin()) {
                    LoginNewActivity.start(mContext);
                } else {
                    //二维码
                    requestPermission();
                }
                break;
            case R.id.bgoto_moreRl:
                if (!isLogin()) {
                    return;
                }
                MobclickAgent.onEvent(mContext, "home_eventMore");//统计点击次数
                String actListUrl = RetrofitHelper.API_URL + "resource/pages/qqqAct/home.html";
                ApproveCardActivity.start(getActivity(), "url", actListUrl, "");
                break;
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
            case R.id.btvSort_2:
                moveToTopx();
                //排序
                showSort();
                break;
            case R.id.btvSort:
                //排序
                showSort();
                break;
            default:
                break;
        }
    }

    private void showSort() {
        final SortWindow sortWindow = new SortWindow(getActivity(), mSearchSortOrder);
        sortWindow.showAsDropDown(xtopTabLinear);
        sortWindow.setFinish(new SortWindow.finish() {
            @Override
            public void setFinish(int mid, String name) {
                mSearchSortOrder = mid;
                if (name.length() == 0) {
                    name = "合作信息";
                    ShowUtils.showTextPerfect(btvSort, name);
                    ShowUtils.showTextPerfect(btvSort2, name);
                    btvSort.setSelected(false);
                    btvSort2.setSelected(false);
                } else {
                    ShowUtils.showTextPerfect(btvSort, name);
                    btvSort.setSelected(true);
                    ShowUtils.showTextPerfect(btvSort2, name);
                    btvSort2.setSelected(true);

                }
                mSearchPage = 1;
                moveToTopx();
                loadDate(true);
                sortWindow.dismiss();
            }
        });
    }

    private boolean isLogin() {
        if (!UserInfoHelper.getIntance().isLogin()) {
            // TODO: 2017/12/20
//            ToastUtils.showCentetImgToast(getContext(), "还未登录");
            LoginNewActivity.start(mContext);
        }
        return UserInfoHelper.getIntance().isLogin();
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
            Log.i("time01====", System.currentTimeMillis() + "");
            String[] all = Constants.userIdList.split(",");
            int size = list.getList().size();
            int size1 = all.length;
            int i, j;
            for (i = 0; i < size; i++) {
                for (j = 0; j < size1; j++) {
                    if (list.getList().get(i).getUser_id() == Integer.parseInt(all[j])) {
                        Log.i("数据===","user_id=="+list.getList().get(i).getUser_id()+",i=="+i+",j==="+j+"  "+Integer.parseInt(all[j]));
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

    /**
     * 下拉刷新与上拉加载更多
     */
    private void handlerRecyclerView() {
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
    }


    public void moveToTop() {
        if (indexScroll != null) {
            indexScroll.smoothScrollTo(0, 0);
        }
    }

    public void reResouceList() {
        if (recyclerView == null) {
            return;
        }
        recyclerView.scrollToPosition(0);
        reSettingRequest();

        loadDate(true);
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
        RequestManager.getInstance().getResourceItem(mSearchPage, mSearchCityId, mSearchKeyWord, "", mSearchSortOrder, mSearchQueryId, mSearchIndustry, 0, 1, time, 0,new GetResourceListCallback() {
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

    private void reSettingRequest() {
        mQueryType = 3;
        ResouceHelper.seletCityId = 0;
        mSearchPage = 1;
        mSearchKeyWord = "";
        mSearchCategory = 0;
        mSearchQueryId = "";
        mSearchIndustry = "";
        mSearchSortOrder = 36;
        xtvOrder2.setText("行业");
        xtvOrder.setText("行业");
        btvSort.setText("排序");
        btvSort2.setText("排序");

        xtvOrder2.setSelected(false);
        xtvOrder.setSelected(false);
        btvSort.setSelected(false);
        btvSort2.setSelected(false);
    }

    @AfterPermissionGranted(TokePhotoUtils.PERMISSION_TOKE_PHOTO)
    public void requestPermission() {
        if (EasyPermissions.hasPermissions(getContext(), TokePhotoUtils.TOKE_PHOTO)) {
            Intent intent2 = new Intent(getActivity(), CaptureActivity.class);
            getActivity().startActivityForResult(intent2, REQUEST_CODE);
        } else {
            EasyPermissions.requestPermissions(
                    this,
                    getString(R.string.permission_need_toke_photo),
                    TokePhotoUtils.PERMISSION_TOKE_PHOTO, TokePhotoUtils.TOKE_PHOTO);
        }
    }

    public void refreshPage() {
        mSearchPage = 1;
        loadDate(true);
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
                    xtvOrder2.setSelected(false);
                    xtvOrder2.setSelected(false);
                } else {
                    xtvOrder2.setSelected(true);
                    xtvOrder2.setSelected(true);
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
            xtvOrder.setSelected(false);
            xtvOrder2.setSelected(false);
        } else {
            ShowUtils.showTextPerfect(xtvOrder, name);
            xtvOrder.setSelected(true);
            ShowUtils.showTextPerfect(xtvOrder2, name);
            xtvOrder2.setSelected(true);


        }
    }

    public void moveToTopx() {
        if (indexScroll != null) {
            int ay = DensityUtil.dp2px(383.25f);
            indexScroll.smoothScrollTo(0, ay);
        }
    }

}
