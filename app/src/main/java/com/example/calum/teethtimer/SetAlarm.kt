package com.example.calum.teethtimer

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.nfc.Tag
import android.os.SystemClock
import android.provider.Settings.Global.getString
import android.provider.Settings.Global.putString
import android.util.Log
import android.widget.Toast
import java.util.*

class SetAlarm {

    val MORNING_REQUEST_CODE = 505
    val EVENING_REQUEST_CODE = 606

    private var alarmMgr: AlarmManager? = null
    private lateinit var alarmIntent: PendingIntent

    fun setAlarm(context: Context, hour: Int, minute: Int, morningOrEvening: String) {

        val sharedPreferences = context?.getSharedPreferences(R.string.preference_file_key.toString(), Context.MODE_PRIVATE)
        var alarmTime = String.format("%01d:%02d", hour, minute)

        if (morningOrEvening == "Morning") {
            alarmMgr = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
            alarmIntent = Intent(context, AlarmReceiver::class.java).let { intent ->
                PendingIntent.getBroadcast(context, MORNING_REQUEST_CODE, intent.putExtra("timeOfAlarm", "Morning"), PendingIntent.FLAG_UPDATE_CURRENT)
            }
            sharedPreferences.edit().putString(R.string.morning_alarm_time.toString(), alarmTime).apply()
        } else if (morningOrEvening == "Evening") {
            alarmMgr = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
            alarmIntent = Intent(context, AlarmReceiver::class.java).let { intent ->
                PendingIntent.getBroadcast(context, EVENING_REQUEST_CODE, intent.putExtra("timeOfAlarm", "Evening"), PendingIntent.FLAG_UPDATE_CURRENT)
            }
            sharedPreferences.edit().putString(R.string.evening_alarm_time.toString(), alarmTime).apply()
        }

        val now: Calendar = Calendar.getInstance()
        val calendar: Calendar = Calendar.getInstance().apply {
            timeInMillis = System.currentTimeMillis()
            set(Calendar.HOUR_OF_DAY, hour)
            set(Calendar.MINUTE, minute)
        }
        if (calendar.before(now)) {
            calendar.timeInMillis += 86400000L
        }

        alarmMgr?.setInexactRepeating(
                AlarmManager.RTC_WAKEUP,
                calendar.timeInMillis,
                AlarmManager.INTERVAL_DAY,
                alarmIntent
        )

        Toast.makeText(context, morningOrEvening + " Alarm set for " + alarmTime, Toast.LENGTH_SHORT).show()
        Log.i(TAG, morningOrEvening + " Alarm set for: " + hour + ":" + minute)
    }
}