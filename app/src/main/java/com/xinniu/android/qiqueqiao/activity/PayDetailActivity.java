package com.xinniu.android.qiqueqiao.activity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.xinniu.android.qiqueqiao.R;
import com.xinniu.android.qiqueqiao.base.BaseActivity;
import com.xinniu.android.qiqueqiao.bean.PayDetailBean;
import com.xinniu.android.qiqueqiao.customs.image.GlideSimpleLoader;
import com.xinniu.android.qiqueqiao.customs.image.ImageWatcherHelper;
import com.xinniu.android.qiqueqiao.request.RequestManager;
import com.xinniu.android.qiqueqiao.request.callback.GetGuaranteePayDetailCallback;
import com.xinniu.android.qiqueqiao.utils.ImageLoader;
import com.xinniu.android.qiqueqiao.utils.StatusBarCompat;
import com.xinniu.android.qiqueqiao.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class PayDetailActivity extends BaseActivity {
    @BindView(R.id.tv_price)
    TextView tvPrice;
    @BindView(R.id.tv_status)
    TextView tvStatus;
    @BindView(R.id.tv_type)
    TextView tvType;
    @BindView(R.id.img)
    ImageView img;
    ArrayList<String> imgList = new ArrayList<>();
    private ImageWatcherHelper iwHelper;
    private String mImageUrl;
    public static void start(Context context, String order_sn, int event_status) {
        Intent starter = new Intent(context, PayDetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("order_sn", order_sn);
        bundle.putInt("event_status", event_status);
        starter.putExtras(bundle);
        context.startActivity(starter);
    }


    @Override
    public int getLayoutId() {
        return R.layout.activity_pay_detail;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        topStatusBar(true);
        String order_sn = getIntent().getStringExtra("order_sn");
        int event_status = getIntent().getIntExtra("event_status", 1);
        iwHelper = ImageWatcherHelper.with(this, new GlideSimpleLoader()) // 一般来讲， ImageWatcher 需要占据全屏的位置
                .setTranslucentStatus(StatusBarCompat.getStatusBarHeight(this));
        getData(order_sn, event_status);

    }

    private void getData(String order_sn, int event_status) {
        showBookingToast(1);
        RequestManager.getInstance().payGuaranteeDetail(order_sn, event_status, new GetGuaranteePayDetailCallback() {
            @Override
            public void onSuccess(PayDetailBean item) {
                dismissBookingToast();
                if (item.getIncome_amount().contains(".")) {
                    String[] pricr01 = item.getIncome_amount().split("\\.");
                    tvPrice.setText(pricr01[0]);
                } else {
                    tvPrice.setText(item.getIncome_amount());
                }
                if (item.getStatus() == 0) {
                    //审核中
                    tvStatus.setText("请等待系统确认到账情况");
                } else if (item.getStatus() == 1) {
                    //审核成功
                    tvStatus.setText("系统确认到账");
                }

                if (item.getPay_type() == 1) {
                    tvType.setText("对公转账");

                } else if (item.getPay_type() == 2) {

                    tvType.setText("支付宝");
                }
                ImageLoader.loadLocalImg(item.getPicture_proof(), img);
                mImageUrl=item.getPicture_proof();

            }

            @Override
            public void onFailed(int code, String msg) {
                dismissBookingToast();
                ToastUtils.showCentetImgToast(PayDetailActivity.this, msg);
            }
        });
    }

    @OnClick({R.id.bt_return, R.id.img})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_return:
                finish();
                break;
            case R.id.img:
                final List<String> longPictureList = new ArrayList<>();
                longPictureList.add(mImageUrl);
                final int initPosition = 0;
                iwHelper.show(convert(longPictureList),initPosition);

                break;
        }
    }

    private List<Uri> convert(List<String> data) {
        List<Uri> list = new ArrayList<>();
        for (String d : data) list.add(Uri.parse(d));
        return list;
    }
}
