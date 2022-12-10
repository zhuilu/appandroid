package com.xinniu.android.qiqueqiao.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.umeng.analytics.MobclickAgent;
import com.xinniu.android.qiqueqiao.R;
import com.xinniu.android.qiqueqiao.activity.ApproveCardActivity;
import com.xinniu.android.qiqueqiao.activity.CompanyIndexActivity;
import com.xinniu.android.qiqueqiao.activity.GuaranteeAreaActivity;
import com.xinniu.android.qiqueqiao.activity.HotResourceActivity;
import com.xinniu.android.qiqueqiao.activity.IndexServiceActivity;
import com.xinniu.android.qiqueqiao.activity.PotentualPeoplesActivity;
import com.xinniu.android.qiqueqiao.activity.RewardListActivity;
import com.xinniu.android.qiqueqiao.activity.SelectionResourceActivity;
import com.xinniu.android.qiqueqiao.bean.GetConfigBean;
import com.xinniu.android.qiqueqiao.request.RetrofitHelper;
import com.xinniu.android.qiqueqiao.utils.ImageLoader;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yuchance on 2018/3/31.
 */

public class IndexTabAdapter extends BaseQuickAdapter<GetConfigBean.EntranceListBean, BaseViewHolder> {

    Context context;
    ArrayList<GetConfigBean.EntranceListBean> data = new ArrayList<>();


    public IndexTabAdapter(int layoutResId, @Nullable List<GetConfigBean.EntranceListBean> data, Context context) {
        super(layoutResId, data);
        this.context = context;
        this.data = (ArrayList<GetConfigBean.EntranceListBean>) data;

    }

    @Override
    protected void convert(final BaseViewHolder helper, final GetConfigBean.EntranceListBean item) {
        ImageLoader.loadImageGQ(item.getImg(), (ImageView) helper.getView(R.id.bleft_img));
        helper.setText(R.id.mleft_titletv, item.getName());
        helper.setText(R.id.mleft_contenttv, item.getContent());
        int postion = helper.getAdapterPosition();
        TextView title = helper.getView(R.id.mleft_titletv);
        TextView content = helper.getView(R.id.mleft_contenttv);
        if (postion == 1) {
            title.setTextColor(ContextCompat.getColor(context, R.color._333));
            content.setTextColor(ContextCompat.getColor(context, R.color._cc333));
        } else {
            title.setTextColor(ContextCompat.getColor(context, R.color.white));
            content.setTextColor(ContextCompat.getColor(context, R.color._white));
        }
        (helper.getView(R.id.bleft_rl)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (item.getUrl().equals("qiqueqiao://www.xinniu.com/featuredResources")) {
                    MobclickAgent.onEvent(mContext, "home_featuredResources");//统计点击次数
                    SelectionResourceActivity.start(mContext);
                } else if (item.getUrl().equals("qiqueqiao://www.xinniu.com/homeQualityService")) {
                    MobclickAgent.onEvent(mContext, "home_service");//统计点击次数
                    IndexServiceActivity.start(mContext);
                } else if (item.getUrl().equals("qiqueqiao://www.xinniu.com/homeCompany")) {
                    MobclickAgent.onEvent(mContext, "home_company");
                    Intent intent2 = new Intent(mContext, CompanyIndexActivity.class);
                    mContext.startActivity(intent2);
                } else if (item.getUrl().equals("qiqueqiao://www.xinniu.com/homeEvent")) {
                    MobclickAgent.onEvent(mContext, "home_eventMore");//统计点击次数
                    String actListUrl = RetrofitHelper.API_URL + "resource/pages/qqqAct/home.html";
                    ApproveCardActivity.start(mContext, "url", actListUrl, "");
                } else if (item.getUrl().equals("qiqueqiao://www.xinniu.com/dailyHotResources")) {
                    HotResourceActivity.start(mContext);
                } else if (item.getUrl().equals("qiqueqiao://www.xinniu.com/homeRewardArea")) {
                    //求助列表
                    RewardListActivity.start(mContext);
                } else if (item.getUrl().equals("qiqueqiao://www.xinniu.com/homeGuaranteeArea")) {
                    //担保专区
                    GuaranteeAreaActivity.start(mContext);
                } else if (item.getUrl().equals("qiqueqiao://www.xinniu.com/homePotentialPeoples")) {
                    //潜在客户
                    PotentualPeoplesActivity.start(mContext);
                }

            }
        });

    }
}
