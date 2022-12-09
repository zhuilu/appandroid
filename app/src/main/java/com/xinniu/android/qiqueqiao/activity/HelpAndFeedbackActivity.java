package com.xinniu.android.qiqueqiao.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.xinniu.android.qiqueqiao.R;
import com.xinniu.android.qiqueqiao.base.BaseActivity;

import butterknife.BindView;

/**
 * Created by lzq on 2018/3/2.
 */

public class HelpAndFeedbackActivity extends BaseActivity{
    @BindView(R.id.bt_close)
    ImageView closeIv;
    @Override
    public int getLayoutId() {
        return R.layout.activity_help_and_feedback;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        topStatusBar(false);
        closeIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
