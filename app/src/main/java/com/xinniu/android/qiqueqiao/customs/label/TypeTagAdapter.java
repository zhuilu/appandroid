package com.xinniu.android.qiqueqiao.customs.label;

import android.content.Context;
import androidx.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xinniu.android.qiqueqiao.R;
import com.xinniu.android.qiqueqiao.bean.SeclectCateBean;
import com.xinniu.android.qiqueqiao.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yuchance on 2018/6/27.
 */

public class TypeTagAdapter extends BaseQuickAdapter<SeclectCateBean.CommonCategoryBean.ZlistBean,BaseViewHolder> {

    private Context mContext;
    private SeclectCateBean seclectCateBean = new SeclectCateBean();
    private List<Integer> datas;
    List<SeclectCateBean.UserCategoryBean> other = new ArrayList<>();

    public TypeTagAdapter(Context mContext,int layoutResId, @Nullable List<SeclectCateBean.CommonCategoryBean.ZlistBean> data,SeclectCateBean seclectCateBean,List<SeclectCateBean.UserCategoryBean> other) {
        super(layoutResId, data);
        this.mContext = mContext;
        this.seclectCateBean = seclectCateBean;
        this.other = other;
    }


    @Override
    protected void convert(BaseViewHolder helper, final SeclectCateBean.CommonCategoryBean.ZlistBean item) {
        TextView tv = helper.getView(R.id.item_label_type);
        helper.setText(R.id.item_label_type,item.getName());
        if (item.isCheck()){
            tv.setSelected(true);
        }else {
            tv.setSelected(false);
        }
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datas = new ArrayList<>();
                for (int i = 0; i < seclectCateBean.getCommonCategory().size(); i++) {
                    for (int j = 0; j <seclectCateBean.getCommonCategory().get(i).getZlist().size() ; j++) {
                        if (seclectCateBean.getCommonCategory().get(i).getZlist().get(j).isCheck()){
                            datas.add(seclectCateBean.getCommonCategory().get(i).getZlist().get(j).getId());
                        }
                    }
                }
                for (int i = 0; i <other.size() ; i++) {
                    if (other.get(i).isCheck()){
                     datas.add(other.get(i).getId());
                    }
                }
                if (item.isCheck()){
                    item.setCheck(false);
                }else {
                    if (datas.size()<6) {
                        item.setCheck(true);
                    }else {
                        item.setCheck(false);
                        ToastUtils.showCentetToast(mContext,"最多只能选择6个标签");
                    }
                }
                notifyDataSetChanged();
            }
        });

    }

}
