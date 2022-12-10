package com.xinniu.android.qiqueqiao.adapter;

import android.content.Context;
import androidx.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xinniu.android.qiqueqiao.R;
import com.xinniu.android.qiqueqiao.activity.GoseeBillActivity;
import com.xinniu.android.qiqueqiao.bean.MyActListBean;
import com.xinniu.android.qiqueqiao.utils.ImageLoader;
import com.xinniu.android.qiqueqiao.utils.TimeUtils;

import java.util.List;

/**
 * Created by yuchance on 2019/1/8.
 */

public class MyActListAdapter extends BaseQuickAdapter<MyActListBean.ListBean,BaseViewHolder>{

    private Context context;

    public MyActListAdapter(Context context,int layoutResId, @Nullable List<MyActListBean.ListBean> data) {
        super(layoutResId, data);
        this.context = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, final MyActListBean.ListBean item) {
        ImageLoader.loadImageGQ(item.getPoster(), (ImageView) helper.getView(R.id.myact_img));
        helper.setText(R.id.myact_titletv,item.getTitle());
        helper.setText(R.id.myact_citytv,item.getProvince_name()+item.getCity_name());
        if (item.getStatus()==0){
            helper.setText(R.id.myact_applystate,"已取消报名");
            helper.setText(R.id.myact_goseetv,"");
        }else if (item.getStatus() == 1){
            helper.setText(R.id.myact_applystate,"报名成功");
            helper.setText(R.id.myact_goseetv,"查看电子票");
        }else {
            helper.setText(R.id.myact_applystate,"签到成功");
            helper.setText(R.id.myact_goseetv,"查看电子票");
        }
        helper.setText(R.id.myact_timetv, TimeUtils.time2monthday(item.getStart_time()*1000)+" " +TimeUtils.time2Weekt(item.getStart_time()*1000));
        helper.getView(R.id.bitem_act).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setOnClick.setOnClick(item.getId(),item.getStatus());
            }
        });

    }

    public interface setOnClick{
        void setOnClick(int actId,int status);
    }

    private setOnClick setOnClick;

    public void setSetOnClick(MyActListAdapter.setOnClick setOnClick) {
        this.setOnClick = setOnClick;
    }
}
