package com.example.procatfirst.ui.cart

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.MaterialTheme.typography
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.viewModelScope
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
    onToOrderingClicked: () -> Unit,
    onGoToProfile: () -> Unit,
    cartViewModel: CartViewModel = viewModel()
) {
    var showDialog by remember { mutableStateOf(false) }

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

        if (showDialog) {
            ConfirmInnDialog(
                onContinue = {
                    onToOrderingClicked()
                    showDialog = false
                },
                onGoToProfile = {
                    onGoToProfile()
                    showDialog = false
                },
                onCancel = {
                    showDialog = false
                }
            )
        }
            Button (
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            onClick = {
                if (cartViewModel.checked) {
                    onToOrderingClicked()
                } else {
                    showDialog = true
                }
            }
        ) {
            Text(text = stringResource(R.string.checkout))
        }
    }

}

@Composable
fun ConfirmInnDialog(
    onGoToProfile: () -> Unit,
    onCancel: () -> Unit,
    onContinue: () -> Unit

) {
    AlertDialog(
        title = {
            Text(text = stringResource(R.string.confirm_inn))
        },
        text = {
            Text(text = stringResource(R.string.confirm_inn_text))
        },
        onDismissRequest = {
            onCancel()
        },
        confirmButton = {
            TextButton(
                onClick = {
                    onGoToProfile()
                }
            ) {
                Text(text = stringResource(R.string.to_personal))
            }
        },
        dismissButton = {
            TextButton(
                onClick = {
                    onContinue()
                }
            ) {
                Text(text = stringResource(R.string.next))
            }
        }
    )
}





