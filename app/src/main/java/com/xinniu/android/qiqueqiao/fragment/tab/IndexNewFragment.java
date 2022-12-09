package com.xinniu.android.qiqueqiao.fragment.tab;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
//import android.support.annotation.NonNull;
//import android.support.v4.content.ContextCompat;
//import android.support.v4.widget.NestedScrollView;
//import android.support.v7.widget.LinearLayoutManager;
//import androidx.recyclerview.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshFooter;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.RefreshState;
import com.scwang.smartrefresh.layout.listener.OnMultiPurposeListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.stx.xmarqueeview.XMarqueeView;
import com.umeng.analytics.MobclickAgent;
import com.xinniu.android.qiqueqiao.R;
import com.xinniu.android.qiqueqiao.activity.ApproveCardActivity;
import com.xinniu.android.qiqueqiao.activity.ClassRoomActivity;
import com.xinniu.android.qiqueqiao.activity.CoopDetailActivity;
import com.xinniu.android.qiqueqiao.activity.IndexCellActivity;
import com.xinniu.android.qiqueqiao.activity.IndexClassifyActivity;
import com.xinniu.android.qiqueqiao.activity.IndexServiceActivity;
import com.xinniu.android.qiqueqiao.activity.LaunchTransactionAvtivity;
import com.xinniu.android.qiqueqiao.activity.LoginNewActivity;
import com.xinniu.android.qiqueqiao.activity.RewardListActivity;
import com.xinniu.android.qiqueqiao.activity.SreachActivity;
import com.xinniu.android.qiqueqiao.adapter.IndexCellNewAdapter;
import com.xinniu.android.qiqueqiao.adapter.IndexMarqueeNewAdapter;
import com.xinniu.android.qiqueqiao.adapter.MainClassRoomAdapter;
import com.xinniu.android.qiqueqiao.adapter.MainEnentsAdapter;
import com.xinniu.android.qiqueqiao.adapter.MainRewardAdapter;
import com.xinniu.android.qiqueqiao.adapter.MainServiceAdapter;
import com.xinniu.android.qiqueqiao.adapter.MainTypeAdapter;
import com.xinniu.android.qiqueqiao.base.LazyBaseFragment;
import com.xinniu.android.qiqueqiao.bean.MainBean;
import com.xinniu.android.qiqueqiao.common.Constants;
import com.xinniu.android.qiqueqiao.customs.ClearEditText;
import com.xinniu.android.qiqueqiao.customs.MyNestedScrollView;
import com.xinniu.android.qiqueqiao.request.RequestManager;
import com.xinniu.android.qiqueqiao.request.RetrofitHelper;
import com.xinniu.android.qiqueqiao.request.callback.GetMainCallback;
import com.xinniu.android.qiqueqiao.user.UserInfoHelper;
import com.xinniu.android.qiqueqiao.utils.ComUtils;
import com.xinniu.android.qiqueqiao.utils.MyBannerImgLoader;
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

