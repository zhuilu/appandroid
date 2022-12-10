package com.xinniu.android.qiqueqiao.utils;

import static android.os.Environment.DIRECTORY_PICTURES;

import android.Manifest;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;

import com.xinniu.android.qiqueqiao.BuildConfig;

import java.io.File;

import androidx.fragment.app.Fragment;
//import android.support.v4.content.FileProvider;

/**
 * Created by qinlei
 * Created on 2017/12/19
 * Created description :
 */

public class TokePhotoUtils {

    public static final int PERMISSION_TOKE_PHOTO = 1000;
    public static final int CAMERA_REQUEST = 200;
    public static final int GALLERY_REQUEST = 201;
    public static final int CROP_REQUEST = 202;

    public static final String[] TOKE_PHOTO = {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA,Manifest.permission.READ_EXTERNAL_STORAGE};

    private Uri imgUri;
    private File imgFile;
    private Context mContext;

    private static TokePhotoUtils instance;

    private TokePhotoUtils(Context context) {
        mContext = context;
        initImgUri();
    }

    public static TokePhotoUtils getInstance(Context context) {
        if (instance == null) {
            instance = new TokePhotoUtils(context);
        }
        return instance;
    }

    public Uri getImgUri() {
        return imgUri;
    }

    public File getImgFile() {
        return imgFile;
    }

    private void initImgUri() {
        if (imgUri == null) {
            imgFile = getImageFile();
            imgUri = FileProvider.getUriForFile(mContext,BuildConfig.APPLICATION_ID + ".FileProvider", imgFile);
        }
    }

    private File getImageFile() {
        File outputImage = new File(mContext.getExternalFilesDir(DIRECTORY_PICTURES),
                System.currentTimeMillis()+"head.jpg");
        if (outputImage!=null) {
            if (outputImage.getParentFile() != null) {
                if (!outputImage.getParentFile().exists()) {
                    outputImage.getParentFile().mkdir();
                }
            }
        }
        return outputImage;
    }

    /**
     * 拍照
     * @param context
     */
    public void tokePhoto(AppCompatActivity context) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imgUri);
        context.startActivityForResult(intent, CAMERA_REQUEST);
    }

    /**
     * 拍照
     * @param context
     */
    public void tokePhotoTwo(AppCompatActivity context) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imgUri);
        context.startActivityForResult(intent, CAMERA_REQUEST);
    }

    /**
     * 拍照
     * @param context
     */
    public void tokePhoto(Fragment context) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imgUri);
        context.startActivityForResult(intent, CAMERA_REQUEST);
    }

    /**
     * 相册选择
     * @param context
     */
    public void chooseGallary(AppCompatActivity context) {
        Intent intent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        context.startActivityForResult(intent, GALLERY_REQUEST);
    }

    /**
     * 相册选择
     * @param context
     */
    public void chooseGallary(Fragment context) {
        Intent intent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        context.startActivityForResult(intent, GALLERY_REQUEST);
    }
    /**
     * 裁剪长方形的图片
     * @param context
     * @param uri
     * @param request
     */
    public void cropRawPhotoTwo(AppCompatActivity context, Uri uri, int request) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        if (request == CAMERA_REQUEST) {
            intent.setDataAndType(uri, "image/*");
        } else if (request == GALLERY_REQUEST) {
            intent.setDataAndType(getImageContentUri(mContext, getRealPathFromURI(mContext, uri)), "image/*");
        }
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 2);
        intent.putExtra("aspectY", 1);
        //设置裁剪框高宽
        intent.putExtra("return-data", false);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(imgFile));
        context.startActivityForResult(intent, CROP_REQUEST);
    }


    /**
     * 裁剪正方形的图片
     * @param context
     * @param uri
     * @param request
     */
    public void cropRawPhoto(AppCompatActivity context, Uri uri, int request) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        if (request == CAMERA_REQUEST) {
            intent.setDataAndType(uri, "image/*");
        } else if (request == GALLERY_REQUEST) {
            intent.setDataAndType(getImageContentUri(mContext, getRealPathFromURI(mContext, uri)), "image/*");
        }
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("return-data", false);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(imgFile));
        context.startActivityForResult(intent, CROP_REQUEST);
    }


    /**
     * 多图裁剪正方形的图片
     * @param context
     * @param uri
     * @param request
     */
    public void cropRawPhotoThree(AppCompatActivity context, Uri uri, int request) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        if (request == CAMERA_REQUEST) {
            intent.setDataAndType(uri, "image/*");
        } else if (request == GALLERY_REQUEST) {
            intent.setDataAndType(getImageContentUri(mContext, getRealPathFromURI(mContext, uri)), "image/*");
        }
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("return-data", false);
        imgFile = getImageFile();
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(imgFile));
        context.startActivityForResult(intent, CROP_REQUEST);
    }

    /**
     * 裁剪正方形的图片
     * @param context
     * @param uri
     * @param request
     */
    public void cropRawPhoto(Fragment context, Uri uri, int request) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        if (request == CAMERA_REQUEST) {
            intent.setDataAndType(uri, "image/*");
        } else if (request == GALLERY_REQUEST) {
            intent.setDataAndType(getImageContentUri(mContext, getRealPathFromURI(mContext, uri)), "image/*");
        }
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("return-data", false);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(imgFile));
        context.startActivityForResult(intent, CROP_REQUEST);
    }

    /**
     * 裁剪正方形的图片
     * @param context
     * @param uri
     * @param request
     */
    public void cropPhoto(Fragment context, Uri uri, int request) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        if (request == CAMERA_REQUEST) {
            intent.setDataAndType(uri, "image/*");
        } else if (request == GALLERY_REQUEST) {
            intent.setDataAndType(getImageContentUri(mContext, getRealPathFromURI(mContext, uri)), "image/*");
        }
        intent.putExtra("crop", "true");
        intent.putExtra("return-data", false);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(imgFile));
        context.startActivityForResult(intent, CROP_REQUEST);
    }


    public String getRealPathFromURI(Context context, Uri contentURI) {
        String result;
        Cursor cursor = context.getContentResolver().query(contentURI, null, null, null, null);
        if (cursor == null) {
            result = contentURI.getPath();
        } else {
            cursor.moveToFirst();
            int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            if (idx == -1) {
            }
            result = cursor.getString(idx);
            cursor.close();
        }
        return result;
    }

    private Uri getImageContentUri(Context context, String path) {
        Cursor cursor = context.getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                new String[]{MediaStore.Images.Media._ID},
                MediaStore.Images.Media.DATA + "=? ",
                new String[]{path}, null);
        if (cursor != null && cursor.moveToFirst()) {
            int id = cursor.getInt(cursor.getColumnIndex(MediaStore.Images.Media._ID));
            Uri baseUri = Uri.parse("content://media/external/images/media");
            return Uri.withAppendedPath(baseUri, "" + id);
        } else {
            ContentValues contentValues = new ContentValues(1);
            contentValues.put(MediaStore.Images.Media.DATA, path);
            return context.getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues);
        }
    }
}
