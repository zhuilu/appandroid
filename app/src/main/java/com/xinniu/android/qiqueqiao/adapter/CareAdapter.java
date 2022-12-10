package com.xinniu.android.qiqueqiao.adapter;

import android.content.Context;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xinniu.android.qiqueqiao.R;
import com.xinniu.android.qiqueqiao.activity.PersonCentetActivity;
import com.xinniu.android.qiqueqiao.bean.CareBean;
import com.xinniu.android.qiqueqiao.utils.ImageLoader;

import java.util.List;

/**
 * Created by lzq on 2017/12/13.
 */

public class CareAdapter extends BaseQuickAdapter<CareBean.ListBean,BaseViewHolder>{

    private List<CareBean> list;
    private Context context;

    public CareAdapter(Context context,int layoutResId, @Nullable List<CareBean.ListBean> data) {
        super(layoutResId, data);
        this.context = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, final CareBean.ListBean item) {
        if (item!=null) {
            if (!TextUtils.isEmpty(item.getRealname())) {
                helper.setText(R.id.item_name, item.getRealname());
            }
            if (!TextUtils.isEmpty(item.getCompany() + "") && !TextUtils.isEmpty(item.getPosition() + "")) {
                helper.setText(R.id.item_company, item.getCompany() + " | " + item.getPosition());
            }
            if (!TextUtils.isEmpty(item.getHead_pic())) {
                ImageLoader.loadImage(item.getHead_pic(), (ImageView) helper.getView(R.id.item_care_iv));
            }
            LinearLayout itemLl = helper.getView(R.id.item_Ll);
            itemLl.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    PersonCentetActivity.start(context, String.valueOf(item.getUser_id()));
                }
            });
        }
    }

}
