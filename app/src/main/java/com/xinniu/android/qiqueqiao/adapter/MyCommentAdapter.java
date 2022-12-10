package com.xinniu.android.qiqueqiao.adapter;

import android.content.Context;
import androidx.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xinniu.android.qiqueqiao.R;
import com.xinniu.android.qiqueqiao.bean.GetConfigBean;
import com.xinniu.android.qiqueqiao.bean.MyCommentBean;
import com.xinniu.android.qiqueqiao.utils.ImageLoader;
import com.xinniu.android.qiqueqiao.utils.TimeUtils;

import java.util.List;

/**
 * Created by yuchance on 2018/12/26.
 */

public class MyCommentAdapter extends BaseQuickAdapter<MyCommentBean.ListBean,BaseViewHolder>{

    private Context context;

    public MyCommentAdapter(Context context,int layoutResId, @Nullable List<MyCommentBean.ListBean> data) {
        super(layoutResId, data);
        this.context = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, final MyCommentBean.ListBean item) {
        helper.setText(R.id.item_comment_nametv,item.getNickname());
        helper.setText(R.id.item_comment_timetv, TimeUtils.getTimeStateNew(item.getCreate_time()+""));

        ImageLoader.loadAvter(item.getThumb_img(), (ImageView) helper.getView(R.id.item_comment_headimg));
        if (item.getIs_v()==1){
            helper.getView(R.id.item_comment_isv).setVisibility(View.VISIBLE);
        }else {
            helper.getView(R.id.item_comment_isv).setVisibility(View.GONE);
        }
        if (item.getType()==1){
            helper.setText(R.id.item_comment_replytv,"【"+item.getCompany()+"】"+item.getTitle());
            helper.setText(R.id.item_commenttv,item.getContent());
        }else {
            if (item.getIs_del() == 1){
                helper.setText(R.id.item_comment_replytv,item.getTo_nickname() +": " + "该留言已被删除");
            }else {
                helper.setText(R.id.item_comment_replytv, item.getTo_nickname() + ": " + item.getTo_content());
            }
            helper.setText(R.id.item_commenttv,item.getContent());
        }
        if (item.getStatus() == 0){
            helper.setText(R.id.item_comment_shieldtv,"[已被屏蔽]");
        }else {
            helper.setText(R.id.item_comment_shieldtv,"");
        }
        helper.getView(R.id.bmy_commentRl).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (item.getIs_del() == 1) {
                    setOnClick.setOnClick(item.getTopic_id(),item.getComment_id(),"该留言已被删除",item.getTopic_type());
                }else if (item.getStatus() == 0){
                    setOnClick.setOnClick(item.getTopic_id(),item.getComment_id(),"该留言已被屏蔽",item.getTopic_type());
                }else {
                    setOnClick.setOnClick(item.getTopic_id(),item.getComment_id(),"",item.getTopic_type());
                }
            }
        });


    }

    public interface setOnClick{
        void setOnClick(int resourceId,int commentId,String commentStatus,int type);
    }
    private setOnClick setOnClick;

    public void setSetOnClick(MyCommentAdapter.setOnClick setOnClick) {
        this.setOnClick = setOnClick;
    }
}
