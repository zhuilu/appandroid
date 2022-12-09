package com.xinniu.android.qiqueqiao.utils;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.xinniu.android.qiqueqiao.R;

/**
 * Created by yuchance on 2018/5/22.
 */

public class ShowUtils {



    public static void showTextPerfect(TextView textView, String text){
        if (textView==null){
            return;
        }

        if (!TextUtils.isEmpty(text)){
                textView.setText(text);
        }else {
                textView.setText("");
        }
    }
    public static void showTextPerfect(TextView textView, CharSequence text){
        if (textView!=null){
            if (!TextUtils.isEmpty(text)){
                textView.setText(text);
            }else {
                textView.setText("");
            }
        }
    }
    public static void showTextPerfect(TextView textView, int resId){
        if (textView!=null){
            textView.setText(resId);
        }
    }
    public static void showTextPerfect(TextView textView, String text,setNullback setNullback){
        if (textView==null){
            return;
        }

        if (!TextUtils.isEmpty(text)){
            textView.setText(text);
        }else {
            textView.setText("");
            setNullback.setNullback();
        }
    }
    public interface setNullback{
        void setNullback();
    }
    private setNullback setNullback;

    public void setSetNullback(ShowUtils.setNullback setNullback) {
        this.setNullback = setNullback;
    }

    public static void showBackgroud(View view, Drawable backgroud){
        if (view!=null&&backgroud!=null){
            view.setBackground(backgroud);
        }
    }
    public static void showTextColor(TextView view,int color){
        if (view!=null){
            view.setTextColor(color);
        }
    }
    public static void showSelected(View view,boolean isSelected){
        if (view!=null){
            view.setSelected(isSelected);
        }
    }
    public static void showUnClick(View view,boolean isSelected){
        if (view!=null){
            view.setClickable(isSelected);
        }
    }

    public static void showImgPerfect(ImageView imageView,String imgUrl,int type){

        switch (type){
            //用户头像
            case 1:
                if (imageView !=null) {
                    if (!TextUtils.isEmpty(imgUrl)) {
                        if (imgUrl.contains("https")){
                           imgUrl = imgUrl.replace("https","http");
                        }
                        ImageLoader.loadAvter(imgUrl, imageView);
                    }
                }
                break;
            //公司头像
            case 2:
                if (imageView !=null) {
                    if (!TextUtils.isEmpty(imgUrl)) {
                        ImageLoader.loadCompanyHead(imgUrl, imageView);
                    }
                }
                break;
            //大图
            case 3:
                if (imageView !=null) {
                    if (!TextUtils.isEmpty(imgUrl)) {
                        ImageLoader.loadLocalImg(imgUrl, imageView);
                    }
                }
                break;
            //其他图片
            default:
                if (imageView !=null) {
                    if (!TextUtils.isEmpty(imgUrl)) {
                        ImageLoader.loadImageGQ(imgUrl, imageView);
                    }
                }
                break;
        }
    }
    public static void showViewVisible(View view,int visibily){
        if (view!=null){
            view.setVisibility(visibily);
        }
    }



    public static void searchLineShow(String content,String keyWord,int lineNum,TextView onTv){
        int a = content.indexOf(keyWord);
        String onWatchStr = content.substring(a);
        onTv.setMaxLines(5);
        onTv.setEllipsize(TextUtils.TruncateAt.END);
        if (onWatchStr.length()>lineNum*5){
            ShowUtils.showTextPerfect(onTv,StringUtils.strToSpannableStr(onWatchStr,keyWord));
        }else if (onWatchStr.length()<lineNum*5&&onWatchStr.length()>lineNum*4){
            if (a>lineNum){
                onWatchStr = content.substring(a+lineNum);
                ShowUtils.showTextPerfect(onTv,StringUtils.strToSpannableStr(onWatchStr,keyWord));
            }else {
                ShowUtils.showTextPerfect(onTv,StringUtils.strToSpannableStr(content,keyWord));
            }
        }else if (onWatchStr.length()<lineNum*4&&onWatchStr.length()>lineNum*3){
            if (a>lineNum*2){
                onWatchStr = content.substring(a-(lineNum*2));
                ShowUtils.showTextPerfect(onTv,StringUtils.strToSpannableStr(onWatchStr,keyWord));
            }else {
                ShowUtils.showTextPerfect(onTv,StringUtils.strToSpannableStr(content,keyWord));
            }
        }else if (onWatchStr.length()<lineNum*3&&onWatchStr.length()>lineNum*2){
            if (a>lineNum*3){
                onWatchStr = content.substring(a-(lineNum*3));
                ShowUtils.showTextPerfect(onTv,StringUtils.strToSpannableStr(onWatchStr,keyWord));
            }else {
                ShowUtils.showTextPerfect(onTv,StringUtils.strToSpannableStr(content,keyWord));
            }
        }else if (onWatchStr.length()<lineNum*2&&onWatchStr.length()>lineNum*1){
            if (a>lineNum*4){
                onWatchStr = content.substring(a-(lineNum*4));
                ShowUtils.showTextPerfect(onTv,StringUtils.strToSpannableStr(onWatchStr,keyWord));
            }else {
                ShowUtils.showTextPerfect(onTv,StringUtils.strToSpannableStr(content,keyWord));
            }
        }else if (onWatchStr.length()<lineNum*1){
            if (a>lineNum*5){
                onWatchStr = content.substring(a-(lineNum*5));
                ShowUtils.showTextPerfect(onTv,StringUtils.strToSpannableStr(onWatchStr,keyWord));
            }else {
                ShowUtils.showTextPerfect(onTv,StringUtils.strToSpannableStr(content,keyWord));
            }
        }else {
            ShowUtils.showTextPerfect(onTv,StringUtils.strToSpannableStr(content,keyWord));
        }
    }
    public static void showImageResource(ImageView view,int resId){
        if (view == null){
            return;
        }
        view.setImageResource(resId);
    }
    private static Dialog mDialog;
    private static View view;

    public static void showBookingToast(Context context,int type) {
        if (mDialog!=null&&mDialog.isShowing()){
            mDialog.dismiss();
        }

        mDialog = new Dialog( context , R.style.Them_dialog);
        LayoutInflater inflater = LayoutInflater.from( context );
        if (type==1) {
            view = inflater.inflate(R.layout.book_loading, null);
        }else if (type==2){
            view = inflater.inflate(R.layout.read_loading, null);
        }else {
            view = inflater.inflate(R.layout.book_loading, null);
        }
        mDialog.setContentView(view);
        mDialog.setCanceledOnTouchOutside( false );
        mDialog.show();
    }
    public static void dismissBookingToast(){

        if( mDialog != null && mDialog.isShowing()){
            mDialog.dismiss();
        }
    }






}
