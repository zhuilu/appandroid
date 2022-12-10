package com.xinniu.android.qiqueqiao.fragment.tab;


import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.umeng.analytics.MobclickAgent;
import com.xinniu.android.qiqueqiao.R;
import com.xinniu.android.qiqueqiao.activity.ApproveCardActivity;
import com.xinniu.android.qiqueqiao.activity.AuthenticationActivity;
import com.xinniu.android.qiqueqiao.activity.AuthenticationSuccessActivity;
import com.xinniu.android.qiqueqiao.activity.CertificationActivity;
import com.xinniu.android.qiqueqiao.activity.CommunicationRecordActivity;
import com.xinniu.android.qiqueqiao.activity.CompanyEditActivity;
import com.xinniu.android.qiqueqiao.activity.CompanyInfoActivity;
import com.xinniu.android.qiqueqiao.activity.FeedbackActivity;
import com.xinniu.android.qiqueqiao.activity.LoginNewActivity;
import com.xinniu.android.qiqueqiao.activity.MineInfoActivity;
import com.xinniu.android.qiqueqiao.activity.MyActListActivity;
import com.xinniu.android.qiqueqiao.activity.MyCollectActivity;
import com.xinniu.android.qiqueqiao.activity.MyCommentActivity;
import com.xinniu.android.qiqueqiao.activity.MyPushActivity;
import com.xinniu.android.qiqueqiao.activity.MyRewardActivity;
import com.xinniu.android.qiqueqiao.activity.MyTransactionOrdersActivity;
import com.xinniu.android.qiqueqiao.activity.MyWalletActivity;
import com.xinniu.android.qiqueqiao.activity.PersonCentetActivity;
import com.xinniu.android.qiqueqiao.activity.PropStoreActivity;
import com.xinniu.android.qiqueqiao.activity.SettingActivity;
import com.xinniu.android.qiqueqiao.activity.VipV4ListActivity;
import com.xinniu.android.qiqueqiao.activity.WelfareAgencyAvtivity;
import com.xinniu.android.qiqueqiao.base.LazyBaseFragment;
import com.xinniu.android.qiqueqiao.bean.CenterBean;
import com.xinniu.android.qiqueqiao.bean.DetailedUserInfoBean;
import com.xinniu.android.qiqueqiao.bean.MyCompanyBean;
import com.xinniu.android.qiqueqiao.customs.qldialog.QLTipDialog;
import com.xinniu.android.qiqueqiao.request.RequestManager;
import com.xinniu.android.qiqueqiao.request.callback.GetMyCompanyCallback;
import com.xinniu.android.qiqueqiao.request.callback.RequestCallback;
import com.xinniu.android.qiqueqiao.user.UserInfoHelper;
import com.xinniu.android.qiqueqiao.utils.ImageLoader;
import com.xinniu.android.qiqueqiao.utils.ShowUtils;
import com.xinniu.android.qiqueqiao.utils.ToastUtils;
import com.xinniu.android.qiqueqiao.widget.CircleImageView;

import butterknife.OnClick;
import retrofit2.Call;

/**
 * 我的
 * Created by BDXK on 2017/11/7.
 */
public class MineFragment extends LazyBaseFragment {
    CircleImageView mmineMyhead;
    ImageView mmineIsvImg;
    FrameLayout mineHeadFl;
    TextView btvMineName;
    ImageView misVipImg;
    TextView mtvMinePosition;
    RelativeLayout bmineInfo;
    View lineview;
    TextView bcoopCard;
    FrameLayout myCardFl;
    TextView bmineEdittv;
    LinearLayout ymineMyinfo;
    ImageView mypushImg;
    TextView mineMypushTitle;
    TextView mmineMypushnum;
    RelativeLayout bminePushRl;
    ImageView mineShapeLine;
    TextView mmineMyLword;
    RelativeLayout bmineMyLword;
    TextView mmineMyGroup;
    RelativeLayout bmineMyGroup;
    TextView mmineMyCollect;
    RelativeLayout bmineMyCollect;
    ImageView mmineVipIcon;
    TextView msurplusLimitstv;
    TextView bmineVip;
    RelativeLayout mmineVipInfoRl;
    ImageView vipCion;
    TextView tvAuthenticationStatus;
    RelativeLayout bmineAuthentication;
    ImageView taskCenter;
    TextView taskTv;
    RelativeLayout bmineTask;
    ImageView mineMycompany;
    RelativeLayout bmineMycompany;
    ImageView mineMyact;
    RelativeLayout bmineMyact;
    ImageView mineMyprop;
    RelativeLayout bmineMyprop;
    ImageView mineMycustomer;
    RelativeLayout bmineMycustomer;
    ImageView mineMyset;
    TextView textView3;
    RelativeLayout bmineMyset;
    ImageView companyVipImg;
    TextView tvVipNote;
    RelativeLayout bmineWelfareAgency;
    View view;
    ImageView imgRe;
    private CenterBean mCenterBean;

