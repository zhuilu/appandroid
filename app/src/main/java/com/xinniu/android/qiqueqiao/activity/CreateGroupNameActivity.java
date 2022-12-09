package com.xinniu.android.qiqueqiao.activity;

import android.os.Bundle;
import android.text.Editable;
import android.text.Selection;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.xinniu.android.qiqueqiao.R;
import com.xinniu.android.qiqueqiao.base.BaseActivity;
import com.xinniu.android.qiqueqiao.utils.ComUtils;
import com.xinniu.android.qiqueqiao.utils.ToastUtils;

import java.io.UnsupportedEncodingException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by yuchance on 2018/9/29.
 */

public class CreateGroupNameActivity extends BaseActivity {
    @BindView(R.id.mcreatenameTv)
    TextView mcreatenameTv;
    @BindView(R.id.mcreatenameEt)
    EditText mcreatenameEt;
    @BindView(R.id.bnextgoTv)
    TextView bnextgoTv;
    private int groupId;
    private String str = "";
    private String imputtxt;

    @Override
    public int getLayoutId() {
        return R.layout.activity_creategroup_name;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {topStatusBar(true);
        ComUtils.addActivity(CreateGroupNameActivity.this);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            groupId = bundle.getInt("groupId");
        }
        bnextgoTv.setSelected(false);
        bnextgoTv.setClickable(false);
        mcreatenameEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                imputtxt = mcreatenameEt.getText().toString();
                str = stringFilter(imputtxt);
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!TextUtils.isEmpty(str)){
                    String limtxt = getLimitSubstring(str);
                    if (!TextUtils.isEmpty(limtxt)){
                        if (!limtxt.equals(imputtxt)){
                            mcreatenameEt.setText(limtxt);
                            mcreatenameEt.setSelection(limtxt.length());
                        }
                    }
                }else {
                    mcreatenameTv.setText(""+30);
                }

            }
        });
    }






    @OnClick({R.id.bt_finish, R.id.bnextgoTv})
    public void onViewClicked(View v) {
        switch (v.getId()) {
            case R.id.bt_finish:
                finish();
                break;
            case R.id.bnextgoTv:
                if (mcreatenameEt!=null){
                    if (mcreatenameEt.getText().toString().trim().length()>0){
                        PerfectGroupActivity.start(CreateGroupNameActivity.this,mcreatenameEt.getText().toString().trim(),groupId);
                    }else {
                        ToastUtils.showCentetToast(CreateGroupNameActivity.this,"请输入群组名称");
                    }
                }

                break;
            default:
                break;
        }
    }

    //校验输入类型
    public static String stringFilterx(String str)throws PatternSyntaxException {
        // 仅仅同意字母、数字和汉字
        String   regEx  =  "[`~!@#$%^&*()+=|{}':;',\\\\[\\\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";
        Pattern p   =   Pattern.compile(regEx);
        Matcher m   =   p.matcher(str);
        if(m.find()) {
            return "";
        }else {
            return str;
        }
    }


    //校验输入类型
    public static String stringFilter(String str)throws PatternSyntaxException {
        // 仅仅同意字母、数字和汉字
        String   regEx  =  "[^a-zA-Z0-9\u4E00-\u9FA5]";
        Pattern p   =   Pattern.compile(regEx);
        Matcher m   =   p.matcher(str);
        return   m.replaceAll("").trim();
    }

    //限制字符长度
    private String getLimitSubstring(String inputStr) {
        int orignLen = inputStr.length();
        int resultLen = 0;
        String temp = null;
        for (int i = 0; i < orignLen; i++) {
            temp = inputStr.substring(i, i + 1);
            try {// 3 bytes to indicate chinese word,1 byte to indicate english
                // word ,in utf-8 encode
                if (temp.getBytes("utf-8").length == 3) {
                    resultLen += 3;
                } else {
                    resultLen++;
                }
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            Log.d("==CreateGroupName", "resultLen:" + resultLen);
            if (resultLen > 30) {
                return inputStr.substring(0, i);
            }else {
                if (resultLen==0){
                    bnextgoTv.setSelected(false);
                    bnextgoTv.setClickable(false);
                }else {
                    bnextgoTv.setSelected(true);
                    bnextgoTv.setClickable(true);
                }
                mcreatenameTv.setText(""+(30-resultLen));
            }
        }
        return inputStr;
    }

}
