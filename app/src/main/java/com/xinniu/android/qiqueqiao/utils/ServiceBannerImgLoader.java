package com.xinniu.android.qiqueqiao.utils;


import android.content.Context;
import android.widget.ImageView;

import com.xinniu.android.qiqueqiao.activity.ServiceDetailActivity;

/**
 * Created by yuchance on 2018/6/22.
 */

public class ServiceBannerImgLoader extends com.youth.banner.loader.ImageLoader {

    @Override
    public void displayImage(Context context, Object path, ImageView imageView) {
        ImageLoader.loadBanner3(context, path.toString(), imageView);

    }

    @Override
    public ImageView createImageView(Context context) {
        RoundImageView imageView = new RoundImageView(context);
        imageView.setmBorderRadius(8);
        return imageView;
        //  return super.createImageView(context);
    }

}
