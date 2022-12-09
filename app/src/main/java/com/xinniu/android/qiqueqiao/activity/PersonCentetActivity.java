package com.xinniu.android.qiqueqiao.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
//import android.support.annotation.NonNull;
//import android.support.v4.content.ContextCompat;
//import android.support.v7.app.AppCompatDialog;
//import android.support.v7.widget.LinearLayoutManager;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.umeng.analytics.MobclickAgent;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.xinniu.android.qiqueqiao.R;
import com.xinniu.android.qiqueqiao.adapter.PersonInfoAdapter;
import com.xinniu.android.qiqueqiao.base.BaseActivity;
import com.xinniu.android.qiqueqiao.bean.GetUserResourcesBean;
import com.xinniu.android.qiqueqiao.bean.GoFriendApplyBean;
import com.xinniu.android.qiqueqiao.bean.TalkToUserBean;
import com.xinniu.android.qiqueqiao.customs.qldialog.QLPersonDialog;
import com.xinniu.android.qiqueqiao.customs.qldialog.QLTipDialog;
import com.xinniu.android.qiqueqiao.dialog.AlertDialogUtils;
import com.xinniu.android.qiqueqiao.dialog.DeleteReplyDialog;
import com.xinniu.android.qiqueqiao.dialog.NoLinkDialog;
import com.xinniu.android.qiqueqiao.request.RequestManager;
import com.xinniu.android.qiqueqiao.request.callback.AllResultDoCallback;
import com.xinniu.android.qiqueqiao.request.callback.GetUserResourcesCallback;
import com.xinniu.android.qiqueqiao.request.callback.GoFriendApplyCallback;
import com.xinniu.android.qiqueqiao.request.callback.TalkToUserCallback;
import com.xinniu.android.qiqueqiao.user.UserInfoHelper;
import com.xinniu.android.qiqueqiao.utils.FullyLinearLayoutManager;
//import com.xinniu.android.qiqueqiao.utils.IMUtils;
import com.xinniu.android.qiqueqiao.utils.ImageLoader;
import com.xinniu.android.qiqueqiao.utils.NoScrollRecyclerView;
import com.xinniu.android.qiqueqiao.utils.RoundImageView;
import com.xinniu.android.qiqueqiao.utils.ShareUtils;
import com.xinniu.android.qiqueqiao.utils.ShowUtils;
import com.xinniu.android.qiqueqiao.utils.StringUtils;
import com.xinniu.android.qiqueqiao.utils.TimeUtils;
import com.xinniu.android.qiqueqiao.utils.ToastUtils;
import com.xinniu.android.qiqueqiao.widget.CareButton;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by lzq on 2017/12/19.
 */

public class PersonCentetActivity extends BaseActivity {

    @BindView(R.id.head_iv)
    ImageView headIv;
    @BindView(R.id.real_name_tv)
    TextView nameTv;
    @BindView(R.id.location_tv)
    TextView locationTv;
    @BindView(R.id.camg_tv)
    TextView comyTv;
    @BindView(R.id.care_bt)
    CareButton careBt;
    @BindView(R.id.rv)
    NoScrollRecyclerView rv;
    @BindView(R.id.bt_back)
    LinearLayout closeBt;
    @BindView(R.id.emptyView)
    RelativeLayout emptyRl;
    @BindView(R.id.id_swipe_refresh_layout)
    SmartRefreshLayout mSwipeRefreshLayout;
    @BindView(R.id.isAddV)
    ImageView isAddV;
    @BindView(R.id.position_tv)
    TextView positionTv;
    @BindView(R.id.person_kingImg)
    ImageView personKingImg;
    @BindView(R.id.gotoReport)
    ImageView gotoReport;
    @BindView(R.id.mcoop_detail_companyImg)
    RoundImageView mcoopDetailCompanyImg;
    @BindView(R.id.mcoop_brandName)
    TextView mcoopBrandName;
    @BindView(R.id.mcoop_companyName)
    TextView mcoopCompanyName;
    @BindView(R.id.mcoop_company_info)
    TextView mcoopCompanyInfo;
    @BindView(R.id.ycompany_Rl)
    RelativeLayout ycompanyRl;
    @BindView(R.id.tool_bar_title)
    TextView toolBarTitle;
    @BindView(R.id.care_Rl)
    RelativeLayout careRl;
    @BindView(R.id.blxcooptv)
    TextView blxcooptv;
    @BindView(R.id.baddfriendtv)
    TextView baddfriendtv;
    @BindView(R.id.yperson_centerLl)
    LinearLayout ypersonCenterLl;
    @BindView(R.id.bsendmessagetv)
    TextView bsendmessagetv;
    @BindView(R.id.yperson_selectRl)
    RelativeLayout ypersonSelectRl;
    @BindView(R.id.gotoMore)
    ImageView gotoMore;
    @BindView(R.id.real_name_ll)
    RelativeLayout realNameLl;
    @BindView(R.id.ypersonceter_share)
    LinearLayout ypersonceterShare;
    @BindView(R.id.company_v)
    ImageView companyV;
    @BindView(R.id.person_rz)
    ImageView personRz;

