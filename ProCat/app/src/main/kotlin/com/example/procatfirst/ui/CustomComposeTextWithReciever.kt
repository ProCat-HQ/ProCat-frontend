package com.example.procatfirst.ui

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.unit.sp
import com.example.procatfirst.repository.data_storage_deprecated.DataCoordinatorOLD
import com.example.procatfirst.intents.SystemNotifications

@Composable
fun CustomComposeTextWithReceiver(){

    var text by remember { mutableStateOf(DataCoordinatorOLD.shared.userEmailPreferenceVariable) }

    // MARK: Visual
    Text(text = text, fontSize = 24.sp)

    // MARK: Receivers

    val receiver: IntentsReceiverAbstractObject = object : IntentsReceiverAbstractObject() {
        override fun howToReactOnIntent() {
            text = "Wow it works!!!"
        }
    }

    receiver.CreateReceiver(intentToReact = SystemNotifications.myTestIntent)

    val receiver1: IntentsReceiverAbstractObject = object : IntentsReceiverAbstractObject() {
        override fun howToReactOnIntent() {
            text = DataCoordinatorOLD.shared.userEmailPreferenceVariable
        }
    }

    receiver1.CreateReceiver(intentToReact = SystemNotifications.gotUserDataIntent)

}