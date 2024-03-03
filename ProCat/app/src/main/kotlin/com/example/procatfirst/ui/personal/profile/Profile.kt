package com.example.procatfirst.ui.personal.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.procatfirst.R
import com.example.procatfirst.ui.auth.AuthViewModel
import com.example.procatfirst.ui.theme.ProCatFirstTheme
import com.example.procatfirst.ui.theme.md_theme_light_scrim
import com.example.procatfirst.ui.theme.md_theme_light_tertiary

@Composable
fun ProfileScreen(
    profileViewModel: ProfileViewModel = viewModel(),
    //modifier: Modifier = Modifier
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

        Text(
            text = "Имя",
            style = MaterialTheme.typography.titleLarge,
        )
        Spacer(modifier = Modifier.height(16.dp))
        mutableField(
            stringResource(R.string.fullname),
            profileUiState.fullName,
            userInput = profileViewModel.userInputFullName,
            onUserInputChanged = { profileViewModel.updateUserFullName(it) },
            onKeyboardDone = { profileViewModel.saveUserFullName() },

            )
        mutableField(
            stringResource(R.string.phone),
            profileUiState.phoneNumber,
            userInput = profileViewModel.userInputPhoneNumber,
            onUserInputChanged = { profileViewModel.updateUserPhoneNumber(it) },
            onKeyboardDone = { profileViewModel.saveUserPhoneNumber() },
        )
        mutableField(
            stringResource(R.string.inn),
            profileUiState.identificationNumber,
            userInput = profileViewModel.userInputIdentificationNumber,
            onUserInputChanged = { profileViewModel.updateUserIdentificationNumber(it) },
            onKeyboardDone = { profileViewModel.saveUserIdentificationNumber() },
        )
        mutableField(
            stringResource(R.string.email),
            profileUiState.email,
            userInput = profileViewModel.userInputEmail,
            onUserInputChanged = { profileViewModel.updateUserEmail(it) },
            onKeyboardDone = { profileViewModel.saveUserEmail() },
            )
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

        if (isChangePasswordDialogVisible) {
            ChangePasswordDialog(
                onDismiss = { isChangePasswordDialogVisible = false },
                onChangePassword = { newPassword ->
                    // Handle password change
                }
            )
        }
    }


}

@Composable
fun mutableField(
    title: String = "",
    data: String = "",

    userInput: String,
    onUserInputChanged: (String) -> Unit,
    onKeyboardDone: () -> Unit,
) {
    var isChangeVisible by remember { mutableStateOf(false) }

    Column (
        modifier = Modifier
            .padding(8.dp)
    ){
        Row(

        ){
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
            }

        }
        HorizontalDivider(thickness = 1.dp, color = Color.LightGray)
    }

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
        title = { Text(if (isCheckingOldPassword) "Введите старый пароль" else "Введите новый пароль") },
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
                    label = { Text(if (isCheckingOldPassword) "Старый пароль" else "Новый пароль") },
                    visualTransformation = PasswordVisualTransformation(),
                    modifier = Modifier
                        .padding(0.dp, 8.dp)
                        .fillMaxWidth()
                )

                if (!isCheckingOldPassword) {
                    OutlinedTextField(
                        value = confirmNewPassword,
                        onValueChange = { confirmNewPassword = it },
                        label = { Text("Подтвердите новый пароль") },
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
                        // Check old password logic here
                        isCheckingOldPassword = false
                    } else {
                        if (newPassword == confirmNewPassword) {
                            onChangePassword(newPassword)
                            onDismiss()
                        } else {
                            // Handle password confirmation mismatch
                        }
                    }
                }
            ) {
                Text(if (isCheckingOldPassword) "Проверить" else "Сменить пароль")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Отмена")
            }
        }
    )
}


@Preview(showBackground = true)
@Composable
fun ProfilePreview() {
    ProCatFirstTheme {
        ProfileScreen()
    }
}