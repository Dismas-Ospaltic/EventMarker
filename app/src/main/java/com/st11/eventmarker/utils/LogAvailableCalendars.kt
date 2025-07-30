package com.st11.eventmarker.utils

import android.content.Context
import android.provider.CalendarContract
import android.util.Log

fun logAvailableCalendars(context: Context) {
    val projection = arrayOf(
        CalendarContract.Calendars._ID,
        CalendarContract.Calendars.CALENDAR_DISPLAY_NAME
    )
    val uri = CalendarContract.Calendars.CONTENT_URI

    context.contentResolver.query(uri, projection, null, null, null)?.use { cursor ->
        while (cursor.moveToNext()) {
            val id = cursor.getLong(0)
            val name = cursor.getString(1)
            Log.d("CalendarDebug", "Calendar ID: $id, Name: $name")
        }
    }
}
