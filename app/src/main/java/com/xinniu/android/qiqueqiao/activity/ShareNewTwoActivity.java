package com.xinniu.android.qiqueqiao.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.xinniu.android.qiqueqiao.R;
import com.xinniu.android.qiqueqiao.base.BaseActivity;
import com.xinniu.android.qiqueqiao.request.RequestManager;
import com.xinniu.android.qiqueqiao.utils.QRCodeUtil;
import com.xinniu.android.qiqueqiao.utils.ShareUtils;
import com.xinniu.android.qiqueqiao.utils.ToastUtils;
import com.xinniu.android.qiqueqiao.utils.WebViewScreenShotUtils;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by BDXK on 2019/3/15.
 * project : newbridge--- android xs
 */

public class ShareNewTwoActivity extends BaseActivity {
    @BindView(R.id.llayout_root_view)
    LinearLayout llayoutRootView;
    @BindView(R.id.img_bg_01)
    ImageView imgBg01;
    @BindView(R.id.img_bg_02)
    ImageView imgBg02;
    @BindView(R.id.img_bg_03)
    ImageView imgBg03;
    @BindView(R.id.yhandleHScroll)
    HorizontalScrollView yhandleHScroll;
    @BindView(R.id.bwx_shareTv)
    TextView bwxShareTv;
    @BindView(R.id.bpyq_shareTv)
    TextView bpyqShareTv;
    @BindView(R.id.balc_shareTv)
    TextView balcShareTv;
    @BindView(R.id.bfinish_back)
    ImageView bfinishBack;
    @BindView(R.id.llayout_01)
    LinearLayout llayout01;
    @BindView(R.id.llayout_02)
    LinearLayout llayout02;
    @BindView(R.id.llayout_03)
    LinearLayout llayout03;
    @BindView(R.id.llayout_root)
    LinearLayout llayoutRoot;
    private View view01, view02, view03;
    ScrollView scrollView01, scrollView02, scrollView03;
    private String mUrl;
    private int position = 1;//当前是选择第一个背景

