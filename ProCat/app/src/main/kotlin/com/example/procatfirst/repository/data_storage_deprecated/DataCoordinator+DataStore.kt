package com.example.procatfirst.repository.data_storage_deprecated

import android.content.Context
import android.util.Log
import androidx.datastore.preferences.core.edit
import com.example.procatfirst.repository.data_storage_deprecated.DataCoordinatorOLD.Companion.identifier
import kotlinx.coroutines.flow.firstOrNull

// MARK: Sample String Functionality
// Please note that the DataStore functionality must be called within a coroutine.
suspend fun DataCoordinatorOLD.getRefreshTokenDataStore(context: Context): String {
    return context.dataStore.data.firstOrNull()?.get(PreferencesKeys.userEmail)
        ?: defaultUserEmailPreferenceValue
}

suspend fun DataCoordinatorOLD.setRefreshTokenDataStore(context: Context, value: String) {
    Log.i(
        identifier,
        "user email old: $refreshTokenPreferenceVariable"
    )
    context.dataStore.edit { preferences ->
        preferences[PreferencesKeys.userEmail] = value
        Log.i(
            identifier,
            "user email new: $refreshTokenPreferenceVariable"
        )
    }
}

suspend fun DataCoordinatorOLD.getUserPhoneDataStore(context: Context): String {
    return context.dataStore.data.firstOrNull()?.get(PreferencesKeys.userPhone)
        ?: defaultUserPhonePreferenceValue
}

suspend fun DataCoordinatorOLD.setUserPhoneDataStore(context: Context, value: String) {
    Log.i(
        identifier,
        "ok"
    )
    context.dataStore.edit { preferences ->
        preferences[PreferencesKeys.userPhone] = value
        Log.i(
            identifier,
            "ok"
        )
    }
}