package com.xinniu.android.qiqueqiao.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
//import android.support.annotation.NonNull;
//import android.support.v7.app.AlertDialog;
//import android.support.v7.widget.LinearLayoutManager;
//import androidx.recyclerview.widget.RecyclerView;
import android.text.TextPaint;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.xinniu.android.qiqueqiao.R;
import com.xinniu.android.qiqueqiao.adapter.SearchIndexAdapter;
import com.xinniu.android.qiqueqiao.adapter.SearchServiceAdapter;
import com.xinniu.android.qiqueqiao.base.BaseActivity;
import com.xinniu.android.qiqueqiao.bean.CenterBean;
import com.xinniu.android.qiqueqiao.bean.CityV2Bean;
import com.xinniu.android.qiqueqiao.bean.IndexNewBean;
import com.xinniu.android.qiqueqiao.bean.IndexServiceBean;
import com.xinniu.android.qiqueqiao.bean.SelectCategory;
import com.xinniu.android.qiqueqiao.bean.SourceScreenBean;
import com.xinniu.android.qiqueqiao.customs.CityNewWindow;
import com.xinniu.android.qiqueqiao.customs.ClearEditText;
import com.xinniu.android.qiqueqiao.customs.IndustryWindow;
import com.xinniu.android.qiqueqiao.customs.SortOrderWindow;
import com.xinniu.android.qiqueqiao.customs.qldialog.QLTipDialog;
import com.xinniu.android.qiqueqiao.request.RequestManager;
import com.xinniu.android.qiqueqiao.request.callback.CommonCallback;
import com.xinniu.android.qiqueqiao.request.callback.GetAppAreaCallback;
import com.xinniu.android.qiqueqiao.request.callback.GetResourceListCallback;
import com.xinniu.android.qiqueqiao.request.callback.RequestCallback;
import com.xinniu.android.qiqueqiao.request.callback.ServiceListCallback;
import com.xinniu.android.qiqueqiao.request.callback.SourceScreenCallback;
import com.xinniu.android.qiqueqiao.request.converter.ResultDO;
import com.xinniu.android.qiqueqiao.user.UserInfoHelper;
//import com.xinniu.android.qiqueqiao.utils.IMUtils;
import com.xinniu.android.qiqueqiao.utils.ResouceHelper;
import com.xinniu.android.qiqueqiao.utils.ShowUtils;
import com.xinniu.android.qiqueqiao.utils.StringUtils;
import com.xinniu.android.qiqueqiao.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import retrofit2.Call;

/**
 * Created by lzq on 2017/12/8.
 * 搜索页面
 */

public class SreachListActivity extends BaseActivity {


    @BindView(R.id.result_rv)
    RecyclerView mRecyclerView;
    @BindView(R.id.srearch_tv)
    TextView searchTv;
    @BindView(R.id.sreach_content_et)
    ClearEditText sreachEt;
    @BindView(R.id.bt_return)
    LinearLayout closeIv;
    @BindView(R.id.searchSwipe)
    SmartRefreshLayout searchSwipe;
    @BindView(R.id.tvOrder)
    TextView tvOrder;
    @BindView(R.id.tvSearched)
    TextView tvSearched;
    @BindView(R.id.thecoopRl)
    LinearLayout thecoopRl;
    @BindView(R.id.yperch_Rl)
    RelativeLayout yperchRl;
    @BindView(R.id.mallline)
    View mallline;
    @BindView(R.id.mofferline)
    View mofferline;
    @BindView(R.id.mneedline)
    View mneedline;
    @BindView(R.id.bsearch_allTv)
    TextView bsearchAllTv;
    @BindView(R.id.bsearch_offerTv)
    TextView bsearchOfferTv;
    @BindView(R.id.bsearch_needTv)
    TextView bsearchNeedTv;
    @BindView(R.id.topTabLinear)
    RelativeLayout topTabLinear;
    @BindView(R.id.bsearch_service)
    TextView bsearchService;
    @BindView(R.id.mserviceline)
    View mserviceline;
    @BindView(R.id.search_text)
    TextView searchText;
    @BindView(R.id.search_content)
    TextView searchContent;
    @BindView(R.id.bnoRec_lxx)
    TextView bnoRecLxx;
    @BindView(R.id.rlayout_sercive)
    RelativeLayout rlayoutSercive;
    private int mSearchCityId = 0;
    private int leftSelectCity = 0;
    private int mSearchSortOrder;
    private int mQueryType = 3;
    private String mSearchQueryId = "";
    private String mSearchIndustry = "";
    private String mSearchCooperationMode;


