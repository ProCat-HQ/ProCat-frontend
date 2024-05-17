package com.example.procatfirst.repository.data_coordinator

import com.example.procatfirst.data.OrderFull
import com.example.procatfirst.data.OrdersPayload
import com.example.procatfirst.repository.api.ApiCalls
import com.example.procatfirst.repository.cache.UserDataCache
import com.example.procatfirst.repository.cache.UserOrdersCache

//callback: (List<OrderFull>) -> Unit

suspend fun DataCoordinator.getUserOrders() : List<OrderFull> {
    if (UserOrdersCache.shared.getOrders().isEmpty()) {
        ApiCalls.shared.getUserOrders(UserDataCache.shared.getUserData()!!.id
        ) { payload: OrdersPayload? ->
            if (payload?.rows != null) {
                UserOrdersCache.shared.setOrders(payload.rows)
            }
        }
    }
    //callback(UserOrdersCache.shared.getOrders())
    return UserOrdersCache.shared.getOrders()
}

suspend fun DataCoordinator.addUserOrder(order : OrderFull) : Boolean {

    return true

}