package com.xinniu.android.qiqueqiao.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextPaint;
import android.text.TextUtils;
import android.view.View;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.umeng.analytics.MobclickAgent;
import com.xinniu.android.qiqueqiao.R;
import com.xinniu.android.qiqueqiao.adapter.CellTagsAdapter;
import com.xinniu.android.qiqueqiao.adapter.CellTagxAdapter;
import com.xinniu.android.qiqueqiao.adapter.IndexNewAdapter;
import com.xinniu.android.qiqueqiao.base.BaseActivity;
import com.xinniu.android.qiqueqiao.bean.CellTagsBean;
import com.xinniu.android.qiqueqiao.bean.CityV2Bean;
import com.xinniu.android.qiqueqiao.bean.IndexNewBean;
import com.xinniu.android.qiqueqiao.bean.ReleaseCheckBean;
import com.xinniu.android.qiqueqiao.bean.SourceScreenBean;
import com.xinniu.android.qiqueqiao.customs.CellScreenWindow;
import com.xinniu.android.qiqueqiao.customs.CityNewWindow;
import com.xinniu.android.qiqueqiao.customs.ScreenTypeWindow;
import com.xinniu.android.qiqueqiao.customs.SortWindow;
import com.xinniu.android.qiqueqiao.customs.qldialog.QLTipDialog;
import com.xinniu.android.qiqueqiao.customs.qldialog.QLTipTwoDialog;
import com.xinniu.android.qiqueqiao.request.RequestManager;
import com.xinniu.android.qiqueqiao.request.callback.CommonCallback;
import com.xinniu.android.qiqueqiao.request.callback.GetAppAreaCallback;
import com.xinniu.android.qiqueqiao.request.callback.GetReleaseCheckCallback;
import com.xinniu.android.qiqueqiao.request.callback.GetResourceListCallback;
import com.xinniu.android.qiqueqiao.request.callback.GetTagsCallback;
import com.xinniu.android.qiqueqiao.request.callback.SourceScreenCallback;
import com.xinniu.android.qiqueqiao.request.converter.ResultDO;
import com.xinniu.android.qiqueqiao.user.UserInfoHelper;
import com.xinniu.android.qiqueqiao.utils.ResouceHelper;
import com.xinniu.android.qiqueqiao.utils.ShowUtils;
import com.xinniu.android.qiqueqiao.utils.StatusBarUtil;
import com.xinniu.android.qiqueqiao.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 首页-分类详情
 * Created by yuchance on 2018/6/25.
 */

public class IndexCellActivity extends BaseActivity implements CellTagsAdapter.setTags, CellTagxAdapter.setTagx {
    @BindView(R.id.bimgBack)
    RelativeLayout bimgBack;
    @BindView(R.id.mtvIndexTitle)
    TextView mtvIndexTitle;
    @BindView(R.id.btvPlace)
    TextView btvPlace;
    @BindView(R.id.downRl)
    RelativeLayout downRl;
    @BindView(R.id.recyclerIndexCell)
    RecyclerView recyclerIndexCell;
    @BindView(R.id.refreshIndexCell)
    SmartRefreshLayout refreshIndexCell;
    @BindView(R.id.mrecyclerSth)
    RecyclerView mrecyclerSth;
    @BindView(R.id.rlIndexCell)
    RelativeLayout rlIndexCell;
    @BindView(R.id.yperch_Rl)
    RelativeLayout yperchRl;
    @BindView(R.id.tvOrder)
    TextView tvOrder;
    @BindView(R.id.index_titleRl)
    RelativeLayout indexTitleRl;
    @BindView(R.id.btvSort)
    TextView btvSort;
    @BindView(R.id.sreach_content_tv)
    TextView sreachContentEt;
    @BindView(R.id.tv_hy)
    TextView tvHy;
    @BindView(R.id.tv_fb)
    TextView tvFb;
    private int mSearchCityId = 0;
    private int mSearchSortOrder = 36;//36刷新时间排序，37发布时间排序
    private int mSearchCategory;
    private String mSearchQueryId;
    private String mSearchIndustry;
    private int mSearchPage = 1;
    private IndexNewAdapter adapter;
    private List<IndexNewBean.ListBean> resourceItems = new ArrayList<>();
    private int id;
    private String theTitle;
    private List<CellTagsBean.ProvideCategoryBean.ListBean> tags = new ArrayList<>();
    private List<CellTagsBean.NeedCategoryBean.ListBeanX> tagx = new ArrayList<>();
    private final static int THECITYCODE = 1011;
    private View footView;
    private CityNewWindow cityNewWindow;
    private int leftSelectCity;


