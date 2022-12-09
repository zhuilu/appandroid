package com.xinniu.android.qiqueqiao.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.CornerPathEffect;
import android.graphics.Paint;
//import androidx.viewpager.widget.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xinniu.android.qiqueqiao.R;

/**
 * Created by lzq on 2018/3/6.
 */

public class ViewPagerIndicate extends FrameLayout implements ViewPager.OnPageChangeListener{
    /**
     *圆点数量
     */
    private int mTabCount;
    private Paint mPaint;
    private Context mContext;
    private int mInitTranslationX;
    private float mTranslationX;
    ImageView orageImageView;
    public ViewPagerIndicate(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.ViewPagerIndicate);
        mTabCount = a.getInt(R.styleable.ViewPagerIndicate_item_count,2);
        if (mTabCount<=0){
            mTabCount = 2;
        }
        a.recycle();
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(context.getResources().getColor(R.color.colorPrimaryDark));
        mPaint.setPathEffect(new CornerPathEffect(3));
    }
    public ViewPagerIndicate(Context context) {
        super(context);

    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mInitTranslationX = getWidth() / mTabCount / 2 - 8
                / 2;
    }

    public void setViewPager(ViewPager viewPager){
        viewPager.setOnPageChangeListener(this);
//        this.noteTextView = noteTextView;
    }
    public void setCount(int count){
        mTabCount = count;
    }
    public void addTabItem(){
        LinearLayout linearLayout = new LinearLayout(mContext);
        FrameLayout.LayoutParams fLayoutParams = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT);
        linearLayout.setLayoutParams(fLayoutParams);
        linearLayout.setGravity(Gravity.CENTER_VERTICAL);
        linearLayout.setOrientation(LinearLayout.HORIZONTAL);
        if (mTabCount == 1){
            setVisibility(View.GONE);
        }else{
            setVisibility(View.VISIBLE);
        }
        for (int i=0;i<mTabCount;i++){
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
            layoutParams.setMargins(8,8,8,8);
            ImageView imageView = new ImageView(mContext);
            imageView.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.yuandian_bai));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setLayoutParams(layoutParams);
            linearLayout.addView(imageView);
        }
        orageImageView = new ImageView(mContext);
        orageImageView.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.yuandian_lan));
        orageImageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        FrameLayout.LayoutParams f1LayoutParams = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT);
        f1LayoutParams.setMargins(8,8,8,8);
        orageImageView.setLayoutParams(f1LayoutParams);
        addView(linearLayout);
        addView(orageImageView);
    }
    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        mTranslationX = getWidth() / mTabCount * (position + positionOffset);
        orageImageView.setTranslationX(mTranslationX);
        if (onSelectedPageListener != null){
            onSelectedPageListener.onScroll(position,positionOffset,positionOffsetPixels);
        }
    }

    @Override
    public void onPageSelected(int position) {
        if (onSelectedPageListener != null){
            onSelectedPageListener.onSelected(position);
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {
    }
    OnSelectedPageListener onSelectedPageListener;
    public interface OnSelectedPageListener{
        void onSelected(int position);
        void onScroll(int position, float positionOffset, int positionOffsetPixels);
    }
    public void setOnSelectedPageListener(OnSelectedPageListener onSelectedPageListener){
        this.onSelectedPageListener = onSelectedPageListener;
    }
}
