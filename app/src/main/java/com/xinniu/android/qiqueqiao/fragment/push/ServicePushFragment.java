package com.xinniu.android.qiqueqiao.fragment.push;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
//import android.support.annotation.NonNull;
//import android.support.annotation.Nullable;
//import android.support.v7.app.AlertDialog;
//import android.support.v7.widget.LinearLayoutManager;
//import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.xinniu.android.qiqueqiao.R;
import com.xinniu.android.qiqueqiao.activity.CompanyEditActivity;
import com.xinniu.android.qiqueqiao.activity.PublishingServiceActivity;
import com.xinniu.android.qiqueqiao.activity.SuperExposureActivity;
import com.xinniu.android.qiqueqiao.activity.TopCardActivity;
import com.xinniu.android.qiqueqiao.adapter.MyServiceAdapter;
import com.xinniu.android.qiqueqiao.base.LazyBaseFragment;
import com.xinniu.android.qiqueqiao.bean.MyPushBean;
import com.xinniu.android.qiqueqiao.bean.MyServicePushBean;
import com.xinniu.android.qiqueqiao.customs.qldialog.QLTipDialog;
import com.xinniu.android.qiqueqiao.dialog.AlertDialogUtils;
import com.xinniu.android.qiqueqiao.dialog.DeleteReplyDialog;
import com.xinniu.android.qiqueqiao.request.RequestManager;
import com.xinniu.android.qiqueqiao.request.callback.AllResultDoCallback;
import com.xinniu.android.qiqueqiao.request.callback.CommonCallback;
import com.xinniu.android.qiqueqiao.request.callback.RequestCallback;
import com.xinniu.android.qiqueqiao.request.converter.ResultDO;
import com.xinniu.android.qiqueqiao.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import retrofit2.Call;

