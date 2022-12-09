package com.xinniu.android.qiqueqiao.customs.qldialog;

import android.content.Context;
//import android.support.v7.app.AppCompatDialog;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xinniu.android.qiqueqiao.R;

/**
 * Created by qinlei
 * Created on 2017/12/12
 * Created description :
 */

public class QLTipTwoDialog extends QLDialog {
    private String mTitle;
    private String mMessage;
    private String mNegative;
    private String mPositive;
    private INegativeCallback mNegativeCallback;
    private IPositiveCallback mPositiveCallback;

    public QLTipTwoDialog(Context context, int theme) {
        super(context, theme);
    }

    public void setTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public void setMessage(String mMessage) {
        this.mMessage = mMessage;
    }

    public void setNegative(String mNegative) {
        this.mNegative = mNegative;
    }

    public void setPositive(String mPositive) {
        this.mPositive = mPositive;
    }

    public void setNegativeCallback(INegativeCallback mNegativeCallback) {
        this.mNegativeCallback = mNegativeCallback;
    }

    public void setPositiveCallback(IPositiveCallback mPositiveCallback) {
        this.mPositiveCallback = mPositiveCallback;
    }

    @Override
    protected void createDialog(View mView) {
        TextView tvTitle = (TextView) this.mView.findViewById(R.id.tv_title);
        TextView tvMessage = (TextView) this.mView.findViewById(R.id.tv_message);
        LinearLayout llBtnRoot = (LinearLayout) this.mView.findViewById(R.id.ll_btn_root);
        TextView tvNegative = (TextView) this.mView.findViewById(R.id.btn_negative);
        TextView tvPositive = (TextView) this.mView.findViewById(R.id.btn_positive);
        View line = this.mView.findViewById(R.id.line);

        if (TextUtils.isEmpty(mTitle)) {
            tvTitle.setVisibility(View.GONE);
        } else {
            tvTitle.setVisibility(View.VISIBLE);
            tvTitle.setText(mTitle);
        }

        if (TextUtils.isEmpty(mMessage)) {
            throw new RuntimeException("QLDialog message is null");
        } else {
            tvMessage.setVisibility(View.VISIBLE);
            tvMessage.setText(mMessage);
        }

        if (TextUtils.isEmpty(mNegative) && TextUtils.isEmpty(mPositive)) {
            llBtnRoot.setVisibility(View.GONE);
        }

        if (!TextUtils.isEmpty(mNegative) && !TextUtils.isEmpty(mPositive)) {
            line.setVisibility(View.VISIBLE);

        }

        if (!TextUtils.isEmpty(mNegative) && TextUtils.isEmpty(mPositive)
                || TextUtils.isEmpty(mNegative) && !TextUtils.isEmpty(mPositive)) {
            tvNegative.setBackgroundResource(R.drawable.btn_bottom_10);
            tvPositive.setBackgroundResource(R.drawable.btn_bottom_10);
        }

        if (!TextUtils.isEmpty(mNegative) && mNegativeCallback != null) {
            llBtnRoot.setVisibility(View.VISIBLE);
            tvNegative.setVisibility(View.VISIBLE);
            tvNegative.setText(mNegative);
            tvNegative.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mNegativeCallback.onClick();
                    dismiss();
                }
            });
        }

        if (!TextUtils.isEmpty(mPositive) && mPositiveCallback != null) {
            llBtnRoot.setVisibility(View.VISIBLE);
            tvPositive.setVisibility(View.VISIBLE);
            tvPositive.setText(mPositive);
            tvPositive.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mPositiveCallback.onClick();
                    dismiss();
                }
            });
        }
    }

    public static class Builder extends AQLDialogBuilder<QLTipTwoDialog.Builder> {

        private String mTitle;
        private String mMessage;
        private String mNegative;
        private String mPositive;
        private INegativeCallback mNegativeCallback;
        private IPositiveCallback mPositiveCallback;

        public Builder(Context context) {
            super(context);
        }

        public Builder(Context context, int theme) {
            super(context, theme);
        }

        @Override
        protected AppCompatDialog buildWithTheme(Context mContext, int mTheme) {
            return new QLTipTwoDialog(mContext, mTheme);
        }

        public Builder setTitle(String title) {
            this.mTitle = title;
            return this;
        }

        public Builder setMessage(String message) {
            this.mMessage = message;
            return this;
        }

        public Builder setNegativeButton(String negative, INegativeCallback negativeCallback) {
            this.mNegative = negative;
            this.mNegativeCallback = negativeCallback;
            return this;
        }

        public Builder setPositiveButton(String positive, IPositiveCallback positiveCallback) {
            this.mPositive = positive;
            this.mPositiveCallback = positiveCallback;
            return this;
        }



        @Override
        protected void buildDialog(QLDialog dialog) {
            QLTipTwoDialog qlDialog = (QLTipTwoDialog) dialog;
            setView(qlDialog, R.layout.dialog_tip_two);

            qlDialog.setTitle(mTitle);
            qlDialog.setMessage(mMessage);
            if (!TextUtils.isEmpty(mNegative) && mNegativeCallback != null) {
                qlDialog.setNegative(mNegative);
                qlDialog.setNegativeCallback(mNegativeCallback);
            }
            if (!TextUtils.isEmpty(mPositive) && mPositiveCallback != null) {
                qlDialog.setPositive(mPositive);
                qlDialog.setPositiveCallback(mPositiveCallback);
            }

            qlDialog.setFillWidthPercent(0.8f);
            qlDialog.setGravity(Gravity.CENTER);
        }
    }

    public interface INegativeCallback {
        void onClick();
    }



    public interface IPositiveCallback {
        void onClick();
    }
}
