package com.example.procatfirst.ui.ordering

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
import androidx.compose.material.Divider
import androidx.compose.material.Surface
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SelectableDates
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TimePicker
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.procatfirst.R
import java.text.SimpleDateFormat
import java.util.Date


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DateTimePickerComponent(
    orderingViewModel: OrderingViewModel,

    ) {
    val orderingUiState by orderingViewModel.uiState.collectAsState()


    var showDatePicker by remember { mutableStateOf(false) }
    val datePickerState = rememberDatePickerState(selectableDates = object : SelectableDates {
        override fun isSelectableDate(utcTimeMillis: Long): Boolean {
            return utcTimeMillis >= System.currentTimeMillis()
        }
    })
    val selectedDate = datePickerState.selectedDateMillis?.let {
        convertMillisToDate(it)
    } ?: ""

    var showTimePickerFrom by remember { mutableStateOf(false) }
    var showTimePickerTo by remember { mutableStateOf(false) }

    val timePickerStateFrom = rememberTimePickerState()
    val selectedTimeFromHour = timePickerStateFrom.hour
    val selectedTimeFromMinute = timePickerStateFrom.minute

    val timePickerStateTo = rememberTimePickerState()
    val selectedTimeToHour = timePickerStateTo.hour
    val selectedTimeToMinute = timePickerStateTo.minute


    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {

        Text(text = "Выбранная дата: $selectedDate", modifier = Modifier.padding(bottom = 16.dp))

        ElevatedButton(
            onClick = {
                showDatePicker = true
            },
            modifier = Modifier.fillMaxWidth(),
        ) {
            Text(text = stringResource(R.string.pick_date))
        }

        Divider(modifier = Modifier.padding(vertical = 24.dp))

        Text(text = "Выбранное время: ${orderingUiState.selectedTimeFromHour}:${orderingUiState.selectedTimeFromMinute} - ${orderingUiState.selectedTimeToHour}:${orderingUiState.selectedTimeToMinute}", modifier = Modifier.padding(bottom = 16.dp))


        Row() {
            ElevatedButton(
                onClick = {
                    showTimePickerFrom = true
                },
                modifier = Modifier.weight(3f),
            ) {
                Text(text = stringResource(R.string.from))
            }
            Spacer(modifier = Modifier.weight(1f))
            ElevatedButton(
                onClick = {
                    showTimePickerTo = true
                },
                modifier = Modifier.weight(3f),
            ) {
                Text(text = stringResource(R.string.to))
            }
        }


    }

    if (showDatePicker) {
        DatePickerDialog(
            onDismissRequest = { /*TODO*/ },
            confirmButton = {
                TextButton(
                    onClick = {
                        showDatePicker = false
                        orderingViewModel.updateSelectedDate(selectedDate)
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


    // time picker component From
    if (showTimePickerFrom) {
        TimePickerDialog(
            onDismissRequest = { /*TODO*/ },
            confirmButton = {
                TextButton(
                    onClick = {
                        showTimePickerFrom = false
                        orderingViewModel.updateSelectedTimeFrom(selectedTimeFromHour, selectedTimeFromMinute)
                    }
                ) { Text("OK") }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        showTimePickerFrom = false
                    }
                ) { Text("Закрыть") }
            }
        )
        {
            TimePicker(state = timePickerStateFrom)
        }
    }


    // time picker component To
    if (showTimePickerTo) {
        TimePickerDialog(
            onDismissRequest = { /*TODO*/ },
            confirmButton = {
                TextButton(
                    onClick = {
                        showTimePickerTo = false
                        orderingViewModel.updateSelectedTimeTo(selectedTimeToHour, selectedTimeToMinute)
                    }
                ) { Text("OK") }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        showTimePickerTo = false
                    }
                ) { Text("Закрыть") }
            }
        )
        {
            TimePicker(state = timePickerStateTo)
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
    val formatter = SimpleDateFormat("yyyy-MM-dd")
    return formatter.format(Date(millis))
}