package com.example.procatfirst

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.example.procatfirst.repository.api.ApiCalls
import ru.dublgis.dgismobile.mapsdk.LonLat
import ru.dublgis.dgismobile.mapsdk.Map
import ru.dublgis.dgismobile.mapsdk.MapFragment
import ru.dublgis.dgismobile.mapsdk.MapPointerEvent
import ru.dublgis.dgismobile.mapsdk.MarkerClickCallback
import ru.dublgis.dgismobile.mapsdk.MarkerOptions
import ru.dublgis.dgismobile.mapsdk.directions.CarRouteOptions
import ru.dublgis.dgismobile.mapsdk.directions.DirectionsOptions
import ru.dublgis.dgismobile.mapsdk.location.UserLocationOptions
import ru.dublgis.dgismobile.mapsdk.mapObjectsByIds


class MapActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map)

        val mapFragment = supportFragmentManager.findFragmentById(R.id.map_fragment) as MapFragment

        mapFragment.setup(
            apiKey = BuildConfig.apiKey,
            center = LonLat(83.0888, 54.8432),
            zoom = 16.0
        )

        //mapFragment.mapReadyCallback = this::onDGisMapReady
        //mapFragment.mapReadyCallback = this::onClickListenerReady
        mapFragment.mapReadyCallback = this::onDirectionReady

    }

    private fun onDGisMapReady(map: Map?) {
        map?.let {
            val marker = it.addMarker(
                MarkerOptions(
                    LonLat(83.0888, 54.8432)
                )
            )
            marker.setOnClickListener { println("ooo") }
        }
    }

    private fun onDirectionReady(map: Map?) {
        map?.enableUserLocation(UserLocationOptions(isVisible = true))
        map?.userLocation?.observe(this, Observer {})
        val direct = map?.createDirections(DirectionsOptions(BuildConfig.apiKey))
        val opt = CarRouteOptions(listOf(LonLat(83.0888, 54.8432), LonLat(83.0888, 54.8632)))
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

    private fun createToast(body: String?) {
        val myToast = Toast.makeText(this, body, Toast.LENGTH_SHORT)
        myToast.show()
    }

    private fun runApi(url: String) {
        ApiCalls.shared.runApi()
    }

}
