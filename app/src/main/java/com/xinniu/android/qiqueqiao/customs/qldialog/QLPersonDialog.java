package com.xinniu.android.qiqueqiao.customs.qldialog;

import android.content.Context;
import androidx.appcompat.app.AppCompatDialog;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.xinniu.android.qiqueqiao.R;

import static com.xinniu.android.qiqueqiao.customs.qldialog.QLPersonDialog.ShareType.DELETE_GO;
import static com.xinniu.android.qiqueqiao.customs.qldialog.QLPersonDialog.ShareType.REPORT_GO;
import static com.xinniu.android.qiqueqiao.customs.qldialog.QLPersonDialog.ShareType.SHARE_CIRCLE;
import static com.xinniu.android.qiqueqiao.customs.qldialog.QLPersonDialog.ShareType.SHARE_DD;
import static com.xinniu.android.qiqueqiao.customs.qldialog.QLPersonDialog.ShareType.SHARE_QQ;
import static com.xinniu.android.qiqueqiao.customs.qldialog.QLPersonDialog.ShareType.SHARE_WEIBO;
import static com.xinniu.android.qiqueqiao.customs.qldialog.QLPersonDialog.ShareType.SHARE_WEIXIN;


/**
 * Created by yuchance on 2018/7/31.
 */

public class QLPersonDialog extends QLDialog {
    private ShareNewCallback mShareCallback;

    private boolean isFriend;
    private boolean isMe;
    private Context context;

    public void setContext(Context context) {
        this.context = context;
    }

    public void setmShareCallback(ShareNewCallback mShareCallback) {
        this.mShareCallback = mShareCallback;
    }

    public boolean getIsFriend() {
        return isFriend;
    }

    public void setFriend(boolean friend) {
        isFriend = friend;
    }

    public void setMe(boolean me) {
        isMe = me;
    }

    protected QLPersonDialog(Context context, int theme) {
        super(context, theme);
    }

    @Override
    protected void createDialog(View mView) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        int screenWidth = wm.getDefaultDisplay().getWidth();
        LinearLayout bshare_wx = (LinearLayout) mView.findViewById(R.id.bshare_wx);
        LinearLayout bshare_pcircle = (LinearLayout) mView.findViewById(R.id.bshare_circle);
        LinearLayout bshare_qq = (LinearLayout) mView.findViewById(R.id.bshare_qq);
        LinearLayout bshare_dd = (LinearLayout) mView.findViewById(R.id.bshare_dd);

        LinearLayout breportLl = (LinearLayout) mView.findViewById(R.id.breport_Ll);
        LinearLayout bdeleteLl = (LinearLayout) mView.findViewById(R.id.bdelete_Ll);
        LinearLayout llayout = (LinearLayout) mView.findViewById(R.id.llayout);

        if (isMe) {
            llayout.setVisibility(View.GONE);
        } else {
            llayout.setVisibility(View.VISIBLE);
        }
        if (isFriend) {

            bdeleteLl.setVisibility(View.VISIBLE);
        } else {
            bdeleteLl.setVisibility(View.GONE);
        }


        bshare_wx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mShareCallback.onClickShare(SHARE_WEIXIN);
            }
        });
        bshare_dd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mShareCallback.onClickShare(SHARE_DD);
            }
        });
        bshare_pcircle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mShareCallback.onClickShare(SHARE_CIRCLE);
            }
        });
        bshare_qq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mShareCallback.onClickShare(SHARE_QQ);
            }
        });


        breportLl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mShareCallback.onClickShare(REPORT_GO);
            }
        });

        bdeleteLl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mShareCallback.onClickShare(DELETE_GO);
            }
        });

        mView.findViewById(R.id.tv_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
    }

    public static class Builder extends AQLDialogBuilder<Builder> {
        private ShareNewCallback mShareCallback;
        private boolean isFriend;
        private boolean isMe;
        private Context context;

        public Builder setContext(Context context) {
            this.context = context;
            return this;
        }

        public boolean isFriend() {
            return isFriend;
        }

        public Builder setFriend(boolean friend) {
            this.isFriend = friend;
            return this;
        }

        public Builder setIsMe(boolean isMe) {
            this.isMe = isMe;
            return this;
        }

        public Builder setShareCallback(ShareNewCallback mShareCallback) {
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
            return new QLPersonDialog(mContext, mTheme);
        }

        @Override
        protected void buildDialog(QLDialog dialog) {
            QLPersonDialog qlDialog = (QLPersonDialog) dialog;
            setView(qlDialog, R.layout.dialog_person_share);
            qlDialog.setContext(context);
            qlDialog.setFriend(isFriend);
            qlDialog.setMe(isMe);
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
        SHARE_WEIBO,
        SHARE_QRCODE,
        SHARE_DD,//分享到钉钉
        REPORT_GO,
        DELETE_GO,
        SHARE_FRIEND
    }

    public interface ShareNewCallback {
        void onClickShare(ShareType type);
    }

}
