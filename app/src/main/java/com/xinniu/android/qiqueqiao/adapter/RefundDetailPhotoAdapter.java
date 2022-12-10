package com.xinniu.android.qiqueqiao.adapter;

import android.content.Context;
import androidx.annotation.Nullable;
import android.util.SparseArray;
import android.view.View;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xinniu.android.qiqueqiao.R;
import com.xinniu.android.qiqueqiao.utils.ImageLoader;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yuchance on 2018/3/31.
 */

public class RefundDetailPhotoAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

    Context context;
    ArrayList<String> data = new ArrayList<>();
    ArrayList<String> thumbs = new ArrayList<>();

    SparseArray<ImageView> imageGroupList = new SparseArray<>();

    public RefundDetailPhotoAdapter(int layoutResId, @Nullable List<String> data, Context context, List<String> datas) {
        super(layoutResId, data);
        this.context = context;
        this.thumbs = (ArrayList<String>) data;
        this.data = (ArrayList<String>) datas;
        imageGroupList.clear();
    }


    @Override
    protected void convert(final BaseViewHolder helper, final String item) {
        ImageLoader.loadLocalImg(item, (ImageView) helper.getView(R.id.item_photoImg));
        imageGroupList.put(helper.getAdapterPosition(), (ImageView) helper.getView(R.id.item_photoImg));
//        if (mAllImageWidth>0){
//            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(2*mAllImageWidth/7,2*mAllImageWidth/7);
//            (helper.getView(R.id.item_photoImg)).setLayoutParams(params);
//        }
        (helper.getView(R.id.item_photoImg)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int postion = helper.getAdapterPosition();
                setOnClick.setOnClick(data, postion, imageGroupList, (ImageView) v);


            }
        });

    }


    public interface setOnClick {
        void setOnClick(ArrayList<String> list, int position, SparseArray<ImageView> data, ImageView v);
    }

    private setOnClick setOnClick;

    public void setSetOnClick(RefundDetailPhotoAdapter.setOnClick setOnClick) {
        this.setOnClick = setOnClick;
    }
}
