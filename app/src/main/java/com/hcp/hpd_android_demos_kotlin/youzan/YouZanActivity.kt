package com.hcp.hpd_android_demos_kotlin.youzan

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.hcp.hpd_android_demos_kotlin.R


class YouZanActivity : AppCompatActivity() {

    companion object {
        private const val url =
            "https://shop41610176.youzan.com/v2/showcase/homepage?alias=d8PgEGLuap&dc_ps=2605582907183549446.300001"
        private const val urlSingle =
            "https://shop41610176.m.youzan.com/wscgoods/detail/2flkz20he3om0"
        private const val youhuiquan =
            "https://shop41610176.youzan.com/wscump/coupon/fetch?alias=7g9imhl5&sign=1972575ee9d48a8b5a7f85adeb09802d&shopAutoEnter=1"
    }

    private lateinit var button1: Button
    private lateinit var button2: Button
    private lateinit var button3: Button
    private lateinit var button4: Button
    private lateinit var button5: Button
    private lateinit var button6: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_you_zan)

        button1 = findViewById(R.id.button1)
        button2 = findViewById(R.id.button2)
        button3 = findViewById(R.id.button3)
        button4 = findViewById(R.id.button4)
        button5 = findViewById(R.id.button5)
        button6 = findViewById(R.id.button6)

        button1.setOnClickListener {
            val uri = Uri.parse(url)
            val intent = Intent(Intent.ACTION_VIEW, uri)
            startActivity(intent)
        }
        button2.setOnClickListener {
            val uri = Uri.parse(urlSingle)
            val intent = Intent(Intent.ACTION_VIEW, uri)
            startActivity(intent)
        }
        button3.setOnClickListener {
            YouZanWebActivity.url = url
            val intent = Intent(
                this, YouZanWebActivity::class.java
            )
            startActivity(intent)
        }
        button4.setOnClickListener {
            YouZanWebActivity.url = urlSingle
            val intent = Intent(
                this, YouZanWebActivity::class.java
            )
            startActivity(intent)
        }
        button5.setOnClickListener {

        }
        button6.setOnClickListener {
            YouZanWebActivity.url = youhuiquan
            val intent = Intent(
                this, YouZanWebActivity::class.java
            )
            startActivity(intent)
        }
    }
}