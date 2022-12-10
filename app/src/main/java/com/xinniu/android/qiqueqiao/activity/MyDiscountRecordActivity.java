package com.xinniu.android.qiqueqiao.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.xinniu.android.qiqueqiao.R;
import com.xinniu.android.qiqueqiao.adapter.MyDiscountRecordAdapter;
import com.xinniu.android.qiqueqiao.base.BaseActivity;
import com.xinniu.android.qiqueqiao.bean.CashWithdrawalBean;
import com.xinniu.android.qiqueqiao.request.RequestManager;
import com.xinniu.android.qiqueqiao.request.callback.GetCashWithdrawalCallback;
import com.xinniu.android.qiqueqiao.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class MyDiscountRecordActivity extends BaseActivity {
    @BindView(R.id.yperch_Rl)
    RelativeLayout yperchRl;
    @BindView(R.id.rv)
    RecyclerView rv;
    @BindView(R.id.system_smartrefresh)
    SmartRefreshLayout systemSmartrefresh;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_empty)
    TextView tvEmpty;
    private List<CashWithdrawalBean.ListBean> datas = new ArrayList<>();
    private MyDiscountRecordAdapter myBillAdapter;
    private int page = 1;

    public static void start(Context context) {
        Intent intent = new Intent(context, MyDiscountRecordActivity.class);
        context.startActivity(intent);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_my_bill;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        topStatusBar(true);
        tvTitle.setText("提现记录");
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
        rv.setLayoutManager(linearLayoutManager);
        myBillAdapter = new MyDiscountRecordAdapter(MyDiscountRecordActivity.this, R.layout.item_cash_withdrawal, datas);
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
        RequestManager.getInstance().withdrawList(page, new GetCashWithdrawalCallback() {

            @Override
            public void onSuccess(CashWithdrawalBean item) {
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
                            tvEmpty.setText("暂无提现记录");
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
                ToastUtils.showCentetToast(MyDiscountRecordActivity.this, msg);

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
