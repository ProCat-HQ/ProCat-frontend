package com.example.procatfirst.repository.data_storage_deprecated

import android.content.Context
import android.util.Log
import androidx.datastore.preferences.core.edit
import com.example.procatfirst.repository.data_storage_deprecated.DataCoordinatorOLD.Companion.identifier
import kotlinx.coroutines.flow.firstOrNull

// Please note that the DataStore functionality must be called within a coroutine.
suspend fun DataCoordinatorOLD.getRefreshTokenDataStore(context: Context): String {
    return context.dataStore.data.firstOrNull()?.get(PreferencesKeys.refreshToken)
        ?: defaultRefreshTokenValue
}

suspend fun DataCoordinatorOLD.setRefreshTokenDataStore(context: Context, value: String) {
    Log.i(
        identifier,
        "user email old: $refreshTokenPreferenceVariable"
    )
    context.dataStore.edit { preferences ->
        preferences[PreferencesKeys.refreshToken] = value
        Log.i(
            identifier,
            "user email new: $refreshTokenPreferenceVariable"
        )
    }
}
