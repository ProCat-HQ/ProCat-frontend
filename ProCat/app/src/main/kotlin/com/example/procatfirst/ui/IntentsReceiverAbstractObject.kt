package com.example.procatfirst.ui

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.platform.LocalContext

/**
 * Абстрактный класс для работы с intents (сигналы).
 * Внутри своего Composable класса можно создать объект для отслеживания сигналов:
 *
 * val receiver: IntentsReceiverAbstractObject = object : IntentsReceiverAbstractObject() {
 *         override fun howToReactOnIntent() {
 *             TODO - тут пишем, что хотим делать при получении сигнала
 *         }
 *     }
 *
 *     receiver.CreateReceiver(intentToReact = SystemNotifications.<SIGNAL_NAME>)
 *
 *    <SIGNAL_NAME> - имя сигнала, на который реагируем.
 *    Его предварительно нужно описать по образцу в файле SystemNotifications в папке intents
 */
abstract class IntentsReceiverAbstractObject {

    var extras : Bundle? = null

    @Composable
    fun CreateReceiver(intentToReact: String) {
        // MARK: Variables
        val identifier = "[Comp.WithReceiver]"
        val context = LocalContext.current

        // MARK: BROADCAST / Notifications
        DisposableEffect(context) {
            // Declare Intents and Receiver
            val broadCast = object : BroadcastReceiver() {
                override fun onReceive(context: Context?, intent: Intent?) {

                    Log.i(
                        identifier,
                        "  $intent | ${intent?.extras}"
                    )

                    val action = intent?.action ?: return
                    when (action) {
                        intentToReact -> {
                            howToReactOnIntent()
                            Log.i(
                                identifier,
                                "got Intent"
                            )
                            // Check for Extras
                            extras = intent.extras
                            //val extra = intent.extras?.getString(SystemNotificationsExtras.myExtra)
                            }
                    }
                }
            }
            // Register Intents
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                context.registerReceiver(
                    broadCast,
                    IntentFilter(intentToReact),
                    Context.RECEIVER_NOT_EXPORTED
                )
            }

            // Dispose
            onDispose {
                context.unregisterReceiver(broadCast)
            }
        }
    }

    open fun howToReactOnIntent() {}

    fun getExtra(extra : String) : String? {
        return extras?.getString(extra)
    }

}