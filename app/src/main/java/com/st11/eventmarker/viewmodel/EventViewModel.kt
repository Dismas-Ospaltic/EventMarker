package com.st11.eventmarker.viewmodel


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.st11.eventmarker.model.EventEntity
import com.st11.eventmarker.repository.EventRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class  EventViewModel(private val eventRepository: EventRepository) : ViewModel() {

    // Holds the list of events upcoming
    private val _events = MutableStateFlow<List<EventEntity>>(emptyList())
    val events: StateFlow<List<EventEntity>> = _events


    // Holds the list of stale events
    private val _pastEvents = MutableStateFlow<List<EventEntity>>(emptyList())
    val pastEvents: StateFlow<List<EventEntity>> = _pastEvents


    init {
        getAllEvents()
        getAllPastEvents()
    }

//    private val _isLoading = MutableStateFlow(true)
//    val isLoading: StateFlow<Boolean> = _isLoading

    private fun getAllEvents() {
        viewModelScope.launch {
            eventRepository.getAllUpcomingDateEvents().collectLatest { eventList ->
                _events.value = eventList
            }
        }
    }

    private fun getAllPastEvents() {
        viewModelScope.launch {
            eventRepository.getAllPastDateEvents().collectLatest { pastEventList ->
                _pastEvents.value = pastEventList
            }
        }
    }



    /**
     * Insert a new event record
     */
    fun insertEvent(event: EventEntity) {
        viewModelScope.launch {
            eventRepository.insert(event)
        }
    }

    /**
     * Update an existing event record
     */
    fun updateEvent(event: EventEntity) {
        viewModelScope.launch {
            eventRepository.update(event)
        }
    }

    /**
     * Update the details of events
     */
    fun updateEventsDetails(eventId: String, eventTitle: String, eventVenue: String, eventPriority: String, eventCategory: String, noteDescription: String) {
        viewModelScope.launch {
            val success = eventRepository.updateEventsDetails(eventId, eventTitle, eventVenue, eventPriority, eventCategory, noteDescription)
            if (success) {
                // Refresh debt list if update is successful
                _events.value = _events.value.map { event ->
                    if (event.eventId == eventId) event.copy(
                        eventTitle = eventTitle,
                       eventVenue = eventVenue,
                        eventPriority = eventPriority,
                        eventCategory = eventCategory,
                        noteDescription = noteDescription
                    ) else event
                }
            }
        }
    }


}