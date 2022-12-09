package com.xinniu.android.qiqueqiao.im.message;

import android.os.Parcel;

import com.xinniu.android.qiqueqiao.utils.Logger;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

import io.rong.common.ParcelUtils;
import io.rong.imlib.MessageTag;
import io.rong.imlib.model.MessageContent;

/**
 * Created by lzq on 2017/12/14.
 */
@MessageTag(value = "QiQueQiao:ExchangeInfoMsg", flag = MessageTag.ISCOUNTED | MessageTag.ISPERSISTED)
public class ExchangeMessage extends MessageContent {

    public int type;
    public int id;
    public int info;
    public String userInfo;
    public String toUserInfo;
    public String content;


    public ExchangeMessage(byte[] data) {
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
                Logger.i("lzq","JSONObject:"+extra.toString());
                if (extra.has("action")) {
                    type = extra.optInt("action");
                }
                if (extra.has("info")) {
                    info = extra.optInt("info");
                }
                if (extra.has("user_info")) {
                    userInfo = extra.optString("user_info");
                }
                if (extra.has("to_user_info")) {
                    toUserInfo = extra.optString("to_user_info");
                }
                if (extra.has("id")) {
                    id = extra.optInt("id");
                }
            }


        } catch (JSONException e) {
        }

    }

    public void ExchangeMessage(String content){
        this.content = content;
    }

    @Override
    public byte[] encode() {
        JSONObject jsonObj = new JSONObject();

        try {
            jsonObj.put("content", "请求交换电话已发送");
        } catch (JSONException e) {
        }

        try {
            return jsonObj.toString().getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return null;
    }

    //给消息赋值。
    public ExchangeMessage(Parcel in) {
        type =ParcelUtils.readIntFromParcel(in);//该类为工具类，消息属性
        info = ParcelUtils.readIntFromParcel(in);
        userInfo = ParcelUtils.readFromParcel(in);
        toUserInfo = ParcelUtils.readFromParcel(in);
        id = ParcelUtils.readIntFromParcel(in);
        content =  ParcelUtils.readFromParcel(in);
        //这里可继续增加你消息的属性
    }

    /**
     * 读取接口，目的是要从Parcel中构造一个实现了Parcelable的类的实例处理。
     */
    public static final Creator<ExchangeMessage> CREATOR = new Creator<ExchangeMessage>() {

        @Override
        public ExchangeMessage createFromParcel(Parcel source) {
            return new ExchangeMessage(source);
        }

        @Override
        public ExchangeMessage[] newArray(int size) {
            return new ExchangeMessage[size];
        }
    };

    /**
     * 描述了包含在 Parcelable 对象排列信息中的特殊对象的类型。
     *
     * @return 一个标志位，表明Parcelable对象特殊对象类型集合的排列。
     */
    @Override
    public int describeContents() {
        return 0;
    }

    /**
     * 将类的数据写入外部提供的 Parcel 中。
     *
     * @param dest  对象被写入的 Parcel。
     * @param flags 对象如何被写入的附加标志。
     */
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        ParcelUtils.writeToParcel(dest, type);//该类为工具类，对消息中属性进行序列化
        ParcelUtils.writeToParcel(dest, info);
        ParcelUtils.writeToParcel(dest, userInfo);
        ParcelUtils.writeToParcel(dest, toUserInfo);
        ParcelUtils.writeToParcel(dest, id);
        ParcelUtils.writeToParcel(dest, content);
        //这里可继续增加你消息的属性
    }
}
