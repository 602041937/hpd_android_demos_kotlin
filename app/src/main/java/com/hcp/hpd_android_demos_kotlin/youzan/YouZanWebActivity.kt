package com.hcp.hpd_android_demos_kotlin.youzan

import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.hcp.hpd_android_demos_kotlin.R
import com.youzan.androidsdk.YouzanSDK
import com.youzan.androidsdk.YouzanToken
import com.youzan.androidsdk.YzLoginCallback
import com.youzan.androidsdk.event.AbsAuthEvent
import com.youzan.androidsdk.event.AbsCheckAuthMobileEvent
import com.youzan.androidsdk.event.AbsPaymentFinishedEvent
import com.youzan.androidsdk.model.trade.TradePayFinishedModel
import com.youzan.androidsdkx5.YouzanBrowser

class YouZanWebActivity : AppCompatActivity() {

    private lateinit var webView: YouzanBrowser

    companion object {
        var url =
            "https://shop41610176.youzan.com/v2/showcase/homepage?alias=d8PgEGLuap&dc_ps=2605582907183549446.300001"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_you_zan_web)

        webView = findViewById(R.id.webView)

        webView.loadUrl(url)

        webView.subscribe(object : AbsCheckAuthMobileEvent() {})

        webView.subscribe(object : AbsAuthEvent() {

            override fun call(p0: Context?, p1: Boolean) {

                YouzanSDK.yzlogin("185113748736", "", "", "", "0", object : YzLoginCallback {
                    override fun onSuccess(p0: YouzanToken?) {

                        webView.post {
                            webView.sync(p0)
                            Toast.makeText(this@YouZanWebActivity, "登录成功", Toast.LENGTH_SHORT)
                                .show()
                        }
                    }

                    override fun onFail(p0: String?) {

                    }
                })
            }
        })

        webView.subscribe(object : AbsPaymentFinishedEvent() {
            override fun call(p0: Context?, p1: TradePayFinishedModel?) {
                Toast.makeText(p0, "支付成功", Toast.LENGTH_SHORT).show()
            }
        })
    }

    override fun onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack()
        } else {
            super.onBackPressed()
        }
    }
}