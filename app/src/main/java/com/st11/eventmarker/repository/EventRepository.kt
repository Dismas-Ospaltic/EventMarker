package com.st11.eventmarker.repository


import com.st11.eventmarker.data.local.EventDao
import com.st11.eventmarker.model.EventEntity
import com.st11.eventmarker.utils.formatDate
import kotlinx.coroutines.flow.Flow

class EventRepository(private val eventDao: EventDao) {

    private val currentDate = System.currentTimeMillis()

     fun getAllPastDateEvents(): Flow<List<EventEntity>> {
         val formattedDate = formatDate(currentDate) // Should return "yyyy/MM/dd"
         return eventDao.getAllPastDateEvents(formattedDate)
     }

    fun getAllUpcomingDateEvents(): Flow<List<EventEntity>> {
        val formattedDate = formatDate(currentDate) // Should return "yyyy/MM/dd"
        return eventDao.getAllUpcomingDateEvents(formattedDate)
    }

    suspend fun insert(event: EventEntity) {
        eventDao.insertEvent(event)
    }

    suspend fun update(event: EventEntity) {
        eventDao.updateEvent(event)
    }

    suspend fun  updateEventsDetails(eventId: String, eventTitle: String, eventVenue: String, eventPriority: String, eventCategory: String, noteDescription: String,eventDate: String): Boolean {
        val rowsUpdated = eventDao.updateEventsDetails(eventId, eventTitle, eventVenue, eventPriority, eventCategory, noteDescription, eventDate) ?: 0
        return rowsUpdated > 0
    }





}
