package com.xinniu.android.qiqueqiao.adapter;

import android.content.Context;
import androidx.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xinniu.android.qiqueqiao.R;
import com.xinniu.android.qiqueqiao.activity.PersonCentetActivity;
import com.xinniu.android.qiqueqiao.bean.SeenBean;
import com.xinniu.android.qiqueqiao.utils.ImageLoader;
import com.xinniu.android.qiqueqiao.widget.CareButton;

import java.util.List;

/**
 * Created by lzq on 2017/12/13.
 */

public class SeenMeAdapter extends BaseQuickAdapter<SeenBean.ListBean,BaseViewHolder>{

    private Context context;

    public SeenMeAdapter(Context context,int layoutResId, @Nullable List<SeenBean.ListBean> data) {
        super(layoutResId, data);
        this.context = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, final SeenBean.ListBean item) {
        final CareButton itemCare = helper.getView(R.id.item_care);
        RelativeLayout itemView = helper.getView(R.id.itemView);
        helper.setText(R.id.item_name,item.realname);
        helper.setText(R.id.item_company,item.company+" | "+item.position);
        ImageLoader.loadImage(item.head_pic, (ImageView) helper.getView(R.id.item_seen_iv));
        itemCare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemCare.click(item.user_id);
            }
        });
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PersonCentetActivity.start(context,String.valueOf(item.user_id));
            }
        });



    }
}
