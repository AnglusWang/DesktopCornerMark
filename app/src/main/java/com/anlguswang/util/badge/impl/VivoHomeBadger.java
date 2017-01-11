package com.anlguswang.util.badge.impl;

import android.app.Notification;
import android.content.Context;
import android.content.Intent;

import com.anlguswang.util.badge.Badger;

import java.util.Arrays;
import java.util.List;

/**
 * Created by anglus on 2017/1/6.
 */

public class VivoHomeBadger extends Badger {
    @Override
    public void executeBadge(Context context, Notification notification,
                             int notificationId, int thisNotificationCount, int count) {
        setNotification(notification, notificationId, context);
        String launcherClassName = getLauncherClassName(context);
        if (launcherClassName == null) {
            return;
        }

        Intent intent = new Intent("launcher.action.CHANGE_APPLICATION_NOTIFICATION_NUM");
        intent.putExtra("packageName", context.getPackageName());
        intent.putExtra("className", launcherClassName);
        intent.putExtra("notificationNum", count);
        context.sendBroadcast(intent);
    }

    @Override
    public List<String> getSupportLaunchers() {
        return Arrays.asList("com.vivo.launcher");
    }
}
