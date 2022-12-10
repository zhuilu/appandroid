package com.xinniu.android.qiqueqiao.customs.qldialog;

import android.content.Context;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatDialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatDialog;

import com.xinniu.android.qiqueqiao.R;
import com.xinniu.android.qiqueqiao.activity.ChatActivity;
import com.xinniu.android.qiqueqiao.user.UserInfoHelper;
import com.xinniu.android.qiqueqiao.utils.StringUtils;
import com.xinniu.android.qiqueqiao.utils.ToastUtils;

/**
 * Created by lzq on 2017/12/21.
 */

public class InputCommentLanguageDialog extends AppCompatDialog implements View.OnClickListener {
    Context context;
    EditText edit_content;
    TextView tv_num;
    TextView tv_cancle;
    TextView tv_save;
    TextView tv_title;
    private SaveCallback mShareCallback;
    private String str = "";//编辑内容

    public InputCommentLanguageDialog(Context context, int theme, String str) {
        super(context, theme);
        this.context = context;
        this.str = str;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_input_cl);
        edit_content = (EditText) findViewById(R.id.edit_content);
        tv_num = (TextView) findViewById(R.id.tv_num);
        tv_title = (TextView) findViewById(R.id.tv_title);
        tv_cancle = (TextView) findViewById(R.id.tv_cancle);
        tv_cancle.setOnClickListener(this);
        tv_save = (TextView) findViewById(R.id.tv_save);
        tv_save.setOnClickListener(this);


        if (str.length() > 0) {
            if (str.equals("招呼语")) {
                tv_title.setText("新增招呼语");
            } else {
                tv_title.setText("编辑常用语");
                edit_content.setText(str);
                edit_content.setSelection(str.length());
                tv_num.setText(str.length() + "/100");
            }
        } else {
            tv_title.setText("新增常用语");
        }
        edit_content.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String s = editable.toString();
                tv_num.setText(s.length() + "/100");

            }
        });
    }


    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.tv_save) {
            if (!StringUtils.isEmpty(edit_content.getText().toString())) {
                if (mShareCallback != null) {
                    mShareCallback.onSaveCall(edit_content.getText().toString());
                }
                dismiss();
            } else {
                ToastUtils.showToast(context, "请输入内容");
            }
        }
        if (id == R.id.tv_cancle) {
            dismiss();
        }
    }

    public interface SaveCallback {
        void onSaveCall(String str);
    }

    public void setmShareCallback(SaveCallback mShareCallback) {
        this.mShareCallback = mShareCallback;
    }
}
