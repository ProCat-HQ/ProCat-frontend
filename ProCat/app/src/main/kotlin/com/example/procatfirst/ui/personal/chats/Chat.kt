package com.example.procatfirst.ui.personal.chats

import androidx.compose.runtime.Composable
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults.textFieldColors
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.BottomCenter
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.procatfirst.R
import com.example.procatfirst.data.Message
import com.example.procatfirst.data.User
import com.example.procatfirst.data.UserDataProvider.users
import com.example.procatfirst.ui.theme.md_theme_light_inversePrimary
import com.example.procatfirst.ui.theme.md_theme_light_outline
import com.example.procatfirst.ui.theme.md_theme_light_scrim
import com.example.procatfirst.ui.theme.md_theme_light_secondaryContainer
import com.example.procatfirst.ui.theme.md_theme_light_tertiaryContainer


@Composable
fun ChatScreen (
    chatViewModel: ChatViewModel = viewModel()
) {
    val chatUiState by chatViewModel.uiState.collectAsState()
    val companion = users[2]

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(md_theme_light_scrim)
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            UserNameRow(
                user = companion,
                name = chatUiState.chatTheme,
                modifier = Modifier.padding(top = 60.dp, start = 20.dp, end = 20.dp, bottom = 20.dp)
            )
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        color = MaterialTheme.colorScheme.background,
                        shape = RoundedCornerShape(
                            topStart = 30.dp, topEnd = 30.dp
                        )
                    )
                    .padding(top = 25.dp)

            ) {
                LazyColumn(
                    modifier = Modifier.padding(
                        start = 15.dp,
                        top = 25.dp,
                        end = 15.dp,
                        bottom = 75.dp
                    )
                ) {
                    items(chatViewModel.messages, key = { it.messageId }) {
                        ChatRow(message = it, currentUserId = 2)
                    }
                }
            }
        }
        CustomTextField(
            text = chatViewModel.userInputMessage, onValueChange = { chatViewModel.updateTextMessage(it) },
            modifier = Modifier
                .padding(horizontal = 20.dp, vertical = 20.dp)
                .align(BottomCenter),
            chatViewModel = chatViewModel
        )
    }

}

@Composable
fun ChatRow(
    message: Message,
    currentUserId: Int
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = if (message.userId == currentUserId) Alignment.End else Alignment.Start
    ) {
        Box(
            modifier = Modifier
                .background(
                    if (message.userId == currentUserId) md_theme_light_tertiaryContainer else md_theme_light_secondaryContainer,
                    RoundedCornerShape(100.dp)
                ),
            contentAlignment = Center
        ) {
            Text(
                text = message.text, style = TextStyle(
                    color = md_theme_light_scrim,
                    fontSize = 15.sp
                ),
                modifier = Modifier.padding(vertical = 8.dp, horizontal = 15.dp),
                textAlign = TextAlign.End
            )
        }
        Text(
            text = message.date,
            style = TextStyle(
                color = md_theme_light_outline,
                fontSize = 12.sp
            ),
            modifier = Modifier.padding(vertical = 8.dp, horizontal = 15.dp),
        )
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomTextField(
    text: String,
    modifier: Modifier = Modifier,
    onValueChange: (String) -> Unit,
    chatViewModel: ChatViewModel
) {
    TextField(
        value = text, onValueChange = { onValueChange(it) },
        placeholder = {
            Text(
                text = stringResource(R.string.type_message),
                style = TextStyle(
                    fontSize = 14.sp,
                    color = md_theme_light_scrim
                ),
                textAlign = TextAlign.Center
            )
        },
        colors = textFieldColors(
            containerColor = md_theme_light_secondaryContainer,
            unfocusedIndicatorColor = Color.Transparent,
            focusedIndicatorColor = Color.Transparent
        ),
        leadingIcon = { CommonIconButton(imageVector = Icons.Default.Add) {
            chatViewModel.sendMessage(
                text
            )
        }
        },
        modifier = modifier.fillMaxWidth(),
        shape = CircleShape,
        keyboardOptions = KeyboardOptions.Default.copy(
            imeAction = ImeAction.Done
        ),
        keyboardActions = KeyboardActions(
            onDone = { chatViewModel.sendMessage(text) }
        )
    )

}

@Composable
fun CommonIconButton(
    imageVector: ImageVector,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .clickable(onClick = onClick)
            .background(md_theme_light_inversePrimary, CircleShape)
            .size(33.dp),
        contentAlignment = Center,
    ) {
        Icon(
            imageVector = imageVector,
            contentDescription = null,
            tint = Color.White
        )
    }
}




@Composable
fun UserNameRow(
    modifier: Modifier = Modifier,
    name: String,
    user: User
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row {

            Column {
                Text(
                    text = name, style = TextStyle(
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp
                    )
                )
                Text(
                    text = user.fullName, style = TextStyle(
                        color = Color.White,
                        fontSize = 14.sp
                    )
                )
            }
        }
    }
}
