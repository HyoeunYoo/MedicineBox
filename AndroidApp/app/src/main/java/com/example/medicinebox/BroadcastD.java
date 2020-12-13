package com.example.medicinebox;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;

public class BroadcastD extends BroadcastReceiver {
    String INTENT_ACTION = Intent.ACTION_BOOT_COMPLETED;

    @Override
    public void onReceive(Context context, Intent intent) {//알람 시간이 되었을때 onReceive를 호출함
        //NotificationManager 안드로이드 상태바에 메세지를 던지기위한 서비스 불러오고
        NotificationManager notificationmanager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, new Intent(context, MainActivity.class), PendingIntent.FLAG_UPDATE_CURRENT);

        //Notification.Builder builder = new Notification.Builder(context);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "default");

        //OREO API 26 이상에서는 채널 필요
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {

            builder.setSmallIcon(R.drawable.icon); //mipmap 사용시 Oreo 이상에서 시스템 UI 에러남


            String channelName ="Medicine Box Alarm";
            String description = "복용 알림";
            int importance = NotificationManager.IMPORTANCE_HIGH; //소리와 알림메시지를 같이 보여줌

            NotificationChannel channel = new NotificationChannel("default", channelName, importance);
            channel.setDescription(description);

            if (notificationmanager != null) {
                // 노티피케이션 채널을 시스템에 등록
                notificationmanager.createNotificationChannel(channel);
            }
        }else builder.setSmallIcon(R.mipmap.ic_launcher); // Oreo 이하에서 mipmap 사용하지 않으면 Couldn't create icon: StatusBarIcon 에러남


        builder.setWhen(System.currentTimeMillis())
                .setNumber(1)
                .setContentTitle("복용 알림")
                .setContentText("약을 드실 시간입니다.")
                .setDefaults(NotificationCompat.DEFAULT_ALL)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .setContentIntent(pendingIntent);

        notificationmanager.notify(1, builder.build());
    }
}
