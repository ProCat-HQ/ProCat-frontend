package com.example.procatfirst.data

import kotlinx.serialization.Serializable

@Serializable
data class Order(
    val orderId: Int,
    val status: String,
    val totalPrice: Int,
    val rentalPeriod: String,
    val address: String,
    val companyName: String,
    val contract: Int,
    val userId: Int
)


object OrderDataProvider {
    val orders = listOf(
        Order(
            orderId = 1,
            status = "Выполнен",
            totalPrice = 800,
            rentalPeriod = "30.04.2023-23.06.2023",
            address = "RooStreet, 5",
            companyName = "ProCat",
            contract = 898,
            userId = 1
        ),
        Order(
            orderId = 2,
            status = "Выполнен",
            totalPrice = 800,
            rentalPeriod = "30.04.2023-23.06.2023",
            address = "RooStreet, 5",
            companyName = "ProCat",
            contract = 898,
            userId = 1
        ),
        Order(
            orderId = 3,
            status = "Выполнен",
            totalPrice = 800,
            rentalPeriod = "30.04.2023-23.06.2023",
            address = "RooStreet, 5",
            companyName = "ProCat",
            contract = 898,
            userId = 1
        ),
        Order(
            orderId = 4,
            status = "Выполнен",
            totalPrice = 800,
            rentalPeriod = "30.04.2023-23.06.2023",
            address = "RooStreet, 5",
            companyName = "ProCat",
            contract = 898,
            userId = 1
        )
    )

}