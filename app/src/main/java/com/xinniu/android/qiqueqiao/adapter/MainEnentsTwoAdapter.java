package com.xinniu.android.qiqueqiao.adapter;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xinniu.android.qiqueqiao.R;
import com.xinniu.android.qiqueqiao.activity.ApproveCardActivity;
import com.xinniu.android.qiqueqiao.activity.LoginNewActivity;
import com.xinniu.android.qiqueqiao.bean.ActivityListBean;
import com.xinniu.android.qiqueqiao.user.UserInfoHelper;
import com.xinniu.android.qiqueqiao.utils.ImageLoader;
import com.xinniu.android.qiqueqiao.utils.RoundImageView;
import com.xinniu.android.qiqueqiao.utils.TimeUtils;

import java.util.List;
//import android.support.v4.content.ContextCompat;

/**
 * Created by yuchance on 2018/3/30.
 */

public class MainEnentsTwoAdapter extends BaseQuickAdapter<ActivityListBean.ListBean, BaseViewHolder> {


    private AppCompatActivity context;

    public MainEnentsTwoAdapter(AppCompatActivity context, int layoutResId, @Nullable List<ActivityListBean.ListBean> data) {
        super(layoutResId, data);
        this.context = context;

    }


    @Override
    protected void convert(BaseViewHolder helper, final ActivityListBean.ListBean item) {
        RoundImageView roundImageView = helper.getView(R.id.mindex_actone_img);
        roundImageView.setmBorderRadius(6);
        ImageLoader.loadImageGQ(item.getPoster(),roundImageView);
        helper.setText(R.id.mindex_act_onetitletv, item.getTitle());
        if (item.getCity_name() != null && item.getCity_name().length() > 0 && !item.getCity_name().equals("null")&&(item.getProvince_name() != null && item.getProvince_name().length() > 0 && !item.getProvince_name().equals("null"))) {
            helper.getView(R.id.mindex_act_oneplacetv).setVisibility(View.VISIBLE);
            if (item.getProvince_name().equals(item.getCity_name())) {
                helper.setText(R.id.mindex_act_oneplacetv, item.getCity_name());
            } else {
                helper.setText(R.id.mindex_act_oneplacetv, item.getProvince_name() + item.getCity_name());
            }
        }else{
            helper.getView(R.id.mindex_act_oneplacetv).setVisibility(View.GONE);
        }
        helper.setText(R.id.mindex_act_onetimetv, TimeUtils.time2monthday(item.getCreate_time() * 1000) + TimeUtils.time2Weekt(item.getCreate_time() * 1000));
        helper.setText(R.id.tv_num, item.getApplicants_num() + "人已报名");
        //获取当前的毫秒值
        TextView textView= helper.getView(R.id.img_status_bm);
        if (item.getEnd_time() - item.getCurent_time() > 0) {
            helper.getView(R.id.img_status).setVisibility(View.VISIBLE);
            helper.getView(R.id.img_status_bm).setBackgroundResource(R.drawable.bg_activity_start);
            helper.setText(R.id.img_status_bm,"立即报名");
            textView.setTextColor(ContextCompat.getColor(context, R.color.blue_bg_4B96F3));
        } else {
            helper.getView(R.id.img_status).setVisibility(View.GONE);
            helper.getView(R.id.img_status_bm).setBackgroundResource(R.drawable.bg_activity_end);
            helper.setText(R.id.img_status_bm,"已结束");
            textView.setTextColor(ContextCompat.getColor(context, R.color._999999));
        }
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
            LoginNewActivity.start(mContext);
        }
        return UserInfoHelper.getIntance().isLogin();
    }

}
