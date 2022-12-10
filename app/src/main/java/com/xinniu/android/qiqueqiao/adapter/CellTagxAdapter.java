package com.xinniu.android.qiqueqiao.adapter;

import androidx.annotation.Nullable;
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
 * Created by yuchance on 2018/12/20.
 */

public class CellTagxAdapter extends BaseQuickAdapter<CellTagsBean.NeedCategoryBean.ListBeanX,BaseViewHolder> {
    private List<CellTagsBean.ProvideCategoryBean.ListBean> datas = new ArrayList<>();
    private List<CellTagsBean.NeedCategoryBean.ListBeanX> datax = new ArrayList<>();

    public CellTagxAdapter(int layoutResId, @Nullable List<CellTagsBean.NeedCategoryBean.ListBeanX> data,List<CellTagsBean.ProvideCategoryBean.ListBean> datas) {
        super(layoutResId, data);
        this.datax = data;
        this.datas = datas;
    }


    @Override
    protected void convert(final BaseViewHolder helper, final CellTagsBean.NeedCategoryBean.ListBeanX item) {
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
                    for (int i = 0; i < datax.size(); i++) {
                       datax.get(i).setCheck(false);

                    }
//                    if (item.isCheck()){
//                        item.setCheck(false);
////                        setTags.setTags();
//                    }else {
                        item.setCheck(true);
 //                   }

//                    for (int i = 0; i < datas.size(); i++) {
//                        if (datas.get(i).isCheck()){
//                            sb.append(datas.get(i).getId()+"");
//                            sb.append("_");
//                        }
//                    }
                    for (int i = 0; i < datax.size(); i++) {
                        if (datax.get(i).isCheck()){
                            sb.append(datax.get(i).getId()+"_");
                        }
                    }
                    if (sb.length()>0) {
                        sb.delete(sb.length() - 1, sb.length());
                    }else {
                        sb.append("");
                    }
                    setTagx.setTags(sb.toString());

                    notifyDataSetChanged();
                }
            });
        }
    }
    public interface setTagx{
        void setTags(String id);
    }
    private setTagx setTagx;

    public void setSetTagx(setTagx setTagx) {
        this.setTagx = setTagx;
    }
}
