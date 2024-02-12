package com.example.procatfirst.ui.personal.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.procatfirst.R
import com.example.procatfirst.ui.theme.ProCatFirstTheme
import com.example.procatfirst.ui.theme.md_theme_light_scrim
import com.example.procatfirst.ui.theme.md_theme_light_tertiary

@Composable
fun ProfileScreen(
    //onNextButtonClicked: () -> Unit,
    //personalViewModel: PersonalViewModel = viewModel(),
    //modifier: Modifier = Modifier
) {
    Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = "Имя",
            style = MaterialTheme.typography.titleLarge,
        )

        mutableField(
            "Телефон",
            "89642130784"
        )
        mutableField(
            "ИНН",
            "00000000000"
        )
        mutableField(
            "Электронная почта",
            "email@gmail.com"
        )

        Button(
            modifier = Modifier.fillMaxWidth().padding(16.dp),
            onClick = { }
        ) {
            Text(
                text = stringResource(R.string.change_password),
                fontSize = 16.sp
            )
        }
    }


}

@Composable
fun mutableField(
    title: String = "",
    data: String = ""
) {
    Row(
        //modifier = Modifier.padding(16.dp),
        modifier = Modifier.fillMaxWidth().padding(16.dp)

    ){
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.bodyLarge,
            )
            Text(
                text = data,
                style = MaterialTheme.typography.bodyMedium,
            )
        }
        IconButton(
            onClick = { },
        ) {
            Icon(
                imageVector = Icons.Default.Edit,
                contentDescription = stringResource(R.string.edit)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ProfilePreview() {
    ProCatFirstTheme {
        ProfileScreen()
    }
}