    private String name;
    private FullyLinearLayoutManager mManeger;
    private String headPic;
    private int targetId;
    private final static String USER_ID = "user_id";
    private int mPage;
    private PersonInfoAdapter mAdapter;
    private List<GetUserResourcesBean.ListBean> list = new ArrayList<>();
    private int companyId = 0;
    private int page = 1;
    private int isTalk;
    private int isVip = 0;
    private String position;
    private int userId;
    private String shareCardUrl;
    private String miniUrl;
    private String description;
    private String title;
    private int friendStatus;
    private AppCompatDialog shareDialog;
    private NoLinkDialog noLinkDialog;

    public static void start(Context context, String userId, boolean isMe) {
        Intent starter = new Intent(context, PersonCentetActivity.class);
        starter.putExtra(USER_ID, Integer.valueOf(userId));
        starter.putExtra("isMe", isMe);
        context.startActivity(starter);
    }

    public static void start(Context context, String userId) {
        Intent starter = new Intent(context, PersonCentetActivity.class);
        starter.putExtra(USER_ID, Integer.valueOf(userId));
        context.startActivity(starter);
    }

    public static void start(Context context, String userId, String flag) {
        Intent starter = new Intent(context, PersonCentetActivity.class);
        starter.putExtra(USER_ID, Integer.valueOf(userId));
        starter.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(starter);
    }


