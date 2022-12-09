package com.xinniu.android.qiqueqiao.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.xinniu.android.qiqueqiao.R;
import com.xinniu.android.qiqueqiao.adapter.base.BDBaseAdapter;
import com.xinniu.android.qiqueqiao.adapter.base.BDViewHolder;
import com.xinniu.android.qiqueqiao.bean.SelectCategory;

import java.util.List;

/**
 * Created by lzq on 2018/1/10.
 */

public class AddTagGridAdapter extends BDBaseAdapter {

    public boolean isShowDeleteButton() {
        return isShowDeleteButton;
    }

    public void setShowDeleteButton(boolean showDeleteButton) {
        isShowDeleteButton = showDeleteButton;
    }

    private boolean isShowDeleteButton;

    public AddTagGridAdapter(Context context, List mDatas, int itemLayoutId) {
        super(context, mDatas, itemLayoutId);
    }

    @Override
    public void convert(final BDViewHolder helper, Object item, final int position) {
        final SelectCategory child = (SelectCategory) item;
        TextView textView = helper.getView(R.id.text);
        ImageView deletebutton = helper.getView(R.id.delete_button);
        textView.setSelected(child.isCheck());
        textView.setText(child.getName());
        if (isShowDeleteButton){
            deletebutton.setVisibility(View.VISIBLE);
        }else{
            deletebutton.setVisibility(View.GONE);
        }
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isShowDeleteButton){
                    if (childItemClick != null){
                        childItemClick.onDelete(helper.getConvertView(),child);
                    }
                }else {
                    if (childItemClick != null){
                        childItemClick.onSelect(child);
                    }
                }
            }
        });
        deletebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (childItemClick != null){
                    childItemClick.onDelete(helper.getConvertView(),child);
                }
            }
        });
    }


    private OnAddTagItemClick childItemClick;

    public void setOnAddTagItemClick(OnAddTagItemClick childItemClick) {
        this.childItemClick = childItemClick;
    }

    public interface OnAddTagItemClick {
        void onSelect(SelectCategory item);
        void onDelete(View v,SelectCategory item);
    }
}

