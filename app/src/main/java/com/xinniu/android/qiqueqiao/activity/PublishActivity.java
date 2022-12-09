//package com.xinniu.android.qiqueqiao.activity;
//
//import android.content.Context;
//import android.content.Intent;
//import android.os.Build;
//import android.os.Bundle;
////import android.support.annotation.RequiresApi;
////import androidx.recyclerview.widget.RecyclerView;
//import android.text.TextUtils;
//import android.view.View;
//import android.widget.EditText;
//import android.widget.ImageView;
//import android.widget.RelativeLayout;
//import android.widget.ScrollView;
//import android.widget.TextView;
//
//import com.xinniu.android.qiqueqiao.EditResouceInfoHelper;
//import com.xinniu.android.qiqueqiao.MainActivity;
//import com.xinniu.android.qiqueqiao.R;
//import com.xinniu.android.qiqueqiao.ReleaseStepHelper;
//import com.xinniu.android.qiqueqiao.adapter.EditResouceAdapter;
//import com.xinniu.android.qiqueqiao.base.BaseActivity;
//import com.xinniu.android.qiqueqiao.bean.RePublishBean;
//import com.xinniu.android.qiqueqiao.bean.ResourceReleaseBean;
//import com.xinniu.android.qiqueqiao.bean.UploadBean;
//import com.xinniu.android.qiqueqiao.request.RequestManager;
//import com.xinniu.android.qiqueqiao.request.callback.RequestCallback;
//import com.xinniu.android.qiqueqiao.request.callback.ResourceReleaseCallback;
//import com.xinniu.android.qiqueqiao.utils.FullyGridLayoutManager;
//import com.xinniu.android.qiqueqiao.utils.ShowUtils;
//import com.xinniu.android.qiqueqiao.utils.ToastUtils;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import butterknife.BindView;
//import butterknife.ButterKnife;
//import butterknife.OnClick;
//import retrofit2.Call;
//import top.zibin.luban.CompressionPredicate;
//import top.zibin.luban.Luban;
//
///**
// * Created by yuchance on 2018/3/29.
// * 发布资源页面(已废弃)
// */
//
//public class PublishActivity extends BaseActivity implements EditResouceAdapter.EditRemove {
//
//    @BindView(R.id.bt_close)
//    ImageView btClose;
//    @BindView(R.id.activity_publish_sendtv)
//    TextView activityPublishSendtv;
//    @BindView(R.id.activity_publish_coop_tv)
//    TextView activityPublishCoopTv;
//    @BindView(R.id.activity_publish_coop_Img)
//    ImageView activityPublishCoopImg;
//    @BindView(R.id.activity_publish_coop_content_tv)
//    TextView activityPublishCoopContentTv;
//    @BindView(R.id.activity_publish_demand_tv)
//    TextView activityPublishDemandTv;
//    @BindView(R.id.activity_publish_supply_tv)
//    TextView activityPublishSupplyTv;
//    @BindView(R.id.activity_publish_supply_img)
//    ImageView activityPublishSupplyImg;
//    @BindView(R.id.activity_publish_demandx_tv)
//    TextView activityPublishDemandxTv;
//    @BindView(R.id.activity_publish_demandx_img)
//    ImageView activityPublishDemandxImg;
//    @BindView(R.id.activity_publish_recycler)
//    RecyclerView activityPublishRecycler;
//    @BindView(R.id.activity_publish_sv)
//    ScrollView activityPublishSv;
//    @BindView(R.id.coopRl)
//    RelativeLayout coopRl;
//    @BindView(R.id.supplyRl)
//    RelativeLayout supplyRl;
//    @BindView(R.id.demandxRl)
//    RelativeLayout demandxRl;
//
//
//    @BindView(R.id.supply_ed_activity_publish)
//    TextView supplyEdActivityPublish;
//    @BindView(R.id.demandx_ed_activity_publish)
//    TextView demandxEdActivityPublish;
//    @BindView(R.id.ed_activity_publish)
//    EditText edtextActivityPublish;
//
//    private static final String RESOURCE_ID = "RESOURCE_ID";
//    @BindView(R.id.publish_title)
//    TextView publishTitle;
//    @BindView(R.id.coopplaceRl)
//    RelativeLayout coopplaceRl;
//    @BindView(R.id.activity_publish_coopplace_content_tv)
//    TextView activityPublishCoopplaceContentTv;
//    @BindView(R.id.msupplydetail_ed_activity_publish)
//    TextView msupplydetailEdActivityPublish;
//    @BindView(R.id.bsupplydetailRl)
//    RelativeLayout bsupplydetailRl;
//    @BindView(R.id.mdemanddetailx_ed_activity_publish)
//    TextView mdemanddetailxEdActivityPublish;
//    @BindView(R.id.bdemanddetailxRl)
//    RelativeLayout bdemanddetailxRl;
//
//
//    private EditResouceAdapter provideEditResouceAdapter;
//    private ArrayList<String> provideImgList = new ArrayList<>();
//
//
//    private Bundle bundle;
//
//    public static int COOPWAY_CODE = 101;
//
//    public static int RSA_NEED_CODE = 102;
//
//    public static int RSA_OFFER_CODE = 103;
//
////    public final static
//
//
//    private String provide_attr;
//
//    private String need_attr;
//    private List<String> offerList = new ArrayList<>();
//    private List<String> thumbList = new ArrayList<>();
//    private String provide_img;
//    private String thumb_img;
//    private int coopId;
//    private int cityId;
//    private static String GOTO_CODE = "gotoCode";
//    public RePublishBean PublishBean;
//    private int editPosition;
//    private ArrayList<Integer> offerselectList = new ArrayList<>();
//    private ArrayList<Integer> needselectList = new ArrayList<>();
//
//
//    private int id;
//    private ArrayList<String> reImgList = new ArrayList<>();
//    private String needName;
//    private String offerName;
//
//
//    public static void start(Context context, int resId, int gotoCode) {
//        Intent starter = new Intent(context, PublishActivity.class);
//        starter.putExtra(RESOURCE_ID, resId);
//        starter.putExtra(GOTO_CODE, gotoCode);
//        context.startActivity(starter);
//    }
//
//    @Override
//    public int getLayoutId() {
//        return R.layout.activity_publish;
//    }
//
//    @Override
//    public void initViews(Bundle savedInstanceState) {
//        topStatusBar(true);
//        activityPublishCoopContentTv.setKeyListener(null);
//        supplyEdActivityPublish.setKeyListener(null);
//        demandxEdActivityPublish.setKeyListener(null);
//        provideEditResouceAdapter = new EditResouceAdapter(mContext, provideImgList, EditResouceAdapter.TYPE_OFFER, 8 - provideImgList.size());
//        FullyGridLayoutManager manager = new FullyGridLayoutManager(this, 4);
//
//        activityPublishRecycler.setLayoutManager(manager);
//
//        activityPublishRecycler.setAdapter(provideEditResouceAdapter);
//
//        bundle = new Bundle();
//
//
//        if (getIntent().getIntExtra(GOTO_CODE, 1001) == 1000) {
//            publishTitle.setText("编辑合作信息");
//            activityPublishSendtv.setText("确定保存");
//            id = getIntent().getIntExtra(RESOURCE_ID, 0);
//            buildData(id);
//        }
//
//
//    }
//
//    private void buildData(int id) {
//        RequestManager.getInstance().getRePublishNew(id, new RequestCallback<RePublishBean>() {
//
//            @Override
//            public void requestStart(Call call) {
//                showBookingToast(1);
//            }
//
//            @Override
//            public void onSuccess(RePublishBean rePublishBean) {
//                PublishBean = rePublishBean;
//                provide_attr = rePublishBean.getProvide_attr();
//                offerName = rePublishBean.getProvide_remark();
//                need_attr = rePublishBean.getNeed_attr();
//                needName = rePublishBean.getNeed_remark();
//                coopId = Integer.parseInt(rePublishBean.getCooperation_mode());
//                provide_img = rePublishBean.getImages();
//                ShowUtils.showTextPerfect(edtextActivityPublish,rePublishBean.getTitle());
//                ShowUtils.showTextPerfect(activityPublishCoopContentTv,rePublishBean.getCooperation_mode_cn());
//                coopId = Integer.parseInt(rePublishBean.getCooperation_mode());
//                ShowUtils.showTextPerfect(supplyEdActivityPublish,offerName);
//                ShowUtils.showTextPerfect(msupplydetailEdActivityPublish,rePublishBean.getProvide_describe());
//                ShowUtils.showTextPerfect(demandxEdActivityPublish,needName);
//                ShowUtils.showTextPerfect(mdemanddetailxEdActivityPublish,rePublishBean.getNeed_describe());
//                ShowUtils.showTextPerfect(activityPublishCoopplaceContentTv,rePublishBean.getCity_name());
//                cityId = rePublishBean.getCity();
////                if (TextUtils.isEmpty(offerName)) {
////                    activityPublishSupplyEd.setVisibility(View.GONE);
////                    ShowUtils.showViewVisible(activityPublishSupplyEd);
////                } else {
////                    activityPublishSupplyEd.setVisibility(View.VISIBLE);
////
////                }
//                if (!TextUtils.isEmpty(provide_attr)) {
//                    String[] pattr = provide_attr.split("_");
//                    if (pattr.length > 0) {
//                        for (int i = 0; i < pattr.length; i++) {
//                            offerselectList.add(Integer.parseInt(pattr[i]));
//                        }
//                    }
//                }
//                if (!TextUtils.isEmpty(need_attr)) {
//                    String[] nattr = need_attr.split("_");
//                    if (nattr.length > 0) {
//                        for (int j = 0; j < nattr.length; j++) {
//                            needselectList.add(Integer.parseInt(nattr[j]));
//                        }
//                    }
//
//                }
//                String reThumb = rePublishBean.getThumb_img();
//                String rePhoto = rePublishBean.getImages();
//                if (!TextUtils.isEmpty(rePhoto)) {
//                    String[] photos = rePhoto.split(",");
//                    for (int i = 0; i < photos.length; i++) {
//                        offerList.add(photos[i]);
//                    }
//                }
//                if (!TextUtils.isEmpty(reThumb)){
//                    String[] thumb = reThumb.split(",");
//                    for (int i = 0; i < thumb.length; i++) {
//                        thumbList.add(thumb[i]);
//                    }
//                }
//                provideEditResouceAdapter.setTHEEDIT("theEdit");
//                provideEditResouceAdapter.setIsAddPic(true);
//                provideEditResouceAdapter.setEditRemove(PublishActivity.this);
////                ReleaseStepHelper.getInstance().setOfferList(reImgList);
//                provideImgList.clear();
//                provideImgList.addAll(offerList);
//                provideEditResouceAdapter.notifyDataSetChanged();
//            }
//
//            @Override
//            public void onFailed(int code, String msg) {
//                ToastUtils.showCentetImgToast(PublishActivity.this, msg);
//            }
//
//            @Override
//            public void requestEnd() {
//                dismissBookingToast();
//            }
//        });
//
//    }
//
//
//    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
//    @OnClick({R.id.bt_close, R.id.coopRl, R.id.supplyRl, R.id.demandxRl, R.id.activity_publish_sendtv, R.id.coopplaceRl,R.id.bsupplydetailRl, R.id.bdemanddetailxRl})
//    public void onViewClicked(View view) {
//        switch (view.getId()) {
//            case R.id.bt_close:
//                finish();
//                break;
//            case R.id.coopRl:
//                //选择合作方式
//                gotoResult(CoopWayActivity.class, COOPWAY_CODE);
//                break;
//            case R.id.supplyRl:
//                intent = new Intent(PublishActivity.this, ResourceTypeActivity.class);
//                Bundle bundle = new Bundle();
//                bundle.putString(ResourceTypeActivity.RESOURCETYPE, "offer");
//                bundle.putIntegerArrayList(ResourceTypeActivity.OFFERSELECTARRAY, offerselectList);
//                intent.putExtras(bundle);
//                startActivityForResult(intent, ResourceTypeActivity.OFFERREQUESTCODE, bundle);
//                break;
//            case R.id.demandxRl:
//                intent = new Intent(PublishActivity.this, ResourceTypeActivity.class);
//                Bundle bundle1 = new Bundle();
//                bundle1.putString(ResourceTypeActivity.RESOURCETYPE, "need");
//                bundle1.putIntegerArrayList(ResourceTypeActivity.NEEDSELECTARRAY, needselectList);
//                intent.putExtras(bundle1);
//                startActivityForResult(intent, ResourceTypeActivity.NEEDREQUESTCODE, bundle1);
//                break;
//            case R.id.activity_publish_sendtv:
//                //确定发布
//                if (TextUtils.isEmpty(edtextActivityPublish.getText().toString().trim())) {
//                    ToastUtils.showCentetImgToast(PublishActivity.this, "请输入标题");
//                    return;
//                }
//                if (TextUtils.isEmpty(activityPublishCoopContentTv.getText().toString().trim())) {
//                    ToastUtils.showCentetImgToast(PublishActivity.this, "请选择合作方式");
//                    return;
//                }
//                if (TextUtils.isEmpty(activityPublishCoopplaceContentTv.getText().toString().trim())) {
//                    ToastUtils.showCentetImgToast(PublishActivity.this, "请选择合作地区");
//                    return;
//                }
//
//                if (TextUtils.isEmpty(supplyEdActivityPublish.getText().toString().trim()) ) {
//                    ToastUtils.showCentetImgToast(PublishActivity.this, "请选择提供资源类型");
//                    return;
//                }
//
//                if (TextUtils.isEmpty(msupplydetailEdActivityPublish.getText().toString().trim())) {
//                        ToastUtils.showCentetImgToast(PublishActivity.this, "请填写提供资源详情");
//                        return;
//                }
//                if (TextUtils.isEmpty(demandxEdActivityPublish.getText().toString().trim())) {
//                    ToastUtils.showCentetImgToast(PublishActivity.this, "请选择需求资源类型");
//                    return;
//                }
//
//                if (TextUtils.isEmpty(mdemanddetailxEdActivityPublish.getText().toString().trim())) {
//                        ToastUtils.showCentetImgToast(PublishActivity.this, "请填写需求资源详情");
//                        return;
//                }
//
//                if (getIntent().getIntExtra(GOTO_CODE, 1001) == 1000) {
//                    showBookingToast(2);
//                    reSendData();
//                } else {
//                    showBookingToast(2);
//                    sendData();
//                }
//
//                break;
//            case R.id.coopplaceRl:
//                intent = new Intent(PublishActivity.this, SelectCityActivity.class);
//                intent.putExtra(SelectCityActivity.FROM_TYPE, 1);
//                startActivityForResult(intent, MainActivity.SELECTED_REQUEST_CODE);
//                break;
//            case R.id.bsupplydetailRl:
//                CompanyEditinfoActivity.startCompanyInfo(PublishActivity.this,"accessCode","offerDetail","offerDetailInfo",msupplydetailEdActivityPublish.getText().toString());
//                break;
//            case R.id.bdemanddetailxRl:
//                CompanyEditinfoActivity.startCompanyInfo(PublishActivity.this,"accessCode","needDetail","needDetailInfo",mdemanddetailxEdActivityPublish.getText().toString());
//                break;
//            default:
//                break;
//        }
//    }
//
//    private void reSendData() {
//        showBookingToast(2);
//        if (ReleaseStepHelper.getInstance().getOfferList().size() > 0) {
//            for (int i = 0; i < ReleaseStepHelper.getInstance().getOfferList().size(); i++) {
//                if (!ReleaseStepHelper.getInstance().getOfferList().get(i).contains("http")) {
//                    reImgList.add(ReleaseStepHelper.getInstance().getOfferList().get(i));
//                }
//            }
//
//            updateImg(reImgList);
//
//        } else {
//            reAnalysis();
//        }
//
//
//    }
//
//    private void reAnalysis() {
//        if (offerList.size() > 0) {
//            for (int i = 0; i < offerList.size(); i++) {
//                if (i == 0) {
//                    provide_img = offerList.get(i);
//                } else {
//                    provide_img = provide_img + "," + offerList.get(i);
//                }
//            }
//        }
//        if (thumbList.size()>0){
//            for (int i = 0; i < thumbList.size(); i++) {
//                if (i == 0) {
//                    thumb_img = thumbList.get(i);
//                } else {
//                    thumb_img = thumb_img + "," + thumbList.get(i);
//                }
//            }
//        }
//        PublishBean.setTitle(edtextActivityPublish.getText().toString().trim());
//        PublishBean.setCooperation_mode(coopId + "");
//        PublishBean.setProvide_attr(provide_attr);
//        PublishBean.setProvide_remark(offerName);
//        PublishBean.setNeed_attr(need_attr);
//        PublishBean.setNeed_remark(needName);
//        PublishBean.setProvide_describe(msupplydetailEdActivityPublish.getText().toString().trim());
//        PublishBean.setNeed_describe(mdemanddetailxEdActivityPublish.getText().toString().trim());
//        PublishBean.setImages(provide_img);
//        PublishBean.setThumb_img(thumb_img);
//        PublishBean.setCity(cityId);
//
//        showBookingToast(2);
//        RequestManager.getInstance().editResourceInfoV4(PublishBean, new ResourceReleaseCallback() {
//                    @Override
//                    public void onSuccess(ResourceReleaseBean bean) {
//                        dismissBookingToast();
//                        if (bean.getIs_verify().equals("0")) {
//                            ToastUtils.showCentetImgToast(PublishActivity.this, "编辑成功");
//                            EditResouceInfoHelper.getInstance().clearItem(id);
//                            finish();
//                        } else {
//                            Intent intent = new Intent(PublishActivity.this, ResourceReleaseSuccessActivity.class);
//                            intent.putExtra("is_verify", bean.getIs_verify());
//                            intent.putExtra("from", "repush");
//                            intent.putExtra("resources_id", id);
//                            intent.putExtra("query_id",provide_attr);
//                            startActivity(intent);
//                            finish();
//                        }
//                    }
//
//                    @Override
//                    public void onFailed(int code, String msg) {
//                        ToastUtils.showCentetImgToast(PublishActivity.this, msg);
//                    }
//                }
//
//        );
//    }
//
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == COOPWAY_CODE && resultCode == CoopWayActivity.COOPWAY_RESULT) {
//            String name = data.getExtras().getString(CoopWayActivity.NAME);
//            activityPublishCoopContentTv.setText(name);
//            coopId = data.getExtras().getInt("id");
//        }
////        if (){
////            refreshprovidePic(data.getStringArrayListExtra(TakePhotoActivity.PHOTO_LIST));
////
////        }
//        if (resultCode == RESULT_OK && requestCode == EditResouceAdapter.TYPE_OFFER) {
//            refreshprovidePic(data.getStringArrayListExtra(TakePhotoActivity.PHOTO_LIST));
//        }
////        if (resultCode == ResourceSelectActivity.RESULT_OFFER && requestCode == RSA_OFFER_CODE) {
////            name = data.getExtras().getString("provide");
////            provide_top = data.getExtras().getInt("provide_top");
////            provide_attr = data.getExtras().getString("provide_attr");
////            supplyEdActivityPublish.setText(name);
////            if (!TextUtils.isEmpty(supplyEdActivityPublish.getText())) {
////                activityPublishSupplyEd.setVisibility(View.VISIBLE);
////            } else {
////                activityPublishSupplyEd.setVisibility(View.GONE);
////
////            }
////
////        }
////        if (resultCode == ResourceSelectActivity.RESULT_NEED && requestCode == RSA_NEED_CODE) {
////            name1 = data.getExtras().getString("need");
////            need_top = data.getExtras().getInt("need_top");
////            need_attr = data.getExtras().getString("need_attr");
////            demandxEdActivityPublish.setText(name1);
////            if (!TextUtils.isEmpty(demandxEdActivityPublish.getText())) {
////                activityPublishDemandxEd.setVisibility(View.VISIBLE);
////            } else {
////                activityPublishDemandxEd.setVisibility(View.GONE);
////
////            }
////        }
//        if (resultCode == MainActivity.SELECTED_REQUEST_CODE) {
//            cityId = data.getIntExtra(SelectCityActivity.CITY_ID, -1);
//            String cityName = data.getStringExtra(SelectCityActivity.CITY_NAME);
//            if (cityId != -1) {
//                activityPublishCoopplaceContentTv.setText(cityName);
//            }
//        }
//        if (requestCode == ResourceTypeActivity.OFFERREQUESTCODE && resultCode == ResourceTypeActivity.OFFERRESULTCODE) {
//            offerselectList = data.getExtras().getIntegerArrayList(ResourceTypeActivity.OFFERSELECTARRAY);
//            offerName = data.getExtras().getString(ResourceTypeActivity.RESOURCENAME);
//            supplyEdActivityPublish.setText(offerName);
//            provide_attr = data.getExtras().getString(ResourceTypeActivity.RESOURCEATTR);
//        }
//        if (requestCode == ResourceTypeActivity.NEEDREQUESTCODE && resultCode == ResourceTypeActivity.NEEDRESULTCODE) {
//            needselectList = data.getExtras().getIntegerArrayList(ResourceTypeActivity.NEEDSELECTARRAY);
//            needName = data.getExtras().getString(ResourceTypeActivity.RESOURCENAME);
//            demandxEdActivityPublish.setText(needName);
//            need_attr = data.getExtras().getString(ResourceTypeActivity.RESOURCEATTR);
//        }
//        if (requestCode == CompanyEditinfoActivity.THEREQUESTCODE){
//            if (data!=null){
//                Bundle bundle = data.getExtras();
//                switch (resultCode){
//                    case CompanyEditinfoActivity.NEEDRESULT:
//                       String needDetail = bundle.getString("mContent");
//                       mdemanddetailxEdActivityPublish.setText(needDetail);
//                        break;
//                    case CompanyEditinfoActivity.OFFERRESULT:
//                        String offerDetail = bundle.getString("mContent");
//                        msupplydetailEdActivityPublish.setText(offerDetail);
//                        break;
//                    default:
//                            break;
//                }
//            }
//        }
//
//
//    }
//
//    public void refreshprovidePic(ArrayList<String> list) {
////        if (list.size() < 8) {
////            list.add(EditResouceAdapter.ADD);
////        }
//        ReleaseStepHelper.getInstance().setOfferList(list);
//        provideImgList.clear();
//        provideImgList.addAll(list);
//        provideEditResouceAdapter.notifyDataSetChanged();
//    }
//
//    private void sendData() {
//
//        if (provideImgList.size() > 0) {
//            updateImg(ReleaseStepHelper.getInstance().getOfferList());
//        } else {
//            analysis();
//        }
//
//    }
//
//    private void updateImg(ArrayList<String> updateList) {
//        for (String item : updateList) {
//            Luban.with(PublishActivity.this).load(item).ignoreBy(300).setTargetDir(item).filter(new CompressionPredicate() {
//                @Override
//                public boolean apply(String path) {
//                    RequestManager.getInstance().updateBase64(path, new RequestCallback<UploadBean>() {
//                        @Override
//                        public void requestStart(Call call) {
//
//                        }
//
//                        @Override
//                        public void onSuccess(UploadBean uploadBean) {
//                            offerList.add(uploadBean.getPath());
//                            thumbList.add(uploadBean.getThumb_img());
//                            if (getIntent().getIntExtra(GOTO_CODE, 1001) == 1000) {
//                                reAnalysis();
//                            } else {
//                                analysis();
//                            }
//
//                        }
//
//                        @Override
//                        public void onFailed(int code, String msg) {
//
//                        }
//
//                        @Override
//                        public void requestEnd() {
//
//                        }
//                    });
//
//                    return false;
//                }
//            }).launch();
//
//        }
//
//    }
//
//    private void analysis() {
//        if (isUpdateSuccess()) {
//            if (offerList.size() > 0) {
//                for (int i = 0; i < offerList.size(); i++) {
//                    if (i == 0) {
//                        provide_img = offerList.get(i);
//                    } else {
//                        provide_img = provide_img + "," + offerList.get(i);
//                    }
//                }
//            }
//            if (thumbList.size()>0){
//                for (int i = 0; i < thumbList.size(); i++) {
//                    if (i == 0) {
//                        thumb_img = thumbList.get(i);
//                    } else {
//                        thumb_img = thumb_img + "," + thumbList.get(i);
//                    }
//                }
//            }
//
//            RequestManager.getInstance().sendResourceV5(edtextActivityPublish.getText().toString().trim(),
//                    provide_attr,
//                    offerName, need_attr,
//                    needName, coopId,
//                    msupplydetailEdActivityPublish.getText().toString().trim(), mdemanddetailxEdActivityPublish.getText().toString().trim(),
//                    provide_img,thumb_img, cityId, new ResourceReleaseCallback() {
//                        @Override
//                        public void onSuccess(ResourceReleaseBean bean) {
//                            dismissBookingToast();
//                            Intent intent = new Intent(PublishActivity.this, ResourceReleaseSuccessActivity.class);
//                            intent.putExtra("shareUrl", bean.getShare_url());
//                            intent.putExtra("offer", offerName);
//                            intent.putExtra("is_verify", bean.getIs_verify());
//                            intent.putExtra("resources_id", bean.getResources_id());
//                            intent.putExtra("from", "push");
//                            intent.putExtra("wechatUrl", bean.getWechat_url());
//                            intent.putExtra("query_id",provide_attr);
//                            startActivityForResult(intent, MainActivity.RELEASE_SUCCESS);
//                            ReleaseStepHelper.getInstance().clearDate();
//                            finish();
//
//                        }
//
//                        @Override
//                        public void onFailed(int code, String msg) {
//                            dismissBookingToast();
//                            ToastUtils.showCentetImgToast(PublishActivity.this, msg);
//                        }
//                    });
//        }
//
//
//    }
//
//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        ReleaseStepHelper.getInstance().clearDate();
//        EditResouceInfoHelper.getInstance().clearItem(-1);
//
//    }
//
//    public boolean isUpdateSuccess() {
//        if (offerList.size() == ReleaseStepHelper.getInstance().getOfferList().size()) {
//            return true;
//        } else {
//            return false;
//        }
//
//    }
//
//    @Override
//    public void editRemove(int postion) {
//        offerList.remove(postion);
//    }
//
//}
