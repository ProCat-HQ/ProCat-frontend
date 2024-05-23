package com.example.procatfirst.ui.cart

import android.content.Context
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.procatfirst.R


@Composable
fun Cart(
    modifier: Modifier = Modifier,
    onToOrderingClicked: () -> Unit,
    onGoToProfile: () -> Unit,
    cartViewModel: CartViewModel = viewModel(),
    context: Context,
) {
    val cartUiState by cartViewModel.uiState.collectAsState()

    Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(R.string.app_name),
            style = typography.titleLarge,
            color = MaterialTheme.colorScheme.onSurface
        )

        ToolCard(context = context)

        if (cartUiState.confirmIinDialog) {
            ConfirmInnDialog(
                onContinue = {
                    onToOrderingClicked()
                    cartViewModel.closeDialog()
                },
                onGoToProfile = {
                    onGoToProfile()
                    cartViewModel.closeDialog()
                },
                onCancel = {
                    cartViewModel.closeDialog()
                }
            )
        }

        if (cartUiState.emptyDialog) {
            EmptyDialog(
                onCancel = {
                    cartViewModel.closeDialog()
                }
            )
        }

        Button (
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            onClick = {
                cartViewModel.checkIsEmpty(onToOrderingClicked)
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

@Composable
fun EmptyDialog(
    onCancel: () -> Unit,

) {
    AlertDialog(
        title = {
            Text(text = "Ой")
        },
        text = {
            Text(text = "Ваша корзина пуста")
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
                Text(text = "OK")
            }
        },
    )
}





