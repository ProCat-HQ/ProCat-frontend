package com.example.procatfirst.ui.personal.chats

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.graphics.Color
import androidx.compose.runtime.Composable

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Divider
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.graphics.Color.Companion.Gray
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.procatfirst.data.User
import com.example.procatfirst.data.UserDataProvider.users
import com.example.procatfirst.ui.theme.ProCatFirstTheme
import com.example.procatfirst.ui.theme.md_theme_light_onPrimary
import com.example.procatfirst.ui.theme.md_theme_light_outline
import com.example.procatfirst.ui.theme.md_theme_light_scrim


@Composable
fun ListOfChatsScreen(
    onToChatClicked: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(md_theme_light_scrim)
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 30.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        md_theme_light_onPrimary, RoundedCornerShape(
                            topStart = 30.dp, topEnd = 30.dp
                        )
                    )
            ) {

                LazyColumn(
                    modifier = Modifier.padding(bottom = 15.dp, top = 30.dp)
                ) {
                    items(users, key = { it.userId }) {
                        UserEachRow(
                            user = it,
                            onToChatClicked = onToChatClicked
                        ) {
                            //navigation
                        }
                    }
                }
            }
        }
    }
}


@Composable
fun UserEachRow(
    user: User,
    onToChatClicked: () -> Unit,
    function: () -> Unit
) {

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .clickable { onToChatClicked() }
            .padding(horizontal = 20.dp, vertical = 5.dp),
    ) {
        Column {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row {
                    Column {
                        Text(
                            text = user.fullName, style = TextStyle(
                                color = md_theme_light_scrim, fontSize = 15.sp, fontWeight = FontWeight.Bold
                            )
                        )
                        Text( //lastMessage?
                            text = user.phoneNumber, style = TextStyle(
                                color = md_theme_light_outline, fontSize = 14.sp
                            )
                        )
                    }

                }
                Text( //date of last message?
                    text = user.role, style = TextStyle(
                        color = md_theme_light_outline, fontSize = 12.sp
                    )
                )
            }
            HorizontalDivider(modifier = Modifier.fillMaxWidth(), thickness = 1.dp, color = Black)
        }
    }

}

@Preview(showBackground = true)
@Composable
fun ChatsPreview() {
    ProCatFirstTheme {
        //ListOfChatsScreen()
    }
}