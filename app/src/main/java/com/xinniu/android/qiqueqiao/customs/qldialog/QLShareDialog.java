package com.xinniu.android.qiqueqiao.customs.qldialog;

import android.content.Context;
//import android.support.v7.app.AppCompatDialog;
//import android.support.v7.widget.GridLayoutManager;
//import androidx.recyclerview.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatDialog;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.scwang.smartrefresh.layout.util.DensityUtil;
import com.xinniu.android.qiqueqiao.R;
import com.xinniu.android.qiqueqiao.adapter.ShareDialogAdapter;
import com.xinniu.android.qiqueqiao.adapter.base.BDOnItemClickListener;
import com.xinniu.android.qiqueqiao.customs.wheelview.util.WheelUtils;
import com.xinniu.android.qiqueqiao.utils.ComUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by qinlei
 * Created on 2017/12/12
 * Created description :
 */

public class QLShareDialog extends QLDialog {
    private List<ShareType> mShareTypeList = new ArrayList<>();
    private ShareCallback mShareCallback;
    private int withQrCode;

    public void setmShareCallback(ShareCallback mShareCallback) {
        this.mShareCallback = mShareCallback;
    }

    public void setWithQRCode(int withQRCode) {
        this.withQrCode = withQRCode;
        //初始化分享列表
        if (withQRCode ==1) {
            mShareTypeList.add(ShareType.SHARE_WEIXIN);
            mShareTypeList.add(ShareType.SHARE_CIRCLE);
            mShareTypeList.add(ShareType.SHARE_QQ);
            mShareTypeList.add(ShareType.SHARE_WEIBO);
            mShareTypeList.add(ShareType.SHARE_QRCODE);
        } else if (withQRCode == 2){
            mShareTypeList.add(ShareType.SHARE_WEIXIN);
            mShareTypeList.add(ShareType.SHARE_CIRCLE);
            mShareTypeList.add(ShareType.SHARE_QQ);
            mShareTypeList.add(ShareType.SHARE_WEIBO);
        }else if (withQRCode == 3){
            mShareTypeList.add(ShareType.SHARE_WEIXIN);
            mShareTypeList.add(ShareType.SHARE_CIRCLE);
        }else if (withQRCode == 4){
            mShareTypeList.add(ShareType.SHARE_WEIXIN);
            mShareTypeList.add(ShareType.SHARE_CIRCLE);
            mShareTypeList.add(ShareType.SHARE_QRCODE);
        }
    }

    public void setCollectStatus (boolean isCollect){


    }


    protected QLShareDialog(Context context, int theme) {
        super(context, theme);
    }

    @Override
    protected void createDialog(View mView) {
        RecyclerView recyclerView = (RecyclerView) mView.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 5));
        ShareDialogAdapter adapter = new ShareDialogAdapter(getContext(), mShareTypeList);
        if (withQrCode==4){
            adapter.setQrcodeStr("生成海报");
        }else {
            adapter.setQrcodeStr("生成图片");
        }
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new BDOnItemClickListener() {
            @Override
            public void onItemClick(ViewGroup parent, View view, Object o, int position) {
                if (mShareCallback != null) {
                    mShareCallback.onClickShare((ShareType) o);
                    dismiss();
                }
            }

            @Override
            public boolean onItemLongClick(ViewGroup parent, View view, Object o, int position) {
                return false;
            }
        });
        mView.findViewById(R.id.tv_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
    }

    public static class Builder extends QLDialog.AQLDialogBuilder<QLShareDialog.Builder> {
        private ShareCallback mShareCallback;
        private int withQRCode = 1;
        private boolean isCollect = false;

        public Builder setWithQRCode(int isWith) {
            this.withQRCode = isWith;
            return this;
        }
        public Builder setShareCallback(ShareCallback mShareCallback) {
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
            return new QLShareDialog(mContext, mTheme);
        }

        @Override
        protected void buildDialog(QLDialog dialog) {
            QLShareDialog qlDialog = (QLShareDialog) dialog;
            setView(qlDialog, R.layout.dialog_share);

            qlDialog.setWithQRCode(withQRCode);
            qlDialog.setCollectStatus(isCollect);
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
        SHARE_QRCODE
    }

    public interface ShareCallback {
        void onClickShare(ShareType type);
    }


}
