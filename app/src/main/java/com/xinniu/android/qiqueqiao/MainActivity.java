package com.xinniu.android.qiqueqiao;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.umeng.analytics.MobclickAgent;
import com.xinniu.android.qiqueqiao.activity.ActMemberActivity;
import com.xinniu.android.qiqueqiao.activity.ApproveCardActivity;
import com.xinniu.android.qiqueqiao.activity.CertificationActivity;
import com.xinniu.android.qiqueqiao.activity.CompanyEditActivity;
import com.xinniu.android.qiqueqiao.activity.CoopDetailActivity;
import com.xinniu.android.qiqueqiao.activity.GoseeBillActivity;
import com.xinniu.android.qiqueqiao.activity.GroupMessageActivity;
import com.xinniu.android.qiqueqiao.activity.LoginNewActivity;
import com.xinniu.android.qiqueqiao.activity.MyPushActivity;
import com.xinniu.android.qiqueqiao.activity.PublishSelectTypeActivity;
import com.xinniu.android.qiqueqiao.adapter.MainFragmentPagerAdapter;
import com.xinniu.android.qiqueqiao.base.BaseActivity;
import com.xinniu.android.qiqueqiao.bean.AppVertion;
import com.xinniu.android.qiqueqiao.bean.DetailedUserInfoBean;
import com.xinniu.android.qiqueqiao.bean.GetPopupBean;
import com.xinniu.android.qiqueqiao.bean.NewsV2Bean;
import com.xinniu.android.qiqueqiao.bean.QrcodeBean;
import com.xinniu.android.qiqueqiao.bean.UserIdBean;
import com.xinniu.android.qiqueqiao.bean.VipPopUpBean;
import com.xinniu.android.qiqueqiao.common.Constants;
import com.xinniu.android.qiqueqiao.customs.ViewPager;
import com.xinniu.android.qiqueqiao.customs.qldialog.QLTipDialog;
import com.xinniu.android.qiqueqiao.dialog.ActivityDialog;
import com.xinniu.android.qiqueqiao.dialog.AlertDialogUtils;
import com.xinniu.android.qiqueqiao.dialog.NewPersonPricilegesDialog;
import com.xinniu.android.qiqueqiao.dialog.PushpokeTimeDialog;
import com.xinniu.android.qiqueqiao.dialog.UpdateDialog;
import com.xinniu.android.qiqueqiao.fragment.message.TalkListFragment;
import com.xinniu.android.qiqueqiao.fragment.tab.CompanyFragment;
import com.xinniu.android.qiqueqiao.fragment.tab.IndexFragment;
import com.xinniu.android.qiqueqiao.fragment.tab.IndexNewTwoFragment;
import com.xinniu.android.qiqueqiao.fragment.tab.MineFragment;
import com.xinniu.android.qiqueqiao.request.RequestManager;
import com.xinniu.android.qiqueqiao.request.callback.AllResultDoCallback;
import com.xinniu.android.qiqueqiao.request.callback.CommonCallback;
import com.xinniu.android.qiqueqiao.request.callback.GetAppVertionCallback;
import com.xinniu.android.qiqueqiao.request.callback.GetNewsV2Callback;
import com.xinniu.android.qiqueqiao.request.callback.GetPopupCallback;
import com.xinniu.android.qiqueqiao.request.callback.GetUserIdListCallback;
import com.xinniu.android.qiqueqiao.request.callback.QrCallback;
import com.xinniu.android.qiqueqiao.request.callback.RequestCallback;
import com.xinniu.android.qiqueqiao.request.callback.VipPopupCallback;
import com.xinniu.android.qiqueqiao.request.converter.ResultDO;
import com.xinniu.android.qiqueqiao.user.UserInfoHelper;
import com.xinniu.android.qiqueqiao.utils.ComUtils;
import com.xinniu.android.qiqueqiao.utils.PreferenceHelper;
import com.xinniu.android.qiqueqiao.utils.StatusBarUtil;
import com.xinniu.android.qiqueqiao.utils.ToastUtils;
import com.xinniu.android.qiqueqiao.zxing.activity.CaptureActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.OnClick;
import cn.jpush.android.api.JPushInterface;
import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;
import retrofit2.Call;

