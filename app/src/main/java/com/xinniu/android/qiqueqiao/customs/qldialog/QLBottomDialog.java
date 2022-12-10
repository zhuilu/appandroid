package com.xinniu.android.qiqueqiao.customs.qldialog;

import android.content.Context;
//import android.support.v4.content.ContextCompat;
import androidx.appcompat.app.AppCompatDialog;
import androidx.core.content.ContextCompat;

import android.view.Gravity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.xinniu.android.qiqueqiao.R;
import com.xinniu.android.qiqueqiao.utils.ToastUtils;

/**
 * Created by qinlei
 * Created on 2017/12/12
 * Created description :
 */

public class QLBottomDialog extends QLDialog {

    private int checkColor;
    private int normalColor;
    private int checkPosition;
    private String strCancel;
    private String strSure;
    private String strOne;
    private String strTwo;
    private int strThree;

    private BottomDialogItemCallback bottomDialogItemCallback;//点击列表回调
//    private BottomDialogSureCallback bottomDialogSureCallback;//点击确定回调

    private TextView tvOne;
    private TextView tvTwo;
    private TextView tvThree;

    protected QLBottomDialog(Context context, int theme) {
        super(context, theme);
    }

    public void setCheckColor(int checkColor) {
        this.checkColor = checkColor;
    }

    public void setNormalColor(int normalColor) {
        this.normalColor = normalColor;
    }

    public void setCheckPosition(int checkPosition) {
        this.checkPosition = checkPosition;
    }

    public void setStrCancel(String strCancel) {
        this.strCancel = strCancel;
    }

    public void setStrSure(String strSure) {
        this.strSure = strSure;
    }

    public void setStrOne(String strOne) {
        this.strOne = strOne;
    }

    public void setStrTwo(String strTwo) {
        this.strTwo = strTwo;
    }

    public void setStrThree(int strThree){this.strThree = strThree; }

    public void setBottomDialogItemCallback(BottomDialogItemCallback bottomDialogItemCallback) {
        this.bottomDialogItemCallback = bottomDialogItemCallback;
    }

//    public void setBottomDialogSureCallback(BottomDialogSureCallback bottomDialogSureCallback) {
//        this.bottomDialogSureCallback = bottomDialogSureCallback;
//    }

    @Override
    protected void createDialog(View mView) {
        TextView tvCancel = (TextView) mView.findViewById(R.id.tv_cancel);
        TextView tvSure = (TextView) mView.findViewById(R.id.tv_sure);
        tvOne = (TextView) mView.findViewById(R.id.tv_1);
        tvTwo = (TextView) mView.findViewById(R.id.tv_2);
        tvThree = (TextView) mView.findViewById(R.id.tv_3);

        tvOne.setText(strOne);
        tvTwo.setText(strTwo);

        setCheck(checkPosition);

        tvOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setCheck(0);
                if (bottomDialogItemCallback != null) {
                    bottomDialogItemCallback.onClick(checkPosition);
                    dismiss();
                }
            }
        });
        tvTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setCheck(1);
                if (bottomDialogItemCallback != null) {
                    bottomDialogItemCallback.onClick(checkPosition);
                    dismiss();
                }
            }
        });
        if (strThree == 1){
            tvThree.setVisibility(View.VISIBLE);
        }else {
            tvThree.setVisibility(View.GONE);
        }
        tvThree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        tvCancel.setText(strCancel);
        tvSure.setText(strSure);
        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
        tvSure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkPosition == -1) {
                    ToastUtils.showCentetImgToast(getContext(), "还未选中任何选项");
                    return;
                }
//                if (bottomDialogSureCallback != null) {
//                    bottomDialogSureCallback.onClick(checkPosition);
//                    dismiss();
//                }
            }
        });
    }

    /**
     * 设置选中
     *
     * @param positon
     */
    private void setCheck(int positon) {
        checkPosition = positon;
        tvOne.setTextColor(ContextCompat.getColor(getContext(), normalColor));
        tvTwo.setTextColor(ContextCompat.getColor(getContext(), normalColor));

        switch (positon) {
            case 0:
                tvOne.setTextColor(ContextCompat.getColor(getContext(), checkColor));
                break;
            case 1:
                tvTwo.setTextColor(ContextCompat.getColor(getContext(), checkColor));
                break;
            default:
                break;
        }
    }

    public static class Builder extends AQLDialogBuilder<QLBottomDialog.Builder> {

        private int checkColor = R.color._333;//选中的颜色
        private int normalColor = R.color._999;//没有选中的颜色
        private int checkPosition = -1;
        private String strCancel = "取消";
        private String strSure = "确定";
        private String strOne;
        private String strTwo;
        private int strThree;

        private BottomDialogItemCallback bottomDialogItemCallback;//点击列表回调
//        private BottomDialogSureCallback bottomDialogSureCallback;//点击确定回调

        public Builder setCheckColor(int checkColor) {
            this.checkColor = checkColor;
            return this;
        }

        public Builder setNormalColor(int normalColor) {
            this.normalColor = normalColor;
            return this;
        }

        public Builder setCheckPosition(int checkPosition) {
            this.checkPosition = checkPosition;
            return this;
        }

        public Builder setStrCancel(String strCancel) {
            this.strCancel = strCancel;
            return this;
        }

        public Builder setStrSure(String strSure) {
            this.strSure = strSure;
            return this;
        }

        public Builder setStrOne(String strOne) {
            this.strOne = strOne;
            return this;
        }

        public Builder setStrTwo(String strTwo) {
            this.strTwo = strTwo;
            return this;
        }

        public Builder setType(int Type){
            this.strThree = Type;
            return this;
        }

        public Builder setBottomDialogItemCallback(BottomDialogItemCallback bottomDialogItemCallback) {
            this.bottomDialogItemCallback = bottomDialogItemCallback;
            return this;
        }

//        public Builder setBottomDialogSureCallback(BottomDialogSureCallback bottomDialogSureCallback) {
//            this.bottomDialogSureCallback = bottomDialogSureCallback;
//            return this;
//        }

        public Builder(Context context) {
            super(context);
        }

        public Builder(Context context, int theme) {
            super(context, theme);
        }

        @Override
        protected AppCompatDialog buildWithTheme(Context mContext, int mTheme) {
            return new QLBottomDialog(mContext, mTheme);
        }

        @Override
        protected void buildDialog(QLDialog dialog) {
            QLBottomDialog qlDialog = (QLBottomDialog) dialog;
            setView(qlDialog, R.layout.dialog_bottom);

            qlDialog.setCheckColor(checkColor);
            qlDialog.setNormalColor(normalColor);
            qlDialog.setCheckPosition(checkPosition);
            qlDialog.setStrCancel(strCancel);
            qlDialog.setStrSure(strSure);
            qlDialog.setStrOne(strOne);
            qlDialog.setStrTwo(strTwo);
            qlDialog.setStrThree(strThree);
            qlDialog.setBottomDialogItemCallback(bottomDialogItemCallback);
//            qlDialog.setBottomDialogSureCallback(bottomDialogSureCallback);

            qlDialog.setFillWidthPercent(1f);
            qlDialog.setGravity(Gravity.BOTTOM);
            qlDialog.setAnimation(R.style.anim_bottom);
        }
    }

    public interface BottomDialogItemCallback {
        void onClick(int position);
    }

//    public interface BottomDialogSureCallback {
//        void onClick(int position);
//    }

}
