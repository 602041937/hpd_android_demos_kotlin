package com.hcp.hpd_android_demos_kotlin.location

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.amap.api.maps.AMap
import com.amap.api.maps.CameraUpdateFactory
import com.amap.api.maps.MapView
import com.amap.api.maps.model.CameraPosition
import com.amap.api.maps.model.MyLocationStyle
import com.amap.api.services.core.LatLonPoint
import com.amap.api.services.core.PoiItem
import com.amap.api.services.poisearch.PoiResult
import com.amap.api.services.poisearch.PoiSearch
import com.hcp.hpd_android_demos_kotlin.R

class LocationActivity : AppCompatActivity() {

    companion object {
        private const val TAG = "LocationActivity"
    }

    private lateinit var mapView: MapView
    private lateinit var aMap: AMap
    private lateinit var myLocationStyle: MyLocationStyle
    private var poiItems = ArrayList<PoiItem>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_location)

        mapView = findViewById(R.id.mapView)
        mapView.onCreate(savedInstanceState)
        aMap = mapView.map
        aMap.setOnMyLocationChangeListener {
            Log.i(Companion.TAG, "setOnMyLocationChangeListener: $it")
        }
        aMap.moveCamera(CameraUpdateFactory.zoomTo(15.0f))
        //定位
        myLocationStyle = MyLocationStyle()
        //定位一次并显示在中心
        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATE)
        //显示定位蓝点
        myLocationStyle.showMyLocation(true)
        aMap.setMyLocationStyle(myLocationStyle)
        //显示定位按钮
        aMap.uiSettings.isMyLocationButtonEnabled = true
        //开启定位功能
        aMap.isMyLocationEnabled = true

        aMap.setOnCameraChangeListener(object : AMap.OnCameraChangeListener {

            override fun onCameraChangeFinish(p0: CameraPosition?) {
                Log.i(
                    Companion.TAG,
                    "setOnCameraChangeListener onCameraChangeFinish: p0 = $p0"
                )
                search(p0)
            }

            override fun onCameraChange(p0: CameraPosition?) {
                Log.i(Companion.TAG, "setOnCameraChangeListener onCameraChange: p0 = $p0")

            }
        })
    }

    private fun search(position: CameraPosition?) {
        if (position != null) {
            val poiSearch = PoiSearch(this, PoiSearch.Query("", ""))
            val latLonPoint = LatLonPoint(position.target.latitude, position.target.longitude)
            poiSearch.bound = PoiSearch.SearchBound(latLonPoint, 1000)
            poiSearch.setOnPoiSearchListener(object : PoiSearch.OnPoiSearchListener {

                override fun onPoiItemSearched(p0: PoiItem?, p1: Int) {
                    Log.i(
                        Companion.TAG,
                        "setOnPoiSearchListener onPoiItemSearched: p0 = $p0,p1= $p1"
                    )
                }

                override fun onPoiSearched(p0: PoiResult?, p1: Int) {
                    Log.i(
                        Companion.TAG,
                        "setOnPoiSearchListener onPoiSearched: p0 = $p0,p1= $p1"
                    )
                    poiItems = p0?.pois ?: ArrayList<PoiItem>()
                }
            })
            poiSearch.searchPOIAsyn()
        }
    }

    override fun onResume() {
        super.onResume()
        mapView.onResume()
    }

    override fun onPause() {
        super.onPause()
        mapView.onPause()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        mapView.onSaveInstanceState(outState)
    }

    override fun onDestroy() {
        super.onDestroy()
        mapView.onDestroy()
    }

}