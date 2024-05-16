package com.example.procatfirst.repository.data_storage_deprecated

import android.content.Context
import android.util.Log
import androidx.datastore.preferences.preferencesDataStore

/**
 * Класс для работы с долговременной памятью.
 * Получаем данные вот так:
 *          DataCoordinator.shared.userEmailPreferenceVariable
 * Обновляем данные в файле DataCoordinator+Update.kt
 *
 * Добавляем функционал для работы с новыми данными:
 *      Очень тяжело и во всех файликах надо правки вносить.
 *      Если сильно хочется, то можно по образцу попробовать.
 *      Можно мне написать, я умею, я сделаю.
 *      Постараюсь оптимизировать этот процесс, но думаю, что это будет не скоро.
 */
class DataCoordinatorOLD {
    companion object {
        val shared = DataCoordinatorOLD()
        const val identifier = "[DataCoordinator]"
    }
    // MARK: Variables
    // Create a variable for each preference, along with a default value.
    // This is to guarantee that if it can't find it it resets to a value that you can control.
    /// Sample String
    var userPhonePreferenceVariable: String = ""
    val defaultUserPhonePreferenceValue: String = ""
    var userEmailPreferenceVariable: String = ""
    val defaultUserEmailPreferenceValue: String = ""


    // MARK: Data Store Variables
    private val USER_PREFERENCES_NAME = "user_preferences"
    val Context.dataStore by preferencesDataStore(
        name = USER_PREFERENCES_NAME
    )

    // MARK: Lifecycle
    suspend fun initialize(onLoad: () -> Unit, context: Context) {
        Log.i(
            identifier,
            "READY"
        )
        // Load DataStore Settings
        // Update Sample String
        userPhonePreferenceVariable = getUserPhoneDataStore(context)
        userEmailPreferenceVariable = getUserEmailDataStore(context)
        // Update Sample Int
        onLoad()
    }

}