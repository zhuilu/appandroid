package com.xinniu.android.qiqueqiao.adapter;

import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatRatingBar;

import com.xinniu.android.qiqueqiao.R;
import com.xinniu.android.qiqueqiao.activity.CoopDetailActivity;
import com.xinniu.android.qiqueqiao.adapter.base.BDRecyclerViewAdapter;
import com.xinniu.android.qiqueqiao.adapter.base.BDRecylerViewHolder;
import com.xinniu.android.qiqueqiao.bean.ResourceItem;
import com.xinniu.android.qiqueqiao.dialog.DeleteItemDialog;
import com.xinniu.android.qiqueqiao.utils.ImageLoader;
import com.xinniu.android.qiqueqiao.utils.StringUtils;
import com.xinniu.android.qiqueqiao.widget.VipTextView;

import java.util.List;

/**
 * Created by BDXK on 2017/11/16.
 */

public class IndexFragmentAdapter extends BDRecyclerViewAdapter {

    private Context context;

    public String getKeyWord() {
        return keyWord;
    }

    public void setKeyWord(String keyWord) {
        this.keyWord = keyWord;
    }

    private String keyWord;
    private List datas;

    public IndexFragmentAdapter(Context context, int layoutId, List datas) {
        super(context, layoutId, datas);
        this.context = context;
        this.datas = datas;
    }