    public static void start(Context context, String data, String url) {
        Intent intent = new Intent(context, ShareNewTwoActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("data", data);
        bundle.putString("url", url);
        intent.putExtras(bundle);
        context.startActivity(intent, bundle);

    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_act_detail;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        topStatusBar(false);
        imgBg01.setImageResource(R.mipmap.act_theme_bg_1);
        imgBg02.setImageResource(R.mipmap.act_theme_bg_2);
        imgBg03.setImageResource(R.mipmap.act_theme_bg_3);
        Bundle bundle = getIntent().getExtras();
        String str = bundle.getString("data");
        mUrl = bundle.getString("url");
        try {
            JSONObject jsonObject = new JSONObject(str);
            showView(jsonObject);
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

    private void showView(JSONObject jsonObject) {
        Bitmap mQRBitmap = QRCodeUtil.createQRCodeBitmap(mUrl + "&&urltype=qqqh5", 480, 480);
        String title = "", company = "", address = "", time = "";
        try {
            title = jsonObject.getString("title");
            company = jsonObject.getString("corporate_name");
            address = jsonObject.getString("address");
            time = jsonObject.getString("start_time_text") + " " + jsonObject.getString("start_time_point") + "至" + jsonObject.getString("end_time_point");

        } catch (JSONException e) {
            e.printStackTrace();
        }
        view01 = View.inflate(ShareNewTwoActivity.this, R.layout.view_act_detail_one, null);
        scrollView01 = (ScrollView) view01.findViewById(R.id.mshareWeb);
        TextView tvTitle = (TextView) view01.findViewById(R.id.tv_title);
        TextView tvCompany = (TextView) view01.findViewById(R.id.tv_company);
        ImageView imageMa = (ImageView) view01.findViewById(R.id.img_ma);
        TextView tvAddress = (TextView) view01.findViewById(R.id.tv_address);
        TextView tvTime = (TextView) view01.findViewById(R.id.tv_time);
        tvTitle.setText(title);
        tvCompany.setText(company);
        tvAddress.setText(address);
        tvTime.setText(time);
        imageMa.setImageBitmap(mQRBitmap);
        llayoutRootView.removeAllViews();
        llayoutRootView.addView(view01);//默认选择第一条


        view02 = View.inflate(ShareNewTwoActivity.this, R.layout.view_act_detail_two, null);
        scrollView02 = (ScrollView) view02.findViewById(R.id.mshareWeb);
        TextView tvTitle2 = (TextView) view02.findViewById(R.id.tv_title);
        TextView tvCompany2 = (TextView) view02.findViewById(R.id.tv_company);
        ImageView imageMa2 = (ImageView) view02.findViewById(R.id.img_ma);
        TextView tvAddress2 = (TextView) view02.findViewById(R.id.tv_address);
        TextView tvTime2 = (TextView) view02.findViewById(R.id.tv_time);
        tvTitle2.setText(title);
        tvCompany2.setText(company);
        tvAddress2.setText(address);
        tvTime2.setText(time);
        imageMa2.setImageBitmap(mQRBitmap);

        view03 = View.inflate(ShareNewTwoActivity.this, R.layout.view_act_detail_three, null);
        scrollView03 = (ScrollView) view03.findViewById(R.id.mshareWeb);
        TextView tvTitle3 = (TextView) view03.findViewById(R.id.tv_title);
        TextView tvCompany3 = (TextView) view03.findViewById(R.id.tv_company);
        ImageView imageMa3 = (ImageView) view03.findViewById(R.id.img_ma);
        TextView tvAddress3 = (TextView) view03.findViewById(R.id.tv_address);
        TextView tvTime3 = (TextView) view03.findViewById(R.id.tv_time);
        tvTitle3.setText(title);
        tvCompany3.setText(company);
        tvAddress3.setText(address);
        tvTime3.setText(time);
        imageMa3.setImageBitmap(mQRBitmap);
    }


    @OnClick({R.id.img_bg_01, R.id.img_bg_02, R.id.img_bg_03, R.id.bwx_shareTv, R.id.bpyq_shareTv, R.id.balc_shareTv, R.id.bfinish_back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_bg_01:
                position = 1;
                clear();
                llayout01.setBackgroundResource(R.drawable.bg_select);
                llayoutRootView.removeAllViews();
                llayoutRootView.addView(view01);
                break;
            case R.id.img_bg_02:
                position = 2;
                clear();
                llayout02.setBackgroundResource(R.drawable.bg_select);
                llayoutRootView.removeAllViews();
                llayoutRootView.addView(view02);
                break;
            case R.id.img_bg_03:
                position = 3;
                clear();
                llayout03.setBackgroundResource(R.drawable.bg_select);
                llayoutRootView.removeAllViews();
                llayoutRootView.addView(view03);
                break;
            case R.id.bwx_shareTv:
                Bitmap bitmap = null;
                if (position == 1) {
                    bitmap = WebViewScreenShotUtils.ActionScreenshot2(ShareNewTwoActivity.this, scrollView01);
                } else if (position == 2) {
                    bitmap = WebViewScreenShotUtils.ActionScreenshot2(ShareNewTwoActivity.this, scrollView02);
                } else if (position == 3) {
                    bitmap = WebViewScreenShotUtils.ActionScreenshot2(ShareNewTwoActivity.this, scrollView03);
                }
                if (bitmap == null) {
                    ToastUtils.showCentetToast(ShareNewTwoActivity.this, "海报保存失败，请稍后再分享");
                    return;
                }
                showBookingToast(2);
                ShareUtils.ShareLongImg(ShareNewTwoActivity.this, SHARE_MEDIA.WEIXIN, bitmap, new UMShareListener() {
                    @Override
                    public void onStart(SHARE_MEDIA share_media) {
                        dismissBookingToast();
                    }

                    @Override
                    public void onResult(SHARE_MEDIA share_media) {
                        dismissBookingToast();
                        RequestManager.getInstance().dailyShare(ShareNewTwoActivity.this);
                    }

                    @Override
                    public void onError(SHARE_MEDIA share_media, Throwable throwable) {
                        dismissBookingToast();

                    }

                    @Override
                    public void onCancel(SHARE_MEDIA share_media) {
                        dismissBookingToast();
                    }
                });
                break;
            case R.id.bpyq_shareTv:
                Bitmap bitmapx = null;
                if (position == 1) {
                    bitmapx = WebViewScreenShotUtils.ActionScreenshot2(ShareNewTwoActivity.this, scrollView01);
                } else if (position == 2) {
                    bitmapx = WebViewScreenShotUtils.ActionScreenshot2(ShareNewTwoActivity.this, scrollView02);
                } else if (position == 3) {
                    bitmapx = WebViewScreenShotUtils.ActionScreenshot2(ShareNewTwoActivity.this, scrollView03);
                }
                if (bitmapx == null) {
                    ToastUtils.showCentetToast(ShareNewTwoActivity.this, "海报保存失败，请稍后再分享");
                    return;
                }
                showBookingToast(2);
                ShareUtils.ShareLongImg(ShareNewTwoActivity.this, SHARE_MEDIA.WEIXIN_CIRCLE, bitmapx, new UMShareListener() {
                    @Override
                    public void onStart(SHARE_MEDIA share_media) {
                        dismissBookingToast();
                    }

                    @Override
                    public void onResult(SHARE_MEDIA share_media) {
                        dismissBookingToast();
                        RequestManager.getInstance().dailyShare(ShareNewTwoActivity.this);
                    }

                    @Override
                    public void onError(SHARE_MEDIA share_media, Throwable throwable) {
                        Log.i("失败了吗", "11111111111111");
                        dismissBookingToast();
                    }

                    @Override
                    public void onCancel(SHARE_MEDIA share_media) {
                        Log.i("取消了吗", "11111111111111");
                        dismissBookingToast();
                    }
                });
                break;
            case R.id.balc_shareTv:
                Bitmap bitmapc = null;
                if (position == 1) {
                    bitmapc = WebViewScreenShotUtils.ActionScreenshot2(ShareNewTwoActivity.this, scrollView01);
                } else if (position == 2) {
                    bitmapc = WebViewScreenShotUtils.ActionScreenshot2(ShareNewTwoActivity.this, scrollView02);
                } else if (position == 3) {
                    bitmapc = WebViewScreenShotUtils.ActionScreenshot2(ShareNewTwoActivity.this, scrollView03);
                }
                if (bitmapc == null) {
                    ToastUtils.showCentetToast(ShareNewTwoActivity.this, "海报保存失败，请稍后再试");
                    return;
                }
                boolean b = WebViewScreenShotUtils.isS(bitmapc, ShareNewTwoActivity.this);
                if (b) {
                    Toast.makeText(ShareNewTwoActivity.this, "保存成功", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(ShareNewTwoActivity.this, "保存失败", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.bfinish_back:
                finish();
                break;
        }
    }

    private void clear() {
        llayout01.setBackground(null);
        llayout02.setBackground(null);
        llayout03.setBackground(null);

    }


}
