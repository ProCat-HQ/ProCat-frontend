package com.example.procatfirst.data


data class DeliverymenResponse(
    val status: Int,
    val message: String,
    val payload: DeliverymenPayload
)
data class DeliverymenPayload(
    val count: Int,
    val rows: List<Deliveryman>
)

data class Deliveryman(
    val id: Int,
    val carCapacity: String,
    val workingHoursStart: String,
    val workingHoursEnd: String,
    val carId: String,
    val fullName: String,
    val email: String,
    val phoneNumber: String
)

data class SimpleDeliverymenResponse(
    val status: Int,
    val message: String,
    val payload: SimpleDeliveryman
)

data class SimpleDeliveryman(
    val id: Int,
    val carCapacity: String,
    val workingHoursStart: String,
    val workingHoursEnd: String,
    val carId: String,
)

// ---------------------------

data class AllDeliveriesForDeliverymanResponse(
    val status: Int,
    val message: String,
    val payload: DeliveryPayload
)
data class DeliveryPayload(
    val count: Int,
    val rows: List<Delivery>
)

data class Delivery(
    val id: Int,
    val timeStart: String,
    val timeEnd: String,
    val method: String,
    val order: DeliveryOrder
)

data class DeliveryOrder(
    val id: Int,
    val status: String,
    val totalPrice: Int,
    //val deposit: Int,
    val address: String,
    val latitude: String,
    val longitude: String
)

// ---------------------------

data class RouteResponse(
    val status: Int,
    val message: String,
    val payload: RoutePayload
)
data class RoutePayload(
    val points: List<Point>
)

data class Point(
    val address: String,
    val latitude: String,
    val longitude: String,
    val deliveryId: Int,
)

//------------------------------

data class DeliveryResponse(
    val status: Int,
    val message: String,
    val payload: Delivery
)



