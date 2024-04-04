package com.example.procatfirst.repository.cache

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import com.example.procatfirst.data.Order
import com.example.procatfirst.data.OrderDataProvider
import com.example.procatfirst.data.User

class AllOrdersCache {

    var orders : MutableList<Order> = OrderDataProvider.deliveryOrders.toMutableList()

    companion object {
        val shared = AllOrdersCache()
        const val identifier = "[AllOrdersCache]"
    }

    fun getAllOrders() : MutableList<Order> {
        return orders
    }

    fun setAllOrders(ordersData : MutableList<Order>) {
        orders = ordersData
    }

    fun setOrder(order : Order) {
        orders[orders.indexOf(order)] = order
    }

    fun addOrder(order : Order) {
        orders.add(order)
    }

}