public class IndexNewFragment extends LazyBaseFragment {
    @BindView(R.id.mindex_ban)
    Banner mindexBan;
    @BindView(R.id.mCompanyDySoreView_index)
    DynamicSoreView mCompanyDySoreViewIndex;
    @BindView(R.id.coop_dtimg)
    ImageView coopDtimg;
    @BindView(R.id.view)
    View view;
    @BindView(R.id.goto_imgx)
    ImageView gotoImgx;
    @BindView(R.id.mMarqueeView)
    XMarqueeView mMarqueeView;
    @BindView(R.id.mrecyclerType)
    RecyclerView mrecyclerType;
    @BindView(R.id.tv)
    TextView tv;
    @BindView(R.id.img_01)
    ImageView img01;
    @BindView(R.id.img_011)
    ImageView img011;
    @BindView(R.id.img_02)
    ImageView img02;
    @BindView(R.id.img_022)
    ImageView img022;
    @BindView(R.id.img_03)
    ImageView img03;
    @BindView(R.id.img_033)
    ImageView img033;
    @BindView(R.id.img_04)
    ImageView img04;
    @BindView(R.id.tv_01)
    TextView tv01;
    @BindView(R.id.tv_02)
    TextView tv02;
    @BindView(R.id.tv_03)
    TextView tv03;
    @BindView(R.id.tv_04)
    TextView tv04;
    @BindView(R.id.tv_launch_transation)
    TextView tvLaunchTransation;
    @BindView(R.id.rlayout_trans)
    RelativeLayout rlayoutTrans;
    @BindView(R.id.textView2)
    TextView textView2;
    @BindView(R.id.rlayout_company_service)
    RelativeLayout rlayoutCompanyService;
    @BindView(R.id.company_service_recycler)
    RecyclerView companyServiceRecycler;
    @BindView(R.id.textView3)
    TextView textView3;
    @BindView(R.id.rlayout_activity)
    RelativeLayout rlayoutActivity;
    @BindView(R.id.activity_recycler)
    RecyclerView activityRecycler;
    @BindView(R.id.textView4)
    TextView textView4;
    @BindView(R.id.rlayout_reward)
    RelativeLayout rlayoutReward;
    @BindView(R.id.reward_recycler)
    RecyclerView rewardRecycler;
    @BindView(R.id.textView5)
    TextView textView5;
    @BindView(R.id.rlayout_class)
    RelativeLayout rlayoutClass;
    @BindView(R.id.class_recycler)
    RecyclerView classRecycler;
    @BindView(R.id.thecoopRl)
    LinearLayout thecoopRl;
    @BindView(R.id.indexScroll)
    MyNestedScrollView indexScroll;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    @BindView(R.id.rl_search)
    RelativeLayout rlSearch;
    @BindView(R.id.qrBt)
    ImageView qrBt;
    @BindView(R.id.glassImg)
    ImageView glassImg;
    @BindView(R.id.sreach_content_et)
    ClearEditText sreachContentEt;
    @BindView(R.id.sreach_content_ll)
    RelativeLayout sreachContentLl;
    @BindView(R.id.yindex_search)
    RelativeLayout yindexSearch;
    @BindView(R.id.r_search)
    RelativeLayout rSearch;
    //打开扫描界面请求码
    public final static int REQUEST_CODE = 0x01;
    //扫描成功返回码
    public final static int RESULT_OK = 0xA1;
    private List<MainBean.BannerBean> mBannerList = new ArrayList<>();
    private List<String> bannerImgs = new ArrayList<>();
    private IndexMarqueeNewAdapter marqueeAdapter;
    private List<MainBean.ScrollingInfoBean> scrollList = new ArrayList<>();
    private MainTypeAdapter mainTypeAdapter;
    private List<MainBean.RecommendNavBean> mResourceList = new ArrayList<>();
    private MainServiceAdapter mainServiceAdapter;
    private List<MainBean.ServiceProviderBean> mServiceList = new ArrayList<>();
    private MainEnentsAdapter mainEnentsAdapter;
    private List<MainBean.EventBean> mEventList = new ArrayList<>();
    private MainRewardAdapter mainRewardAdapter;
    private List<MainBean.RewardBean> mRewardList = new ArrayList<>();
    private MainClassRoomAdapter mainClassRoomAdapter;
    private List<MainBean.VideoBean> mVideoList = new ArrayList<>();

