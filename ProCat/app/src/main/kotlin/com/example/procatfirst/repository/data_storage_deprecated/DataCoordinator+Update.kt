package com.example.procatfirst.repository.data_storage_deprecated

import android.content.Context

// MARK: DataStore Update Functionality
/**
 * Методы для обновления данных  долговременной памяти.
 */
suspend fun DataCoordinatorOLD.updateUserEmail(context: Context, value: String) {
    // Update Value
    this.userEmailPreferenceVariable = value
    // Save to System
    // Update DataStore
    setRefreshTokenDataStore(context, value)
}

// MARK: DataStore Update Functionality
suspend fun DataCoordinatorOLD.updateUserPhone(context: Context, value: String) {
    // Update Value
    this.userPhonePreferenceVariable = value
    // Save to System
    // Update DataStore
    setUserPhoneDataStore(context, value)
}