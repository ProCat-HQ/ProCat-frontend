package com.example.procatfirst.data


data class CartPayload(
    val items: Set<CartItem>
)

data class CartItem(
    val id: Int,
    val name: String,
    val price: Int,
    val count: Int,
    val image: String
)
// ---------------------------


data class OrdersPayload(
    val count: Int,
    val rows: List<OrderFull>
)

data class OrderFull(
    val id: Int,
    val status: String,
    val totalPrice: Int,
    val deposit: Int,
    val rentalPeriodStart: String,
    val rentalPeriodEnd: String,
    val address: String,
    val latitude: String,
    val longitude: String,
    val companyName: String?,
    val createdAt: String,
    val items: List<OrderItem>
)

data class OrderItem(
    val id: Int,
    val name: String,
    val price: Int,
    val count: Int,
    val image: String
)

// ---------------------------

data class OrderRequest(
    val rentalPeriodStart: String,
    val rentalPeriodEnd: String,
    val address: String,
    val companyName: String?,
    val deliveryMethod: String,
    val deliveryTimeStart: String,
    val deliveryTimeEnd: String
    )


data class PaymentPayload(
    val payments: List<Payment>
)

data class Payment(
    val id: Int,
    val paid: Int,
    val method: String,
    val price: Int,
    val createdAt: String
)


