package com.xinniu.android.qiqueqiao.fragment.reward;

import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.xinniu.android.qiqueqiao.R;
import com.xinniu.android.qiqueqiao.adapter.MyPublicRewardAdapter;
import com.xinniu.android.qiqueqiao.base.LazyBaseFragment;
import com.xinniu.android.qiqueqiao.bean.MyPublicRewardBean;
import com.xinniu.android.qiqueqiao.request.RequestManager;
import com.xinniu.android.qiqueqiao.request.callback.RequestCallback;
import com.xinniu.android.qiqueqiao.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import retrofit2.Call;

public class PublicRewardFragment extends LazyBaseFragment {
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
    private List<MyPublicRewardBean.ListBean> mData = new ArrayList<>();
    private MyPublicRewardAdapter mAdapter;
    private int curPage = 1;
    private Call mCall;
    protected View footView1;


    public static PublicRewardFragment newInstance() {
        Bundle args = new Bundle();
        PublicRewardFragment fragment = new PublicRewardFragment();
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
        getData(curPage, true);
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
    protected void lazyLoad() {

    }

    private void getData(final int page, final boolean isShow) {
        RequestManager.getInstance().getMyPublicRewardList(page, new RequestCallback<MyPublicRewardBean>() {
            @Override
            public void requestStart(Call call) {
                if (isShow) {
                    showBookingToast(3);
                }
                mCall = call;
            }

            @Override
            public void onSuccess(MyPublicRewardBean myPushBean) {
                curPage = page;
                if (page == 1) {
                    mData.clear();
                    if (myPushBean.getList().size() == 0) {
                        pushtv.setText("您还未发布过悬赏");
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
                dismissBookingToast();
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
        mAdapter = new MyPublicRewardAdapter((AppCompatActivity) getActivity(), R.layout.item_my_public_reward, mData);
        recyclerView.setAdapter(mAdapter);


    }



    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mCall != null) {
            mCall.cancel();
        }
    }
}
