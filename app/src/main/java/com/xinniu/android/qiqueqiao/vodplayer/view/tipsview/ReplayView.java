package com.xinniu.android.qiqueqiao.vodplayer.view.tipsview;

import android.content.Context;
import android.content.res.Resources;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.xinniu.android.qiqueqiao.R;
import com.xinniu.android.qiqueqiao.vodplayer.utils.ImageLoader;


/*
 * Copyright (C) 2010-2018 Alibaba Group Holding Limited.
 */

/**
 * 重播提示对话框。播放结束的时候会显示这个界面
 */
public class ReplayView extends RelativeLayout {
    //重播按钮
    private ImageView mReplayBtn;
    private ImageView mPoster;//视频封面
    private Context context;
    //重播事件监听
    private OnReplayClickListener mOnReplayClickListener = null;
    private String posterUrl = "";

    public ReplayView(Context context,String url) {
        super(context);
        this.context=context;
        this.posterUrl=url;
        init();
    }

    public ReplayView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ReplayView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        LayoutInflater inflater = (LayoutInflater) getContext()
                .getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        Resources resources = getContext().getResources();

        View view = inflater.inflate(R.layout.alivc_dialog_replay, null);
//        int viewWidth = resources.getDimensionPixelSize(R.dimen.dp_220);
//        int viewHeight = resources.getDimensionPixelSize(R.dimen.dp_120);


        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        addView(view, params);
        mPoster = (ImageView) view.findViewById(R.id.img_poster);

        new ImageLoader(mPoster).loadAsync(posterUrl);
        //设置监听
        mReplayBtn = (ImageView) view.findViewById(R.id.replay);
        mReplayBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnReplayClickListener != null) {
                    mOnReplayClickListener.onReplay();
                }
            }
        });

    }


    /**
     * 重播点击事件
     */
    public interface OnReplayClickListener {
        /**
         * 重播事件
         */
        void onReplay();
    }

    /**
     * 设置重播事件监听
     *
     * @param l 重播事件
     */
    public void setOnReplayClickListener(OnReplayClickListener l) {
        mOnReplayClickListener = l;
    }

}
