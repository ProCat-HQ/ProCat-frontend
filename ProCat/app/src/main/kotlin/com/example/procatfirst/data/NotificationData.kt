package com.example.procatfirst.data

import kotlinx.serialization.Serializable

@Serializable
data class Notification(
    val notificationId: Int,
    val title: String,
    val description: String,
    var isViewed: Boolean,
    val createdAt: Int
)

data class NotificationItem(
    val id: Int,
    val title: String,
    val description: String,
    var isViewed: Boolean,
    val createdAt: String
)

data class NotificationPayload(
    val notifications: List<Notification>
)

object NotificationDataProvider {
    val notifications = listOf(
        NotificationItem(
            id = 1,
            title = "Новое сообщение в чате",
            description = "He'll want to use your yacht, and I don't want this thing smelling like fish.",
            isViewed = true,
            createdAt = "11.02.2024"
        ),
        NotificationItem(
            id = 2,
            title = "Инструмент из подписки в продаже",
            description = "He'll want to use your yacht, and I don't want this thing smelling like fish.",
            isViewed = false,
            createdAt = "12.02.2024"
        ),
        NotificationItem(
            id = 2,
            title = "Заказ в доставке",
            description = "He'll want to use your yacht, and I don't want this thing smelling like fish.",
            isViewed = true,
            createdAt = "10.02.2024"
        ),
        NotificationItem(
            id = 4,
            title = "Оплата не прошла",
            description = "He'll want to use your yacht, and I don't want this thing smelling like fish.",
            isViewed = false,
            createdAt = "09.02.2024"
        ),
    )
}