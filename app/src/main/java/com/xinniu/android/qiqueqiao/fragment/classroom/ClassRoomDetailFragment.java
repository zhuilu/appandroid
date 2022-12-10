package com.xinniu.android.qiqueqiao.fragment.classroom;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.xinniu.android.qiqueqiao.R;
import com.xinniu.android.qiqueqiao.activity.LoginNewActivity;
import com.xinniu.android.qiqueqiao.activity.PersonCentetActivity;
import com.xinniu.android.qiqueqiao.adapter.CoopDetailPhotoAdapter;
import com.xinniu.android.qiqueqiao.adapter.IndexNewTwoAdapter;
import com.xinniu.android.qiqueqiao.base.LazyBaseFragment;
import com.xinniu.android.qiqueqiao.bean.ClassRoomDetailBean;
import com.xinniu.android.qiqueqiao.request.RequestManager;
import com.xinniu.android.qiqueqiao.request.callback.VideoDetailCallback;
import com.xinniu.android.qiqueqiao.user.UserInfoHelper;
import com.xinniu.android.qiqueqiao.utils.FullyLinearLayoutManager;
import com.xinniu.android.qiqueqiao.utils.ImageLoader;
import com.xinniu.android.qiqueqiao.utils.NoScrollRecyclerView;
import com.xinniu.android.qiqueqiao.utils.RoundImageView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class ClassRoomDetailFragment extends LazyBaseFragment {
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_tag_1)
    TextView tvTag1;
    @BindView(R.id.tv_tag_2)
    TextView tvTag2;
    @BindView(R.id.tv_tag_3)
    TextView tvTag3;
    @BindView(R.id.mcoop_detail_companyImg)
    RoundImageView mcoopDetailCompanyImg;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_jiabing_detail)
    TextView tvJiabingDetail;
    @BindView(R.id.tv_detail)
    TextView tvDetail;
    @BindView(R.id.tv)
    TextView tv;
    @BindView(R.id.photo_recycler)
    RecyclerView photoRecycler;
    @BindView(R.id.mcompany_resource_list)
    NoScrollRecyclerView mcompanyResourceList;
    @BindView(R.id.mcompany_infoImg)
    RelativeLayout mcompanyInfoImg;
    @BindView(R.id.mscrollview)
    NestedScrollView mscrollview;
    @BindView(R.id.llayout_resource)
    LinearLayout llayoutResource;
    @BindView(R.id.llayout_person)
    LinearLayout llayoutPerson;
    private int userId;

    private IndexNewTwoAdapter indexNewAdapter;
    private List<ClassRoomDetailBean.ResourcesListBean> datas = new ArrayList<ClassRoomDetailBean.ResourcesListBean>();
    List<String> photoList = new ArrayList<>();
    private int id;


    public static ClassRoomDetailFragment newInstance(int mId) {

        Bundle args = new Bundle();
        args.putInt("id", mId);

        ClassRoomDetailFragment fragment = new ClassRoomDetailFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_classroom_detail;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        if (getArguments() != null) {
            id = getArguments().getInt("id");
        }

        FullyLinearLayoutManager manager = new FullyLinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        mcompanyResourceList.setLayoutManager(manager);
        indexNewAdapter = new IndexNewTwoAdapter((AppCompatActivity) getActivity(), R.layout.item_index_new, datas, 1);
        mcompanyResourceList.setAdapter(indexNewAdapter);

        getDetail();

    }

    @Override
    protected void lazyLoad() {

    }

    private void getDetail() {
        RequestManager.getInstance().getVedioDetail(id, new VideoDetailCallback() {
            @Override
            public void onSuccess(ClassRoomDetailBean item) {
                tvTitle.setText(item.getTitle());
                datas.clear();
                if (item.getResources_list().size() == 0) {
                    llayoutResource.setVisibility(View.GONE);
                } else {
                    llayoutResource.setVisibility(View.VISIBLE);
                    datas.addAll(item.getResources_list());
                    indexNewAdapter.notifyDataSetChanged();
                }
                if (item.getUser_id() != null && item.getUser_id() > 0) {
                    userId = item.getUser_id();
                    llayoutPerson.setVisibility(View.VISIBLE);
                    ImageLoader.loadLocalImg(item.getHead_pic(), mcoopDetailCompanyImg);
                    tvName.setText(item.getRealname() + " · " + item.getCompany() + item.getPosition());
                    tvJiabingDetail.setText(item.getCharacter_introduce());
                } else {
                    llayoutPerson.setVisibility(View.GONE);
                }
                if (item.getCategory().size() > 0) {
                    if (item.getCategory().size() == 3) {
                        tvTag1.setVisibility(View.VISIBLE);
                        tvTag2.setVisibility(View.VISIBLE);
                        tvTag3.setVisibility(View.VISIBLE);
                        tvTag1.setText(item.getCategory().get(0).getName());
                        tvTag2.setText(item.getCategory().get(1).getName());
                        tvTag3.setText(item.getCategory().get(2).getName());
                    } else if (item.getCategory().size() == 2) {
                        tvTag1.setVisibility(View.VISIBLE);
                        tvTag2.setVisibility(View.VISIBLE);
                        tvTag3.setVisibility(View.GONE);
                        tvTag1.setText(item.getCategory().get(0).getName());
                        tvTag2.setText(item.getCategory().get(1).getName());
                    } else if (item.getCategory().size() == 1) {
                        tvTag1.setVisibility(View.VISIBLE);
                        tvTag2.setVisibility(View.GONE);
                        tvTag3.setVisibility(View.GONE);
                        tvTag1.setText(item.getCategory().get(0).getName());

                    }
                } else {
                    tvTag1.setVisibility(View.GONE);
                    tvTag2.setVisibility(View.GONE);
                    tvTag3.setVisibility(View.GONE);

                }
                tvDetail.setText(item.getIntroduce());
                if (!TextUtils.isEmpty(item.getImages())) {
                    photoRecycler.setVisibility(View.VISIBLE);
                    tv.setVisibility(View.VISIBLE);
                    String[] photoArray = item.getImages().split(",");

                    for (int i = 0; i < photoArray.length; i++) {
                        photoList.add(photoArray[i]);
                    }

                    LinearLayoutManager manager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
                    photoRecycler.setLayoutManager(manager);

                    CoopDetailPhotoAdapter photoAdapter = new CoopDetailPhotoAdapter(R.layout.item_recycler_photo_two, photoList, getActivity());
                    photoRecycler.setAdapter(photoAdapter);


                } else {
                    photoRecycler.setVisibility(View.GONE);
                    tv.setVisibility(View.GONE);
                }


                dismissBookingToast();
            }

            @Override
            public void onFailue(int code, String msg) {
                dismissBookingToast();
            }
        });
    }


    @OnClick(R.id.llayout_person)
    public void onClick() {
        if (!isLogin()) {
            LoginNewActivity.start(mContext);
            return;
        }
        PersonCentetActivity.start(getActivity(), userId + "", true);
    }
    private boolean isLogin() {
        if (!UserInfoHelper.getIntance().isLogin()) {
            // TODO: 2017/12/20
//            ToastUtils.showCentetImgToast(getContext(), "还未登录");
            LoginNewActivity.start(mContext);
        }
        return UserInfoHelper.getIntance().isLogin();
    }

}
