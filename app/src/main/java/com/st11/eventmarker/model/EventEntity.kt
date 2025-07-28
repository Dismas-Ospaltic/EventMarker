package com.st11.eventmarker.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.st11.eventmarker.utils.formatDate

@Entity(tableName = "events")
data class EventEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val eventDate: String, //= formatDate(System.currentTimeMillis()), //yyyy/MM/dd
    val eventStartTime: String,
    val eventEndTime: String,
    val eventTitle: String,
    val eventVenue: String = null.toString(),
    val eventPriority: String,
    val eventCategory: String,
    val noteDescription: String = null.toString(),
    val eventId: String,
    val timestamp: Long = System.currentTimeMillis()
)