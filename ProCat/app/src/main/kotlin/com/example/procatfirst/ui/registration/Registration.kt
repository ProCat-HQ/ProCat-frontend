package com.example.procatfirst.ui.registration

import android.content.Context
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.shapes
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.TextFieldDefaults
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import com.example.procatfirst.ui.theme.ProCatFirstTheme
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewModelScope
import com.example.procatfirst.R
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.procatfirst.intents.SystemNotifications
import com.example.procatfirst.ui.IntentsReceiverAbstractObject

import com.example.procatfirst.ui.theme.md_theme_light_scrim
import com.example.procatfirst.ui.theme.md_theme_light_tertiary


@Composable
fun RegistrationScreen(
    context: Context,
    onNextButtonClicked: () -> Unit,
    onToAuthClick: () -> Unit,
    registrationViewModel: RegistrationViewModel = viewModel(),
    modifier: Modifier = Modifier
) {

    val registrationUiState by registrationViewModel.uiState.collectAsState()

    val receiver: IntentsReceiverAbstractObject = object : IntentsReceiverAbstractObject() {
        var counter = 0
        override fun howToReactOnIntent() {
            if (counter == 0) {
                counter++
            } else {
                onNextButtonClicked()
            }
        }
    }

    receiver.CreateReceiver(intentToReact = SystemNotifications.loginIntent)

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

        inputField(
            userInputLastName = registrationViewModel.userInputLastName,
            onUserLastNameChanged = { registrationViewModel.updateUserLastName(it) },
            onKeyboardDoneLn = { registrationViewModel.checkUserLastName() },
            isLastNameWrong = registrationUiState.enteredLastNameWrong,

            userInputFirstName = registrationViewModel.userInputFirstName,
            onUserFirstNameChanged = { registrationViewModel.updateUserFirstName(it) },
            onKeyboardDoneFn = { registrationViewModel.checkUserFirstName() },
            isFirstNameWrong = registrationUiState.enteredFirstNameWrong,

            userInputFatherName = registrationViewModel.userInputFatherName,
            onUserFatherNameChanged = { registrationViewModel.updateUserFatherName(it) },
            onKeyboardDoneFan = { registrationViewModel.checkUserFatherName() },
            isFatherNameWrong = registrationUiState.enteredFatherNameWrong,

            userInputPhoneNumber = registrationViewModel.userInputPhoneNumber,
            onUserPhoneNumberChanged = { registrationViewModel.updateUserPhoneNumber(it) },
            onKeyboardDone2 = { registrationViewModel.checkUserPhoneNumber() },
            isPhoneNumberWrong = registrationUiState.enteredPhoneNumberWrong,

            userInputPassword = registrationViewModel.userInputPassword,
            onUserPasswordChanged = { registrationViewModel.updateUserPassword(it) },
            onKeyboardDone = { registrationViewModel.checkUserPassword() },
            isPasswordWrong = registrationUiState.enteredPasswordWrong
        )


        Button(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            onClick = { registrationViewModel.signUp(onToAuthClick, context)}
        ) {
            Text(
                text = stringResource(R.string.register),
                fontSize = 16.sp
            )
        }
        OutlinedButton(
            onClick = { onToAuthClick() },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(text = stringResource(R.string.to_auth))
        }


        if (registrationViewModel.isInputCodeDialogVisible) {
            InputCodeDialog(registrationViewModel, registrationUiState)
        }

    }

}

