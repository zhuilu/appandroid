package com.xinniu.android.qiqueqiao.adapter;

//import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xinniu.android.qiqueqiao.R;
import com.xinniu.android.qiqueqiao.bean.InteractiveNewsBean;
import com.xinniu.android.qiqueqiao.utils.ImageLoader;
import com.xinniu.android.qiqueqiao.utils.TimeUtils;
import com.xinniu.android.qiqueqiao.widget.CircleImageView;

import java.util.List;

/**
 * Created by yuchance on 2018/10/31.
 */

public class InteractInformAdapter extends BaseQuickAdapter<InteractiveNewsBean.ListBean,BaseViewHolder> {


    public InteractInformAdapter(int layoutResId, @Nullable List<InteractiveNewsBean.ListBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, final InteractiveNewsBean.ListBean item) {
        helper.setText(R.id.item_interact_nametv,item.getNickname());
        helper.setText(R.id.item_interact_positiontv,item.getCompany() + item.getPosition());
        ImageLoader.loadAvter(item.getThumb_img(), (CircleImageView) helper.getView(R.id.item_interact_headimg));
        if (item.getType()==1){
            helper.setVisible(R.id.item_interact_replyinfotv,false);
            helper.setText(R.id.item_interact_replytv,"给你留言");
        }else if (item.getType() == 2){
            helper.setVisible(R.id.item_interact_replyinfotv,true);
            helper.setText(R.id.item_interact_replytv,"回复了");
            helper.setText(R.id.item_interact_replyinfotv,item.getTo_nickname());
        }else {
            helper.setVisible(R.id.item_interact_replyinfotv,false);
            helper.setText(R.id.item_interact_replytv,"回复了你");
        }
        helper.setText(R.id.item_interact_timetv, TimeUtils.millis2Stringt(item.getCreate_time()*1000));
        helper.setText(R.id.item_interact_replycontenttv,item.getContent());
        helper.getView(R.id.interact_informRl).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setOnclick.setOnClick(item.getTopic_id(),item.getComment_id(),item.getTopic_type());
            }
        });
        if (item.isNew()){
            helper.getView(R.id.interact_informRl).setSelected(true);
        }else {
            helper.getView(R.id.interact_informRl).setSelected(false);
        }

    }

    public interface setOnclick{
        void setOnClick(int resourceId,int commentId,int type);
    }

    private setOnclick setOnclick;

    public void setSetOnclick(InteractInformAdapter.setOnclick setOnclick) {
        this.setOnclick = setOnclick;
    }
}
