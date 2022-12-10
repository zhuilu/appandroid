package com.xinniu.android.qiqueqiao.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebChromeClient;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatDialog;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.github.lzyzsd.jsbridge.BridgeWebView;
import com.github.lzyzsd.jsbridge.BridgeWebViewClient;
import com.github.lzyzsd.jsbridge.DefaultHandler;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.xinniu.android.qiqueqiao.R;
import com.xinniu.android.qiqueqiao.adapter.ClassRoomCommentOneAdapter;
import com.xinniu.android.qiqueqiao.adapter.IndexNewTwoAdapter;
import com.xinniu.android.qiqueqiao.base.BaseActivity;
import com.xinniu.android.qiqueqiao.bean.ClassRoomDetailBean;
import com.xinniu.android.qiqueqiao.bean.CommentBean;
import com.xinniu.android.qiqueqiao.customs.qldialog.QLClassRoomDialog;
import com.xinniu.android.qiqueqiao.dialog.DeleteReplyDialog;
import com.xinniu.android.qiqueqiao.request.RequestManager;
import com.xinniu.android.qiqueqiao.request.callback.AllResultDoCallback;
import com.xinniu.android.qiqueqiao.request.callback.CommentListCallback;
import com.xinniu.android.qiqueqiao.request.callback.CommentSuccessCallback;
import com.xinniu.android.qiqueqiao.request.callback.VideoDetailCallback;
import com.xinniu.android.qiqueqiao.user.UserInfoHelper;
import com.xinniu.android.qiqueqiao.utils.FullyLinearLayoutManager;
import com.xinniu.android.qiqueqiao.utils.NoScrollRecyclerView;
import com.xinniu.android.qiqueqiao.utils.ShareUtils;
import com.xinniu.android.qiqueqiao.utils.ShowUtils;
import com.xinniu.android.qiqueqiao.utils.TimeUtils;
import com.xinniu.android.qiqueqiao.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

//import android.support.annotation.NonNull;
import androidx.core.widget.NestedScrollView;
import androidx.appcompat.app.AppCompatDialog;
import androidx.recyclerview.widget.LinearLayoutManager;

public class ClassRoomDetailTwoActivity extends BaseActivity {

