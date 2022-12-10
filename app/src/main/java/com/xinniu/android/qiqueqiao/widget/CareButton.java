package com.xinniu.android.qiqueqiao.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.xinniu.android.qiqueqiao.R;
import com.xinniu.android.qiqueqiao.bean.UserFollowBean;
import com.xinniu.android.qiqueqiao.request.RequestManager;
import com.xinniu.android.qiqueqiao.request.callback.UserFolloweCallback;
import com.xinniu.android.qiqueqiao.utils.ToastUtils;

/**
 * Created by lzq on 2018/1/15.
 */

@SuppressLint("AppCompatCustomView")
public class CareButton extends TextView{
    private Context context;
    public static final int FOLLOW_ACTION = 1;
    public static final int UNFOLLOW_ACTION = 2;

    public static final int FOLLOW_STATUS= 1;
    public static final int UNFOLLOW_STATUS = 0;
    private int followStatus = 1;
    private OnFollowListener onFollowListener;
    public CareButton(Context context) {
        super(context);
        this.context = context;
    }

    public CareButton(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
    }

    public CareButton(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
    }

    public void initFollowStatus(int status){
        followStatus = status;
        if (followStatus == FOLLOW_STATUS){
            setText("已关注");
            setSelected(true);
            setTextColor(ContextCompat.getColor(context,R.color.colorPrimary));
            setCompoundDrawables(null,null,null,null);
            if (changeState!=null) {
                changeState.changeState(true);
            }
        }
        if (followStatus == UNFOLLOW_STATUS){
            setText("关注");
            setSelected(false);
            setTextColor(ContextCompat.getColor(context,R.color.white));
            Drawable drawable = getResources().getDrawable(R.mipmap.com_add);
            drawable.setBounds(0,0,drawable.getMinimumWidth(),drawable.getMinimumHeight());
            setCompoundDrawablePadding(6);
            setCompoundDrawables(drawable,null,null,null);
            if (changeState!=null){
                changeState.changeState(false);
            }
        }
    }

    public void click(int uid){
        if(followStatus == CareButton.FOLLOW_STATUS){
            followAction(uid,UNFOLLOW_ACTION);
        }
        if (followStatus == CareButton.UNFOLLOW_STATUS){
            followAction(uid,FOLLOW_ACTION);
        }
    }


    public void setOnFollowListener(OnFollowListener onFollowListener){
        this.onFollowListener = onFollowListener;
    }

    public interface OnFollowListener{
        void followSuccess();
        void unfollowSuccess();
    }

    private void followAction(int uid, final int action){
        RequestManager.getInstance().userFollow(uid, action, new UserFolloweCallback() {
            @Override
            public void onSuccess(UserFollowBean bean) {
                String tip = "";
                if (action == FOLLOW_ACTION){
                    tip = "关注成功";
                    setText("已关注");
                    setTextColor(ContextCompat.getColor(context,R.color.colorPrimary));
                    setCompoundDrawables(null,null,null,null);
                    setSelected(true);
                    followStatus = FOLLOW_STATUS;
                    if(onFollowListener != null){
                        onFollowListener.followSuccess();
                    }
                    if (changeState !=null){
                        changeState.changeState(true);
                    }
                }
                if (action == UNFOLLOW_ACTION){
                    tip = "取消关注成功";
                    setText("关注");
                    setSelected(false);
                    setTextColor(ContextCompat.getColor(context,R.color.white));
                    Drawable drawable = getResources().getDrawable(R.mipmap.com_add);
                    drawable.setBounds(0,0,drawable.getMinimumWidth(),drawable.getMinimumHeight());
                    setCompoundDrawablePadding(6);
                    setCompoundDrawables(drawable,null,null,null);
                    followStatus = UNFOLLOW_STATUS;
                    if(onFollowListener != null){
                        onFollowListener.unfollowSuccess();
                    }
                    if (changeState !=null){
                        changeState.changeState(false);
                    }
                }
                ToastUtils.showSuccessfulToast(context, tip);
            }

            @Override
            public void onFailed(int code, String msg) {
                ToastUtils.showCentetImgToast(context, msg);
            }
        });
    }
    public interface changeState{
        void changeState(boolean state);
    }
    private changeState changeState;


    public void setChangeState(changeState changeState) {
        this.changeState = changeState;
    }
}
