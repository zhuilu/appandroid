package com.xinniu.android.qiqueqiao.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xinniu.android.qiqueqiao.R;
import com.xinniu.android.qiqueqiao.activity.ServiceDetailActivity;
import com.xinniu.android.qiqueqiao.bean.IndexServiceBean;
import com.xinniu.android.qiqueqiao.utils.ImageLoader;

import java.util.List;

import androidx.annotation.Nullable;

/**
 * Created by yuchance on 2018/3/30.
 */

public class IndexServiceAdapter extends BaseQuickAdapter<IndexServiceBean.ListBean, BaseViewHolder> {


    private Context context;

    public IndexServiceAdapter(Context context, int layoutResId, @Nullable List<IndexServiceBean.ListBean> data, int lineType, boolean isSearchPerch) {
        super(layoutResId, data);
        this.context = context;

    }

    public IndexServiceAdapter(Context context, int layoutResId, @Nullable List<IndexServiceBean.ListBean> data, int lineType) {
        super(layoutResId, data);
        this.context = context;

    }


    @Override
    protected void convert(BaseViewHolder helper, final IndexServiceBean.ListBean item) {
        String[] sourceStrArray = item.getImages().split(",");
        ImageLoader.loadLocalImg(sourceStrArray[0], (ImageView) helper.getView(R.id.mcoop_detail_companyImg));
        helper.setText(R.id.tv_title, item.getTitle());
        helper.setText(R.id.tv_company_name, item.getBrand());
        String[] tags = item.getRemark().split(",");
        if (tags.length == 3) {
            helper.setVisible(R.id.tv_tag_1, true);
            helper.setVisible(R.id.tv_tag_2, true);
            helper.setVisible(R.id.tv_tag_3, true);
            helper.setText(R.id.tv_tag_1, tags[0]);
            helper.setText(R.id.tv_tag_2, tags[1]);
            helper.setText(R.id.tv_tag_3, tags[2]);
        } else if (tags.length == 2) {
            helper.setVisible(R.id.tv_tag_1, true);
            helper.setVisible(R.id.tv_tag_2, true);
            helper.setVisible(R.id.tv_tag_3, false);
            helper.setText(R.id.tv_tag_1, tags[0]);
            helper.setText(R.id.tv_tag_2, tags[1]);
        } else if (tags.length == 1) {
            helper.setVisible(R.id.tv_tag_1, true);
            helper.setVisible(R.id.tv_tag_2, false);
            helper.setVisible(R.id.tv_tag_3, false);
            helper.setText(R.id.tv_tag_1, tags[0]);
        }

        if (item.getCorporate_vip() == 1) {
            helper.getView(R.id.img_rz).setVisibility(View.VISIBLE);
        } else {
            helper.getView(R.id.img_rz).setVisibility(View.GONE);
        }
        helper.getView(R.id.item_index).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ServiceDetailActivity.start(context, item.getId());
            }
        });

    }


}
