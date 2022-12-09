package com.xinniu.android.qiqueqiao.activity;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
//import android.support.v7.app.AppCompatDialog;
//import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.xinniu.android.qiqueqiao.GroupBeanDao;
import com.xinniu.android.qiqueqiao.OtherUserInfoDao;
import com.xinniu.android.qiqueqiao.R;
import com.xinniu.android.qiqueqiao.adapter.GroupMessageMemberAdapter;
import com.xinniu.android.qiqueqiao.base.BaseActivity;
import com.xinniu.android.qiqueqiao.base.BaseApp;
import com.xinniu.android.qiqueqiao.bean.GroupBean;
import com.xinniu.android.qiqueqiao.bean.GroupInfoBean;
import com.xinniu.android.qiqueqiao.bean.UpgradeGroupBean;
import com.xinniu.android.qiqueqiao.customs.qldialog.QLAddGroupWayDialog;
import com.xinniu.android.qiqueqiao.customs.qldialog.QLQRCodeDialog;
import com.xinniu.android.qiqueqiao.customs.qldialog.QLShareDialog;
import com.xinniu.android.qiqueqiao.request.RequestManager;
import com.xinniu.android.qiqueqiao.request.callback.AllResultDoCallback;
import com.xinniu.android.qiqueqiao.request.callback.GetGroupInfoCallback;
import com.xinniu.android.qiqueqiao.request.callback.UpgradeGroupCallback;
import com.xinniu.android.qiqueqiao.user.UserInfoHelper;
import com.xinniu.android.qiqueqiao.utils.BitmapUtils;
import com.xinniu.android.qiqueqiao.utils.ComUtils;
import com.xinniu.android.qiqueqiao.utils.NoScrollRecyclerView;
import com.xinniu.android.qiqueqiao.utils.ShareUtils;
import com.xinniu.android.qiqueqiao.utils.ShowUtils;
import com.xinniu.android.qiqueqiao.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.rong.imkit.RongIM;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.Conversation;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

/**
 * Created by yuchance on 2018/10/10.
 */

public class GroupMessagemActivity extends BaseActivity {

    private static final int PERMISSION_WRITE_EXTERNAL_STORAGE = 1000;

    @BindView(R.id.beditTv)
    TextView beditTv;
    @BindView(R.id.mgroupNameTv)
    TextView mgroupNameTv;
    @BindView(R.id.mgroupCodeTv)
    TextView mgroupCodeTv;

    @BindView(R.id.memberinfoRl)
    RelativeLayout memberinfoRl;
    @BindView(R.id.mgroup)
    TextView mgroup;
    @BindView(R.id.mgroup_person_numtv)
    TextView mgroupPersonNumtv;
    @BindView(R.id.bupgrade_group)
    TextView bupgradeGroup;
    @BindView(R.id.bmember_num)
    TextView bmemberNum;
    @BindView(R.id.mcoop_infoTv)
    TextView mcoopInfoTv;
    @BindView(R.id.madd_group_way)
    TextView maddGroupWay;
    @BindView(R.id.munset_switch)
    TextView munsetSwitch;
    @BindView(R.id.ygroupintroRlx)
    RelativeLayout ygroupintroRlx;
    @BindView(R.id.mmessage_disturb_switch)
    CheckBox mmessageDisturbSwitch;
    @BindView(R.id.mgroupmemberRecycler)
    NoScrollRecyclerView mgroupmemberRecycler;
    @BindView(R.id.baddwayRl)
    RelativeLayout baddwayRl;
    private int groupId;
    private boolean isDisturb;
    private GroupMessageMemberAdapter adapter;
    private List<GroupInfoBean.UserListBean> datas = new ArrayList<>();
    private String groupName;
    private int userIdentity;
    private String groupIntro;
    private String groupIndustry;
    private int groupIndustryId;
    private String cityName;
    private int cityId;
    private int identity;
    private AppCompatDialog qrCodeDialog;
    private String headUrl;
    private String shareUrl;
    private Bitmap mQRBitmap;
    private AppCompatDialog shareDialog;
    private int groupNumber;

    private AppCompatDialog verifydialog;
    private int isVerify;
    private String notice;

    final OtherUserInfoDao dao = BaseApp.getApplication().getDaoSession().getOtherUserInfoDao();
    private String groupCode;


