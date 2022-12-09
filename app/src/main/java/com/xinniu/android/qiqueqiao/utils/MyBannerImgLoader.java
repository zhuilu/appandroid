package com.xinniu.android.qiqueqiao.utils;


import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.youth.banner.loader.*;

/**
 * Created by yuchance on 2018/6/22.
 */

public class MyBannerImgLoader extends com.youth.banner.loader.ImageLoader {

    @Override
    public void displayImage(Context context, Object path, ImageView imageView) {
        ImageLoader.loadBanner(context,path.toString(),imageView);
    }
    @Override
    public ImageView createImageView(Context context) {
        return super.createImageView(context);
    }

}
