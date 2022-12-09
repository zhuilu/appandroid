package com.xinniu.android.qiqueqiao.adapter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
//import android.support.annotation.Nullable;
//import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.SparseArray;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xinniu.android.qiqueqiao.R;
import com.xinniu.android.qiqueqiao.activity.CoopDetailActivity;
import com.xinniu.android.qiqueqiao.activity.LoginNewActivity;
import com.xinniu.android.qiqueqiao.activity.MyPushActivity;
import com.xinniu.android.qiqueqiao.activity.PersonCentetActivity;
import com.xinniu.android.qiqueqiao.activity.SreachActivity;
import com.xinniu.android.qiqueqiao.bean.CommunicationRecordBean;
import com.xinniu.android.qiqueqiao.bean.MainBean;
import com.xinniu.android.qiqueqiao.user.UserInfoHelper;
import com.xinniu.android.qiqueqiao.utils.ImageLoader;
import com.xinniu.android.qiqueqiao.utils.RoundImageView;
import com.xinniu.android.qiqueqiao.utils.StringUtils;
import com.xinniu.android.qiqueqiao.utils.TimeUtils;
import com.xinniu.android.qiqueqiao.widget.CircleImageView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yuchance on 2018/12/27.
 */

public class CommunicationRecordAdapter extends BaseQuickAdapter<CommunicationRecordBean.DataBean.ListBean, BaseViewHolder> {

    private Activity context;
    ArrayList<String> images = new ArrayList<>();

    SparseArray<ImageView> imageGroupList= new SparseArray<>();

    public CommunicationRecordAdapter(Activity context, int layoutResId, @Nullable List<CommunicationRecordBean.DataBean.ListBean> data) {
        super(layoutResId, data);
        this.context = context;
        imageGroupList.clear();
    }

