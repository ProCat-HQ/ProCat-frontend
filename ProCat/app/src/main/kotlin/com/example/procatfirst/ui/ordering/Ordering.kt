package com.example.procatfirst.ui.ordering

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Surface
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.procatfirst.R
import com.example.procatfirst.data.CartItem
import com.example.procatfirst.ui.theme.ProCatFirstTheme

@Composable
fun OrderingScreen(
    orderingViewModel: OrderingViewModel = viewModel(),
    onToConfirmationClicked: (OrderingViewModel) -> Unit,

    ) {

    //-----------------------------------------------------
    val orderingUiState by orderingViewModel.uiState.collectAsState()

    val addresses = listOf(
        SelectionOption("Адрес 1", initialSelectedValue = true),
        SelectionOption("Адрес 2", initialSelectedValue = false)
    )
    val onOptionClicked: (SelectionOption) -> Unit = { selectedOption ->
        addresses.forEach { it.selected = false }
        selectedOption.selected = true
        orderingViewModel.address = selectedOption.option
    }


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
            AddressSelectionSection(addresses, onOptionClicked)
        }

        DateTimePickerComponent(orderingViewModel)

        OutlinedTextField(
            value = orderingUiState.periodInDays.toString(),
            onValueChange = { try { orderingViewModel.updateSelectedPeriod(it.toInt()) }
            catch (e : NumberFormatException ) { orderingViewModel.updateSelectedPeriod(0)} },
            label = { Text("Длительность аренды в днях") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        )

        if(orderingViewModel.uiState.value.tools.isNotEmpty()) {
            ToolsScreenCartSmall(orderingViewModel.uiState.value.tools)
        } else {
            Text(text = "Ваша корзина пуста", fontSize = 18.sp)
        }

        Spacer(modifier = Modifier.weight(1f))
        var sum = 0
        for (t in orderingViewModel.uiState.value.tools) {
            sum += t.price * t.count
        }

        Column(

        ) {
            Row (){
                Text(
                    text = stringResource(R.string.final_price),
                    modifier = Modifier.weight(5f)
                )

                Text(
                    text = sum.toString(),
                    modifier = Modifier.weight(1f)

                )
            }
            Button (
                modifier = Modifier
                    .fillMaxWidth(),
                onClick = {
                    orderingViewModel.order()
                    onToConfirmationClicked(orderingViewModel)
                }
            ) {
                Text(text = stringResource(R.string.order))
            }
        }
    }
}

@Composable
fun AddressSelectionSection(
    addresses: List<SelectionOption>,
    onOptionClicked: (SelectionOption) -> Unit
) {
    Text(text = stringResource(R.string.choose_address_from_list))
    LazyColumn (
        modifier = Modifier.height(200.dp)
    ){
        items(addresses) { option -> SingleSelectionCard(option, onOptionClicked) }
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


@Composable
fun ToolsScreenCartSmall(
    tools: List<CartItem>
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        for (tool in tools)
            ToolCardCartSmall(tool = tool)
    }
}


@Composable
fun ToolCardCartSmall(
    tool: CartItem
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clip(MaterialTheme.shapes.small)
            .background(MaterialTheme.colorScheme.background)
            .clickable { /* Handle click on the card if needed */ }
            .padding(8.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(8.dp)
        ) {
            Column(
                modifier = Modifier
                    .weight(3f)
            ) {
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = tool.name,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(5.dp))
                Text(
                    text = (stringResource(id = R.string.tool_price, tool.price)),
                    style = MaterialTheme.typography.bodyLarge,
                    textAlign = TextAlign.Start,
                    modifier = Modifier.fillMaxWidth()
                )
            }
            Text(
                text = ("Кол-во: ${tool.count}"),
                textAlign = TextAlign.End,
            )
        }

    }
}



@Preview(showBackground = true)
@Composable
fun OrderingPreview() {
    ProCatFirstTheme {
        //OrderingScreen()
    }
}