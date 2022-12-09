package com.xinniu.android.qiqueqiao.adapter;

import android.content.Context;
import android.view.View;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xinniu.android.qiqueqiao.R;
import com.xinniu.android.qiqueqiao.bean.GetReleaseTemplateBean;
import com.xinniu.android.qiqueqiao.bean.ServiceCategoryAndTag;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yuchance on 2018/12/13.
 */

public class PublishServiceAdapter extends BaseMultiItemQuickAdapter<ServiceCategoryAndTag.ZlistBean,BaseViewHolder> {


    private Context context;
    List<ServiceCategoryAndTag.ZlistBean> data = new ArrayList<>();


    public PublishServiceAdapter(Context context,List<ServiceCategoryAndTag.ZlistBean> data) {
        super(data);
        this.context = context;
        this.data = data;
        addItemType(ServiceCategoryAndTag.ZlistBean.SYSTEMTYPE,R.layout.item_label_typetv_service);
        addItemType(ServiceCategoryAndTag.ZlistBean.USERTYPE,R.layout.item_resource_other_service);

    }

    @Override
    protected void convert(BaseViewHolder helper, final ServiceCategoryAndTag.ZlistBean item) {
        switch (helper.getItemViewType()){
            case ServiceCategoryAndTag.ZlistBean.SYSTEMTYPE:
                helper.setText(R.id.item_label_type,item.getName());
                if (item.isCheck()){
                    helper.getView(R.id.item_label_type).setSelected(true);
                }else {
                    helper.getView(R.id.item_label_type).setSelected(false);
                }
                helper.getView(R.id.item_label_type).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (item.isCheck()){
                            item.setCheck(false);
                        }else {
                            List<Integer> check = new ArrayList<>();
                            for (int i = 0; i < data.size(); i++) {
                                if (data.get(i).isCheck()){
                                    check.add(data.get(i).getId());
                                }
                            }
                            if (check.size()>2){
                                setAddOnClick.setAddOnClick(2);
                                return;
                            }
                            item.setCheck(true);

                        }
                        notifyDataSetChanged();
                    }
                });
                break;
            case ServiceCategoryAndTag.ZlistBean.USERTYPE:
                helper.getView(R.id.addImg).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        List<Integer> check = new ArrayList<>();
                        for (int i = 0; i < data.size(); i++) {
                            if (data.get(i).isCheck()){
                                check.add(data.get(i).getId());
                            }
                        }
                        if (check.size()>2){
                            setAddOnClick.setAddOnClick(2);
                            return;
                        }
                        setAddOnClick.setAddOnClick(1);
                    }
                });

                break;
            default:
                break;

        }

    }

    public interface setAddOnClick{
        void setAddOnClick(int type);
    }
    private setAddOnClick setAddOnClick;

    public void setSetAddOnClick(setAddOnClick setAddOnClick) {
        this.setAddOnClick = setAddOnClick;
    }
}
