package com.xinniu.android.qiqueqiao.customs.qldialog;

import android.content.Context;
import androidx.appcompat.app.AppCompatDialog;
import android.view.Gravity;
import android.view.View;

import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.scwang.smartrefresh.layout.util.DensityUtil;
import com.xinniu.android.qiqueqiao.R;
import com.xinniu.android.qiqueqiao.adapter.EditResouceAdapter;
import com.xinniu.android.qiqueqiao.utils.ComUtils;
import com.xinniu.android.qiqueqiao.utils.ShowUtils;

import static com.xinniu.android.qiqueqiao.customs.qldialog.QLnewDialog.ShareType.CLOSE_MESSAGE;
import static com.xinniu.android.qiqueqiao.customs.qldialog.QLnewDialog.ShareType.COLLECT_GO;
import static com.xinniu.android.qiqueqiao.customs.qldialog.QLnewDialog.ShareType.DELETE_GO;
import static com.xinniu.android.qiqueqiao.customs.qldialog.QLnewDialog.ShareType.DOWN_GO;
import static com.xinniu.android.qiqueqiao.customs.qldialog.QLnewDialog.ShareType.EDIT_GO;
import static com.xinniu.android.qiqueqiao.customs.qldialog.QLnewDialog.ShareType.OPEN_MESSAGE;
import static com.xinniu.android.qiqueqiao.customs.qldialog.QLnewDialog.ShareType.REPORT_GO;
import static com.xinniu.android.qiqueqiao.customs.qldialog.QLnewDialog.ShareType.SHARE_CIRCLE;
import static com.xinniu.android.qiqueqiao.customs.qldialog.QLnewDialog.ShareType.SHARE_DD;
import static com.xinniu.android.qiqueqiao.customs.qldialog.QLnewDialog.ShareType.SHARE_FRIEND;
import static com.xinniu.android.qiqueqiao.customs.qldialog.QLnewDialog.ShareType.SHARE_QQ;
import static com.xinniu.android.qiqueqiao.customs.qldialog.QLnewDialog.ShareType.SHARE_QRCODE;
import static com.xinniu.android.qiqueqiao.customs.qldialog.QLnewDialog.ShareType.SHARE_WEIBO;
import static com.xinniu.android.qiqueqiao.customs.qldialog.QLnewDialog.ShareType.SHARE_WEIXIN;
import static com.xinniu.android.qiqueqiao.customs.qldialog.QLnewDialog.ShareType.TOP_GO;


/**
 * Created by yuchance on 2018/7/31.
 */

public class QLnewDialog extends QLDialog {
    private ShareNewCallback mShareCallback;
    private boolean isCollect;
    private boolean isMessage;//开启关闭留言
    private int isVer;
    private boolean isMe;
    private Context context;

    public void setContext(Context context) {
        this.context = context;
    }

    public void setmShareCallback(ShareNewCallback mShareCallback) {
        this.mShareCallback = mShareCallback;
    }


    public void setCollectStatus(boolean isCollect) {
        this.isCollect = isCollect;
    }

    public void setMessageStatus(boolean isMessage) {
        this.isMessage = isMessage;
    }

    public void setIsVer(int isVer) {
        this.isVer = isVer;
    }

    public void setMe(boolean me) {
        isMe = me;
    }

    protected QLnewDialog(Context context, int theme) {
        super(context, theme);
    }

    @Override
    protected void createDialog(View mView) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        int screenWidth = wm.getDefaultDisplay().getWidth();
        LinearLayout bshare_wx = (LinearLayout) mView.findViewById(R.id.bshare_wx);
        LinearLayout bshare_pcircle = (LinearLayout) mView.findViewById(R.id.bshare_circle);
        LinearLayout bshare_qq = (LinearLayout) mView.findViewById(R.id.bshare_qq);
        LinearLayout bshare_wb = (LinearLayout) mView.findViewById(R.id.bshare_wb);
        LinearLayout bshare_dd = (LinearLayout) mView.findViewById(R.id.bshare_dd);

        LinearLayout bshare_tp = (LinearLayout) mView.findViewById(R.id.bshare_pht);
        LinearLayout bcollectLl = (LinearLayout) mView.findViewById(R.id.bcollect_Ll);
        ImageView collectImg = (ImageView) mView.findViewById(R.id.isCollect_img);

        LinearLayout bmessageLm = (LinearLayout) mView.findViewById(R.id.btop_Lm);
        ImageView messageImg = (ImageView) mView.findViewById(R.id.img_message);
        TextView messageText = (TextView) mView.findViewById(R.id.tv_message);

        LinearLayout breportLl = (LinearLayout) mView.findViewById(R.id.breport_Ll);
        LinearLayout btopLl = (LinearLayout) mView.findViewById(R.id.btop_Ll);
        LinearLayout beditLl = (LinearLayout) mView.findViewById(R.id.bedit_Ll);
        LinearLayout bdownLl = (LinearLayout) mView.findViewById(R.id.bdown_Ll);
        LinearLayout bdeleteLl = (LinearLayout) mView.findViewById(R.id.bdelete_Ll);
        LinearLayout bshare_friend = (LinearLayout) mView.findViewById(R.id.bshare_friend);
//        HorizontalScrollView yhandleHScroll = (HorizontalScrollView) mView.findViewById(R.id.yhandleHScroll);
        if (screenWidth > 0) {
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(screenWidth / 11 * 2, ViewGroup.LayoutParams.WRAP_CONTENT);
            bshare_tp.setLayoutParams(params);
            bshare_wb.setLayoutParams(params);
        //    bcollectLl.setLayoutParams(params);
            breportLl.setLayoutParams(params);
            btopLl.setLayoutParams(params);
            beditLl.setLayoutParams(params);
            bdownLl.setLayoutParams(params);
            bdeleteLl.setLayoutParams(params);
            bmessageLm.setLayoutParams(params);

            bshare_friend.setLayoutParams(params);
            bshare_wx.setLayoutParams(params);
            bshare_pcircle.setLayoutParams(params);
            bshare_qq.setLayoutParams(params);
            bshare_dd.setLayoutParams(params);

        }


