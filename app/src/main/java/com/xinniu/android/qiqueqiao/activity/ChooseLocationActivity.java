package com.xinniu.android.qiqueqiao.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.xinniu.android.qiqueqiao.MainActivity;
import com.xinniu.android.qiqueqiao.R;
import com.xinniu.android.qiqueqiao.base.BaseActivity;
import com.xinniu.android.qiqueqiao.bean.AreaBean;
import com.xinniu.android.qiqueqiao.bean.DetailedUserInfoBean;
import com.xinniu.android.qiqueqiao.bean.RegisterBean;
import com.xinniu.android.qiqueqiao.common.Constants;
import com.xinniu.android.qiqueqiao.request.RequestManager;
import com.xinniu.android.qiqueqiao.request.callback.GetAreaGroupCallback;
import com.xinniu.android.qiqueqiao.request.callback.RegisterCallback;
import com.xinniu.android.qiqueqiao.request.callback.RequestCallback;
import com.xinniu.android.qiqueqiao.request.callback.ResultCallback;
import com.xinniu.android.qiqueqiao.user.UserInfoHelper;
import com.xinniu.android.qiqueqiao.utils.ComUtils;
//import com.xinniu.android.qiqueqiao.utils.IMUtils;
import com.xinniu.android.qiqueqiao.utils.StringUtils;
import com.xinniu.android.qiqueqiao.utils.ToastUtils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import butterknife.BindView;
import butterknife.OnClick;
import cn.jpush.android.api.JPushInterface;
import io.rong.imlib.RongIMClient;
import retrofit2.Call;

public class ChooseLocationActivity extends BaseActivity {
    @BindView(R.id.tv_address)
    TextView tvAddress;
    @BindView(R.id.img_address)
    ImageView imgAddress;
    @BindView(R.id.rlayout_select)
    RelativeLayout rlayoutSelect;
    @BindView(R.id.tv_submit)
    TextView tvSubmit;
    private String mPhone;
    private String mCityName;
    private List<AreaBean> mBean = new ArrayList<>();
    private List<String> options1Items = new ArrayList<>();
    private ArrayList<ArrayList<String>> options2Items = new ArrayList<>();
    private int position1 = 0, position2 = 0;
    private int city_pid;
    private String openId;
    private String unionid;
    private int make = 1;//1:手机验证码，2：微信

    public static void start(Context context, String phone, String city_name, int city_pid, String openId, String unionid, int make) {
        Intent intent = new Intent(context, ChooseLocationActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("phone", phone);
        bundle.putString("city_name", city_name);
        bundle.putInt("city_pid", city_pid);
        bundle.putString("openId", openId);
        bundle.putString("unionid", unionid);
        bundle.putInt("make", make);
        intent.putExtras(bundle);
        context.startActivity(intent, bundle);
    }


    @Override
    public int getLayoutId() {
        return R.layout.activity_choose_location;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        topStatusBar(true);
        ComUtils.addActivity(ChooseLocationActivity.this);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            mPhone = bundle.getString("phone");
            mCityName = bundle.getString("city_name");
            city_pid = bundle.getInt("city_pid");
            openId = bundle.getString("openId");
            unionid = bundle.getString("unionid");
            make = bundle.getInt("make");
        }
        if (StringUtils.isEmpty(mCityName)) {
            imgAddress.setVisibility(View.GONE);
            tvAddress.setHint("请选择");

        } else {
            String[] address = mCityName.split(",");
            if (address.length == 2) {
                tvAddress.setHint("");
                tvAddress.setText(address[1]);
                imgAddress.setVisibility(View.VISIBLE);
            } else if (address.length == 1) {
                tvAddress.setHint("");
                tvAddress.setText(address[0]);
                imgAddress.setVisibility(View.VISIBLE);
            } else if (address.length == 0) {
                tvAddress.setHint("请选择");
                imgAddress.setVisibility(View.GONE);
            }


        }
        getAreaList();
    }

    private void getAreaList() {
        showBookingToast(1);
        RequestManager.getInstance().getAreaList(new GetAreaGroupCallback() {
            @Override
            public void onSuccess(List<AreaBean> item) {
                mBean.clear();
                mBean = item;
                String[] address = mCityName.split(",");
                String province = "";
                String city = "";
                if (address.length == 2) {
                    province = address[0];
                    city = address[1];

                } else if (address.length == 1) {
                    province = address[0];
                }
                for (int i = 0; i < item.size(); i++) {//遍历省份
                    options1Items.add(item.get(i).getName());
                    if (province.equals(item.get(i).getName())) {
                        position1 = i;
                    }
                    ArrayList<String> cityList = new ArrayList<>();//该省的城市列表（第二级）

                    for (int c = 0; c < item.get(i).getCity().size(); c++) {//遍历该省份的所有城市
                        String cityName = item.get(i).getCity().get(c);
                        cityList.add(cityName);//添加城市
                        if (city.equals(cityName)) {
                            position2 = c;
                        }

                    }

                    /**
                     * 添加城市数据
                     */
                    options2Items.add(cityList);
                }
                dismissBookingToast();
            }

            @Override
            public void onFailed(int code, String msg) {
                dismissBookingToast();
                ToastUtils.showCentetToast(ChooseLocationActivity.this, msg);

            }
        });
    }

