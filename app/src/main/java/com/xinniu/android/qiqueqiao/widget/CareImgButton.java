package com.xinniu.android.qiqueqiao.widget;

import android.content.Context;
//import android.support.annotation.Nullable;
import android.util.AttributeSet;

import com.xinniu.android.qiqueqiao.R;
import com.xinniu.android.qiqueqiao.bean.UserFollowBean;
import com.xinniu.android.qiqueqiao.request.RequestManager;
import com.xinniu.android.qiqueqiao.request.callback.UserFolloweCallback;
import com.xinniu.android.qiqueqiao.utils.Logger;
import com.xinniu.android.qiqueqiao.utils.ToastUtils;

/**
 * Created by lzq on 2018/3/5.
 */

public class CareImgButton extends android.support.v7.widget.AppCompatImageView {
    private Context context;
    public static final int FOLLOW_ACTION = 1;
    public static final int UNFOLLOW_ACTION = 2;

    public static final int FOLLOW_STATUS = 1;
    public static final int UNFOLLOW_STATUS = 0;
    private int followStatus = 1;
    private OnFollowListener onFollowListener;

    public CareImgButton(Context context) {
        super(context);
        this.context = context;
    }

    public CareImgButton(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
    }

    public CareImgButton(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
    }

//    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public void initFollowStatus(int status) {
        followStatus = status;
        if (followStatus == FOLLOW_STATUS) {
//            setBackground(context.getResources().getDrawable(R.mipmap.care_bule));
            setImageResource(R.mipmap.care_bule);
        }
        if (followStatus == UNFOLLOW_STATUS) {
//            setBackground(context.getResources().getDrawable(R.mipmap.care_gray));
            setImageResource(R.mipmap.care_gray);
        }
    }

    public void click(int uid) {
        Logger.i("lzq","CareImgButton click "+followStatus);
        if (followStatus == CareButton.FOLLOW_STATUS) {
            followAction(uid, UNFOLLOW_ACTION);
        }
        if (followStatus == CareButton.UNFOLLOW_STATUS) {
            followAction(uid, FOLLOW_ACTION);
        }
    }


    public void setOnFollowListener(OnFollowListener onFollowListener) {
        this.onFollowListener = onFollowListener;
    }

    public interface OnFollowListener {
        void followSuccess();

        void unfollowSuccess();
    }

    private void followAction(int uid, final int action) {
        RequestManager.getInstance().userFollow(uid, action, new UserFolloweCallback() {
//            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onSuccess(UserFollowBean bean) {
                String tip = "";
                if (action == FOLLOW_ACTION) {
                    tip = "关注成功";
//                    setBackground(context.getResources().getDrawable(R.mipmap.care_bule));
                    setImageResource(R.mipmap.care_bule);
                    followStatus = FOLLOW_STATUS;
                    if (onFollowListener != null) {
                        onFollowListener.followSuccess();
                    }
                }
                if (action == UNFOLLOW_ACTION) {
                    tip = "取消关注";
//                    setBackground(context.getResources().getDrawable(R.mipmap.care_gray));
                    setImageResource(R.mipmap.care_gray);
                    followStatus = UNFOLLOW_STATUS;
                    if (onFollowListener != null) {
                        onFollowListener.unfollowSuccess();
                    }
                }
                ToastUtils.showCentetImgToast(context, tip);
            }

            @Override
            public void onFailed(int code, String msg) {
                ToastUtils.showCentetImgToast(context, msg);
            }
        });
    }
}
