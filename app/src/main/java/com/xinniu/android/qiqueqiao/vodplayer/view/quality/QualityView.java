package com.xinniu.android.qiqueqiao.vodplayer.view.quality;

import android.content.Context;
//import android.support.annotation.AttrRes;
//import android.support.annotation.NonNull;
//import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.aliyun.vodplayer.media.IAliyunVodPlayer;
import com.xinniu.android.qiqueqiao.R;
import com.xinniu.android.qiqueqiao.vodplayer.widget.AliyunScreenMode;
import com.xinniu.android.qiqueqiao.vodplayer.widget.AliyunVodPlayerView;

import java.util.LinkedList;
import java.util.List;

/*
 * Copyright (C) 2010-2018 Alibaba Group Holding Limited.
 */

/**
 * 清晰度列表view。用于显示不同的清晰度列表。
 * 在{@link AliyunVodPlayerView}中使用。
 */
public class QualityView extends RelativeLayout {

    //显示清晰度的列表
    private ListView mListView;
    private BaseAdapter mAdapter;
    //adapter的数据源
    private List<String> mQualityItems;
    //当前播放的清晰度，高亮显示
    private String currentQuality;
    //清晰度项的点击事件
    private OnQualityClickListener mOnQualityClickListener;
    //是否是mts源
    private boolean isMtsSource = false;
    //显示动画
    private Animation showAnim;
    //隐藏动画
    private Animation hideAnim;
    //动画是否结束
    private boolean animEnd = true;

    public QualityView(@NonNull Context context) {
        super(context);
        init();
    }


