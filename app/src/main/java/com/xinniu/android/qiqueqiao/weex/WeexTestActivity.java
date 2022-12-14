//package com.xinniu.android.qiqueqiao.weex;
//
//import android.os.Bundle;
//
//import android.view.View;
//
//import com.taobao.weex.IWXRenderListener;
//import com.taobao.weex.WXSDKInstance;
//import com.taobao.weex.common.WXRenderStrategy;
//import com.taobao.weex.utils.WXFileUtils;
//import com.xinniu.android.qiqueqiao.R;
//
///**
// * Created by lzq on 2018/1/10.
// */
//
//public class WeexTestActivity extends AppCompatActivity implements IWXRenderListener {
//
//    WXSDKInstance mWXSDKInstance;
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//        mWXSDKInstance = new WXSDKInstance(this);
//        mWXSDKInstance.registerRenderListener(this);
//        /**
//         * WXSample 可以替换成自定义的字符串，针对埋点有效。
//         * template 是.we transform 后的 js文件。
//         * option 可以为空，或者通过option传入 js需要的参数。例如bundle js的地址等。
//         * jsonInitData 可以为空。
//         * width 为-1 默认全屏，可以自己定制。
//         * height =-1 默认全屏，可以自己定制。
//         */
//        mWXSDKInstance.render("WXSample", WXFileUtils.loadFileContent("hello.js", this), null, null, -1, -1, WXRenderStrategy.APPEND_ASYNC);
//    }
//    @Override
//    public void onViewCreated(WXSDKInstance instance, View view) {
//        setContentView(view);
//    }
//    @Override
//    public void onRenderSuccess(WXSDKInstance instance, int width, int height) {
//    }
//    @Override
//    public void onRefreshSuccess(WXSDKInstance instance, int width, int height) {
//    }
//    @Override
//    public void onException(WXSDKInstance instance, String errCode, String msg) {
//    }
//    @Override
//    protected void onResume() {
//        super.onResume();
//        if(mWXSDKInstance!=null){
//            mWXSDKInstance.onActivityResume();
//        }
//    }
//    @Override
//    protected void onPause() {
//        super.onPause();
//        if(mWXSDKInstance!=null){
//            mWXSDKInstance.onActivityPause();
//        }
//    }
//    @Override
//    protected void onStop() {
//        super.onStop();
//        if(mWXSDKInstance!=null){
//            mWXSDKInstance.onActivityStop();
//        }
//    }
//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        if(mWXSDKInstance!=null){
//            mWXSDKInstance.onActivityDestroy();
//        }
//    }
//}
