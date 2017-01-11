package com.anlguswang.util.badge.impl;

import android.app.Notification;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

import com.anlguswang.util.badge.Badger;

import java.util.Arrays;
import java.util.List;

/**
 * Created by anglus on 2017/1/6.
 */

public class OppoHomeBadger extends Badger {
    @Override
    public void executeBadge(Context context, Notification notification,
                             int notificationId, int thisNotificationCount, int count) {
        setNotification(notification, notificationId, context);
        String launcherClassName = getLauncherClassName(context);
        if (launcherClassName == null) {
            return;
        }

        try {
            Bundle localBundle = new Bundle();
            localBundle.putInt("app_badge_count", count);
            context.getContentResolver()
                    .call(Uri.parse("content://com.android.badge/badge"),
                            "setAppBadgeCount", null, localBundle);
        } catch (Exception localException) {
            Log.d("BadgeUtil", "OPPOBadge badge get a crash" + localException.getMessage());
        }
    }

    @Override
    public List<String> getSupportLaunchers() {
        return Arrays.asList("com.oppo.launcher");
    }
}
