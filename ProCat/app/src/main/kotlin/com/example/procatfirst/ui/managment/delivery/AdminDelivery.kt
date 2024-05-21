package com.example.procatfirst.ui.managment.delivery

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.procatfirst.R
import com.example.procatfirst.data.ClusterResult
import com.example.procatfirst.data.Delivery
import com.example.procatfirst.data.DeliveryLocation
import com.example.procatfirst.ui.editing.deliverymen.DeliverymenViewModel

@Composable
fun AdminDelivery(
    adminDeliveryViewModel: AdminDeliveryViewModel = viewModel()
) {
    val adminDeliveryUiState by adminDeliveryViewModel.uiState.collectAsState()

    Column() {
        Button(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            onClick = { adminDeliveryViewModel.makeCluster()}
        ) {
            Text(
                text = stringResource(R.string.make_cluster),
                fontSize = 16.sp
            )
        }
        OutlinedButton(
            onClick = {adminDeliveryViewModel.confirm()},
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text("Подтвердить")
        }
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.padding(16.dp),
        ) {
            items(adminDeliveryUiState.deliveryPairs) { deliveries ->
                DeliveryCard(
                    deliveries = deliveries,
                    onLocationClick = { id -> adminDeliveryViewModel.getDelivery(id) },
                    deliverymenIds = adminDeliveryUiState.deliveryPairs.map{ clusterResult -> clusterResult.deliverymanId },
                    onDeliverymanChange = { deliveryId, newDeliverymanId ->
                        adminDeliveryViewModel.changeDeliveryman(deliveryId, newDeliverymanId)
                    },
                    deliverymanId = deliveries.deliverymanId
                    )
            }
        }
    }

    if (adminDeliveryUiState.isDeliveryDialogOpen && adminDeliveryUiState.currentDelivery != null) {
        DeliveryDetailsDialog(
            delivery = adminDeliveryUiState.currentDelivery!!,
            onDismiss = { adminDeliveryViewModel.closeDeliveryDialog() }
        )
    }

}

@Composable
fun DeliveryCard(
    deliveries: ClusterResult,
    onLocationClick: (Int) -> Unit,
    deliverymenIds: List<Int>,
    onDeliverymanChange: (Int, Int) -> Unit,
    deliverymanId: Int,
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clip(MaterialTheme.shapes.medium)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
        ) {
            Text(
                text = "Курьер: ${deliveries.deliverymanId}",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
            deliveries.deliveries.forEach { delivery ->
                DeliveryLocationCard(
                    deliveryLocation = delivery,
                    onClick = { onLocationClick(delivery.deliveryId) },
                    deliverymenIds = deliverymenIds,
                    onDeliverymanChange = { newDeliverymanId ->
                        onDeliverymanChange(delivery.deliveryId, newDeliverymanId)
                    },
                    deliverymanId
                )
            }
        }
    }
}


@Composable
fun DeliveryLocationCard(
    deliveryLocation: DeliveryLocation,
    onClick: () -> Unit,
    deliverymenIds: List<Int>,
    onDeliverymanChange: (Int) -> Unit,
    deliverymanId: Int,
) {
    var expanded by remember { mutableStateOf(false) }
    var selectedDeliverymanId by remember { mutableStateOf(deliverymanId) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clip(MaterialTheme.shapes.small)
            .padding(vertical = 8.dp)
            .clickable { onClick() },
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.tertiaryContainer,
            contentColor = MaterialTheme.colorScheme.onTertiaryContainer,
            disabledContainerColor = MaterialTheme.colorScheme.tertiaryContainer,
            disabledContentColor = MaterialTheme.colorScheme.onTertiaryContainer
        ),

    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
        ) {
            Text(
                text = "Address: ${deliveryLocation.address}",
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Bold
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column (

                ){
                    Text(
                        text = "Selected ID: $selectedDeliverymanId",
                        style = MaterialTheme.typography.bodySmall
                    )
                    Text(
                        text = "Latitude: ${deliveryLocation.latitude}",
                        style = MaterialTheme.typography.bodySmall
                    )
                    Text(
                        text = "Longitude: ${deliveryLocation.longitude}",
                        style = MaterialTheme.typography.bodySmall
                    )
                    Text(
                        text = "Delivery ID: ${deliveryLocation.deliveryId}",
                        style = MaterialTheme.typography.bodySmall
                    )
                }
                Box {
                    FilledTonalButton(onClick = { expanded = true }) {
                        Text("Изменить")
                    }
                    DropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false }
                    ) {
                        deliverymenIds.forEach { deliverymanId ->
                            DropdownMenuItem(
                                onClick = {
                                    selectedDeliverymanId = deliverymanId
                                    onDeliverymanChange(deliverymanId)
                                    expanded = false
                                },
                                text = { Text(text = deliverymanId.toString()) }
                            )
                        }
                    }
                }
            }

        }
    }
}


@Composable
fun DeliveryDetailsDialog(
    delivery: Delivery,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(text = "Delivery Details") },
        text = {
            Column {
                Text(text = "ID: ${delivery.id}")
                Text(text = "Time Start: ${delivery.timeStart}")
                Text(text = "Time End: ${delivery.timeEnd}")
                Text(text = "Method: ${delivery.method}")
                //Text(text = "Order ID: ${delivery.order.id}")
                Text(text = "Order Status: ${delivery.order.status}")
                Text(text = "Total Price: ${delivery.order.totalPrice}")
                Text(text = "Address: ${delivery.order.address}")
                Text(text = "Latitude: ${delivery.order.latitude}")
                Text(text = "Longitude: ${delivery.order.longitude}")
            }
        },
        confirmButton = {
            Button(onClick = onDismiss) {
                Text("OK")
            }
        }
    )
}