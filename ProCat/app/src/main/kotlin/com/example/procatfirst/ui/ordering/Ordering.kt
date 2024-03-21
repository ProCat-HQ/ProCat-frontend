package com.example.procatfirst.ui.ordering

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerFormatter
import androidx.compose.material3.DatePickerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.procatfirst.R
import com.example.procatfirst.ui.theme.ProCatFirstTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OrderingScreen(
    orderingViewModel: OrderingViewModel = viewModel(),
) {
    var delivery by remember { mutableStateOf(true) }
    val selectedDate = remember { mutableStateOf("01/01/2023") }


    Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
        ,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(

        ) {

            Text(
                text = stringResource(R.string.delivery)
            )
            Switch(
                checked = delivery,
                onCheckedChange = {
                    delivery = it
                }
            )
        }

        // выбор даты
        // выбор адреса
        // залог?

        Text(text = "Selected Date: ${selectedDate.value}")

        Row (

        ){
            Text(
                text = stringResource(R.string.final_price),
                modifier = Modifier.weight(5f)

            )
            Text(
                text = "5.000$",
                modifier = Modifier.weight(1f)

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