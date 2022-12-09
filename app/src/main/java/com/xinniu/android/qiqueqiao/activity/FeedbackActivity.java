package com.xinniu.android.qiqueqiao.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
//import androidx.recyclerview.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.xinniu.android.qiqueqiao.R;
import com.xinniu.android.qiqueqiao.adapter.base.FeedBackImgViewAdapter;
import com.xinniu.android.qiqueqiao.base.BaseActivity;
import com.xinniu.android.qiqueqiao.bean.UploadBean;
import com.xinniu.android.qiqueqiao.dialog.AlertDialogUtils;
import com.xinniu.android.qiqueqiao.request.RequestManager;
import com.xinniu.android.qiqueqiao.request.callback.RequestCallback;
import com.xinniu.android.qiqueqiao.user.UserInfoHelper;
import com.xinniu.android.qiqueqiao.utils.FullyGridLayoutManager;
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

/**
 * 建议反馈
 */
public class FeedbackActivity extends BaseActivity {
    @BindView(R.id.bt_finish)
    RelativeLayout btFinish;
    @BindView(R.id.tv_submit)
    TextView tvSubmit;
    @BindView(R.id.edit_content)
    EditText editContent;
    @BindView(R.id.tv_num)
    TextView tvNum;
    @BindView(R.id.activity_publish_recycler)
    RecyclerView activityPublishRecycler;
    @BindView(R.id.edit_phone)
    EditText editPhone;
    private FeedBackImgViewAdapter provideEditResouceAdapter;
    private ArrayList<String> provideImgList = new ArrayList<>();
    private List<String> imgList = new ArrayList<>();
    private List<String> thumbList = new ArrayList<>();
    private Call mCall;

    public static void start(Context context) {
        Intent starter = new Intent(context, FeedbackActivity.class);
        context.startActivity(starter);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_feedback;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        topStatusBar(true);

        provideEditResouceAdapter = new FeedBackImgViewAdapter(mContext, provideImgList, 4);

        FullyGridLayoutManager manager = new FullyGridLayoutManager(this, 4);

        activityPublishRecycler.setLayoutManager(manager);

        activityPublishRecycler.setAdapter(provideEditResouceAdapter);
        editContent.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String s = editable.toString();
                tvNum.setText(s.length() + "/200");

            }
        });
    }


    @OnClick({R.id.bt_finish, R.id.tv_submit})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_finish:
                finish();
                break;
            case R.id.tv_submit:
                //提交
                String text = editContent.getText().toString();
                //  String phone = editPhone.getText().toString();
                String phone = UserInfoHelper.getIntance().getUserName();
                if (TextUtils.isEmpty(text)) {
                    showDialogView("请输入反馈内容");
                    return;
                }
//                if (!TextUtils.isEmpty(phone) && phone.length() < 11) {
//                    showDialogView("请输入正确的手机号");
//                    return;
//                }
                if (provideImgList.size() > 0) {
                    showBookingToast(2);
                    updateImg(provideImgList, text, phone);
                } else {
                    showBookingToast(2);
                    //直接提交
                    submit(text, phone);

                }

                break;
        }
    }

    /**
     * 提交
     *
     * @param text  内容
     * @param phone 号码
     */
    private void submit(String text, String phone) {
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
            RequestManager.getInstance().feedBack(text, provide_img, thumb_img, phone, new RequestCallback<String>() {
                @Override
                public void requestStart(Call call) {
                    showBookingToast(2);
                    mCall = call;
                }

                @Override
                public void onSuccess(String s) {
                    ToastUtils.showCentetImgToast(FeedbackActivity.this, "提交成功，谢谢您的反馈");
                    finish();
                }

                @Override
                public void onFailed(int code, String msg) {
                    ToastUtils.showCentetImgToast(FeedbackActivity.this, msg);
                }

                @Override
                public void requestEnd() {
                    dismissBookingToast();
                }
            });

        }


    }

    private void updateImg(ArrayList<String> provideImgList, final String text, final String phone) {
        for (String item : provideImgList) {
            Luban.with(FeedbackActivity.this).load(item).ignoreBy(300).setTargetDir(getPath()).filter(new CompressionPredicate() {
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
                                    submit(text, phone);

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
                            ToastUtils.showCentetToast(FeedbackActivity.this, "图片压缩失败，请重新选择图片");
                        }
                    }).launch();

        }

    }



    public void showDialogView(String msg) {
        AlertDialogUtils.AlertDialog(FeedbackActivity.this, msg, "知道了", "", new AlertDialogUtils.setOnClick() {
            @Override
            public void setLeftOnClick(DialogInterface dialog) {
                dialog.dismiss();
            }

            @Override
            public void setRightOnClick(DialogInterface dialog) {

            }
        });

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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mCall != null) {
            mCall.cancel();
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
