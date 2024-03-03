package com.example.procatfirst.ui.personal.notifications

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.procatfirst.data.NotificationDataProvider
import com.example.procatfirst.data.Notification
import kotlinx.coroutines.flow.MutableStateFlow


class NotificationViewModel: ViewModel() {

    val notifications: MutableStateFlow<List<Notification>> = MutableStateFlow(
        NotificationDataProvider.notifications)

    init {

    }

    fun markAsRead(notification: Notification) {
        notification.isViewed = true
        updateNotifications()
    }

    private fun updateNotifications() {
        notifications.value = notifications.value.toMutableList()
            .sortedByDescending { it.isViewed }
            .toList()
    }
}