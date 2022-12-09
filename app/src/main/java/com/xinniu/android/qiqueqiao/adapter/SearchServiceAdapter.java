package com.xinniu.android.qiqueqiao.adapter;

import android.app.Activity;
//import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xinniu.android.qiqueqiao.R;
import com.xinniu.android.qiqueqiao.activity.ServiceDetailActivity;
import com.xinniu.android.qiqueqiao.bean.IndexServiceBean;
import com.xinniu.android.qiqueqiao.utils.ImageLoader;
import com.xinniu.android.qiqueqiao.utils.StringUtils;

import java.util.List;

/**
 * Created by yuchance on 2018/3/30.
 */

public class SearchServiceAdapter extends BaseQuickAdapter<IndexServiceBean.ListBean, BaseViewHolder> {


    private Activity context;
    private String keyWord;


    public String getKeyWord() {
        return keyWord;
    }

    public void setKeyWord(String keyWord) {
        this.keyWord = keyWord;
    }
    public SearchServiceAdapter(Activity context, int layoutResId, @Nullable List<IndexServiceBean.ListBean> data, int lineType, boolean isSearchPerch) {
        super(layoutResId, data);
        this.context = context;

    }

    public SearchServiceAdapter(Activity context, int layoutResId, @Nullable List<IndexServiceBean.ListBean> data, int lineType) {
        super(layoutResId, data);
        this.context = context;

    }


    @Override
    protected void convert(BaseViewHolder helper, final IndexServiceBean.ListBean item) {
        if (!TextUtils.isEmpty(keyWord)) {
            String[] sourceStrArray = item.getImages().split(",");
            ImageLoader.loadLocalImg(sourceStrArray[0], (ImageView) helper.getView(R.id.mcoop_detail_companyImg));
            helper.setText(R.id.tv_title, StringUtils.strToSpannableStr(item.getTitle(), keyWord));
            helper.setText(R.id.tv_company_name, StringUtils.strToSpannableStr(item.getBrand(), keyWord));
            String[] tags = item.getRemark().split(",");
            if (tags.length == 3) {
                helper.setVisible(R.id.tv_tag_1, true);
                helper.setVisible(R.id.tv_tag_2, true);
                helper.setVisible(R.id.tv_tag_3, true);
                helper.setText(R.id.tv_tag_1, StringUtils.strToSpannableStr(tags[0], keyWord));
                helper.setText(R.id.tv_tag_2, StringUtils.strToSpannableStr(tags[1], keyWord));
                helper.setText(R.id.tv_tag_3, StringUtils.strToSpannableStr(tags[2], keyWord));
            } else if (tags.length == 2) {
                helper.setVisible(R.id.tv_tag_1, true);
                helper.setVisible(R.id.tv_tag_2, true);
                helper.setVisible(R.id.tv_tag_3, false);
                helper.setText(R.id.tv_tag_1, StringUtils.strToSpannableStr(tags[0], keyWord));
                helper.setText(R.id.tv_tag_2, StringUtils.strToSpannableStr(tags[1], keyWord));
            } else if (tags.length == 1) {
                helper.setVisible(R.id.tv_tag_1, true);
                helper.setVisible(R.id.tv_tag_2, false);
                helper.setVisible(R.id.tv_tag_3, false);
                helper.setText(R.id.tv_tag_1, StringUtils.strToSpannableStr(tags[0], keyWord));

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


}
