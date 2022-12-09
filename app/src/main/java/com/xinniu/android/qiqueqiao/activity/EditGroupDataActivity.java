package com.xinniu.android.qiqueqiao.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.xinniu.android.qiqueqiao.R;
import com.xinniu.android.qiqueqiao.base.BaseActivity;
import com.xinniu.android.qiqueqiao.bean.DetailedUserInfoBean;
import com.xinniu.android.qiqueqiao.bean.SelectCategory;
import com.xinniu.android.qiqueqiao.request.RequestManager;
import com.xinniu.android.qiqueqiao.request.callback.AllResultDoCallback;
import com.xinniu.android.qiqueqiao.request.callback.GetCategoryCallback;
import com.xinniu.android.qiqueqiao.utils.ShowUtils;
import com.xinniu.android.qiqueqiao.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by yuchance on 2018/10/9.
 */

public class EditGroupDataActivity extends BaseActivity {
    @BindView(R.id.mgroupNameTv)
    TextView mgroupNameTv;
    @BindView(R.id.mgroupintroTv)
    TextView mgroupintroTv;
    @BindView(R.id.mgroupIndustryTv)
    TextView mgroupIndustryTv;
    @BindView(R.id.mgroupCityTv)
    TextView mgroupCityTv;
    private String groupName;
    private String groupIntro;
    private String industry;
    private int industryId;
    private String groupCity;
    private int groupCityId;
    private int groupId;
//    private DetailedUserInfoBean userDetailInfoBean;//网络加载的用户详细数据
    private List<String> industryList = new ArrayList<>();
    private List<Integer> industryIdList = new ArrayList<>();

    public final static int SELECTED_CITY_SUCCESS = 501;

    private OptionsPickerView industryPv;
    private int option = 0;

    public static void start(Activity context, int groupId, String groupName, String groupIntro, String industry, int industryId, String groupCity, int groupCityId){
        Intent intent = new Intent(context,EditGroupDataActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt("groupId",groupId);
        bundle.putString("groupName",groupName);
        bundle.putString("groupIntro",groupIntro);
        bundle.putString("industry",industry);
        bundle.putInt("industryId",industryId);
        bundle.putString("groupCity",groupCity);
        bundle.putInt("groupCityId",groupCityId);
        intent.putExtras(bundle);
        context.startActivityForResult(intent,401,bundle);


    }






    @Override
    public int getLayoutId() {
        return R.layout.activity_editgroupdata;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {topStatusBar(true);
        Bundle bundle =getIntent().getExtras();
        if (bundle!=null){
            groupId = bundle.getInt("groupId");
            groupName = bundle.getString("groupName");
            ShowUtils.showTextPerfect(mgroupNameTv,groupName);
            groupIntro = bundle.getString("groupIntro");
            ShowUtils.showTextPerfect(mgroupintroTv,groupIntro);
            industry = bundle.getString("industry");
            ShowUtils.showTextPerfect(mgroupIndustryTv,industry);
            industryId = bundle.getInt("industryId");
            groupCity = bundle.getString("groupCity");
            ShowUtils.showTextPerfect(mgroupCityTv,groupCity);
            groupCityId = bundle.getInt("groupCityId");
        }
        initIndustry();

    }
    private void initIndustry() {
        RequestManager.getInstance().getScreen(2, new GetCategoryCallback() {
            @Override
            public void onSuccess(List<SelectCategory> list) {
                industryList.clear();
                industryIdList.clear();
                for (int i = 0; i < list.size(); i++) {
                    industryList.add(list.get(i).getName());
                    industryIdList.add(list.get(i).getId());
                }
            }

            @Override
            public void onFailed(int code, String msg) {

            }
        });
        industryPv = new OptionsPickerBuilder(EditGroupDataActivity.this, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
//                if (userDetailInfoBean == null) {
//                    return;
//                }
                option = options1;
                mgroupIndustryTv.setText(industryList.get(options1));
//                userDetailInfoBean.setCompany_industry(industryIdList.get(options1));
                industryId = industryIdList.get(options1);
            }
        }).setContentTextSize(25).build();
        industryPv.setPicker(industryList);
//        for (int i = 0; i < industryIdList.size(); i++) {
//            if (userDetailInfoBean.getCompany_industry() == industryIdList.get(i)){
//                option = i;
//            }
//        }
        industryPv.setSelectOptions(option);
    }



    @OnClick({R.id.beditcacelTv, R.id.bsavetv, R.id.bgroupNameRl, R.id.groupintroRl, R.id.groupIndustryRl, R.id.groupCityRl})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.beditcacelTv:
                finish();
                break;
            case R.id.bsavetv:
                commitData();
                break;
            case R.id.bgroupNameRl:
                EditGroupActivity.start(EditGroupDataActivity.this,"groupname",groupId,groupName);
                break;
            case R.id.groupintroRl:
                EditGroupActivity.start(EditGroupDataActivity.this,"groupintro",groupIntro);
                break;
            case R.id.groupIndustryRl:
//                if (userDetailInfoBean == null) {
//                    return;
//                }


                industryPv.show();
                break;
            case R.id.groupCityRl:
                startActivityForResult(new Intent(EditGroupDataActivity.this,SelectCityActivity.class),SELECTED_CITY_SUCCESS);

                break;
                default:
                    break;
        }
    }

    private void commitData() {
        RequestManager.getInstance().modifyInfo(groupId, groupName, groupIntro, industryId, groupCityId, new AllResultDoCallback() {
            @Override
            public void onSuccess(String msg) {
                ToastUtils.showCentetToast(EditGroupDataActivity.this,msg);
                setResult(402);
                finish();
            }

            @Override
            public void onFailed(int code, String msg) {
                ToastUtils.showCentetToast(EditGroupDataActivity.this,msg);
            }
        });




    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == SELECTED_CITY_SUCCESS){
            mgroupCityTv.setText(data.getStringExtra(SelectCityActivity.CITY_NAME));
            groupCityId = data.getIntExtra(SelectCityActivity.CITY_ID,0);
        }
        if (requestCode == 501&&resultCode == 502){
            groupName = data.getStringExtra("groupName");
            mgroupNameTv.setText(groupName);
        }
        if (requestCode == 601&&resultCode == 602){
            groupIntro = data.getStringExtra("groupIntro");
            mgroupintroTv.setText(groupIntro);
        }

    }
}
