package com.xinniu.android.qiqueqiao.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.xinniu.android.qiqueqiao.R;
import com.xinniu.android.qiqueqiao.base.BaseActivity;
import com.xinniu.android.qiqueqiao.customs.ClearEditText;
import com.xinniu.android.qiqueqiao.request.RequestManager;
import com.xinniu.android.qiqueqiao.request.callback.AllResultDoCallback;
import com.xinniu.android.qiqueqiao.utils.ShowUtils;
import com.xinniu.android.qiqueqiao.utils.ToastUtils;

import butterknife.BindView;
import butterknife.OnClick;

//import android.support.v4.content.ContextCompat;

/**
 * Created by yuchance on 2018/10/15.
 */

public class EditGroupActivity extends BaseActivity {
    @BindView(R.id.medit_title)
    TextView meditTitle;
    @BindView(R.id.mright_go)
    TextView mrightGo;
    @BindView(R.id.mapply_fored)
    EditText mapplyFored;
    @BindView(R.id.mapply_forRl)
    RelativeLayout mapplyForRl;
    @BindView(R.id.mgroup_nameEt)
    ClearEditText mgroupNameEt;
    @BindView(R.id.mgroup_nameRl)
    RelativeLayout mgroupNameRl;
    @BindView(R.id.mgroup_introEd)
    EditText mgroupIntroEd;
    @BindView(R.id.mgroup_introRl)
    RelativeLayout mgroupIntroRl;
    @BindView(R.id.mgroup_announcementEd)
    EditText mgroupAnnouncementEd;
    @BindView(R.id.mgroup_announcementRl)
    RelativeLayout mgroupAnnouncementRl;
    @BindView(R.id.bt_finishImg)
    ImageView btFinishImg;
    @BindView(R.id.bt_finishTv)
    TextView btFinishTv;
    private String from;
    private int type;
    private long groupId;
    private String name;
    private Intent intent;
    private String content = "";

    @Override
    public int getLayoutId() {
        return R.layout.activity_edit_group;
    }