public class ServicePushFragment extends LazyBaseFragment {
    public static int MYPUSHCODE = 101;
    public static int MYPUSHCODETWO = 102;
    @BindView(R.id.pushtv)
    TextView pushtv;
    @BindView(R.id.bgotoPush)
    TextView bgotoPush;
    @BindView(R.id.yperch_Rl)
    RelativeLayout yperchRl;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.my_push_swipe)
    SmartRefreshLayout myPushSwipe;
    @BindView(R.id.bmypush_buytop)
    TextView bmypushBuytop;
    @BindView(R.id.mtopcard_num)
    TextView mtopcardNum;
    @BindView(R.id.bmypush_buytop_2)
    TextView bmypushBuytop2;
    @BindView(R.id.mtopcard_num_2)
    TextView mtopcardNum2;
    //  @BindView(R.id.msurplus_num)
    TextView msurplusNum;

    private List<MyServicePushBean.ListBean> mData = new ArrayList<>();
    private MyServiceAdapter mAdapter;
    private int curPage = 1;
    private Call mCall;
    protected View footView1;
    private int refreshNum;
    private int cardNum;
    private int fixedNum;
    private int mPosition;

    public static ServicePushFragment newInstance() {

        Bundle args = new Bundle();

        ServicePushFragment fragment = new ServicePushFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_service_push;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        footView1 = getActivity().getLayoutInflater().inflate(R.layout.view_unload_more_gray, null);
        initAdapter();
        getService(curPage, true);
        myPushSwipe.setEnableRefresh(false);

        myPushSwipe.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                curPage++;
                getService(curPage, false);
            }
        });
    }

    private void getService(final int page, final boolean isShow) {
        RequestManager.getInstance().getMyPush(page, new RequestCallback<MyPushBean>() {
            @Override
            public void requestStart(Call call) {

            }

            @Override
            public void onSuccess(MyPushBean myPushBean) {

                refreshNum = myPushBean.getRefresh_num();
                cardNum = myPushBean.getCard_num();
                fixedNum = myPushBean.getFixed_top_card_num();
                msurplusNum.setText("今日剩余刷新权限: " + refreshNum + "次");
                mtopcardNum.setText("刷新卡 X " + cardNum);
                mtopcardNum2.setText("置顶卡 X " + fixedNum);


            }

            @Override
            public void onFailed(int code, String msg) {

            }

            @Override
            public void requestEnd() {

            }
        });
        RequestManager.getInstance().getMyServicePush(page, new RequestCallback<MyServicePushBean>() {
            @Override
            public void requestStart(Call call) {
                if (isShow) {
                    showBookingToast(3);
                }
                mCall = call;
            }

            @Override
            public void onSuccess(MyServicePushBean myPushBean) {
                curPage = page;
                if (page == 1) {
                    mData.clear();
                    if (myPushBean.getList().size() == 0) {
                        yperchRl.setVisibility(View.VISIBLE);
                        mAdapter.removeAllFooterView();
                        myPushSwipe.setEnableLoadMore(false);
                    } else {
                        yperchRl.setVisibility(View.GONE);
                        if (myPushBean.getHasMore() == 0) {
                            mAdapter.setFooterView(footView1);
                            myPushSwipe.setEnableLoadMore(false);
                        } else {
                            mAdapter.removeAllFooterView();
                            myPushSwipe.setEnableLoadMore(true);
                        }
                    }
                } else {
                    if (myPushBean.getHasMore() == 0) {
                        mAdapter.setFooterView(footView1);
                        myPushSwipe.setEnableLoadMore(false);
                    } else {
                        mAdapter.removeAllFooterView();
                        myPushSwipe.setEnableLoadMore(true);
                    }
                }

                mData.addAll(myPushBean.getList());
                mAdapter.notifyDataSetChanged();
                if (myPushSwipe != null) {
                    if (myPushSwipe.isEnableRefresh()) {
                        myPushSwipe.finishRefresh(true);
                    }
                    if (myPushSwipe.isEnableLoadMore()) {
                        myPushSwipe.finishLoadMore(true);
                    }
                }
            }

            @Override
            public void onFailed(int code, String msg) {
                ToastUtils.showCentetImgToast(getContext(), msg);
            }

            @Override
            public void requestEnd() {
                dismissBookingToast();
            }
        });
    }

    private void initAdapter() {
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter = new MyServiceAdapter(getActivity(), R.layout.item_service_push, mData);
        recyclerView.setAdapter(mAdapter);
        mAdapter.setCallback(new MyServiceAdapter.Callback() {

            @Override
            public void onMore(int position, String isUp) {
                mPosition = position;
                if (isUp.equals("1")) {
                    //编辑
                    //发布之前做判断
                    toReleaseActivity(2);
                } else if (isUp.equals("2")) {
                    //刷新
                    unrefresh(position);

                } else if (isUp.equals("3")) {
                    //上架
                    delete(position, 1);
                } else if (isUp.equals("4")) {
                    //下架
                    delete(position, 2);
                } else if (isUp.equals("5")) {
                    //删除
                    delete(position, 3);
                }

            }
        });
    }


    ////1：上架，2：下架，3：删除
    private void delete(final int position, final int i) {
        RequestManager.getInstance().operateService(mData.get(position).getId(), i, new RequestCallback<String>() {
            @Override
            public void requestStart(Call call) {
                showBookingToast(2);
                mCall = call;
            }

            @Override
            public void onSuccess(String s) {
                ToastUtils.showCentetImgToast(getContext(), s);
                curPage = 1;
                getService(curPage, false);
            }

            @Override
            public void onFailed(int code, String msg) {
                if (code == 202 && i == 1) {
                    new QLTipDialog.Builder(getActivity())
                            .setCancelable(true)
                            .setCancelableOnTouchOutside(true)
                            .setMessage(msg)
                            .setNegativeButton("我知道了", new QLTipDialog.INegativeCallback() {
                                @Override
                                public void onClick() {

                                }
                            })
                            .show(getActivity());

                } else {
                    ToastUtils.showCentetImgToast(getContext(), msg);
                }
            }

            @Override
            public void requestEnd() {
                dismissBookingToast();
            }
        });
    }

    @Override
    protected void lazyLoad() {

    }

    @OnClick({R.id.bgotoPush, R.id.bmypush_buytop, R.id.bmypush_buytop_2})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bgotoPush:
                //发布之前做判断
                toReleaseActivity(1);
                break;
            case R.id.bmypush_buytop:
                TopCardActivity.start(getActivity(), MYPUSHCODE);
                break;
            case R.id.bmypush_buytop_2:
                SuperExposureActivity.start(getActivity(), MYPUSHCODETWO);
                break;
            default:
                break;
        }
    }

    /**
     * 转调到发布页面
     *
     * @param type
     */
    public void toReleaseActivity(final int type) {
        showBookingToast(2);
        RequestManager.getInstance().sendCheck(new CommonCallback() {
            @Override
            public void onSuccess(ResultDO resultDO) {
                dismissBookingToast();
                if (type == 2) {
                    //编辑
                    PublishingServiceActivity.start(getActivity(), mData.get(mPosition).getId(), mData.get(mPosition).getP_name(), mData.get(mPosition).getP_id(), 1000);
                } else {
                    PublishingServiceActivity.start(getActivity());
                }
            }

            @Override
            public void onFailed(int code, final String msg) {
                dismissBookingToast();
                if (code == 305) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setMessage(msg);
                    builder.setPositiveButton("去完善", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(getContext(), CompanyEditActivity.class);
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
                    new QLTipDialog.Builder(getActivity())
                            .setCancelable(true)
                            .setCancelableOnTouchOutside(true)
                            .setMessage(msg)
                            .setNegativeButton("我知道了", new QLTipDialog.INegativeCallback() {
                                @Override
                                public void onClick() {

                                }
                            })
                            .show(getActivity());
                }
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

    public void refresh() {

        curPage = 1;
        getService(curPage, false);


    }

    private void unrefresh(final int pos) {
        RequestManager.getInstance().goRefreshCheck(2, new AllResultDoCallback() {
            @Override
            public void onSuccess(String msg) {
                if (refreshNum > 0) {
                    AlertDialogUtils.AlertDialog(getContext(), "将此条服务信息刷新至最高排名", "取消", "确认刷新", new AlertDialogUtils.setOnClick() {
                        @Override
                        public void setLeftOnClick(DialogInterface dialog) {
                            dialog.dismiss();
                        }

                        @Override
                        public void setRightOnClick(DialogInterface dialog) {
                            refresh(pos);
                        }
                    });
                } else {
                    if (cardNum > 0) {
                        AlertDialogUtils.AlertDialog(getActivity(), "您本日刷新权限已不足，本次刷新将消耗1张刷新卡", "取消", "立即刷新", new AlertDialogUtils.setOnClick() {
                            @Override
                            public void setLeftOnClick(DialogInterface dialog) {
                                dialog.dismiss();
                            }

                            @Override
                            public void setRightOnClick(DialogInterface dialog) {
                                refresh(pos);
                            }
                        });

                    } else {
                        AlertDialogUtils.AlertDialog(getContext(), "本日刷新权限已用完", "请购买刷新卡刷新您的合作信息", "取消", "去购买", new AlertDialogUtils.setOnClick() {
                            @Override
                            public void setLeftOnClick(DialogInterface dialog) {
                                dialog.dismiss();
                            }

                            @Override
                            public void setRightOnClick(DialogInterface dialog) {
                                TopCardActivity.start(getActivity(), MYPUSHCODE);
                            }
                        });
                    }
                }
            }

            @Override
            public void onFailed(int code, String msg) {

                ToastUtils.showCentetToast(getContext(), msg);

            }
        });
    }

    private void refresh(int pos) {

        RequestManager.getInstance().refresh(2, mData.get(pos).getId(), new CommonCallback() {
            @Override
            public void onSuccess(ResultDO resultDO) {
                ToastUtils.showCentetImgToast(getActivity(), resultDO.getMsg());
                curPage = 1;
                getService(curPage, false);
            }

            @Override
            public void onFailed(int code, String msg) {
                ToastUtils.showCentetImgToast(getActivity(), msg);
            }
        });

    }


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
         msurplusNum = rootView.findViewById(R.id.msurplus_num);
        return rootView;
    }
}
