package com.xinniu.android.qiqueqiao.activity;

import android.app.AlertDialog;
import android.app.ApplicationErrorReport;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
//import android.support.v4.app.FragmentTransaction;
import android.view.View;

import androidx.fragment.app.FragmentTransaction;

import com.xinniu.android.qiqueqiao.R;
import com.xinniu.android.qiqueqiao.base.BaseActivity;
import com.xinniu.android.qiqueqiao.fragment.message.group.QQQGroupListFragment;
import com.xinniu.android.qiqueqiao.request.RequestManager;
import com.xinniu.android.qiqueqiao.request.callback.AllResultDoCallback;
import com.xinniu.android.qiqueqiao.utils.ComUtils;

import butterknife.ButterKnife;
import butterknife.OnClick;
import io.rong.imlib.model.Conversation;


/**
 * Created by yuchance on 2018/9/26.
 * 新的圈子页面(群组消息页面)
 */

public class CircleNewActivity extends BaseActivity {

    QQQGroupListFragment fragment;

    @Override
    public int getLayoutId() {
        return R.layout.activity_circle_new;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        initView();
    }

    private void initView() {

        topStatusBar(true);
        ComUtils.addActivity(this);
        fragment = new QQQGroupListFragment();
        Uri uri = Uri.parse("rong://" + getApplicationInfo().packageName).buildUpon()
                .appendPath("conversationlist")
                .appendQueryParameter(Conversation.ConversationType.GROUP.getName(), "false")//设置私聊会话，该会话聚合显示
                .build();
//        fragment.setUri(uri);//设置 ConverssationListFragment 的显示属性
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.rong_content, fragment);
        transaction.commit();
    }


    @OnClick({R.id.bt_finish, R.id.bcircle_addgroupRl, R.id.bcircle_creategroupRl})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bt_finish:
                finish();
                break;
            case R.id.bcircle_addgroupRl:
                AddGroupActivity.start(CircleNewActivity.this);
                break;
            case R.id.bcircle_creategroupRl:
                RequestManager.getInstance().groupCheck(new AllResultDoCallback() {
                    @Override
                    public void onSuccess(String msg) {
                        CreateGroupActivity.start(CircleNewActivity.this);
                    }

                    @Override
                    public void onFailed(int code, String msg) {
                        final AlertDialog.Builder builder = new AlertDialog.Builder(CircleNewActivity.this);
                        if (code == 301){
                            builder.setTitle(msg);
                            builder.setNegativeButton("知道了", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                            builder.setPositiveButton("去开通", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    startActivity(new Intent(CircleNewActivity.this, VipV4ListActivity.class));
                                    dialog.dismiss();
                                }
                            });
                            builder.show();
                        }
                        if (code == 202){
                            builder.setTitle(msg);
                            builder.setNegativeButton("知道了", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                            builder.show();
                        }

                    }
                });

                break;
            default:
                break;
        }
    }
}
