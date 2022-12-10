package com.xinniu.android.qiqueqiao.im;

import android.content.Context;

import androidx.fragment.app.Fragment;

import java.util.List;


import io.rong.imkit.conversation.extension.IExtensionModule;
import io.rong.imkit.conversation.extension.RongExtension;
import io.rong.imkit.conversation.extension.component.emoticon.IEmoticonTab;
import io.rong.imkit.conversation.extension.component.plugin.IPluginModule;
import io.rong.imlib.model.Conversation;
import io.rong.imlib.model.Message;

public class MyExtensionModule implements IExtensionModule {
    @Override
    public void onInit(Context context,String s) {

    }

    @Override
    public void onAttachedToExtension(Fragment fragment, RongExtension rongExtension) {

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