    public static void start(Context context, String from,long groupId,String name) {
        Intent intent = new Intent(context, EditGroupActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("from", from);
        bundle.putLong("groupId",groupId);
        bundle.putString("name",name);
        intent.putExtras(bundle);
        context.startActivity(intent, bundle);
    }
    public static void start(AppCompatActivity context, String from, int groupId, String groupName) {
        Intent intent = new Intent(context, EditGroupActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("from", from);
        bundle.putInt("groupId",groupId);
        bundle.putString("groupName",groupName);
        intent.putExtras(bundle);
        context.startActivityForResult(intent,501,bundle);
    }
    public static void start(AppCompatActivity context, String from, String groupIntro) {
        Intent intent = new Intent(context, EditGroupActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("from", from);
        bundle.putString("groupIntro",groupIntro);
        intent.putExtras(bundle);
        context.startActivityForResult(intent,601,bundle);
    }
    public static void start(AppCompatActivity context, String from, int groupId, String name, String content){
        Intent intent = new Intent(context,EditGroupActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("from",from);
        bundle.putInt("groupId",groupId);
        bundle.putString("name",name);
        bundle.putString("content",content);
        intent.putExtras(bundle);
        context.startActivityForResult(intent,701,bundle);


    }






    @Override
    public void initViews(Bundle savedInstanceState) {topStatusBar(true);
        intent = getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle != null) {

            from = bundle.getString("from");
            if (from.equals("apply")) {
                meditTitle.setText("申请加群");
                mrightGo.setText("提交");
                groupId = bundle.getLong("groupId");
                name = bundle.getString("name");
                ShowUtils.showViewVisible(mapplyForRl,View.VISIBLE);

                ShowUtils.showViewVisible(mgroupNameRl,View.GONE);

                ShowUtils.showViewVisible(mgroupAnnouncementRl,View.GONE);

                ShowUtils.showViewVisible(mgroupIntroRl,View.GONE);

                ShowUtils.showViewVisible(btFinishImg,View.VISIBLE);

                ShowUtils.showViewVisible(btFinishTv,View.GONE);

            } else if (from.equals("groupname")) {
                meditTitle.setText("群组名称");
                mrightGo.setText("完成");
                String groupName = bundle.getString("groupName");
                ShowUtils.showViewVisible(mapplyForRl,View.GONE);

                ShowUtils.showViewVisible(mgroupNameRl,View.VISIBLE);

                ShowUtils.showViewVisible(mgroupAnnouncementRl,View.GONE);

                ShowUtils.showViewVisible(mgroupIntroRl,View.GONE);

                ShowUtils.showViewVisible(btFinishImg,View.VISIBLE);

                ShowUtils.showViewVisible(btFinishTv,View.GONE);
                ShowUtils.showTextPerfect(mgroupNameEt,groupName);
            } else if (from.equals("groupintro")) {
                meditTitle.setText("群介绍");
                mrightGo.setText("完成");
                String groupIntro = bundle.getString("groupIntro");
                ShowUtils.showViewVisible(mapplyForRl,View.GONE);

                ShowUtils.showViewVisible(mgroupNameRl,View.GONE);

                ShowUtils.showViewVisible(mgroupAnnouncementRl,View.GONE);

                ShowUtils.showViewVisible(mgroupIntroRl,View.VISIBLE);

                ShowUtils.showViewVisible(btFinishImg,View.VISIBLE);

                ShowUtils.showViewVisible(btFinishTv,View.GONE);
                ShowUtils.showTextPerfect(mgroupIntroEd,groupIntro);
            } else if (from.equals("groupannounce")) {
                meditTitle.setText("群组公告");
                mrightGo.setText("完成");
                groupId = bundle.getInt("groupId");
                name = bundle.getString("name");
                content = bundle.getString("content");
                if (!TextUtils.isEmpty(content)){
                    mgroupAnnouncementEd.setText(content);
                }
                ShowUtils.showViewVisible(mapplyForRl,View.GONE);

                ShowUtils.showViewVisible(mgroupNameRl,View.GONE);

                ShowUtils.showViewVisible(mgroupAnnouncementRl,View.VISIBLE);

                ShowUtils.showViewVisible(mgroupIntroRl,View.GONE);

                ShowUtils.showViewVisible(btFinishImg,View.GONE);

                ShowUtils.showViewVisible(btFinishTv,View.VISIBLE);
                mrightGo.setTextColor(ContextCompat.getColor(EditGroupActivity.this,R.color.text_color_search));
                mrightGo.setClickable(false);

                mgroupAnnouncementEd.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {

                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                        if (content==null){
                            content = "";
                        }
                        if (!content.equals(mgroupAnnouncementEd.getText().toString())){
                            mrightGo.setTextColor(ContextCompat.getColor(EditGroupActivity.this,R.color.text_color_1));
                            mrightGo.setClickable(true);
                        }else {
                            mrightGo.setTextColor(ContextCompat.getColor(EditGroupActivity.this,R.color.text_color_search));
                            mrightGo.setClickable(false);
                        }
                    }
                });


            }
        }





    }


    @OnClick({R.id.bt_finish, R.id.mright_go})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bt_finish:
                finish();
                break;
            case R.id.mright_go:
                commitData();
                break;
            default:
                break;
        }
    }

    private void commitData() {
        if (from.equals("apply")){
            applyGroup();
        }else if (from.equals("groupname")){
            String groupName = mgroupNameEt.getText().toString();
            intent.putExtra("groupName",groupName);
            setResult(502,intent);
            finish();
        }else if (from.equals("groupintro")){
            String groupIntro = mgroupIntroEd.getText().toString();
            if (groupIntro.length()>15&&groupIntro.length()<500) {
                intent.putExtra("groupIntro", groupIntro);
                setResult(602, intent);
                finish();
            }else {
                ToastUtils.showCentetToast(EditGroupActivity.this,"群介绍为15字到500字之间");
            }
        }else if (from.equals("groupannounce")){
            final int editType;
            AlertDialog.Builder builder;
            if (TextUtils.isEmpty(mgroupAnnouncementEd.getText().toString())){
                editType = 2;
            }else {
                editType = 1;
            }
            if (editType == 2){
                builder = new AlertDialog.Builder(EditGroupActivity.this);
                builder.setTitle("确定清空群公告?");
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        changeAnnounce(editType);
                    }
                }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.show();

            }else {
                builder = new AlertDialog.Builder(EditGroupActivity.this);
                builder.setTitle("该公告会通知全部群成员,是否发布?");
                builder.setPositiveButton("发布", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        changeAnnounce(editType);
                    }
                }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.show();

            }


        }
    }

    private void changeAnnounce(int editType) {

        RequestManager.getInstance().editNotice(groupId, mgroupAnnouncementEd.getText().toString(), name, editType, new AllResultDoCallback() {
            @Override
            public void onSuccess(String msg) {
                ToastUtils.showCentetToast(EditGroupActivity.this,msg);
                setResult(702);
                finish();
            }

            @Override
            public void onFailed(int code, String msg) {
                ToastUtils.showCentetToast(EditGroupActivity.this,msg);
            }
        });
        }

    private void applyGroup() {
        RequestManager.getInstance().applyGroup(groupId, name, 2, mapplyFored.getText().toString(), new AllResultDoCallback() {
            @Override
            public void onSuccess(String msg) {
                ToastUtils.showCentetToast(EditGroupActivity.this,msg);
                finish();
            }

            @Override
            public void onFailed(int code, String msg) {
                ToastUtils.showCentetToast(EditGroupActivity.this,msg);
                finish();
            }
        });
    }

}
