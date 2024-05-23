package com.example.procatfirst.ui.personal.notifications

import android.content.Context
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.procatfirst.R
import com.example.procatfirst.repository.cache.UserDataCache
import com.example.procatfirst.ui.theme.ProCatFirstTheme
import com.example.procatfirst.ui.theme.md_theme_light_outline
import com.example.procatfirst.ui.theme.md_theme_light_scrim
import com.example.procatfirst.ui.theme.md_theme_light_surface
import com.example.procatfirst.ui.theme.md_theme_light_surfaceVariant
import com.example.procatfirst.ui.theme.md_theme_light_tertiaryContainer
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

@Composable
fun NotificationsScreen(
    context: Context,
    notificationViewModel: NotificationViewModel = viewModel()
) {
    val notificationsUiState by notificationViewModel.uiState.collectAsState()
    val userRole = UserDataCache.shared.getUserRole()
    var showDialog by remember { mutableStateOf(false) }


    Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        //verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(R.string.notifications),
            style = MaterialTheme.typography.titleLarge
        )


        notificationsUiState.notifications.forEach { notification ->
            notification(
                title = notification.title,
                date = notification.createdAt,
                description = notification.description,
                isViewed = notification.isViewed,
                onClick = {
                    notificationViewModel.markAsRead(notification)
                },
                onDelete = {
                    notificationViewModel.deleteNotification(notification)
                }
            )
        }

        FilledTonalButton(onClick = {  },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
        ) {
            Text(
                text = stringResource(R.string.ask_question)
            )
        }
        if (userRole == "admin") {
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                onClick = {
                    showDialog = true
                }
            ) {
                Text(
                    text = stringResource(R.string.send_notification),
                    fontSize = 16.sp
                )
            }
        }

        if (showDialog) {
            SendNotificationDialog(
                onDismiss = { showDialog = false },
                onSend = { userId, title, body ->
                    makeNotification(context = context, title, body)
                    notificationViewModel.sendNotification(userId, title, body)
                }
            )
        }

    }
}

fun formatTime(datetime: String): String {
    val zonedDateTime = ZonedDateTime.parse(datetime)
    val formatter = DateTimeFormatter.ofPattern("HH:mm")
    return zonedDateTime.format(formatter)
}

@Composable
fun notification(
    title: String,
    date: String,
    description: String,
    isViewed: Boolean,
    onClick: () -> Unit,
    onDelete: () -> Unit
) {
    var isCardClicked by remember { mutableStateOf(false) }

    val cardColor = if (isCardClicked) MaterialTheme.colorScheme.surfaceVariant else if (isViewed) MaterialTheme.colorScheme.surfaceVariant else MaterialTheme.colorScheme.primaryContainer

    OutlinedCard(
        colors = CardDefaults.cardColors(
            containerColor = cardColor,
        ),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme. scrim),
        modifier = Modifier
            .fillMaxWidth()
            .clip(MaterialTheme.shapes.small)
            .padding(8.dp)
            .clickable{
                isCardClicked = !isCardClicked
                onClick()
            }

    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ){
            Text(
                text = title,
                textAlign = TextAlign.Left,
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.weight(1f)

            )
            Text(
                text = formatTime(date),
                textAlign = TextAlign.Right,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.outline
            )
            /*
            IconButton(onClick = onDelete) {
                Icon(
                    imageVector = Icons.Filled.Close,
                    contentDescription = "Delete notification"
                )
            } */

        }
        Text(
            text = description,
            textAlign = TextAlign.Left,
            modifier = Modifier
                .padding(16.dp),
            style = MaterialTheme.typography.bodyMedium

        )


    }
}

@Composable
fun SendNotificationDialog(
    onDismiss: () -> Unit,
    onSend: (userId: Int, title: String, body: String) -> Unit
) {
    var userId by remember { mutableStateOf("") }
    var title by remember { mutableStateOf("") }
    var body by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(text = "Send Notification") },
        text = {
            Column {
                OutlinedTextField(
                    value = userId,
                    onValueChange = { userId = it },
                    label = { Text("User ID") },
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = title,
                    onValueChange = { title = it },
                    label = { Text("Title") },
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = body,
                    onValueChange = { body = it },
                    label = { Text("Body") },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },
        confirmButton = {
            TextButton(onClick = {
                onSend(userId.toInt(), title, body)
                onDismiss()
            }) {
                Text("Send")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}

