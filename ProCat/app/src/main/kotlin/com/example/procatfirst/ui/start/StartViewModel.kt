package com.example.procatfirst.ui.start

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.procatfirst.repository.cache.UserDataCache
import com.example.procatfirst.repository.data_coordinator.DataCoordinator
import com.example.procatfirst.repository.data_coordinator.setTokenAndRole
import kotlinx.coroutines.launch

class StartViewModel(context: Context, nextPageAction : () -> Unit): ViewModel() {

    init{
        open(context)
    }

    private fun open(context: Context) {
        authorise(context) {}
        Log.v("OPEN", "OPEN")
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
            if (UserDataCache.shared.getUserToken() == "") {
                DataCoordinator.shared.refresh(callback, context)
            }
        }
    }

}