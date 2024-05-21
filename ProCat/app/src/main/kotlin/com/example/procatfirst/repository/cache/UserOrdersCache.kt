package com.example.procatfirst.repository.cache

import com.example.procatfirst.data.OrderFull
import com.example.procatfirst.data.OrderSmall

class UserOrdersCache {

    companion object {
        val shared = UserOrdersCache()
        const val identifier = "[AllOrdersCache]"
    }

    private var orders : MutableMap<Int, OrderFull> = HashMap()
    private var orderResponse: OrderSmall? = null

    fun getOrders() : List<OrderFull> {
        return orders.values.toList()
    }

    fun setOrders(newOrders : List<OrderFull>) {
        orders.clear()
        for (order in newOrders) {
            orders[order.id] = order
        }
    }

    fun getOrder(id : Int) : OrderFull? {
        return orders[id]
    }

    fun changeStatus(id : Int, status : String) {
        orders[id]?.status = status
    }

    fun getOrderResponse() : OrderSmall? {
        return orderResponse
    }

    fun setOrderResponse(order: OrderSmall?) {
        orderResponse = order
    }

}