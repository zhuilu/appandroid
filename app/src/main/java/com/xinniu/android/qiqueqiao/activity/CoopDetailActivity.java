package com.xinniu.android.qiqueqiao.activity;

import android.Manifest;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.SparseArray;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDialog;
import androidx.core.content.ContextCompat;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.FutureTarget;

import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.request.transition.Transition;
import com.google.gson.Gson;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.umeng.analytics.MobclickAgent;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.xinniu.android.qiqueqiao.R;
import com.xinniu.android.qiqueqiao.adapter.CoopCommentAdapter;
import com.xinniu.android.qiqueqiao.adapter.CoopDetailNeedeAdapter;
import com.xinniu.android.qiqueqiao.adapter.CoopDetailOfferAdapter;
import com.xinniu.android.qiqueqiao.adapter.CoopDetailPhotoAdapter;
import com.xinniu.android.qiqueqiao.adapter.CoopHeadLxAdapter;
import com.xinniu.android.qiqueqiao.base.BaseActivity;
import com.xinniu.android.qiqueqiao.bean.CoopDetailBean;
import com.xinniu.android.qiqueqiao.bean.GoToCollectBean;
import com.xinniu.android.qiqueqiao.bean.InquireBean;
import com.xinniu.android.qiqueqiao.bean.ResourceErrorBean;
import com.xinniu.android.qiqueqiao.bean.TalkToUserBean;
import com.xinniu.android.qiqueqiao.bean.WechatQr;
import com.xinniu.android.qiqueqiao.common.Constants;
import com.xinniu.android.qiqueqiao.customs.image.GlideSimpleLoader;
import com.xinniu.android.qiqueqiao.customs.image.ImageWatcher;
import com.xinniu.android.qiqueqiao.customs.image.ImageWatcherHelper;
import com.xinniu.android.qiqueqiao.customs.qldialog.QLTipDialog;
import com.xinniu.android.qiqueqiao.customs.qldialog.QLTipTwoDialog;
import com.xinniu.android.qiqueqiao.customs.qldialog.QLnewDialog;
import com.xinniu.android.qiqueqiao.dialog.DeleteReplyDialog;
import com.xinniu.android.qiqueqiao.dialog.NoLinkDialog;
import com.xinniu.android.qiqueqiao.request.RequestManager;
import com.xinniu.android.qiqueqiao.request.RetrofitHelper;
import com.xinniu.android.qiqueqiao.request.callback.AllResultDoCallback;
import com.xinniu.android.qiqueqiao.request.callback.CommonCallback;
import com.xinniu.android.qiqueqiao.request.callback.GetCoopDetailCallback;
import com.xinniu.android.qiqueqiao.request.callback.GetWechatQrCallback;
import com.xinniu.android.qiqueqiao.request.callback.GoCommentCallback;
import com.xinniu.android.qiqueqiao.request.callback.GotoCollectCallback;
import com.xinniu.android.qiqueqiao.request.callback.InquireCallback;
import com.xinniu.android.qiqueqiao.request.callback.MessageCallback;
import com.xinniu.android.qiqueqiao.request.callback.RequestCallback;
import com.xinniu.android.qiqueqiao.request.callback.TalkToUserCallback;
import com.xinniu.android.qiqueqiao.request.converter.ResultDO;
import com.xinniu.android.qiqueqiao.user.UserInfoHelper;
import com.xinniu.android.qiqueqiao.utils.BitmapUtils;
import com.xinniu.android.qiqueqiao.utils.ComUtils;
import com.xinniu.android.qiqueqiao.utils.ImageLoader;
import com.xinniu.android.qiqueqiao.utils.RoundImageView;
import com.xinniu.android.qiqueqiao.utils.ShareUtils;
import com.xinniu.android.qiqueqiao.utils.ShowUtils;
import com.xinniu.android.qiqueqiao.utils.StatusBarCompat;
import com.xinniu.android.qiqueqiao.utils.TimeUtils;
import com.xinniu.android.qiqueqiao.utils.ToastUtils;
import com.xinniu.android.qiqueqiao.widget.CircleImageView;
import com.xinniu.android.qiqueqiao.widget.CommentExpandableListView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;
import retrofit2.Call;

//import android.support.annotation.NonNull;
//import android.support.v4.content.ContextCompat;
import androidx.core.widget.NestedScrollView;
import androidx.appcompat.app.AppCompatDialog;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
//import com.xinniu.android.qiqueqiao.utils.IMUtils;

/**
 * ????????????
 * Created by yuchance on 2018/3/31.
 */

public class CoopDetailActivity extends BaseActivity {

    @BindView(R.id.bt_close)
    LinearLayout btClose;
    @BindView(R.id.item_index_recycler_img)
    CircleImageView itemIndexRecyclerImg;
    @BindView(R.id.item_index_nameTv)
    TextView itemIndexNameTv;
    @BindView(R.id.item_index_position)
    TextView itemIndexPosition;

    @BindView(R.id.detail_type)
    TextView detailType;
    @BindView(R.id.item_index_recycler_mannertv)
    TextView itemIndexRecyclerMannertv;
    @BindView(R.id.coop_detail_day)
    TextView coopDetailDay;

    @BindView(R.id.coop_detail_photo_recycler)
    RecyclerView coopDetailPhotoRecycler;


    @BindView(R.id.blxto_tv)
    TextView activity_lxtv;


    List<String> photoList = new ArrayList<>();
    @BindView(R.id.coop_detail_time)
    TextView DetailTime;


    private final static String RESOURCEID = "detailId";

    private final static String COMMENTID = "commentId";


    @BindView(R.id.title_tv)
    TextView titleTv;

    @BindView(R.id.detail_fx)
    ImageView detailFx;
    @BindView(R.id.tool_bar)
    RelativeLayout toolBar;

