package com.xinniu.android.qiqueqiao.customs;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.RadioGroup;

import com.xinniu.android.qiqueqiao.R;


public class SimpleColorViewIndicate extends LinearLayout {
    RadioGroup group;

    public SimpleColorViewIndicate(Context context, AttributeSet attrs) {
        super(context, attrs);
        initParam(context, attrs);
    }

    float radius;
    float icon_distance;
    int icon_color_normal;
    int icon_color_selected;

    private void initParam(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.YBRenPageIndicator);
        if (typedArray != null) {
            radius = typedArray.getDimension(R.styleable.YBRenPageIndicator_icon_radius, 30);
            icon_distance = typedArray.getDimension(R.styleable.YBRenPageIndicator_icon_distance, 30);
            icon_color_normal = typedArray.getColor(R.styleable.YBRenPageIndicator_icon_color_normal, Color.LTGRAY);
            icon_color_selected = typedArray.getColor(R.styleable.YBRenPageIndicator_icon_color_selected, Color.GRAY);
            typedArray.recycle();
        }
    }

    public void notifyDataSetChanged(int itemCount) {
        removeAllViews();
        if (group == null) {
            group = new RadioGroup(getContext());
            group.setOrientation(HORIZONTAL);
        } else {
            group.removeAllViews();
        }

        for (int i = 0; i < itemCount; i++) {
            group.addView(new Dot(getContext(), radius,
                    icon_distance
                    , icon_color_normal
                    , icon_color_selected));
        }
        addView(group);
        setSelectedPosition(0);
    }

    public void setSelectedPosition(int position) {
        if (group != null && group.getChildCount() > position) {
            ((Dot) group.getChildAt(position)).setChecked(true);
        }
    }
}
