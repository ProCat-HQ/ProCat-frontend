package com.example.procatfirst.ui.start

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.procatfirst.repository.data_coordinator.DataCoordinator
import com.example.procatfirst.repository.data_coordinator.setTokenAndRole
import kotlinx.coroutines.launch

class StartViewModel(): ViewModel() {

    //private val _uiState = MutableStateFlow(SearchUiState("", DataCoordinator.shared.getCatalog()))
    //val uiState: StateFlow<SearchUiState> = _uiState.asStateFlow()

    init{
        open()
    }

    private fun open() {

    }

    fun authorise(context: Context, nextPageAction : () -> Unit) {
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
            DataCoordinator.shared.refresh(callback, context)
        }
    }

}