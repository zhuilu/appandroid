package com.xinniu.android.qiqueqiao.activity;

import android.content.Intent;
import android.media.ExifInterface;
import android.os.Bundle;
import androidx.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.litao.android.lib.BaseGalleryActivity;
import com.litao.android.lib.Configuration;
import com.litao.android.lib.entity.PhotoEntry;
import com.xinniu.android.qiqueqiao.R;
import com.xinniu.android.qiqueqiao.customs.qldialog.QLTipDialog;
import com.xinniu.android.qiqueqiao.utils.StatusBarUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lzq on 2018/1/25.
 * 添加图片
 */

public class AddPictruActivity extends BaseGalleryActivity implements View.OnClickListener {
    public List<PhotoEntry> mSelectedPhotos = new ArrayList<>();
    private ArrayList<String> fromList;
    private TextView addSuccess;
    private ImageView closeBt;
    private int size = 0;
    public final static String PHOTO_LIST = "photo_list";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_take_photo);
        StatusBarUtil.StatusBarLightMode(this,true);
        attachFragment(R.id.gallery_root);
        fromList = getIntent().getStringArrayListExtra(PHOTO_LIST);
        size = fromList.size();
        addSuccess = (TextView) findViewById(R.id.add_success);
        addSuccess.setOnClickListener(this);
        closeBt = (ImageView) findViewById(R.id.bt_close);
        closeBt.setOnClickListener(this);
    }


    @Override
    public Configuration getConfiguration() {
        Configuration cfg = new Configuration.Builder()
                .hasCamera(false)
                .hasShade(true)
                .hasPreview(true)
                .setSpaceSize(8)
                .setPhotoMaxWidth(120)
                .setCheckBoxColor(0xFF2DA0FB)
                .setDialogHeight(Configuration.DIALOG_HALF)
                .setDialogMode(Configuration.DIALOG_LIST)
                .setMaximum(8 - size)
                .setTip(null)
                .setAblumsTitle(null)
                .build();
        return cfg;
    }

    @Override
    public List<PhotoEntry> getSelectPhotos() {
        return mSelectedPhotos;
    }

    @Override
    public void onSelectedCountChanged(int i) {
        addSuccess.setText("完成(" + i + ")");
    }

    @Override
    public void onAlbumChanged(String s) {

    }

    @Override
    public void onTakePhoto(PhotoEntry photoEntry) {

    }

    @Override
    public void onChoosePhotos(List<PhotoEntry> list) {
        mSelectedPhotos = list;
        if (list != null) {
            for (PhotoEntry item : list) {
                fromList.add(item.getPath());
            }
            Intent intent = new Intent();
            intent.putStringArrayListExtra(PHOTO_LIST, fromList);
            setResult(RESULT_OK, intent);
            finish();
        }
    }

    @Override
    public void onPhotoClick(PhotoEntry photoEntry) {

    }

    @Override
    public void onSelectedSuccess() {
        new QLTipDialog.Builder(AddPictruActivity.this)
                .setCancelable(true)
                .setCancelableOnTouchOutside(true)
                .setMessage("最多添加"+(8-size)+"张图片")
                .setPositiveButton("我知道了", new QLTipDialog.IPositiveCallback() {
                    @Override
                    public void onClick() {

                    }
                })
                .show(AddPictruActivity.this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.add_success) {
            sendPhotos();
        }
        if (id == R.id.bt_close) {
            finish();
        }
    }




}

