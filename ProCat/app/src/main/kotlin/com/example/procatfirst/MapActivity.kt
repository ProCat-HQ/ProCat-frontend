package com.example.procatfirst

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import ru.dublgis.dgismobile.mapsdk.LonLat
import ru.dublgis.dgismobile.mapsdk.Map
import ru.dublgis.dgismobile.mapsdk.MapFragment
import ru.dublgis.dgismobile.mapsdk.MapPointerEvent
import ru.dublgis.dgismobile.mapsdk.MarkerOptions
import ru.dublgis.dgismobile.mapsdk.directions.CarRouteOptions
import ru.dublgis.dgismobile.mapsdk.directions.DirectionsOptions
import ru.dublgis.dgismobile.mapsdk.labels.Label
import ru.dublgis.dgismobile.mapsdk.labels.LabelOptions
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

        mapFragment.mapReadyCallback = this::onDGisMapReady
        //mapFragment.mapReadyCallback = this::onClickListenerReady
        //mapFragment.mapReadyCallback = this::onDirectionReady

    }

    private fun onDGisMapReady(map: Map?) {
        val labels = mutableListOf<Label?>()
        val goButton = addActionButton {
            onDirectionReady(map)
            it.visibility = View.INVISIBLE
        }
        goButton.visibility = View.INVISIBLE

        map?.let {
            val marker = it.addMarker(
                MarkerOptions(
                    LonLat(83.0888, 54.8432)
                )
            )
            marker.setOnClickListener {
                labels.add(showLabel(map, LonLat(83.0887, 54.8431), "Заказ №6423"))
                goButton.visibility = View.VISIBLE
            }
        }
        map?.setOnClickListener {
            for (i in labels) {
                i?.hide()
            }
            goButton.visibility = View.INVISIBLE
        }

    }

    private fun showLabel(map: Map?, coords: LonLat, text: String): Label? {
        return map?.createLabel(
            LabelOptions(
                coordinates = coords,
                text = text
            )
        )
    }

    private fun onDirectionReady(map: Map?) {
        map?.enableUserLocation(UserLocationOptions(isVisible = true))
        map?.userLocation?.observe(this, Observer {})
        val direct = map?.createDirections(DirectionsOptions(BuildConfig.apiKey))
            //createToast(map?.userLocation?.value.toString()) //null
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

    private fun addActionButton(action: (View) -> Unit) : Button {
            val btn = findViewById<Button>(R.id.zoom_in)
            btn.setOnClickListener( action )
            return btn
    }

    private fun createToast(@Suppress("UNUSED_PARAMETER") view: View?, body: String?) {
        val myToast = Toast.makeText(this, body, Toast.LENGTH_SHORT)
        myToast.show()
    }

    private fun createToast(body: String?) {
        val myToast = Toast.makeText(this, body, Toast.LENGTH_SHORT)
        myToast.show()
    }

}
