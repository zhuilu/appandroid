package com.xinniu.android.qiqueqiao.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
//import android.support.v7.widget.GridLayoutManager;
//import android.support.v7.widget.LinearLayoutManager;
//import androidx.recyclerview.widget.RecyclerView;
import android.text.Editable;
import android.text.Html;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.xinniu.android.qiqueqiao.R;
import com.xinniu.android.qiqueqiao.adapter.FuzzySearchAdapter;
import com.xinniu.android.qiqueqiao.adapter.SreachItemAdapter;
import com.xinniu.android.qiqueqiao.base.BaseActivity;
import com.xinniu.android.qiqueqiao.bean.ResourcesTitleBean;
import com.xinniu.android.qiqueqiao.bean.SelectCategoryTwo;
import com.xinniu.android.qiqueqiao.customs.ClearEditText;
import com.xinniu.android.qiqueqiao.request.RequestManager;
import com.xinniu.android.qiqueqiao.request.callback.GetUserCategoryTwoCallback;
import com.xinniu.android.qiqueqiao.request.callback.PreSearchCallback;
import com.xinniu.android.qiqueqiao.user.UserInfoHelper;
import com.xinniu.android.qiqueqiao.utils.ShowUtils;
import com.xinniu.android.qiqueqiao.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by lzq on 2017/12/8.
 * 搜索页面
 */

public class SreachActivity extends BaseActivity implements SreachItemAdapter.OnItemClickListener {

    @BindView(R.id.sreach_content_et)
    ClearEditText sreachContentEt;
    @BindView(R.id.delete_tag)
    ImageView deleteTag;
    @BindView(R.id.last_search_tv)
    FrameLayout lastSearchTv;
    @BindView(R.id.last_search_div)
    View lastSearchDiv;
    @BindView(R.id.rv_last)
    RecyclerView rvLast;
    @BindView(R.id.rv_hot)
    RecyclerView rvHot;
    @BindView(R.id.rv_hot_company)
    RecyclerView rvHotCompany;
    @BindView(R.id.history_ll)
    LinearLayout historyLL;
    @BindView(R.id.tv_title_search)
    TextView tvTitleSearch;
    @BindView(R.id.img)
    ImageView img;
    @BindView(R.id.tv_num)
    TextView tvNum;
    @BindView(R.id.rlayout_one)
    RelativeLayout rlayoutOne;
    @BindView(R.id.tv_title_search_two)
    TextView tvTitleSearchTwo;
    @BindView(R.id.img_01)
    ImageView img01;
    @BindView(R.id.rlayout_two)
    RelativeLayout rlayoutTwo;
    @BindView(R.id.result_search)
    RecyclerView resultSearch;
    @BindView(R.id.ll_search)
    LinearLayout llSearch;


    private FuzzySearchAdapter mFuzzySearchAdapter;
    private List<String> hotComapnyList = new ArrayList<>();
    private SreachItemAdapter hotCompanyAdapter;
    private List<String> hotList = new ArrayList<>();
    private SreachItemAdapter hotAdapter;
    private List<String> lastList = UserInfoHelper.getIntance().getSearchHistory();
    private SreachItemAdapter lastAdapter;

    private List<String> fuzzySearchList = new ArrayList<>();
    private boolean isPre = true;//是否进行预处理处理

    public static void start(Context mContext) {
        Intent intent = new Intent(mContext, SreachActivity.class);
        mContext.startActivity(intent);

    }


