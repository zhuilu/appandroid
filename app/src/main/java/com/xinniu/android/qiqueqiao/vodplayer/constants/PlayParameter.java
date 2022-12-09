package com.xinniu.android.qiqueqiao.vodplayer.constants;

/**
 * 播放参数, 包含:
 *  vid, vidSts, akId, akSecre, scuToken
 */
public class PlayParameter {


    /**
     * type, 用于区分播放类型, 默认为vidsts播放
     * vidsts: vid类型
     * localSource: url类型
     *
     */
    public static String PLAY_PARAM_TYPE = "vidsts";

    /**
     * vid
     */
    public static String PLAY_PARAM_VID = "";

    /**
     * akId
     */
    public static String PLAY_PARAM_AK_ID = "";

    /**
     * akSecre
     */
    public static String PLAY_PARAM_AK_SECRE = "";

    /**
     * scuToken
     */
    public static String PLAY_PARAM_SCU_TOKEN = "";

    /**
     * url类型的播放地址, 初始为:http://player.alicdn.com/video/aliyunmedia.mp4
     */
    public static String PLAY_PARAM_URL = "";

}
