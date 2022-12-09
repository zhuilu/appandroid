package com.xinniu.android.qiqueqiao.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xinniu.android.qiqueqiao.R;
import com.xinniu.android.qiqueqiao.adapter.base.BDBaseAdapter;
import com.xinniu.android.qiqueqiao.adapter.base.BDViewHolder;
import com.xinniu.android.qiqueqiao.bean.SelectCategory;
import com.xinniu.android.qiqueqiao.common.Constants;

import java.util.List;

/**
 * Created by BDXK on 2017/11/14.
 */

public class IndexRightSelectGridAdapter extends BDBaseAdapter {

    public boolean isShowMore() {
        return isShowMore;
    }

    public void setShowMore(boolean showMore) {
        isShowMore = showMore;
    }

    private boolean isShowMore;
    private Context context;

    public IndexRightSelectGridAdapter(Context context, List mDatas, int itemLayoutId) {
        super(context, mDatas, itemLayoutId);
        this.context = context;
//        if (mDatas.size() > 5){
//            SelectCategory item = new SelectCategory();
//            item.setId(Constants.ID_TAG_MORE);
//            item.setName("更多");
//            mDatas.add(5,item);
//        }
    }

    @Override
    public void convert(BDViewHolder helper, Object item, final int position) {
        final SelectCategory child = (SelectCategory) item;
        TextView textView = helper.getView(R.id.text);
//        LinearLayout linearLayout = helper.getView(R.id.ll_layout);
        textView.setSelected(child.isCheck());
//        linearLayout.setSelected(child.isCheck());
        textView.setText(child.getName());
        textView.setCompoundDrawables(null, null, null, null);
        textView.setCompoundDrawablePadding(0);
        if (child.getId() == Constants.ID_TAG_MORE){

            Drawable rightDrawable = context.getResources().getDrawable(R.mipmap.icon_tag_more);
            rightDrawable.setBounds(0, 0, rightDrawable.getMinimumWidth(), rightDrawable.getMinimumHeight());
//            textView.setCompoundDrawablePadding(10);
            textView.setCompoundDrawables(null, null, rightDrawable, null);
//            textView.setCompoundDrawables(context.getResources().getDrawable(R.mipmap.down_2),null,null,null);
        }
        if (child.getId() == Constants.ID_TAG_ADD){
            Drawable rightDrawable = context.getResources().getDrawable(R.mipmap.icon_add_tag);

            rightDrawable.setBounds(0, 0, rightDrawable.getMinimumWidth(), rightDrawable.getMinimumHeight());
//            textView.setCompoundDrawablePadding(10);
            textView.setCompoundDrawables(null, null, rightDrawable, null);
//            textView.setText(child.getName()+" + ");
        }
//        if (!isShowMore){
//            if (position > 5){
//                textView.setVisibility(View.GONE);
//            }else{
//                textView.setVisibility(View.VISIBLE);
//            }
//        }else{
//            textView.setVisibility(View.VISIBLE);
//        }
    }


    private ChildItemClick childItemClick;

    public void setChildItemClick(ChildItemClick childItemClick) {
        this.childItemClick = childItemClick;
    }

    public interface ChildItemClick {
        void check(int position);
    }
}
