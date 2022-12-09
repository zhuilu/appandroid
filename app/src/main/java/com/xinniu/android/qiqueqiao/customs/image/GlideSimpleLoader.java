package com.xinniu.android.qiqueqiao.customs.image;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.Uri;
//import android.support.annotation.NonNull;
//import android.support.annotation.Nullable;
import android.transition.Transition;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;

public class GlideSimpleLoader implements ImageWatcher.Loader {
    @Override
    public void load(Context context, Uri uri, final ImageWatcher.LoadCallback lc) {
        Glide.with(context).load(uri)
                .into(
//                        new SimpleTarget<Drawable>() {
//                    @Override
//                    public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
//                        lc.onResourceReady(resource);
//                    }
//
//                    @Override
//                    public void onLoadFailed(@Nullable Drawable errorDrawable) {
//                        lc.onLoadFailed(errorDrawable);
//                    }
//
//                    @Override
//                    public void onLoadStarted(@Nullable Drawable placeholder) {
//                        lc.onLoadStarted(placeholder);
//                    }
//                }

                        new SimpleTarget<GlideDrawable>() {
                            @Override
                            public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> glideAnimation) {
                                lc.onResourceReady(resource);
                            }
                        }
                );
    }
}
