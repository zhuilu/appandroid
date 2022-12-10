package com.xinniu.android.qiqueqiao.adapter;

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
import com.xinniu.android.qiqueqiao.activity.AddPictruActivity;
import com.xinniu.android.qiqueqiao.activity.TakePhotoActivity;
import com.xinniu.android.qiqueqiao.utils.ImageLoader;
import com.xinniu.android.qiqueqiao.widget.ExplosionField;

import java.util.ArrayList;

import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by lzq on 2018/1/17.
 */

public class EditResouceAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private ArrayList<String> mList;
    private boolean isAddPic = false;
    public final static String ADD = "add";
    private int type = 401;

    public final static int TYPE_NEED = 701;
    public final static int TYPE_OFFER = 702;
    private ExplosionField mExplosionField;
    private final static int ADDP = 123;
    private final static int PHOTO = 234;
    private int max = 0;
    private String THEEDIT = "theEdit";

    private int mAllImageWidth = 0;


    public EditResouceAdapter(Context context, ArrayList<String> list, int type) {
        mExplosionField = ExplosionField.attach2Window((AppCompatActivity) context);
        this.mList = list;
        this.context = context;
        this.type = type;
//        if (mList.size()<8 && !mList.contains(ADD)){
//            mList.add(ADD);
//        }
//        获取屏幕宽高
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        int screenWidth = wm.getDefaultDisplay().getWidth();
        mAllImageWidth = screenWidth - DensityUtil.dp2px(80);
    }

    public EditResouceAdapter(Context context, ArrayList<String> list, int type, int max) {
        mExplosionField = ExplosionField.attach2Window((AppCompatActivity) context);
        this.mList = list;
        this.context = context;
        this.type = type;
        this.max = max;
        //        获取屏幕宽高
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        int screenWidth = wm.getDefaultDisplay().getWidth();
        mAllImageWidth = screenWidth - DensityUtil.dp2px(80);
    }

    public void setTHEEDIT(String THEEDIT) {
        this.THEEDIT = THEEDIT;
    }
    //    @Override
//    public RecyclerView.ViewHodler onCreateViewHolder(ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(context).inflate(R.layout.item_edit_resource, parent, false);
//        ViewHodler viewHolder = new ViewHodler(view);
//
//        return viewHolder;
//    }

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
            ((ViewHodler) holder).itemFl.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    if (isAddPic){
//                        Intent intent = new Intent(context, AddPictruActivity.class);
//                        intent.putStringArrayListExtra(AddPictruActivity.PHOTO_LIST,mList);
//                        ((Activity)context).startActivityForResult(intent, type);
//                    }else{
//                        Intent intent = new Intent(context, TakePhotoActivity.class);
//                        intent.putStringArrayListExtra(TakePhotoActivity.PHOTO_LIST,mList);
//                        ((Activity)context).startActivityForResult(intent, type);
//                    }
                }
            });
            ((ViewHodler) holder).deleteTv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mExplosionField.explode(v);

//                    if (mList.size() < 8 && !mList.contains(ADD)){
//                        mList.add(ADD);
//                    }
                    if (THEEDIT.equals("theEdit")) {
                        if (mList.get(position).contains("http")) {
                            editRemove.editRemove(position);
                        }
                    }
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
                        if (isAddPic) {
                            Intent intent = new Intent(context, AddPictruActivity.class);
                            intent.putStringArrayListExtra(AddPictruActivity.PHOTO_LIST, mList);
                            ((Activity) context).startActivityForResult(intent, type);
                        } else {
                            Intent intent = new Intent(context, TakePhotoActivity.class);
                            intent.putStringArrayListExtra(TakePhotoActivity.PHOTO_LIST, mList);
                            ((Activity) context).startActivityForResult(intent, type);
                        }
                    }
                });
//                if ( mAllImageWidth > 0 ){
//                    FrameLayout.LayoutParams addParams = new FrameLayout.LayoutParams( mAllImageWidth/4, mAllImageWidth/4);
//                    addParams.topMargin = DensityUtil.dp2px(  5 );
//                    addParams.leftMargin = DensityUtil.dp2px(  1 );
//                    ((AddViewHolder)holder).itemAddIv.setLayoutParams( addParams );
//                }


            } else {
                ((AddViewHolder) holder).itemAddIv.setVisibility(View.GONE);
            }

        }


    }

//    @Override
//    public void onBindViewHolder(RecyclerView.ViewHodler holder, int position) {
//
//    }

//    @Override
//    public void onBindViewHolder(ViewHodler holder, final int position) {
//        if (mList.get(position).equals(ADD)){
//            holder.itemTv.setImageDrawable(context.getResources().getDrawable(R.mipmap.circle_addimg));
//            holder.deleteTv.setVisibility(View.GONE);
//        }else{
//            ImageLoader.loadLocalImg(mList.get(position),holder.itemTv);
//            holder.deleteTv.setVisibility(View.VISIBLE);
//        }
//        holder.itemFl.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (mList.get(position).equals(ADD)){
//                    ArrayList<String> list = new ArrayList<String>();
//                    for (String item : mList){
//                        if (!item.equals(ADD)){
//                            list.add(item);
//                        }
//                    }
//                    if (isAddPic){
//                        Intent intent = new Intent(context, AddPictruActivity.class);
//                        intent.putStringArrayListExtra(AddPictruActivity.PHOTO_LIST,list);
//                        ((Activity)context).startActivityForResult(intent, type);
//                    }else{
//                        Intent intent = new Intent(context, TakePhotoActivity.class);
//                        intent.putStringArrayListExtra(TakePhotoActivity.PHOTO_LIST,list);
//                        ((Activity)context).startActivityForResult(intent, type);
//                    }
//                }
//            }
//        });
//        holder.deleteTv.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                mExplosionField.explode(v);
//                mList.remove(position);
//                if (mList.size() < 8 && !mList.contains(ADD)){
//                    mList.add(ADD);
//                }
//                notifyDataSetChanged();
//            }
//        });
////    }


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


    public void setIsAddPic(boolean isAddPic) {
        this.isAddPic = isAddPic;
    }

    private EditRemove editRemove;

    public void setEditRemove(EditRemove editRemove) {
        this.editRemove = editRemove;
    }

    public interface EditRemove {
        void editRemove(int postion);
    }


}
