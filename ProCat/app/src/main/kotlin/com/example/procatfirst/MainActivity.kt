package com.example.procatfirst

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Observer
import com.example.procatfirst.ui.theme.ProCatFirstTheme

import com.example.procatfirst.repository.data_storage_deprecated.DataCoordinatorOLD
import com.example.procatfirst.intents.NotificationCoordinator
import com.example.procatfirst.repository.data_coordinator.DataCoordinator
import ru.dublgis.dgismobile.mapsdk.*
import ru.dublgis.dgismobile.mapsdk.Map
import ru.dublgis.dgismobile.mapsdk.location.UserLocationOptions

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
                    //ProCatApp()
                    Button(onClick = { startActivity(Intent(this@MainActivity, MapActivity::class.java)) }) {
                        Text("Hello")
                    }
                }
            }
        }
    }

    override fun onDestroy() {
        Log.i (
            "[EXIT]", "EXIT!"
        ) // Обработка закрытия приложения - можно кэш в файл сохранить
        // PS - Обрабатывается смерть активити, например при смене ориентации
            // Закрытие пока никак не отлавливается
        super.onDestroy()
    }

    private fun initBackground() {
        NotificationCoordinator.shared.initialize(baseContext)
        DataCoordinator.shared.initialize(baseContext)

        DataCoordinatorOLD.shared.initialize(
            context = baseContext,
            onLoad = {
                //DataCoordinator.shared.updateUserEmail(DataCoordinator.shared.defaultUserEmailPreferenceValue)
            }
        )
    }
    
}

