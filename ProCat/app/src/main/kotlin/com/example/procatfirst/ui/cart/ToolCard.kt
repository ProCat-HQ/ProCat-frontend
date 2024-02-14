package com.example.procatfirst.ui.cart

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.unit.sp
import com.example.procatfirst.data.Tool
import com.example.procatfirst.repository.data_storage_deprecated.DataCoordinatorOLD
import com.example.procatfirst.intents.SystemNotifications
import com.example.procatfirst.repository.cache.UserCartCache
import com.example.procatfirst.repository.data_coordinator.DataCoordinator
import com.example.procatfirst.ui.IntentsReceiverAbstractObject
import java.lang.Thread.sleep

@Composable
fun ToolCard() {
    /*
    var isActive by remember { mutableStateOf(DataCoordinator.shared.toolsInCartPreferenceVariable.isNotEmpty())}
    if(isActive) {
        val tools: MutableList<Tool> = DataCoordinator.shared.toolsInCartPreferenceVariable
        var toolName by remember {mutableStateOf(tools[0].name)}
        var toolImgId by remember { mutableIntStateOf(tools[0].imageResId) }
        val receiver: IntentsReceiverAbstractObject = object : IntentsReceiverAbstractObject() {
            override fun howToReactOnIntent() {
                toolName = tools[0].name
                toolImgId = tools[0].imageResId
            }
        }
        val receiver1: IntentsReceiverAbstractObject = object : IntentsReceiverAbstractObject() {
            override fun howToReactOnIntent() {
                isActive = false
            }
        }
        receiver1.CreateReceiver(intentToReact = SystemNotifications.delInCartIntent)
        receiver.CreateReceiver(intentToReact = SystemNotifications.myTestIntent)

        val cardRow = Row (Modifier.background(Color.LightGray)) {
            Image(
                painter = painterResource(id = toolImgId),
                contentDescription = stringResource(id = R.string.hammer),
                modifier = Modifier
                    .size(60.dp)
                    .aspectRatio(1.0f) // Сохраняет соотношение сторон 1:1
                    .padding(top = 2.dp, bottom = 2.dp)
            )
            Text(text = toolName, fontSize = 24.sp)
            Spacer(Modifier.size(5.dp))
            Button(onClick = { DataCoordinator.shared.updateRemoveToolsInCart(tools[0]) }) {
                Text(text = "удалить", fontSize = 14.sp)
            }
        }
    } else {
        Text(text = "Ваша корзина пуста", fontSize = 18.sp)
    }
    
*/
    var tools by remember { mutableStateOf(DataCoordinator.shared.getUserCart()) }
    var isActive by remember { mutableStateOf(true) }
    val receiver1: IntentsReceiverAbstractObject = object : IntentsReceiverAbstractObject() {
        override fun howToReactOnIntent() {
            isActive = false
            tools = DataCoordinator.shared.getUserCart()
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