    @BindView(R.id.img_good)
    ImageView imgGood;
    @BindView(R.id.tv_good_num)
    TextView tvGoodNum;
    @BindView(R.id.rlayout_good)
    RelativeLayout rlayoutGood;
    @BindView(R.id.rlayout_share)
    RelativeLayout rlayoutShare;
    @BindView(R.id.llayout_comment)
    RelativeLayout llayoutComment;
    @BindView(R.id.ycoop_lxwordRl)
    RelativeLayout ycoopLxwordRl;
    @BindView(R.id.mcoop_sendEt)
    EditText mcoopSendEt;
    @BindView(R.id.ycoop_sendRl)
    RelativeLayout ycoopSendRl;
    @BindView(R.id.rlayout_bottom)
    LinearLayout rlayoutBottom;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_tag_1)
    TextView tvTag1;
    @BindView(R.id.tv_tag_2)
    TextView tvTag2;
    @BindView(R.id.tv_tag_3)
    TextView tvTag3;
    @BindView(R.id.tv_name_time)
    TextView tvNameTime;
    @BindView(R.id.mbridgeWeb)
    BridgeWebView webView;
    @BindView(R.id.llayout_detail)
    LinearLayout llayoutDetail;
    @BindView(R.id.mcompany_resource_list)
    NoScrollRecyclerView mcompanyResourceList;
    @BindView(R.id.llayout_resource)
    LinearLayout llayoutResource;
    @BindView(R.id.mcoop_detail_lword)
    TextView mcoopDetailLword;
    @BindView(R.id.coop_lwordRl)
    RelativeLayout coopLwordRl;
    @BindView(R.id.mcoop_comment_recycler)
    NoScrollRecyclerView mcoopCommentRecycler;
    @BindView(R.id.detail_img)
    ImageView detailImg;
    @BindView(R.id.detail_tv)
    TextView detailTv;
    @BindView(R.id.bleavewordx_tv)
    TextView bleavewordxTv;
    @BindView(R.id.rl_empty)
    RelativeLayout rlEmpty;
    @BindView(R.id.mscrollview)
    NestedScrollView mscrollview;
    @BindView(R.id.view)
    View view;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    @BindView(R.id.bleaveword_sendtv)
    TextView bleavewordSendtv;
    @BindView(R.id.messageLayout)
    LinearLayout messageLayout;
    private int id;
    private int mPosition;
    private int sendType;
    private int commmentId;
    private IndexNewTwoAdapter indexNewAdapter;
    private List<ClassRoomDetailBean.ResourcesListBean> datas = new ArrayList<ClassRoomDetailBean.ResourcesListBean>();

    private ClassRoomCommentOneAdapter classRoomCommentAdapter;
    private List<CommentBean.ListBean> mDatas = new ArrayList<>();
    private String description;
    private String shareUrl;
    private String thumbImg;
    private String wechatUrl;
    private String titleString;
    int page = 1;
    private InputMethodManager imm;
    ClassRoomDetailBean itemData;
    private AppCompatDialog shareDialog;

    public static void start(Context context, int id) {
        Intent starter = new Intent(context, ClassRoomDetailTwoActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt("id", id);
        starter.putExtras(bundle);
        context.startActivity(starter);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_classroom_detail_two;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        topStatusBar(true);
        Bundle bundle = getIntent().getExtras();
        id = bundle.getInt("id");
        imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        FullyLinearLayoutManager manager = new FullyLinearLayoutManager(ClassRoomDetailTwoActivity.this, LinearLayoutManager.VERTICAL, false) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        mcompanyResourceList.setLayoutManager(manager);
        indexNewAdapter = new IndexNewTwoAdapter(ClassRoomDetailTwoActivity.this, R.layout.item_index_new, datas, 1);
        mcompanyResourceList.setAdapter(indexNewAdapter);
        mcompanyResourceList.setNestedScrollingEnabled(false);
        FullyLinearLayoutManager manager1 = new FullyLinearLayoutManager(ClassRoomDetailTwoActivity.this, LinearLayoutManager.VERTICAL, false) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        mcoopCommentRecycler.setLayoutManager(manager1);
        classRoomCommentAdapter = new ClassRoomCommentOneAdapter(ClassRoomDetailTwoActivity.this, R.layout.item_classroom_comment, mDatas);
        mcoopCommentRecycler.setAdapter(classRoomCommentAdapter);
        mcoopCommentRecycler.setNestedScrollingEnabled(false);
        //下拉刷新
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                page = 1;
                getDetail();

            }
        });
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                page++;
                buildInquire(page, id);


            }
        });
        getDetail();

        classRoomCommentAdapter.setSetOnClick(new ClassRoomCommentOneAdapter.setOnClick() {
            @Override
            public void setOnDetail(int userId) {
                if (!isLoginState()) {
                    LoginNewActivity.start(mContext);
                    return;
                }
                PersonCentetActivity.start(ClassRoomDetailTwoActivity.this, userId + "", true);
            }

            @Override
            public void setGroupComment(final int position) {
                if (!isLoginState()) {
                    LoginNewActivity.start(mContext);
                    return;
                }
                mPosition = position;
                //判断是否是自己。

                if (mDatas.get(position).getUser_id() == UserInfoHelper.getIntance().getUserId()) {
                    DeleteReplyDialog deleteReplyDialog = new DeleteReplyDialog("回复", "删除", "取消");
                    deleteReplyDialog.setSetOnClick(new DeleteReplyDialog.setOnClick() {
                        @Override
                        public void setOnClickLeft() {
                            //做个延迟防止键盘弹不出
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    setClick(2, mDatas.get(position).getId(), mDatas.get(position).getRealname(), mDatas);
                                }
                            }, 500);


                        }

                        @Override
                        public void setOnClickMiddle() {
                            goDelete(mDatas.get(position).getId(), position);
                        }

                        @Override
                        public void setOnClickRight() {

                        }

                        @Override
                        public void theOnDismiss(int type) {

                        }
                    });
                    deleteReplyDialog.show(getSupportFragmentManager(), "90");

                } else {
                    setClick(2, mDatas.get(position).getId(), mDatas.get(position).getRealname(), mDatas);


                }
            }

            @Override
            public void setChildReply(int groupPosition, int childPosition) {

            }


            @Override
            public void setGood(int position) {
                mPosition = position;
                if (!isLoginState()) {
                    LoginNewActivity.start(mContext);
                    return;
                }
                if (mDatas.get(position).getIs_upvote() == 1) {
                    toUpVote(mDatas.get(position).getId(), 2, 0);
                } else {
                    toUpVote(mDatas.get(position).getId(), 2, 1);
                }


            }
        });

    }

    private void goDelete(int commentId, final int position) {
        RequestManager.getInstance().doVideoDelComment(commentId, new AllResultDoCallback() {
            @Override
            public void onSuccess(String msg) {
                ToastUtils.showCentetToast(ClassRoomDetailTwoActivity.this, msg);

                itemData.setComment_count(itemData.getComment_count() - 1);
                mcoopDetailLword.setText("评论 · " + itemData.getComment_count());
                if (mDatas.size() == 1) {
                    //重新更新数据
                    page = 1;
                    buildInquire(page, id);
                } else {
                    classRoomCommentAdapter.deleteReplyData(position);
                }

            }

            @Override
            public void onFailed(int code, String msg) {
                ToastUtils.showCentetToast(ClassRoomDetailTwoActivity.this, msg);
            }
        });


    }

    private void setClick(int type, int commment_Id, String name, List<CommentBean.ListBean> data) {
        if (type == 2) {
            commmentId = commment_Id;
            mcoopSendEt.setHint("回复" + name);
        } else {
            mcoopSendEt.setHint("发布评论");

        }
        ycoopSendRl.setVisibility(View.VISIBLE);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        mcoopSendEt.setCursorVisible(true);
        mcoopSendEt.setFocusable(true);
        mcoopSendEt.setFocusableInTouchMode(true);
        mcoopSendEt.requestFocus();
        sendType = type;
        ycoopLxwordRl.setVisibility(View.GONE);
        imm.showSoftInput(mcoopSendEt, 0);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mcoopSendEt.setShowSoftInputOnFocus(true);
        }
    }

    @OnClick({R.id.bt_return, R.id.rlayout_share_top, R.id.rlayout_good, R.id.rlayout_share, R.id.llayout_comment, R.id.bleaveword_sendtv})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_return:
                finish();
                break;
            case R.id.rlayout_share_top:
                showShareDialog();
                break;
            case R.id.rlayout_good:
                if (!isLoginState()) {
                    LoginNewActivity.start(mContext);
                    return;
                }
                if (itemData.getIs_upvote() == 1) {
                    toUpVote(id, 1, 0);
                } else {

                    toUpVote(id, 1, 1);
                }
                break;
            case R.id.rlayout_share:
                showShareDialog();
                break;
            case R.id.llayout_comment:
                if (!isLoginState()) {
                    LoginNewActivity.start(mContext);
                    return;
                }
                ycoopSendRl.setVisibility(View.VISIBLE);
                getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
                mcoopSendEt.setCursorVisible(true);
                mcoopSendEt.setFocusable(true);
                mcoopSendEt.setFocusableInTouchMode(true);
                mcoopSendEt.requestFocus();
                mcoopSendEt.setHint("发布评论");
                sendType = 1;
                ycoopLxwordRl.setVisibility(View.GONE);
                imm.showSoftInput(mcoopSendEt, 0);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    mcoopSendEt.setShowSoftInputOnFocus(true);
                }
                break;
            case R.id.bleaveword_sendtv:
                if (!TextUtils.isEmpty(mcoopSendEt.getText().toString().trim())) {
                    if (sendType == 1) {
                        sendData(0);
                    } else if (sendType == 2) {
                        sendData(commmentId);
                    }

                }
                break;
        }
    }

    private void sendData(final int p_id) {
        showBookingToast(2);
        RequestManager.getInstance().goClassRoomComment(id, mcoopSendEt.getText().toString().trim(), p_id, new CommentSuccessCallback() {
            @Override
            public void onSuccess(CommentBean.ListBean item) {
                dismissBookingToast();
                ShowUtils.showViewVisible(ycoopSendRl, View.GONE);
                mcoopSendEt.clearFocus();
                if (bleavewordSendtv.getWindowToken() != null) {
                    imm.hideSoftInputFromWindow(bleavewordSendtv.getWindowToken(),
                            InputMethodManager.HIDE_NOT_ALWAYS);
                    mcoopSendEt.setText("");
                }
                //更新
                item.setNew(true);
                for (int i = 0; i < mDatas.size(); i++) {
                    mDatas.get(i).setNew(false);
                }
                mDatas.add(0, item);
                mscrollview.scrollTo(0, 0);
                classRoomCommentAdapter.notifyDataSetChanged();
                itemData.setComment_count(itemData.getComment_count() + 1);
                mcoopDetailLword.setText("评论 · " + itemData.getComment_count());
                mscrollview.post(new Runnable() {
                    @Override
                    public void run() {
                        //滚动到指定位置（滚动要跳过的控件的高度的距离）
                        mscrollview.scrollTo(0, messageLayout.getMeasuredHeight());
                    }
                });
                ToastUtils.showCentetToast(ClassRoomDetailTwoActivity.this, "发送成功");

            }

            @Override
            public void onFailed(int code, String msg) {
                dismissBookingToast();
                ToastUtils.showCentetToast(ClassRoomDetailTwoActivity.this, msg);
            }
        });

    }

    private void getDetail() {
        showBookingToast(1);
        RequestManager.getInstance().getVedioDetail(id, new VideoDetailCallback() {
            @Override
            public void onSuccess(ClassRoomDetailBean item) {
                mcoopDetailLword.setText("评论 · " + item.getComment_count());
                itemData = item;
                tvTitle.setText(item.getTitle());
                datas.clear();
                if (item.getResources_list().size() == 0) {
                    llayoutResource.setVisibility(View.GONE);
                    view.setVisibility(View.GONE);
                } else {
                    llayoutResource.setVisibility(View.VISIBLE);
                    datas.addAll(item.getResources_list());
                    indexNewAdapter.notifyDataSetChanged();
                    view.setVisibility(View.VISIBLE);
                }

                tvNameTime.setText(item.getRealname() + "  " + TimeUtils.time22ActTime(item.getCreate_time() * 1000));


                if (item.getCategory().size() > 0) {
                    if (item.getCategory().size() == 3) {
                        tvTag1.setVisibility(View.VISIBLE);
                        tvTag2.setVisibility(View.VISIBLE);
                        tvTag3.setVisibility(View.VISIBLE);
                        tvTag1.setText(item.getCategory().get(0).getName());
                        tvTag2.setText(item.getCategory().get(1).getName());
                        tvTag3.setText(item.getCategory().get(2).getName());
                    } else if (item.getCategory().size() == 2) {
                        tvTag1.setVisibility(View.VISIBLE);
                        tvTag2.setVisibility(View.VISIBLE);
                        tvTag3.setVisibility(View.GONE);
                        tvTag1.setText(item.getCategory().get(0).getName());
                        tvTag2.setText(item.getCategory().get(1).getName());
                    } else if (item.getCategory().size() == 1) {
                        tvTag1.setVisibility(View.VISIBLE);
                        tvTag2.setVisibility(View.GONE);
                        tvTag3.setVisibility(View.GONE);
                        tvTag1.setText(item.getCategory().get(0).getName());

                    }
                } else {
                    tvTag1.setVisibility(View.GONE);
                    tvTag2.setVisibility(View.GONE);
                    tvTag3.setVisibility(View.GONE);

                }
                if (item.getUpvote_num() > 999) {
                    tvGoodNum.setText("999+");
                } else {
                    tvGoodNum.setText(item.getUpvote_num() + "");
                }

                if (item.getIs_upvote() == 1) {
                    imgGood.setBackgroundResource(R.mipmap.good_blue);
                    tvGoodNum.setTextColor(getResources().getColor(R.color.blue_bg_4B96F3));
                } else {
                    imgGood.setBackgroundResource(R.mipmap.good_gray);
                    tvGoodNum.setTextColor(getResources().getColor(R.color._666));
                }

                description = item.getIntroduce();
                thumbImg = item.getPoster();
                titleString = "异业课堂|" + item.getTitle();
                shareUrl = item.getShare_url();

                webView.getSettings().setJavaScriptEnabled(true);
                webView.getSettings().setUseWideViewPort(true);
                webView.getSettings().setLoadWithOverviewMode(true);
                if (webView.isHardwareAccelerated())
                    webView.setLayerType(View.LAYER_TYPE_HARDWARE, null);

                webView.setDefaultHandler(new DefaultHandler());

                webView.setWebChromeClient(new WebChromeClient());

                webView.setWebViewClient(new MyWebViewClient(webView));
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    webView.setWebContentsDebuggingEnabled(true);
                }
                webView.setLayerType(View.LAYER_TYPE_NONE, null);
                webView.loadData("<style> img{ width:100%; height:auto;} </style>" + item.getDetails(), "text/html;charset=utf-8", "utf-8");

                buildInquire(page, id);

            }

            @Override
            public void onFailue(int code, String msg) {
                dismissBookingToast();
                ToastUtils.showCentetToast(ClassRoomDetailTwoActivity.this, msg);
            }
        });
    }

    private void buildInquire(final int inquirePage, int id) {
        RequestManager.getInstance().getCommentList(id, inquirePage, new CommentListCallback() {
            @Override
            public void onSuccess(CommentBean userInfoBean) {

                if (inquirePage == 1) {
                    dismissBookingToast();
                    mDatas.clear();
                    if (userInfoBean.getList().size() == 0) {
                        detailImg.setBackgroundResource(R.mipmap.success_imgicon_two);
                        detailTv.setText("还没有人评论，快来抢沙发吧...");
                        detailTv.setTextColor(getResources().getColor(R.color._999));
                        bleavewordxTv.setVisibility(View.VISIBLE);
                        rlEmpty.setVisibility(View.VISIBLE);
                        classRoomCommentAdapter.removeAllFooterView();
                        refreshLayout.setEnableLoadMore(false);
                    } else {

                        rlEmpty.setVisibility(View.GONE);
                        if (userInfoBean.getHasMore() == 0) {
                            classRoomCommentAdapter.setFooterView(footView);
                            refreshLayout.setEnableLoadMore(false);
                        } else {
                            classRoomCommentAdapter.removeAllFooterView();
                            refreshLayout.setEnableLoadMore(true);
                        }
                    }
                } else {

                    if (userInfoBean.getHasMore() == 0) {
                        classRoomCommentAdapter.setFooterView(footView);
                        refreshLayout.setEnableLoadMore(false);

                    } else {
                        classRoomCommentAdapter.removeAllFooterView();
                        refreshLayout.setEnableLoadMore(true);

                    }
                }

                mDatas.addAll(userInfoBean.getList());
                classRoomCommentAdapter.notifyDataSetChanged();
                finishSwipe();
            }

            @Override
            public void onFailed(int code, String msg) {
                dismissBookingToast();
                finishSwipe();
                ToastUtils.showCentetToast(ClassRoomDetailTwoActivity.this, msg);
            }
        });
    }

    public class MyWebViewClient extends BridgeWebViewClient {

        public MyWebViewClient(BridgeWebView webView) {
            super(webView);
        }

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && webView.canGoBack()) {
            webView.goBack();
            return true;
        } else {
            finish();
            return true;
        }

    }


    private void finishSwipe() {
        if (refreshLayout != null) {
            if (refreshLayout.isEnableRefresh()) {
                refreshLayout.finishRefresh();
            }
            if (refreshLayout.isEnableLoadMore()) {
                refreshLayout.finishLoadMore();
            }
        }
    }

    private void showShareDialog() {
        shareDialog = new QLClassRoomDialog.Builder(mContext).setContext(ClassRoomDetailTwoActivity.this).setPicture(true).setShareCallback(new QLClassRoomDialog.ShareNewCallback() {
            @Override
            public void onClickShare(QLClassRoomDialog.ShareType type) {
                switch (type) {
                    case SHARE_WEIXIN:

                        shareCircle(SHARE_MEDIA.WEIXIN);
                        shareDialog.dismiss();
                        break;
                    case SHARE_CIRCLE:

                        shareCircle(SHARE_MEDIA.WEIXIN_CIRCLE);
                        shareDialog.dismiss();
                        break;
                    case SHARE_QQ:

                        shareCircle(SHARE_MEDIA.QQ);
                        shareDialog.dismiss();
                        break;
                    case SHARE_DD:

                        shareCircle(SHARE_MEDIA.DINGTALK);
                        shareDialog.dismiss();
                        break;
                    case SHARE_PICTURE:
                        if (itemData.getUser_id() != null && itemData.getUser_id() > 0) {
                            ShareClassRoomActivity.start(mContext, itemData.getPoster(), itemData.getTitle(), itemData.getUser_id(), itemData.getHead_pic(), itemData.getRealname(), " · " + itemData.getCompany() + itemData.getPosition(), shareUrl);

                        } else {
                            ShareClassRoomActivity.start(mContext, itemData.getPoster(), itemData.getTitle(), 0, "", "", "", shareUrl);
                        }


                        shareDialog.dismiss();
                        break;
                    default:

                        break;
                }
            }
        }).build();
        shareDialog.show();
    }

    private void shareCircle(SHARE_MEDIA share_media) {

        if (description == null || shareUrl == null || thumbImg == null) {
            return;
        }
        ShareUtils.shareWebUrl(
                this,
                thumbImg,
                share_media,
                shareUrl,
                titleString,
                description, new UMShareListener() {
                    @Override
                    public void onStart(SHARE_MEDIA share_media) {

                    }

                    @Override
                    public void onResult(SHARE_MEDIA share_media) {

                    }

                    @Override
                    public void onError(SHARE_MEDIA share_media, Throwable throwable) {

                    }

                    @Override
                    public void onCancel(SHARE_MEDIA share_media) {

                    }
                });

    }


    private void toUpVote(int id, final int type, final int un_upvote) {
        showBookingToast(2);
        RequestManager.getInstance().videoUpVote(id, type, un_upvote, new AllResultDoCallback() {
            @Override
            public void onSuccess(String msg) {
                dismissBookingToast();
                ToastUtils.showCentetToast(ClassRoomDetailTwoActivity.this, msg);
                if (type == 1) {
                    if (un_upvote == 1) {
                        //点赞
                        itemData.setIs_upvote(1);
                        //数量加1
                        int num = itemData.getUpvote_num() + 1;
                        itemData.setUpvote_num(num);
                        if (itemData.getUpvote_num() > 999) {
                            tvGoodNum.setText("999+");
                        } else {
                            tvGoodNum.setText(itemData.getUpvote_num() + "");
                        }
                        imgGood.setBackgroundResource(R.mipmap.good_blue);
                        tvGoodNum.setTextColor(getResources().getColor(R.color.blue_bg_4B96F3));
                    } else {
                        //点赞
                        itemData.setIs_upvote(0);
                        //数量加1
                        int num = itemData.getUpvote_num() - 1;
                        itemData.setUpvote_num(num);
                        if (itemData.getUpvote_num() > 999) {
                            tvGoodNum.setText("999+");
                        } else {
                            tvGoodNum.setText(itemData.getUpvote_num() + "");
                        }
                        imgGood.setBackgroundResource(R.mipmap.good_gray);
                        tvGoodNum.setTextColor(getResources().getColor(R.color._666));
                    }
                } else if (type == 2) {
                    if (un_upvote == 1) {
                        mDatas.get(mPosition).setIs_upvote(1);
                        int num = mDatas.get(mPosition).getUpvote_num() + 1;
                        mDatas.get(mPosition).setUpvote_num(num);
                    } else {
                        mDatas.get(mPosition).setIs_upvote(0);
                        int num = mDatas.get(mPosition).getUpvote_num() - 1;
                        mDatas.get(mPosition).setUpvote_num(num);
                    }
                    classRoomCommentAdapter.notifyDataSetChanged();

                }

            }

            @Override
            public void onFailed(int code, String msg) {
                dismissBookingToast();
                ToastUtils.showCentetToast(ClassRoomDetailTwoActivity.this, msg);
            }
        });
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // TODO Auto-generated method stub
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            if (this.getCurrentFocus() != null) {
                if (this.getCurrentFocus().getWindowToken() != null) {
                    imm.hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(),
                            InputMethodManager.HIDE_NOT_ALWAYS);
                    ycoopSendRl.setVisibility(View.GONE);
                    ycoopLxwordRl.setVisibility(View.VISIBLE);
                }
            }
        }
        return super.onTouchEvent(event);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            //当isShouldHideInput(v, ev)为true时，表示的是点击输入框区域，则需要显示键盘，同时显示光标，反之，需要隐藏键盘、光标
            if (isShouldHideInput(ycoopSendRl, ev)) {

                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm != null) {
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                    //处理Editext的光标隐藏、显示逻辑
                    mcoopSendEt.clearFocus();
                    ycoopSendRl.setVisibility(View.GONE);
                    ycoopLxwordRl.setVisibility(View.VISIBLE);
                }
            }
            return super.dispatchTouchEvent(ev);
        }
        // 必不可少，否则所有的组件都不会有TouchEvent了
        if (getWindow().superDispatchTouchEvent(ev)) {
            return true;
        }
        return super.dispatchTouchEvent(ev);
    }

    public boolean isShouldHideInput(View v, MotionEvent event) {
        if (v != null && (v instanceof RelativeLayout)) {
            int[] leftTop = {0, 0};
            //获取输入框当前的location位置
            v.getLocationInWindow(leftTop);
            int left = leftTop[0];
            int top = leftTop[1];
            int bottom = top + v.getHeight();
            int right = left + v.getWidth();
            if (event.getX() > left && event.getX() < right
                    && event.getY() > top && event.getY() < bottom) {
                // 点击的是输入框区域，保留点击EditText的事件
                return false;
            } else {
                return true;
            }
        }
        return false;
    }
}
