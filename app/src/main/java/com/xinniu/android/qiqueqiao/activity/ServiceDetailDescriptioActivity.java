package com.xinniu.android.qiqueqiao.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.xinniu.android.qiqueqiao.R;
import com.xinniu.android.qiqueqiao.base.BaseActivity;
import com.xinniu.android.qiqueqiao.bean.ImageBean;
import com.xinniu.android.qiqueqiao.richtexteditor.RichTextEditor;
import com.xinniu.android.qiqueqiao.utils.StringUtils;
import com.xinniu.android.qiqueqiao.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class ServiceDetailDescriptioActivity extends BaseActivity {

    @BindView(R.id.bt_finish)
    RelativeLayout btFinish;
    @BindView(R.id.et_new_content)
    RichTextEditor etNewContent;
    @BindView(R.id.mpublish_titletv)
    TextView mpublishTitletv;
    @BindView(R.id.tv_num)
    TextView tvNum;
    @BindView(R.id.llayout_num)
    LinearLayout llayoutNum;
    private int tokeType = -1;
    private ProgressDialog insertDialog;
    private ProgressDialog loadingDialog;
    private Subscription subsInsert;
    private Subscription subsLoading;
    private String textData = "";//文字
    private List<ImageBean> listDta = new ArrayList<>();//图片集合
    private ArrayList<String> mList = new ArrayList<>();
    private String mType = "1";//1是添加2是编辑
    InputMethodManager imm;

    public static void startSimpleEidtForResult(AppCompatActivity context, int requestCode, String content, String title, String type) {
        Intent intent = new Intent(context, ServiceDetailDescriptioActivity.class);
        intent.putExtra("data", content);
        intent.putExtra("title", title);
        intent.putExtra("type", type);
        context.startActivityForResult(intent, requestCode);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_service_detail_descriptio;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        topStatusBar(true);
        imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        //   getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);

        final String data = getIntent().getExtras().getString("data");
        mType = getIntent().getExtras().getString("type");
        if (getIntent().getExtras().getString("title").equals("案例详情")) {
            llayoutNum.setVisibility(View.GONE);
        } else {
            llayoutNum.setVisibility(View.VISIBLE);
        }
        mpublishTitletv.setText(getIntent().getExtras().getString("title"));
        insertDialog = new ProgressDialog(this);
        insertDialog.setMessage("正在插入图片...");
        insertDialog.setCanceledOnTouchOutside(false);
        loadingDialog = new ProgressDialog(this);
        loadingDialog.setMessage("图片加载中...");
        loadingDialog.setCanceledOnTouchOutside(false);

        if (!StringUtils.isEmpty(data)) {

            etNewContent.post(new Runnable() {
                @Override
                public void run() {
                    //showEditData(note.getContent());
                    etNewContent.clearAllLayout();
                    showDataSync(data);
                }
            });
        }
        etNewContent.setOnDeleteListener(new RichTextEditor.OnDeleteListener() {
            @Override
            public void onDelete(String url) {
                //删除回调
                mList.remove(url);
            }

            @Override
            public void onNumChange(int num) {
                tvNum.setText(num + "");
            }
        });
    }

    /**
     * 异步方式显示数据
     *
     * @param html
     */
    private void showDataSync(final String html) {
        loadingDialog.show();

        subsLoading = Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                showEditData(subscriber, html);
            }
        })
                .onBackpressureBuffer()
                .subscribeOn(Schedulers.io())//生产事件在io
                .observeOn(AndroidSchedulers.mainThread())//消费事件在UI线程
                .subscribe(new Observer<String>() {
                    @Override
                    public void onCompleted() {
                        loadingDialog.dismiss();
                        //计算字数
                        if (mpublishTitletv.getText().equals("服务详情")) {
                            //加载完成后计算，
                            List<RichTextEditor.EditData> editList = etNewContent.buildEditData();
                            int num = 0;
                            for (RichTextEditor.EditData itemData : editList) {
                                if (itemData.inputStr != null && itemData.inputStr.length() > 0) {
                                    String s = itemData.inputStr.replace("\n", "").replaceAll("\\s*", "");
                                    num = num + s.length();
                                    Log.d("RichEditor", "commit inputStr=" + itemData.inputStr.length());
                                } else if (itemData.imagePath != null) {
                                    num = num + 50;
                                    Log.d("RichEditor", "commit imgePath=" + itemData.imagePath);

                                }
                            }
                            tvNum.setText(num + "");
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        loadingDialog.dismiss();

                    }

                    @Override
                    public void onNext(String text) {
                        if (text.contains("<div>")) {
                            String regMatchEnter = "<div>|</div>";
                            text = text.replaceAll(regMatchEnter, "");
                            if (text.contains("</br>")) {
                                text = text.replaceAll("</br>", "\n");
                            }
                            etNewContent.addEditTextAtIndex(etNewContent.getLastIndex(), text);
                        } else {

                            etNewContent.addImageViewAtIndex(etNewContent.getLastIndex(), text);
                            mList.add(text);//编辑状态先保存图片

                        }

                    }
                });
    }

    /**
     * 显示数据
     */
    protected void showEditData(Subscriber<? super String> subscriber, String html) {
        try {
            List<String> textList = StringUtils.cutStringByImgTag(html);
            for (int i = 0; i < textList.size(); i++) {
                String text = textList.get(i);
                if (text.contains("<img")) {
                    String imagePath = StringUtils.getImgSrc(text);
                    // if (new File(imagePath).exists()) {
                    subscriber.onNext(imagePath);
//                    } else {
//                        ToastUtils.showCentetImgToast(mContext, "图片" + i + "已丢失，请重新插入！");
//                    }
                } else {
                    subscriber.onNext(text);
                }

            }
            subscriber.onCompleted();
        } catch (Exception e) {
            e.printStackTrace();
            subscriber.onError(e);
        }
    }


    @OnClick({R.id.bt_finish, R.id.tv_save, R.id.tv_add_picture})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_finish:
                if (imm != null) {
                    imm.hideSoftInputFromWindow(etNewContent.getWindowToken(),
                            InputMethodManager.HIDE_NOT_ALWAYS);
                }
                finish();
                break;
            case R.id.tv_save:
                String noteContent = getEditData();
                int num = Integer.parseInt(tvNum.getText().toString());
                if (noteContent.length() == 0) {
                    ToastUtils.showCentetImgToast(mContext, "请先填写详情内容");
                    return;
                }
                if (num < 50) {
                    ToastUtils.showCentetImgToast(mContext, "服务详情不得少于50字");
                    return;
                }
                if (listDta.size() > 20) {
                    ToastUtils.showCentetImgToast(mContext, "图片不能超过20张");
                    return;
                }

                if (num > 5000) {
                    ToastUtils.showCentetImgToast(mContext, "图片和详情文字一起不能超过5000字");
                    return;
                }

                List<RichTextEditor.EditData> editList = etNewContent.buildEditData();
                Gson g = new Gson();
                String jsonString = g.toJson(listDta);
                String jsonString1 = g.toJson(editList);
                Intent intent = new Intent();
                intent.putExtra("data", noteContent);
                intent.putExtra("picture", jsonString);
                intent.putExtra("originData", jsonString1);
                setResult(AppCompatActivity.RESULT_OK, intent);
                finish();
                if (imm != null) {
                    imm.hideSoftInputFromWindow(etNewContent.getWindowToken(),
                            InputMethodManager.HIDE_NOT_ALWAYS);
                }
                break;
            case R.id.tv_add_picture:
                if (mList.size() < 20) {
                    //添加图片
                    Intent intent1 = new Intent(ServiceDetailDescriptioActivity.this, TakePhotoThreeActivity.class);
                    intent1.putStringArrayListExtra(AddPictruActivity.PHOTO_LIST, mList);
                    startActivityForResult(intent1, 401);
                } else {
                    ToastUtils.showCentetImgToast(mContext, "选择图片不能超过20张");
                }
                break;
        }
    }


    /**
     * 负责处理编辑数据提交等事宜，请自行实现
     */
    private String getEditData() {
        textData = "";
        listDta.clear();
        List<RichTextEditor.EditData> editList = etNewContent.buildEditData();
        StringBuffer content = new StringBuffer();
        for (RichTextEditor.EditData itemData : editList) {
            if (itemData.inputStr != null && itemData.inputStr.length() > 0) {
                //   content.append(itemData.inputStr);
                if (itemData.inputStr.contains("\n")) {
                    //所以换行符改成html的换行
                    content.append("<div>").append(itemData.inputStr.replaceAll("\n", "</br>")).append("</div>");
                } else {
                    content.append("<div>").append(itemData.inputStr).append("</div>");
                    textData = textData + itemData.inputStr;
                }
                Log.d("RichEditor", "commit inputStr=" + itemData.inputStr.length());
            } else if (itemData.imagePath != null) {
                content.append("<img src=\"").append(itemData.imagePath).append("\"/>");
                Log.d("RichEditor", "commit imgePath=" + itemData.imagePath);

                ImageBean imageBean = new ImageBean(itemData.imagePath, itemData.imagePath);
                listDta.add(imageBean);
            }
        }
        return content.toString();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == 401) {
                ArrayList<String> list = data.getStringArrayListExtra(TakePhotoActivity.PHOTO_LIST);
                for (int i = 0; i < list.size(); i++) {
                    mList.add(list.get(i));
                    insertImagesSync(list.get(i));
                }
            }
        }
    }

    private void insertImagesSync(final String data) {
        subsInsert = Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                try {
                    subscriber.onNext(data);
                    subscriber.onCompleted();
                } catch (Exception e) {
                    e.printStackTrace();
                    subscriber.onError(e);
                }
            }
        })
                .onBackpressureBuffer()
                .subscribeOn(Schedulers.io())//生产事件在io
                .observeOn(AndroidSchedulers.mainThread())//消费事件在UI线程
                .subscribe(new Observer<String>() {
                    @Override
                    public void onCompleted() {
                        insertDialog.dismiss();
                        if (etNewContent.getLastIndex() == 2) {
                            if (!etNewContent.getFristEdit().equals("请输入详情内容")) {
                                etNewContent.addEditTextAtIndex(etNewContent.getLastIndex(), "");
                            }

                        } else {
                            etNewContent.addEditTextAtIndex(etNewContent.getLastIndex(), "");
                        }
                        //   ToastUtils.showCentetImgToast(mContext, "图片插入成功");
                    }

                    @Override
                    public void onError(Throwable e) {
                        insertDialog.dismiss();
                        Log.i("图片插入失败:", e.getMessage());
                        //   ToastUtils.showCentetImgToast(mContext, "图片插入失败,请重新选择");
                    }

                    @Override
                    public void onNext(String imagePath) {
                        etNewContent.insertImage(imagePath, etNewContent.getMeasuredWidth());
                    }
                });

    }

    //监听返回键(有软键盘的情况下想直接返回，需要拦截KeyEvent.ACTION_UP事件)
    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP) {
            finish();
        }
        return super.dispatchKeyEvent(event);
    }
}
