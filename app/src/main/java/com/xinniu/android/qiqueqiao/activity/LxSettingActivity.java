package com.xinniu.android.qiqueqiao.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
//import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.xinniu.android.qiqueqiao.R;
import com.xinniu.android.qiqueqiao.base.BaseActivity;
import com.xinniu.android.qiqueqiao.bean.FriendStatusBean;
import com.xinniu.android.qiqueqiao.bean.GoFriendApplyBean;
import com.xinniu.android.qiqueqiao.dialog.AlertDialogUtils;
import com.xinniu.android.qiqueqiao.request.RequestManager;
import com.xinniu.android.qiqueqiao.request.callback.AllResultDoCallback;
import com.xinniu.android.qiqueqiao.request.callback.CommonCallback;
import com.xinniu.android.qiqueqiao.request.callback.GetFriendStatusCallback;
import com.xinniu.android.qiqueqiao.request.callback.GoFriendApplyCallback;
import com.xinniu.android.qiqueqiao.request.converter.ResultDO;
//import com.xinniu.android.qiqueqiao.utils.IMUtils;
import com.xinniu.android.qiqueqiao.utils.ImageLoader;
import com.xinniu.android.qiqueqiao.utils.ShowUtils;
import com.xinniu.android.qiqueqiao.utils.ToastUtils;
import com.xinniu.android.qiqueqiao.widget.CircleImageView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.Conversation;

/**
 * Created by yuchance on 2018/12/5.
 */

public class LxSettingActivity extends BaseActivity {
    @BindView(R.id.mcircle_headImg)
    CircleImageView mcircleHeadImg;
    @BindView(R.id.mnametv)
    TextView mnametv;
    @BindView(R.id.misvip)
    ImageView misvip;
    @BindView(R.id.mpostion)
    TextView mpostion;
    @BindView(R.id.btoplx_check)
    CheckBox btoplxCheck;
    @BindView(R.id.bdislx_check)
    CheckBox bdislxCheck;
    @BindView(R.id.blx_settingstv)
    TextView blxSettingstv;
    private String targetId;
    private String name;
    private String position;
    private String headPic;
    private String isVip = "";
    private RongIMClient.BlacklistStatus mBlacklistStatus;
    private boolean isTop;
    private int friendStatus;

    public static void start(Context context, String targetId, String name, String position, String headPic, String isVip) {
        Intent intent = new Intent(context, LxSettingActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("targetId", targetId);
        bundle.putString("name", name);
        bundle.putString("position", position);
        bundle.putString("headPic", headPic);
        bundle.putString("isVip", isVip);
        intent.putExtras(bundle);
        context.startActivity(intent, bundle);


    }


    @Override
    public int getLayoutId() {
        return R.layout.activity_lx_setting;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        topStatusBar(true);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            targetId = bundle.getString("targetId");
            name = bundle.getString("name");
            position = bundle.getString("position");
            headPic = bundle.getString("headPic");
            isVip = bundle.getString("isVip");
            mnametv.setText(name);
            mpostion.setText(position);
            ImageLoader.loadAvter(headPic, mcircleHeadImg);
            if (isVip.equals("1")) {
                ShowUtils.showBackgroud(misvip, ContextCompat.getDrawable(LxSettingActivity.this, R.mipmap.vip_iconx));
                ShowUtils.showTextColor(mnametv, ContextCompat.getColor(LxSettingActivity.this, R.color.king_color));

            } else if (isVip.equals("2")) {
                ShowUtils.showBackgroud(misvip, ContextCompat.getDrawable(LxSettingActivity.this, R.mipmap.svip_iconx));
                ShowUtils.showTextColor(mnametv, ContextCompat.getColor(LxSettingActivity.this, R.color.king_color));

            } else {
                ShowUtils.showViewVisible(misvip, View.GONE);
                ShowUtils.showTextColor(mnametv, ContextCompat.getColor(LxSettingActivity.this, R.color.text_color_1));

            }

        }


        RongIMClient.getInstance().getConversation(Conversation.ConversationType.PRIVATE, targetId, new RongIMClient.ResultCallback<Conversation>() {
            @Override
            public void onSuccess(Conversation conversation) {
                //解决异常
                try {
                    isTop = conversation.isTop();
                } catch (NullPointerException e) {
                    e.printStackTrace();
                }

                if (isTop) {
                    btoplxCheck.setChecked(true);
                } else {
                    btoplxCheck.setChecked(false);
                }
            }

            @Override
            public void onError(RongIMClient.ErrorCode errorCode) {

            }
        });
        RongIMClient.getInstance().getBlacklistStatus(targetId, new RongIMClient.ResultCallback<RongIMClient.BlacklistStatus>() {
            @Override
            public void onSuccess(RongIMClient.BlacklistStatus blacklistStatus) {
                mBlacklistStatus = blacklistStatus;
                if (mBlacklistStatus == RongIMClient.BlacklistStatus.IN_BLACK_LIST) {
                    bdislxCheck.setChecked(true);
                } else {
                    bdislxCheck.setChecked(false);
                }
            }

            @Override
            public void onError(RongIMClient.ErrorCode errorCode) {

            }
        });
        RequestManager.getInstance().getFriendStatus(Integer.parseInt(targetId), new GetFriendStatusCallback() {
            @Override
            public void onSuccess(FriendStatusBean bean) {
                friendStatus = bean.getStatus();
                if (friendStatus == 1) {
                    blxSettingstv.setSelected(false);
                    blxSettingstv.setText("删除好友");
                } else {
                    blxSettingstv.setSelected(true);
                    blxSettingstv.setText("+ 加好友");
                }

            }

            @Override
            public void onFailed(int code, String msg) {

            }
        });

    }