    @BindView(R.id.item_index_isv_img)
    ImageView IsvImg;
    @BindView(R.id.item_index_vipImg)
    ImageView itemIndexVipImg;
    @BindView(R.id.item_index_recycler_placetv)
    TextView itemIndexRecyclerPlacetv;
    //    @BindView(R.id.coop_moffer_label)
//    TagFlowLayout coopMofferLabel;
    @BindView(R.id.coop_moffer_title)
    TextView coopMofferTitle;
    @BindView(R.id.coop_moffer_content)
    TextView coopMofferContent;
    @BindView(R.id.coop_moffer_contentLl)
    LinearLayout coopMofferContentLl;
    //    @BindView(R.id.coop_mneed_label)
//    TagFlowLayout coopMneedLabel;
    @BindView(R.id.coop_mneed_title)
    TextView coopMneedTitle;
    @BindView(R.id.coop_mneed_content)
    TextView coopMneedContent;
    @BindView(R.id.coop_mneed_contentLl)
    LinearLayout coopMneedContentLl;
    @BindView(R.id.activity_coop_detail_look)
    TextView activityCoopDetailLook;
    @BindView(R.id.coopdetail_Rl)
    RelativeLayout coopdetailRl;
    @BindView(R.id.mcoop_detail_warningRl)
    RelativeLayout coopDetailWarningRl;
    @BindView(R.id.mcoop_detail_stateTv)
    TextView mcoopDetailStateTv;
    @BindView(R.id.mcoop_detail_companyImg)
    RoundImageView mcoopDetailCompanyImg;
    @BindView(R.id.mcoop_brandName)
    TextView mcoopBrandName;
    @BindView(R.id.mcoop_companyName)
    TextView mcoopCompanyName;
    @BindView(R.id.mcoop_company_info)
    TextView mcoopCompanyInfo;
    @BindView(R.id.bgoto_company)
    RelativeLayout bgotoCompany;
    @BindView(R.id.ycompany_Rl)
    RelativeLayout ycompanyRl;
    @BindView(R.id.bcollect_status)
    ImageView bcollectStatus;
    @BindView(R.id.yperch_Rl)
    RelativeLayout yperchRl;
    @BindView(R.id.coop_detail_recycler)
    RecyclerView coopDetailRecycler;
    @BindView(R.id.mcoop_detail_linktv)
    TextView mcoopDetailLinktv;
    @BindView(R.id.mcoop_detail_lword)
    TextView mcoopDetailLword;
    @BindView(R.id.mcoop_lword_recycler)
    CommentExpandableListView mcoopLwordRecycler;
    @BindView(R.id.bcollect_tv)
    TextView bcollectTv;
    @BindView(R.id.bleaveword_tv)
    TextView bleavewordTv;
    @BindView(R.id.bgoto_communicationRl)
    RelativeLayout bgotoCommunicationRl;
    @BindView(R.id.bleavewordx_tv)
    TextView bleavewordxTv;
    @BindView(R.id.mcoop_perchRl)
    RelativeLayout mcoopPerchRl;
    @BindView(R.id.mcoop_refresh)
    SmartRefreshLayout mcoopRefresh;
    @BindView(R.id.bleaveword_sendtv)
    TextView bleavewordSendtv;
    @BindView(R.id.mcoop_sendEt)
    EditText mcoopSendEt;
    @BindView(R.id.ycoop_sendRl)
    RelativeLayout ycoopSendRl;
    @BindView(R.id.mscrollview)
    NestedScrollView mscrollview;
    @BindView(R.id.coop_lwordRl)
    RelativeLayout coopLwordRl;
    @BindView(R.id.ycoop_photoLl)
    LinearLayout ycoopPhotoLl;
    @BindView(R.id.ycoop_lxwordRl)
    RelativeLayout ycoopLxwordRl;
    //    @BindView(R.id.moffer_titletv)
//    TextView mofferTitletv;
//    @BindView(R.id.mneed_titletv)
//    TextView mneedTitletv;
    @BindView(R.id.bussiness_tv)
    TextView bussinessTv;
    @BindView(R.id.line_viewx)
    View lineViewx;
    @BindView(R.id.goimg)
    ImageView goimg;
    @BindView(R.id.detail_img)
    ImageView detailImg;
    @BindView(R.id.detail_tv)
    TextView detailTv;
    @BindView(R.id.perchtv)
    TextView perchtv;
    @BindView(R.id.perchImg)
    ImageView perchImg;
    @BindView(R.id.llayout_empty)
    LinearLayout llayoutEmpty;
    @BindView(R.id.company_vip_img)
    ImageView companyVipImg;
    @BindView(R.id.view)
    View view;
    @BindView(R.id.rlayout_danbao)
    RelativeLayout rlayoutDanbao;
    @BindView(R.id.person_rz)
    ImageView personRz;
    @BindView(R.id.rcy_offer)
    RecyclerView rcyOffer;
    @BindView(R.id.rcy_need)
    RecyclerView rcyNeed;
    private int page;
    private int id;
    //    private CoopLxAdapter coopLxAdapter;
    public static int REQUESTREFRESH = 2018;
    private CoopHeadLxAdapter coopHeadLxAdapter;

    private String shareUrl;
    private StringBuilder builder = new StringBuilder();
    private String headPic = "";
    private StringBuilder titleString = new StringBuilder();
    private Bitmap mQRBitmap;//???????????????
    private static final int PERMISSION_WRITE_EXTERNAL_STORAGE = 1000;


    private List<CoopDetailBean.ZListBean> lxList = new ArrayList<>();
    private int userId;
    private int resourceId;
    private String name;
    private int companyId;
    private String wechatUrl;
    private int isCollect = 0;
    private String qrUrl;
    private Call mCall;
    private AppCompatDialog shareDialog;
    //????????????
    private int isVer;
    //??????????????????????????????
    private boolean isMeRe;
    private int is_closeComment;//??????????????????
    private View footView;
    private NoLinkDialog noLinkDialog;
    private int inquirePage = 1;
    List<InquireBean.ListBean> datas = new ArrayList<>();
    private CoopCommentAdapter commentAdapter;
    private InputMethodManager imm;
    private int sendType;
    private int commmentId;
    private int commentxId = 0;
    private int cpositon;
    private int lwordmeasure;
    private boolean isscroll = true;
    private int meId;
    private int nposition;
    private List<String> thumbList = new ArrayList<>();
    private String company = "";
    private String position = "";
    private int isVip;
    private int p_id;
    private String p_name;
    public static String MYCOMMENT = "MYCOMMENT";

    private CoopDetailBean mBeanData;//??????????????????
    private String mImageMa = "";//???????????????
    private ImageWatcherHelper iwHelper;
    private String imgUrl;

