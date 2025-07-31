//package com.st11.eventmarker.utils
//
//
//import android.app.*
//import android.content.Context
//import android.content.Intent
//import android.os.Build
//import java.time.LocalDateTime
//import java.time.ZoneId
//
//fun scheduleReminder(
//    context: Context,
//    reminderTime: LocalDateTime,
//    title: String,
//    message: String
//) {
//    val requestCode = reminderTime.hashCode()
//
//    val intent = Intent(context, ReminderReceiver::class.java).apply {
//        putExtra("title", title)
//        putExtra("message", message)
//    }
//
//    val pendingIntent = PendingIntent.getBroadcast(
//        context,
//        requestCode,
//        intent,
//        PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
//    )
//
//    val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
//    val triggerAtMillis = reminderTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()
//
//    alarmManager.setExactAndAllowWhileIdle(
//        AlarmManager.RTC_WAKEUP,
//        triggerAtMillis,
//        pendingIntent
//    )
//}


package com.st11.eventmarker.utils

import android.app.*
import android.content.Context
import android.content.Intent
import android.os.Build
import android.provider.Settings
import android.widget.Toast
import java.time.LocalDateTime
import java.time.ZoneId

/**
 * Schedules an exact alarm with title and message at the given reminder time.
 */
fun scheduleReminder(
    context: Context,
    reminderTime: LocalDateTime,
    title: String,
    message: String
) {
    val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

    // ⚠️ For Android 12 and above, check if app can schedule exact alarms
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S && !alarmManager.canScheduleExactAlarms()) {
        Toast.makeText(context, "Please allow exact alarms in system settings.", Toast.LENGTH_LONG).show()

        // Open the system settings screen to request the permission
        val intent = Intent(Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        context.startActivity(intent)
        return
    }

    val intent = Intent(context, ReminderReceiver::class.java).apply {
        putExtra("title", title)
        putExtra("message", message)
    }

    val pendingIntent = PendingIntent.getBroadcast(
        context,
        reminderTime.hashCode(),
        intent,
        PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
    )

    val triggerAtMillis = reminderTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()

    // ⏰ Schedule the alarm
    alarmManager.setExactAndAllowWhileIdle(
        AlarmManager.RTC_WAKEUP,
        triggerAtMillis,
        pendingIntent
    )
}


//val viewModel: EventNotifyViewModel = koinViewModel()
//
//val time = LocalDateTime.of(2025, 8, 4, 9, 30)
//val title = "Team Meeting"
//val message = "Don't forget to join at 9:30 AM"
//
//LaunchedEffect(Unit) {
//    viewModel.scheduleEventNotification(context, time, title, message)
//}
