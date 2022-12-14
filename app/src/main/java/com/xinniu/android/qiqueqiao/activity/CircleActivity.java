package com.xinniu.android.qiqueqiao.activity;

import static com.umeng.socialize.utils.ContextUtil.getContext;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.gson.Gson;
import com.xinniu.android.qiqueqiao.R;
import com.xinniu.android.qiqueqiao.RedPointHelper;
import com.xinniu.android.qiqueqiao.adapter.CircleFragmentAdapter;
import com.xinniu.android.qiqueqiao.adapter.base.BDOnItemClickListener;
import com.xinniu.android.qiqueqiao.base.BaseActivity;
import com.xinniu.android.qiqueqiao.bean.CircleBean;
import com.xinniu.android.qiqueqiao.bean.CircleCallBean;
import com.xinniu.android.qiqueqiao.bean.GroupBean;
import com.xinniu.android.qiqueqiao.customs.qldialog.QLTipDialog;
import com.xinniu.android.qiqueqiao.request.RequestManager;
import com.xinniu.android.qiqueqiao.request.callback.CircleCallCallback;
import com.xinniu.android.qiqueqiao.request.callback.CommonCallback;
import com.xinniu.android.qiqueqiao.request.callback.RequestCallback;
import com.xinniu.android.qiqueqiao.request.converter.ResultDO;
import com.xinniu.android.qiqueqiao.user.UserInfoHelper;
import com.xinniu.android.qiqueqiao.utils.ToastUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import io.rong.imlib.RongIMClient;
import retrofit2.Call;

//import android.support.v4.widget.SwipeRefreshLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
//import com.xinniu.android.qiqueqiao.utils.IMUtils;

/**
 * Created by yuchance on 2018/5/14.
 * ???????????????(?????????)
 */

public class CircleActivity extends BaseActivity {

    @BindView(R.id.tb_return)
    ImageView mTbReturn;
    @BindView(R.id.tb_title)
    TextView mTbTitle;
    @BindView(R.id.tb_menu)
    ImageView tbMenu;
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout mSwipeRefreshLayout;

    private CircleFragmentAdapter mAdapter;
    private List<CircleBean> mData = new ArrayList<>();

    private Call<ResultDO<List<CircleBean>>> mCall;

