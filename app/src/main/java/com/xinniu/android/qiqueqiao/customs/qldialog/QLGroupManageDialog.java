package com.xinniu.android.qiqueqiao.customs.qldialog;

import android.content.Context;
//import android.support.v7.app.AppCompatDialog;
import android.view.Gravity;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.xinniu.android.qiqueqiao.R;
import com.xinniu.android.qiqueqiao.activity.GroupMemberManageActivity;
import com.xinniu.android.qiqueqiao.dialog.MakeoverCodeDialog;
import com.xinniu.android.qiqueqiao.utils.ShowUtils;

import static com.xinniu.android.qiqueqiao.customs.qldialog.QLGroupManageDialog.ShareType.BANNER1;
import static com.xinniu.android.qiqueqiao.customs.qldialog.QLGroupManageDialog.ShareType.BANNER3;
import static com.xinniu.android.qiqueqiao.customs.qldialog.QLGroupManageDialog.ShareType.DELETE;
import static com.xinniu.android.qiqueqiao.customs.qldialog.QLGroupManageDialog.ShareType.DOMANAGER;
import static com.xinniu.android.qiqueqiao.customs.qldialog.QLGroupManageDialog.ShareType.MAKEOVER;
import static com.xinniu.android.qiqueqiao.customs.qldialog.QLGroupManageDialog.ShareType.NOBANNER;
import static com.xinniu.android.qiqueqiao.customs.qldialog.QLGroupManageDialog.ShareType.NOMANAGER;

/**
 * Created by yuchance on 2018/10/12.
 */

public class QLGroupManageDialog extends QLDialog {
    private ShareNewCallback mShareCallback;

    private int isBanner;
    private int isManager;
    private int userManager;

    public void setUserManager(int userManager) {
        this.userManager = userManager;
    }

    public void setIsBanner(int isBanner) {
        this.isBanner = isBanner;
    }

    public void setIsManager(int isManager) {
        this.isManager = isManager;
    }

    public void setmShareCallback(ShareNewCallback mShareCallback) {
        this.mShareCallback = mShareCallback;
    }

    protected QLGroupManageDialog(Context context, int theme) {
        super(context, theme);
    }

    @Override
    protected void createDialog(View mView) {
       RelativeLayout bdomanagerTv = (RelativeLayout) mView.findViewById(R.id.bdomanagerTv);
        RelativeLayout mmakeoverTv = (RelativeLayout) mView.findViewById(R.id.mmakeoverTv);
        RelativeLayout mbanned1Tv = (RelativeLayout) mView.findViewById(R.id.mbanned1Tv);
        RelativeLayout mbanned3Tv = (RelativeLayout) mView.findViewById(R.id.mbanned3Tv);
        RelativeLayout mdeleteTv = (RelativeLayout) mView.findViewById(R.id.mdeleteTv);
        RelativeLayout bcancelTv = (RelativeLayout) mView.findViewById(R.id.bcancelTv);
        RelativeLayout noBannerTv = (RelativeLayout) mView.findViewById(R.id.mnobannedTv);
        TextView managerTv = (TextView) mView.findViewById(R.id.mmanagerTV);
        if (isManager == 1){
            mmakeoverTv.setVisibility(View.GONE);
            managerTv.setText("解除管理员");
            mbanned1Tv.setVisibility(View.GONE);
            mbanned3Tv.setVisibility(View.GONE);
        }else if (isManager == 2){
            mmakeoverTv.setVisibility(View.VISIBLE);
            mbanned1Tv.setVisibility(View.GONE);
            mbanned3Tv.setVisibility(View.GONE);
        }else {
            if (isBanner == 0) {
                noBannerTv.setVisibility(View.GONE);
                mbanned1Tv.setVisibility(View.VISIBLE);
                mbanned3Tv.setVisibility(View.VISIBLE);
            } else if (isBanner == 1) {
                noBannerTv.setVisibility(View.VISIBLE);
                mbanned1Tv.setVisibility(View.GONE);
                mbanned3Tv.setVisibility(View.GONE);
            }
        }
        if (userManager == 2){
            mmakeoverTv.setVisibility(View.VISIBLE);
            bdomanagerTv.setVisibility(View.VISIBLE);
        }else if (userManager == 1){
            mmakeoverTv.setVisibility(View.GONE);
            bdomanagerTv.setVisibility(View.GONE);
        }
        bdomanagerTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isManager == 1){
                    mShareCallback.onClickShare(NOMANAGER);
                }else {
                    mShareCallback.onClickShare(DOMANAGER);
                }
            }
        });
        mmakeoverTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                dialog = new MakeoverCodeDialog(GroupMemberManageActivity.this);
//                dialog.show(getFragmentManager(),"group");
                dismiss();
                mShareCallback.onClickShare(MAKEOVER);
            }
        });
        mbanned1Tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mShareCallback.onClickShare(BANNER1);
            }
        });
        mbanned3Tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mShareCallback.onClickShare(BANNER3);
            }
        });
        mdeleteTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mShareCallback.onClickShare(DELETE);
            }
        });
        bcancelTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        noBannerTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mShareCallback.onClickShare(NOBANNER);
            }
        });

    }

    public static class Builder extends QLDialog.AQLDialogBuilder<QLGroupManageDialog.Builder> {
        private ShareNewCallback mShareCallback;
        private int isBanner;
        private int isManager;
        private int userManager;

        public Builder setUserManager(int userManager){
            this.userManager = userManager;
            return this;
        }

        public Builder setIsBanner(int isBanner) {
            this.isBanner = isBanner;
            return this;
        }

        public Builder setIsManager(int isManager) {
            this.isManager = isManager;
            return this;
        }

        public Builder setmShareCallback(ShareNewCallback mShareCallback) {
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
            return new QLGroupManageDialog(mContext, mTheme);
        }

        @Override
        protected void buildDialog(QLDialog dialog) {
            QLGroupManageDialog qlDialog = (QLGroupManageDialog) dialog;
            setView(qlDialog, R.layout.popwindow_groupmember);
            qlDialog.setIsBanner(isBanner);
            qlDialog.setIsManager(isManager);
            qlDialog.setUserManager(userManager);
            qlDialog.setmShareCallback(mShareCallback);
            qlDialog.setAnimation(R.style.anim_bottom);
            qlDialog.setFillWidthPercent(1f);
            qlDialog.setGravity(Gravity.BOTTOM);
        }
    }

    public enum ShareType {
        DOMANAGER,
        MAKEOVER,
        BANNER1,
        BANNER3,
        DELETE,
        NOBANNER,
        NOMANAGER

    }

    public interface ShareNewCallback {
        void onClickShare(ShareType type);
    }

}
