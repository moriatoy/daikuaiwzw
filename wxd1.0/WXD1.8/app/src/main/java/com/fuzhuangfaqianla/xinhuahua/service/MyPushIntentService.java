package com.fuzhuangfaqianla.xinhuahua.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.umeng.message.UTrack;
import com.umeng.message.UmengMessageService;
import com.umeng.message.entity.UMessage;
import com.fuzhuangfaqianla.xinhuahua.R;
import com.fuzhuangfaqianla.xinhuahua.ui.main.MainActivity;

import org.android.agoo.common.AgooConstants;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

/**
 * Created by xiongchang on 17/6/23.
 */

public class MyPushIntentService extends UmengMessageService {

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public void onMessage(Context context, Intent intent) {
        try {
            //可以通过MESSAGE_BODY取得消息体
            String message = intent.getStringExtra(AgooConstants.MESSAGE_BODY);

            UMessage msg = new UMessage(new JSONObject(message));
            String title = msg.title;
            String text = msg.text;
            String after_open = msg.after_open;

            String url = "";
            String activity = "";
            String custom = "";
            Map<String,String> extra_map = msg.extra;
            if(extra_map != null){
                for(Map.Entry<String,String> entry : extra_map.entrySet()){
                    String key = entry.getKey();
                    String value = entry.getValue();

                    switch (key){
                        case "after open":
                            after_open = value;
                            break;

                        case "url":
                            url = value;
                            break;

                        case "activity":
                            activity = value;
                            break;

                        case "custom":
                            custom = value;
                            break;
                    }
                }
            }

            Log.i("test", "message=" + message);      //消息体
            Log.i("test", "title=" + title);      //通知标题
            Log.i("test", "text=" + text);        //通知内容
            Log.i("test", "after_open=" + after_open); //打开的页面
            Log.i("test", "url=" + url); //打开的页面
            Log.i("test", "activity=" + activity); //打开的页面
            Log.i("test", "custom=" + custom); //打开的页面

            Intent intentAct = new Intent(context, MainActivity.class);
            intentAct.putExtra("after_open",after_open);
            intentAct.putExtra("url",url);
            intentAct.putExtra("activity",activity);
            intentAct.putExtra("custom",custom);
            intentAct.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);

            //TODO
            showNotification(context,msg,intentAct);

            // 完全自定义消息的处理方式，点击或者忽略
            boolean isClickOrDismissed = true;
            if(isClickOrDismissed) {
                //完全自定义消息的点击统计
                UTrack.getInstance(getApplicationContext()).trackMsgClick(msg);
            } else {
                //完全自定义消息的忽略统计
                UTrack.getInstance(getApplicationContext()).trackMsgDismissed(msg);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void showNotification(Context context, UMessage msg, Intent intent) {
        PendingIntent pendingIntent = PendingIntent.getActivity(this,
                msg.builder_id, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationManager mNotificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        Notification.Builder builder = new Notification.Builder(this)
                .setLargeIcon(BitmapFactory.decodeResource(context.getResources(),R.drawable.logo))
                .setSmallIcon(R.drawable.logo)
                .setTicker(msg.ticker)
                .setContentTitle(getResources().getString(R.string.app_name))
                .setContentText(msg.text)
                .setContentIntent(pendingIntent)
                .setNumber(1);
        Notification notification = builder.getNotification();
        notification.flags |= Notification.FLAG_AUTO_CANCEL;// 点击后自动消失
        notification.defaults |= Notification.DEFAULT_SOUND;
        notification.defaults |= Notification.DEFAULT_VIBRATE ;
        mNotificationManager.notify(msg.builder_id, notification);

    }

}
