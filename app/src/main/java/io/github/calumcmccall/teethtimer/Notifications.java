package io.github.calumcmccall.teethtimer;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.provider.Settings;
import android.support.v4.app.NotificationCompat;

public class Notifications {

    private Context mContext;
    private NotificationManager mNotificationManager;
    private NotificationCompat.Builder mBuilder;
    public static final String NOTIFICATION_CHANNEL_ID = "10001";

    public Notifications(Context context){
        mContext = context;
    }

    public void createNotification(String title, String message) {
        Intent resultIntent = new Intent(mContext, MainActivity.class);
        resultIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        PendingIntent resultPendingIntent = PendingIntent.getActivity(mContext,
                0, resultIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);

        mBuilder = new NotificationCompat.Builder(mContext, NOTIFICATION_CHANNEL_ID);
        mBuilder.setSmallIcon(R.drawable.ic_notification);
        mBuilder.setContentTitle(title)
                .setContentText(message)
                .setAutoCancel(true)
                .setSound(Settings.System.DEFAULT_NOTIFICATION_URI)
                .setContentIntent(resultPendingIntent);

        mNotificationManager = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel notificationChannel = new NotificationChannel(NOTIFICATION_CHANNEL_ID, "Morning and Evening Alarm", importance);
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.RED);
            assert mNotificationManager != null;
            mBuilder.setChannelId(NOTIFICATION_CHANNEL_ID);
            mNotificationManager.createNotificationChannel(notificationChannel);
        }
        assert mNotificationManager != null;
        mNotificationManager.notify(0, mBuilder.build());
    }
}
