package com.xinniu.android.qiqueqiao.customs.qldialog;

import android.content.Context;
//import android.support.v7.app.AppCompatDialog;
import android.view.Gravity;
import android.view.View;

import com.xinniu.android.qiqueqiao.R;

/**
 * Created by qinlei
 * Created on 2017/12/12
 * Created description :
 */

public class QLJoinDialog extends QLDialog {
    private JoinCallback mJoinCallback;

    public void setJoinCallback(JoinCallback joinCallback) {
        this.mJoinCallback = joinCallback;
    }

    protected QLJoinDialog(Context context, int theme) {
        super(context, theme);
    }

    @Override
    protected void createDialog(View mView) {
        mView.findViewById(R.id.btn_join).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mJoinCallback != null) {
                    mJoinCallback.onJoinClick();
                    dismiss();
                }
            }
        });
    }

    public static class Builder extends QLDialog.AQLDialogBuilder<QLJoinDialog.Builder> {

        private JoinCallback mJoinCallback;

        public Builder setJoinCallback(JoinCallback joinCallback) {
            this.mJoinCallback = joinCallback;
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
            return new QLJoinDialog(mContext, R.style.Dialog_Fullscreen);
        }

        @Override
        protected void buildDialog(QLDialog dialog) {
            QLJoinDialog qlDialog = (QLJoinDialog) dialog;
            setView(qlDialog, R.layout.dialog_join);
            qlDialog.setJoinCallback(mJoinCallback);
            qlDialog.setFillWidthPercent(1f);
            qlDialog.setGravity(Gravity.BOTTOM);
            qlDialog.setAnimation(R.style.anim_bottom);
        }
    }

    public interface JoinCallback {
        void onJoinClick();
    }
}
