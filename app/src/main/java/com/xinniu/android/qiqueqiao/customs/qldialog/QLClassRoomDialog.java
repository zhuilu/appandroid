package com.xinniu.android.qiqueqiao.customs.qldialog;

import android.content.Context;
import androidx.appcompat.app.AppCompatDialog;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.xinniu.android.qiqueqiao.R;



/**
 * Created by yuchance on 2018/7/31.
 */

public class QLClassRoomDialog extends QLDialog {
    private ShareNewCallback mShareCallback;
    private Context context;
    private boolean isPicture=false;
    public void setContext(Context context) {
        this.context = context;
    }

    public void setmShareCallback(ShareNewCallback mShareCallback) {
        this.mShareCallback = mShareCallback;
    }

    public void setPicture(boolean picture) {
        isPicture = picture;
    }

    protected QLClassRoomDialog(Context context, int theme) {
        super(context, theme);
    }

    @Override
    protected void createDialog(View mView) {
        LinearLayout bshare_wx = (LinearLayout) mView.findViewById(R.id.bshare_wx);
        LinearLayout bshare_pcircle = (LinearLayout) mView.findViewById(R.id.bshare_circle);
        LinearLayout bshare_qq = (LinearLayout) mView.findViewById(R.id.bshare_qq);
        LinearLayout bshare_dd = (LinearLayout) mView.findViewById(R.id.bshare_dd);

        LinearLayout bshare_picture = (LinearLayout) mView.findViewById(R.id.bshare_picture);
        if (isPicture) {
            bshare_picture.setVisibility(View.VISIBLE);
        }else{
            bshare_picture.setVisibility(View.GONE);
        }
        bshare_picture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mShareCallback.onClickShare(ShareType.SHARE_PICTURE);
            }
        });
        bshare_wx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mShareCallback.onClickShare(ShareType.SHARE_WEIXIN);
            }
        });
        bshare_dd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mShareCallback.onClickShare(ShareType.SHARE_DD);
            }
        });
        bshare_pcircle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mShareCallback.onClickShare(ShareType.SHARE_CIRCLE);
            }
        });
        bshare_qq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mShareCallback.onClickShare(ShareType.SHARE_QQ);
            }
        });

        mView.findViewById(R.id.tv_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
    }

    public static class Builder extends AQLDialogBuilder<QLClassRoomDialog.Builder> {
        private ShareNewCallback mShareCallback;
        private Context context;
        private boolean isPicture;
        public Builder setContext(Context context) {
            this.context = context;
            return this;
        }

        public Builder setPicture(boolean picture) {
            this.isPicture = picture;
            return this;
        }

        public Builder setShareCallback(QLClassRoomDialog.ShareNewCallback mShareCallback) {
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
            return new QLClassRoomDialog(mContext, mTheme);
        }

        @Override
        protected void buildDialog(QLDialog dialog) {
            QLClassRoomDialog qlDialog = (QLClassRoomDialog) dialog;
            setView(qlDialog, R.layout.dialog_classroom_share);
            qlDialog.setContext(context);
            qlDialog.setPicture(isPicture);
            qlDialog.setmShareCallback(mShareCallback);
            qlDialog.setAnimation(R.style.anim_bottom);
            qlDialog.setFillWidthPercent(1f);
            qlDialog.setGravity(Gravity.BOTTOM);
        }
    }

    public enum ShareType {
        SHARE_WEIXIN,
        SHARE_CIRCLE,
        SHARE_QQ,
        SHARE_DD,//分享到钉钉
        SHARE_PICTURE,

    }

    public interface ShareNewCallback {
        void onClickShare(ShareType type);
    }

}
