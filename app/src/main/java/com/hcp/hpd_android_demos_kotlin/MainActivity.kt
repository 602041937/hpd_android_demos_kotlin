package com.hcp.hpd_android_demos_kotlin

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.hcp.hpd_android_demos_kotlin.custom_view.synthesis_score.SynthesisScoreActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val enterBtn = findViewById<Button>(R.id.btn_enter)
        enterBtn.setOnClickListener {
            val intent = Intent(this@MainActivity, SynthesisScoreActivity::class.java)
            startActivity(intent)
        }
    }
}