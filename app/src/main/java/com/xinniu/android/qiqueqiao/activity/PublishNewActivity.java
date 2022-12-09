package com.xinniu.android.qiqueqiao.activity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
//import android.support.annotation.RequiresApi;
//import android.support.v7.app.AppCompatDialog;
//import android.support.v7.widget.LinearLayoutManager;
//import androidx.recyclerview.widget.RecyclerView;
import android.text.TextUtils;
import android.text.method.ScrollingMovementMethod;
import android.util.ArraySet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.umeng.analytics.MobclickAgent;
import com.xinniu.android.qiqueqiao.EditResouceInfoHelper;
import com.xinniu.android.qiqueqiao.MainActivity;
import com.xinniu.android.qiqueqiao.R;
import com.xinniu.android.qiqueqiao.ReleaseStepHelper;
import com.xinniu.android.qiqueqiao.adapter.EditResouceAdapter;
import com.xinniu.android.qiqueqiao.adapter.PublicNeedTypeAdapter;
import com.xinniu.android.qiqueqiao.adapter.PublicOfferTypeAdapter;
import com.xinniu.android.qiqueqiao.base.BaseActivity;
import com.xinniu.android.qiqueqiao.bean.CombingCardBean;
import com.xinniu.android.qiqueqiao.bean.GetEditResourceInfoV2Bean;
import com.xinniu.android.qiqueqiao.bean.GetReleaseTemplateNewBean;
import com.xinniu.android.qiqueqiao.bean.PublicSelectTagBean;
import com.xinniu.android.qiqueqiao.bean.ReleaseTemplateBean;
import com.xinniu.android.qiqueqiao.bean.ResourceReleaseBean;
import com.xinniu.android.qiqueqiao.bean.UploadBean;
import com.xinniu.android.qiqueqiao.customs.label.FlowLayoutManager;
import com.xinniu.android.qiqueqiao.customs.qldialog.ReleaseTemplateDialog;
import com.xinniu.android.qiqueqiao.dialog.AlertDialogUtils;
import com.xinniu.android.qiqueqiao.request.RequestManager;
import com.xinniu.android.qiqueqiao.request.RetrofitHelper;
import com.xinniu.android.qiqueqiao.request.callback.GetCombingCardCallback;
import com.xinniu.android.qiqueqiao.request.callback.GetEditResourceV2Callback;
import com.xinniu.android.qiqueqiao.request.callback.GetReleaseTemplateCallback;
import com.xinniu.android.qiqueqiao.request.callback.GetTemplateCallback;
import com.xinniu.android.qiqueqiao.request.callback.RequestCallback;
import com.xinniu.android.qiqueqiao.request.callback.ResourceReleaseCallback;
import com.xinniu.android.qiqueqiao.utils.ComUtils;
import com.xinniu.android.qiqueqiao.utils.FullyGridLayoutManager;
import com.xinniu.android.qiqueqiao.utils.ShowUtils;
import com.xinniu.android.qiqueqiao.utils.ToastUtils;

import java.io.File;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import butterknife.BindView;
import butterknife.OnClick;
import retrofit2.Call;
import top.zibin.luban.CompressionPredicate;
import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;
import top.zibin.luban.OnRenameListener;

/**
 * 发布资源界面
 * Created by yuchance on 2018/12/12.
 */

public class PublishNewActivity extends BaseActivity implements EditResouceAdapter.EditRemove {
    @BindView(R.id.mpublish_titletv)
    TextView mpublishTitletv;
    @BindView(R.id.mcoopplace_publishtv)
    TextView mcoopplacePublishtv;
    @BindView(R.id.mcoop_titletv)
    EditText mcoopTitletv;
    @BindView(R.id.mpublish_offeret)
    TextView mpublishOfferet;
    @BindView(R.id.mpublish_needet)
    TextView mpublishNeedet;
    @BindView(R.id.activity_publish_recycler)
    RecyclerView activityPublishRecycler;
    @BindView(R.id.bgotopublish)
    TextView bgotopublish;
    //    @BindView(R.id.yofferTypetv)
//    TextView yofferTypetv;
    @BindView(R.id.moffer_recycler)
    RecyclerView mofferRecycler;
    @BindView(R.id.yofferDetailtv)
    TextView yofferDetailtv;
    @BindView(R.id.yofferEtRl)
    RelativeLayout yofferEtRl;
    //    @BindView(R.id.yNeedTypeTv)
//    TextView yNeedTypeTv;
    @BindView(R.id.mneed_recycler)
    RecyclerView mneedRecycler;
    @BindView(R.id.yNeedDetailtv)
    TextView yNeedDetailtv;
    @BindView(R.id.yneedEtRl)
    RelativeLayout yneedEtRl;
    @BindView(R.id.disturb_switch)
    CheckBox disturbSwitch;
    @BindView(R.id.secured_transactions_switch)
    CheckBox securedTransactionsSwitch;
    @BindView(R.id.rlayout_secured_transactions)
    RelativeLayout rlayoutSecuredTransactions;
    @BindView(R.id.secured_transactions_view)
    View securedTransactionsView;
    @BindView(R.id.tv_support)
    TextView tvSupport;
    @BindView(R.id.tv_support_no)
    TextView tvSupportNo;
    @BindView(R.id.view)
    View view;
    @BindView(R.id.view_01)
    View view01;
    private String title;
    private String title1 = "";
    private int typeId;
    private static String GOTO_CODE = "gotoCode";
    public static int MYPUSHCODE = 101;


