package com.xinniu.android.qiqueqiao.customs;

import android.content.Context;
//import android.support.v7.widget.LinearLayoutManager;

public class ScrollLinearLayoutManager extends LinearLayoutManager {
    private boolean mCanVerticalScroll = true;

    public ScrollLinearLayoutManager(Context context) {
        super(context);
    }

    public ScrollLinearLayoutManager(Context context, int orientation, boolean reverseLayout) {
        super(context, orientation, reverseLayout);
    }



    @Override
    public boolean canScrollHorizontally() {
        if (!mCanVerticalScroll){
            return false;
        }else {
            return super.canScrollHorizontally();
        }

    }

    public void setmCanVerticalScroll(boolean b){
        mCanVerticalScroll = b;
    }
}

