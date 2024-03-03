package com.example.procatfirst.data

import kotlinx.serialization.Serializable

@Serializable
data class Notification(
    val notificationId: Int,
    val title: String,
    val description: String,
    var isViewed: Boolean,
    val userId: Int
)

object NotificationDataProvider {
    val notifications = listOf(
        Notification(
            notificationId = 1,
            title = "Новое сообщение в чате",
            description = "He'll want to use your yacht, and I don't want this thing smelling like fish.",
            isViewed = true,
            userId = 1
        ),
        Notification(
            notificationId = 2,
            title = "Инструмент из подписки в продаже",
            description = "He'll want to use your yacht, and I don't want this thing smelling like fish.",
            isViewed = false,
            userId = 1
        ),
        Notification(
            notificationId = 2,
            title = "Заказ в доставке",
            description = "He'll want to use your yacht, and I don't want this thing smelling like fish.",
            isViewed = true,
            userId = 1
        ),
        Notification(
            notificationId = 4,
            title = "Оплата не прошла",
            description = "He'll want to use your yacht, and I don't want this thing smelling like fish.",
            isViewed = false,
            userId = 1
        ),
    )
}