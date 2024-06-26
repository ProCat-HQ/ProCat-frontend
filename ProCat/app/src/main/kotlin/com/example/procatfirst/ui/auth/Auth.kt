package com.example.procatfirst.ui.auth

import android.content.Context
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.procatfirst.R
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.procatfirst.intents.SystemNotifications
import com.example.procatfirst.ui.IntentsReceiverAbstractObject


@Composable
fun AuthScreen(
    onNextButtonClicked: () -> Unit,
    onToRegistrationClick: () -> Unit,
    authViewModel: AuthViewModel = viewModel(),
    context: Context
) {
    val authUiState by authViewModel.uiState.collectAsState()

    val receiver: IntentsReceiverAbstractObject = object : IntentsReceiverAbstractObject() {
        override fun howToReactOnIntent() {
            authViewModel.wrongPassword()
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
            userInputPassword = authViewModel.userInputPassword,
            onUserPasswordChanged = { authViewModel.updateUserPassword(it) },
            onKeyboardDone = { authViewModel.check() },
            isPasswordWrong = authUiState.enteredPasswordWrong,

            userInputPhoneNumber = authViewModel.userInputPhoneNumber,
            onUserPhoneNumberChanged = { authViewModel.updateUserPhoneNumber(it) },
            onKeyboardDone2 = { authViewModel.check() },
            isPhoneNumberWrong = authUiState.enteredPhoneNumberWrong,
        )


        Button(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            onClick = { authViewModel.signIn(onNextButtonClicked, context = context)}
        ) {
            Text(
                text = stringResource(R.string.sign_in),
                fontSize = 16.sp
            )
        }

        OutlinedButton(
            onClick = { onToRegistrationClick() },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(text = stringResource(R.string.to_register))
        }

        TextButton(

            onClick = { authViewModel.forgotPassword() },

            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = stringResource(R.string.forgot_password),
                fontSize = 16.sp
            )
        }

    }

}

@Composable
fun inputField(
    isPasswordWrong: Boolean,
    userInputPassword: String,
    onUserPasswordChanged: (String) -> Unit,
    onKeyboardDone: () -> Unit,

    isPhoneNumberWrong: Boolean,
    userInputPhoneNumber: String,
    onUserPhoneNumberChanged: (String) -> Unit,
    onKeyboardDone2: () -> Unit,
) {
    Column(
        modifier = Modifier.padding(16.dp)
    ) {
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
            visualTransformation = PasswordVisualTransformation(),
            isError = isPasswordWrong,
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