package com.example.procatfirst.repository.cache

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import com.example.procatfirst.data.Order
import com.example.procatfirst.data.OrderDataProvider
import com.example.procatfirst.data.Point
import com.example.procatfirst.data.User
import ru.dublgis.dgismobile.mapsdk.LonLat

class AllOrdersCache {

    var orders : MutableList<Order> = OrderDataProvider.deliveryOrders.toMutableList()
    var adressToCoords : MutableState<LonLat> = mutableStateOf( LonLat(54.843243, 83.088801) )
    var ordersToDelivery: List<Point>? = null
    public var storage: LonLat? = null

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

    fun setCoords(coords : LonLat) {
        adressToCoords = mutableStateOf(coords)
    }

    fun getCoords() : LonLat {
        return adressToCoords.value
    }

    fun setPoints(points : List<Point>) {
        ordersToDelivery = points
    }

    fun getPoints() : List<Point>? {
        return ordersToDelivery
    }

}