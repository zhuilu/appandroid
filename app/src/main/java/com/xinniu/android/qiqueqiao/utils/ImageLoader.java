package com.xinniu.android.qiqueqiao.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;
import com.xinniu.android.qiqueqiao.R;

import java.io.File;

/**
 * Created by lzq on 2017/12/13.
 */

public class ImageLoader {
    private static Context mContext;

    public static void init(Context context) {
        mContext = context;
    }

    public static void loadImage(String Url, ImageView imageView) {

        if (!TextUtils.isEmpty(Url)) {
            if (Url.contains("https")) {
                Url = Url.replace("https", "http");
            }
            if (imageView == null) {
                return;
            }

            Glide.with(mContext).load(TextUtils.isEmpty(Url) ? "http:" : Url)
                    .error(R.mipmap.load_error)
                    .placeholder(R.mipmap.circle_avatar_placeholder)
                    .override(100, 100)
                    .into(imageView);


//        Picasso.with(mContext)
//                .load(TextUtils.isEmpty(Url)?"http:":Url)
//                .error(R.mipmap.load_error)
//                .placeholder(R.mipmap.circle_avatar_placeholder)
//                .resize(100,100)
//                .into(imageView);
//        ImageLoader.loadImage(Url,imageView);
        }
    }

    public static void loadCompanyCell(String url, ImageView imageView) {
        if (TextUtils.isEmpty(url)) {
            return;
        }
        Glide.with(mContext).load(TextUtils.isEmpty(url) ? "http:" : url)
                .override(100, 100)
                .into(imageView);
    }


    public static void loadAvter(String url, ImageView imageView) {
        if (TextUtils.isEmpty(url)) {
            return;
        }
        Glide.with(mContext).load(TextUtils.isEmpty(url) ? "http:" : url)
                .error(R.mipmap.home_avatar_placeholder)
                .override(100, 100)
                .into(imageView);

//        Picasso.with(mContext)
//                .load(TextUtils.isEmpty(url)?"http:":url)
//                .error(R.mipmap.load_error)
//                .resize(100,100)
//                .into(imageView);
//        ImageLoader.loadImage(url,imageView);
    }


    public static void loadCompanyHead(String url, ImageView imageView) {
        if (imageView == null) {
            return;
        }
        Glide.with(mContext).load(TextUtils.isEmpty(url) ? "http:" : url)
                .error(R.mipmap.default2_icon)
                .placeholder(R.mipmap.default2_icon)
                .into(imageView);
    }

    public static void loadImageGQ(String Url, ImageView imageView) {
        Glide.with(mContext).load(TextUtils.isEmpty(Url) ? "http:" : Url).into(imageView);

//        Picasso.with(mContext)
//                .load(TextUtils.isEmpty(Url)?"http:":Url)
//                .into(imageView);
    }

    public static void loadImageGQ1(final String Url, final ImageView imageView) {
        Glide.with(mContext)
                .load(TextUtils.isEmpty(Url) ? "http:" : Url)
                .asBitmap()
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                        //加载完成后的处理
                        imageView.setImageBitmap(resource);
                        LocalCacheUtils.setLocalCache(Url, resource);
                    }
                });


