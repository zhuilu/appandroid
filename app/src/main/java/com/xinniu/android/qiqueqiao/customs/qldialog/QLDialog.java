package com.xinniu.android.qiqueqiao.customs.qldialog;

import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDialog;

import com.xinniu.android.qiqueqiao.R;

import androidx.appcompat.app.AppCompatDialog;

/**
 * Created by qinlei
 * Created on 2017/12/6
 * Created description :
 */

public abstract class QLDialog extends AppCompatDialog {
    protected static final int NO_ANIMATION = -1;

    protected float mFillWidthPercent = ViewGroup.LayoutParams.MATCH_PARENT;
    protected int mGravity;
    protected int mAnimation = NO_ANIMATION;
    protected View mView;

    protected QLDialog(Context context, int theme) {
        super(context, theme);
    }

    /**
     * 设置 dialog 的宽度占比
     *
     * @param mFillPercent 1～0 之间 1 表示为 MATCH_PARENT
     */
    protected void setFillWidthPercent(float mFillPercent) {
        this.mFillWidthPercent = mFillPercent;
    }

    protected void setGravity(int mGravity) {
        this.mGravity = mGravity;
    }

    protected void setAnimation(int mAnimation) {
        this.mAnimation = mAnimation;
    }

    protected void setView(View mView) {
        this.mView = mView;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initContentView();

        Window dialog_window = this.getWindow();
        setFileWidth(dialog_window);
        setGravity(dialog_window);
        setAnimation(dialog_window);
    }

    private void setFileWidth(Window dialog_window) {
        if (mFillWidthPercent == ViewGroup.LayoutParams.MATCH_PARENT) {
            dialog_window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        } else {
            int deviceWidth = Resources.getSystem().getDisplayMetrics().widthPixels;
            dialog_window.setLayout((int) (deviceWidth * mFillWidthPercent), ViewGroup.LayoutParams.WRAP_CONTENT);
        }
    }

    private void setGravity(Window dialog_window) {
        dialog_window.setGravity(mGravity);
    }

    private void setAnimation(Window dialog_window) {
        if (mAnimation != NO_ANIMATION) {
            dialog_window.setWindowAnimations(mAnimation);
        }
    }

    private void initContentView() {
        if (mView == null) {
            throw new RuntimeException("dialog contentView is null");
        } else {
            setContentView(mView);
            createDialog(mView);
        }
    }

    /**
     * 初始化 mView 将设置的属性初始化到 Dialog 上
     *
     * @param mView
     */
    protected abstract void createDialog(View mView);

    public static abstract class AQLDialogBuilder<T extends AQLDialogBuilder> {
        private static final int NO_DEFINE_THEME = -1;
        private Context mContext;
        private int mTheme = NO_DEFINE_THEME;
        private float mDimAmount = 0.7f;
        private boolean mCancelable = true;
        private boolean mCancelableOnTouchOutside = true;

        private OnCancelListener mOnCancelListener;
        private OnDismissListener mOnDismissListener;
        private OnKeyListener mOnKeyListener;
        private OnShowListener mOnShowListener;

        public AQLDialogBuilder(Context context) {
            this.mContext = context;
        }

        public AQLDialogBuilder(Context context, int theme) {
            this.mContext = context;
            this.mTheme = theme;
        }

        protected void setView(QLDialog qlDialog, int layoutId) {
            View inflateView = View.inflate(mContext, layoutId, null);
            qlDialog.setView(inflateView);
        }

        /**
         * 设置背景透明度
         *
         * @param dimAmount 0 ～ 1 之间
         * @return
         */
        public T setDimAmount(float dimAmount) {
            this.mDimAmount = dimAmount;
            return getThis();
        }

        public T setCancelable(boolean cancelable) {
            this.mCancelable = cancelable;
            return getThis();
        }

        public T setCancelableOnTouchOutside(boolean cancelableOnTouchOutside) {
            this.mCancelableOnTouchOutside = cancelableOnTouchOutside;
            return getThis();
        }

        public T setOnCancelListener(OnCancelListener onCancelListener) {
            this.mOnCancelListener = onCancelListener;
            return getThis();
        }

        public T setOnDismissListener(OnDismissListener onDismissListener) {
            this.mOnDismissListener = onDismissListener;
            return getThis();
        }

        public T setOnKeyListener(OnKeyListener onKeyListener) {
            this.mOnKeyListener = onKeyListener;
            return getThis();
        }

        public T setOnShowListener(OnShowListener onShowListener) {
            this.mOnShowListener = onShowListener;
            return getThis();
        }


        protected T getThis() {
            return (T) this;
        }

        private void setDialogParams(AppCompatDialog dialog) {
            if (dialog.getWindow() != null) {
                dialog.getWindow().setDimAmount(mDimAmount);
            }
            dialog.setCancelable(mCancelable);
            dialog.setCanceledOnTouchOutside(mCancelableOnTouchOutside);
            dialog.setOnCancelListener(mOnCancelListener);
            dialog.setOnDismissListener(mOnDismissListener);
            dialog.setOnKeyListener(mOnKeyListener);
            dialog.setOnShowListener(mOnShowListener);
        }

        public void show(AppCompatActivity activity) {
            //处理当前弹窗依附的activity已经消失的情况
            if (activity == null) {
                return;
            }

            if (activity.isFinishing()) {
                return;
            }

            build().show();
        }

        public AppCompatDialog build() {
            AppCompatDialog appCompatDialog = initDialog();
            if (appCompatDialog instanceof QLDialog) {
                buildDialog((QLDialog) appCompatDialog);
            }
            setDialogParams(appCompatDialog);
            return appCompatDialog;
        }


        private AppCompatDialog initDialog() {
            AppCompatDialog qlDialog;
            if (mTheme == NO_DEFINE_THEME) {
                qlDialog = buildWithTheme(mContext, R.style.QLDialog);
            } else {
                qlDialog = buildWithTheme(mContext, mTheme);
            }
            return qlDialog;
        }

        protected abstract AppCompatDialog buildWithTheme(Context mContext, int mTheme);

        protected abstract void buildDialog(QLDialog dialog);
    }
}
