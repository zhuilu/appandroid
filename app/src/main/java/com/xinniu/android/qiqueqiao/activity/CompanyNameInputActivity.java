package com.xinniu.android.qiqueqiao.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
//import android.support.v7.widget.LinearLayoutManager;
//import androidx.recyclerview.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.xinniu.android.qiqueqiao.R;
import com.xinniu.android.qiqueqiao.adapter.CompanyNameInputAdapter;
import com.xinniu.android.qiqueqiao.base.BaseActivity;
import com.xinniu.android.qiqueqiao.bean.CityTokenBean;
import com.xinniu.android.qiqueqiao.bean.CompanyNameBean;
import com.xinniu.android.qiqueqiao.bean.CompanyNameNewBean;
import com.xinniu.android.qiqueqiao.user.UserInfoHelper;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by yuchance on 2018/5/15.
 */

public class CompanyNameInputActivity extends BaseActivity {

    @BindView(R.id.bfinish_img)
    RelativeLayout bfinishImg;
    @BindView(R.id.bfinish_tv)
    TextView bfinishTv;
    @BindView(R.id.mcompany_input_recycler)
    RecyclerView mcompanyInputRecycler;
    @BindView(R.id.company_title)
    TextView companyTitle;
    @BindView(R.id.mcompany_ed)
    EditText mcompanyEd;

    private String editString;
    private List<CompanyNameNewBean.ResultBean.ItemsBean> datas = new ArrayList<>();
    private CompanyNameInputAdapter adapter;


    private Handler handler = new Handler();

    /**
     * 延迟线程，看是否还有下一个字符输入
     */
    private Runnable delayRun = new Runnable() {

        @Override
        public void run() {
            //在这里调用服务器的接口，获取数据
            getSearchResult(editString);
        }
    };
    private String companyName;
    private final OkHttpClient client = new OkHttpClient();

    @Override
    public int getLayoutId() {
        return R.layout.activity_company_nameinput;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        topStatusBar(true);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            companyName = bundle.getString("companyName");
        }
        mcompanyEd.setText(companyName);
        mcompanyEd.requestFocus();
        LinearLayoutManager manager = new LinearLayoutManager(CompanyNameInputActivity.this, LinearLayoutManager.VERTICAL, false);
        mcompanyInputRecycler.setLayoutManager(manager);
        adapter = new CompanyNameInputAdapter(CompanyNameInputActivity.this, R.layout.item_company_input, datas);
        mcompanyInputRecycler.setAdapter(adapter);
        mcompanyEd.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (delayRun != null) {
                    //每次editText有变化的时候，则移除上次发出的延迟线程
                    handler.removeCallbacks(delayRun);
                }
                editString = s.toString();

