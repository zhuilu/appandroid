package com.xinniu.android.qiqueqiao;

/**
 * Created by lzq on 2018/1/16.
 */

public interface OnReleaseStepListener {
    int RELEASE_STEP_1 = 1;
    int RELEASE_STEP_2 = 2;
    int RELEASE_STEP_3 = 3;
    void onLeftClick(int step);
    void onRightClick(int step);
}
