package com.xinniu.android.qiqueqiao.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.xinniu.android.qiqueqiao.R;
import com.xinniu.android.qiqueqiao.adapter.MyTransactionOrdersAdapter;
import com.xinniu.android.qiqueqiao.base.BaseActivity;
import com.xinniu.android.qiqueqiao.bean.GuaranteeOrderBean;
import com.xinniu.android.qiqueqiao.customs.qldialog.QLTipDialog;
import com.xinniu.android.qiqueqiao.customs.qldialog.QLTipTwoDialog;
import com.xinniu.android.qiqueqiao.request.RequestManager;
import com.xinniu.android.qiqueqiao.request.callback.AllResultDoCallback;
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
                if (type.equals("????????????")) {
                    if (listeren.getStatus() == 0 && listeren.getIs_initiate() == 0) {
                        //???????????????
                        agreeGuarantee(listeren.getId(), 0);
                    } else {
                        //????????????
                        cancelGuarantee(listeren.getId());
                    }

                } else if (type.equals("?????????")) {
                    PayDespositActivity.startSimpleEidtForResult(MyTransactionOrdersActivity.this, listeren.getId(), listeren.getEstimated_amount(), 32);

                } else if (type.equals("????????????")) {
                    PayDetailActivity.start(MyTransactionOrdersActivity.this, listeren.getOrder_sn(), 1);

                } else if (type.equals("????????????")) {
                    //?????????????????????????????????????????????
                    new QLTipTwoDialog.Builder(MyTransactionOrdersActivity.this)
                            .setCancelable(true)
                            .setCancelableOnTouchOutside(true)
                            .setTitle("?????????????????????????????????")
                            .setMessage("????????????????????????????????????")
                            .setPositiveButton("????????????", new QLTipTwoDialog.IPositiveCallback() {
                                @Override
                                public void onClick() {
                                    doSettlementTwo(listeren.getId(), listeren.getRemaining_amount());

                                }
                            })
                            .setNegativeButton("??????", new QLTipTwoDialog.INegativeCallback() {
                                @Override
                                public void onClick() {
                                    dissMissDialog();

                                }
                            })
                            .show(MyTransactionOrdersActivity.this);


                } else if (type.equals("????????????")) {  //??????????????????
                    doSettlement(listeren.getId());


                } else if (type.equals("????????????")) {
                    showSureDialog(listeren.getId());

                } else if (type.equals("????????????")) {
                    //????????????????????????????????????
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
     * ??????????????????
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
     * ??????????????????
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
     * ???????????????????????????
     *
     * @param mId      ??????id
     * @param is_agree 1?????????0?????????
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
     * ????????????
     *
     * @param mId ??????id
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
                        .setTitle("????????????")
                        .setMessage("?????????????????????????????????")
                        .setNegativeButton("????????????", new QLTipDialog.INegativeCallback() {
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
        //??????WebSettings??????
        WebSettings webSettings = agree_ment.getSettings();
        //????????????????????????????????????
        webSettings.setUseWideViewPort(true); //????????????????????????webview?????????
        webSettings.setLoadWithOverviewMode(true); // ????????????????????????
        //????????????
        webSettings.setSupportZoom(true); //????????????????????????true??????????????????????????????
        webSettings.setBuiltInZoomControls(true); //????????????????????????????????????false?????????WebView????????????
        webSettings.setDisplayZoomControls(false); //???????????????????????????
        //??????????????????
        webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK); //??????webview?????????
        webSettings.setAllowFileAccess(true); //????????????????????????
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true); //????????????JS???????????????
        webSettings.setLoadsImagesAutomatically(true); //????????????????????????
        webSettings.setDefaultTextEncodingName("utf-8");//??????????????????

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
