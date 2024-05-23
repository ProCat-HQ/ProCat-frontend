package com.example.procatfirst.ui.editing.deliverymen

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
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.procatfirst.data.Deliveryman
import com.example.procatfirst.data.User

@Composable
fun Deliverymen(
    deliverymenViewModel: DeliverymenViewModel = viewModel()
) {
    val deliverymenUiState by deliverymenViewModel.uiState.collectAsState()
    var showUsers by remember { mutableStateOf(false) }
    var showCourierDialog by remember { mutableStateOf(false) }
    var showDeleteDialog by remember { mutableStateOf(false) }
    var selectedUser by remember { mutableStateOf<User?>(null) }


    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        items(deliverymenUiState.deliverymen) { deliveryMan ->
            DeliveryManCard(
                man = deliveryMan,
                onDeleteClick = {
                    deliverymenViewModel.deleteDeliveryman(deliveryMan.id)
                }
            )
        }

        item {
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                onClick = {
                    if (showUsers) {
                        showUsers = false
                    } else {
                        deliverymenViewModel.loadUsers()
                        showUsers = true
                    }
                }
            ) {
                Text(if (showUsers) "Скрыть пользователей" else "Отобразить пользователей")
            }
        }

        if (showUsers) {
            items(deliverymenUiState.users) { user ->
                UserNameCard(
                    user = user,
                    onMakeCourierClick = {
                        selectedUser = user
                        showCourierDialog = true
                    },
                    onDeleteClick = {
                        selectedUser = user
                        showDeleteDialog = true
                    }
                )
            }
        }
    }

    if (showCourierDialog && selectedUser != null) {
        MakeCourierDialog(
            user = selectedUser!!,
            onDismiss = { showCourierDialog = false },
            onConfirm = { carCapacity, workingHoursStart, workingHoursEnd, carId ->
                deliverymenViewModel.makeCourier(selectedUser!!, carCapacity, workingHoursStart, workingHoursEnd, carId)
                showCourierDialog = false
            }
        )
    }
    if (showDeleteDialog && selectedUser != null) {
        DeleteUserDialog(
            user = selectedUser!!,
            onDismiss = { showDeleteDialog = false },
            onConfirm = {
                deliverymenViewModel.deleteUser(selectedUser!!.id)
                showDeleteDialog = false
            }
        )
    }

}

@Composable
fun DeliveryManCard(
    man: Deliveryman,
    onDeleteClick: () -> Unit
) {
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
                text = man.fullName,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "Вместимость: ${man.carCapacity}",
                style = MaterialTheme.typography.bodyMedium,
            )
            Text(
                text = "Часы работы: ${man.workingHoursStart.dropLast(3)} - ${man.workingHoursEnd.dropLast(3)}",
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
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.BottomEnd
            ) {
                OutlinedButton(
                    onClick = onDeleteClick,
                    modifier = Modifier.padding(top = 8.dp)
                ) {
                    Text("Удалить")
                }
            }
        }
    }
}


@Composable
fun UserNameCard(
    user: User,
    onMakeCourierClick: () -> Unit,
    onDeleteClick: () -> Unit,
) {
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
                text = "${user.id} ${user.fullName}",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
            ) {
                OutlinedButton(
                    onClick = onDeleteClick,
                    Modifier.padding(5.dp),
                    colors = ButtonColors(MaterialTheme.colorScheme.secondaryContainer, MaterialTheme.colorScheme.error, MaterialTheme.colorScheme.errorContainer, MaterialTheme.colorScheme.onErrorContainer),
                ) {
                    Text("Удалить")
                }
                OutlinedButton(
                    onClick = onMakeCourierClick,
                ) {
                    Text("Сделать курьером")
                }
            }
        }
    }
}


@Composable
fun MakeCourierDialog(
    user: User,
    onDismiss: () -> Unit,
    onConfirm: (String, String, String, String) -> Unit
) {
    var carCapacity by remember { mutableStateOf("") }
    var workingHoursStart by remember { mutableStateOf("") }
    var workingHoursEnd by remember { mutableStateOf("") }
    var carId by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(text = "Сделать курьером: ${user.fullName}") },
        text = {
            Column {
                OutlinedTextField(
                    value = carCapacity,
                    onValueChange = { carCapacity = it },
                    label = { Text("Вместимость машины") },
                    modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)
                )
                OutlinedTextField(
                    value = workingHoursStart,
                    onValueChange = { workingHoursStart = it },
                    label = { Text("Начало работы") },
                    modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)
                )
                OutlinedTextField(
                    value = workingHoursEnd,
                    onValueChange = { workingHoursEnd = it },
                    label = { Text("Конец работы") },
                    modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)
                )
                OutlinedTextField(
                    value = carId,
                    onValueChange = { carId = it },
                    label = { Text("ID машины") },
                    modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)
                )
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    onConfirm(carCapacity, workingHoursStart, workingHoursEnd, carId)
                }
            ) {
                Text("Подтвердить")
            }
        },
        dismissButton = {
            Button(onClick = onDismiss) {
                Text("Отмена")
            }
        }
    )
}

@Composable
fun DeleteUserDialog(
    user: User,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit,
) {

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(text = "Удалить пользователя?: ${user.fullName}") },
        confirmButton = {
            Button(
                onClick = {
                    onConfirm()
                }
            ) {
                Text("Подтвердить")
            }
        },
        dismissButton = {
            Button(onClick = onDismiss) {
                Text("Отмена")
            }
        }
    )
}