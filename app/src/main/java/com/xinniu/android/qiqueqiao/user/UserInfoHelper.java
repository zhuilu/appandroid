package com.xinniu.android.qiqueqiao.user;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.xinniu.android.qiqueqiao.base.BaseApp;
import com.xinniu.android.qiqueqiao.bean.CenterBean;
import com.xinniu.android.qiqueqiao.bean.CircleBasicInfoBean;
import com.xinniu.android.qiqueqiao.bean.CircleBean;
import com.xinniu.android.qiqueqiao.bean.GroupBean;
import com.xinniu.android.qiqueqiao.bean.OtherUserInfo;
import com.xinniu.android.qiqueqiao.bean.UserInfoBean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.rong.imlib.model.Conversation;

/**
 * Created by BDXK on 2017/12/12.
 * project : xiqueqiao--- android xs
 */

public class UserInfoHelper {

    private static UserInfoHelper intance;
    private static SharedPreferences mSharedPreferences;
    public final static int IS_NEW_USER = 1;
    private Map<String, OtherUserInfo> map = new HashMap<>();
    private Map<String, GroupBean> mCirclemap = new HashMap<>();
    private List<Conversation> privateConversationList = new ArrayList<>();
    private CenterBean mDetailedUserInfoBean;
    public final static String USER_ID = "user_id";
    public final static String USER_TOKEN = "user_token";
    public final static String USER_RONG_TOKEN = "rong_user_token";
    public final static String USER_NAME = "user_name";
    public final static String IS_TALK_TO_KEFU = "IS_TALK_TO_KEFU";
    public final static String SEARCH_TAG = "SEARCH_TAG";
    public final static int MAX_SEARCH_TAG = 10;
    private String password;
    private boolean isPerfect;
    private UserInfoBean userInfoBean;
    public final static String QY_TOKEN = "qy_token";
    public final static String THE_F_ID = "f_id";
    public final static String HEAD_URL = "head_url";
    public final static String TIME_POKE = "time_poke";
    public final static String TIME_ACT = "time_act";
    public final static String ACT_ID = "act_id";
    public final static String TIME_WEEK_ACT = "time_week_act";
    public final static String TIME_KNOW_DO = "time_know_do";

    public final static String COMPANY = "company";

    public final static String POSITION = "position";

    public final static String IS_V = "is_v";

    public final static String NICKNAME = "nickname";

    public final static String REALNAME = "realname";


    public void setSearcHistory(List<String> list) {
        int size = 0;
        if (list.size() <= MAX_SEARCH_TAG) {
            size = list.size();
        } else {
            size = MAX_SEARCH_TAG;
        }
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        for (int i = 0; i < size; i++) {
            editor.putString("SEARCH_TAG" + i, list.get(i));
        }
        editor.apply();
    }

    public List<String> getSearchHistory() {
        List<String> list = new ArrayList<>();
        for (int i = 0; i < MAX_SEARCH_TAG; i++) {
            String item = mSharedPreferences.getString("SEARCH_TAG" + i, "");
            if (!TextUtils.isEmpty(item)) {
                list.add(item);
            }
        }
        return list;
    }

    /**
     * 设置引导页是否已经加载
     *
     * @param isFrist
     */
    public void setIsFrist(boolean isFrist) {
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putBoolean("ISFRIST", isFrist);
        editor.apply();
    }

    /**
     * 判断是否需要加载引导页
     *
     * @return
     */
    public boolean getIsFrist() {
        boolean isFrist = mSharedPreferences.getBoolean("ISFRIST", true);
        return isFrist;
    }

    //存入首页上部分数据
    public void setIndexBannerList(String json) {
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putString("indexTop", json);
        editor.apply();
    }

    //取出首页上部分数据
    public String getIndexBannerList() {
        String data = mSharedPreferences.getString("indexTop", "");
        return data;
    }

    //存入首页下部分数据
    public void setIndexList(String json) {
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putString("indexBottom", json);
        editor.apply();
    }

