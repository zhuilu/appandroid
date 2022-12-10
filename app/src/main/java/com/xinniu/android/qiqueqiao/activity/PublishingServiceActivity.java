package com.xinniu.android.qiqueqiao.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.xinniu.android.qiqueqiao.R;
import com.xinniu.android.qiqueqiao.adapter.EditResouceAdapter;
import com.xinniu.android.qiqueqiao.adapter.EditResouceTwoAdapter;
import com.xinniu.android.qiqueqiao.adapter.PublishServiceAdapter;
import com.xinniu.android.qiqueqiao.base.BaseActivity;
import com.xinniu.android.qiqueqiao.bean.ImageBean;
import com.xinniu.android.qiqueqiao.bean.ReleaseServiceSuccessBean;
import com.xinniu.android.qiqueqiao.bean.SeclectCateBean;
import com.xinniu.android.qiqueqiao.bean.ServiceCategoryAndTag;
import com.xinniu.android.qiqueqiao.bean.ServiceDetailBean;
import com.xinniu.android.qiqueqiao.bean.UploadBean;
import com.xinniu.android.qiqueqiao.customs.NestedRecyclerView;
import com.xinniu.android.qiqueqiao.customs.label.FlowLayoutManager;
import com.xinniu.android.qiqueqiao.customs.qldialog.QLBottomDialog;
import com.xinniu.android.qiqueqiao.dialog.AlertDialogUtils;
import com.xinniu.android.qiqueqiao.request.RequestManager;
import com.xinniu.android.qiqueqiao.request.callback.AddTagCallback;
import com.xinniu.android.qiqueqiao.request.callback.GetServiceDetailCallback;
import com.xinniu.android.qiqueqiao.request.callback.ReleaseServiceSuccessCallback;
import com.xinniu.android.qiqueqiao.request.callback.RequestCallback;
import com.xinniu.android.qiqueqiao.richtexteditor.RichTextEditor;
import com.xinniu.android.qiqueqiao.utils.FullyGridLayoutManager;
import com.xinniu.android.qiqueqiao.utils.StringUtils;
import com.xinniu.android.qiqueqiao.utils.ToastUtils;
import com.xinniu.android.qiqueqiao.utils.TokePhotoUtils;

import java.io.File;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;
import retrofit2.Call;
import top.zibin.luban.CompressionPredicate;
import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;
import top.zibin.luban.OnRenameListener;

