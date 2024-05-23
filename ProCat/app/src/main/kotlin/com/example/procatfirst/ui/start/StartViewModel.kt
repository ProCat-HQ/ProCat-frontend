package com.example.procatfirst.ui.start

import android.content.Context
import android.graphics.BitmapFactory
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.procatfirst.repository.api.ApiCalls
import com.example.procatfirst.repository.cache.CatalogCache
import com.example.procatfirst.repository.cache.UserDataCache
import com.example.procatfirst.repository.data_coordinator.DataCoordinator
import com.example.procatfirst.repository.data_coordinator.getImage
import com.example.procatfirst.repository.data_coordinator.setTokenAndRole
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class StartViewModel(context: Context, nextPageAction : () -> Unit): ViewModel() {

    private val _uiState = MutableStateFlow(StartUiState())
    val uiState: StateFlow<StartUiState> = _uiState.asStateFlow()

    init{
        open(context)
    }

    private fun open(context: Context) {
        authorise(context, false) {}
        Log.v("OPEN", "OPEN")
    }

    fun authorise(context: Context, manual: Boolean, nextPageAction : () -> Unit) {
        val callback = {status: String, acc: String, ref: String ->
            Log.d("Status", status)
            if (status == "SUCCESS") {
                viewModelScope.launch {
                    DataCoordinator.shared.setTokenAndRole(acc, ref, context)
                }
                Toast.makeText(context, "Успешный вход!", Toast.LENGTH_SHORT).show()
            }
            else {
                nextPageAction()
            }
        }
        viewModelScope.launch {
            if (UserDataCache.shared.getUserToken() == "") {
                DataCoordinator.shared.refresh(callback, context, manual)
            }
        }
    }

}