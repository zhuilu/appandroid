package com.xinniu.android.qiqueqiao.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.google.gson.Gson;
import com.xinniu.android.qiqueqiao.R;
import com.xinniu.android.qiqueqiao.adapter.base.CustomServiceImgViewAdapter;
import com.xinniu.android.qiqueqiao.adapter.base.FeedBackImgViewAdapter;
import com.xinniu.android.qiqueqiao.base.BaseActivity;
import com.xinniu.android.qiqueqiao.bean.GuaranteeDetailBean;
import com.xinniu.android.qiqueqiao.bean.UploadBean;
import com.xinniu.android.qiqueqiao.request.RequestManager;
import com.xinniu.android.qiqueqiao.request.callback.AllResultDoCallback;
import com.xinniu.android.qiqueqiao.request.callback.RequestCallback;
import com.xinniu.android.qiqueqiao.utils.FullyGridLayoutManager;
import com.xinniu.android.qiqueqiao.utils.ImageLoader;
import com.xinniu.android.qiqueqiao.utils.StringUtils;
import com.xinniu.android.qiqueqiao.utils.ToastUtils;
import com.xinniu.android.qiqueqiao.widget.CircleImageView;

import java.io.File;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import retrofit2.Call;
import top.zibin.luban.CompressionPredicate;
import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;
import top.zibin.luban.OnRenameListener;

import androidx.recyclerview.widget.RecyclerView;

public class ApplicationRefundAvtivity extends BaseActivity {
    @BindView(R.id.btn_submit)
    TextView btnSubmit;
    @BindView(R.id.tv_person_status)
    TextView tvPersonStatus;
    @BindView(R.id.item_lx_headimg)
    CircleImageView itemLxHeadimg;
    @BindView(R.id.lx_nametv)
    TextView lxNametv;
    @BindView(R.id.tv_identity)
    TextView tvIdentity;
    @BindView(R.id.ll)
    LinearLayout ll;
    @BindView(R.id.lx_positiontv)
    TextView lxPositiontv;
    @BindView(R.id.tv_price_d)
    TextView tvPriceD;
    @BindView(R.id.rlayout_01)
    RelativeLayout rlayout01;
    @BindView(R.id.view_01)
    View view01;
    @BindView(R.id.tv_price_y)
    TextView tvPriceY;
    @BindView(R.id.rlayout_02)
    RelativeLayout rlayout02;
    @BindView(R.id.view_02)
    View view02;
    @BindView(R.id.tv_price_s)
    TextView tvPriceS;
    @BindView(R.id.rlayout_04)
    RelativeLayout rlayout04;
    @BindView(R.id.tv_price_t)
    TextView tvPriceT;
    @BindView(R.id.rlayout_05)
    RelativeLayout rlayout05;
    @BindView(R.id.tv_reson)
    TextView tvReson;
    @BindView(R.id.rlayout_06)
    RelativeLayout rlayout06;
    @BindView(R.id.edit_content)
    EditText editContent;
    @BindView(R.id.activity_publish_recycler)
    RecyclerView activityPublishRecycler;
    private CustomServiceImgViewAdapter provideEditResouceAdapter;
    private ArrayList<String> provideImgList = new ArrayList<>();
    private List<String> imgList = new ArrayList<>();
    private List<String> thumbList = new ArrayList<>();
    private int option = 0;
    private int refund_status;
    private OptionsPickerView industryPv;
    private List<String> typeList = new ArrayList<>();
    private int mId;
    private GuaranteeDetailBean mBean;