//import android.support.annotation.NonNull;
import androidx.fragment.app.Fragment;
//import android.support.v7.app.AlertDialog;
//import com.xinniu.android.qiqueqiao.utils.IMUtils;

/**
 * ?????????
 */
public class MainActivity extends BaseActivity implements ViewPager.OnPageChangeListener, GestureDetector.OnGestureListener, EasyPermissions.PermissionCallbacks {

    @BindView(R.id.viewPager)
    ViewPager viewPager;
    @BindViews({R.id.tab_main, R.id.tab_message, R.id.tab_circle, R.id.tab_mine})
    List<TextView> tabViews;
    @BindView(R.id.tab_main)
    TextView indexBt;
    @BindView(R.id.msg_red_point)
    TextView msgRedPoint;
    @BindView(R.id.tab_circle_msg)
    ImageView tabCircleMsg;

    public static int SYSTEM_NUM = 0;
    @BindView(R.id.tv_publish_tips)
    TextView tvPublishTips;
    // private IndexFragment indexNewFragment;//?????????
//    private IndexNewFragment indexNewFragment;//???/?????????
    private IndexNewTwoFragment indexNewFragment;//???/?????????
    private TalkListFragment messageFragment;//??????
    //    private CircleFragment circleFragment;
    private CompanyFragment companyFragment;//?????????
    private MineFragment mineFragment;//??????
    private ArrayList<Fragment> fragments = new ArrayList<>();
    private int pageNo = 0;
    private RedPointBroadcastReciver mBroadcastReceiver;
    private long mTaskId;
    public final static int SELECTED_REQUEST_CODE = 501;
    public final static int RELEASE_SUCCESS = 401;
    public final static int RELEASE_FAILED = 400;
    private UpdateDialog dialog;

    private PushpokeTimeDialog timeDialog;
    private NewPersonPricilegesDialog newPersonPricilegesDialog;
    private Call mCall;

    public static final String BROADCAST_ACTION = "com.xinniu.android.qiqueqiao.abc";
    private String systemnum;
    private String intactnum;
    private String appName = "";

    //??????AMapLocationClient?????????
    public AMapLocationClient mLocationClient = null;
    //??????AMapLocationClientOption??????
    public AMapLocationClientOption mLocationOption = null;
    private static final int PERMISSION_LOCATION = 1000;
    private int flag = 0;

    private CountDownTimer mTimer;

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }


    @Override
    public void initViews(Bundle savedInstanceState) {
//        if (Build.VERSION.SDK_INT >= 23) {
//            String[] mPermissionList = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE};
//            ActivityCompat.requestPermissions(this, mPermissionList, 123);
//
//        }
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            flag = bundle.getInt("flag");

        }
        if (mTimer == null) {
            mTimer = new CountDownTimer((long) (10 * 1000), 1000) {

                @Override
                public void onTick(long millisUntilFinished) {

                }

                @Override
                public void onFinish() {
                    tvPublishTips.setVisibility(View.GONE);
                }
            };

        }

        //???????????????
        mLocationClient = new AMapLocationClient(getApplicationContext());
        //????????????????????????
        mLocationClient.setLocationListener(mLocationListener);
        //?????????AMapLocationClientOption??????
        mLocationOption = new AMapLocationClientOption();
        /**
         * ?????????????????????????????????????????????????????????????????????????????????????????????
         */
        mLocationOption.setLocationPurpose(AMapLocationClientOption.AMapLocationPurpose.SignIn);
        //?????????????????????AMapLocationMode.Hight_Accuracy?????????????????????
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        //???????????????????????????
        //??????????????????false???
        mLocationOption.setOnceLocation(true);
        //????????????3s???????????????????????????????????????
        //??????setOnceLocationLatest(boolean b)?????????true??????????????????SDK???????????????3s?????????????????????????????????????????????????????????true???setOnceLocation(boolean b)????????????????????????true???????????????????????????false???
        mLocationOption.setOnceLocationLatest(true);
        mLocationClient.setLocationOption(mLocationOption);
        if (Build.VERSION.SDK_INT >= 23) {
            requestPermission();
        } else {
            mLocationClient.startLocation();
        }

        checkUpdate();//??????????????????

        Set<String> PushArray = new HashSet<>();
        int userId = UserInfoHelper.getIntance().getUserId();
        PushArray.add(userId + "");
        if (userId != 0) {
            JPushInterface.resumePush(getApplicationContext());
            Log.d("==MainActivity", "????????????????????????");
            JPushInterface.setAlias(getApplicationContext(), 0, userId + "");
            JPushInterface.setTags(getApplicationContext(), 0, PushArray);
        }

        //??????
        //   indexNewFragment = new IndexFragment();
        indexNewFragment = new IndexNewTwoFragment();
        //??????
        messageFragment = new TalkListFragment();

        //??????
