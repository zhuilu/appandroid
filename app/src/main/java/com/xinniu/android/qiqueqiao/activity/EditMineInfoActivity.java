package com.xinniu.android.qiqueqiao.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.xinniu.android.qiqueqiao.R;
import com.xinniu.android.qiqueqiao.base.BaseActivity;
import com.xinniu.android.qiqueqiao.fragment.edit.PhoneEditFragment;
import com.xinniu.android.qiqueqiao.fragment.edit.PwdEditFragment;
import com.xinniu.android.qiqueqiao.fragment.edit.SimpleEditFragment;

import butterknife.BindView;

import androidx.fragment.app.Fragment;
//import androidx.appcompat.widget.Toolbar;

/**
 * Created by qinlei
 * Created on 2017/12/18
 * Created description : 统一入口，修改个人信息
 */

public class EditMineInfoActivity extends BaseActivity {
    private static final int EDIT_SIMPLE = 1;
    private static final String PARAM_TITLE = "PARAM_TITLE";
    private static final String PARAM_HINT = "PARAM_HINT";
    private static final String PARAM_STR_EDITTEXT = "PARAM_STR_EDITTEXT";
    private static final String PARAM_TYPE = "PARAM_TYPE";
    private static final String PARAM_TYPE_CHILD = "PARAM_TYPE_CHILD";
    @BindView(R.id.title_bar)
    RelativeLayout titleBar;
    private String title;
    private String hint;
    private String strEdittext;
    private int simpleType;

    public static final int EDIT_PHONE = 2;

    public static final int EDIT_PWD = 3;

    @BindView(R.id.tb_return)
    ImageView mTbReturn;
    @BindView(R.id.tb_title)
    TextView mTbTitle;
    @BindView(R.id.tb_menu)
    ImageView mTbMenu;
    @BindView(R.id.tool_bar)
    Toolbar toolBar;

    private InputMethodManager imm;


    public static void startSimpleEidtForResult(AppCompatActivity context, int requestCode, String title, String hint, String strEditText, int type) {
        Intent starter = new Intent(context, EditMineInfoActivity.class);
        starter.putExtra(PARAM_TYPE, EDIT_SIMPLE);
        starter.putExtra(PARAM_TYPE_CHILD, type);
        starter.putExtra(PARAM_TITLE, title);
        starter.putExtra(PARAM_HINT, hint);
        starter.putExtra(PARAM_STR_EDITTEXT, strEditText);
        context.startActivityForResult(starter, requestCode);
    }

    public static void startPhoneEdit(AppCompatActivity context, String title) {
        Intent starter = new Intent(context, EditMineInfoActivity.class);
        starter.putExtra(PARAM_TYPE, EDIT_PHONE);
        starter.putExtra(PARAM_TITLE, title);
        context.startActivityForResult(starter, EDIT_PHONE);
    }

    public static void startPwdEdit(Context context, String title) {
        Intent starter = new Intent(context, EditMineInfoActivity.class);
        starter.putExtra(PARAM_TYPE, EDIT_PWD);
        starter.putExtra(PARAM_TITLE, title);
        context.startActivity(starter);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_edit_mine_info;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        topStatusBar(true);
        initTitleBar();
        imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        switch (getIntent().getIntExtra(PARAM_TYPE, 1)) {
            case EDIT_SIMPLE:
                getSimpleIntentParam();
                break;
            case EDIT_PHONE:
                getPhoneIntentParam();
                break;
            case EDIT_PWD:
                getPwdIntentParam();
                break;
            default:
                break;
        }
    }

    private void initTitleBar() {
        title = getIntent().getStringExtra(PARAM_TITLE);
        mTbReturn.setImageResource(R.mipmap.chevron);
        mTbReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        if (!TextUtils.isEmpty(title)) {
            mTbTitle.setText(title);
        }
    }

    private void getSimpleIntentParam() {
        titleBar.setVisibility(View.GONE);
        hint = getIntent().getStringExtra(PARAM_HINT);
        strEdittext = getIntent().getStringExtra(PARAM_STR_EDITTEXT);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        simpleType = getIntent().getIntExtra(PARAM_TYPE_CHILD, -1);
        Fragment fragment = SimpleEditFragment.newInstance(hint, strEdittext, title,simpleType);
        getSupportFragmentManager().beginTransaction().add(R.id.fragment, fragment).commit();
    }

    private void getPhoneIntentParam() {
        Fragment fragment = PhoneEditFragment.newInstance();
        getSupportFragmentManager().beginTransaction().add(R.id.fragment, fragment).commit();
    }

    private void getPwdIntentParam() {
        Fragment fragment = PwdEditFragment.newInstance();
        getSupportFragmentManager().beginTransaction().add(R.id.fragment, fragment).commit();
    }

}
