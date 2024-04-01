package com.example.procatfirst.repository

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
//import dagger.Component
//import dagger.Module
//import dagger.Provides
//import dagger.hilt.InstallIn
//import dagger.hilt.android.HiltAndroidApp
//import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import java.io.IOException
//import javax.inject.Singleton

/*
@HiltAndroidApp
class ProCatApplication: Application()

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideContext(application: Application): Context {
        return application.applicationContext
    }
    @Provides
    @Singleton
    fun provideUserRoleRepository(context: Context): UserRoleRepository {
        return UserRoleRepository(context)
    }
}

@Singleton
@Component(modules = [RepositoryModule::class])
interface AppComponent {
    fun getUserRoleRepository(): UserRoleRepository
}

 */

class UserRoleRepository(
    private val context: Context
){

    companion object {
        @SuppressLint("StaticFieldLeak")
        val shared : UserRoleRepository? = null
        private val Context.dataStore: DataStore<Preferences> by preferencesDataStore("user_role")
        val USER_ROLE = stringPreferencesKey("user_role")
        const val TAG = "UserRoleRepo"
    }


    private val userRole: Flow<String> = context.dataStore.data
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

    fun getUserRole() : Flow<String> {
        return userRole
    }


    suspend fun saveUserRole(userRole: String) {
        context.dataStore.edit { preferences ->
            preferences[USER_ROLE] = userRole
        }
    }

}