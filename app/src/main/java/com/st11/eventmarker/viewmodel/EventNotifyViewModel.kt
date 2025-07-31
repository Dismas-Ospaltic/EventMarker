package com.st11.eventmarker.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import com.st11.eventmarker.utils.scheduleReminder
import java.time.LocalDateTime

class EventNotifyViewModel : ViewModel() {

    fun scheduleEventNotification(
        context: Context,
        reminderTime: LocalDateTime,
        title: String,
        message: String
    ) {
        scheduleReminder(context, reminderTime, title, message)
    }
}


