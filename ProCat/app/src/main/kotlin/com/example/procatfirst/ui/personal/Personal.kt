package com.example.procatfirst.ui.personal

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.procatfirst.R
import com.example.procatfirst.repository.UserRoleRepository
import com.example.procatfirst.ui.auth.AuthViewModel
import com.example.procatfirst.ui.theme.ProCatFirstTheme

@Composable
fun PersonalScreen(
    onToProfileClicked: () -> Unit,
    onToOrdersClicked: () -> Unit,
    onToNotificationsClicked: () -> Unit,
    onToChatsClicked:() -> Unit,
    onToDeliveryClicked:() -> Unit,
    onToManagerClicked:() -> Unit,
) {
    Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        // Profile button
        Button(
            modifier = Modifier.fillMaxWidth().padding(16.dp),
            onClick = { onToProfileClicked() }
        ) {
            Text(
                text = stringResource(R.string.profile),
                fontSize = 16.sp
            )
        }
        // My orders button
        Button(
            modifier = Modifier.fillMaxWidth().padding(16.dp),
            onClick = { onToOrdersClicked() }
        ) {
            Text(
                text = stringResource(R.string.orders),
                fontSize = 16.sp
            )
        }

        // Notifications button
        Button(
            modifier = Modifier.fillMaxWidth().padding(16.dp),
            onClick = { onToNotificationsClicked() }
        ) {
            Text(
                text = stringResource(R.string.notifications),
                fontSize = 16.sp
            )
        }

        // Chats button
        Button(
            modifier = Modifier.fillMaxWidth().padding(16.dp),
            onClick = { onToChatsClicked() }
        ) {
            Text(
                text = stringResource(R.string.chats),
                fontSize = 16.sp
            )
        }

        if(UserRoleRepository.shared?.getUserRole().toString() == "delivery") {
            Button(
                modifier = Modifier.fillMaxWidth().padding(16.dp),
                onClick = { onToDeliveryClicked() }
            ) {
                Text(
                    text = "Я курьер",
                    fontSize = 16.sp
                )
            }
        }


        if(UserRoleRepository.shared?.getUserRole().toString() == "manager") {
            Button(
                modifier = Modifier.fillMaxWidth().padding(16.dp),
                onClick = { onToManagerClicked() }
            ) {
                Text(
                    text = "Я менеджер",
                    fontSize = 16.sp
                )
            }
        }

    }
}

@Preview(showBackground = true)
@Composable
fun PersonalPreview() {
    ProCatFirstTheme {
        //PersonalScreen()
    }
}