    private CellScreenWindow screenWindow;
    private CellTagsAdapter cellAdapter;
    private CellTagxAdapter cellTagxAdapter;
    private CellTagsBean cellTagsBean = new CellTagsBean();


    public static void start(Context mContext, int theId, String theTitle) {
        Intent intent = new Intent(mContext, IndexCellActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt("id", theId);
        bundle.putString("title", theTitle);
        intent.putExtras(bundle);
        mContext.startActivity(intent, bundle);
    }


    @Override
    public int getLayoutId() {
        return R.layout.activity_index_cell;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        StatusBarUtil.StatusBarLightMode(this, true);
        Bundle bundle = getIntent().getExtras();
        id = bundle.getInt("id");
        theTitle = bundle.getString("title");
        ShowUtils.showTextPerfect(mtvIndexTitle, theTitle);
        LinearLayoutManager manager = new LinearLayoutManager(IndexCellActivity.this, LinearLayoutManager.HORIZONTAL, false);
        mrecyclerSth.setLayoutManager(manager);
        cellAdapter = new CellTagsAdapter(R.layout.item_cell_tags, tags, tagx);
        cellTagxAdapter = new CellTagxAdapter(R.layout.item_cell_tags, tagx, tags);
        cellAdapter.setSetTags(IndexCellActivity.this);
        cellTagxAdapter.setSetTagx(IndexCellActivity.this);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
        recyclerIndexCell.setLayoutManager(linearLayoutManager);
        adapter = new IndexNewAdapter(IndexCellActivity.this, R.layout.item_index_new, resourceItems, 2,0);
        recyclerIndexCell.setAdapter(adapter);

        footView = getLayoutInflater().inflate(R.layout.view_unload_more, null);


        buildData(mSearchPage, mSearchCityId, mSearchSortOrder, mSearchCategory, mSearchQueryId, mSearchIndustry, id, 1);
        refreshIndexCell.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                mSearchPage = 1;
                buildData(mSearchPage, mSearchCityId, mSearchSortOrder, mSearchCategory, mSearchQueryId, mSearchIndustry, id, 0);
            }
        });
        refreshIndexCell.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                mSearchPage++;
                buildData(mSearchPage, mSearchCityId, mSearchSortOrder, mSearchCategory, mSearchQueryId, mSearchIndustry, id, 0);
            }
        });
    }

    private void buildTags() {
        RequestManager.getInstance().getTagsV3(id, new GetTagsCallback() {
            @Override
            public void onSuccess(CellTagsBean bean) {
                rlIndexCell.setVisibility(View.VISIBLE);
                tags.clear();
                tagx.clear();
                //取提供的第一个分类下的标签列表
                if (bean.getProvide_category().size() > 0) {
                    for (int i = 0; i < bean.getProvide_category().size(); i++) {
                        CellTagsBean.ProvideCategoryBean.ListBean provideCategoryBean = new CellTagsBean.ProvideCategoryBean.ListBean();
                        provideCategoryBean.setId(-1);
                        provideCategoryBean.setName("其他");
                        provideCategoryBean.setCheck(false);
                        bean.getProvide_category().get(i).getList().add(provideCategoryBean);
                    }
                }

                if (bean.getNeed_category().size() > 0) {
                    for (int i = 0; i < bean.getNeed_category().size(); i++) {
                        CellTagsBean.NeedCategoryBean.ListBeanX needCategoryBean = new CellTagsBean.NeedCategoryBean.ListBeanX();
                        needCategoryBean.setId(-2);
                        needCategoryBean.setName("其他");
                        needCategoryBean.setCheck(false);
                        bean.getNeed_category().get(i).getList().add(needCategoryBean);
                    }
                }
                cellTagsBean = bean;
                if (bean.getProvide_category() != null && bean.getProvide_category().size() > 0) {
                    tags.addAll(bean.getProvide_category().get(0).getList());

                    mrecyclerSth.setAdapter(cellAdapter);
                } else {
                    if (bean.getNeed_category() != null && bean.getNeed_category().size() > 0) {
                        tagx.addAll(bean.getNeed_category().get(0).getList());
                        mrecyclerSth.setAdapter(cellTagxAdapter);
                    }
                }
                cellTagxAdapter.notifyDataSetChanged();
                cellAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailed(int code, String msg) {
                //   ToastUtils.showCentetToast(IndexCellActivity.this, msg);
                rlIndexCell.setVisibility(View.GONE);
            }
        });
    }


    @OnClick({R.id.bimgBack, R.id.btvPlace, R.id.downRl, R.id.tvOrder, R.id.img_release, R.id.btvSort,R.id.tv_hy, R.id.tv_fb,R.id.sreach_content_tv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bimgBack:
                finish();
                break;
            //地区
            case R.id.btvPlace:
                if (ResouceHelper.getInstance().getCityV2List() != null) {
                    showCity(ResouceHelper.getInstance().getCityV2List());
                } else {
                    RequestCity();
                }
                break;
            case R.id.tvOrder:
                if (ResouceHelper.getSourceScreenBean() != null) {
                    showScreenPop(ResouceHelper.getSourceScreenBean());
                } else {
                    buildScreen();

                }
                break;
            case R.id.downRl:
                showTypeScreen(cellTagsBean);
                break;
            case R.id.img_release:
                //发布需求
                if (!UserInfoHelper.getIntance().isLogin()) {
                    LoginNewActivity.start(IndexCellActivity.this);
                    return;
                }
                toReleaseActivity();

                break;
            case R.id.btvSort:
                //排序
                showSort();
                break;
            case R.id.tv_hy:
                mSearchSortOrder = 36;
                tvHy.setTextColor(getResources().getColor(R.color._333));
                tvFb.setTextColor(getResources().getColor(R.color._999));
                TextPaint tp = tvHy.getPaint();
                tp.setFakeBoldText(true);
                TextPaint tp1 = tvFb.getPaint();
                tp1.setFakeBoldText(false);

                mSearchPage = 1;
                recyclerIndexCell.scrollToPosition(0);
                buildData(mSearchPage, mSearchCityId, mSearchSortOrder, mSearchCategory, mSearchQueryId, mSearchIndustry, id, 2);
                break;
            case R.id.tv_fb:
                mSearchSortOrder = 37;
                tvHy.setTextColor(getResources().getColor(R.color._999));
                tvFb.setTextColor(getResources().getColor(R.color._333));
                TextPaint tp2 = tvHy.getPaint();
                tp2.setFakeBoldText(false);
                TextPaint tp3 = tvFb.getPaint();
                tp3.setFakeBoldText(true);
                mSearchPage = 1;
                recyclerIndexCell.scrollToPosition(0);
                buildData(mSearchPage, mSearchCityId, mSearchSortOrder, mSearchCategory, mSearchQueryId, mSearchIndustry, id, 2);
                break;
            case R.id.sreach_content_tv:
                SreachListActivity.start(IndexCellActivity.this,"",1,id,theTitle);


                break;
            default:

                break;
        }
    }

    private void showSort() {
        final SortWindow sortWindow = new SortWindow(IndexCellActivity.this, mSearchSortOrder);
        sortWindow.showAsDropDown(indexTitleRl);
        sortWindow.setFinish(new SortWindow.finish() {
            @Override
            public void setFinish(int mid, String name) {
                mSearchSortOrder = mid;
                if (name.length() == 0) {
                    name = "排序";
                    ShowUtils.showTextPerfect(btvSort, name);
                    btvSort.setSelected(false);
                } else {
                    ShowUtils.showTextPerfect(btvSort, name);
                    btvSort.setSelected(true);
//                    TextPaint tp = btvSort.getPaint();
//                    tp.setFakeBoldText(true);
//                    ShowUtils.showTextPerfect(btvSort, name);
//                    btvSort.setSelected(true);
//                    TextPaint xtp = btvSort.getPaint();
//                    xtp.setFakeBoldText(true);
                }
                mSearchPage = 1;
                recyclerIndexCell.scrollToPosition(0);
                buildData(mSearchPage, mSearchCityId, mSearchSortOrder, mSearchCategory, mSearchQueryId, mSearchIndustry, id, 2);
                sortWindow.dismiss();
            }
        });
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
//                PublishSelectTypeActivity.start(IndexCellActivity.this);
                check(theTitle, id);
            }

            @Override
            public void onFailed(int code, final String msg) {
                dismissBookingToast();
                if (code == 202) {
                    new QLTipDialog.Builder(IndexCellActivity.this)
                            .setCancelable(true)
                            .setCancelableOnTouchOutside(true)
                            .setMessage(msg)
                            .setNegativeButton("我知道了", new QLTipDialog.INegativeCallback() {
                                @Override
                                public void onClick() {

                                }
                            })
                            .show(IndexCellActivity.this);
                } else if (code == 305) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(IndexCellActivity.this);
                    builder.setMessage(msg);
                    builder.setPositiveButton("去完善", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
//                            ToastUtils.showCentetImgToast(MainActivity.this, msg);
                            Intent intent = new Intent(IndexCellActivity.this, CompanyEditActivity.class);

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
                    new QLTipDialog.Builder(IndexCellActivity.this)
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
                            CertificationActivity.start(IndexCellActivity.this, 1);
                        }
                    })
                            .show(IndexCellActivity.this);
                }
            }
        });
    }

    private void check(final String title, final int typeId) {
        RequestManager.getInstance().releaseCheck(typeId, new GetReleaseCheckCallback() {
            @Override
            public void onSuccess() {
                PublishNewActivity.start(IndexCellActivity.this, theTitle, id, 1001);
            }

            @Override
            public void onFailed(int code, String msg, String getConfigBean) {
                if (code == 311) {
                    Gson gson = new Gson();
                    ReleaseCheckBean bean = gson.fromJson(getConfigBean, ReleaseCheckBean.class);
                    new QLTipTwoDialog.Builder(IndexCellActivity.this)
                            .setCancelable(true)
                            .setCancelableOnTouchOutside(true)
                            .setTitle(bean.getData().getPrompt_msg())
                            .setMessage(bean.getData().getSolve_prompt())
                            .setNegativeButton("取消", new QLTipTwoDialog.INegativeCallback() {
                                @Override
                                public void onClick() {

                                }
                            }).setPositiveButton("查看我的发布", new QLTipTwoDialog.IPositiveCallback() {
                        @Override
                        public void onClick() {
                            //接单
                            MyPushActivity.start(mContext);
                        }
                    })
                            .show(IndexCellActivity.this);

                } else {
                    ToastUtils.showCentetToast(IndexCellActivity.this, msg);
                }

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

    private void showCity(List<CityV2Bean> leftList) {
        cityNewWindow = new CityNewWindow(IndexCellActivity.this, leftList, leftSelectCity);
        if (mSearchCityId != 0) {
            for (int i = 0; i < leftList.get(leftSelectCity).getZlist().size(); i++) {
                if (leftList.get(leftSelectCity).getZlist().get(i).getId() == mSearchCityId) {
                    leftList.get(leftSelectCity).getZlist().get(i).setCheck(true);
                }
            }
        }
        cityNewWindow.showAsDropDown(indexTitleRl);
        cityNewWindow.setSetCityIdAndPostion(new CityNewWindow.getCityIdAndPostion() {
            @Override
            public void getCityIdandPostion(int leftPostion, int cityId, String cityName) {
                leftSelectCity = leftPostion;
                mSearchCityId = cityId;
                refreshResouceListByCt(cityName);
                mSearchPage = 1;
                buildData(mSearchPage, mSearchCityId, mSearchSortOrder, mSearchCategory, mSearchQueryId, mSearchIndustry, id, 2);

            }
        });
    }

    private void refreshResouceListByCt(String cityName) {
        ShowUtils.showTextPerfect(btvPlace, cityName);
        btvPlace.setSelected(true);
//        TextPaint tp = btvPlace.getPaint();
//        tp.setFakeBoldText(true);
//        ShowUtils.showTextPerfect(btvPlace, cityName);
//        btvPlace.setSelected(true);
//        TextPaint xtp = btvPlace.getPaint();
//        xtp.setFakeBoldText(true);
    }


    private void buildData(final int mSearchPage, int mSearchCityId, int mSearchSortOrder, int mSearchCategory, String mSearchQueryId, String mSearchIndustry, int p_id, final int isShow) {
        if (isShow == 1) {
            showBookingToast(isShow);
        } else if (isShow == 2) {
            showBookingToast(isShow);
        } else {

        }

        RequestManager.getInstance().getResourceItem(mSearchPage, mSearchCityId, null, "", mSearchSortOrder, mSearchQueryId, mSearchIndustry, p_id, 0, 0,0, new GetResourceListCallback() {
            @Override
            public void onSuccess(IndexNewBean resultDO) {
                dismissBookingToast();
                if (isShow == 1) {
                    //第一次执行
                    buildTags();
                }
                finishSwipe();
                if (mSearchPage == 1) {
                    resourceItems.clear();
                    if (resultDO.getList().size() == 0) {
                        yperchRl.setVisibility(View.VISIBLE);
                        adapter.removeAllFooterView();
                        refreshIndexCell.setEnableLoadMore(false);
                    } else {
                        yperchRl.setVisibility(View.GONE);
                        if (resultDO.getHasMore() == 0) {
                            adapter.setFooterView(footView);
                            refreshIndexCell.setEnableLoadMore(false);
                        } else {
                            adapter.removeAllFooterView();
                            refreshIndexCell.setEnableLoadMore(true);
                        }
                    }
                } else {
                    if (resultDO.getHasMore() == 0) {
                        adapter.setFooterView(footView);
                        refreshIndexCell.setEnableLoadMore(false);
                    } else {
                        adapter.removeAllFooterView();
                        refreshIndexCell.setEnableLoadMore(true);
                    }
                }
                resourceItems.addAll(resultDO.getList());

                if (resourceItems.size() > 15 && !resourceItems.get(15).isSearchPerch()) {
                    resourceItems.add(15, new IndexNewBean.ListBean(0, "", 0, "", "", "", "", "", "", 0, 0, 0, 0, 0, "", "", true));
                }
                adapter.notifyDataSetChanged();

            }

            @Override
            public void onFailed(int code, String msg) {
                dismissBookingToast();
                buildTags();
                finishSwipe();
                ToastUtils.showCentetToast(IndexCellActivity.this, msg);
            }
        });

    }

    @Override
    public void setTags(String xid) {
        mSearchQueryId = xid;
        mSearchPage = 1;
        buildData(mSearchPage, mSearchCityId, mSearchSortOrder, mSearchCategory, mSearchQueryId, mSearchIndustry, id, 2);
    }

    public void setTagx(String xid) {
        mSearchQueryId = xid;
        mSearchPage = 1;
        buildData(mSearchPage, mSearchCityId, mSearchSortOrder, mSearchCategory, mSearchQueryId, mSearchIndustry, id, 2);

    }


    private void showTypeScreen(CellTagsBean data) {
        ScreenTypeWindow screenTypeWindow = new ScreenTypeWindow(IndexCellActivity.this, data);
        screenTypeWindow.showAsDropDown(rlIndexCell);
        screenTypeWindow.setmSetfinish(new ScreenTypeWindow.setfinish() {
            @Override
            public void setToFinish(String mId) {
                if (TextUtils.isEmpty(mId)) {
                    mSearchQueryId = mId;
                    buildData(mSearchPage, mSearchCityId, mSearchSortOrder, mSearchCategory, mSearchQueryId, mSearchIndustry, id, 2);

                } else {
                    mSearchQueryId = mId;
                    buildData(mSearchPage, mSearchCityId, mSearchSortOrder, mSearchCategory, mSearchQueryId, mSearchIndustry, id, 2);
                }
                cellAdapter.notifyDataSetChanged();
                cellTagxAdapter.notifyDataSetChanged();
            }
        });

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == MyPushActivity.REQUESTCODE && resultCode == CoopDetailActivity.REQUESTREFRESH) {
            mSearchPage = 1;
            buildData(mSearchPage, mSearchCityId, mSearchSortOrder, mSearchCategory, mSearchQueryId, mSearchIndustry, id, 2);
        }

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

        screenWindow = new CellScreenWindow(IndexCellActivity.this, screenBean);
        screenWindow.showAsDropDown(indexTitleRl);
        screenWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                screenWindow.onDismiss();
                if (TextUtils.isEmpty(mSearchIndustry)) {
                    tvOrder.setSelected(false);
                } else {
                    tvOrder.setSelected(true);
                }
            }
        });
        screenWindow.setFinish(new CellScreenWindow.finish() {
            @Override
            public void setFinish(String SearchIndustry, String name) {
                MobclickAgent.onEvent(mContext, "home_sourceIndustry", name);
                mSearchIndustry = SearchIndustry;
                if (name.length() == 0) {
                    name = "行业";
                    ShowUtils.showTextPerfect(tvOrder, name);
                } else {
                    ShowUtils.showTextPerfect(tvOrder, name);
                    tvOrder.setSelected(true);
//                    TextPaint tp = tvOrder.getPaint();
//                    tp.setFakeBoldText(true);
//                    ShowUtils.showTextPerfect(tvOrder, name);
//                    tvOrder.setSelected(true);
//                    TextPaint xtp = tvOrder.getPaint();
//                    xtp.setFakeBoldText(true);
                }
                mSearchPage = 1;
                recyclerIndexCell.scrollToPosition(0);
                buildData(mSearchPage, mSearchCityId, mSearchSortOrder, mSearchCategory, mSearchQueryId, mSearchIndustry, id, 2);
                screenWindow.dismissPopup();
            }
        });

    }

}
