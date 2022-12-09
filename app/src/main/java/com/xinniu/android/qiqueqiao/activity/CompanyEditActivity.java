package com.xinniu.android.qiqueqiao.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.xinniu.android.qiqueqiao.MainActivity;
import com.xinniu.android.qiqueqiao.R;
import com.xinniu.android.qiqueqiao.SelectCompanyDialog;
import com.xinniu.android.qiqueqiao.base.BaseActivity;
import com.xinniu.android.qiqueqiao.bean.MyCompanyBean;
import com.xinniu.android.qiqueqiao.bean.SelectCategory;
import com.xinniu.android.qiqueqiao.bean.UploadBean;
import com.xinniu.android.qiqueqiao.customs.qldialog.QLBottomDialog;
import com.xinniu.android.qiqueqiao.dialog.AlertDialogUtils;
import com.xinniu.android.qiqueqiao.request.RequestManager;
import com.xinniu.android.qiqueqiao.request.callback.CommitCorporateCallback;
import com.xinniu.android.qiqueqiao.request.callback.GetMyCompanyCallback;
import com.xinniu.android.qiqueqiao.request.callback.RequestCallback;
import com.xinniu.android.qiqueqiao.utils.BitmapUtils;
import com.xinniu.android.qiqueqiao.utils.ImageLoader;
import com.xinniu.android.qiqueqiao.utils.ShowUtils;
import com.xinniu.android.qiqueqiao.utils.ToastUtils;
import com.xinniu.android.qiqueqiao.utils.TokePhotoUtils;
import com.xinniu.android.qiqueqiao.widget.CircleImageView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;
import retrofit2.Call;

/**
 * Created by yuchance on 2018/5/8.
 */

public class CompanyEditActivity extends BaseActivity {

    @BindView(R.id.mcompany_logoImg)
    CircleImageView mcompanyLogoImg;
    @BindView(R.id.bcompany_logoRl)
    RelativeLayout bcompanyLogoRl;
    @BindView(R.id.mcombrand_name)
    TextView mcombrandName;
    @BindView(R.id.bcombrand_nameRl)
    RelativeLayout bcombrandNameRl;
    @BindView(R.id.mcompany_industrytv)
    TextView mcompanyIndustrytv;
    @BindView(R.id.bcomindustryRl)
    RelativeLayout bcomindustryRl;
    @BindView(R.id.mcompany_placetv)
    TextView mcompanyPlacetv;
    @BindView(R.id.bcomplaceRl)
    RelativeLayout bcomplaceRl;
    @BindView(R.id.mcompany_introtv)
    TextView mcompanyIntrotv;
    @BindView(R.id.bcomintroRl)
    RelativeLayout bcomintroRl;
    @BindView(R.id.mcompany_nettv)
    TextView mcompanyNettv;
    @BindView(R.id.bcomnetRl)
    RelativeLayout bcomnetRl;
    @BindView(R.id.bcommitTv)
    TextView bcommitTv;
    @BindView(R.id.finish_img)
    RelativeLayout finishImg;
    @BindView(R.id.mcompany_nametv)
    TextView mcompanyNametv;
    private Bundle bundlex;
    private int tokeType = -1;
    private TokePhotoUtils tokePhotoUtils;

    public final static int THECITYCODE = 1010;
    private int city = 0;
    private Call mCall;
    private String url = "";
    private int id = 0;
    private int industry = 0;
    private String brand;
    private String name;
    private String cityName;
    private String companyName;
    private String introduce;
    private String netUrl;


    @Override
    public int getLayoutId() {
        return R.layout.activity_company_edit;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        topStatusBar(false);
        tokePhotoUtils = TokePhotoUtils.getInstance(this);
        Bundle bundle = getIntent().getExtras();
        if (bundle!=null) {
            id = bundle.getInt("id");
        }
        buildData();
    }

    private void buildData() {
        showBookingToast(1);
        RequestManager.getInstance().getMyCompany(new GetMyCompanyCallback() {
            @Override
            public void onSuccess(MyCompanyBean bean) {
                dismissBookingToast();
                if (bean == null) {
                    return;
                }
                if (!TextUtils.isEmpty(bean.getLogo())) {
                    ShowUtils.showImgPerfect(mcompanyLogoImg, bean.getLogo(), 2);
                }
                url = bean.getLogo();
                if (!TextUtils.isEmpty(bean.getBrand())) {
                    brand = bean.getBrand();
                }else {
                    brand = "";
                }
                ShowUtils.showTextPerfect(mcombrandName, brand);
                name = bean.getName();
                ShowUtils.showTextPerfect(mcompanyNametv, name);
                if (!TextUtils.isEmpty(bean.getCity_name())) {
                    cityName = bean.getCity_name();
                }else {
                    cityName = "";
                }
                ShowUtils.showTextPerfect(mcompanyPlacetv, cityName);
                city = bean.getCity();
                if (!TextUtils.isEmpty(bean.getCompany_name())){
                    companyName = bean.getCompany_name();
                }else {
                    companyName = "";
                }
                ShowUtils.showTextPerfect(mcompanyIndustrytv, companyName);

                    industry = bean.getCompany_industry();
               if (!TextUtils.isEmpty(bean.getIntroduce())) {
                introduce = bean.getIntroduce();
               }else {
                   introduce = "";
               }
                ShowUtils.showTextPerfect(mcompanyIntrotv, introduce);
               if (!TextUtils.isEmpty(bean.getUrl())) {
                   netUrl = bean.getUrl();
               }else {
                   netUrl = "";
               }
                ShowUtils.showTextPerfect(mcompanyNettv, netUrl);


            }

            @Override
            public void onFailed(int code, String msg) {
                dismissBookingToast();
                ToastUtils.showCentetToast(CompanyEditActivity.this, msg);
            }
        });


    }

