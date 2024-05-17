package com.example.procatfirst.ui.start

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.procatfirst.R


@Composable
fun StartScreen(
    controller : Context,
    modifier: Modifier = Modifier,
    onNextButtonClicked: () -> Unit,
    startViewModel: StartViewModel = StartViewModel(controller, onNextButtonClicked),

) {

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = stringResource(R.string.welcome),
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.onSurface
        )


        Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = stringResource(id = R.string.logo),
                modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(1.0f) // Сохраняет соотношение сторон 1:1
                        .padding(top = 5.dp, bottom = 5.dp)
        )

        Button(
            onClick = { startViewModel.authorise(controller, onNextButtonClicked) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            Text(stringResource(R.string.authorize))
        }

    }

}