    @Override
    public int getLayoutId() {
        return R.layout.activity_person_centet;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        topStatusBar(false);
        targetId = getIntent().getIntExtra("user_id", 0);
        boolean isMe = getIntent().getBooleanExtra("isMe", false);
        if (isMe) {
            toolBarTitle.setText("合作名片");
        }

        mAdapter = new PersonInfoAdapter(this, R.layout.item_person_page, list);
        mManeger = new FullyLinearLayoutManager(this, LinearLayoutManager.VERTICAL, false) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        rv.setLayoutManager(mManeger);
        rv.setAdapter(mAdapter);
        loadData(page, true, true);
        mSwipeRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                page = 1;
                loadData(page, false, false);
            }
        });
        mSwipeRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                page++;
                loadData(page, false, false);
            }
        });


    }


    private void loadData(final int page, final boolean isShow, final boolean isLoad) {
        if (isShow) {
            showBookingToast(1);
        }
        RequestManager.getInstance().getUserResources(targetId, page, new GetUserResourcesCallback() {
            @Override
            public void onSuccess(GetUserResourcesBean bean) {
                dismissBookingToast();
                if (isLoad) {
                    friendStatus = bean.getFriend_status();
                    if (friendStatus == 2) {
                        ShowUtils.showViewVisible(ypersonSelectRl, View.VISIBLE);
                        ShowUtils.showViewVisible(ypersonCenterLl, View.GONE);
                        ShowUtils.showViewVisible(bsendmessagetv, View.GONE);
                        ShowUtils.showViewVisible(ypersonceterShare, View.VISIBLE);
                    } else if (friendStatus == 1) {
                        ShowUtils.showViewVisible(ypersonSelectRl, View.VISIBLE);
                        ShowUtils.showViewVisible(ypersonCenterLl, View.GONE);
                        ShowUtils.showViewVisible(bsendmessagetv, View.VISIBLE);
                        ShowUtils.showViewVisible(ypersonceterShare, View.GONE);
                    } else {
                        ShowUtils.showViewVisible(ypersonSelectRl, View.VISIBLE);
                        ShowUtils.showViewVisible(ypersonCenterLl, View.VISIBLE);
                        ShowUtils.showViewVisible(bsendmessagetv, View.GONE);
                        ShowUtils.showViewVisible(ypersonceterShare, View.GONE);
                        isTalk = bean.getIs_talk();
                        if (isTalk == 1) {
                            ShowUtils.showTextPerfect(blxcooptv, "继续沟通");
                        } else {
                            ShowUtils.showTextPerfect(blxcooptv, "立即私聊合作");
                        }
                    }
                    userId = UserInfoHelper.getIntance().getUserId();
                    shareCardUrl = bean.getShare_user_url();
                    miniUrl = bean.getWechat_user_url();
                    description = "谈合作，上企鹊桥";

                    if (targetId == userId) {
                        //    careBt.setVisibility(View.GONE);
                        //    careRl.setVisibility(View.GONE);
                        gotoReport.setVisibility(View.GONE);

                        if (!TextUtils.isEmpty(shareCardUrl)) {
                            gotoMore.setVisibility(View.VISIBLE);
                        } else {
                            gotoMore.setVisibility(View.GONE);
                        }
                    } else {
                        //    careBt.setVisibility(View.VISIBLE);
                        //   careRl.setVisibility(View.VISIBLE);
//                        if (friendStatus == 1) {
//                            gotoReport.setVisibility(View.GONE);
                        gotoMore.setVisibility(View.VISIBLE);
//                        } else {
//                            gotoReport.setVisibility(View.VISIBLE);
//                            gotoMore.setVisibility(View.GONE);
//                        }

                    }
                }
                if (bean.getList().size() > 0) {
                    ShowUtils.showViewVisible(emptyRl, View.GONE);
                } else {
                    ShowUtils.showViewVisible(emptyRl, View.VISIBLE);
                }

                if (page == 1) {
                    list.clear();
                    if (bean.getList().size() == 0) {
                        ShowUtils.showViewVisible(emptyRl, View.VISIBLE);
                        mAdapter.removeAllFooterView();
                        mSwipeRefreshLayout.setEnableLoadMore(false);
                    } else {
                        ShowUtils.showViewVisible(emptyRl, View.GONE);
                        if (bean.getHasMore() == 0) {
                            mAdapter.setFooterView(footView);
                            mSwipeRefreshLayout.setEnableLoadMore(false);
                        } else {
                            mAdapter.removeAllFooterView();
                            mSwipeRefreshLayout.setEnableLoadMore(true);
                        }
                    }
                } else {
                    if (bean.getHasMore() == 0) {
                        mAdapter.setFooterView(footView);
                        mSwipeRefreshLayout.setEnableLoadMore(false);
                    } else {
                        mAdapter.removeAllFooterView();
                        mSwipeRefreshLayout.setEnableLoadMore(true);
                    }
                }
                name = bean.getRealname();
                isVip = bean.getIs_vip();
                if (bean.getIs_v() == 1) {
                    ShowUtils.showViewVisible(isAddV, View.VISIBLE);
                } else {
                    ShowUtils.showViewVisible(isAddV, View.GONE);
                }
                if (bean.getIs_corporate_vip() == 1) {
                    //企业会员不显示vip
                    ShowUtils.showViewVisible(companyV, View.VISIBLE);
                    ShowUtils.showViewVisible(personKingImg, View.GONE);
                    ShowUtils.showTextColor(nameTv, ContextCompat.getColor(PersonCentetActivity.this, R.color.king_color));
                } else {
                    ShowUtils.showViewVisible(companyV, View.GONE);
                    if (isVip == 1) {
                        ShowUtils.showViewVisible(personKingImg, View.VISIBLE);
                        ShowUtils.showImageResource(personKingImg, R.mipmap.vip_iconx);
                        ShowUtils.showTextColor(nameTv, ContextCompat.getColor(PersonCentetActivity.this, R.color.king_color));
                    } else if (isVip == 0) {
                        ShowUtils.showViewVisible(personKingImg, View.GONE);
                        ShowUtils.showTextColor(nameTv, ContextCompat.getColor(PersonCentetActivity.this, R.color.text_color_1));
                    } else if (isVip == 2) {
                        ShowUtils.showViewVisible(personKingImg, View.VISIBLE);
                        ShowUtils.showImageResource(personKingImg, R.mipmap.svip_iconx);
                        ShowUtils.showTextColor(nameTv, ContextCompat.getColor(PersonCentetActivity.this, R.color.king_color));
                    }
                }

                if (bean.getIs_cloud_auth() == 1) {
                    personRz.setVisibility(View.VISIBLE);
                } else {
                    personRz.setVisibility(View.GONE);
                }

                companyId = bean.getCorporate_id();
                list.addAll(bean.getList());
                mAdapter.setCompany(bean.getCompany());
                mAdapter.notifyDataSetChanged();

                nameTv.setText(StringUtils.hintString(name, 5));
                positionTv.setText(TimeUtils.getStatus(bean.getLast_login() * 1000));
                comyTv.setText(StringUtils.hintString(bean.getCompany()));
//                if (TextUtils.isEmpty(bean.getCity_name())) {
//                    locationTv.setVisibility(View.GONE);
//                } else {
                locationTv.setVisibility(View.VISIBLE);
                position = bean.getCompany() + bean.getPosition();
                locationTv.setText(position);
                //  }
                headPic = bean.getHead_pic();
                ImageLoader.loadAvter(headPic, headIv);
                careBt.initFollowStatus(bean.getFollow_status());
                careBt.setChangeState(new CareButton.changeState() {
                    @Override
                    public void changeState(boolean state) {
                        careRl.setSelected(state);
                    }
                });
                finishSwipe();
                if (bean.getCorporate_info() != null) {
                    title = "【合作名片】" + bean.getCorporate_info().getBrand() + bean.getPosition() + "-" + bean.getRealname();
                    ycompanyRl.setVisibility(View.VISIBLE);
                    ImageLoader.loadImage(bean.getCorporate_info().getLogo(), mcoopDetailCompanyImg);
                    ShowUtils.showTextPerfect(mcoopBrandName, bean.getCorporate_info().getBrand());
                    ShowUtils.showTextPerfect(mcoopCompanyName, bean.getCorporate_info().getName());
                    SpannableString string = new SpannableString(bean.getCorporate_info().getResources_count() + "条合作信息 " + "· " + bean.getCorporate_info().getUser_num() + "人 " + "· " + bean.getCorporate_info().getCompany_name());
                    ForegroundColorSpan colorSpan1 = new ForegroundColorSpan(ContextCompat.getColor(PersonCentetActivity.this, R.color.colorPrimary));
                    ForegroundColorSpan colorSpan2 = new ForegroundColorSpan(ContextCompat.getColor(PersonCentetActivity.this, R.color.colorPrimary));
                    RelativeSizeSpan sizeSpan01 = new RelativeSizeSpan(1.1f);
                    RelativeSizeSpan sizeSpan02 = new RelativeSizeSpan(1.1f);
                    string.setSpan(colorSpan1, 0, (bean.getCorporate_info().getResources_count() + "").length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
                    string.setSpan(sizeSpan01, 0, (bean.getCorporate_info().getResources_count() + "").length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);

                    string.setSpan(colorSpan2, (bean.getCorporate_info().getResources_count() + "").length() + 8, (bean.getCorporate_info().getResources_count() + "").length() + 8 + (bean.getCorporate_info().getUser_num() + "").length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
                    string.setSpan(sizeSpan02, (bean.getCorporate_info().getResources_count() + "").length() + 8, (bean.getCorporate_info().getResources_count() + "").length() + 8 + (bean.getCorporate_info().getUser_num() + "").length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);

                    ShowUtils.showTextPerfect(mcoopCompanyInfo, string);
                    companyId = bean.getCorporate_info().getId();
                } else {
                    title = "【合作名片】" + bean.getCompany() + bean.getPosition() + "-" + bean.getRealname();
                    ycompanyRl.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailed(int code, String msg) {
                dismissBookingToast();
                finishSwipe();
            }
        });
    }

    private void finishSwipe() {
        if (mSwipeRefreshLayout != null) {
            if (mSwipeRefreshLayout.isEnableRefresh()) {
                mSwipeRefreshLayout.finishRefresh();
            }
            if (mSwipeRefreshLayout.isEnableLoadMore()) {
                mSwipeRefreshLayout.finishLoadMore();
            }
        }
    }


    @OnClick({R.id.blxcooptv, R.id.baddfriendtv, R.id.bsendmessagetv, R.id.bgoto_company, R.id.gotoMore, R.id.bshare_wxtv, R.id.bshare_phototv, R.id.care_bt, R.id.bt_back, R.id.head_iv, R.id.gotoReport})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.blxcooptv:
                goToChat();
                break;
            case R.id.baddfriendtv:
                if (isTalk == 0) {
                    AlertDialogUtils.AlertDialog(PersonCentetActivity.this, "申请添加好友，将消耗1次沟通权限，互为好友，即可无限制沟通", "取消", "申请添加", new AlertDialogUtils.setOnClick() {
                        @Override
                        public void setLeftOnClick(DialogInterface dialog) {
                            dialog.dismiss();
                        }

                        @Override
                        public void setRightOnClick(DialogInterface dialog) {
                            addFriend();
                        }
                    });
                } else {
                    addFriend();
                }
                break;
            case R.id.bsendmessagetv:
                goToChat();
                break;
            case R.id.bgoto_company:
                CompanyInfoActivity.start(PersonCentetActivity.this, companyId);
                break;
            case R.id.gotoMore:
                if (targetId == userId) {
                    goShareCard();
                } else {
                    goToMore();
                }
                break;
            case R.id.bshare_wxtv:

                if (targetId == userId) {
                    MobclickAgent.onEvent(this, "usercenter_wechatShare");//统计点击次数
                }
                ShareUtils.shareWxMini(PersonCentetActivity.this, SHARE_MEDIA.WEIXIN, shareCardUrl, "这是我的合作名片，点击查看", "", miniUrl, new UMShareListener() {
                    @Override
                    public void onStart(SHARE_MEDIA share_media) {
                        showBookingToast(2);
                    }

                    @Override
                    public void onResult(SHARE_MEDIA share_media) {
                        dismissBookingToast();
                    }

                    @Override
                    public void onError(SHARE_MEDIA share_media, Throwable throwable) {
                        dismissBookingToast();
                    }

                    @Override
                    public void onCancel(SHARE_MEDIA share_media) {
                        dismissBookingToast();
                    }
                });
                break;
            case R.id.bshare_phototv:

                if (targetId == userId) {
                    MobclickAgent.onEvent(this, "usercenter_createPoster");//统计点击次数
                }
                if (!TextUtils.isEmpty(shareCardUrl)) {
                    ShareNewActivity.start(PersonCentetActivity.this, shareCardUrl, "", "person");
                } else {

                }
                break;

            case R.id.care_bt:
                careBt.click(targetId);
                break;
            case R.id.bt_back:
                finish();
                break;
            case R.id.head_iv:
                //  ImageActivity.start(PersonCentetActivity.this, headPic);
                break;
            case R.id.gotoReport:
                ReportListActivity.start(PersonCentetActivity.this, 1, targetId);
                break;

            default:
                break;
        }
    }

    private void goShareCard() {
        final DeleteReplyDialog replyDialog = new DeleteReplyDialog("", "分享名片", "取消");
        replyDialog.setSetOnClick(new DeleteReplyDialog.setOnClick() {
            @Override
            public void setOnClickLeft() {

            }

            @Override
            public void setOnClickMiddle() {
                if (!TextUtils.isEmpty(shareCardUrl)) {
                    ShareNewActivity.start(PersonCentetActivity.this, shareCardUrl, "", "person");
//                ApproveCardActivity.start(PersonCentetActivity.this,"url",shareCardUrl,);
                } else {

                }
            }

            @Override
            public void setOnClickRight() {
                replyDialog.dismiss();
            }

            @Override
            public void theOnDismiss(int type) {

            }
        });
        replyDialog.show(getFragmentManager(), "80");

    }

    private void goToMore() {
//        final DeleteReplyDialog deleteReplyDialog = new DeleteReplyDialog("举报", "删除好友", "取消");
//        deleteReplyDialog.setSetOnClick(new DeleteReplyDialog.setOnClick() {
//            @Override
//            public void setOnClickLeft() {
//                ReportListActivity.start(PersonCentetActivity.this, 1, targetId);
//            }
//
//            @Override
//            public void setOnClickMiddle() {
//                deleteFriend();
//            }
//
//            @Override
//            public void setOnClickRight() {
//                deleteReplyDialog.dismiss();
//            }
//
//            @Override
//            public void theOnDismiss(int type) {
//
//            }
//        });
//        deleteReplyDialog.show(getFragmentManager(), "90");
        boolean isFriend = false;
        if (friendStatus == 1) {
            isFriend = true;
        }
        shareDialog = new QLPersonDialog.Builder(mContext)
                .setFriend(isFriend)
                .setIsMe(false)
                .setContext(PersonCentetActivity.this).setShareCallback(new QLPersonDialog.ShareNewCallback() {
                    @Override
                    public void onClickShare(QLPersonDialog.ShareType type) {
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

                            case REPORT_GO:
                                ReportListActivity.start(PersonCentetActivity.this, 1, targetId);
                                shareDialog.dismiss();
                                break;
                            case DELETE_GO:
                                deleteFriend();
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

//        if (share_media == SHARE_MEDIA.WEIXIN) {
//            if (title == null || shareCardUrl == null || headPic == null || miniUrl == null) {
//                return;
//            }
//            ShareUtils.shareWxMini(this, share_media, shareCardUrl, title, description, miniUrl, new UMShareListener() {
//                @Override
//                public void onStart(SHARE_MEDIA share_media) {
//
//                }
//
//                @Override
//                public void onResult(SHARE_MEDIA share_media) {
//
//                    RequestManager.getInstance().dailyShare(PersonCentetActivity.this);
//                }
//
//                @Override
//                public void onError(SHARE_MEDIA share_media, Throwable throwable) {
//
//
//                }
//
//                @Override
//                public void onCancel(SHARE_MEDIA share_media) {
//
//                }
//            });
//
//
//        } else {
        if (title == null || shareCardUrl == null || headPic == null) {
            return;
        }
        ShareUtils.shareWebUrl(
                this,
                headPic,
                share_media,
                shareCardUrl,
                title,
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
        // }
    }

    private void deleteFriend() {
        showBookingToast(2);
        RequestManager.getInstance().delFriend(targetId, new AllResultDoCallback() {
            @Override
            public void onSuccess(String msg) {
                dismissBookingToast();
                page = 1;
                loadData(page, false, true);
                ToastUtils.showCentetToast(PersonCentetActivity.this, msg);

            }

            @Override
            public void onFailed(int code, String msg) {
                dismissBookingToast();
                ToastUtils.showCentetToast(PersonCentetActivity.this, msg);
            }
        });
    }

    private void addFriend() {
        RequestManager.getInstance().goFriendApply(targetId, 2, new GoFriendApplyCallback() {
            @Override
            public void onSuccess(final GoFriendApplyBean data, String msg) {
                if (data.getStatus() == 1) {
                    AlertDialogUtils.AlertDialog(PersonCentetActivity.this, msg, "知道了", "", new AlertDialogUtils.setOnClick() {
                        @Override
                        public void setLeftOnClick(DialogInterface dialog) {
                            loadData(page, false, true);
                            //IMUtils.singleChatForResource(PersonCentetActivity.this, String.valueOf(targetId), name, 0, position, headPic, isVip + "");

                            dialog.dismiss();
                        }

                        @Override
                        public void setRightOnClick(DialogInterface dialog) {

                        }
                    });
                } else if (data.getStatus() == 2) {
                    loadData(page, false, true);
                    ToastUtils.showCentetToast(PersonCentetActivity.this, msg);
                    //IMUtils.singleChatForResource(PersonCentetActivity.this, String.valueOf(targetId), name, 0, position, headPic, isVip + "");
                } else {
                    AlertDialogUtils.AlertDialog(PersonCentetActivity.this, msg, "知道了", "", new AlertDialogUtils.setOnClick() {
                        @Override
                        public void setLeftOnClick(DialogInterface dialog) {
                            dialog.dismiss();
                        }

                        @Override
                        public void setRightOnClick(DialogInterface dialog) {

                        }
                    });
                }
            }

            @Override
            public void onFailed(int code, String msg) {
                ToastUtils.showCentetToast(PersonCentetActivity.this, msg);
            }
        });
    }

    private void goToChat() {
        if (name == null) {
            return;
        }
        if (targetId == UserInfoHelper.getIntance().getUserId()) {
            ToastUtils.showCentetImgToast(PersonCentetActivity.this, "不能和自己交谈");
            return;
        }
        showBookingToast(2);
        RequestManager.getInstance().talkToUserNOResouceId(targetId, new TalkToUserCallback() {
            @Override
            public void onSuccess(TalkToUserBean bean) {
                dismissBookingToast();
                //IMUtils.singleChatForResource(PersonCentetActivity.this, String.valueOf(targetId), name, bean.getIs_qrcode(), position, headPic, 0 + "");

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
                    ToastUtils.showCentetToast(PersonCentetActivity.this, msg);
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
                    .show(PersonCentetActivity.this);
        } else if (code == 301) {
            noLinkDialog = new NoLinkDialog(msg);
            noLinkDialog.show(getFragmentManager(), "lx");

        }

    }
}
