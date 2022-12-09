package com.xinniu.android.qiqueqiao.activity;

import android.os.Bundle;
//import android.support.annotation.NonNull;
//import android.support.v7.widget.LinearLayoutManager;
//import androidx.recyclerview.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.xinniu.android.qiqueqiao.R;
import com.xinniu.android.qiqueqiao.adapter.CompanyFragAdapter;
import com.xinniu.android.qiqueqiao.base.BaseActivity;
import com.xinniu.android.qiqueqiao.bean.CenterBean;
import com.xinniu.android.qiqueqiao.bean.CompanyListsBean;
import com.xinniu.android.qiqueqiao.request.RequestManager;
import com.xinniu.android.qiqueqiao.request.callback.CompanyListCallback;
import com.xinniu.android.qiqueqiao.request.callback.RequestCallback;
import com.xinniu.android.qiqueqiao.user.UserInfoHelper;
//import com.xinniu.android.qiqueqiao.utils.IMUtils;
import com.xinniu.android.qiqueqiao.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import retrofit2.Call;

/**
 * Created by yuchance on 2018/5/30.
 */

public class CompanySearchActivity extends BaseActivity {
    @BindView(R.id.back_title)
    RelativeLayout backTitle;
    @BindView(R.id.bcompany_searchTv)
    TextView bcompanySearchTv;
    @BindView(R.id.mcompany_searchEt)
    EditText mcompanySearchEt;
    @BindView(R.id.mcompany_rl)
    RecyclerView mcompanyRl;
    @BindView(R.id.mrefreshLayout)
    SmartRefreshLayout mrefreshLayout;
    int page = 1;
    @BindView(R.id.yperch_Rl)
    RelativeLayout yperchRl;
    private CompanyFragAdapter adapter;
    private List<CompanyListsBean.ListBean> datas = new ArrayList<>();
    private CenterBean mCenterBean;

    @Override
    public int getLayoutId() {
        return R.layout.activity_company_search;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        topStatusBar(false);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            String data = bundle.getString("data");
            mcompanySearchEt.setText(data);
            mcompanySearchEt.setSelection(data.length());
            page = 1;
            initData(mcompanySearchEt.getText().toString(), page);
        }
        LinearLayoutManager manager = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
        mcompanyRl.setLayoutManager(manager);
        adapter = new CompanyFragAdapter(CompanySearchActivity.this, R.layout.item_company_cell, datas);
        mcompanyRl.setAdapter(adapter);

        mrefreshLayout.setEnableRefresh(false);
        mrefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                page++;
                initData(mcompanySearchEt.getText().toString(), page);
            }
        });
        mcompanySearchEt.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    page = 1;
                    initData(mcompanySearchEt.getText().toString(), page);
                    return true;
                }
                return false;
            }
        });


    }


    @OnClick({R.id.back_title, R.id.bcompany_searchTv, R.id.bnoRec_lx})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back_title:
                finish();
                break;
            case R.id.bcompany_searchTv:
                page = 1;
                initData(mcompanySearchEt.getText().toString(), page);
                break;
            case R.id.bnoRec_lx:
                if (!isLogin()) {
                    return;
                }
                RequestManager.getInstance().center(new RequestCallback<CenterBean>() {
                    @Override
                    public void requestStart(Call call) {

                    }

                    @Override
                    public void onSuccess(CenterBean centerBean) {
                        mCenterBean = centerBean;
                        String content = "专属服务经理你好，我未在企鹊桥搜索到我需要的企业，你这边帮我定向寻找下吧";
                        //IMUtils.singleChat(CompanySearchActivity.this, String.valueOf(mCenterBean.getUsers().getF_id()), "客服", "2", content);
                    }

                    @Override
                    public void onFailed(int code, String msg) {
                        ToastUtils.showCentetImgToast(mContext, msg);
                    }

                    @Override
                    public void requestEnd() {
                    }
                });
                break;
            default:
                break;
        }
    }

    private void initData(String keyWord, final int page) {
        RequestManager.getInstance().searchCompanyList(keyWord, page, new CompanyListCallback() {
            @Override
            public void onSuccess(CompanyListsBean bean) {
                if (page == 1) {
                    datas.clear();
                    if (bean.getList().size() == 0) {
                        yperchRl.setVisibility(View.VISIBLE);
                        adapter.removeAllFooterView();
                    } else {
                        yperchRl.setVisibility(View.GONE);
                        if (bean.getHasMore() == 0) {
                            adapter.setFooterView(footView);
                        } else {
                            adapter.removeAllFooterView();
                        }
                    }
                } else {
                    if (bean.getHasMore() == 0) {
                        adapter.setFooterView(footView);
                    } else {
                        adapter.removeAllFooterView();
                    }
                }
                datas.addAll(bean.getList());
                adapter.notifyDataSetChanged();
                finishSwipe();
            }

            @Override
            public void onFailue(int code, String msg) {
                ToastUtils.showCentetToast(CompanySearchActivity.this, msg);
            }
        });


    }

    private void finishSwipe() {
        if (mrefreshLayout != null) {
            if (mrefreshLayout.isEnableLoadMore()) {
                mrefreshLayout.finishLoadMore(200);
            }
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

}