    @Override
    public int getLayoutId() {
        return R.layout.circle_resource;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        topStatusBar(false);
        mTbReturn.setVisibility(View.VISIBLE);
        mTbReturn.setImageResource(R.mipmap.chevron);
        mTbTitle.setText(R.string.tab_circle);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter = new CircleFragmentAdapter(getContext(), mData);
        mRecyclerView.setAdapter(mAdapter);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getCircleList();
            }
        });
        getCircleList();
        mAdapter.setOnItemClickListener(new BDOnItemClickListener() {
            @Override
            public void onItemClick(ViewGroup parent, View view, Object o, int position) {
                if (!UserInfoHelper.getIntance().isLogin()) {
                    // TODO: 2017/12/20
                    ToastUtils.showCentetImgToast(getContext(), "????????????");
                    return;
                }
                final CircleBean circleBean = (CircleBean) o;
                RequestManager.getInstance().userBelongCircle(circleBean.getId(), new RequestCallback<String>() {
                    @Override
                    public void requestStart(Call call) {
                        showBookingToast(2);
                    }

                    @Override
                    public void onSuccess(String s) {
                        try {
                            JSONObject data = new JSONObject(s);
                            int isJson = data.getInt("status");
                            if (isJson == 1) {
                                //????????????
//                                TODO: 2017/12/14 ???????????????
//                                startConversationActivity(CircleActivity.this,circleBean);
                            } else {
                                //?????????
                                if (circleBean.getIs_verify() == 1) {
                                    showApply1Dialog(circleBean);
                                } else {
//                                    startConversationActivity(CircleActivity.this,circleBean);
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailed(int code, String msg) {
                        ToastUtils.showCentetImgToast(mContext, msg);
                    }

                    @Override
                    public void requestEnd() {
                      dismissBookingToast();
                    }
                });

            }

            @Override
            public boolean onItemLongClick(ViewGroup parent, View view, Object o, int position) {
                return false;
            }
        });
    }


    public static void startConversationActivity(Context activity, GroupBean circleBean) {
        RedPointHelper.getInstance().sendMsgCircle();
        circleBean.setUnReadMesCount(0);
        ConversationActivity.start(
                activity,
                circleBean.getId(),
                circleBean.getName(),circleBean.getNum(),
                circleBean.getHead_pic(),
                circleBean.getShare_url());
    }

    private void showApply1Dialog(final CircleBean circleBean) {
        new QLTipDialog.Builder(CircleActivity.this)
                .setCancelable(true)
                .setCancelableOnTouchOutside(true)
                .setMessage("???????????????????????????\n" +
                        "?????????????????????????????????")
                .setPositiveButton("????????????", new QLTipDialog.IPositiveCallback() {
                    @Override
                    public void onClick() {
                        RequestManager.getInstance().applyCircle(circleBean.getId(), "", new CircleCallCallback() {
                                    @Override
                                    public void onSuccess(CircleCallBean bean) {
                                        ToastUtils.showCentetToast(CircleActivity.this,"????????????");
                                    }

                                    @Override
                                    public void onFailed(int code, String msg, String data) {
                                        if (code == 301){
                                            AlertDialog.Builder builder = new AlertDialog.Builder(CircleActivity.this);
                                            builder.setMessage(msg);
                                            builder.setPositiveButton("?????????", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    Intent intent = new Intent(CircleActivity.this, VipV4ListActivity.class);
                                                    Bundle bundle = new Bundle();
                                                    bundle.putString("svip", "svip");
                                                    intent.putExtras(bundle);
                                                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                                    startActivity(intent, bundle);
                                                }
                                            });
                                            builder.setNegativeButton("??????", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    dialog.dismiss();
                                                }
                                            });
                                            builder.show();
                                            return;
                                        }
                                        if (code == 208){
                                            try {
                                                Gson gson = new Gson();
                                                JSONObject object = new JSONObject(data);
                                                String datax = object.optString("data");
                                                CircleCallBean callBean = gson.fromJson(datax,CircleCallBean.class);
                                                int checkStatus = callBean.getCheckStatus();
                                                if (checkStatus == 1){
                                                    ToastUtils.showCentetToast(CircleActivity.this,"???????????????????????????,????????????");

                                                }else if (checkStatus == 2){
                                                    AlertDialog.Builder builder = new AlertDialog.Builder(CircleActivity.this);
                                                    builder.setMessage(msg);
                                                    builder.setPositiveButton("?????????", new DialogInterface.OnClickListener() {
                                                        @Override
                                                        public void onClick(DialogInterface dialog, int which) {
                                                            toReleaseActivity();
                                                        }
                                                    });
                                                    builder.setNegativeButton("??????", new DialogInterface.OnClickListener() {
                                                        @Override
                                                        public void onClick(DialogInterface dialog, int which) {
                                                            dialog.dismiss();
                                                        }
                                                    });
                                                    builder.show();
                                                }else if (checkStatus == 3){
                                                    AlertDialog.Builder builder = new AlertDialog.Builder(CircleActivity.this);
                                                    builder.setMessage("??????????????????????????????????????????");
                                                    builder.setPositiveButton("?????????", new DialogInterface.OnClickListener() {
                                                        @Override
                                                        public void onClick(DialogInterface dialog, int which) {
                                                            Intent intent = new Intent(CircleActivity.this, VipV4ListActivity.class);
                                                            Bundle bundle = new Bundle();
                                                            bundle.putString("svip", "svip");
                                                            intent.putExtras(bundle);
                                                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                                            startActivity(intent, bundle);
                                                        }
                                                    });
                                                    builder.setNegativeButton("??????", new DialogInterface.OnClickListener() {
                                                        @Override
                                                        public void onClick(DialogInterface dialog, int which) {
                                                            dialog.dismiss();
                                                        }
                                                    });
                                                    builder.show();
                                                }
                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }
                                            return;
                                        }


                                    }
                                }

                        );
                    }
                })
                .show(CircleActivity.this);
    }

    public void getCircleList() {
        RequestManager.getInstance().getCircleList(new RequestCallback<List<CircleBean>>() {
            @Override
            public void requestStart(Call call) {
                mCall = call;
//                showLoadingDialog(0);
            }

            @Override
            public void onSuccess(List<CircleBean> circleBeen) {
                mData.clear();
                for (final CircleBean item : circleBeen) {
                    if (mSwipeRefreshLayout != null) {
                        mSwipeRefreshLayout.setRefreshing(false);
                    }
                    getUnreadCount(String.valueOf(item.getId()), new RongIMClient.ResultCallback<Integer>() {
                        @Override
                        public void onSuccess(Integer integer) {
                            item.setUnReadMesCount(integer);
                            mAdapter.notifyDataSetChanged();
                        }

                        @Override
                        public void onError(RongIMClient.ErrorCode errorCode) {

                        }
                    });
                }
                mData.addAll(circleBeen);
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailed(int code, String msg) {
                mSwipeRefreshLayout.setRefreshing(false);
                ToastUtils.showCentetImgToast(CircleActivity.this, msg);
            }

            @Override
            public void requestEnd() {
//                dissMissDialog();
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mCall != null) {
            mCall.cancel();
        }
    }

    private void getUnreadCount(String targetId, RongIMClient.ResultCallback<Integer> callback) {
        //IMUtils.getUnreadCount(Conversation.ConversationType.GROUP, targetId, callback);
    }

//    private void applyCircle(CircleBean circleBean) {
//        RequestManager.getInstance().applyCircle(circleBean.getId(), "", new CommonCallback() {
//            @Override
//            public void onSuccess(ResultDO resultDO) {
//                new QLTipDialog.Builder(getContext())
//                        .setCancelable(false)
//                        .setCancelableOnTouchOutside(false)
//                        .setMessage("??????????????????????????????\n???????????????")
//                        .setPositiveButton("????????????", new QLTipDialog.IPositiveCallback() {
//                            @Override
//                            public void onClick() {
//
//                            }
//                        })
//                        .show();
//            }
//
//            @Override
//            public void onFailed(int code, String msg) {
//                ToastUtils.showCentetImgToast(getContext(), msg);
//            }
//        });
//    }


    @OnClick(R.id.tb_return)
    public void onViewClicked() {
        finish();
    }
    public void toReleaseActivity() {
        showBookingToast(2);
        RequestManager.getInstance().isPerfect(new CommonCallback() {
            @Override
            public void onSuccess(ResultDO resultDO) {
                dismissBookingToast();
//                startActivityForResult(new Intent(CircleActivity.this, PublishActivity.class), MainActivity.RELEASE_SUCCESS);
            }

            @Override
            public void onFailed(int code, final String msg) {
                dismissBookingToast();
                if (code == 202) {
                    new QLTipDialog.Builder(CircleActivity.this)
                            .setCancelable(true)
                            .setCancelableOnTouchOutside(true)
                            .setMessage(msg)
                            .setNegativeButton("????????????", new QLTipDialog.INegativeCallback() {
                                @Override
                                public void onClick() {

                                }
                            })
                            .show(CircleActivity.this);
                } else if (code == 305){
                     AlertDialog.Builder builder = new  AlertDialog.Builder(CircleActivity.this);
                    builder.setMessage(msg);
                    builder.setPositiveButton("?????????", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
//                            ToastUtils.showCentetImgToast(MainActivity.this, msg);
                            Intent intent = new Intent(CircleActivity.this, CompanyEditActivity.class);
                            startActivity(intent);
                            dialog.dismiss();
                        }
                    });
                    builder.setNegativeButton("??????", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    builder.show();
                }else if (code == 310) {
                    //???????????????
                    new QLTipDialog.Builder(CircleActivity.this)
                            .setCancelable(true)
                            .setCancelableOnTouchOutside(true)
                            .setMessage(msg)
                            .setNegativeButton("??????", new QLTipDialog.INegativeCallback() {
                                @Override
                                public void onClick() {

                                }
                            }).setPositiveButton("?????????", new QLTipDialog.IPositiveCallback() {
                        @Override
                        public void onClick() {
                            CertificationActivity.start(CircleActivity.this, 1);
                        }
                    })
                            .show(CircleActivity.this);
                }
            }
        });
    }
}
