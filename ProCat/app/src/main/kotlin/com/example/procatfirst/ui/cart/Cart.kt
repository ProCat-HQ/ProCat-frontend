package com.example.procatfirst.ui.cart

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.procatfirst.R
import com.example.procatfirst.intents.NotificationCoordinator
import com.example.procatfirst.intents.SystemNotifications
import com.example.procatfirst.intents.sendIntent
import com.example.procatfirst.ui.auth.AuthViewModel
import com.example.procatfirst.ui.theme.md_theme_light_scrim
import com.example.procatfirst.ui.theme.md_theme_light_tertiary


@Composable
fun Cart(
    modifier: Modifier = Modifier,
    onToOrderingClicked: () -> Unit

) {

    Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(R.string.app_name),
            style = typography.titleLarge,
            color = md_theme_light_scrim
        )

        ToolCard()

        Button (
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            //onClick = {NotificationCoordinator.shared.sendIntent(SystemNotifications.cartLoaded) }
            onClick = {onToOrderingClicked()}
        ) {
            Text(text = stringResource(R.string.checkout))
        }
    }

}




