package com.hcp.hpd_android_demos_kotlin.location

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
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
    private lateinit var recyclerView: RecyclerView
    private lateinit var aMap: AMap
    private lateinit var myLocationStyle: MyLocationStyle
    private var poiItems = ArrayList<PoiItem>()

    private lateinit var adapter: Adapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_location)

        mapView = findViewById(R.id.mapView)
        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = Adapter()
        recyclerView.adapter = adapter

        initMap(savedInstanceState)
    }

    private fun initMap(savedInstanceState: Bundle?) {

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
                    adapter.notifyDataSetChanged()
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

    inner class Adapter : RecyclerView.Adapter<Adapter.ViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

            val view =
                LayoutInflater.from(parent.context).inflate(R.layout.location_cell, parent, false)
            return ViewHolder(view)
        }

        override fun getItemCount(): Int {
            return poiItems.size
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val item = poiItems[position]
            holder.tvTitle.text = item.title
            holder.tvSubTitle.text = item.snippet

            holder.itemView.setOnClickListener {
                finish()
            }
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
}