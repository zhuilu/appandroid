package com.xinniu.android.qiqueqiao.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.widget.TextView;

import com.xinniu.android.qiqueqiao.R;

/**
 * Created by lzq on 2017/12/20.
 */

@SuppressLint("AppCompatCustomView")
public class VipTextView extends TextView{
    private static final int STROKE_WIDTH = 2;
    private int borderCol;

    private Paint borderPaint;
    private Paint cPaint;
    private int ccolor;

    public VipTextView(Context context, AttributeSet attrs) {
        super(context, attrs);

        TypedArray a = context.getTheme().obtainStyledAttributes(attrs,
                R.styleable.TextViewBorder, 0, 0);
        try {
            borderCol = a.getColor(R.styleable.TextViewBorder_borderColor, 0);//0 is default
        } finally {
            a.recycle();
        }

        borderPaint = new Paint();
        borderPaint.setStyle(Paint.Style.STROKE);
        borderPaint.setStrokeWidth(STROKE_WIDTH);
        borderPaint.setAntiAlias(true);

        cPaint = new Paint();
        cPaint.setStyle(Paint.Style.FILL);
        cPaint.setAntiAlias(true);


    }

    @Override
    protected void onDraw(Canvas canvas) {

        if (0 == this.getText().toString().length())
            return;
        borderPaint.setColor(borderCol);
        cPaint.setColor(ccolor);


        int w = this.getMeasuredWidth();
        int h = this.getMeasuredHeight();

        RectF r = new RectF(2, 2, w - 2, h - 2);
        RectF r1 = new RectF(5, 5, w - 5, h - 5);
        canvas.drawRoundRect(r, 5, 5, cPaint);
        canvas.drawRoundRect(r, 5, 5, borderPaint);
        super.onDraw(canvas);
    }

    public int getBordderColor() {
        return borderCol;
    }

    public void setBorderColor(int newColor) {
        setTextColor(newColor);
        borderCol = newColor;
        invalidate();
        requestLayout();
    }

    public void setCColor(int color){
        ccolor = color;
        invalidate();
        requestLayout();
    }
}
