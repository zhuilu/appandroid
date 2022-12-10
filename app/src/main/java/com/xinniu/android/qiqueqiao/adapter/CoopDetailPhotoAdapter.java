package com.xinniu.android.qiqueqiao.adapter;

import android.content.Context;
import androidx.annotation.Nullable;
import android.util.SparseArray;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.scwang.smartrefresh.layout.util.DensityUtil;
import com.xinniu.android.qiqueqiao.R;
import com.xinniu.android.qiqueqiao.activity.ViewpagerImageActivity;
import com.xinniu.android.qiqueqiao.bean.ServiceCategoryAndTag;
import com.xinniu.android.qiqueqiao.utils.ImageLoader;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yuchance on 2018/3/31.
 */

public class CoopDetailPhotoAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

    Context context;
    ArrayList<String> data = new ArrayList<>();
    private int mAllImageWidth = 0;
    ArrayList<String> thumbs = new ArrayList<>();

    SparseArray<ImageView> imageGroupList= new SparseArray<>();

    public CoopDetailPhotoAdapter(int layoutResId, @Nullable List<String> data, Context context, List<String> datas) {
        super(layoutResId, data);
        this.context = context;
        this.thumbs = (ArrayList<String>) data;
        this.data = (ArrayList<String>) datas;
        imageGroupList.clear();
    }

    //    public CoopDetailPhotoAdapter(int layoutResId, @Nullable List<String> thumbs,List<String> data  ,Context context) {
//        super(layoutResId, data);
//        this.context = context;
//        this.thumbs = (ArrayList<String>) thumbs;
//        this.data = (ArrayList<String>) data;
//
//////        获取屏幕宽高
////        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
////        int screenWidth = wm.getDefaultDisplay().getWidth();
////        mAllImageWidth = screenWidth - DensityUtil.dp2px(50) ;
//    }
    public CoopDetailPhotoAdapter(int layoutResId, @Nullable List<String> data, Context context) {
        super(layoutResId, data);
        this.context = context;
        this.data = (ArrayList<String>) data;
////        获取屏幕宽高
//        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
//        int screenWidth = wm.getDefaultDisplay().getWidth();
//        mAllImageWidth = screenWidth - DensityUtil.dp2px(50) ;
    }

    @Override
    protected void convert(final BaseViewHolder helper, final String item) {
        ImageLoader.loadLocalImg(item, (ImageView) helper.getView(R.id.item_photoImg));
        imageGroupList.put(helper.getAdapterPosition(),(ImageView) helper.getView(R.id.item_photoImg));
//        if (mAllImageWidth>0){
//            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(2*mAllImageWidth/7,2*mAllImageWidth/7);
//            (helper.getView(R.id.item_photoImg)).setLayoutParams(params);
//        }
        (helper.getView(R.id.item_photoImg)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int postion = helper.getAdapterPosition();
                //  ViewpagerImageActivity.start(context, data, postion);

                    setOnClick.setOnClick(data, postion, imageGroupList, (ImageView) v);



            }
        });

    }


    public interface setOnClick {
        void setOnClick(ArrayList<String> list, int position,SparseArray<ImageView> data,ImageView v);
    }

    private setOnClick setOnClick;

    public void setSetOnClick(CoopDetailPhotoAdapter.setOnClick setOnClick) {
        this.setOnClick = setOnClick;
    }
}
