package com.xinniu.android.qiqueqiao.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextPaint;
import android.text.TextUtils;
import android.view.View;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.xinniu.android.qiqueqiao.R;
import com.xinniu.android.qiqueqiao.adapter.IndexNewAdapter;
import com.xinniu.android.qiqueqiao.base.BaseActivity;
import com.xinniu.android.qiqueqiao.bean.CityV2Bean;
import com.xinniu.android.qiqueqiao.bean.IndexNewBean;
import com.xinniu.android.qiqueqiao.bean.SelectCategory;
import com.xinniu.android.qiqueqiao.bean.SourceScreenBean;
import com.xinniu.android.qiqueqiao.common.Constants;
import com.xinniu.android.qiqueqiao.customs.CityNewWindow;
import com.xinniu.android.qiqueqiao.customs.SourceScreenWindow;
import com.xinniu.android.qiqueqiao.customs.TypeSelectWindow;
import com.xinniu.android.qiqueqiao.request.RequestManager;
import com.xinniu.android.qiqueqiao.request.callback.GetAppAreaCallback;
import com.xinniu.android.qiqueqiao.request.callback.GetCategoryCallback;
import com.xinniu.android.qiqueqiao.request.callback.GetResourceListCallback;
import com.xinniu.android.qiqueqiao.request.callback.SourceScreenCallback;
import com.xinniu.android.qiqueqiao.utils.ResouceHelper;
import com.xinniu.android.qiqueqiao.utils.ShowUtils;
import com.xinniu.android.qiqueqiao.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by yuchance on 2018/6/26.
 */

public class IndexAllActivity extends BaseActivity {

    @BindView(R.id.bt_close)
    RelativeLayout btClose;
    @BindView(R.id.bsearch_Rl)
    RelativeLayout bsearchRl;
    @BindView(R.id.btvSort)
    TextView btvSort;
    @BindView(R.id.btvScreen)
    TextView btvScreen;
    @BindView(R.id.btvPlace)
    TextView btvPlace;
    @BindView(R.id.recyclerAll)
    RecyclerView recyclerAll;
    @BindView(R.id.mrefresh)
    SmartRefreshLayout mrefresh;
    @BindView(R.id.theselect)
    RelativeLayout theselect;
    @BindView(R.id.yperch_Rl)
    RelativeLayout yperchRl;

    private int mSearchCityId = 0;
    private String mSearchKeyWord;
    private int mSearchSortOrder = 36;
    private int mSearchCategory;
    private String mSearchQueryId;
    private String mSearchIndustry;

    private int mSearchPage = 1;
    private int mQueryType = 3;
    private IndexNewAdapter adapter;
    private List<IndexNewBean.ListBean> resourceItems = new ArrayList<>();
    private final static int THECITYCODE = 1011;
    private TypeSelectWindow typeSelectWindow;
    private Integer SORTINT = 5;
    private SourceScreenWindow screenWindow;
    private CityNewWindow cityNewWindow;
    private int leftSelectCity;


    public static void start(Context mContext) {
        Intent intent = new Intent(mContext, IndexAllActivity.class);
        mContext.startActivity(intent);
    }


