package com.xinniu.android.qiqueqiao.adapter.base;

import android.view.View;
import android.view.ViewGroup;

public interface BDOnItemClickListener<T>
{
    void onItemClick(ViewGroup parent, View view, T t, int position);
    boolean onItemLongClick(ViewGroup parent, View view, T t, int position);
}