    public static void start(Context context, int resourceId) {
        Intent starter = new Intent(context, CoopDetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt(RESOURCEID, resourceId);
        starter.putExtras(bundle);
        context.startActivity(starter);
    }

    public static void startx(Context context, int resourceId, int commentId) {
        Intent starter = new Intent(context, CoopDetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt(RESOURCEID, resourceId);
        bundle.putInt(COMMENTID, commentId);
        starter.putExtras(bundle);
        context.startActivity(starter);
    }


    public static void start(AppCompatActivity context, int resourceId, int requestCode) {
        Intent starter = new Intent(context, CoopDetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt(RESOURCEID, resourceId);
        starter.putExtras(bundle);
        context.startActivityForResult(starter, requestCode);
    }

    public static void startx(Context context, int resourceId, int commentId, String mycomment) {
        Intent starter = new Intent(context, CoopDetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt(RESOURCEID, resourceId);
        bundle.putInt(COMMENTID, commentId);
        bundle.putString(MYCOMMENT, mycomment);
        starter.putExtras(bundle);
        context.startActivity(starter);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_coop_detail;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        topStatusBar(true);
//        boolean isTranslucentStatus = false;
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            isTranslucentStatus = true;
//        }
        meId = UserInfoHelper.getIntance().getUserId();
        imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        Bundle bundle = getIntent().getExtras();
        footView = getLayoutInflater().inflate(R.layout.view_unload_more, null);
        footView.setBackgroundColor(getResources().getColor(R.color.bg_gray));
        if (bundle != null) {
            id = bundle.getInt("detailId");
            commentxId = bundle.getInt("commentId");
            String commentStatus = bundle.getString(MYCOMMENT);
            if (!TextUtils.isEmpty(commentStatus)) {
                ToastUtils.showCentetToast(CoopDetailActivity.this, commentStatus);
            }
        }
//        mcoopRefresh.setEnableFooterTranslationContent(true);
//        mcoopRefresh.setEnableNestedScroll(false);
//        mcoopRefresh.setEnableOverScrollDrag(true);

        // mcoopRefresh.setFooterHeight(5);
        page = 1;
        commentAdapter = new CoopCommentAdapter(CoopDetailActivity.this, datas);
        mcoopLwordRecycler.setAdapter(commentAdapter);
        mcoopLwordRecycler.setGroupIndicator(null);

        commentAdapter.setSetOnClick(new CoopCommentAdapter.setOnClick() {
            @Override
            public void setOnDetail(int userId) {
                PersonCentetActivity.start(CoopDetailActivity.this, userId + "", true);
            }

            @Override
            public void setGroupComment(final int groupPosition) {
                if (datas.get(groupPosition).getFrom_uid() == meId) {
                    DeleteReplyDialog deleteReplyDialog = new DeleteReplyDialog("??????", "??????", "??????");
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
                            mcoopSendEt.setHint("??????" + datas.get(groupPosition).getNickname());
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
                    mcoopSendEt.setHint("??????" + datas.get(groupPosition).getNickname());
                    sendType = 2;
                    ycoopLxwordRl.setVisibility(View.GONE);
                    imm.showSoftInput(mcoopSendEt, 0);
                    mcoopSendEt.setShowSoftInputOnFocus(true);

                }
            }


            @Override
            public void setChildReply(final int groupPosition, final int childPosition) {
                if (datas.get(groupPosition).getReply().get(childPosition).getFrom_uid() == meId) {
                    DeleteReplyDialog deleteReplyDialog = new DeleteReplyDialog("", "??????", "??????");
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
                    mcoopSendEt.setHint("??????" + datas.get(groupPosition).getReply().get(childPosition).getFrom_nickname());
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
                buildInquire(inquirePage, commentxId, is_closeComment);
            }
        });

        mcoopRefresh.setEnableRefresh(false);

        showBookingToast(1);
        loadDatax();
        iwHelper = ImageWatcherHelper.with(this, new GlideSimpleLoader()) // ??????????????? ImageWatcher ???????????????????????????
                .setTranslucentStatus(StatusBarCompat.getStatusBarHeight(this)) // ??????????????????????????????????????????ImageWatcher?????? ?????????????????????????????????ImageView????????????????????????Y?????????????????????
                .setOnPictureLongPressListener(new ImageWatcher.OnPictureLongPressListener() {
                    @Override
                    public void onPictureLongPress(ImageView v, final Uri uri, int pos) {
                        // ??????????????????????????????????????????????????????????????????????????????????????????
                        // Toast.makeText(v.getContext().getApplicationContext(), "????????????" + (pos + 1) + "?????????", Toast.LENGTH_SHORT).show();
                        final DeleteReplyDialog dialog = new DeleteReplyDialog("???????????????", "????????????", "??????");
                        dialog.setSetOnClick(new DeleteReplyDialog.setOnClick() {
                            @Override
                            public void setOnClickLeft() {
//                            AlertDialogUtils.AlertDialog(ViewpagerImageActivity.this,"????????????","???????????????????????????");
//                        Uri imgUri =  ImageLoader.getImageUrl2Uri(url);
                                downloadImage(uri.toString());


                            }

                            @Override
                            public void setOnClickMiddle() {
                                Glide.with(CoopDetailActivity.this).asBitmap().load(uri).into(new SimpleTarget<Bitmap>() {
                                    @Override
                                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                                        mQRBitmap = resource;
                                        requestPermission();
                                    }


                                });
                            }

                            @Override
                            public void setOnClickRight() {
                                dialog.dismiss();
                            }

                            @Override
                            public void theOnDismiss(int type) {

                            }
                        });
                        dialog.show(getSupportFragmentManager(), "viewphoto");
                    }
                });


    }

    private void goDelete(int id, final int type, final int nposition, final int cposition) {
        RequestManager.getInstance().dodelComment(id, type, new AllResultDoCallback() {
            @Override
            public void onSuccess(String msg) {
                ToastUtils.showCentetToast(CoopDetailActivity.this, msg);
                if (type == 1) {

                    if (datas.size() == 1) {
                        //??????????????????
                        inquirePage = 1;
                        buildInquire(inquirePage, commentxId, is_closeComment);
                    } else {
                        commentAdapter.deleteReplyData(nposition);
                    }
                } else if (type == 2) {
                    commentAdapter.deleteReplyData(nposition, cposition);
                }
            }

            @Override
            public void onFailed(int code, String msg) {
                ToastUtils.showCentetToast(CoopDetailActivity.this, msg);
            }
        });
    }

    //????????????
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

        RequestManager.getInstance().goReplyComment(resourceId, commentId, replyType, replyId, comment, uId, headImg, nickName, company, position, isV, 1, new GoCommentCallback() {
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

                ToastUtils.showCentetToast(CoopDetailActivity.this, "????????????");
                //??????????????????????????????????????????
                mcoopLwordRecycler.collapseGroup(cposition);
                mcoopLwordRecycler.expandGroup(cposition);
            }

            @Override
            public void onFailed(int code, String msg) {
                dismissBookingToast();
                ToastUtils.showCentetToast(CoopDetailActivity.this, msg);
            }
        });
    }

    private void loadDatax() {
        RequestManager.getInstance().getCoopDetail(id, page, new GetCoopDetailCallback() {
            @Override
            public void onSuccess(CoopDetailBean bean) {

                initData(bean);
            }

            @Override
            public void onUndercarriage(String bean) {
                Gson gson = new Gson();
                ResourceErrorBean errorResponse = gson.fromJson(bean, ResourceErrorBean.class);

                ToastUtils.showCentetImgToast(CoopDetailActivity.this, errorResponse.getMsg());
                if (errorResponse.getCode() == 220) {
                    activity_lxtv.setEnabled(false);
                    activity_lxtv.setBackground(ContextCompat.getDrawable(CoopDetailActivity.this, R.drawable.bg_lx_detail_un));
                    try {
                        JSONObject object = new JSONObject(bean);
                        String datax = object.optString("data");
                        CoopDetailBean coopDetailBean = gson.fromJson(datax, CoopDetailBean.class);
                        initData(coopDetailBean);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                }
            }

            @Override
            public void onFailed(int code, String msg) {
                if (activity_lxtv != null) {
                    activity_lxtv.setSelected(true);
                }
                ToastUtils.showCentetImgToast(CoopDetailActivity.this, msg);
            }
        });
    }

    /**
     * ???????????????
     *
     * @param bean
     */
    private void initData(CoopDetailBean bean) {
        mBeanData = bean;
        loadQr(id, UserInfoHelper.getIntance().getUserId(), 1);//???????????????????????????
        long lastTime = bean.getLast_login();
        long createTime = bean.getCreate_time();
        company = bean.getCompany();
        position = bean.getPosition();
        p_id = bean.getP_id();
        p_name = bean.getP_name();
        is_closeComment = bean.getIs_closeComment();
        int is_transaction = bean.getIs_transaction();//????????????????????????
        if (is_transaction == 1) {
            rlayoutDanbao.setVisibility(View.VISIBLE);
        } else {
            rlayoutDanbao.setVisibility(View.GONE);
        }
        ShowUtils.showImgPerfect(itemIndexRecyclerImg, bean.getHead_pic(), 1);
        ShowUtils.showTextPerfect(titleTv, "???" + bean.getCompany() + "???" + bean.getTitle());
        ShowUtils.showTextPerfect(itemIndexNameTv, bean.getRealname());
        view.setVisibility(View.GONE);
        if (Constants.userIdList.length() > 0) {
            String[] all = Constants.userIdList.split(",");
            int size1 = all.length;
            int j;

            for (j = 0; j < size1; j++) {
                if (bean.getUser_id() == Integer.parseInt(all[j])) {
                    view.setVisibility(View.VISIBLE);
                }
            }
        }


        ShowUtils.showTextPerfect(itemIndexPosition, bean.getCompany() + bean.getPosition());
        ShowUtils.showTextPerfect(detailType, bean.getCompany_name());
        ShowUtils.showTextPerfect(itemIndexRecyclerMannertv, bean.getP_name());
        ShowUtils.showTextPerfect(coopDetailDay, TimeUtils.getTimeStateNewTwo(createTime + ""));
        if (bean.getIs_v() == 1) {
            ShowUtils.showViewVisible(IsvImg, View.VISIBLE);
        } else {
            ShowUtils.showViewVisible(IsvImg, View.INVISIBLE);
        }
        isVip = bean.getIs_vip();
        if (bean.getIs_corporate_vip() == 1) {
            //????????????????????????vip
            ShowUtils.showViewVisible(companyVipImg, View.VISIBLE);
            ShowUtils.showViewVisible(itemIndexVipImg, View.GONE);
            ShowUtils.showTextColor(itemIndexNameTv, ContextCompat.getColor(CoopDetailActivity.this, R.color.king_color));
        } else {
            ShowUtils.showViewVisible(companyVipImg, View.GONE);
            ShowUtils.showViewVisible(itemIndexVipImg, View.VISIBLE);
            if (isVip == 0) {
                ShowUtils.showViewVisible(itemIndexVipImg, View.GONE);
                ShowUtils.showTextColor(itemIndexNameTv, ContextCompat.getColor(CoopDetailActivity.this, R.color.text_color_1));
                view.setBackgroundColor(ContextCompat.getColor(CoopDetailActivity.this, R.color.text_color_1));
            } else if (isVip == 1) {
                ShowUtils.showBackgroud(itemIndexVipImg, ContextCompat.getDrawable(CoopDetailActivity.this, R.mipmap.vip_iconx));
                ShowUtils.showTextColor(itemIndexNameTv, ContextCompat.getColor(CoopDetailActivity.this, R.color.king_color));
                view.setBackgroundColor(ContextCompat.getColor(CoopDetailActivity.this, R.color.king_color));
            } else if (isVip == 2) {
                ShowUtils.showBackgroud(itemIndexVipImg, ContextCompat.getDrawable(CoopDetailActivity.this, R.mipmap.svip_iconx));
                ShowUtils.showTextColor(itemIndexNameTv, ContextCompat.getColor(CoopDetailActivity.this, R.color.king_color));
                view.setBackgroundColor(ContextCompat.getColor(CoopDetailActivity.this, R.color.king_color));
            }

        }

        if (bean.getIs_cloud_auth() == 1) {
            personRz.setVisibility(View.VISIBLE);
        } else {
            personRz.setVisibility(View.GONE);
        }

        if (bean.getCorporate_info() == null) {
            ShowUtils.showViewVisible(ycompanyRl, View.GONE);
        } else {
            companyId = bean.getCorporate_info().getId();
            ShowUtils.showViewVisible(bgotoCompany, View.VISIBLE);
            ImageLoader.loadCompanyHead(bean.getCorporate_info().getLogo(), mcoopDetailCompanyImg);
            ShowUtils.showTextPerfect(mcoopBrandName, bean.getCorporate_info().getBrand());
            ShowUtils.showTextPerfect(mcoopCompanyName, bean.getCorporate_info().getName());
            ShowUtils.showTextPerfect(mcoopCompanyInfo, bean.getCorporate_info().getResources_count() + "??????????????? ?? " + bean.getCorporate_info().getUser_num() + "?????? ?? " + bean.getCorporate_info().getCompany_name());
            headPic = bean.getCorporate_info().getLogo();
        }
        wechatUrl = bean.getWechat_url();
        if (!TextUtils.isEmpty(bean.getCity_name())) {
            ShowUtils.showTextPerfect(itemIndexRecyclerPlacetv, bean.getCity_name());
        } else {
            ShowUtils.showViewVisible(itemIndexRecyclerPlacetv, View.GONE);
        }
        if (bean.getIs_collect() == 1) {
            isCollect = 1;
            bcollectTv.setSelected(true);
        } else {
            isCollect = 0;
            bcollectTv.setSelected(false);
        }
        ShowUtils.showTextPerfect(activityCoopDetailLook, "?????? " + bean.getView());

        if (bean.getProvide_category().size() > 0) {
            LinearLayoutManager manager2 = new LinearLayoutManager(CoopDetailActivity.this, LinearLayoutManager.VERTICAL, false);
            rcyOffer.setLayoutManager(manager2);
            CoopDetailOfferAdapter coopDetailOfferAdapter = new CoopDetailOfferAdapter(CoopDetailActivity.this, R.layout.item_coop_detail, bean.getProvide_category());
            rcyOffer.setAdapter(coopDetailOfferAdapter);
            rcyOffer.setNestedScrollingEnabled(false);
            if (!TextUtils.isEmpty(bean.getProvide_describe())) {
                ShowUtils.showTextPerfect(coopMofferContent, bean.getProvide_describe());

            }
            if (!TextUtils.isEmpty(bean.getProvide_description_title())) {
                ShowUtils.showTextPerfect(coopMofferTitle, bean.getProvide_description_title());

            } else {
                coopMneedTitle.setText("?????????????????????");
            }
        } else {
            ShowUtils.showViewVisible(coopMofferContentLl, View.GONE);
        }
        if (bean.getIs_verify() == 0) {//1???????????? 2???????????? 0?????????
            ShowUtils.showViewVisible(coopDetailWarningRl, View.VISIBLE);
            ShowUtils.showTextPerfect(mcoopDetailStateTv, "???????????????");
            isVer = 0;
        } else if (bean.getIs_verify() == 1) {

            if (bean.getStatus() == 1) {
                ShowUtils.showViewVisible(coopDetailWarningRl, View.GONE);
                isVer = 1;
                //0???????????????1???????????????2???????????????3?????????????????????4????????????
//                if (bean.getReservation_status() == 1 || bean.getReservation_status() == 2) {
//                    isVer = 4;
//                }
            } else {
                ShowUtils.showViewVisible(coopDetailWarningRl, View.VISIBLE);
                ShowUtils.showTextPerfect(mcoopDetailStateTv, "???????????????");
                isVer = 3;
            }
        } else if (bean.getIs_verify() == 2) {
            ShowUtils.showViewVisible(coopDetailWarningRl, View.VISIBLE);
            ShowUtils.showTextPerfect(mcoopDetailStateTv, "??????????????????");
            isVer = 2;
        }
        if (bean.getFollow_status() == 2) {
            isMeRe = true;
            if (bean.getIs_del() == 0) {
                ShowUtils.showViewVisible(yperchRl, View.GONE);
                ShowUtils.showViewVisible(detailFx, View.VISIBLE);
            } else {
                ShowUtils.showViewVisible(yperchRl, View.VISIBLE);
                ShowUtils.showViewVisible(detailFx, View.GONE);
            }
        } else {
            isMeRe = false;
            if (bean.getIs_del() == 0) {
                ShowUtils.showViewVisible(yperchRl, View.GONE);
            } else {
                ShowUtils.showViewVisible(yperchRl, View.VISIBLE);
                isVer = 4;
            }
        }


        if (bean.getNeed_category().size() > 0) {
            LinearLayoutManager manager2 = new LinearLayoutManager(CoopDetailActivity.this, LinearLayoutManager.VERTICAL, false);
            rcyNeed.setLayoutManager(manager2);
            CoopDetailNeedeAdapter coopDetailOfferAdapter = new CoopDetailNeedeAdapter(CoopDetailActivity.this, R.layout.item_coop_detail, bean.getNeed_category());
            rcyNeed.setAdapter(coopDetailOfferAdapter);
            rcyNeed.setNestedScrollingEnabled(false);


            if (!TextUtils.isEmpty(bean.getNeed_description_title())) {
                ShowUtils.showTextPerfect(coopMneedTitle, bean.getNeed_description_title());

            } else {
                coopMneedTitle.setText("?????????????????????");
            }


            if (!TextUtils.isEmpty(bean.getNeed_describe())) {
                ShowUtils.showTextPerfect(coopMneedContent, bean.getNeed_describe());

            } else {
                ShowUtils.showViewVisible(coopMneedContent, View.GONE);
            }
        } else {

            ShowUtils.showViewVisible(coopMneedContentLl, View.GONE);
        }


        DetailTime.setText(TimeUtils.getStatus(lastTime * 1000));
        if (!TextUtils.isEmpty(bean.getImages())) {
            ycoopPhotoLl.setVisibility(View.VISIBLE);
            String[] photoArray = bean.getImages().split(",");
            if (!TextUtils.isEmpty(bean.getThumb_img())) {
                String[] thumbs = bean.getThumb_img().split(",");
                for (int i = 0; i < thumbs.length; i++) {
                    thumbList.add(thumbs[i]);
                }
            }
            for (int i = 0; i < photoArray.length; i++) {
                photoList.add(photoArray[i]);
            }

            final LinearLayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
            coopDetailPhotoRecycler.setLayoutManager(manager);
            if (thumbList.size() == photoList.size()) {
                CoopDetailPhotoAdapter photoAdapter = new CoopDetailPhotoAdapter(R.layout.item_recycler_photo, thumbList, this, photoList);
                coopDetailPhotoRecycler.setAdapter(photoAdapter);
                photoAdapter.setSetOnClick(new CoopDetailPhotoAdapter.setOnClick() {
                    @Override
                    public void setOnClick(ArrayList<String> list, int position, SparseArray<ImageView> data, ImageView v) {
//                        ViewpagerImageActivity.start(mContext, list, position);
//                        overridePendingTransition(R.anim.main_fade_in, 0);
                        iwHelper.show(position, data, convert(list));
                    }
                });
            } else {
                CoopDetailPhotoAdapter photoAdapter = new CoopDetailPhotoAdapter(R.layout.item_recycler_photo, photoList, this);
                coopDetailPhotoRecycler.setAdapter(photoAdapter);
                photoAdapter.setSetOnClick(new CoopDetailPhotoAdapter.setOnClick() {
                    @Override
                    public void setOnClick(ArrayList<String> list, int position, SparseArray<ImageView> data, ImageView v) {
//                        ViewpagerImageActivity.start(mContext, list, position);
//                        overridePendingTransition(R.anim.main_fade_in, 0);
                        iwHelper.show(position, data, convert(list));
                    }
                });
            }

        } else {
            ycoopPhotoLl.setVisibility(View.GONE);
        }
        //?????????????????????
        LinearLayoutManager layoutManager = new LinearLayoutManager(CoopDetailActivity.this, LinearLayoutManager.HORIZONTAL, false);
        coopDetailRecycler.setLayoutManager(layoutManager);
        coopHeadLxAdapter = new CoopHeadLxAdapter(CoopDetailActivity.this, lxList);
        coopDetailRecycler.setAdapter(coopHeadLxAdapter);
        lxList.clear();
        if (bean.getZ_list() != null) {
            if (bean.getZ_list().size() > 4) {
                for (int i = 0; i < bean.getZ_list().size(); i++) {
                    bean.getZ_list().get(i).setItemType(CoopDetailBean.ZListBean.COMMON);
                }
                lxList.addAll(bean.getZ_list().subList(0, 4));
                lxList.add(new CoopDetailBean.ZListBean(CoopDetailBean.ZListBean.MORE));
            } else {

                for (int i = 0; i < bean.getZ_list().size(); i++) {
                    bean.getZ_list().get(i).setItemType(CoopDetailBean.ZListBean.COMMON);
                }
                lxList.addAll(bean.getZ_list());
            }
        }


        coopHeadLxAdapter.notifyDataSetChanged();
        if (bean.getTalk_num() == 0) {
            bgotoCommunicationRl.setVisibility(View.GONE);
        } else {
            bgotoCommunicationRl.setVisibility(View.VISIBLE);
        }
        if (bean.getIs_colleagueTalk() == 0) {
            mcoopDetailLinktv.setTextColor(getResources().getColor(R.color.search_text_color_gray));
            mcoopDetailLinktv.setBackgroundDrawable(null);
            ShowUtils.showTextPerfect(mcoopDetailLinktv, "????????????");
        } else {
            mcoopDetailLinktv.setTextColor(getResources().getColor(R.color.blue_bg_4B96F3));
            mcoopDetailLinktv.setBackgroundResource(R.mipmap.communicated_bg);
            ShowUtils.showTextPerfect(mcoopDetailLinktv, "?????????????????????");
        }


        shareUrl = bean.getShare_url();
//        headPic = bean.getHead_pic();
        titleString.append("???" + bean.getCompany() + "???").append(bean.getTitle());
        builder.append("??????")
                .append(bean.getProvide_remark())
                .append("??????????????????");

        userId = bean.getUser_id();
        commentAdapter.setUserId(userId);
        resourceId = bean.getId();
        coopHeadLxAdapter.setResourceId(resourceId);
        name = bean.getRealname();
        qrUrl = bean.getQrcode_url();
        //??????????????????
        inquirePage = 1;

        buildInquire(inquirePage, commentxId, is_closeComment);
        //????????????????????????????????????
        if (UserInfoHelper.getIntance().getUserId() == bean.getUser_id() && (bean.getP_id() == 8 || bean.getP_id() == 7) && bean.getIs_transaction() == 0) {
            isPopups(bean.getId());

        } else {

        }


    }

    /**
     * ??????????????????????????????
     *
     * @param id
     */
    private void isPopups(final int id) {
        RequestManager.getInstance().isPopups(id, new AllResultDoCallback() {
            @Override
            public void onSuccess(String msg) {
                new QLTipTwoDialog.Builder(CoopDetailActivity.this)
                        .setCancelable(false)
                        .setCancelableOnTouchOutside(false)
                        .setTitle("????????????????????????????????????")
                        .setMessage("?????????????????????????????????????????????")
                        .setPositiveButton("??????", new QLTipTwoDialog.IPositiveCallback() {
                            @Override
                            public void onClick() {
                                clickPopups(id, 1);

                            }
                        })
                        .setNegativeButton("??????", new QLTipTwoDialog.INegativeCallback() {
                            @Override
                            public void onClick() {
                                clickPopups(id, 2);
                            }
                        })
                        .show(CoopDetailActivity.this);

            }

            @Override
            public void onFailed(int code, String msg) {

            }
        });
    }

    private void clickPopups(int id, final int i) {
        RequestManager.getInstance().clickPopups(id, i, new AllResultDoCallback() {
            @Override
            public void onSuccess(String msg) {
                if (i == 1) {
                    ToastUtils.showCentetToast(CoopDetailActivity.this, "????????????");
                }
            }

            @Override
            public void onFailed(int code, String msg) {
                ToastUtils.showCentetToast(CoopDetailActivity.this, msg);
            }
        });
    }

    /**
     * ???????????????????????????
     *
     * @param id
     * @param user_id
     * @param i
     */

    private void loadQr(int id, int user_id, int i) {
        RequestManager.getInstance().getWechatQr(id, user_id, i, new GetWechatQrCallback() {
            @Override
            public void onSuccess(WechatQr item) {
                mImageMa = item.getImg();

            }

            @Override
            public void onFailed(int code, String msg) {

            }
        });
    }

    private List<Uri> convert(List<String> data) {
        List<Uri> list = new ArrayList<>();
        for (String d : data) list.add(Uri.parse(d));
        return list;
    }

    //????????????
    private void buildInquire(final int inquirePage, final int commmentxId, final int is_colleagueTalk) {
        RequestManager.getInstance().getInquire(resourceId, inquirePage, commmentxId, 1, new InquireCallback() {
            @Override
            public void onSuccess(InquireBean userInfoBean) {
                dismissBookingToast();
                mcoopDetailLword.setText("?????? ?? " + userInfoBean.getTotal());
                if (is_colleagueTalk == 1) {//????????????
                    mcoopPerchRl.setVisibility(View.VISIBLE);
                    mcoopLwordRecycler.setVisibility(View.GONE);
                    detailImg.setBackgroundResource(R.mipmap.jin_message);
                    detailTv.setText("??????????????????????????????????????????????????????");
                    detailTv.setTextColor(getResources().getColor(R.color._999));
                    bleavewordxTv.setVisibility(View.GONE);
                    mcoopRefresh.setEnableLoadMore(false);
                    mcoopLwordRecycler.removeFooterView(footView);
                    llayoutEmpty.setVisibility(View.VISIBLE);

                } else {
                    llayoutEmpty.setVisibility(View.GONE);
                    mcoopLwordRecycler.setVisibility(View.VISIBLE);
                    if (inquirePage == 1) {
                        datas.clear();
                        if (userInfoBean.getList().size() == 0) {
                            detailImg.setBackgroundResource(R.mipmap.success_imgicon_two);
                            detailTv.setText("??????????????????,?????????????????????????????????");
                            detailTv.setTextColor(getResources().getColor(R.color._999));
                            bleavewordxTv.setVisibility(View.VISIBLE);
                            mcoopPerchRl.setVisibility(View.VISIBLE);
                            mcoopLwordRecycler.removeFooterView(footView);
//                       commentAdapter.removeAllFooterView();
                            llayoutEmpty.setVisibility(View.VISIBLE);
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
                    if (Constants.userIdList.length() > 0) {
                        Log.i("time03====", System.currentTimeMillis() + "");
                        String[] all = Constants.userIdList.split(",");
                        int size = userInfoBean.getList().size();
                        int size1 = all.length;
                        int i, j, m, n;
                        for (i = 0; i < size; i++) {


                            for (j = 0; j < size1; j++) {
                                if (userInfoBean.getList().get(i).getFrom_uid() == Integer.parseInt(all[j])) {
                                    userInfoBean.getList().get(i).setU(true);
                                }
                            }

                            if (userInfoBean.getList().get(i).getReply().size() > 0) {
                                int size2 = userInfoBean.getList().get(i).getReply().size();
                                for (m = 0; m < size2; m++) {
                                    for (n = 0; n < size1; n++) {
                                        if (userInfoBean.getList().get(i).getReply().get(m).getFrom_uid() == Integer.parseInt(all[n])) {
                                            userInfoBean.getList().get(i).getReply().get(m).setU(true);
                                        }
                                    }
                                }
                            }

                        }
                        Log.i("time04====", System.currentTimeMillis() + "");
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
                }
                finishSwipe();

            }

            @Override
            public void onFailed(int code, String msg) {
                dismissBookingToast();
                finishSwipe();
                ToastUtils.showCentetToast(CoopDetailActivity.this, msg);
            }
        });
    }

    private void showShareDialog() {
        boolean isCollected = false;
        boolean isMessage = true;
        if (isCollect == 1) {
            isCollected = true;
        } else {
            isCollected = false;
        }
        if (is_closeComment == 1) {//??????
            isMessage = false;
        } else {
            isMessage = true;
        }

        shareDialog = new QLnewDialog.Builder(mContext)
                .setCollectStatus(isCollected)
                .setContext(CoopDetailActivity.this)
                .setIsVer(isVer)
                .setIsMe(isMeRe)
                .setMessage(isMessage)
                .setShareCallback(new QLnewDialog.ShareNewCallback() {
                    @Override
                    public void onClickShare(QLnewDialog.ShareType type) {
                        switch (type) {
                            case SHARE_WEIXIN:
                                if (isVer == 2) {
                                    ToastUtils.showCentetToast(CoopDetailActivity.this, "??????????????????????????????");
                                    return;
                                }
                                if (isVer == 3) {
                                    ToastUtils.showCentetToast(CoopDetailActivity.this, "????????????????????????");
                                    return;
                                }
                                if (isVer == 4) {
                                    ToastUtils.showCentetToast(CoopDetailActivity.this, "???????????????????????????");
                                    return;
                                }
                                MobclickAgent.onEvent(CoopDetailActivity.this, "resource_wechatShare");//??????????????????
                                shareCircle(SHARE_MEDIA.WEIXIN);
                                shareDialog.dismiss();
                                break;
                            case SHARE_CIRCLE:
                                if (isVer == 2) {
                                    ToastUtils.showCentetToast(CoopDetailActivity.this, "??????????????????????????????");
                                    return;
                                }
                                if (isVer == 3) {
                                    ToastUtils.showCentetToast(CoopDetailActivity.this, "????????????????????????");
                                    return;
                                }
                                if (isVer == 4) {
                                    ToastUtils.showCentetToast(CoopDetailActivity.this, "???????????????????????????");
                                    return;
                                }
                                MobclickAgent.onEvent(CoopDetailActivity.this, "resource_wechatTimeLineShare");//??????????????????
                                shareCircle(SHARE_MEDIA.WEIXIN_CIRCLE);
                                shareDialog.dismiss();
                                break;
                            case SHARE_QQ:
                                if (isVer == 2) {
                                    ToastUtils.showCentetToast(CoopDetailActivity.this, "??????????????????????????????");
                                    return;
                                }
                                if (isVer == 3) {
                                    ToastUtils.showCentetToast(CoopDetailActivity.this, "????????????????????????");
                                    return;
                                }
                                if (isVer == 4) {
                                    ToastUtils.showCentetToast(CoopDetailActivity.this, "???????????????????????????");
                                    return;
                                }
                                MobclickAgent.onEvent(CoopDetailActivity.this, "resource_qqShare");//??????????????????
                                shareCircle(SHARE_MEDIA.QQ);
                                shareDialog.dismiss();
                                break;
                            case SHARE_DD:
                                if (isVer == 2) {
                                    ToastUtils.showCentetToast(CoopDetailActivity.this, "??????????????????????????????");
                                    return;
                                }
                                if (isVer == 3) {
                                    ToastUtils.showCentetToast(CoopDetailActivity.this, "????????????????????????");
                                    return;
                                }
                                if (isVer == 4) {
                                    ToastUtils.showCentetToast(CoopDetailActivity.this, "???????????????????????????");
                                    return;
                                }
                                shareCircle(SHARE_MEDIA.DINGTALK);
                                shareDialog.dismiss();
                                break;
                            case SHARE_WEIBO:
                                ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                                if (cm != null) {
                                    cm.setPrimaryClip(ClipData.newPlainText(null, shareUrl));
                                }
                                if (cm.hasPrimaryClip()) {
                                    cm.getPrimaryClip().getItemAt(0).getText();
                                }
                                ToastUtils.showCentetToast(CoopDetailActivity.this, "????????????");
                                shareDialog.dismiss();
                                break;
                            case SHARE_QRCODE:
                                if (isVer == 2) {
                                    ToastUtils.showCentetToast(CoopDetailActivity.this, "??????????????????????????????");
                                    return;
                                }
                                if (isVer == 3) {
                                    ToastUtils.showCentetToast(CoopDetailActivity.this, "????????????????????????");
                                    return;
                                }
                                if (isVer == 4) {
                                    ToastUtils.showCentetToast(CoopDetailActivity.this, "???????????????????????????");
                                    return;
                                }

//                                if (mImageMa.length() == 0) {
//                                    ToastUtils.showCentetToast(CoopDetailActivity.this, "?????????????????????????????????");
//                                    return;
//                                     }
                                showQrDialog();
                                shareDialog.dismiss();
                                break;
                            case COLLECT_GO:
                                gotoCollect();
                                break;
                            case CLOSE_MESSAGE:
                                //????????????
                                gotoMessage();

                                break;
                            case OPEN_MESSAGE:
                                //????????????
                                gotoMessage();
                                break;

                            case REPORT_GO:
                                goToReport();
                                break;
                            case TOP_GO:
                                refresh(resourceId);
                                break;
                            case EDIT_GO:
                                ComUtils.addActivity(CoopDetailActivity.this);
                                PublishNewActivity.start(CoopDetailActivity.this, resourceId, p_name, p_id, 1000);
                                shareDialog.dismiss();
                                break;
                            case DOWN_GO:
                                downResource(resourceId);
                                break;
                            case DELETE_GO:
                                deleteResource(resourceId);
                                break;
                            case SHARE_FRIEND:
                                MobclickAgent.onEvent(CoopDetailActivity.this, "resource_friendShare");//??????????????????
                                FriendLxActivity.start(CoopDetailActivity.this, 3, resourceId);
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
//            if (builder == null || shareUrl == null || headPic == null || wechatUrl == null) {
//                return;
//            }
//            ShareUtils.shareWxMini(this, share_media, shareUrl, titleString.toString(), builder.toString(), wechatUrl, new UMShareListener() {
//                @Override
//                public void onStart(SHARE_MEDIA share_media) {
//
//                }
//
//                @Override
//                public void onResult(SHARE_MEDIA share_media) {
//
//                    RequestManager.getInstance().dailyShare(CoopDetailActivity.this);
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
        if (builder == null || shareUrl == null) {
            return;
        }
        ShareUtils.shareWebUrl(
                this,
                headPic,
                share_media,
                shareUrl,
                titleString.toString(),
                builder.toString(), new UMShareListener() {
                    @Override
                    public void onStart(SHARE_MEDIA share_media) {

                    }

                    @Override
                    public void onResult(SHARE_MEDIA share_media) {

                        RequestManager.getInstance().dailyShare(CoopDetailActivity.this);
                    }

                    @Override
                    public void onError(SHARE_MEDIA share_media, Throwable throwable) {

                    }

                    @Override
                    public void onCancel(SHARE_MEDIA share_media) {

                    }
                });
        //  }
    }

    /**
     * ????????????
     */

    private void showQrDialog() {

        MobclickAgent.onEvent(this, "resource_createPoster");//??????????????????????????????
        //  ShareNewActivity.start(CoopDetailActivity.this, qrUrl, resourceId + "", "coop");
        Gson gson = new Gson();
        String str1 = gson.toJson(mBeanData);
        ShareNewThreeActivity.start(CoopDetailActivity.this, str1, mImageMa);
        shareDialog.dismiss();
    }

    /**
     * ????????????????????????
     */
    @AfterPermissionGranted(PERMISSION_WRITE_EXTERNAL_STORAGE)
    private void requestPermission() {
        if (EasyPermissions.hasPermissions(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            if (mQRBitmap == null) {
                return;
            }
            BitmapUtils.saveImageToGallery(mContext, mQRBitmap);
            ToastUtils.showSuccessfulToast(mContext, "??????????????????");
        } else {
            EasyPermissions.requestPermissions(this, getString(R.string.permission_need_save_bitmap),
                    PERMISSION_WRITE_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
    }


    @OnClick({R.id.rlayout_danbao, R.id.blxto_tv, R.id.bt_close, R.id.detail_fx, R.id.coopdetail_Rl, R.id.bgoto_company, R.id.bgoto_communicationRl, R.id.bcollect_tv, R.id.bleaveword_tv, R.id.bleavewordx_tv, R.id.bleaveword_sendtv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rlayout_danbao:
                //????????????????????????
                String url = RetrofitHelper.API_URL + "resource/pages/guarTran/qqqgt.html?needLogin=1";
                Intent intent1 = new Intent(CoopDetailActivity.this, ApproveCardActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("theUrl", url);
                bundle.putString("thetitle", "????????????");
                bundle.putString("webType", "Event");
                intent1.putExtras(bundle);
                startActivity(intent1, bundle);
                break;
            case R.id.blxto_tv:
                lxData();
                break;
            case R.id.bt_close:
                finish();
                break;
            case R.id.detail_fx:
                showShareDialog();
                break;
            case R.id.coopdetail_Rl://??????????????????
                MobclickAgent.onEvent(this, "resource_usercenter");//??????????????????
                PersonCentetActivity.start(CoopDetailActivity.this, userId + "");
                break;
            case R.id.bgoto_company:
                MobclickAgent.onEvent(this, "resource_companyInfo");//??????????????????
                CompanyInfoActivity.start(CoopDetailActivity.this, companyId);
                break;
            case R.id.bgoto_communicationRl:
                MobclickAgent.onEvent(this, "resource_talkList");//??????????????????
                CoopCommunicationActivity.start(CoopDetailActivity.this, resourceId);
                break;
            case R.id.bcollect_tv:
                gotoCollect();
                break;
            case R.id.bleaveword_tv:
                //??????
                if (is_closeComment == 0) {
                    ycoopSendRl.setVisibility(View.VISIBLE);
                    getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
                    mcoopSendEt.setCursorVisible(true);
                    mcoopSendEt.setFocusable(true);
                    mcoopSendEt.setFocusableInTouchMode(true);
                    mcoopSendEt.requestFocus();
                    mcoopSendEt.setHint("??????????????????????????????????????????");
                    sendType = 1;
                    ycoopLxwordRl.setVisibility(View.GONE);
                    imm.showSoftInput(mcoopSendEt, 0);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        mcoopSendEt.setShowSoftInputOnFocus(true);
                    }
                } else {
                    //????????????
                    ToastUtils.showCentetToast(CoopDetailActivity.this, "????????????????????????");
                }
                break;
            case R.id.bleavewordx_tv:
                if (is_closeComment == 0) {
                    ycoopSendRl.setVisibility(View.VISIBLE);
                    getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
                    mcoopSendEt.setCursorVisible(true);
                    mcoopSendEt.setFocusable(true);
                    mcoopSendEt.setFocusableInTouchMode(true);
                    mcoopSendEt.requestFocus();
                    mcoopSendEt.setHint("??????????????????????????????????????????");
                    sendType = 1;
                    ycoopLxwordRl.setVisibility(View.GONE);
                    imm.showSoftInput(mcoopSendEt, 0);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        mcoopSendEt.setShowSoftInputOnFocus(true);
                    }
                } else {
                    //????????????
                    ToastUtils.showCentetToast(CoopDetailActivity.this, "????????????????????????");
                }
                break;
            case R.id.bleaveword_sendtv:
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
            default:

                break;
        }
    }

    //????????????
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
        RequestManager.getInstance().goComment(resourceId, mcoopSendEt.getText().toString(), nickName, headImg, uId, company, position, isV, 1, new GoCommentCallback() {
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
                ToastUtils.showCentetToast(CoopDetailActivity.this, "????????????");

            }

            @Override
            public void onFailed(int code, String msg) {
                dismissBookingToast();
                ToastUtils.showCentetToast(CoopDetailActivity.this, msg);
            }
        });
    }

    private void goToReport() {
        ReportListActivity.start(CoopDetailActivity.this, 2, id);

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
            //???isShouldHideInput(v, ev)???true???????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????
            if (isShouldHideInput(ycoopSendRl, ev)) {

                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm != null) {
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                    //??????Editext??????????????????????????????
                    mcoopSendEt.clearFocus();
                    ycoopSendRl.setVisibility(View.GONE);
                    ycoopLxwordRl.setVisibility(View.VISIBLE);
                }
            }
            return super.dispatchTouchEvent(ev);
        }
        // ????????????????????????????????????????????????TouchEvent???
        if (getWindow().superDispatchTouchEvent(ev)) {
            return true;
        }
        return super.dispatchTouchEvent(ev);
    }

    public boolean isShouldHideInput(View v, MotionEvent event) {
        if (v != null && (v instanceof RelativeLayout)) {
            int[] leftTop = {0, 0};
            //????????????????????????location??????
            v.getLocationInWindow(leftTop);
            int left = leftTop[0];
            int top = leftTop[1];
            int bottom = top + v.getHeight();
            int right = left + v.getWidth();
            if (event.getX() > left && event.getX() < right
                    && event.getY() > top && event.getY() < bottom) {
                // ??????????????????????????????????????????EditText?????????
                return false;
            } else {
                return true;
            }
        }
        return false;
    }

    //???????????????????????????
    private void gotoMessage() {
        final int message;
        if (is_closeComment == 0) {//1????????????0:??????
            message = 1;//?????????1:?????????2?????????
        } else {
            message = 2;
        }
        RequestManager.getInstance().goToMessage(id, message, new MessageCallback() {
            @Override
            public void onSuccess(String msg) {

                if (message == 1) {
                    //????????????????????????????????????
                    ToastUtils.showSuccessfulToast(CoopDetailActivity.this, "???????????????");
                } else {

                    ToastUtils.showSuccessfulToast(CoopDetailActivity.this, "?????????????????????");

                }
                if (shareDialog != null && shareDialog.isShowing()) {
                    shareDialog.dismiss();
                }

                loadDatax();
            }

            @Override
            public void onFailed(int code, String msg) {
                ToastUtils.showCentetToast(CoopDetailActivity.this, msg);
            }

        });

    }

    //?????????
    private void gotoCollect() {
        int collect;
        if (isCollect == 0) {
            collect = 1;
        } else {
            collect = 2;
        }
        RequestManager.getInstance().goToCollect(id, collect, new GotoCollectCallback() {
            @Override
            public void onSuccess(GoToCollectBean data, String msg) {
                int status = data.getStatus();
                if (status == 1) {
                    bcollectStatus.setSelected(true);
                    ToastUtils.showSuccessfulToast(CoopDetailActivity.this, msg);
                    isCollect = 1;
                } else {
                    bcollectStatus.setSelected(false);
                    ToastUtils.showSuccessfulToast(CoopDetailActivity.this, msg);
                    isCollect = 0;
                }
                if (shareDialog != null && shareDialog.isShowing()) {
                    shareDialog.dismiss();
                }

                loadDatax();
            }

            @Override
            public void onFailed(int code, String msg) {
                ToastUtils.showCentetToast(CoopDetailActivity.this, msg);
            }
        });

    }

    private void lxData() {
        if (userId == UserInfoHelper.getIntance().getUserId()) {
            ToastUtils.showCentetImgToast(CoopDetailActivity.this, "?????????????????????");
            return;
        }
        showBookingToast(2);
        RequestManager.getInstance().talkToUser(resourceId, userId, new TalkToUserCallback() {
            @Override
            public void onSuccess(TalkToUserBean bean) {
                dismissBookingToast();
                //IMUtils.singleChatForResource(CoopDetailActivity.this, String.valueOf(userId), name, bean.getIs_qrcode(), company + position, headPic, isVip + "");
                finish();
            }

            @Override
            public void onFailue(int code, String msg) {
                dismissBookingToast();

                if (code == 301) {
                    //??????????????????
                    showNormalDialog(msg, 301);

                } else if (code == 303) {
                    //????????????????????????
                    showNormalDialog(msg, 303);

                } else {
                    ToastUtils.showCentetToast(CoopDetailActivity.this, msg);
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
                    .setNegativeButton("?????????", new QLTipDialog.INegativeCallback() {
                        @Override
                        public void onClick() {
//                        dissMissDialog();
                        }
                    })
                    .show(CoopDetailActivity.this);
        } else if (code == 301) {
            noLinkDialog = new NoLinkDialog(msg);
            noLinkDialog.show(getSupportFragmentManager(), "lx");


        }

    }

    private void downResource(int resourceId) {
        RequestManager.getInstance().delResource(resourceId, new RequestCallback<String>() {
            @Override
            public void requestStart(Call call) {
                showBookingToast(2);
                mCall = call;
            }

            @Override
            public void onSuccess(String s) {
                ToastUtils.showCentetImgToast(CoopDetailActivity.this, "????????????");
                shareDialog.dismiss();
                loadDatax();
            }

            @Override
            public void onFailed(int code, String msg) {
                ToastUtils.showCentetImgToast(CoopDetailActivity.this, msg);
            }

            @Override
            public void requestEnd() {
                dismissBookingToast();

            }
        });
    }

    private void deleteResource(int resourceId) {
        RequestManager.getInstance().delResourceV2(resourceId, new RequestCallback<String>() {
            @Override
            public void requestStart(Call call) {
                showBookingToast(2);
                mCall = call;
            }

            @Override
            public void onSuccess(String s) {
                ToastUtils.showCentetImgToast(CoopDetailActivity.this, "????????????");
                shareDialog.dismiss();
                setResult(REQUESTREFRESH);
                finish();
            }

            @Override
            public void onFailed(int code, String msg) {
                ToastUtils.showCentetImgToast(CoopDetailActivity.this, msg);
            }

            @Override
            public void requestEnd() {
                dismissBookingToast();

            }
        });
    }

    //????????????
    private void refresh(int resourceId) {
        RequestManager.getInstance().refresh(1, resourceId, new CommonCallback() {
            @Override
            public void onSuccess(ResultDO resultDO) {
                ToastUtils.showCentetImgToast(CoopDetailActivity.this, resultDO.getMsg());
                shareDialog.dismiss();
                loadDatax();
            }

            @Override
            public void onFailed(int code, String msg) {
                ToastUtils.showCentetImgToast(CoopDetailActivity.this, msg);
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
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        lwordmeasure = mcoopLwordRecycler.getTop();
        if (commentxId != 0 && isscroll) {
            if (mscrollview != null) {
                mscrollview.smoothScrollTo(0, lwordmeasure);
                isscroll = false;
            }
//            coopLwordRl.setFocusable(true);
//            coopLwordRl.setFocusableInTouchMode(true);
//            coopLwordRl.requestFocus();

        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mCall != null) {
            mCall.cancel();
        }
    }

    public void downloadImage(final String url) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    FutureTarget<File> target = Glide.with(mContext)
                            .load(url)
                            .downloadOnly(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL);
                    final File imageFile = target.get();
                    imgUrl = imageFile.getPath();
                    if (!TextUtils.isEmpty(imgUrl)) {
                        FriendLxActivity.startx(CoopDetailActivity.this, FriendLxActivity.COOPPHOTO, imgUrl);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
