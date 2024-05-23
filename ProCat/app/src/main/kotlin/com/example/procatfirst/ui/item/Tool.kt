package com.example.procatfirst.ui.item

import android.annotation.SuppressLint
import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Horizontal
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.procatfirst.R
import com.example.procatfirst.ui.theme.ProCatFirstTheme
import com.example.procatfirst.ui.theme.md_theme_light_onSurfaceVariant

@Composable
fun ToolScreen(
    toolId : Int,
    toolViewModel: ToolViewModel = ToolViewModel(toolId),
    onNextButtonClicked: () -> Unit,
    modifier: Modifier = Modifier,
    context: Context,
    ) {
    val toolUiState by toolViewModel.uiState.collectAsState()

    Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
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

        if (toolUiState.tool != null) {
            Row {
                Column {
                    Text(
                        text = toolUiState.tool!!.name,
                        style = MaterialTheme.typography.titleLarge
                    )
                    Text(
                        text = toolUiState.tool!!.categoryName,
                        style = MaterialTheme.typography.bodySmall
                    )
                }
//                IconButton(onClick = { toolViewModel.fav() }, modifier = Modifier.align(Horizontal.Top)) {
//                    if (!toolUiState.favourite){
//                        Icon(Icons.Filled.FavoriteBorder, contentDescription = "Избранное")
//                    }
//                    else {
//                        Icon(Icons.Filled.Favorite, contentDescription = "Избранное")
//                    }
//                }
            }
            HorizontalDivider(thickness = 1.dp, color = md_theme_light_onSurfaceVariant)
            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = stringResource(R.string.toolDescription),
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = toolUiState.tool!!.description,
                style = MaterialTheme.typography.bodyMedium
            )
            Spacer(modifier = Modifier.height(5.dp))
            Text(
                text = "Цена",
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = toolUiState.tool!!.price.toString(),
                style = MaterialTheme.typography.bodyMedium
            )
            // !! Fake warning, it's null usually
            if (toolUiState.tool!!.info != null) {
                for (info in toolUiState.tool!!.info) {
                    Text(
                        text = info.name,
                        style = MaterialTheme.typography.titleMedium
                    )
                    Text(
                        text = info.description,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
            BottomBar(
                addToCart = { toolViewModel.addToCart(toolUiState.tool!!, context) },
                toolViewModel
            )
        }

        ElevatedButton(
            onClick = { onNextButtonClicked() },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = stringResource(R.string.cart), fontSize = 16.sp)
        }

    }

}

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun BottomBar(
    addToCart: () -> Unit,
    toolViewModel: ToolViewModel
) {
    val toolUiState by toolViewModel.uiState.collectAsState()
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 15.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.Bottom
    ) {
        FilledTonalButton (
            modifier = Modifier.fillMaxWidth(),
            onClick = { addToCart() },
            enabled = !toolUiState.addedToCart

        ) {
            Text(
                text = if (toolUiState.addedToCart) stringResource(R.string.addedToCart) else stringResource(R.string.addToCart),
                fontSize = 16.sp
            )
        }
    }

}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SmallTopAppBarTool(
) {
    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                title = {
                    Text(text = stringResource(R.string.tool))
                }
            )
        },
    ) {
            innerPadding ->
        LazyColumn(
            contentPadding = innerPadding,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {

        }
    }
}


@Preview(showBackground = true)
@Composable
fun ToolPreview() {
    ProCatFirstTheme {
        //ToolScreen()
    }
}
