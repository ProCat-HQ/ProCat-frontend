package com.example.procatfirst

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.example.procatfirst.ui.theme.ProCatFirstTheme

import com.example.procatfirst.repository.data_storage_deprecated.DataCoordinatorOLD
import com.example.procatfirst.intents.NotificationCoordinator
import com.example.procatfirst.repository.data_storage.DataStorage

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
                    ProCatApp()
                }
            }
        }
    }

    override fun onDestroy() {
        println("ShutDown") // Обработка закрытия приложения - можно кэш в файл сохранить
        super.onDestroy()
    }

    private fun initBackground() {
        NotificationCoordinator.shared.initialize(baseContext)

        DataStorage.shared.initialize(baseContext)

        DataCoordinatorOLD.shared.initialize(
            context = baseContext,
            onLoad = {
                //DataCoordinator.shared.updateUserEmail(DataCoordinator.shared.defaultUserEmailPreferenceValue)
            }
        )
    }
    
}

