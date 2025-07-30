package com.st11.eventmarker.utils

import android.content.ContentValues
import android.content.Context
import android.provider.CalendarContract
import android.util.Log
import android.widget.Toast
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.TimeZone



//fun addEventToCalendar(
//    context: Context,
//    title: String,
//    date: String,      // "2025-07-30"
//    startTime: String, // "14:00"
//    endTime: String,   // "15:00"
//    location: String?
//) {
//    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")
//    val startDateTime = LocalDateTime.parse("$date $startTime", formatter)
//    val endDateTime = LocalDateTime.parse("$date $endTime", formatter)
//
//    val startMillis = startDateTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()
//    val endMillis = endDateTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()
//
//    val calendarId = getDefaultCalendarId(context)
//    if (calendarId == null) {
//        Toast.makeText(context, "No writable calendar found", Toast.LENGTH_LONG).show()
//        return
//    }
//
//    val values = ContentValues().apply {
//        put(CalendarContract.Events.DTSTART, startMillis)
//        put(CalendarContract.Events.DTEND, endMillis)
//        put(CalendarContract.Events.TITLE, title)
//        put(CalendarContract.Events.EVENT_LOCATION, location ?: "")
//        put(CalendarContract.Events.CALENDAR_ID, calendarId)
//        put(CalendarContract.Events.EVENT_TIMEZONE, TimeZone.getDefault().id)
//    }
//
//    val uri = context.contentResolver.insert(CalendarContract.Events.CONTENT_URI, values)
//
//    if (uri != null) {
//        Toast.makeText(context, "Event added to calendar", Toast.LENGTH_SHORT).show()
//    } else {
//        Toast.makeText(context, "Failed to add event", Toast.LENGTH_SHORT).show()
//    }
//    logAvailableCalendars(context)
//}


fun addEventToCalendar(
    context: Context,
    title: String,
    date: String,      // "2025-07-30"
    startTime: String, // "14:00"
    endTime: String,   // "15:00"
    location: String?
) {
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")
    val startDateTime = LocalDateTime.parse("$date $startTime", formatter)
    val endDateTime = LocalDateTime.parse("$date $endTime", formatter)

    val startMillis = startDateTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()
    val endMillis = endDateTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()

    val cursor = context.contentResolver.query(
        CalendarContract.Calendars.CONTENT_URI,
        arrayOf(
            CalendarContract.Calendars._ID,
            CalendarContract.Calendars.CALENDAR_DISPLAY_NAME,
            CalendarContract.Calendars.CALENDAR_ACCESS_LEVEL,
            CalendarContract.Calendars.OWNER_ACCOUNT
        ),
        "${CalendarContract.Calendars.VISIBLE} = 1 AND ${CalendarContract.Calendars.CALENDAR_ACCESS_LEVEL} >= ${CalendarContract.Calendars.CAL_ACCESS_CONTRIBUTOR}",
        null,
        null
    )

    var addedCount = 0

    cursor?.use {
        while (it.moveToNext()) {
            val calendarId = it.getLong(it.getColumnIndexOrThrow(CalendarContract.Calendars._ID))
            val calendarName = it.getString(it.getColumnIndexOrThrow(CalendarContract.Calendars.CALENDAR_DISPLAY_NAME))

            val values = ContentValues().apply {
                put(CalendarContract.Events.DTSTART, startMillis)
                put(CalendarContract.Events.DTEND, endMillis)
                put(CalendarContract.Events.TITLE, title)
                put(CalendarContract.Events.EVENT_LOCATION, location ?: "")
                put(CalendarContract.Events.CALENDAR_ID, calendarId)
                put(CalendarContract.Events.EVENT_TIMEZONE, TimeZone.getDefault().id)
            }

            val uri = context.contentResolver.insert(CalendarContract.Events.CONTENT_URI, values)

            if (uri != null) {
                addedCount++
                Log.d("CalendarEvent", "Event added to: $calendarName (ID: $calendarId)")
            } else {
                Log.w("CalendarEvent", "Failed to add to: $calendarName (ID: $calendarId)")
            }
        }
    }

    if (addedCount > 0) {
        Toast.makeText(context, "Event added to $addedCount calendar(s)", Toast.LENGTH_SHORT).show()
    } else {
        Toast.makeText(context, "No events added. No writable calendars found.", Toast.LENGTH_LONG).show()
    }
}


