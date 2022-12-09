package com.xinniu.android.qiqueqiao.activity;

import android.content.Intent;
import android.os.Bundle;
//import android.support.annotation.NonNull;
//import android.support.v7.widget.LinearLayoutManager;
//import androidx.recyclerview.widget.RecyclerView;
import android.text.TextPaint;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.umeng.analytics.MobclickAgent;
import com.xinniu.android.qiqueqiao.R;
import com.xinniu.android.qiqueqiao.adapter.CompanyCellAdapter;
import com.xinniu.android.qiqueqiao.adapter.CompanyFragAdapter;
import com.xinniu.android.qiqueqiao.base.BaseActivity;
import com.xinniu.android.qiqueqiao.bean.CityV2Bean;
import com.xinniu.android.qiqueqiao.bean.CompanyListsBean;
import com.xinniu.android.qiqueqiao.bean.SelectCategory;
import com.xinniu.android.qiqueqiao.customs.CityNewWindow;
import com.xinniu.android.qiqueqiao.request.RequestManager;
import com.xinniu.android.qiqueqiao.request.callback.CompanyListCallback;
import com.xinniu.android.qiqueqiao.request.callback.GetAppAreaCallback;
import com.xinniu.android.qiqueqiao.request.callback.GetCategoryCallback;
import com.xinniu.android.qiqueqiao.utils.FullyLinearLayoutManager;
import com.xinniu.android.qiqueqiao.utils.ResouceHelper;
import com.xinniu.android.qiqueqiao.utils.ShowUtils;
import com.xinniu.android.qiqueqiao.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import fj.mtsortbutton.lib.DynamicSoreView;
import fj.mtsortbutton.lib.Interface.IDynamicSore;

public class CompanyIndexActivity extends BaseActivity implements IDynamicSore {

    @BindView(R.id.bt_finish)
    RelativeLayout btFinish;
    @BindView(R.id.bfragment_city)
    TextView bfragmentCity;
    @BindView(R.id.search_img)
    ImageView searchImg;
    @BindView(R.id.bsearch_company)
    RelativeLayout bsearchCompany;
    @BindView(R.id.title_bar)
    RelativeLayout titleBar;
    @BindView(R.id.mCompanyDySoreView)
    DynamicSoreView mCompanyDySoreView;
    @BindView(R.id.mfragment_company_recycler)
    RecyclerView mfragmentCompanyRecycler;
    @BindView(R.id.companySwipe)
    SmartRefreshLayout companySwipe;
    @BindView(R.id.yperch_Rl)
    RelativeLayout yperchRl;
    private int cityIdx = 0;
    private List<CompanyListsBean.ListBean> datas = new ArrayList<>();

    private int industryId = 0;
    private int page = 1;
    private CompanyFragAdapter adapter;
    private CityNewWindow cityNewWindow;
    private int leftSelectCity = 0;
    private List<SelectCategory> companyCellList = new ArrayList<>();
    private String cityNamex = "全国";

