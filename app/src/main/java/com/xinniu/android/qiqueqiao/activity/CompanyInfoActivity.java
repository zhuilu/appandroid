package com.xinniu.android.qiqueqiao.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialog;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.bumptech.glide.Glide;

import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshFooter;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.RefreshState;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnMultiPurposeListener;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.xinniu.android.qiqueqiao.R;
import com.xinniu.android.qiqueqiao.adapter.CompanyInfoPhotoAdapter;
import com.xinniu.android.qiqueqiao.adapter.IndexNewAdapter;
import com.xinniu.android.qiqueqiao.base.BaseActivity;
import com.xinniu.android.qiqueqiao.bean.IndexNewBean;
import com.xinniu.android.qiqueqiao.bean.MyCompanyBean;
import com.xinniu.android.qiqueqiao.customs.MyScrollView;
import com.xinniu.android.qiqueqiao.customs.qldialog.QLClassRoomDialog;
import com.xinniu.android.qiqueqiao.request.RequestManager;
import com.xinniu.android.qiqueqiao.request.callback.GetMyCompanyCallback;
import com.xinniu.android.qiqueqiao.request.callback.GetResourceListCallback;
import com.xinniu.android.qiqueqiao.utils.FastBlur;
import com.xinniu.android.qiqueqiao.utils.FullyLinearLayoutManager;
import com.xinniu.android.qiqueqiao.utils.ImageLoader;
import com.xinniu.android.qiqueqiao.utils.NoScrollRecyclerView;
import com.xinniu.android.qiqueqiao.utils.RoundImageView;
import com.xinniu.android.qiqueqiao.utils.ShareUtils;
import com.xinniu.android.qiqueqiao.utils.ShowUtils;
import com.xinniu.android.qiqueqiao.utils.ToastUtils;
import com.xinniu.android.qiqueqiao.widget.CircleImageView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

//import android.support.annotation.NonNull;
//import android.support.v4.content.ContextCompat;
import androidx.appcompat.app.AppCompatDialog;
import androidx.recyclerview.widget.LinearLayoutManager;

/**
 * Created by yuchance on 2018/5/7.
 */

public class CompanyInfoActivity extends BaseActivity {

    @BindView(R.id.bedit_companytv)
    ImageView beditCompanytv;
    @BindView(R.id.mcompany_headicon)
    ImageView mcompanyHeadicon;
    @BindView(R.id.mcompany_brandname)
    TextView mcompanyBrandname;
    @BindView(R.id.mcompany_companyname)
    TextView mcompanyCompanyname;
    @BindView(R.id.bcompany_info_lookall)
    TextView bcompanyInfoLookall;
    @BindView(R.id.mcompany_member_recycler)
    NoScrollRecyclerView mcompanyMemberRecycler;
    @BindView(R.id.mcompany_introducetv)
    TextView mcompanyIntroducetv;
    @BindView(R.id.bcompany_intromoreImg)
    ImageView bcompanyIntromoreImg;

    @BindView(R.id.mcompany_nettv)
    TextView mcompanyNettv;
    @BindView(R.id.mcompany_netRl)
    RelativeLayout mcompanyNetRl;

    @BindView(R.id.mcompany_resource_list)
    NoScrollRecyclerView mcompanyResourceList;
    @BindView(R.id.mcompany_typeTv)
    TextView mcompanyTypeTv;
    @BindView(R.id.mcompany_info_refresh)
    SmartRefreshLayout mcompanyInfoRefresh;
    @BindView(R.id.info_pointgo)
    ImageView infoPointgo;

