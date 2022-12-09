package com.xinniu.android.qiqueqiao.customs;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.HorizontalScrollView;

/**
 * Created by yuchance on 2018/7/17.
 */

public class MyHScrollView extends HorizontalScrollView{
    private OnScrollListener listener;

    public void setListener(OnScrollListener listener) {
        this.listener = listener;
    }

    public MyHScrollView(Context context) {
        super(context);
    }

    public MyHScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyHScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    //设置接口
    public interface OnScrollListener{
        void onScroll(int scrollY,int oScrollY,int scrollX,int oScrollX);
    }

    //重写原生onScrollChanged方法，将参数传递给接口，由接口传递出去
    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        if(listener != null){

            //这里我只传了垂直滑动的距离
            listener.onScroll(t,oldt,l,oldl);
        }
    }

//    @Override
//    public void fling(int velocityX) {
//        super.fling(velocityX/1000);
//
//    }
}
