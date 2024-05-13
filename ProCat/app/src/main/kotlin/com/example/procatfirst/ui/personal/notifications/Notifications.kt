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
import androidx.compose.material3.Button
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
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
import com.example.procatfirst.ui.theme.ProCatFirstTheme
import com.example.procatfirst.ui.theme.md_theme_light_outline
import com.example.procatfirst.ui.theme.md_theme_light_scrim
import com.example.procatfirst.ui.theme.md_theme_light_surface
import com.example.procatfirst.ui.theme.md_theme_light_surfaceVariant
import com.example.procatfirst.ui.theme.md_theme_light_tertiaryContainer

@Composable
fun NotificationsScreen(
    context: Context,
    notificationViewModel: NotificationViewModel = viewModel(),
    ) {

    val notifications by notificationViewModel.notifications.collectAsState()

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


        notifications.forEach { notification ->
            notification(
                title = notification.title,
                date = notification.createdAt,
                description = notification.description,
                isViewed = notification.isViewed,
                onClick = {
                    notificationViewModel.markAsRead(notification)
                }
            )
        }

        //переходить в новый диалог с установленной темой
        FilledTonalButton(onClick = {  }) {
            Text(
                text = stringResource(R.string.ask_question)
            )
        }
        Button(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            onClick = { makeNotification(context = context, "Уведомление!", "Нажми и посмотри") }
        ) {
            Text(
                text = stringResource(R.string.send_notification),
                fontSize = 16.sp
            )
        }

    }
}

@Composable
fun notification(
    title: String,
    date: String,
    description: String,
    isViewed: Boolean,
    onClick: () -> Unit
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
                text = date,
                textAlign = TextAlign.Right,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.outline
            )
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