    //提供和需求标签
    private List<GetReleaseTemplateNewBean.NeedCategoryBean.SystemCategoryBeanX> needList = new ArrayList<>();
    private PublicNeedTypeAdapter needAdapter;

    private List<GetReleaseTemplateNewBean.ProvideCategoryBean.SystemCategoryBean> offerList = new ArrayList<>();
    private PublicOfferTypeAdapter offerAdapter;


    private EditResouceAdapter provideEditResouceAdapter;
    private ArrayList<String> provideImgList = new ArrayList<>();
    private int cityId;
    private List<String> imgList = new ArrayList<>();
    private List<String> thumbList = new ArrayList<>();
    private int goToCode;

    private ArrayList<String> reImgList = new ArrayList<>();
    private ArrayList<String> reImgList1 = new ArrayList<>();
    private int resourceId;
    private String cityName = "";
    private InputMethodManager imm;
    private AppCompatDialog templateDialog;
    private ReleaseTemplateBean releaseTemplateBean;//发布模板
    private int is_combing = 0;//是否使用梳理卡
    private int mNum = 0;
    private int is_transaction = -1;//是否使用担保交易

    public static void start(Activity context, String title, int typeId, int gotoCode) {
        Intent intent = new Intent(context, PublishNewActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("title", title);
        bundle.putInt("typeId", typeId);
        bundle.putInt(GOTO_CODE, gotoCode);
        intent.putExtras(bundle);
        context.startActivityForResult(intent, MainActivity.RELEASE_SUCCESS, bundle);
    }

    public static void start(Activity context, int resourceId, String title, int typeId, int gotoCode) {
        Intent intent = new Intent(context, PublishNewActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt("resourceId", resourceId);
        bundle.putString("title", title);
        bundle.putInt("typeId", typeId);
        bundle.putInt(GOTO_CODE, gotoCode);
        intent.putExtras(bundle);
        context.startActivityForResult(intent, MainActivity.RELEASE_SUCCESS, bundle);
    }


    @Override
    public int getLayoutId() {
        return R.layout.activity_publishnew;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        topStatusBar(true);
        ComUtils.addActivity(PublishNewActivity.this);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            title = bundle.getString("title");
            title1 = title;
            typeId = bundle.getInt("typeId");
            mpublishTitletv.setText("发布" + title);
            if (bundle.getInt(GOTO_CODE, 1001) == 1000) {
                goToCode = bundle.getInt(GOTO_CODE, 1001);
                mpublishTitletv.setText("编辑" + title);
                bgotopublish.setText("确定保存");
            }
        }
        if (typeId == 8 || typeId == 7) {
            //效果渠道
            rlayoutSecuredTransactions.setVisibility(View.VISIBLE);
            securedTransactionsView.setVisibility(View.VISIBLE);
        } else {
            rlayoutSecuredTransactions.setVisibility(View.GONE);
            securedTransactionsView.setVisibility(View.GONE);
        }
        mpublishOfferet.setMovementMethod(ScrollingMovementMethod.getInstance());
        mpublishNeedet.setMovementMethod(ScrollingMovementMethod.getInstance());
        LinearLayoutManager manager1 = new LinearLayoutManager(PublishNewActivity.this, LinearLayoutManager.VERTICAL, false);
        mneedRecycler.setLayoutManager(manager1);
        needAdapter = new PublicNeedTypeAdapter(PublishNewActivity.this, R.layout.item_publich_new, needList);
        mneedRecycler.setAdapter(needAdapter);
        mneedRecycler.setNestedScrollingEnabled(false);
        needAdapter.setSetAddOnClick(new PublicNeedTypeAdapter.setAddOnClick() {
            @Override
            public void setAddOnClick(int type) {
                if (type == 2) {
                    AlertDialogUtils.AlertDialog(PublishNewActivity.this, "最多选择3个标签", "知道了", "", new AlertDialogUtils.setOnClick() {
                        @Override
                        public void setLeftOnClick(DialogInterface dialog) {
                            dialog.dismiss();
                        }

                        @Override
                        public void setRightOnClick(DialogInterface dialog) {

                        }
                    });
                }

            }
        });

        LinearLayoutManager manager2 = new LinearLayoutManager(PublishNewActivity.this, LinearLayoutManager.VERTICAL, false);
        mofferRecycler.setLayoutManager(manager2);
        offerAdapter = new PublicOfferTypeAdapter(PublishNewActivity.this, R.layout.item_publich_new, offerList);
        mofferRecycler.setAdapter(offerAdapter);
        mofferRecycler.setNestedScrollingEnabled(false);
        offerAdapter.setSetAddOnClick(new PublicOfferTypeAdapter.setAddOnClick() {
            @Override
            public void setAddOnClick(int type) {
                if (type == 2) {
                    AlertDialogUtils.AlertDialog(PublishNewActivity.this, "最多选择3个标签", "知道了", "", new AlertDialogUtils.setOnClick() {
                        @Override
                        public void setLeftOnClick(DialogInterface dialog) {
                            dialog.dismiss();
                        }

                        @Override
                        public void setRightOnClick(DialogInterface dialog) {

                        }
                    });
                }

            }
        });


        provideEditResouceAdapter = new EditResouceAdapter(mContext, provideImgList, EditResouceAdapter.TYPE_OFFER, 8 - provideImgList.size());

        FullyGridLayoutManager manager = new FullyGridLayoutManager(this, 4);

        activityPublishRecycler.setLayoutManager(manager);

        activityPublishRecycler.setAdapter(provideEditResouceAdapter);
        buildData();
        getCombingCardNum(0);

        resourceId = bundle.getInt("resourceId");
        mcoopTitletv.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {


                return (event.getKeyCode() == KeyEvent.KEYCODE_ENTER);
            }
        });

