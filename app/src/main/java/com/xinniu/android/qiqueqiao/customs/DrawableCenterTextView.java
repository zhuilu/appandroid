package com.xinniu.android.qiqueqiao.customs;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by yuchance on 2018/4/25.
 */

@SuppressLint("AppCompatCustomView")
public class DrawableCenterTextView extends TextView {

    public DrawableCenterTextView(Context context, AttributeSet attrs,
                                  int defStyle) {
            super(context, attrs, defStyle);
        }

    public DrawableCenterTextView(Context context, AttributeSet attrs) {
            super(context, attrs);
        }

    public DrawableCenterTextView(Context context) {
            super(context);
        }

        @Override
        protected void onDraw(Canvas canvas) {
            Drawable[] drawables = getCompoundDrawables();
            if (drawables != null) {
                Drawable drawableLeft = drawables[0];
                if (drawableLeft != null) {
                    float textWidth = getPaint().measureText(getText().toString());
                    int drawablePadding = getCompoundDrawablePadding();
                    int drawableWidth = 0;
                    drawableWidth = drawableLeft.getIntrinsicWidth();
                    float bodyWidth = textWidth + drawableWidth + drawablePadding;
                    canvas.translate(0, getHeight() / 2);
                }
            }
            super.onDraw(canvas);
        }
}

