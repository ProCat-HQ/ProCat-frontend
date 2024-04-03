package com.example.procatfirst.ui.ordering

import android.app.TimePickerDialog
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Divider
import androidx.compose.material.Surface
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DatePickerFormatter
import androidx.compose.material3.DatePickerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SelectableDates
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TimePicker
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.procatfirst.R
import com.example.procatfirst.ui.theme.ProCatFirstTheme
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun OrderingScreen(
    orderingViewModel: OrderingViewModel = viewModel(),
) {
    //var delivery by remember { mutableStateOf(true) }
    //var address by remember { mutableStateOf("") }

    val addresses = listOf(
        SelectionOption("Адрес 1", initialSelectedValue = true),
        SelectionOption("Адрес 2", initialSelectedValue = false)
    )
    val onOptionClicked: (SelectionOption) -> Unit = { selectedOption ->
        //selectedOption.selected = !selectedOption.selected
        addresses.forEach { it.selected = false }
        selectedOption.selected = true
        orderingViewModel.address = selectedOption.option
    }


    Column(
        modifier = Modifier
            //.verticalScroll(rememberScrollState())
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
                checked = orderingViewModel.delivery,
                onCheckedChange = {
                    orderingViewModel.delivery = it
                    orderingViewModel.address = ""
                }
            )
        }


        if (orderingViewModel.delivery) {
            OutlinedTextField(
                value = orderingViewModel.address,
                onValueChange = { orderingViewModel.address = it },
                label = { Text(text = stringResource(R.string.enter_address)) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp)
            )
        } else {
            Text(text = stringResource(R.string.choose_address_from_list))
            LazyColumn (){
                items(addresses) { option -> SingleSelectionCard(option, onOptionClicked) }
            }

        }

        DateTimePickerComponent(orderingViewModel)

        Spacer(modifier = Modifier.weight(1f))

        Column(
            
        ) {
            Row (){
                Text(
                    text = stringResource(R.string.final_price),
                    modifier = Modifier.weight(5f)

                )
                Text(
                    text = "5.000$",
                    modifier = Modifier.weight(1f)

                )
            }
            Button (
                modifier = Modifier
                    .fillMaxWidth(),
                onClick = {
                    //TODO
                    }
            ) {
                Text(text = stringResource(R.string.order))
            }
        }
        


    }
}

class SelectionOption(val option: String, var initialSelectedValue: Boolean) {
    var selected by mutableStateOf(initialSelectedValue)
}

@Composable
fun SingleSelectionCard(selectionOption: SelectionOption, onOptionClicked: (SelectionOption) -> Unit) {
    Surface(modifier = Modifier
        .fillMaxSize()
        .padding(horizontal = 8.dp, vertical = 4.dp)) {
        Surface(
            modifier = Modifier
                .border(1.dp, MaterialTheme.colorScheme.primary, RectangleShape)
                .clickable(true, onClick = { onOptionClicked(selectionOption) }),
            color = if (selectionOption.selected) { MaterialTheme.colorScheme.primary } else { MaterialTheme.colorScheme.background },
        ) {
            Row(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = selectionOption.option,
                    style = MaterialTheme.typography.bodyMedium,
                    color = if (selectionOption.selected) MaterialTheme.colorScheme.surface else MaterialTheme.colorScheme.onSurface

                )
            }
        }
    }
}



@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun OrderingPreview() {
    ProCatFirstTheme {
        OrderingScreen()
    }
}