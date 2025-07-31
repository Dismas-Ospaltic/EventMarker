package com.st11.eventmarker.domain.usecase


import android.content.Context
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.workDataOf
import com.st11.eventmarker.worker.ReminderWorker
import java.util.concurrent.TimeUnit

object ReminderScheduler {
//    fun scheduleReminder(context: Context, hours: Long) {
//        val workRequest = PeriodicWorkRequestBuilder<ReminderWorker>(hours, TimeUnit.HOURS)
//            .build()
//
//        WorkManager.getInstance(context).enqueue(workRequest)
//    }

    fun scheduleOneTimeReminder(
        context: Context,
        title: String,
        message: String,
        scheduledTimeMillis: Long
    ) {
        val currentTimeMillis = System.currentTimeMillis()
        val delayMillis = scheduledTimeMillis - currentTimeMillis

        if (delayMillis <= 0) return // ⛔ Ignore past time

        val data = workDataOf(
            "title" to title,
            "message" to message
        )

        val workRequest = OneTimeWorkRequestBuilder<ReminderWorker>()
            .setInitialDelay(delayMillis, TimeUnit.MILLISECONDS)
            .setInputData(data)
            .build()

        WorkManager.getInstance(context)
            .enqueue(workRequest)
    }


}