    public QualityView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public QualityView(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        //初始化布局
        LayoutInflater.from(getContext()).inflate(R.layout.alivc_view_quality, this, true);
        mListView = (ListView) findViewById(R.id.quality_view);

        mListView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        //不显示滚动条，保证全部被显示
        mListView.setVerticalScrollBarEnabled(false);
        mListView.setHorizontalScrollBarEnabled(false);

        mAdapter = new QualityAdapter();
        mListView.setAdapter(mAdapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //点击之后就隐藏
                hide();
                //回调监听
                if (mOnQualityClickListener != null && mQualityItems != null) {
                    mOnQualityClickListener.onQualityClick(mQualityItems.get(position));
                }
            }
        });


        //倍速view使用到的动画
        showAnim = AnimationUtils.loadAnimation(getContext(), R.anim.view_speed_show);
        hideAnim = AnimationUtils.loadAnimation(getContext(), R.anim.view_speed_hide);
        showAnim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                //显示动画开始的时候，将倍速view显示出来
                animEnd = false;
                mListView.setVisibility(VISIBLE);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                animEnd = true;
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        hideAnim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                animEnd = false;
            }

            @Override
            public void onAnimationEnd(Animation animation) {

                //隐藏动画结束的时候，将倍速view隐藏掉
                mListView.setVisibility(INVISIBLE);
                animEnd = true;
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        hide();
    }


    /**
     * 设置清晰度点击监听
     *
     * @param l 点击监听
     */
    public void setOnQualityClickListener(OnQualityClickListener l) {
        mOnQualityClickListener = l;
    }

    /**
     * 设置清晰度
     *
     * @param qualities      所有支持的清晰度
     * @param currentQuality 当前的清晰度
     */
    public void setQuality(List<String> qualities, String currentQuality) {
        //排序之后显示出来
        mQualityItems = sortQuality(qualities);
        this.currentQuality = currentQuality;
        if (mAdapter != null) {
            mAdapter.notifyDataSetChanged();
        }
    }

    /**
     * 设置是否是MTS的源，因为清晰度的文字显示与其他的不一样
     *
     * @param isMts 是否是MTS的源
     */
    public void setIsMtsSource(boolean isMts) {
        isMtsSource = isMts;
    }

    private List<String> sortQuality(List<String> qualities) {

        //MTS的源不需要排序
        if (isMtsSource) {
            return qualities;
        }

        String ld = null, sd = null, hd = null, fd = null, k2 = null, k4 = null, od = null;
        for (String quality : qualities) {
            if (IAliyunVodPlayer.QualityValue.QUALITY_FLUENT.equals(quality)) {
                fd = IAliyunVodPlayer.QualityValue.QUALITY_FLUENT;
            } else if (IAliyunVodPlayer.QualityValue.QUALITY_LOW.equals(quality)) {
                ld = IAliyunVodPlayer.QualityValue.QUALITY_LOW;
            } else if (IAliyunVodPlayer.QualityValue.QUALITY_STAND.equals(quality)) {
                sd = IAliyunVodPlayer.QualityValue.QUALITY_STAND;
            } else if (IAliyunVodPlayer.QualityValue.QUALITY_HIGH.equals(quality)) {
                hd = IAliyunVodPlayer.QualityValue.QUALITY_HIGH;
            } else if (IAliyunVodPlayer.QualityValue.QUALITY_2K.equals(quality)) {
                k2 = IAliyunVodPlayer.QualityValue.QUALITY_2K;
            } else if (IAliyunVodPlayer.QualityValue.QUALITY_4K.equals(quality)) {
                k4 = IAliyunVodPlayer.QualityValue.QUALITY_4K;
            } else if (IAliyunVodPlayer.QualityValue.QUALITY_ORIGINAL.equals(quality)) {
                od = IAliyunVodPlayer.QualityValue.QUALITY_ORIGINAL;
            }
        }

        //清晰度按照fd,ld,sd,hd,2k,4k,od排序
        List<String> sortedQuality = new LinkedList<String>();

        if (!TextUtils.isEmpty(od)) {
            sortedQuality.add(od);
        }
        if (!TextUtils.isEmpty(k4)) {
            sortedQuality.add(k4);
        }
        if (!TextUtils.isEmpty(k2)) {
            sortedQuality.add(k2);
        }
        if (!TextUtils.isEmpty(hd)) {
            sortedQuality.add(hd);
        }
        if (!TextUtils.isEmpty(sd)) {
            sortedQuality.add(sd);
        }
        if (!TextUtils.isEmpty(ld)) {
            sortedQuality.add(ld);
        }
        if (!TextUtils.isEmpty(fd)) {
            sortedQuality.add(fd);
        }
        return sortedQuality;
    }


    /**
     * 设置当前屏幕模式。不同的模式，speedView的大小不一样
     *
     * @param screenMode
     */
    public void setScreenMode(AliyunScreenMode screenMode) {
        ViewGroup.LayoutParams listViewParam = mListView.getLayoutParams();


        if (screenMode == AliyunScreenMode.Small) {
            //小屏的时候，是铺满整个播放器的
            listViewParam.width = getWidth();
            listViewParam.height = getHeight();
        } else if (screenMode == AliyunScreenMode.Full) {
            //如果是全屏的，就显示一半
            AliyunVodPlayerView parentView = (AliyunVodPlayerView) getParent();
            IAliyunVodPlayer.LockPortraitListener lockPortraitListener = parentView.getLockPortraitMode();
            if (lockPortraitListener == null) {
                //没有设置这个监听，说明不是固定模式，按正常的界面显示就OK
                listViewParam.width = getWidth() / 4;
            } else {
                listViewParam.width = getWidth();
            }
            listViewParam.height = getHeight();
        }

        mListView.setLayoutParams(listViewParam);
    }

    /**
     * 显示倍速view
     *
     * @param screenMode 屏幕模式
     */
    public void show(AliyunScreenMode screenMode) {

        setScreenMode(screenMode);

        mListView.startAnimation(showAnim);

    }

    /**
     * 隐藏
     */
    public void hide() {
        if (mListView != null && mListView.getVisibility() == VISIBLE) {
//            mListView.setVisibility(GONE);
            mListView.startAnimation(hideAnim);
        }
    }


    public interface OnQualityClickListener {
        /**
         * 清晰度点击事件
         *
         * @param quality 点中的清晰度
         */
        void onQualityClick(String quality);
    }

    /**
     * 清晰度列表的适配器
     */
    private class QualityAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            if (mQualityItems != null) {
                return mQualityItems.size();
            }
            return 0;
        }

        @Override
        public Object getItem(int position) {
            return mQualityItems.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = LayoutInflater.from(getContext()).inflate(R.layout.ratetype_item, null);
            TextView tv_name = (TextView) view.findViewById(R.id.tv_name);
            RelativeLayout root = (RelativeLayout) view.findViewById(R.id.rlayout_root);
            WindowManager wm = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
            int screenHeight = wm.getDefaultDisplay().getHeight();

            if (mQualityItems.size() > 0) {
                ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, screenHeight / mQualityItems.size());
                root.setLayoutParams(params);

            } else {
                ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                root.setLayoutParams(params);
            }

            if (mQualityItems != null) {
                String quality = mQualityItems.get(position);
                tv_name.setText(QualityItem.getItem(getContext(), quality, isMtsSource).getName());
                //默认白色，当前清晰度为主题色。
                if (quality.equals(currentQuality)) {
                    tv_name.setBackgroundResource(R.drawable.alivc_check_bg_blue);
                    // view.setTextColor(ContextCompat.getColor(getContext(), themeColorResId));
                } else {
                    tv_name.setBackground(null);
                    // view.setTextColor(ContextCompat.getColor(getContext(), R.color.alivc_white));
                }
            }
            return view;
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //动画没有结束的时候，触摸是没有效果的
        if (mListView.getVisibility() == VISIBLE && animEnd) {
            hide();
            //回调监听
            if (mOnQualityClickListener != null && mQualityItems != null) {
                mOnQualityClickListener.onQualityClick("");
            }
            return true;
        }

        return super.onTouchEvent(event);
    }
}
