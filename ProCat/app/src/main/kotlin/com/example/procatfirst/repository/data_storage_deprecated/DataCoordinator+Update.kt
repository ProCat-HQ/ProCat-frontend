package com.example.procatfirst.repository.data_storage_deprecated

import android.content.Context

// MARK: DataStore Update Functionality
/**
 * Методы для обновления данных  долговременной памяти.
 */
suspend fun DataCoordinatorOLD.updateRefreshToken(context: Context, value: String) {
    // Update Value
    this.refreshTokenPreferenceVariable = value
    // Save to System
    // Update DataStore
    setRefreshTokenDataStore(context, value)
}