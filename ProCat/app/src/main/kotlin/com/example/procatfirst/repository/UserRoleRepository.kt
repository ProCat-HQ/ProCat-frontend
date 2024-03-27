package com.example.procatfirst.repository

import android.content.Context
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import java.io.IOException

class UserRoleRepository(
    private val context: Context
){

    private companion object {
        private val Context.dataStore: DataStore<Preferences> by preferencesDataStore("user_role")
        val USER_ROLE = stringPreferencesKey("user_role")
        const val TAG = "UserRoleRepo"
    }


    val userRole: Flow<String> = context.dataStore.data
        .catch {
            if(it is IOException) {
                Log.e(TAG, "Error reading preferences.", it)
                emit(emptyPreferences())
            } else {
                throw it
            }
        }
        .map { preferences ->
            preferences[USER_ROLE] ?: "guest"
        }


    suspend fun saveUserRole(userRole: String) {
        context.dataStore.edit { preferences ->
            preferences[USER_ROLE] = userRole
        }
    }
}