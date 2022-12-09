package com.xinniu.android.qiqueqiao.im.provider;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
//import android.support.v4.app.ActivityCompat;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.core.app.ActivityCompat;

import com.xinniu.android.qiqueqiao.R;
import com.xinniu.android.qiqueqiao.im.message.ExchangeMessage;
import com.xinniu.android.qiqueqiao.request.RequestManager;
import com.xinniu.android.qiqueqiao.utils.Logger;
import com.xinniu.android.qiqueqiao.utils.ToastUtils;
import com.xinniu.android.qiqueqiao.utils.Utils;


import io.rong.imkit.conversation.messgelist.provider.IMessageProvider;
import io.rong.imlib.model.Message;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

/**
 * Created by lzq on 2017/12/14.
 */

//@ProviderTag(messageContent = ExchangeMessage.class)
//     extends IMessageProvider<ExchangeMessage>
public class ExchageMessageProvider {

    private Context context;

    class ViewHolder {
        TextView content;
        TextView left;
        TextView right;
        LinearLayout rootll;
    }

//    //    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
////    @Override
//    public void bindView(View view, int i, final ExchangeMessage exchangeMessage, UIMessage uiMessage) {
//        ViewHolder holder = (ViewHolder) view.getTag();
//        if (uiMessage.getMessageDirection() == Message.MessageDirection.SEND) {//消息方向，自己发送的
//            holder.rootll.setBackgroundResource(R.mipmap.chat_rc_ic_bubble_right);
//        } else {
//            holder.rootll.setBackgroundResource(R.mipmap.chat_rc_ic_bubble_left);
//        }
//        if (exchangeMessage.type == 0) {
//            if (exchangeMessage.info == 1) {
//                holder.content.setText("我想和你交换电话号码，是否同意？");
//                holder.left.setVisibility(View.VISIBLE);
//                holder.left.setText("拒绝");
//                holder.left.setBackgroundResource(R.drawable.shape_chat_un_agree);
//                holder.left.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        RequestManager.getInstance().confirmExchange(2, exchangeMessage.id);
//                    }
//                });
//                holder.right.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        RequestManager.getInstance().confirmExchange(1, exchangeMessage.id);
//                    }
//                });
//                holder.right.setText("同意");
//                holder.right.setBackgroundResource(R.drawable.shape_chat_agree);
//            }
//            if (exchangeMessage.info == 2) {
//                holder.content.setText("我想和你交换微信，是否同意？");
//                holder.left.setVisibility(View.VISIBLE);
//                holder.left.setText("拒绝");
//                holder.left.setBackgroundResource(R.drawable.shape_chat_un_agree);
//                holder.left.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        RequestManager.getInstance().confirmExchange(2, exchangeMessage.id);
//                    }
//                });
//                holder.right.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        RequestManager.getInstance().confirmExchange(1, exchangeMessage.id);
//                    }
//                });
//                holder.right.setText("同意");
//                holder.right.setBackgroundResource(R.drawable.shape_chat_agree);
//            }
//        }
//        if (exchangeMessage.type == 1) {
//            if (exchangeMessage.info == 1) {
//                final String phone;
//                if (uiMessage.getMessageDirection() == Message.MessageDirection.RECEIVE) {
//                    phone =  exchangeMessage.userInfo;
//                    Logger.i("lzq","exchangeMessage.toUserInfo phone:"+phone);
//                } else {
//                    phone = exchangeMessage.userInfo;
//                    Logger.i("lzq","exchangeMessage.userInfo phone:"+phone);
//                }
//                holder.content.setText("我的电话：" + phone);
//                holder.left.setVisibility(View.GONE);
//                holder.right.setText("呼叫");
//                holder.right.setBackgroundResource(R.drawable.shape_chat_call);
//                holder.right.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        //// TODO: 2017/12/26
//                        call(phone);
//                    }
//                });
//            }
//            if (exchangeMessage.info == 2) {
//                final String wechatStr;
//                if (uiMessage.getMessageDirection() == Message.MessageDirection.RECEIVE) {
//                    wechatStr = exchangeMessage.userInfo;
////                    holder.content.setText("我的微信："+exchangeMessage.toUserInfo);
//                    Logger.i("lzq","exchangeMessage.toUserInfo phone:"+wechatStr);
//                } else {
//                    wechatStr = exchangeMessage.userInfo;
//                }
//                if (TextUtils.isEmpty(wechatStr) || wechatStr.contentEquals("null")){
//                    holder.content.setText("对方未填写微信" );
//                }else{
//                    holder.content.setText("我的微信：" + wechatStr);
//                }
//                holder.left.setVisibility(View.GONE);
//                holder.right.setText("复制");
//                holder.right.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        Utils.copyContent(wechatStr,context);
//                    }
//                });
//                holder.right.setBackgroundResource(R.drawable.shape_chat_copy);
//            }
//        }
//
//    }
//
//    @Override
//    public Spannable getContentSummary(ExchangeMessage exchangeMessage) {
//        return new SpannableString(exchangeMessage.content);
//    }
//
//    @Override
//    public void onItemClick(View view, int i, ExchangeMessage exchangeMessage, UIMessage uiMessage) {
//
//    }
//
//    @Override
//    public View newView(Context context, ViewGroup viewGroup) {
//        this.context = context;
//        View view = LayoutInflater.from(context).inflate(R.layout.item_talk_exchange, viewGroup, false);
//        ViewHolder holder = new ViewHolder();
//        holder.content = (TextView) view.findViewById(R.id.content);
//        holder.left = (TextView) view.findViewById(R.id.left_bt);
//        holder.right = (TextView) view.findViewById(R.id.right_bt);
//        holder.rootll = (LinearLayout) view.findViewById(R.id.root_view_ll);
//        view.setTag(holder);
//        return view;
//    }

    private void call(String phone) {
        requestPermission(phone);
    }
    @AfterPermissionGranted(11111)
    public void requestPermission(String phone) {
        if (EasyPermissions.hasPermissions(context, new String[]{Manifest.permission.CALL_PHONE})) {
            Intent intent1 = new Intent(Intent.ACTION_CALL, Uri.parse("tel:"+phone));
            intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                ToastUtils.showCentetImgToast(context,"未获取电话权限");
                return;
            }
            context.startActivity(intent1);
        } else {
            EasyPermissions.requestPermissions((Activity)context,
                    "系统需要获取获取您的电话权限",
                    11111, new String[]{Manifest.permission.CALL_PHONE});
        }
    }
}
