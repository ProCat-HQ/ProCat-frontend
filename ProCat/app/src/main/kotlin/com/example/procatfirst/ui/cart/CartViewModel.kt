package com.example.procatfirst.ui.cart

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class CartViewModel: ViewModel()  {

    var checked by mutableStateOf(false)
        private set

    fun init() {
        checked = false
        //isConfirmed
    }
}
