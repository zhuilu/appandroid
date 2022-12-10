package com.xinniu.android.qiqueqiao.customs.qldialog;

import android.content.Context;
import androidx.appcompat.app.AppCompatDialog;
import android.view.Gravity;
import android.view.View;
import android.widget.RelativeLayout;


import androidx.appcompat.app.AppCompatDialog;

import com.xinniu.android.qiqueqiao.R;


/**
 * Created by yuchance on 2018/10/17.
 */

public class QLAddGroupWayDialog extends QLDialog {
    private ShareNewCallback mShareCallback;
    private int type;

    public void setType(int type) {
        this.type = type;
    }

    public void setmShareCallback(ShareNewCallback mShareCallback) {
        this.mShareCallback = mShareCallback;
    }

    protected QLAddGroupWayDialog(Context context, int theme) {
        super(context, theme);
    }

    @Override
    protected void createDialog(View mView) {
        RelativeLayout baddgroupway1 = (RelativeLayout) mView.findViewById(R.id.baddgroupway1);
        RelativeLayout baddgroupway2= (RelativeLayout) mView.findViewById(R.id.baddgroupway2);
        RelativeLayout baddgroupway3 = (RelativeLayout) mView.findViewById(R.id.baddgroupway3);
        RelativeLayout baddgroupway4 = (RelativeLayout) mView.findViewById(R.id.baddgroupway4);
        switch (type){
            case 0:
                baddgroupway1.setVisibility(View.GONE);
                baddgroupway2.setVisibility(View.VISIBLE);
                baddgroupway3.setVisibility(View.VISIBLE);

                break;
            case 1:
                baddgroupway1.setVisibility(View.VISIBLE);
                baddgroupway2.setVisibility(View.GONE);
                baddgroupway3.setVisibility(View.VISIBLE);
                break;
            case 2:
                baddgroupway1.setVisibility(View.VISIBLE);
                baddgroupway2.setVisibility(View.VISIBLE);
                baddgroupway3.setVisibility(View.GONE);
                break;
            default:
                baddgroupway1.setVisibility(View.VISIBLE);
                baddgroupway2.setVisibility(View.VISIBLE);
                baddgroupway3.setVisibility(View.VISIBLE);
                break;
        }


        baddgroupway1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mShareCallback.onClickShare(ShareType.NOVERIFY,"无需验证");
                dismiss();
            }
        });
        baddgroupway2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mShareCallback.onClickShare(ShareType.VERIFY,"需要身份验证");
                dismiss();
            }
        });
        baddgroupway3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mShareCallback.onClickShare(ShareType.NOADD,"不允许任何人加群");
                dismiss();
            }
        });
        baddgroupway4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });


    }

    public static class Builder extends QLDialog.AQLDialogBuilder<Builder> {
        private ShareNewCallback mShareCallback;

        private int type;

        public Builder setType(int type) {
            this.type = type;
            return this;
        }

        public Builder setmShareCallback(QLAddGroupWayDialog.ShareNewCallback mShareCallback) {
            this.mShareCallback = mShareCallback;
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
            return new QLAddGroupWayDialog(mContext, mTheme);
        }

        @Override
        protected void buildDialog(QLDialog dialog) {
            QLAddGroupWayDialog qlDialog = (QLAddGroupWayDialog) dialog;
            setView(qlDialog, R.layout.dialog_add_groupway);
            qlDialog.setType(type);
            qlDialog.setmShareCallback(mShareCallback);
            qlDialog.setAnimation(R.style.anim_bottom);
            qlDialog.setFillWidthPercent(1f);
            qlDialog.setGravity(Gravity.BOTTOM);
        }
    }

    public enum ShareType {
        NOVERIFY,
        VERIFY,
        NOADD

    }

    public interface ShareNewCallback {
        void onClickShare(ShareType type,String typeWay);
    }
}