    @Override
    public int getLayoutId() {
        return R.layout.activity_index_all;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        topStatusBar(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(IndexAllActivity.this, LinearLayoutManager.VERTICAL, false);
        recyclerAll.setLayoutManager(linearLayoutManager);
        adapter = new IndexNewAdapter(IndexAllActivity.this, R.layout.item_index_new, resourceItems, 0,0);
        recyclerAll.setAdapter(adapter);
        buildData(mQueryType, mSearchPage, mSearchCityId, mSearchKeyWord, mSearchSortOrder, mSearchCategory, mSearchQueryId, mSearchIndustry, true);
        mrefresh.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                mSearchPage = 1;
                buildData(mQueryType, mSearchPage, mSearchCityId, mSearchKeyWord, mSearchSortOrder, mSearchCategory, mSearchQueryId, mSearchIndustry, false);
            }
        });
        mrefresh.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                mSearchPage++;
                buildData(mQueryType, mSearchPage, mSearchCityId, mSearchKeyWord, mSearchSortOrder, mSearchCategory, mSearchQueryId, mSearchIndustry, false);
            }
        });
    }

    private void buildData(int mQueryType, final int mSearchPage, int mSearchCityId, String mSearchKeyWord, int mSearchSortOrder, int mSearchCategory, String mSearchQueryId, String mSearchIndustry, boolean isShow) {
        if (isShow) {
            showBookingToast(1);
        }
        RequestManager.getInstance().getResourceItem(mSearchPage, mSearchCityId, mSearchKeyWord,"", mSearchSortOrder, mSearchQueryId, mSearchIndustry,0,0,0,0, new GetResourceListCallback() {
            @Override
            public void onSuccess(IndexNewBean resultDO) {
                dismissBookingToast();
                finishSwipe();
                if (mSearchPage == 1) {
                    resourceItems.clear();
                    if (resultDO.getList().size() == 0) {
                        yperchRl.setVisibility(View.VISIBLE);
                        adapter.removeAllFooterView();
                        mrefresh.setEnableLoadMore(false);
                    }else {
                        yperchRl.setVisibility(View.GONE);
                        if (resultDO.getHasMore()==0){
                            adapter.setFooterView(footView);
                            mrefresh.setEnableLoadMore(false);
                        }else {
                            adapter.removeAllFooterView();
                            mrefresh.setEnableLoadMore(true);
                        }
                    }
                }else {
                    if (resultDO.getHasMore()==0){
                        adapter.setFooterView(footView);
                        mrefresh.setEnableLoadMore(false);
                    }else {
                        adapter.removeAllFooterView();
                        mrefresh.setEnableLoadMore(true);
                    }
                }
                resourceItems.addAll(resultDO.getList());
                if (resourceItems.size()>15&&!resourceItems.get(15).isSearchPerch()){
                    resourceItems.add(15,new IndexNewBean.ListBean(0,"",0,"","","","","","",0,0,0,0,0,"","",true));
                }
                if (resourceItems.size() == 0) {
                    ToastUtils.showCentetImgToast(mContext, "暂无数据");
                }
                adapter.notifyDataSetChanged();

            }

            @Override
            public void onFailed(int code, String msg) {
                dismissBookingToast();
                finishSwipe();
                ToastUtils.showCentetToast(IndexAllActivity.this, msg);
            }
        });

    }

    @OnClick({R.id.bt_close, R.id.bsearch_Rl, R.id.btvSort, R.id.btvScreen, R.id.btvPlace})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bt_close:
                finish();
                break;
            case R.id.bsearch_Rl:
                SreachActivity.start(IndexAllActivity.this);
                break;
            case R.id.btvSort:
                if (ResouceHelper.getInstance().getOrderList() != null) {
                    showPopu(ResouceHelper.getInstance().getOrderList(), Constants.TYPE_SELECT_ORDER);
                } else {
                    buildWindow(SORTINT);
                }
                break;
            case R.id.btvScreen:
                if (ResouceHelper.getSourceScreenBean() != null) {
                    showScreenPop(ResouceHelper.getSourceScreenBean());
                } else {
                    buildScreen();

                }
                break;
            case R.id.btvPlace:
