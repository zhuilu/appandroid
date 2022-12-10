package com.xinniu.android.qiqueqiao.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.xinniu.android.qiqueqiao.R;
import com.xinniu.android.qiqueqiao.adapter.base.CustomServiceImgViewAdapter;
import com.xinniu.android.qiqueqiao.adapter.base.FeedBackImgViewAdapter;
import com.xinniu.android.qiqueqiao.base.BaseActivity;
import com.xinniu.android.qiqueqiao.bean.UploadBean;
import com.xinniu.android.qiqueqiao.request.RequestManager;
import com.xinniu.android.qiqueqiao.request.callback.AllResultDoCallback;
import com.xinniu.android.qiqueqiao.request.callback.RequestCallback;
import com.xinniu.android.qiqueqiao.utils.FullyGridLayoutManager;
import com.xinniu.android.qiqueqiao.utils.StringUtils;
import com.xinniu.android.qiqueqiao.utils.ToastUtils;

import java.io.File;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import retrofit2.Call;
import top.zibin.luban.CompressionPredicate;
import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;
import top.zibin.luban.OnRenameListener;

import androidx.recyclerview.widget.RecyclerView;

/**
 * 申请客服介入
 */
public class ApplicatuinRewardCustomeServiceActivity extends BaseActivity {
    @BindView(R.id.edit_content)
    EditText editContent;
    @BindView(R.id.activity_publish_recycler)
    RecyclerView activityPublishRecycler;
    @BindView(R.id.tv_type_select)
    TextView tvType;
    @BindView(R.id.rlayout_select)
    RelativeLayout rlayoutSelect;
    @BindView(R.id.view_empty)
    View viewEmpty;
    @BindView(R.id.view_01)
    View view01;
    @BindView(R.id.tv_01)
    TextView tv01;
    private CustomServiceImgViewAdapter provideEditResouceAdapter;
    private ArrayList<String> provideImgList = new ArrayList<>();
    private List<String> imgList = new ArrayList<>();
    private List<String> thumbList = new ArrayList<>();

    private int mId;

    public static void start(Context context, int id) {
        Intent starter = new Intent(context, ApplicatuinRewardCustomeServiceActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt("id", id);
        context.startActivity(starter);
    }
    public static void startSimpleEidtForResult(AppCompatActivity context, int id, int requestCode) {
        Intent intent = new Intent(context, ApplicatuinRewardCustomeServiceActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt("id", id);
        intent.putExtras(bundle);
        context.startActivityForResult(intent, requestCode);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_application_custome_service;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        topStatusBar(true);
        Intent intent = getIntent();
        mId = intent.getIntExtra("id", -1);

        viewEmpty.setVisibility(View.GONE);
        rlayoutSelect.setVisibility(View.GONE);
        view01.setVisibility(View.GONE);
        editContent.setHint("请描述申请理由");
        provideEditResouceAdapter = new CustomServiceImgViewAdapter(mContext, provideImgList, 4);
        FullyGridLayoutManager manager = new FullyGridLayoutManager(this, 4);
        activityPublishRecycler.setLayoutManager(manager);
        activityPublishRecycler.setAdapter(provideEditResouceAdapter);

    }

    @OnClick({R.id.bt_return, R.id.btn_submit})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_return:
                finish();
                break;
            case R.id.btn_submit:
                //提交
                String text = editContent.getText().toString();
                if(StringUtils.isEmpty(text)){
                    ToastUtils.showCentetToast(ApplicatuinRewardCustomeServiceActivity.this,"请填写申请理由");
                    return;
                }
                if (provideImgList.size() > 0) {
                    showBookingToast(2);
                    updateImg(provideImgList, text);
                } else {
                    showBookingToast(2);
                    //直接提交
                    submit(text);

                }

                break;

        }
    }

    private void updateImg(ArrayList<String> provideImgList, final String text) {
        for (String item : provideImgList) {
            Luban.with(ApplicatuinRewardCustomeServiceActivity.this).load(item).ignoreBy(300).setTargetDir(getPath()).filter(new CompressionPredicate() {
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
            })
                    .setCompressListener(new OnCompressListener() {
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
                                    imgList.add(uploadBean.getPath());
                                    thumbList.add(uploadBean.getThumb_img());
                                    submit(text);

                                }

                                @Override
                                public void onFailed(int code, String msg) {

                                }

                                @Override
                                public void requestEnd() {

                                }
                            });
                        }

                        @Override
                        public void onError(Throwable e) {
                            dismissBookingToast();
                            ToastUtils.showCentetToast(ApplicatuinRewardCustomeServiceActivity.this, "图片压缩失败，请重新选择图片");
                        }
                    }).launch();

        }

    }

    private void submit(String text) {
        String provide_img = "";
        String thumb_img = "";

        if (isUpdateSuccess()) {
            if (imgList.size() > 0) {
                for (int i = 0; i < imgList.size(); i++) {
                    if (i == 0) {
                        provide_img = imgList.get(i);
                    } else {
                        provide_img = provide_img + "," + imgList.get(i);
                    }
                }
            }
            if (thumbList.size() > 0) {
                for (int i = 0; i < thumbList.size(); i++) {
                    if (i == 0) {
                        thumb_img = thumbList.get(i);
                    } else {
                        thumb_img = thumb_img + "," + thumbList.get(i);
                    }
                }
            }

            RequestManager.getInstance().applicationRewardService(mId, text, provide_img, thumb_img, new AllResultDoCallback() {
                @Override
                public void onSuccess(String msg) {
                    dismissBookingToast();
                    ToastUtils.showCentetToast(ApplicatuinRewardCustomeServiceActivity.this, msg);
                    Intent intent = new Intent();
                    setResult(Activity.RESULT_OK, intent);
                    finish();
                }

                @Override
                public void onFailed(int code, String msg) {
                    dismissBookingToast();
                    ToastUtils.showCentetToast(ApplicatuinRewardCustomeServiceActivity.this, msg);
                }
            });


        }

    }

    //刷新图片
    public void refreshprovidePic(ArrayList<String> list) {
        provideImgList.clear();
        provideImgList.addAll(list);
        provideEditResouceAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == FeedBackImgViewAdapter.TYPE) {
            refreshprovidePic(data.getStringArrayListExtra(TakePhotoTwoActivity.PHOTO_LIST));
        }

    }


    public boolean isUpdateSuccess() {
        if (imgList.size() == provideImgList.size()) {
            return true;
        } else {
            return false;
        }

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
