package com.xinniu.android.qiqueqiao.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.xinniu.android.qiqueqiao.R;
import com.xinniu.android.qiqueqiao.base.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

/**rc_plugin_default
 *  rc_emotion_toggle
 *  rc_fr_conversation
 * Created by lzq on 2017/12/26.
 * 关于企鹊桥
 */

public class AboutQQQActivity extends BaseActivity implements View.OnClickListener{
    @BindView(R.id.bt_close)
    ImageView closeIv;
    @Override
    public int getLayoutId() {
        return R.layout.activity_about_qqq;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        topStatusBar(true);
    }

    @OnClick({R.id.bt_close})
    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.bt_close){
            finish();
        }
    }
}
