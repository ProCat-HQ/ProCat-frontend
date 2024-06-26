package com.example.procatfirst.repository.data_coordinator

import com.example.procatfirst.data.OrderFull
import com.example.procatfirst.data.OrderRequest
import com.example.procatfirst.data.OrderSmall
import com.example.procatfirst.data.OrdersPayload
import com.example.procatfirst.repository.api.ApiCalls
import com.example.procatfirst.repository.cache.UserDataCache
import com.example.procatfirst.repository.cache.UserOrdersCache


suspend fun DataCoordinator.getUserOrders(callback : (orders : List<OrderFull>) -> Unit) {

        ApiCalls.shared.getUserOrders(UserDataCache.shared.getUserData()!!.id
        ) { payload: OrdersPayload? ->
            if (payload?.rows != null) {
                UserOrdersCache.shared.setOrders(payload.rows)
                callback(UserOrdersCache.shared.getOrders())
            }
        }
}

suspend fun DataCoordinator.createNewOrder(order : OrderRequest, callback: (OrderSmall?) -> Unit) {
    ApiCalls.shared.createNewOrder(order) {
        callback(it)
    }
}