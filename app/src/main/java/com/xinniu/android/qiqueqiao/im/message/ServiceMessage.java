package com.xinniu.android.qiqueqiao.im.message;

import android.os.Parcel;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

import io.rong.common.ParcelUtils;
import io.rong.imlib.MessageTag;
import io.rong.imlib.model.MessageContent;

/**
 * Created by lzq on 2017/12/14.
 */
@MessageTag(value = "QiQueQiao:ServiceMsg", flag = MessageTag.ISCOUNTED | MessageTag.ISPERSISTED)
public class ServiceMessage extends MessageContent {
    public String content;

    public int id;
    public int view;
    public String title;
    public String remark;
    public String images;
    public String attr;
    public ServiceMessage(byte[] data) {
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
                if (extra.has("attr")) {
                    attr = extra.optString("attr");
                }
                if (extra.has("images")) {
                    images = extra.optString("images");
                }
                if (extra.has("remark")) {
                    remark = extra.optString("remark");
                }

                if (extra.has("title")) {
                    title = extra.optString("title");
                }




            }


        } catch (JSONException e) {
        }

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

    //给消息赋值。
    public ServiceMessage(Parcel in) {
        content = ParcelUtils.readFromParcel(in);//该类为工具类，消息属性
        id = ParcelUtils.readIntFromParcel(in);
        view = ParcelUtils.readIntFromParcel(in);
        attr = ParcelUtils.readFromParcel(in);
        images = ParcelUtils.readFromParcel(in);
        remark = ParcelUtils.readFromParcel(in);
        title = ParcelUtils.readFromParcel(in);



        //这里可继续增加你消息的属性
    }

    /**
     * 读取接口，目的是要从Parcel中构造一个实现了Parcelable的类的实例处理。
     */
    public static final Creator<ServiceMessage> CREATOR = new Creator<ServiceMessage>() {

        @Override
        public ServiceMessage createFromParcel(Parcel source) {
            return new ServiceMessage(source);
        }

        @Override
        public ServiceMessage[] newArray(int size) {
            return new ServiceMessage[size];
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
        ParcelUtils.writeToParcel(dest, content);//该类为工具类，对消息中属性进行序列化
        ParcelUtils.writeToParcel(dest, id);
        ParcelUtils.writeToParcel(dest, view);
        ParcelUtils.writeToParcel(dest, attr);
        ParcelUtils.writeToParcel(dest, images);
        ParcelUtils.writeToParcel(dest, remark);
        ParcelUtils.writeToParcel(dest, title);


        //这里可继续增加你消息的属性
    }
}
