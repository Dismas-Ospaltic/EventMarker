package com.st11.eventmarker.utils

import android.icu.util.Calendar

fun getMillisFromDateTime(year: Int, month: Int, day: Int, hour: Int, minute: Int): Long {
    val calendar = Calendar.getInstance().apply {
        set(Calendar.YEAR, year)
        set(Calendar.MONTH, month - 1) // 0-based
        set(Calendar.DAY_OF_MONTH, day)
        set(Calendar.HOUR_OF_DAY, hour)
        set(Calendar.MINUTE, minute)
        set(Calendar.SECOND, 0)
        set(Calendar.MILLISECOND, 0)
    }
    return calendar.timeInMillis
}