    //首页列表
    private List<IndexServiceBean.ListBean> data = new ArrayList<>();
    private List<IndexNewBean.ListBean> resourceItems = new ArrayList<>();
    private SearchIndexAdapter mIndexFragmentAdapter;
    private SearchServiceAdapter indexServiceAdapter;

    public final static String CONTENT_TAG = "content";

    private int page = 1;
    private String keyWord;
    private String keyWordRange = "3";
    private CenterBean mCenterBean;
    private List<SelectCategory> selectBean = new ArrayList<>();
    private String mCurrent = "0";
    private List<String> lastList = UserInfoHelper.getIntance().getSearchHistory();//搜索历史
    private int mFrom = 0;
    private int p_id = 0;


    public static void start(Context mContext, String keyword, int from, int p_id, String theTitle) {
        Intent intent = new Intent(mContext, SreachListActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("content", keyword);
        bundle.putInt("from", from);
        bundle.putInt("p_id", p_id);
        bundle.putString("p_name", theTitle);
        intent.putExtras(bundle);
        mContext.startActivity(intent);

    }


    @Override
    public int getLayoutId() {
        return R.layout.activity_search_list;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        topStatusBar(true);

        indexServiceAdapter = new SearchServiceAdapter(SreachListActivity.this, R.layout.item_service, data, 2);
        mIndexFragmentAdapter = new SearchIndexAdapter(mContext, R.layout.item_index_search_new, resourceItems, 0);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mIndexFragmentAdapter);


        keyWord = getIntent().getStringExtra(CONTENT_TAG);
        mFrom = getIntent().getIntExtra("from", 0);
        p_id = getIntent().getIntExtra("p_id", 0);

        if (mFrom == 1) {
            //从分类页过来的搜索
            String p_name = getIntent().getStringExtra("p_name");
            rlayoutSercive.setVisibility(View.INVISIBLE);
            sreachEt.setHint("搜索" + p_name);
            ShowUtils.showViewVisible(thecoopRl, View.GONE);
            sreachEt.setFocusable(true);   //设置可以获取焦点
            sreachEt.setFocusableInTouchMode(true);
            sreachEt.requestFocus();
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        }
        searchSwipe.setEnableRefresh(false);
        if (!TextUtils.isEmpty(keyWord)) {
            sreachEt.setText(keyWord);
            sreachEt.setSelection(keyWord.length());
            search(mQueryType, page, mSearchCityId, mSearchSortOrder, mSearchQueryId, mSearchIndustry, mSearchCooperationMode, keyWord, keyWordRange, true, true);
        }

        sreachEt.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    final String tvName = sreachEt.getText().toString();
                    if (TextUtils.isEmpty(tvName)) {
                        ToastUtils.showCentetImgToast(SreachListActivity.this, "请输入搜索内容");
                    } else {

                        keyWordRange = "3";
                        keyWord = tvName;
                        mQueryType = 3;
                        mCurrent = "0";
                        page = 1;
                        mSearchCityId = 0;
                        mSearchIndustry = "";
                        mSearchCooperationMode = "";
                        tvOrder.setText("行业");
                        tvOrder.setSelected(false);
                        bsearchAllTv.setSelected(true);
                        bsearchOfferTv.setSelected(false);
                        bsearchNeedTv.setSelected(false);
                        bsearchService.setSelected(false);
                        mserviceline.setVisibility(View.GONE);
                        mallline.setVisibility(View.VISIBLE);
                        mofferline.setVisibility(View.GONE);
                        mneedline.setVisibility(View.GONE);
                        tvOrder.setVisibility(View.VISIBLE);
                        tvSearched.setVisibility(View.VISIBLE);
                        mRecyclerView.setAdapter(mIndexFragmentAdapter);
                        refreshResouceListByCt("地区", false);
                        search(mQueryType, page, mSearchCityId, mSearchSortOrder, mSearchQueryId, mSearchIndustry, mSearchCooperationMode, keyWord, keyWordRange, true, true);
                        showBookingToast(2);
                        return true;
                    }

                }
                return false;
            }
        });

        searchSwipe.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                page++;
                search(mQueryType, page, mSearchCityId, mSearchSortOrder, mSearchQueryId, mSearchIndustry, mSearchCooperationMode, keyWord, keyWordRange, false, false);

            }
        });


    }


    @OnClick({R.id.srearch_tv, R.id.bt_return, R.id.tvOrder, R.id.tvSearched, R.id.bnoRec_lxx, R.id.bsearch_allTv, R.id.bsearch_offerTv, R.id.bsearch_needTv, R.id.bsearch_service})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.srearch_tv:
                final String tvName = sreachEt.getText().toString();
                if (TextUtils.isEmpty(tvName)) {
                    ToastUtils.showCentetImgToast(SreachListActivity.this, "请输入搜索内容");
                    return;
                }

                keyWordRange = "3";
                bsearchAllTv.setSelected(true);
                bsearchOfferTv.setSelected(false);
                bsearchNeedTv.setSelected(false);
                bsearchService.setSelected(false);
                mserviceline.setVisibility(View.GONE);
                mallline.setVisibility(View.VISIBLE);
                mofferline.setVisibility(View.GONE);
                mneedline.setVisibility(View.GONE);
                tvOrder.setVisibility(View.VISIBLE);
                tvSearched.setVisibility(View.VISIBLE);
                keyWord = tvName;
                mQueryType = 3;
                page = 1;
                mSearchCityId = 0;
                mSearchQueryId = "";
                mSearchIndustry = "";
                mSearchCooperationMode = "";
