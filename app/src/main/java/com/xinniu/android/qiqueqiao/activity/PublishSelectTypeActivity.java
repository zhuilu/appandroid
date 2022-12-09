package com.xinniu.android.qiqueqiao.activity;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
//import android.support.v7.app.AlertDialog;
//import android.support.v7.widget.LinearLayoutManager;
//import androidx.recyclerview.widget.RecyclerView;
import android.view.View;

import com.google.gson.Gson;
import com.umeng.analytics.MobclickAgent;
import com.xinniu.android.qiqueqiao.R;
import com.xinniu.android.qiqueqiao.adapter.PublishSelectTypeAdapter;
import com.xinniu.android.qiqueqiao.base.BaseActivity;
import com.xinniu.android.qiqueqiao.bean.GetReleaseTypeBean;
import com.xinniu.android.qiqueqiao.bean.ReleaseCheckBean;
import com.xinniu.android.qiqueqiao.customs.qldialog.QLTipDialog;
import com.xinniu.android.qiqueqiao.customs.qldialog.QLTipTwoDialog;
import com.xinniu.android.qiqueqiao.request.RequestManager;
import com.xinniu.android.qiqueqiao.request.callback.CommonCallback;
import com.xinniu.android.qiqueqiao.request.callback.GetReleaseCheckCallback;
import com.xinniu.android.qiqueqiao.request.callback.GetReleaseTypeCallback;
import com.xinniu.android.qiqueqiao.request.converter.ResultDO;
import com.xinniu.android.qiqueqiao.utils.ComUtils;
import com.xinniu.android.qiqueqiao.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 发布-选择资源类型
 * Created by yuchance on 2018/12/12.
 */

public class PublishSelectTypeActivity extends BaseActivity {
    @BindView(R.id.mrecycler)
    RecyclerView mrecycler;
    private List<GetReleaseTypeBean> datas = new ArrayList<>();
    private PublishSelectTypeAdapter adapter;


    public static void start(Activity context) {
        Intent intent = new Intent(context, PublishSelectTypeActivity.class);

        context.startActivity(intent);
    }


    @Override
    public int getLayoutId() {
        return R.layout.activity_publish_selecttype;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        topStatusBar(true);
        ComUtils.addActivity(this);
        LinearLayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mrecycler.setLayoutManager(manager);
        adapter = new PublishSelectTypeAdapter(PublishSelectTypeActivity.this, R.layout.item_publish_type, datas);
        mrecycler.setAdapter(adapter);
        buildData();
        adapter.setSetOnClick(new PublishSelectTypeAdapter.setOnClick() {
            @Override
            public void setOnClick(String title, int typeId) {
                MobclickAgent.onEvent(mContext, "publish_category", title);//统计banner点击次数
                if (typeId == -1) {
                    toReleaseActivity();
                } else if (typeId == -2) {
                    PublicRewardAvtivity.start(PublishSelectTypeActivity.this);

                } else {
                    //先判断
                    check(title, typeId);

                }
            }
        });

    }

    private void check(final String title, final int typeId) {
        RequestManager.getInstance().releaseCheck(typeId, new GetReleaseCheckCallback() {
            @Override
            public void onSuccess() {
                PublishNewActivity.start(PublishSelectTypeActivity.this, title, typeId, 1001);
            }

            @Override
            public void onFailed(int code, String msg, String getConfigBean) {
                if (code == 311) {
                    Gson gson = new Gson();
                    ReleaseCheckBean bean = gson.fromJson(getConfigBean, ReleaseCheckBean.class);
                    new QLTipTwoDialog.Builder(PublishSelectTypeActivity.this)
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
                            .show(PublishSelectTypeActivity.this);

                } else {
                    ToastUtils.showCentetToast(PublishSelectTypeActivity.this, msg);
                }

            }
        });
    }

    private void buildData() {
        showBookingToast(1);
        RequestManager.getInstance().getReleaseType(1, new GetReleaseTypeCallback() {
            @Override
            public void onSuccess(List<GetReleaseTypeBean> bean) {
                dismissBookingToast();
                datas.addAll(bean);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailed(int code, String msg) {
                dismissBookingToast();
            }
        });
    }


    @OnClick(R.id.bt_finish)
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bt_finish:
                finish();
                break;
            default:
                break;
        }


    }

    /**
     * 转调到发布页面
     */
    public void toReleaseActivity() {
        showBookingToast(2);
        RequestManager.getInstance().sendCheck(new CommonCallback() {
            @Override
            public void onSuccess(ResultDO resultDO) {
                dismissBookingToast();
                PublishingServiceActivity.start(PublishSelectTypeActivity.this);
            }

            @Override
            public void onFailed(int code, final String msg) {
                dismissBookingToast();
                if (code == 305) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(PublishSelectTypeActivity.this);
                    builder.setMessage(msg);
                    builder.setPositiveButton("去完善", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(PublishSelectTypeActivity.this, CompanyEditActivity.class);
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
                } else {
                    new QLTipDialog.Builder(PublishSelectTypeActivity.this)
                            .setCancelable(true)
                            .setCancelableOnTouchOutside(true)
                            .setMessage(msg)
                            .setNegativeButton("我知道了", new QLTipDialog.INegativeCallback() {
                                @Override
                                public void onClick() {

                                }
                            })
                            .show(PublishSelectTypeActivity.this);
                }
            }
        });
    }
}
