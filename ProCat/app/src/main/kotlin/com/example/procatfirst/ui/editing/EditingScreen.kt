package com.example.procatfirst.ui.editing

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.procatfirst.R

@Composable
fun EditingScreen(
    onToStoresClicked: () -> Unit,
    onToAllDeliverymenClicked: () -> Unit,
    onToItemsEditingClicked: () -> Unit
){
    Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            onClick = { onToStoresClicked() }
        ) {
            Text(
                text = stringResource(R.string.stores),
                fontSize = 16.sp
            )
        }

        Button(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            onClick = { onToAllDeliverymenClicked() }
        ) {
            Text(
                text = stringResource(R.string.deliverymen),
                fontSize = 16.sp
            )
        }

        Button(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            onClick = { onToItemsEditingClicked() }
        ) {
            Text(
                text = stringResource(R.string.items),
                fontSize = 16.sp
            )
        }

    }
}