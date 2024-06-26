package com.example.procatfirst.ui.personal.profile

import android.content.Context
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.procatfirst.R
import com.example.procatfirst.ui.theme.ProCatFirstTheme
import com.example.procatfirst.ui.theme.md_theme_light_tertiary

@Composable
fun ProfileScreen(
    profileViewModel: ProfileViewModel = viewModel(),
    context: Context? = null,
) {
    var isChangePasswordDialogVisible by remember { mutableStateOf(false) }

    val profileUiState by profileViewModel.uiState.collectAsState()


    Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState())
            .padding(start = 16.dp, end = 16.dp, bottom = 16.dp)
        ,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(text = "Профиль", style = MaterialTheme.typography.titleLarge)
        Spacer(modifier = Modifier.height(16.dp))
        MutableField(
            stringResource(R.string.fullname),
            profileUiState.fullName,
            userInput = profileViewModel.userInputFullName,
            onUserInputChanged = { profileViewModel.updateUserFullName(it) },
            onKeyboardDone = { profileViewModel.saveUserFullName() },
            saveChanges = { profileViewModel.fullSaveUserFullName(it) },
            profileViewModel = profileViewModel
        )
        MutableField(
            stringResource(R.string.phone),
            profileUiState.phoneNumber,
            userInput = profileViewModel.userInputPhoneNumber,
            onUserInputChanged = { profileViewModel.updateUserPhoneNumber(it) },
            onKeyboardDone = { profileViewModel.saveUserPhoneNumber() },
            saveChanges = {profileViewModel.fullSaveUserPhoneNumber(it) },
            profileViewModel = profileViewModel
        )
        MutableField(
            stringResource(R.string.email),
            profileUiState.email,
            userInput = profileViewModel.userInputEmail,
            onUserInputChanged = { profileViewModel.updateUserEmail(it) },
            onKeyboardDone = { profileViewModel.saveUserEmail() },
            saveChanges = { profileViewModel.fullSaveUserEmail(it, context!!) },
            profileViewModel = profileViewModel
        )
        MutableField(
            stringResource(R.string.inn),
            profileUiState.identificationNumber,
            userInput = profileViewModel.userInputIdentificationNumber,
            onUserInputChanged = { profileViewModel.updateUserIdentificationNumber(it) },
            onKeyboardDone = { profileViewModel.saveUserIdentificationNumber() },
            saveChanges = { profileViewModel.fullSaveUserIdentificationNumber(it, context = context!!) },
            profileViewModel = profileViewModel
        )
        if (profileViewModel.uiState.value.isConfirmed) {
            Row(
                    modifier = Modifier.padding(16.dp)
            ) {
                Text(text = stringResource(R.string.inn_confirmed))
                Icon(
                        imageVector = Icons.Default.Check,
                        contentDescription = stringResource(R.string.inn_confirmed),
                        tint = md_theme_light_tertiary
                )
            }
        }
        FilledTonalButton(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            onClick = {
                isChangePasswordDialogVisible = true

            }
        ) {
            Text(
                text = stringResource(R.string.change_password),
                fontSize = 16.sp
            )
        }
    }

    if (profileUiState.errorDialog) {
        ErrorDialog(
            onDismiss = { profileViewModel.hideErrorDialog() },
            message = profileUiState.errorMessage,
        )
    }

    if (profileUiState.passwordDialog) {
        EnterPasswordDialog(
            onConfirm = { profileViewModel.action(it) }
        ) { profileViewModel.hidePasswordDialog() }
    }

        if (isChangePasswordDialogVisible) {
            ChangePasswordDialog(
                onDismiss = { isChangePasswordDialogVisible = false },
                onChangePassword = { newPassword ->
                    // Handle password change
                }
            )
        }


}