    @OnClick({R.id.bcompany_logoRl, R.id.bcombrand_nameRl, R.id.bcomindustryRl, R.id.mcompany_placetv, R.id.mcompany_introtv, R.id.bcomnetRl, R.id.bcommitTv, R.id.finish_img,R.id.bcompany_change})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.finish_img:
                if (!brand.equals(mcombrandName.getText().toString()) || !companyName.equals(mcompanyIndustrytv.getText().toString()) || !cityName.equals(mcompanyPlacetv.getText().toString())|| !introduce.equals(mcompanyIntrotv.getText().toString())|| !netUrl.equals(mcompanyNettv.getText().toString())) {
                    AlertDialogUtils.AlertDialog(CompanyEditActivity.this, "放弃对资料的修改？", "继续编辑", "放弃", new AlertDialogUtils.setOnClick() {
                        @Override
                        public void setLeftOnClick(DialogInterface dialog) {
                            dialog.dismiss();
                        }

                        @Override
                        public void setRightOnClick(DialogInterface dialog) {
                            finish();
                            dialog.dismiss();
                        }
                    });
                    return;
                }

                finish();
                break;
            case R.id.bcompany_logoRl:
                new QLBottomDialog.Builder(this)
                        .setNormalColor(R.color._999)
                        .setStrOne("拍照")
                        .setStrTwo("从手机相册选择")
                        .setBottomDialogItemCallback(new QLBottomDialog.BottomDialogItemCallback() {
                            @Override
                            public void onClick(int position) {
                                switch (position) {
                                    case 0:
                                        tokeType = 0;
                                        break;
                                    case 1:
                                        tokeType = 1;
                                        break;
                                }
                                requestPermission();
                            }
                        }).show(CompanyEditActivity.this);
                break;
            case R.id.bcombrand_nameRl:
                CompanyEditinfoActivity.startCompanyInfo(CompanyEditActivity.this, "accessCode", "brandName",mcombrandName.getText().toString());
                break;
            case R.id.bcomindustryRl:

                SelectCompanyDialog selectCompanyDialog = new SelectCompanyDialog(this, R.style.QLDialog);
                selectCompanyDialog.setItemSelect(new SelectCompanyDialog.ItemSelect() {
                    @Override
                    public void itemSelect(SelectCategory selectCategories) {
                        ShowUtils.showTextPerfect(mcompanyIndustrytv, selectCategories.getName());
//                        userDetailInfoBean.setCompany_name(selectCategories.getName());
                        industry = selectCategories.getId();
                    }
                });
                selectCompanyDialog.show();
                break;
            case R.id.mcompany_placetv:
                startActivityForResult(new Intent(CompanyEditActivity.this, SelectCityActivity.class), THECITYCODE);
                break;
            case R.id.mcompany_introtv:
                CompanyEditinfoActivity.startCompanyInfo(CompanyEditActivity.this, "accessCode", "introduce",mcompanyIntrotv.getText().toString());
                break;
            case R.id.bcomnetRl:
                CompanyEditinfoActivity.startCompanyInfo(CompanyEditActivity.this, "accessCode", "netUrl",mcompanyNettv.getText().toString());
                break;
            case R.id.bcommitTv:
                String NetUrl = mcompanyNettv.getText().toString();
                String brandName = mcombrandName.getText().toString();
                String companyIntro = mcompanyIntrotv.getText().toString();
                if (TextUtils.isEmpty(brandName)) {
                    ToastUtils.showCentetToast(CompanyEditActivity.this, "请输入品牌名称");
                    return;
                }
                if (industry == 0) {
                    ToastUtils.showCentetToast(CompanyEditActivity.this, "请选择公司行业");
                    return;
                }
                if (city == 0) {
                    ToastUtils.showCentetToast(CompanyEditActivity.this, "请选择所在地区");
                    return;
                }
                commitData(id, url,name,brandName, companyIntro, city, industry, NetUrl);

