package com.example.procatfirst.ui.personal.notifications

import com.example.procatfirst.data.NotificationItem


data class NotificationsUiState (
    val notifications: List<NotificationItem> = emptyList(),
)