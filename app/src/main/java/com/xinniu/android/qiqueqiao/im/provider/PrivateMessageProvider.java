package com.xinniu.android.qiqueqiao.im.provider;

import com.xinniu.android.qiqueqiao.im.message.HeadMessage;

import io.rong.imkit.model.ProviderTag;

/**
 * Created by lzq on 2017/12/26.
 */
@ProviderTag(messageContent = HeadMessage.class, showPortrait = false, centerInHorizontal = true)
public class PrivateMessageProvider extends HeadMessageProvider{
}
