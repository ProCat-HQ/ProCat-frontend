package com.example.procatfirst.ui.cart

import android.content.Context
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.procatfirst.intents.SystemNotifications
import com.example.procatfirst.ui.IntentsReceiverAbstractObject

@Composable
fun ToolCard(
    toolViewModel: ToolCartViewModel = viewModel(),
    context: Context,
) {

    val toolUiState by toolViewModel.uiState.collectAsState()

    val receiver1: IntentsReceiverAbstractObject = object : IntentsReceiverAbstractObject() {
        override fun howToReactOnIntent() {
            toolViewModel.updateTools()
        }
    }
    receiver1.CreateReceiver(intentToReact = SystemNotifications.delInCartIntent)

    if(toolUiState.tools.items.isNotEmpty() && toolUiState.isActive) {
        ToolsScreenCart(toolUiState.tools, context)
    } else {
        Text(text = "Ваша корзина пуста", fontSize = 18.sp)
    }

}