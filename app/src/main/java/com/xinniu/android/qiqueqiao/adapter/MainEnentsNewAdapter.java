package com.xinniu.android.qiqueqiao.adapter;

import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xinniu.android.qiqueqiao.R;
import com.xinniu.android.qiqueqiao.activity.ApproveCardActivity;
import com.xinniu.android.qiqueqiao.activity.LoginNewActivity;
import com.xinniu.android.qiqueqiao.bean.ActivityColumnListBean;
import com.xinniu.android.qiqueqiao.user.UserInfoHelper;
import com.xinniu.android.qiqueqiao.utils.ImageLoader;
import com.xinniu.android.qiqueqiao.utils.RoundImageView;
import com.xinniu.android.qiqueqiao.utils.TimeUtils;

import java.util.List;

import androidx.annotation.Nullable;

/**
 * Created by yuchance on 2018/3/30.
 */

public class MainEnentsNewAdapter extends BaseQuickAdapter<ActivityColumnListBean.ListBean.ActivityListBean, BaseViewHolder> {


    private AppCompatActivity context;

    public MainEnentsNewAdapter(AppCompatActivity context, int layoutResId, @Nullable List<ActivityColumnListBean.ListBean.ActivityListBean> data) {
        super(layoutResId, data);
        this.context = context;

    }


    @Override
    protected void convert(BaseViewHolder helper, final ActivityColumnListBean.ListBean.ActivityListBean item) {
        if(helper.getAdapterPosition()==0){
            helper.getView(R.id.v).setVisibility(View.VISIBLE);
        }else{
            helper.getView(R.id.v).setVisibility(View.GONE);
        }
        RoundImageView roundImageView=  helper.getView(R.id.mindex_actone_img);
        roundImageView.setmBorderRadius(4);
        ImageLoader.loadImageGQ(item.getPoster(), (RoundImageView) helper.getView(R.id.mindex_actone_img));
        helper.setText(R.id.mindex_act_onetitletv, item.getTitle());
        if (item.getProvince_name().equals(item.getCity_name())) {
            helper.setText(R.id.mindex_act_oneplacetv, item.getCity_name());
        } else {
            helper.setText(R.id.mindex_act_oneplacetv, item.getProvince_name() + item.getCity_name());
        }
        helper.setText(R.id.mindex_act_onetimetv, TimeUtils.time2monthday(item.getCreate_time() * 1000) + TimeUtils.time2Weekt(item.getCreate_time() * 1000));

        helper.getView(R.id.llayout_default).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isLogin()) {
                    return;
                }
                ApproveCardActivity.start(context, "url", item.getUrl(), "");
            }
        });

    }

    private boolean isLogin() {
        if (!UserInfoHelper.getIntance().isLogin()) {
            // TODO: 2017/12/20
//            ToastUtils.showCentetImgToast(getContext(), "还未登录");
            LoginNewActivity.start(mContext);
        }
        return UserInfoHelper.getIntance().isLogin();
    }

}
