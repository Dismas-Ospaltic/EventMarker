package com.st11.eventmarker.data.local


import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.st11.eventmarker.model.EventEntity
import kotlinx.coroutines.flow.Flow

//This interface defines the database operations.
@Dao
interface EventDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEvent(event: EventEntity)

    @Update
    suspend fun updateEvent(event: EventEntity)


    @Query("SELECT * FROM events WHERE eventDate < :formattedDate  ORDER BY timestamp DESC")
    fun getAllPastDateEvents(formattedDate: String): Flow<List<EventEntity>>

    @Query("SELECT * FROM events WHERE eventDate >= :formattedDate  ORDER BY timestamp DESC")
    fun getAllUpcomingDateEvents(formattedDate: String): Flow<List<EventEntity>>

//
//    @Query("SELECT * FROM expenses ORDER BY timestamp DESC")
//    fun getAllExpenses(): Flow<List<ExpenseEntity>>

    @Query("UPDATE events SET eventTitle = :eventTitle, eventVenue = :eventVenue, eventPriority = :eventPriority, eventCategory = :eventCategory, noteDescription = :noteDescription WHERE eventId = :eventId")
    suspend fun updateEventsDetails(eventId: String, eventTitle: String, eventVenue: String, eventPriority: String, eventCategory: String, noteDescription: String): Int?


}