                break;
            case R.id.bcompany_change:
//                AlertDialog.Builder builder = new AlertDialog.Builder(CompanyEditActivity.this);
//                builder.setTitle("如需更换企业请到个人资料中修改");
//                builder.setPositiveButton("知道了", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        dialog.dismiss();
//                    }
//                }).show();
                AlertDialogUtils.AlertDialog(CompanyEditActivity.this, "如需更换企业请到个人资料中修改", null, "知道了", new AlertDialogUtils.setOnClick() {
                    @Override
                    public void setLeftOnClick(DialogInterface dialog) {

                    }

                    @Override
                    public void setRightOnClick(DialogInterface dialog) {
                        dialog.dismiss();
                    }
                });

                break;
            default:
                break;
        }
    }

    private void commitData(int id, String url,String companyName,String brand, String intro, int city, int industryId, String net) {
        showBookingToast(2);
        RequestManager.getInstance().commitCorporateEdit(id, url,companyName, net, brand, intro, city, industryId, new CommitCorporateCallback() {
            @Override
            public void onSuccess() {
                dismissBookingToast();
                ToastUtils.showCentetToast(CompanyEditActivity.this, "编辑成功");
                finish();
            }

            @Override
            public void onFailed(int code, String msg) {
                dismissBookingToast();
                ToastUtils.showCentetToast(CompanyEditActivity.this, msg);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CompanyEditinfoActivity.THEREQUESTCODE) {
            if (data != null) {
                bundlex = data.getExtras();
                String brand = bundlex.getString("mContent");
                switch (resultCode) {
                    case CompanyEditinfoActivity.BRANDRESULT:
                        mcombrandName.setText(brand);
                        break;
                    case CompanyEditinfoActivity.INDUSTRYRESULT:
                        mcompanyIndustrytv.setText(brand);
                        break;
                    case CompanyEditinfoActivity.INTRODUCERESULT:
                        mcompanyIntrotv.setText(brand);
                        break;
                    case CompanyEditinfoActivity.NETURLRESULT:
                        mcompanyNettv.setText(brand);
                        break;
                    default:
                        break;

                }
            }

        }
        if (requestCode == THECITYCODE) {
            if (resultCode == MainActivity.SELECTED_REQUEST_CODE) {
                mcompanyPlacetv.setText(data.getStringExtra(SelectCityActivity.CITY_NAME));
                city = data.getIntExtra(SelectCityActivity.CITY_ID, 0);
            }

        }
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case TokePhotoUtils.CAMERA_REQUEST:
                    tokePhotoUtils.cropRawPhoto(this, tokePhotoUtils.getImgUri(), TokePhotoUtils.CAMERA_REQUEST);
                    break;
                case TokePhotoUtils.GALLERY_REQUEST:
                    tokePhotoUtils.cropRawPhoto(this, data.getData(), TokePhotoUtils.GALLERY_REQUEST);
                    break;
                case TokePhotoUtils.CROP_REQUEST:
                    updateHead();
                    break;
                default:
                    break;

            }
        }
    }

    /**
     * 获取权限保存图片
     */
    @AfterPermissionGranted(TokePhotoUtils.PERMISSION_TOKE_PHOTO)
    public void requestPermission() {
        if (EasyPermissions.hasPermissions(this, TokePhotoUtils.TOKE_PHOTO)) {
            switch (tokeType) {
                case 0://拍照
                    tokePhotoUtils.tokePhoto(this);
                    break;
                case 1://图册
                    tokePhotoUtils.chooseGallary(this);
                    break;
            }
        } else {
            EasyPermissions.requestPermissions(
                    this,
                    getString(R.string.permission_need_toke_photo),
                    TokePhotoUtils.PERMISSION_TOKE_PHOTO, TokePhotoUtils.TOKE_PHOTO);
        }
    }

    /**
     * 上传头像
     */
    private void updateHead() {
        Bitmap bm = BitmapUtils.compressScale(tokePhotoUtils.getImgFile().getAbsolutePath());
        String base64bm = BitmapUtils.bitmapToBase64(bm);
//        Log.d(TAG, base64bm);
//        mcompanyLogoImg.setImageBitmap(bm);
        RequestManager.getInstance().update(base64bm, new RequestCallback<UploadBean>() {
            @Override
            public void requestStart(Call call) {
                mCall = call;
                showBookingToast(2);
            }

            @Override
            public void onSuccess(UploadBean uploadBean) {
                if (!TextUtils.isEmpty(uploadBean.getPath())) {
                    Log.d(TAG, "onSuccess: " + uploadBean.getPath());
//                    userDetailInfoBean.setHead_pic(uploadBean.getPath());
//                    Picasso.with(BaseApp.getInstance())
//                            .load(uploadBean.getPath())
//                            .skipMemoryCache()
//                            .into(imageHead);
                    url = uploadBean.getPath();
                    ImageLoader.loadAvter(uploadBean.getPath(), mcompanyLogoImg);
                }
            }

            @Override
            public void onFailed(int code, String msg) {
                Log.d(TAG, "onFailed: " + code);
                ToastUtils.showCentetImgToast(mContext, msg);
            }

            @Override
            public void requestEnd() {
                dismissBookingToast();
            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mCall != null) {
            mCall.cancel();
        }
    }

}
