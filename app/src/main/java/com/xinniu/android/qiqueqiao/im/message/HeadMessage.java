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
@MessageTag(value = "QiQueQiao:SourceMsg", flag = MessageTag.ISCOUNTED | MessageTag.ISPERSISTED)
public class HeadMessage extends MessageContent {

    public String content;

    public int id;
//    public String need_remark;
//    public String provide_remark;
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




    public HeadMessage(byte[] data) {
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
    public HeadMessage(Parcel in) {
        content=ParcelUtils.readFromParcel(in);//该类为工具类，消息属性
        id =ParcelUtils.readIntFromParcel(in);
        view =ParcelUtils.readIntFromParcel(in);
        talk =ParcelUtils.readIntFromParcel(in);
        is_hot = ParcelUtils.readIntFromParcel(in);
//        need_remark=ParcelUtils.readFromParcel(in);
//        provide_remark=ParcelUtils.readFromParcel(in);
        provide_resource_cn=ParcelUtils.readFromParcel(in);
        need_resource_cn=ParcelUtils.readFromParcel(in);
        cooperation_mode_cn=ParcelUtils.readFromParcel(in);
        realname=ParcelUtils.readFromParcel(in);
        position=ParcelUtils.readFromParcel(in);
        company=ParcelUtils.readFromParcel(in);
        head_pic=ParcelUtils.readFromParcel(in);
        title = ParcelUtils.readFromParcel(in);
        isV = ParcelUtils.readIntFromParcel(in);
        isVip = ParcelUtils.readIntFromParcel(in);

        //这里可继续增加你消息的属性
    }

    /**
     * 读取接口，目的是要从Parcel中构造一个实现了Parcelable的类的实例处理。
     */
    public static final Creator<HeadMessage> CREATOR = new Creator<HeadMessage>() {

        @Override
        public HeadMessage createFromParcel(Parcel source) {
            return new HeadMessage(source);
        }

        @Override
        public HeadMessage[] newArray(int size) {
            return new HeadMessage[size];
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
        ParcelUtils.writeToParcel(dest, talk);
        ParcelUtils.writeToParcel(dest, is_hot);
//        if(!TextUtils.isEmpty(need_remark)) {
//            ParcelUtils.writeToParcel(dest, need_remark);
//        }
//        if (!TextUtils.isEmpty(provide_remark)) {
//            ParcelUtils.writeToParcel(dest, provide_remark);
//        }
//        if (!TextUtils.isEmpty(title)){

//        }
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

        //这里可继续增加你消息的属性
    }
}
