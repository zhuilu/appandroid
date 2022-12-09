package com.xinniu.android.qiqueqiao.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
//import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.scwang.smartrefresh.layout.util.DensityUtil;
import com.xinniu.android.qiqueqiao.R;
import com.xinniu.android.qiqueqiao.activity.AddPictruActivity;
import com.xinniu.android.qiqueqiao.activity.TakePhotoActivity;
import com.xinniu.android.qiqueqiao.utils.ImageLoader;
import com.xinniu.android.qiqueqiao.widget.ExplosionField;

import java.util.ArrayList;

/**
 * Created by lzq on 2018/1/17.
 */

public class EditResouceTwoAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

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


    public EditResouceTwoAdapter(Context context, ArrayList<String> list, int type) {
        mExplosionField = ExplosionField.attach2Window((Activity) context);
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

    public EditResouceTwoAdapter(Context context, ArrayList<String> list, int type, int max) {
        mExplosionField = ExplosionField.attach2Window((Activity) context);
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
            View view = inflater.inflate(R.layout.item_addimg_two, parent, false);
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
                FrameLayout.LayoutParams linearParams = (FrameLayout.LayoutParams) ((ViewHodler) holder).tv.getLayoutParams(); //取控件textView当前的布局参数 linearParams.height = 20;// 控件的高强制设成20

                linearParams.width = mAllImageWidth / 4;

                ((ViewHodler) holder).tv.setLayoutParams(linearParams); //使设置好的布局参数应用到控件
            }
            if (position == 0) {
                ((ViewHodler) holder).tv.setVisibility(View.VISIBLE);
            } else {
                ((ViewHodler) holder).tv.setVisibility(View.GONE);
            }
            ((ViewHodler) holder).deleteTv.setVisibility(View.VISIBLE);

            ((ViewHodler) holder).deleteTv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mExplosionField.explode(v);
                    if (THEEDIT.equals("theEdit")) {
                        if (mList.get(position).contains("http")) {
                            editRemove.editRemove(position,1);
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

                            editRemove.editRemove(position, 2);

//                        if (isAddPic) {
//                            Intent intent = new Intent(context, AddPictruActivity.class);
//                            intent.putStringArrayListExtra(AddPictruActivity.PHOTO_LIST, mList);
//                            ((Activity) context).startActivityForResult(intent, type);
//                        } else {
//                            Intent intent = new Intent(context, TakePhotoActivity.class);
//                            intent.putStringArrayListExtra(TakePhotoActivity.PHOTO_LIST, mList);
//                            ((Activity) context).startActivityForResult(intent, type);
//                        }
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
        TextView tv;

        public ViewHodler(View itemView) {
            super(itemView);
            itemTv = (ImageView) itemView.findViewById(R.id.item_iv);
            deleteTv = (ImageView) itemView.findViewById(R.id.delete_item);
            itemFl = (FrameLayout) itemView.findViewById(R.id.item_fl);
            tv = (TextView) itemView.findViewById(R.id.tv);
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
        void editRemove(int postion,int type);
    }


}
