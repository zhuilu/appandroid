package com.xinniu.android.qiqueqiao.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.xinniu.android.qiqueqiao.MainActivity;
import com.xinniu.android.qiqueqiao.R;
import com.xinniu.android.qiqueqiao.base.BaseActivity;
import com.xinniu.android.qiqueqiao.request.RequestManager;
import com.xinniu.android.qiqueqiao.request.callback.AllResultDoCallback;
import com.xinniu.android.qiqueqiao.utils.ComUtils;
import com.xinniu.android.qiqueqiao.utils.ToastUtils;

import butterknife.BindView;
import butterknife.OnClick;

//import android.support.v4.content.ContextCompat;

/**
 * Created by yuchance on 2018/10/9.
 */

public class PerfectGroupActivity extends BaseActivity {

    @BindView(R.id.mperfectEt)
    EditText mperfectEt;
    @BindView(R.id.mgroupCityTv)
    TextView mgroupCityTv;
    @BindView(R.id.bnextgoTv)
    TextView bnextgoTv;
    private int cityId;
    private int groupId;
    private String groupName;

    @Override
    public int getLayoutId() {
        return R.layout.activity_perfectgroup;
    }

    public static void start(AppCompatActivity context, String groupName, int groupId) {
        Intent intent = new Intent(context, PerfectGroupActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("groupName",groupName);
        bundle.putInt("groupId",groupId);
        intent.putExtras(bundle);
        context.startActivityForResult(intent,501,bundle);

    }


    @Override
    public void initViews(Bundle savedInstanceState) {topStatusBar(true);
        ComUtils.addActivity(PerfectGroupActivity.this);
        bnextgoTv.setSelected(false);
        bnextgoTv.setClickable(false);
        Bundle bundle = getIntent().getExtras();
        if (bundle!=null){
            groupId = bundle.getInt("groupId");
            groupName = bundle.getString("groupName");
        }

        mperfectEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (mperfectEt!=null&&mperfectEt.getText().toString().trim().length()>0){
                    if (!TextUtils.isEmpty(mgroupCityTv.getText().toString())) {
                        bnextgoTv.setSelected(true);
                        bnextgoTv.setClickable(true);
                    }else {
                        bnextgoTv.setSelected(false);
                        bnextgoTv.setClickable(false);
                    }
                }else {
                    bnextgoTv.setSelected(false);
                    bnextgoTv.setClickable(false);
                }
            }
        });

    }

    @OnClick({R.id.bt_finish, R.id.perCityRl, R.id.bnextgoTv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bt_finish:
                finish();
                break;
            case R.id.perCityRl:
                intent = new Intent(PerfectGroupActivity.this, SelectCityActivity.class);
                intent.putExtra(SelectCityActivity.FROM_TYPE, 1);
                startActivityForResult(intent, MainActivity.SELECTED_REQUEST_CODE);
                break;
            case R.id.bnextgoTv:
                commitData();
                break;
            default:
                break;
        }
    }

    private void commitData() {
        if (mperfectEt!=null&&(mperfectEt.getText().toString().trim().length()<15||mperfectEt.getText().toString().trim().length()>500)){
            ToastUtils.showCentetToast(PerfectGroupActivity.this,"群介绍为15个字到500个字之间");
            return;
        }
        RequestManager.getInstance().establishGroup(PerfectGroupActivity.this, groupName, groupId, mperfectEt.getText().toString().trim(), cityId, new AllResultDoCallback() {
            @Override
            public void onSuccess(String msg) {
                ToastUtils.showCentetToast(PerfectGroupActivity.this,msg);
                setResult(500);
                ComUtils.finishshortAll();
            }

            @Override
            public void onFailed(int code, String msg) {
                ToastUtils.showCentetToast(PerfectGroupActivity.this,msg);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == MainActivity.SELECTED_REQUEST_CODE) {
            cityId = data.getIntExtra(SelectCityActivity.CITY_ID, -1);
            String cityName = data.getStringExtra(SelectCityActivity.CITY_NAME);
            if (cityId != -1) {
                mgroupCityTv.setText(cityName);
                mgroupCityTv.setTextColor(ContextCompat.getColor(PerfectGroupActivity.this, R.color.colorPrimary));
                if (mperfectEt.getText().toString().trim().length() > 0) {
                    bnextgoTv.setSelected(true);
                    bnextgoTv.setClickable(true);
                }
            }
        }
    }

}
