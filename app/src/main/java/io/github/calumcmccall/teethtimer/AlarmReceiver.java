package io.github.calumcmccall.teethtimer;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import static android.content.ContentValues.TAG;

public class AlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        String timeOfAlarm = intent.getStringExtra("timeOfAlarm");

        Notifications notifications = new Notifications(context);

        if (timeOfAlarm.equals("Morning")) {
            notifications.createNotification("Teeth Timer", "Good morning! Time to brush your teeth.");
        } else if (timeOfAlarm.equals("Evening")) {
            notifications.createNotification("Teeth Timer", "Good evening! Time to brush your teeth.");
        }
    }

}