//        Picasso.with(mContext)
//                .load(TextUtils.isEmpty(Url)?"http:":Url)
//                .into(imageView);
    }

    public static void loadImageCpGQ(String Url, ImageView imageView) {
        Glide.with(mContext).load(TextUtils.isEmpty(Url) ? "http:" : Url).listener(new RequestListener<String, GlideDrawable>() {
            @Override
            public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                return false;
            }

            @Override
            public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                return false;
            }
        })
                .crossFade(2000)
                .into(imageView);
    }


    public static void loadBanner(Context context, final String url, final ImageView imageView) {
        if (TextUtils.isEmpty(url)) {
            return;
        }
        if (imageView == null) {
            return;
        }
        // Glide.with(mContext).load(url).centerCrop().into(imageView);
        if (LocalCacheUtils.getLocalCache(url) != null) {
            imageView.setImageBitmap(LocalCacheUtils.getLocalCache(url));
        } else {
            Glide.with(mContext)
                    .load(url)
                    .asBitmap()
                    .into(new SimpleTarget<Bitmap>() {
                        @Override
                        public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                            //加载完成后的处理
                            imageView.setImageBitmap(resource);
                            //   LocalCacheUtils.setLocalCache(url, resource);
                        }
                    });
        }
    }

    public static void loadBanner2(Context context, final String url, final ImageView imageView) {
        if (TextUtils.isEmpty(url)) {
            return;
        }
        if (imageView == null) {
            return;
        }
        Glide.with(mContext).load(url).centerCrop().into(imageView);

    }

    public static void loadBanner3(Context context, final String url, final ImageView imageView) {
        if (TextUtils.isEmpty(url)) {
            return;
        }
        if (imageView == null) {
            return;
        }
        Glide.with(mContext).load(url).centerCrop().into(imageView);

    }

    public static void loadLocalImg(String Url, ImageView imageView) {
        if (!TextUtils.isEmpty(Url)) {
            if (!Url.startsWith("http")) {
                Glide.with(mContext)
                        .load(TextUtils.isEmpty(Url) ? "http:" : "file://" + Url)
                        .placeholder(R.mipmap.load_error)
                        //    .override(150,150)
                        .into(imageView);
//                Picasso.with(mContext)
//                        .load(TextUtils.isEmpty(Url) ? "http:" : "file://" + Url)
//                        .into(imageView);
            } else {
                Glide.with(mContext)
                        .load(TextUtils.isEmpty(Url) ? "http:" : Url)
                        .into(imageView);
//                Picasso.with(mContext)
//                        .load(TextUtils.isEmpty(Url) ? "http:" : Url)
//                        .into(imageView);
            }
        }

    }

    public static void loadLocalImg1(String Url, ImageView imageView) {
        if (!TextUtils.isEmpty(Url)) {
            if (!Url.startsWith("http")) {
                Glide.with(mContext)
                        .load(TextUtils.isEmpty(Url) ? "http:" : "file://" + Url)
                        .placeholder(R.mipmap.load_error)
                        .override(150, 150)
                        .into(imageView);
//                Picasso.with(mContext)
//                        .load(TextUtils.isEmpty(Url) ? "http:" : "file://" + Url)
//                        .into(imageView);
            } else {
                Glide.with(mContext)
                        .load(TextUtils.isEmpty(Url) ? "http:" : Url)
                        .into(imageView);
//                Picasso.with(mContext)
//                        .load(TextUtils.isEmpty(Url) ? "http:" : Url)
//                        .into(imageView);
            }
        }

    }


    public static void loadActImage(String url, ImageView imageView) {
        if (TextUtils.isEmpty(url)) {
            return;
        }
        if (imageView == null) {
            return;
        }
        Glide.with(mContext).load(url).fitCenter().into(imageView);
    }

    public static Bitmap getBitmap(String url) {
        final Bitmap[] bitmap = {null};
        Glide.with(mContext).load(url).asBitmap().into(new SimpleTarget<Bitmap>() {
            @Override
            public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                bitmap[0] = resource;
            }
        });


        return bitmap[0];

    }

    /**
     * 获取图片的Uri
     */
    public static Uri getImageUrl2Uri(String imgUrl) {
        Uri uri = null;

//           FutureTarget<File> future = Glide.with(mContext).load(imgUrl).downloadOnly(500,500);
//           File cacheFile = future.get();
//           String path = cacheFile.getAbsolutePath();
        uri = Uri.fromFile(new File(imgUrl));

        return uri;
    }

    /**
     * 生成视图的预览
     */
    public static Bitmap toImage(Activity activity, View v) {
        Bitmap bitmap;
        View view = activity.getWindow().getDecorView();
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();
        bitmap = view.getDrawingCache();
        return bitmap;

    }


}
