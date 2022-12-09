package com.xinniu.android.qiqueqiao.utils.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.xinniu.android.qiqueqiao.DaoMaster;
import com.xinniu.android.qiqueqiao.GroupBeanDao;
import com.xinniu.android.qiqueqiao.OtherUserInfoDao;

import org.greenrobot.greendao.database.Database;

/**
 * Created by yuchance on 2018/6/12.
 */

public class MySQLiteOpenHelper extends DaoMaster.OpenHelper {
    public MySQLiteOpenHelper(Context context, String name) {
        super(context, name);
    }

    public MySQLiteOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory) {
        super(context, name, factory);
    }
//    @Override
//    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
//        MigrationHelper.getInstance().migrate(db, OtherUserInfoDao.class);//数据版本变更才会执行
//    }

    @Override
    public void onUpgrade(Database db, int oldVersion, int newVersion) {
        MigrationHelper.getInstance().migrate(db, OtherUserInfoDao.class);//数据版本变更才会执行
    }
}
