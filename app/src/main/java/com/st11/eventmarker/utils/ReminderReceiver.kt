package com.st11.eventmarker.utils



import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.media.AudioAttributes
import android.net.Uri
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import com.st11.eventmarker.R

//class ReminderReceiver : BroadcastReceiver() {
//    override fun onReceive(context: Context, intent: Intent) {
//        val title = intent.getStringExtra("title") ?: "Reminder"
//        val message = intent.getStringExtra("message") ?: "You have an upcoming event."
//
//        val channelId = "event_reminder_channel"
//
//        // Create notification channel (required for Android 8+)
//        val name = "Event Reminders"
//        val desc = "Notifications for scheduled events"
//        val importance = android.app.NotificationManager.IMPORTANCE_HIGH
//        val channel = android.app.NotificationChannel(channelId, name, importance).apply {
//            description = desc
//            setSound(
//                Uri.parse("android.resource://${context.packageName}/${R.raw.notify}"),
//                null
//            )
//        }
//        val notificationManager = context.getSystemService(NotificationManager::class.java)
//        notificationManager.createNotificationChannel(channel)
//
//        val builder = NotificationCompat.Builder(context, channelId)
//            .setSmallIcon(R.drawable.calendar) // replace with your own icon
//            .setContentTitle(title)
//            .setContentText(message)
//            .setAutoCancel(true)
//            .setPriority(NotificationCompat.PRIORITY_HIGH)
//            .setSound(Uri.parse("android.resource://${context.packageName}/${R.raw.notify}"))
//
////        with(NotificationManagerCompat.from(context)) {
////            notify(System.currentTimeMillis().toInt(), builder.build())
////        }
//
//
//        // Show notification if permission is granted (Android 13+)
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
//            if (ContextCompat.checkSelfPermission(applicationContext, android.Manifest.permission.POST_NOTIFICATIONS)
//                == PackageManager.PERMISSION_GRANTED) {
//                with(NotificationManagerCompat.from(applicationContext)) {
//                    notify(1001, builder.build())
//                }
//            }
//        } else {
//            with(NotificationManagerCompat.from(applicationContext)) {
//                notify(1001, builder.build())
//            }
//    }
//}


class ReminderReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        val title = intent.getStringExtra("title") ?: "Reminder"
        val message = intent.getStringExtra("message") ?: "You have an event!"

        val channelId = "event_reminder_channel"

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val soundUri = Uri.parse("android.resource://${context.packageName}/${R.raw.notify}")
            val attributes = AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_NOTIFICATION)
                .build()

            val channel = NotificationChannel(channelId, "Event Reminders", NotificationManager.IMPORTANCE_HIGH).apply {
                description = "Notifications for your events"
                setSound(soundUri, attributes)
            }

            val notificationManager = context.getSystemService(NotificationManager::class.java)
            notificationManager.createNotificationChannel(channel)
        }

        val builder = NotificationCompat.Builder(context, channelId)
            .setSmallIcon(R.drawable.calendar)
            .setContentTitle(title)
            .setContentText(message)
            .setAutoCancel(true)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setSound(Uri.parse("android.resource://${context.packageName}/${R.raw.notify}"))

        // Permission check (Android 13+)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (androidx.core.content.ContextCompat.checkSelfPermission(
                    context,
                    android.Manifest.permission.POST_NOTIFICATIONS
                ) == android.content.pm.PackageManager.PERMISSION_GRANTED
            ) {
                NotificationManagerCompat.from(context).notify(1001, builder.build())
            }
        } else {
            NotificationManagerCompat.from(context).notify(1001, builder.build())
        }
    }
}
