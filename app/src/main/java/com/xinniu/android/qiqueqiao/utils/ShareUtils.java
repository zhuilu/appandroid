package com.xinniu.android.qiqueqiao.utils;

import android.graphics.Bitmap;

import androidx.appcompat.app.AppCompatActivity;

import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMMin;
import com.umeng.socialize.media.UMWeb;
import com.xinniu.android.qiqueqiao.R;

import java.io.File;

/**
 * Created by lzq on 2017/12/18.
 */

public class ShareUtils {
    public static void shareText(AppCompatActivity activity, SHARE_MEDIA shareMedia, String content, UMShareListener shareListener){
        if (!isInstall(activity,shareMedia)){
            return;
        }
        new ShareAction(activity)
                .setPlatform(SHARE_MEDIA.QQ)//传入平台
                .withText(content)//分享内容
                .setCallback(shareListener)//回调监听器
                .share();
    }

    public static void shareWebUrl(AppCompatActivity activity, String headUrl, SHARE_MEDIA shareMedia, String url, String title, String description, UMShareListener shareListener){
        if (!isInstall(activity,shareMedia)){
            ToastUtils.showCentetImgToast(activity,"应用未安装");
            return;
        }
        if(url == null || title == null||description == null){
            return;
        }
        UMWeb web = new UMWeb(url);
        if(StringUtils.isEmpty(headUrl)){
            //mController.setShareMedia(new UMImage(getActivity(), R.drawable.icon));
            UMImage thumb = new UMImage(activity, R.mipmap.default_head_img);
            web.setThumb(thumb);  //缩略图
        }else {
            UMImage thumb = new UMImage(activity, headUrl);
            web.setThumb(thumb);  //缩略图
        }

        web.setTitle(title);//标题

        web.setDescription(description);//描述
        new ShareAction(activity)
                .setPlatform(shareMedia)//传入平台
                .withMedia(web)//分享内容
                .setCallback(shareListener)//回调监听器
                .share();
    }
    public static void shareWebUrl(AppCompatActivity activity, SHARE_MEDIA shareMedia, String url, String title, String description, UMShareListener shareListener){
        if (!isInstall(activity,shareMedia)){
//            ToastUtils.showCentetImgToast(activity,"应用未安装");
            return;
        }
        if(url == null || title == null||description == null){
            return;
        }
        UMImage thumb =  new UMImage(activity, R.mipmap.ic_launcher);
        UMWeb web = new UMWeb(url);
        web.setTitle(title);//标题
        web.setThumb(thumb);  //缩略图
        web.setDescription(description);//描述
        new ShareAction(activity)
                .setPlatform(shareMedia)//传入平台
                .withMedia(web)//分享内容
                .setCallback(shareListener)//回调监听器
                .share();
    }
    public static void shareImg(AppCompatActivity activity, SHARE_MEDIA shareMedia, File file, UMShareListener shareListener){
        if (!isInstall(activity,shareMedia)){
            return;
        }
        UMImage thumb =  new UMImage(activity, R.mipmap.ic_launcher);
        UMImage image = new UMImage(activity, file);
        image.setThumb(thumb);
        new ShareAction(activity)
                .setPlatform(shareMedia)//传入平台
                .withMedia(image)//分享内容
                .setCallback(shareListener)//回调监听器
                .share();
    }
    public static boolean isInstall(AppCompatActivity context, SHARE_MEDIA shareMedia){
        if (UMShareAPI.get(context).isInstall(context,shareMedia)){
            return true;
        }else{
            return false;
        }
    }
    public static void shareWxMini(AppCompatActivity activity, SHARE_MEDIA shareMedia, String shareUrl, String title, String description, String miniUrl, UMShareListener shareListener){
        UMMin umMin = new UMMin(shareUrl);
        UMImage imagelocal = new UMImage(activity,R.mipmap.app_white_share);
//兼容低版本的网页链接
        umMin.setThumb(imagelocal);
// 小程序消息封面图片
        umMin.setTitle(title);
// 小程序消息title
        umMin.setDescription(description);
// 小程序消息描述
        umMin.setPath(miniUrl);
//小程序页面路径
        umMin.setUserName("gh_7ba789be1f8a");
// 小程序原始id,在微信平台查询
        new ShareAction(activity)
                .withMedia(umMin)
                .setPlatform(shareMedia)
                .setCallback(shareListener).share();
    }

    public static void ShareLongImg(AppCompatActivity activity, SHARE_MEDIA share_media, Bitmap bitmap, UMShareListener shareListener){
        //分享图片初始化
        UMImage image = new UMImage(activity,bitmap);
        //分享图片
        image.compressStyle= UMImage.CompressStyle.QUALITY;
        new ShareAction(activity).withMedia(image).setPlatform(share_media).setCallback(shareListener).share();

    }

    public static void ShareImg(AppCompatActivity activity, SHARE_MEDIA share_media, String bitmap, UMShareListener shareListener){
        //分享图片初始化
        UMImage image = new UMImage(activity,bitmap);
        //分享图片
        image.compressStyle= UMImage.CompressStyle.QUALITY;
        new ShareAction(activity).withMedia(image).setPlatform(share_media).setCallback(shareListener).share();

    }






}