    public static void start(Context context, int groupId) {
        Intent intent = new Intent(context, GroupMessagemActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt("groupId", groupId);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }


    @Override
    public int getLayoutId() {
        return R.layout.activity_group_messagem;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        ComUtils.addActivity(this);
        topStatusBar(true);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            groupId = bundle.getInt("groupId");
        }
        LinearLayoutManager manager = new LinearLayoutManager(GroupMessagemActivity.this, LinearLayoutManager.HORIZONTAL, false);
        mgroupmemberRecycler.setLayoutManager(manager);
        adapter = new GroupMessageMemberAdapter(GroupMessagemActivity.this, datas);
        adapter.setSetOnClick(new GroupMessageMemberAdapter.setOnClick() {
            @Override
            public void setOnClick() {
                FriendLxActivity.startx(GroupMessagemActivity.this,FriendLxActivity.INVITEGROUP,groupId);
            }
        });
        mgroupmemberRecycler.setAdapter(adapter);
        initDatas();
    }

    private void initDatas() {
        showBookingToast(1);
        RequestManager.getInstance().getGroupInformation(groupId, new GetGroupInfoCallback() {

            @Override
            public void onSuccess(GroupInfoBean bean) {
                dismissBookingToast();
                groupName = bean.getName();
                groupIntro = bean.getIntroduction();
                groupIndustry = bean.getCategory_name();
                groupIndustryId = bean.getCategory();
                cityName = bean.getCity_name();
                cityId = bean.getCity();
                identity = bean.getUser_identity();
                headUrl = bean.getHead_pic();
                shareUrl = bean.getShare_url();
                notice = bean.getNotice();
                userIdentity = bean.getUser_identity();
                ShowUtils.showTextPerfect(mgroupNameTv, bean.getName());
                ShowUtils.showTextPerfect(mgroupCodeTv, "群号 :" + bean.getGroup_number());
                ShowUtils.showTextPerfect(mgroupPersonNumtv, bean.getNumber() + "人群");
                groupNumber = bean.getNumber();
                groupCode = bean.getGroup_number();
                if (groupNumber == 500) {
                    ShowUtils.showViewVisible(bupgradeGroup, View.GONE);
                } else {
                    if (userIdentity == 2) {
                        ShowUtils.showViewVisible(bupgradeGroup, View.VISIBLE);
                    } else {
                        ShowUtils.showViewVisible(bupgradeGroup, View.GONE);
                    }
                }

                ShowUtils.showTextPerfect(bmemberNum, bean.getNum() + "人");
                ShowUtils.showTextPerfect(mcoopInfoTv, bean.getResources_count() + "条");
                isVerify = bean.getIs_verify();
                if (isVerify == 0) {
                    ShowUtils.showTextPerfect(maddGroupWay, "无需验证");
                } else if (bean.getIs_verify() == 1) {
                    ShowUtils.showTextPerfect(maddGroupWay, "需要身份验证");
                } else if (bean.getIs_verify() == 2) {
                    ShowUtils.showTextPerfect(maddGroupWay, "不允许任何人加群");
                }
                if (TextUtils.isEmpty(bean.getNotice())) {
                    ShowUtils.showViewVisible(munsetSwitch, View.VISIBLE);
                    ShowUtils.showViewVisible(ygroupintroRlx, View.GONE);
                } else {
                    ShowUtils.showViewVisible(munsetSwitch, View.GONE);
                    ShowUtils.showViewVisible(ygroupintroRlx, View.VISIBLE);
                    ShowUtils.showTextPerfect(mgroup, bean.getNotice());
                }

                if (userIdentity == 2 || userIdentity == 1) {
                    ShowUtils.showViewVisible(beditTv, View.VISIBLE);
                } else {
                    ShowUtils.showViewVisible(beditTv, View.GONE);
                }
                if (userIdentity == 2){
                    baddwayRl.setVisibility(View.VISIBLE);
                }else {
                    baddwayRl.setVisibility(View.GONE);
                }

                datas.clear();
                if (bean.getUserList().size()<4) {
                    for (int i = 0; i < bean.getUserList().size(); i++) {
                        bean.getUserList().get(i).setItemType(GroupInfoBean.UserListBean.COMMON);
                    }
                }else {
                    bean.getUserList().remove(bean.getUserList().size()-1);
                    for (int i = 0; i < bean.getUserList().size(); i++) {
                        bean.getUserList().get(i).setItemType(GroupInfoBean.UserListBean.COMMON);
                    }
                }
                bean.getUserList().add(new GroupInfoBean.UserListBean(GroupInfoBean.UserListBean.INVITE));
                datas.addAll(bean.getUserList());
                adapter.notifyDataSetChanged();


            }

            @Override
            public void onFailed(int code, String msg) {
                dismissBookingToast();
                ToastUtils.showCentetToast(GroupMessagemActivity.this, msg);
            }
        });
        RongIM.getInstance().getConversationNotificationStatus(Conversation.ConversationType.GROUP, groupId + "", new RongIMClient.ResultCallback<Conversation.ConversationNotificationStatus>() {
            @Override
            public void onSuccess(Conversation.ConversationNotificationStatus conversationNotificationStatus) {
                if (conversationNotificationStatus == Conversation.ConversationNotificationStatus.DO_NOT_DISTURB) {
                    mmessageDisturbSwitch.setChecked(true);
                    isDisturb = true;
                } else if (conversationNotificationStatus == Conversation.ConversationNotificationStatus.NOTIFY) {
                    mmessageDisturbSwitch.setChecked(false);
                    isDisturb = false;

                }
            }

            @Override
            public void onError(RongIMClient.ErrorCode errorCode) {

            }
        });
    }


