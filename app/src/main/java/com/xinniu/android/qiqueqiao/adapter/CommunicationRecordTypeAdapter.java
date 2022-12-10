package com.xinniu.android.qiqueqiao.adapter;

import android.util.SparseArray;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xinniu.android.qiqueqiao.R;
import com.xinniu.android.qiqueqiao.bean.CommunicationRecordBean;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by yuchance on 2018/12/27.
 */

public class CommunicationRecordTypeAdapter extends BaseQuickAdapter<CommunicationRecordBean, BaseViewHolder> {

    private AppCompatActivity context;
    private List<CommunicationRecordBean> mData = new ArrayList<>();

    public CommunicationRecordTypeAdapter(AppCompatActivity context, int layoutResId, @Nullable List<CommunicationRecordBean> data) {
        super(layoutResId, data);
        this.context = context;
        this.mData = data;
    }

    @Override
    protected void convert(BaseViewHolder helper, final CommunicationRecordBean item) {
        String[] times=item.getTime().split("-");
        if(times.length>0) {
            helper.setText(R.id.tv_time, times[1] + "月" + times[2] + "日");
        }

        CommunicationRecordAdapter mainResoureAdapter = new CommunicationRecordAdapter(context, R.layout.item_communication_record, item.getData().getList());
        RecyclerView recyclerView = helper.getView(R.id.item_mrecyclerType);
        LinearLayoutManager manager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(mainResoureAdapter);
        mainResoureAdapter.setSetOnClick(new CommunicationRecordAdapter.setOnClick() {
            @Override
            public void setOnClick(ArrayList<String> list, int position, SparseArray<ImageView> data, ImageView v) {
                setOnClick.setOnClick(list, position, data, (ImageView)v);
            }
        });

    }

    public interface setOnClick {
        void setOnClick(ArrayList<String> list, int position, SparseArray<ImageView> data, ImageView v);
    }

    private setOnClick setOnClick;

    public void setSetOnClick(setOnClick setOnClick) {
        this.setOnClick = setOnClick;
    }
}