    @Override
    public int getLayoutId() {
        return R.layout.activity_company_index;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        topStatusBar(true);
        FullyLinearLayoutManager manager = new FullyLinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        mfragmentCompanyRecycler.setLayoutManager(manager);
        adapter = new CompanyFragAdapter(mContext, R.layout.item_company_cell, datas);
        mfragmentCompanyRecycler.setAdapter(adapter);
        companySwipe.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                page = 1;
                buildDatas(industryId, cityIdx, page, false);
            }
        });
        companySwipe.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                page++;
                buildDatas(industryId, cityIdx, page, false);
            }
        });

        buildCell();
    }

    private void buildCell() {
        showBookingToast(2);
        buildIndustry();
        mCompanyDySoreView.setiDynamicSore(this);
    }

    @OnClick({R.id.bfragment_city, R.id.bsearch_company, R.id.bt_finish})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bfragment_city:
                MobclickAgent.onEvent(mContext, "company_area");//统计banner点击次数

                if (ResouceHelper.getInstance().getCityV2List() != null) {
                    showCity(ResouceHelper.getInstance().getCityV2List());
                } else {
                    RequestCity();
                }

                break;
            case R.id.bsearch_company:
                gotoSearch_company();
                break;
            case R.id.bt_finish:
                finish();
                break;
            default:
                break;
        }
    }

    private void buildDatas(int industryId, int cityId, final int page, boolean isShow) {
        if (isShow) {
            showBookingToast(2);
        }
        RequestManager.getInstance().getCompanyList(industryId, cityId, page, new CompanyListCallback() {
            @Override
            public void onSuccess(CompanyListsBean bean) {
                dismissBookingToast();
                if (page == 1) {
                    datas.clear();
                    if (bean.getList().size() == 0) {
                        yperchRl.setVisibility(View.VISIBLE);
                        adapter.removeAllFooterView();
                        companySwipe.setEnableLoadMore(false);
                    } else {
                        yperchRl.setVisibility(View.GONE);
                        if (bean.getHasMore() == 0) {
                            adapter.setFooterView(footView1);
                            companySwipe.setEnableLoadMore(false);
                        } else {
                            adapter.removeAllFooterView();
                            companySwipe.setEnableLoadMore(true);
                        }
                    }
                } else {
                    if (bean.getHasMore() == 0) {
                        adapter.setFooterView(footView);
                        companySwipe.setEnableLoadMore(false);
                    } else {
                        adapter.removeAllFooterView();
                        companySwipe.setEnableLoadMore(true);
                    }
                }
                datas.addAll(bean.getList());
                adapter.notifyDataSetChanged();
                finishSwipe();
            }

            @Override
            public void onFailue(int code, String msg) {
                dismissBookingToast();
                finishSwipe();
                ToastUtils.showCentetToast(mContext, msg);
            }
        });


    }


    private void RequestCity() {
        showBookingToast(2);
        RequestManager.getInstance().getAppArea(new GetAppAreaCallback() {
            @Override
            public void onSuccess(List<CityV2Bean> list) {
                list.get(0).getZlist().add(0, new CityV2Bean.ZlistBean(0, "全国", false));
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

    private void gotoSearch_company() {
        MobclickAgent.onEvent(mContext, "company_searchbar");//统计banner点击次数
        Intent intent = new Intent(mContext, CompanySearchActivity.class);
        mContext.startActivity(intent);
    }

    private void buildIndustry() {

        RequestManager.getInstance().getScreen(2, new GetCategoryCallback() {
                    @Override
                    public void onSuccess(List<SelectCategory> list) {
                        ResouceHelper.getInstance().setCompanySelect(list);
                        companyCellList.addAll(list);
                        mCompanyDySoreView.setGridView(R.layout.viewpager_page).init(companyCellList);

                    }

                    @Override
                    public void onFailed(int code, String msg) {

                    }
                }

        );
        buildDatas(industryId, cityIdx, page, false);

    }


    private void showCity(final List<CityV2Bean> leftList) {
        cityNewWindow = new CityNewWindow(CompanyIndexActivity.this, leftList, leftSelectCity);
        if (cityIdx != 0) {
            for (int i = 0; i < leftList.get(leftSelectCity).getZlist().size(); i++) {
                if (leftList.get(leftSelectCity).getZlist().get(i).getId() == cityIdx) {
                    leftList.get(leftSelectCity).getZlist().get(i).setCheck(true);
                }
            }
        }
        cityNewWindow.showAsDropDown(titleBar);
        cityNewWindow.setSetCityIdAndPostion(new CityNewWindow.getCityIdAndPostion() {
            @Override
            public void getCityIdandPostion(int leftPostion, int cityId, String cityName) {
                leftSelectCity = leftPostion;
                cityIdx = cityId;
                cityNamex = cityName;
                refreshResouceListByCt(cityNamex);
                buildDatas(industryId, cityIdx, page, true);
            }
        });

    }

    private void refreshResouceListByCt(String cityName) {
        ShowUtils.showTextPerfect(bfragmentCity, cityName);
        bfragmentCity.setSelected(true);
        TextPaint tp = bfragmentCity.getPaint();
        tp.setFakeBoldText(true);
        ShowUtils.showTextPerfect(bfragmentCity, cityName);
        bfragmentCity.setSelected(true);
        TextPaint xtp = bfragmentCity.getPaint();
        xtp.setFakeBoldText(true);
    }

    private void finishSwipe() {
        if (companySwipe != null) {
            if (companySwipe.isEnableRefresh()) {
                companySwipe.finishRefresh(200);
            }
            if (companySwipe.isEnableLoadMore()) {
                companySwipe.finishLoadMore(200);
            }
        }
    }

    @Override
    public void setGridView(View view, int i, List list) {
        final List<SelectCategory> datas = list;
        GridView gridView = (GridView) view.findViewById(R.id.gridView);
        mCompanyDySoreView.setNumColumns(gridView);
        CompanyCellAdapter adapter = new CompanyCellAdapter(mContext, datas);
        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                MobclickAgent.onEvent(mContext, "company_industry", datas.get(position).getName());//统计banner点击次数
                CompanyCellActivity.start(mContext, datas.get(position).getId(), datas.get(position).getName(), cityIdx, cityNamex);
            }
        });

    }
}
