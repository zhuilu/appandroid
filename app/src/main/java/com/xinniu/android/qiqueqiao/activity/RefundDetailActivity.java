package com.xinniu.android.qiqueqiao.activity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
//import android.support.v7.widget.LinearLayoutManager;
//import androidx.recyclerview.widget.RecyclerView;
import android.text.TextUtils;
import android.util.SparseArray;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xinniu.android.qiqueqiao.R;
import com.xinniu.android.qiqueqiao.adapter.CoopDetailPhotoAdapter;
import com.xinniu.android.qiqueqiao.adapter.RefundDetailPhotoAdapter;
import com.xinniu.android.qiqueqiao.base.BaseActivity;
import com.xinniu.android.qiqueqiao.bean.GuaranteeOrderBean;
import com.xinniu.android.qiqueqiao.bean.RefundDetailBean;
import com.xinniu.android.qiqueqiao.customs.image.GlideSimpleLoader;
import com.xinniu.android.qiqueqiao.customs.image.ImageWatcherHelper;
import com.xinniu.android.qiqueqiao.customs.qldialog.QLTipDialog;
import com.xinniu.android.qiqueqiao.request.RequestManager;
import com.xinniu.android.qiqueqiao.request.callback.AllResultDoCallback;
import com.xinniu.android.qiqueqiao.request.callback.GetGuaranteeOrderCallback;
import com.xinniu.android.qiqueqiao.request.callback.GetRefundDetailCallback;
import com.xinniu.android.qiqueqiao.user.UserInfoHelper;
import com.xinniu.android.qiqueqiao.utils.FullyGridLayoutManager;
//import com.xinniu.android.qiqueqiao.utils.IMUtils;
import com.xinniu.android.qiqueqiao.utils.ImageLoader;
import com.xinniu.android.qiqueqiao.utils.StatusBarCompat;
import com.xinniu.android.qiqueqiao.utils.StringUtils;
import com.xinniu.android.qiqueqiao.utils.TimeUtils;
import com.xinniu.android.qiqueqiao.utils.ToastUtils;
import com.xinniu.android.qiqueqiao.widget.CircleImageView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class RefundDetailActivity extends BaseActivity {
    @BindView(R.id.tv_status)
    TextView tvStatus;
    @BindView(R.id.tv_status_memo)
    TextView tvStatusMemo;
    @BindView(R.id.tv_time)
    TextView tvTime;
    @BindView(R.id.tv_01)
    TextView tv01;
    @BindView(R.id.tv_02)
    TextView tv02;
    @BindView(R.id.tv_03)
    TextView tv03;
    @BindView(R.id.view1)
    LinearLayout view1;
    @BindView(R.id.tv_application)
    TextView tvApplication;
    @BindView(R.id.tv_application_time)
    TextView tvApplicationTime;
    @BindView(R.id.item_lx_headimg)
    CircleImageView itemLxHeadimg;
    @BindView(R.id.tv_price_t)
    TextView tvPriceT;
    @BindView(R.id.tv_refund_reson)
    TextView tvRefundReson;
    @BindView(R.id.tv_refund_memo)
    TextView tvRefundMemo;
    @BindView(R.id.tv_img_01)
    TextView tvImg01;
    @BindView(R.id.refund_photo_recycler_01)
    RecyclerView refundPhotoRecycler01;
    @BindView(R.id.view_bottom_01)
    View viewBottom01;
    @BindView(R.id.tv_refuse)
    TextView tvRefuse;
    @BindView(R.id.tv_refuse_time)
    TextView tvRefuseTime;
    @BindView(R.id.item_lx_headimg_02)
    CircleImageView itemLxHeadimg02;
    @BindView(R.id.tv_refuse_memo)
    TextView tvRefuseMemo;
    @BindView(R.id.tv_img_02)
    TextView tvImg02;
    @BindView(R.id.refund_photo_recycler_02)
    RecyclerView refundPhotoRecycler02;
    @BindView(R.id.llayout_refuse)
    LinearLayout llayoutRefuse;
    @BindView(R.id.view_bottom_02)
    View viewBottom02;
    @BindView(R.id.tv_custome_service)
    TextView tvCustomeService;
    @BindView(R.id.tv_custome_service_time)
    TextView tvCustomeServiceTime;
    @BindView(R.id.item_lx_headimg_03)
    CircleImageView itemLxHeadimg03;
    @BindView(R.id.tv_custome_service_memo)
    TextView tvCustomeServiceMemo;
    @BindView(R.id.tv_img_03)
    TextView tvImg03;
    @BindView(R.id.refund_photo_recycler_03)
    RecyclerView refundPhotoRecycler03;
    @BindView(R.id.llayout_custome_service)
    LinearLayout llayoutCustomeService;
    private int mId;
    private String order_sn;
    private ImageWatcherHelper iwHelper;
    private RefundDetailBean mBean;
    RefundDetailPhotoAdapter photoAdapter, photoAdapter1, photoAdapter2;

    public static void startSimpleEidtForResult(Activity context, String order_sn, int id, int requestCode) {
        Intent intent = new Intent(context, RefundDetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt("id", id);
        bundle.putString("order_sn", order_sn);
        intent.putExtras(bundle);
        context.startActivityForResult(intent, requestCode);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_refund_detail;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        topStatusBar(true);
        Intent intent = getIntent();
        mId = intent.getIntExtra("id", -1);
        order_sn = intent.getStringExtra("order_sn");
        iwHelper = ImageWatcherHelper.with(this, new GlideSimpleLoader()) // 一般来讲， ImageWatcher 需要占据全屏的位置
                .setTranslucentStatus(StatusBarCompat.getStatusBarHeight(this));
        showBookingToast(1);
        getData();
    }

    private void getData() {

        RequestManager.getInstance().refundDetail(order_sn, new GetRefundDetailCallback() {
            @Override
            public void onSuccess(RefundDetailBean item) {
                mBean = item;

                int status = item.getStatus();//0申请中，1成功，2拒绝,3取消，4超时退款成功
                //判断当前是买方还是卖方
                int refund_obj_user_id = item.getRefund_obj().getUser_id();//
                int mUserId = UserInfoHelper.getIntance().getUserId();//当前登录的账号id
                boolean isBuyer = false;//当前账号是否是买家
                long time = (item.getRefund_obj().getOpeartion_time() + 72 * 60 * 60) - item.getNow_time();
                long longHours = time / (60 * 60); //根据时间差来计算小时数
                long longMinutes = (time - longHours * (60 * 60)) / 60;   //根据时间差来计算分钟数
                String longMinutes01;
                String longHours01;

                if (longMinutes < 10) {
                    longMinutes01 = "0" + longMinutes;
                } else {
                    longMinutes01 = longMinutes + "";
                }
                if (longHours < 10) {
                    longHours01 = "0" + longHours;
                } else {
                    longHours01 = longHours + "";
                }
                if (refund_obj_user_id == mUserId) {
                    isBuyer = true;
                }


                if (status == 0) {
                    if (isBuyer) {
                        tvStatus.setText("您已申请退款");
                        tvStatusMemo.setText("请等待对方进行确认");
                        tvTime.setText("剩余时间" + longHours01 + ":" + longMinutes01 + "，超时将自动退款");
                        tvTime.setVisibility(View.VISIBLE);
                        tv01.setVisibility(View.VISIBLE);
                        tv02.setVisibility(View.GONE);
                        tv03.setVisibility(View.GONE);
                        tv01.setText("取消退款");
                    } else {
                        tvStatus.setText("对方已申请退款");
                        tvStatusMemo.setText("请尽快处理退款申请");
                        tvTime.setText("剩余时间" + longHours01 + ":" + longMinutes01 + "，超时将自动退款");
                        tvTime.setVisibility(View.VISIBLE);

                        tv01.setVisibility(View.VISIBLE);
                        tv02.setVisibility(View.VISIBLE);
                        tv03.setVisibility(View.GONE);
                        tv01.setText("拒绝退款");
                        tv02.setText("同意退款");

                    }

                } else if (status == 1) {
                    //成功
                    tvStatus.setText("退款成功");
                    tv01.setVisibility(View.GONE);
                    tv02.setVisibility(View.GONE);
                    tv03.setVisibility(View.GONE);
                    if (item.getServing_obj() != null && !item.getServing_obj().equals("null") && item.getServing_obj().getUser_id() != 0) {
                        //判断是否客服介入成功
                        tvStatusMemo.setText("客服已进行处理");
                        tvTime.setText("处理结果：" + item.getServing_obj().getResult_desc());
                        tvTime.setVisibility(View.VISIBLE);

                    } else {
                        if (isBuyer) {

                            tvStatusMemo.setText("对方同意了您的退款申请");
                            tvTime.setText("退款金额已进入您的钱包");
                            tvTime.setVisibility(View.VISIBLE);

                        } else {

                            tvStatusMemo.setText("您同意了对方的退款申请");
                            tvTime.setText("退款金额已进入对方的钱包");
                            tvTime.setVisibility(View.VISIBLE);

                        }
                    }

                } else if (status == 2) {
                    //拒绝
                    //先判断客服介入
                    if (item.getServing_obj() != null && !item.getServing_obj().equals("null") && item.getServing_obj().getUser_id() != 0) {
                        tvStatus.setText("退款失败");
                        tvTime.setText("请等待客服处理结果");
                        tvTime.setVisibility(View.VISIBLE);
                        tv01.setVisibility(View.GONE);
                        tv02.setVisibility(View.GONE);
                        tv03.setVisibility(View.GONE);
                        if (isBuyer) {
                            tvStatusMemo.setText("已申请客服介入");
                            if (!StringUtils.isEmpty(item.getServing_obj().getResult_desc())) {
                                tvStatusMemo.setText("客服已进行处理");
                                tvTime.setText("处理结果：" + item.getServing_obj().getResult_desc());
                                tvTime.setVisibility(View.VISIBLE);
                            }
                        } else {
                            tvStatusMemo.setText("对方已申请客服介入");
                            if (!StringUtils.isEmpty(item.getServing_obj().getResult_desc())) {
                                tvStatusMemo.setText("客服已进行处理");
                                tvTime.setText("处理结果：" + item.getServing_obj().getResult_desc());
                                tvTime.setVisibility(View.VISIBLE);
                            }
                        }

                    } else {
                        tvTime.setVisibility(View.GONE);
                        tv01.setVisibility(View.GONE);
                        tv02.setVisibility(View.GONE);
                        if (isBuyer) {
                            tvStatus.setText("退款失败");
                            tvStatusMemo.setText("对方拒绝了您的的退款申请");
                            tv03.setVisibility(View.VISIBLE);
                            tv03.setText("申请客服介入");
                        } else {
                            tvStatus.setText("退款失败");
                            tvStatusMemo.setText("您拒绝了对方的退款申请");
                            tv03.setVisibility(View.GONE);
                        }

                    }

                } else if (status == 3) {
                    //取消
                    tvTime.setVisibility(View.GONE);
                    tv01.setVisibility(View.GONE);
                    tv02.setVisibility(View.GONE);
                    tv03.setVisibility(View.GONE);
                    if (isBuyer) {
                        tvStatus.setText("退款取消");
                        tvStatusMemo.setText("您已取消退款申请");
                    } else {
                        tvStatus.setText("退款取消");
                        tvStatusMemo.setText("对方已取消退款申请");

                    }

                } else if (status == 4) {
                    tv01.setVisibility(View.GONE);
                    tv02.setVisibility(View.GONE);
                    tv03.setVisibility(View.GONE);
                    tvTime.setVisibility(View.VISIBLE);
                    if (isBuyer) {
                        tvStatus.setText("退款成功");
                        tvStatusMemo.setText("超时未处理，已自动退款");
                        tvTime.setText("退款金额已进入您的钱包");

                    } else {
                        tvStatus.setText("退款成功");
                        tvStatusMemo.setText("超时未处理，已自动退款");
                        tvTime.setText("退款金额已进入对方的钱包");
                    }
                }

                ImageLoader.loadAvter(item.getRefund_obj().getHead_pic(), itemLxHeadimg);
                tvApplicationTime.setText(TimeUtils.time2ActTime(item.getRefund_obj().getOpeartion_time() * 1000));
//                if (item.getRefund_obj().getRefund_amount().contains(".")) {
//                    String[] pricr01 = item.getRefund_obj().getRefund_amount().split("\\.");
//                    tvPriceT.setText("-¥ " + pricr01[0]);
//                } else {
//                    tvPriceT.setText("-¥ " + item.getRefund_obj().getRefund_amount());//担保金额
                //   }
                if (item.getRefund_obj().getRefund_status() == 1) {
                    tvRefundReson.setText("双方协商一致");
                } else if (item.getRefund_obj().getRefund_status() == 2) {
                    tvRefundReson.setText("双方未达成交易");
                } else if (item.getRefund_obj().getRefund_status() == 3) {
                    tvRefundReson.setText("交易终止");
                } else if (item.getRefund_obj().getRefund_status() == 4) {
                    tvRefundReson.setText("交易完成，退还多余担保金");
                }
                if (StringUtils.isEmpty(item.getRefund_obj().getDesc())) {
                    tvRefundMemo.setText("无");
                } else {
                    tvRefundMemo.setText(item.getRefund_obj().getDesc());
                }
                if (StringUtils.isEmpty(item.getRefund_obj().getThumb_img())) {
                    tvImg01.setVisibility(View.GONE);
                    //  refundPhotoRecycler01.setVisibility(View.GONE);
                } else {
                    //有图片
                    tvImg01.setVisibility(View.VISIBLE);
                    //    refundPhotoRecycler01.setVisibility(View.VISIBLE);
                    List<String> photoList1 = new ArrayList<>();
                    List<String> thumbList1 = new ArrayList<>();
                    String[] thumbs1 = item.getRefund_obj().getThumb_img().split(",");
                    String[] photoArray1 = item.getRefund_obj().getImages().split(",");

                    for (int i = 0; i < thumbs1.length; i++) {
                        thumbList1.add(thumbs1[i]);
                    }

                    for (int i = 0; i < photoArray1.length; i++) {
                        photoList1.add(photoArray1[i]);
                    }
                    // FullyGridLayoutManager manager = new FullyGridLayoutManager(RefundDetailActivity.this, 4);
                    final LinearLayoutManager manager = new LinearLayoutManager(RefundDetailActivity.this, LinearLayoutManager.HORIZONTAL, false);
                    refundPhotoRecycler01.setLayoutManager(manager);
                    photoAdapter = new RefundDetailPhotoAdapter(R.layout.item_recycler_refund, thumbList1, RefundDetailActivity.this, photoList1);
                    refundPhotoRecycler01.setAdapter(photoAdapter);

                    photoAdapter.setSetOnClick(new RefundDetailPhotoAdapter.setOnClick() {
                        @Override
                        public void setOnClick(ArrayList<String> list, int position, SparseArray<ImageView> data, ImageView v) {
                            iwHelper.show(position, data, convert(list));
                        }
                    });
                }

                if (item.getRefuse_obj() != null) {
                    if (item.getRefuse_obj().getUser_id() != 0) {
                        llayoutRefuse.setVisibility(View.VISIBLE);
                        ImageLoader.loadAvter(item.getRefuse_obj().getHead_pic(), itemLxHeadimg02);
                        tvRefuseTime.setText(TimeUtils.time2ActTime(item.getRefuse_obj().getOpeartion_time() * 1000));
                        if (StringUtils.isEmpty(item.getRefuse_obj().getDesc())) {
                            tvRefuseMemo.setText("无");
                        } else {
                            tvRefuseMemo.setText(item.getRefuse_obj().getDesc());
                        }
                        if (StringUtils.isEmpty(item.getRefuse_obj().getThumb_img())) {
                            tvImg02.setVisibility(View.GONE);
                            refundPhotoRecycler02.setVisibility(View.GONE);
                        } else {
                            //有图片
                            tvImg02.setVisibility(View.VISIBLE);
                            refundPhotoRecycler02.setVisibility(View.VISIBLE);
                            List<String> photoList2 = new ArrayList<>();
                            List<String> thumbList2 = new ArrayList<>();
                            String[] thumbs2 = item.getRefuse_obj().getThumb_img().split(",");
                            String[] photoArray2 = item.getRefuse_obj().getImages().split(",");

                            for (int i = 0; i < thumbs2.length; i++) {
                                thumbList2.add(thumbs2[i]);
                            }

                            for (int i = 0; i < photoArray2.length; i++) {
                                photoList2.add(photoArray2[i]);
                            }
                            final LinearLayoutManager manager = new LinearLayoutManager(RefundDetailActivity.this, LinearLayoutManager.HORIZONTAL, false);
                            refundPhotoRecycler02.setLayoutManager(manager);
                            photoAdapter1 = new RefundDetailPhotoAdapter(R.layout.item_recycler_refund, thumbList2, RefundDetailActivity.this, photoList2);
                            refundPhotoRecycler02.setAdapter(photoAdapter1);
                            photoAdapter1.setSetOnClick(new RefundDetailPhotoAdapter.setOnClick() {
                                @Override
                                public void setOnClick(ArrayList<String> list, int position, SparseArray<ImageView> data, ImageView v) {
                                    iwHelper.show(position, data, convert(list));
                                }
                            });
                        }
                    } else {
                        llayoutRefuse.setVisibility(View.GONE);
                    }
                } else {
                    llayoutRefuse.setVisibility(View.GONE);
                }


                if (item.getServing_obj() != null && !item.getServing_obj().equals("null")) {
                    if (item.getServing_obj().getUser_id() != 0) {
                        llayoutCustomeService.setVisibility(View.VISIBLE);
                        ImageLoader.loadAvter(item.getServing_obj().getHead_pic(), itemLxHeadimg03);
                        tvCustomeServiceTime.setText(TimeUtils.time2ActTime(item.getServing_obj().getOpeartion_time() * 1000));
                        if (StringUtils.isEmpty(item.getServing_obj().getDesc())) {
                            tvCustomeServiceMemo.setText("无");
                        } else {
                            tvCustomeServiceMemo.setText(item.getServing_obj().getDesc());
                        }
                        if (StringUtils.isEmpty(item.getServing_obj().getThumb_img())) {
                            tvImg03.setVisibility(View.GONE);
                            refundPhotoRecycler03.setVisibility(View.GONE);
                        } else {
                            //有图片
                            tvImg03.setVisibility(View.VISIBLE);
                            refundPhotoRecycler03.setVisibility(View.VISIBLE);
                            List<String> photoList = new ArrayList<>();
                            List<String> thumbList = new ArrayList<>();
                            String[] thumbs = item.getServing_obj().getThumb_img().split(",");
                            String[] photoArray = item.getServing_obj().getImages().split(",");

                            for (int i = 0; i < thumbs.length; i++) {
                                thumbList.add(thumbs[i]);
                            }

                            for (int i = 0; i < photoArray.length; i++) {
                                photoList.add(photoArray[i]);
                            }
                            final LinearLayoutManager manager = new LinearLayoutManager(RefundDetailActivity.this, LinearLayoutManager.HORIZONTAL, false);
                            refundPhotoRecycler03.setLayoutManager(manager);
                            photoAdapter2 = new RefundDetailPhotoAdapter(R.layout.item_recycler_refund, thumbList, RefundDetailActivity.this, photoList);
                            refundPhotoRecycler03.setAdapter(photoAdapter2);
                            photoAdapter2.setSetOnClick(new RefundDetailPhotoAdapter.setOnClick() {
                                @Override
                                public void setOnClick(ArrayList<String> list, int position, SparseArray<ImageView> data, ImageView v) {
                                    iwHelper.show(position, data, convert(list));
                                }
                            });
                        }
                    } else {
                        llayoutCustomeService.setVisibility(View.GONE);
                    }
                } else {
                    llayoutCustomeService.setVisibility(View.GONE);
                }
                dismissBookingToast();
            }

            @Override
            public void onFailed(int code, String msg) {
                dismissBookingToast();
                ToastUtils.showCentetToast(RefundDetailActivity.this, msg);

            }
        });
    }


    @OnClick({R.id.bt_return, R.id.tv_01, R.id.tv_02, R.id.tv_03, R.id.item_lx_headimg, R.id.item_lx_headimg_02, R.id.item_lx_headimg_03})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_return:
                Intent intent = new Intent();
                setResult(Activity.RESULT_OK, intent);
                finish();
                break;
            case R.id.tv_01:
                if (tv01.getText().toString().equals("拒绝退款")) {
                    RefusalRefundActivity.startSimpleEidtForResult(RefundDetailActivity.this, mId, 32);

                } else if (tv01.getText().toString().equals("取消退款")) {
                    cancelRefund();

                }
                break;
            case R.id.tv_02:
                //同意退款
                new QLTipDialog.Builder(RefundDetailActivity.this)
                        .setCancelable(false)
                        .setCancelableOnTouchOutside(false)
                        .setMessage("确定同意退款")
                        .setPositiveButton("同意退款", new QLTipDialog.IPositiveCallback() {
                            @Override
                            public void onClick() {
                                agreeRefund();
                            }
                        })
                        .setNegativeButton("取消", new QLTipDialog.INegativeCallback() {
                            @Override
                            public void onClick() {
                            }
                        })
                        .show(RefundDetailActivity.this);

                break;
            case R.id.tv_03:
                //申请客服介入

                ApplicatuinCustomeServiceActivity.startSimpleEidtForResult(RefundDetailActivity.this, "1", mId, 32);
                break;
            case R.id.item_lx_headimg:
                PersonCentetActivity.start(RefundDetailActivity.this, mBean.getRefund_obj().getUser_id() + "");
                break;
            case R.id.item_lx_headimg_02:
                PersonCentetActivity.start(RefundDetailActivity.this, mBean.getRefuse_obj().getUser_id() + "");
                break;
            case R.id.item_lx_headimg_03:
                PersonCentetActivity.start(RefundDetailActivity.this, mBean.getServing_obj().getUser_id() + "");
                break;
        }
    }

    /**
     * 同意退款
     */
    private void agreeRefund() {
        showBookingToast(2);
        RequestManager.getInstance().agreeRefund(mId, new AllResultDoCallback() {
            @Override
            public void onSuccess(String msg) {
                dismissBookingToast();
                ToastUtils.showCentetToast(RefundDetailActivity.this, msg);
                Intent intent = new Intent();
                setResult(Activity.RESULT_OK, intent);
                finish();
            }

            @Override
            public void onFailed(int code, String msg) {
                dismissBookingToast();
                ToastUtils.showCentetToast(RefundDetailActivity.this, msg);
            }
        });
    }

    /**
     * 取消退款
     */

    private void cancelRefund() {
        showBookingToast(2);
        RequestManager.getInstance().cancelRefund(mId, new AllResultDoCallback() {
            @Override
            public void onSuccess(String msg) {
                dismissBookingToast();
                ToastUtils.showCentetToast(RefundDetailActivity.this, msg);
                Intent intent = new Intent();
                setResult(Activity.RESULT_OK, intent);
                finish();
            }

            @Override
            public void onFailed(int code, String msg) {
                dismissBookingToast();
                ToastUtils.showCentetToast(RefundDetailActivity.this, msg);
            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            //TODO something
            Intent intent = new Intent();
            setResult(Activity.RESULT_OK, intent);
            finish();
            return true;

        }
        return super.onKeyDown(keyCode, event);


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {

            if (requestCode == 32) {
                getData();


            }
        }

    }

    private List<Uri> convert(List<String> data) {
        List<Uri> list = new ArrayList<>();
        for (String d : data) list.add(Uri.parse(d));
        return list;
    }
}
