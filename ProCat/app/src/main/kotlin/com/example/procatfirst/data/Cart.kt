package com.example.procatfirst.data

import com.squareup.moshi.Json
import kotlinx.serialization.Serializable

@Serializable
data class CartResponse(
    val status: Int,
    val message: String,
    val payload: CartPayload
)


@Serializable
data class CartPayload(
    val items: Set<CartItem>
)

@Serializable
data class CartItem(
    val id: Int,
    val name: String,
    val price: Int,
    val count: Int,
    val image: String
)
// ---------------------------

data class OrdersResponse (
    val status: Int,
    val message: String,
    val payload: OrdersPayload,
)

data class OrdersPayload(
    val count: Int,
    val rows: List<OrderFull>
)

data class OrderFull(
    val id: Int,
    var status: String,
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
    val deliveryTimeEnd: String,
    )

data class NewOrderResponse(
    val status: Int,
    val message: String,
    val payload: OrderSmall,
)

data class OrderSmall(
    val orderId: Int,
    val totalPrice: Int,
    val deposit: Int,
    val items: List<OrderItemSmall>,
)

data class OrderItemSmall(
    val name: String,
    val count: Int,
    val price: Int,
    val priceDeposit: Int,
)

data class PaymentResponse(
    val status: Int,
    val message: String,
    val payload: PaymentPayload
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


