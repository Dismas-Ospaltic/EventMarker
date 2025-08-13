package com.st11.eventmarker.data.datastore



import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map


// Extension property for DataStore
private val Context.userDataStore: DataStore<Preferences> by preferencesDataStore(name = "notify_prefs")

class NotificationPreferences(private val context: Context) {

    companion object {
        val IS_NOTIFY_ENABLED = booleanPreferencesKey("is_notify_enabled")
    }

    // Save full user data (on login)
    suspend fun saveUserData() {
        context.userDataStore.edit { preferences ->
            preferences[IS_NOTIFY_ENABLED] = true
        }
    }



    // Observe user data (Flow)
    val userData: Flow<UserData> = context.userDataStore.data.map { preferences ->
        UserData(
            isNotificationEnabled = preferences[IS_NOTIFY_ENABLED] ?: false
        )
    }

    // Clear all data (on logout)
    suspend fun clearUserData() {
        context.userDataStore.edit { it.clear() }
    }
}

// Data class for structured access
data class UserData(
    val isNotificationEnabled: Boolean
)