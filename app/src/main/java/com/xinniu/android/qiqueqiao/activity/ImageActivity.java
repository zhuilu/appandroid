package com.xinniu.android.qiqueqiao.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.xinniu.android.qiqueqiao.R;
import com.xinniu.android.qiqueqiao.base.BaseActivity;
import com.xinniu.android.qiqueqiao.utils.ImageLoader;

import butterknife.BindView;

/**
 * Created by lzq on 2017/12/28.
 */

public class ImageActivity extends BaseActivity{
    @BindView(R.id.image)
    ImageView image;
    @BindView(R.id.bt_close)
    ImageView linearLayout;
    @Override
    public int getLayoutId() {
        return R.layout.activity_image;
    }

    public static void start(Context context,String url){
        if (url == null){
            return;
        }
        Intent intent = new Intent(context,ImageActivity.class);
        intent.putExtra("image",url);
        context.startActivity(intent);
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        String url = getIntent().getStringExtra("image");
        ImageLoader.loadImageGQ(url,image);
        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

}
