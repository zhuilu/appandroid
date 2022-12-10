package com.xinniu.android.qiqueqiao.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
//import android.support.v4.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.xinniu.android.qiqueqiao.R;
import com.xinniu.android.qiqueqiao.adapter.CoopDetailNeedeAdapter;
import com.xinniu.android.qiqueqiao.adapter.CoopDetailOfferAdapter;
import com.xinniu.android.qiqueqiao.base.BaseActivity;
import com.xinniu.android.qiqueqiao.bean.CoopDetailBean;
import com.xinniu.android.qiqueqiao.customs.label.FlowLayout;
import com.xinniu.android.qiqueqiao.customs.label.TagAdapter;
import com.xinniu.android.qiqueqiao.customs.label.TagFlowLayout;
import com.xinniu.android.qiqueqiao.request.RequestManager;
import com.xinniu.android.qiqueqiao.utils.ImageLoader;
import com.xinniu.android.qiqueqiao.utils.ShareUtils;
import com.xinniu.android.qiqueqiao.utils.ShowUtils;
import com.xinniu.android.qiqueqiao.utils.WebViewScreenShotUtils;
import com.xinniu.android.qiqueqiao.widget.CircleImageView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by BDXK on 2019/3/15.
 * project : newbridge--- android xs
 */

public class ShareNewThreeActivity extends BaseActivity {
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
    @BindView(R.id.view)
    View view_line;
    private View view01;
    private View view02;
    private View view03;
    ScrollView scrollView01;
    ScrollView scrollView02;
    ScrollView scrollView03;
    private int position = 1;//当前是选择第一个背景


