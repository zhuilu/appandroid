package com.xinniu.android.qiqueqiao.adapter;

import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xinniu.android.qiqueqiao.R;
import com.xinniu.android.qiqueqiao.activity.ClassRoomDetailActivity;
import com.xinniu.android.qiqueqiao.activity.ClassRoomDetailTwoActivity;
import com.xinniu.android.qiqueqiao.bean.MainBean;
import com.xinniu.android.qiqueqiao.utils.ImageLoader;
import com.xinniu.android.qiqueqiao.utils.RoundImageView;

import java.util.List;

import androidx.annotation.Nullable;
//import android.support.v4.content.ContextCompat;

/**
 * Created by yuchance on 2018/10/9.
 */

public class MainClassRoomAdapter extends BaseQuickAdapter<MainBean.VideoBean, BaseViewHolder> {
    private AppCompatActivity context;

    public MainClassRoomAdapter(AppCompatActivity context, int layoutResId, @Nullable List<MainBean.VideoBean> data) {
        super(layoutResId, data);
        this.context = context;
    }

    @Override
    protected void convert(final BaseViewHolder helper, final MainBean.VideoBean item) {
        RoundImageView roundImageView=  helper.getView(R.id.img_poster);
        roundImageView.setmBorderRadius(6);
        ImageLoader.loadLocalImg(item.getPoster(), (RoundImageView) helper.getView(R.id.img_poster));
        helper.setText(R.id.tv_title, item.getTitle());
        if (item.getCategory().size() > 0) {
            if (item.getCategory().size() == 3) {
                helper.setVisible(R.id.tv_tag_1, true);
                helper.setVisible(R.id.tv_tag_2, true);
                helper.setVisible(R.id.tv_tag_3, true);
                helper.setText(R.id.tv_tag_1, item.getCategory().get(0).getName());
                helper.setText(R.id.tv_tag_2, item.getCategory().get(1).getName());
                helper.setText(R.id.tv_tag_3, item.getCategory().get(2).getName());
            } else if (item.getCategory().size() == 2) {
                helper.setVisible(R.id.tv_tag_1, true);
                helper.setVisible(R.id.tv_tag_2, true);
                helper.setVisible(R.id.tv_tag_3, false);
                helper.setText(R.id.tv_tag_1, item.getCategory().get(0).getName());
                helper.setText(R.id.tv_tag_2, item.getCategory().get(1).getName());
            } else if (item.getCategory().size() == 1) {
                helper.setVisible(R.id.tv_tag_1, true);
                helper.setVisible(R.id.tv_tag_2, false);
                helper.setVisible(R.id.tv_tag_3, false);
                helper.setText(R.id.tv_tag_1, item.getCategory().get(0).getName());
            }
        } else {
            helper.setVisible(R.id.tv_tag_1, false);
            helper.setVisible(R.id.tv_tag_2, false);
            helper.setVisible(R.id.tv_tag_3, false);

        }


        helper.getView(R.id.rlayout_root).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(item.getType()==1) {
                    ClassRoomDetailActivity.start(context, item.getId(), item.getVideo_id());
                }else{
                    ClassRoomDetailTwoActivity.start(context, item.getId());
                }

            }
        });

    }


}
