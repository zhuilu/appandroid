package com.xinniu.android.qiqueqiao.adapter;

import android.content.Context;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import com.xinniu.android.qiqueqiao.R;
import com.xinniu.android.qiqueqiao.adapter.base.BDBaseAdapter;
import com.xinniu.android.qiqueqiao.adapter.base.BDViewHolder;
import com.xinniu.android.qiqueqiao.base.BaseApp;
import com.xinniu.android.qiqueqiao.bean.SelectCategory;
import com.xinniu.android.qiqueqiao.utils.ResouceHelper;

import java.util.List;

/**
 * Created by BDXK on 2017/11/14.
 */

public class IndexRightSelectAdapter extends BDBaseAdapter {


    public IndexRightSelectAdapter(Context context, List mDatas, int itemLayoutId) {
        super(context, mDatas, itemLayoutId);
    }

    @Override
    public void convert(BDViewHolder helper, Object item, final int position) {
        final SelectCategory child = (SelectCategory) item;
        ((TextView) helper.getView(R.id.text)).setText(child.getName());
        GridView gridView = helper.getView(R.id.gridView);
        final List<SelectCategory> selectCategories = child.getZ_index();
        final IndexRightSelectGridAdapter adapter
                = new IndexRightSelectGridAdapter(gridView.getContext(),
                selectCategories, R.layout.right_select_child_item);
        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (selectCategories.get(i).isCheck()){
                    for (SelectCategory category : selectCategories) {
                        category.setCheck(false);
                    }
                    //当点击item时，如果tempLeftSelectPd != leftSelectPd，则更新getRightListById(BaseApp.leftSelectPd)
                    //从而实现资源分类互斥的效果
                    if (ResouceHelper.tempLeftSelectPd != ResouceHelper.leftSelectPd){
                        List<SelectCategory> categoryList = ResouceHelper.getInstance().getRightListById(ResouceHelper.leftSelectPd);
                        for (SelectCategory category : categoryList) {
                            for (SelectCategory item1 : category.getZ_index()) {
                                item1.setCheck(false);
                            }
                        }
                        ResouceHelper.getInstance().setRightCache(ResouceHelper.leftSelectPd,categoryList);
                        ResouceHelper.leftSelectPd = ResouceHelper.tempLeftSelectPd;
                    }
                }else{
                    for (SelectCategory category : selectCategories) {
                        category.setCheck(false);
                    }
                    //当点击item时，如果tempLeftSelectPd != leftSelectPd，则更新getRightListById(BaseApp.leftSelectPd)
                    //从而实现资源分类互斥的效果
                    if (ResouceHelper.tempLeftSelectPd != ResouceHelper.leftSelectPd){
                        List<SelectCategory> categoryList = ResouceHelper.getInstance().getRightListById(ResouceHelper.leftSelectPd);
                        for (SelectCategory category : categoryList) {
                            for (SelectCategory item1 : category.getZ_index()) {
                                item1.setCheck(false);
                            }
                        }
                        ResouceHelper.getInstance().setRightCache(ResouceHelper.leftSelectPd,categoryList);
                        ResouceHelper.leftSelectPd = ResouceHelper.tempLeftSelectPd;
                    }
                    selectCategories.get(i).setCheck(true);
                }

                adapter.notifyDataSetChanged();
            }
        });
    }


    private ChildItemClick childItemClick;

    public void setChildItemClick(ChildItemClick childItemClick) {
        this.childItemClick = childItemClick;
    }

    public interface ChildItemClick {
        void check(int position);
    }
}
