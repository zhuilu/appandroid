package com.xinniu.android.qiqueqiao.activity;

import android.content.Intent;
import android.os.Bundle;
//import android.support.annotation.Nullable;
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
 * Created by lzq on 2018/1/17.
 * 选择图片页面
 */

public class TakePhotoThreeActivity extends BaseGalleryActivity implements View.OnClickListener {
    public List<PhotoEntry> mSelectedPhotos = new ArrayList<>();
    private TextView addSuccess;
    private ImageView closeBt;
    public final static String PHOTO_LIST = "photo_list";
    public final static String SELECT_SIZE = "SELECT_SIZE";
    private int size = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_take_photo);
        StatusBarUtil.StatusBarLightMode(this,true);
        attachFragment(R.id.gallery_root);
        List<String> list = getIntent().getStringArrayListExtra(PHOTO_LIST);
        size = list.size();
//        for (String item : list) {
//            PhotoEntry photoEntry = new PhotoEntry();
//            photoEntry.setChecked(true);
//            photoEntry.setPath(item);
//            mSelectedPhotos.add(photoEntry);
//        }
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
                .setMaximum(20 - size)
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
            ArrayList<String> selectedList = new ArrayList<>();
            for (PhotoEntry item : list) {
                selectedList.add(item.getPath());
            }
            Intent intent = new Intent();
            intent.putStringArrayListExtra(PHOTO_LIST, selectedList);
            setResult(RESULT_OK, intent);
            finish();
        }
    }

    @Override
    public void onPhotoClick(PhotoEntry photoEntry) {

    }

    @Override
    public void onSelectedSuccess() {
        new QLTipDialog.Builder(TakePhotoThreeActivity.this)
                .setCancelable(true)
                .setCancelableOnTouchOutside(true)
                .setMessage("最多选择" + (20 - size) + "张图片")
                .setPositiveButton("我知道了", new QLTipDialog.IPositiveCallback() {
                    @Override
                    public void onClick() {

                    }
                })
                .show(TakePhotoThreeActivity.this);
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
