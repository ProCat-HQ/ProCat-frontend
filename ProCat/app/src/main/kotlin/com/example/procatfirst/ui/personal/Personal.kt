package com.example.procatfirst.ui.personal

import android.content.Context
import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.procatfirst.R
import com.example.procatfirst.repository.cache.UserDataCache
import com.example.procatfirst.repository.data_coordinator.DataCoordinator
import com.example.procatfirst.repository.data_coordinator.getUserData
import com.example.procatfirst.ui.theme.ProCatFirstTheme

@Composable
fun PersonalScreen(
    context: Context,
    onToProfileClicked: () -> Unit,
    onToOrdersClicked: () -> Unit,
    onToNotificationsClicked: () -> Unit,
    onToChatsClicked:() -> Unit,
    onToDeliveryClicked:() -> Unit,
    onToManagerClicked:() -> Unit,
    onToAdminDeliveryClicked:() -> Unit,
    //onToAllDeliverymenClicked: () -> Unit,
    onToEditingClicked: () -> Unit,
    personalViewModel: PersonalViewModel = viewModel()
) {

    val personalUiState by personalViewModel.uiState.collectAsState()
    val userRole = UserDataCache.shared.getUserRole()


    Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        // Profile button
        Button(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            onClick = { onToProfileClicked() }
        ) {
            Text(
                text = stringResource(R.string.profile),
                fontSize = 16.sp
            )
        }
        // My orders button
        if(userRole != "admin") {
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                onClick = { onToOrdersClicked() }
            ) {
                Text(
                    text = stringResource(R.string.orders),
                    fontSize = 16.sp
                )
            }
        }

        // Notifications button
        Button(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            onClick = { onToNotificationsClicked() }
        ) {
            Text(
                text = stringResource(R.string.notifications),
                fontSize = 16.sp
            )
        }

        // Chats button
        Button(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            onClick = { onToChatsClicked() }
        ) {
            Text(
                text = stringResource(R.string.chats),
                fontSize = 16.sp
            )
        }


        if(userRole == "deliveryman" || userRole == "admin") {
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                onClick = { onToDeliveryClicked() }
            ) {
                Text(
                    text = "Заказы по доставке",
                    fontSize = 16.sp
                )
            }
        }

        if(userRole == "manager" || userRole == "admin") {
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                onClick = { onToManagerClicked() }
            ) {
                Text(
                    text = "Я менеджер",
                    fontSize = 16.sp
                )
            }
        }
        if(userRole == "admin") {
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                onClick = { onToAdminDeliveryClicked() }
            ) {
                Text(
                    text = stringResource(R.string.delivery),
                    fontSize = 16.sp
                )
            }
        }
        /*
        if(userRole == "admin") {
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                onClick = { onToAllDeliverymenClicked() }
            ) {
                Text(
                    text = stringResource(R.string.deliverymen),
                    fontSize = 16.sp
                )
            }
        } */
        if(userRole == "admin") {
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                onClick = { onToEditingClicked() }
            ) {
                Text(
                    text = stringResource(R.string.editing),
                    fontSize = 16.sp
                )
            }
        }
        Spacer(modifier = Modifier.weight(1f))
    }
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Bottom
    ){
        TextButton(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            onClick = { personalViewModel.openLogoutDialog() },
            colors = ButtonColors(MaterialTheme.colorScheme.background, MaterialTheme.colorScheme.error, MaterialTheme.colorScheme.errorContainer, MaterialTheme.colorScheme.onErrorContainer),

            ) {
            Text(
                text = "Выйти из аккаунта",
                fontSize = 16.sp
            )
        }
    }



    if (personalUiState.logout) {
        ConfirmLogoutDialog(
            onCancel = { personalViewModel.cancelLogout() },
            onContinue = {
                personalViewModel.confirmLogout(context)
                         },
        )
    }
}

@Composable
fun ConfirmLogoutDialog(
    onCancel: () -> Unit,
    onContinue: () -> Unit
) {
    Log.d("DIALOG", "DIALOG")
    AlertDialog(
        title = {
            Text(text = "Выход")
        },
        text = {
            Text(text = "ВЫ уверены, что хотите выйти из аккаунта?")
        },
        onDismissRequest = {
            onCancel()
        },
        confirmButton = {
            TextButton(
                onClick = {
                    onCancel()
                }
            ) {
                Text(text = "Отмена")
            }
        },
        dismissButton = {
            TextButton(
                onClick = {
                    onContinue()
                }
            ) {
                Text(text = "Да, я хочу выйти")
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
fun PersonalPreview() {
    ProCatFirstTheme {
        //PersonalScreen()
    }
}