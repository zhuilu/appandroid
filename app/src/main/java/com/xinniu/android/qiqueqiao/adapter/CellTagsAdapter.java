package com.xinniu.android.qiqueqiao.adapter;

//import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xinniu.android.qiqueqiao.R;
import com.xinniu.android.qiqueqiao.bean.CellTagsBean;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by yuchance on 2018/6/26.
 */

public class CellTagsAdapter extends BaseQuickAdapter<CellTagsBean.ProvideCategoryBean.ListBean,BaseViewHolder> {

    private List<CellTagsBean.ProvideCategoryBean.ListBean> datas = new ArrayList<>();
    private List<CellTagsBean.NeedCategoryBean.ListBeanX> datax = new ArrayList<>();

    public CellTagsAdapter(int layoutResId, @Nullable List<CellTagsBean.ProvideCategoryBean.ListBean> data,List<CellTagsBean.NeedCategoryBean.ListBeanX> datax) {
        super(layoutResId, data);
        this.datas = data;
        this.datax = datax;
    }

    @Override
    protected void convert(final BaseViewHolder helper, final CellTagsBean.ProvideCategoryBean.ListBean item) {
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
                    StringBuffer sb = new StringBuffer();
                    for (int i = 0; i < datas.size(); i++) {
                        datas.get(i).setCheck(false);
                    }
//                    if (item.isCheck()){
//                        item.setCheck(false);
//                        setTags.setTags();
 //                   }else {
                        item.setCheck(true);
 //                   }

                    for (int i = 0; i < datas.size(); i++) {
                        if (datas.get(i).isCheck()){
                            sb.append(datas.get(i).getId()+"");
                            sb.append("_");
                        }
                    }
//                    for (int i = 0; i < datax.size(); i++) {
//                        if (datax.get(i).isCheck()){
//                            sb.append(datax.get(i).getId()+"_");
//                        }
//                    }
                    if (sb.length()>0) {
                        sb.delete(sb.length() - 1, sb.length());
                    }else {
                        sb.append("");
                    }
                    setTags.setTags(sb.toString());

                    notifyDataSetChanged();
                }
            });
        }
    }
    public interface setTags{
        void setTags(String id);
    }
    private setTags setTags;

    public void setSetTags(CellTagsAdapter.setTags setTags) {
        this.setTags = setTags;
    }
}