    public static MineFragment newInstance() {

        Bundle args = new Bundle();

        MineFragment fragment = new MineFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public int getLayoutId() {
        return R.layout.fragment_minex;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        getUserInfo();
    }

    /**
     * 获取用户信息,防止微信登录保存不了手机号
     */
    private void getUserInfo() {
        RequestManager.getInstance().getUserInfo(new RequestCallback<DetailedUserInfoBean>() {
            @Override
            public void requestStart(Call call) {

            }

            @Override
            public void onSuccess(DetailedUserInfoBean userDetailInfoBean) {
                UserInfoHelper.getIntance().setUsername(userDetailInfoBean.getMobile());
            }

            @Override
            public void onFailed(int code, String msg) {

            }

            @Override
            public void requestEnd() {

            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        buildData();

    }

    private void buildData() {
        Typeface typeface = Typeface.createFromAsset(mContext.getAssets(), "agency_bold.ttf");
        mmineMyLword.setTypeface(typeface);
        mmineMyGroup.setTypeface(typeface);
        mmineMyCollect.setTypeface(typeface);
        mineMypushTitle.setTypeface(typeface);

        if (!UserInfoHelper.getIntance().isLogin()) {
            ShowUtils.showViewVisible(imgRe, View.GONE);
            mmineMyhead.setImageResource(R.mipmap.mine_myhead);
            mmineIsvImg.setVisibility(View.GONE);

            btvMineName.setText("立即登录");
            mtvMinePosition.setText("登录后可发布合作信息和沟通合作");
            ymineMyinfo.setVisibility(View.GONE);
            misVipImg.setVisibility(View.GONE);
            mmineMypushnum.setText("--");
            mmineMyLword.setText("--");
            mmineMyGroup.setText("--");
            mmineMyCollect.setText("--");
            mmineVipInfoRl.setBackgroundResource(R.drawable.bg_mine_blue_vip);
            mmineVipIcon.setBackground(null);
            mmineVipIcon.setVisibility(View.GONE);
            tvVipNote.setVisibility(View.VISIBLE);
            msurplusLimitstv.setVisibility(View.GONE);
            bmineVip.setText("请先登录");
            bmineVip.setBackgroundResource(R.drawable.bg_mine_vip_open);
            bmineWelfareAgency.setVisibility(View.GONE);
            view.setVisibility(View.GONE);
            ShowUtils.showTextPerfect(tvAuthenticationStatus, "让他人觉得真实可信");
            return;
        }
        RequestManager.getInstance().center(new RequestCallback<CenterBean>() {
            @Override
            public void requestStart(Call call) {

            }

            @Override
            public void onSuccess(CenterBean centerBean) {
                mCenterBean = centerBean;
                UserInfoHelper.getIntance().setHeadUrl(mCenterBean.getUsers().getHead_pic());
                setDataToView(mCenterBean);
            }

            @Override
            public void onFailed(int code, String msg) {

            }

            @Override
            public void requestEnd() {

            }
        });


    }

    private void setDataToView(final CenterBean mCenterBean) {

        ShowUtils.showViewVisible(ymineMyinfo, View.VISIBLE);
        //真实姓名
        if (!TextUtils.isEmpty(mCenterBean.getUsers().getRealname())) {
            ShowUtils.showTextPerfect(btvMineName, mCenterBean.getUsers().getRealname());
        } else {
            btvMineName.setText("未设置姓名");
        }
        //头像,部分手机报mmineMyhead为null,加异常处理
        try {
            ImageLoader.loadAvter(mCenterBean.getUsers().getHead_pic(), mmineMyhead);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            mmineMyhead.setImageResource(R.mipmap.mine_myhead);
        }


        //职位
        if (!TextUtils.isEmpty(mCenterBean.getUsers().getPosition())) {
            ShowUtils.showTextPerfect(mtvMinePosition, mCenterBean.getUsers().getCompany() + mCenterBean.getUsers().getPosition());
        } else {
            ShowUtils.showTextPerfect(mtvMinePosition, "未设置职位");
        }
        //认证信息
        if (mCenterBean.getUsers().getIs_v() == 1) {
            ShowUtils.showViewVisible(mmineIsvImg, View.VISIBLE);
        } else {
            ShowUtils.showViewVisible(mmineIsvImg, View.GONE);
        }
        //发布条数
        ShowUtils.showTextPerfect(mmineMypushnum, mCenterBean.getRelease() + "");
        //留言条数
        ShowUtils.showTextPerfect(mmineMyLword, mCenterBean.getComment_count() + "");
        //群组/沟通记录条数
        ShowUtils.showTextPerfect(mmineMyGroup, mCenterBean.getTalk_count() + "");
        //收藏
        ShowUtils.showTextPerfect(mmineMyCollect, mCenterBean.getCollectCount() + "");

        if (mCenterBean.getUsers().getIs_corporate_vip() == 1) {
            //  ShowUtils.showViewVisible(companyVipImg, View.VISIBLE);

        } else {
            //  ShowUtils.showViewVisible(companyVipImg, View.GONE);

        }
        if (mCenterBean.getUsers().getIs_cloud_auth() == 1) {
            ShowUtils.showViewVisible(imgRe, View.VISIBLE);
            imgRe.setBackground(null);
            imgRe.setBackgroundResource(R.mipmap.mine_re);

        } else {
            ShowUtils.showViewVisible(imgRe, View.VISIBLE);
            imgRe.setBackground(null);
            imgRe.setBackgroundResource(R.mipmap.mine_re_no);

        }
        //会员
        if (mCenterBean.getUsers().getIs_vip() == 2) {
//            ShowUtils.showViewVisible(misVipImg, View.VISIBLE);
//            misVipImg.setBackground(null);
//            misVipImg.setBackgroundResource(R.mipmap.svip_iconx_two);

            mmineVipInfoRl.setBackgroundResource(R.drawable.bg_mineyellow_vip);
            mmineVipIcon.setVisibility(View.VISIBLE);
            tvVipNote.setVisibility(View.GONE);
            mmineVipIcon.setBackground(null);
            mmineVipIcon.setImageResource(R.mipmap.svip_iconx_two);
            msurplusLimitstv.setVisibility(View.VISIBLE);
            String str = "<font color='#82663D'>剩余</font><font color='#FA6F4F'>" + mCenterBean.getUsers().getNum() + "次</font><font color='#82663D'>沟通权限</font>";
            msurplusLimitstv.setText(Html.fromHtml(str));
            bmineVip.setText("立即续费");
            bmineVip.setBackgroundResource(R.drawable.bg_mine_vip_open_yellow);
        } else if (mCenterBean.getUsers().getIs_vip() == 1) {
//            ShowUtils.showViewVisible(misVipImg, View.VISIBLE);
//            misVipImg.setBackground(null);
//            ShowUtils.showImageResource(misVipImg, R.mipmap.vip_iconx_two);

            mmineVipInfoRl.setBackgroundResource(R.drawable.bg_mine_blue_vip);
            mmineVipIcon.setVisibility(View.VISIBLE);
            tvVipNote.setVisibility(View.GONE);
            mmineVipIcon.setBackground(null);
            mmineVipIcon.setImageResource(R.mipmap.vip_iconx_two);
            msurplusLimitstv.setVisibility(View.VISIBLE);
            String str = "<font color='#999999'>剩余</font><font color='#FA6F4F'>" + mCenterBean.getUsers().getNum() + "次</font><font color='#999999'>沟通权限</font>";
            msurplusLimitstv.setText(Html.fromHtml(str));
            bmineVip.setText("立即续费");
            bmineVip.setBackgroundResource(R.drawable.bg_mine_vip_open);
        } else {
//            ShowUtils.showViewVisible(misVipImg, View.GONE);

            mmineVipInfoRl.setBackgroundResource(R.drawable.bg_mine_blue_vip);
            mmineVipIcon.setVisibility(View.GONE);
            tvVipNote.setVisibility(View.VISIBLE);
            msurplusLimitstv.setVisibility(View.VISIBLE);
            String str = "<font color='#999999'>剩余</font><font color='#FA6F4F'>" + mCenterBean.getUsers().getNum() + "次</font><font color='#999999'>沟通权限</font>";
            msurplusLimitstv.setText(Html.fromHtml(str));
            bmineVip.setText("立即开通");
            bmineVip.setBackgroundResource(R.drawable.bg_mine_vip_open);
        }
        //名片上传状态
        if (!TextUtils.isEmpty(mCenterBean.getUsers().getIs_v() + "")) {
            // 0：未上传 1：上传2：正在审核中 3：审核失败）
            switch (mCenterBean.getUsers().getIs_v()) {
                case 0:
                    ShowUtils.showTextPerfect(tvAuthenticationStatus, "让他人觉得真实可信");
                    ShowUtils.showTextColor(tvAuthenticationStatus, ContextCompat.getColor(mContext, R.color.text_color_AA));
                    break;
                case 1:
                    ShowUtils.showTextPerfect(tvAuthenticationStatus, "已认证");
                    ShowUtils.showTextColor(tvAuthenticationStatus, ContextCompat.getColor(mContext, R.color.colorPrimary));
                    break;
                case 2:
                    ShowUtils.showTextPerfect(tvAuthenticationStatus, "审核中");
                    ShowUtils.showTextColor(tvAuthenticationStatus, ContextCompat.getColor(mContext, R.color.text_color_AA));
                    break;
                case 3:
                    ShowUtils.showTextPerfect(tvAuthenticationStatus, "未通过(请重新认证)");
                    ShowUtils.showTextColor(tvAuthenticationStatus, ContextCompat.getColor(mContext, R.color.text_color_AA));
                    break;
            }
        }

        if (mCenterBean.getWelfare_club() == 1) {
            bmineWelfareAgency.setVisibility(View.VISIBLE);
            view.setVisibility(View.VISIBLE);
        } else {
            bmineWelfareAgency.setVisibility(View.GONE);
            view.setVisibility(View.GONE);
        }

    }

    @Override
    protected void lazyLoad() {

    }

    @OnClick({R.id.img_re, R.id.bmine_myreward, R.id.bmine_welfare_agency, R.id.bmine_myorder, R.id.bmine_mywallet, R.id.btv_mine_name, R.id.bmine_info, R.id.bcoop_card, R.id.bmine_edittv, R.id.bmine_my_lword, R.id.bmine_my_collect, R.id.mmine_vip_infoRl, R.id.bmine_authentication, R.id.bmine_task, R.id.bmine_my_group, R.id.bmine_mycompany, R.id.bmine_myact, R.id.bmine_myprop, R.id.bmine_mycustomer, R.id.bmine_myset, R.id.bmine_pushRl})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_re:
                if (!isLogin()) {
                    return;
                }
                if (mCenterBean.getInfo_status() == 0) {
                    new QLTipDialog.Builder(getContext())
                            .setCancelable(false)
                            .setCancelableOnTouchOutside(false)
                            .setMessage("请先完善个人信息")
                            .setPositiveButton("去完善", new QLTipDialog.IPositiveCallback() {
                                @Override
                                public void onClick() {
                                    MineInfoActivity.start(getContext());
                                }
                            })
                            .setNegativeButton("取消", new QLTipDialog.INegativeCallback() {
                                @Override
                                public void onClick() {
                                }
                            })
                            .show((AppCompatActivity) getActivity());
                } else {

                    if (mCenterBean.getUsers().getIs_cloud_auth() == 0) {
                        CertificationActivity.start(getContext(), 0);
                    }
                }
                break;
            case R.id.bmine_myreward:
                if (!isLogin()) {
                    return;
                }
                MyRewardActivity.start(mContext);

                break;
            case R.id.bmine_welfare_agency:
                if (!isLogin()) {
                    return;
                }
                MobclickAgent.onEvent(getContext(), "mine_welfareclub");//统计点击次数
                WelfareAgencyAvtivity.start(mContext);

                break;
            case R.id.bmine_myorder:
                if (!isLogin()) {
                    return;
                }
                //我的订单
                MyTransactionOrdersActivity.start(mContext);

                break;
            case R.id.bmine_mywallet:
                if (!isLogin()) {
                    return;
                }/**
             * 进入我的钱包页面
             * @param activity :从哪个activity的跳转
             */
//                JrmfClient.intentWallet(getActivity());
                MyWalletActivity.start(getContext(), mCenterBean.getUsers().getIs_vip());

                break;
            case R.id.btv_mine_name:
                if (!isLogin()) {
                    return;
                }
                break;
            case R.id.bmine_info:
                if (!isLogin()) {
                    return;
                }
                break;
            case R.id.bcoop_card://我的合作卡片
                MobclickAgent.onEvent(getContext(), "mine_cooperation");//统计点击次数
                if (!isLogin()) {
                    return;
                }

                PersonCentetActivity.start(mContext, UserInfoHelper.getIntance().getUserId() + "", true);
                break;
            case R.id.bmine_edittv:
                if (!isLogin()) {
                    return;
                }
                if (mCenterBean != null) {
                    MineInfoActivity.start(getContext());
                }
                break;
            case R.id.bmine_my_lword://我的留言
                MobclickAgent.onEvent(getContext(), "mine_comment");//统计点击次数
                if (!isLogin()) {
                    return;
                }

                MyCommentActivity.start(mContext);
                break;
            case R.id.bmine_my_collect://我的收藏
                MobclickAgent.onEvent(getContext(), "mine_collection");//统计点击次数
                if (!isLogin()) {
                    return;
                }

                MyCollectActivity.start(getContext());
                break;
            case R.id.mmine_vip_infoRl://vip
                MobclickAgent.onEvent(getContext(), "mine_vipcenter");//统计点击次数
                if (!isLogin()) {
                    return;
                }

                Intent intent = new Intent(getContext(), VipV4ListActivity.class);
                startActivity(intent);
                break;
            case R.id.bmine_authentication:
                MobclickAgent.onEvent(getContext(), "mine_jobIdentity");//统计点击次数
                if (!isLogin()) {
                    return;
                }
                if (mCenterBean.getInfo_status() == 0) {
                    new QLTipDialog.Builder(getContext())
                            .setCancelable(false)
                            .setCancelableOnTouchOutside(false)
                            .setMessage("请先完善个人信息")
                            .setPositiveButton("去完善", new QLTipDialog.IPositiveCallback() {
                                @Override
                                public void onClick() {
                                    MineInfoActivity.start(getContext());
                                }
                            })
                            .setNegativeButton("取消", new QLTipDialog.INegativeCallback() {
                                @Override
                                public void onClick() {
                                }
                            })
                            .show((AppCompatActivity) getActivity());
                } else {
//                    switch (mCenterBean.getUsers().getIs_v()) {
//
//                        case 0://tvAuthenticationStatus.setText("上传名片");
//                        case 3://tvAuthenticationStatus.setText("审核失败");
//                        case 1://tvAuthenticationStatus.setText("已上传");
//                        case 2://tvAuthenticationStatus.setText("审核中");
//                            isPerfect();
//                            break;
//                    }
                    if ((mCenterBean.getUsers().getIs_cloud_auth() == 0 && mCenterBean.getUsers().getIs_v() == 1)) {

                        //实人认证未通过并且身份认证通过，直接进入实人认证
                        CertificationActivity.start(getContext(), 0);

                    } else if (mCenterBean.getUsers().getIs_cloud_auth() == 1 && mCenterBean.getUsers().getIs_v() == 1) {
                        //认证成功界面
                        AuthenticationSuccessActivity.start(getContext(), mCenterBean.getUsers().getCorporate_name(), mCenterBean.getUsers().getIdentification_name());


                    } else if ((mCenterBean.getUsers().getIs_cloud_auth() == 0 && mCenterBean.getUsers().getIs_v() == 0) || (mCenterBean.getUsers().getIs_cloud_auth() == 1 && mCenterBean.getUsers().getIs_v() == 0) || (mCenterBean.getUsers().getIs_cloud_auth() == 0 && mCenterBean.getUsers().getIs_v() == 3) || (mCenterBean.getUsers().getIs_cloud_auth() == 1 && mCenterBean.getUsers().getIs_v() == 3)) {
                        //实人认证未通过并且身份认证也没有认证或者认证失败，直接进入实人认证
                        //实人认证通过，身份认证未认证
                        AuthenticationActivity.start(getContext(), mCenterBean.getUsers().getIs_cloud_auth(), mCenterBean.getUsers().getIs_v(), mCenterBean.getUsers().getCorporate_name(), mCenterBean.getUsers().getIdentification_name(), mCenterBean.getUsers().getIs_join(), mCenterBean.getUsers().getRealname());

                    } else if ((mCenterBean.getUsers().getIs_cloud_auth() == 1 || mCenterBean.getUsers().getIs_cloud_auth() == 0) && mCenterBean.getUsers().getIs_v() == 2) {
                        //实人认证通过，身份认证审核中或者失败
                        isPerfect();

                    }

                }
                break;
            case R.id.bmine_task:
                MobclickAgent.onEvent(getContext(), "mine_task");//统计点击次数
                if (!isLogin()) {
                    return;
                }
                ApproveCardActivity.start(mContext, "task");
                break;
            case R.id.bmine_my_group:
                MobclickAgent.onEvent(getContext(), "mine_groupList");//统计点击次数
                if (!isLogin()) {
                    return;
                }
                //  MyGroupActivity.start(mContext);
                //沟通记录
                CommunicationRecordActivity.start(mContext);
                break;
            case R.id.bmine_mycompany:
                MobclickAgent.onEvent(getContext(), "mine_company");//统计点击次数
                if (!isLogin()) {
                    return;
                }
                int companyId = UserInfoHelper.getIntance().getCenterBean().getUsers().getCorporate_id();
                if (companyId > 0) {
                    getMyCompany();
                } else {
                    Intent intentx = new Intent(getContext(), CompanyEditActivity.class);
                    startActivity(intentx);
                }
                break;
            case R.id.bmine_myact:
                MobclickAgent.onEvent(getContext(), "mine_eventList");//统计点击次数
                if (!isLogin()) {
                    return;
                }
                MyActListActivity.start(mContext);
                break;
            case R.id.bmine_myprop:
                MobclickAgent.onEvent(getContext(), "mine_propsmall");//统计点击次数
                if (!isLogin()) {
                    return;
                }
                PropStoreActivity.start(mContext);
                break;
            case R.id.bmine_mycustomer:
                //客服改意见反馈了
                //  MobclickAgent.onEvent(getContext(), "mine_contact");//统计点击次数
                if (!isLogin()) {
                    return;
                }
                //  //IMUtils.singleChat(getActivity(), String.valueOf(mCenterBean.getUsers().getF_id()), "客服", "3", "");
                FeedbackActivity.start(getContext());

                break;
            case R.id.bmine_myset:
                MobclickAgent.onEvent(getContext(), "mine_setting");//统计点击次数
                if (!isLogin()) {
                    return;
                }
                SettingActivity.start(getContext());


                break;
            case R.id.bmine_pushRl:
                MobclickAgent.onEvent(getContext(), "mine_publish");//统计点击次数
                if (!isLogin()) {
                    return;
                }
                MyPushActivity.start(mContext);
                break;
            default:
                break;
        }
    }


    private void getMyCompany() {
        RequestManager.getInstance().getMyCompany(new GetMyCompanyCallback() {
            @Override
            public void onSuccess(MyCompanyBean bean) {
                if (bean == null) {
                    return;
                }
                MyCompanyBean mBean = bean;
                int companyId = mBean.getId();
                CompanyInfoActivity.start(mContext, companyId);

            }

            @Override
            public void onFailed(int code, String msg) {
                ToastUtils.showCentetImgToast(getActivity(), msg);
            }
        });

    }

    private void isPerfect() {
        ApproveCardActivity.start(mContext, "approve");
    }

    private boolean isLogin() {
        if (!UserInfoHelper.getIntance().isLogin()) {
            // TODO: 2017/12/20
//            ToastUtils.showCentetImgToast(getContext(), "还未登录");
            LoginNewActivity.start(mContext);
        }
        return UserInfoHelper.getIntance().isLogin();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        mmineMyhead = (CircleImageView) rootView.findViewById(R.id.mmine_myhead);
        mmineIsvImg = rootView.findViewById(R.id.mmine_isv_img);

        mmineVipInfoRl = (RelativeLayout) rootView.findViewById(R.id.mmine_vip_infoRl);

        mmineVipIcon = (ImageView) rootView.findViewById(R.id.mmine_vip_icon);
        tvVipNote = (TextView) rootView.findViewById(R.id.tv_vip_note);
        mineHeadFl = rootView.findViewById(R.id.mine_headFl);
        btvMineName = rootView.findViewById(R.id.btv_mine_name);
        misVipImg = rootView.findViewById(R.id.mis_vip_img_m);
        mtvMinePosition = rootView.findViewById(R.id.mtv_mine_position);
        bmineInfo = rootView.findViewById(R.id.bmine_info);
        lineview = rootView.findViewById(R.id.lineview);
        bcoopCard = rootView.findViewById(R.id.bcoop_card);
        myCardFl = rootView.findViewById(R.id.myCardFl);
        bmineEdittv = rootView.findViewById(R.id.bmine_edittv);
        ymineMyinfo = rootView.findViewById(R.id.ymine_myinfo);
        mypushImg = rootView.findViewById(R.id.mypush_img);
        mineMypushTitle = rootView.findViewById(R.id.mine_mypush_title);
        mmineMypushnum = rootView.findViewById(R.id.mmine_mypushnum);
        bminePushRl = rootView.findViewById(R.id.bmine_pushRl);
        mineShapeLine = rootView.findViewById(R.id.mine_shape_line);
        mmineMyLword = rootView.findViewById(R.id.mmine_my_lword);
        bmineMyLword = rootView.findViewById(R.id.bmine_my_lword);
        mmineMyGroup = rootView.findViewById(R.id.mmine_my_group);
        bmineMyGroup = rootView.findViewById(R.id.bmine_my_group);
        mmineMyCollect = rootView.findViewById(R.id.mmine_my_collect);
        bmineMyCollect = rootView.findViewById(R.id.bmine_my_collect);
        msurplusLimitstv = rootView.findViewById(R.id.msurplus_limitstv);
        bmineVip = rootView.findViewById(R.id.bmine_vip);

        vipCion = rootView.findViewById(R.id.vip_cion);
        tvAuthenticationStatus = rootView.findViewById(R.id.tv_authentication_status);
        bmineAuthentication = rootView.findViewById(R.id.bmine_authentication);
        taskCenter = rootView.findViewById(R.id.task_center);
        taskTv = rootView.findViewById(R.id.task_tv);
        bmineTask = rootView.findViewById(R.id.bmine_task);
        mineMycompany = rootView.findViewById(R.id.mine_mycompany);
        bmineMycompany = rootView.findViewById(R.id.bmine_mycompany);
        mineMyact = rootView.findViewById(R.id.mine_myact);
        bmineMyact = rootView.findViewById(R.id.bmine_myact);
        mineMyprop = rootView.findViewById(R.id.mine_myprop);
        bmineMyprop = rootView.findViewById(R.id.bmine_myprop);
        mineMycustomer = rootView.findViewById(R.id.mine_mycustomer);
        bmineMycustomer = rootView.findViewById(R.id.bmine_mycustomer);
        mineMyset = rootView.findViewById(R.id.mine_myset);
        textView3 = rootView.findViewById(R.id.textView3);
        bmineMyset = rootView.findViewById(R.id.bmine_myset);
        companyVipImg = rootView.findViewById(R.id.company_vip_img);
        bmineWelfareAgency = rootView.findViewById(R.id.bmine_welfare_agency);
        view = rootView.findViewById(R.id.view);
        imgRe = rootView.findViewById(R.id.img_re);
        return rootView;
    }


}
