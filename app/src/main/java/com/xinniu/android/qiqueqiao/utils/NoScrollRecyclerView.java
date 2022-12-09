package com.xinniu.android.qiqueqiao.utils;

import android.content.Context;
//import android.support.annotation.Nullable;
//import androidx.recyclerview.widget.RecyclerView;
import android.util.AttributeSet;

/**
 * Created by Reborn on 2018-01-17.
 */

public class NoScrollRecyclerView extends RecyclerView {
    public NoScrollRecyclerView(Context context) {
        super(context);
    }

    public NoScrollRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public NoScrollRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onMeasure(int widthSpec, int heightSpec) {
        int spec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.UNSPECIFIED);
        super.onMeasure(widthSpec, spec);
    }
}
