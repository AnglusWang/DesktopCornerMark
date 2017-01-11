package com.angluswang.badgedemo;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.anlguswang.util.badge.BadgeUtil;

public class MainActivity extends FragmentActivity {

    public static final int NOTIFY_ID = 100;
    private EditText tvCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvCount = (EditText) findViewById(R.id.tv_count);
        findViewById(R.id.bt_set).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    final int count = Integer.parseInt(tvCount.getText().toString());
                    NotificationCompat.Builder mBuilder =
                            new NotificationCompat.Builder(getApplicationContext())
                                    .setSmallIcon(getApplicationInfo().icon)
                                    .setWhen(System.currentTimeMillis())
                                    .setAutoCancel(true)
                                    .setContentTitle("ContentTitle")
                                    .setContentText("ContentText")
                                    .setNumber(count);

                    //点击set 后，app退到桌面等待3s看效果（有的launcher当app在前台设置未读数量无效）
                    final Notification notification = mBuilder.build();
                    new Handler(getMainLooper()).postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            BadgeUtil.sendBadgeNotification(notification, NOTIFY_ID,
                                    getApplicationContext(), count, count);
                            Toast.makeText(getApplicationContext(), "success",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }, 3 * 1000);

                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
            }
        });

        findViewById(R.id.bt_clear).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // XIAO_MI 当进入app后就自动清除了未读数量.
                // 有些需要主动清除。可以所有的都清除，没什么影响
                BadgeUtil.resetBadgeCount(getApplicationContext());
            }
        });

        String launcherPackageName = getLauncherPackageName(getBaseContext());
        Log.i("Info", "launcherPackageName:--->>> " + launcherPackageName);
//        Toast.makeText(this, launcherPackageName, Toast.LENGTH_SHORT).show();
    }

    public String getLauncherPackageName(Context context)
    {
        final Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        final ResolveInfo res = context.getPackageManager().resolveActivity(intent, 0);
        if(res.activityInfo == null)
        {
            return "";
        }
        //如果是不同桌面主题，可能会出现某些问题，这部分暂未处理
        if(res.activityInfo.packageName.equals("android"))
        {
            return "";
        }else
        {
            return res.activityInfo.packageName;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        // 启动后删除之前我们定义的通知
        NotificationManager notificationManager =
                (NotificationManager) this.getSystemService(NOTIFICATION_SERVICE);
        notificationManager.cancel(NOTIFY_ID);
    }
}
