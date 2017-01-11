package com.anlguswang.util.badge.impl;

import android.app.Notification;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import com.anlguswang.util.badge.Badger;

import java.util.Arrays;
import java.util.List;

/**
 * Created by anglus on 2017/1/6.
 */

public class HuaweiHomeBadger extends Badger {
    @Override
    public void executeBadge(Context context, Notification notification,
                             int notificationId, int thisNotificationCount, int count) {
        setNotification(notification, notificationId, context);
        String launcherClassName = getLauncherClassName(context);
        if (launcherClassName == null) {
            return;
        }

        Bundle localBundle = new Bundle();
        localBundle.putString("package", context.getPackageName());
        localBundle.putString("class", launcherClassName);
        localBundle.putInt("badgenumber", count);
        context.getContentResolver()
                .call(Uri.parse("content://com.huawei.android.launcher.settings/badge/"),
                        "change_badge", null, localBundle);
    }

    @Override
    public List<String> getSupportLaunchers() {
        return Arrays.asList("com.huawei.android.launcher");
    }
}