//                tvSourceType.setText("全部资源");
                tvOrder.setText("行业");
                tvOrder.setSelected(false);
                refreshResouceListByCt("地区", false);
                search(mQueryType, page, mSearchCityId, mSearchSortOrder, mSearchQueryId, mSearchIndustry, mSearchCooperationMode, keyWord, keyWordRange, true, true);
                break;
            case R.id.bt_return:
                finish();
                break;

//            case R.id.tvSourceType:
//                SelectCategory category = new SelectCategory(3, "全部资源", false);
//                SelectCategory category1 = new SelectCategory(1, "仅看提供", false);
//                SelectCategory category2 = new SelectCategory(2, "仅看需求", false);
//                selectBean.clear();
//                selectBean.add(category);
//                selectBean.add(category1);
//                selectBean.add(category2);
//                showPopu(selectBean);
//
//                break;
            case R.id.tvOrder:
                if (ResouceHelper.getSourceScreenBean() != null) {
                    showIndustry(ResouceHelper.getSourceScreenBean());
                } else {
                    buildIndustry();
                }
                break;
            case R.id.tvSearched:
                if (ResouceHelper.getInstance().getCityV2List() != null) {
                    showCityList(ResouceHelper.getInstance().getCityV2List());
                } else {
                    buildCity();
                }
                break;
            case R.id.bnoRec_lxx:
                if (!isLogin()) {
                    return;
                }
                if (bnoRecLxx.getText().toString().equals("立即发布")) {
                    toReleaseActivity();

                } else {
                    RequestManager.getInstance().center(new RequestCallback<CenterBean>() {
                        @Override
                        public void requestStart(Call call) {

                        }

                        @Override
                        public void onSuccess(CenterBean centerBean) {
                            mCenterBean = centerBean;
                            String sreachStr = sreachEt.getText().toString();
                            String content = "";
                            if (mCurrent.equals("0")) {
                                content = "专属服务经理你好，我未在企鹊桥搜索到“" + sreachStr + "”的资源，你这边帮我定向寻找下吧";
                            } else {
                                content = "专属服务经理你好，我未在企鹊桥搜索到“" + sreachStr + "”的服务，你这边帮我定向寻找下吧";
                            }

                            //IMUtils.singleChat(SreachListActivity.this, String.valueOf(mCenterBean.getUsers().getF_id()), "客服", "1", content);
                        }

                        @Override
                        public void onFailed(int code, String msg) {
                            ToastUtils.showCentetImgToast(mContext, msg);
                        }

                        @Override
                        public void requestEnd() {
                        }
                    });
                }
                break;
            case R.id.bsearch_allTv:
                if (keyWordRange.equals("3")) {
                    return;
                }
                bsearchAllTv.setSelected(true);
                bsearchOfferTv.setSelected(false);
                bsearchNeedTv.setSelected(false);
                bsearchService.setSelected(false);
                mserviceline.setVisibility(View.GONE);
                mallline.setVisibility(View.VISIBLE);
                mofferline.setVisibility(View.GONE);
                mneedline.setVisibility(View.GONE);
                keyWordRange = "3";
                tvOrder.setVisibility(View.VISIBLE);
                tvSearched.setVisibility(View.VISIBLE);
                mRecyclerView.setAdapter(mIndexFragmentAdapter);
                page = 1;
                mCurrent = "0";
                search(mQueryType, page, mSearchCityId, mSearchSortOrder, mSearchQueryId, mSearchIndustry, mSearchCooperationMode, keyWord, keyWordRange, false, false);

                break;
            case R.id.bsearch_offerTv:
                if (keyWordRange.equals("1")) {
                    return;
                }
                bsearchAllTv.setSelected(false);
                bsearchOfferTv.setSelected(true);
                bsearchNeedTv.setSelected(false);
                bsearchService.setSelected(false);
                mserviceline.setVisibility(View.GONE);
                mallline.setVisibility(View.GONE);
                mofferline.setVisibility(View.VISIBLE);
                mneedline.setVisibility(View.GONE);
                keyWordRange = "1";
                mRecyclerView.setAdapter(mIndexFragmentAdapter);
                tvOrder.setVisibility(View.VISIBLE);
                tvSearched.setVisibility(View.VISIBLE);
                page = 1;
                mCurrent = "0";
                search(mQueryType, page, mSearchCityId, mSearchSortOrder, mSearchQueryId, mSearchIndustry, mSearchCooperationMode, keyWord, keyWordRange, false, false);

                break;
            case R.id.bsearch_needTv:
                if (keyWordRange.equals("2")) {
                    return;
                }
                bsearchAllTv.setSelected(false);
                bsearchOfferTv.setSelected(false);
                bsearchNeedTv.setSelected(true);
                bsearchService.setSelected(false);
                mserviceline.setVisibility(View.GONE);
                mallline.setVisibility(View.GONE);
                mofferline.setVisibility(View.GONE);
                mneedline.setVisibility(View.VISIBLE);
                keyWordRange = "2";
                mRecyclerView.setAdapter(mIndexFragmentAdapter);
                tvOrder.setVisibility(View.VISIBLE);
                tvSearched.setVisibility(View.VISIBLE);
                page = 1;
                mCurrent = "0";
                search(mQueryType, page, mSearchCityId, mSearchSortOrder, mSearchQueryId, mSearchIndustry, mSearchCooperationMode, keyWord, keyWordRange, false, false);

                break;
            case R.id.bsearch_service:
                if (keyWordRange.equals("4")) {
                    return;
                }
                bsearchAllTv.setSelected(false);
                bsearchOfferTv.setSelected(false);
                bsearchNeedTv.setSelected(false);
                bsearchService.setSelected(true);
                mserviceline.setVisibility(View.VISIBLE);
                mallline.setVisibility(View.GONE);
                mofferline.setVisibility(View.GONE);
                mneedline.setVisibility(View.GONE);
                //找服务请求
                tvOrder.setVisibility(View.GONE);
                tvSearched.setVisibility(View.GONE);
                mRecyclerView.setAdapter(indexServiceAdapter);
                page = 1;
                mCurrent = "1";
                keyWordRange = "4";
                buildData(page, sreachEt.getText().toString());
                break;

            default:
                break;
        }
    }

    /**
     * 找服务请求
     *
     * @param mPage
     * @param s
     */
    private void buildData(final int mPage, String s) {
        showBookingToast(2);
        addItem(keyWord);
        RequestManager.getInstance().getSearchServiceInfoList(mPage, s, new ServiceListCallback() {
            @Override
            public void onSuccess(IndexServiceBean bean) {
                dismissBookingToast();
                mIndexFragmentAdapter.removeAllFooterView();
                ShowUtils.showViewVisible(thecoopRl, View.VISIBLE);
                if (mPage == 1) {
                    data.clear();
                    if (bean.getList().size() == 0) {
                        searchSwipe.setEnableLoadMore(false);
                     //   ShowUtils.showViewVisible(thecoopRl, View.GONE);
                        ShowUtils.showViewVisible(yperchRl, View.VISIBLE);
                        searchText.setText("未搜索到相关服务");
                        searchContent.setText("试试联系专属服务经理，精准寻找服务");
                        bnoRecLxx.setText("立即联系");
                    } else {

                        ShowUtils.showViewVisible(yperchRl, View.GONE);
                        if (bean.getHasMore() == 0) {
                            indexServiceAdapter.setFooterView(footView);
                            searchSwipe.setEnableLoadMore(false);
                        } else {
                            indexServiceAdapter.removeAllFooterView();
                            searchSwipe.setEnableLoadMore(true);
                        }
                    }
                } else {
                    if (bean.getHasMore() == 0) {
                        indexServiceAdapter.setFooterView(footView);
                        searchSwipe.setEnableLoadMore(false);
                    } else {
                        indexServiceAdapter.removeAllFooterView();
                        searchSwipe.setEnableLoadMore(true);
                    }
                }

                data.addAll(bean.getList());
                indexServiceAdapter.setKeyWord(keyWord);
                indexServiceAdapter.notifyDataSetChanged();
                stopRefresh();

            }

            @Override
            public void onFailue(int code, String msg) {
                dismissBookingToast();
                ToastUtils.showCentetToast(SreachListActivity.this, msg);
            }
        });

    }

    private void buildIndustry() {
        RequestManager.getInstance().getSourceScreen(new SourceScreenCallback() {
            @Override
            public void onSuccess(SourceScreenBean bean) {
                ResouceHelper.setSourceScreenBean(bean);
                showIndustry(bean);
            }

            @Override
            public void onFailue(int code, String msg) {
                ToastUtils.showCentetToast(mContext, msg);
            }
        });
    }

    private void showIndustry(SourceScreenBean screenBean) {
        if (!mSearchIndustry.equals("")) {
            for (int i = 0; i < screenBean.getCompany_list().size(); i++) {
                if ((screenBean.getCompany_list().get(i).getId() + "").equals(mSearchIndustry)) {
                    screenBean.getCompany_list().get(i).setCheck(true);
                } else {
                    screenBean.getCompany_list().get(i).setCheck(false);
                }
            }
        } else {
            for (int i = 0; i < screenBean.getCompany_list().size(); i++) {

                screenBean.getCompany_list().get(i).setCheck(false);

            }
        }
        IndustryWindow mWindow = new IndustryWindow(SreachListActivity.this, screenBean.getCompany_list(), "公司行业");
        if (mWindow.isShowing()) {
            mWindow.dismiss();
        } else {
            mWindow.showPopupWindow(thecoopRl);
        }
        mWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {

            }
        });
        mWindow.setmSetfinish(new IndustryWindow.setfinish() {
            @Override
            public void setToFinish(int mId, String mName) {
                tvOrder.setText(mName);
                mSearchIndustry = mId + "";
                page = 1;
                search(mQueryType, page, mSearchCityId, mSearchSortOrder, mSearchQueryId, mSearchIndustry, mSearchCooperationMode, keyWord, keyWordRange, false, false);

            }
        });

    }


    private void buildCity() {
        showBookingToast(2);
        RequestManager.getInstance().getAppArea(new GetAppAreaCallback() {
            @Override
            public void onSuccess(List<CityV2Bean> list) {
                list.get(0).getZlist().add(0, new CityV2Bean.ZlistBean(0, "全国", false));
                ResouceHelper.getInstance().setCityV2List(list);
                dismissBookingToast();
                showCityList(list);
            }

            @Override
            public void onFailed(int code, String msg) {
                dismissBookingToast();
            }
        });

    }


    private void showCityList(List<CityV2Bean> leftList) {
        CityNewWindow cityNewWindow = new CityNewWindow(SreachListActivity.this, leftList, leftSelectCity);
        if (mSearchCityId != 0) {
            for (int i = 0; i < leftList.get(leftSelectCity).getZlist().size(); i++) {
                if (leftList.get(leftSelectCity).getZlist().get(i).getId() == mSearchCityId) {
                    leftList.get(leftSelectCity).getZlist().get(i).setCheck(true);
                }
            }
        }
        cityNewWindow.showPopupWindowx(thecoopRl);
        cityNewWindow.setSetCityIdAndPostion(new CityNewWindow.getCityIdAndPostion() {
            @Override
            public void getCityIdandPostion(int leftPostion, int cityId, String cityName) {
                leftSelectCity = leftPostion;
                mSearchCityId = cityId;
                refreshResouceListByCt(cityName, true);
                search(mQueryType, page, mSearchCityId, mSearchSortOrder, mSearchQueryId, mSearchIndustry, mSearchCooperationMode, keyWord, keyWordRange, false, false);
            }
        });
    }


    private void showPopu(List<SelectCategory> orderList) {
        final SortOrderWindow sortOrderWindow = new SortOrderWindow(SreachListActivity.this, orderList);
        sortOrderWindow.showAsDropDown(thecoopRl);
        sortOrderWindow.setmSetfinish(new SortOrderWindow.setfinish() {
            @Override
            public void setToFinish(int mId, String mName) {
//                tvSourceType.setText(mName);
                keyWordRange = mId + "";
                page = 1;
                sortOrderWindow.dissPop();
                search(mQueryType, page, mSearchCityId, mSearchSortOrder, mSearchQueryId, mSearchIndustry, mSearchCooperationMode, keyWord, keyWordRange, false, false);
            }
        });


        sortOrderWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {

                sortOrderWindow.onDismiss();
            }
        });
    }

    private void search(int mQueryType, final int page, int mSearchCityId, int mSearchSortOrder, String mSearchQueryId, String mSearchIndustry, String mSearchCooperationMode, final String keyWord, String keyWordRange, final boolean isAdd, final boolean isShow) {
        showBookingToast(2);
        if (isAdd) {
            addItem(keyWord);
        }
        RequestManager.getInstance().getResourceItem(page, mSearchCityId, keyWord, keyWordRange, mSearchSortOrder, mSearchQueryId, mSearchIndustry, p_id, 0, 0, 0,new GetResourceListCallback() {
            @Override
            public void onSuccess(IndexNewBean resultDO) {
                dismissBookingToast();
                stopRefresh();
                ShowUtils.showViewVisible(thecoopRl, View.VISIBLE);
                if (page == 1) {
                    resourceItems.clear();
                    if (resultDO.getList().size() > 0) {

                        searchSwipe.setEnableLoadMore(true);
                        ShowUtils.showViewVisible(yperchRl, View.GONE);
                        mIndexFragmentAdapter.removeAllFooterView();
                    } else {
                        if (resultDO.getHasMore() == 0) {
                            mIndexFragmentAdapter.removeAllFooterView();
                            mIndexFragmentAdapter.setFooterView(footView);
                            searchSwipe.setEnableLoadMore(false);
                        } else {
                            mIndexFragmentAdapter.removeAllFooterView();
                            searchSwipe.setEnableLoadMore(true);
                        }
                     //   ShowUtils.showViewVisible(thecoopRl, View.GONE);
                        ShowUtils.showViewVisible(yperchRl, View.VISIBLE);
                        searchText.setText("未搜索到相关合作信息");
                        searchContent.setText("试试发布合作需求吧");
                        bnoRecLxx.setText("立即发布");
                    }
                } else {

                    if (resultDO.getHasMore() == 0) {
                        mIndexFragmentAdapter.removeAllFooterView();
                        mIndexFragmentAdapter.setFooterView(footView);
                        searchSwipe.setEnableLoadMore(false);
                    } else {
                        mIndexFragmentAdapter.removeAllFooterView();
                        searchSwipe.setEnableLoadMore(true);
                    }
                }


                resourceItems.addAll(resultDO.getList());
                mIndexFragmentAdapter.setKeyWord(keyWord);
                mIndexFragmentAdapter.notifyDataSetChanged();

            }

            @Override
            public void onFailed(int code, String msg) {
                ToastUtils.showCentetImgToast(SreachListActivity.this, msg);
                stopRefresh();
                dismissBookingToast();
            }
        });
    }

    private void addItem(String item) {
        if (!StringUtils.isEmpty(item)) {
            for (int i = 0; i < lastList.size(); i++) {
                if (lastList.get(i).equals(item)) {
                    lastList.remove(i);
                }
            }
            lastList.add(0, item);
        }
    }

    @Override
    protected void onDestroy() {
        UserInfoHelper.getIntance().setSearcHistory(lastList);
        super.onDestroy();
    }


    private void stopRefresh() {
        if (searchSwipe != null) {
            if (searchSwipe.isEnableLoadMore()) {
                searchSwipe.finishLoadMore(true);
            }
        }
    }

    public void refreshResouceListByCt(String cityName, boolean isBold) {
        ShowUtils.showTextPerfect(tvSearched, cityName);
        tvSearched.setSelected(isBold);
        TextPaint tp = tvSearched.getPaint();
        tp.setFakeBoldText(isBold);

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
     * 转调到发布页面
     */
    public void toReleaseActivity() {
        showBookingToast(2);
        RequestManager.getInstance().isPerfect(new CommonCallback() {
            @Override
            public void onSuccess(ResultDO resultDO) {
                dismissBookingToast();

                //  PublishNewActivity.start(IndexCellActivity.this, theTitle, id, 1001);
                PublishSelectTypeActivity.start(SreachListActivity.this);
            }

            @Override
            public void onFailed(int code, final String msg) {
                dismissBookingToast();
                if (code == 202) {
                    new QLTipDialog.Builder(SreachListActivity.this)
                            .setCancelable(true)
                            .setCancelableOnTouchOutside(true)
                            .setMessage(msg)
                            .setNegativeButton("我知道了", new QLTipDialog.INegativeCallback() {
                                @Override
                                public void onClick() {

                                }
                            })
                            .show(SreachListActivity.this);
                } else if (code == 305) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(SreachListActivity.this);
                    builder.setMessage(msg);
                    builder.setPositiveButton("去完善", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
//                            ToastUtils.showCentetImgToast(MainActivity.this, msg);
                            Intent intent = new Intent(SreachListActivity.this, CompanyEditActivity.class);

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
                } else if (code == 310) {
                    //未实人认证
                    new QLTipDialog.Builder(SreachListActivity.this)
                            .setCancelable(true)
                            .setCancelableOnTouchOutside(true)
                            .setMessage(msg)
                            .setNegativeButton("取消", new QLTipDialog.INegativeCallback() {
                                @Override
                                public void onClick() {

                                }
                            }).setPositiveButton("去认证", new QLTipDialog.IPositiveCallback() {
                        @Override
                        public void onClick() {
                            CertificationActivity.start(SreachListActivity.this, 1);
                        }
                    })
                            .show(SreachListActivity.this);
                }
            }
        });
    }
}
