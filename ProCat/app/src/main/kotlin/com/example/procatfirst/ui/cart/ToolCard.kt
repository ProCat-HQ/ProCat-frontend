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
import com.example.procatfirst.intents.SystemNotifications
import com.example.procatfirst.repository.data_coordinator.DataCoordinator
import com.example.procatfirst.ui.IntentsReceiverAbstractObject
import getUserCart
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Composable
fun ToolCard(
    toolViewModel: ToolCartViewModel = viewModel()
) {

    val toolUiState by toolViewModel.uiState.collectAsState()

    var tools by remember {
        mutableStateOf(emptyList<Tool>())
    }

    var isActive by remember { mutableStateOf(true) }

    toolViewModel.viewModelScope.launch {
        tools = withContext(Dispatchers.IO) {
            DataCoordinator.shared.getUserCart()
        }
    }

    val receiver1: IntentsReceiverAbstractObject = object : IntentsReceiverAbstractObject() {
        override fun howToReactOnIntent() {
            isActive = false
            toolViewModel.viewModelScope.launch { tools = DataCoordinator.shared.getUserCart() }
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