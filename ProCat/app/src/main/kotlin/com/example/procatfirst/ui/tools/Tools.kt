package com.example.procatfirst.ui.tools

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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.List
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.procatfirst.R
import com.example.procatfirst.data.Tool
import com.example.procatfirst.repository.cache.CatalogCache
import com.example.procatfirst.intents.SystemNotifications
import com.example.procatfirst.repository.data_coordinator.DataCoordinator
import com.example.procatfirst.repository.data_coordinator.getCatalog
import com.example.procatfirst.repository.data_coordinator.loadCatalog
import com.example.procatfirst.ui.IntentsReceiverAbstractObject
import com.example.procatfirst.ui.theme.ProCatFirstTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

@Composable
fun ToolsScreen(
    onNextButtonClicked: () -> Unit,
    onNextButtonClicked1: (Tool) -> Unit,
    toolsViewModel: ToolsViewModel = viewModel(),
    modifier: Modifier = Modifier
    ) {
    val searchUiState by toolsViewModel.uiState.collectAsState()
    loadCatalog(toolsViewModel)
    //ToolDataProvider.tools
    //DataCoordinator.shared.addToolToUserCart(Tool(2, "Молоток", 1, "ddjdjjd", "sksks", 30))
    var tools by remember {
        mutableStateOf(DataCoordinator.shared.getCatalog())
    }
    var loadText by remember {
        mutableStateOf("Loading...")
    }
    var isActive by remember { mutableStateOf(tools.isNotEmpty())}
    val receiver1: IntentsReceiverAbstractObject = object : IntentsReceiverAbstractObject() {
        override fun howToReactOnIntent() {
            if(CatalogCache.shared.getCatalogStuff().isEmpty()) {
                loadText = "Нет соединения с сервером"
            }
            else {
                tools = CatalogCache.shared.getCatalogStuff()
                isActive = true
            }
        }
    }
    receiver1.CreateReceiver(intentToReact = SystemNotifications.stuffAddedIntent)
    if (isActive) {
        Column (

        ){
            Row(
                modifier = Modifier
                    .padding(16.dp),
            ){
                OutlinedTextField(
                    value = toolsViewModel.userInputSearch,
                    singleLine = true,
                    onValueChange = { toolsViewModel.updateInputSearch(it) },
                    label = { Text(stringResource(R.string.search)) },
                    keyboardActions = KeyboardActions(
                        onDone = { toolsViewModel.search() }
                    ),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = MaterialTheme.colorScheme.surface,
                        unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                        disabledContainerColor = MaterialTheme.colorScheme.surface,
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)

                )
                IconButton(onClick = {}) {
                    Icon(
                        imageVector = Icons.Outlined.List,
                        contentDescription = stringResource(R.string.filter)
                    )
                }

            }
            Button(
                onClick = { onNextButtonClicked() },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text(stringResource(R.string.next))
            }
            LazyColumn(
                modifier = Modifier
                    //.verticalScroll(rememberScrollState())
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                items(tools) { tool ->
                    ToolCard(tool = tool, onNextButtonClicked1)
                }
            }
        }
    }
    else {
        Text(text = loadText)
        Button(
            onClick = { onNextButtonClicked() },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(stringResource(R.string.next))
        }
    }

}

@Composable
fun ToolCard(
    tool: Tool,
    onNextButtonClicked: (Tool) -> Unit,
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clip(MaterialTheme.shapes.medium)
            .background(MaterialTheme.colorScheme.background)
            .clickable { onNextButtonClicked(tool) }
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
        ) {
            Image(
                painter = painterResource(id = tool.imageResId),
                contentDescription = tool.name,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp)
                    .clip(MaterialTheme.shapes.medium)
            )
            Spacer(modifier = Modifier.height(16.dp))
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
                textAlign = TextAlign.End,
                modifier = Modifier.fillMaxWidth()

            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ToolPreview() {
    ProCatFirstTheme {
        //ToolsScreen()
    }
}

/**
 *  На самом деле тут корутина не понадобилась, но для примера пусть пока будет
 */
fun loadCatalog(toolsViewModel : ViewModel) {
    toolsViewModel.viewModelScope.launch {
        DataCoordinator.shared.loadCatalog()
    }
}