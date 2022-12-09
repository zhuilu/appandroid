package com.xinniu.android.qiqueqiao.adapter;

import android.content.Context;
//import androidx.recyclerview.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.xinniu.android.qiqueqiao.R;
import com.xinniu.android.qiqueqiao.bean.ResourceItem;
import com.xinniu.android.qiqueqiao.utils.ImageLoader;
import com.xinniu.android.qiqueqiao.utils.StringUtils;

import java.util.List;

/**
 * Created by lzq on 2018/1/20.
 */

public class IndeItemNeedAdapter extends RecyclerView.Adapter<IndeItemNeedAdapter.ViewHodler> {

    private Context context;
    private List<ResourceItem.NeedTagsBean> mList;
    private String keyWord;


    public IndeItemNeedAdapter(Context context, List<ResourceItem.NeedTagsBean> list) {
        this.mList = list;
        this.context = context;
    }

    @Override
    public ViewHodler onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_index, parent, false);
        ViewHodler viewHolder = new ViewHodler(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHodler holder, final int position) {
        ImageLoader.loadLocalImg(mList.get(position).getIcon(), holder.itemTv);
        if (TextUtils.isEmpty(keyWord)){
            holder.contentTv.setText(mList.get(position).getName());
        }else{
            holder.contentTv.setText(StringUtils.strToSpannableStr(mList.get(position).getName(),keyWord));
        }
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class ViewHodler extends RecyclerView.ViewHolder {

        ImageView itemTv;
        TextView contentTv;

        public ViewHodler(View itemView) {
            super(itemView);
            itemTv = (ImageView) itemView.findViewById(R.id.head_icon);
            contentTv = (TextView) itemView.findViewById(R.id.content);
        }
    }

    public String getKeyWord() {
        return keyWord;
    }

    public void setKeyWord(String keyWord) {
        this.keyWord = keyWord;
    }
}
