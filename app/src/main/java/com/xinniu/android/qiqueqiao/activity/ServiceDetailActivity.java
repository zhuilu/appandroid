package com.xinniu.android.qiqueqiao.activity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebChromeClient;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.github.lzyzsd.jsbridge.BridgeWebView;
import com.github.lzyzsd.jsbridge.BridgeWebViewClient;
import com.github.lzyzsd.jsbridge.DefaultHandler;
import com.google.gson.Gson;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.util.DensityUtil;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.xinniu.android.qiqueqiao.R;
import com.xinniu.android.qiqueqiao.adapter.CoopCommentAdapter;
import com.xinniu.android.qiqueqiao.adapter.ServiceDetailCaseAdapter;
import com.xinniu.android.qiqueqiao.base.BaseActivity;
import com.xinniu.android.qiqueqiao.bean.InquireBean;
import com.xinniu.android.qiqueqiao.bean.ResourceErrorBean;
import com.xinniu.android.qiqueqiao.bean.ServiceDetailBean;
import com.xinniu.android.qiqueqiao.bean.TalkToUserBean;
import com.xinniu.android.qiqueqiao.customs.image.GlideSimpleLoader;
import com.xinniu.android.qiqueqiao.customs.image.ImageWatcherHelper;
import com.xinniu.android.qiqueqiao.customs.qldialog.QLClassRoomDialog;
import com.xinniu.android.qiqueqiao.customs.qldialog.QLTipDialog;
import com.xinniu.android.qiqueqiao.dialog.DeleteReplyDialog;
import com.xinniu.android.qiqueqiao.dialog.NoLinkDialog;
import com.xinniu.android.qiqueqiao.request.RequestManager;
import com.xinniu.android.qiqueqiao.request.callback.AllResultDoCallback;
import com.xinniu.android.qiqueqiao.request.callback.GetServiceDetailCallback;
import com.xinniu.android.qiqueqiao.request.callback.GoCommentCallback;
import com.xinniu.android.qiqueqiao.request.callback.InquireCallback;
import com.xinniu.android.qiqueqiao.request.callback.RequestCallback;
import com.xinniu.android.qiqueqiao.request.callback.TalkToUserCallback;
import com.xinniu.android.qiqueqiao.user.UserInfoHelper;
import com.xinniu.android.qiqueqiao.utils.ImageLoader;
import com.xinniu.android.qiqueqiao.utils.RoundImageView;
import com.xinniu.android.qiqueqiao.utils.ServiceBannerImgLoader;
import com.xinniu.android.qiqueqiao.utils.ShareUtils;
import com.xinniu.android.qiqueqiao.utils.ShowUtils;
import com.xinniu.android.qiqueqiao.utils.StatusBarCompat;
import com.xinniu.android.qiqueqiao.utils.StatusBarUtil;
import com.xinniu.android.qiqueqiao.utils.ToastUtils;
import com.xinniu.android.qiqueqiao.widget.CommentExpandableListView;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.listener.OnBannerListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import retrofit2.Call;

//import android.support.annotation.NonNull;
//import android.support.v4.content.ContextCompat;
import androidx.core.widget.NestedScrollView;
import androidx.appcompat.app.AppCompatDialog;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatDialog;
import androidx.core.content.ContextCompat;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
//import com.xinniu.android.qiqueqiao.utils.IMUtils;

