package com.xinniu.android.qiqueqiao.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
//import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.xinniu.android.qiqueqiao.R;
import com.xinniu.android.qiqueqiao.adapter.SimpleTextAdapter;
import com.xinniu.android.qiqueqiao.base.BaseActivity;
import com.xinniu.android.qiqueqiao.bean.LLSimpleTextBean;
import com.xinniu.android.qiqueqiao.customs.qldialog.InputCommentLanguageDialog;
import com.xinniu.android.qiqueqiao.divider.DividerItemDecoration;
import com.xinniu.android.qiqueqiao.divider.OnRecyclerItemClickListener;
import com.xinniu.android.qiqueqiao.divider.SimpleItemTouchHelperCallback;
import com.xinniu.android.qiqueqiao.request.RequestManager;
import com.xinniu.android.qiqueqiao.request.callback.GetCommlySentenceCallback;
import com.xinniu.android.qiqueqiao.user.UserInfoHelper;
import com.xinniu.android.qiqueqiao.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by BDXK on 2019/3/11.
 * project : newbridge--- android xs
 */

public class CommentLanguageActivity extends BaseActivity {
    @BindView(R.id.bt_finish)
    RelativeLayout btFinish;
    @BindView(R.id.tv_save)
    TextView tvSave;
    @BindView(R.id.tool_bar)
    RelativeLayout toolBar;
    @BindView(R.id.myListView)
    RecyclerView myListView;
    @BindView(R.id.tv_add)
    TextView tvAdd;
    List<LLSimpleTextBean> mList;
    SimpleTextAdapter adapter;
    ItemTouchHelper.Callback callback;
    private boolean canDrag = false;
    ItemTouchHelper touchHelper;

    @Override
    public int getLayoutId() {
        return R.layout.activity_comment_language;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        topStatusBar(true);
        mList = new ArrayList<LLSimpleTextBean>();//?????????
        adapter = new SimpleTextAdapter(this, mList);
        myListView.setLayoutManager(new LinearLayoutManager(this));
        //  myListView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        myListView.setAdapter(adapter);
        //????????????Callback
        callback = new SimpleItemTouchHelperCallback(adapter);
        //???Callback??????ItemtouchHelper
        touchHelper = new ItemTouchHelper(callback);
        //??????ItemTouchHelper???attachToRecyclerView??????????????????
        touchHelper.attachToRecyclerView(myListView);
        myListView.addOnItemTouchListener(new OnRecyclerItemClickListener(myListView) {
            @Override
            public void onItemClick(RecyclerView.ViewHolder vh) {

            }

            @Override
            public void onItemLongClick(RecyclerView.ViewHolder vh) {
                //??????????????????????????????????????????????????????????????????
                if (canDrag) {
                    touchHelper.startDrag(vh);

                }
            }
        });
        adapter.setmItemSelect(new SimpleTextAdapter.ItemSelect() {
            @Override
            public void itemSelect(LLSimpleTextBean selectCategories, final int position, String type, SimpleTextAdapter.ViewHolder viewHolder) {
                if (type.equals("??????")) {
                    mList.remove(position);
                    adapter.notifyDataSetChanged();
                    Gson gson = new Gson();//???????????????
                    String json = gson.toJson(mList);
                    UserInfoHelper.getIntance().setCL(json);

                } else if (type.equals("??????")) {

                        //??????
                        InputCommentLanguageDialog inputCommentLanguageDialog = new InputCommentLanguageDialog(CommentLanguageActivity.this, R.style.QLDialog1,selectCategories.getText().toString());
                        inputCommentLanguageDialog.setmShareCallback(new InputCommentLanguageDialog.SaveCallback() {
                            @Override
                            public void onSaveCall(String str) {
                                mList.get(position).setText(str);
                                Gson gson = new Gson();
                                String json = gson.toJson(mList);
                                UserInfoHelper.getIntance().setCL(json);
                                adapter.notifyDataSetChanged();
                            }
                        });
                        inputCommentLanguageDialog.show();

                }

            }
        });
        if (UserInfoHelper.getIntance().getClList().length() > 0) {
            //???????????????
            String json = UserInfoHelper.getIntance().getClList();
            Gson gson = new Gson();
            List<LLSimpleTextBean> list = gson.fromJson(json, new TypeToken<List<LLSimpleTextBean>>() {
            }.getType());
            mList.clear();
            mList.addAll(list);
            adapter.notifyDataSetChanged();
        } else {
            RequestManager.getInstance().getCommonlySentenceList(new GetCommlySentenceCallback() {
                @Override
                public void onSuccess(List<LLSimpleTextBean> list) {
                    Gson gson = new Gson();
                    String json = gson.toJson(list);
                    UserInfoHelper.getIntance().setCL(json);
                    mList.clear();
                    mList.addAll(list);
                    adapter.notifyDataSetChanged();
                }

                @Override
                public void onFailed(int code, String msg) {
                    ToastUtils.showCentetToast(CommentLanguageActivity.this, msg);
                }
            });
        }
    }


    @OnClick({R.id.bt_finish, R.id.tv_save, R.id.tv_add})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bt_finish:
                finish();
                break;
            case R.id.tv_save:
                //??????
                if (tvSave.getText().toString().equals("??????")) {
                    tvSave.setText("??????");
                    canDrag = true;
                    adapter.changetShowDelImage(true);
                } else {
                    tvSave.setText("??????");
                    canDrag = false;
                    adapter.changetShowDelImage(false);
                }
                break;
            case R.id.tv_add:
                //???????????????
                //??????
                InputCommentLanguageDialog inputCommentLanguageDialog = new InputCommentLanguageDialog(CommentLanguageActivity.this, R.style.QLDialog1,"");
                inputCommentLanguageDialog.setmShareCallback(new InputCommentLanguageDialog.SaveCallback() {
                    @Override
                    public void onSaveCall(String str) {

                        LLSimpleTextBean llSimpleTextBean = new LLSimpleTextBean(str);
                        mList.add(0, llSimpleTextBean);
                        Gson gson = new Gson();
                        String json = gson.toJson(mList);
                        UserInfoHelper.getIntance().setCL(json);
                        adapter.notifyDataSetChanged();
                    }
                });
                inputCommentLanguageDialog.show();
                break;
        }
    }


}






