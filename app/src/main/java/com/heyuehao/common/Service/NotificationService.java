package com.heyuehao.common.Service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;

import com.heyuehao.R;
import com.heyuehao.activity.RecordDetail;

import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import androidx.core.app.NotificationCompat;
import cn.leancloud.AVOSCloud;
import cn.leancloud.AVObject;
import cn.leancloud.AVQuery;
import cn.leancloud.AVUser;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class NotificationService extends Service {
    public NotificationService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
//        throw new UnsupportedOperationException("Not yet implemented");
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Calendar calendar = Calendar.getInstance();
        String date = calendar.get(Calendar.YEAR) + "年" + (calendar.get(Calendar.MONTH)+1)  + "月" + calendar.get(Calendar.DAY_OF_MONTH) + "日";
        // 查询是否有事件推送
        checkToday(date);

        Intent i = new Intent(this, AlarmService.class);
        startService(i);

        return super.onStartCommand(intent, flags, startId);
    }

    public void checkToday(String date) {
        AVOSCloud.initialize(this, getResources().getString(R.string.appId), getResources().getString(R.string.appKey));
        AVQuery<AVObject> userQuery = new AVQuery<>(getResources().getString(R.string.className));
        AVQuery<AVObject> dateQuery = new AVQuery<>(getResources().getString(R.string.className));
        dateQuery.whereContains("date", date.split("年")[1]);
        userQuery.whereEqualTo("user", AVUser.getCurrentUser());
        // 组合查询
        AVQuery query = AVQuery.and(Arrays.asList(userQuery, dateQuery));
        query.findInBackground().subscribe(new Observer<List<AVObject>>() {
            @Override
            public void onSubscribe(Disposable d) { }

            @Override
            public void onNext(List<AVObject> avObjects) {
                for(AVObject obj : avObjects) {
                    // 判断是否推送字段
                    if((Boolean) obj.get("push")) {
                        notification(date);
                        break;
                    }
                }
            }

            @Override
            public void onError(Throwable e) { }

            @Override
            public void onComplete() { }
        });
    }

    public void notification(String date) {
        Intent in = new Intent(this, RecordDetail.class);
        in.putExtra("date", date);
        PendingIntent pi = PendingIntent.getActivity(this, 0, in, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        Notification notification = new NotificationCompat.Builder(this, "push")
                .setAutoCancel(true)
                .setDefaults(Notification.DEFAULT_ALL) // 使用系统默认的通知响铃和振动效果
                .setContentTitle("那年今日")
                .setContentText("历史上的今天好像发生了什么不得了的事情")
                .setSmallIcon(R.mipmap.ic_launcher)
                .setAutoCancel(true) // 点击后取消通知
                .setContentIntent(pi) // 点击跳转
                .build();
        manager.notify(2, notification);
    }
}