    @OnClick({R.id.bt_finish, R.id.beditTv, R.id.b_qrcode_img, R.id.bgoto_memberImg, R.id.bmember_num, R.id.b_share_img, R.id.bupgrade_group, R.id.mmessage_disturb_switch, R.id.bdeletegrouptv, R.id.memberinfoRl, R.id.baddwayRl,R.id.coopRl})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bt_finish:
                finish();
                break;
            case R.id.beditTv:
                EditGroupDataActivity.start(GroupMessagemActivity.this, groupId, groupName, groupIntro, groupIndustry, groupIndustryId, cityName, cityId);
                break;
            case R.id.b_qrcode_img:
                /**
                 * 初始化并显示QRCodeDialog
                 */
                showQrDialog();
                break;
            case R.id.bgoto_memberImg:
                GroupMemberManageActivity.start(GroupMessagemActivity.this, groupId, groupName, userIdentity);
                break;
            case R.id.bmember_num:
                GroupMemberManageActivity.start(GroupMessagemActivity.this, groupId, groupName, identity);
                break;
            case R.id.b_share_img:
                showShareDialog();
                break;
            case R.id.bupgrade_group:
                int vip = UserInfoHelper.getIntance().getCenterBean().getUsers().getIs_vip();
                AlertDialog.Builder dialog;
                if (vip == 1) {
                    if (groupNumber == 100) {
                        dialog = new AlertDialog.Builder(GroupMessagemActivity.this);
                        dialog.setTitle("将升级至200人群组?").setNegativeButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                upgradeGroup();
                            }
                        }).setPositiveButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        }).show();

                    } else if (groupNumber == 200) {
                        dialog = new AlertDialog.Builder(GroupMessagemActivity.this);
                        dialog.setTitle("500人群组为SVIP专享,点击开通SVIP").setNegativeButton("去开通", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                startActivity(new Intent(GroupMessagemActivity.this, VipV4ListActivity.class));
                                dialog.dismiss();
                            }
                        }).setPositiveButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        }).show();

                    }

                } else if (vip == 2) {
                    dialog = new AlertDialog.Builder(GroupMessagemActivity.this);
                    dialog.setTitle("将升级至500人群组?").setNegativeButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            upgradeGroup();
                        }
                    }).setPositiveButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    }).show();

                } else {
                    dialog = new AlertDialog.Builder(GroupMessagemActivity.this);
                    dialog.setTitle("开通VIP,即可享受高级群组特权").setNegativeButton("去开通", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            startActivity(new Intent(GroupMessagemActivity.this, VipV4ListActivity.class));
                            dialog.dismiss();
                        }
                    }).setPositiveButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    dialog.show();
                }

                break;
            case R.id.bdeletegrouptv:
                AlertDialog.Builder builder = new AlertDialog.Builder(GroupMessagemActivity.this);
                if (userIdentity == 2) {
                    builder.setTitle("您是此群的群主,退出将直接解散该群,确认解散吗?");
                    builder.setNegativeButton("确认", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            deleteGroup();
                        }
                    });
                    builder.setPositiveButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    }).show();

                } else {
                    builder.setTitle("确认退出群组吗?");
                    builder.setNegativeButton("确认", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            deleteGroup();
                        }
                    });
                    builder.setPositiveButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    }).show();

                }
                final GroupBeanDao groupDao = BaseApp.getApplication().getDaoSession().getGroupBeanDao();
                        List<GroupBean> list = groupDao.queryBuilder().offset(0).limit(1).orderAsc(GroupBeanDao.Properties.Id).build().list();
                        if (list.size()>0) {
                            Long id = list.get(0).getTheid();
                            dao.deleteByKey(id);
                        }
                        RongIM.getInstance().removeConversation(Conversation.ConversationType.GROUP, groupId+"", (RongIMClient.ResultCallback) null);



                break;
            case R.id.mmessage_disturb_switch:
                if (isDisturb) {
                    RongIM.getInstance().setConversationNotificationStatus(Conversation.ConversationType.GROUP, groupId + "", Conversation.ConversationNotificationStatus.NOTIFY, new RongIMClient.ResultCallback<Conversation.ConversationNotificationStatus>() {
                        @Override
                        public void onSuccess(Conversation.ConversationNotificationStatus conversationNotificationStatus) {
//                            mmessageDisturbSwitch.setChecked(true);
                            isDisturb = false;
                        }

                        @Override
                        public void onError(RongIMClient.ErrorCode errorCode) {

                        }
                    });
                } else {
                    RongIM.getInstance().setConversationNotificationStatus(Conversation.ConversationType.GROUP, groupId + "", Conversation.ConversationNotificationStatus.DO_NOT_DISTURB, new RongIMClient.ResultCallback<Conversation.ConversationNotificationStatus>() {
                        @Override
                        public void onSuccess(Conversation.ConversationNotificationStatus conversationNotificationStatus) {
//                            mmessageDisturbSwitch.setChecked(false);
                            isDisturb = true;
                        }

                        @Override
                        public void onError(RongIMClient.ErrorCode errorCode) {

                        }
                    });
                }
                break;
            case R.id.memberinfoRl:
                if (TextUtils.isEmpty(notice)){
                    if (userIdentity==2||userIdentity==1) {
                        EditGroupActivity.start(GroupMessagemActivity.this,"groupannounce",groupId,groupName,"");
                    }else {
                        builder = new AlertDialog.Builder(GroupMessagemActivity.this);
                        builder.setTitle("只有群主或管理员才能修改群公告").setPositiveButton("知道了", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                        builder.show();
                    }
                }else {
                    GroupNewNoticeActivity.start(GroupMessagemActivity.this, groupId, groupName,identity);
                }
                break;
            case R.id.baddwayRl:

                popgroupWay();

                break;
            case R.id.coopRl:
                CircleResourceActivity.start(GroupMessagemActivity.this,groupId);
                break;
            default:
                break;
        }
    }

    private void popgroupWay() {
        verifydialog = new QLAddGroupWayDialog.Builder(mContext)
                .setType(3)
                .setmShareCallback(new QLAddGroupWayDialog.ShareNewCallback() {
                    @Override
                    public void onClickShare(QLAddGroupWayDialog.ShareType type, String typeWay) {
                        switch (type) {
                            case NOVERIFY:
                                if (isVerify == 0){
                                    return;
                                }
                                joinManner(0, typeWay);
                                break;
                            case VERIFY:
                                if (isVerify == 1){
                                    return;
                                }
                                joinManner(1, typeWay);
                                break;
                            case NOADD:
                                if (isVerify == 2){
                                    return;
                                }
                                joinManner(2, typeWay);
                                break;
                            default:
                                break;
                        }
                    }
                })
                .build();
        verifydialog.show();
    }

    private void joinManner(final int isType, final String typeWay) {
        RequestManager.getInstance().joinManner(groupId, isType, new AllResultDoCallback() {
            @Override
            public void onSuccess(String msg) {
                ToastUtils.showCentetToast(GroupMessagemActivity.this, msg);
                isVerify = isType;
                maddGroupWay.setText(typeWay);
            }

            @Override
            public void onFailed(int code, String msg) {
                ToastUtils.showCentetToast(GroupMessagemActivity.this, msg);
            }
        });
    }

    private void deleteGroup() {
        RequestManager.getInstance().quitGroup(groupId, new AllResultDoCallback() {
            @Override
            public void onSuccess(String msg) {
                ToastUtils.showCentetToast(GroupMessagemActivity.this, msg);
                ComUtils.finishshortAll();
            }

            @Override
            public void onFailed(int code, String msg) {
                ToastUtils.showCentetToast(GroupMessagemActivity.this, msg);
            }
        });


    }

    private void upgradeGroup() {
        RequestManager.getInstance().upgradeGroup(groupName, groupId, new UpgradeGroupCallback() {
            @Override
            public void onSuccess(UpgradeGroupBean bean,String msg) {
                if (bean.getNumber() == 200) {
                    ShowUtils.showTextPerfect(mgroupPersonNumtv, bean.getNumber() + "人群");
                    ShowUtils.showViewVisible(bupgradeGroup, View.VISIBLE);
                } else if (bean.getNumber() == 500) {
                    ShowUtils.showTextPerfect(mgroupPersonNumtv, bean.getNumber() + "人群");
                    ShowUtils.showViewVisible(bupgradeGroup, View.GONE);
                }
                ToastUtils.showCentetToast(GroupMessagemActivity.this,msg);
            }

            @Override
            public void onFailue(int code, String msg) {
                ToastUtils.showCentetToast(GroupMessagemActivity.this,msg);
            }
        });

    }

    private void showQrDialog() {

        String qrcode = shareUrl + "/" + UserInfoHelper.getIntance().getUserId();
        GroupQrcodeActivity.start(GroupMessagemActivity.this,groupName,qrcode,headUrl,shareUrl);

//        qrCodeDialog = new QLQRCodeDialog.Builder(mContext)
//                .setTitle("【企鹊桥】" + groupName)//设置标题
//                .setImageHead(headUrl)
//                .setQRCodeString(shareUrl + "/" + UserInfoHelper.getIntance().getUserId())//设置二维码信息
//                .setShareCallback(new QLQRCodeDialog.ShareCallback() {
//                    @Override
//                    public void onClickShare(Bitmap bitmap) {
//                        mQRBitmap = bitmap;
//                        requestPermission();
//                    }
//                }).build();
//        qrCodeDialog.show();
    }

    /**
     * 获取权限保存图片
     */
    @AfterPermissionGranted(PERMISSION_WRITE_EXTERNAL_STORAGE)
    private void requestPermission() {
        if (EasyPermissions.hasPermissions(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            if (mQRBitmap == null) {
                return;
            }
            BitmapUtils.saveImageToGallery(mContext, mQRBitmap);
            ToastUtils.showCentetImgToast(mContext, "保存图片成功");
        } else {
            EasyPermissions.requestPermissions(this, getString(R.string.permission_need_save_bitmap),
                    PERMISSION_WRITE_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 401 && resultCode == 402) {
            initDatas();
        }
    }

    private void showShareDialog() {
        if (shareDialog != null) {
            shareDialog.show();
            return;
        }
        shareDialog = new QLShareDialog.Builder(mContext)
                .setWithQRCode(1)//设置是否伴随二维码分享
                .setShareCallback(new QLShareDialog.ShareCallback() {
                    @Override
                    public void onClickShare(QLShareDialog.ShareType type) {
                        switch (type) {
                            case SHARE_WEIXIN:
                                shareCircle(SHARE_MEDIA.WEIXIN);
                                break;
                            case SHARE_CIRCLE:
                                shareCircle(SHARE_MEDIA.WEIXIN_CIRCLE);
                                break;
                            case SHARE_QQ:
                                shareCircle(SHARE_MEDIA.QQ);
                                break;
                            case SHARE_WEIBO:
                                shareCircle(SHARE_MEDIA.SINA);
                                break;
                            case SHARE_QRCODE:
                                showQrDialog();
                                break;
                        }
                    }
                }).build();
        shareDialog.show();
    }

    private void shareCircle(SHARE_MEDIA share_media) {

        if (headUrl == null) {
            return;
        }
        ShareUtils.shareWebUrl(
                GroupMessagemActivity.this
                , headUrl,
                share_media,
                shareUrl + "/" + UserInfoHelper.getIntance().getUserId(),
                "邀请您加入群聊"+"『"+groupName+"』",
                "群号: "+groupCode, new UMShareListener() {
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


