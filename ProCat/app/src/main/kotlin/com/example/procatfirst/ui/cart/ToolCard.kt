package com.example.procatfirst.ui.cart

import android.util.Log
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.procatfirst.intents.SystemNotifications
import com.example.procatfirst.repository.data_coordinator.DataCoordinator
import com.example.procatfirst.ui.IntentsReceiverAbstractObject
import getUserCartPayload

@Composable
fun ToolCard(
    toolViewModel: ToolCartViewModel = viewModel()
) {

    val toolUiState by toolViewModel.uiState.collectAsState()

    val receiver1: IntentsReceiverAbstractObject = object : IntentsReceiverAbstractObject() {
        override fun howToReactOnIntent() {
            toolViewModel.changeIsActive(false)
            toolViewModel.updateTools()
            toolViewModel.changeIsActive(true)
        }
    }
    receiver1.CreateReceiver(intentToReact = SystemNotifications.delInCartIntent)
    receiver1.CreateReceiver(intentToReact = SystemNotifications.cartLoaded)

    if(toolUiState.tools.items.isNotEmpty() && toolUiState.isActive) {
        ToolsScreenCart(toolUiState.tools)
    } else {
        Text(text = "Ваша корзина пуста", fontSize = 18.sp)
    }
    Button(onClick = {
        Log.d("CART_UI", toolUiState.tools.items.toString())
        Log.d("CART_PAYLOAD", DataCoordinator.shared.getUserCartPayload().toString())
        toolViewModel.changeIsActive(false)
        toolViewModel.updateTools()
        toolViewModel.changeIsActive(true)
    }) {
        Text(text = "NEGR", fontSize = 18.sp)
    }

}