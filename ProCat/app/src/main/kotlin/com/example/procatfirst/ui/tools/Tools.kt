package com.example.procatfirst.ui.tools

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.List
import androidx.compose.material3.AlertDialog
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.procatfirst.R
import com.example.procatfirst.data.Tool
import com.example.procatfirst.intents.SystemNotifications
import com.example.procatfirst.ui.IntentsReceiverAbstractObject
import com.example.procatfirst.ui.theme.ProCatFirstTheme

@Composable
fun ToolsScreen(
    onNextButtonClicked: () -> Unit,
    onNextButtonClicked1: (Tool) -> Unit,
    toolsViewModel: ToolsViewModel = viewModel(),
    modifier: Modifier = Modifier
    ) {
    val searchUiState by toolsViewModel.uiState.collectAsState()
    val (showFilterDialog, setShowFilterDialog) = remember { mutableStateOf(false) }
    val (groupByCategory, setGroupByCategory) = remember { mutableStateOf(false) }


    val receiver1: IntentsReceiverAbstractObject = object : IntentsReceiverAbstractObject() {
        override fun howToReactOnIntent() {
            toolsViewModel.updateTools()
        }
    }
    receiver1.CreateReceiver(intentToReact = SystemNotifications.stuffAddedIntent)

    if (searchUiState.isActive) {
        Column (

        ){
            Row(
                modifier = Modifier
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
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
                        .weight(1f)
                )
                Spacer(modifier = Modifier.width(8.dp))

                IconButton(onClick = {setShowFilterDialog(true)}) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Outlined.List,
                        contentDescription = stringResource(R.string.filter)
                    )
                }

            }
            Button(
                onClick = { toolsViewModel.search() },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text(stringResource(R.string.search))
            }
            if (groupByCategory) {
                LazyColumn(
                    modifier = Modifier
                        //.verticalScroll(rememberScrollState())
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    val groupedTools = searchUiState.tools.groupBy { it.categoryId }
                    groupedTools.forEach { (categoryId, tools) ->
                        item {
                            Text(
                                text = categoryId.toString(),
                                style = MaterialTheme.typography.titleLarge,
                                fontWeight = FontWeight.Bold
                            )
                        }
                        items(tools) { tool ->
                            ToolCard(tool = tool, onNextButtonClicked1)
                        }
                    }
                }


            } else {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    modifier = Modifier
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    items(searchUiState.tools) { tool ->
                        ToolCard(tool = tool, onNextButtonClicked1)
                    }
                }
            }

        }
        if (showFilterDialog) {
            FilterDialog(
                onDismissRequest = { setShowFilterDialog(false) },
                onSortByPriceAscending = {
                    toolsViewModel.sortByPriceAscending()
                    setShowFilterDialog(false)
                },
                onSortByPriceDescending = {
                    toolsViewModel.sortByPriceDescending()
                    setShowFilterDialog(false)
                },
                onCategoryGroupRequest = {
                    setGroupByCategory(true)
                    setShowFilterDialog(false)
                },
                resetRequest = {
                    setGroupByCategory(false)
                    toolsViewModel.resetFilters()
                    setShowFilterDialog(false)
                }
            )
        }
    }
    else {
        Text(text = searchUiState.loadText)
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
    toolViewModel: ToolViewModel = ToolViewModel(tool)
) {

    val toolUiState by toolViewModel.uiState.collectAsState()

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



@Composable
fun FilterDialog(
    onDismissRequest: () -> Unit,
    onSortByPriceAscending: () -> Unit,
    onSortByPriceDescending: () -> Unit,
    onCategoryGroupRequest: () -> Unit,
    resetRequest: () -> Unit,
    modifier: Modifier = Modifier
) {
    AlertDialog(
        onDismissRequest = onDismissRequest,
        title = {
            Text(stringResource(R.string.sort_by_price))
        },
        text = {
            Column(
                modifier = modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Button(
                    onClick = onSortByPriceAscending,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(stringResource(R.string.ascending))
                }
                Button(
                    onClick = onSortByPriceDescending,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(stringResource(R.string.descending))
                }
                Button(
                    onClick = onCategoryGroupRequest,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(stringResource(R.string.category_group))
                }
            }
        },
        confirmButton = {
            Button(
                onClick = resetRequest,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(stringResource(R.string.reset))
            }
        },
        dismissButton = {
            Button(
                onClick = onDismissRequest,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(stringResource(R.string.cancel))
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
fun ToolPreview() {
    ProCatFirstTheme {
        //ToolsScreen()
    }
}

