package com.xinniu.android.qiqueqiao.fragment.maintab;

import android.os.Bundle;
//import android.support.annotation.NonNull;
//import android.support.v7.widget.LinearLayoutManager;
//import androidx.recyclerview.widget.RecyclerView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.xinniu.android.qiqueqiao.R;
import com.xinniu.android.qiqueqiao.activity.LoginNewActivity;
import com.xinniu.android.qiqueqiao.adapter.ClassRoomAdapter;
import com.xinniu.android.qiqueqiao.adapter.ClassRoomTagsAdapter;
import com.xinniu.android.qiqueqiao.base.LazyBaseFragment;
import com.xinniu.android.qiqueqiao.bean.ClassRoomListBean;
import com.xinniu.android.qiqueqiao.bean.VideoCategoryBean;
import com.xinniu.android.qiqueqiao.request.RequestManager;
import com.xinniu.android.qiqueqiao.request.callback.AllResultDoCallback;
import com.xinniu.android.qiqueqiao.request.callback.GetVedioCategoryCallback;
import com.xinniu.android.qiqueqiao.request.callback.VideoListCallback;
import com.xinniu.android.qiqueqiao.user.UserInfoHelper;
import com.xinniu.android.qiqueqiao.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class ClassRoomFragment extends LazyBaseFragment {
    @BindView(R.id.mfragment_company_recycler)
    RecyclerView mfragmentCompanyRecycler;
    @BindView(R.id.companySwipe)
    SmartRefreshLayout companySwipe;
    @BindView(R.id.mrecyclerSth)
    RecyclerView mrecyclerSth;
    private ClassRoomAdapter mClassRoomAdapter;
    private List<ClassRoomListBean.ListBean> mListData=new ArrayList<>();
    private int page = 1;
    private ClassRoomTagsAdapter classRoomTagsAdapter;
    private List<VideoCategoryBean> tags = new ArrayList<>();
    private int p_id = 0;//全部
    public static ClassRoomFragment newInstance() {

        Bundle args = new Bundle();
        ClassRoomFragment fragment = new ClassRoomFragment();
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public int getLayoutId() {
        return R.layout.fragment_classroom;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {

        LinearLayoutManager manager = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
        mfragmentCompanyRecycler.setLayoutManager(manager);
        mListData = new ArrayList<>();
        mClassRoomAdapter = new ClassRoomAdapter(getActivity(), R.layout.item_classroom_new, mListData);
        mfragmentCompanyRecycler.setAdapter(mClassRoomAdapter);
        mClassRoomAdapter.setOnclick(new ClassRoomAdapter.Onclick() {
            @Override
            public void click(ClassRoomListBean.ListBean item, int position) {
                if (!isLogin()) {
                    return;
                }
                //	1点赞，0取消点赞
                if (item.getIs_upvote() == 1) {
                    toUpVote(item, position, 0);

                } else {
                    toUpVote(item, position, 1);
                }


            }
        });
        companySwipe.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                page = 1;
                buildDatas(page, false);
            }
        });
        companySwipe.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                page++;
                buildDatas(page, false);
            }
        });

        LinearLayoutManager manager2 = new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false);
        mrecyclerSth.setLayoutManager(manager2);
        classRoomTagsAdapter = new ClassRoomTagsAdapter(getContext(), R.layout.item_classroom_tags, tags);
        mrecyclerSth.setAdapter(classRoomTagsAdapter);
        classRoomTagsAdapter.setSetTags(new ClassRoomTagsAdapter.setTags() {
            @Override
            public void setTags(int id) {
                p_id = id;
                page = 1;
                buildDatas(page, false);
            }
        });
    }

    private void toUpVote(final ClassRoomListBean.ListBean item, final int position, final int un_upvote) {
        showBookingToast(2);

        RequestManager.getInstance().videoUpVote(item.getId(), 1, un_upvote, new AllResultDoCallback() {
            @Override
            public void onSuccess(String msg) {
                dismissBookingToast();
                ToastUtils.showCentetToast(getContext(), msg);
                if (un_upvote == 1) {
                    //点赞
                    item.setIs_upvote(1);
                    //数量加1
                    int num = item.getUpvote_num() + 1;
                    item.setUpvote_num(num);
                } else {
                    item.setIs_upvote(0);
                    //数量加1
                    int num = item.getUpvote_num() - 1;
                    item.setUpvote_num(num);
                }
                //只更新当前这条数据
                mClassRoomAdapter.notifyItemChanged(position, item);

            }

            @Override
            public void onFailed(int code, String msg) {
                dismissBookingToast();

                ToastUtils.showCentetToast(getContext(), msg);
            }
        });
    }

    @Override
    protected void lazyLoad() {
        buildDatas(page, false);
    }

    private void buildDatas(final int page, final boolean isShow) {
        if (isShow) {
            showBookingToast(3);
        }
        int userId = UserInfoHelper.getIntance().getUserId();
        RequestManager.getInstance().getVedioList(userId, page, p_id, new VideoListCallback() {
            @Override
            public void onSuccess(ClassRoomListBean bean) {
                dismissBookingToast();
                if (isShow) {
                    buildTags();
                }
                if (page == 1) {
                    mListData.clear();
                    if (bean.getList().size() == 0) {
                        mClassRoomAdapter.removeAllFooterView();
                        companySwipe.setEnableLoadMore(false);
                    } else {

                        if (bean.getHasMore() == 0) {
                            mClassRoomAdapter.setFooterView(gfootView);
                            companySwipe.setEnableLoadMore(false);
                        } else {
                            mClassRoomAdapter.removeAllFooterView();
                            companySwipe.setEnableLoadMore(true);
                        }
                    }
                } else {
                    if (bean.getHasMore() == 0) {
                        mClassRoomAdapter.setFooterView(footView);
                        companySwipe.setEnableLoadMore(false);
                    } else {
                        mClassRoomAdapter.removeAllFooterView();
                        companySwipe.setEnableLoadMore(true);
                    }
                }
                mListData.addAll(bean.getList());
                mClassRoomAdapter.notifyDataSetChanged();
                finishSwipe();
            }

            @Override
            public void onFailue(int code, String msg) {
                dismissBookingToast();
                finishSwipe();
                ToastUtils.showCentetToast(getContext(), msg);
            }
        });


    }

    private void buildTags() {
        RequestManager.getInstance().getVedioTags(new GetVedioCategoryCallback() {
            @Override
            public void onSuccess(List<VideoCategoryBean> bean) {
                tags.clear();
                tags.add(new VideoCategoryBean(0, "全部", "", true));
                tags.addAll(bean);
                classRoomTagsAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailed(int code, String msg) {

            }
        });
    }

    private void finishSwipe() {
        if (companySwipe != null) {
            if (companySwipe.isEnableRefresh()) {
                companySwipe.finishRefresh(200);
            }
            if (companySwipe.isEnableLoadMore()) {
                companySwipe.finishLoadMore(200);
            }
        }
    }

    private boolean isLogin() {
        if (!UserInfoHelper.getIntance().isLogin()) {
            // TODO: 2017/12/20
//            ToastUtils.showCentetImgToast(getContext(), "还未登录");
            LoginNewActivity.start(mContext);
        }
        return UserInfoHelper.getIntance().isLogin();
    }

}