public class ServiceDetailActivity extends BaseActivity {
    @BindView(R.id.bcollect_tv)
    TextView bcollectTv;
    @BindView(R.id.bleaveword_tv)
    TextView bleavewordTv;
    @BindView(R.id.blxto_tv)
    TextView blxtoTv;
    @BindView(R.id.ycoop_lxwordRl)
    RelativeLayout ycoopLxwordRl;
    @BindView(R.id.bleaveword_sendtv)
    TextView bleavewordSendtv;
    @BindView(R.id.mcoop_sendEt)
    EditText mcoopSendEt;
    @BindView(R.id.ycoop_sendRl)
    RelativeLayout ycoopSendRl;
    @BindView(R.id.activity_lxtv)
    LinearLayout activityLxtv;
    @BindView(R.id.bfinishImg)
    ImageView bfinishImg;
    @BindView(R.id.bedit_share)
    ImageView beditShare;
    @BindView(R.id.banner)
    Banner banner;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_01)
    TextView tv01;
    @BindView(R.id.tv_02)
    TextView tv02;
    @BindView(R.id.tv_03)
    TextView tv03;
    @BindView(R.id.tv_hot_num)
    TextView tvHotNum;
    @BindView(R.id.mcoop_detail_companyImg)
    RoundImageView mcoopDetailCompanyImg;
    @BindView(R.id.mcoop_brandName)
    TextView mcoopBrandName;
    @BindView(R.id.company_v)
    ImageView companyV;
    @BindView(R.id.mcoop_companyName)
    TextView mcoopCompanyName;
    @BindView(R.id.mcoop_company_info)
    TextView mcoopCompanyInfo;
    @BindView(R.id.ycompany_Rl)
    RelativeLayout ycompanyRl;
    @BindView(R.id.photo_recycler)
    RecyclerView photoRecycler;
    @BindView(R.id.mbridgeWeb)
    BridgeWebView webView;
    @BindView(R.id.mcoop_detail_lword)
    TextView mcoopDetailLword;
    @BindView(R.id.coop_lwordRl)
    RelativeLayout coopLwordRl;
    @BindView(R.id.mcoop_lword_recycler)
    CommentExpandableListView mcoopLwordRecycler;
    @BindView(R.id.detail_img)
    ImageView detailImg;
    @BindView(R.id.detail_tv)
    TextView detailTv;
    @BindView(R.id.bleavewordx_tv)
    TextView bleavewordxTv;
    @BindView(R.id.mcoop_perchRl)
    RelativeLayout mcoopPerchRl;
    @BindView(R.id.mscrollview)
    NestedScrollView mscrollview;
    @BindView(R.id.mcoop_refresh)
    SmartRefreshLayout mcoopRefresh;
    @BindView(R.id.bt_close)
    LinearLayout btClose;
    @BindView(R.id.bt_share)
    LinearLayout btShare;
    @BindView(R.id.tv_service)
    TextView tvService;
    @BindView(R.id.view_01)
    View view01;
    @BindView(R.id.rlayout_service)
    RelativeLayout rlayoutService;
    @BindView(R.id.tv_detail)
    TextView tvDetail;
    @BindView(R.id.view_02)
    View view02;
    @BindView(R.id.rlayout_detail)
    RelativeLayout rlayoutDetail;
    @BindView(R.id.tv_message)
    TextView tvMessage;
    @BindView(R.id.view_03)
    View view03;
    @BindView(R.id.rlayout_message)
    RelativeLayout rlayoutMessage;
    @BindView(R.id.rlayout_top)
    RelativeLayout rlayoutTop;
    @BindView(R.id.llayout_service_case)
    LinearLayout llayoutServiceCase;
    @BindView(R.id.tv_status)
    TextView tvStatus;
    @BindView(R.id.yperch_Rl)
    LinearLayout yperchRl;
    @BindView(R.id.llayout_detail)
    LinearLayout llayoutDetail;
    @BindView(R.id.tv_detail_01)
    TextView tvDetail01;

    @BindView(R.id.bt_finish)
    RelativeLayout btFinish;
    @BindView(R.id.perchtv)
    TextView perchtv;
    @BindView(R.id.perchImg)
    ImageView perchImg;
    @BindView(R.id.serviceLayout)
    LinearLayout serviceLayout;
    @BindView(R.id.messageLayout)
    LinearLayout messageLayout;
    @BindView(R.id.img_go)
    ImageView imgGo;
    @BindView(R.id.main)
    RelativeLayout main;
    private int mServiceId;
    private InputMethodManager imm;
    List<InquireBean.ListBean> datas = new ArrayList<>();
    private CoopCommentAdapter commentAdapter;
    private View footView;
    private int inquirePage = 1;
    //发布状态
    private int isVer;
    ServiceDetailBean mBean;
    private int companyId;
    private AppCompatDialog shareDialog;
    private String description;
    private String shareUrl;
    private String thumbImg;
    private String titleString;

    private int sendType;
    private int commmentId;
    private int commentxId = 0;
    private int cpositon;
    private int nposition;
    private int lwordmeasure;
    private boolean isscroll = true;
    private NoLinkDialog noLinkDialog;
    ArrayList<String> bannerImgs = new ArrayList<>();
    ArrayList<String> bannerImgsBig = new ArrayList<>();
    private ImageWatcherHelper iwHelper;
    public static void start(Context context, int serviceId) {
        Intent starter = new Intent(context, ServiceDetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt("id", serviceId);
        starter.putExtras(bundle);
        context.startActivity(starter);
    }

    public static void startx(Context context, int resourceId, int commentId) {
        Intent starter = new Intent(context, ServiceDetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt("id", resourceId);
        bundle.putInt("commentId", commentId);
        starter.putExtras(bundle);
        context.startActivity(starter);
    }

    public static void startx(Context context, int resourceId, int commentId, String mycomment) {
        Intent starter = new Intent(context, ServiceDetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt("id", resourceId);
        bundle.putInt("commentId", commentId);
        bundle.putString("MYCOMMENT", mycomment);
        starter.putExtras(bundle);
        context.startActivity(starter);
    }

    @Override
    public int getLayoutId() {

        return R.layout.activity_service_detail;
    }


    @Override
    public void initViews(Bundle savedInstanceState) {
        StatusBarUtil.StatusBarLightMode(ServiceDetailActivity.this, false);
        imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            mServiceId = bundle.getInt("id");
            commentxId = bundle.getInt("commentId");
            String commentStatus = bundle.getString("MYCOMMENT");
            if (!TextUtils.isEmpty(commentStatus)) {
                ToastUtils.showCentetToast(ServiceDetailActivity.this, commentStatus);
            }
        }
        footView = getLayoutInflater().inflate(R.layout.view_unload_more, null);
        banner.setBannerStyle(BannerConfig.NUM_INDICATOR);
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) banner.getLayoutParams();
        params.height = (int) getResources().getDimension(R.dimen.dp_360);
        banner.setLayoutParams(params);
        banner.setImageLoader(new ServiceBannerImgLoader());
        iwHelper = ImageWatcherHelper.with(this, new GlideSimpleLoader()) // 一般来讲， ImageWatcher 需要占据全屏的位置
                .setTranslucentStatus(StatusBarCompat.getStatusBarHeight(this));
        banner.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {
//                  ViewpagerImageActivity.start(ServiceDetailActivity.this, bannerImgsBig, position);
//                //取消原有默认的Activity到Activity的过渡动画
//                overridePendingTransition(R.anim.main_fade_in, 0);
                final int initPosition = 0;
                iwHelper.show(convert(bannerImgsBig),initPosition);
            }
        });
        commentAdapter = new CoopCommentAdapter(ServiceDetailActivity.this, datas);
        mcoopLwordRecycler.setAdapter(commentAdapter);
        mcoopLwordRecycler.setGroupIndicator(null);

        commentAdapter.setSetOnClick(new CoopCommentAdapter.setOnClick() {
            @Override
            public void setOnDetail(int userId) {
                PersonCentetActivity.start(ServiceDetailActivity.this, userId + "", true);
            }

            @Override
            public void setGroupComment(final int groupPosition) {
                if (datas.get(groupPosition).getFrom_uid() == UserInfoHelper.getIntance().getUserId()) {
                    DeleteReplyDialog deleteReplyDialog = new DeleteReplyDialog("回复", "删除", "取消");
                    deleteReplyDialog.setSetOnClick(new DeleteReplyDialog.setOnClick() {
                        @Override
                        public void setOnClickLeft() {
                            cpositon = groupPosition;
                            commmentId = datas.get(groupPosition).getId();
                            ycoopSendRl.setVisibility(View.VISIBLE);
                            ycoopLxwordRl.setVisibility(View.GONE);

                        }

                        @Override
                        public void setOnClickMiddle() {
                            goDelete(datas.get(groupPosition).getId(), 1, groupPosition, 0);
                        }

                        @Override
                        public void setOnClickRight() {

                        }

                        @Override
                        public void theOnDismiss(int type) {
                            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
                            mcoopSendEt.setCursorVisible(true);
                            mcoopSendEt.setFocusable(true);
                            mcoopSendEt.setFocusableInTouchMode(true);
                            mcoopSendEt.requestFocus();
                            mcoopSendEt.setHint("回复" + datas.get(groupPosition).getNickname());
                            sendType = 2;

                            imm.showSoftInput(mcoopSendEt, InputMethodManager.SHOW_FORCED);
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                                mcoopSendEt.setShowSoftInputOnFocus(true);
                            }
                        }
                    });
                    deleteReplyDialog.show(getSupportFragmentManager(), "90");

                } else {
                    cpositon = groupPosition;
                    commmentId = datas.get(groupPosition).getId();
                    ycoopSendRl.setVisibility(View.VISIBLE);
                    getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
                    mcoopSendEt.setCursorVisible(true);
                    mcoopSendEt.setFocusable(true);
                    mcoopSendEt.setFocusableInTouchMode(true);
                    mcoopSendEt.requestFocus();
                    mcoopSendEt.setHint("回复" + datas.get(groupPosition).getNickname());
                    sendType = 2;
                    ycoopLxwordRl.setVisibility(View.GONE);
                    imm.showSoftInput(mcoopSendEt, 0);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        mcoopSendEt.setShowSoftInputOnFocus(true);
                    }

                }
            }


            @Override
            public void setChildReply(final int groupPosition, final int childPosition) {
                if (datas.get(groupPosition).getReply().get(childPosition).getFrom_uid() == UserInfoHelper.getIntance().getUserId()) {
                    DeleteReplyDialog deleteReplyDialog = new DeleteReplyDialog("", "删除", "取消");
                    deleteReplyDialog.setSetOnClick(new DeleteReplyDialog.setOnClick() {
                        @Override
                        public void setOnClickLeft() {

                        }

                        @Override
                        public void setOnClickMiddle() {
                            goDelete(datas.get(groupPosition).getId(), 2, groupPosition, childPosition);
                        }

                        @Override
                        public void setOnClickRight() {

                        }

                        @Override
                        public void theOnDismiss(int type) {
//
                        }
                    });
                    deleteReplyDialog.show(getSupportFragmentManager(), "90");
                } else {
                    cpositon = groupPosition;
                    nposition = childPosition;
                    commmentId = datas.get(groupPosition).getId();
                    ycoopSendRl.setVisibility(View.VISIBLE);
                    getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
                    mcoopSendEt.setCursorVisible(true);
                    mcoopSendEt.setFocusable(true);
                    mcoopSendEt.setFocusableInTouchMode(true);
                    mcoopSendEt.requestFocus();
                    mcoopSendEt.setHint("回复" + datas.get(groupPosition).getReply().get(childPosition).getFrom_nickname());
                    sendType = 3;
                    ycoopLxwordRl.setVisibility(View.GONE);
                    imm.showSoftInput(mcoopSendEt, 0);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        mcoopSendEt.setShowSoftInputOnFocus(true);
                    }
                }
            }
        });

        mcoopLwordRecycler.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                return true;
            }
        });

        mcoopRefresh.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                inquirePage++;
                buildInquire(inquirePage, commentxId);
            }
        });

        mcoopRefresh.setEnableRefresh(false);
        mscrollview.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                int ps = banner.getBottom();
                if (scrollY > 0) {
                    int bs = banner.getBottom();
                    float percent = (float) scrollY / bs;
                    rlayoutTop.setVisibility(View.VISIBLE);
                    rlayoutTop.setAlpha(percent);
                    StatusBarUtil.StatusBarLightMode(ServiceDetailActivity.this, true);
                    if (scrollY >= ps) {

                        //按钮选中操作
                        final int as = DensityUtil.dp2px(50);

                        int ps01 = serviceLayout.getMeasuredHeight() - as;
                        //                               llayoutDetail.getTop();
                        int ps02 = messageLayout.getMeasuredHeight() - as;
                        // llayoutDetail.getBottom();
                        if (scrollY < ps01) {
                            clear();
                            tvService.setTextColor(getResources().getColor(R.color.blue_bg_4B96F3));
                            view01.setVisibility(View.VISIBLE);
                        } else if (scrollY < ps02) {
                            clear();
                            tvDetail.setTextColor(getResources().getColor(R.color.blue_bg_4B96F3));
                            view02.setVisibility(View.VISIBLE);
                        } else {
                            clear();

                            tvMessage.setTextColor(getResources().getColor(R.color.blue_bg_4B96F3));
                            view03.setVisibility(View.VISIBLE);
                        }
                    }
                } else {
                    rlayoutTop.setAlpha(0);
                    rlayoutTop.setVisibility(View.GONE);
                    StatusBarUtil.StatusBarLightMode(ServiceDetailActivity.this, false);

                }
            }
        });


        showBookingToast(1);
        loadDatax();

    }

    private List<Uri> convert(List<String> data) {
        List<Uri> list = new ArrayList<>();
        for (String d : data) list.add(Uri.parse(d));
        return list;
    }
    private void goDelete(int id, final int type, final int nposition, final int cposition) {
        RequestManager.getInstance().dodelComment(id, type, new AllResultDoCallback() {
            @Override
            public void onSuccess(String msg) {
                ToastUtils.showCentetToast(ServiceDetailActivity.this, msg);
                if (type == 1) {

                    if (datas.size() == 1) {
                        //重新更新数据
                        inquirePage = 1;
                        buildInquire(inquirePage, commentxId);
                    } else {
                        commentAdapter.deleteReplyData(nposition);
                    }
                } else if (type == 2) {
                    commentAdapter.deleteReplyData(nposition, cposition);
                }
            }

            @Override
            public void onFailed(int code, String msg) {
                ToastUtils.showCentetToast(ServiceDetailActivity.this, msg);
            }
        });
    }

    //回复留言
    private void goReply(int commentId, final int replyType, String comment, final int cposition, final int nposition) {
        showBookingToast(2);
        String realname = UserInfoHelper.getIntance().getInfoRealname();
        String nickName = UserInfoHelper.getIntance().getInfoNickName();
        int replyId = 0;
        if (!TextUtils.isEmpty(realname)) {
            nickName = realname;
        }
        String headImg = UserInfoHelper.getIntance().getHeadUrl();
        int uId = UserInfoHelper.getIntance().getUserId();
        String company = UserInfoHelper.getIntance().getInfoCompany();
        String position = UserInfoHelper.getIntance().getIngoPosition();
        int isV = UserInfoHelper.getIntance().getInfoIsV();
        if (replyType == 1) {
            replyId = datas.get(cposition).getId();
        } else if (replyType == 2) {
            replyId = datas.get(cposition).getReply().get(nposition).getId();
        }

        RequestManager.getInstance().goReplyComment(mServiceId, commentId, replyType, replyId, comment, uId, headImg, nickName, company, position, isV, 2, new GoCommentCallback() {
            @Override
            public void onSuccess(InquireBean.ListBean data) {
                dismissBookingToast();
                ShowUtils.showViewVisible(ycoopSendRl, View.GONE);
                mcoopSendEt.clearFocus();
                if (bleavewordSendtv.getWindowToken() != null) {
                    imm.hideSoftInputFromWindow(bleavewordSendtv.getWindowToken(),
                            InputMethodManager.HIDE_NOT_ALWAYS);
                    mcoopSendEt.setText("");
                }

                commentAdapter.addTheCommentReData(data, cposition, nposition);

                ToastUtils.showCentetToast(ServiceDetailActivity.this, "发送成功");
                //必须重新伸缩之后才能更新数据
                mcoopLwordRecycler.collapseGroup(cposition);
                mcoopLwordRecycler.expandGroup(cposition);
            }

            @Override
            public void onFailed(int code, String msg) {
                dismissBookingToast();
                ToastUtils.showCentetToast(ServiceDetailActivity.this, msg);
            }
        });
    }

    //获取详情数据
    private void loadDatax() {
        RequestManager.getInstance().getServiceDetails(mServiceId, new GetServiceDetailCallback() {
            @Override
            public void onSuccess(ServiceDetailBean bean) {
                initData(bean);
            }

            @Override
            public void onUndercarriage(String bean) {
                Gson gson = new Gson();
                ResourceErrorBean errorResponse = gson.fromJson(bean, ResourceErrorBean.class);

                ToastUtils.showCentetImgToast(ServiceDetailActivity.this, errorResponse.getMsg());
                if (errorResponse.getCode() == 220) {
                    blxtoTv.setEnabled(false);
                    blxtoTv.setBackground(ContextCompat.getDrawable(ServiceDetailActivity.this, R.drawable.bg_lx_detail_un));
                    try {
                        JSONObject object = new JSONObject(bean);
                        String datax = object.optString("data");
                        ServiceDetailBean coopDetailBean = gson.fromJson(datax, ServiceDetailBean.class);
                        initData(coopDetailBean);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                }
            }

            @Override
            public void onFailed(int code, String msg) {
                if (blxtoTv != null) {
                    blxtoTv.setSelected(true);
                }
                ToastUtils.showCentetImgToast(ServiceDetailActivity.this, msg);
            }
        });

    }

    private void initData(ServiceDetailBean bean) {
        mBean = bean;
        String[] sourceStrArray = bean.getThumb_img().split(",");
        String[] sourceStrArray1 = bean.getImages().split(",");
        thumbImg = sourceStrArray[0];
        titleString = bean.getTitle();
        description = "找企业服务，上企鹊桥";
        shareUrl = bean.getShare_url();
        bannerImgs.clear();
        bannerImgsBig.clear();
        for (int i = 0; i < sourceStrArray1.length; i++) {
            bannerImgsBig.add(sourceStrArray1[i]);
        }
        for (int i = 0; i < sourceStrArray1.length; i++) {
            bannerImgs.add(sourceStrArray1[i]);
        }
        banner.setImages(bannerImgs);
        banner.start();

        if (bean.getIs_verify() == 0) {
            ShowUtils.showViewVisible(tvStatus, View.VISIBLE);
            ShowUtils.showTextPerfect(tvStatus, "审核中");
            isVer = 0;
        } else if (bean.getIs_verify() == 1) {

            if (bean.getStatus() == 1) {
                ShowUtils.showViewVisible(tvStatus, View.GONE);
                isVer = 1;
            } else {
                ShowUtils.showViewVisible(tvStatus, View.VISIBLE);
                ShowUtils.showTextPerfect(tvStatus, "服务已下架");
                isVer = 3;
            }
        } else if (bean.getIs_verify() == 2) {
            ShowUtils.showViewVisible(tvStatus, View.VISIBLE);
            ShowUtils.showTextPerfect(tvStatus, "审核未通过");
            isVer = 2;
        }
        ShowUtils.showTextPerfect(tvTitle, bean.getTitle());
        String[] tags = bean.getRemark().split(",");
        if (tags.length == 3) {
            ShowUtils.showViewVisible(tv01, View.VISIBLE);
            ShowUtils.showTextPerfect(tv01, tags[0]);
            ShowUtils.showViewVisible(tv02, View.VISIBLE);
            ShowUtils.showTextPerfect(tv02, tags[1]);

            ShowUtils.showViewVisible(tv03, View.VISIBLE);
            ShowUtils.showTextPerfect(tv03, tags[2]);

        } else if (tags.length == 2) {
            ShowUtils.showViewVisible(tv01, View.VISIBLE);
            ShowUtils.showTextPerfect(tv01, tags[0]);
            ShowUtils.showViewVisible(tv02, View.VISIBLE);
            ShowUtils.showTextPerfect(tv02, tags[1]);

            ShowUtils.showViewVisible(tv03, View.GONE);
        } else if (tags.length == 1) {
            ShowUtils.showViewVisible(tv01, View.VISIBLE);
            ShowUtils.showTextPerfect(tv01, tags[0]);
            ShowUtils.showViewVisible(tv02, View.GONE);
            ShowUtils.showViewVisible(tv03, View.GONE);
        } else {
            ShowUtils.showViewVisible(tv01, View.GONE);
            ShowUtils.showViewVisible(tv02, View.GONE);
            ShowUtils.showViewVisible(tv03, View.GONE);
        }
        ShowUtils.showTextPerfect(tvHotNum, bean.getView() + "热度");

        if (bean.getCorporate_info() == null) {
            ShowUtils.showViewVisible(ycompanyRl, View.GONE);
        } else {
            companyId = bean.getCorporate_info().getId();
            ImageLoader.loadCompanyHead(bean.getCorporate_info().getLogo(), mcoopDetailCompanyImg);
            ShowUtils.showTextPerfect(mcoopBrandName, bean.getCorporate_info().getBrand());
            ShowUtils.showTextPerfect(mcoopCompanyName, bean.getCorporate_info().getName());
            String text = "<font color='#4B96F3'>" + bean.getCorporate_info().getService_count() + "</font>条服务 · " + "<font color='#4B96F3'>" + bean.getCorporate_info().getCorporate_view() + "</font>人查看";
            mcoopCompanyInfo.setText(Html.fromHtml(text));
            if (bean.getCorporate_info().getCorporate_vip() == 1) {
                companyV.setVisibility(View.VISIBLE);
            } else {
                companyV.setVisibility(View.GONE);
            }
        }
        if (bean.getService_case().size() == 0) {
            ShowUtils.showViewVisible(llayoutServiceCase, View.GONE);
        } else {
            ShowUtils.showViewVisible(llayoutServiceCase, View.VISIBLE);
            LinearLayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
            photoRecycler.setLayoutManager(manager);

            ServiceDetailCaseAdapter photoAdapter = new ServiceDetailCaseAdapter(R.layout.item_recycler_case, bean.getService_case(), this);
            photoRecycler.setAdapter(photoAdapter);
        }

        webView.getSettings().setJavaScriptEnabled(true);
        if (webView.isHardwareAccelerated()) webView.setLayerType(View.LAYER_TYPE_HARDWARE, null);

        webView.setDefaultHandler(new DefaultHandler());

        webView.setWebChromeClient(new WebChromeClient());

        webView.setWebViewClient(new MyWebViewClient(webView));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            webView.setWebContentsDebuggingEnabled(true);
        }
        webView.setLayerType(View.LAYER_TYPE_NONE, null);
        webView.loadData("<style> img{ width:100%; height:auto;} </style>" + bean.getDetails(), "text/html;charset=utf-8", "utf-8");
        if (bean.getIs_del() == 0) {
            ShowUtils.showViewVisible(yperchRl, View.GONE);
        } else {
            ShowUtils.showViewVisible(yperchRl, View.VISIBLE);
            isVer = 4;
        }

        dismissBookingToast();
        if (bean.getIs_collect() == 1) {

            bcollectTv.setSelected(true);
        } else {
            bcollectTv.setSelected(false);
        }
        inquirePage = 1;
        buildInquire(inquirePage, commentxId);
    }

    //获取留言
    private void buildInquire(final int inquirePage, final int commmentxId) {
        RequestManager.getInstance().getInquire(mServiceId, inquirePage, commmentxId, 2, new InquireCallback() {
            @Override
            public void onSuccess(InquireBean userInfoBean) {
                dismissBookingToast();
                mcoopDetailLword.setText("留言 · " + userInfoBean.getTotal());

                mcoopLwordRecycler.setVisibility(View.VISIBLE);
                if (inquirePage == 1) {
                    datas.clear();
                    if (userInfoBean.getList().size() == 0) {
                        detailImg.setBackgroundResource(R.mipmap.success_imgicon_two);
                        detailTv.setText("还没有人留言,快来问问初步合作细节吧");
                        detailTv.setTextColor(getResources().getColor(R.color._999));
                        bleavewordxTv.setVisibility(View.VISIBLE);
                        mcoopPerchRl.setVisibility(View.VISIBLE);
                        mcoopLwordRecycler.removeFooterView(footView);
                        mcoopRefresh.setEnableLoadMore(false);
                    } else {

                        mcoopPerchRl.setVisibility(View.GONE);
                        if (userInfoBean.getHasMore() == 0) {
//                            commentAdapter.setFooterView(footView);
                            mcoopLwordRecycler.addFooterView(footView);
                            mcoopRefresh.setEnableLoadMore(false);
                        } else {
                            mcoopLwordRecycler.removeFooterView(footView);
//                            commentAdapter.removeAllFooterView();
                            mcoopRefresh.setEnableLoadMore(true);
                        }
                    }
                } else {
                    if (userInfoBean.getHasMore() == 0) {
//                        commentAdapter.setFooterView(footView);
                        mcoopLwordRecycler.addFooterView(footView);
                        mcoopRefresh.setEnableLoadMore(false);
                    } else {
                        mcoopLwordRecycler.removeFooterView(footView);
//                        commentAdapter.removeAllFooterView();
                        mcoopRefresh.setEnableLoadMore(true);
                    }
                }

                datas.addAll(userInfoBean.getList());
                for (int i = 0; i < datas.size(); i++) {
                    if (datas.get(i).getReply().size() > 3) {
                        datas.get(i).setOpen(false);
                    } else {
                        datas.get(i).setOpen(true);
                    }
                }
                for (int i = 0; i < datas.size(); i++) {
                    mcoopLwordRecycler.expandGroup(i);
                }
                if (commmentxId != 0) {
                    if (datas.size() > 0) {
                        datas.get(0).setOpen(true);
                    }
                }
                commentAdapter.notifyDataSetChanged();

                finishSwipe();

            }

            @Override
            public void onFailed(int code, String msg) {
                dismissBookingToast();
                finishSwipe();
                ToastUtils.showCentetToast(ServiceDetailActivity.this, msg);
            }
        });
    }

    @OnClick({R.id.bt_finish, R.id.bcollect_tv, R.id.bleaveword_tv, R.id.bleavewordx_tv, R.id.blxto_tv, R.id.bleaveword_sendtv, R.id.bfinishImg, R.id.bedit_share, R.id.bt_close, R.id.bt_share, R.id.rlayout_service, R.id.rlayout_detail, R.id.rlayout_message, R.id.ycompany_Rl})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_finish:
                finish();
                break;
            case R.id.bcollect_tv:
                //收藏
                if (!isLoginState()) {
                    LoginNewActivity.start(mContext);
                    return;
                }
                gotoCollect();

                break;
            case R.id.bleaveword_tv:

                if (!isLoginState()) {
                    LoginNewActivity.start(mContext);
                    return;
                }
                //留言
                ycoopSendRl.setVisibility(View.VISIBLE);
                getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
                mcoopSendEt.setCursorVisible(true);
                mcoopSendEt.setFocusable(true);
                mcoopSendEt.setFocusableInTouchMode(true);
                mcoopSendEt.requestFocus();
                mcoopSendEt.setHint("禁止留联系方式，违规将被禁言");
                sendType = 1;
                ycoopLxwordRl.setVisibility(View.GONE);
                imm.showSoftInput(mcoopSendEt, 0);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    mcoopSendEt.setShowSoftInputOnFocus(true);
                }
                break;
            case R.id.bleavewordx_tv:
                //留言

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
                mcoopSendEt.setHint("禁止留联系方式，违规将被禁言");
                sendType = 1;
                ycoopLxwordRl.setVisibility(View.GONE);
                imm.showSoftInput(mcoopSendEt, 0);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    mcoopSendEt.setShowSoftInputOnFocus(true);
                }
                break;

            case R.id.blxto_tv:

                if (!isLoginState()) {
                    LoginNewActivity.start(mContext);
                    return;
                }
                //聊天
                lxData();
                break;
            case R.id.bleaveword_sendtv:
                //发送留言
                if (!TextUtils.isEmpty(mcoopSendEt.getText().toString().trim())) {
                    if (sendType == 1) {
                        sendData();
                    } else if (sendType == 2) {
                        goReply(commmentId, 1, mcoopSendEt.getText().toString(), cpositon, 0);
                    } else if (sendType == 3) {
                        goReply(commmentId, 2, mcoopSendEt.getText().toString(), cpositon, nposition);
                    }

                }
                break;
            case R.id.bfinishImg:
                finish();
                break;
            case R.id.bedit_share:
                //分享
                showShareDialog();

                break;
            case R.id.bt_close:
                finish();
                break;
            case R.id.bt_share:
                //分享
                showShareDialog();
                break;
            case R.id.rlayout_service:
                clear();
                mscrollview.scrollTo(0, 0);
                tvService.setTextColor(getResources().getColor(R.color.blue_bg_4B96F3));
                view01.setVisibility(View.VISIBLE);
                break;
            case R.id.rlayout_detail:
                clear();
                final int as = DensityUtil.dp2px(50);
                mscrollview.post(new Runnable() {
                    @Override
                    public void run() {
                        //滚动到指定位置（滚动要跳过的控件的高度的距离）
                        mscrollview.scrollTo(0, serviceLayout.getMeasuredHeight() - as);
                    }
                });

                tvDetail.setTextColor(getResources().getColor(R.color.blue_bg_4B96F3));
                view02.setVisibility(View.VISIBLE);
                break;
            case R.id.rlayout_message:
                clear();
                final int as0 = DensityUtil.dp2px(50);
                mscrollview.post(new Runnable() {
                    @Override
                    public void run() {
                        //滚动到指定位置（滚动要跳过的控件的高度的距离）
                        mscrollview.scrollTo(0, messageLayout.getMeasuredHeight() - as0);
                    }
                });
                tvMessage.setTextColor(getResources().getColor(R.color.blue_bg_4B96F3));
                view03.setVisibility(View.VISIBLE);
                break;
            case R.id.ycompany_Rl:
                CompanyInfoActivity.start(ServiceDetailActivity.this, companyId);
                break;
        }
    }

    private void lxData() {
        if (mBean.getUser_id() == UserInfoHelper.getIntance().getUserId()) {
            ToastUtils.showCentetImgToast(ServiceDetailActivity.this, "不能和自己交谈");
            return;
        }
        showBookingToast(2);
        RequestManager.getInstance().talkToUserService(mServiceId, mBean.getUser_id(), new TalkToUserCallback() {
            @Override
            public void onSuccess(TalkToUserBean bean) {
                dismissBookingToast();
                //IMUtils.singleChatForResource(ServiceDetailActivity.this, String.valueOf(mBean.getUser_id()), mBean.getRealname(), bean.getIs_qrcode(), mBean.getCorporate_info().getBrand() + mBean.getPosition(), mBean.getHead_pic(), mBean.getIs_vip() + "");
                finish();
            }

            @Override
            public void onFailue(int code, String msg) {
                dismissBookingToast();

                if (code == 301) {
                    //会员权限不够
                    showNormalDialog(msg, 301);

                } else if (code == 303) {
                    //每天沟通已达上限
                    showNormalDialog(msg, 303);

                } else {
                    ToastUtils.showCentetToast(ServiceDetailActivity.this, msg);
                }
            }
        });
    }

    private void showNormalDialog(String msg, int code) {
        if (code == 303) {
            new QLTipDialog.Builder(this)
                    .setCancelable(false)
                    .setCancelableOnTouchOutside(false)
                    .setMessage(msg)
                    .setNegativeButton("知道了", new QLTipDialog.INegativeCallback() {
                        @Override
                        public void onClick() {
//                        dissMissDialog();
                        }
                    })
                    .show(ServiceDetailActivity.this);
        } else if (code == 301) {
            noLinkDialog = new NoLinkDialog(msg);
            noLinkDialog.show(getSupportFragmentManager(), "lx");


        }

    }

    private void clear() {
        tvService.setTextColor(getResources().getColor(R.color._999));
        view01.setVisibility(View.INVISIBLE);
        tvDetail.setTextColor(getResources().getColor(R.color._999));
        view02.setVisibility(View.INVISIBLE);
        tvMessage.setTextColor(getResources().getColor(R.color._999));
        view03.setVisibility(View.INVISIBLE);
    }

    //添加留言
    private void sendData() {
        showBookingToast(2);
        String realname = UserInfoHelper.getIntance().getInfoRealname();
        String nickName = UserInfoHelper.getIntance().getInfoNickName();
        if (!TextUtils.isEmpty(realname)) {
            nickName = realname;
        }
        String headImg = UserInfoHelper.getIntance().getHeadUrl();
        int uId = UserInfoHelper.getIntance().getUserId();
        String company = UserInfoHelper.getIntance().getInfoCompany();
        String position = UserInfoHelper.getIntance().getIngoPosition();
        int isV = UserInfoHelper.getIntance().getInfoIsV();
        RequestManager.getInstance().goComment(mServiceId, mcoopSendEt.getText().toString(), nickName, headImg, uId, company, position, isV, 2, new GoCommentCallback() {
            @Override
            public void onSuccess(InquireBean.ListBean data) {
                dismissBookingToast();
                ShowUtils.showViewVisible(ycoopSendRl, View.GONE);
                mcoopSendEt.clearFocus();
                if (bleavewordSendtv.getWindowToken() != null) {
                    imm.hideSoftInputFromWindow(bleavewordSendtv.getWindowToken(),
                            InputMethodManager.HIDE_NOT_ALWAYS);
                    mcoopSendEt.setText("");
                }
                for (int i = 0; i < datas.size(); i++) {
                    datas.get(i).setNew(false);
                }
                data.setNew(true);
                datas.add(0, data);
                commentAdapter.notifyDataSetChanged();
                mcoopPerchRl.setVisibility(View.GONE);
                ToastUtils.showCentetToast(ServiceDetailActivity.this, "发送成功");

            }

            @Override
            public void onFailed(int code, String msg) {
                dismissBookingToast();
                ToastUtils.showCentetToast(ServiceDetailActivity.this, msg);
            }
        });
    }

    //去关注
    private void gotoCollect() {
        final int collect;
        if (mBean.getIs_collect() == 0) {
            collect = 1;//	1:收藏；2：取消收藏
        } else {
            collect = 2;
        }
        RequestManager.getInstance().goToServiceCollect(mServiceId, collect, new RequestCallback<String>() {
            @Override
            public void requestStart(Call call) {
                showBookingToast(2);
            }

            @Override
            public void onSuccess(String s) {
                if (collect == 1) {
                    bcollectTv.setSelected(true);
                    ToastUtils.showSuccessfulToast(ServiceDetailActivity.this, "收藏成功");
                    mBean.setIs_collect(1);//改变初始值
                } else {
                    bcollectTv.setSelected(false);
                    ToastUtils.showSuccessfulToast(ServiceDetailActivity.this, "取消收藏");
                    mBean.setIs_collect(0);
                }
            }

            @Override
            public void onFailed(int code, String msg) {
                dismissBookingToast();
                ToastUtils.showCentetImgToast(ServiceDetailActivity.this, msg);
            }

            @Override
            public void requestEnd() {
                dismissBookingToast();

            }
        });

    }

    @Override
    public void onStart() {
        super.onStart();
        banner.startAutoPlay();

    }

    @Override
    public void onStop() {
        super.onStop();
        banner.stopAutoPlay();

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

    private void showShareDialog() {
        shareDialog = new QLClassRoomDialog.Builder(mContext).setContext(ServiceDetailActivity.this).setShareCallback(new QLClassRoomDialog.ShareNewCallback() {
            @Override
            public void onClickShare(QLClassRoomDialog.ShareType type) {
                switch (type) {
                    case SHARE_WEIXIN:
                        if (isVer == 2) {
                            ToastUtils.showCentetToast(ServiceDetailActivity.this, "此服务信息未通过审核");
                            return;
                        }
                        if (isVer == 3) {
                            ToastUtils.showCentetToast(ServiceDetailActivity.this, "此服务信息已下架");
                            return;
                        }
                        if (isVer == 4) {
                            ToastUtils.showCentetToast(ServiceDetailActivity.this, "此服务信息已被删除");
                            return;
                        }
                        shareCircle(SHARE_MEDIA.WEIXIN);
                        shareDialog.dismiss();
                        break;
                    case SHARE_CIRCLE:
                        if (isVer == 2) {
                            ToastUtils.showCentetToast(ServiceDetailActivity.this, "此服务信息未通过审核");
                            return;
                        }
                        if (isVer == 3) {
                            ToastUtils.showCentetToast(ServiceDetailActivity.this, "此服务信息已下架");
                            return;
                        }
                        if (isVer == 4) {
                            ToastUtils.showCentetToast(ServiceDetailActivity.this, "此服务信息已被删除");
                            return;
                        }
                        shareCircle(SHARE_MEDIA.WEIXIN_CIRCLE);
                        shareDialog.dismiss();
                        break;
                    case SHARE_QQ:
                        if (isVer == 2) {
                            ToastUtils.showCentetToast(ServiceDetailActivity.this, "此服务信息未通过审核");
                            return;
                        }
                        if (isVer == 3) {
                            ToastUtils.showCentetToast(ServiceDetailActivity.this, "此服务信息已下架");
                            return;
                        }
                        if (isVer == 4) {
                            ToastUtils.showCentetToast(ServiceDetailActivity.this, "此服务信息已被删除");
                            return;
                        }
                        shareCircle(SHARE_MEDIA.QQ);
                        shareDialog.dismiss();
                        break;
                    case SHARE_DD:
                        if (isVer == 2) {
                            ToastUtils.showCentetToast(ServiceDetailActivity.this, "此服务信息未通过审核");
                            return;
                        }
                        if (isVer == 3) {
                            ToastUtils.showCentetToast(ServiceDetailActivity.this, "此服务信息已下架");
                            return;
                        }
                        if (isVer == 4) {
                            ToastUtils.showCentetToast(ServiceDetailActivity.this, "此服务信息已被删除");
                            return;
                        }
                        shareCircle(SHARE_MEDIA.DINGTALK);
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

    private void finishSwipe() {
        if (mcoopRefresh != null) {
            if (mcoopRefresh.isEnableRefresh()) {
                mcoopRefresh.finishRefresh(200);
            }
            if (mcoopRefresh.isEnableLoadMore()) {
                mcoopRefresh.finishLoadMore(200);
            }

        }
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

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        lwordmeasure = mcoopLwordRecycler.getTop();

        if (commentxId != 0 && isscroll) {
            if (mscrollview != null) {
                mscrollview.smoothScrollTo(0, lwordmeasure);
                isscroll = false;
            }


        }
    }


}
