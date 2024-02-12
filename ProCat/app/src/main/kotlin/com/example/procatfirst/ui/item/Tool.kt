package com.example.procatfirst.ui.item

import android.annotation.SuppressLint
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
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.procatfirst.R
import com.example.procatfirst.intents.SystemNotifications
import com.example.procatfirst.ui.IntentsReceiverAbstractObject
import com.example.procatfirst.ui.theme.ProCatFirstTheme
import com.example.procatfirst.ui.theme.md_theme_light_onSurfaceVariant
import kotlinx.coroutines.flow.StateFlow

@Composable
fun ToolScreen(
    toolViewModel: ToolViewModel = viewModel(),
    onNextButtonClicked: () -> Unit,
    modifier: Modifier = Modifier
    ) {
    val toolUiState by toolViewModel.uiState.collectAsState()

    val receiver: IntentsReceiverAbstractObject = object : IntentsReceiverAbstractObject() {
        override fun howToReactOnIntent() {
            toolViewModel.removeFromCart()
        }
    }

    receiver.CreateReceiver(intentToReact = SystemNotifications.shakalno)

    Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        //verticalArrangement = Arrangement.Center,
        //horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Image(
            painter = painterResource(id = R.drawable.hammer),
            contentDescription = stringResource(id = R.string.hammer),
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1.0f) // Сохраняет соотношение сторон 1:1
                .padding(top = 5.dp, bottom = 5.dp)
        )

        Text(
            text = toolUiState.tool.name,
            style = MaterialTheme.typography.titleLarge
        )
        Text(
            text = "строительный",
            style = MaterialTheme.typography.bodySmall
        )
        Divider(thickness = 1.dp, color = md_theme_light_onSurfaceVariant)
        Spacer(modifier = Modifier.height(10.dp))

        Text(
            text = stringResource(R.string.toolDescription),
            style = MaterialTheme.typography.titleMedium
        )
        Text(
            text = toolUiState.tool.description,
            style = MaterialTheme.typography.bodyMedium
        )
        Spacer(modifier = Modifier.height(5.dp))
        Text(
            text = stringResource(R.string.toolSpecifications),
            style = MaterialTheme.typography.titleMedium
        )
        Text(
            text = toolUiState.tool.specifications,
            style = MaterialTheme.typography.bodyMedium
        )

        BottomBar(
            addToCart = { toolViewModel.addToCart() },
            toolViewModel
        )

        Button(
            onClick = { onNextButtonClicked() },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(stringResource(R.string.cart))
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
        Button(
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
