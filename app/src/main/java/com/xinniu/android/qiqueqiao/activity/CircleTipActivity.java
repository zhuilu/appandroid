package com.xinniu.android.qiqueqiao.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
//import android.support.v7.widget.AppCompatRatingBar;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatRatingBar;

import com.xinniu.android.qiqueqiao.R;
import com.xinniu.android.qiqueqiao.base.BaseActivity;
import com.xinniu.android.qiqueqiao.utils.ImageLoader;
import com.xinniu.android.qiqueqiao.widget.CircleImageView;

import butterknife.BindView;

/**
 * Created by qinlei
 * Created on 2017/12/23
 * Created description :
 */

public class CircleTipActivity extends BaseActivity {
    private static final String TITLE = "title";
    private static final String HEAD = "HEAD";
    private static final String LEVEL = "LEVEL";
    private static final String NUM = "NUM";
    private static final String NOTIFACTION = "NOTIFACTION";

    @BindView(R.id.tb_return)
    ImageView tbReturn;
    @BindView(R.id.tb_title)
    TextView tbTitle;
    @BindView(R.id.tb_menu)
    ImageView tbMenu;
    @BindView(R.id.image_head)
    CircleImageView imageHead;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.star_view)
    AppCompatRatingBar starView;
    @BindView(R.id.tv_num_people)
    TextView tvNumPeople;
    @BindView(R.id.rl_title_bar_root)
    RelativeLayout rlTitleBarRoot;
    @BindView(R.id.circle_tip)
    TextView circleTip;

    private String title;
    private String headUrl;
    private int level;
    private int num;
    private String notifaction;

    public static void start(Context context,
                             String title, String headUrl,
                             int level, int num, String notifaction) {
        Intent starter = new Intent(context, CircleTipActivity.class);
        starter.putExtra(TITLE, title);
        starter.putExtra(HEAD, headUrl);
        starter.putExtra(LEVEL, level);
        starter.putExtra(NUM, num);
        starter.putExtra(NOTIFACTION, notifaction);
        context.startActivity(starter);
    }

    @Override
    public int getLayoutId() {
        return R.layout.circle_tip_activity;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        topStatusBar(true);
        getIntentData();
        tbReturn.setImageResource(R.mipmap.chevron);
        tbReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        tbTitle.setText("群公告");
        starView.setNumStars(level);
        starView.setRating(level);
        tvTitle.setText(title);
//        Picasso.with(BaseApp.getInstance()).load(headUrl)
//                .noFade()
//                .error(R.mipmap.ic_default_head)
//                .into(imageHead);
        ImageLoader.loadAvter(headUrl,imageHead);
        tvNumPeople.setText(num + "人在线");
        circleTip.setText(notifaction);
    }

    /**
     * 从 Intent 中获取信息
     */
    private void getIntentData() {
        title = getIntent().getStringExtra(TITLE);
        headUrl = getIntent().getStringExtra(HEAD);
        level = getIntent().getIntExtra(LEVEL, 0);
        num = getIntent().getIntExtra(NUM, 0);
        notifaction = getIntent().getStringExtra(NOTIFACTION);
    }


}
