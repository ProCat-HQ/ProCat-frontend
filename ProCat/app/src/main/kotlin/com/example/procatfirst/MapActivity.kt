package com.example.procatfirst

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.procatfirst.repository.api.ApiCalls
import com.example.procatfirst.repository.data_storage_deprecated.DataCoordinatorOLD
import ru.dublgis.dgismobile.mapsdk.LonLat
import ru.dublgis.dgismobile.mapsdk.MapFragment
import ru.dublgis.dgismobile.mapsdk.Map
import ru.dublgis.dgismobile.mapsdk.MapPointerEvent
import ru.dublgis.dgismobile.mapsdk.MarkerOptions


class MapActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map)

        val mapFragment = supportFragmentManager.findFragmentById(R.id.map_fragment) as MapFragment

        mapFragment.setup(
            apiKey = BuildConfig.apiKey,
            center = LonLat(37.6175, 55.7520),
            zoom = 16.0
        )

        //mapFragment.mapReadyCallback = this::onDGisMapReady
        mapFragment.mapReadyCallback = this::onClickListenerReady
    }

    private fun onDGisMapReady(map: Map?) {
        map?.let {
            val marker = it.addMarker(
                MarkerOptions(
                LonLat(37.6175, 55.7520)
                )
            )
        }
    }

    private fun onClickListenerReady(map: Map?) {
        map?.setOnClickListener { event : MapPointerEvent ->
            val coordinates = "${event.lngLat.lat}° N, ${event.lngLat.lon}° E";
            Toast.makeText(this, coordinates, Toast.LENGTH_LONG).show();
            val objectId = event.target?.id;
            Toast.makeText(this, objectId.toString(), Toast.LENGTH_LONG).show();
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
