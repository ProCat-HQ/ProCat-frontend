package com.example.procatfirst

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.procatfirst.data.Point
import com.example.procatfirst.repository.api.ApiCalls
import com.example.procatfirst.repository.cache.AllOrdersCache
import kotlinx.coroutines.launch
import ru.dublgis.dgismobile.mapsdk.LonLat
import ru.dublgis.dgismobile.mapsdk.Map
import ru.dublgis.dgismobile.mapsdk.MapFragment
import ru.dublgis.dgismobile.mapsdk.MapPointerEvent
import ru.dublgis.dgismobile.mapsdk.Marker
import ru.dublgis.dgismobile.mapsdk.MarkerOptions
import ru.dublgis.dgismobile.mapsdk.directions.CarRouteOptions
import ru.dublgis.dgismobile.mapsdk.directions.Directions
import ru.dublgis.dgismobile.mapsdk.directions.DirectionsOptions
import ru.dublgis.dgismobile.mapsdk.labels.Label
import ru.dublgis.dgismobile.mapsdk.labels.LabelOptions
import ru.dublgis.dgismobile.mapsdk.location.UserLocationOptions
import ru.dublgis.dgismobile.mapsdk.mapObjectsByIds


class MapActivity : AppCompatActivity() {

    private var location = LonLat(54.843243, 83.088801)
    private var direct : Directions? = null
    private var points : MutableList<Point>? = null
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map)

        val mapFragment = supportFragmentManager.findFragmentById(R.id.map_fragment) as MapFragment

        mapFragment.setup(
            apiKey = BuildConfig.apiKey,
            center = LonLat(83.0888, 54.8432),
            zoom = 16.0
        )

        mapFragment.mapReadyCallback = this::onDGisMapReady

    }

    private fun onDGisMapReady(map: Map?) {
        val labels = mutableListOf<Label?>()
        val markers = mutableMapOf<Int, Marker?>()
        val finishes = mutableListOf<Button>()
        val goButton = addActionButton {
            onDirectionReady(map)
            it.visibility = View.INVISIBLE
        }

        map?.enableUserLocation(UserLocationOptions(isVisible = true))

        val outButton = addOutButton {
            //start main activity
            startActivity(Intent(this, MainActivity().javaClass))
        }
        points = (AllOrdersCache.shared.getPoints() as MutableList<Point>?)!!
        map?.let { map1 ->
            for (order in points!!) {
                val marker = map1.addMarker(
                    MarkerOptions(
                        LonLat(order.longitude.toDouble(), order.latitude.toDouble())
                    )
                )
                val finishButton = addFinishButton {
                    it.visibility = View.INVISIBLE
                   // map.hideMarker(marker)
//                    for (m in markers) {
//                        if (m.value == marker) {
//                            markers.remove(m.key)
//                            break
//                        }
//                    }
                    finishOrder(AllOrdersCache.shared.ordersToDelivery!!.size - order.deliveryId + 1) // Дичь полная, надо разобраться, но очень не хочется
                    //finishOrder(order.deliveryId)
                }
                finishButton.visibility = View.INVISIBLE
                finishes.add(finishButton)
                marker.setOnClickListener {
                    location = marker.position
                    Log.i("LOCATION", location.toString())
                    getAndShowOrderInfo(order.deliveryId) {
                        labels.add(showLabel(map, marker.position, it))
                    }
                    goButton.visibility = View.INVISIBLE
                    finishButton.visibility = View.VISIBLE
                }
                markers[order.deliveryId] = marker
            }
        }
        map?.setOnClickListener {
            for (i in labels) {
                i?.hide()
            }
            for (i in finishes) {
                i.visibility = View.INVISIBLE
            }
            goButton.visibility = View.VISIBLE
            direct?.clear()
        }

    }

    private fun showLabel(map: Map?, coords: LonLat, text: String): Label? {
        return map?.createLabel(
            LabelOptions(
                coordinates = coords,
                text = text,
                haloRadius = 1f,
                fontSize = 14f,
                haloColor = 2258161111.toInt(),
            )

        )
    }

    private fun getAndShowOrderInfo(id: Int, callback: (String) -> Unit) {
        lifecycleScope.launch {
            ApiCalls.shared.getOrderApi(id) {
                if (it?.items != null) {
                    var result = "Номер заказа: $id, инструмент: "
                    for (i in it.items) {
                        result += "${i.name}: ${i.count} "
                    }
                    callback(result)
                }
            }
        }
    }

    private fun finishOrder(id: Int) {
        Log.v("FINISED", id.toString())
        lifecycleScope.launch {
            for (or in AllOrdersCache.shared.ordersToDelivery!!) {
                if (or.deliveryId == id) {
                    AllOrdersCache.shared.ordersToDelivery?.remove(or)
                    break
                }
            }
            ApiCalls.shared.getOrderApi(id) {
                val newStatus = if (it?.status == "delivering") {
                    "rent"
                } else {
                    "returned"
                }
                ApiCalls.shared.changeStatusForDeliveryFromDeliveryIdApi(id, newStatus) {it1 ->
                    Log.d("MAP-ORDER", it1)
                }
            }
        }
    }

    private fun onDirectionReady(map: Map?) {

        direct = map?.createDirections(DirectionsOptions(BuildConfig.apiKey))
        //points?.add(0, Point("Склад", map?.userLocation?.value?.longitude.toString(), map?.userLocation?.value?.latitude.toString(), 0 ))
        points?.add(0, Point("Склад", AllOrdersCache.shared.storage!!.lon.toString(), AllOrdersCache.shared.storage!!.lat.toString(), 0 ))
        val pointList = mutableListOf<LonLat>()
        for (point in points!!) {
            pointList.add(LonLat(point.longitude.toDouble(), point.latitude.toDouble()))
        }
        val opt = CarRouteOptions(pointList)
        direct?.carRoute(opt)
    }


    private fun onClickListenerReady(map: Map?) {
        map?.setOnClickListener { event : MapPointerEvent ->
            val coordinates = "${event.lngLat.lat}° N, ${event.lngLat.lon}° E";
            Toast.makeText(this, coordinates, Toast.LENGTH_LONG).show();
            val objectId = event.target?.id;
            Toast.makeText(this, objectId.toString(), Toast.LENGTH_LONG).show();
            map.setSelectedObjects(mapObjectsByIds(objectId.toString()))
            Log.i(
                "Map click",
                coordinates + ' ' + objectId.toString()
            )
        }
    }

    private fun addActionButton(action: (View) -> Unit) : Button {
            val btn = findViewById<Button>(R.id.go_button)
            btn.setOnClickListener( action )
            return btn
    }

    private fun addFinishButton(action: (View) -> Unit) : Button {
        val btn = findViewById<Button>(R.id.finish_button)
        btn.setOnClickListener( action )
        return btn
    }

    private fun addOutButton(action: (View) -> Unit) : Button {
        val btn = findViewById<Button>(R.id.out)
        btn.setOnClickListener( action )
        return btn
    }

    private fun createToast(body: String?) {
        val myToast = Toast.makeText(this, body, Toast.LENGTH_SHORT)
        myToast.show()
    }

}
