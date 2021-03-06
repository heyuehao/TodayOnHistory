package com.heyuehao.common.Service;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;

import com.heyuehao.R;
import com.heyuehao.common.Config.SettingsConfig;

import java.util.Calendar;
import java.util.TimeZone;

import androidx.core.app.NotificationCompat;

public class AlarmService extends Service {
    Notification notification;
    AlarmManager manager;
    PendingIntent pendingIntent;

    public AlarmService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
//        throw new UnsupportedOperationException("Not yet implemented");
        return null;
    }

    public int onStartCommand(Intent intent, int flags, int startId) {
        // 通知常驻
        StayForeground();

        manager = (AlarmManager) getSystemService(ALARM_SERVICE);
        // 启动时间
        long triggerAtTime = getStartTime();
//        triggerAtTime = System.currentTimeMillis() + 5000;
        Log.d("triggerAtTime", String.valueOf(triggerAtTime));
        // 启动的任务
        Intent in = new Intent(this, NotificationService.class);
        pendingIntent = PendingIntent.getService(this, 0, in, PendingIntent.FLAG_UPDATE_CURRENT);
        manager.set(AlarmManager.RTC_WAKEUP, triggerAtTime, pendingIntent);
//        return START_NOT_STICKY;
        return super.onStartCommand(intent, flags, startId);
    }

    public long getStartTime() {
        // 读取设置的时间
        SettingsConfig sc = new SettingsConfig(this);
        String pushTime = sc.get("pushTime");
        if("default value".equals(pushTime)) {
            pushTime = "8:00";
        }
        int hour = Integer.parseInt(pushTime.split(":")[0]);
        int minute = Integer.parseInt(pushTime.split(":")[1]);
        // 开机之后到现在的运行时间(包括睡眠时间)
        long systemTime = System.currentTimeMillis();
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        // 这里时区需要设置一下，不然会有8个小时的时间差
        calendar.setTimeZone(TimeZone.getTimeZone("GMT+8"));
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        // 选择的定时时间
        long selectTime = calendar.getTimeInMillis();
        // 如果当前时间大于设置的时间，就从第二天计算到第二天的剩余时间
        if(systemTime > selectTime) {
            calendar.add(Calendar.DAY_OF_MONTH, 1);
            selectTime = calendar.getTimeInMillis();
        }
        return selectTime;
    }

    public void StayForeground() {
        // 兼容安卓8.0
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("stay", "前台保持服务", NotificationManager.IMPORTANCE_MIN);
            NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            manager.createNotificationChannel(channel);
        }

        Notification notification = new NotificationCompat.Builder(AlarmService.this, "stay")
                .setContentTitle("那年今日")
                .setContentText("前台保持服务")
                .setSmallIcon(R.mipmap.ic_launcher)
                .build();
        startForeground(1, notification);
    }

    @Override
    public void onDestroy() {
        // 停止通知服务
        manager.cancel(pendingIntent);
        stopForeground(true);
        super.onDestroy();
    }
}
