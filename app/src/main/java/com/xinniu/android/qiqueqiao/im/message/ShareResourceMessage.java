package com.xinniu.android.qiqueqiao.im.message;

import android.os.Parcel;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

import io.rong.common.ParcelUtils;
import io.rong.imlib.MessageTag;
import io.rong.imlib.model.MessageContent;

/**
 * Created by yuchance on 2018/11/30.
 */
@MessageTag(value = "QiQueQiao:ShareSourceMsg", flag = MessageTag.ISCOUNTED | MessageTag.ISPERSISTED)
public class ShareResourceMessage extends MessageContent {

    public String content;

    public int id;

    public String provide_resource_cn;
    public String need_resource_cn;
    public String cooperation_mode_cn;
    public int view;
    public int talk;
    public int is_hot;
    public String realname;
    public String position;
    public String company;
    public String head_pic;
    public String title;
    public int isV;
    public int isVip;




    @Override
    public byte[] encode() {
        JSONObject jsonObj = new JSONObject();

        try {
            jsonObj.put("content", "这是一条消息内容");
        } catch (JSONException e) {
        }

        try {
            return jsonObj.toString().getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }

    public ShareResourceMessage(byte[] data) {
        String jsonStr = null;

        try {
            jsonStr = new String(data, "UTF-8");
        } catch (UnsupportedEncodingException e1) {

        }

        try {

            JSONObject jsonObj = new JSONObject(jsonStr);
            if (jsonObj.has("content")) {
                content = jsonObj.optString("content");
            }
            JSONObject extra;
            if (jsonObj.has("extra")) {
                extra = jsonObj.optJSONObject("extra");
                if (extra.has("id")) {
                    id = extra.optInt("id");
                }
                if (extra.has("view")) {
                    view = extra.optInt("view");
                }
                if (extra.has("talk")) {
                    talk = extra.optInt("talk");
                }
                if (extra.has("is_hot")) {
                    is_hot = extra.optInt("is_hot");
                }
                if (extra.has("title")){
                    title = extra.optString("title");
                }
                if (extra.has("is_v")){
                    isV = extra.optInt("is_v");
                }
                if (extra.has("is_vip")){
                    isVip = extra.getInt("is_vip");
                }
                if (extra.has("provide_resource_cn")) {
                    provide_resource_cn = extra.optString("provide_resource_cn");
                }
                if (extra.has("need_resource_cn")) {
                    need_resource_cn = extra.optString("need_resource_cn");
                }
                if (extra.has("cooperation_mode_cn")) {
                    cooperation_mode_cn = extra.optString("cooperation_mode_cn");
                }
                if (extra.has("realname")) {
                    realname = extra.optString("realname");
                }
                if (extra.has("position")) {
                    position = extra.optString("position");
                }
                if (extra.has("company")) {
                    company = extra.optString("company");
                }
                if (extra.has("head_pic")) {
                    head_pic = extra.optString("head_pic");
                }
            }


        } catch (JSONException e) {
        }


    }

    public ShareResourceMessage(Parcel source) {
//        content = ParcelUtils.readFromParcel(source);
        content= ParcelUtils.readFromParcel(source);//该类为工具类，消息属性
        id =ParcelUtils.readIntFromParcel(source);
        view =ParcelUtils.readIntFromParcel(source);
        talk =ParcelUtils.readIntFromParcel(source);
        is_hot = ParcelUtils.readIntFromParcel(source);
//        need_remark=ParcelUtils.readFromParcel(in);
//        provide_remark=ParcelUtils.readFromParcel(in);
        provide_resource_cn=ParcelUtils.readFromParcel(source);
        need_resource_cn=ParcelUtils.readFromParcel(source);
        cooperation_mode_cn=ParcelUtils.readFromParcel(source);
        realname=ParcelUtils.readFromParcel(source);
        position=ParcelUtils.readFromParcel(source);
        company=ParcelUtils.readFromParcel(source);
        head_pic=ParcelUtils.readFromParcel(source);
        title = ParcelUtils.readFromParcel(source);
        isV = ParcelUtils.readIntFromParcel(source);
        isVip = ParcelUtils.readIntFromParcel(source);

    }

    @Override
    public int describeContents() {
        return 0;
    }

    /**
     * 读取接口，目的是要从Parcel中构造一个实现了Parcelable的类的实例处理。
     */
    public static final Creator<ShareResourceMessage> CREATOR = new Creator<ShareResourceMessage>() {

        @Override
        public ShareResourceMessage createFromParcel(Parcel source) {
            return new ShareResourceMessage(source);
        }

        @Override
        public ShareResourceMessage[] newArray(int size) {
            return new ShareResourceMessage[size];
        }
    };

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        ParcelUtils.writeToParcel(dest, content);//该类为工具类，对消息中属性进行序列化
        ParcelUtils.writeToParcel(dest, id);
        ParcelUtils.writeToParcel(dest, view);
        ParcelUtils.writeToParcel(dest, talk);
        ParcelUtils.writeToParcel(dest, is_hot);
        ParcelUtils.writeToParcel(dest, provide_resource_cn);
        ParcelUtils.writeToParcel(dest, need_resource_cn);
        ParcelUtils.writeToParcel(dest, cooperation_mode_cn);
        ParcelUtils.writeToParcel(dest, realname);
        ParcelUtils.writeToParcel(dest, position);
        ParcelUtils.writeToParcel(dest, company);
        ParcelUtils.writeToParcel(dest, head_pic);
        ParcelUtils.writeToParcel(dest,title);
        ParcelUtils.writeToParcel(dest,isV);
        ParcelUtils.writeToParcel(dest,isVip);
    }
}
