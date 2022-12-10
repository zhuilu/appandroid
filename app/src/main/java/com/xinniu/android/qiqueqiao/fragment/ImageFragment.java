package com.xinniu.android.qiqueqiao.fragment;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatDialog;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;

import com.bumptech.glide.request.FutureTarget;

import com.bumptech.glide.request.RequestListener;

import com.bumptech.glide.request.target.Target;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.xinniu.android.qiqueqiao.R;
import com.xinniu.android.qiqueqiao.activity.MyPushActivity;
import com.xinniu.android.qiqueqiao.base.LazyBaseFragment;

import com.xinniu.android.qiqueqiao.utils.ImageLoader;
import com.xinniu.android.qiqueqiao.utils.ShareUtils;
import com.xinniu.android.qiqueqiao.utils.ToastUtils;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import retrofit2.Call;



public class ImageFragment extends LazyBaseFragment {
    @BindView(R.id.image)
    ImageView imageView;
    public final static String IMAGE_URL_TAG = "IMAGE_URL_TAG";
    String url;
    private String imgUrl;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_image;
    }

    public static ImageFragment newInstance(String url) {

        Bundle args = new Bundle();
        args.putString(IMAGE_URL_TAG, url);
        ImageFragment fragment = new ImageFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        url = getArguments().getString(IMAGE_URL_TAG);
//        Glide.with(mContext).asBitmap().load(TextUtils.isEmpty(url)?"http:":url).listener(new RequestListener<String, Bitmap>() {
//            @Override
//            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<String> target, boolean isFirstResource) {
//                showBookingToast(2);
//                return false;
//            }
//
//            @Override
//            public boolean onResourceReady(String resource, Object model, Target<String> target, DataSource dataSource, boolean isFirstResource) {
//                dismissBookingToast();
//                return false;
//            }
//
//
//        }).into(imageView);


        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setOnClickLister.setFinish();
            }
        });
        imageView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                downloadImage(url);
                if (!TextUtils.isEmpty(imgUrl)) {
                    setOnClickLister.setLong(imgUrl);
                }
                return true;
            }
        });


    }

    @Override
    protected void lazyLoad() {

    }
    public interface setOnClickLister{
        void setFinish();
        void setLong(String url);
    }

    private setOnClickLister setOnClickLister;

    public void setSetOnClickLister(ImageFragment.setOnClickLister setOnClickLister) {
        this.setOnClickLister = setOnClickLister;
    }
    public void downloadImage(final String url) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    FutureTarget<File> target = Glide.with(mContext)
                            .load(url)
                            .downloadOnly(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL);
                    final File imageFile = target.get();
                    imgUrl = imageFile.getPath();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

}
