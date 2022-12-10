package com.xinniu.android.qiqueqiao.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.xinniu.android.qiqueqiao.R;
import com.xinniu.android.qiqueqiao.base.BaseActivity;

import butterknife.BindView;

/**
 * Created by lzq on 2018/1/31.
 */

public class CircleSuggestActivity extends BaseActivity{
    private int type;
    private String content;
//    public final static int TYPE_SUGGEST = 1;
//    public final static int TYPE_TIP = 2;
    public final static int REQUEST_CIRLE_SUGGEST = 701;
    @BindView(R.id.content_tv)
    TextView  contentTv;
    @BindView(R.id.content_et)
    EditText contentEt;
    @BindView(R.id.edit_success)
    TextView editSuccess;
    @BindView(R.id.bt_return)
    ImageView closeBt;
//    public static void start(Activity context, int type, String content){
//        Intent intent = new Intent(context,CircleSuggestActivity.class);
//        intent.putExtra("type",type);
//        intent.putExtra("content",content);
//        context.startActivityForResult(intent,REQUEST_CIRLE_SUGGEST);
//    }
    public static void start(AppCompatActivity context, String content){
        Intent intent = new Intent(context,CircleSuggestActivity.class);
//        intent.putExtra("type",type);
        intent.putExtra("content",content);
        context.startActivityForResult(intent,REQUEST_CIRLE_SUGGEST);
    }
    @Override
    public int getLayoutId() {
        return R.layout.activity_circle_tip;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        topStatusBar(false);
        content = getIntent().getStringExtra("content");
        contentTv.setText(content);
        closeBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