//        circleFragment = new CircleFragment();
        companyFragment = new CompanyFragment();
//        circleFragment.setOnCheckCircleListener(this);

        //??????
        mineFragment = new MineFragment();


        mBroadcastReceiver = new RedPointBroadcastReciver(new RedPointBroadcastReciver.RedPointListner() {
            @Override
            public void onReceive(int type, boolean isShow, int count) {

                if (type == RedPointHelper.MSG_TYPE_MAIN) {
                    if (isShow) {
                        if (count > 0) {
                            msgRedPoint.setVisibility(View.VISIBLE);
                            msgRedPoint.setText("" + (count + SYSTEM_NUM));
                        } else {
                            msgRedPoint.setVisibility(View.GONE);
                        }
                    } else {
                        msgRedPoint.setVisibility(View.GONE);
                    }
                }
            }
        });


        IntentFilter intentFilter = new IntentFilter();

        //???????????????????????????
        intentFilter.addAction(RedPointHelper.ACTION_TYPE_MAIN_MSG);


        //??????Context???registerReceiver??????????????????????????????
        registerReceiver(mBroadcastReceiver, intentFilter);

        fragments.add(indexNewFragment);
        fragments.add(messageFragment);
        fragments.add(companyFragment);

        fragments.add(mineFragment);
        tabViews.get(pageNo).setSelected(true);
        viewPager.setAdapter(new MainFragmentPagerAdapter(getSupportFragmentManager(), fragments));
        viewPager.setOffscreenPageLimit(4);
        viewPager.setOnPageChangeListener(this);
        final GestureDetector gestureScanner = new GestureDetector(this);
        gestureScanner.setOnDoubleTapListener(new GestureDetector.OnDoubleTapListener() {
            @Override
            public boolean onDoubleTap(MotionEvent e) {
                //?????????????????????
                if (viewPager.getCurrentItem() == 0) {
//                    indexNewFragment.moveToTop();
//                    indexNewFragment.refreshPage();
                } else {
                    viewPager.setCurrentItem(0, false);
                }
                return false;
            }

            @Override
            public boolean onDoubleTapEvent(MotionEvent e) {
                //?????????????????????
                return false;
            }

            @Override
            public boolean onSingleTapConfirmed(MotionEvent e) {
                //??????????????????????????????
                viewPager.setCurrentItem(0, false);
//                                                      ToastUtils.showCentetImgToast(MainActivity.this,"??????????????????");
                if (viewPager.getCurrentItem() == 0) {
                    dismissBookingToast();
                    //  indexNewFragment.moveToTop();
                }
                StatusBarUtil.StatusBarLightMode(MainActivity.this, false);
                //?????????????????????????????????????????????id?????????????????????????????????????????????????????????????????????????????????5?????????????????????????????????????????????
                if (Constants.userIdList.length() > 0) {
                    //????????????????????????5??????
                    Long s = (System.currentTimeMillis() - Constants.times) / (1000 * 60);
                    if (s > 5) {
                        getUserIdList();
                    }
                } else {
                    getUserIdList();
                }
                return false;
            }
        });

        indexBt.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {

                return gestureScanner.onTouchEvent(event);
            }
        });

        timeDialog = new PushpokeTimeDialog();
        newPersonPricilegesDialog = new NewPersonPricilegesDialog();
        if (isLoginState()) {
            getUserIdList();
        } else {
            Constants.userIdList = "";
            Constants.times = 0;
        }
        isShowActivity();
        isPushSwitch();
        isActivate();
        getUserInfo();
        if (isLoginState()) {
            isNew();//????????????
        }

        if (flag == 1) {
            tvPublishTips.setVisibility(View.VISIBLE);
            flag = 0;
            mTimer.start();
        }


    }


    private void isNew() {
        if (Constants.newcomer_package == 1) {
            Constants.newcomer_package = 0;
            newPersonPricilegesDialog.show(getSupportFragmentManager(), "new");
        }
    }

    private void getUserIdList() {
        RequestManager.getInstance().getUserIdList(new GetUserIdListCallback() {
            @Override
            public void onSuccess(UserIdBean list) {
                Constants.userIdList = list.getUser_list();
                Constants.times = System.currentTimeMillis();
            }

            @Override
            public void onFailed(int code, String msg) {

            }
        });


    }

    private void isActivate() {
        RequestManager.getInstance().vipPopup(new VipPopupCallback() {
            @Override
            public void onSuccess(VipPopUpBean bean) {
                if (bean.getIs_have() == 1) {
                    AlertDialogUtils.AlertDialog(MainActivity.this, "??????????????????SVIP?????????????????????", "??????????????????>????????????>???????????????????????????", "????????????", "?????????", new AlertDialogUtils.setOnClick() {
                        @Override
                        public void setLeftOnClick(final DialogInterface dialog) {
                            showBookingToast(2);
                            RequestManager.getInstance().waitActivated(new AllResultDoCallback() {
                                @Override
                                public void onSuccess(String msg) {
                                    dismissBookingToast();
                                    dialog.dismiss();
                                }

                                @Override
                                public void onFailed(int code, String msg) {
                                    dismissBookingToast();
                                    dialog.dismiss();
                                }
                            });
                        }

                        @Override
                        public void setRightOnClick(DialogInterface dialog) {
                            ActMemberActivity.start(MainActivity.this);
                            dialog.dismiss();
                        }
                    });
                }

            }

            @Override
            public void onFailed(int code, String msg) {
                ToastUtils.showCentetToast(MainActivity.this, msg);
            }
        });

    }

    private void getUserInfo() {
        if (!isLoginState()) {
            Log.d("====MainActivity", "?????????");
            return;
        }

        RequestManager.getInstance().getUserInfo(new RequestCallback<DetailedUserInfoBean>() {
            @Override
            public void requestStart(Call call) {
                mCall = call;
            }

            @Override
            public void onSuccess(DetailedUserInfoBean detailedUserInfoBean) {

            }

            @Override
            public void onFailed(int code, String msg) {

            }

            @Override
            public void requestEnd() {

            }
        });

    }

    private void isShowActivity() {
        RequestManager.getInstance().getPopup(new GetPopupCallback() {
            @Override
            public void onSuccess(GetPopupBean bean) {
                int actId = bean.getId();
                Log.d("==MainActivity", "actId:" + actId);
                int type = bean.getType();
                String imgPath = bean.getImg_path();
                String jumpUrl = bean.getJump_url();
                int status = bean.getStatus();
                int actedId = UserInfoHelper.getIntance().getActId();
                if (status == 1) {
                    if (type == 1) {

                        if (actId != actedId) {
                            ActivityDialog actDialog = new ActivityDialog(imgPath, actId, jumpUrl);
                            actDialog.show(getSupportFragmentManager(), "act");
                        }
                    } else if (type == 2) {
                        ActivityDialog dialog = new ActivityDialog(imgPath, actId, jumpUrl);
                        dialog.show(getSupportFragmentManager(), "act");
                    } else if (type == 3) {
                        if (isActTimeWeek()) {
                            ActivityDialog dialog = new ActivityDialog(imgPath, actId, jumpUrl);
                            dialog.show(getSupportFragmentManager(), "act");
                        }

                    }
                    if (actId != actedId) {
                        UserInfoHelper.getIntance().setActTimeWeek(0);
                        UserInfoHelper.getIntance().setActTime(0);
                    }

                }
            }

            @Override
            public void onFailed(int code, String msg) {

            }
        });


    }

    private void isPushSwitch() {

        if (!ComUtils.isNotificationEnabled(this)) {
            if (!isFiveDayTime()) {
                return;
            }
            timeDialog.show(getSupportFragmentManager(), "time");
        }

    }


    @OnClick({R.id.tab_circle, R.id.tab_publish, R.id.tab_message, R.id.tab_mine})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tab_message://??????
                if (!UserInfoHelper.getIntance().isLogin()) {
                    LoginNewActivity.start(MainActivity.this);
                    return;
                }
                if (viewPager.getCurrentItem() == 1) {
                    messageFragment.srcollToUnReadMsg();
                }
                StatusBarUtil.StatusBarLightMode(MainActivity.this, true);
                viewPager.setCurrentItem(1, false);
