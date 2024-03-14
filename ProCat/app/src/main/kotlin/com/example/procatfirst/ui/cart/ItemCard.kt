package com.example.procatfirst.ui.cart

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.procatfirst.R
import com.example.procatfirst.data.Tool
import com.example.procatfirst.repository.data_storage_deprecated.DataCoordinatorOLD
import com.example.procatfirst.repository.data_storage_deprecated.updateRemoveToolsInCart
import com.example.procatfirst.intents.NotificationCoordinator
import com.example.procatfirst.intents.SystemNotifications
import com.example.procatfirst.intents.sendIntent
import com.example.procatfirst.repository.data_coordinator.DataCoordinator

@Composable
fun ToolsScreenCart(
    tools: List<Tool>
) {

        Column(
            modifier = Modifier
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            for (tool in tools)
                ToolCardCart(tool = tool)
        }
}

@Composable
fun ToolCardCart(
    toolViewModel: ToolViewModel = viewModel(),
    tool: Tool
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clip(MaterialTheme.shapes.small)
            .background(MaterialTheme.colorScheme.background)
            .clickable { /* Handle click on the card if needed */ }
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(8.dp)
        ) {
            Column(
                modifier = Modifier
                    .padding(6.dp)
                    .weight(3f) // Adjust the weight for text content
            ) {
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = tool.name,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = tool.description,
                    style = MaterialTheme.typography.bodyMedium
                )
                Spacer(modifier = Modifier.height(5.dp))
                Text(
                    text = (stringResource(id = R.string.tool_price, tool.price)),
                    style = MaterialTheme.typography.bodyLarge,
                    textAlign = TextAlign.Start,
                    modifier = Modifier.fillMaxWidth()
                )
                Button(onClick = {
                    toolViewModel.removeFromCart(tool)
                }) {
                    Text(text = "удалить", fontSize = 14.sp)
                }
            }

            Column (
                modifier = Modifier
                    .weight(2f),
                verticalArrangement = Arrangement.Center

            ){
                Spacer(modifier = Modifier.height(10.dp))
                Image(
                    painter = painterResource(id = R.drawable.hammer),//(id = tool.imageResId),
                    contentDescription = tool.name,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(150.dp)
                        .clip(MaterialTheme.shapes.medium)
                )
            }

        }

    }
}