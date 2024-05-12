package com.example.procatfirst.ui.personal.notifications

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.procatfirst.data.NotificationDataProvider
import com.example.procatfirst.data.NotificationItem
import kotlinx.coroutines.flow.MutableStateFlow


class NotificationViewModel: ViewModel() {

    private var isFirstTime = true


    val notifications: MutableStateFlow<List<NotificationItem>> = MutableStateFlow(
        NotificationDataProvider.notifications)

    init {
        updateNotifications()
    }

    fun markAsRead(notification: NotificationItem) {
        notification.isViewed = true
    }

    private fun updateNotifications() {
        notifications.value = notifications.value.toMutableList()
            .sortedByDescending { it.createdAt }
            .toList()
    }
}