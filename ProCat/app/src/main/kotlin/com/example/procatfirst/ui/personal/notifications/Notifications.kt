package com.example.procatfirst.ui.personal.notifications

import android.app.Notification
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.procatfirst.R
import com.example.procatfirst.ui.theme.ProCatFirstTheme

@Composable
fun NotificationsScreen(

) {
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

        notification()
        notification()
        notification()

        FilledTonalButton(onClick = {  }) {
            Text(
                text = stringResource(R.string.ask_question)
            )
        }

    }
}

@Composable
fun notification(
    header: String = "Header",
    date: String = "12.02.2024",
    body: String = "He'll want to use your yacht, and I don't want this thing smelling like fish."
) {
    OutlinedCard(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface,
        ),
        border = BorderStroke(1.dp, Color.Black),
        modifier = Modifier
            .fillMaxWidth()
            .clip(MaterialTheme.shapes.small)
            .padding(8.dp)

    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ){
            Text(
                text = header,
                textAlign = TextAlign.Left,
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = date,
                textAlign = TextAlign.Right,
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Gray
            )
        }
        Text(
            text = body,
            textAlign = TextAlign.Left,
            modifier = Modifier
                .padding(16.dp),
            style = MaterialTheme.typography.bodyMedium

        )


    }
}

@Preview(showBackground = true)
@Composable
fun PersonalPreview() {
    ProCatFirstTheme {
        NotificationsScreen()
    }
}