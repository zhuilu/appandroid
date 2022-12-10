package com.xinniu.android.qiqueqiao.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.xinniu.android.qiqueqiao.R;
import com.xinniu.android.qiqueqiao.base.BaseActivity;
import com.xinniu.android.qiqueqiao.dialog.AlertDialogUtils;
import com.xinniu.android.qiqueqiao.request.RequestManager;
import com.xinniu.android.qiqueqiao.request.callback.AllResultDoCallback;
import com.xinniu.android.qiqueqiao.user.UserInfoHelper;
import com.xinniu.android.qiqueqiao.utils.ComUtils;
import com.xinniu.android.qiqueqiao.utils.ToastUtils;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by yuchance on 2018/11/20.
 * 激活会员页面
 */

public class ActMemberActivity extends BaseActivity {
    @BindView(R.id.mact_member_tv)
    TextView mactMemberTv;
    @BindView(R.id.bact_member_summit)
    TextView bactMemberSummit;
    @BindView(R.id.mact_memberRl)
    RelativeLayout mactMemberRl;
    @BindView(R.id.mno_memberRl)
    RelativeLayout mnoMemberRl;


    public static void start(AppCompatActivity context){
        Intent intent = new Intent(context,ActMemberActivity.class);
        context.startActivityForResult(intent,106);
    }


    @Override
    public int getLayoutId() {
        return R.layout.activity_act_member;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        topStatusBar(true);
        ComUtils.addActivity(this);
        SpannableString sb = new SpannableString("您的账号已经成功购买企鹊桥SVIP一年\n点击下方按钮立即激活会员权益");
        sb.setSpan(new ForegroundColorSpan(Color.parseColor("#2DA0FB")),13,17, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        mactMemberTv.setText(sb);
        buildDatas();
    }

    private void buildDatas() {
        String name = UserInfoHelper.getIntance().getInfoNickName();
        RequestManager.getInstance().whetherBind(name, new AllResultDoCallback() {
            @Override
            public void onSuccess(String msg) {
                mactMemberRl.setVisibility(View.VISIBLE);
                mnoMemberRl.setVisibility(View.GONE);
            }

            @Override
            public void onFailed(int code, String msg) {
                if (code == 202){
                    mactMemberRl.setVisibility(View.GONE);
                    mnoMemberRl.setVisibility(View.VISIBLE);
                }else {
                    ToastUtils.showCentetToast(ActMemberActivity.this,msg);
                }
            }
        });

    }


    @OnClick({R.id.bt_finish, R.id.b_rightTv, R.id.bact_member_summit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bt_finish:
                finish();
                break;
            case R.id.b_rightTv:
                MakeConvertcodeActivity.start(ActMemberActivity.this);
                break;
            case R.id.bact_member_summit:
                summit();
                break;
                default:
                    break;
        }
    }

    private void summit() {
        RequestManager.getInstance().goActivation(new AllResultDoCallback() {
            @Override
            public void onSuccess(String msg) {
                AlertDialogUtils.AlertDialog(ActMemberActivity.this, msg, "我知道了", "", new AlertDialogUtils.setOnClick() {
                    @Override
                    public void setLeftOnClick(DialogInterface dialog) {
                        new Intent(ActMemberActivity.this,VipV4ListActivity.class);
                        setResult(16);
                        dialog.dismiss();
                        finish();
                    }

                    @Override
                    public void setRightOnClick(DialogInterface dialog) {

                    }
                });

            }

            @Override
            public void onFailed(int code, String msg) {
                ToastUtils.showCentetToast(ActMemberActivity.this,msg);
            }
        });
    }


}
