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
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.graphics.Color.Companion.Gray
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.procatfirst.data.Person
import com.example.procatfirst.data.PersonList.personList
import com.example.procatfirst.ui.theme.ProCatFirstTheme


@Composable
fun ListOfChatsScreen(
    //navHostController: NavHostController
    onToChatClicked: () -> Unit

) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 30.dp)
        ) {
            //HeaderOrViewStory()
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Color.White, RoundedCornerShape(
                            topStart = 30.dp, topEnd = 30.dp
                        )
                    )
            ) {

                LazyColumn(
                    modifier = Modifier.padding(bottom = 15.dp, top = 30.dp)
                ) {
                    items(personList, key = { it.id }) {
                        UserEachRow(
                            person = it,
                            onToChatClicked = onToChatClicked
                        ) {
                            /*navHostController.currentBackStackEntry?.savedStateHandle?.set(
                                "data",
                                it
                            ) */
                            //navHostController.navigate(CHAT_SCREEN)
                        }
                    }
                }
            }
        }
    }
}


@Composable
fun UserEachRow(
    person: Person,
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
                            text = person.name, style = TextStyle(
                                color = Color.Black, fontSize = 15.sp, fontWeight = FontWeight.Bold
                            )
                        )
                        Text(
                            text = "okay", style = TextStyle(
                                color = Gray, fontSize = 14.sp
                            )
                        )
                    }

                }
                Text(
                    text = "_12_23_pm", style = TextStyle(
                        color = Gray, fontSize = 12.sp
                    )
                )
            }
            Divider(modifier = Modifier.fillMaxWidth(), thickness = 1.dp, color = Black)
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