package com.xinniu.android.qiqueqiao.adapter;

//import android.support.annotation.Nullable;
//import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xinniu.android.qiqueqiao.R;
import com.xinniu.android.qiqueqiao.bean.AddGroupClassifyBean;
import com.xinniu.android.qiqueqiao.utils.ImageLoader;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yuchance on 2018/9/27.
 */

public class AddGroupClassifyAdapter extends BaseMultiItemQuickAdapter<AddGroupClassifyBean,BaseViewHolder> {

    private List<AddGroupClassifyBean> datas = new ArrayList<>();

    public AddGroupClassifyAdapter(List data) {
        super(data);
        addItemType(AddGroupClassifyBean.RECOMMEND, R.layout.item_addgroup_classify);
        addItemType(AddGroupClassifyBean.COMMMON, R.layout.item_addgroup_classify);
    }

    @Override
    protected void convert(final BaseViewHolder helper, final AddGroupClassifyBean item) {
        switch (helper.getItemViewType()) {
            case AddGroupClassifyBean.COMMMON:
                RelativeLayout itemRl = helper.getView(R.id.item_addgroup_Rl);
                TextView addGroupTv = helper.getView(R.id.item_addgroup_tv);
                ImageView addGroupImg = helper.getView(R.id.item_addgroup_img);
                helper.setText(R.id.item_addgroup_tv,item.getName());
                ImageLoader.loadAvter(item.getImg(), addGroupImg);
                if (item.isCheck()){
                    addGroupImg.setSelected(true);
                    addGroupTv.setSelected(true);
                }else {
                    addGroupImg.setSelected(false);
                    addGroupTv.setSelected(false);
                }
                itemRl.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (!item.isCheck()){
                            setGroupClassifyId.setGroupClassifyId(item.getId(),helper.getAdapterPosition());
                        }
                    }
                });
                break;
            case AddGroupClassifyBean.RECOMMEND:
                RelativeLayout itemR = helper.getView(R.id.item_addgroup_Rl);
                TextView addGroup = helper.getView(R.id.item_addgroup_tv);
                ImageView addGroupx = helper.getView(R.id.item_addgroup_img);
                helper.setText(R.id.item_addgroup_tv,"推荐");
                addGroupx.setImageResource(R.mipmap.recommendimg);
                if (item.isCheck()){
                    addGroup.setSelected(true);
                    addGroupx.setSelected(true);
                }else {
                    addGroup.setSelected(false);
                    addGroupx.setSelected(false);
                }
                itemR.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (!item.isCheck()){
                            setGroupClassifyId.setGroupClassifyId(item.getId(),helper.getAdapterPosition());
                        }
                    }
                });
                break;
                default:
                    break;
        }



    }

       public interface setGroupClassifyId{
        void setGroupClassifyId(int groupClassifyId,int position);
    }

    private setGroupClassifyId setGroupClassifyId;

    public void setSetGroupClassifyId(AddGroupClassifyAdapter.setGroupClassifyId setGroupClassifyId) {
        this.setGroupClassifyId = setGroupClassifyId;
    }
}
