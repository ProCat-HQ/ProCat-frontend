package com.example.procatfirst.ui.start

import android.content.Context
import android.content.Intent
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
import com.example.procatfirst.MapActivity
import com.example.procatfirst.R
import com.example.procatfirst.repository.api.ApiCalls

@Composable
fun StartScreen(
    controller : Context,
    modifier: Modifier = Modifier,
    onNextButtonClicked: () -> Unit,
    ) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = stringResource(R.string.welcome),
            style = MaterialTheme.typography.titleLarge
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
            onClick = { onNextButtonClicked() },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(stringResource(R.string.next))
        }
        Button(
            onClick = { controller.startActivity(Intent(controller, MapActivity().javaClass)) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(6.dp)
        ) {
            Text("Карта!")
        }
        Button(
            onClick = { //ApiCalls.shared.geocoderApi() },
                ApiCalls.shared.postApi("88005553535", "paSSword") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(6.dp)
        ) {
            //Text("Geocoder")
            Text("PostApi")
        }

    }

}
