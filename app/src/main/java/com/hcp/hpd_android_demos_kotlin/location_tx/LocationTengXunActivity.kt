package com.hcp.hpd_android_demos_kotlin.location_tx

import android.location.Location
import android.os.Bundle
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hcp.hpd_android_demos_kotlin.R
import com.tencent.lbssearch.TencentSearch
import com.tencent.lbssearch.`object`.param.SearchParam
import com.tencent.lbssearch.`object`.result.Geo2AddressResultObject
import com.tencent.lbssearch.httpresponse.BaseObject
import com.tencent.map.geolocation.TencentLocation
import com.tencent.map.geolocation.TencentLocationListener
import com.tencent.map.geolocation.TencentLocationManager
import com.tencent.map.geolocation.TencentLocationRequest
import com.tencent.map.tools.net.http.HttpResponseListener
import com.tencent.tencentmap.mapsdk.maps.LocationSource
import com.tencent.tencentmap.mapsdk.maps.MapView
import com.tencent.tencentmap.mapsdk.maps.TencentMap
import com.tencent.tencentmap.mapsdk.maps.model.CameraPosition


class LocationTengXunActivity : AppCompatActivity(), TencentLocationListener, LocationSource {

    companion object {
        private const val TAG = "LocationTengXunActivity"
    }

    private lateinit var mapView: MapView
    private lateinit var map: TencentMap
    private lateinit var recyclerView: RecyclerView

    private lateinit var adapter: Adapter

    private lateinit var locationManager: TencentLocationManager
    private lateinit var locationRequest: TencentLocationRequest
    private var locationChangedListener: LocationSource.OnLocationChangedListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_location)

        mapView = findViewById(R.id.mapView)
        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = Adapter()
        recyclerView.adapter = adapter

        initMap()
    }

    private fun initMap() {

        map = mapView.map
        map.addOnMapLoadedCallback {
            Log.i(TAG, "addOnMapLoadedCallback")
        }
        map.setOnCameraChangeListener(object : TencentMap.OnCameraChangeListener {
            override fun onCameraChangeFinished(p0: CameraPosition?) {
                Log.i(
                    TAG,
                    "setOnCameraChangeListener onCameraChangeFinished: p0 = $p0"
                )
                search(p0)
            }

            override fun onCameraChange(p0: CameraPosition?) {
                Log.i(TAG, "setOnCameraChangeListener onCameraChange: p0 = $p0")
            }

        })
        map.mapType = TencentMap.MAP_TYPE_NORMAL

        locationManager = TencentLocationManager.getInstance(this)
        locationRequest = TencentLocationRequest.create()
        locationRequest.isAllowGPS = true
        locationRequest.interval = 3000
        map.setLocationSource(this)
        map.isMyLocationEnabled = true
    }

    private fun search(position: CameraPosition?) {
        if (position != null) {
            val tencentSearch = TencentSearch(this)
            val latLng = position.target
            val searchParam = SearchParam("深圳", SearchParam.Nearby(latLng, 100))
            tencentSearch.search(searchParam, object : HttpResponseListener<BaseObject> {

                override fun onSuccess(p0: Int, p1: BaseObject?) {
                    Log.i(TAG, "geo2address onSuccess p0=$p0,p1=$p1")
                    if (p1 == null) {
                        return
                    }
                    val data = p1 as Geo2AddressResultObject
                }

                override fun onFailure(p0: Int, p1: String?, p2: Throwable?) {
                    Log.i(TAG, "geo2address onFailure p0=$p0,p1=$p1")
                }
            })
        }
    }

    override fun onStart() {
        super.onStart()
        mapView.onStart()
    }

    override fun onRestart() {
        super.onRestart()
        mapView.onRestart()
    }

    override fun onResume() {
        super.onResume()
        mapView.onResume()
    }

    override fun onPause() {
        super.onPause()
        mapView.onPause()
    }

    override fun onStop() {
        super.onStop()
        mapView.onStop()
    }

    override fun onDestroy() {
        super.onDestroy()
        mapView.onDestroy()
    }

    inner class Adapter : RecyclerView.Adapter<Adapter.ViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

            val view =
                LayoutInflater.from(parent.context).inflate(R.layout.location_cell, parent, false)
            return ViewHolder(view)
        }

        override fun getItemCount(): Int {
            return 0
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
//            val item = poiItems[position]
//            holder.tvTitle.text = item.title
//            holder.tvSubTitle.text = item.snippet
//
//            holder.itemView.setOnClickListener {
//                finish()
//            }
        }

        inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

            var tvTitle: TextView
            var tvSubTitle: TextView

            init {
                tvTitle = itemView.findViewById(R.id.tvTitle)
                tvSubTitle = itemView.findViewById(R.id.tvSubTitle)
            }
        }
    }

    override fun onStatusUpdate(p0: String?, p1: Int, p2: String?) {
        Log.i(TAG, "onStatusUpdate p0=$p0,p1=$p1,p2=$p2")
    }

    override fun onLocationChanged(p0: TencentLocation?, p1: Int, p2: String?) {
        Log.i(TAG, "onLocationChanged p0=$p0,p1=$p1,p2=$p2")
        if (p0 != null && p1 == TencentLocation.ERROR_OK && locationChangedListener != null) {
            val location = Location(p0.provider)
            location.latitude = p0.latitude
            location.altitude = p0.altitude
            location.accuracy = p0.accuracy
            location.bearing = p0.bearing
            locationChangedListener?.onLocationChanged(location)
        }
    }

    override fun deactivate() {
        Log.i(TAG, "deactivate")
        //当不需要展示定位点时，需要停止定位并释放相关资源
        locationManager.removeUpdates(this)
        locationChangedListener = null
    }

    override fun activate(p0: LocationSource.OnLocationChangedListener?) {
        Log.i(TAG, "activate p0=$p0")
        locationChangedListener = p0
        val error =
            locationManager.requestSingleFreshLocation(locationRequest, this, Looper.myLooper())
        when (error) {
            1 -> Toast.makeText(
                this,
                "设备缺少使用腾讯定位服务需要的基本条件",
                Toast.LENGTH_SHORT
            ).show()
            2 -> Toast.makeText(
                this,
                "manifest 中配置的 key 不正确", Toast.LENGTH_SHORT
            ).show()
            3 -> Toast.makeText(
                this,
                "自动加载libtencentloc.so失败", Toast.LENGTH_SHORT
            ).show()
            else -> {
            }
        }
    }
}