package com.xinniu.android.qiqueqiao.customs;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import androidx.appcompat.widget.AppCompatRadioButton;

import com.xinniu.android.qiqueqiao.R;

public class Dot extends AppCompatRadioButton {
    /**
     * 放大放大倍数
     */
    public static final float SCALE = 1.0f;
    /**
     * 透明度
     */
    public static final int ALPHA = 255;

    private float[] scaleFloats = new float[]{SCALE, SCALE, SCALE};

    private int[] alphas = new int[]{ALPHA, ALPHA, ALPHA,};

    float radius;
    int icon_color_normal;
    int icon_color_selected;

    public Dot(Context context) {
        this(context, null);
    }

    public Dot(Context context, float radius,
               float icon_distance,
               int icon_color_normal,
               int icon_color_selected) {
        this(context, null);

        this.radius = radius;
        setCircleRadius(radius);
        this.icon_color_normal = icon_color_normal;
        this.icon_color_selected = icon_color_selected;
    }


    @Override
    public void setChecked(boolean checked) {
        super.setChecked(checked);
        invalidate();
        requestLayout();
    }

    public Dot(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    /**
     * 小圆初始化设置
     *
     * @param context
     * @param attrs
     */
    private void init(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.YBRenPageIndicator);
        if (typedArray != null) {
            radius = typedArray.getDimension(R.styleable.YBRenPageIndicator_icon_radius, 30);
            icon_color_normal = typedArray.getColor(R.styleable.YBRenPageIndicator_icon_color_normal, Color.LTGRAY);
            icon_color_selected = typedArray.getColor(R.styleable.YBRenPageIndicator_icon_color_selected, Color.GRAY);
            typedArray.recycle();
        }
        setBackground(null);
        setButtonDrawable(null);
        paint = new Paint();
        paint.setAntiAlias(true);// 设置画笔的锯齿效果。 true是去除，大家一看效果就明白了
        setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));

    }

    /**
     * Dot默认宽度或高度
     */
    private float defaultSize = 10;

    /**
     * 修改DoT默认尺寸
     *
     * @param circleRadius
     */
    public void setCircleRadius(float circleRadius) {
        if (circleRadius > 0)
            defaultSize = circleRadius;
    }

    /**
     * 小圆画笔
     */
    private Paint paint = null;

    public void setColor(int color) {
        if (paint != null)
            paint.setColor(color);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        float radius = getWidth() / 3;
        float x = getWidth() / 2;
        float y = getHeight() / 2;
        canvas.save();//保存设置
        float translateX = x;
        canvas.translate(translateX, y);//平移画笔起始点
        canvas.scale(scaleFloats[0], scaleFloats[0]);//放大
        if (isChecked()) {
            paint.setColor(icon_color_selected);
        } else {
            paint.setColor(icon_color_normal);
        }
        paint.setAlpha(alphas[0]);//设置透明度
        canvas.drawCircle(0, 0, radius, paint);//画圆
        canvas.restore();
    }


    /**
     * 当控件的父元素正要放置该控件时调用
     * 根据父容器传递跟子容器的大小要求来确定子容器的大小
     *
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = measureDimension(dip2px(getContext(), defaultSize), widthMeasureSpec);
        int height = measureDimension(dip2px(getContext(), defaultSize), heightMeasureSpec);
        setMeasuredDimension(width, height);
    }
    /**
     * 将dip或dp值转换为px值，保证尺寸大小不变
     *
     * @param dipValue
     *            （DisplayMetrics类中属性density）
     * @return
     */
    public static int dip2px(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }
    /**
     * 根据父容器传递跟子容器的大小要求来确定子容器的大小
     *
     * @param defaultSize
     * @param measureSpec
     * @return
     */
    private int measureDimension(int defaultSize, int measureSpec) {
        int result = defaultSize;
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);
        if (specMode == MeasureSpec.EXACTLY) {
            result = specSize;
        } else if (specMode == MeasureSpec.AT_MOST) {
            result = Math.min(defaultSize, specSize);
        } else {
            result = defaultSize;
        }
        return result;
    }
}