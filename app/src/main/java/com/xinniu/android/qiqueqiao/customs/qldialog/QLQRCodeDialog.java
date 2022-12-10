package com.xinniu.android.qiqueqiao.customs.qldialog;

import android.content.Context;
import android.graphics.Bitmap;
import androidx.appcompat.app.AppCompatDialog;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.xinniu.android.qiqueqiao.R;
import com.xinniu.android.qiqueqiao.utils.ImageLoader;
import com.xinniu.android.qiqueqiao.utils.QRCodeUtil;
import com.xinniu.android.qiqueqiao.utils.ShowUtils;

/**
 * Created by qinlei
 * Created on 2017/12/12
 * Created description :
 */

public class QLQRCodeDialog extends QLDialog {
    private String mImageHead;
    private String mTitle;
    private int mRating;
    private String mQRCodeString;
    private ShareCallback mShareCallback;

    public void setImageHead(String imageHead) {
        this.mImageHead = imageHead;
    }

    public void setTitle(String title) {
        this.mTitle = title;
    }

    public void setRating(int rating) {
        this.mRating = rating;
    }

    public void setQRCodeString(String qrCodeString) {
        this.mQRCodeString = qrCodeString;
    }

    public void setShareCallback(ShareCallback shareCallback) {
        this.mShareCallback = shareCallback;
    }

    protected QLQRCodeDialog(Context context, int theme) {
        super(context, theme);
    }

    @Override
    protected void createDialog(View mView) {
        ImageView imageHead = (ImageView) mView.findViewById(R.id.image_head);
        TextView tvTitle = (TextView) mView.findViewById(R.id.tv_title);
        RatingBar ratingBar = (RatingBar) mView.findViewById(R.id.star_view);
        ImageView imageQrCode = (ImageView) mView.findViewById(R.id.image_qr_code);

        final Bitmap mBitmap = QRCodeUtil.createQRCodeBitmap(mQRCodeString, 480, 480);

        ImageLoader.loadImage(mImageHead,imageHead);
        ShowUtils.showTextPerfect(tvTitle,mTitle);
        ratingBar.setRating(mRating);
        ratingBar.setNumStars(mRating);
        imageQrCode.setImageBitmap(mBitmap);


        mView.findViewById(R.id.tv_share).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mShareCallback != null) {
                    mShareCallback.onClickShare(mBitmap);
                }
                dismiss();
            }
        });
    }

    public static class Builder extends QLDialog.AQLDialogBuilder<QLQRCodeDialog.Builder> {
        private String mImageHead;
        private String mTitle;
        private int mRating;
        private String mQRCodeString;
        private ShareCallback mShareCallback;

        public Builder setImageHead(String imageHead) {
            this.mImageHead = imageHead;
            return this;
        }

        public Builder setTitle(String title) {
            this.mTitle = title;
            return this;
        }

        public Builder setRating(int rating) {
            this.mRating = rating;
            return this;
        }

        public Builder setQRCodeString(String qrCodeString) {
            this.mQRCodeString = qrCodeString;
            return this;
        }

        public Builder setShareCallback(ShareCallback shareCallback) {
            this.mShareCallback = shareCallback;
            return this;
        }

        public Builder(Context context) {
            super(context);
        }

        public Builder(Context context, int theme) {
            super(context, theme);
        }

        @Override
        protected AppCompatDialog buildWithTheme(Context mContext, int mTheme) {
            return new QLQRCodeDialog(mContext, mTheme);
        }

        @Override
        protected void buildDialog(QLDialog dialog) {
            QLQRCodeDialog qlDialog = (QLQRCodeDialog) dialog;
            setView(qlDialog, R.layout.dialog_qr_code);


            qlDialog.setImageHead(mImageHead);
            qlDialog.setTitle(mTitle);
            qlDialog.setRating(mRating);
            qlDialog.setQRCodeString(mQRCodeString);
            qlDialog.setShareCallback(mShareCallback);

            qlDialog.setFillWidthPercent(0.8f);
            qlDialog.setGravity(Gravity.CENTER);
        }
    }

    public interface ShareCallback {
        void onClickShare(Bitmap bitmap);
    }
}
