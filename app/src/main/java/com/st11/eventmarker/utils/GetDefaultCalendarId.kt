package com.st11.eventmarker.utils

import android.content.Context
import android.provider.CalendarContract

//fun getDefaultCalendarId(context: Context): Long? {
//    val projection = arrayOf(
//        CalendarContract.Calendars._ID,
//        CalendarContract.Calendars.CALENDAR_DISPLAY_NAME
//    )
//    val uri = CalendarContract.Calendars.CONTENT_URI
//
//    context.contentResolver.query(uri, projection, null, null, null)?.use { cursor ->
//        while (cursor.moveToNext()) {
//            val id = cursor.getLong(0)
//            val name = cursor.getString(1)
//            // You can filter by calendar name here if needed
//            return id
//        }
//    }
//    return null
//}

fun getDefaultCalendarId(context: Context): Long? {
    val projection = arrayOf(
        CalendarContract.Calendars._ID,
        CalendarContract.Calendars.CALENDAR_DISPLAY_NAME
    )
    val uri = CalendarContract.Calendars.CONTENT_URI

    return try {
        context.contentResolver.query(uri, projection, null, null, null)?.use { cursor ->
            while (cursor.moveToNext()) {
                val id = cursor.getLong(0)
                val name = cursor.getString(1)
                return id
            }
        }
        null
    } catch (e: SecurityException) {
        e.printStackTrace()
        null
    }
}

