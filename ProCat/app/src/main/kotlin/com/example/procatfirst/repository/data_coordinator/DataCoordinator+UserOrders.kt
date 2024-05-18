package com.example.procatfirst.repository.data_coordinator

import com.example.procatfirst.data.OrderFull
import com.example.procatfirst.data.OrderRequest
import com.example.procatfirst.data.OrderSmall
import com.example.procatfirst.data.OrdersPayload
import com.example.procatfirst.repository.api.ApiCalls
import com.example.procatfirst.repository.cache.UserDataCache
import com.example.procatfirst.repository.cache.UserOrdersCache


suspend fun DataCoordinator.getUserOrders() : List<OrderFull> {
    //if (UserOrdersCache.shared.getOrders().isEmpty()) {
        ApiCalls.shared.getUserOrders(UserDataCache.shared.getUserData()!!.id
        ) { payload: OrdersPayload? ->
            if (payload?.rows != null) {
                UserOrdersCache.shared.setOrders(payload.rows)
            }
        }
    //}

    return UserOrdersCache.shared.getOrders()
}

suspend fun DataCoordinator.createNewOrder(order : OrderRequest) : OrderSmall? {
    var orderResponse : OrderSmall? = null
    ApiCalls.shared.createNewOrder(order) {
        ord : OrderSmall? ->
        orderResponse = ord
    }
    return orderResponse
}