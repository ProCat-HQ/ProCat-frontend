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
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.example.procatfirst.R
import com.example.procatfirst.repository.UserRoleRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import com.example.procatfirst.repository.api.ApiCalls
import com.example.procatfirst.repository.data_coordinator.DataCoordinator
import com.example.procatfirst.repository.data_coordinator.getUserRole
import com.example.procatfirst.repository.data_coordinator.setUserRole


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
            onClick = { onNextButtonClicked() },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            Text(stringResource(R.string.authorize))
        }
//        Button(
//            onClick = { context.startActivity(Intent(controller, MapActivity().javaClass)) },
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(6.dp)
//        ) {
//            Text("Карта!")
//        }
//        Button(
//            onClick = {
//                ApiCalls.shared.signUpApi("88005553535", "paSSword", "misha evdokimov") },
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(8.dp)
//        ) {
//            Text("Register")
//        }
//        Button(
//                onClick = { //ApiCalls.shared.geocoderApi() },
//                    ApiCalls.shared.signInApi("88005553535", "paSSword") },
//                modifier = Modifier
//                        .fillMaxWidth()
//                        .padding(8.dp)
//        ) {
//            //Text("Geocoder")
//            Text("login")
//        }
//
//        Button(
//                onClick = { //ApiCalls.shared.geocoderApi() },
//                    ApiCalls.shared.getUserDataApi(1) },
//                modifier = Modifier
//                        .fillMaxWidth()
//                        .padding(6.dp)
//        ) {
//            //Text("Geocoder")
//            Text("getUser")
//        }

    }

}
