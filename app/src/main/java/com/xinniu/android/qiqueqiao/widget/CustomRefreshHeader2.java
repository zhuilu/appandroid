package com.xinniu.android.qiqueqiao.widget;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
//import android.support.annotation.NonNull;
//import android.support.annotation.Nullable;
//import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshKernel;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.RefreshState;
import com.scwang.smartrefresh.layout.constant.SpinnerStyle;
import com.scwang.smartrefresh.layout.util.DensityUtil;
import com.xinniu.android.qiqueqiao.R;

/**
 * 自定义下拉刷新头部
 * Created by BDXK on 2019/3/4.
 * project : newBridge--- android xs
 */

public class CustomRefreshHeader2 extends LinearLayout implements RefreshHeader {
    private ImageView mImageView;//刷新动画视图
    private Context mContext;
    private AnimationDrawable releaseingAnim;

    private Drawable[] mImages;

    @Override
    public View getView() {
        return this;
    }

    public CustomRefreshHeader2(Context context) {
        this(context, null, 0);
    }

    public CustomRefreshHeader2(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomRefreshHeader2(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        setGravity(Gravity.CENTER_HORIZONTAL | Gravity.BOTTOM);
        mImageView = new ImageView(mContext);

        addView(mImageView, DensityUtil.dp2px(50), DensityUtil.dp2px(50));
        initImage();
    }

    private void initImage() {
        mImages = new Drawable[]{
                ContextCompat.getDrawable(mContext, R.drawable.dropdown_loading_h_1),
                ContextCompat.getDrawable(mContext, R.drawable.dropdown_loading_h_2),
                ContextCompat.getDrawable(mContext, R.drawable.dropdown_loading_h_3),
                ContextCompat.getDrawable(mContext, R.drawable.dropdown_loading_h_4),
                ContextCompat.getDrawable(mContext, R.drawable.dropdown_loading_h_5),
                ContextCompat.getDrawable(mContext, R.drawable.dropdown_loading_h_6),
                ContextCompat.getDrawable(mContext, R.drawable.dropdown_loading_h_7),
                ContextCompat.getDrawable(mContext, R.drawable.dropdown_loading_h_8),
                ContextCompat.getDrawable(mContext, R.drawable.dropdown_loading_h_9),
                ContextCompat.getDrawable(mContext, R.drawable.dropdown_loading_h_10),
                ContextCompat.getDrawable(mContext, R.drawable.dropdown_loading_h_11),
                ContextCompat.getDrawable(mContext, R.drawable.dropdown_loading_h_12),
                ContextCompat.getDrawable(mContext, R.drawable.dropdown_loading_h_13),
                ContextCompat.getDrawable(mContext, R.drawable.dropdown_loading_h_14),
                ContextCompat.getDrawable(mContext, R.drawable.dropdown_loading_h_15),
                ContextCompat.getDrawable(mContext, R.drawable.dropdown_loading_h_16),
                ContextCompat.getDrawable(mContext, R.drawable.dropdown_loading_h_17),
                ContextCompat.getDrawable(mContext, R.drawable.dropdown_loading_h_18),
                ContextCompat.getDrawable(mContext, R.drawable.dropdown_loading_h_19),
                ContextCompat.getDrawable(mContext, R.drawable.dropdown_loading_h_20),
                ContextCompat.getDrawable(mContext, R.drawable.dropdown_loading_h_21),
                ContextCompat.getDrawable(mContext, R.drawable.dropdown_loading_h_22),
                ContextCompat.getDrawable(mContext, R.drawable.dropdown_loading_h_23),
                ContextCompat.getDrawable(mContext, R.drawable.dropdown_loading_h_24),
                ContextCompat.getDrawable(mContext, R.drawable.dropdown_loading_h_25),
                ContextCompat.getDrawable(mContext, R.drawable.dropdown_loading_h_26),
                ContextCompat.getDrawable(mContext, R.drawable.dropdown_loading_h_27),
                ContextCompat.getDrawable(mContext, R.drawable.dropdown_loading_h_28),
                ContextCompat.getDrawable(mContext, R.drawable.dropdown_loading_h_29),
                ContextCompat.getDrawable(mContext, R.drawable.dropdown_loading_h_30),
                ContextCompat.getDrawable(mContext, R.drawable.dropdown_loading_h_31),
                ContextCompat.getDrawable(mContext, R.drawable.dropdown_loading_h_32),
                ContextCompat.getDrawable(mContext, R.drawable.dropdown_loading_h_33),
                ContextCompat.getDrawable(mContext, R.drawable.dropdown_loading_h_34),
                ContextCompat.getDrawable(mContext, R.drawable.dropdown_loading_h_35),
                ContextCompat.getDrawable(mContext, R.drawable.dropdown_loading_h_36),
                ContextCompat.getDrawable(mContext, R.drawable.dropdown_loading_h_37),
                ContextCompat.getDrawable(mContext, R.drawable.dropdown_loading_h_38),
                ContextCompat.getDrawable(mContext, R.drawable.dropdown_loading_h_39),
                ContextCompat.getDrawable(mContext, R.drawable.dropdown_loading_h_40),
                ContextCompat.getDrawable(mContext, R.drawable.dropdown_loading_h_41),
                ContextCompat.getDrawable(mContext, R.drawable.dropdown_loading_h_42),
                ContextCompat.getDrawable(mContext, R.drawable.dropdown_loading_h_43),
                ContextCompat.getDrawable(mContext, R.drawable.dropdown_loading_h_44),
                ContextCompat.getDrawable(mContext, R.drawable.dropdown_loading_h_45),
                ContextCompat.getDrawable(mContext, R.drawable.dropdown_loading_h_46),
                ContextCompat.getDrawable(mContext, R.drawable.dropdown_loading_h_47),
                ContextCompat.getDrawable(mContext, R.drawable.dropdown_loading_h_48),
                ContextCompat.getDrawable(mContext, R.drawable.dropdown_loading_h_49),
                ContextCompat.getDrawable(mContext, R.drawable.dropdown_loading_h_50),
                ContextCompat.getDrawable(mContext, R.drawable.dropdown_loading_h_51),
                ContextCompat.getDrawable(mContext, R.drawable.dropdown_loading_h_52),
                ContextCompat.getDrawable(mContext, R.drawable.dropdown_loading_h_53),
                ContextCompat.getDrawable(mContext, R.drawable.dropdown_loading_h_54),
                ContextCompat.getDrawable(mContext, R.drawable.dropdown_loading_h_55),
                ContextCompat.getDrawable(mContext, R.drawable.dropdown_loading_h_56),
                ContextCompat.getDrawable(mContext, R.drawable.dropdown_loading_h_57),
                ContextCompat.getDrawable(mContext, R.drawable.dropdown_loading_h_58),
                ContextCompat.getDrawable(mContext, R.drawable.dropdown_loading_h_59),
                ContextCompat.getDrawable(mContext, R.drawable.dropdown_loading_h_60),
                ContextCompat.getDrawable(mContext, R.drawable.dropdown_loading_h_61),
                ContextCompat.getDrawable(mContext, R.drawable.dropdown_loading_h_62),
        };
    }

    @NonNull
    @Override
    public SpinnerStyle getSpinnerStyle() {
        return SpinnerStyle.Translate;//指定动画为平移
    }

    @Override
    public void setPrimaryColors(int... colors) {

    }

    @Override
    public void onInitialized(@NonNull RefreshKernel kernel, int height, int maxDragHeight) {

    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    @Override
    public void onMoving(boolean isDragging, float percent, int offset, int height, int maxDragHeight) {
        if (percent < 1) {
         //   Log.i("执行21===", "下拉刷新状态:");
            //下拉刷新状态
            int position = (int) (percent*mImages.length);
            mImageView.setImageDrawable(mImages[position]);
        } else {
          //  Log.i("执行21===", "释放刷新....:");
        }
    }

    @Override
    public void onReleased(@NonNull RefreshLayout refreshLayout, int height, int maxDragHeight) {

       // Log.i("执行21===", height + "");
        mImageView.setImageDrawable(null);
        mImageView.setImageResource(R.drawable.anim_pull_refreshing);
        releaseingAnim = (AnimationDrawable) mImageView.getDrawable();
        releaseingAnim.start();
    }

    @Override
    public void onStartAnimator(@NonNull RefreshLayout refreshLayout, int height, int maxDragHeight) {

    }

    @Override
    public int onFinish(@NonNull RefreshLayout refreshLayout, boolean success) {
        //动画结束

       // Log.i("执行===", "动画结束");
        if (releaseingAnim != null && releaseingAnim.isRunning()) {
            releaseingAnim.stop();
        }
        return 10;
    }


    @Override
    public void onHorizontalDrag(float percentX, int offsetX, int offsetMax) {

    }

    @Override
    public boolean isSupportHorizontalDrag() {
        return false;
    }

    @Override
    public void onStateChanged(@NonNull RefreshLayout refreshLayout, @NonNull RefreshState oldState, @NonNull RefreshState newState) {
        switch (newState) {
            case None:
                break;
            case PullDownToRefresh:
               // Log.i("执行===", "1");
                break;
            case Refreshing:
              //  Log.i("执行===", "2");
                break;
            case ReleaseToRefresh:
                //Log.i("执行===", "3");
                break;
        }
    }
}
