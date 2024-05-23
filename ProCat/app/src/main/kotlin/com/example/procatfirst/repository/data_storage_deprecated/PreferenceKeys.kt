package com.example.procatfirst.repository.data_storage_deprecated

import androidx.datastore.preferences.core.stringPreferencesKey

/**
 * Идентификаторы для данных, которые будем хранить в долговременной памяти.
 */
object PreferencesKeys {
    val userEmail = stringPreferencesKey("userEmailPreferenceKey")
    val userPhone = stringPreferencesKey("userPhonePreferenceKey")
}