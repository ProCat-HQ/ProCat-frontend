package com.example.procatfirst.repository.data_coordinator

import com.example.procatfirst.data.Order
import com.example.procatfirst.repository.cache.AllOrdersCache

fun DataCoordinator.setOrderStatus(order : Order, status : String) {
    val newOrder : Order = order
    newOrder.status = status
    AllOrdersCache.shared.setOrder(order)
}

fun DataCoordinator.getAllOrders() : List<Order> {
    return AllOrdersCache.shared.getAllOrders()
}