    //取出首页下部分数据
    public String getIndexList() {
        String data = mSharedPreferences.getString("indexBottom", "");
        return data;
    }


    //存入新首页数据
    public void setIndexNewList(String json) {
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putString("indexNew", json);
        editor.apply();
    }

    //取出新首页数据
    public String getIndexNewList() {
        String data = mSharedPreferences.getString("indexNew", "");
        return data;
    }

    //存入常用语数据
    public void setCL(String json) {
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putString("cl", json);
        editor.apply();
    }

    //取出常用语数据
    public String getClList() {
        String data = mSharedPreferences.getString("cl", "");
        return data;
    }


    public void clearHistory() {
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        for (int i = 0; i < MAX_SEARCH_TAG; i++) {
            editor.putString("SEARCH_TAG" + i, "");
        }
        editor.apply();
    }

    public String getPassword() {
        if (password == null) {
            return "";
        }
        return password;
    }

    public void setPassword(String password) {
        if (password == null) {
            this.password = "";
            return;
        }
        this.password = password;
    }


    public List<Conversation> getPrivateConversationList() {
        return privateConversationList;
    }

    public void setPrivateConversationList(List<Conversation> privateConversationList) {
        this.privateConversationList = privateConversationList;
    }


    public boolean isPerfect() {

        return isPerfect;
    }


    public void setNOTalkToKefu() {
        mSharedPreferences.edit().putInt(IS_TALK_TO_KEFU, 0).apply();
    }

    public void setTalkToKefu() {
        mSharedPreferences.edit().putInt(IS_TALK_TO_KEFU, 1).apply();
    }

    public int getTalkToKefu() {
        return mSharedPreferences.getInt(IS_TALK_TO_KEFU, 0);
    }

    public void putUserInfoToMap(String key, OtherUserInfo value) {
        if (key != null && value != null) {
            map.put(key, value);
        }
    }

    public OtherUserInfo getUserInfoFromMap(String key) {
        if (map.containsKey(key)) {
            return map.get(key);
        }
        return null;
    }

    public Map<String, OtherUserInfo> getUserInfoFromMap() {

        return map;

    }

    public void putCircleToMap(String key, GroupBean value) {
        if (key != null && value != null) {
            mCirclemap.put(key, value);
        }
    }

    public GroupBean getCircleFromMap(String key) {
        if (mCirclemap.containsKey(key)) {
            return mCirclemap.get(key);
        }
        return null;
    }


    public void setPerfect(boolean perfect) {
        isPerfect = perfect;
    }


    private UserInfoHelper() {
        mSharedPreferences = BaseApp.context.getSharedPreferences("user_info", Context.MODE_PRIVATE);
    }

    public static UserInfoHelper getIntance() {
        if (intance == null) {
            intance = new UserInfoHelper();
        }
        return intance;
    }

    public CenterBean getCenterBean() {
        if (mDetailedUserInfoBean == null) {
            return new CenterBean();
        }
        return mDetailedUserInfoBean;
    }

    public void setCenterBean(CenterBean mDetailedUserInfoBean) {
        this.mDetailedUserInfoBean = mDetailedUserInfoBean;
    }

    public void clearUserInfoBean() {
        if (mDetailedUserInfoBean != null) {
            mDetailedUserInfoBean = new CenterBean();
        }
    }

    public void logout() {
        UserInfoBean bean = new UserInfoBean();
        clearUserInfoBean();
        setUserInfoBean(bean);
        mSharedPreferences.edit().clear();
    }

    public boolean isLogin() {
        String mUserToken = getToken();
        int mUserId = getUserId();
        if (TextUtils.isEmpty(mUserToken)) {
            return false;
        }
        if (mUserId == 0) {
            return false;
        }
        return true;
    }


    public void setUserInfoBean(UserInfoBean userInfoBean) {
        mSharedPreferences.edit().putInt(USER_ID, userInfoBean.user_id)
                .putString(USER_TOKEN, userInfoBean.token)
                .putString(USER_RONG_TOKEN, userInfoBean.rong_token)
                .apply();
        this.userInfoBean = userInfoBean;
    }

