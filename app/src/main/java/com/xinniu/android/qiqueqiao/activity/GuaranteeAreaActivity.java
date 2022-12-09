package com.xinniu.android.qiqueqiao.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
//import android.support.annotation.NonNull;
//import android.support.v7.widget.LinearLayoutManager;
//import androidx.recyclerview.widget.RecyclerView;
import android.text.TextPaint;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.scwang.smartrefresh.layout.util.DensityUtil;
import com.xinniu.android.qiqueqiao.R;
import com.xinniu.android.qiqueqiao.adapter.IndexNewAdapter;
import com.xinniu.android.qiqueqiao.base.BaseActivity;
import com.xinniu.android.qiqueqiao.bean.IndexNewBean;
import com.xinniu.android.qiqueqiao.customs.MyScrollView;
import com.xinniu.android.qiqueqiao.request.RequestManager;
import com.xinniu.android.qiqueqiao.request.callback.GetResourceListCallback;
import com.xinniu.android.qiqueqiao.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class GuaranteeAreaActivity extends BaseActivity {
    @BindView(R.id.mrecyclerType)
    RecyclerView mrecyclerType;
    @BindView(R.id.indexScroll)
    MyScrollView indexScroll;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    @BindView(R.id.tv_all)
    TextView tvAll;
    @BindView(R.id.tv_tg)
    TextView tvTg;
    @BindView(R.id.tv_xg)
    TextView tvXg;
    @BindView(R.id.ll)
    LinearLayout ll;
    @BindView(R.id.tv_all_01)
    TextView tvAll01;
    @BindView(R.id.tv_tg_01)
    TextView tvTg01;
    @BindView(R.id.tv_xg_01)
    TextView tvXg01;
    @BindView(R.id.llayout_top)
    LinearLayout llayoutTop;
    private List<IndexNewBean.ListBean> resourceItems = new ArrayList<>();
    private IndexNewAdapter mainResoureAdapter;
    private int mSearchCityId = 0;
    private String mSearchKeyWord = "";
    private int mSearchSortOrder = 36;
    private int mSearchCategory;
    private String mSearchQueryId = "";
    private String mSearchIndustry = "";

    private int mSearchPage = 1;
    private int mQueryType = 3;
    private int p_id = 0;

    public static void start(Context context) {
        Intent intent = new Intent(context, GuaranteeAreaActivity.class);
        context.startActivity(intent);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_guarantee_area;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        topStatusBar(true);
        TextPaint tp01 = tvAll.getPaint();
        tp01.setFakeBoldText(true);
        tvAll.setTextColor(getResources().getColor(R.color._333));


        TextPaint tp02 = tvAll01.getPaint();
        tp02.setFakeBoldText(true);
        tvAll01.setTextColor(getResources().getColor(R.color._333));

        mainResoureAdapter = new IndexNewAdapter(GuaranteeAreaActivity.this, R.layout.item_index_new, resourceItems, 2, 1);
        LinearLayoutManager manager = new LinearLayoutManager(GuaranteeAreaActivity.this, LinearLayoutManager.VERTICAL, false);
        mrecyclerType.setLayoutManager(manager);
        mrecyclerType.setAdapter(mainResoureAdapter);
        mrecyclerType.setNestedScrollingEnabled(false);
        buildData(mQueryType, mSearchPage, mSearchCityId, mSearchKeyWord, mSearchSortOrder, mSearchCategory, mSearchQueryId, mSearchIndustry, p_id, true);
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                mSearchPage = 1;
                buildData(mQueryType, mSearchPage, mSearchCityId, mSearchKeyWord, mSearchSortOrder, mSearchCategory, mSearchQueryId, mSearchIndustry, p_id, false);
            }
        });
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                mSearchPage++;
                buildData(mQueryType, mSearchPage, mSearchCityId, mSearchKeyWord, mSearchSortOrder, mSearchCategory, mSearchQueryId, mSearchIndustry, p_id, false);
            }
        });

        indexScroll.setListener(new MyScrollView.OnScrollListener() {
            @Override
            public void onScroll(int scrollY) {
                int ps = ll.getTop();
                if (scrollY >= ps) {
                    llayoutTop.setVisibility(View.VISIBLE);
                } else {
                    llayoutTop.setVisibility(View.GONE);

                }

            }
        });
    }

    private void buildData(int mQueryType, final int mSearchPage, int mSearchCityId, String mSearchKeyWord, int mSearchSortOrder, int mSearchCategory, String mSearchQueryId, String mSearchIndustry, int p_id, boolean isShow) {
        if (isShow) {
            showBookingToast(1);
        }
        RequestManager.getInstance().getResourceItem(mSearchPage, mSearchCityId, mSearchKeyWord, "", mSearchSortOrder, mSearchQueryId, mSearchIndustry, p_id, 0, 0, 1, new GetResourceListCallback() {
            @Override
            public void onSuccess(IndexNewBean resultDO) {
                dismissBookingToast();
                finishSwipe();
                if (mSearchPage == 1) {
                    resourceItems.clear();
                    if (resultDO.getList().size() == 0) {
                        mainResoureAdapter.removeAllFooterView();
                        refreshLayout.setEnableLoadMore(false);
                    } else {

                        if (resultDO.getHasMore() == 0) {
                            mainResoureAdapter.setFooterView(footView);
                            refreshLayout.setEnableLoadMore(false);
                        } else {
                            mainResoureAdapter.removeAllFooterView();
                            refreshLayout.setEnableLoadMore(true);
                        }
                    }
                } else {
                    if (resultDO.getHasMore() == 0) {
                        mainResoureAdapter.setFooterView(footView);
                        refreshLayout.setEnableLoadMore(false);
                    } else {
                        mainResoureAdapter.removeAllFooterView();
                        refreshLayout.setEnableLoadMore(true);
                    }
                }
                resourceItems.addAll(resultDO.getList());
                mainResoureAdapter.notifyDataSetChanged();
                if (resourceItems.size() == 0 && mSearchPage == 1) {
                    ToastUtils.showCentetImgToast(mContext, "暂无数据");
                }


            }

            @Override
            public void onFailed(int code, String msg) {
                dismissBookingToast();
                finishSwipe();
                ToastUtils.showCentetToast(GuaranteeAreaActivity.this, msg);
            }
        });

    }


    private void finishSwipe() {
        if (refreshLayout != null) {
            if (refreshLayout.isEnableRefresh()) {
                refreshLayout.finishRefresh(200);
            }
            if (refreshLayout.isEnableLoadMore()) {
                refreshLayout.finishLoadMore(200);
            }
        }
    }

    @OnClick({R.id.bt_finish, R.id.tv_launch_transation, R.id.rlayout_all, R.id.rlayout_tg, R.id.rlayout_xg, R.id.rlayout_all_01, R.id.rlayout_tg_01, R.id.rlayout_xg_01})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_finish:
                finish();
                break;
            case R.id.tv_launch_transation:
                if(!isLoginState()){
                    LoginNewActivity.start(mContext);
                    return;
                }
                LaunchTransactionAvtivity.start(GuaranteeAreaActivity.this, "-1", 1);
                break;
            case R.id.rlayout_all:
                clearAllStyle();
                TextPaint tp01 = tvAll.getPaint();
                tp01.setFakeBoldText(true);
                tvAll.setTextColor(getResources().getColor(R.color._333));

                TextPaint tp02 = tvAll01.getPaint();
                tp02.setFakeBoldText(true);
                tvAll01.setTextColor(getResources().getColor(R.color._333));
                mSearchPage = 1;
                p_id = 0;
                buildData(mQueryType, mSearchPage, mSearchCityId, mSearchKeyWord, mSearchSortOrder, mSearchCategory, mSearchQueryId, mSearchIndustry, p_id, false);

                break;
            case R.id.rlayout_tg:
                clearAllStyle();
                TextPaint tp03 = tvTg.getPaint();
                tp03.setFakeBoldText(true);
                tvTg.setTextColor(getResources().getColor(R.color._333));

                TextPaint tp04 = tvTg01.getPaint();
                tp04.setFakeBoldText(true);
                tvTg01.setTextColor(getResources().getColor(R.color._333));
                mSearchPage = 1;
                p_id = 7;
                buildData(mQueryType, mSearchPage, mSearchCityId, mSearchKeyWord, mSearchSortOrder, mSearchCategory, mSearchQueryId, mSearchIndustry, p_id, false);
                break;
            case R.id.rlayout_xg:
                clearAllStyle();
                TextPaint tp05 = tvXg.getPaint();
                tp05.setFakeBoldText(true);
                tvXg.setTextColor(getResources().getColor(R.color._333));

                TextPaint tp06 = tvXg01.getPaint();
                tp06.setFakeBoldText(true);
                tvXg01.setTextColor(getResources().getColor(R.color._333));
                mSearchPage = 1;
                p_id = 8;
                buildData(mQueryType, mSearchPage, mSearchCityId, mSearchKeyWord, mSearchSortOrder, mSearchCategory, mSearchQueryId, mSearchIndustry, p_id, false);
                break;
            case R.id.rlayout_all_01:
                clearAllStyle();
                TextPaint tp011 = tvAll.getPaint();
                tp011.setFakeBoldText(true);
                tvAll.setTextColor(getResources().getColor(R.color._333));

                TextPaint tp022 = tvAll01.getPaint();
                tp022.setFakeBoldText(true);
                tvAll01.setTextColor(getResources().getColor(R.color._333));
                mSearchPage = 1;
                p_id = 0;
                buildData(mQueryType, mSearchPage, mSearchCityId, mSearchKeyWord, mSearchSortOrder, mSearchCategory, mSearchQueryId, mSearchIndustry, p_id, false);
                int ay = DensityUtil.dp2px(245f);
                indexScroll.smoothScrollTo(0, ay);
                break;
            case R.id.rlayout_tg_01:
                clearAllStyle();
                TextPaint tp033 = tvTg.getPaint();
                tp033.setFakeBoldText(true);
                tvTg.setTextColor(getResources().getColor(R.color._333));

                TextPaint tp044 = tvTg01.getPaint();
                tp044.setFakeBoldText(true);
                tvTg01.setTextColor(getResources().getColor(R.color._333));
                mSearchPage = 1;
                p_id = 7;
                buildData(mQueryType, mSearchPage, mSearchCityId, mSearchKeyWord, mSearchSortOrder, mSearchCategory, mSearchQueryId, mSearchIndustry, p_id, false);
                int ay0 = DensityUtil.dp2px(245f);
                indexScroll.smoothScrollTo(0, ay0);
                break;
            case R.id.rlayout_xg_01:
                clearAllStyle();
                TextPaint tp055 = tvXg.getPaint();
                tp055.setFakeBoldText(true);
                tvXg.setTextColor(getResources().getColor(R.color._333));

                TextPaint tp066 = tvXg01.getPaint();
                tp066.setFakeBoldText(true);
                tvXg01.setTextColor(getResources().getColor(R.color._333));
                mSearchPage = 1;
                p_id = 8;
                buildData(mQueryType, mSearchPage, mSearchCityId, mSearchKeyWord, mSearchSortOrder, mSearchCategory, mSearchQueryId, mSearchIndustry, p_id, false);
                int ay1 = DensityUtil.dp2px(245f);
                indexScroll.smoothScrollTo(0, ay1);
                break;
        }
    }


    /**
     * 清除选中样式
     */
    private void clearAllStyle() {
        TextPaint tp01 = tvAll.getPaint();
        tp01.setFakeBoldText(false);
        tvAll.setTextColor(getResources().getColor(R.color._999999));


        TextPaint tp02 = tvAll01.getPaint();
        tp02.setFakeBoldText(false);
        tvAll01.setTextColor(getResources().getColor(R.color._999999));


        TextPaint tp03 = tvTg.getPaint();
        tp03.setFakeBoldText(false);
        tvTg.setTextColor(getResources().getColor(R.color._999999));


        TextPaint tp04 = tvTg01.getPaint();
        tp04.setFakeBoldText(false);
        tvTg01.setTextColor(getResources().getColor(R.color._999999));


        TextPaint tp05 = tvXg.getPaint();
        tp05.setFakeBoldText(false);
        tvXg.setTextColor(getResources().getColor(R.color._999999));

        TextPaint tp06 = tvXg01.getPaint();
        tp06.setFakeBoldText(false);
        tvXg01.setTextColor(getResources().getColor(R.color._999999));

    }

}
