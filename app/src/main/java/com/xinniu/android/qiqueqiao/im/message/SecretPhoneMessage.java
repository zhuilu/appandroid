package com.xinniu.android.qiqueqiao.im.message;

import android.annotation.SuppressLint;
import android.os.Parcel;

import com.xinniu.android.qiqueqiao.utils.Logger;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

import io.rong.common.ParcelUtils;

import io.rong.imlib.MessageTag;
import io.rong.imlib.model.MessageContent;

/**
 * Created by yuchance on 2018/4/23.
 */

@MessageTag(value = "QiQueQiao:PrivacyNumberMsg", flag = MessageTag.ISCOUNTED | MessageTag.ISPERSISTED)
public class SecretPhoneMessage extends MessageContent {


    public String content;


    public SecretPhoneMessage(byte[] data) {
//        String jsonStr = null;
//        try {
//            jsonStr = new String(data, "UTF-8");
//        } catch (UnsupportedEncodingException e1) {
//
//        }
//
//        try {
//            JSONObject jsonObj = new JSONObject(jsonStr);
//            if (jsonObj.has("content")) {
//                content = jsonObj.optString("content");
//            }
//        } catch (JSONException e) {
//        }
    }




    public SecretPhoneMessage(Parcel source) {
       content = ParcelUtils.readFromParcel(source);

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
    public static final Creator<SecretPhoneMessage> CREATOR = new Creator<SecretPhoneMessage>() {

        @Override
        public SecretPhoneMessage createFromParcel(Parcel source) {
            return new SecretPhoneMessage(source);
        }

        @Override
        public SecretPhoneMessage[] newArray(int size) {
            return new SecretPhoneMessage[size];
        }
    };


    @Override
    public void writeToParcel(Parcel dest, int flags) {

        ParcelUtils.writeToParcel(dest,content);

    }
}
