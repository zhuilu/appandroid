package com.xinniu.android.qiqueqiao.im;

import java.util.List;

import io.rong.imkit.IExtensionModule;
import io.rong.imkit.RongExtension;
import io.rong.imkit.emoticon.IEmoticonTab;
import io.rong.imkit.plugin.IPluginModule;
import io.rong.imlib.model.Conversation;
import io.rong.imlib.model.Message;

public class MyExtensionModule implements IExtensionModule {
    @Override
    public void onInit(String s) {

    }

    @Override
    public void onConnect(String s) {

    }

    @Override
    public void onAttachedToExtension(RongExtension rongExtension) {

    }

    @Override
    public void onDetachedFromExtension() {

    }

    @Override
    public void onReceivedMessage(Message message) {

    }

    @Override
    public List<IPluginModule> getPluginModules(Conversation.ConversationType conversationType) {
        return null;
    }

    @Override
    public List<IEmoticonTab> getEmoticonTabs() {
        return null;
    }

    @Override
    public void onDisconnect() {

    }
}