    public void setUserId(int userId) {
        mSharedPreferences.edit().putInt(USER_ID, userId).apply();
    }

    public void setToken(String token) {
        mSharedPreferences.edit().putString(USER_TOKEN, token).apply();
    }

    public int getUserId() {
        return mSharedPreferences.getInt(USER_ID, 0);
    }

    public String getToken() {
        return mSharedPreferences.getString(USER_TOKEN, "");
    }

    public void setUsername(String name) {
        mSharedPreferences.edit().putString(USER_NAME, name).apply();
    }

    public String getUserName() {
        return mSharedPreferences.getString(USER_NAME, "");
    }


    public void setRongyunToken(String token) {
        mSharedPreferences.edit().putString(USER_RONG_TOKEN, token).apply();
    }

    public String getRongyunToken() {
        return mSharedPreferences.getString(USER_RONG_TOKEN, "");
    }

    public void setPushTimePoke(long timePoke) {
        mSharedPreferences.edit().putLong(TIME_POKE, timePoke).apply();
    }

    public long getPushTimePoke() {
        return mSharedPreferences.getLong(TIME_POKE, 0);
    }

    public void setActTime(long timeAct) {
        mSharedPreferences.edit().putLong(TIME_ACT, timeAct).apply();
    }

    public long getActTime() {
        return mSharedPreferences.getLong(TIME_ACT, 0);
    }


    public void setActTimeWeek(int timePoke) {
        mSharedPreferences.edit().putInt(TIME_WEEK_ACT, timePoke).apply();
    }

    public int getActTimeWeek() {
        return mSharedPreferences.getInt(TIME_WEEK_ACT, 0);
    }

    public void setInfoCompany(String company) {
        mSharedPreferences.edit().putString(COMPANY, company).apply();
    }

    public String getInfoCompany() {
        return mSharedPreferences.getString(COMPANY, "");
    }

    public void setInfoPosition(String position) {
        mSharedPreferences.edit().putString(POSITION, position).apply();
    }

    public String getIngoPosition() {
        return mSharedPreferences.getString(POSITION, "");
    }

    public void setInfoIsV(int isV) {
        mSharedPreferences.edit().putInt(IS_V, isV).apply();
    }

    public int getInfoIsV() {
        return mSharedPreferences.getInt(IS_V, 0);
    }

    public void setInfoNickName(String nickName) {
        mSharedPreferences.edit().putString(NICKNAME, nickName).apply();
    }

    public String getInfoNickName() {
        return mSharedPreferences.getString(NICKNAME, "");
    }


    public void setKnowDoTime(int time) {
        mSharedPreferences.edit().putInt(TIME_KNOW_DO, time).apply();
    }

    public int getKnowDoTime() {
        return mSharedPreferences.getInt(TIME_KNOW_DO, 0);
    }


    public void setActId(int actId) {
        mSharedPreferences.edit().putInt(ACT_ID, actId).apply();
    }

    public int getActId() {
        return mSharedPreferences.getInt(ACT_ID, 0);
    }


    public void setInfoRealname(String realname) {
        mSharedPreferences.edit().putString(REALNAME, realname).apply();
    }

    public String getInfoRealname() {
        return mSharedPreferences.getString(REALNAME, "");
    }


    public void exit() {
        userInfoBean = null;
    }

    public void setQYtoken(String token) {
        mSharedPreferences.edit().putString(QY_TOKEN, token).apply();
    }

    public String getQyToken() {
        return mSharedPreferences.getString(QY_TOKEN, "");

    }

    public void setF_id(int F_id) {
        mSharedPreferences.edit().putInt(THE_F_ID, F_id).apply();
    }

    public int getF_id() {
        return mSharedPreferences.getInt(THE_F_ID, 0);
    }

    public void setHeadUrl(String url) {
        mSharedPreferences.edit().putString(HEAD_URL, url).apply();
    }

    public String getHeadUrl() {
        return mSharedPreferences.getString(HEAD_URL, "");
    }


}
