package com.xinniu.android.qiqueqiao.adapter.base;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.scwang.smartrefresh.layout.util.DensityUtil;
import com.xinniu.android.qiqueqiao.R;
import com.xinniu.android.qiqueqiao.activity.TakePhotoTwoActivity;
import com.xinniu.android.qiqueqiao.utils.ImageLoader;
import com.xinniu.android.qiqueqiao.widget.ExplosionField;

import java.util.ArrayList;

import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by lzq on 2018/1/17.
 */

public class FeedBackImgViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private ArrayList<String> mList;
    private ExplosionField mExplosionField;
    private final static int ADDP = 123;
    private final static int PHOTO = 234;
    public final static int TYPE= 704;
    private int max = 0;
    private int mAllImageWidth = 0;


    public FeedBackImgViewAdapter(Context context, ArrayList<String> list, int max) {
        mExplosionField = ExplosionField.attach2Window((AppCompatActivity) context);
        this.mList = list;
        this.context = context;
        this.max = max;
        //        获取屏幕宽高
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        int screenWidth = wm.getDefaultDisplay().getWidth();
        mAllImageWidth = screenWidth - DensityUtil.dp2px(80);
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        RecyclerView.ViewHolder holder = null;
        if (viewType == PHOTO) {
            View view = inflater.inflate(R.layout.item_edit_resource, parent, false);
            holder = new ViewHodler(view);
        } else if (viewType == ADDP) {
            View view = inflater.inflate(R.layout.item_addimg, parent, false);
            holder = new AddViewHolder(view);
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof ViewHodler) {
            ImageLoader.loadLocalImg1(mList.get(position), ((ViewHodler) holder).itemTv);
            if (mAllImageWidth > 0) {
                FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(mAllImageWidth / 4, mAllImageWidth / 4);
                ((ViewHodler) holder).itemTv.setLayoutParams(params);
            }

            ((ViewHodler) holder).deleteTv.setVisibility(View.VISIBLE);

            ((ViewHodler) holder).deleteTv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mExplosionField.explode(v);
                    mList.remove(position);
                    notifyDataSetChanged();
                }
            });

        } else {
            if (mList.size() < max) {
                ((AddViewHolder) holder).itemAddIv.setVisibility(View.VISIBLE);
                if (mAllImageWidth > 0) {
                    FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(mAllImageWidth / 4, mAllImageWidth / 4);
                    ((AddViewHolder) holder).itemAddIv.setLayoutParams(params);
                }

                ((AddViewHolder) holder).itemAddIv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(context, TakePhotoTwoActivity.class);
                        intent.putStringArrayListExtra(TakePhotoTwoActivity.PHOTO_LIST, mList);
                        ((Activity) context).startActivityForResult(intent, TYPE);

                    }
                });

            } else {
                ((AddViewHolder) holder).itemAddIv.setVisibility(View.GONE);
            }

        }


    }


    @Override
    public int getItemViewType(int position) {
        if (position == mList.size()) {
            return ADDP;
        } else {
            return PHOTO;
        }

    }

    @Override
    public int getItemCount() {
        return mList.size() + 1;
    }


    class ViewHodler extends RecyclerView.ViewHolder {

        ImageView itemTv;
        FrameLayout itemFl;
        ImageView deleteTv;

        public ViewHodler(View itemView) {
            super(itemView);
            itemTv = (ImageView) itemView.findViewById(R.id.item_iv);
            deleteTv = (ImageView) itemView.findViewById(R.id.delete_item);
            itemFl = (FrameLayout) itemView.findViewById(R.id.item_fl);
        }
    }

    class AddViewHolder extends RecyclerView.ViewHolder {
        ImageView itemAddIv;

        public AddViewHolder(View itemView) {
            super(itemView);
            itemAddIv = (ImageView) itemView.findViewById(R.id.item_addiv);
        }
    }


}