    public static void start(Context context, String data, String ma) {
        Intent intent = new Intent(context, ShareNewThreeActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("data", data);
        bundle.putString("ma", ma);
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
        Bundle bundle = getIntent().getExtras();
        String data = bundle.getString("data");

        Gson gson = new Gson();

        CoopDetailBean bean = gson.fromJson(data, new TypeToken<CoopDetailBean>() {
        }.getType());

        String ma = bundle.getString("ma");
        imgBg01.setImageResource(R.mipmap.act_theme_bg_1);
        imgBg02.setImageResource(R.mipmap.act_theme_bg_2);
        imgBg03.setImageResource(R.mipmap.act_theme_bg_3);
        view_line.setVisibility(View.VISIBLE);
        showView(bean, ma);
        showView2(bean, ma);
        showView3(bean, ma);
    }

    private void showView(CoopDetailBean bean, String imgMaUrl) {
        view01 = View.inflate(ShareNewThreeActivity.this, R.layout.view_coop_detail_one, null);
        scrollView01 = (ScrollView) view01.findViewById(R.id.mshareWeb);
        CircleImageView imgHead = (CircleImageView) view01.findViewById(R.id.image_head);
        ImageView imgRenZ = (ImageView) view01.findViewById(R.id.mimage_isv);
        TextView tvName = (TextView) view01.findViewById(R.id.tv_mine_name);
        ImageView imgIsVip = (ImageView) view01.findViewById(R.id.mine_vipImg);
        TextView tvPosition = (TextView) view01.findViewById(R.id.tv_mine_position);
        TextView tvTitle = (TextView) view01.findViewById(R.id.tv_title);
        final TagFlowLayout coopMofferLabel = (TagFlowLayout) view01.findViewById(R.id.coop_moffer_label);
        TextView coopMofferContent = (TextView) view01.findViewById(R.id.coop_moffer_content);
        final TextView mofferTitletv = (TextView) view01.findViewById(R.id.moffer_titletv);
        TextView coopMofferTitle = (TextView) view01.findViewById(R.id.coop_moffer_title);
        ImageView imgMa = (ImageView) view01.findViewById(R.id.img_ma);
        RecyclerView rcyOffer = (RecyclerView) view01.findViewById(R.id.rcy_offer);


        ShowUtils.showImgPerfect(imgHead, bean.getHead_pic(), 1);
        ShowUtils.showTextPerfect(tvName, bean.getRealname());
        ShowUtils.showTextPerfect(tvPosition, bean.getCompany() + bean.getPosition());
        ShowUtils.showTextPerfect(tvTitle, "【" + bean.getCompany() + "】" + bean.getTitle());
        ImageLoader.loadImageGQ(imgMaUrl, imgMa);
        if (bean.getIs_v() == 1) {
            ShowUtils.showViewVisible(imgRenZ, View.VISIBLE);
        } else {
            ShowUtils.showViewVisible(imgRenZ, View.INVISIBLE);
        }
        int isVip = bean.getIs_vip();
        if (isVip == 0) {
            ShowUtils.showViewVisible(imgIsVip, View.GONE);

        } else if (isVip == 1) {
            ShowUtils.showBackgroud(imgIsVip, ContextCompat.getDrawable(ShareNewThreeActivity.this, R.mipmap.vip_iconx));

        } else if (isVip == 2) {
            ShowUtils.showBackgroud(imgIsVip, ContextCompat.getDrawable(ShareNewThreeActivity.this, R.mipmap.svip_iconx));

        }

        //判断是否有我提供
        if (bean.getProvide_category().size() > 0) {

            LinearLayoutManager manager2 = new LinearLayoutManager(ShareNewThreeActivity.this, LinearLayoutManager.VERTICAL, false);
            rcyOffer.setLayoutManager(manager2);
            CoopDetailOfferAdapter coopDetailOfferAdapter = new CoopDetailOfferAdapter(ShareNewThreeActivity.this, R.layout.item_coop_detail, bean.getProvide_category());
            rcyOffer.setAdapter(coopDetailOfferAdapter);
            rcyOffer.setNestedScrollingEnabled(false);
            if (!TextUtils.isEmpty(bean.getProvide_description_title())) {
                ShowUtils.showTextPerfect(coopMofferTitle, bean.getProvide_description_title());
            } else {
                // ShowUtils.showViewVisible(coopMofferTitle, View.GONE);
                ShowUtils.showTextPerfect(coopMofferTitle, "提供资源说明：");
            }

            if (!TextUtils.isEmpty(bean.getProvide_describe())) {
                ShowUtils.showTextPerfect(coopMofferContent, bean.getProvide_describe());
            } else {
                ShowUtils.showViewVisible(coopMofferContent, View.GONE);
            }
            ShowUtils.showTextPerfect(mofferTitletv, bean.getProvide_category_title(), new ShowUtils.setNullback() {
                @Override
                public void setNullback() {
                    //ShowUtils.showViewVisible(mofferTitletv, View.GONE);
                    mofferTitletv.setText("提供资源类型：");
                }
            });


        } else if (bean.getNeed_category().size() > 0) {
            LinearLayoutManager manager2 = new LinearLayoutManager(ShareNewThreeActivity.this, LinearLayoutManager.VERTICAL, false);
            rcyOffer.setLayoutManager(manager2);
            CoopDetailNeedeAdapter coopDetailOfferAdapter = new CoopDetailNeedeAdapter(ShareNewThreeActivity.this, R.layout.item_coop_detail, bean.getNeed_category());
            rcyOffer.setAdapter(coopDetailOfferAdapter);
            rcyOffer.setNestedScrollingEnabled(false);

            if (!TextUtils.isEmpty(bean.getNeed_category_title())) {
                ShowUtils.showTextPerfect(coopMofferTitle, bean.getNeed_description_title());

            } else {
                //  ShowUtils.showViewVisible(coopMofferTitle, View.GONE);
                ShowUtils.showTextPerfect(coopMofferTitle, "需求资源说明：");
            }


            if (!TextUtils.isEmpty(bean.getNeed_describe())) {
                ShowUtils.showTextPerfect(coopMofferContent, bean.getNeed_describe());

            } else {
                ShowUtils.showViewVisible(coopMofferContent, View.GONE);
            }

            ShowUtils.showTextPerfect(mofferTitletv, bean.getNeed_category_title(), new ShowUtils.setNullback() {
                @Override
                public void setNullback() {
                    // ShowUtils.showViewVisible(mofferTitletv, View.GONE);
                    mofferTitletv.setText("提供资源类型：");
                }
            });

        }

        llayoutRootView.removeAllViews();
        llayoutRootView.addView(view01);//默认选择第一条

    }

    private void showView2(CoopDetailBean bean, String imgMaUrl) {
        view02 = View.inflate(ShareNewThreeActivity.this, R.layout.view_coop_detail_two, null);
        scrollView02 = (ScrollView) view02.findViewById(R.id.mshareWeb);
        CircleImageView imgHead = (CircleImageView) view02.findViewById(R.id.image_head);
        ImageView imgRenZ = (ImageView) view02.findViewById(R.id.mimage_isv);
        TextView tvName = (TextView) view02.findViewById(R.id.tv_mine_name);
        ImageView imgIsVip = (ImageView) view02.findViewById(R.id.mine_vipImg);
        TextView tvPosition = (TextView) view02.findViewById(R.id.tv_mine_position);
        TextView tvTitle = (TextView) view02.findViewById(R.id.tv_title);
        final TagFlowLayout coopMofferLabel = (TagFlowLayout) view02.findViewById(R.id.coop_moffer_label);
        TextView coopMofferContent = (TextView) view02.findViewById(R.id.coop_moffer_content);
        final TextView mofferTitletv = (TextView) view02.findViewById(R.id.moffer_titletv);
        TextView coopMofferTitle = (TextView) view02.findViewById(R.id.coop_moffer_title);
        ImageView imgMa = (ImageView) view02.findViewById(R.id.img_ma);
        RecyclerView rcyOffer = (RecyclerView)view02.findViewById(R.id.rcy_offer);

        ShowUtils.showImgPerfect(imgHead, bean.getHead_pic(), 1);
        ShowUtils.showTextPerfect(tvName, bean.getRealname());
        ShowUtils.showTextPerfect(tvPosition, bean.getCompany() + bean.getPosition());
        ShowUtils.showTextPerfect(tvTitle, "【" + bean.getCompany() + "】" + bean.getTitle());
        ImageLoader.loadImageGQ(imgMaUrl, imgMa);
        if (bean.getIs_v() == 1) {
            ShowUtils.showViewVisible(imgRenZ, View.VISIBLE);
        } else {
            ShowUtils.showViewVisible(imgRenZ, View.INVISIBLE);
        }
        int isVip = bean.getIs_vip();
        if (isVip == 0) {
            ShowUtils.showViewVisible(imgIsVip, View.GONE);

        } else if (isVip == 1) {
            ShowUtils.showBackgroud(imgIsVip, ContextCompat.getDrawable(ShareNewThreeActivity.this, R.mipmap.vip_iconx));

        } else if (isVip == 2) {
            ShowUtils.showBackgroud(imgIsVip, ContextCompat.getDrawable(ShareNewThreeActivity.this, R.mipmap.svip_iconx));

        }

        //判断是否有我提供
        if (bean.getProvide_category().size() > 0) {

            LinearLayoutManager manager2 = new LinearLayoutManager(ShareNewThreeActivity.this, LinearLayoutManager.VERTICAL, false);
            rcyOffer.setLayoutManager(manager2);
            CoopDetailOfferAdapter coopDetailOfferAdapter = new CoopDetailOfferAdapter(ShareNewThreeActivity.this, R.layout.item_coop_detail, bean.getProvide_category());
            rcyOffer.setAdapter(coopDetailOfferAdapter);
            rcyOffer.setNestedScrollingEnabled(false);
            if (!TextUtils.isEmpty(bean.getProvide_description_title())) {
                ShowUtils.showTextPerfect(coopMofferTitle, bean.getProvide_description_title());
            } else {
                // ShowUtils.showViewVisible(coopMofferTitle, View.GONE);
                ShowUtils.showTextPerfect(coopMofferTitle, "提供资源说明：");
            }

            if (!TextUtils.isEmpty(bean.getProvide_describe())) {
                ShowUtils.showTextPerfect(coopMofferContent, bean.getProvide_describe());
            } else {
                ShowUtils.showViewVisible(coopMofferContent, View.GONE);
            }
            ShowUtils.showTextPerfect(mofferTitletv, bean.getProvide_category_title(), new ShowUtils.setNullback() {
                @Override
                public void setNullback() {
                    //ShowUtils.showViewVisible(mofferTitletv, View.GONE);
                    mofferTitletv.setText("提供资源类型：");
                }
            });


        } else if (bean.getNeed_category().size() > 0) {
            LinearLayoutManager manager2 = new LinearLayoutManager(ShareNewThreeActivity.this, LinearLayoutManager.VERTICAL, false);
            rcyOffer.setLayoutManager(manager2);
            CoopDetailNeedeAdapter coopDetailOfferAdapter = new CoopDetailNeedeAdapter(ShareNewThreeActivity.this, R.layout.item_coop_detail, bean.getNeed_category());
            rcyOffer.setAdapter(coopDetailOfferAdapter);
            rcyOffer.setNestedScrollingEnabled(false);

            if (!TextUtils.isEmpty(bean.getNeed_category_title())) {
                ShowUtils.showTextPerfect(coopMofferTitle, bean.getNeed_description_title());

            } else {
                //  ShowUtils.showViewVisible(coopMofferTitle, View.GONE);
                ShowUtils.showTextPerfect(coopMofferTitle, "需求资源说明：");
            }


            if (!TextUtils.isEmpty(bean.getNeed_describe())) {
                ShowUtils.showTextPerfect(coopMofferContent, bean.getNeed_describe());

            } else {
                ShowUtils.showViewVisible(coopMofferContent, View.GONE);
            }

            ShowUtils.showTextPerfect(mofferTitletv, bean.getNeed_category_title(), new ShowUtils.setNullback() {
                @Override
                public void setNullback() {
                    // ShowUtils.showViewVisible(mofferTitletv, View.GONE);
                    mofferTitletv.setText("提供资源类型：");
                }
            });

        }
        llayoutRootView.removeAllViews();
        llayoutRootView.addView(view01);//默认选择第一条

    }


    private void showView3(CoopDetailBean bean, String imgMaUrl) {
        view03 = View.inflate(ShareNewThreeActivity.this, R.layout.view_coop_detail_three, null);
        scrollView03 = (ScrollView) view03.findViewById(R.id.mshareWeb);
        CircleImageView imgHead = (CircleImageView) view03.findViewById(R.id.image_head);
        ImageView imgRenZ = (ImageView) view03.findViewById(R.id.mimage_isv);
        TextView tvName = (TextView) view03.findViewById(R.id.tv_mine_name);
        ImageView imgIsVip = (ImageView) view03.findViewById(R.id.mine_vipImg);
        TextView tvPosition = (TextView) view03.findViewById(R.id.tv_mine_position);
        TextView tvTitle = (TextView) view03.findViewById(R.id.tv_title);
        final TagFlowLayout coopMofferLabel = (TagFlowLayout) view03.findViewById(R.id.coop_moffer_label);
        TextView coopMofferContent = (TextView) view03.findViewById(R.id.coop_moffer_content);
        final TextView mofferTitletv = (TextView) view03.findViewById(R.id.moffer_titletv);
        TextView coopMofferTitle = (TextView) view03.findViewById(R.id.coop_moffer_title);
        ImageView imgMa = (ImageView) view03.findViewById(R.id.img_ma);
        RecyclerView rcyOffer = (RecyclerView)view03.findViewById(R.id.rcy_offer);

        ShowUtils.showImgPerfect(imgHead, bean.getHead_pic(), 1);
        ShowUtils.showTextPerfect(tvName, bean.getRealname());
        ShowUtils.showTextPerfect(tvPosition, bean.getCompany() + bean.getPosition());
        ShowUtils.showTextPerfect(tvTitle, "【" + bean.getCompany() + "】" + bean.getTitle());
        ImageLoader.loadImageGQ(imgMaUrl, imgMa);
        if (bean.getIs_v() == 1) {
            ShowUtils.showViewVisible(imgRenZ, View.VISIBLE);
        } else {
            ShowUtils.showViewVisible(imgRenZ, View.INVISIBLE);
        }
        int isVip = bean.getIs_vip();
        if (isVip == 0) {
            ShowUtils.showViewVisible(imgIsVip, View.GONE);

        } else if (isVip == 1) {
            ShowUtils.showBackgroud(imgIsVip, ContextCompat.getDrawable(ShareNewThreeActivity.this, R.mipmap.vip_iconx));

        } else if (isVip == 2) {
            ShowUtils.showBackgroud(imgIsVip, ContextCompat.getDrawable(ShareNewThreeActivity.this, R.mipmap.svip_iconx));

        }

        //判断是否有我提供
        if (bean.getProvide_category().size() > 0) {

            LinearLayoutManager manager2 = new LinearLayoutManager(ShareNewThreeActivity.this, LinearLayoutManager.VERTICAL, false);
            rcyOffer.setLayoutManager(manager2);
            CoopDetailOfferAdapter coopDetailOfferAdapter = new CoopDetailOfferAdapter(ShareNewThreeActivity.this, R.layout.item_coop_detail, bean.getProvide_category());
            rcyOffer.setAdapter(coopDetailOfferAdapter);
            rcyOffer.setNestedScrollingEnabled(false);
            if (!TextUtils.isEmpty(bean.getProvide_description_title())) {
                ShowUtils.showTextPerfect(coopMofferTitle, bean.getProvide_description_title());
            } else {
                // ShowUtils.showViewVisible(coopMofferTitle, View.GONE);
                ShowUtils.showTextPerfect(coopMofferTitle, "提供资源说明：");
            }

            if (!TextUtils.isEmpty(bean.getProvide_describe())) {
                ShowUtils.showTextPerfect(coopMofferContent, bean.getProvide_describe());
            } else {
                ShowUtils.showViewVisible(coopMofferContent, View.GONE);
            }
            ShowUtils.showTextPerfect(mofferTitletv, bean.getProvide_category_title(), new ShowUtils.setNullback() {
                @Override
                public void setNullback() {
                    //ShowUtils.showViewVisible(mofferTitletv, View.GONE);
                    mofferTitletv.setText("提供资源类型：");
                }
            });


        } else if (bean.getNeed_category().size() > 0) {
            LinearLayoutManager manager2 = new LinearLayoutManager(ShareNewThreeActivity.this, LinearLayoutManager.VERTICAL, false);
            rcyOffer.setLayoutManager(manager2);
            CoopDetailNeedeAdapter coopDetailOfferAdapter = new CoopDetailNeedeAdapter(ShareNewThreeActivity.this, R.layout.item_coop_detail, bean.getNeed_category());
            rcyOffer.setAdapter(coopDetailOfferAdapter);
            rcyOffer.setNestedScrollingEnabled(false);

            if (!TextUtils.isEmpty(bean.getNeed_category_title())) {
                ShowUtils.showTextPerfect(coopMofferTitle, bean.getNeed_description_title());

            } else {
                //  ShowUtils.showViewVisible(coopMofferTitle, View.GONE);
                ShowUtils.showTextPerfect(coopMofferTitle, "需求资源说明：");
            }


            if (!TextUtils.isEmpty(bean.getNeed_describe())) {
                ShowUtils.showTextPerfect(coopMofferContent, bean.getNeed_describe());

            } else {
                ShowUtils.showViewVisible(coopMofferContent, View.GONE);
            }

            ShowUtils.showTextPerfect(mofferTitletv, bean.getNeed_category_title(), new ShowUtils.setNullback() {
                @Override
                public void setNullback() {
                    // ShowUtils.showViewVisible(mofferTitletv, View.GONE);
                    mofferTitletv.setText("提供资源类型：");
                }
            });

        }

        llayoutRootView.removeAllViews();
        llayoutRootView.addView(view01);//默认选择第一条

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
                showBookingToast(2);
                Bitmap bitmap = null;
                if (position == 1) {
                    bitmap = WebViewScreenShotUtils.ActionScreenshot2(ShareNewThreeActivity.this, scrollView01);
                } else if (position == 2) {
                    bitmap = WebViewScreenShotUtils.ActionScreenshot2(ShareNewThreeActivity.this, scrollView02);
                } else if (position == 3) {
                    bitmap = WebViewScreenShotUtils.ActionScreenshot2(ShareNewThreeActivity.this, scrollView03);
                }
                ShareUtils.ShareLongImg(ShareNewThreeActivity.this, SHARE_MEDIA.WEIXIN, bitmap, new UMShareListener() {
                    @Override
                    public void onStart(SHARE_MEDIA share_media) {
                        dismissBookingToast();
                    }

                    @Override
                    public void onResult(SHARE_MEDIA share_media) {
                        dismissBookingToast();
                        RequestManager.getInstance().dailyShare(ShareNewThreeActivity.this);
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
                showBookingToast(2);
                Bitmap bitmapx = null;
                if (position == 1) {
                    bitmapx = WebViewScreenShotUtils.ActionScreenshot2(ShareNewThreeActivity.this, scrollView01);
                } else if (position == 2) {
                    bitmapx = WebViewScreenShotUtils.ActionScreenshot2(ShareNewThreeActivity.this, scrollView02);
                } else if (position == 3) {
                    bitmapx = WebViewScreenShotUtils.ActionScreenshot2(ShareNewThreeActivity.this, scrollView03);
                }
                ShareUtils.ShareLongImg(ShareNewThreeActivity.this, SHARE_MEDIA.WEIXIN_CIRCLE, bitmapx, new UMShareListener() {
                    @Override
                    public void onStart(SHARE_MEDIA share_media) {
                        dismissBookingToast();
                    }

                    @Override
                    public void onResult(SHARE_MEDIA share_media) {
                        dismissBookingToast();
                        RequestManager.getInstance().dailyShare(ShareNewThreeActivity.this);
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
            case R.id.balc_shareTv:
                Bitmap bitmapc = null;
                if (position == 1) {
                    bitmapc = WebViewScreenShotUtils.ActionScreenshot2(ShareNewThreeActivity.this, scrollView01);
                } else if (position == 2) {
                    bitmapc = WebViewScreenShotUtils.ActionScreenshot2(ShareNewThreeActivity.this, scrollView02);
                } else if (position == 3) {
                    bitmapc = WebViewScreenShotUtils.ActionScreenshot2(ShareNewThreeActivity.this, scrollView03);
                }
                boolean b = WebViewScreenShotUtils.isS(bitmapc, ShareNewThreeActivity.this);
                if (b) {
                    Toast.makeText(ShareNewThreeActivity.this, "保存成功", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(ShareNewThreeActivity.this, "保存失败", Toast.LENGTH_SHORT).show();
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
