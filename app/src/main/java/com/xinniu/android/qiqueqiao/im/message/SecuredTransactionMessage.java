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
@MessageTag(value = "QiQueQiao:SecuredTransactionMsg", flag = MessageTag.ISCOUNTED | MessageTag.ISPERSISTED)
public class SecuredTransactionMessage extends MessageContent {

    public String content;

    public int id;
    public String text;// 交易内容
    public String estimated_amount;// 金额


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

    public SecuredTransactionMessage(byte[] data) {
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

                if (extra.has("text")) {
                    text = extra.optString("text");
                }

                if (extra.has("estimated_amount")) {
                    estimated_amount = extra.optString("estimated_amount");
                }

            }


        } catch (JSONException e) {
        }


    }

    public SecuredTransactionMessage(Parcel source) {
//        content = ParcelUtils.readFromParcel(source);
        content = ParcelUtils.readFromParcel(source);//该类为工具类，消息属性
        id = ParcelUtils.readIntFromParcel(source);
        text = ParcelUtils.readFromParcel(source);
        estimated_amount = ParcelUtils.readFromParcel(source);


    }

    @Override
    public int describeContents() {
        return 0;
    }

    /**
     * 读取接口，目的是要从Parcel中构造一个实现了Parcelable的类的实例处理。
     */
    public static final Creator<SecuredTransactionMessage> CREATOR = new Creator<SecuredTransactionMessage>() {

        @Override
        public SecuredTransactionMessage createFromParcel(Parcel source) {
            return new SecuredTransactionMessage(source);
        }

        @Override
        public SecuredTransactionMessage[] newArray(int size) {
            return new SecuredTransactionMessage[size];
        }
    };

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        ParcelUtils.writeToParcel(dest, content);//该类为工具类，对消息中属性进行序列化
        ParcelUtils.writeToParcel(dest, id);
        ParcelUtils.writeToParcel(dest, text);
        ParcelUtils.writeToParcel(dest, estimated_amount);

    }
}
