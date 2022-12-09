package com.xinniu.android.qiqueqiao.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
//import android.support.annotation.NonNull;
//import android.support.v7.app.AlertDialog;
//import android.support.v7.widget.LinearLayoutManager;
//import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.RelativeLayout;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.xinniu.android.qiqueqiao.R;
import com.xinniu.android.qiqueqiao.adapter.MyDiscountRecordAdapter;
import com.xinniu.android.qiqueqiao.adapter.MyTransactionOrdersAdapter;
import com.xinniu.android.qiqueqiao.base.BaseActivity;
import com.xinniu.android.qiqueqiao.bean.CashWithdrawalBean;
import com.xinniu.android.qiqueqiao.bean.GuaranteeOrderBean;
import com.xinniu.android.qiqueqiao.customs.qldialog.QLTipDialog;
import com.xinniu.android.qiqueqiao.customs.qldialog.QLTipTwoDialog;
import com.xinniu.android.qiqueqiao.request.RequestManager;
import com.xinniu.android.qiqueqiao.request.callback.AllResultDoCallback;
import com.xinniu.android.qiqueqiao.request.callback.GetCashWithdrawalCallback;
import com.xinniu.android.qiqueqiao.request.callback.GetGuaranteeOrderCallback;
import com.xinniu.android.qiqueqiao.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class MyTransactionOrdersActivity extends BaseActivity {

    @BindView(R.id.yperch_Rl)
    RelativeLayout yperchRl;
    @BindView(R.id.rv)
    RecyclerView rv;
    @BindView(R.id.system_smartrefresh)
    SmartRefreshLayout systemSmartrefresh;
    private List<GuaranteeOrderBean.ListBean> datas = new ArrayList<>();
    private MyTransactionOrdersAdapter myTransactionOrdersAdapter;
    private int page = 1;

    public static void start(Context context) {
        Intent starter = new Intent(context, MyTransactionOrdersActivity.class);
        context.startActivity(starter);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_my_transaction_orders;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        topStatusBar(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
        rv.setLayoutManager(linearLayoutManager);
        myTransactionOrdersAdapter = new MyTransactionOrdersAdapter(MyTransactionOrdersActivity.this, R.layout.item_transaction_orders, datas);
        rv.setAdapter(myTransactionOrdersAdapter);
        myTransactionOrdersAdapter.setSetOnClick(new MyTransactionOrdersAdapter.setOnClick() {
            @Override
            public void setOnClickListeren(final GuaranteeOrderBean.ListBean listeren, String type) {
                if (type.equals("取消交易")) {
                    if (listeren.getStatus() == 0 && listeren.getIs_initiate() == 0) {
                        //不同意交易
                        agreeGuarantee(listeren.getId(), 0);
                    } else {
                        //取消交易
                        cancelGuarantee(listeren.getId());
                    }

                } else if (type.equals("去支付")) {
                    PayDespositActivity.startSimpleEidtForResult(MyTransactionOrdersActivity.this, listeren.getId(), listeren.getEstimated_amount(), 32);

                } else if (type.equals("支付详情")) {
                    PayDetailActivity.start(MyTransactionOrdersActivity.this, listeren.getOrder_sn(), 1);

                } else if (type.equals("全额结算")) {
                    //买家直接全额结算，不用输入金额
                    new QLTipTwoDialog.Builder(MyTransactionOrdersActivity.this)
                            .setCancelable(true)
                            .setCancelableOnTouchOutside(true)
                            .setTitle("确认结算全部剩余担保金")
                            .setMessage("全额结算后，交易将会关闭")
                            .setPositiveButton("确认结算", new QLTipTwoDialog.IPositiveCallback() {
                                @Override
                                public void onClick() {
                                    doSettlementTwo(listeren.getId(), listeren.getRemaining_amount());

                                }
                            })
                            .setNegativeButton("取消", new QLTipTwoDialog.INegativeCallback() {
                                @Override
                                public void onClick() {
                                    dissMissDialog();

                                }
                            })
                            .show(MyTransactionOrdersActivity.this);


                } else if (type.equals("申请结算")) {  //卖家申请结算
                    doSettlement(listeren.getId());


                } else if (type.equals("确认交易")) {
                    showSureDialog(listeren.getId());

                } else if (type.equals("部分结算")) {
                    //买家跳转结算界面输入金额
                    SettlementActivity.startSimpleEidtForResult(MyTransactionOrdersActivity.this, listeren.getId(), listeren.getRemaining_amount(), 33);

                } else if (type.equals("1")) {
                    TransactionDetailsActivity.start(mContext, listeren.getId() + "");
                }
            }
        });
        systemSmartrefresh.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                page = 1;
                buildData(page);
            }
        });
        systemSmartrefresh.setOnLoadMoreListener(new OnLoadMoreListener() {
                                                     @Override
                                                     public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                                                         page++;
                                                         buildData(page);
                                                     }
                                                 }

        );

    }

    @Override
    protected void onResume() {
        super.onResume();
        page = 1;
        buildData(page);
    }

    /**
     * 卖家申请结算
     */
    private void doSettlement(int id) {
        showBookingToast(2);
        RequestManager.getInstance().applicationSettlement(id, new AllResultDoCallback() {
            @Override
            public void onSuccess(String msg) {
                dismissBookingToast();
                ToastUtils.showCentetToast(MyTransactionOrdersActivity.this, msg);
                page = 1;
                buildData(page);

            }

            @Override
            public void onFailed(int code, String msg) {
                dismissBookingToast();
                ToastUtils.showCentetToast(MyTransactionOrdersActivity.this, msg);
            }
        });
    }

    /**
     * 买家申请结算
     */
    private void doSettlementTwo(int id, String price) {
        showBookingToast(2);
        RequestManager.getInstance().guaranteeSettlement(id, price, new AllResultDoCallback() {
            @Override
            public void onSuccess(String msg) {
                dismissBookingToast();
                ToastUtils.showCentetToast(MyTransactionOrdersActivity.this, msg);
                page = 1;
                buildData(page);

            }

            @Override
            public void onFailed(int code, String msg) {
                dismissBookingToast();
                ToastUtils.showCentetToast(MyTransactionOrdersActivity.this, msg);
            }
        });
    }

    /**
     * 同意或者不同意交易
     *
     * @param mId      交易id
     * @param is_agree 1同意，0不同意
     */
    private void agreeGuarantee(int mId, int is_agree) {
        showBookingToast(2);
        RequestManager.getInstance().agreeGuarantee(mId, is_agree, new AllResultDoCallback() {
            @Override
            public void onSuccess(String msg) {
                page = 1;
                buildData(page);
                dismissBookingToast();
                ToastUtils.showCentetImgToast(MyTransactionOrdersActivity.this, msg);
            }

            @Override
            public void onFailed(int code, String msg) {
                dismissBookingToast();
                ToastUtils.showCentetImgToast(MyTransactionOrdersActivity.this, msg);

            }
        });
    }

    /**
     * 取消交易
     *
     * @param mId 交易id
     */
    private void cancelGuarantee(int mId) {
        showBookingToast(2);
        RequestManager.getInstance().cancelGuarantee(mId, new AllResultDoCallback() {
            @Override
            public void onSuccess(String msg) {
                page = 1;
                buildData(page);
                dismissBookingToast();
                ToastUtils.showCentetImgToast(MyTransactionOrdersActivity.this, msg);
            }

            @Override
            public void onFailed(int code, String msg) {
                dismissBookingToast();
                ToastUtils.showCentetImgToast(MyTransactionOrdersActivity.this, msg);

            }
        });
    }


    @OnClick(R.id.bt_finish)
    public void onClick() {
        finish();
    }

    private void buildData(final int page) {
        RequestManager.getInstance().getGuaranteeOrderList(page, new GetGuaranteeOrderCallback() {

            @Override
            public void onSuccess(GuaranteeOrderBean item) {
                if (page == 1) {
                    datas.clear();
                }
                datas.addAll(item.getList());
                myTransactionOrdersAdapter.notifyDataSetChanged();
                finishSwipe();
                if (item.getList() != null) {
                    if (page == 1) {
                        if (item.getList().size() == 0) {
                            yperchRl.setVisibility(View.VISIBLE);
                            myTransactionOrdersAdapter.removeAllFooterView();
                            systemSmartrefresh.setEnableLoadMore(false);
                        } else {
                            yperchRl.setVisibility(View.GONE);
                            if (item.getHasMore() == 0) {
                                myTransactionOrdersAdapter.setFooterView(footView1);
                                systemSmartrefresh.setEnableLoadMore(false);
                            } else {
                                myTransactionOrdersAdapter.removeAllFooterView();
                                systemSmartrefresh.setEnableLoadMore(true);
                            }
                        }

                    } else {
                        if (item.getHasMore() == 0) {
                            myTransactionOrdersAdapter.setFooterView(footView1);
                            systemSmartrefresh.setEnableLoadMore(false);
                        } else {
                            myTransactionOrdersAdapter.removeAllFooterView();
                            systemSmartrefresh.setEnableLoadMore(true);
                        }
                    }
                } else {
                    yperchRl.setVisibility(View.VISIBLE);
                }

            }

            @Override
            public void onFailed(int code, String msg) {
                ToastUtils.showCentetToast(MyTransactionOrdersActivity.this, msg);

            }
        });
    }

    private void finishSwipe() {
        if (systemSmartrefresh != null) {
            if (systemSmartrefresh.isEnableRefresh()) {
                systemSmartrefresh.finishRefresh();
            }
            if (systemSmartrefresh.isEnableLoadMore()) {
                systemSmartrefresh.finishLoadMore();
            }
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {

            if (requestCode == 32) {
                page = 1;
                buildData(page);


            } else if (requestCode == 33) {
                page = 1;
                buildData(page);
                new QLTipDialog.Builder(MyTransactionOrdersActivity.this)
                        .setCancelable(true)
                        .setCancelableOnTouchOutside(true)
                        .setTitle("结算成功")
                        .setMessage("结算金额已进入对方钱包")
                        .setNegativeButton("我知道了", new QLTipDialog.INegativeCallback() {
                            @Override
                            public void onClick() {

                            }
                        })
                        .show(MyTransactionOrdersActivity.this);
            }
        }

    }


    private void showSureDialog(final int id) {
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_secured_transactiona_greement, null, false);
        final AlertDialog dialog = new AlertDialog.Builder(this).setView(view).create();
        WebView agree_ment = view.findViewById(R.id.agree_ment);
        //声明WebSettings子类
        WebSettings webSettings = agree_ment.getSettings();
        //设置自适应屏幕，两者合用
        webSettings.setUseWideViewPort(true); //将图片调整到适合webview的大小
        webSettings.setLoadWithOverviewMode(true); // 缩放至屏幕的大小
        //缩放操作
        webSettings.setSupportZoom(true); //支持缩放，默认为true。是下面那个的前提。
        webSettings.setBuiltInZoomControls(true); //设置内置的缩放控件。若为false，则该WebView不可缩放
        webSettings.setDisplayZoomControls(false); //隐藏原生的缩放控件
        //其他细节操作
        webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK); //关闭webview中缓存
        webSettings.setAllowFileAccess(true); //设置可以访问文件
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true); //支持通过JS打开新窗口
        webSettings.setLoadsImagesAutomatically(true); //支持自动加载图片
        webSettings.setDefaultTextEncodingName("utf-8");//设置编码格式

        agree_ment.loadUrl("file:////android_asset/secured_transaction_agreement.html");
        view.findViewById(R.id.tv_cancle).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dialog.dismiss();
            }
        });
        view.findViewById(R.id.tv_sure).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                agreeGuarantee(id, 1);
                dialog.dismiss();
            }
        });
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();

    }
}
