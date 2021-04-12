package com.hcp.hpd_android_demos_kotlin

import android.Manifest
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.hcp.hpd_android_demos_kotlin.location_tx.LocationTengXunActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val enterBtn = findViewById<Button>(R.id.btn_enter)
        enterBtn.setOnClickListener {
            val intent = Intent(this@MainActivity, LocationTengXunActivity::class.java)
            startActivity(intent)
        }

//        ActivityCompat.requestPermissions(
//            this,
//            arrayOf(
//                Manifest.permission.ACCESS_COARSE_LOCATION,
//                Manifest.permission.ACCESS_FINE_LOCATION,
//                Manifest.permission.ACCESS_LOCATION_EXTRA_COMMANDS,
//                Manifest.permission.WRITE_EXTERNAL_STORAGE,
//                Manifest.permission.READ_PHONE_STATE,
//                Manifest.permission.ACCESS_NETWORK_STATE
//
//            ),
//            1
//        );
    }
}