@Composable
fun inputField(
    isLastNameWrong: Boolean,
    userInputLastName: String,
    onUserLastNameChanged: (String) -> Unit,
    onKeyboardDoneLn: () -> Unit,

    isFirstNameWrong: Boolean,
    userInputFirstName: String,
    onUserFirstNameChanged: (String) -> Unit,
    onKeyboardDoneFn: () -> Unit,

    isFatherNameWrong: Boolean,
    userInputFatherName: String,
    onUserFatherNameChanged: (String) -> Unit,
    onKeyboardDoneFan: () -> Unit,

    isPasswordWrong: Boolean,
    userInputPassword: String,
    onUserPasswordChanged: (String) -> Unit,
    onKeyboardDone: () -> Unit,

    isPhoneNumberWrong: Boolean,
    userInputPhoneNumber: String,
    onUserPhoneNumberChanged: (String) -> Unit,
    onKeyboardDone2: () -> Unit,

    modifier: Modifier = Modifier
) {
    Column(
        modifier = Modifier.padding(16.dp)
    ) {
        OutlinedTextField(
            value = userInputLastName,
            singleLine = true,
            shape = shapes.large,
            modifier = Modifier.fillMaxWidth(),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = colorScheme.surface,
                unfocusedContainerColor = colorScheme.surface,
                disabledContainerColor = colorScheme.surface,
            ),
            onValueChange = { onUserLastNameChanged(it) },
            label = {
                if (isLastNameWrong) {
                    Text(stringResource(R.string.wrong_last_name))
                } else {
                    Text(stringResource(R.string.enter_last_name))
                }
            },
            isError = isLastNameWrong,
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = { onKeyboardDoneLn() }
            )
        )

        OutlinedTextField(
            value = userInputFirstName,
            singleLine = true,
            shape = shapes.large,
            modifier = Modifier.fillMaxWidth(),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = colorScheme.surface,
                unfocusedContainerColor = colorScheme.surface,
                disabledContainerColor = colorScheme.surface,
            ),
            onValueChange = { onUserFirstNameChanged(it) },
            label = {
                if (isFirstNameWrong) {
                    Text(stringResource(R.string.wrong_first_name))
                } else {
                    Text(stringResource(R.string.enter_first_name))
                }
            },
            isError = isFirstNameWrong,
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = { onKeyboardDoneFn() }
            )
        )

        OutlinedTextField(
            value = userInputFatherName,
            singleLine = true,
            shape = shapes.large,
            modifier = Modifier.fillMaxWidth(),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = colorScheme.surface,
                unfocusedContainerColor = colorScheme.surface,
                disabledContainerColor = colorScheme.surface,
            ),
            onValueChange = { onUserFatherNameChanged(it) },
            label = {
                if (isFatherNameWrong) {
                    Text(stringResource(R.string.wrong_father_name))
                } else {
                    Text(stringResource(R.string.enter_father_name))
                }
            },
            isError = isFatherNameWrong,
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = { onKeyboardDoneFan() }
            )
        )

        OutlinedTextField(
            value = userInputPhoneNumber,
            singleLine = true,
            shape = shapes.large,
            modifier = Modifier.fillMaxWidth(),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = colorScheme.surface,
                unfocusedContainerColor = colorScheme.surface,
                disabledContainerColor = colorScheme.surface,
            ),
            onValueChange = { newValue ->
                val newPhoneNumber = if (newValue.startsWith("+")) newValue else "+$newValue"
                onUserPhoneNumberChanged(newPhoneNumber)
            },
            label = {
                if (isPhoneNumberWrong) {
                    Text(stringResource(R.string.wrong_phone_number))
                } else {
                    Text(stringResource(R.string.enter_phone_number))
                }
            },
            isError = isPhoneNumberWrong,
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = { onKeyboardDone2() }
            )
        )

        OutlinedTextField(
            value = userInputPassword,
            singleLine = true,
            shape = shapes.large,
            modifier = Modifier.fillMaxWidth(),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = colorScheme.surface,
                unfocusedContainerColor = colorScheme.surface,
                disabledContainerColor = colorScheme.surface,
            ),
            onValueChange = onUserPasswordChanged,
            label = {
                if (isPasswordWrong) {
                    Text(stringResource(R.string.wrong_password))
                } else {
                    Text(stringResource(R.string.enter_password))
                }
            },
            isError = isPasswordWrong,
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Done,
                keyboardType = KeyboardType.Password
            ),
            keyboardActions = KeyboardActions(
                onDone = { onKeyboardDone() }
            )
        )

    }
}


@Composable
fun InputCodeDialog(
    registrationViewModel: RegistrationViewModel,
    registrationUiState: RegistrationUiState
) {

    AlertDialog(
        onDismissRequest = {registrationViewModel.closeDialog() },
        confirmButton = {
            Button(
            onClick = {
                registrationViewModel.checkCode()
            }
        ) {
            Text("Подтвердить")
        } },
        dismissButton = {
            Button(
                onClick = {registrationViewModel.closeDialog()}
            ) {
                Text("Отмена")
            }
        },
        text = {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text("Введите код из SMS:")
                TextField(
                    value = registrationViewModel.userInputCode,
                    onValueChange = { registrationViewModel.updateUserCode(it) },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Done
                    ),
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    TextButton(onClick = {registrationViewModel.sendCodeAgain()}) {
                        Text("Выслать код повторно")
                    }
                }
            }
        }
    )
}


@Preview(showBackground = true)
@Composable
fun AuthPreview() {
    ProCatFirstTheme {
        //AuthScreen()
    }
}