package com.xinniu.android.qiqueqiao.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.Nullable;
//import android.support.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xinniu.android.qiqueqiao.R;
import com.xinniu.android.qiqueqiao.activity.CoopDetailActivity;
import com.xinniu.android.qiqueqiao.bean.GetUserResourcesBean;
import com.xinniu.android.qiqueqiao.bean.ResouceInfoBean;
import com.xinniu.android.qiqueqiao.bean.SeenBean;
import com.xinniu.android.qiqueqiao.utils.ImageLoader;
import com.xinniu.android.qiqueqiao.utils.TimeUtils;

import java.util.List;

/**
 * Created by lzq on 2017/12/13.
 */

public class PersonInfoAdapter extends BaseQuickAdapter<GetUserResourcesBean.ListBean, BaseViewHolder> {

    private List<GetUserResourcesBean.ListBean> list;
    private Context context;
    private String company = "";

    public PersonInfoAdapter(Context context, int layoutResId, @Nullable List<GetUserResourcesBean.ListBean> data) {
        super(layoutResId, data);
        this.context = context;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    @Override
    protected void convert(BaseViewHolder helper, final GetUserResourcesBean.ListBean item) {
        if (!TextUtils.isEmpty(item.getTitle())) {
            helper.setText(R.id.item_my_resourcex_title, "【" + company + "】" + item.getTitle());
        } else {
            helper.setGone(R.id.item_my_resourcex_title, false);
        }

        helper.setText(R.id.item_index_recycler_mannertv, item.getP_name());

        helper.setText(R.id.resource_eyeTv, item.getView() + "");
        helper.setText(R.id.resource_tellTv, item.getComment_count() + "");
        helper.getView(R.id.item_person_page_Ll).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putInt("detailId", item.getId());
                Intent intent = new Intent(context, CoopDetailActivity.class);
                intent.putExtras(bundle);
                context.startActivity(intent, bundle);
            }
        });
    }
}
