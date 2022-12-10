package com.xinniu.android.qiqueqiao.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextPaint;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.umeng.analytics.MobclickAgent;
import com.xinniu.android.qiqueqiao.R;
import com.xinniu.android.qiqueqiao.adapter.CompanyFragAdapter;
import com.xinniu.android.qiqueqiao.base.BaseActivity;
import com.xinniu.android.qiqueqiao.bean.CityV2Bean;
import com.xinniu.android.qiqueqiao.bean.CompanyListsBean;
import com.xinniu.android.qiqueqiao.customs.CityNewWindow;
import com.xinniu.android.qiqueqiao.request.RequestManager;
import com.xinniu.android.qiqueqiao.request.callback.CompanyListCallback;
import com.xinniu.android.qiqueqiao.request.callback.GetAppAreaCallback;
import com.xinniu.android.qiqueqiao.utils.ResouceHelper;
import com.xinniu.android.qiqueqiao.utils.ShowUtils;
import com.xinniu.android.qiqueqiao.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by yuchance on 2018/8/28.
 */

public class CompanyCellActivity extends BaseActivity {
    @BindView(R.id.back_title)
    RelativeLayout backTitle;
    @BindView(R.id.bcompanyCity)
    TextView bcompanyCity;
    @BindView(R.id.mTvTitle)
    TextView mTvTitle;
    @BindView(R.id.bImgSearch)
    ImageView bImgSearch;
    @BindView(R.id.mcompanyRecycler)
    RecyclerView mcompanyRecycler;
    @BindView(R.id.mRefreshLayout)
    SmartRefreshLayout mRefreshLayout;
    @BindView(R.id.company_title)
    RelativeLayout companyTitle;

    private CompanyFragAdapter adapter;
    private int leftSelectCity = 0;


    private List<CompanyListsBean.ListBean> datas = new ArrayList<>();
    private int id;
    private int cityIdx = 0;
    private int page = 1;
    private String cityNamex;


    public static void start(Context context, int id, String title,int cityId,String cityName) {
        Intent intent = new Intent(context, CompanyCellActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt("id", id);
        bundle.putString("title", title);
        bundle.putInt("cityId",cityId);
        bundle.putString("cityName",cityName);
        intent.putExtras(bundle);
        context.startActivity(intent,bundle);

    }


    @Override
    public int getLayoutId() {
        return R.layout.activity_company_cell;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        LinearLayoutManager manager = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
        mcompanyRecycler.setLayoutManager(manager);
        Bundle bundle = getIntent().getExtras();
        id = bundle.getInt("id");
        String title = bundle.getString("title");
        cityIdx = bundle.getInt("cityId");
        cityNamex = bundle.getString("cityName");
        if (cityIdx!=0){
            refreshResouceListByCt(cityNamex);
        }
        mTvTitle.setText(title);
        adapter = new CompanyFragAdapter(mContext, R.layout.item_company_cell, datas);
        mcompanyRecycler.setAdapter(adapter);

        mRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                page++;
                buildData(id,cityIdx,page,false);
            }
        });
        mRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                page = 1;
                buildData(id,cityIdx,page,false);
            }
        });

        buildData(id, cityIdx, page, true);
    }

    private void buildData(int industryId, int cityId, final int page, boolean isShow) {
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
//                        yperchRl.setVisibility(View.VISIBLE);
                        adapter.removeAllFooterView();
                        mRefreshLayout.setEnableLoadMore(false);
                    } else {
//                        yperchRl.setVisibility(View.GONE);
                        if (bean.getHasMore() == 0) {
                            adapter.setFooterView(footView);
                            mRefreshLayout.setEnableLoadMore(false);
                        } else {
                            adapter.removeAllFooterView();
                            mRefreshLayout.setEnableLoadMore(true);
                        }
                    }
                } else {
                    if (bean.getHasMore() == 0) {
                        adapter.setFooterView(footView);
                        mRefreshLayout.setEnableLoadMore(false);
                    } else {
                        adapter.removeAllFooterView();
                        mRefreshLayout.setEnableLoadMore(true);
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

    private void finishSwipe() {
        if (mRefreshLayout != null) {
            if (mRefreshLayout.isEnableRefresh()) {
                mRefreshLayout.finishRefresh(200);
            }
            if (mRefreshLayout.isEnableLoadMore()) {
                mRefreshLayout.finishLoadMore(200);
            }
        }
    }


    @OnClick({R.id.back_title,R.id.bcompanyCity, R.id.bImgSearch})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bcompanyCity:
                if (ResouceHelper.getInstance().getCityV2List() != null) {
                    showCity(ResouceHelper.getInstance().getCityV2List());
                } else {
                    RequestCity();
                }
                break;
            case R.id.bImgSearch:
                gotoSearch_company();
                break;
            case R.id.back_title:
                finish();
                break;
            default:
                break;
        }
    }

    private void gotoSearch_company() {
        MobclickAgent.onEvent(mContext, "company_searchbar");//统计banner点击次数
        Intent intent = new Intent(mContext, CompanySearchActivity.class);
        mContext.startActivity(intent);
    }

    private void showCity(final List<CityV2Bean> leftList) {
        CityNewWindow cityNewWindow = new CityNewWindow(CompanyCellActivity.this, leftList, leftSelectCity);
        if (cityIdx != 0) {
            for (int i = 0; i < leftList.get(leftSelectCity).getZlist().size(); i++) {
                if (leftList.get(leftSelectCity).getZlist().get(i).getId() == cityIdx) {
                    leftList.get(leftSelectCity).getZlist().get(i).setCheck(true);
                }
            }
        }
        cityNewWindow.showAsDropDown(companyTitle);
        cityNewWindow.setSetCityIdAndPostion(new CityNewWindow.getCityIdAndPostion() {
            @Override
            public void getCityIdandPostion(int leftPostion, int cityId, String cityName) {
                leftSelectCity = leftPostion;
                cityIdx = cityId;
                cityNamex = cityName;
                refreshResouceListByCt(cityNamex);
                buildData(id, cityIdx, page, true);
            }
        });

    }
    private void refreshResouceListByCt(String cityName) {
        ShowUtils.showTextPerfect(bcompanyCity, cityName);
        bcompanyCity.setSelected(true);
        TextPaint tp = bcompanyCity.getPaint();
        tp.setFakeBoldText(true);
        ShowUtils.showTextPerfect(bcompanyCity, cityName);
        bcompanyCity.setSelected(true);
        TextPaint xtp = bcompanyCity.getPaint();
        xtp.setFakeBoldText(true);
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

}
