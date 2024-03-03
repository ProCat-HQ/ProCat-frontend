package com.example.procatfirst.ui.ordering

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.procatfirst.R
import com.example.procatfirst.ui.theme.ProCatFirstTheme

@Composable
fun OrderingScreen(
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(

        ) {
            var checked by remember { mutableStateOf(true) }

            Text(
                text = stringResource(R.string.delivery)
            )
            Switch(
                checked = checked,
                onCheckedChange = {
                    checked = it
                }
            )
        }

    }
}


@Preview(showBackground = true)
@Composable
fun OrderingPreview() {
    ProCatFirstTheme {
        OrderingScreen()
    }
}