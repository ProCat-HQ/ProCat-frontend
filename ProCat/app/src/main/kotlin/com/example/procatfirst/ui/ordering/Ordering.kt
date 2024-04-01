package com.example.procatfirst.ui.ordering

import android.app.TimePickerDialog
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
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
import androidx.compose.material3.SelectableDates
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OrderingScreen(
    orderingViewModel: OrderingViewModel = viewModel(),
) {
    var delivery by remember { mutableStateOf(true) }


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


        DateTimePickerComponent(
        )
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


@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DateTimePickerComponent(
    //onDateSelected: (String) -> Unit,

    ) {

    var showDatePicker by remember { mutableStateOf(false) }
    val datePickerState = rememberDatePickerState(selectableDates = object : SelectableDates {
        override fun isSelectableDate(utcTimeMillis: Long): Boolean {
            return utcTimeMillis >= System.currentTimeMillis()
        }
    })
    val selectedDate = datePickerState.selectedDateMillis?.let {
        convertMillisToDate(it)
    } ?: ""

    var showTimePicker by remember { mutableStateOf(false) }
    val timePickerState = rememberTimePickerState()
    val selectedTime = timePickerState.hour

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {

        Text(text = "Выбранная дата: $selectedDate", modifier = Modifier.padding(bottom = 16.dp))

        Button(
            onClick = {
                showDatePicker = true
            },
            modifier = Modifier.fillMaxWidth(),
        ) {
            Text(text = stringResource(R.string.pick_date))
        }

        Divider(modifier = Modifier.padding(vertical = 24.dp))

        Text(text = "Выбранное время: $selectedTime", modifier = Modifier.padding(bottom = 16.dp))


        Button(
            onClick = {
                showTimePicker = true
            },
            modifier = Modifier.fillMaxWidth(),
        ) {
            Text(text = stringResource(R.string.pick_time))
        }

    }

    if (showDatePicker) {
        DatePickerDialog(
            onDismissRequest = { /*TODO*/ },
            confirmButton = {
                TextButton(
                    onClick = {
                        showDatePicker = false
                        //onDateSelected(selectedDate)
                    }
                ) { Text("OK") }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        showDatePicker = false
                    }
                ) { Text("Закрыть") }
            }        )
        {
            DatePicker(state = datePickerState)
        }
    }


    // time picker component
    if (showTimePicker) {
        TimePickerDialog(
            onDismissRequest = { /*TODO*/ },
            confirmButton = {
                TextButton(
                    onClick = {
                        showTimePicker = false
                    }
                ) { Text("OK") }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        showTimePicker = false
                    }
                ) { Text("Закрыть") }
            }
        )
        {
            TimePicker(state = timePickerState)
        }
    }

}

@Composable
fun TimePickerDialog(
    title: String = "Выберите время",
    onDismissRequest: () -> Unit,
    confirmButton: @Composable (() -> Unit),
    dismissButton: @Composable (() -> Unit)? = null,
    containerColor: Color = MaterialTheme.colorScheme.surface,
    content: @Composable () -> Unit,
) {
    Dialog(
        onDismissRequest = onDismissRequest,
        properties = DialogProperties(
            usePlatformDefaultWidth = false
        ),
    ) {
        Surface(
            shape = MaterialTheme.shapes.extraLarge,
            //tonalElevation = 6.dp,
            modifier = Modifier
                .width(IntrinsicSize.Min)
                .height(IntrinsicSize.Min)
                .background(
                    shape = MaterialTheme.shapes.extraLarge,
                    color = containerColor
                ),
            color = containerColor
        ) {
            Column(
                modifier = Modifier.padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 20.dp),
                    text = title,
                    style = MaterialTheme.typography.labelMedium
                )
                content()
                Row(
                    modifier = Modifier
                        .height(40.dp)
                        .fillMaxWidth()
                ) {
                    Spacer(modifier = Modifier.weight(1f))
                    dismissButton?.invoke()
                    confirmButton()
                }
            }
        }
    }
}

private fun convertMillisToDate(millis: Long): String {
    val formatter = SimpleDateFormat("dd.MM.yyyy")
    return formatter.format(Date(millis))
}



@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun OrderingPreview() {
    ProCatFirstTheme {
        OrderingScreen()
    }
}