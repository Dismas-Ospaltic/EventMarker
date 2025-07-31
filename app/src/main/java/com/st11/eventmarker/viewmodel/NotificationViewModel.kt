package com.st11.eventmarker.viewmodel


import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.st11.eventmarker.domain.usecase.ReminderScheduler
import com.st11.eventmarker.utils.getMillisFromDateTime
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class NotificationViewModel() : ViewModel() {

    fun scheduleUserNotification(
        context: Context,
        title: String,
        message: String,
        year: Int,
        month: Int,
        day: Int,
        hour: Int,
        minute: Int
    ) {
        val scheduledMillis = getMillisFromDateTime(year, month, day, hour, minute)

        ReminderScheduler.scheduleOneTimeReminder(
            context = context,
            title = title,
            message = message,
            scheduledTimeMillis = scheduledMillis
        )
    }

}