//                messageFragment.moveToTalkListFragment();
                RequestManager.getInstance().getNewsV2(new GetNewsV2Callback() {
                    @Override
                    public void onSuccess(NewsV2Bean bean) {
                        systemnum = bean.getSystem().getNum() + "";
                        intactnum = bean.getInteractive().getNum() + "";
                        messageFragment.setNumFragment(systemnum, intactnum);
                    }

                    @Override
                    public void onFailed(int code, String msg) {

                    }
                });

                break;
            case R.id.tab_publish://??????
                MobclickAgent.onEvent(mContext, "main_publish");//??????banner????????????
                if (!UserInfoHelper.getIntance().isLogin()) {
                    LoginNewActivity.start(MainActivity.this);
                    return;
                }
                toReleaseActivity();
                tvPublishTips.setVisibility(View.GONE);
                break;
            case R.id.tab_circle://??????


                StatusBarUtil.StatusBarLightMode(MainActivity.this, true);
                viewPager.setCurrentItem(2, false);
                break;
            case R.id.tab_mine://??????

                StatusBarUtil.StatusBarLightMode(MainActivity.this, true);
                viewPager.setCurrentItem(3, false);
                break;
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    /**
     * ?????????????????????
     */
    public void toReleaseActivity() {
        showBookingToast(2);
        RequestManager.getInstance().isPerfect(new CommonCallback() {
            @Override
            public void onSuccess(ResultDO resultDO) {
                dismissBookingToast();
//                startActivityForResult(new Intent(MainActivity.this, PublishSelectTypeActivity.class), RELEASE_SUCCESS);
                PublishSelectTypeActivity.start(MainActivity.this);
            }

            @Override
            public void onFailed(int code, final String msg) {
                dismissBookingToast();
                if (code == 202) {
                    new QLTipDialog.Builder(MainActivity.this)
                            .setCancelable(true)
                            .setCancelableOnTouchOutside(true)
                            .setMessage(msg)
                            .setNegativeButton("????????????", new QLTipDialog.INegativeCallback() {
                                @Override
                                public void onClick() {

                                }
                            })
                            .show(MainActivity.this);
                } else if (code == 305) {

                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                    builder.setMessage(msg);
                    builder.setPositiveButton("?????????", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
//                            ToastUtils.showCentetImgToast(MainActivity.this, msg);
                            Intent intent = new Intent(MainActivity.this, CompanyEditActivity.class);

                            startActivity(intent);
                            dialog.dismiss();
                        }
                    });
                    builder.setNegativeButton("??????", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    builder.show();
                } else if (code == 310) {
                    //???????????????
                    new QLTipDialog.Builder(MainActivity.this)
                            .setCancelable(true)
                            .setCancelableOnTouchOutside(true)
                            .setMessage(msg)
                            .setNegativeButton("??????", new QLTipDialog.INegativeCallback() {
                                @Override
                                public void onClick() {

                                }
                            }).setPositiveButton("?????????", new QLTipDialog.IPositiveCallback() {
                        @Override
                        public void onClick() {
                            CertificationActivity.start(MainActivity.this, 1);
                        }
                    })
                            .show(MainActivity.this);
                }
            }
        });
    }

    @Override
    public void onPageSelected(int position) {
        for (int i = 0; i < tabViews.size(); i++) {
            tabViews.get(i).setSelected(false);
        }
        tabViews.get(position).setSelected(true);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

//        if (resultCode == MainActivity.SELECTED_REQUEST_CODE) {
//            int cityId = data.getIntExtra(SelectCityActivity.CITY_ID, -1);
//            String cityName = data.getStringExtra(SelectCityActivity.CITY_NAME);
//            if (cityId != -1) {
//                ResouceHelper.seletCityId = cityId;
//                indexFragment.refreshResouceListByCt(cityName);
//            }
//        }
        if (requestCode == MyPushActivity.REQUESTCODE && resultCode == CoopDetailActivity.REQUESTREFRESH) {
            if (indexNewFragment != null) {
                // indexNewFragment.refreshPage();
            }

        }
        if (requestCode == MainActivity.RELEASE_SUCCESS) {
            //?????????????????????
            viewPager.setCurrentItem(0, false);
            //   indexNewFragment.refreshPage();
        }
        if (resultCode == IndexFragment.RESULT_OK) {
            Bundle bundle = data.getExtras();
            String scanResult = bundle.getString(CaptureActivity.INTENT_EXTRA_KEY_QR_SCAN);
            // TODO: 2018/1/23
            scanQrcode(scanResult);
        }

        if (requestCode == AppSettingsDialog.DEFAULT_SETTINGS_REQ_CODE) {
            //??????????????????????????????????????????????????????APP?????????????????????
            Log.i(TAG, "onPermissionsDenied:------>??????????????????????????????APP");
            mLocationClient.startLocation();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }


    @Override
    public boolean onDown(MotionEvent e) {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent e) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {

    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        return false;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mBroadcastReceiver);
        mLocationClient.onDestroy();//?????????????????????????????????????????????????????????
        mLocationClient = null;
        mLocationOption = null;
        if (mTimer != null) {
            mTimer.cancel();
            mTimer = null;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        //IMUtils.getUnreadCount(new RongIMClient.ResultCallback<Integer>() {
//            @Override
//            public void onSuccess(Integer integer) {
//                if (integer <= 0) {
//                    ShowUtils.showViewVisible(msgRedPoint, View.GONE);
//                } else {
//                    ShowUtils.showViewVisible(msgRedPoint, View.VISIBLE);
//                    ShowUtils.showTextPerfect(msgRedPoint, "" + (integer + SYSTEM_NUM));
//                }
//            }
//
//            @Override
//            public void onError(RongIMClient.ErrorCode errorCode) {
//
//            }
//        }, Conversation.ConversationType.PRIVATE);
//        //IMUtils.getUnreadCount(new RongIMClient.ResultCallback<Integer>() {
//            @Override
//            public void onSuccess(Integer integer) {
//                if (integer <= 0) {
//
//                    //  ShowUtils.showViewVisible(tabCircleMsg, View.GONE);
//                } else {
//                    // ShowUtils.showViewVisible(tabCircleMsg, View.GONE);
//                }
//            }
//
//            @Override
//            public void onError(RongIMClient.ErrorCode errorCode) {
//
//            }
//        }, Conversation.ConversationType.GROUP);
    }

    private void checkUpdate() {
        RequestManager.getInstance().getAppVertion(new GetAppVertionCallback() {
            @Override
            public void onSuccess(final AppVertion vertion) {
                if (vertion == null) {
                    return;
                }
                if (vertion.getRemark() == null) {
                    return;
                }
                if (vertion.getUrl() == null) {
                    return;
                }
                if (vertion.getApp_name() == null) {
                    return;
                }
                if (vertion.getVersion() == null) {
                    return;
                }
                appName = vertion.getApp_name() + vertion.getVersion() + ".apk";
                if (vertion.getIs_force_update() == 1) {
                    dialog = new UpdateDialog(MainActivity.this, R.style.QLDialog);
                    dialog.setCanceledOnTouchOutside(false);
                    dialog.setUpdateListener(new UpdateDialog.OnUpdateListener() {
                        @Override
                        public void onUpdateClick() {
                            ToastUtils.showToast(MainActivity.this, "????????????");
                            if (canDownloadState(mContext)) {
                                new DownloadUtils(mContext, vertion.getUrl(), appName);
                            } else {
                                Intent intent = new Intent();
                                intent.setAction("android.intent.action.VIEW");
                                Uri content_url = Uri.parse(vertion.getUrl());
                                intent.setData(content_url);
                                mContext.startActivity(intent);
                            }
                            dialog.noTouchButton();
                        }
                    });
                    dialog.setCancelable(false);
                    dialog.show();
                    dialog.setContent(vertion.getRemark());
                } else {
                    new QLTipDialog.Builder(MainActivity.this)
                            .setCancelable(true)
                            .setCancelableOnTouchOutside(true)
                            .setMessage(vertion.getRemark())
                            .setNegativeButton("??????", new QLTipDialog.INegativeCallback() {
                                @Override
                                public void onClick() {

                                }
                            })
                            .setPositiveButton("??????", new QLTipDialog.IPositiveCallback() {
                                @Override
                                public void onClick() {
                                    //??????
                                    ToastUtils.showToast(MainActivity.this, "????????????");
                                    if (canDownloadState(mContext)) {
                                        new DownloadUtils(mContext, vertion.getUrl(), appName);
                                    } else {
                                        Intent intent = new Intent();
                                        intent.setAction("android.intent.action.VIEW");
                                        Uri content_url = Uri.parse(vertion.getUrl());
                                        intent.setData(content_url);
                                        mContext.startActivity(intent);
                                    }
                                }
                            })
                            .show(MainActivity.this);
                }
            }

            @Override
            public void onFailed(int code, String msg) {
            }
        });
    }


    private void scanQrcode(String code) {
        RequestManager.getInstance().scanQrcode(code, new QrCallback() {
            @Override
            public void onSuccess(QrcodeBean resultDO) {
                switch (resultDO.getType()) {
                    case "resources":
                        CoopDetailActivity.start(MainActivity.this, resultDO.getTarget_id());
                        break;
                    case "activity":
                        GoseeBillActivity.start(MainActivity.this, resultDO.getTarget_id());
                        break;
                    case "group_info":
                        GroupMessageActivity.start(MainActivity.this, resultDO.getTarget_id(), "add");
                        break;
                    case "qiqueqiao_h5":
                        ApproveCardActivity.start(MainActivity.this, "url", resultDO.getUrl(), "");
                        break;
                }
            }

            @Override
            public void onFailed(int code, String msg, final String data) {
                if (code == 205) {
                    AlertDialogUtils.AlertDialog(MainActivity.this, msg, "?????????", "", new AlertDialogUtils.setOnClick() {
                        @Override
                        public void setLeftOnClick(DialogInterface dialog) {
                            try {
                                JSONObject object = new JSONObject(data);
                                JSONObject datas = object.optJSONObject("data");
                                String targetId = datas.optString("target_id");
                                GoseeBillActivity.start(MainActivity.this, Integer.parseInt(targetId));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            } catch (NumberFormatException e) {
                                e.printStackTrace();
                            }

                        }

                        @Override
                        public void setRightOnClick(DialogInterface dialog) {

                        }
                    });


                }
                if (code == 202) {
                    AlertDialogUtils.AlertDialog(MainActivity.this, "????????????", msg, "?????????", "", new AlertDialogUtils.setOnClick() {
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
        });
    }


    private boolean isFiveDayTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        Date date = new Date();
        long data = Integer.parseInt(dateFormat.format(date));

        long oldTime = UserInfoHelper.getIntance().getPushTimePoke();

        if (data > oldTime) {
            return true;
        } else {
            return false;
        }
    }

    private boolean isActTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        Date date = new Date();
        long data = Integer.parseInt(dateFormat.format(date));
        Log.d("==MainActivity", "year:" + data);
        long oldTime = UserInfoHelper.getIntance().getActTime();
        Log.d("==MainActivity", "oldTime:" + oldTime);
        if (data > oldTime) {
            return true;
        } else {
            return false;
        }
    }

    private boolean isActTimeWeek() {

        Calendar calendar = Calendar.getInstance();
        calendar.setFirstDayOfWeek(Calendar.MONDAY);
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        calendar.setMinimalDaysInFirstWeek(7);
        calendar.setTime(new Date());
        int oldweek = UserInfoHelper.getIntance().getActTimeWeek();
        int weeks = calendar.get(Calendar.WEEK_OF_YEAR);
        if (oldweek == weeks) {
            return false;
        } else {
            return true;
        }
    }

    //???????????????????????????
    public AMapLocationListener mLocationListener = new AMapLocationListener() {
        @Override
        public void onLocationChanged(AMapLocation aMapLocation) {
            if (aMapLocation != null) {
                if (aMapLocation.getErrorCode() == 0) {
                    //??????????????????amapLocation?????????????????????
                    Constants.lat = aMapLocation.getLatitude();
                    Constants.lon = aMapLocation.getLongitude();
                    Log.i("??????", Constants.lat + "");
                } else {
                    //???????????????????????????ErrCode????????????????????????????????????????????????errInfo???????????????????????????????????????
                    Log.i("????????????", aMapLocation.getErrorCode() + "");
                    Constants.lat = 0;
                    Constants.lon = 0;
                }
            }
        }
    };

    @Override
    protected void onStop() {
        super.onStop();
        mLocationClient.stopLocation();//??????????????????????????????????????????????????????
    }

    String[] needPermissions = {
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.READ_PHONE_STATE
    };


    private void requestPermission() {
        if (EasyPermissions.hasPermissions(this, needPermissions)) {

            mLocationClient.startLocation();
        } else {
            int flag = PreferenceHelper.readInt(MainActivity.this, "splash_prefrence",
                    "LOCATION", 0);
            if (flag == 0) {
                EasyPermissions.requestPermissions(this,
                        "????????????????????????????????????",
                        PERMISSION_LOCATION, needPermissions);
                PreferenceHelper.write(MainActivity.this, "splash_prefrence",
                        "LOCATION", 1);
            }
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        // ???????????????????????????EasyPermissions
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {
        //??????

        mLocationClient.startLocation();
    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {
        //??????
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
//            new AppSettingsDialog
//                    .Builder(this)
//                    .setRationale("????????????????????????????????????????????????")
//                    .setPositiveButton("???")
//                    .setNegativeButton("???")
//                    .build()
//                    .show();
        }

    }

    private static boolean canDownloadState(Context ctx) {
        try {
            int state = ctx.getPackageManager().getApplicationEnabledSetting("com.android.providers.downloads");

            if (state == PackageManager.COMPONENT_ENABLED_STATE_DISABLED
                    || state == PackageManager.COMPONENT_ENABLED_STATE_DISABLED_USER
                    || state == PackageManager.COMPONENT_ENABLED_STATE_DISABLED_UNTIL_USED) {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

}
