package com.xinniu.android.qiqueqiao;

import java.util.Map;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.AbstractDaoSession;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.identityscope.IdentityScopeType;
import org.greenrobot.greendao.internal.DaoConfig;

import com.xinniu.android.qiqueqiao.bean.GroupBean;
import com.xinniu.android.qiqueqiao.bean.OtherUserInfo;

import com.xinniu.android.qiqueqiao.GroupBeanDao;
import com.xinniu.android.qiqueqiao.OtherUserInfoDao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * {@inheritDoc}
 * 
 * @see org.greenrobot.greendao.AbstractDaoSession
 */
public class DaoSession extends AbstractDaoSession {

    private final DaoConfig groupBeanDaoConfig;
    private final DaoConfig otherUserInfoDaoConfig;

    private final GroupBeanDao groupBeanDao;
    private final OtherUserInfoDao otherUserInfoDao;

    public DaoSession(Database db, IdentityScopeType type, Map<Class<? extends AbstractDao<?, ?>>, DaoConfig>
            daoConfigMap) {
        super(db);

        groupBeanDaoConfig = daoConfigMap.get(GroupBeanDao.class).clone();
        groupBeanDaoConfig.initIdentityScope(type);

        otherUserInfoDaoConfig = daoConfigMap.get(OtherUserInfoDao.class).clone();
        otherUserInfoDaoConfig.initIdentityScope(type);

        groupBeanDao = new GroupBeanDao(groupBeanDaoConfig, this);
        otherUserInfoDao = new OtherUserInfoDao(otherUserInfoDaoConfig, this);

        registerDao(GroupBean.class, groupBeanDao);
        registerDao(OtherUserInfo.class, otherUserInfoDao);
    }
    
    public void clear() {
        groupBeanDaoConfig.clearIdentityScope();
        otherUserInfoDaoConfig.clearIdentityScope();
    }

    public GroupBeanDao getGroupBeanDao() {
        return groupBeanDao;
    }

    public OtherUserInfoDao getOtherUserInfoDao() {
        return otherUserInfoDao;
    }

}