        mpublishOfferet.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    // 通知ScrollView控件不要干扰
                    mpublishOfferet.setBackgroundColor(getResources().getColor(R.color.white));
                    view.getParent().requestDisallowInterceptTouchEvent(true);
                }
                if (motionEvent.getAction() == MotionEvent.ACTION_MOVE) {
                    // 通知ScrollView控件不要干扰
                    view.getParent().requestDisallowInterceptTouchEvent(true);
                }
                if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    mpublishOfferet.setBackgroundColor(getResources().getColor(R.color.bg_gray_F8));
                    view.getParent().requestDisallowInterceptTouchEvent(false);
                }
                return false;
            }
        });

        mpublishNeedet.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    // 通知ScrollView控件不要干扰
                    view.getParent().requestDisallowInterceptTouchEvent(true);
                    mpublishNeedet.setBackgroundColor(getResources().getColor(R.color.white));

                }
                if (motionEvent.getAction() == MotionEvent.ACTION_MOVE) {
                    // 通知ScrollView控件不要干扰
                    view.getParent().requestDisallowInterceptTouchEvent(true);
                }
                if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    mpublishNeedet.setBackgroundColor(getResources().getColor(R.color.bg_gray_F8));

                    view.getParent().requestDisallowInterceptTouchEvent(false);
                }
                return false;
            }
        });
    }


    private void reBuildData(int resourceId) {
        showBookingToast(1);
        RequestManager.getInstance().getEditResourceV2(resourceId, new GetEditResourceV2Callback() {
            @Override
            public void onSuccess(GetEditResourceInfoV2Bean bean) {
                dismissBookingToast();
                is_combing = bean.getIs_combing();

                if (is_combing == 1) {
                    //编辑状态不能关闭，只能开启
                    disturbSwitch.setChecked(true);
                    disturbSwitch.setEnabled(false);
                    disturbSwitch.setAlpha((float) 0.5);
                } else {
                    //编辑状态不能关闭，只能开启
                    disturbSwitch.setChecked(false);
                    disturbSwitch.setEnabled(true);

                }
                is_transaction = bean.getIs_transaction();
                if (is_transaction == 1) {
                    tvSupport.setTextColor(getResources().getColor(R.color.white));
                    tvSupport.setBackgroundResource(R.drawable.bg_support_blue);
                    tvSupportNo.setTextColor(getResources().getColor(R.color._777));
                    tvSupportNo.setBackgroundResource(R.drawable.bg_support_gray);

                    // securedTransactionsSwitch.setChecked(true);
                } else if (is_transaction == 0) {
                    tvSupportNo.setTextColor(getResources().getColor(R.color.white));
                    tvSupportNo.setBackgroundResource(R.drawable.bg_support_blue);
                    tvSupport.setTextColor(getResources().getColor(R.color._777));
                    tvSupport.setBackgroundResource(R.drawable.bg_support_gray);
                    //   securedTransactionsSwitch.setChecked(false);

                } else {
                    tvSupportNo.setTextColor(getResources().getColor(R.color._777));
                    tvSupportNo.setBackgroundResource(R.drawable.bg_support_gray);
                    tvSupport.setTextColor(getResources().getColor(R.color._777));
                    tvSupport.setBackgroundResource(R.drawable.bg_support_gray);
                }
                mcoopTitletv.setText(bean.getTitle());
                //判断分类id，在添加标签
                for (int j = 0; j < bean.getProvide_category().size(); j++) {
                    for (int i = 0; i < offerList.size(); i++) {
                        if (offerList.get(i).getId() == bean.getProvide_category().get(j).getId()) {
                            for (int m = 0; m < bean.getProvide_category().get(j).getList().size(); m++) {
                                for (int n = 0; n < offerList.get(i).getList().size(); n++) {
                                    if (offerList.get(i).getList().get(n).getId() == bean.getProvide_category().get(j).getList().get(m).getId()) {

                                        offerList.get(i).getList().get(n).setCheck(true);

                                    }
                                }
                            }
                        }
                    }
                }

                for (int j = 0; j < bean.getNeed_category().size(); j++) {
                    for (int i = 0; i < needList.size(); i++) {
                        if (needList.get(i).getId() == bean.getNeed_category().get(j).getId()) {
                            for (int m = 0; m < bean.getNeed_category().get(j).getList().size(); m++) {
                                for (int n = 0; n < needList.get(i).getList().size(); n++) {
                                    if (needList.get(i).getList().get(n).getId() == bean.getNeed_category().get(j).getList().get(m).getId()) {

                                        needList.get(i).getList().get(n).setCheck(true);

                                    }
                                }
                            }
                        }
                    }
                }

                needAdapter.notifyDataSetChanged();
                offerAdapter.notifyDataSetChanged();
                String reThumb = bean.getThumb_img();
                String rePhoto = bean.getImages();
                if (!TextUtils.isEmpty(rePhoto)) {
                    String[] photos = rePhoto.split(",");
                    for (int i = 0; i < photos.length; i++) {
                        imgList.add(photos[i]);
                        reImgList1.add(photos[i]);
                    }
                }
                if (!TextUtils.isEmpty(reThumb)) {
                    String[] thumb = reThumb.split(",");
                    for (int i = 0; i < thumb.length; i++) {
                        thumbList.add(thumb[i]);
                    }
                }


                mpublishNeedet.setText(bean.getNeed_describe());
                mpublishOfferet.setText(bean.getProvide_describe());
                mcoopplacePublishtv.setText(bean.getCity_name());
                cityId = bean.getCity();


                provideEditResouceAdapter.setTHEEDIT("theEdit");
                provideEditResouceAdapter.setIsAddPic(true);
                provideEditResouceAdapter.setEditRemove(PublishNewActivity.this);
                ReleaseStepHelper.getInstance().setOfferList(reImgList1);
                provideImgList.clear();
                provideImgList.addAll(imgList);
                provideEditResouceAdapter.notifyDataSetChanged();


            }

            @Override
            public void onFailed(int code, String msg) {
                dismissBookingToast();
                ToastUtils.showCentetToast(PublishNewActivity.this, msg);
            }
        });

    }

    /**
     * 获取模板资源
     */
    private void buildData() {
//        showBookingToast(1);
        RequestManager.getInstance().getReleaseTemplate(typeId, new GetReleaseTemplateCallback() {
            @Override
            public void onSuccess(GetReleaseTemplateNewBean bean) {
//                dismissBookingToast();
                needList.clear();
                offerList.clear();
                if (bean.getNeed_category() != null) {
                    if (!TextUtils.isEmpty(bean.getNeed_description_title())) {
                        yNeedDetailtv.setText(bean.getNeed_description_title());
                    } else {
                        yNeedDetailtv.setText("需求资源说明");
                    }
                    if (bean.getNeed_category().getSystem_category().size() > 0) {
                        ShowUtils.showViewVisible(yNeedDetailtv, View.VISIBLE);
                        ShowUtils.showViewVisible(yneedEtRl, View.VISIBLE);
                        ShowUtils.showViewVisible(mneedRecycler, View.VISIBLE);
                        ShowUtils.showTextPerfect(mpublishNeedet, bean.getNeed_description());
                        needList.addAll(bean.getNeed_category().getSystem_category());

                        needAdapter.notifyDataSetChanged();
                    } else {
                        ShowUtils.showViewVisible(yNeedDetailtv, View.GONE);
                        ShowUtils.showViewVisible(yneedEtRl, View.GONE);
                        ShowUtils.showViewVisible(mneedRecycler, View.GONE);
                        ShowUtils.showViewVisible(view01, View.GONE);
                    }

                } else {
                    ShowUtils.showViewVisible(yNeedDetailtv, View.GONE);
                    ShowUtils.showViewVisible(yneedEtRl, View.GONE);
                    ShowUtils.showViewVisible(mneedRecycler, View.GONE);
                    ShowUtils.showViewVisible(view01, View.GONE);
                }
                if (bean.getProvide_category() != null) {
                    if (!TextUtils.isEmpty(bean.getProvide_description_title())) {
                        yofferDetailtv.setText(bean.getProvide_description_title());
                    } else {
                        yofferDetailtv.setText("提供资源说明");
                    }
                    if (bean.getProvide_category().getSystem_category().size() > 0) {
                        ShowUtils.showViewVisible(yofferDetailtv, View.VISIBLE);
                        ShowUtils.showViewVisible(yofferEtRl, View.VISIBLE);
                        ShowUtils.showViewVisible(mofferRecycler, View.VISIBLE);
                        ShowUtils.showTextPerfect(mpublishOfferet, bean.getProvide_description());
                        offerList.addAll(bean.getProvide_category().getSystem_category());
                        offerAdapter.notifyDataSetChanged();
                    } else {
                        ShowUtils.showViewVisible(yofferDetailtv, View.GONE);
                        ShowUtils.showViewVisible(yofferEtRl, View.GONE);
                        ShowUtils.showViewVisible(mofferRecycler, View.GONE);
                        ShowUtils.showViewVisible(view, View.GONE);
                    }
                } else {
                    ShowUtils.showViewVisible(yofferDetailtv, View.GONE);
                    ShowUtils.showViewVisible(yofferEtRl, View.GONE);
                    ShowUtils.showViewVisible(mofferRecycler, View.GONE);
                    ShowUtils.showViewVisible(view, View.GONE);
                }


                if (goToCode == 1000) {
                    reBuildData(resourceId);
                }

            }

            @Override
            public void onFailed(int code, String msg) {
//                dismissBookingToast();
                ToastUtils.showCentetToast(PublishNewActivity.this, msg);
            }
        });
    }

    @OnClick({R.id.rlayout_rule, R.id.bt_finish, R.id.bgotopublish, R.id.bcoopplaceRl, R.id.tv_release_template, R.id.mpublish_offeret, R.id.mpublish_needet, R.id.disturb_switch, R.id.tv_support, R.id.tv_support_no, R.id.rlayout_secured_transactions})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rlayout_rule:
                //审核规范
                ApproveCardActivity.start(PublishNewActivity.this, "rule");
                break;
            case R.id.bt_finish:
                finish();
                break;
            case R.id.bgotopublish:

                String title = mcoopTitletv.getText().toString();
                StringBuffer offerAttr = new StringBuffer();
                StringBuffer needAttr = new StringBuffer();
                StringBuffer offerRemark = new StringBuffer();
                StringBuffer needRemark = new StringBuffer();
                String provideDescribe = mpublishOfferet.getText().toString();
                String needDescribe = mpublishNeedet.getText().toString();
                if (TextUtils.isEmpty(title)) {
                    showDialogView("请输入合作标题");
                    MobclickAgent.onEvent(mContext, "publish_clickPub", "合作标题为空");//统计点击次数
                    return;
                }

                List<PublicSelectTagBean> offers = new ArrayList<>();
                for (int i = 0; i < offerList.size(); i++) {
                    PublicSelectTagBean m = new PublicSelectTagBean();
                    m.setName(offerList.get(i).getTitle());
                    List<Integer> n = new ArrayList<>();
                    for (int j = 0; j < offerList.get(i).getList().size(); j++) {
                        if (offerList.get(i).getList().get(j).isCheck()) {
                            n.add(offerList.get(i).getList().get(j).getId());
                            offerAttr.append(offerList.get(i).getList().get(j).getId());
                            offerAttr.append("_");
                            offerRemark.append(offerList.get(i).getList().get(j).getName());
                            offerRemark.append(",");
                        }
                    }
                    m.setmData(n);
                    offers.add(m);
                }
                if (offerAttr.length() > 1) {
                    offerAttr.deleteCharAt(offerAttr.length() - 1);
                }
                if (offerRemark.length() > 1) {
                    offerRemark.deleteCharAt(offerRemark.length() - 1);
                }
                List<PublicSelectTagBean> needs = new ArrayList<>();
                for (int i = 0; i < needList.size(); i++) {
                    PublicSelectTagBean m = new PublicSelectTagBean();
                    m.setName(needList.get(i).getTitle());
                    List<Integer> n = new ArrayList<>();
                    for (int j = 0; j < needList.get(i).getList().size(); j++) {
                        if (needList.get(i).getList().get(j).isCheck()) {
                            n.add(needList.get(i).getList().get(j).getId());
                            needAttr.append(needList.get(i).getList().get(j).getId());
                            needAttr.append("_");
                            needRemark.append(needList.get(i).getList().get(j).getName());
                            needRemark.append(",");
                        }
                    }
                    m.setmData(n);
                    needs.add(m);
                }
                if (needAttr.length() > 1) {
                    needAttr.deleteCharAt(needAttr.length() - 1);
                }
                if (needRemark.length() > 1) {
                    needRemark.deleteCharAt(needRemark.length() - 1);
                }

                if (mofferRecycler.getVisibility() == View.VISIBLE) {
//                    if (offerAttr.length() == 0) {
//                        showDialogView("请选择提供资源类型");
//                        MobclickAgent.onEvent(mContext, "publish_clickPub", "提供资源类型为空");//统计点击次数
//                        return;
//                    }
                    for (int i = 0; i < offers.size(); i++) {
                        if (offers.get(i).getmData().size() == 0) {
                            showDialogView("请选择" + offers.get(i).getName());
                            return;
                        }
                    }


                    if (TextUtils.isEmpty(mpublishOfferet.getText().toString())) {
                        showDialogView("请输入提供资源说明");
                        MobclickAgent.onEvent(mContext, "publish_clickPub", "提供资源说明为空");//统计点击次数
                        return;
                    }


                }
                if (mneedRecycler.getVisibility() == View.VISIBLE) {
//                    if (needAttr.length() == 0) {
//                        showDialogView("请选择需求资源类型");
//                        MobclickAgent.onEvent(mContext, "publish_clickPub", "需求资源类型为空");//统计点击次数
//                        return;
//                    }

                    for (int i = 0; i < needs.size(); i++) {
                        if (needs.get(i).getmData().size() == 0) {
                            showDialogView("请选择" + needs.get(i).getName());
                            return;
                        }
                    }
                    if (TextUtils.isEmpty(mpublishNeedet.getText().toString())) {
                        showDialogView("请输入合作需求说明");
                        MobclickAgent.onEvent(mContext, "publish_clickPub", "合作需求说明为空");//统计点击次数
                        return;
                    }
                }

                if (cityId == 0) {
                    if (!cityName.equals("全国")) {
                        showDialogView("请选择合作地区");
                        MobclickAgent.onEvent(mContext, "publish_clickPub", "合作地区为空");//统计点击次数
                        return;
                    }
                }

                if (typeId == 8 || typeId == 7) {
                    if (is_transaction != 1 && is_transaction != 0) {
                        showDialogView("请选择是否支持企鹊桥担保交易");
                        return;
                    }
                }
                if (goToCode == 1000) {

                    reSendData(title, typeId, offerAttr.toString(), offerRemark.toString(), needAttr.toString(), needRemark.toString(), provideDescribe, needDescribe, cityId);
                } else {
                    if (provideImgList.size() > 0) {
                        showBookingToast(2);
                        updateImg(ReleaseStepHelper.getInstance().getOfferList(), title, typeId, offerAttr.toString(), offerRemark.toString(), needAttr.toString(), needRemark.toString(), provideDescribe, needDescribe, cityId);
                    } else {
                        showBookingToast(2);
                        goToSend(title, typeId, offerAttr.toString(), offerRemark.toString(), needAttr.toString(), needRemark.toString(), provideDescribe, needDescribe, cityId);

                    }
                }


                break;
            case R.id.bcoopplaceRl:
                Intent intent = new Intent(PublishNewActivity.this, SelectCityActivity.class);
                intent.putExtra(SelectCityActivity.FROM_TYPE, 1);
                startActivityForResult(intent, MainActivity.SELECTED_REQUEST_CODE);
                break;
            case R.id.tv_release_template:
                if (releaseTemplateBean != null) {
                    if (templateDialog != null && templateDialog.isShowing()) {

                    } else {
                        templateDialog = new ReleaseTemplateDialog.Builder(mContext)
                                .setReleaseTemplateBean(releaseTemplateBean).build();
                        templateDialog.show();
                    }
                } else {
                    //模板
                    showBookingToast(2);
                    getTemplate();
                }
                break;
            case R.id.mpublish_offeret:
                InputResourceDetailActivity.startSimpleEidtForResult(this, 30, yofferDetailtv.getText().toString(),
                        mpublishOfferet.getText().toString(), 1);
                break;
            case R.id.mpublish_needet:
                InputResourceDetailActivity.startSimpleEidtForResult(this, 31, yNeedDetailtv.getText().toString(),
                        mpublishNeedet.getText().toString(), 2);
                break;
            case R.id.disturb_switch:
                //是否使用梳理卡
                if (is_combing == 1) {
                    is_combing = 0;
                } else {
                    if (mNum == 0) {
                        //去购买梳理卡
                        CardingCardActivity.start(PublishNewActivity.this, MYPUSHCODE);
                        disturbSwitch.setChecked(false);
                    } else {
                        is_combing = 1;
                    }

                }

                break;
            case R.id.tv_support:
                //开通担保交易

                is_transaction = 1;
                tvSupport.setTextColor(getResources().getColor(R.color.white));
                tvSupport.setBackgroundResource(R.drawable.bg_support_blue);
                tvSupportNo.setTextColor(getResources().getColor(R.color._777));
                tvSupportNo.setBackgroundResource(R.drawable.bg_support_gray);


                break;
            case R.id.tv_support_no:
                //关闭担保交易

                is_transaction = 0;
                tvSupportNo.setTextColor(getResources().getColor(R.color.white));
                tvSupportNo.setBackgroundResource(R.drawable.bg_support_blue);
                tvSupport.setTextColor(getResources().getColor(R.color._777));
                tvSupport.setBackgroundResource(R.drawable.bg_support_gray);


                break;

            case R.id.rlayout_secured_transactions:
                //担保交易点击跳转
                String url = RetrofitHelper.API_URL + "resource/pages/guarTran/guarTran.html?needLogin=1";
                Intent intent1 = new Intent(PublishNewActivity.this, ApproveCardActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("theUrl", url);
                bundle.putString("thetitle", "担保交易");
                bundle.putString("webType", "Event");
                intent1.putExtras(bundle);
                startActivity(intent1, bundle);
                break;
            default:
                break;
        }
    }

    private void getCombingCardNum(final int i) {
        RequestManager.getInstance().getCombingCardList(new GetCombingCardCallback() {
            @Override
            public void onSuccess(CombingCardBean list) {
                mNum = list.getCombing_card_num();
                if (i == 1) {
                    //购买成功回来默认打开使用
                    if (mNum > 0) {
                        disturbSwitch.setChecked(true);
                        is_combing = 1;
                    }
                }
            }

            @Override
            public void onFailed(int code, String msg) {

            }
        });
    }

    /**
     * 获取发布模板
     */
    private void getTemplate() {

        RequestManager.getInstance().getTemplate(typeId, new GetTemplateCallback() {
            @Override
            public void onSuccess(ReleaseTemplateBean item) {
                releaseTemplateBean = item;
                dismissBookingToast();
                templateDialog = new ReleaseTemplateDialog.Builder(mContext)
                        .setReleaseTemplateBean(item).build();
                templateDialog.show();


            }

            @Override
            public void onFailed(int code, String msg) {
                dismissBookingToast();
                ToastUtils.showCentetToast(PublishNewActivity.this, msg);
            }
        });
    }


    //编辑上传图片过滤
    private void reSendData(final String title, final int p_id, final String providerAttr,
                            final String providerRemark, final String needAttr, final String needRemark,
                            final String providerDescribe, final String needDescribe, int city) {
        showBookingToast(2);
        reImgList.clear();
        if (ReleaseStepHelper.getInstance().getOfferList().size() > 0) {
            for (int i = 0; i < ReleaseStepHelper.getInstance().getOfferList().size(); i++) {
                if (!ReleaseStepHelper.getInstance().getOfferList().get(i).contains("http")) {
                    reImgList.add(ReleaseStepHelper.getInstance().getOfferList().get(i));
                }
            }
            if (reImgList.size() > 0) {

                updateImg(reImgList, title, p_id, providerAttr, providerRemark, needAttr, needRemark, providerDescribe, needDescribe, cityId);
            } else {
                reAnalysis(title, p_id, providerAttr, providerRemark, needAttr, needRemark, providerDescribe, needDescribe, cityId);
            }
        } else {
            reAnalysis(title, p_id, providerAttr, providerRemark, needAttr, needRemark, providerDescribe, needDescribe, cityId);
        }


    }

    //重新发布资源(编辑资源)
    private void reAnalysis(final String title, final int p_id, final String providerAttr,
                            final String providerRemark, final String needAttr, final String needRemark,
                            final String providerDescribe, final String needDescribe, int city) {
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
            RequestManager.getInstance().editResourceV5(resourceId, title, providerAttr, providerRemark, needAttr, needRemark, providerDescribe, needDescribe, provide_img, thumb_img, city, p_id, is_combing, is_transaction, new ResourceReleaseCallback() {
                @Override
                public void onSuccess(ResourceReleaseBean bean) {
                    dismissBookingToast();
                    MobclickAgent.onEvent(mContext, "publish_clickPub", "编辑成功");//统计点击次数
                    MobclickAgent.onEvent(mContext, "publish_category_clickPub", title1);//统计点击次数
                    if (bean.getIs_verify().equals("0")) {
                        ToastUtils.showCentetImgToast(PublishNewActivity.this, "编辑成功");
                        EditResouceInfoHelper.getInstance().clearItem(resourceId);
                        finish();
                    } else {
                        Intent intent = new Intent(PublishNewActivity.this, ResourceReleaseSuccessActivity.class);
                        intent.putExtra("is_verify", bean.getIs_verify());
                        intent.putExtra("from", "repush");
                        intent.putExtra("resources_id", resourceId);
                        intent.putExtra("query_id", providerAttr + "_" + needAttr);
                        intent.putExtra("p_id", typeId);
                        intent.putExtra("p_name", title1);
                        startActivity(intent);
                        finish();
                    }
                }

                @Override
                public void onFailed(int code, String msg) {
                    MobclickAgent.onEvent(mContext, "publish_clickPub", "编辑失败");//统计点击次数
                    dismissBookingToast();

                    ToastUtils.showCentetToast(PublishNewActivity.this, msg);
                }
            });
        }
    }


    //上传照片
    private void updateImg(ArrayList<String> updateList, final String title, final int p_id,
                           final String providerAttr,
                           final String providerRemark, final String needAttr, final String needRemark,
                           final String providerDescribe, final String needDescribe, final int city) {

        for (String item : updateList) {
            Luban.with(PublishNewActivity.this).load(item).ignoreBy(300).setTargetDir(getPath()).filter(new CompressionPredicate() {
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
                            if (goToCode == 1000) {
                                reAnalysis(title, p_id, providerAttr, providerRemark, needAttr, needRemark, providerDescribe, needDescribe, city);
                            } else {
                                goToSend(title, p_id, providerAttr.toString(), providerRemark.toString(), needAttr.toString(), needRemark.toString(), providerDescribe, needDescribe, city);

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
                    ToastUtils.showCentetToast(PublishNewActivity.this, "图片压缩失败，请重新选择图片");
                }
            }).launch();

        }


    }

    private String provide_img;
    private String thumb_img;

    //去发布资源
    private void goToSend(final String title, final int p_id, final String providerAttr,
                          final String providerRemark, final String needAttr, String needRemark,
                          String providerDescribe, String needDescribe, int city) {
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


            RequestManager.getInstance().sendResourceV6(title, p_id, providerAttr, providerRemark, needAttr, needRemark,
                    providerDescribe, needDescribe, provide_img, thumb_img, city, is_combing, is_transaction, new ResourceReleaseCallback() {
                        @Override
                        public void onSuccess(ResourceReleaseBean bean) {
                            MobclickAgent.onEvent(mContext, "publish_clickPub", "发布成功");//统计点击次数
                            MobclickAgent.onEvent(mContext, "publish_category_clickPub", title1);//统计点击次数
                            dismissBookingToast();
                            Intent intent = new Intent(PublishNewActivity.this, ResourceReleaseSuccessActivity.class);
                            intent.putExtra("shareUrl", bean.getShare_url());
                            intent.putExtra("offer", providerRemark);
                            intent.putExtra("is_verify", bean.getIs_verify());
                            intent.putExtra("resources_id", bean.getResources_id());
                            intent.putExtra("from", "push");
                            intent.putExtra("wechatUrl", bean.getWechat_url());
                            intent.putExtra("query_id", providerAttr + "_" + needAttr);
                            intent.putExtra("qrcode", bean.getQrcode_url());
                            intent.putExtra("p_id", typeId);
                            intent.putExtra("p_name", title1);
                            startActivityForResult(intent, MainActivity.RELEASE_SUCCESS);
                            ReleaseStepHelper.getInstance().clearDate();
                            ComUtils.finishshortAll();

                        }

                        @Override
                        public void onFailed(int code, String msg) {
                            MobclickAgent.onEvent(mContext, "publish_clickPub", "发布失败");//统计点击次数
                            dismissBookingToast();
                            Log.i("vvvvvv11111", msg);

                            ToastUtils.showCentetToast(PublishNewActivity.this, msg);
                        }
                    });
        }
    }


    //刷新图片
    public void refreshprovidePic(ArrayList<String> list) {
//        if (list.size() < 8) {
//            list.add(EditResouceAdapter.ADD);
//        }
        ReleaseStepHelper.getInstance().setOfferList(list);
        provideImgList.clear();
        provideImgList.addAll(list);
        provideEditResouceAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == EditResouceAdapter.TYPE_OFFER) {
            refreshprovidePic(data.getStringArrayListExtra(TakePhotoActivity.PHOTO_LIST));
        }
        if (resultCode == MainActivity.SELECTED_REQUEST_CODE) {
            cityId = data.getIntExtra(SelectCityActivity.CITY_ID, -1);
            cityName = data.getStringExtra(SelectCityActivity.CITY_NAME);
            if (cityId != -1) {
                mcoopplacePublishtv.setText(cityName);
            }
        } else if (resultCode == RESULT_OK && requestCode == 30) {
            String data1 = data.getStringExtra("data");
            mpublishOfferet.setText(data1);
        } else if (resultCode == RESULT_OK && requestCode == 31) {
            String data1 = data.getStringExtra("data");
            mpublishNeedet.setText(data1);
        } else if (resultCode == RESULT_OK && requestCode == MYPUSHCODE) {
            getCombingCardNum(1);
        }


    }


    public void showDialogView(String msg) {
        AlertDialogUtils.AlertDialog(PublishNewActivity.this, msg, "知道了", "", new AlertDialogUtils.setOnClick() {
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
        List<String> list = ReleaseStepHelper.getInstance().getOfferList();
        if (imgList.size() == ReleaseStepHelper.getInstance().getOfferList().size()) {
            return true;
        } else {
            return false;
        }

    }


    @Override
    public void editRemove(int postion) {
        imgList.remove(postion);
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
