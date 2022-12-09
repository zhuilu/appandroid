package com.xinniu.android.qiqueqiao.vodplayer.view.speed;

import android.content.Context;
//import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alivc.player.VcPlayerLog;
import com.aliyun.vodplayer.media.IAliyunVodPlayer;
import com.xinniu.android.qiqueqiao.R;
import com.xinniu.android.qiqueqiao.vodplayer.widget.AliyunScreenMode;
import com.xinniu.android.qiqueqiao.vodplayer.widget.AliyunVodPlayerView;

/*
 * Copyright (C) 2010-2018 Alibaba Group Holding Limited.
 */

/**
 * 倍速播放界面。用于控制倍速。
 * 在{@link AliyunVodPlayerView}中使用。
 */

public class SpeedView extends RelativeLayout {

    private static final String TAG = SpeedView.class.getSimpleName();

    private SpeedValue mSpeedValue;

    private View mMainSpeedView;
    //显示动画
    private Animation showAnim;
    //隐藏动画
    private Animation hideAnim;
    //动画是否结束
    private boolean animEnd = true;

    // 正常倍速
    private TextView mNormalBtn;
    //1.25倍速
    private TextView mOneQrtTimeBtn;
    //1.5倍速
    private TextView mOneHalfTimeBtn;
    //2倍速
    private TextView mTwoTimeBtn;

    //0.75倍速
    private TextView mNormalfTimeBtn;
    //1.75倍速
    private TextView mOneHalffTimeBtn;

    //屏幕模式
    private AliyunScreenMode mScreenMode;
    //倍速选择事件
    private OnSpeedClickListener mOnSpeedClickListener = null;
    //倍速是否变化
    private boolean mSpeedChanged = false;


    public SpeedView(Context context) {
        super(context);
        init();
    }

