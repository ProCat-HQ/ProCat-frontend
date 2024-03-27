package com.example.procatfirst

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.lifecycleScope
import com.example.procatfirst.intents.NotificationCoordinator
import com.example.procatfirst.repository.UserRoleRepository
import com.example.procatfirst.repository.data_coordinator.DataCoordinator
import com.example.procatfirst.repository.data_storage_deprecated.DataCoordinatorOLD
import com.example.procatfirst.ui.theme.ProCatFirstTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


//@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initBackground()

        setContent {
            ProCatFirstTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    ProCatApp(
                        this)
                }
            }
        }
    }

    override fun onDestroy() {
        Log.i (
            "[EXIT]", "EXIT!"
        ) // Обработка закрытия приложения - можно кэш в файл сохранить
        // PS - Обрабатывается смерть активити, например при смене ориентации
            // Закрытие приложения пока никак не отлавливается
        super.onDestroy()
    }

    private fun initBackground() {
        NotificationCoordinator.shared.initialize(baseContext)
        lifecycleScope.launch { DataCoordinator.shared.initialize(baseContext) }

        DataCoordinatorOLD.shared.initialize(
            context = baseContext,
            onLoad = {
                //DataCoordinator.shared.updateUserEmail(DataCoordinator.shared.defaultUserEmailPreferenceValue)
            }
        )
    }
    
}

