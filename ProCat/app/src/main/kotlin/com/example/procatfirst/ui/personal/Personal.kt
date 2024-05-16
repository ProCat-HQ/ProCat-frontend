package com.example.procatfirst.ui.personal

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.procatfirst.R
import com.example.procatfirst.repository.cache.UserDataCache
import com.example.procatfirst.ui.theme.ProCatFirstTheme

@Composable
fun PersonalScreen(
    onToProfileClicked: () -> Unit,
    onToOrdersClicked: () -> Unit,
    onToNotificationsClicked: () -> Unit,
    onToChatsClicked:() -> Unit,
    onToDeliveryClicked:() -> Unit,
    onToManagerClicked:() -> Unit,
    personalViewModel: PersonalViewModel = PersonalViewModel(),
    uiState: State<PersonalUiState> = personalViewModel.uiState.collectAsState()
) {

    var showDialog by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Center,
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

        val userRole = UserDataCache.shared.getUserRole()

        if(userRole == "delivery" || userRole == "admin") {
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                onClick = { onToDeliveryClicked() }
            ) {
                Text(
                    text = "Я курьер",
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
        Button(
            colors = ButtonColors(Color.Red, Color.White, Color.Cyan, Color.Magenta),
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            onClick = { showDialog = true}
        ) {
            Text(
                text = "Выйти из аккаунта",
                fontSize = 16.sp
            )
        }
    }

    if (showDialog) {
        ConfirmLogoutDialog(
            onCancel = { showDialog = false},
            onContinue = {
                personalViewModel.confirmLogout()
                showDialog = false
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