    public SpeedView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }


    public SpeedView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        //初始化布局
        LayoutInflater.from(getContext()).inflate(R.layout.alivc_view_speed, this, true);
        mMainSpeedView = findViewById(R.id.speed_view);
        mMainSpeedView.setVisibility(INVISIBLE);

        //找出控件
        mOneQrtTimeBtn = (TextView) findViewById(R.id.one_quartern);
        mNormalBtn = (TextView) findViewById(R.id.normal);
        mOneHalfTimeBtn = (TextView) findViewById(R.id.one_half);
        mTwoTimeBtn = (TextView) findViewById(R.id.two);

        mNormalfTimeBtn = (TextView) findViewById(R.id.normal_fu);
        mOneHalffTimeBtn = (TextView) findViewById(R.id.one_halff);

        //对每个倍速项做点击监听
        mOneQrtTimeBtn.setOnClickListener(mClickListener);
        mNormalBtn.setOnClickListener(mClickListener);
        mOneHalfTimeBtn.setOnClickListener(mClickListener);
        mTwoTimeBtn.setOnClickListener(mClickListener);
        mNormalfTimeBtn.setOnClickListener(mClickListener);
        mOneHalffTimeBtn.setOnClickListener(mClickListener);


        //倍速view使用到的动画
        showAnim = AnimationUtils.loadAnimation(getContext(), R.anim.view_speed_show);
        hideAnim = AnimationUtils.loadAnimation(getContext(), R.anim.view_speed_hide);
        showAnim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                //显示动画开始的时候，将倍速view显示出来
                animEnd = false;
                mMainSpeedView.setVisibility(VISIBLE);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                animEnd = true;
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        hideAnim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                animEnd = false;
            }

            @Override
            public void onAnimationEnd(Animation animation) {

                //隐藏动画结束的时候，将倍速view隐藏掉
                mMainSpeedView.setVisibility(INVISIBLE);
                if (mOnSpeedClickListener != null) {
                    mOnSpeedClickListener.onHide();
                }

                //如果倍速有变化，会提示倍速变化的消息
                if (mSpeedChanged) {
                    String times = "";
                    if (mSpeedValue == SpeedValue.OneQuartern) {
                        times = getResources().getString(R.string.alivc_speed_optf_times);
                    } else if (mSpeedValue == SpeedValue.Normal) {
                        times = getResources().getString(R.string.alivc_speed_one_times);
                    } else if (mSpeedValue == SpeedValue.OneHalf) {
                        times = getResources().getString(R.string.alivc_speed_opt_times);
                    } else if (mSpeedValue == SpeedValue.Twice) {
                        times = getResources().getString(R.string.alivc_speed_twice_times);
                    } else if (mSpeedValue == SpeedValue.OneHalff) {
                        times = getResources().getString(R.string.alivc_speed_optff_times);
                    } else if (mSpeedValue == SpeedValue.Normalf) {
                        times = getResources().getString(R.string.alivc_speed_times);
                    }

                }
                animEnd = true;
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        setSpeed(SpeedValue.Normal);
//监听view的Layout事件
        getViewTreeObserver().addOnGlobalLayoutListener(new MyLayoutListener());
    }



    private class MyLayoutListener implements ViewTreeObserver.OnGlobalLayoutListener {
        private AliyunScreenMode lastLayoutMode = null;

        @Override
        public void onGlobalLayout() {
            if (mMainSpeedView.getVisibility() == VISIBLE) {

                //防止重复设置
                if (lastLayoutMode == mScreenMode) {
                    return;
                }

                setScreenMode(mScreenMode);
                lastLayoutMode = mScreenMode;
            }
        }
    }

    private OnClickListener mClickListener = new OnClickListener() {
        @Override
        public void onClick(View view) {
            if (mOnSpeedClickListener == null) {
                return;
            }

            if (view == mNormalBtn) {
                mOnSpeedClickListener.onSpeedClick(SpeedValue.Normal);
            } else if (view == mOneQrtTimeBtn) {
                mOnSpeedClickListener.onSpeedClick(SpeedValue.OneQuartern);
            } else if (view == mOneHalfTimeBtn) {
                mOnSpeedClickListener.onSpeedClick(SpeedValue.OneHalf);
            } else if (view == mTwoTimeBtn) {
                mOnSpeedClickListener.onSpeedClick(SpeedValue.Twice);
            } else if (view == mOneHalffTimeBtn) {
                mOnSpeedClickListener.onSpeedClick(SpeedValue.OneHalff);
            } else if (view == mNormalfTimeBtn) {
                mOnSpeedClickListener.onSpeedClick(SpeedValue.Normalf);
            }
        }

    };

    /**
     * 设置倍速点击事件
     *
     * @param l
     */
    public void setOnSpeedClickListener(OnSpeedClickListener l) {
        mOnSpeedClickListener = l;
    }

    /**
     * 设置当前屏幕模式。不同的模式，speedView的大小不一样
     *
     * @param screenMode
     */
    public void setScreenMode(AliyunScreenMode screenMode) {
        ViewGroup.LayoutParams speedViewParam = mMainSpeedView.getLayoutParams();


        if (screenMode == AliyunScreenMode.Small) {
            //小屏的时候，是铺满整个播放器的
            speedViewParam.width = getWidth();
            speedViewParam.height = getHeight();
        } else if (screenMode == AliyunScreenMode.Full) {
            //如果是全屏的，就显示一半
            AliyunVodPlayerView parentView = (AliyunVodPlayerView) getParent();
            IAliyunVodPlayer.LockPortraitListener lockPortraitListener = parentView.getLockPortraitMode();
            if (lockPortraitListener == null) {
                //没有设置这个监听，说明不是固定模式，按正常的界面显示就OK
                speedViewParam.width = getWidth() / 4;
            } else {
                speedViewParam.width = getWidth();
            }
            speedViewParam.height = getHeight();
        }

        VcPlayerLog.d(TAG, "setScreenModeStatus screenMode = " + screenMode.name() + " , width = " + speedViewParam.width + " , height = " + speedViewParam.height);
        mScreenMode = screenMode;
        mMainSpeedView.setLayoutParams(speedViewParam);
    }

    /**
     * 倍速监听
     */
    public interface OnSpeedClickListener {
        /**
         * 选中某个倍速
         *
         * @param value 倍速值
         */
        void onSpeedClick(SpeedValue value);

        /**
         * 倍速界面隐藏
         */
        void onHide();
    }

    /**
     * 倍速值
     */
    public static enum SpeedValue {
        /**
         * 正常倍速
         */
        Normal,
        /**
         * 1.25倍速
         */
        OneQuartern,
        /**
         * 1.5倍速
         */
        OneHalf,
        /**
         * 2倍速
         */
        Twice,
        /**
         * 0.75倍速
         */
        Normalf,
        /**
         * 1.75倍速
         */
        OneHalff,
        /**
         * 拿来显示控制栏用的标志
         */
        NO
    }


    /**
     * 设置显示的倍速
     *
     * @param speedValue 倍速值
     */
    public void setSpeed(SpeedValue speedValue) {
        if (speedValue == null) {
            return;
        }

        if (mSpeedValue != speedValue) {
            mSpeedValue = speedValue;
            mSpeedChanged = true;
            updateSpeedCheck();
        } else {
            mSpeedChanged = false;
        }

        hide();

    }

    /**
     * 更新倍速选项的状态
     */
    private void updateSpeedCheck() {
        mOneQrtTimeBtn.setBackground(null);
        mNormalBtn.setBackground(null);
        mOneHalfTimeBtn.setBackground(null);
        mTwoTimeBtn.setBackground(null);
        mOneHalffTimeBtn.setBackground(null);
        mNormalfTimeBtn.setBackground(null);
        if(mSpeedValue == SpeedValue.OneQuartern){
            mOneQrtTimeBtn.setBackgroundResource(R.drawable.alivc_check_bg_blue);
        }else if(mSpeedValue == SpeedValue.Normal){
            mNormalBtn.setBackgroundResource(R.drawable.alivc_check_bg_blue);
        }else if(mSpeedValue == SpeedValue.OneHalf){
            mOneHalfTimeBtn.setBackgroundResource(R.drawable.alivc_check_bg_blue);
        }else if(mSpeedValue == SpeedValue.Twice){
            mTwoTimeBtn.setBackgroundResource(R.drawable.alivc_check_bg_blue);
        }else if(mSpeedValue == SpeedValue.OneHalff){
            mOneHalffTimeBtn.setBackgroundResource(R.drawable.alivc_check_bg_blue);
        }else if(mSpeedValue == SpeedValue.Normalf){
            mNormalfTimeBtn.setBackgroundResource(R.drawable.alivc_check_bg_blue);
        }


    }


    /**
     * 显示倍速view
     *
     * @param screenMode 屏幕模式
     */
    public void show(AliyunScreenMode screenMode) {

        setScreenMode(screenMode);

        mMainSpeedView.startAnimation(showAnim);

    }

    /**
     * 隐藏
     */
    public void hide() {
        if (mMainSpeedView.getVisibility() == VISIBLE) {
            mMainSpeedView.startAnimation(hideAnim);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //动画没有结束的时候，触摸是没有效果的
        if (mMainSpeedView.getVisibility() == VISIBLE && animEnd) {
            hide();
            if (mOnSpeedClickListener != null) {
                mOnSpeedClickListener.onSpeedClick(SpeedValue.NO);
            }

            return true;
        }

        return super.onTouchEvent(event);
    }


}