    @Override
    public void convert(final BDRecylerViewHolder holder, Object o) {
        final ResourceItem resourceItem = (ResourceItem) o;
        if (!TextUtils.isEmpty(keyWord)){
            String realName = resourceItem.getRealname();
            String companyInfo = resourceItem.getCompany();
            String positionInfo = resourceItem.getPosition();
            ((TextView)holder.getView(R.id.tvName)).setText(StringUtils.strToSpannableStr(realName,keyWord));

            if (resourceItem.getProvide_tags().size() > 0){
                ((LinearLayout) holder.getView(R.id.offer_rv)).removeAllViews();
                holder.getView(R.id.offer_ll).setVisibility(View.VISIBLE);
                for (int i = 0 ; i < resourceItem.getProvide_tags().size();i++){
                    View view = LayoutInflater.from(context).inflate(R.layout.item_index,null);
                    ImageView head = (ImageView) view.findViewById(R.id.head_icon);
                    ImageLoader.loadLocalImg(resourceItem.getProvide_tags().get(i).getIcon(), head);
                    TextView content = (TextView) view.findViewById(R.id.content);
                    content.setText(StringUtils.strToSpannableStr(resourceItem.getProvide_tags().get(i).getName(),keyWord));
                    ((LinearLayout) holder.getView(R.id.offer_rv)).addView(view);
                }
            }else{
                holder.getView(R.id.offer_ll).setVisibility(View.GONE);
            }
            if (resourceItem.getNeed_tags().size() > 0){
                holder.getView(R.id.need_ll).setVisibility(View.VISIBLE);
                ((LinearLayout) holder.getView(R.id.need_rv)).removeAllViews();
                for (int i = 0 ; i < resourceItem.getNeed_tags().size();i++){
                    View view = LayoutInflater.from(context).inflate(R.layout.item_index,null);
                    ImageView head = (ImageView) view.findViewById(R.id.head_icon);
                    ImageLoader.loadLocalImg(resourceItem.getNeed_tags().get(i).getIcon(), head);
                    TextView content = (TextView) view.findViewById(R.id.content);
                    content.setText(StringUtils.strToSpannableStr(resourceItem.getNeed_tags().get(i).getName(),keyWord));
                    ((LinearLayout) holder.getView(R.id.need_rv)).addView(view);
                }
            }else{
                holder.getView(R.id.need_ll).setVisibility(View.GONE);
            }

            ((TextView)holder.getView(R.id.tvInfo)).setText(StringUtils.strToSpannableStr(positionInfo,keyWord));
            ((TextView)holder.getView(R.id.company_info)).setText(StringUtils.strToSpannableStr(companyInfo,keyWord));
            ((VipTextView)holder.getView(R.id.vip_name)).setVisibility(View.GONE);
            ((AppCompatRatingBar)holder.getView(R.id.star_view)).setVisibility(View.GONE);
            if (resourceItem.getIs_v() == 1){
                if (!TextUtils.isEmpty(resourceItem.getFont_color()) && !TextUtils.isEmpty(resourceItem.getBackground_color())){
                    ((VipTextView)holder.getView(R.id.vip_name)).setVisibility(View.VISIBLE);
                    ((AppCompatRatingBar)holder.getView(R.id.star_view)).setVisibility(View.VISIBLE);
                    ((VipTextView)holder.getView(R.id.vip_name)).setTextSize(resourceItem.getFont_size());
                    ((VipTextView)holder.getView(R.id.vip_name)).setText(resourceItem.getWords());
                    ((VipTextView)holder.getView(R.id.vip_name)).setBorderColor(Color.parseColor("#"+resourceItem.getFont_color()));
                    ((VipTextView)holder.getView(R.id.vip_name)).setCColor(Color.parseColor("#"+resourceItem.getBackground_color()));
                    ((AppCompatRatingBar)holder.getView(R.id.star_view)).setNumStars(resourceItem.getCircle_level());
                }
            }

            if (resourceItem.getIs_v() == 1){
                holder.getView(R.id.is_add_v).setVisibility(View.VISIBLE);
            }else{
                holder.getView(R.id.is_add_v).setVisibility(View.GONE);
            }
            ImageLoader.loadImage(resourceItem.getHead_pic(),(ImageView) holder.getView(R.id.imgLogo));

            holder.getView(R.id.itemRoot).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    CoopDetailActivity.start(context,resourceItem.getId());
                }
            });
        }else{
            if (resourceItem.getProvide_tags().size() > 0){
                ((LinearLayout) holder.getView(R.id.offer_rv)).removeAllViews();
                holder.getView(R.id.offer_ll).setVisibility(View.VISIBLE);
                for (int i = 0 ; i < resourceItem.getProvide_tags().size();i++){
                    View view = LayoutInflater.from(context).inflate(R.layout.item_index,null);
                    ImageView head = (ImageView) view.findViewById(R.id.head_icon);
                    ImageLoader.loadLocalImg(resourceItem.getProvide_tags().get(i).getIcon(), head);
                    TextView content = (TextView) view.findViewById(R.id.content);
                    content.setMaxLines(1);
                    content.setText(StringUtils.hintString(resourceItem.getProvide_tags().get(i).getName(),5));
                    ((LinearLayout) holder.getView(R.id.offer_rv)).addView(view);
                }
            }else{
                holder.getView(R.id.offer_ll).setVisibility(View.GONE);
            }
            if (resourceItem.getNeed_tags().size() > 0){
                holder.getView(R.id.need_ll).setVisibility(View.VISIBLE);
                ((LinearLayout) holder.getView(R.id.need_rv)).removeAllViews();
                for (int i = 0 ; i < resourceItem.getNeed_tags().size();i++){
                    View view = LayoutInflater.from(context).inflate(R.layout.item_index,null);
                    ImageView head = (ImageView) view.findViewById(R.id.head_icon);
                    ImageLoader.loadLocalImg(resourceItem.getNeed_tags().get(i).getIcon(), head);
                    TextView content = (TextView) view.findViewById(R.id.content);
                    content.setMaxLines(1);
                    content.setText(StringUtils.hintString(resourceItem.getNeed_tags().get(i).getName(),5));
                    ((LinearLayout) holder.getView(R.id.need_rv)).addView(view);
                }
            }else{
                holder.getView(R.id.need_ll).setVisibility(View.GONE);
            }
            ((TextView)holder.getView(R.id.tvName)).setText(resourceItem.getRealname());
            ((TextView)holder.getView(R.id.tvInfo)).setText(resourceItem.getPosition());
            ((TextView)holder.getView(R.id.company_info)).setText(resourceItem.getCompany());
            ((VipTextView)holder.getView(R.id.vip_name)).setVisibility(View.GONE);
            ((AppCompatRatingBar)holder.getView(R.id.star_view)).setVisibility(View.GONE);
            if (resourceItem.getIs_vip() == 1){
                if (!TextUtils.isEmpty(resourceItem.getFont_color()) && !TextUtils.isEmpty(resourceItem.getBackground_color())){
                    ((VipTextView)holder.getView(R.id.vip_name)).setVisibility(View.VISIBLE);
                    ((AppCompatRatingBar)holder.getView(R.id.star_view)).setVisibility(View.VISIBLE);
                    ((VipTextView)holder.getView(R.id.vip_name)).setTextSize(resourceItem.getFont_size());
                    ((VipTextView)holder.getView(R.id.vip_name)).setText(resourceItem.getWords());
                    ((VipTextView)holder.getView(R.id.vip_name)).setBorderColor(Color.parseColor("#"+resourceItem.getFont_color()));
                    ((VipTextView)holder.getView(R.id.vip_name)).setCColor(Color.parseColor("#"+resourceItem.getBackground_color()));
                    ((AppCompatRatingBar)holder.getView(R.id.star_view)).setNumStars(resourceItem.getCircle_level());
                }
            }

            if (resourceItem.getIs_v() == 1){
                holder.getView(R.id.is_add_v).setVisibility(View.VISIBLE);
            }else{
                holder.getView(R.id.is_add_v).setVisibility(View.GONE);
            }
            ImageLoader.loadImage(resourceItem.getHead_pic(),(ImageView) holder.getView(R.id.imgLogo));

            holder.getView(R.id.itemRoot).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    CoopDetailActivity.start(context,resourceItem.getId());
                }
            });
        }
        holder.getView(R.id.fragment_item_close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DeleteItemDialog dialog = new DeleteItemDialog(context,R.style.QLDialog,resourceItem.getId());
                dialog.setDeleteItemListener(new DeleteItemDialog.DeleteItem() {
                    @Override
                    public void deleteItem(int id) {
                        datas.remove(getPosition(holder));
                        notifyDataSetChanged();
                    }
                });
                dialog.show();
            }
        });
    }
}
