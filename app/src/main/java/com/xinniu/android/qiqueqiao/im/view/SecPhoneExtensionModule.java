//package com.xinniu.android.qiqueqiao.im.view;
//
//import java.util.List;
//
//import io.rong.imkit.DefaultExtensionModule;
//import io.rong.imkit.IExtensionModule;
//import io.rong.imkit.RongExtensionManager;
//import io.rong.imkit.emoticon.IEmoticonTab;
//import io.rong.imkit.plugin.IPluginModule;
//import io.rong.imlib.model.Conversation;
//
///**
// * Created by yuchance on 2018/4/20.
// */
//
//public class SecPhoneExtensionModule extends DefaultExtensionModule {
//
//    //    private TellPhonePlugin phonePlugin;
//
//    @Override
//    public List<IPluginModule> getPluginModules(Conversation.ConversationType conversationType) {
//        List<IExtensionModule> moduleList = RongExtensionManager.getInstance().getExtensionModules();
//
//        if (conversationType.equals(Conversation.ConversationType.PRIVATE)) {
//            List<IPluginModule> pluginModules = super.getPluginModules(conversationType);
//            if (pluginModules.size() > 1) {
//                pluginModules.remove(1);
//            }
//            //   pluginModules.add(new TellPhonePlugin());//隐藏拨打电话
//            pluginModules.add(new SendCardPlugin());
//
//            return pluginModules;
//        } else {
//            return super.getPluginModules(conversationType);
//        }
//
//    }
//
//    @Override
//    public List<IEmoticonTab> getEmoticonTabs() {
//        return super.getEmoticonTabs();
//    }
//}
