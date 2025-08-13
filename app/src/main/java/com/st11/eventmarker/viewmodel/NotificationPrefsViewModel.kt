package com.st11.eventmarker.viewmodel




import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.st11.eventmarker.data.datastore.NotificationPreferences
import com.st11.eventmarker.data.datastore.UserData
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class NotificationPrefsViewModel(
    private val notificationPreferences: NotificationPreferences
) : ViewModel() {

    // Expose user data as StateFlow (recommended for UI)
    val userData: StateFlow<UserData> = notificationPreferences.userData
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), UserData(false))

    // Save user data
    fun saveUserData() {
        viewModelScope.launch {
            notificationPreferences.saveUserData()
        }
    }

    // Clear user data (logout)
    fun clearUserData() {
        viewModelScope.launch {
            notificationPreferences.clearUserData()
        }
    }
}