public class PublishingServiceActivity extends BaseActivity implements EditResouceTwoAdapter.EditRemove, EasyPermissions.PermissionCallbacks {
    @BindView(R.id.mpublish_titletv)
    TextView mpublishTitletv;
    @BindView(R.id.activity_publish_recycler)
    RecyclerView activityPublishRecycler;
    @BindView(R.id.mcoopplace_publishtv)
    TextView mcoopplacePublishtv;
    @BindView(R.id.tv_memo)
    TextView tvMemo;
    @BindView(R.id.view_empty)
    View viewEmpty;
    @BindView(R.id.moffer_recycler)
    NestedRecyclerView mofferRecycler;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_detail)
    TextView tvDetail;
    @BindView(R.id.tv_publich)
    TextView tvPublich;
    private EditResouceTwoAdapter provideEditResouceAdapter;
    private ArrayList<String> provideImgList = new ArrayList<>();
    private int mTypeId = -1;
    private PublishServiceAdapter publishServiceAdapter;
    private List<ServiceCategoryAndTag.ZlistBean> offerList = new ArrayList<>();
    private InputMethodManager imm;
    private List<ImageBean> pictureList = new ArrayList<>();
    private List<RichTextEditor.EditData> editList = new ArrayList<>();//原输入的内容，用来替换上传成功的照片
    private List<String> imgList = new ArrayList<>();
    private List<String> thumbList = new ArrayList<>();
    private List<ImageBean> imgListDetail = new ArrayList<>();
    private String provide_img;
    private String thumb_img;
    private static String GOTO_CODE = "gotoCode";
    private int goToCode;
    private int mServiceId;//服务id
    private String detailData = "";
    private int tokeType = -1;
    private TokePhotoUtils tokePhotoUtils;

    public static void start(Context context) {
        Intent intent = new Intent(context, PublishingServiceActivity.class);
        context.startActivity(intent);
    }

    public static void start(AppCompatActivity context, int resourceId, String title, int typeId, int gotoCode) {
        Intent intent = new Intent(context, PublishingServiceActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt("serviceId", resourceId);
        bundle.putString("title", title);
        bundle.putInt("typeId", typeId);
        bundle.putInt(GOTO_CODE, gotoCode);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_publishing_service;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        topStatusBar(true);
        tokePhotoUtils = TokePhotoUtils.getInstance(this);
        mpublishTitletv.setText("发布服务");
        tvPublich.setText("发布");
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            mTypeId = bundle.getInt("typeId");
            mServiceId = bundle.getInt("serviceId");
            if (bundle.getInt(GOTO_CODE, 1001) == 1000) {
                goToCode = bundle.getInt(GOTO_CODE, 1001);
                mpublishTitletv.setText("编辑服务");
                tvPublich.setText("确定保存");

            }
        }
        provideEditResouceAdapter = new EditResouceTwoAdapter(mContext, provideImgList, EditResouceAdapter.TYPE_OFFER, 8 - provideImgList.size());
        FullyGridLayoutManager manager = new FullyGridLayoutManager(this, 4);
        activityPublishRecycler.setLayoutManager(manager);
        activityPublishRecycler.setAdapter(provideEditResouceAdapter);
        provideEditResouceAdapter.setEditRemove(PublishingServiceActivity.this);
        if (goToCode == 1000) {
            reBuildData(mServiceId);
        }

    }

    private void reBuildData(int mServiceId) {
        showBookingToast(1);
        RequestManager.getInstance().getServiceDetails(mServiceId, new GetServiceDetailCallback() {
            @Override
            public void onSuccess(ServiceDetailBean bean) {
                dismissBookingToast();
                String reThumb = bean.getThumb_img();
                String rePhoto = bean.getImages();
                if (!TextUtils.isEmpty(rePhoto)) {
                    String[] photos = rePhoto.split(",");
                    for (int i = 0; i < photos.length; i++) {
                        imgList.add(photos[i]);
                    }
                }
                if (!TextUtils.isEmpty(reThumb)) {
                    String[] thumb = reThumb.split(",");
                    for (int i = 0; i < thumb.length; i++) {
                        thumbList.add(thumb[i]);
                    }
                }
                provideEditResouceAdapter.setTHEEDIT("theEdit");
                provideEditResouceAdapter.setIsAddPic(true);

                provideImgList.clear();
                provideImgList.addAll(imgList);
                provideEditResouceAdapter.notifyDataSetChanged();


                mTypeId = bean.getP_id();
                String[] offerStr = bean.getRemark().split(",");
                String[] offerId = bean.getAttr().split("_");
                offerList.clear();
                for (int i = 0; i < bean.getP_zlist().size(); i++) {
                    offerList.add(new ServiceCategoryAndTag.ZlistBean(ServiceCategoryAndTag.ZlistBean.SYSTEMTYPE, Integer.parseInt(bean.getP_zlist().get(i).getId()), bean.getP_zlist().get(i).getName(), false));

                }
                //获取系统的
                for (int i = 0; i < offerList.size(); i++) {
                    for (int j = 0; j < offerId.length; j++) {
                        if (offerList.get(i).getId() == Integer.parseInt(offerId[j])) {
                            offerList.get(i).setCheck(true);
                        }
                    }

                }


                mcoopplacePublishtv.setText(bean.getP_name());
                update();

                tvTitle.setText(bean.getTitle());
                detailData = bean.getDetails();
                String text = "";
                List<String> textList = StringUtils.cutStringByImgTag(bean.getDetails());
                for (int i = 0; i < textList.size(); i++) {
                    if (textList.get(i).contains("<img")) {
                        text = text + "[图片]";
                    } else if (textList.get(i).contains("<div>")) {
                        String regMatchEnter = "<div>|</div>";
                        String text01 = textList.get(i).replaceAll(regMatchEnter, "");
                        if (text01.contains("</br>")) {
                            text01 = text01.replaceAll("</br>", "");
                        }
                        text = text + text01;
                    }
                }
                tvDetail.setText(text);

            }

            @Override
            public void onUndercarriage(String bean) {
                dismissBookingToast();
            }

            @Override
            public void onFailed(int code, String msg) {
                dismissBookingToast();
                ToastUtils.showCentetImgToast(PublishingServiceActivity.this, msg);
            }
        });

    }

    private void addTag(final String trim, final int isType) {
        RequestManager.getInstance().addTagV2(trim, mTypeId, 0, new AddTagCallback() {
            @Override
            public void onSuccess(SeclectCateBean.UserCategoryBean item) {

                if (offerList.size() > 0) {
                    offerList.add(offerList.size() - 1, new ServiceCategoryAndTag.ZlistBean(ServiceCategoryAndTag.ZlistBean.SYSTEMTYPE,
                            item.getId(), trim, true));
                }
                publishServiceAdapter.notifyDataSetChanged();

                if (imm != null) {
                    imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
                }
                dispopwindow();
            }

            @Override
            public void onFailed(int code, String msg) {
                dispopwindow();
                ToastUtils.showCentetImgToast(PublishingServiceActivity.this, msg);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
//            if (requestCode == EditResouceAdapter.TYPE_OFFER) {
//                refreshprovidePic(data.getStringArrayListExtra(TakePhotoActivity.PHOTO_LIST));
//            }

            if (requestCode == TokePhotoUtils.CAMERA_REQUEST) {
                tokePhotoUtils.cropRawPhotoThree(this, tokePhotoUtils.getImgUri(), TokePhotoUtils.CAMERA_REQUEST);
            } else if (requestCode == TokePhotoUtils.GALLERY_REQUEST) {
                tokePhotoUtils.cropRawPhotoThree(this, data.getData(), TokePhotoUtils.GALLERY_REQUEST);
            } else if (requestCode == TokePhotoUtils.CROP_REQUEST) {
                refreshprovidePic(tokePhotoUtils.getImgFile().getAbsolutePath());
            } else if (requestCode == 30) {
                String name = data.getStringExtra("data");
                Log.d(TAG, "onActivityResult: " + name);

                tvTitle.setText(name);
            } else if (requestCode == 31) {//详情
                String data1 = data.getStringExtra("data");
                String picture = data.getStringExtra("picture");
                String originData = data.getStringExtra("originData");
                Gson gson = new Gson();
                if (picture != null && picture.length() > 0) {

                    pictureList = gson.fromJson(picture, new TypeToken<List<ImageBean>>() {
                    }.getType());
                }
                if (originData != null && originData.length() > 0) {

                    editList = gson.fromJson(originData, new TypeToken<List<RichTextEditor.EditData>>() {
                    }.getType());
                }
                detailData = data1;
                Log.d(TAG, "onActivityResult: " + data1 + ",,,,,,,,,," + picture);
                String text = "";
                List<String> textList = StringUtils.cutStringByImgTag(data1);
                for (int i = 0; i < textList.size(); i++) {
                    if (textList.get(i).contains("<img")) {
                        text = text + "[图片]";
                    } else if (textList.get(i).contains("<div>")) {
                        String regMatchEnter = "<div>|</div>";
                        String text01 = textList.get(i).replaceAll(regMatchEnter, "");
                        if (text01.contains("</br>")) {
                            text01 = text01.replaceAll("</br>", "");
                        }
                        text = text + text01;
                    }
                }
                tvDetail.setText(text);

            } else if (requestCode == 32) {
                String data1 = data.getStringExtra("data");
                mTypeId = data.getIntExtra("id", 0);
                String list = data.getStringExtra("tags");
                offerList.clear();
                if (list != null && list.length() > 0) {
                    Gson gson = new Gson();
                    offerList = gson.fromJson(list, new TypeToken<List<ServiceCategoryAndTag.ZlistBean>>() {
                    }.getType());
                }
                for (int i = 0; i < offerList.size(); i++) {
                    offerList.get(i).setItemType(ServiceCategoryAndTag.ZlistBean.SYSTEMTYPE);
                }
                mcoopplacePublishtv.setText(data1);
                update();


            }
        }

    }

    private void update() {
        //获取服务标签
        viewEmpty.setVisibility(View.GONE);
        mofferRecycler.setVisibility(View.VISIBLE);
        tvMemo.setVisibility(View.GONE);
        offerList.add(new ServiceCategoryAndTag.ZlistBean(ServiceCategoryAndTag.ZlistBean.USERTYPE));
        publishServiceAdapter = new PublishServiceAdapter(PublishingServiceActivity.this, offerList);
        mofferRecycler.setLayoutManager(new FlowLayoutManager());
        mofferRecycler.setAdapter(publishServiceAdapter);
        publishServiceAdapter.setSetAddOnClick(new PublishServiceAdapter.setAddOnClick() {
            @Override
            public void setAddOnClick(int type) {
                if (type == 2) {
                    AlertDialogUtils.AlertDialog(PublishingServiceActivity.this, "最多选择3个标签", "知道了", "", new AlertDialogUtils.setOnClick() {
                        @Override
                        public void setLeftOnClick(DialogInterface dialog) {
                            dialog.dismiss();
                        }

                        @Override
                        public void setRightOnClick(DialogInterface dialog) {

                        }
                    });
                } else {
                    setPopWindow(R.layout.view_resource_type_other);
                    final EditText contentEt = (EditText) popview.findViewById(R.id.view_type_other_ed);
                    TextView cancelTv = (TextView) popview.findViewById(R.id.view_other_cancelTv);
                    TextView addTv = (TextView) popview.findViewById(R.id.view_other_addTv);
//                showSoftInputFromWindow(ResourceTypeActivity.this,contentEt);
                    imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
                    cancelTv.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dispopwindow();
                            if (imm != null) {
                                imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
                            }
                        }
                    });
                    addTv.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (!TextUtils.isEmpty(contentEt.getText().toString().trim())) {
                                addTag(contentEt.getText().toString().trim(), 0);

                            } else {
                                ToastUtils.showToast(PublishingServiceActivity.this, "请输入自定义类型");
                            }
                            dispopwindow();
                        }
                    });
                }
            }
        });
    }

    //刷新图片
    public void refreshprovidePic(String path) {
        //  provideImgList.clear();
        provideImgList.add(path);
        provideEditResouceAdapter.notifyDataSetChanged();
    }


    @OnClick({R.id.rlayout_type, R.id.rlayout_title, R.id.rlayout_detail, R.id.rlayout_biaoqian, R.id.bt_finish, R.id.tv_publich})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rlayout_type:
                //选择类型
                PublishServiceSelectTypeActivity.startSimpleEidtForResult(this, 32, mTypeId);
                break;
            case R.id.rlayout_title:
                InputServiceTitleActivity.startSimpleEidtForResult(this, 30, "服务标题",
                        tvTitle.getText().toString(), "写一个有吸引力的标题吧", 1);
                break;
            case R.id.rlayout_detail:
                //详情
                if (goToCode == 1000) {
                    ServiceDetailDescriptioActivity.startSimpleEidtForResult(this, 31, detailData, "图文介绍", "2");

                } else {
                    ServiceDetailDescriptioActivity.startSimpleEidtForResult(this, 31, detailData, "图文介绍", "1");
                }

                break;
            case R.id.rlayout_biaoqian:
                //标签
                if (StringUtils.isEmpty(mcoopplacePublishtv.getText().toString())) {
                    ToastUtils.showCentetImgToast(mContext, "请先选择服务类型");
                    return;
                }
                break;
            case R.id.bt_finish:
                finish();
                break;
            case R.id.tv_publich:

                StringBuffer offerAttr = new StringBuffer();
                for (int i = 0; i < offerList.size(); i++) {
                    if (offerList.get(i).isCheck()) {
                        offerAttr.append(offerList.get(i).getId());
                        offerAttr.append("_");
                    }
                }
                if (offerAttr.length() > 1) {
                    offerAttr.deleteCharAt(offerAttr.length() - 1);
                }
                if (provideImgList.size() == 0) {
                    showDialogView("请至少上传一张介绍图");
                    return;
                }
                if (TextUtils.isEmpty(mcoopplacePublishtv.getText().toString())) {
                    showDialogView("请选择服务类型");
                    return;
                }

                if (offerAttr.length() == 0) {
                    showDialogView("请选择服务标签");
                    return;
                }
                if (TextUtils.isEmpty(tvTitle.getText().toString())) {
                    showDialogView("请填写服务标题");
                    return;
                }

                if (TextUtils.isEmpty(tvDetail.getText().toString())) {
                    showDialogView("请填写图文介绍");
                    return;
                }

                if (goToCode == 1000) {
                    //编辑

                    reSendData(provideImgList, mTypeId, offerAttr, tvTitle.getText().toString(), tvDetail.getText().toString());
                } else {
                    showBookingToast(2);
                    updateImg(provideImgList, mTypeId, offerAttr, tvTitle.getText().toString(), tvDetail.getText().toString(), "1");
                }
                break;
        }
    }

    ArrayList<ImageBean> reImgList01 = new ArrayList<>();

    private void reSendData(ArrayList<String> provideImgList, int mTypeId, StringBuffer offerAttr, String s, String s1) {
        showBookingToast(2);
        //判断图片和详情图片是否更新
        ArrayList<String> reImgList = new ArrayList<>();

        for (int i = 0; i < provideImgList.size(); i++) {
            if (!provideImgList.get(i).contains("http")) {
                reImgList.add(provideImgList.get(i));
            }
        }

        //从详情里取出图片内容，判断图片是否更新

        ArrayList<ImageBean> list = new ArrayList<>();
        try {
            List<String> textList = StringUtils.cutStringByImgTag(detailData);
            for (int i = 0; i < textList.size(); i++) {
                String text = textList.get(i);
                if (text.contains("<img")) {
                    String imagePath = StringUtils.getImgSrc(text);
                    ImageBean imageBean = new ImageBean(imagePath, imagePath);
                    list.add(imageBean);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();

        }
        for (int i = 0; i < list.size(); i++) {
            if (!list.get(i).getName().contains("http")) {
                reImgList01.add(list.get(i));
            }
        }

        if (reImgList.size() > 0) {
            updateImg(reImgList, mTypeId, offerAttr, s, s1, "2");

        } else if (reImgList01.size() > 0) {

            uploadDetailImag(reImgList01, mTypeId, offerAttr, s, s1, "2");
        } else {
            //重新发布
            reAnalysis(mTypeId, offerAttr, s, s1);
        }


    }

    private void updateImg(ArrayList<String> provideImgList, final int mTypeId, final StringBuffer offerAttr, final String s, final String s1, final String type) {

        for (String item : provideImgList) {
            Luban.with(PublishingServiceActivity.this).load(item).ignoreBy(300).setTargetDir(getPath()).filter(new CompressionPredicate() {
                @Override
                public boolean apply(String path) {


                    return !(TextUtils.isEmpty(path) || path.toLowerCase().endsWith(".gif"));
                }
            }).setRenameListener(new OnRenameListener() {
                @Override
                public String rename(String filePath) {
                    try {
                        MessageDigest md = MessageDigest.getInstance("MD5");
                        md.update(filePath.getBytes());
                        return new BigInteger(1, md.digest()).toString(32);
                    } catch (NoSuchAlgorithmException e) {
                        e.printStackTrace();
                    }
                    return "";
                }
            }).setCompressListener(new OnCompressListener() {
                @Override
                public void onStart() {
                }

                @Override
                public void onSuccess(File file) {
                    RequestManager.getInstance().updateBase64(file.getAbsolutePath(), new RequestCallback<UploadBean>() {
                        @Override
                        public void requestStart(Call call) {

                        }

                        @Override
                        public void onSuccess(UploadBean uploadBean) {
                            imgList.add(uploadBean.getPath());
                            thumbList.add(uploadBean.getThumb_img());
                            if (type.equals("1")) {
                                if (pictureList.size() > 0) {
                                    //上传详情里面的图片
                                    uploadDetailImag(pictureList, mTypeId, offerAttr, s, s1, type);
                                } else {
                                    //发布,详情里没有图片
                                    goToSend(mTypeId, offerAttr, s, s1);
                                }
                            } else {
                                //编辑
                                if (reImgList01.size() > 0) {
                                    uploadDetailImag(reImgList01, mTypeId, offerAttr, s, s1, type);

                                } else {
                                    reAnalysis(mTypeId, offerAttr, s, s1);
                                }
                            }

                        }

                        @Override
                        public void onFailed(int code, String msg) {

                        }

                        @Override
                        public void requestEnd() {

                        }
                    });
                }

                @Override
                public void onError(Throwable e) {
                    dismissBookingToast();
                    ToastUtils.showCentetToast(PublishingServiceActivity.this, "图片压缩失败，请重新选择图片");
                }
            }).launch();


        }

    }

    private void uploadDetailImag(List<ImageBean> provideImgList, final int mTypeId, final StringBuffer offerAttr, final String s, final String s1, final String type) {
        imgListDetail.clear();
        for (final ImageBean m : provideImgList) {

            Luban.with(PublishingServiceActivity.this).load(m.getName()).ignoreBy(300).setTargetDir(getPath()).filter(new CompressionPredicate() {
                @Override
                public boolean apply(String path) {


                    return !(TextUtils.isEmpty(path) || path.toLowerCase().endsWith(".gif"));

                }
            }).setRenameListener(new OnRenameListener() {
                @Override
                public String rename(String filePath) {
                    try {
                        MessageDigest md = MessageDigest.getInstance("MD5");
                        md.update(filePath.getBytes());
                        return new BigInteger(1, md.digest()).toString(32);
                    } catch (NoSuchAlgorithmException e) {
                        e.printStackTrace();
                    }
                    return "";
                }
            }).setCompressListener(new OnCompressListener() {
                @Override
                public void onStart() {
                }

                @Override
                public void onSuccess(File file) {
                    RequestManager.getInstance().updateBase64(file.getAbsolutePath(), new RequestCallback<UploadBean>() {
                        @Override
                        public void requestStart(Call call) {

                        }

                        @Override
                        public void onSuccess(UploadBean uploadBean) {

                            ImageBean imageBean = new ImageBean(m.getId(), uploadBean.getPath());
                            imgListDetail.add(imageBean);
                            if (type.equals("1")) {
                                //发布
                                goToSend(mTypeId, offerAttr, s, s1);
                            } else {
                                //编辑
                                reAnalysis(mTypeId, offerAttr, s, s1);
                            }


                        }

                        @Override
                        public void onFailed(int code, String msg) {

                        }

                        @Override
                        public void requestEnd() {

                        }
                    });
                }

                @Override
                public void onError(Throwable e) {
                    dismissBookingToast();
                    ToastUtils.showCentetToast(PublishingServiceActivity.this, "图片压缩失败，请重新选择图片");
                }
            }).launch();


        }
    }

    /**
     * 编辑保存
     *
     * @param mTypeId
     * @param offerAttr
     * @param s
     * @param s1
     */
    private void reAnalysis(int mTypeId, StringBuffer offerAttr, String s, String s1) {
        if (isUpdateSuccessTwo()) {
            if (imgList.size() > 0) {
                for (int i = 0; i < imgList.size(); i++) {
                    if (i == 0) {
                        provide_img = imgList.get(i);
                    } else {
                        provide_img = provide_img + "," + imgList.get(i);
                    }
                }
            }
            if (thumbList.size() > 0) {
                for (int i = 0; i < thumbList.size(); i++) {
                    if (i == 0) {
                        thumb_img = thumbList.get(i);
                    } else {
                        thumb_img = thumb_img + "," + thumbList.get(i);
                    }
                }
            }

            if (editList.size() > 0) {
                detailNew = getEditData();
            } else {
                detailNew = s1;
            }
            Log.i("iiiiiiiiii", detailNew);


            RequestManager.getInstance().UpdateServiceInfo(mTypeId, offerAttr.toString(), s, detailNew, provide_img, thumb_img, mServiceId, new ReleaseServiceSuccessCallback() {
                @Override
                public void onSuccess(ReleaseServiceSuccessBean item) {
                    dismissBookingToast();
                    //发布
                    Intent intent = new Intent(PublishingServiceActivity.this, ServiceReleaseSuccessActivity.class);
                    intent.putExtra("id", item.getId());
                    intent.putExtra("from", "edit");
                    startActivity(intent);
                    finish();
                }

                @Override
                public void onFailed(int code, String msg) {
                    dismissBookingToast();
                    ToastUtils.showCentetImgToast(PublishingServiceActivity.this, msg);
                }

            });
        }
    }

    String detailNew = "";

    //去发布服务
    private void goToSend(int mTypeId, StringBuffer offerAttr, String title, String detail) {
        if (isUpdateSuccess()) {
            if (imgList.size() > 0) {
                for (int i = 0; i < imgList.size(); i++) {
                    if (i == 0) {
                        provide_img = imgList.get(i);
                    } else {
                        provide_img = provide_img + "," + imgList.get(i);
                    }
                }
            }
            if (thumbList.size() > 0) {
                for (int i = 0; i < thumbList.size(); i++) {
                    if (i == 0) {
                        thumb_img = thumbList.get(i);
                    } else {
                        thumb_img = thumb_img + "," + thumbList.get(i);
                    }
                }
            }


            detailNew = getEditData();
            Log.i("iiiiiiiiii", detailNew);


            RequestManager.getInstance().sendServiceInfo(mTypeId, offerAttr.toString(), title, detailNew, provide_img, thumb_img, new ReleaseServiceSuccessCallback() {
                @Override
                public void onSuccess(ReleaseServiceSuccessBean item) {
                    dismissBookingToast();
                    //发布
                    Intent intent = new Intent(PublishingServiceActivity.this, ServiceReleaseSuccessActivity.class);
                    intent.putExtra("id", item.getId());
                    intent.putExtra("from", "push");
                    startActivity(intent);
                    finish();
                }

                @Override
                public void onFailed(int code, String msg) {
                    dismissBookingToast();
                    ToastUtils.showCentetImgToast(PublishingServiceActivity.this, msg);
                }

            });
        }
    }

    /**
     * 负责处理编辑数据提交等事宜，请自行实现
     */
    private String getEditData() {
        for (ImageBean m : pictureList) {
            for (ImageBean n : imgListDetail) {
                if (m.getId().equals(n.getId())) {
                    //把上传成功的照片地址替换本地地址
                    m.setName(n.getName());
                }

            }
        }
        StringBuffer content = new StringBuffer();
        for (RichTextEditor.EditData itemData : editList) {
            if (itemData.inputStr != null) {
                if (itemData.inputStr.contains("\n")) {
                    //所以换行符改成html的换行
                    content.append("<div>").append(itemData.inputStr.replaceAll("\n", "</br>")).append("</div>");
                } else {
                    content.append("<div>").append(itemData.inputStr).append("</div>");
                }
            } else if (itemData.imagePath != null) {
                for (ImageBean m : pictureList) {

                    if (m.getId().equals(itemData.imagePath)) {
                        //把上传成功的照片地址替换本地地址
                        content.append("<img src=\"").append(m.getName()).append("\"/>");
                    }


                }

            }
        }
        return content.toString();
    }

    public void showDialogView(String msg) {
        AlertDialogUtils.AlertDialog(PublishingServiceActivity.this, msg, "知道了", "", new AlertDialogUtils.setOnClick() {
            @Override
            public void setLeftOnClick(DialogInterface dialog) {
                dialog.dismiss();
            }

            @Override
            public void setRightOnClick(DialogInterface dialog) {

            }
        });

    }

    public boolean isUpdateSuccess() {
        if (imgList.size() == provideImgList.size() && imgListDetail.size() == pictureList.size()) {
            return true;
        } else {
            return false;
        }

    }

    public boolean isUpdateSuccessTwo() {
        if (imgList.size() == provideImgList.size() && imgListDetail.size() == reImgList01.size()) {
            return true;
        } else {
            return false;
        }

    }


    @Override
    public void editRemove(int postion, int type) {
        if (type == 1) {
            imgList.remove(postion);
        } else {
            //弹出选择照片
            new QLBottomDialog.Builder(this)
                    .setNormalColor(R.color._999)
                    .setStrOne("拍照")
                    .setStrTwo("从手机相册选择")
                    .setStrCancel("取消")
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
                    }).show(PublishingServiceActivity.this);

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

    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {

    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            new AppSettingsDialog.Builder(this)
                    .setTitle(R.string.need_permission_setting_title)
                    .setRationale(R.string.need_permission_setting_content)
                    .build()
                    .show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }


    private String getPath() {
        String path = Environment.getExternalStorageDirectory() + "/Luban/image/";
        File file = new File(path);
        if (file.mkdirs()) {
            return path;
        }
        return path;
    }

}