                //延迟800ms，如果不再输入字符，则执行该线程的run方法
                handler.postDelayed(delayRun, 200);
            }
        });
        adapter.setSetCompanyName(new CompanyNameInputAdapter.SetCompanyName() {
            @Override
            public void setCompanyName(String company) {
                Intent intent = new Intent();
                intent.putExtra("data", company);
                setResult(1033, intent);
                finish();
            }
        });

    }


    @OnClick({R.id.bfinish_img, R.id.bfinish_tv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bfinish_img:
                finish();
                break;
            case R.id.bfinish_tv:
                Intent intent = new Intent();
                intent.putExtra("data", mcompanyEd.getText().toString());
                setResult(1033, intent);
                finish();
                break;
            default:

                break;
        }
    }

    private void getSearchResult(final String editString) {
//        String QYtoken = UserInfoHelper.getIntance().getQyToken();
//        if (!TextUtils.isEmpty(QYtoken)) {
        Request request = new Request.Builder().addHeader("Authorization", "936ee03e-951c-4c46-928a-a17c3819d101").url("http://open.api.tianyancha.com/services/open/search/2.0?word=" + editString).build();
        client.newCall(request).enqueue(new Callback() {
            public void onResponse(Call call, final Response response) throws IOException {
                datas.clear();
                final String result = response.body().string();
                try {
                    JSONObject object = new JSONObject(result);
                    String code = object.getString("error_code");
                    if (code.equals("0")) {
                        Gson gson = new Gson();
                        CompanyNameNewBean bean = gson.fromJson(result, CompanyNameNewBean.class);

                        if (bean.getResult().getTotal() > 5) {
                            for (int i = 0; i < 5; i++) {
                                datas.add(bean.getResult().getItems().get(i));
                            }

                        } else {
                            datas.addAll(bean.getResult().getItems());
                        }

                    }
                    mHandler.sendEmptyMessage(0);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            public void onFailure(Call call, IOException e) {

            }
        });

//            OkHttpUtils.get().url("http://api.qianzhan.com/OpenPlatformService/CompanyInputTips")
//                    .addParams("token",QYtoken)
//                    .addParams("type","JSON")
//                    .addParams("input",editString)
//                    .build().execute(new StringCallback() {
//                @Override
//                public void onError(Call call, Exception e, int id) {
//
//                }
//
//                @Override
//                public void onResponse(String response, int id) {
//                    try {
//                        JSONObject object = new JSONObject(response);
//                        String code = object.getString("status");
//                        if (code.equals("200")){
//                            Gson gson = new Gson();
//                            CompanyNameBean bean = gson.fromJson(response,CompanyNameBean.class);
//                            datas.clear();
//                           datas.addAll(bean.getResult());
//                           adapter.notifyDataSetChanged();
//                        }else if (code.equals("101")||code.equals("102")){
//                            refreshToken();
//                        }
//
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//                }
//            });
//        } else {
//            refreshToken();
//        }
    }

    private void refreshToken() {
//        RequestManager.getInstance().getCompanyToken(new CompanyTokenCallback() {
//            @Override
//            public void onSuccess(ResultDO<CityTokenBean> bean) {
//                getSearchResult(editString);
//            }
//
//            @Override
//            public void onFailue(int code, String msg) {
//                ToastUtils.showCentetToast(CompanyNameInputActivity.this,msg);
//            }
//        });
//        OkHttpUtils.get().url("http://api.qianzhan.com/OpenPlatformService/GetToken")
//                .addParams("type", "JSON").addParams("appkey", "3d4f8662f53c95d9")
//                .addParams("seckey", "17691c25fad6ec1b")
//                .build().execute(new StringCallback() {
//
//            @Override
//            public void onError(Call call, Exception e, int id) {
//
//            }
//
//            @Override
//            public void onResponse(String response, int id) {
//                try {
//                    JSONObject object = new JSONObject(response);
//                    String code = object.getString("status");
//                    if (code.equals("200")) {
//                        Gson gson = new Gson();
//                        CityTokenBean bean = gson.fromJson(response, CityTokenBean.class);
//                        if (!TextUtils.isEmpty(bean.getResult().getToken())) {
//                            String token = bean.getResult().getToken();
//                            UserInfoHelper.getIntance().setQYtoken(token);
//                            getSearchResult(editString);
//                        }
//
//                    }
//
//
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//
//
//            }
//        });

        Request request = new Request.Builder().url("http://api.qianzhan.com/OpenPlatformService/GetToken?type=JSON&appkey=3d4f8662f53c95d9&seckey=17691c25fad6ec1b").build();
        client.newCall(request).enqueue(new Callback() {
            public void onResponse(Call call, final Response response) throws IOException {
                final String result = response.body().string();
                try {
                    JSONObject object = new JSONObject(result);
                    String code = object.getString("status");
                    if (code.equals("200")) {
                        Gson gson = new Gson();
                        CityTokenBean bean = gson.fromJson(result, CityTokenBean.class);
                        if (!TextUtils.isEmpty(bean.getResult().getToken())) {
                            String token = bean.getResult().getToken();
                            UserInfoHelper.getIntance().setQYtoken(token);
                            getSearchResult(editString);
                        }

                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            public void onFailure(Call call, IOException e) {

            }
        });
    }

    Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    //do something,refresh UI;
                    adapter.notifyDataSetChanged();
                    break;
                default:
                    break;
            }
        }

    };
}