    public static void startSimpleEidtForResult(AppCompatActivity context, int id, String data, int requestCode) {
        Intent intent = new Intent(context, ApplicationRefundAvtivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt("id", id);
        bundle.putString("data", data);
        intent.putExtras(bundle);
        context.startActivityForResult(intent, requestCode);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_application_refund;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        topStatusBar(true);
        Intent intent = getIntent();
        mId = intent.getIntExtra("id", -1);
        String data = intent.getStringExtra("data");
        Gson gson2 = new Gson();
        mBean = gson2.fromJson(data, GuaranteeDetailBean.class);
        ImageLoader.loadAvter(mBean.getObj_user_info().getHead_pic(), itemLxHeadimg);
        lxNametv.setText(mBean.getObj_user_info().getRealname());
        String job = "";
        if (StringUtils.isEmpty(mBean.getObj_user_info().getCompany()) && StringUtils.isEmpty(mBean.getObj_user_info().getPosition())) {
            job = "";
        } else {
            if (StringUtils.isEmpty(mBean.getObj_user_info().getCompany())) {
                job = mBean.getObj_user_info().getPosition();
            } else if (StringUtils.isEmpty(mBean.getObj_user_info().getPosition())) {
                job = mBean.getObj_user_info().getCompany();
            } else {
                job = mBean.getObj_user_info().getCompany() + "|" + mBean.getObj_user_info().getPosition();
            }
        }
        lxPositiontv.setText(job);
        if (mBean.getObj_user_info().getParty_a_or_party_b() == 1) {
            //	1甲方，2：乙方
            tvIdentity.setText("买方");
        } else {
            tvIdentity.setText("卖方");
        }
        if (mBean.getEstimated_amount().contains(".")) {
            String[] pricr01 = mBean.getEstimated_amount().split("\\.");
            tvPriceD.setText(pricr01[0]);//担保金额
        } else {
            tvPriceD.setText(mBean.getEstimated_amount());//担保金额
        }

        if (mBean.getRemaining_amount().contains(".")) {
            String[] pricr01 = mBean.getRemaining_amount().split("\\.");
            tvPriceS.setText(pricr01[0]);
            //   tvPriceT.setText("-¥ " + pricr01[0]);
        } else {
            tvPriceS.setText(mBean.getRemaining_amount());
            //    tvPriceT.setText("¥ " + mBean.getRemaining_amount());
        }
        if (mBean.getSettlement_amount().contains(".")) {
            String[] pricr01 = mBean.getSettlement_amount().split("\\.");
            tvPriceY.setText(pricr01[0]);
        } else {
            tvPriceY.setText(mBean.getSettlement_amount());
        }
        //买方显示处理
        int is_initiate = mBean.getIs_initiate();   //是否发起人
        if (is_initiate == 1) {
            tvPersonStatus.setText("交易对方");
        } else {
            tvPersonStatus.setText("发起人");
        }

        provideEditResouceAdapter = new CustomServiceImgViewAdapter(mContext, provideImgList, 4);
        FullyGridLayoutManager manager = new FullyGridLayoutManager(this, 4);
        activityPublishRecycler.setLayoutManager(manager);
        activityPublishRecycler.setAdapter(provideEditResouceAdapter);
        typeList.add("双方协商一致");
        typeList.add("双方未达成交易");
        typeList.add("交易终止");
        typeList.add("交易完成，退还多余担保金");
        industryPv = new OptionsPickerBuilder(ApplicationRefundAvtivity.this, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {

                option = options1;
                tvReson.setText(typeList.get(options1));
                refund_status = option + 1;

            }
        }).setTitleText("退款原因")
                .build();
        industryPv.setPicker(typeList);
        industryPv.setSelectOptions(option);
    }

    @OnClick({R.id.bt_return, R.id.btn_submit, R.id.rlayout_info, R.id.rlayout_06})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_return:
                finish();
                break;
            case R.id.btn_submit:
                if (tvReson.getText().toString().equals("请选择")) {
                    ToastUtils.showCentetToast(mContext, "请选择退款原因");
                    return;
                }
                if (provideImgList.size() > 0) {
                    showBookingToast(2);
                    updateImg(provideImgList);
                } else {
                    showBookingToast(2);
                    //直接提交
                    submit();

                }
                break;
            case R.id.rlayout_info:
                //个人中心
                PersonCentetActivity.start(ApplicationRefundAvtivity.this, mBean.getObj_user_info().getUser_id() + "");
                break;
            case R.id.rlayout_06:
                industryPv.show();
                break;
        }
    }

    private void updateImg(ArrayList<String> provideImgList) {
        for (String item : provideImgList) {
            Luban.with(ApplicationRefundAvtivity.this).load(item).ignoreBy(300).setTargetDir(getPath()).filter(new CompressionPredicate() {
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
            })
                    .setCompressListener(new OnCompressListener() {
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
                                    submit();

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
                            ToastUtils.showCentetToast(ApplicationRefundAvtivity.this, "图片压缩失败，请重新选择图片");
                        }
                    }).launch();

        }
    }

    private void submit() {
        String provide_img = "";
        String thumb_img = "";

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
            String text = "";
            if (!StringUtils.isEmpty(editContent.getText().toString())) {
                text = editContent.getText().toString();
            }

            RequestManager.getInstance().applicationRefund(mBean.getOrder_sn(), mId, mBean.getObj_user_info().getUser_id(), mBean.getEstimated_amount(), mBean.getSettlement_amount(), mBean.getRemaining_amount(), refund_status, text, provide_img, thumb_img, new AllResultDoCallback() {
                @Override
                public void onSuccess(String msg) {
                    dismissBookingToast();
                    ToastUtils.showCentetToast(ApplicationRefundAvtivity.this, msg);
                    Intent intent = new Intent();
                    setResult(Activity.RESULT_OK, intent);
                    finish();
                }

                @Override
                public void onFailed(int code, String msg) {
                    dismissBookingToast();
                    ToastUtils.showCentetToast(ApplicationRefundAvtivity.this, msg);
                }
            });

        }


    }

    //刷新图片
    public void refreshprovidePic(ArrayList<String> list) {
        provideImgList.clear();
        provideImgList.addAll(list);
        provideEditResouceAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == FeedBackImgViewAdapter.TYPE) {
            refreshprovidePic(data.getStringArrayListExtra(TakePhotoTwoActivity.PHOTO_LIST));
        }

    }


    public boolean isUpdateSuccess() {
        if (imgList.size() == provideImgList.size()) {
            return true;
        } else {
            return false;
        }

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
