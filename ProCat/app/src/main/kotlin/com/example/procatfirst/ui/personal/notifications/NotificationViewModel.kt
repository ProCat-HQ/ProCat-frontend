package com.example.procatfirst.ui.personal.notifications

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.procatfirst.data.NotificationItem
import com.example.procatfirst.repository.api.ApiCalls
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


class NotificationViewModel: ViewModel() {

    private val _uiState = MutableStateFlow(NotificationsUiState())
    val uiState: StateFlow<NotificationsUiState> = _uiState.asStateFlow()

    init {
        getNotifications()
    }

    private fun getNotifications() {
        viewModelScope.launch {
            val callback = {status: String, result: List<NotificationItem> ->
                if(status == "SUCCESS") {
                    _uiState.update { currentState ->
                        currentState.copy(
                            notifications = result
                        )
                    }
                }
            }
            ApiCalls.shared.getNotificationsApi(callback)
        }
    }

    fun markAsRead(notification: NotificationItem) {
        viewModelScope.launch {
            val callback = {status: String ->
                if(status == "SUCCESS") {
                    getNotifications()
                }
            }
            ApiCalls.shared.setNotificationToViewedApi(notification.id, callback)
        }
    }

    fun sendNotification(userId: Int, title: String, body: String) {
        viewModelScope.launch {
            val callback = {status: String ->
                if(status == "SUCCESS") {

                }
            }
            ApiCalls.shared.sendNotificationApi(userId, title, body, callback)
        }
    }

    fun deleteNotification(notification: NotificationItem) {
        viewModelScope.launch {
            val callback = {status: String ->
                if(status == "SUCCESS") {
                    getNotifications()
                }
            }
            ApiCalls.shared.deleteNotificationApi(notification.id, callback)
        }

    }


}