        if (isCollect) {
            ShowUtils.showImageResource(collectImg, R.mipmap.ncollected_icon);
        } else {
            ShowUtils.showImageResource(collectImg, R.mipmap.collect_icon);
        }
        if (isMessage) {//留言开启状态
            ShowUtils.showImageResource(messageImg, R.mipmap.close_message);
            ShowUtils.showTextPerfect(messageText, R.string.close_leaving_message_text);
        } else {
            ShowUtils.showImageResource(messageImg, R.mipmap.open_message);
            ShowUtils.showTextPerfect(messageText, R.string.leaving_message_text);
        }
        if (isMe) {
          //  bcollectLl.setVisibility(View.VISIBLE);
            breportLl.setVisibility(View.GONE);
            bmessageLm.setVisibility(View.VISIBLE);//留言显示
            if (isVer == 0) {
                btopLl.setVisibility(View.VISIBLE);
                beditLl.setVisibility(View.VISIBLE);
                bdownLl.setVisibility(View.GONE);
                bdeleteLl.setVisibility(View.VISIBLE);
            } else if (isVer == 1) {
                btopLl.setVisibility(View.VISIBLE);
                beditLl.setVisibility(View.VISIBLE);
                bdownLl.setVisibility(View.VISIBLE);
                bdeleteLl.setVisibility(View.VISIBLE);
            } else if (isVer == 2) {
                btopLl.setVisibility(View.GONE);
                beditLl.setVisibility(View.VISIBLE);
                bdownLl.setVisibility(View.GONE);
                bdeleteLl.setVisibility(View.VISIBLE);
            } else if (isVer == 3) {
                btopLl.setVisibility(View.VISIBLE);
                beditLl.setVisibility(View.VISIBLE);
                bdownLl.setVisibility(View.GONE);
                bdeleteLl.setVisibility(View.VISIBLE);
                bmessageLm.setVisibility(View.GONE);//留言隐藏
            }else if (isVer == 4) {
                btopLl.setVisibility(View.GONE);
                beditLl.setVisibility(View.GONE);
                bdownLl.setVisibility(View.GONE);
                bdeleteLl.setVisibility(View.GONE);
                bmessageLm.setVisibility(View.VISIBLE);//留言隐藏
            }
        } else {
//            yhandleHScroll.setVisibility(View.VISIBLE);
        //    bcollectLl.setVisibility(View.VISIBLE);
            breportLl.setVisibility(View.VISIBLE);
            btopLl.setVisibility(View.GONE);
            beditLl.setVisibility(View.GONE);
            bdownLl.setVisibility(View.GONE);
            bdeleteLl.setVisibility(View.GONE);
            bmessageLm.setVisibility(View.GONE);//留言隐藏
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
        bshare_wb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mShareCallback.onClickShare(SHARE_WEIBO);
            }
        });
        bshare_tp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mShareCallback.onClickShare(SHARE_QRCODE);
            }
        });
        bcollectLl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mShareCallback.onClickShare(COLLECT_GO);
            }
        });
        bmessageLm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isMessage) {
                    mShareCallback.onClickShare(CLOSE_MESSAGE);//关闭留言
                } else {
                    mShareCallback.onClickShare(OPEN_MESSAGE);//开启留言
                }
            }
        });
        breportLl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mShareCallback.onClickShare(REPORT_GO);
            }
        });
        btopLl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mShareCallback.onClickShare(TOP_GO);
            }
        });
        beditLl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mShareCallback.onClickShare(EDIT_GO);
            }
        });
        bdownLl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mShareCallback.onClickShare(DOWN_GO);
            }
        });
        bdeleteLl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mShareCallback.onClickShare(DELETE_GO);
            }
        });
        bshare_friend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mShareCallback.onClickShare(SHARE_FRIEND);
                dismiss();
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
        private boolean isMessage = true;//开启关闭留言
        private boolean isCollect = false;
        private int isVer;
        private boolean isMe;
        private Context context;

        public Builder setContext(Context context) {
            this.context = context;
            return this;
        }

        public Builder setMessage(boolean message) {
            isMessage = message;
            return this;
        }

        public Builder setCollectStatus(boolean isCollect) {
            this.isCollect = isCollect;
            return this;
        }

        public Builder setIsVer(int isVer) {
            this.isVer = isVer;
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
            return new QLnewDialog(mContext, mTheme);
        }

        @Override
        protected void buildDialog(QLDialog dialog) {
            QLnewDialog qlDialog = (QLnewDialog) dialog;
            setView(qlDialog, R.layout.dialog_new_share);
            qlDialog.setCollectStatus(isCollect);
            qlDialog.setContext(context);
            qlDialog.setIsVer(isVer);
            qlDialog.setMe(isMe);
            qlDialog.setMessageStatus(isMessage);
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
        COLLECT_GO,
        REPORT_GO,
        OPEN_MESSAGE,//开启留言
        CLOSE_MESSAGE,//关闭留言
        TOP_GO,
        EDIT_GO,
        DOWN_GO,
        DELETE_GO,
        SHARE_FRIEND
    }

    public interface ShareNewCallback {
        void onClickShare(ShareType type);
    }

}
