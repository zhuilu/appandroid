package com.xinniu.android.qiqueqiao.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
//import android.support.annotation.NonNull;
//import android.support.v7.widget.LinearLayoutManager;
//import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.xinniu.android.qiqueqiao.R;
import com.xinniu.android.qiqueqiao.adapter.MyBillAdapter;
import com.xinniu.android.qiqueqiao.base.BaseActivity;
import com.xinniu.android.qiqueqiao.bean.BillBean;
import com.xinniu.android.qiqueqiao.request.RequestManager;
import com.xinniu.android.qiqueqiao.request.callback.GetMyBillListCallback;
import com.xinniu.android.qiqueqiao.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class MyBillActivity extends BaseActivity {
    @BindView(R.id.yperch_Rl)
    RelativeLayout yperchRl;
    @BindView(R.id.rv)
    RecyclerView rv;
    @BindView(R.id.system_smartrefresh)
    SmartRefreshLayout systemSmartrefresh;
    private List<BillBean.ListBean> datas = new ArrayList<>();
    private MyBillAdapter myBillAdapter;
    private int page = 1;
    public static void start(Context context) {
        Intent intent = new Intent(context, MyBillActivity.class);
        context.startActivity(intent);
    }
    @Override
    public int getLayoutId() {
        return R.layout.activity_my_bill;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        topStatusBar(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
        rv.setLayoutManager(linearLayoutManager);
        myBillAdapter = new MyBillAdapter(MyBillActivity.this, R.layout.item_bill, datas);
        rv.setAdapter(myBillAdapter);
        systemSmartrefresh.setOnLoadMoreListener(new OnLoadMoreListener() {
                                                     @Override
                                                     public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                                                         page++;
                                                         buildData(page);
                                                     }
                                                 }

        );
        buildData(page);
    }

    private void buildData(final int page) {
        systemSmartrefresh.setEnableRefresh(false);
        RequestManager.getInstance().getBillList(page, new GetMyBillListCallback() {

            @Override
            public void onSuccess(BillBean item) {
                if (page == 1) {
                    datas.clear();
                }
                datas.addAll(item.getList());
                myBillAdapter.notifyDataSetChanged();
                stopRefresh();
                if (item.getList() != null) {
                    if (page == 1) {
                        if (item.getList().size() == 0) {
                            yperchRl.setVisibility(View.VISIBLE);
                            myBillAdapter.removeAllFooterView();
                            systemSmartrefresh.setEnableLoadMore(false);
                        } else {
                            yperchRl.setVisibility(View.GONE);
                            if (item.getHasMore() == 0) {
                                myBillAdapter.setFooterView(footView1);
                                systemSmartrefresh.setEnableLoadMore(false);
                            } else {
                                myBillAdapter.removeAllFooterView();
                                systemSmartrefresh.setEnableLoadMore(true);
                            }
                        }

                    } else {
                        if (item.getHasMore() == 0) {
                            myBillAdapter.setFooterView(footView1);
                            systemSmartrefresh.setEnableLoadMore(false);
                        } else {
                            myBillAdapter.removeAllFooterView();
                            systemSmartrefresh.setEnableLoadMore(true);
                        }
                    }
                } else {
                    yperchRl.setVisibility(View.VISIBLE);
                }

            }

            @Override
            public void onFailed(int code, String msg) {
                ToastUtils.showCentetToast(MyBillActivity.this,msg);

            }
        });
    }

    @OnClick(R.id.bt_finish)
    public void onClick() {
        finish();
    }
    private void stopRefresh() {
        if (systemSmartrefresh.isEnableLoadMore()) {
            systemSmartrefresh.finishLoadMore(true);
        }
    }

}