    @BindView(R.id.mcompany_infoImg)
    RelativeLayout mcompanyInfoImg;
    @BindView(R.id.yeditRl)
    RelativeLayout yeditRl;
    @BindView(R.id.ycompany_intromoreRl)
    RelativeLayout ycompanyIntromoreRl;
    @BindView(R.id.mcompany_bgimg)
    ImageView mcompanyBgimg;
    @BindView(R.id.company_scroll)
    MyScrollView companyScroll;
    @BindView(R.id.mcompany_titleRl)
    RelativeLayout mcompanyTitleRl;
    @BindView(R.id.company_headRl)
    RelativeLayout companyHeadRl;
    @BindView(R.id.line1)
    View line1;
    @BindView(R.id.imgv)
    View imgv;
    @BindView(R.id.bussinessRl)
    RelativeLayout bussinessRl;
    @BindView(R.id.line2)
    View line2;
    @BindView(R.id.tvbussinessinfo)
    TextView tvbussinessinfo;
    @BindView(R.id.companyinfoRl)
    RelativeLayout companyinfoRl;
    @BindView(R.id.line3)
    View line3;
    @BindView(R.id.tvbussinessnet)
    TextView tvbussinessnet;
    @BindView(R.id.line4)
    View line4;
    @BindView(R.id.tvbussinessmember)
    TextView tvbussinessmember;
    @BindView(R.id.memberRl)
    RelativeLayout memberRl;
    @BindView(R.id.imgv2)
    View imgv2;
    @BindView(R.id.service_pointgo)
    ImageView servicePointgo;
    @BindView(R.id.service_info_lookall)
    TextView serviceInfoLookall;
    @BindView(R.id.mcoop_detail_companyImg)
    RoundImageView mcoopDetailCompanyImg;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_tag_1)
    TextView tvTag1;
    @BindView(R.id.tv_tag_2)
    TextView tvTag2;
    @BindView(R.id.tv_tag_3)
    TextView tvTag3;
    @BindView(R.id.item_index_recycler_img)
    CircleImageView itemIndexRecyclerImg;
    @BindView(R.id.index_new_isv)
    ImageView indexNewIsv;
    @BindView(R.id.item_index_recycler_Fl)
    FrameLayout itemIndexRecyclerFl;
    @BindView(R.id.item_index_nameTv)
    TextView itemIndexNameTv;
    @BindView(R.id.item_index)
    RelativeLayout itemIndex;
    @BindView(R.id.servicell)
    LinearLayout servicell;
    @BindView(R.id.imgv1)
    View imgv1;
    @BindView(R.id.resourceRl)
    RelativeLayout resourceRl;
    @BindView(R.id.companyheadRl)
    RelativeLayout companyheadRl;
    @BindView(R.id.mtitleTv)
    TextView mtitleTv;
    @BindView(R.id.bfinishImg)
    ImageView bfinishImg;
    @BindView(R.id.item_index_position)
    TextView itemIndexPosition;
    @BindView(R.id.bshare)
    ImageView bshare;

    private IndexNewAdapter indexNewAdapter;
    private List<IndexNewBean.ListBean> datas = new ArrayList<>();

    int page = 1;
    private int id;
    private String url;
    private Intent intent;
    private Bundle bundle;
    private int userIsV;
    private int mServiceId;
    private List<MyCompanyBean.UsersBean> userData = new ArrayList<>();
    private String description;
    private String shareUrl;
    private String thumbImg;
    private String wechatUrl;
    private String titleString;
    private AppCompatDialog shareDialog;

    @Override
    public int getLayoutId() {
        return R.layout.activity_company_infox;
    }

    public static void start(Context mContext, int companyId) {
        Intent intent = new Intent(mContext, CompanyInfoActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt("id", companyId);
        intent.putExtras(bundle);
        mContext.startActivity(intent, bundle);
    }


    @Override
    public void initViews(Bundle savedInstanceState) {

        mcompanyInfoRefresh.setEnableRefresh(false);
        mcompanyInfoRefresh.setEnableFooterTranslationContent(false);
        Bundle bundle = getIntent().getExtras();
        id = bundle.getInt("id");
        mcompanyInfoRefresh.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                page++;
                loadDatas(page, false);
            }
        });
        FullyLinearLayoutManager manager = new FullyLinearLayoutManager(CompanyInfoActivity.this, LinearLayoutManager.VERTICAL, false) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        mcompanyResourceList.setLayoutManager(manager);
        indexNewAdapter = new IndexNewAdapter(CompanyInfoActivity.this, R.layout.item_index_new, datas, 1,1);
        mcompanyResourceList.setAdapter(indexNewAdapter);
        showBookingToast(3);
        RequestManager.getInstance().getCorporateInfo(id, new GetMyCompanyCallback() {
            @Override
            public void onSuccess(MyCompanyBean bean) {
                dismissBookingToast();
                ShowUtils.showTextPerfect(mcompanyBrandname, bean.getBrand());
                if (!TextUtils.isEmpty(bean.getLogo())) {
                    ShowUtils.showImgPerfect(mcompanyHeadicon, bean.getLogo(), 2);
                    Glide.with(CompanyInfoActivity.this).asBitmap().load(bean.getLogo()).into(new SimpleTarget<Bitmap>() {
                        @Override
                        public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                            Bitmap scaledBitmap = Bitmap.createScaledBitmap(resource, resource.getWidth(), resource.getHeight() / 2, false);
                            Bitmap blurBitmap = FastBlur.doBlur(scaledBitmap, 100, true);
//                            Bitmap blurBitmapx = FastBlur.doBlur(blurBitmap, 50, true);
//                            Bitmap blurBitmap = rsBlur.rsBlur(CompanyInfoActivity.this,scaledBitmap,1,0.1f);
                            mcompanyBgimg.setImageBitmap(blurBitmap);
                        }
                    });
                } else {
                    mcompanyBgimg.setImageResource(R.mipmap.normal_bg);

                }
                dismissBookingToast();
                titleString = "【" + bean.getBrand() + "】" + bean.getName() + bean.getIntroduce();
                description = "找合作，上企鹊桥";
                thumbImg = bean.getLogo();
                shareUrl = bean.getShare_url();

                if (bean.getService_info() != null) {
                    servicell.setVisibility(View.VISIBLE);

                    //公司服务信息
                    mServiceId = bean.getService_info().getId();
                    String[] sourceStrArray = bean.getService_info().getImages().split(",");
                    ImageLoader.loadLocalImg(sourceStrArray[0], mcoopDetailCompanyImg);
                    ShowUtils.showTextPerfect(tvTitle, bean.getService_info().getTitle());
                    String[] tags = bean.getService_info().getRemark().split(",");
                    if (tags.length == 3) {
                        ShowUtils.showViewVisible(tvTag1, View.VISIBLE);
                        ShowUtils.showViewVisible(tvTag2, View.VISIBLE);
                        ShowUtils.showViewVisible(tvTag3, View.VISIBLE);
                        ShowUtils.showTextPerfect(tvTag1, tags[0]);
                        ShowUtils.showTextPerfect(tvTag2, tags[1]);
                        ShowUtils.showTextPerfect(tvTag3, tags[2]);
                    } else if (tags.length == 2) {
                        ShowUtils.showViewVisible(tvTag1, View.VISIBLE);
                        ShowUtils.showViewVisible(tvTag2, View.VISIBLE);
                        ShowUtils.showViewVisible(tvTag3, View.GONE);
                        ShowUtils.showTextPerfect(tvTag1, tags[0]);
                        ShowUtils.showTextPerfect(tvTag2, tags[1]);
                    } else if (tags.length == 1) {
                        ShowUtils.showViewVisible(tvTag1, View.VISIBLE);
                        ShowUtils.showViewVisible(tvTag2, View.GONE);
                        ShowUtils.showViewVisible(tvTag3, View.GONE);
                        ShowUtils.showTextPerfect(tvTag1, tags[0]);
                    }
                    ImageLoader.loadAvter(bean.getService_info().getHead_pic(), itemIndexRecyclerImg);
                    if (bean.getService_info().getIs_v() == 1) {
                        indexNewIsv.setVisibility(View.VISIBLE);
                    } else {
                        indexNewIsv.setVisibility(View.GONE);
                    }
                    ShowUtils.showTextPerfect(itemIndexNameTv, bean.getService_info().getRealname());
                    ShowUtils.showTextPerfect(itemIndexPosition, "· " + bean.getService_info().getPosition());
                } else {
                    servicell.setVisibility(View.GONE);
                }
                ShowUtils.showTextPerfect(mcompanyCompanyname, bean.getName());
                ShowUtils.showTextPerfect(mcompanyTypeTv, bean.getCompany_name());
//                ShowUtils.showTextPerfect(mcompanyMambernum, "公司成员" + "(" + bean.getUser_num() + ")");

                if (bean.getUser_num() <= 5) {
                    ShowUtils.showViewVisible(bcompanyInfoLookall, View.GONE);
                    ShowUtils.showViewVisible(infoPointgo, View.GONE);
                } else {
                    ShowUtils.showViewVisible(bcompanyInfoLookall, View.VISIBLE);
                    ShowUtils.showViewVisible(infoPointgo, View.VISIBLE);
                }
                if (bean.getIntroduce_verify() == 1) {//审核通知

                    if (!TextUtils.isEmpty(bean.getIntroduce())) {
                        ShowUtils.showTextPerfect(mcompanyIntroducetv, bean.getIntroduce());
                        mcompanyIntroducetv.setTextColor(ContextCompat.getColor(mContext, R.color._666));
                    } else {
                        ShowUtils.showTextPerfect(mcompanyIntroducetv, "暂无公司介绍");
                        mcompanyIntroducetv.setTextColor(ContextCompat.getColor(mContext, R.color.blue_bg_4B96F3));
                    }
                } else if (bean.getIntroduce_verify() == 0) {

                    ShowUtils.showTextPerfect(mcompanyIntroducetv, "公司信息正在审核中");
                    mcompanyIntroducetv.setTextColor(ContextCompat.getColor(mContext, R.color.blue_bg_4B96F3));
                } else if (bean.getIntroduce_verify() == 2) {//审核失败

                    ShowUtils.showTextPerfect(mcompanyIntroducetv, "暂无公司介绍");
                    mcompanyIntroducetv.setTextColor(ContextCompat.getColor(mContext, R.color.blue_bg_4B96F3));
                }
                if (mcompanyIntroducetv != null) {
                    if (mcompanyIntroducetv.getLineCount() <= 5) {
                        ShowUtils.showViewVisible(bcompanyIntromoreImg, View.GONE);
                        ShowUtils.showViewVisible(ycompanyIntromoreRl, View.GONE);
                    } else {
                        ShowUtils.showViewVisible(bcompanyIntromoreImg, View.VISIBLE);
                        ShowUtils.showViewVisible(ycompanyIntromoreRl, View.VISIBLE);
                    }
                }
                if (!TextUtils.isEmpty(bean.getUrl())) {
                    ShowUtils.showViewVisible(mcompanyNetRl, View.VISIBLE);
//                    ShowUtils.showViewVisible(ycompanyNetRl, View.VISIBLE);
//                    ShowUtils.showViewVisible(lineView3, View.VISIBLE);
                    url = bean.getUrl();
                    ShowUtils.showTextPerfect(mcompanyNettv, url);
                } else {
                    ShowUtils.showViewVisible(mcompanyNetRl, View.GONE);
//                    ShowUtils.showViewVisible(ycompanyNetRl, View.GONE);
//                    ShowUtils.showViewVisible(lineView3, View.GONE);
                }
                userIsV = bean.getUser_isv();
                if (bean.getUser_isjoin() == 1) {
                    ShowUtils.showViewVisible(beditCompanytv, View.VISIBLE);
                    ShowUtils.showViewVisible(yeditRl, View.VISIBLE);

                } else {
                    ShowUtils.showViewVisible(beditCompanytv, View.GONE);
                    ShowUtils.showViewVisible(yeditRl, View.GONE);


                }
                LinearLayoutManager manager = new LinearLayoutManager(CompanyInfoActivity.this, LinearLayoutManager.HORIZONTAL, false);
                mcompanyMemberRecycler.setLayoutManager(manager);
                userData.addAll(bean.getUsers());
                for (int i = 0; i < userData.size(); i++) {
                    userData.get(i).setItemType(MyCompanyBean.UsersBean.TEXT);
                }
                if (bean.getUser_num() > 5) {
                    userData.add(new MyCompanyBean.UsersBean(MyCompanyBean.UsersBean.IMG));
                }
                CompanyInfoPhotoAdapter adapter = new CompanyInfoPhotoAdapter(CompanyInfoActivity.this, userData, bean.getUser_num(), id);
                mcompanyMemberRecycler.setAdapter(adapter);
            }

            @Override
            public void onFailed(int code, String msg) {
                dismissBookingToast();
                ToastUtils.showCentetToast(CompanyInfoActivity.this, msg);
            }
        });

        loadDatas(page, false);

        mcompanyInfoRefresh.setOnMultiPurposeListener(new OnMultiPurposeListener() {
            @Override
            public void onHeaderMoving(RefreshHeader header, boolean isDragging, float percent, int offset, int headerHeight, int maxDragHeight) {
                Log.d("==CompanyInfoActivity", "offset:" + offset);
                Log.d("==CompanyInfoActivity", "headerHeight:" + headerHeight);
                Log.d("==CompanyInfoActivity", "maxDragHeight:" + maxDragHeight);
            }

            @Override
            public void onHeaderReleased(RefreshHeader header, int headerHeight, int maxDragHeight) {
                Log.d("==CompanyInfoActivity", "offset:" + headerHeight);

            }

            @Override
            public void onHeaderStartAnimator(RefreshHeader header, int headerHeight, int maxDragHeight) {

            }

            @Override
            public void onHeaderFinish(RefreshHeader header, boolean success) {

            }

            @Override
            public void onFooterMoving(RefreshFooter footer, boolean isDragging, float percent, int offset, int footerHeight, int maxDragHeight) {

            }

            @Override
            public void onFooterReleased(RefreshFooter footer, int footerHeight, int maxDragHeight) {

            }

            @Override
            public void onFooterStartAnimator(RefreshFooter footer, int footerHeight, int maxDragHeight) {

            }

            @Override
            public void onFooterFinish(RefreshFooter footer, boolean success) {

            }

            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {

            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {

            }

            @Override
            public void onStateChanged(@NonNull RefreshLayout refreshLayout, @NonNull RefreshState oldState, @NonNull RefreshState newState) {

            }
        });
        companyScroll.setListener(new MyScrollView.OnScrollListener() {
            @Override
            public void onScroll(int scrollY) {
                if (scrollY < 200) {
                    float x = scrollY / 200f;
                    mcompanyTitleRl.setAlpha(x);
                } else {
                    mcompanyTitleRl.setAlpha(1);
                }


            }
        });


    }

    private void loadDatas(final int page, boolean isShow) {
        if (isShow) {
            showBookingToast(1);
        }
        RequestManager.getInstance().getResourceByCorporateId(page, id, new GetResourceListCallback() {

            @Override
            public void onSuccess(IndexNewBean resultDO) {
                dismissBookingToast();


                if (page == 1) {
                    datas.clear();
                    if (resultDO.getList().size() == 0) {
                        ShowUtils.showViewVisible(mcompanyInfoImg, View.VISIBLE);
                        indexNewAdapter.removeAllFooterView();
                        mcompanyInfoRefresh.setEnableLoadMore(false);
                    } else {
                        ShowUtils.showViewVisible(mcompanyInfoImg, View.GONE);
                        if (resultDO.getHasMore() == 0) {
                            indexNewAdapter.setFooterView(footView);
                            mcompanyInfoRefresh.setEnableLoadMore(false);
                        } else {
                            indexNewAdapter.removeAllFooterView();
                            mcompanyInfoRefresh.setEnableLoadMore(true);
                        }
                    }
                } else {
                    if (resultDO.getHasMore() == 0) {
                        indexNewAdapter.setFooterView(footView);
                        mcompanyInfoRefresh.setEnableLoadMore(false);
                    } else {
                        indexNewAdapter.removeAllFooterView();
                        mcompanyInfoRefresh.setEnableLoadMore(true);
                    }
//                    ShowUtils.showViewVisible(mcompanyInfoImg,View.GONE);
                }
                datas.addAll(resultDO.getList());
                indexNewAdapter.notifyDataSetChanged();
                stopRefresh();
            }

            @Override
            public void onFailed(int code, String msg) {
                ToastUtils.showCentetToast(CompanyInfoActivity.this, msg);
                stopRefresh();
            }
        });
    }


    @OnClick({R.id.item_index, R.id.rlayout_all_service, R.id.bedit_companytv, R.id.bcompany_info_lookall, R.id.bcompany_intromoreImg, R.id.info_pointgo, R.id.mcompany_netRl, R.id.ycompany_intromoreRl, R.id.bfinishImg, R.id.bshare})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.item_index:
                //服务详情
                ServiceDetailActivity.start(CompanyInfoActivity.this, mServiceId);
                break;
            case R.id.rlayout_all_service:
                //全部服务
                CompanyAllServiceActivity.start(CompanyInfoActivity.this, id);
                break;
            case R.id.bedit_companytv:
                if (userIsV != 1) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(CompanyInfoActivity.this);
                    builder.setTitle("您未认证为本企业员工,请先进行名片认证");
                    builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    builder.setPositiveButton("去认证", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ApproveCardActivity.start(CompanyInfoActivity.this, "approve");
                            dialog.dismiss();
                        }
                    });
                    builder.show();
                    return;
                }


                intent = new Intent(CompanyInfoActivity.this, CompanyEditActivity.class);
                bundle = new Bundle();
                bundle.putInt("id", id);
                intent.putExtras(bundle);
                startActivity(intent);
                break;
            case R.id.bcompany_info_lookall:
                GroupMemberActivity.start(CompanyInfoActivity.this, id, 0, GroupMemberActivity.TYPE_COMPANY);
                break;
            case R.id.bcompany_intromoreImg:
                mcompanyIntroducetv.setMaxLines(Integer.MAX_VALUE);
                ShowUtils.showViewVisible(bcompanyIntromoreImg, View.GONE);
                ShowUtils.showViewVisible(ycompanyIntromoreRl, View.GONE);
                break;
            case R.id.info_pointgo:

                break;
            case R.id.mcompany_netRl:
                intent = new Intent(CompanyInfoActivity.this, AgreeMentActivity.class);
                bundle = new Bundle();
                bundle.putString("companyUrl", url);
                intent.putExtras(bundle);
                startActivity(intent);
                break;
            case R.id.ycompany_intromoreRl:
                mcompanyIntroducetv.setMaxLines(Integer.MAX_VALUE);
                ShowUtils.showViewVisible(bcompanyIntromoreImg, View.GONE);
                ShowUtils.showViewVisible(ycompanyIntromoreRl, View.GONE);
                break;
            case R.id.bfinishImg:
                finish();
                break;
            case R.id.bshare:
                //分享
                shareDialog = new QLClassRoomDialog.Builder(mContext).setContext(CompanyInfoActivity.this).setShareCallback(new QLClassRoomDialog.ShareNewCallback() {
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
                            default:

                                break;
                        }
                    }
                }).build();
                shareDialog.show();
                break;
            default:
                break;
        }
    }

    private void stopRefresh() {
        if (mcompanyInfoRefresh != null) {
            if (mcompanyInfoRefresh.isEnableLoadMore()) {
                mcompanyInfoRefresh.finishLoadMore(true);
            }
        }
    }

    private void shareCircle(SHARE_MEDIA share_media) {


        if (titleString == null || shareUrl == null || thumbImg == null) {
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

}
