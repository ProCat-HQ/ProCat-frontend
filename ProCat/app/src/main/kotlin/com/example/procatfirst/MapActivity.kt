package com.example.procatfirst

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.procatfirst.repository.api.ApiCalls
import ru.dublgis.dgismobile.mapsdk.LonLat
import ru.dublgis.dgismobile.mapsdk.MapFragment


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

    }

    private fun createToast(body: String?) {
        val myToast = Toast.makeText(this, body, Toast.LENGTH_SHORT)
        myToast.show()
    }

    private fun runApi(url: String) {
        ApiCalls.shared.runApi()
    }

}
