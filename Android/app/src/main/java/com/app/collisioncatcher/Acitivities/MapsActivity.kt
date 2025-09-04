package com.app.collisioncatcher.Acitivities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.app.collisioncatcher.Entity.MapData

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.app.collisioncatcher.R
import com.app.collisioncatcher.androidClient.HardwareApi
import com.app.soulplace.androidClient.RetrofitService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var latitude : String
    private lateinit var longitude : String // Example: San Francisco

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)

        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        fetchLocation()
    }
    private fun fetchLocation() {
        RetrofitService().getPlaneRetrofit().create<HardwareApi>(HardwareApi::class.java).getGpsData().enqueue(object:
            Callback<MapData>
        {
            override fun onResponse(call: Call<MapData>, response: Response<MapData>) {
                if(response.code()==200)
                {
                    val location = LatLng(28.4572,77.4983)
                    mMap.addMarker(MarkerOptions().position(location).title("ESP32 Location"))
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 15f))
                }
                else
                {
                    Log.e("Error While Getting the Location",response.message())
                }
            }

            override fun onFailure(call: Call<MapData>, t: Throwable) {
                Log.e("Error While Connecting to the Server",t.message.toString())
            }
        })
    }
}