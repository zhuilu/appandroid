package com.xinniu.android.qiqueqiao.widget;

import android.content.Context;
import androidx.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;

import com.xinniu.android.qiqueqiao.R;

/**
 * Created by lzq on 2018/2/1.
 */

public class QQQCheckBox extends AppCompatImageView {
    private boolean isCheck;
    public QQQCheckBox(Context context) {
        super(context);
        init();
    }

    public QQQCheckBox(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public QQQCheckBox(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }
    private void init(){
        this.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isCheck){
                    isCheck = false;
                    setImageResource(R.mipmap.selected_false);
                }else{
                    isCheck = true;
                    setImageResource(R.mipmap.selected_true);
                }
                if (onCheckChangeListener != null){
                    onCheckChangeListener.onChange(isCheck);
                }
            }
        });
    }

    public void setCheck(boolean isCheck){
        this.isCheck = isCheck;
        if (isCheck){
            setImageResource(R.mipmap.selected_true);
        }else{
            setImageResource(R.mipmap.selected_false);
        }
    }


    public boolean isCheck(){
        return isCheck;
    }

    public interface OnCheckChangeListener{
        void onChange(boolean isCheak);
    }
    OnCheckChangeListener onCheckChangeListener;
    public void setOnCheckChangeListener(OnCheckChangeListener onCheckChangeListener){
        this.onCheckChangeListener = onCheckChangeListener;
    }

}
