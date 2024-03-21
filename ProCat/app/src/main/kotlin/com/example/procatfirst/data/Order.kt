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
    val deliveryOrders = listOf(
        Order(
            orderId = 1,
            status = "Ожидает доставки",
            totalPrice = 8000,
            rentalPeriod = "30.04.2023-23.06.2023",
            address = "Пирогова, 1",
            companyName = "54.843243 83.088801",
            contract = 897,
            userId = 1
        ),
        Order(
            orderId = 2,
            status = "Ожидает возврата",
            totalPrice = 900,
            rentalPeriod = "30.04.2023-23.06.2023",
            address = "Николаева, 11/5",
            companyName = "54.858720 83.111537",
            contract = 898,
            userId = 2
        ),
        Order(
            orderId = 3,
            status = "Ожидает доставки",
            totalPrice = 1700,
            rentalPeriod = "30.04.2023-23.06.2023",
            address = "Терешковой, 19",
            companyName = "54.843569 83.109435",
            contract = 899,
            userId = 1
        )
    )

}