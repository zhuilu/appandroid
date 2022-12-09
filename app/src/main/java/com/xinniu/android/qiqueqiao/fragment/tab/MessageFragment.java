package com.xinniu.android.qiqueqiao.fragment.tab;

import android.os.Bundle;
//import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.TextView;

import com.xinniu.android.qiqueqiao.R;
import com.xinniu.android.qiqueqiao.base.LazyBaseFragment;
import com.xinniu.android.qiqueqiao.fragment.message.InteractFragment;
import com.xinniu.android.qiqueqiao.fragment.message.TalkListFragment;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by BDXK on 2017/11/7.
 * 目前已废弃 使用TalkListFragment
 */

public class MessageFragment extends LazyBaseFragment implements View.OnClickListener{
    @BindView(R.id.message_chat)
    TextView talkListTv;
    @BindView(R.id.message_interact)
    TextView interactTv;
    TalkListFragment talkListFragment;
    InteractFragment interactFragment;

    public static MessageFragment newInstance() {
        
        Bundle args = new Bundle();
        
        MessageFragment fragment = new MessageFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_message;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        talkListTv.setSelected(true);
        interactTv.setSelected(false);
        talkListFragment = new TalkListFragment();
        interactFragment = new InteractFragment();
        FragmentTransaction fTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fTransaction.add(R.id.child_fragment, talkListFragment, "chat_list");
        fTransaction.add(R.id.child_fragment, interactFragment, "interact");
        fTransaction.hide(interactFragment);
        fTransaction.show(talkListFragment);
        fTransaction.commit();
//        if (talkListFragment!=null) {
//            talkListFragment.initView();
//        }
    }

    @Override
    protected void lazyLoad() {
        if (talkListFragment!=null) {
            talkListFragment.initView();
        }
    }

    @OnClick({R.id.message_chat,R.id.message_interact})
    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.message_chat){
            talkListTv.setSelected(true);
            interactTv.setSelected(false);
            getActivity().getSupportFragmentManager().beginTransaction().hide(interactFragment).show(talkListFragment).commit();
        }
        if (id == R.id.message_interact){
            talkListTv.setSelected(false);
            interactTv.setSelected(true);
            getActivity().getSupportFragmentManager().beginTransaction().hide(talkListFragment).show(interactFragment).commit();
            if (talkListFragment!=null) {
                talkListFragment.initView();
            }
        }
    }
    public void srcollToUnReadMsg(){
        if (talkListFragment!=null) {
            talkListFragment.srcollToUnReadMsg();
        }
    }

    public void moveToTalkListFragment() {
        if (talkListTv != null) {
            talkListTv.setSelected(true);
        }
        if (interactTv != null) {
            interactTv.setSelected(false);
        }
        if (getActivity()!=null) {
            if (getActivity().getSupportFragmentManager() != null) {
                getActivity().getSupportFragmentManager().beginTransaction().hide(interactFragment).show(talkListFragment).commit();

            }
        }
    }
    public void setNumFragment(String systemnum,String intactnum){
        if (talkListFragment!=null){
            talkListFragment.setNumFragment(systemnum,intactnum);
        }

    }





}
