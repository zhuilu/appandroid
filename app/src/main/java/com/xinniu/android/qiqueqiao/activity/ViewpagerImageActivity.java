package com.xinniu.android.qiqueqiao.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;

import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.xinniu.android.qiqueqiao.R;
import com.xinniu.android.qiqueqiao.base.BaseActivity;
import com.xinniu.android.qiqueqiao.customs.SimpleColorViewIndicate;
import com.xinniu.android.qiqueqiao.dialog.DeleteReplyDialog;
import com.xinniu.android.qiqueqiao.fragment.ImageFragment;
import com.xinniu.android.qiqueqiao.utils.BitmapUtils;
import com.xinniu.android.qiqueqiao.utils.ToastUtils;

import java.util.ArrayList;

import butterknife.BindView;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

import androidx.fragment.app.Fragment;
//import android.support.v4.app.FragmentManager;
//import android.support.v4.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

/**
 * Created by lzq on 2018/2/7.
 * 照片放大
 */

public class ViewpagerImageActivity extends BaseActivity {

    private static final int PERMISSION_WRITE_EXTERNAL_STORAGE = 1000;

    @BindView(R.id.m_viewpager)
    ViewPager mViewPager;
    public final static String IMAGE_URL_TAG = "IMAGE_URL_TAG";
    public final static String IMAGE_URL_POSITiON_TAG = "IMAGE_URL_POSITiON_TAG";
    @BindView(R.id.id_new_indicator)
    SimpleColorViewIndicate idNewIndicator;
    @BindView(R.id.big_image_bg_rl)
    RelativeLayout bigImageBgRl;
    private ArrayList<String> mList;
    private int showPosition = 0;
    private Bitmap mQRBitmap;

    @Override
    public int getLayoutId() {
        return R.layout.activity_image_viewpager;
    }

    public static void start(Context context, ArrayList<String> list, int position) {
        Intent starter = new Intent(context, ViewpagerImageActivity.class);
        starter.putStringArrayListExtra(IMAGE_URL_TAG, list);
        starter.putExtra(IMAGE_URL_POSITiON_TAG, position);
        context.startActivity(starter);

    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        topStatusBar(true);

        mList = getIntent().getStringArrayListExtra(IMAGE_URL_TAG);
        showPosition = getIntent().getIntExtra(IMAGE_URL_POSITiON_TAG, 0);

        ImageAdapter adapter = new ImageAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(adapter);
        idNewIndicator.notifyDataSetChanged(mList.size());
        mViewPager.setCurrentItem(showPosition, false);
        idNewIndicator.setSelectedPosition(showPosition);
        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                idNewIndicator.setSelectedPosition(position);

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });



    }

    private class ImageAdapter extends FragmentStatePagerAdapter {

        public ImageAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            ImageFragment imageFragment = ImageFragment.newInstance(mList.get(position));
            imageFragment.setSetOnClickLister(new ImageFragment.setOnClickLister() {
                @Override
                public void setFinish() {
                    finish();
                    overridePendingTransition(0, R.anim.main_fade_out);
                }

                @Override
                public void setLong(final String url) {
                    final DeleteReplyDialog dialog = new DeleteReplyDialog("发送给好友", "保存图片", "取消");
                    dialog.setSetOnClick(new DeleteReplyDialog.setOnClick() {
                        @Override
                        public void setOnClickLeft() {
//                            AlertDialogUtils.AlertDialog(ViewpagerImageActivity.this,"确定发送","确定将此图片发送给");
//                        Uri imgUri =  ImageLoader.getImageUrl2Uri(url);
                            FriendLxActivity.startx(ViewpagerImageActivity.this, FriendLxActivity.COOPPHOTO, url);

                        }

                        @Override
                        public void setOnClickMiddle() {
                            Glide.with(ViewpagerImageActivity.this).asBitmap().load(url).into(new SimpleTarget<Bitmap>() {
                                @Override
                                public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                                    mQRBitmap = resource;
                                    requestPermission();
                                }
                            });
                        }

                        @Override
                        public void setOnClickRight() {
                            dialog.dismiss();
                        }

                        @Override
                        public void theOnDismiss(int type) {

                        }
                    });
                    dialog.show(getSupportFragmentManager(), "viewphoto");
                }
            });
            return imageFragment;
        }

        @Override
        public int getCount() {
            return mList.size();
        }

    }

    /**
     * 获取权限保存图片
     */
    @AfterPermissionGranted(PERMISSION_WRITE_EXTERNAL_STORAGE)
    private void requestPermission() {
        if (EasyPermissions.hasPermissions(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            if (mQRBitmap == null) {
                return;
            }
            BitmapUtils.saveImageToGallery(mContext, mQRBitmap);
            ToastUtils.showSuccessfulToast(mContext, "保存图片成功");
        } else {
            EasyPermissions.requestPermissions(this, getString(R.string.permission_need_save_bitmap),
                    PERMISSION_WRITE_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
    }



}
