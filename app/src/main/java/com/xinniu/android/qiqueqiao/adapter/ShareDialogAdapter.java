package com.xinniu.android.qiqueqiao.adapter;

import android.content.Context;
import android.media.Image;
//import androidx.recyclerview.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.TextView;

import com.xinniu.android.qiqueqiao.R;
import com.xinniu.android.qiqueqiao.adapter.base.BDRecyclerViewAdapter;
import com.xinniu.android.qiqueqiao.adapter.base.BDRecylerViewHolder;
import com.xinniu.android.qiqueqiao.customs.qldialog.QLShareDialog;

import java.util.List;

/**
 * Created by qinlei
 * Created on 2017/12/12
 * Created description :
 */

public class ShareDialogAdapter extends BDRecyclerViewAdapter {

    private String qrcodeStr = "生成图片";

    public void setQrcodeStr(String qrcodeStr) {
        this.qrcodeStr = qrcodeStr;
    }

    public ShareDialogAdapter(Context context, List datas) {
        super(context, R.layout.share_dialog_item, datas);
    }

    public ShareDialogAdapter(Context context, int layoutId, List datas) {
        super(context, layoutId, datas);
    }


    @Override
    public void convert(BDRecylerViewHolder holder, Object o) {
        QLShareDialog.ShareType shareType = (QLShareDialog.ShareType) o;
        ImageView imageView = holder.getView(R.id.image_share);
        TextView textView = holder.getView(R.id.tv_share);
        switch (shareType) {
            case SHARE_WEIXIN:
                imageView.setImageResource(R.mipmap.share_weixin_t);
                textView.setText("微信");
                break;
            case SHARE_CIRCLE:
                imageView.setImageResource(R.mipmap.share_circle_t);
                textView.setText("朋友圈");
                break;
            case SHARE_QQ:
                imageView.setImageResource(R.mipmap.share_qq_t);
                textView.setText("QQ");
                break;
            case SHARE_WEIBO:
                imageView.setImageResource(R.mipmap.share_weibo);
                textView.setText("微博");
                break;
            case SHARE_QRCODE:
                imageView.setImageResource(R.mipmap.create_photo_icon_t);
                textView.setText(qrcodeStr);
                break;
        }
    }

}
