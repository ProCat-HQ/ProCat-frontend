package com.example.procatfirst.ui.cart

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.procatfirst.R
import com.example.procatfirst.data.CartItem
import com.example.procatfirst.data.CartPayload

@Composable
fun ToolsScreenCart(
    tools: CartPayload,
    context: Context,
) {
        Column(
            modifier = Modifier
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            for (tool in tools.items)
                ToolCardCart(tool = tool, context = context)
        }
}

@Composable
fun ToolCardCart(
    tool: CartItem,
    toolViewModel: ToolViewModel = ToolViewModel(tool),
    context: Context,
) {

    val toolUiState by toolViewModel.uiState.collectAsState()

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clip(MaterialTheme.shapes.small)
            .background(MaterialTheme.colorScheme.background)
            .clickable {}
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(8.dp)
        ) {
            Column(
                modifier = Modifier
                    .padding(6.dp)
                    .weight(3f)
            ) {
                IconButton(
                    onClick = { toolViewModel.removeFromCart(tool) },
                    modifier = Modifier.size(24.dp)
                ) {
                    Icon(Icons.Default.Clear, contentDescription = stringResource(R.string.delete))
                }
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = tool.name,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(8.dp))
                Spacer(modifier = Modifier.height(5.dp))
                Text(
                    text = (stringResource(id = R.string.tool_price, tool.price)),
                    style = MaterialTheme.typography.bodyLarge,
                    textAlign = TextAlign.Start,
                    modifier = Modifier.fillMaxWidth()
                )
                QuantityButton(
                    quantity =  toolViewModel.uiState.value.tool.count,
                    onIncrease = { toolViewModel.increaseAmount(context) },
                    onDecrease = { toolViewModel.decreaseAmount() }
                )
            }

            Column (
                modifier = Modifier
                    .weight(2f),
                verticalArrangement = Arrangement.Center

            ){
                Spacer(modifier = Modifier.height(10.dp))
                if (toolUiState.bitmap != null) {
                    Image(
                        bitmap = toolUiState.bitmap!!.asImageBitmap(),
                        contentDescription = stringResource(id = R.string.logo),
                        modifier = Modifier
                            .fillMaxWidth()
                            .aspectRatio(1.0f) // Сохраняет соотношение сторон 1:1
                            .padding(top = 5.dp, bottom = 5.dp)
                    )
                }
            }

        }

    }
}

@Composable
fun QuantityButton(
    quantity: Int,
    onIncrease: () -> Unit,
    onDecrease: () -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = onDecrease) {
            Icon(Icons.Default.Clear, contentDescription = "Decrease")
        }
        Text(
            text = quantity.toString(),
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(horizontal = 8.dp)
        )
        IconButton(onClick = onIncrease) {
            Icon(Icons.Default.Add, contentDescription = "Increase")
        }
    }
}
