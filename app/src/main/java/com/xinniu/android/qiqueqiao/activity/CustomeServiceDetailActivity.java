package com.xinniu.android.qiqueqiao.activity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xinniu.android.qiqueqiao.R;
import com.xinniu.android.qiqueqiao.adapter.RefundDetailPhotoAdapter;
import com.xinniu.android.qiqueqiao.base.BaseActivity;
import com.xinniu.android.qiqueqiao.bean.GuaranteeServiceInfoBean;
import com.xinniu.android.qiqueqiao.customs.image.GlideSimpleLoader;
import com.xinniu.android.qiqueqiao.customs.image.ImageWatcherHelper;
import com.xinniu.android.qiqueqiao.request.RequestManager;
import com.xinniu.android.qiqueqiao.request.callback.GetGuaranteeServiceinfoCallback;
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

public class CustomeServiceDetailActivity extends BaseActivity {
    @BindView(R.id.tv_status)
    TextView tvStatus;
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
    @BindView(R.id.tv_status_memo)
    TextView tvStatusMemo;
    private ImageWatcherHelper iwHelper;
    public static void start(Context context, String order_sn, int id) {
        Intent starter = new Intent(context, CustomeServiceDetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("order_sn", order_sn);
        bundle.putInt("id", id);
        starter.putExtras(bundle);
        context.startActivity(starter);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_custome_service_detail;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        topStatusBar(true);
        int id = getIntent().getIntExtra("id", -1);
        iwHelper = ImageWatcherHelper.with(this, new GlideSimpleLoader()) // 一般来讲， ImageWatcher 需要占据全屏的位置
                .setTranslucentStatus(StatusBarCompat.getStatusBarHeight(this));
        getData(id);
    }

    private void getData(int id) {
        showBookingToast(1);
        RequestManager.getInstance().serviceInfo(id, new GetGuaranteeServiceinfoCallback() {
            @Override
            public void onSuccess(GuaranteeServiceInfoBean item) {
                int status = item.getStatus();//	0 介入中，1介入结束
                if (status == 0) {
                    tvStatus.setText("已申请客服介入");
                    tvStatusMemo.setText("请等待客服处理结果");
                }else if(status==1){
                    tvStatus.setText("客服已进行处理");
                    tvStatusMemo.setText("处理结果："+item.getResult_desc());
                }
                ImageLoader.loadAvter(item.getHead_pic(), itemLxHeadimg03);
                tvCustomeServiceTime.setText(TimeUtils.time2ActTime(item.getCreate_time() * 1000));

                if (StringUtils.isEmpty(item.getDesc())) {
                    tvCustomeServiceMemo.setText("无");
                } else {
                    tvCustomeServiceMemo.setText(item.getDesc());
                }
                if (StringUtils.isEmpty(item.getThumb_img())) {
                    tvImg03.setVisibility(View.GONE);
                    refundPhotoRecycler03.setVisibility(View.GONE);
                } else {
                    //有图片
                    tvImg03.setVisibility(View.VISIBLE);
                    refundPhotoRecycler03.setVisibility(View.VISIBLE);
                    List<String> photoList = new ArrayList<>();
                    List<String> thumbList = new ArrayList<>();
                    String[] thumbs = item.getThumb_img().split(",");
                    String[] photoArray = item.getImages().split(",");

                    for (int i = 0; i < thumbs.length; i++) {
                        thumbList.add(thumbs[i]);
                    }

                    for (int i = 0; i < photoArray.length; i++) {
                        photoList.add(photoArray[i]);
                    }
                    final LinearLayoutManager manager = new LinearLayoutManager(CustomeServiceDetailActivity.this, LinearLayoutManager.HORIZONTAL, false);
                    refundPhotoRecycler03.setLayoutManager(manager);
                    RefundDetailPhotoAdapter  photoAdapter2 = new RefundDetailPhotoAdapter(R.layout.item_recycler_refund, thumbList, CustomeServiceDetailActivity.this, photoList);
                    refundPhotoRecycler03.setAdapter(photoAdapter2);
                    photoAdapter2.setSetOnClick(new RefundDetailPhotoAdapter.setOnClick() {
                        @Override
                        public void setOnClick(ArrayList<String> list, int position, SparseArray<ImageView> data, ImageView v) {
                            iwHelper.show(position, data, convert(list));
                        }
                    });
                }
                dismissBookingToast();

            }

            @Override
            public void onFailed(int code, String msg) {
                dismissBookingToast();
                ToastUtils.showCentetImgToast(CustomeServiceDetailActivity.this, msg);
            }
        });
    }

    @OnClick({R.id.bt_return, R.id.item_lx_headimg_03})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_return:
                finish();
                break;
            case R.id.item_lx_headimg_03:
                break;
        }
    }

    private List<Uri> convert(List<String> data) {
        List<Uri> list = new ArrayList<>();
        for (String d : data) list.add(Uri.parse(d));
        return list;
    }
}