    @OnClick({R.id.finish_img, R.id.rlayout_select, R.id.tv_submit})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.finish_img:
                finish();
                break;
            case R.id.rlayout_select:
                showPickerView();
                break;
            case R.id.tv_submit:
                if (StringUtils.isEmpty(tvAddress.getText().toString())) {
                    ToastUtils.showCentetToast(ChooseLocationActivity.this, "请选择地区");
                    return;
                }
                doRegister();

                break;
        }
    }

    private void doRegister() {
        showBookingToast(2);
        RequestManager.getInstance().registerV3(mPhone, Constants.lon + "", Constants.lat + "", 1, unionid, openId, city_pid, tvAddress.getText().toString(), new RegisterCallback() {
            @Override
            public void onSuccess(final RegisterBean bean) {
                int status = bean.getStatus();//0：直接登录，1：需要完善信息
                RequestManager.getInstance().getUserInfo(bean.getUser_id(), bean.getToken(), new RequestCallback<DetailedUserInfoBean>() {
                    @Override
                    public void requestStart(Call call) {
                    }

                    @Override
                    public void onSuccess(DetailedUserInfoBean detailedUserInfoBean) {


                    }

                    @Override
                    public void onFailed(int code, String msg) {

                    }

                    @Override
                    public void requestEnd() {

                    }
                });
                if (status == 0) {
                    //IMUtils.connectIM(bean.getRong_token(), true, new ResultCallback<String>() {

                        @Override
                        public void onFail(int errorCode) {
                            ToastUtils.showCentetToast(ChooseLocationActivity.this, "聊天服务器连接失败");
                            dismissBookingToast();
                        }


                        @Override
                        public void onSuccess(String s) {
                            UserInfoHelper.getIntance().setUsername(mPhone);
                            UserInfoHelper.getIntance().setUserId(bean.getUser_id());
                            UserInfoHelper.getIntance().setToken(bean.getToken());
                            UserInfoHelper.getIntance().setRongyunToken(bean.getRong_token());
                            if (JPushInterface.isPushStopped(getApplicationContext())) {
                                Set<String> PushArray = new HashSet<>();
                                int userId = UserInfoHelper.getIntance().getUserId();
                                PushArray.add(userId + "");
                                JPushInterface.resumePush(getApplicationContext());
                                JPushInterface.setAlias(getApplicationContext(), 0, userId + "");
                                JPushInterface.setTags(getApplicationContext(), 0, PushArray);
                            }
                            dismissBookingToast();
                            startActivity(new Intent(ChooseLocationActivity.this, MainActivity.class));
                            ComUtils.finishshortAll();

                        }


                    });
                } else if (status == 1) {
                    Constants.newcomer_package = bean.getNewcomer_package();//是否有新人礼包
                    UserInfoHelper.getIntance().setUsername(mPhone);
                    CompleteInfoActivity.start(ChooseLocationActivity.this, bean.getToken(), bean.getUser_id(), bean.getRong_token());
                    finish();
                }
            }

            @Override
            public void onFailed(int code, String msg) {
                dismissBookingToast();
                ToastUtils.showCentetImgToast(ChooseLocationActivity.this, msg);
            }
        });
    }

    private void showPickerView() {// 弹出选择器

        OptionsPickerView pvOptions = new OptionsPickerBuilder(ChooseLocationActivity.this, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                //返回的分别是三个级别的选中位置
                String opt1tx = options1Items.size() > 0 ?
                        options1Items.get(options1) : "";

                String opt2tx = options2Items.size() > 0
                        && options2Items.get(options1).size() > 0 ?
                        options2Items.get(options1).get(options2) : "";
                city_pid = mBean.get(options1).getId();


                String tx = opt1tx + opt2tx;
                tvAddress.setHint("");
                tvAddress.setText(opt2tx);

            }
        })

                .setTitleText("城市选择")
                .setDividerColor(Color.BLACK)
                .setTextColorCenter(Color.BLACK) //设置选中项文字颜色
                .setContentTextSize(20)
                .setLineSpacingMultiplier(2.0f)//设置两横线之间的间隔倍数
                .setSelectOptions(position1, position2)  //设置默认选中项
                .build();

        pvOptions.setPicker(options1Items, options2Items);//二级选择器
        pvOptions.show();
    }
}