package com.example.procatfirst.ui.cart

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.procatfirst.data.Tool
import com.example.procatfirst.intents.NotificationCoordinator
import com.example.procatfirst.intents.SystemNotifications
import com.example.procatfirst.intents.sendIntent
import com.example.procatfirst.repository.data_coordinator.DataCoordinator
import com.example.procatfirst.ui.IntentsReceiverAbstractObject
import kotlinx.coroutines.launch

@Composable
fun ToolCard(toolViewModel: ToolCardViewModel = viewModel()) {

    val authUiState by toolViewModel.uiState.collectAsState()

    var t = emptyList<Tool>()
    toolViewModel.viewModelScope.launch {
        t = DataCoordinator.shared.getUserCart()
        //NotificationCoordinator.shared.sendIntent(SystemNotifications.cartLoaded)
    }

    var tools by remember {
        mutableStateOf(t)
    }

    var isActive by remember { mutableStateOf(true) }
    val receiver1: IntentsReceiverAbstractObject = object : IntentsReceiverAbstractObject() {
        override fun howToReactOnIntent() {
            isActive = false
            toolViewModel.viewModelScope.launch { t = DataCoordinator.shared.getUserCart() }
            //tools = t
            isActive = true
        }
    }
    receiver1.CreateReceiver(intentToReact = SystemNotifications.delInCartIntent)
    receiver1.CreateReceiver(intentToReact = SystemNotifications.cartLoaded)

    if(tools.isNotEmpty() && isActive) {
        ToolsScreenCart(tools)
    } else {
        Text(text = "Ваша корзина пуста", fontSize = 18.sp)
    }

}