package com.xinniu.android.qiqueqiao.adapter;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xinniu.android.qiqueqiao.R;
import com.xinniu.android.qiqueqiao.bean.GetReleaseTemplateBean;
import com.xinniu.android.qiqueqiao.bean.SeclectCateBean;
import com.xinniu.android.qiqueqiao.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yuchance on 2018/12/13.
 */

public class PublishOfferAdapter extends BaseMultiItemQuickAdapter<GetReleaseTemplateBean.ProvideCategoryBean.SystemCategoryBeanX,BaseViewHolder> {
    private Context context;
    private List<GetReleaseTemplateBean.ProvideCategoryBean.SystemCategoryBeanX> data = new ArrayList<>();


    public PublishOfferAdapter(Context context,List<GetReleaseTemplateBean.ProvideCategoryBean.SystemCategoryBeanX> data) {
        super(data);
        this.context = context;
        this.data = data;
        addItemType(GetReleaseTemplateBean.NeedCategoryBean.SystemCategoryBean.NEEDSYSTEMTYPE,R.layout.item_label_typetv);
        addItemType(GetReleaseTemplateBean.NeedCategoryBean.SystemCategoryBean.NEEDUSERTYPE,R.layout.item_resource_other);
    }

    @Override
    protected void convert(BaseViewHolder helper, final GetReleaseTemplateBean.ProvideCategoryBean.SystemCategoryBeanX item) {
        switch (helper.getItemViewType()){
            case GetReleaseTemplateBean.NeedCategoryBean.SystemCategoryBean.NEEDSYSTEMTYPE:
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
            case GetReleaseTemplateBean.NeedCategoryBean.SystemCategoryBean.NEEDUSERTYPE:
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
        void setAddOnClick(int isType);
    }
    private setAddOnClick setAddOnClick;

    public void setSetAddOnClick(setAddOnClick setAddOnClick) {
        this.setAddOnClick = setAddOnClick;
    }
}