@Composable
fun MutableField(
    title: String = "",
    data: String = "",
    userInput: String,
    onUserInputChanged: (String) -> Unit,
    onKeyboardDone: () -> Unit,
    saveChanges: (String) -> Unit,

    profileViewModel: ProfileViewModel
) {
    var isChangeVisible by remember { mutableStateOf(false) }

    Column (
        modifier = Modifier
            .padding(8.dp)
    ){
        Row{
            Column(
                modifier = Modifier
                    .weight(5f)
                    .padding(16.dp)
            ) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.bodyLarge,
                )
                if (isChangeVisible) {
                    OutlinedTextField(
                        value = userInput,
                        onValueChange = {
                            onUserInputChanged(it)
                        },
                        keyboardOptions = KeyboardOptions.Default.copy(
                            imeAction = ImeAction.Done
                        ),
                        keyboardActions = KeyboardActions(
                            onDone = { onKeyboardDone() }
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                    )
                } else {
                    Text(
                        text = data,
                        style = MaterialTheme.typography.bodyMedium,
                    )
                }
            }
            Column (
                modifier = Modifier
                    .weight(1f)
                    .padding(16.dp)
            ){
                IconButton(
                    onClick = { isChangeVisible = !isChangeVisible},
                ) {
                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = stringResource(R.string.edit)
                    )
                }
                IconButton(
                    onClick = {
                        profileViewModel.showPasswordDialog { saveChanges(it) }
                    },
                ) {
                    Icon(
                        imageVector = Icons.Default.Check,
                        contentDescription = stringResource(R.string.save)
                    )
                }
            }

        }
        HorizontalDivider(thickness = 1.dp, color = Color.LightGray)
    }

}

@Composable
fun EnterPasswordDialog(
    onConfirm: (String) -> Unit,
    onDismiss: () -> Unit
) {
    var password by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = { onDismiss() },
        title = { Text(stringResource(R.string.enter_password)) },
        text = {
            Column {
                OutlinedTextField(
                    value = password,
                    onValueChange = {
                            password = it
                    },
                    label = { Text("Пароль для подтверждения") },
                    visualTransformation = PasswordVisualTransformation(),
                    modifier = Modifier
                        .padding(0.dp, 8.dp)
                        .fillMaxWidth()
                )
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    onConfirm(password)
                    onDismiss()
                }
            ) {
                Text(stringResource(R.string.modify))
            }
        }
    )
}

@Composable
fun ErrorDialog(message: String, onDismiss: () -> Unit) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Ошибка") },
        text = {Text(message)},
        confirmButton = {
            TextButton(onClick = onDismiss) {
                Text("Ok")
            }
        },
    )
}

@Composable
fun ChangePasswordDialog(
    onDismiss: () -> Unit,
    onChangePassword: (String) -> Unit
) {
    var oldPassword by remember { mutableStateOf("") }
    var newPassword by remember { mutableStateOf("") }
    var confirmNewPassword by remember { mutableStateOf("") }

    var isCheckingOldPassword by remember { mutableStateOf(true) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(if (isCheckingOldPassword) stringResource(R.string.enter_old_password) else stringResource(
            R.string.enter_new_password
        )
        ) },
        text = {
            Column {
                OutlinedTextField(
                    value = if (isCheckingOldPassword) oldPassword else newPassword,
                    onValueChange = {
                        if (isCheckingOldPassword) {
                            oldPassword = it
                        } else {
                            newPassword = it
                        }
                    },
                    label = { Text(if (isCheckingOldPassword) stringResource(R.string.old_password) else stringResource(
                        R.string.new_password
                    )
                    ) },
                    visualTransformation = PasswordVisualTransformation(),
                    modifier = Modifier
                        .padding(0.dp, 8.dp)
                        .fillMaxWidth()
                )

                if (!isCheckingOldPassword) {
                    OutlinedTextField(
                        value = confirmNewPassword,
                        onValueChange = { confirmNewPassword = it },
                        label = { Text(stringResource(R.string.confirm_old_password)) },
                        visualTransformation = PasswordVisualTransformation(),
                        modifier = Modifier
                            .padding(0.dp, 8.dp)
                            .fillMaxWidth()
                    )
                }
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    if (isCheckingOldPassword) {
                        isCheckingOldPassword = false
                    } else {
                        if (newPassword == confirmNewPassword) {
                            onChangePassword(newPassword)
                            onDismiss()
                        } else { // to do
                        }
                    }
                }
            ) {
                Text(if (isCheckingOldPassword) "Проверить" else "Сменить пароль")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(stringResource(R.string.cancellation))
            }
        }
    )
}