//                Intent intent = new Intent(mContext, SelectCityActivity.class);
//                intent.putExtra(SelectCityActivity.FROM_TYPE, 2);
//                startActivityForResult(intent, THECITYCODE);
                if (ResouceHelper.getInstance().getCityV2List()!=null){
                    showCity(ResouceHelper.getInstance().getCityV2List());
                }else {
                    RequestCity();
                }


                break;
            default:

                break;
        }
    }

    private void RequestCity() {
        showBookingToast(2);
        RequestManager.getInstance().getAppArea(new GetAppAreaCallback() {
            @Override
            public void onSuccess(List<CityV2Bean> list) {
                list.get(0).getZlist().add(0,new CityV2Bean.ZlistBean(0,"全国",false));
                showCity(list);
                ResouceHelper.getInstance().setCityV2List(list);
                dismissBookingToast();
            }

            @Override
            public void onFailed(int code, String msg) {
                dismissBookingToast();
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

                dispopwindow();

            }
        });

    }
    private void showCity(final List<CityV2Bean> leftList) {
        cityNewWindow = new CityNewWindow(IndexAllActivity.this,leftList,leftSelectCity);
        if(mSearchCityId !=0){
            for (int i = 0; i < leftList.get(leftSelectCity).getZlist().size() ; i++) {
                if (leftList.get(leftSelectCity).getZlist().get(i).getId() == mSearchCityId){
                    leftList.get(leftSelectCity).getZlist().get(i).setCheck(true);
                }
            }
        }
        cityNewWindow.showAsDropDown(theselect);
        cityNewWindow.setSetCityIdAndPostion(new CityNewWindow.getCityIdAndPostion() {
            @Override
            public void getCityIdandPostion(int leftPostion, int cityId,String cityName) {
                leftSelectCity = leftPostion;
                mSearchCityId = cityId;
                refreshResouceListByCt(cityName);
                buildData(mQueryType, mSearchPage, mSearchCityId, mSearchKeyWord, mSearchSortOrder, mSearchCategory, mSearchQueryId, mSearchIndustry, true);

            }
        });

    }

    private void refreshResouceListByCt(String cityName) {
        ShowUtils.showTextPerfect(btvPlace, cityName);
        btvPlace.setSelected(true);
        TextPaint tp = btvPlace.getPaint();
        tp.setFakeBoldText(true);
        ShowUtils.showTextPerfect(btvPlace, cityName);
        btvPlace.setSelected(true);
        TextPaint xtp = btvPlace.getPaint();
        xtp.setFakeBoldText(true);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == MyPushActivity.REQUESTCODE&&resultCode==CoopDetailActivity.REQUESTREFRESH){
            mSearchPage = 1;
            buildData(mQueryType, mSearchPage, mSearchCityId, mSearchKeyWord, mSearchSortOrder, mSearchCategory, mSearchQueryId, mSearchIndustry, true);
        }


    }

    private void showPopu(List<SelectCategory> orderList, int typeSelectOrder) {

        typeSelectWindow = new TypeSelectWindow(IndexAllActivity.this, orderList, typeSelectOrder);
        typeSelectWindow.showPopupWindowx(theselect);
        typeSelectWindow.setItemClikListner(new TypeSelectWindow.ItemClikListner() {
            @Override
            public void onItemClik(int type, SelectCategory selectCategory) {
                if (type == Constants.TYPE_SELECT_ORDER) {
                    btvSort.setText(selectCategory.getName());
                    mSearchSortOrder = selectCategory.getId();
                    mSearchPage = 1;
                    buildData(mQueryType, mSearchPage, mSearchCityId, mSearchKeyWord, mSearchSortOrder, mSearchCategory, mSearchQueryId, mSearchIndustry, true);

                }
            }
        });

        typeSelectWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                typeSelectWindow.onDismiss();
            }
        });

    }

    private void buildWindow(final Integer type) {
        RequestManager.getInstance().getScreen(type, new GetCategoryCallback() {
            @Override
            public void onSuccess(List<SelectCategory> list) {
                if (type.equals(SORTINT)) {
                    ResouceHelper.getInstance().setOrderList(list);
                    showPopu(ResouceHelper.getInstance().getOrderList(), Constants.TYPE_SELECT_ORDER);
                }
            }

            @Override
            public void onFailed(int code, String msg) {

            }
        });


    }

    private void showScreenPop(SourceScreenBean screenBean) {
        if (mQueryType != 3) {

        }
        if (mSearchQueryId != null && !mSearchQueryId.equals("")) {
            for (int i = 0; i < screenBean.getCategory_list().size(); i++) {
                String[] mQueryId = mSearchQueryId.split("_");
                for (int j = 0; j < mQueryId.length; j++) {
                    if ((screenBean.getCategory_list().get(i).getId() + "").equals(mQueryId[j])) {
                        screenBean.getCategory_list().get(i).setCheck(true);
                    }
                }

            }
        } else {
            for (int i = 0; i < screenBean.getCategory_list().size(); i++) {
                screenBean.getCategory_list().get(i).setCheck(false);
            }
        }

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
        screenWindow = new SourceScreenWindow(IndexAllActivity.this, screenBean);
        screenWindow.showPopupWindow(theselect);
        screenWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                screenWindow.onDismiss();
                if (TextUtils.isEmpty(mSearchQueryId) && TextUtils.isEmpty(mSearchIndustry)) {
                    btvScreen.setSelected(false);
                } else {
                    btvScreen.setSelected(true);
                }
            }
        });
        screenWindow.setFinish(new SourceScreenWindow.finish() {
            @Override
            public void setFinish(String QueryType, String SearchQueryId, String SearchIndustry) {

                if (!TextUtils.isEmpty(QueryType)) {
                    mQueryType = Integer.parseInt(QueryType);
                }
                mSearchQueryId = SearchQueryId;
                mSearchIndustry = SearchIndustry;
                mSearchPage = 1;
                if (TextUtils.isEmpty(mSearchQueryId) && TextUtils.isEmpty(mSearchIndustry)) {
                    btvScreen.setSelected(false);
                } else {
                    btvScreen.setSelected(true);
                }
                recyclerAll.scrollToPosition(0);
                buildData(mQueryType, mSearchPage, mSearchCityId, mSearchKeyWord, mSearchSortOrder, mSearchCategory, mSearchQueryId, mSearchIndustry, true);
                screenWindow.dismissPopup();
            }
        });


    }

    private void finishSwipe() {
        if (mrefresh != null) {
            if (mrefresh.isEnableRefresh()) {
                mrefresh.finishRefresh(200);
            }
            if (mrefresh.isEnableLoadMore()) {
                mrefresh.finishLoadMore(200);
            }
        }
    }
}
