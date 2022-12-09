package com.xinniu.android.qiqueqiao.activity;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
//import android.support.v4.app.Fragment;
//import android.support.v4.app.FragmentManager;
//import android.support.v4.app.FragmentPagerAdapter;
//import androidx.viewpager.widget.ViewPager;
//import android.support.v7.app.AppCompatDialog;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.aliyun.vodplayer.media.AliyunVidSts;
import com.aliyun.vodplayer.media.IAliyunVodPlayer;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.xinniu.android.qiqueqiao.R;
import com.xinniu.android.qiqueqiao.base.BaseActivity;
import com.xinniu.android.qiqueqiao.base.LazyBaseFragment;
import com.xinniu.android.qiqueqiao.bean.ClassRoomDetailBean;
import com.xinniu.android.qiqueqiao.bean.CommentBean;
import com.xinniu.android.qiqueqiao.bean.VidStsBean;
import com.xinniu.android.qiqueqiao.customs.qldialog.QLClassRoomDialog;
import com.xinniu.android.qiqueqiao.fragment.classroom.ClassRoomCommentFragment;
import com.xinniu.android.qiqueqiao.fragment.classroom.ClassRoomDetailFragment;
import com.xinniu.android.qiqueqiao.request.RequestManager;
import com.xinniu.android.qiqueqiao.request.callback.AllResultDoCallback;
import com.xinniu.android.qiqueqiao.request.callback.CommentSuccessCallback;
import com.xinniu.android.qiqueqiao.request.callback.GetAccessTokenCallback;
import com.xinniu.android.qiqueqiao.request.callback.VideoDetailCallback;
import com.xinniu.android.qiqueqiao.utils.ShareUtils;
import com.xinniu.android.qiqueqiao.utils.ShowUtils;
import com.xinniu.android.qiqueqiao.utils.ToastUtils;
import com.xinniu.android.qiqueqiao.vodplayer.constants.PlayParameter;
import com.xinniu.android.qiqueqiao.vodplayer.utils.ScreenUtils;
import com.xinniu.android.qiqueqiao.vodplayer.widget.AliyunVodPlayerView;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ClassRoomDetailActivity extends BaseActivity implements ClassRoomCommentFragment.CallBackListener {
    @BindView(R.id.video_view)
    AliyunVodPlayerView mAliyunVodPlayerView = null;
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
    @BindView(R.id.bleaveword_sendtv)
    TextView bleavewordSendtv;
    @BindView(R.id.mcoop_sendEt)
    EditText mcoopSendEt;
    @BindView(R.id.ycoop_sendRl)
    RelativeLayout ycoopSendRl;
    @BindView(R.id.rlayout_bottom)
    LinearLayout rlayoutBottom;
    @BindView(R.id.b_introducetv)
    RadioButton bIntroducetv;
    @BindView(R.id.b_commenttv)
    RadioButton bCommenttv;
    @BindView(R.id.connection_rg)
    RadioGroup connectionRg;
    @BindView(R.id.rlayout_root)
    RelativeLayout rlayoutRoot;
    @BindView(R.id.mconnect_vp)
    ViewPager mconnectVp;
    private int id;
    private String mVideoId;
    private ClassRoomDetailBean itemData;
    private AppCompatDialog shareDialog;
    private String description;
    private String shareUrl;
    private String thumbImg;
    private String wechatUrl;
    private String titleString;
    private InputMethodManager imm;
    private int sendType;
    private int commmentId;
    private int mPosition;
    List<LazyBaseFragment> fragmentList = new ArrayList<>();
    ClassRoomCommentFragment classRoomCommentFragment;

    public static void start(Context context, int id, String video_id) {
        Intent starter = new Intent(context, ClassRoomDetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("video_id", video_id);
        bundle.putInt("id", id);
        starter.putExtras(bundle);
        context.startActivity(starter);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_classroom_detail;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        topStatusBar(true);
        if (isStrangePhone()) {

        } else {
            setTheme(R.style.AppTheme);
        }
        imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        Bundle bundle = getIntent().getExtras();
        id = bundle.getInt("id");
        mVideoId = bundle.getString("video_id");
        fragmentList.add(ClassRoomDetailFragment.newInstance(id));
        Bundle args = new Bundle();
        args.putInt("id", id);

        classRoomCommentFragment = new ClassRoomCommentFragment();
        classRoomCommentFragment.setArguments(args);
        fragmentList.add(classRoomCommentFragment);
        mconnectVp.setAdapter(new ConnectionAdapter(getSupportFragmentManager()));
        mconnectVp.setOffscreenPageLimit(2);

        mconnectVp.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int arg0) {
                int currentItem = mconnectVp.getCurrentItem();
                if (currentItem == 0) {
                    bIntroducetv.setChecked(true);
                } else {
                    bCommenttv.setChecked(true);
                }
                mconnectVp.setCurrentItem(currentItem);
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onPageScrollStateChanged(int arg0) {
                // TODO Auto-generated method stub

            }
        });

        showBookingToast(1);
        getDetail();
        //视频播放模块初始化
        initAliyunPlayerView();
        requestVidSts();


    }


    private void getDetail() {
        RequestManager.getInstance().getVedioDetail(id, new VideoDetailCallback() {
            @Override
            public void onSuccess(ClassRoomDetailBean item) {
                itemData = item;
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
                dismissBookingToast();
            }

            @Override
            public void onFailue(int code, String msg) {
                dismissBookingToast();
            }
        });
    }


    @OnClick({R.id.b_introducetv, R.id.b_commenttv, R.id.rlayout_good, R.id.rlayout_share, R.id.llayout_comment, R.id.bleaveword_sendtv})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.b_introducetv:
                mconnectVp.setCurrentItem(0);
                break;
            case R.id.b_commenttv:
                mconnectVp.setCurrentItem(1);

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
                bCommenttv.setChecked(true);
                mconnectVp.setCurrentItem(1);
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

                item.setNew(true);

                //更新Fragment
                if (classRoomCommentFragment != null) {
                    classRoomCommentFragment.notify(item);
                }

                ToastUtils.showCentetToast(ClassRoomDetailActivity.this, "发送成功");

            }

            @Override
            public void onFailed(int code, String msg) {
                dismissBookingToast();
                ToastUtils.showCentetToast(ClassRoomDetailActivity.this, msg);
            }
        });

    }

    private void showShareDialog() {
        shareDialog = new QLClassRoomDialog.Builder(mContext).setContext(ClassRoomDetailActivity.this).setPicture(true).setShareCallback(new QLClassRoomDialog.ShareNewCallback() {
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
                            ShareClassRoomActivity.start(mContext,itemData.getPoster(),itemData.getTitle(),itemData.getUser_id(),itemData.getHead_pic(),itemData.getRealname()," · " + itemData.getCompany() + itemData.getPosition(),shareUrl);

                        } else {
                            ShareClassRoomActivity.start(mContext,itemData.getPoster(),itemData.getTitle(),0,"","","",shareUrl);
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
                ToastUtils.showCentetToast(ClassRoomDetailActivity.this, msg);
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

            }

            @Override
            public void onFailed(int code, String msg) {
                dismissBookingToast();
                ToastUtils.showCentetToast(ClassRoomDetailActivity.this, msg);
            }
        });
    }

    /**
     * 请求sts
     */
    private void requestVidSts() {

        RequestManager.getInstance().getAccessToken(new GetAccessTokenCallback() {
            @Override
            public void onSuccess(VidStsBean item) {
                PlayParameter.PLAY_PARAM_VID = mVideoId;
                PlayParameter.PLAY_PARAM_AK_ID = item.getAccessKeyId();
                PlayParameter.PLAY_PARAM_AK_SECRE = item.getAccessKeySecret();
                PlayParameter.PLAY_PARAM_SCU_TOKEN = item.getSecurityToken();
                AliyunVidSts vidSts = new AliyunVidSts();
                vidSts.setVid(PlayParameter.PLAY_PARAM_VID);
                vidSts.setAcId(PlayParameter.PLAY_PARAM_AK_ID);
                vidSts.setAkSceret(PlayParameter.PLAY_PARAM_AK_SECRE);
                vidSts.setSecurityToken(PlayParameter.PLAY_PARAM_SCU_TOKEN);
                if (mAliyunVodPlayerView != null) {
                    mAliyunVodPlayerView.setVidSts(vidSts);
                }
            }

            @Override
            public void onFailue(int code, String msg) {
                requestVidSts();
            }
        });


    }

    private void initAliyunPlayerView() {
        //保持屏幕敞亮
        mAliyunVodPlayerView.setKeepScreenOn(true);

        String sdDir = Environment.getExternalStorageDirectory().getAbsolutePath() + "/test_save_cache";
        mAliyunVodPlayerView.setPlayingCache(false, sdDir, 60 * 60 /*时长, s */, 300 /*大小，MB*/);
        mAliyunVodPlayerView.setAutoPlay(true);

        mAliyunVodPlayerView.setOnUrlTimeExpiredListener(new MyOnUrlTimeExpiredListener(this));
        mAliyunVodPlayerView.setOnTimeExpiredErrorListener(new MyOnTimeExpiredErrorListener(this));


    }


    @Override
    protected void onResume() {
        super.onResume();
        updatePlayerViewMode();
        if (mAliyunVodPlayerView != null) {
            mAliyunVodPlayerView.onResume();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mAliyunVodPlayerView != null) {
            mAliyunVodPlayerView.onStop();
        }


    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        updatePlayerViewMode();
    }

    private void updatePlayerViewMode() {
        if (mAliyunVodPlayerView != null) {
            int orientation = getResources().getConfiguration().orientation;
            if (orientation == Configuration.ORIENTATION_PORTRAIT) {
                //转为竖屏了。
                //显示状态栏
                //                if (!isStrangePhone()) {
                //                    getSupportActionBar().show();
                //                }

                this.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
                mAliyunVodPlayerView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);

                //设置view的布局，宽高之类
                ViewGroup.LayoutParams aliVcVideoViewLayoutParams = (ViewGroup.LayoutParams) mAliyunVodPlayerView
                        .getLayoutParams();
                aliVcVideoViewLayoutParams.height = (int) (ScreenUtils.getWidth(this) * 9.0f / 16);
                aliVcVideoViewLayoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
                rlayoutBottom.setVisibility(View.VISIBLE);
            } else if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
                //转到横屏了。
                //隐藏状态栏
                //   if (!isStrangePhone()) {
                this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                        WindowManager.LayoutParams.FLAG_FULLSCREEN);
                mAliyunVodPlayerView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
                //   }

                //设置view的布局，宽高
                ViewGroup.LayoutParams aliVcVideoViewLayoutParams = (ViewGroup.LayoutParams) mAliyunVodPlayerView
                        .getLayoutParams();
                aliVcVideoViewLayoutParams.height = ViewGroup.LayoutParams.MATCH_PARENT;
                aliVcVideoViewLayoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;

                rlayoutBottom.setVisibility(View.GONE);
            }

        }
    }

    @Override
    protected void onDestroy() {
        if (mAliyunVodPlayerView != null) {
            mAliyunVodPlayerView.onDestroy();
            mAliyunVodPlayerView = null;
        }

        super.onDestroy();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (mAliyunVodPlayerView != null) {
            boolean handler = mAliyunVodPlayerView.onKeyDown(keyCode, event);
            if (!handler) {
                return false;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        //解决某些手机上锁屏之后会出现标题栏的问题。
        updatePlayerViewMode();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @Override
    public void setClick(int type, int commment_Id, String name, List<CommentBean.ListBean> data) {

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


    private static class MyOnUrlTimeExpiredListener implements IAliyunVodPlayer.OnUrlTimeExpiredListener {
        WeakReference<ClassRoomDetailActivity> weakReference;

        public MyOnUrlTimeExpiredListener(ClassRoomDetailActivity activity) {
            weakReference = new WeakReference<ClassRoomDetailActivity>(activity);
        }

        @Override
        public void onUrlTimeExpired(String s, String s1) {
            ClassRoomDetailActivity activity = weakReference.get();
            if (activity != null) {
                activity.onUrlTimeExpired(s, s1);
            }
        }
    }

    private void onUrlTimeExpired(String oldVid, String oldQuality) {
        requestVidSts();

    }

    public static class MyOnTimeExpiredErrorListener implements IAliyunVodPlayer.OnTimeExpiredErrorListener {

        WeakReference<ClassRoomDetailActivity> weakReference;

        public MyOnTimeExpiredErrorListener(ClassRoomDetailActivity activity) {
            weakReference = new WeakReference<>(activity);
        }

        @Override
        public void onTimeExpiredError() {
            ClassRoomDetailActivity activity = weakReference.get();
            if (activity != null) {
                activity.onTimExpiredError();
            }
        }
    }


    /**
     * 鉴权过期
     */
    private void onTimExpiredError() {
        requestVidSts();
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

    class ConnectionAdapter extends FragmentPagerAdapter {

        public ConnectionAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return fragmentList.get(position);
        }

        @Override
        public int getCount() {
            return fragmentList.size();
        }
    }
}
