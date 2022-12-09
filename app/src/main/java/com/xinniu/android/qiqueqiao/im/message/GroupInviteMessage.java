package com.xinniu.android.qiqueqiao.im.message;

import android.annotation.SuppressLint;
import android.os.Parcel;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

import io.rong.common.ParcelUtils;
import io.rong.imlib.MessageTag;
import io.rong.imlib.model.MessageContent;

/**
 * Created by yuchance on 2019/1/11.
 */
@MessageTag(value = "QiQueQiao:InviteToGroupMsg", flag = MessageTag.ISCOUNTED | MessageTag.ISPERSISTED)
public class GroupInviteMessage extends MessageContent {

    public String content;

    public String name;
    public String head_pic;
    public int id;


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


    public GroupInviteMessage(byte[] data) {
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

                if (extra.has("name")) {
                    name = extra.optString("name");
                }
                if (extra.has("head_pic")) {
                    head_pic = extra.optString("head_pic");
                }
                if (extra.has("id")) {
                    id = extra.optInt("id");
                }
            }
        }catch (JSONException e) {
        }



    }
    //给消息赋值
    public GroupInviteMessage(Parcel source) {

        content = ParcelUtils.readFromParcel(source);

        name = ParcelUtils.readFromParcel(source);

        head_pic = ParcelUtils.readFromParcel(source);

        id = ParcelUtils.readIntFromParcel(source);

    }




    @Override
    public int describeContents() {
        return 0;
    }
    /**
     * 读取接口，目的是要从Parcel中构造一个实现了Parcelable的类的实例处理。
     */
    public static final Creator<GroupInviteMessage> CREATOR = new Creator<GroupInviteMessage>() {


        @Override
        public GroupInviteMessage createFromParcel(Parcel source) {
            return new GroupInviteMessage(source);
        }

        @Override
        public GroupInviteMessage[] newArray(int size) {
            return new GroupInviteMessage[size];
        }
    };

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        ParcelUtils.writeToParcel(dest,content);
        ParcelUtils.writeToParcel(dest,name);
        ParcelUtils.writeToParcel(dest,head_pic);
        ParcelUtils.writeToParcel(dest,id);
    }
}
