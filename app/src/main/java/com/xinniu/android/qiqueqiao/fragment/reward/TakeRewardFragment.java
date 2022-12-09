package com.xinniu.android.qiqueqiao.fragment.reward;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
//import android.support.annotation.NonNull;
//import android.support.v7.app.AlertDialog;
//import android.support.v7.widget.LinearLayoutManager;
//import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.xinniu.android.qiqueqiao.R;
import com.xinniu.android.qiqueqiao.activity.CompanyEditActivity;
import com.xinniu.android.qiqueqiao.activity.PublishingServiceActivity;
import com.xinniu.android.qiqueqiao.activity.RewardOrderDetailActivity;
import com.xinniu.android.qiqueqiao.activity.TransactionDetailsActivity;
import com.xinniu.android.qiqueqiao.adapter.MyServiceAdapter;
import com.xinniu.android.qiqueqiao.adapter.MyTakeRewardAdapter;
import com.xinniu.android.qiqueqiao.base.LazyBaseFragment;
import com.xinniu.android.qiqueqiao.bean.MyServicePushBean;
import com.xinniu.android.qiqueqiao.bean.TakeRewardBean;
import com.xinniu.android.qiqueqiao.customs.qldialog.QLTipDialog;
import com.xinniu.android.qiqueqiao.dialog.DeleteReplyDialog;
import com.xinniu.android.qiqueqiao.request.RequestManager;
import com.xinniu.android.qiqueqiao.request.callback.AllResultDoCallback;
import com.xinniu.android.qiqueqiao.request.callback.CommonCallback;
import com.xinniu.android.qiqueqiao.request.callback.RequestCallback;
import com.xinniu.android.qiqueqiao.request.converter.ResultDO;
//import com.xinniu.android.qiqueqiao.utils.IMUtils;
import com.xinniu.android.qiqueqiao.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import retrofit2.Call;

public class TakeRewardFragment extends LazyBaseFragment {
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
    private List<TakeRewardBean.ListBean> mData = new ArrayList<>();
    private MyTakeRewardAdapter mAdapter;
    private int curPage = 1;
    private Call mCall;
    protected View footView1;

    public static TakeRewardFragment newInstance() {
        Bundle args = new Bundle();
        TakeRewardFragment fragment = new TakeRewardFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_my_reward;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        footView1 = getActivity().getLayoutInflater().inflate(R.layout.view_unload_more_gray, null);
        initAdapter();

        myPushSwipe.setEnableRefresh(false);

        myPushSwipe.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                curPage++;
                getData(curPage, false);
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        curPage = 1;
        getData(curPage, false);
    }

    @Override
    protected void lazyLoad() {

    }

    private void getData(final int page, final boolean isShow) {
        RequestManager.getInstance().getMyTakeRewardList(page, new RequestCallback<TakeRewardBean>() {
            @Override
            public void requestStart(Call call) {
                if (isShow) {
                    showBookingToast(3);
                }
                mCall = call;
            }

            @Override
            public void onSuccess(TakeRewardBean myPushBean) {
                curPage = page;
                if (page == 1) {
                    mData.clear();
                    if (myPushBean.getList().size() == 0) {
                        pushtv.setText("您还未接单过悬赏");
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
        mAdapter = new MyTakeRewardAdapter(getActivity(), R.layout.item_my_take_reward, mData);
        recyclerView.setAdapter(mAdapter);
        mAdapter.setCallback(new MyTakeRewardAdapter.Callback() {
            @Override
            public void onEdit(int position, String isUp, TakeRewardBean.ListBean item) {
                if (isUp.equals("1")) {
                    //取消接单
                    cancleReward(position, item);
                } else if (isUp.equals("2")) {
                    //聊天
                    //IMUtils.singleChatForResource(getActivity(), item.getUser_id() + "", "", 0, "", "", "0");
                }
            }
        });


    }

    private void cancleReward(final int position, final TakeRewardBean.ListBean item) {
        RequestManager.getInstance().cancelReward(item.getOrder_sn(), item.getId(), new AllResultDoCallback() {
            @Override
            public void onSuccess(String msg) {
                dismissBookingToast();
                ToastUtils.showCentetToast(getActivity(), msg);
                //只更新当前这条数据
                item.setReceived_status(2);//取消
                mAdapter.notifyItemChanged(position, item);

            }

            @Override
            public void onFailed(int code, String msg) {
                dismissBookingToast();
                ToastUtils.showCentetToast(getActivity(), msg);
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
}