    @Override
    public int getLayoutId() {
        return R.layout.activity_search;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        topStatusBar(true);
        getHotSeacrhs();
        refreshLastSearch();
        lastAdapter = new SreachItemAdapter(this, lastList, "1");
        lastAdapter.setOnItemClickListener(this);
        hotAdapter = new SreachItemAdapter(this, hotList, "2");
        hotAdapter.setOnItemClickListener(this);
        hotCompanyAdapter = new SreachItemAdapter(this, hotComapnyList, "3");
        hotCompanyAdapter.setOnItemClickListener(this);

        rvLast.setLayoutManager(new GridLayoutManager(this, 4));
        rvLast.setAdapter(lastAdapter);
        rvHot.setLayoutManager(new GridLayoutManager(this, 4));
        rvHot.setAdapter(hotAdapter);
        rvHotCompany.setLayoutManager(new GridLayoutManager(this, 4));
        rvHotCompany.setAdapter(hotCompanyAdapter);


        //模糊搜索显示列表
        LinearLayoutManager manager = new LinearLayoutManager(SreachActivity.this, LinearLayoutManager.VERTICAL, false);
        resultSearch.setLayoutManager(manager);
        mFuzzySearchAdapter = new FuzzySearchAdapter(R.layout.item_fuzzy_search, fuzzySearchList);
        resultSearch.setAdapter(mFuzzySearchAdapter);
        mFuzzySearchAdapter.setOnclick(new FuzzySearchAdapter.Onclick() {
            @Override
            public void click(String name, int position) {
                ShowUtils.showViewVisible(llSearch, View.GONE);
                ShowUtils.showViewVisible(historyLL, View.GONE);
                fuzzySearchList.clear();
                mFuzzySearchAdapter.notifyDataSetChanged();

                //跳转搜索结果页
                SreachListActivity.start(SreachActivity.this,name,0,0, "");
                finish();


            }
        });


        ShowUtils.showViewVisible(llSearch, View.GONE);
        ShowUtils.showViewVisible(historyLL, View.VISIBLE);

        sreachContentEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(final Editable s) {

                ShowUtils.showViewVisible(llSearch, View.GONE);
                if (s.length() == 0) {
                    isPre = true;
                    historyLL.setVisibility(View.VISIBLE);
                } else {
                    historyLL.setVisibility(View.GONE);
                    if (isPre) {
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                //延迟0.5秒执行网络请求

                                preSearch(s.toString());
                            }
                        }, 500);
                    }
                }


            }
        });
        sreachContentEt.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    final String tvName = sreachContentEt.getText().toString();
                    if (TextUtils.isEmpty(tvName)) {
                        ToastUtils.showCentetImgToast(SreachActivity.this, "请输入搜索内容");
                    } else {
                        //跳转搜索结果页
                        SreachListActivity.start(SreachActivity.this,tvName,0,0, "");
                        finish();
                        return true;
                    }

                }
                return false;
            }
        });


    }

    private void preSearch(final String s) {

        RequestManager.getInstance().preSeacrhs(s, new PreSearchCallback() {
            @Override
            public void onSuccess(ResourcesTitleBean list) {
                String str1 = "搜索“<font color='#4B96F3'>" + s + "</font>”相关的公司";
                tvNum.setText(list.getCorporate_count() + "条结果");
                tvTitleSearch.setText(Html.fromHtml(str1));
                String str2 = "搜索“<font color='#4B96F3'>" + s + "</font>”相关的合作信息";
                tvTitleSearchTwo.setText(Html.fromHtml(str2));

                fuzzySearchList.clear();
                for (ResourcesTitleBean.ResourcesTitleListBean item : list.getResources_title_list()) {
                    fuzzySearchList.add(item.getTitle());
                }
                mFuzzySearchAdapter.notifyDataSetChanged();
                llSearch.setVisibility(View.VISIBLE);
            }

            @Override
            public void onFailed(int code, String msg) {

            }
        });
    }



    @OnClick({R.id.srearch_tv, R.id.bt_return, R.id.delete_tag, R.id.rlayout_one, R.id.rlayout_two})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.srearch_tv:
                final String tvName = sreachContentEt.getText().toString();
                if (TextUtils.isEmpty(tvName)) {
                    ToastUtils.showCentetImgToast(SreachActivity.this, "请输入搜索内容");
                    return;
                }

               //跳转搜索结果页
                SreachListActivity.start(SreachActivity.this,tvName,0,0, "");
                finish();
                break;
            case R.id.bt_return:
                finish();
                break;
            case R.id.delete_tag:
                lastList.clear();
                UserInfoHelper.getIntance().clearHistory();
                refreshLastSearch();
                break;

            case R.id.rlayout_one:
                //热门企业，走企业搜索
                Bundle bundle = new Bundle();
                bundle.putString("data", sreachContentEt.getText().toString());
                Intent intent = new Intent(SreachActivity.this, CompanySearchActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
                finish();
                break;
            case R.id.rlayout_two:
              //跳搜索结果页面
                SreachListActivity.start(SreachActivity.this,sreachContentEt.getText().toString(),0,0, "");
                finish();
                break;
            default:
                break;
        }
    }


    @Override
    protected void onDestroy() {
        UserInfoHelper.getIntance().setSearcHistory(lastList);
        super.onDestroy();
    }

    @Override
    public void onItemClik(String item, String type) {
        if (type.equals("3")) {
            //热门企业，走企业搜索
            Bundle bundle = new Bundle();
            bundle.putString("data", item);
            Intent intent = new Intent(SreachActivity.this, CompanySearchActivity.class);
            intent.putExtras(bundle);
            startActivity(intent);
            finish();//直接返回首页
        } else {
            isPre = false;
           //跳结果页
            SreachListActivity.start(SreachActivity.this,item,0,0, "");
            finish();
        }
    }

    private void getHotSeacrhs() {
        RequestManager.getInstance().getHotSeacrhs(new GetUserCategoryTwoCallback() {
            @Override
            public void onSuccess(SelectCategoryTwo list) {
                hotList.clear();
                hotComapnyList.clear();
                if (list.getHot_search().size() > 0) {
                    for (SelectCategoryTwo.HotSearchBean item : list.getHot_search()) {
                        hotList.add(item.getName());

                    }
                }
                if (list.getHot_corporate().size() > 0) {
                    for (SelectCategoryTwo.HotCorporateBean item : list.getHot_corporate()) {

                        hotComapnyList.add(item.getName());
                    }
                }
                hotAdapter.notifyDataSetChanged();
                hotCompanyAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailed(int code, String msg) {

            }
        });
    }

    private void refreshLastSearch() {
        if (lastList.size() > 0) {
            showLastSearch();
        } else {
            hideLastSearch();
        }
    }

    private void hideLastSearch() {
        lastSearchTv.setVisibility(View.GONE);
        lastSearchDiv.setVisibility(View.GONE);
        rvLast.setVisibility(View.GONE);
    }

    private void showLastSearch() {
        lastSearchTv.setVisibility(View.VISIBLE);
        lastSearchDiv.setVisibility(View.VISIBLE);
        rvLast.setVisibility(View.VISIBLE);
    }






}
