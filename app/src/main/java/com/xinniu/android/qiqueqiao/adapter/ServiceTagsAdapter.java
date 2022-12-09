package com.xinniu.android.qiqueqiao.adapter;

//import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xinniu.android.qiqueqiao.R;
import com.xinniu.android.qiqueqiao.bean.CellTagsBean;
import com.xinniu.android.qiqueqiao.bean.ServiceCategoryAndTag;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by yuchance on 2018/6/26.
 */

public class ServiceTagsAdapter extends BaseQuickAdapter<ServiceCategoryAndTag.ZlistBean,BaseViewHolder> {

    private List<ServiceCategoryAndTag.ZlistBean> datas = new ArrayList<>();

    public ServiceTagsAdapter(int layoutResId, @Nullable List<ServiceCategoryAndTag.ZlistBean> data) {
        super(layoutResId, data);
        this.datas = data;

    }

    @Override
    protected void convert(final BaseViewHolder helper, final ServiceCategoryAndTag.ZlistBean item) {
        TextView cellTv = helper.getView(R.id.tvItemCell);
        if (item!=null) {
            if (!TextUtils.isEmpty(item.getName())) {
                helper.setText(R.id.tvItemCell, item.getName());
            }
            if (item.isCheck()){
                cellTv.setSelected(true);
            }else {
                cellTv.setSelected(false);
            }
            cellTv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(item.isCheck()){
                        //取消
                        for (int i = 0; i < datas.size(); i++) {
                            datas.get(i).setCheck(false);
                        }

                        setTags.setTags(0);
                    }else {
                        for (int i = 0; i < datas.size(); i++) {
                            datas.get(i).setCheck(false);
                        }
                        datas.get(helper.getLayoutPosition()).setCheck(true);

                        setTags.setTags(item.getId());
                    }
                    notifyDataSetChanged();
                }
            });
        }
    }
    public interface setTags{
        void setTags(int id);
    }
    private setTags setTags;

    public void setSetTags(ServiceTagsAdapter.setTags setTags) {
        this.setTags = setTags;
    }
}
