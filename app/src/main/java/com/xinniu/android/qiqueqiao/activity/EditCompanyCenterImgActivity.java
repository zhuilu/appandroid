package com.xinniu.android.qiqueqiao.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
//import android.support.v7.widget.GridLayoutManager;
//import androidx.recyclerview.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.xinniu.android.qiqueqiao.R;
import com.xinniu.android.qiqueqiao.adapter.EditResouceAdapter;
import com.xinniu.android.qiqueqiao.base.BaseActivity;
import com.xinniu.android.qiqueqiao.bean.UploadBean;
import com.xinniu.android.qiqueqiao.request.RequestManager;
import com.xinniu.android.qiqueqiao.request.callback.RequestCallback;
import com.xinniu.android.qiqueqiao.utils.Logger;
import com.xinniu.android.qiqueqiao.utils.StringUtils;
import com.xinniu.android.qiqueqiao.utils.ToastUtils;

import java.io.File;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import retrofit2.Call;
import top.zibin.luban.CompressionPredicate;
import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;
import top.zibin.luban.OnRenameListener;

/**
 * Created by lzq on 2018/3/1.
 */

public class EditCompanyCenterImgActivity extends BaseActivity {
    @BindView(R.id.center_img_rv)
    RecyclerView imgRv;
    @BindView(R.id.button)
    TextView saveTv;
    private EditResouceAdapter mEditResouceAdapter;
    private ArrayList<String> imgList = new ArrayList<>();
    private static final String PARAM_TYPE = "PARAM_TYPE";
    public static final int PARAM_TYPE_CENTER_IMG = 3;
    private List<String> returnList = new ArrayList<>();

    @Override
    public int getLayoutId() {
        return R.layout.activity_edit_company_center;
    }

    public static void startForResult(Activity context, int requestCode) {
        Intent starter = new Intent(context, EditCompanyCenterImgActivity.class);
        starter.putExtra(PARAM_TYPE, requestCode);
        context.startActivityForResult(starter, requestCode);
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        topStatusBar(false);
        imgList.add(EditResouceAdapter.ADD);
        mEditResouceAdapter = new EditResouceAdapter(EditCompanyCenterImgActivity.this, imgList, EditResouceAdapter.TYPE_NEED);
        imgRv.setLayoutManager(new GridLayoutManager(mContext, 4));
        imgRv.setAdapter(mEditResouceAdapter);
        saveTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                commit();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Logger.i("lzq", "requestCode : " + requestCode + "resultCode : " + resultCode);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case EditResouceAdapter.TYPE_NEED:
                    refreshImgList(data.getStringArrayListExtra(TakePhotoActivity.PHOTO_LIST));
                    break;

            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void refreshImgList(List<String> list) {
        if (list.size() < 4) {
            list.add(EditResouceAdapter.ADD);
        }
        imgList.clear();
        imgList.addAll(list);
        mEditResouceAdapter.notifyDataSetChanged();
    }

    private void commit() {
        for (int i = imgList.size() - 1; i >= 0; i--) {
            if (imgList.get(i).equals(EditResouceAdapter.ADD)) {
                imgList.remove(i);
                break;
            }
        }
        for (String item : imgList) {
            uploadImg(item);
        }
    }

    private void uploadImg(final String item) {
        showBookingToast(2);
        Luban.with(EditCompanyCenterImgActivity.this).load(item).ignoreBy(300).setTargetDir(getPath()).filter(new CompressionPredicate() {
            @Override
            public boolean apply(String path) {
                return !(TextUtils.isEmpty(path) || path.toLowerCase().endsWith(".gif"));
            }
        }).setRenameListener(new OnRenameListener() {
            @Override
            public String rename(String filePath) {
                try {
                    MessageDigest md = MessageDigest.getInstance("MD5");
                    md.update(filePath.getBytes());
                    return new BigInteger(1, md.digest()).toString(32);
                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                }
                return "";
            }
        }).setCompressListener(new OnCompressListener() {
            @Override
            public void onStart() {
            }

            @Override
            public void onSuccess(File file) {
                RequestManager.getInstance().updateBase64(file.getAbsolutePath(), new RequestCallback<UploadBean>() {
                    @Override
                    public void requestStart(Call call) {
                    }

                    @Override
                    public void onSuccess(UploadBean uploadBean) {
                        returnList.add(uploadBean.getPath());
                        if (returnList.size() >= imgList.size()) {
                            dismissBookingToast();
                            Intent intent = new Intent();
                            intent.putExtra("data", StringUtils.getUpdateImg(returnList));
                            intent.putExtra("number", returnList.size());
                            setResult(Activity.RESULT_OK, intent);
                            finish();
                        }
                    }

                    @Override
                    public void onFailed(int code, String msg) {
                        dismissBookingToast();
                    }

                    @Override
                    public void requestEnd() {
                    }
                });
            }

            @Override
            public void onError(Throwable e) {
                dismissBookingToast();
                ToastUtils.showCentetToast(EditCompanyCenterImgActivity.this, "图片压缩失败，请重新选择图片");
            }
        }).launch();
    }

    private String getPath() {
        String path = Environment.getExternalStorageDirectory() + "/Luban/image/";
        File file = new File(path);
        if (file.mkdirs()) {
            return path;
        }
        return path;
    }
}
