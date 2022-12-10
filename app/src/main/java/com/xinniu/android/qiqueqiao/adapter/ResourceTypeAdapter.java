package com.xinniu.android.qiqueqiao.adapter;

import android.content.Context;
import android.nfc.Tag;
import android.os.Build;
import androidx.annotation.Nullable;
//import android.support.annotation.RequiresApi;
//import android.support.v4.content.ContextCompat;
//import android.support.v7.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import android.util.ArraySet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xinniu.android.qiqueqiao.R;
import com.xinniu.android.qiqueqiao.bean.SeclectCateBean;
import com.xinniu.android.qiqueqiao.customs.NestedRecyclerView;
import com.xinniu.android.qiqueqiao.customs.label.FlowLayoutManager;
import com.xinniu.android.qiqueqiao.customs.label.TypeTagAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by yuchance on 2018/4/12.
 */


public class ResourceTypeAdapter extends BaseQuickAdapter<SeclectCateBean.CommonCategoryBean,BaseViewHolder> {


    private List<SeclectCateBean.UserCategoryBean> other = new ArrayList<>();
    private Context mContext;
    private ArrayList<Integer> isFinish = new ArrayList<>();
    private Set<ArrayList<Integer>> selectSet = new HashSet<>();
    private ArrayList<Integer> contentList;
    private SeclectCateBean seclectCateBean = new SeclectCateBean();


    public ResourceTypeAdapter(Context context,int layoutResId, @Nullable List<SeclectCateBean.CommonCategoryBean> data,SeclectCateBean seclectCateBean,List<SeclectCateBean.UserCategoryBean> other) {
        super(layoutResId, data);
        this.mContext = context;
        this.seclectCateBean = seclectCateBean;
        this.other = other;
    }

    public void setSeclectCateBean(SeclectCateBean seclectCateBean) {
        this.seclectCateBean = seclectCateBean;
    }


    public void setIsFinish(ArrayList<Integer> isFinish) {
        this.isFinish = isFinish;
    }


    @Override
    protected void convert(final BaseViewHolder helper, final SeclectCateBean.CommonCategoryBean item) {
        helper.setText(R.id.resource_tv,item.getName());
        final NestedRecyclerView tagLayout = helper.getView(R.id.tagflowType);
//        int height = flowLayoutManager.getTotalHeight();

        tagLayout.setLayoutManager(new FlowLayoutManager());
        TypeTagAdapter adapter = new TypeTagAdapter(mContext,R.layout.item_label_typetv,item.getZlist(),seclectCateBean,other);
        tagLayout.setAdapter(adapter);
    }



    public setFinish setFinish;

    public interface setFinish{
        void setFinish();
    }

    public void setSetFinish(List<Integer> seletList) {
        this.setFinish = setFinish;
    }

}
