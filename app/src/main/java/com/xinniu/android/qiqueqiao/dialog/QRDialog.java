package com.xinniu.android.qiqueqiao.dialog;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
//import android.support.v7.app.AppCompatDialog;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatDialog;

import com.xinniu.android.qiqueqiao.R;
import com.xinniu.android.qiqueqiao.utils.QRCodeUtil;

/**
 * Created by lzq on 2017/12/24.
 */

public class QRDialog extends AppCompatDialog {
    private TextView titleTv;
    private ImageView imageQRIv;
    private TextView shareTv;
    private ShareCallback mShareCallback;
    private String mQRCodeString;
    private  String titleStr;
    private Context context;

    public void setShareCallback(ShareCallback shareCallback){
        this.mShareCallback = shareCallback;
    }
    public QRDialog(Context context, int theme) {
        super(context, theme);
        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_qr);
        titleTv = (TextView) findViewById(R.id.title_tv);
        imageQRIv = (ImageView) findViewById(R.id.image_qr_code);
        shareTv = (TextView) findViewById(R.id.tv_share);
    }

    public void setmQRCodeString(String mQRCodeString) {
        this.mQRCodeString = mQRCodeString;
    }

    public void setTitleStr(String titleStr) {
        this.titleStr = titleStr;
    }

    @Override
    public void show() {
        super.show();
        final Bitmap mBitmap = QRCodeUtil.createQRCodeBitmap(mQRCodeString, 480, 480);
        imageQRIv.setImageBitmap(mBitmap);
        titleTv.setText(titleStr);
        shareTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mShareCallback != null){
                    mShareCallback.onClickShare(mBitmap);
                }
                dismiss();
            }
        });
    }

    public interface ShareCallback {
        void onClickShare(Bitmap bitmap);
    }
}
