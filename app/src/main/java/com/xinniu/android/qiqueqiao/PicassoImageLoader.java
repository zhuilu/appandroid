//package com.xinniu.android.qiqueqiao;
//
//import android.app.Activity;
//import android.net.Uri;
//import android.widget.ImageView;
//
//import com.lzy.imagepicker.loader.ImageLoader;
//import com.squareup.picasso.MemoryPolicy;
//import com.squareup.picasso.Picasso;
//
//import java.io.File;
//
///**
// * Created by lzq on 2018/1/22.
// */
//
//public class PicassoImageLoader implements ImageLoader {
//
//    @Override                         activity            path            imageView
//    public void displayImage(Activity          ,   String      , ImageView            , int width, int height) {
//        Picasso.with(activity)//
//                .load(Uri.fromFile(new File(path)))//
//                .placeholder(R.mipmap.default_image)//
//                .error(R.mipmap.default_image)//
//                .resize(width, height)//
//                .centerInside()//
//                .memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE)//
//                .into(imageView);
//    }
//
//    @Override
//    public void clearMemoryCache() {
//        //这里是清除缓存的方法,根据需要自己实现
//    }
//}
