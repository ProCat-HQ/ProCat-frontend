package com.example.procatfirst.ui.managment.deliverymen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.procatfirst.data.Deliveryman

@Composable
fun Deliverymen(
    deliverymenViewModel: DeliverymenViewModel = viewModel()
) {
    val deliverymenUiState by deliverymenViewModel.uiState.collectAsState()

    Column(
        /*
        modifier = Modifier
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally */
    ) {

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.padding(16.dp),
        ) {
            items(deliverymenUiState.deliverymen) { deliveryMan ->
                DeliveryManCard(man = deliveryMan)
            }
        }
    }

}

@Composable
fun DeliveryManCard(
    man: Deliveryman,
    //onCardClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clip(MaterialTheme.shapes.large)
            .background(MaterialTheme.colorScheme.background)
            //.clickable { onCardClick() }
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
        ) {
            Text(
                text = man.fullName,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "Вместимость: ${man.carCapacity}",
                style = MaterialTheme.typography.bodyMedium,
            )
            Text(
                text = "Часы работы: ${man.workingHoursStart} - ${man.workingHoursEnd}",
                style = MaterialTheme.typography.bodyMedium
            )
            Text(
                text = man.phoneNumber,
                style = MaterialTheme.typography.bodyMedium,
            )
            Text(
                text = man.email,
                style = MaterialTheme.typography.bodyMedium,
            )
        }
    }
}