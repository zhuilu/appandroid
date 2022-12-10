package com.xinniu.android.qiqueqiao.adapter;

import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xinniu.android.qiqueqiao.R;
import com.xinniu.android.qiqueqiao.activity.ClassRoomDetailActivity;
import com.xinniu.android.qiqueqiao.activity.ClassRoomDetailTwoActivity;
import com.xinniu.android.qiqueqiao.bean.ClassRoomListBean;
import com.xinniu.android.qiqueqiao.utils.ImageLoader;
import com.xinniu.android.qiqueqiao.utils.RoundImageView;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
//import android.support.v4.content.ContextCompat;

/**
 * Created by yuchance on 2018/10/9.
 */

public class ClassRoomAdapter extends BaseQuickAdapter<ClassRoomListBean.ListBean, BaseViewHolder> {
    private AppCompatActivity context;
    private List<ClassRoomListBean.ListBean> mData = new ArrayList<>();

    public ClassRoomAdapter(AppCompatActivity context, int layoutResId, @Nullable List<ClassRoomListBean.ListBean> data) {
        super(layoutResId, data);
        this.context = context;
        this.mData = data;
    }

    @Override
    protected void convert(final BaseViewHolder helper, final ClassRoomListBean.ListBean item) {
        RoundImageView roundImageView = helper.getView(R.id.img_poster);
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

        helper.setText(R.id.tv_comment_num, item.getComment_count() + "评论");
        if (item.getUpvote_num() > 999) {
            helper.setText(R.id.tv_good_num, "999+点赞");
        } else {
            helper.setText(R.id.tv_good_num, item.getUpvote_num() + "点赞");
        }

        TextView tv = helper.getView(R.id.tv_type);
        if(item.getType()==1){
            tv.setVisibility(View.VISIBLE);
        }else{
            tv.setVisibility(View.GONE);
        }
        if (helper.getAdapterPosition() == mData.size() - 1) {
            helper.setVisible(R.id.view, false);
        } else {
            helper.setVisible(R.id.view, true);
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

    private Onclick onclick;

    public interface Onclick {
        void click(ClassRoomListBean.ListBean name, int position);

    }

    public void setOnclick(Onclick onclick) {
        this.onclick = onclick;
    }
}