    public static IndexNewFragment newInstance() {
        Bundle args = new Bundle();
        IndexNewFragment fragment = new IndexNewFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_index_new;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        //banner处理
        mindexBan.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) mindexBan.getLayoutParams();
        int width = ComUtils.getScreenWidth(mContext);
        params.height = 180 * width / 375;
        mindexBan.setLayoutParams(params);
        mindexBan.setImageLoader(new MyBannerImgLoader());
        mindexBan.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {
                if (!TextUtils.isEmpty(mBannerList.get(position).getUrl())) {
                    MobclickAgent.onEvent(mContext, "home_banner", mBannerList.get(position).getUrl());//统计banner点击次数
                    ComUtils.goToBannerNext(mContext, mBannerList.get(position).getUrl(), true);
                }
            }
        });
        //分类处理
        mCompanyDySoreViewIndex.setiDynamicSore(new IDynamicSore() {
            @Override
            public void setGridView(View view, int i, List list) {
                final List<MainBean.NavBean> datas = list;
                GridView gridView = (GridView) view.findViewById(R.id.gridView);
                gridView.setSelector(new ColorDrawable(Color.TRANSPARENT));
                mCompanyDySoreViewIndex.setNumColumns(gridView);
                IndexCellNewAdapter adapter = new IndexCellNewAdapter(mContext, datas);
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

        //推荐资源大类
        mainTypeAdapter = new MainTypeAdapter(getActivity(), R.layout.item_main_type, mResourceList);
        LinearLayoutManager manager = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
        mrecyclerType.setLayoutManager(manager);
        mrecyclerType.setAdapter(mainTypeAdapter);
        //公司服务
        mainServiceAdapter = new MainServiceAdapter(getActivity(), R.layout.item_main_service, mServiceList);
        final LinearLayoutManager manager01 = new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false);
        companyServiceRecycler.setLayoutManager(manager01);
        companyServiceRecycler.setAdapter(mainServiceAdapter);
        //商务活动
        mainEnentsAdapter = new MainEnentsAdapter(getActivity(), R.layout.item_main_activity, mEventList);
        final LinearLayoutManager manager02 = new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false);
        activityRecycler.setLayoutManager(manager02);
        activityRecycler.setAdapter(mainEnentsAdapter);
        //悬赏
        mainRewardAdapter = new MainRewardAdapter(getActivity(), R.layout.item_main_reward, mRewardList);
        LinearLayoutManager manager3 = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
        rewardRecycler.setLayoutManager(manager3);
        rewardRecycler.setAdapter(mainRewardAdapter);

        //异业课堂
        mainClassRoomAdapter = new MainClassRoomAdapter(getActivity(), R.layout.item_main_class, mVideoList);
        LinearLayoutManager manager4 = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
        classRecycler.setLayoutManager(manager4);
        classRecycler.setAdapter(mainClassRoomAdapter);

        handlerRecyclerView();
        int w = View.MeasureSpec.makeMeasureSpec(0,
                View.MeasureSpec.UNSPECIFIED);
        int h = View.MeasureSpec.makeMeasureSpec(0,
                View.MeasureSpec.UNSPECIFIED);
        rSearch.measure(w, h);
        final int height = rSearch.getMeasuredHeight();
        indexScroll.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                int bs = ComUtils.countWindowTop(mCompanyDySoreViewIndex);
                if (scrollY > bs) {
                    qrBt.setImageResource(R.mipmap.qrcode_gray);
                    sreachContentLl.setBackground(ContextCompat.getDrawable(mContext, R.drawable.shape_circle_search));
                    rSearch.setBackground(ContextCompat.getDrawable(mContext, R.drawable.shape_white_bg));
                    StatusBarUtil.StatusBarLightMode(getActivity(), true);
                } else {
                    rSearch.setBackground(null);
                    qrBt.setImageResource(R.mipmap.qrcode_white);
                    sreachContentLl.setBackground(ContextCompat.getDrawable(mContext, R.mipmap.search_bg));
                    StatusBarUtil.StatusBarLightMode(getActivity(), false);

                }
            }
        });


        refreshLayout.setEnableFooterTranslationContent(true);
        refreshLayout.setEnableRefresh(true);
        refreshLayout.setEnableLoadMore(false);
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

    }

    @Override
    protected void lazyLoad() {
        if (UserInfoHelper.getIntance().getIndexNewList().length() > 0) {
            //判断有没有缓存
            String json = UserInfoHelper.getIntance().getIndexNewList();
            Gson gson = new Gson();
            MainBean mainBean = gson.fromJson(json, MainBean.class);
            initData(mainBean);
            buildConfig(false);
        } else {
            showBookingToast(3);
            buildConfig(true);
        }
    }

    private void buildConfig(final boolean isShow) {
        RequestManager.getInstance().getNewIndexList(new GetMainCallback() {
            @Override
            public void onSuccess(final MainBean mainBean) {
                //存入数据并显示
                Gson gson = new Gson();
                String json = gson.toJson(mainBean);
                UserInfoHelper.getIntance().setIndexNewList(json);
                initData(mainBean);
                if (isShow) {
                    dismissBookingToast();
                }

                if (refreshLayout != null) {
                    if (refreshLayout.isEnableRefresh()) {
                        refreshLayout.finishRefresh();
                    }

                }
            }

            @Override
            public void onFailed(int code, String msg) {
                if (isShow) {
                    dismissBookingToast();
                }
                if (refreshLayout != null) {
                    if (refreshLayout.isEnableRefresh()) {
                        refreshLayout.finishRefresh();
                    }

                }
                ToastUtils.showCentetImgToast(getActivity(), msg);
            }
        });

    }

    private void initData(MainBean mainBean) {
        bannerImgs.clear();
        mBannerList.clear();
        for (int i = 0; i < mainBean.getBanner().size(); i++) {
            bannerImgs.add(mainBean.getBanner().get(i).getImg());

        }
        mBannerList.addAll(mainBean.getBanner());
        if (mindexBan != null) {
            mindexBan.setImages(bannerImgs);
        }
        if (mindexBan != null) {
            mindexBan.start();
        }

        mCompanyDySoreViewIndex.setGridView(R.layout.viewpager_page).init(mainBean.getNav());
        scrollList.clear();
        scrollList.addAll(mainBean.getScrollingInfo());

        if (marqueeAdapter != null) {
            marqueeAdapter.setDatas(scrollList);
        } else {
            marqueeAdapter = new IndexMarqueeNewAdapter(mContext, scrollList);
            mMarqueeView.setAdapter(marqueeAdapter);
        }

        marqueeAdapter.notifyDataChanged();
        marqueeAdapter.setSetOnClick(new IndexMarqueeNewAdapter.setOnClick() {
            @Override
            public void setOnClick(int resourceId) {
                if (!isLogin()) {
                    return;
                }
                MobclickAgent.onEvent(mContext, "home_scrollinfo");//统计点击次数
                CoopDetailActivity.start(mContext, resourceId);
            }
        });

        if (mainBean.getRecommendNav().size() > 0) {
            //推荐资源列表
            mResourceList.clear();
            mResourceList.addAll(mainBean.getRecommendNav());
            mrecyclerType.setVisibility(View.VISIBLE);
            if (Constants.userIdList.length() > 0) {
                int size02 = mResourceList.size();
                for (int m = 0; m < size02; m++) {
                    String[] all = Constants.userIdList.split(",");
                    int size = mResourceList.get(m).getResources().size();
                    int size1 = all.length;
                    int i, j;
                    for (i = 0; i < size; i++) {
                        for (j = 0; j < size1; j++) {
                            if (mResourceList.get(m).getResources().get(i).getUser_id() == Integer.parseInt(all[j])) {
//                                Log.i("数据===", "user_id==" + list.getList().get(i).getUser_id() + ",i==" + i + ",j===" + j + "  " + Integer.parseInt(all[j]));
                                mResourceList.get(m).getResources().get(i).setU(true);
                            }
                        }
                    }
                }


            }

            mainTypeAdapter.notifyDataSetChanged();

        } else {
            mResourceList.clear();
            mainTypeAdapter.notifyDataSetChanged();
            mrecyclerType.setVisibility(View.INVISIBLE);

        }

        if (mainBean.getIs_transaction() == 1) {
            //担保交易模块显示
            rlayoutTrans.setVisibility(View.VISIBLE);
        } else {
            rlayoutTrans.setVisibility(View.GONE);
        }

        if (mainBean.getServiceProvider().size() > 0) {
            //企业服务
            rlayoutCompanyService.setVisibility(View.VISIBLE);
            mServiceList.clear();
            companyServiceRecycler.setVisibility(View.VISIBLE);
            mServiceList.addAll(mainBean.getServiceProvider());
            mainServiceAdapter.notifyDataSetChanged();
        } else {
            rlayoutCompanyService.setVisibility(View.GONE);
            mServiceList.clear();
            companyServiceRecycler.setVisibility(View.GONE);
            mainServiceAdapter.notifyDataSetChanged();

        }


        if (mainBean.getEvent().size() > 0) {
            //商务活动
            rlayoutActivity.setVisibility(View.VISIBLE);
            mEventList.clear();
            activityRecycler.setVisibility(View.VISIBLE);
            mEventList.addAll(mainBean.getEvent());
            mainEnentsAdapter.notifyDataSetChanged();
        } else {
            rlayoutActivity.setVisibility(View.GONE);
            mEventList.clear();
            activityRecycler.setVisibility(View.GONE);
            mainEnentsAdapter.notifyDataSetChanged();

        }

        if (mainBean.getReward().size() > 0) {
            //悬赏
            rlayoutReward.setVisibility(View.VISIBLE);
            mRewardList.clear();
            rewardRecycler.setVisibility(View.VISIBLE);
            mRewardList.addAll(mainBean.getReward());
            if (Constants.userIdList.length() > 0) {
                String[] all = Constants.userIdList.split(",");
                int size = mRewardList.size();
                int size1 = all.length;
                int i, j;
                for (i = 0; i < size; i++) {
                    for (j = 0; j < size1; j++) {
                        if (mRewardList.get(i).getUser_id() == Integer.parseInt(all[j])) {
//                                Log.i("数据===", "user_id==" + list.getList().get(i).getUser_id() + ",i==" + i + ",j===" + j + "  " + Integer.parseInt(all[j]));
                            mRewardList.get(i).setU(true);
                        }
                    }

                }


            }


            mainRewardAdapter.notifyDataSetChanged();

        } else {
            rlayoutReward.setVisibility(View.GONE);
            mRewardList.clear();
            rewardRecycler.setVisibility(View.GONE);
            mainRewardAdapter.notifyDataSetChanged();


        }

        if (mainBean.getVideo().size() > 0) {
            //异业课堂
            rlayoutClass.setVisibility(View.VISIBLE);
            mVideoList.clear();
            classRecycler.setVisibility(View.VISIBLE);
            mVideoList.addAll(mainBean.getVideo());
            mainClassRoomAdapter.notifyDataSetChanged();
        } else {
            rlayoutClass.setVisibility(View.GONE);
            mVideoList.clear();
                classRecycler.setVisibility(View.GONE);
            mainClassRoomAdapter.notifyDataSetChanged();

        }

    }

    /**
     * 下拉刷新
     */
    private void handlerRecyclerView() {
        //下拉刷新
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {

                buildConfig(false);

            }
        });

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

    @OnClick({R.id.qrBt, R.id.sreach_content_et, R.id.sreach_content_ll, R.id.tv_launch_transation, R.id.rlayout_company_service, R.id.rlayout_activity, R.id.rlayout_reward, R.id.rlayout_class})
    public void onClick(View view) {
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
            case R.id.tv_launch_transation:
                //发起交易
                LaunchTransactionAvtivity.start(getActivity(), "-1", 1);
                break;
            case R.id.rlayout_company_service:
                //更多企业服务
                IndexServiceActivity.start(mContext);
                break;
            case R.id.rlayout_activity:
                //更多商务活动
                MobclickAgent.onEvent(mContext, "home_eventMore");//统计点击次数
                String actListUrl = RetrofitHelper.API_URL + "resource/pages/qqqAct/home.html";
                ApproveCardActivity.start(mContext, "url", actListUrl, "");
                break;
            case R.id.rlayout_reward:
                //更多悬赏
                RewardListActivity.start(mContext);
                break;
            case R.id.rlayout_class:
                //异业课堂
                ClassRoomActivity.start(mContext);
                break;
        }
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

    private boolean isLogin() {
        if (!UserInfoHelper.getIntance().isLogin()) {
            // TODO: 2017/12/20
//            ToastUtils.showCentetImgToast(getContext(), "还未登录");
            LoginNewActivity.start(mContext);
        }
        return UserInfoHelper.getIntance().isLogin();
    }

    /**
     * 此方法用于进入详情，删除资源回来后刷新
     */

    public void refreshPage() {

        buildConfig(false);
    }

    public void moveToTop() {
        if (indexScroll != null) {
            indexScroll.smoothScrollTo(0, 0);
        }
    }

}
