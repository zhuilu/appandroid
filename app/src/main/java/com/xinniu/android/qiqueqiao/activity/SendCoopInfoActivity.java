package com.xinniu.android.qiqueqiao.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
//import android.support.annotation.NonNull;
//import android.support.v7.app.AlertDialog;
//import android.support.v7.widget.LinearLayoutManager;
//import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.xinniu.android.qiqueqiao.R;
import com.xinniu.android.qiqueqiao.adapter.SendCoopInfoAdapter;
import com.xinniu.android.qiqueqiao.base.BaseActivity;
import com.xinniu.android.qiqueqiao.bean.MyPushBean;
import com.xinniu.android.qiqueqiao.customs.qldialog.QLTipDialog;
import com.xinniu.android.qiqueqiao.dialog.AlertDialogUtils;
import com.xinniu.android.qiqueqiao.request.RequestManager;
import com.xinniu.android.qiqueqiao.request.callback.AllResultDoCallback;
import com.xinniu.android.qiqueqiao.request.callback.CommonCallback;
import com.xinniu.android.qiqueqiao.request.callback.RequestCallback;
import com.xinniu.android.qiqueqiao.request.converter.ResultDO;
import com.xinniu.android.qiqueqiao.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;

/**
 * Created by yuchance on 2019/2/22.
 */

public class SendCoopInfoActivity extends BaseActivity {
    @BindView(R.id.mrecycler)
    RecyclerView mrecycler;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    @BindView(R.id.yperch_Rl)
    RelativeLayout yperchRl;
    private int page = 1;
    private Call mCall;
    private List<MyPushBean.ListBean> datas = new ArrayList<>();
    private SendCoopInfoAdapter adapter;
    private String userId;
    private String userName;