    @Override
    protected void convert(BaseViewHolder helper, final CommunicationRecordBean.DataBean.ListBean item) {
        //	1主动沟通，2：添加好友，3：人工对接
        if (item.getType() == 1) {
            helper.getView(R.id.mitem_connect_fl).setVisibility(View.VISIBLE);
            helper.getView(R.id.llayout_img).setVisibility(View.GONE);
            helper.getView(R.id.mitem_connect_nametv).setVisibility(View.VISIBLE);
            helper.getView(R.id.mitem_connect_positiontv).setVisibility(View.VISIBLE);
            helper.getView(R.id.img_type).setBackgroundResource(R.mipmap.communication_1);
            if (item.getIs_v() == 1) {
                helper.setVisible(R.id.mitem_is_v, true);
            } else {
                helper.setVisible(R.id.mitem_is_v, false);
            }
            ImageLoader.loadAvter(item.getHead_pic(), (CircleImageView) helper.getView(R.id.mitem_connect_img));
            helper.setText(R.id.mitem_connect_nametv, item.getRealname());
            if (!TextUtils.isEmpty(item.getCompany())) {
                helper.setText(R.id.mitem_connect_positiontv,  item.getCompany() +"|"+ item.getPosition());
            } else {
                helper.setText(R.id.mitem_connect_positiontv,  item.getPosition());
            }
            helper.getView(R.id.llayout_root).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    PersonCentetActivity.start(context, item.getTo_user_id()+"");
                }
            });


        } else if (item.getType() == 2) {
            helper.getView(R.id.mitem_connect_fl).setVisibility(View.VISIBLE);
            helper.getView(R.id.llayout_img).setVisibility(View.GONE);
            helper.getView(R.id.mitem_connect_nametv).setVisibility(View.VISIBLE);
            helper.getView(R.id.mitem_connect_positiontv).setVisibility(View.VISIBLE);
            helper.getView(R.id.img_type).setBackgroundResource(R.mipmap.communication_2);
            if (item.getIs_v() == 1) {
                helper.setVisible(R.id.mitem_is_v, true);
            } else {
                helper.setVisible(R.id.mitem_is_v, false);
            }
            ImageLoader.loadAvter(item.getHead_pic(), (CircleImageView) helper.getView(R.id.mitem_connect_img));
            helper.setText(R.id.mitem_connect_nametv, item.getRealname());
            if (!TextUtils.isEmpty(item.getCompany())) {
                helper.setText(R.id.mitem_connect_positiontv,  item.getCompany() + item.getPosition());
            } else {
                helper.setText(R.id.mitem_connect_positiontv,  item.getPosition());
            }
            helper.getView(R.id.llayout_root).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    PersonCentetActivity.start(context, item.getTo_user_id()+"");
                }
            });

        } else if (item.getType() == 3) {
            helper.getView(R.id.mitem_connect_fl).setVisibility(View.GONE);
            helper.getView(R.id.llayout_img).setVisibility(View.VISIBLE);
            helper.getView(R.id.mitem_connect_nametv).setVisibility(View.GONE);
            helper.getView(R.id.mitem_connect_positiontv).setVisibility(View.GONE);
            helper.getView(R.id.img_type).setBackgroundResource(R.mipmap.communication_3);
            if(!StringUtils.isEmpty(item.getThumb_img())) {
                String[] thumbImg =item.getThumb_img().split(",");
                String[] img =item.getImages().split(",");
                for(int i=0;i<img.length;i++){
                    images.add(img[i]);
                }
                if (thumbImg.length == 3) {
                    helper.getView(R.id.img_01).setVisibility(View.VISIBLE);
                    helper.getView(R.id.img_02).setVisibility(View.VISIBLE);
                    helper.getView(R.id.img_03).setVisibility(View.VISIBLE);
                    ImageLoader.loadLocalImg(thumbImg[0], (RoundImageView) helper.getView(R.id.img_01));
                    ImageLoader.loadLocalImg(thumbImg[1], (RoundImageView) helper.getView(R.id.img_02));
                    ImageLoader.loadLocalImg(thumbImg[2], (RoundImageView) helper.getView(R.id.img_03));
                    imageGroupList.put(0,(ImageView) helper.getView(R.id.img_01));
                    imageGroupList.put(1,(ImageView) helper.getView(R.id.img_02));
                    imageGroupList.put(2,(ImageView) helper.getView(R.id.img_03));
                } else if (thumbImg.length == 2) {
                    helper.getView(R.id.img_01).setVisibility(View.VISIBLE);
                    helper.getView(R.id.img_02).setVisibility(View.VISIBLE);
                    helper.getView(R.id.img_03).setVisibility(View.GONE);
                    ImageLoader.loadLocalImg(thumbImg[0], (RoundImageView) helper.getView(R.id.img_01));
                    ImageLoader.loadLocalImg(thumbImg[1], (RoundImageView) helper.getView(R.id.img_02));
                    imageGroupList.put(0,(ImageView) helper.getView(R.id.img_01));
                    imageGroupList.put(1,(ImageView) helper.getView(R.id.img_02));
                } else if (thumbImg.length == 1) {
                    helper.getView(R.id.img_01).setVisibility(View.VISIBLE);
                    helper.getView(R.id.img_02).setVisibility(View.GONE);
                    helper.getView(R.id.img_03).setVisibility(View.GONE);
                    ImageLoader.loadLocalImg(thumbImg[0], (RoundImageView) helper.getView(R.id.img_01));
                    imageGroupList.put(0,(ImageView) helper.getView(R.id.img_01));

                }
            }else{
                helper.getView(R.id.llayout_img).setVisibility(View.GONE);
            }
            //图片点击放大功能实现
            helper.getView(R.id.img_01).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    setOnClick.setOnClick(images, 0, imageGroupList, (ImageView)view);
                }
            });
            helper.getView(R.id.img_02).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    setOnClick.setOnClick(images, 1, imageGroupList, (ImageView)view);
                }
            });
            helper.getView(R.id.img_03).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    setOnClick.setOnClick(images, 2, imageGroupList, (ImageView)view);
                }
            });
        }




    }

    public interface setOnClick {
        void setOnClick(ArrayList<String> list, int position, SparseArray<ImageView> data, ImageView v);
    }

    private setOnClick setOnClick;

    public void setSetOnClick(setOnClick setOnClick) {
        this.setOnClick = setOnClick;
    }
}
