package com.anlguswang.util.badge.impl;

import android.app.Notification;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

import com.angluswang.badgedemo.R;
import com.anlguswang.util.badge.Badger;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

public class XiaomiHomeBadger extends Badger {

    @Override
    public void executeBadge(Context context, Notification notification,
                             int notificationId, int thisNotificationCount, int count) {
        boolean isMiUIV6 = true;
        try {
            NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
            builder.setContentTitle("您有" + count + "条未读消息");
            builder.setTicker("您有" + count + "条未读消息");
            builder.setAutoCancel(true);
            builder.setSmallIcon(R.mipmap.ic_launcher);
            builder.setDefaults(Notification.DEFAULT_LIGHTS);
            notification = builder.build();

            Field field = notification.getClass().getDeclaredField("extraNotification");
            Object extraNotification = field.get(notification);
            Method method = extraNotification.getClass()
                    .getDeclaredMethod("setMessageCount", int.class);
            method.invoke(extraNotification, thisNotificationCount);

        } catch (Exception e) {
            e.printStackTrace();
            //miui6 之前的版本
            isMiUIV6 = false;

            String launcherClassName = getLauncherClassName(context);
            if (launcherClassName == null) {
                return;
            }
            Intent localIntent = new Intent("android.intent.action.APPLICATION_MESSAGE_UPDATE");
            localIntent.putExtra("android.intent.extra.update_application_component_name",
                    context.getPackageName() + "/" + launcherClassName);
            localIntent.putExtra("android.intent.extra.update_application_message_text", count);
            context.sendBroadcast(localIntent);

        } finally {
            if (notification != null && isMiUIV6) {
                //miui6 以上版本需要使用通知发送
                setNotification(notification, notificationId, context);
            }
        }
    }

    @Override
    public List<String> getSupportLaunchers() {
        return Arrays.asList(
                "com.miui.miuilite",
                "com.miui.home",
                "com.miui.miuihome",
                "com.miui.miuihome2",
                "com.miui.mihome",
                "com.miui.mihome2"
        );
    }
}