    public static void start(Context context,String userId,String name){
        Intent intent = new Intent(context,SendCoopInfoActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("userId",userId);
        bundle.putString("userName",name);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }



    @Override
    public int getLayoutId() {
        return R.layout.activity_send_coopinfo;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        topStatusBar(true);
        Bundle bundle = getIntent().getExtras();
        userId = bundle.getString("userId");
        userName = bundle.getString("userName");
        LinearLayoutManager manager = new LinearLayoutManager(SendCoopInfoActivity.this, LinearLayoutManager.VERTICAL, false);
        mrecycler.setLayoutManager(manager);
        buildData(page, true);
        adapter = new SendCoopInfoAdapter(this, R.layout.item_send_coopinfo, datas);
        mrecycler.setAdapter(adapter);
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                page = 1;
                buildData(page, false);
            }
        }).setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                page++;
                buildData(page, false);
            }
        });


        adapter.setSetOnClick(new SendCoopInfoAdapter.setOnClick() {
            @Override
            public void setOnClick(final int id) {

                AlertDialogUtils.AlertDialog(SendCoopInfoActivity.this, "确定发送", "确定将此合作信息发送给“" + userName +"”","取消", "确定", new AlertDialogUtils.setOnClick() {
                    @Override
                    public void setLeftOnClick(DialogInterface dialog) {
                        dialog.dismiss();
                    }

                    @Override
                    public void setRightOnClick(DialogInterface dialog) {
                        try {
                            int targetId = Integer.parseInt(userId);
                            sendResource(id,targetId);
                        }catch (NumberFormatException e){

                        }
                    }
                });


            }
        });

    }

    private void buildData(final int page, final boolean isShow) {
        showBookingToast(1);

        RequestManager.getInstance().getMyPush(page, new RequestCallback<MyPushBean>() {
            @Override
            public void requestStart(Call call) {
                if (isShow) {
                    showBookingToast(1);
                }
                mCall = call;
            }

            @Override
            public void onSuccess(MyPushBean myPushBean) {
                if (page == 1) {
                    datas.clear();
                    if (myPushBean.getList().size() == 0) {
                        yperchRl.setVisibility(View.VISIBLE);
                        adapter.removeAllFooterView();
                        refreshLayout.setEnableLoadMore(false);
                    } else {
                        yperchRl.setVisibility(View.GONE);
                        if (myPushBean.getHasMore() == 0) {
                            adapter.setFooterView(footView1);
                            refreshLayout.setEnableLoadMore(false);
                        } else {
                            adapter.removeAllFooterView();
                            refreshLayout.setEnableLoadMore(true);
                        }
                    }
                } else {
                    if (myPushBean.getHasMore() == 0) {
                        adapter.setFooterView(footView1);
                        refreshLayout.setEnableLoadMore(false);
                    } else {
                        adapter.removeAllFooterView();
                        refreshLayout.setEnableLoadMore(true);
                    }
                }
                for (int i = 0; i < myPushBean.getList().size(); i++) {
                    myPushBean.getList().get(i).setItemType(MyPushBean.ListBean.COMMON);
                }
                if (page == 1) {
                    myPushBean.getList().add(0, new MyPushBean.ListBean(MyPushBean.ListBean.THETOP));
                }
                datas.addAll(myPushBean.getList());
                adapter.setCompany(myPushBean.getCompany());
                adapter.notifyDataSetChanged();
                if (refreshLayout != null) {
                    if (refreshLayout.isEnableRefresh()) {
                        refreshLayout.finishRefresh(true);
                    }
                    if (refreshLayout.isEnableLoadMore()) {
                        refreshLayout.finishLoadMore(true);
                    }
                }

            }

            @Override
            public void onFailed(int code, String msg) {
                ToastUtils.showCentetImgToast(SendCoopInfoActivity.this, msg);
            }

            @Override
            public void requestEnd() {
                dismissBookingToast();
            }
        });


    }


    @OnClick({R.id.bt_finish,R.id.bgotoPush})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bt_finish:
                finish();
                break;
            case R.id.bgotoPush:
                toReleaseActivity();
                break;
            default:
                break;

        }

    }


    //发送资源
    private void sendResource(int resourceId,int userId) {
        RequestManager.getInstance().shareResource(userId, resourceId, new AllResultDoCallback() {
            @Override
            public void onSuccess(String msg) {
                ToastUtils.showCentetToast(SendCoopInfoActivity.this, msg);
                finish();
            }

            @Override
            public void onFailed(int code, String msg) {
                ToastUtils.showCentetToast(SendCoopInfoActivity.this, msg);
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
//                startActivityForResult(new Intent(MyPushActivity.this, PublishActivity.class), MainActivity.RELEASE_SUCCESS);
                PublishSelectTypeActivity.start(SendCoopInfoActivity.this);
            }

            @Override
            public void onFailed(int code, final String msg) {
                dismissBookingToast();
                if (code == 202) {
                    new QLTipDialog.Builder(SendCoopInfoActivity.this)
                            .setCancelable(true)
                            .setCancelableOnTouchOutside(true)
                            .setMessage(msg)
                            .setNegativeButton("我知道了", new QLTipDialog.INegativeCallback() {
                                @Override
                                public void onClick() {

                                }
                            })
                            .show(SendCoopInfoActivity.this);
                } else if (code == 305){
                    AlertDialog.Builder builder = new AlertDialog.Builder(SendCoopInfoActivity.this);
                    builder.setMessage(msg);
                    builder.setPositiveButton("去完善", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
//                            ToastUtils.showCentetImgToast(MainActivity.this, msg);
                            Intent intent = new Intent(SendCoopInfoActivity.this, CompanyEditActivity.class);
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
                }else if (code == 310) {
                    //未实人认证
                    new QLTipDialog.Builder(SendCoopInfoActivity.this)
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
                            CertificationActivity.start(SendCoopInfoActivity.this, 1);
                        }
                    })
                            .show(SendCoopInfoActivity.this);
                }
            }
        });
    }
}
