package com.xinniu.android.qiqueqiao.adapter;

import android.content.Context;
import androidx.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xinniu.android.qiqueqiao.R;
import com.xinniu.android.qiqueqiao.activity.GroupMemberActivity;
import com.xinniu.android.qiqueqiao.activity.LoginNewActivity;
import com.xinniu.android.qiqueqiao.activity.PersonCentetActivity;
import com.xinniu.android.qiqueqiao.bean.MyCompanyBean;
import com.xinniu.android.qiqueqiao.user.UserInfoHelper;
import com.xinniu.android.qiqueqiao.utils.ImageLoader;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yuchance on 2018/5/8.
 */

public class CompanyInfoPhotoAdapter extends BaseMultiItemQuickAdapter<MyCompanyBean.UsersBean,BaseViewHolder>{
    private Context mContext;
    private List<MyCompanyBean.UsersBean> data = new ArrayList<>();
    private int userNum;
    private int id;
    public CompanyInfoPhotoAdapter(Context context, @Nullable List<MyCompanyBean.UsersBean> data,int userNum,int id) {
        super( data);
        this.mContext = context;
        this.data = data;
        this.id = id;
        this.userNum = userNum;
        addItemType(MyCompanyBean.UsersBean.TEXT, R.layout.item_company_infophoto);
        addItemType(MyCompanyBean.UsersBean.IMG, R.layout.member_view);
    }

    @Override
    protected void convert(BaseViewHolder helper, final MyCompanyBean.UsersBean item) {
        switch (helper.getItemViewType()) {
            case MyCompanyBean.UsersBean.TEXT:
                ImageLoader.loadAvter(item.getHead_pic(), (ImageView) helper.getView(R.id.mconpany_headImg));
                helper.setText(R.id.mcompany_people_name,item.getRealname());
                helper.setText(R.id.mcompany_people_position,item.getPosition());
                if (item.getIs_v()==1){
                    helper.setVisible(R.id.mcompany_isv,true);
                }else {
                    helper.setVisible(R.id.mcompany_isv,false);
                }
                (helper.getView(R.id.bcompany_headRl)).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (!UserInfoHelper.getIntance().isLogin()){
                            LoginNewActivity.start(mContext);
                            return;
                        }

                        PersonCentetActivity.start(mContext,item.getUser_id()+"");

                    }
                });
                break;
            case MyCompanyBean.UsersBean.IMG:
                helper.setText(R.id.mmember_num,userNum+"位成员");
                ImageLoader.loadAvter(data.get(0).getHead_pic(), (ImageView) helper.getView(R.id.mmemberhead1));
                ImageLoader.loadAvter(data.get(1).getHead_pic(), (ImageView) helper.getView(R.id.mmemberhead2));
                ImageLoader.loadAvter(data.get(2).getHead_pic(), (ImageView) helper.getView(R.id.mmemberhead3));
                helper.getView(R.id.moreRl).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        GroupMemberActivity.start(mContext, id, 0, GroupMemberActivity.TYPE_COMPANY);
                    }
                });
                break;
        }





    }
}
