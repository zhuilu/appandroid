//package com.xinniu.android.qiqueqiao.im.provider;
//
//import android.Manifest;
//import android.content.Context;
//import android.content.DialogInterface;
//import android.content.Intent;
//import android.content.pm.PackageManager;
//import android.net.Uri;
//import android.os.Bundle;
//import android.text.Spannable;
//import android.text.SpannableString;
//import android.text.Spanned;
//import android.text.TextPaint;
//import android.text.TextUtils;
//import android.text.method.LinkMovementMethod;
//import android.text.style.ClickableSpan;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ImageView;
//import android.widget.TextView;
//
//import androidx.appcompat.app.AlertDialog;
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.core.app.ActivityCompat;
//import androidx.core.content.ContextCompat;
//
//import com.xinniu.android.qiqueqiao.R;
//import com.xinniu.android.qiqueqiao.activity.VipV4ListActivity;
//import com.xinniu.android.qiqueqiao.bean.SecretPhoneBean;
//import com.xinniu.android.qiqueqiao.im.message.SecretPhoneMessage;
//import com.xinniu.android.qiqueqiao.request.RequestManager;
//import com.xinniu.android.qiqueqiao.request.callback.SecretPhoneCallback;
//import com.xinniu.android.qiqueqiao.utils.ToastUtils;
//
//
//import io.rong.imlib.model.Conversation;
//import pub.devrel.easypermissions.AfterPermissionGranted;
//import pub.devrel.easypermissions.EasyPermissions;
//
////import android.support.v4.app.ActivityCompat;
////import android.support.v4.content.ContextCompat;
//
///**
// * Created by yuchance on 2018/4/23.
// */
//
//
////@ProviderTag(messageContent = SecretPhoneMessage.class, showPortrait = false, centerInHorizontal = true)
//public class SecretPhoneNumProvider extends IContainerItemProvider.MessageProvider<SecretPhoneMessage> {
//
//    private Context context;
//    class ViewHolder{
//        TextView logTv;
//        ImageView whyImg;
//    }
//
////    public SecretPhoneNumProvider(Context context) {
////        this.context = context;
////    }
////
//////    public SecretPhoneNumProvider(SecretCall secretCall) {
//////        this.secretCall = secretCall;
//////    }
////
////
////    public SecretPhoneNumProvider() {
////    }
//
//    @Override
//    public View newView(Context context, ViewGroup viewGroup) {
//        this.context = context;
//        View  view= LayoutInflater.from(context).inflate(R.layout.item_chat_phone,viewGroup,false);
//        ViewHolder holder = new ViewHolder();
//        holder.logTv = (TextView) view.findViewById(R.id.item_chat_phone_content);
//        holder.whyImg = (ImageView) view.findViewById(R.id.item_chat_phone_whyImg);
//
//        view.setTag(holder);
//        return view;
//    }
//
//
//    @Override
//    public void bindView(View view, int i, SecretPhoneMessage secretPhoneMessage, final UIMessage uiMessage) {
//        ViewHolder holder = (ViewHolder) view.getTag();
//        if (uiMessage.getConversationType() == Conversation.ConversationType.PRIVATE) {
//            SpannableString span = new SpannableString("对方未及时回复？可直接拨打隐私号");
//            span.setSpan(new ClickableSpan() {
//                @Override
//                public void updateDrawState(TextPaint ds) {
//                    super.updateDrawState(ds);
//                    ds.setColor(ContextCompat.getColor(context, R.color.colorPrimary));
//                    ds.setUnderlineText(false);
//                }
//
//                @Override
//                public void onClick(View widget) {
//                    SecretCall(uiMessage.getTargetId());
//                }
//            }, 11, span.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
//            holder.logTv.setText(span);
//            holder.logTv.setMovementMethod(LinkMovementMethod.getInstance());
//
//            holder.whyImg.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(context);
//                    builder.setMessage("可通过拨打虚拟号,直接联系到对方,双方无法看到对方真实号码,必须使用当前账号绑定的手机号码拨打。");
//                    builder.setNegativeButton("我知道了", new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//                            dialog.dismiss();
//                        }
//                    });
//                    builder.show();
//                }
//            });
//        }
//
//
//    }
//
//    @Override
//    public Spannable getContentSummary(SecretPhoneMessage secretPhoneMessage) {
//        if (!TextUtils.isEmpty(secretPhoneMessage.content)) {
//            return new SpannableString(secretPhoneMessage.content);
//        }
//        return null;
//    }
//
//    @Override
//    public void onItemClick(View view, int i, SecretPhoneMessage secretPhoneMessage, UIMessage uiMessage) {
//
//    }
//
////    private SecretCall secretCall;
////
////    public interface SecretCall{
////        void secretCall();
////    }
////
////    public void setSecretCall(SecretCall secretCall) {
////        this.secretCall = secretCall;
////    }
//public void SecretCall(String mTargetId) {
//    int userId = Integer.parseInt(mTargetId);
//    RequestManager.getInstance().getSecretPhone(userId, new SecretPhoneCallback() {
//        @Override
//        public void onSuccess(SecretPhoneBean bean) {
//            String phoneNum = bean.getMobile();
//            goTocall(phoneNum);
//        }
//        @Override
//        public void onFailue(int code, String msg) {
//            if (code == 301){
//                AlertDialog.Builder builder = new AlertDialog.Builder(context);
//                builder.setMessage(msg);
//                builder.setPositiveButton("去开通", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        Intent intent = new Intent(context, VipV4ListActivity.class);
//                        Bundle bundle = new Bundle();
//                        bundle.putString("svip","svip");
//                        intent.putExtras(bundle);
//                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                        context.startActivity(intent,bundle);
//                        dialog.dismiss();
//                    }
//                });
//                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        dialog.dismiss();
//                    }
//                });
//                builder.show();
//
//            }else {
//                ToastUtils.showToast(context,msg);
//            }
//
//
//        }
//    });
//
//
//}
//
//
//    @AfterPermissionGranted(11111)
//    private void goTocall(String phoneNum) {
//        if (EasyPermissions.hasPermissions(context, new String[]{Manifest.permission.CALL_PHONE})) {
//            Intent intent1 = new Intent(Intent.ACTION_CALL, Uri.parse("tel:"+phoneNum));
//            intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
//                // TODO: Consider calling
//                //    ActivityCompat#requestPermissions
//                // here to request the missing permissions, and then overriding
//                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
//                //                                          int[] grantResults)
//                // to handle the case where the user grants the permission. See the documentation
//                // for ActivityCompat#requestPermissions for more details.
//                ToastUtils.showCentetImgToast(context,"未获取电话权限");
//                return;
//            }
//            context.startActivity(intent1);
//        } else {
//            EasyPermissions.requestPermissions((AppCompatActivity) context,
//                    "系统需要获取您的电话权限",
//                    11111, new String[]{Manifest.permission.CALL_PHONE});
//        }
//    }
//
//}
