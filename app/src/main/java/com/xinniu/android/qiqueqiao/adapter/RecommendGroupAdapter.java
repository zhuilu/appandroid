package com.xinniu.android.qiqueqiao.adapter;

//import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xinniu.android.qiqueqiao.R;
import com.xinniu.android.qiqueqiao.bean.SimilarGroupBean;
import com.xinniu.android.qiqueqiao.utils.ImageLoader;

import java.util.List;

/**
 * Created by yuchance on 2018/10/10.
 */

public class RecommendGroupAdapter extends BaseQuickAdapter<SimilarGroupBean,BaseViewHolder> {

    public RecommendGroupAdapter(int layoutResId, @Nullable List<SimilarGroupBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, final SimilarGroupBean item) {
        helper.setText(R.id.mrecommendnameTv,item.getName());
        helper.setText(R.id.mrecommendtypetv,item.getNum()+"人");
        helper.setText(R.id.mrecommendnoticetv,item.getIntroduction());
        ImageLoader.loadAvter(item.getHead_pic(), (ImageView) helper.getView(R.id.mrecommendImg));
        helper.getView(R.id.brecommend_group).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToGroup.goToGroup(item.getId());
            }
        });

    }

    public interface goToGroup{
        void goToGroup(long groupId);
    }
    private goToGroup goToGroup;

    public void setGoToGroup(RecommendGroupAdapter.goToGroup goToGroup) {
        this.goToGroup = goToGroup;
    }
}
