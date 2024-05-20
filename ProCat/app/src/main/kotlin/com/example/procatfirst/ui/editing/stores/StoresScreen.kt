package com.example.procatfirst.ui.editing.stores

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.procatfirst.data.Store
import com.example.procatfirst.ui.managment.deliverymen.DeliveryManCard
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

@Composable
fun StoresScreen(
    storesViewModel: StoresViewModel = viewModel()
) {
    val storesUiState by storesViewModel.uiState.collectAsState()
    val isEditing = remember { mutableStateOf(false) }
    val selectedStore = remember { mutableStateOf<Store?>(null) }

    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {


        items(storesUiState.stores) { store ->
            StoreCard(
                store = store,
                onEditClick = {
                    selectedStore.value = store
                    isEditing.value = true
                }
            )
        }
        item {
            Button(
                onClick = {},
                modifier = Modifier
                    .padding(top = 8.dp)
                    .fillMaxWidth()
            ) {
                Text("Создать магазин")
            }
        }
    }

    if (isEditing.value && selectedStore.value != null) {
        EditStoreDialog(
            store = selectedStore.value!!,
            onSave = { store ->
                storesViewModel.editStore(store)
                isEditing.value = false
            },
            onCancel = {
                isEditing.value = false
            }
        )
    }
}

@Composable
fun StoreCard(
    store: Store,
    onEditClick: () -> Unit
){
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clip(MaterialTheme.shapes.large)
            .background(MaterialTheme.colorScheme.background)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
        ) {
            Text(
                text = store.name,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "Адрес: ${store.address}",
                style = MaterialTheme.typography.bodyMedium,
            )
            Text(
                text = "Часы работы: ${formatTime(store.workingHoursStart)} - ${formatTime(store.workingHoursEnd)}",
                style = MaterialTheme.typography.bodyMedium
            )
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.BottomEnd
            ) {
                OutlinedButton(
                    onClick = onEditClick,
                    modifier = Modifier.padding(top = 8.dp)
                ) {
                    Text("Редактировать")
                }
            }
        }
    }
}

fun formatTime(datetime: String): String {
    val zonedDateTime = ZonedDateTime.parse(datetime)
    val formatter = DateTimeFormatter.ofPattern("HH:mm")
    return zonedDateTime.format(formatter)
}



@Composable
fun EditStoreDialog(
    store: Store,
    onSave: (Store) -> Unit,
    onCancel: () -> Unit
) {
    val name = remember { mutableStateOf(store.name) }
    val address = remember { mutableStateOf(store.address) }
    val workingHoursStart = remember { mutableStateOf(store.workingHoursStart) }
    val workingHoursEnd = remember { mutableStateOf(store.workingHoursEnd) }

    AlertDialog(
        onDismissRequest = onCancel,
        title = {
            Text(text = "Редактирование магазина")
        },
        text = {
            Column {
                OutlinedTextField(
                    value = name.value,
                    onValueChange = { name.value = it },
                    label = { Text("Название магазина") },
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = address.value,
                    onValueChange = { address.value = it },
                    label = { Text("Адрес") },
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = workingHoursStart.value,
                    onValueChange = { workingHoursStart.value = it },
                    label = { Text("Начало работы (HH:mm:ss)") },
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = workingHoursEnd.value,
                    onValueChange = { workingHoursEnd.value = it },
                    label = { Text("Конец работы (HH:mm:ss)") },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },
        confirmButton = {
            TextButton(onClick = {
                val updatedStore = store.copy(
                    name = name.value,
                    address = address.value,
                    workingHoursStart = workingHoursStart.value,
                    workingHoursEnd = workingHoursEnd.value
                )
                onSave(updatedStore)
            }) {
                Text("Сохранить")
            }
        },
        dismissButton = {
            TextButton(onClick = onCancel) {
                Text("Отмена")
            }
        }
    )
}