    @OnClick({R.id.bt_finish, R.id.btoplx_check, R.id.bdislx_check, R.id.blx_settingstv, R.id.blx_informRl, R.id.bgotoPersenInfo})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bt_finish:
                finish();
                break;
            case R.id.btoplx_check:
                if (isTop) {
                    IMUtils.setTop(targetId, false);
                } else {
                    IMUtils.setTop(targetId, true);
                }
                break;
            case R.id.bdislx_check:
                if (mBlacklistStatus == RongIMClient.BlacklistStatus.IN_BLACK_LIST) {
                    blacklistBehavior(2);
                } else {
                    blacklistBehavior(1);
                }
                break;
            case R.id.blx_settingstv:
                if (friendStatus == 1) {
                    AlertDialogUtils.AlertDialog(LxSettingActivity.this, "确认删除好友", "确认", "取消", new AlertDialogUtils.setOnClick() {
                        @Override
                        public void setLeftOnClick(DialogInterface dialog) {
                            deleteFriend();
                            dialog.dismiss();
                        }

                        @Override
                        public void setRightOnClick(DialogInterface dialog) {
                            dialog.dismiss();
                        }
                    });
                } else {

                    addFriend();
                }
                break;
            case R.id.blx_informRl:
                ReportListActivity.start(LxSettingActivity.this, 1, Integer.parseInt(targetId));
                break;
            case R.id.bgotoPersenInfo:
                PersonCentetActivity.start(LxSettingActivity.this, targetId);
                break;
            default:
                break;
        }
    }

    private void addFriend() {
        RequestManager.getInstance().goFriendApply(Integer.parseInt(targetId), 2, new GoFriendApplyCallback() {
            @Override
            public void onSuccess(GoFriendApplyBean data, String msg) {
                AlertDialogUtils.AlertDialog(LxSettingActivity.this, msg, "知道了", "", new AlertDialogUtils.setOnClick() {
                    @Override
                    public void setLeftOnClick(DialogInterface dialog) {
                        dialog.dismiss();
                        finish();
                    }

                    @Override
                    public void setRightOnClick(DialogInterface dialog) {

                    }
                });


            }

            @Override
            public void onFailed(int code, String msg) {

            }
        });


    }

    private void deleteFriend() {
        RequestManager.getInstance().delFriend(Integer.parseInt(targetId), new AllResultDoCallback() {
            @Override
            public void onSuccess(String msg) {
                ToastUtils.showCentetToast(LxSettingActivity.this, msg);
                finish();
            }

            @Override
            public void onFailed(int code, String msg) {
                ToastUtils.showCentetToast(LxSettingActivity.this, msg);
            }
        });
    }

    private void blacklistBehavior(final int action) {
        RequestManager.getInstance().blacklistBehavior(Integer.valueOf(targetId), action, new CommonCallback() {
            @Override
            public void onSuccess(ResultDO resultDO) {
//                if (action == 1) {
//                    bdislxCheck.setChecked(false);
//                }
//                if (action == 2) {
//                    bdislxCheck.setChecked(true);
//                }
            }

            @Override
            public void onFailed(int code, String msg) {
                ToastUtils.showCentetImgToast(LxSettingActivity.this, msg);
            }
        });

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick()
    public void onViewClicked() {
    }
}
