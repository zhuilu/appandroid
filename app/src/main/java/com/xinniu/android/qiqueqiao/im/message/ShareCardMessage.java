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
 * Created by yuchance on 2018/11/30.
 */

@MessageTag(value = "QiQueQiao:ContactMsg", flag = MessageTag.ISCOUNTED | MessageTag.ISPERSISTED)
public class ShareCardMessage extends MessageContent {

    public String content;

    public String realname;
    public String company;
    public String position;
    public String head_pic;
    public int user_id;




    public ShareCardMessage(byte[] data) {
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
                if (extra.has("realname")){
                    realname = extra.optString("realname");
                }
                if (extra.has("company")){
                    company = extra.optString("company");
                }
                if (extra.has("position")){
                    position = extra.optString("position");
                }
                if (extra.has("head_pic")){
                    head_pic = extra.optString("head_pic");
                }
                if (extra.has("user_id")){
                    user_id = extra.optInt("user_id");
                }
            }


        }catch (JSONException e) {
        }



    }

    //给消息赋值
    public ShareCardMessage(Parcel source) {

        content = ParcelUtils.readFromParcel(source);

        realname = ParcelUtils.readFromParcel(source);

        company = ParcelUtils.readFromParcel(source);

        position = ParcelUtils.readFromParcel(source);

        head_pic = ParcelUtils.readFromParcel(source);

        user_id = ParcelUtils.readIntFromParcel(source);

    }

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

    @Override
    public int describeContents() {
        return 0;
    }

    /**
     * 读取接口，目的是要从Parcel中构造一个实现了Parcelable的类的实例处理。
     */
    public static final Creator<ShareCardMessage> CREATOR = new Creator<ShareCardMessage>() {


        @Override
        public ShareCardMessage createFromParcel(Parcel source) {
            return new ShareCardMessage(source);
        }

        @Override
        public ShareCardMessage[] newArray(int size) {
            return new ShareCardMessage[size];
        }
    };


    @Override
    public void writeToParcel(Parcel dest, int flags) {
        ParcelUtils.writeToParcel(dest,content);
        ParcelUtils.writeToParcel(dest,realname);
        ParcelUtils.writeToParcel(dest,company);
        ParcelUtils.writeToParcel(dest,position);
        ParcelUtils.writeToParcel(dest,head_pic);
        ParcelUtils.writeToParcel(dest,user_id);
    }

}
