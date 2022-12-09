package com.xinniu.android.qiqueqiao.fragment.classroom;

import android.os.Bundle;
import android.os.Handler;
//import android.support.v4.widget.NestedScrollView;
//import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.xinniu.android.qiqueqiao.R;
import com.xinniu.android.qiqueqiao.activity.LoginNewActivity;
import com.xinniu.android.qiqueqiao.activity.PersonCentetActivity;
import com.xinniu.android.qiqueqiao.adapter.ClassRoomCommentOneAdapter;
import com.xinniu.android.qiqueqiao.base.LazyBaseFragment;
import com.xinniu.android.qiqueqiao.bean.CommentBean;
import com.xinniu.android.qiqueqiao.dialog.DeleteReplyDialog;
import com.xinniu.android.qiqueqiao.request.RequestManager;
import com.xinniu.android.qiqueqiao.request.callback.AllResultDoCallback;
import com.xinniu.android.qiqueqiao.request.callback.CommentListCallback;
import com.xinniu.android.qiqueqiao.user.UserInfoHelper;
import com.xinniu.android.qiqueqiao.utils.FullyLinearLayoutManager;
import com.xinniu.android.qiqueqiao.utils.NoScrollRecyclerView;
import com.xinniu.android.qiqueqiao.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class ClassRoomCommentFragment extends LazyBaseFragment {
    @BindView(R.id.detail_img)
    ImageView detailImg;
    @BindView(R.id.detail_tv)
    TextView detailTv;
    @BindView(R.id.bleavewordx_tv)
    TextView bleavewordxTv;
    @BindView(R.id.rl)
    RelativeLayout rl;
    @BindView(R.id.tv_empty)
    TextView tvEmpty;
    @BindView(R.id.mcoop_perchRl)
    RelativeLayout mcoopPerchRl;

    int page = 1;
    @BindView(R.id.mcoop_comment_recycler)
    NoScrollRecyclerView mcoopCommentRecycler;
    @BindView(R.id.mscrollview)
    NestedScrollView mscrollview;

    private int id;
    private int mPosition;

    private ClassRoomCommentOneAdapter classRoomCommentAdapter;
    private List<CommentBean.ListBean> datas = new ArrayList<>();


    //声明内部定义的回调接口
    CallBackListener callBackListener;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_classroom_comment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //通过getActivity()获取用于回调修改文本方法的接口
        callBackListener = (CallBackListener) getActivity();
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        if (getArguments() != null) {
            id = getArguments().getInt("id");
        }

        FullyLinearLayoutManager linearLayoutManager = new FullyLinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        mcoopCommentRecycler.setLayoutManager(linearLayoutManager);
        classRoomCommentAdapter = new ClassRoomCommentOneAdapter(getActivity(), R.layout.item_classroom_comment, datas);
        mcoopCommentRecycler.setAdapter(classRoomCommentAdapter);
        classRoomCommentAdapter.setEnableLoadMore(true);
        classRoomCommentAdapter.bindToRecyclerView(mcoopCommentRecycler);
        //该方法需要在bindToRecyclerView()之后调用，或者在adapter.setOnLoadMoreListener之后调用，确保RecyclerView对象已与Adapter绑定
        //判断数据是否满屏，如果没有满屏，那么将会隐藏加载更多，否则显示
        classRoomCommentAdapter.disableLoadMoreIfNotFullPage();
        mcoopCommentRecycler.setNestedScrollingEnabled(false);

        classRoomCommentAdapter.setSetOnClick(new ClassRoomCommentOneAdapter.setOnClick() {
            @Override
            public void setOnDetail(int userId) {
                if (!isLogin()) {
                    LoginNewActivity.start(mContext);
                    return;
                }
                PersonCentetActivity.start(getActivity(), userId + "", true);
            }

            @Override
            public void setGroupComment(final int position) {
                if (!isLogin()) {
                    LoginNewActivity.start(mContext);
                    return;
                }
                mPosition = position;
                //判断是否是自己。

                if (datas.get(position).getUser_id() == UserInfoHelper.getIntance().getUserId()) {
                    DeleteReplyDialog deleteReplyDialog = new DeleteReplyDialog("回复", "删除", "取消");
                    deleteReplyDialog.setSetOnClick(new DeleteReplyDialog.setOnClick() {
                        @Override
                        public void setOnClickLeft() {
                            //做个延迟防止键盘弹不出
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    callBackListener.setClick(2, datas.get(position).getId(), datas.get(position).getRealname(), datas);
                                }
                            }, 500);


                        }

                        @Override
                        public void setOnClickMiddle() {
                            goDelete(datas.get(position).getId(), position);
                        }

                        @Override
                        public void setOnClickRight() {

                        }

                        @Override
                        public void theOnDismiss(int type) {

                        }
                    });
                    deleteReplyDialog.show(getActivity().getFragmentManager(), "90");

                } else {
                    callBackListener.setClick(2, datas.get(position).getId(), datas.get(position).getRealname(), datas);


                }
            }

            @Override
            public void setChildReply(int groupPosition, int childPosition) {

            }


            @Override
            public void setGood(int position) {
                mPosition = position;
                if (!isLogin()) {
                    LoginNewActivity.start(mContext);
                    return;
                }
                if (datas.get(position).getIs_upvote() == 1) {
                    toUpVote(datas.get(position).getId(), 2, 0);
                } else {
                    toUpVote(datas.get(position).getId(), 2, 1);
                }


            }
        });
        classRoomCommentAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                page++;
                buildInquire(page, id);

            }
        });

        buildInquire(page, id);
    }

    private void goDelete(int commentId, final int position) {
        RequestManager.getInstance().doVideoDelComment(commentId, new AllResultDoCallback() {
            @Override
            public void onSuccess(String msg) {
                ToastUtils.showCentetToast(getActivity(), msg);


                if (datas.size() == 1) {
                    //重新更新数据
                    page = 1;
                    buildInquire(page, id);
                } else {
                    classRoomCommentAdapter.deleteReplyData(position);
                }

            }

            @Override
            public void onFailed(int code, String msg) {
                ToastUtils.showCentetToast(getActivity(), msg);
            }
        });


    }

    @Override
    protected void lazyLoad() {

    }

    private void buildInquire(final int inquirePage, int id) {
        RequestManager.getInstance().getCommentList(id, inquirePage, new CommentListCallback() {
            @Override
            public void onSuccess(CommentBean userInfoBean) {

                if (inquirePage == 1) {
                    datas.clear();
                    if (userInfoBean.getList().size() == 0) {
                        detailImg.setBackgroundResource(R.mipmap.success_imgicon_two);
                        detailTv.setText("还没有人评论，快来抢沙发吧...");
                        detailTv.setTextColor(getResources().getColor(R.color._999));
                        bleavewordxTv.setVisibility(View.VISIBLE);
                        mcoopPerchRl.setVisibility(View.VISIBLE);
                        classRoomCommentAdapter.removeFooterView(footView);
                        classRoomCommentAdapter.setEnableLoadMore(false);
                    } else {

                        mcoopPerchRl.setVisibility(View.GONE);
                        if (userInfoBean.getHasMore() == 0) {
                            classRoomCommentAdapter.addFooterView(footView);
                            classRoomCommentAdapter.setEnableLoadMore(false);
                            classRoomCommentAdapter.loadMoreEnd(true);
                        } else {
                            classRoomCommentAdapter.removeFooterView(footView);
                            classRoomCommentAdapter.setEnableLoadMore(true);
                            classRoomCommentAdapter.loadMoreComplete();
                        }
                    }
                } else {

                    if (userInfoBean.getHasMore() == 0) {
                        classRoomCommentAdapter.addFooterView(footView);
                        classRoomCommentAdapter.setEnableLoadMore(false);
                        classRoomCommentAdapter.loadMoreEnd(true);

                    } else {
                        classRoomCommentAdapter.removeFooterView(footView);
                        classRoomCommentAdapter.setEnableLoadMore(true);
                        classRoomCommentAdapter.loadMoreComplete();

                    }
                }

                datas.addAll(userInfoBean.getList());
                classRoomCommentAdapter.notifyDataSetChanged();

            }

            @Override
            public void onFailed(int code, String msg) {
                classRoomCommentAdapter.loadMoreFail();
                ToastUtils.showCentetToast(getActivity(), msg);
            }
        });
    }


    private boolean isLogin() {
        if (!UserInfoHelper.getIntance().isLogin()) {
            // TODO: 2017/12/20
//            ToastUtils.showCentetImgToast(getContext(), "还未登录");
            LoginNewActivity.start(mContext);
        }
        return UserInfoHelper.getIntance().isLogin();
    }

    private void toUpVote(int id, final int type, final int un_upvote) {
        showBookingToast(2);
        RequestManager.getInstance().videoUpVote(id, type, un_upvote, new AllResultDoCallback() {
            @Override
            public void onSuccess(String msg) {
                dismissBookingToast();
                ToastUtils.showCentetToast(getActivity(), msg);
                //评论点赞,un_upvote 1点赞，0取消点赞
                if (un_upvote == 1) {
                    datas.get(mPosition).setIs_upvote(1);
                    int num = datas.get(mPosition).getUpvote_num() + 1;
                    datas.get(mPosition).setUpvote_num(num);
                } else {
                    datas.get(mPosition).setIs_upvote(0);
                    int num = datas.get(mPosition).getUpvote_num() - 1;
                    datas.get(mPosition).setUpvote_num(num);
                }
                classRoomCommentAdapter.notifyDataSetChanged();

            }

            @Override
            public void onFailed(int code, String msg) {
                dismissBookingToast();
                ToastUtils.showCentetToast(getActivity(), msg);
            }
        });
    }


    @OnClick(R.id.bleavewordx_tv)
    public void onClick() {

        if (!isLogin()) {
            LoginNewActivity.start(mContext);
            return;
        }
        callBackListener.setClick(1, 0, "", datas);


    }

    //设置用于修改文本的回调接口
    public static interface CallBackListener {
        public void setClick(int sendType, int commmentId, String name, List<CommentBean.ListBean> datas);
    }

    public void notify(CommentBean.ListBean data) {
        for (int i = 0; i < datas.size(); i++) {
            datas.get(i).setNew(false);
        }
        datas.add(0, data);
        mscrollview.scrollTo(0, 0);
        classRoomCommentAdapter.notifyDataSetChanged();
    }
}
